package org.omoknoone.ppm.domain.notification.service.strategy;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;

public interface NotificationStrategy {

    void send(Employee employee, Notification notification);
}
