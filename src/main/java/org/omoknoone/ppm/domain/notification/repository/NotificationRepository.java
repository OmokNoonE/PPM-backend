package org.omoknoone.ppm.domain.notification.repository;

import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n " +
        "FROM Notification n " +
        "WHERE n.employeeId = :employeeId " +
        "AND n.notificationIsDeleted = false " +
        "ORDER BY n.notificationCreatedDate DESC")
    Page<Notification> findTop10ByEmployeeIdOrderByNotificationCreatedDateDesc(@Param("employeeId") String employeeId, Pageable pageable);
}