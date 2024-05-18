package org.omoknoone.ppm.domain.notification.repository;

import org.omoknoone.ppm.domain.notification.aggregate.SendTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendTemplateRepository extends JpaRepository<SendTemplate, Long> {

    SendTemplate findBySendTemplateType(String sendTemplateType);
}