package org.omoknoone.ppm.domain.holiday.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

public interface HolidayService {

    String fetchHolidayData() throws IOException;
    String fetchHolidayDataByYear(String receivedYear) throws IOException;
    void extractHolidayData(String responseData);
}
