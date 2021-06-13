package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.dao.basic.CustPlanReportDao;
import com.house.home.entity.basic.CustPlanReport;
import com.house.home.entity.basic.CustType;
import com.house.home.service.basic.CustPlanReportService;
import com.house.home.service.basic.CustTypeService;

@Controller
@RequestMapping("/admin/custType")
public class CustTypeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustTypeController.class);

	@Autowired
	private CustTypeService custTypeService;
	@Resource(name = "custTypeCacheManager")
	private ICacheManager custTypeCacheManager;
	
	@Autowired
	private CustPlanReportService custPlanReportService;

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
			HttpServletResponse response, CustType custType) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTypeService.findPageBySql(page, custType);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustType列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custType/custType_list");
	}
	/**
	 * CustType查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custType/custType_code");
	}
	/**
	 * 跳转到新增CustType页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustType页面");
		CustType custType = null;
		if (StringUtils.isNotBlank(id)){
			custType = custTypeService.get(CustType.class, id);
			custType.setCode(null);
		}else{
			custType = new CustType();
		}
		
		return new ModelAndView("admin/basic/custType/custType_save")
			.addObject("custType", custType);
	}
	
	@RequestMapping("/goDefaultPlanReport")
	public ModelAndView goDefaultPlanReport(HttpServletRequest request,
	        HttpServletResponse response, String id) {
	    
	    CustType custType = custTypeService.get(CustType.class, id);
	    CustPlanReport defaultReport = custPlanReportService.findPackageCustPlanReport(custType.getCode());
	    defaultReport = defaultReport != null ? defaultReport : new CustPlanReport();
	    
	    return new ModelAndView("admin/basic/custType/custType_defaultPlanReport")
	            .addObject("custType", custType)
	            .addObject("defaultReport", defaultReport);
	}
	
	@RequestMapping("/doDefaultPlanReport")
	public void doDefaultPlanReport(HttpServletRequest request, HttpServletResponse response,
	        String custTypeCode, String custPlanReportCode) {
	    
	    try {
            custPlanReportService.setPlanReportsForCustType(custTypeCode, custPlanReportCode,
                    getUserContext(request).getCzybh());
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, e.getMessage());
        }
	    
	}
	
	/**
	 * 跳转到修改CustType页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id, String m_umState){
		logger.debug("跳转到修改CustType页面");
		CustType custType = null;
		if (StringUtils.isNotBlank(id)){
			custType = custTypeService.get(CustType.class, id);
		}else{
			custType = new CustType();
		}
		custType.setM_umState(m_umState);
		if("C".equals(m_umState)){
			custType.setCode("");
		}
		return new ModelAndView("admin/basic/custType/custType_update")
			.addObject("custType", custType);
	}
	/**
	 * 跳转到分段发包CustType页面
	 * @return
	 */
	@RequestMapping("/goCtrlExpr")
	public ModelAndView goCtrlExpr(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到分段发包CustType页面");
		CustType custType = null;
		if (StringUtils.isNotBlank(id)){
			custType = custTypeService.get(CustType.class, id);
		}else{
			custType = new CustType();
		}
		
		return new ModelAndView("admin/basic/custType/custType_ctrlExpr")
			.addObject("custType", custType);
	}
	/**
	 * 跳转到查看CustType页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustType页面");
		CustType custType = custTypeService.get(CustType.class, id);
		
		return new ModelAndView("admin/basic/custType/custType_detail")
				.addObject("custType", custType);
	}
	/**
	 * 添加CustType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustType custType){
		logger.debug("添加CustType开始");
		List<Map<String, Object>> list=this.custTypeService.checkExist(custType);
		if(list.size()>0){
			ServletUtils.outPrintFail(request, response, "此客户类型编号或名称已存在，不允许保存");
		}else{
			try{
				custType.setExpired("F");
				if (custType.getDispSeq()==null) {
					custType.setDispSeq(0);
				}
				this.custTypeService.save(custType);
				ServletUtils.outPrintSuccess(request, response);
				custTypeCacheManager.refresh();
			}catch(Exception e){
				ServletUtils.outPrintFail(request, response, "添加CustType失败");
			}
		}
	}
	
	/**
	 * 修改CustType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustType custType){
		logger.debug("修改CustType开始");
			try{
				if (!"T".equals(custType.getExpired())) {
					custType.setExpired("F");
				}
				if (custType.getDispSeq()==null) {
					custType.setDispSeq(0);
				}
				List<Map<String, Object>> list=this.custTypeService.checkExist(custType);
				if(list.size()>0){
					ServletUtils.outPrintFail(request, response, "此客户类型编号或名称已存在，不允许保存");
				}
				this.custTypeService.update(custType);
				custTypeCacheManager.refresh();
				ServletUtils.outPrintSuccess(request, response);
			}catch(Exception e){
				ServletUtils.outPrintFail(request, response, "修改CustType失败");
			}
	}
	
	/**
	 * 删除CustType
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustType开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustType编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustType custType = custTypeService.get(CustType.class, deleteId);
				if(custType == null)
					continue;
				custType.setExpired("T");
				custTypeService.update(custType);
			}
		}
		custTypeCacheManager.refresh();
		logger.debug("删除CustType IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CustType导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustType custType){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custTypeService.findPageBySql(page, custType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"客户类型管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 * 跳转到合同模板管理页面
	 * @return
	 */
	@RequestMapping("/goCustContractTemp")
	public ModelAndView goCustContractTempManage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_list");
	}
	
	/**
	 * 跳转到合同模板管理页面
	 * @return
	 */
	@RequestMapping("/goCustTypePerfExpr")
	public ModelAndView goCustTypePerfExpr(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到分段业绩计算页面");
		CustType custType = null;
		if (StringUtils.isNotBlank(id)){
			custType = custTypeService.get(CustType.class, id);
		}else{
			custType = new CustType();
		}
		return new ModelAndView("admin/basic/custType/custType_custTypePerfExpr")
			.addObject("custType", custType);
	}
}
