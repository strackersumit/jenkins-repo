package com.db.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	@Value("${msg}")
	String msg;
	
	@GetMapping("/show") 
	public String showMsg()
	{
		return msg;
	}

}
