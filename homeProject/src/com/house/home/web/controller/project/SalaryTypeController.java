package com.house.home.web.controller.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.SalaryType;
import com.house.home.service.project.SalaryTypeService;

@Controller
@RequestMapping("/admin/salaryType")
public class SalaryTypeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SalaryTypeController.class);

	@Autowired
	private SalaryTypeService salaryTypeService;

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
			HttpServletResponse response, SalaryType salaryType) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		salaryTypeService.findPageBySql(page, salaryType);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * SalaryType列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/project/salaryType/salaryType_list");
	}
	/**
	 * SalaryType查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/salaryType/salaryType_code");
	}
	/**
	 * 跳转到新增SalaryType页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SalaryType页面");
		SalaryType salaryType = null;
		if (StringUtils.isNotBlank(id)){
			salaryType = salaryTypeService.get(SalaryType.class, id);
			salaryType.setCode(null);
		}else{
			salaryType = new SalaryType();
		}
		
		return new ModelAndView("admin/project/salaryType/salaryType_save")
			.addObject("salaryType", salaryType);
	}
	/**
	 * 跳转到修改SalaryType页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			SalaryType st){
		logger.debug("跳转到复制SalaryType页面");
		JSONObject jsonObject = null;
		Map<String,Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(st.getCode())){
			map = salaryTypeService.findByCode(st.getCode());
			map.put("m_umState", st.getM_umState());
		}else{
			map.put("m_umState", st.getM_umState());
		}
		jsonObject = JSONObject.fromObject(map);
		return new ModelAndView("admin/project/salaryType/salaryType_update")
			.addObject("salaryType", jsonObject);
	}
	
	/**
	 * 跳转到查看SalaryType页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SalaryType页面");
		SalaryType salaryType = salaryTypeService.get(SalaryType.class, id);
		
		return new ModelAndView("admin/project/salaryType/salaryType_detail")
				.addObject("salaryType", salaryType);
	}
	/**
	 * 添加SalaryType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SalaryType salaryType){
		logger.debug("添加SalaryType开始");
		try{
			Map<String,Object> map = salaryTypeService.findByCode(salaryType.getCode());
			if(map!=null){
				ServletUtils.outPrintFail(request, response, "工资类型编号不能重复");
				return;
			}
			salaryType.setLastUpdate(new Date());
			salaryType.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryType.setExpired("F");
			salaryType.setActionLog("ADD");
			this.salaryTypeService.save(salaryType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SalaryType失败");
		}
	}
	
	/**
	 * 修改SalaryType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryType salaryType){
		logger.debug("修改SalaryType开始");
		try{
			salaryType.setLastUpdate(new Date());
			salaryType.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryType.setActionLog("EDIT");
			this.salaryTypeService.update(salaryType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SalaryType失败");
		}
	}
	
	/**
	 * 删除SalaryType
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SalaryType开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SalaryType编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SalaryType salaryType = salaryTypeService.get(SalaryType.class, deleteId);
				if(salaryType == null)
					continue;
				salaryType.setExpired("T");
				salaryTypeService.update(salaryType);
			}
		}
		logger.debug("删除SalaryType IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SalaryType导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SalaryType salaryType){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		salaryTypeService.findPageBySql(page, salaryType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"工资类型表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
