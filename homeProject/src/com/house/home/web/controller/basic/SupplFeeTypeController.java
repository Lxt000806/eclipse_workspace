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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.SupplFeeType;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.basic.SupplFeeTypeService;

@Controller
@RequestMapping("/admin/supplFeeType")
public class SupplFeeTypeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SupplFeeTypeController.class);

	@Autowired
	private SupplFeeTypeService supplFeeTypeService;

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
			HttpServletResponse response, SupplFeeType supplFeeType) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		Supplier supplier=supplFeeTypeService.get(Supplier.class, getUserContext(request).getEmnum());
		if(StringUtils.isNotBlank(supplFeeType.getIsComponent())){
			supplFeeType.setCodes(supplier.getSupplFeeType());
		}
		supplFeeTypeService.findPageBySql(page, supplFeeType);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SupplFeeType列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/supplFeeType/supplFeeType_list");
	}
	/**
	 * SupplFeeType查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/supplFeeType/supplFeeType_code");
	}
	/**
	 * 根据id查询详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSupplFeeType")
	@ResponseBody
	public JSONObject getPurchase(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的code为空", false);
		}
		SupplFeeType supplFeeType= supplFeeTypeService.get(SupplFeeType.class, id);
		if(supplFeeType == null){
			return this.out("系统中不存在Code="+id+"的供应商费用类型", false);
		}
		return this.out(supplFeeType, true);
	}
	/**
	 * 跳转到新增SupplFeeType页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, 
			String m_umState){
		logger.debug("跳转到新增SupplFeeType页面");
		SupplFeeType supplFeeType = new SupplFeeType();
		supplFeeType.setM_umState(m_umState);
		return new ModelAndView("admin/basic/supplFeeType/supplFeeType_win")
			.addObject("supplFeeType", supplFeeType);
	}
	/**
	 * 跳转到修改SupplFeeType页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String code, String m_umState){
		logger.debug("跳转到修改SupplFeeType页面");
		SupplFeeType supplFeeType = null;
		if (StringUtils.isNotBlank(code)){
			supplFeeType = supplFeeTypeService.get(SupplFeeType.class, code);
		}else{
			supplFeeType = new SupplFeeType();
		}
		supplFeeType.setM_umState(m_umState);
		return new ModelAndView("admin/basic/supplFeeType/supplFeeType_win")
			.addObject("supplFeeType", supplFeeType);
	}
	
	/**
	 * 跳转到查看SupplFeeType页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String code, String m_umState){
		logger.debug("跳转到查看SupplFeeType页面");
		SupplFeeType supplFeeType = supplFeeTypeService.get(SupplFeeType.class, code);
		supplFeeType.setM_umState(m_umState);
		return new ModelAndView("admin/basic/supplFeeType/supplFeeType_win")
				.addObject("supplFeeType", supplFeeType);
	}
	/**
	 * 添加SupplFeeType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SupplFeeType supplFeeType){
		logger.debug("添加SupplFeeType开始");
		try{
//			String str = supplFeeTypeService.getSeqNo("tSupplFeeType");
//			supplFeeType.setCode(str);
			SupplFeeType sft = this.supplFeeTypeService.get(SupplFeeType.class, supplFeeType.getCode());
			if (null != sft) {
				ServletUtils.outPrintFail(request, response, "编号重复");
				return;
			}
			supplFeeType.setLastUpdate(new Date());
			supplFeeType.setLastUpdatedBy(getUserContext(request).getCzybh());
			supplFeeType.setExpired("F");
			supplFeeType.setActionLog("ADD");
			this.supplFeeTypeService.save(supplFeeType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SupplFeeType失败");
		}
	}
	
	/**
	 * 修改SupplFeeType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SupplFeeType supplFeeType){
		logger.debug("修改SupplFeeType开始");
		try{
			supplFeeType.setLastUpdate(new Date());
			supplFeeType.setLastUpdatedBy(getUserContext(request).getCzybh());
			supplFeeType.setExpired("F");
			supplFeeType.setActionLog("Edit");
			this.supplFeeTypeService.update(supplFeeType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SupplFeeType失败");
		}
	}
	
	/**
	 * 删除SupplFeeType
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SupplFeeType开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SupplFeeType编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SupplFeeType supplFeeType = supplFeeTypeService.get(SupplFeeType.class, deleteId);
				if(supplFeeType == null)
					continue;
//				supplFeeType.setExpired("T");
				supplFeeTypeService.delete(supplFeeType);
			}
		}
		logger.debug("删除SupplFeeType IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SupplFeeType导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SupplFeeType supplFeeType){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		supplFeeTypeService.findPageBySql(page, supplFeeType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"供应商费用类型管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
