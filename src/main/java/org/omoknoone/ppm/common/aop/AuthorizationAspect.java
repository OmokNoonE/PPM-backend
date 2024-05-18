package org.omoknoone.ppm.common.aop;

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
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

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

        // 요청자의 권한 코드 알아내기
        PermissionDTO permissionDTO = getRequesterPermissionCode();

        // 요정차의 권한 이름 가져오기
        String requesterPermission = getRequesterPermission(commonCodeList, permissionDTO);

        if (permissionAnnotation.PM() && requesterPermission.equals("PM")) {            // PM 권한
            log.info("요청자의 권한은 {}입니다", requesterPermission);
        } else if (permissionAnnotation.PL() && requesterPermission.equals("PL")) {     // PL 권한
            log.info("요청자의 권한은 {}입니다", requesterPermission);
        } else if (permissionAnnotation.PA() && requesterPermission.equals("PA")) {     // PA 권한
            log.info("요청자의 권한은 {}입니다", requesterPermission);
        } else {                                                                        // 권한 없음
            throw new AccessDeniedException("Access Denied\n요청자의 권한 : " + requesterPermission);
        }
    }

    // 요청자의 권한 코드 알아내기
    private PermissionDTO getRequesterPermissionCode() {
        String employeeId = request.getHeader("employeeId");
        Integer projectId = Integer.parseInt(request.getHeader("projectId"));

        // projectId와 employeeId로 구성원Id 조회
        Integer projectMemberId = projectMemberService.viewProjectMemberId(employeeId, projectId);

        // 구성원Id로 권한 목록 조회 (PL or 개발자는 여러개 일 수 있다)
        List<PermissionDTO> projectPermissionList = permissionService
                                                            .viewMemberPermission(Long.valueOf(projectMemberId));

        // 가장 높은 권한을 가진 Permission 반환
        return projectPermissionList.stream()
                .min(Comparator.comparing(PermissionDTO::getPermissionRoleName))
                .orElse(null);
    }

    // 요정차의 권한 이름 가져오기
    private static String getRequesterPermission(List<CommonCodeResponseDTO> commonCodeList, PermissionDTO permissionDTO) {
        String requesterPermission = null;
        for (CommonCodeResponseDTO commonCode : commonCodeList) {
            if (permissionDTO.getPermissionRoleName().equals(commonCode.getCodeId())){
                requesterPermission = commonCode.getCodeName();
            }
        }
        return requesterPermission;
    }
}
