package com.advait.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/greet")
	public  String greet() {
		return "Welcome to Authentication Service!";
	}

}
