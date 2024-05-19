package org.omoknoone.ppm.domain.notification.service.strategy;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.Notification;
import org.omoknoone.ppm.domain.notification.aggregate.SendTemplate;

public interface NotificationStrategy {

    void send(Employee employee, Notification notification, SendTemplate sendTemplate);
}
