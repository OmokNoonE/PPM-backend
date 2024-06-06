package org.omoknoone.ppm.domain.task.repository;

import org.omoknoone.ppm.domain.task.aggregate.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTasksByTaskScheduleIdAndTaskIsDeletedFalse(Long scheduleId);
}
