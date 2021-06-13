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
import com.house.home.entity.basic.Activity;
import com.house.home.entity.basic.WorkType2;
import com.house.home.service.basic.WorkType2Service;

@Controller
@RequestMapping("/admin/workType2")
public class WorkType2Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkType2Controller.class);

	@Autowired
	private WorkType2Service workType2Service;

	/**
	 * @Description: TODO workType2_list分页查询
	 * @author	created by zb
	 * @date	2018-8-17--下午2:11:21
	 * @param request
	 * @param response
	 * @param workType2
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, WorkType2 workType2) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workType2Service.findPageBySql(page, workType2);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * WorkType2列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/workType2/workType2_list");
	}
	/**
	 * WorkType2查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/workType2/workType2_code");
	}
	

	@RequestMapping("/getWorkType2")
	@ResponseBody
	public JSONObject getWorkType2(HttpServletRequest request,HttpServletResponse response,String code){
		if(StringUtils.isEmpty(code)){
			return this.out("传入的Code为空", false);
		}
		WorkType2 workType2= this.workType2Service.get(WorkType2.class, code);
		if(workType2 == null){
			return this.out("系统中不存在Code="+code+"的工种分类2", false);
		}
		return this.out(workType2, true);
	}
	
	/**
	 * 跳转到新增WorkType2页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, WorkType2 workType2){
		if(StringUtils.isNotBlank(workType2.getDenyOfferWorkType2())){
			WorkType2 wt2 = this.workType2Service.get(WorkType2.class, workType2.getCode());
			if(wt2 != null){
				workType2.setDenyOfferWorkType2Descr(wt2.getDescr());
			}
		}
		return new ModelAndView("admin/basic/workType2/workType2_save")
			.addObject("workType2", workType2);
	}
	/**
	 * 跳转到修改WorkType2页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			WorkType2 workType2){
		logger.debug("跳转到修改WorkType2页面");
		return new ModelAndView("admin/basic/workType2/workType2_update")
			.addObject("workType2", workType2);
	}
	
	/**
	 * 跳转到查看WorkType2页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			WorkType2 workType2){
		logger.debug("跳转到查看WorkType2页面");
		return new ModelAndView("admin/basic/workType2/workType2_update")
				.addObject("workType2", workType2);
	}
	/**
	 * 添加WorkType2
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WorkType2 workType2){
		logger.debug("添加WorkType2开始");
		try{
			workType2.setLastUpdate(new Date());
			workType2.setLastUpdatedBy(getUserContext(request).getCzybh());
			workType2.setExpired("F");
			workType2.setActionLog("ADD");
			this.workType2Service.save(workType2);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加WorkType2失败");
		}
	}
	
	/**
	 * 修改WorkType2
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, WorkType2 workType2){
		logger.debug("修改WorkType2开始");
		try{
			workType2.setLastUpdate(new Date());
			workType2.setLastUpdatedBy(getUserContext(request).getCzybh());
			workType2.setActionLog("EDIT");
			this.workType2Service.update(workType2);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改WorkType2失败");
		}
	}
	
	/**
	 * 删除WorkType2
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除WorkType2开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "WorkType2编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				WorkType2 workType2 = workType2Service.get(WorkType2.class, deleteId);
				if(workType2 == null)
					continue;
				workType2.setExpired("T");
				workType2Service.update(workType2);
			}
		}
		logger.debug("删除WorkType2 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *WorkType2导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WorkType2 workType2){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workType2Service.findPageBySql(page, workType2);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"工种分类2_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 * @Description: TODO 动态校验工种分类2是否重复
	 * @author	created by zb
	 * @date	2018-8-17--下午4:21:34
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
		WorkType2 wt2 = this.workType2Service.get(WorkType2.class, code);

		if(wt2 != null){
			return this.out("系统中已存在code="+code+"的信息", false);
		}
		return this.out("wt2", true);
	}
}
