package org.omoknoone.ppm.domain.notification.repository;

import org.omoknoone.ppm.domain.notification.aggregate.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n " +
            "FROM Notification n " +
            "WHERE n.employeeId = :employeeId " +
            "ORDER BY n.notificationCreatedDate DESC")
    List<Notification> findTop10ByEmployeeIdOrderByNotificationCreatedDateDesc(@Param("employeeId") String employeeId);
}