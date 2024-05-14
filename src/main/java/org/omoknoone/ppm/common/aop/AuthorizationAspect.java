package org.omoknoone.ppm.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.omoknoone.ppm.domain.commoncode.service.CommonCodeService;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;
import org.omoknoone.ppm.domain.permission.service.PermissionService;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class AuthorizationAspect {

    private final HttpServletRequest request;
    private final ProjectMemberService projectMemberService;
    private final PermissionService permissionService;
    private final CommonCodeService commonCodeService;
    private final Environment environment;

    @Pointcut("@annotation(org.omoknoone.ppm.common.annotation.Permission)")
    private void annotationPointcut() {}

    @Before("annotationPointcut()")
    public void checkProjectMemberPermission() throws AccessDeniedException {
        log.info("[AOP 실행]");
        // HttpServletRequest 객체를 사용하여 요청 헤더 정보 추출
        String employeeId = request.getHeader("employeeId");
        Integer projectId = Integer.parseInt(request.getHeader("projectId"));
        log.info("employeeId : {}, projectId : {}", employeeId, projectId);
        // projectId와 employeeId로 구성원Id 조회
        Integer projectMemberId = projectMemberService.viewProjectMemberId(employeeId, projectId);
        log.info("projectMemberID : {}", projectMemberId);

        // 구성원Id로 권한 목록 조회 (PL or 개발자는 여러개 일 수 있다)
        List<PermissionDTO> projectPermissionList = permissionService.viewMemberPermission(Long.valueOf(projectMemberId));
        for (PermissionDTO permissionDTO : projectPermissionList) {
            log.info("permissionDTO : {}", permissionDTO);
        }

        // 권한 역할명(PM, PL, 참여자) 조회
        List<CommonCodeResponseDTO> permissionNameCommonCodeList = commonCodeService.viewCommonCodesByGroup(106L);
        for (CommonCodeResponseDTO commonCodeResponseDTO : permissionNameCommonCodeList) {
            log.info("commonCodeResponseDTO : {}", commonCodeResponseDTO);
        }

        // 권한Id가 여러개일 경우(여러 일정을 담당하는 개발자 or 여러 섹션을 관리하는 PL)
        for (PermissionDTO projectPermission : projectPermissionList) {    // 프로젝트 권한 목록
            for (CommonCodeResponseDTO permissionNameCommonCode : permissionNameCommonCodeList) {    // 권한 역할 목록
                String codeName = permissionNameCommonCode.getCodeName();
                Long codeId = permissionNameCommonCode.getCodeId();

                // TODO. 여기 코드 수정 해야 함

                if("PM".equals(codeName) && codeId.equals(projectPermission.getPermissionRoleName())) {
                    log.info("PM이다!!!");
                    continue;
                } else if ("PL".equals(codeName) && codeId.equals(projectPermission.getPermissionProjectMemberId())) {
                    // 권한이 PL인 경우
                    log.info("PL이다!!!");
                } else {
                    throw new AccessDeniedException(environment.getProperty("exception.controller.accessDenied"));
                }
            }
        }
    }
}
