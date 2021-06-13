package com.house.framework.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestControl {
	
	private static final Logger L = LoggerFactory.getLogger(TestControl.class);
	
	@RequestMapping("/list")
	public String list() {
		L.info("测试log4j是否可用");
		return "test/test";
	}
	
}
