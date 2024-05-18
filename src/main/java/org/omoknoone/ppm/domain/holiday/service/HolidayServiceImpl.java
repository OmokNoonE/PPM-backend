package org.omoknoone.ppm.domain.holiday.service;

import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;
import org.omoknoone.ppm.domain.holiday.dto.CreateHolidayDTO;
import org.omoknoone.ppm.domain.holiday.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Value("${holiday.api.key}")
    private String apiKey;

    @Autowired
    public HolidayServiceImpl(HolidayRepository holidayRepository, RestTemplate restTemplate, ModelMapper modelMapper) {
        this.holidayRepository = holidayRepository;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    // 매월 1일 자정에 공휴일 데이터를 가져와서 DB에 저장하는 메소드
    @Override
    public String fetchHolidayData() throws IOException {
        String url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        String monthString = (month < 10) ? "0" + month : String.valueOf(month);

        String fullUrl = String.format("%s?serviceKey=%s&solYear=%d&solMonth=%s", url, apiKey, year, monthString);

        // UTF-8 문자열로 변환하기 위한 설정
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(fullUrl, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseData = responseEntity.getBody();

            // 공휴일 데이터 추출 및 저장
            extractHolidayData(responseData);
        } else {
            throw new IOException("Failed to fetch holiday data. Response code: " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }

    @Override
    public String fetchHolidayDataByYear(String year) throws IOException {
        String url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
        for (int month = 1; month < 13; month++) {
            String monthString = (month < 10) ? "0" + month : String.valueOf(month);
            System.out.println("monthString = " + monthString + "");

            String fullUrl = String.format("%s?serviceKey=%s&solYear=%s&solMonth=%s", url, apiKey, year, monthString);

            // UTF-8 문자열로 변환하기 위한 설정
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(fullUrl, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseData = responseEntity.getBody();
                System.out.println("responseData = " + responseData);
                
                // 공휴일 데이터 추출 및 저장
                extractHolidayData(responseData);
            } else {
                throw new IOException("Failed to fetch holiday data. Response code: " + responseEntity.getStatusCode());
            }
        }

        return "good";
    }

    // API 응답에서 공휴일 데이터를 추출하여 DB에 저장하는 메소드
    @Override
    @Transactional
    public void extractHolidayData(String responseData) {
        Pattern pattern = Pattern.compile("\"dateName\":\"(.*?)\".*?\"locdate\":(\\d+)");
        Matcher matcher = pattern.matcher(responseData);

        while (matcher.find()) {
            System.out.println("들어옴");
            String holidayName = matcher.group(1);
            String locdate = matcher.group(2);

            int apiYear = Integer.parseInt(locdate.substring(0, 4));
            int apiMonth = Integer.parseInt(locdate.substring(4, 6));
            int apiDay = Integer.parseInt(locdate.substring(6));

            LocalDate localDate = LocalDate.of(apiYear, apiMonth, apiDay);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            long weekday = dayOfWeek.getValue() + 10100;

            // DTO를 통해 공휴일 정보를 생성하고, 엔티티로 변환하여 DB에 저장
            CreateHolidayDTO createHoliday = new CreateHolidayDTO();
            createHoliday.setHolidayName(holidayName);
            createHoliday.setHolidayYear(apiYear);
            createHoliday.setHolidayMonth(apiMonth);
            createHoliday.setHolidayDay(apiDay);
            createHoliday.setHolidayWeekday(weekday);
            createHoliday.newHolidayDefaultSet();

            Holiday holiday = modelMapper.map(createHoliday, Holiday.class);

            holidayRepository.save(holiday);
//            System.out.println("Holiday (" + holiday.getHolidayId() + ") data fetched successfully and saved to the database!");
        }
    }
}
