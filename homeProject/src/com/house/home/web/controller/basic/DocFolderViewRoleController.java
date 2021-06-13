package com.house.home.web.controller.basic;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.basic.DocFolderViewRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/docFolderViewRole")
public class DocFolderViewRoleController extends BaseController{
	@Autowired
	private  DocFolderViewRoleService docFolderViewRoleService;

}
