package org.omoknoone.ppm.domain.notification.repository;

import org.omoknoone.ppm.domain.notification.aggregate.entity.NotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {

    @Query("SELECT ns" +
            " FROM NotificationSetting ns" +
            " WHERE ns.employeeId = :employeeId")
    NotificationSetting findByEmployeeId(@Param("employeeId") String employeeId);
}