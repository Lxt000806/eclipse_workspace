package com.house.framework.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simpledemo")
public class TestUploadControl {
	
	@RequestMapping("/upload")
	public String upload() {
		return "simpledemo/index";
	}
}
