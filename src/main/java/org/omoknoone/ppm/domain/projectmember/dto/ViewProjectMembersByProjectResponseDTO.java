package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
public class ViewProjectMembersByProjectResponseDTO {

    private String employeeName;
    private Long projectMemberRoleName;
    private String employeeEmail;
    private String employeeContact;
    private LocalDateTime projectMemberCreatedDate;

    @Builder
    public ViewProjectMembersByProjectResponseDTO(String employeeName, Long projectMemberRoleName,
                                                String employeeEmail, String employeeContact, LocalDateTime projectMemberCreatedDate) {
        this.employeeName = employeeName;
        this.projectMemberRoleName = projectMemberRoleName;
        this.employeeEmail = employeeEmail;
        this.employeeContact = employeeContact;
        this.projectMemberCreatedDate = projectMemberCreatedDate;
    }
}
