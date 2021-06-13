package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BaseAlgorithm;
import com.house.home.service.basic.BaseAlgorithmService;

@Controller
@RequestMapping("/admin/baseAlgorithm")
public class BaseAlgorithmController extends BaseController{
	@Autowired
	private  BaseAlgorithmService baseAlgorithmService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseAlgorithm baseAlgorithm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseAlgorithmService.findPageBySql(page, baseAlgorithm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	//TODO 根据基础项目算法编码查找算法适用类型
	@RequestMapping("/goPrjTypeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPrjTypeJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseAlgorithm baseAlgorithm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseAlgorithmService.findPrjTypePageBySql(page, baseAlgorithm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/baseAlgorithm/baseAlgorithm_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/baseAlgorithm/baseAlgorithm_save");
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, BaseAlgorithm baseAlgorithm) throws Exception {
		BaseAlgorithm ba = baseAlgorithmService.get(BaseAlgorithm.class, baseAlgorithm.getCode());
		ba.setM_umState(baseAlgorithm.getM_umState());
		return new ModelAndView("admin/basic/baseAlgorithm/baseAlgorithm_update")
			.addObject("baseAlgorithm", ba);
	}
	
	/**
	 * @Description: TODO 基础项目算法适用类型CRUD
	 * @author	created by zb
	 * @date	2018-8-28--下午3:53:47
	 * @param baseAlgorithm 传递参数
	 * @param keys 不显示的data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPrjTypeWin")
	public ModelAndView goPrjTypeWin(HttpServletRequest request,
			HttpServletResponse response, BaseAlgorithm baseAlgorithm, String keys) throws Exception {

		return new ModelAndView("admin/basic/baseAlgorithm/baseAlgorithm_win")
			.addObject("baseAlgorithm", baseAlgorithm)
			.addObject("keys", keys);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,BaseAlgorithm baseAlgorithm){
		logger.debug("新增保存");
		try {
			baseAlgorithm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			/*客户表是否有加入信息*/
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "施工类型表无明细");
				return;
			}
			/*执行存储过程*/
			Result result = this.baseAlgorithmService.doSave(baseAlgorithm);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增回访失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseAlgorithm baseAlgorithm){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseAlgorithmService.findPageBySql(page, baseAlgorithm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"基础项目算法管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	// TODO 检查code是否存在
	@RequestMapping("/checkCode")
	@ResponseBody
	public JSONObject checkCode(HttpServletRequest request,HttpServletResponse response,String code){
		if(StringUtils.isEmpty(code)){
			return this.out("传入的code为空", false);
		}
		BaseAlgorithm aitb = this.baseAlgorithmService.get(BaseAlgorithm.class, code);

		if(aitb != null){
			return this.out("系统中已存在code="+code+"的信息", false);
		}
		return this.out(aitb, true);
	}
	
	// TODO 检查Descr是否存在
	@RequestMapping("/checkDescr")
	@ResponseBody
	public JSONObject checkDescr(HttpServletRequest request,HttpServletResponse response,
			BaseAlgorithm baseAlgorithm, String oldDescr){
		if(StringUtils.isEmpty(baseAlgorithm.getDescr())){
			return this.out("传入的descr为空", false);
		} else if (baseAlgorithm.getDescr().equals(oldDescr)) {
			return this.out("存在？", true);
		}
		boolean ba = baseAlgorithmService.hasDescr(baseAlgorithm);

		if(ba){
			return this.out("系统中已存在descr="+baseAlgorithm.getDescr()+"的信息", false);
		}
		return this.out("存在？", true);
	}
	
}
