package org.omoknoone.ppm.domain.stakeholders.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleAndScheduleIdList {
    Long roleName;
    List<Long> scheduleIdList;

    @Builder
    public RoleAndScheduleIdList(Long roleName, List<Long> scheduleIdList) {
        this.roleName = roleName;
        this.scheduleIdList = scheduleIdList;
    }
}
