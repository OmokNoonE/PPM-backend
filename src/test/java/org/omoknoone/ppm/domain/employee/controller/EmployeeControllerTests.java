package org.omoknoone.ppm.domain.employee.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTests {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll(); // Clear any existing data
    }

    @AfterEach
    public void tearDown() {
        employeeRepository.deleteAll(); // Clean up after each test
    }

    @Test
    public void Employee_조회된다() throws Exception {

        // given
        String employeeId = "a123";

        String url = "http://localhost:" + port + "/employees/view/" + employeeId;
        
        // when
        ResponseEntity<ResponseMessage> responseEntity = restTemplate.getForEntity(url, ResponseMessage.class);
        
        // then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        ResponseMessage responseMessage = responseEntity.getBody();
        assertNotNull(responseMessage);

    }
}