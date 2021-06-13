package com.house.home.web.controller.project;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.CustType;
import com.house.home.entity.project.CustCheck;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.project.CheckSalaryConfirmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/checkSalaryConfirm")
public class CheckSalaryConfirmController extends BaseController{
	@Autowired
	private  CheckSalaryConfirmService checkSalaryConfirmService;
	@Autowired
	private  CustTypeService custTypeService;
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,CustCheck custCheck) {
		CustType custType = new CustType();
		custType.setIsPartDecorate("0,1");
		String needCode = custTypeService.getNeedCode(custType);
		custCheck.setCustType(needCode);	
		return new ModelAndView("admin/project/checkSalaryConfirm/checkSalaryConfirm_list")
			.addObject("custCheck", custCheck);
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustCheck custCheck) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		checkSalaryConfirmService.findPageBySql(page, custCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSalaryDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSalaryDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustCheck custCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		checkSalaryConfirmService.findPageSalaryDetailBySql(page, custCheck);
		return new WebPage<Map<String,Object>>(page);	
	}
	
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request, HttpServletResponse response,CustCheck custCheck){
		logger.debug("跳转到确认页面");	
        return new ModelAndView("admin/project/checkSalaryConfirm/checkSalaryConfirm_confirm")
			.addObject("custCheck", custCheck);
	}
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,CustCheck custCheck){
		logger.debug("跳转到确认页面");	
        return new ModelAndView("admin/project/checkSalaryConfirm/checkSalaryConfirm_confirm")
			.addObject("custCheck", custCheck);
	}
	
	@RequestMapping("/doConfirm")
	public void doSave(HttpServletRequest request, HttpServletResponse response,CustCheck custCheck){
		logger.debug("工资确认开始");
		try {
			CustCheck custCheckConfirm=checkSalaryConfirmService.get(CustCheck.class,custCheck.getCustCode());
			if(custCheckConfirm!=null){
				if("1".equals(custCheckConfirm.getIsSalaryConfirm())){
					ServletUtils.outPrintFail(request, response,"该客户工资发放已确认，无须重复确认");
				}
				custCheckConfirm.setSalaryConfirmDate(new Date());
				custCheckConfirm.setSalaryConfirmCZY(this.getUserContext(request).getCzybh());
				custCheckConfirm.setIsSalaryConfirm("1");	
				checkSalaryConfirmService.update(custCheckConfirm);
				ServletUtils.outPrintSuccess(request, response,"确认成功");
			}else{
				ServletUtils.outPrintFail(request, response,"确认操作失败，请检查该客户是否存在");
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "确认操作失败");
		}	
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CustCheck custCheck){		
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		checkSalaryConfirmService.findPageBySql(page, custCheck);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工资发放已确认_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
