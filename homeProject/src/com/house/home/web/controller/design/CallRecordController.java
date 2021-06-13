package com.house.home.web.controller.design;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.web.controller.BaseController;

@Controller
@RequestMapping("/admin/callRecord")
public class CallRecordController extends BaseController {
	
	private static final Logger logger =LoggerFactory.getLogger(CallRecordController.class);

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request , HttpServletResponse response , String path) throws Exception{
		logger.debug("跳转到录音播放页面");
		return new ModelAndView("admin/design/callRecord/callRecord").addObject("path", path);
	}
	
}
