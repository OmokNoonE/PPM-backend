package org.omoknoone.ppm.domain.notification.repository;

import org.omoknoone.ppm.domain.notification.aggregate.entity.Sent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentRepository extends JpaRepository<Sent, Long> {

}