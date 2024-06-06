package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ViewAvailableMembersResponseDTO {

    private String projectMemberEmployeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeContact;
    private LocalDateTime employeeCreatedDate;
}
