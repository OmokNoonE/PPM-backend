package org.omoknoone.ppm.domain.notification.service.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackNotificationStrategy implements NotificationStrategy {

    @Value("${slack.token}")
    private String slackToken;

    @Transactional
    @Override
    public NotificationSentStatus send(Employee employee, String title, String content, NotificationType type) {
        try {
            String slackId = getSlackIdByEmail(employee.getEmployeeEmail());
            if (slackId != null) {
                sendMessageToSlack(slackId, title, content);
                return NotificationSentStatus.SUCCESS;
            } else {
                log.error("슬랙 ID를 찾을 수 없습니다. 이메일: {}", employee.getEmployeeEmail());
                return NotificationSentStatus.FAILURE;
            }
        } catch (Exception e) {
            log.error("슬랙 알림 전송 실패: " + e.getMessage());
            return NotificationSentStatus.FAILURE;
        }
    }

    private void sendMessageToSlack(String slackId, String title, String content) {
        String url = "https://slack.com/api/chat.postMessage";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/json; charset=utf-8");

        // 제목과 내용을 결합하여 메시지 구성
        String message = "*" + title + "*\n\n" + content;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel", slackId);
        jsonObject.put("text", message);
        String body = jsonObject.toString();

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        HttpStatus httpStatus = HttpStatus.resolve(responseEntity.getStatusCode().value());
        if (httpStatus != null && httpStatus.is2xxSuccessful()) {
            log.info("슬랙 메시지 전송 성공: {}", responseEntity.getBody());
        } else {
            log.error("슬랙 메시지 전송 실패: {}", responseEntity.getBody());
        }
    }

    private String getSlackIdByEmail(String email) {
        String url = "https://slack.com/api/users.lookupByEmail?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            if (jsonObject.getBoolean("ok")) {
                JSONObject user = jsonObject.getJSONObject("user");
                return user.getString("id");
            } else {
                log.error("슬랙 이메일 조회 실패: {}", jsonObject.getString("error"));
            }
        } else {
            log.error("슬랙 이메일 조회 HTTP 오류: {}", responseEntity.getStatusCode());
        }
        return null;
    }
}
