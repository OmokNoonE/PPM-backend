package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
public class ViewProjectMembersByProjectResponseDTO {

    private Integer projectMemberId;
    private String projectMemberEmployeeId;
    private String employeeName;
    private Long codeId;
    private String codeName;
    private String employeeEmail;
    private String employeeContact;
    private LocalDateTime projectMemberCreatedDate;

    @Builder
    public ViewProjectMembersByProjectResponseDTO(Integer projectMemberId, String projectMemberEmployeeId, String employeeName, Long codeId, String codeName, String employeeEmail, String employeeContact, LocalDateTime projectMemberCreatedDate) {
        this.projectMemberId = projectMemberId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.employeeName = employeeName;
        this.codeId = codeId;
        this.codeName = codeName;
        this.employeeEmail = employeeEmail;
        this.employeeContact = employeeContact;
        this.projectMemberCreatedDate = projectMemberCreatedDate;
    }
}
