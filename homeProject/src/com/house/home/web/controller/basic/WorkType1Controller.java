package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.house.home.entity.basic.WorkType1;
import com.house.home.entity.basic.WorkType2;
import com.house.home.service.basic.WorkType1Service;

@Controller
@RequestMapping("/admin/workType1")
public class WorkType1Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkType1Controller.class);

	@Autowired
	private WorkType1Service workType1Service;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, WorkType1 workType1) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workType1Service.findPageBySql(page, workType1);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * WorkType1列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/workType1/workType1_list");
	}
	/**
	 * WorkType1查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/workType1/workType1_code");
	}
	/**
	 * 跳转到新增WorkType1页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			WorkType1 workType1){
		logger.debug("跳转到新增WorkType1页面");
		return new ModelAndView("admin/basic/workType1/workType1_save")
			.addObject("workType1", workType1);
	}
	/**
	 * 跳转到修改WorkType1页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改WorkType1页面");
		WorkType1 workType1 = null;
		if (StringUtils.isNotBlank(id)){
			workType1 = workType1Service.get(WorkType1.class, id);
		}else{
			workType1 = new WorkType1();
		}
		
		return new ModelAndView("admin/basic/workType1/workType1_save")
			.addObject("workType1", workType1);
	}
	
	/**
	 * 跳转到查看WorkType1页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看WorkType1页面");
		WorkType1 workType1 = workType1Service.get(WorkType1.class, id);
		
		return new ModelAndView("admin/basic/workType1/workType1_detail")
				.addObject("workType1", workType1);
	}
	/**
	 * 添加WorkType1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WorkType1 wt1){
		logger.debug("添加WorkType1开始");
		try{
		 	WorkType1 workType1=workType1Service.get(WorkType1.class, wt1.getCode());
		 	if(("A".equals(wt1.getM_umState()) || "C".equals(wt1.getM_umState())) && workType1!=null){
		 		ServletUtils.outPrintFail(request, response,"工种编号重复");
		 		return;
		 	}
		 	if(workType1==null){
		 		wt1.setLastUpdate(new Date());
				wt1.setLastUpdatedBy(getUserContext(request).getCzybh());
				wt1.setExpired("F");
				wt1.setActionLog("ADD");
		 		this.workType1Service.save(wt1);
		 	}else{
		 		workType1.setCode(wt1.getCode());
		 		workType1.setDescr(wt1.getDescr());
		 		workType1.setDispSeq(wt1.getDispSeq());
		 		workType1.setItemAppCtrl(wt1.getItemAppCtrl());
				workType1.setLastUpdate(new Date());
				workType1.setLastUpdatedBy(getUserContext(request).getCzybh());
				workType1.setExpired(wt1.getExpired());
				workType1.setActionLog("EDIT");
				this.workType1Service.update(workType1);
		 	}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加WorkType1失败");
		}
	}
	
	/**
	 * 修改WorkType1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, WorkType1 workType1){
		logger.debug("修改WorkType1开始");
		try{
			workType1.setLastUpdate(new Date());
			workType1.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.workType1Service.update(workType1);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改WorkType1失败");
		}
	}
	
	/**
	 * 删除WorkType1
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除WorkType1开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "WorkType1编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				WorkType1 workType1 = workType1Service.get(WorkType1.class, deleteId);
				if(workType1 == null)
					continue;
				workType1.setExpired("T");
				workType1Service.update(workType1);
			}
		}
		logger.debug("删除WorkType1 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *WorkType1导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WorkType1 workType1){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workType1Service.findPageBySql(page, workType1);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工种分类1表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 * 材料工种类型1.2联动
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workType/{type}/{pCode}") //
	@ResponseBody
	public JSONObject getItemType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.workType1Service.findWorkType(type, pCode);//type代表第几级来确定查询哪张表,pcode代表选中的那一列,确定后面的过滤条件
		return this.out(regionList, true);
	}
	
	
	/**
	 * add by cjm 2019-01-08
	 * 人工工种类型1.2联动
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workTypeForWorker/{type}/{pCode}") //
	@ResponseBody
	public JSONObject getWorkType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.workType1Service.findWorkTypeForWorker(type, pCode);//type代表第几级来确定查询哪张表,pcode代表选中的那一列,确定后面的过滤条件
		return this.out(regionList, true);
	}
	
	/**
	 * 材料工种类型1.2 所有的 
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workTypeAll/{type}/{pCode}") //
	@ResponseBody
	public JSONObject geWorkTypeAll(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.workType1Service.findWorkTypeAll(type, pCode);//type代表第几级来确定查询哪张表,pcode代表选中的那一列,确定后面的过滤条件
		return this.out(regionList, true);
	}
	/**
	 * 人工工种类型1.2联动
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workOfferWorkType/{type}/{pCode}")
	@ResponseBody
	public JSONObject getOfferWorkItemType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.workType1Service.findOfferWorkType(type, pCode);//type代表第几级来确定查询哪张表,pcode代表选中的那一列,确定后面的过滤条件
		return this.out(regionList, true);
	}
	
	/**
	 * @Description: TODO 动态校验工种分类1是否重复
	 * @author	created by cjm
	 * @date	2019-05-08
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public JSONObject checkCode(HttpServletRequest request,HttpServletResponse response,
			String code, String oldCode){
		if(StringUtils.isEmpty(code)){
			return this.out("传入的code为空", false);
		} else if (code.equals(oldCode)) {
			return this.out("code未修改", true);
		}
		WorkType1 wt1 = this.workType1Service.get(WorkType1.class, code);

		if(wt1 != null){
			return this.out("系统中已存在code="+code+"的信息", false);
		}
		return this.out("wt1", true);
	}
}
