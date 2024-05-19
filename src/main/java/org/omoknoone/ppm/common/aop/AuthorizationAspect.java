package org.omoknoone.ppm.common.aop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.omoknoone.ppm.common.annotation.Permission;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.omoknoone.ppm.domain.commoncode.service.CommonCodeService;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;
import org.omoknoone.ppm.domain.permission.service.PermissionService;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
@Order(1)
public class AuthorizationAspect {

    private final HttpServletRequest request;
    private final ProjectMemberService projectMemberService;
    private final PermissionService permissionService;
    private final CommonCodeService commonCodeService;
    private final ScheduleService scheduleService;
    private final ObjectMapper objectMapper;

    @Pointcut("@annotation(org.omoknoone.ppm.common.annotation.Permission)")
    private void annotationPointcut() {}

    @Before("annotationPointcut()")
    public void checkProjectMemberPermission(JoinPoint joinPoint) throws AccessDeniedException {

        // 해당 메서드에 적용된 Permission 어노테이션의 값을 가져옴
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Permission permissionAnnotation = signature.getMethod().getAnnotation(Permission.class);

        // '권한 역할명' 공통 코드 조회
        List<CommonCodeResponseDTO> commonCodeList = commonCodeService
                                                        .viewCommonCodesByGroupName("권한 역할명");

        // 요청자의 권한 코드 목록 조회
        List<PermissionDTO> permissionDTOList = getRequesterPermissionCodeList();

        // 요청자의 가장 높은 권한 조회
        PermissionDTO permissionDTO = getHighestPermissionCode(permissionDTOList);

        // 요정차의 권한 이름 가져오기
        String requesterPermission = getRequesterPermission(commonCodeList, permissionDTO);

        if (permissionAnnotation.PM() && requesterPermission.equals("PM")) {            // PM 권한
            log.info("요청자의 권한은 {}입니다", requesterPermission);
        } else if (permissionAnnotation.PL() && requesterPermission.equals("PL")) {     // PL 권한

            // PL인 권한만 추출
            List<PermissionDTO> plPermissionList = getPermissionListByRoleName(
                                                                commonCodeList, permissionDTOList, requesterPermission);

            // 요청 일정 ID를 Body에서 가져오기
            String scheduleId = extractScheduleIdFromRequestBody();

            // 일정 담당 여부를 확인
            boolean isPlResponsibleForSchedule = checkResponsibleForSchedule(plPermissionList, scheduleId, true);

            if (!isPlResponsibleForSchedule) {
                throw new AccessDeniedException("Access Denied: 요청자가 해당 일정을 담당하지 않습니다.");
            }

            log.info("요청자의 권한은 {}입니다", requesterPermission);
        } else if (permissionAnnotation.PA() && requesterPermission.equals("PA")) {     // PA 권한
            // PA인 권한만 추출
            List<PermissionDTO> paPermissionList = getPermissionListByRoleName(
                                                                commonCodeList, permissionDTOList, requesterPermission);

            // 요청 일정 ID를 Body에서 가져오기
            String scheduleId = extractScheduleIdFromRequestBody();

            // 일정 담당 여부를 확인
            boolean isPlResponsibleForSchedule = checkResponsibleForSchedule(paPermissionList, scheduleId, false);

            if (!isPlResponsibleForSchedule) {
                throw new AccessDeniedException("Access Denied: 요청자가 해당 일정을 담당하지 않습니다.");
            }

            log.info("요청자의 권한은 {}입니다", requesterPermission);
        } else {                                                                        // 권한 없음
            throw new AccessDeniedException("Access Denied: 요청자의 권한 [" + requesterPermission + "]");
        }
    }

    private boolean checkResponsibleForSchedule(List<PermissionDTO> permissionDTOList, String scheduleId, boolean isPL) {
        Long requestScheduleId = Long.parseLong(scheduleId);

        for (PermissionDTO permissionDTO : permissionDTOList) {
            if (permissionDTO.getPermissionScheduleId().equals(requestScheduleId)) {
                return true;
            } else if (isPL) { // PL 권한인 경우에만 하위 일정 확인
                List<ScheduleDTO> subScheduleList = scheduleService
                                                            .viewSubSchedules(permissionDTO.getPermissionScheduleId());
                for (ScheduleDTO subSchedule : subScheduleList) {
                    if (subSchedule.getScheduleId().equals(requestScheduleId))
                        return true;
                }
            }
        }
        return false;
    }

    private List<PermissionDTO> getPermissionListByRoleName(
            List<CommonCodeResponseDTO> commonCodeList, List<PermissionDTO> permissionDTOList, String roleName) {

        List<PermissionDTO> permissionList = new ArrayList<>();

        for (CommonCodeResponseDTO commonCode : commonCodeList) {
            if (commonCode.getCodeName().equals(roleName)) {
                for (PermissionDTO permissionDTO : permissionDTOList) {
                    if (permissionDTO.getPermissionRoleName().equals(commonCode.getCodeId())) {
                        permissionList.add(permissionDTO);
                    }
                }
            }
        }
        return permissionList;
    }

    // 일정 ID를 Body에서 가져오기
    private String extractScheduleIdFromRequestBody() {
        try {
            // 요청 본문을 JSON 형식으로 파싱
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());
            // scheduleId 추출
            if (!requestBody.has("scheduleId")) {
                throw new IllegalArgumentException("Request body에 'scheduleId' 필드값이 없습니다");
            }
            return requestBody.get("scheduleId").asText();
        } catch (IOException e) {
            throw new RuntimeException("요청 본문을 읽는 중 오류 발생", e);
        }
    }

    // 요청자의 권한 코드 알아내기
    private List<PermissionDTO> getRequesterPermissionCodeList() {
        String employeeId = request.getHeader("employeeId");
        Integer projectId = Integer.parseInt(request.getHeader("projectId"));

        // projectId와 employeeId로 구성원Id 조회
        Integer projectMemberId = projectMemberService.viewProjectMemberId(employeeId, projectId);

        // 구성원Id로 권한 목록 조회 (PL or 개발자는 여러개 일 수 있다)
        return permissionService.viewMemberPermission(Long.valueOf(projectMemberId));
    }

    private PermissionDTO getHighestPermissionCode(List<PermissionDTO> projectPermissionList) {
        return projectPermissionList.stream()
                .min(Comparator.comparing(PermissionDTO::getPermissionRoleName))
                .orElse(null);
    }

    // 요정차의 권한 이름 가져오기
    private String getRequesterPermission(List<CommonCodeResponseDTO> commonCodeList, PermissionDTO permissionDTO) {
        String requesterPermission = null;
        for (CommonCodeResponseDTO commonCode : commonCodeList) {
            if (permissionDTO.getPermissionRoleName().equals(commonCode.getCodeId())){
                requesterPermission = commonCode.getCodeName();
            }
        }
        return requesterPermission;
    }
}
