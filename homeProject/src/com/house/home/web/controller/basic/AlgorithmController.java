package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Algorithm;
import com.house.home.entity.basic.PrePlanTempDetail;
import com.house.home.service.basic.AlgorithmService;

@Controller
@RequestMapping("/admin/algorithm")
public class AlgorithmController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AlgorithmController.class);

	@Autowired
	private AlgorithmService algorithmService;

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
			HttpServletResponse response, Algorithm algorithm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		algorithmService.findPageBySql(page, algorithm);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询DetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, Algorithm algorithm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		algorithmService.findDetailPageBySql(page, algorithm);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Algorithm列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/algorithm/algorithm_list");
	}
	/**
	 * Algorithm查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/algorithm/algorithm_code");
	}
	/**
	 * 跳转到新增Algorithm页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增Algorithm页面");
		Algorithm algorithm = null;
		if (StringUtils.isNotBlank(id)){
			algorithm = algorithmService.get(Algorithm.class, id);
			algorithm.setCode(null);
		}else{
			algorithm = new Algorithm();
		}
		
		return new ModelAndView("admin/basic/algorithm/algorithm_save")
			.addObject("algorithm", algorithm);
	}
	/**
	 * 跳转到修改Algorithm页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id,String m_umState){
		logger.debug("跳转到修改Algorithm页面");
		Algorithm algorithm = null;
		if (StringUtils.isNotBlank(id)){
			algorithm = algorithmService.get(Algorithm.class, id);
		}else{
			algorithm = new Algorithm();
		}
		algorithm.setM_umState(m_umState);
		return new ModelAndView("admin/basic/algorithm/algorithm_update")
			.addObject("algorithm", algorithm);
	}
	
	/**
	 * 跳转到明细新增页面
	 * @return
	 */
	@RequestMapping("/goDetailAdd")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Algorithm algorithm){
		logger.debug("跳转到明细新增页面");
		return new ModelAndView("admin/basic/algorithm/algorithm_detailAdd")
				.addObject("algorithm", algorithm);
	}
	/**
	 * 添加Algorithm
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Algorithm algorithm){
		logger.debug("添加Algorithm开始");
		try {
			algorithm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.algorithmService.doSave(algorithm);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 修改Algorithm
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Algorithm algorithm){
		logger.debug("修改Algorithm开始");
		try{
			algorithm.setLastUpdate(new Date());
			algorithm.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.algorithmService.update(algorithm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Algorithm失败");
		}
	}
	
	/**
	 * 删除Algorithm
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Algorithm开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Algorithm编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Algorithm algorithm = algorithmService.get(Algorithm.class, deleteId);
				if(algorithm == null)
					continue;
				algorithm.setExpired("T");
				algorithmService.update(algorithm);
			}
		}
		logger.debug("删除Algorithm IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Algorithm导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Algorithm algorithm){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		algorithmService.findPageBySql(page, algorithm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"快速预报价算法管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 检查code是否存在
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public JSONObject checkCode(HttpServletRequest request,HttpServletResponse response,String code){
		if(StringUtils.isEmpty(code)){
			return this.out("传入的code为空", false);
		}
		Algorithm aitb = this.algorithmService.get(Algorithm.class, code);

		if(aitb != null){
			return this.out("系统中已存在code="+code+"的信息", false);
		}
		return this.out(aitb, true);
	}
	/**
	 * 检查Descr是否存在
	 * @param request
	 * @param response
	 * @param algorithm
	 * @param oldDescr
	 * @return
	 */
	@RequestMapping("/checkDescr")
	@ResponseBody
	public JSONObject checkDescr(HttpServletRequest request,HttpServletResponse response,
			Algorithm algorithm, String oldDescr){
		if(StringUtils.isEmpty(algorithm.getDescr())){
			return this.out("传入的descr为空", false);
		} else if (algorithm.getDescr().equals(oldDescr)) {
			return this.out("存在？", true);
		}
		boolean ba = algorithmService.hasDescr(algorithm);

		if(ba){
			return this.out("系统中已存在descr="+algorithm.getDescr()+"的信息", false);
		}
		return this.out("存在？", true);
	}
	/**
	 * 是否切割
	 * 
	 * @param request
	 * @param response
	 * @param prePlanTempDetail
	 */
	@RequestMapping("/checkIsCalCutFee")
	@ResponseBody
	public List<Map<String, Object>> checkIsCalCutFee(HttpServletRequest request,
			HttpServletResponse response, Algorithm algorithm) {
		List<Map<String, Object>>list=algorithmService.checkIsCalCutFee(algorithm);
		return list;
	}
}
