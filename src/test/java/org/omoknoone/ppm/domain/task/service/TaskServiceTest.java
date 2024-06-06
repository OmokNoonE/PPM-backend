package org.omoknoone.ppm.domain.task.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.task.aggregate.Task;
import org.omoknoone.ppm.domain.task.dto.CreateTaskDTO;
import org.omoknoone.ppm.domain.task.dto.ModifyTaskDTO;
import org.omoknoone.ppm.domain.task.dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TaskServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceTest.class);

	@Autowired
	private TaskService taskService;

	@Test
	@DisplayName("업무 생성 테스트")
	public void testCreateTask() {
		// Given
		CreateTaskDTO createTaskDTO = new CreateTaskDTO();
		createTaskDTO.setTaskTitle("Test Task");
		createTaskDTO.setTaskScheduleId(1L);

		// When
		Task result = taskService.createTask(createTaskDTO);

		// Then
		assertNotNull(result);
		assertEquals(createTaskDTO.getTaskTitle(), result.getTaskTitle());
	}

	@BeforeEach
	public void setUp() {
		CreateTaskDTO createTaskDTO = new CreateTaskDTO();
		createTaskDTO.setTaskTitle("Test Task");
		createTaskDTO.setTaskScheduleId(1L);
		taskService.createTask(createTaskDTO);
	}

	@Test
	@DisplayName("업무 조회 테스트")
	public void testViewTask() {
		// Given
		Long taskId = 5L;

		// When
		TaskDTO result = taskService.viewTask(taskId);

		// Then
		assertNotNull(result);
		logger.info("Task ID: " + result.getTaskId());
	}

	@Test
	@DisplayName("스케줄에 속한 업무 조회 테스트")
	public void testViewScheduleTask() {
		// Given
		Long scheduleId = 1L;

		// When
		List<TaskDTO> result = taskService.viewScheduleTask(scheduleId);

		// Then
		assertNotNull(result);
		assertFalse(result.isEmpty());
		logger.info("Task ID: " + result.get(0).getTaskId());
	}

	@Test
	@DisplayName("업무 수정 테스트")
	public void testModifyTask() {
		// Given
		ModifyTaskDTO modifyTaskDTO = new ModifyTaskDTO();
		modifyTaskDTO.setTaskId(13L);
		modifyTaskDTO.setTaskTitle("Modified Task");

		// When
		Long result = taskService.modifyTask(modifyTaskDTO);

		// Then
		assertEquals(modifyTaskDTO.getTaskId(), result);
		logger.info("Task ID: " + result);
	}

	@Test
	@DisplayName("업무 삭제 테스트")
	public void testRemoveTask() {
		// Given
		Long taskId = 14L;

		// When
		Long result = taskService.removeTask(taskId);

		// Then
		assertEquals(taskId, result);

	}
}