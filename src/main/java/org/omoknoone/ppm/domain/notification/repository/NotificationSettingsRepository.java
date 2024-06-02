package org.omoknoone.ppm.domain.notification.repository;

import org.omoknoone.ppm.domain.notification.aggregate.entity.NotificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Long> {

    @Query("SELECT ns" +
            " FROM NotificationSettings ns" +
            " WHERE ns.notificationSettingsEmployeeId = :notificationSettingsEmployeeId")
    NotificationSettings findByNotificationSettingsEmployeeId(@Param("notificationSettingsEmployeeId") String notificationSettingsEmployeeId);
}