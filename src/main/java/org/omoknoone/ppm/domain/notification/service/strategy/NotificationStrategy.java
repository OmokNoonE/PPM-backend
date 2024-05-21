package org.omoknoone.ppm.domain.notification.service.strategy;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;

public interface NotificationStrategy {

    void send(Employee employee, String title, String content, NotificationType type);
}
