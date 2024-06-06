package org.omoknoone.ppm.domain.notification.service.strategy;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;

public interface NotificationStrategy {

    NotificationSentStatus send(Employee employee, String title, String content, NotificationType type);
}
