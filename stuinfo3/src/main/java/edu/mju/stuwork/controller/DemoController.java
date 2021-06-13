/**
 * 
 */
package edu.mju.stuwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author joeyang ong
 *
 */
@Controller
public class DemoController {
	
	@GetMapping("/test")
	public String test() throws Exception{
		System.out.println("test now!");
		return null;
	}
	
	@GetMapping("/work")
	@ResponseBody
	public String work() throws Exception{
		return "hello world!";		
	}

}
