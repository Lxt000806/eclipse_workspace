package com.house.home.web.controller.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.SetItemQuota;
import com.house.home.service.basic.SetItemQuotaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/setItemQuota")
public class SetItemQuotaController extends BaseController{
	@Autowired
	private  SetItemQuotaService setItemQuotaService;

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("admin/basic/setItemQuota/setItemQuota_list");
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, SetItemQuota setItemQuota) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		setItemQuotaService.findPageBySql(page, setItemQuota);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,SetItemQuota setItemQuota) throws Exception {
		Page<Map<String, Object>> page;
		page = this.newPageForJqGrid(request);
		try {
			setItemQuotaService.findDetailByNo(page, setItemQuota);
			return new WebPage<Map<String,Object>>(page);
		} catch (Exception e) {
			//当发生异常时，传一个空的List到result中
			e.printStackTrace();
			List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
			page.setResult(arrayList);
			return new WebPage<Map<String,Object>>(page);
		}
		
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,SetItemQuota stq){
		logger.debug("跳转到新增页面");	
		SetItemQuota setItemQuota=null;
		if (StringUtils.isNotBlank(stq.getNo())){
			setItemQuota=this.setItemQuotaService.get(SetItemQuota.class,stq.getNo());
			setItemQuota.setM_umState(stq.getM_umState());
			setItemQuota.setCustType(setItemQuota.getCustType().trim());
		}else{
			setItemQuota = new SetItemQuota();
			setItemQuota.setM_umState("A");
		}
        return new ModelAndView("admin/basic/setItemQuota/setItemQuota_save")
			.addObject("setItemQuota", setItemQuota);
	}
	
	@RequestMapping("/goSetItemQuotaDetail")
	public ModelAndView goDetailAdd(HttpServletRequest request, HttpServletResponse response,SetItemQuota setItemQuota){
        return new ModelAndView("admin/basic/setItemQuota/setItemQuota_setItemQuotaDetail").addObject("setItemQuota", setItemQuota);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,SetItemQuota setItemQuota){
		logger.debug("添加SetItemQuota开始");
		try{
			SetItemQuota stq = this.setItemQuotaService.get(SetItemQuota.class,setItemQuota.getNo());
			if(stq==null){
				setItemQuota.setActionLog("ADD");
				setItemQuota.setM_umState("A");
			}else{
				setItemQuota.setActionLog("EDIT");
				setItemQuota.setM_umState("M");
			}
			setItemQuota.setLastUpdate(new Date());
			setItemQuota.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.setItemQuotaService.doSave(setItemQuota);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加工程退款信息失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SetItemQuota setItemQuota){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		setItemQuotaService.findPageBySql(page, setItemQuota);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"套餐材料定额表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request ,HttpServletResponse response,
			SetItemQuota setItemQuota){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		setItemQuotaService.findDetailByNo(page, setItemQuota);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"套餐材料定额明细表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	
}
