package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;
import com.house.home.entity.basic.PayRule;
import com.house.home.entity.basic.UpgWithhold;
import com.house.home.entity.finance.ReturnPay;
import com.house.home.service.basic.UpgWithholdService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/upgWithhold")
public class UpgWithholdController extends BaseController{
	@Autowired
	private  UpgWithholdService upgWithholdService;

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, UpgWithhold upgWithhold) throws Exception {
		return new ModelAndView("admin/basic/upgWithhold/upgWithhold_list")
		.addObject("upgWithhold",upgWithhold);
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,UpgWithhold upgWithhold) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		upgWithholdService.findPageBySql(page, upgWithhold);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridDetail(HttpServletRequest request,
			HttpServletResponse response,UpgWithhold upgWithhold) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		upgWithholdService.findDetailByCode(page, upgWithhold);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,UpgWithhold uw){
		logger.debug("跳转到upgWithhold_save页面");
		UpgWithhold upgWithhold = null;
		if("A".equals(uw.getM_umState())){
			upgWithhold = new UpgWithhold();
			upgWithhold.setExpired("F");
			upgWithhold.setAreaTo(1000000.0);
			upgWithhold.setArea(0);
		}else{
			upgWithhold=upgWithholdService.getByCode(uw.getCode());
		}

		upgWithhold.setM_umState(uw.getM_umState());
		
		return new ModelAndView("admin/basic/upgWithhold/upgWithhold_save")
			.addObject("upgWithhold", upgWithhold);
	}
	
	@RequestMapping("/goUpgWithholdDetail")
	public ModelAndView goadd(HttpServletRequest request, HttpServletResponse response,UpgWithhold upgWithhold,String str){
		logger.debug("跳转到新增材料套餐包明细页面");	
		upgWithhold.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView("admin/basic/upgWithhold/upgWithhold_upgWithholdDetail")
			.addObject("upgWithhold", upgWithhold).addObject("itemCodeArray",str);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,UpgWithhold upgWithhold){
		logger.debug("添加ReturnPay开始");
		try{
			UpgWithhold uw = upgWithholdService.getByCode(upgWithhold.getCode());
			if (uw!=null && ("A".equals(upgWithhold.getM_umState()) || "C".equals(upgWithhold.getM_umState())) ){
				ServletUtils.outPrintFail(request, response, "升级扣项规则编号重复");
				return;
			};
//			if(uw==null){
//				upgWithhold.setActionLog("ADD");
//				upgWithhold.setM_umState("A");
//			}else{
//				upgWithhold.setActionLog("EDIT");
//				upgWithhold.setM_umState("M");
//			}
			upgWithhold.setLastUpdate(new Date());
			upgWithhold.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.upgWithholdService.doSave(upgWithhold);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加升级扣项规则信息失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			UpgWithhold upgWithhold){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		upgWithholdService.findPageBySql(page, upgWithhold);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"升级扣项规则表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
