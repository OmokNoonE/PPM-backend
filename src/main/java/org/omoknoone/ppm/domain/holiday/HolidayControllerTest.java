package org.omoknoone.ppm.domain.holiday;

import java.io.IOException;

import org.omoknoone.ppm.domain.holiday.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holiday")
public class HolidayControllerTest {

    private final HolidayService holidayService;

    @Autowired
    public HolidayControllerTest(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PutMapping("/test/{year}")
    public ResponseEntity<String> testCode(@PathVariable("year") String year) {

        try {
            String returnValue = holidayService.fetchHolidayData();
            return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* 몇 분 간격으로만 사용 가능 */
    @PutMapping("/year/{year}")
    public ResponseEntity<String> yearCode(@PathVariable("year") String year) {

        try {
            String response = holidayService.fetchHolidayDataByYear(year);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
