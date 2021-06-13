package com.house.home.web.controller.basic;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.basic.DocDownloadLogService;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/docDownloadLog")
public class DocDownloadLogController extends BaseController{
	@Autowired
	private  DocDownloadLogService docDownloadLogService;

}
