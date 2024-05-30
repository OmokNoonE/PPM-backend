package org.omoknoone.ppm.projectDashboard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/")
	public String test() {
		return "Hello World";
	}
	@GetMapping("/favicon.ico")
    public void favicon() {
        // Return no content or a favicon.ico image
    }
}
