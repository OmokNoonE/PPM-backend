package org.omoknoone.ppm.domain.permission.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleAndSchedulesDTO {
    Long roleName;
    List<Long> scheduleIdList;

    @Builder
    public RoleAndSchedulesDTO(Long roleName, List<Long> scheduleIdList) {
        this.roleName = roleName;
        this.scheduleIdList = scheduleIdList;
    }
}
