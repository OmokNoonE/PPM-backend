package org.omoknoone.ppm.domain.projectDashboard.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GraphScheduler {

	private final GraphService graphService;

	/* 현재 상태가 착수인 프로젝트들의 그래프 데이터를 업데이트함 */
	@Scheduled(cron = "0 0 12 * * ?")			// 매일 오후 12시에 실행
	public void scheduleGraphUpdate() {

		graphService.updateGaugeAll();
		graphService.updateColumnAll();
		graphService.updateLineAll();
		graphService.updatePieAll();

	}
}
