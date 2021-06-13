package com.house.framework.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.house.framework.commons.utils.ServletUtils;

/**
 * 
 * @ClassName: FormTokenController 
 * @Description: token控制器
 *
 */
@Controller
@RequestMapping("/admin/formToken")
public class FormTokenController extends BaseController {

	@RequestMapping("/newToken")
	public void goMain(HttpServletRequest request, HttpServletResponse response) {
		ServletUtils.outPrintFail(request, response, "产生新token");
	}
	
}
