package org.omoknoone.ppm.domain.notification.repository;

import java.util.List;

import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n " +
        "FROM Notification n " +
        "WHERE n.employeeId = :employeeId " +
        "AND n.notificationIsDeleted = false " +
        "ORDER BY n.notificationCreatedDate DESC")
    List<Notification> findTop10ByEmployeeIdOrderByNotificationCreatedDateDesc(@Param("employeeId") String employeeId);

}