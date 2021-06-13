package com.house.home.web.controller.commi;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.commi.PersonalCommiQueryService;
import com.house.home.service.commi.CommiCycleService;

@Controller
@RequestMapping("admin/personalCommiQuery")
public class PersonalCommiQueryController extends BaseController{

	@Autowired
	private PersonalCommiQueryService personalCommiQueryService;
	
	@Autowired
	private CommiCycleService commiCycleService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public List<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response,Employee employee) throws Exception{
		
		employee.setNumber(this.getUserContext(request).getCzybh());
		List<Map<String, Object>> commiDatas = personalCommiQueryService.getMainPageData(employee);
		if (commiDatas == null || commiDatas.size()>1) {
			return null;
		}
		return commiDatas;
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,Employee employee){
		
		String mon = commiCycleService.getMaxMon();
		
		return new ModelAndView("admin/commi/personalCommiQuery/personalCommiQuery_list").addObject("mon", mon);
	}
	
	
}
