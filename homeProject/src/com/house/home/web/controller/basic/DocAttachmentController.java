package com.house.home.web.controller.basic;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.basic.DocAttachmentService;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/docAttachment")
public class DocAttachmentController extends BaseController{
	@Autowired
	private  DocAttachmentService docAttachmentService;

}
