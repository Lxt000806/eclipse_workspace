package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.service.basic.PrjItem1Service;

@Controller
@RequestMapping("/admin/prjItem1")
public class PrjItem1Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjItem1Controller.class);

	@Autowired
	private PrjItem1Service prjItem1Service;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjItem1 prjItem1) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjItem1Service.findPageBySql(page, prjItem1);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PrjItem1列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/prjItem1/prjItem1_list");
	}
	/**
	 * PrjItem1查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/prjItem1/prjItem1_code");
	}
	/**
	 * 跳转到新增PrjItem1页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PrjItem1页面");
		PrjItem1 prjItem1 = null;
		if (StringUtils.isNotBlank(id)){
			prjItem1 = prjItem1Service.get(PrjItem1.class, id);
			prjItem1.setCode(null);
		}else{
			prjItem1 = new PrjItem1();
		}
		
		return new ModelAndView("admin/basic/prjItem1/prjItem1_save")
			.addObject("prjItem1", prjItem1);
	}
	/**
	 * 跳转到修改PrjItem1页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PrjItem1页面");
		PrjItem1 prjItem1 = null;
		if (StringUtils.isNotBlank(id)){
			prjItem1 = prjItem1Service.get(PrjItem1.class, id);
		}else{
			prjItem1 = new PrjItem1();
		}
		prjItem1.setM_umState("M");
		return new ModelAndView("admin/basic/prjItem1/prjItem1_save")
			.addObject("prjItem1", prjItem1);
	}
	
	/**
	 * 跳转到查看PrjItem1页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PrjItem1页面");
		PrjItem1 prjItem1 = prjItem1Service.get(PrjItem1.class, id);
		prjItem1.setM_umState("V");
		return new ModelAndView("admin/basic/prjItem1/prjItem1_save")
				.addObject("prjItem1", prjItem1);
	}
	/**
	 * 添加PrjItem1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrjItem1 prjItem1){
		logger.debug("添加PrjItem1开始");
		try{
			if(prjItem1.getPrjphotoNum()==null){
				prjItem1.setPrjphotoNum(0);
			}
			if(prjItem1.getSignTimes()==null){
				prjItem1.setSignTimes(0);
			}
			prjItem1.setLastUpdate(new Date());
			prjItem1.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjItem1.setExpired("F");
			prjItem1.setActionLog("ADD");
			this.prjItem1Service.save(prjItem1);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PrjItem1失败");
		}
	}
	
	/**
	 * 修改PrjItem1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjItem1 prjItem1){
		logger.debug("修改PrjItem1开始");
		try{
			if(prjItem1.getPrjphotoNum()==null){
				prjItem1.setPrjphotoNum(0);
			}
			if(prjItem1.getSignTimes()==null){
				prjItem1.setSignTimes(0);
			}
			prjItem1.setActionLog("EDIT");
			prjItem1.setLastUpdate(new Date());
			prjItem1.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prjItem1Service.update(prjItem1);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PrjItem1失败");
		}
	}
	
	/**
	 * 删除PrjItem1
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PrjItem1开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PrjItem1编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrjItem1 prjItem1 = prjItem1Service.get(PrjItem1.class, deleteId);
				if(prjItem1 == null)
					continue;
				prjItem1Service.delete(prjItem1);
			}
		}
		logger.debug("删除PrjItem1 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PrjItem1导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PrjItem1 prjItem1){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjItem1Service.findPageBySql(page, prjItem1);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"施工项目1_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/checkCode")
	@ResponseBody
	public JSONObject checkCode(HttpServletRequest request,HttpServletResponse response,String code, String oldCode){
		if (StringUtils.isNotBlank(oldCode) && code.equals(oldCode)) {
			return this.out(null, true);
		}
		if(StringUtils.isEmpty(code)){
			return this.out("传入的code为空", false);
		}
		PrjItem1 prjItem1 = this.prjItem1Service.get(PrjItem1.class, code);
		if(prjItem1 != null){
			return this.out("系统中已存在code="+code+"的信息", false);
		}
		return this.out(prjItem1, true);
	}
}
