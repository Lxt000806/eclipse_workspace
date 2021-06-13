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
import com.house.home.entity.basic.MtCustInfo;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.ReturnPay;
import com.house.home.service.basic.MtCustInfoService;

@Controller
@RequestMapping("/admin/mtCustInfoAssign")
public class MtCustInfoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MtCustInfoController.class);

	@Autowired
	private MtCustInfoService mtCustInfoService;

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
			HttpServletResponse response, MtCustInfo mtCustInfo) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mtCustInfo.setCustRight(getUserContext(request).getCustRight());
		mtCustInfo.setLastUpdatedBy(getUserContext(request).getCzybh());
		mtCustInfoService.findPageBySql(page, mtCustInfo);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * MtCustInfo列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_list");
	}
	/**
	 * MtCustInfo查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_code");
	}
	/**
	 * 跳转到新增MtCustInfo页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增MtCustInfo页面");
		MtCustInfo mtCustInfo = null;
		if (StringUtils.isNotBlank(id)){
			mtCustInfo = mtCustInfoService.get(MtCustInfo.class, Integer.parseInt(id));
		}else{
			mtCustInfo = new MtCustInfo();
		}
		
		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_save")
			.addObject("mtCustInfo", mtCustInfo);
	}
	/**
	 * 跳转到修改MtCustInfo页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改MtCustInfo页面");
		MtCustInfo mtCustInfo = null;
		if (StringUtils.isNotBlank(id)){
			mtCustInfo = mtCustInfoService.get(MtCustInfo.class, Integer.parseInt(id));
		}else{
			mtCustInfo = new MtCustInfo();
		}
		
		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_update")
			.addObject("mtCustInfo", mtCustInfo);
	}
	
	/**
	 * 跳转到查看MtCustInfo页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到查看MtCustInfo页面");
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		
		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_view")
				.addObject("map", jsonObject);
	}
	/**
	 * 添加MtCustInfo
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, MtCustInfo mtCustInfo){
		logger.debug("添加MtCustInfo开始");
		try{
			this.mtCustInfoService.save(mtCustInfo);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加MtCustInfo失败");
		}
	}
	
	/**
	 * 修改MtCustInfo
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, MtCustInfo mtCustInfo){
		logger.debug("修改MtCustInfo开始");
		try{
			mtCustInfo.setLastUpdate(new Date());
			mtCustInfo.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.mtCustInfoService.update(mtCustInfo);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改MtCustInfo失败");
		}
	}
	
	/**
	 * 删除MtCustInfo
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除MtCustInfo开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "MtCustInfo编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				MtCustInfo mtCustInfo = mtCustInfoService.get(MtCustInfo.class, Integer.parseInt(deleteId));
				if(mtCustInfo == null)
					continue;
				mtCustInfo.setExpired("T");
				mtCustInfoService.update(mtCustInfo);
			}
		}
		logger.debug("删除MtCustInfo IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *MtCustInfo导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, MtCustInfo mtCustInfo){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		mtCustInfo.setCustRight(getUserContext(request).getCustRight());
		mtCustInfo.setLastUpdatedBy(getUserContext(request).getCzybh());
		mtCustInfoService.findPageBySql(page, mtCustInfo);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"麦田客户管理表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 取消MtCustInfo
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doCancel")
	public void doCancel(HttpServletRequest request, HttpServletResponse response, MtCustInfo mtCustInfo){
		logger.debug("取消MtCustInfo开始");
		mtCustInfo.setLastUpdatedBy(getUserContext(request).getCzybh());
		mtCustInfoService.cancel(mtCustInfo);
		ServletUtils.outPrintSuccess(request, response,"取消成功");
	}
	/**
	 * MtCustInfo列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSetCzy")
	public ModelAndView goSetCzy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_setCzy");
	}
	/**
	 * MtCustInfo列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goChooseCzy")
	public ModelAndView goSetCzy(HttpServletRequest request,
			HttpServletResponse response,MtCustInfo mtCustInfo) throws Exception {

		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_chooseCzy").addObject("mtCustInfo",mtCustInfo);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goMtRegionJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goMtRegionJqGrid(HttpServletRequest request,
			HttpServletResponse response, MtCustInfo mtCustInfo) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mtCustInfoService.goMtRegionJqGrid(page);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 设置对接人
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/setCzy")
	public void setCzy(HttpServletRequest request, HttpServletResponse response, MtCustInfo mtCustInfo){
		logger.debug("设置对接人开始");
		mtCustInfo.setLastUpdatedBy(getUserContext(request).getCzybh());
		mtCustInfoService.setCzy(mtCustInfo);
		ServletUtils.outPrintSuccess(request, response,"设置成功");
	}
	
	/**
	 * 跳转到查看跟踪情况页面
	 * @return
	 */
	@RequestMapping("/goCustConView")
	public ModelAndView goCustConView(HttpServletRequest request, HttpServletResponse response,MtCustInfo mtCustInfo){
		logger.debug("跳转到查看ConRemarksView页面");
		
		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_custConView")
				.addObject("mtCustInfo", mtCustInfo);
	}
	
	/**
	 * 查询CustConJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCustConJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCustConJqGrid(HttpServletRequest request,
			HttpServletResponse response, MtCustInfo mtCustInfo) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mtCustInfo.setCustRight(getUserContext(request).getCustRight());
		mtCustInfo.setLastUpdatedBy(getUserContext(request).getCzybh());
		mtCustInfoService.findCustConPageBySql(page, mtCustInfo);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goAddCustCode")
	public ModelAndView goAddCustCode(HttpServletRequest request, HttpServletResponse response,
				MtCustInfo mtCustInfo){
		logger.debug("跳转到查看goAddCustCode页面");

		Customer customer = new Customer();
		if(mtCustInfo.getPK() != 0){
			mtCustInfo = mtCustInfoService.get(MtCustInfo.class, mtCustInfo.getPK());
		}
		
		String custCode = mtCustInfoService.getCustCodeByPk(mtCustInfo);
		if(StringUtils.isNotBlank(custCode)){
			customer = mtCustInfoService.get(Customer.class, custCode);
		}
		
		return new ModelAndView("admin/basic/mtCustInfo/mtCustInfo_addCustCode")
				.addObject("mtCustInfo", mtCustInfo).addObject("customer", customer);
	}
	
	@RequestMapping("/doAddCustCode")
	public void doAddCustCode(HttpServletRequest request, HttpServletResponse response, MtCustInfo mtCustInfo){
		logger.debug("修改MtCustInfo开始");
		try{
			Customer oldCustomer= new Customer();
			Customer customer = new Customer();
			String custCode = mtCustInfoService.getCustCodeByPk(mtCustInfo);
			if(StringUtils.isNotBlank(mtCustInfo.getCustCode()) && mtCustInfo.getCustCode().equals(custCode)){
				ServletUtils.outPrintFail(request, response, "修改失败,意向客户无变化。");
				return;
			}
			
			if(StringUtils.isBlank(mtCustInfo.getCustCode())){
				ServletUtils.outPrintFail(request, response, "修改失败,新意向客户为空。");
				return;
			}
			oldCustomer= mtCustInfoService.get(Customer.class, custCode);
			customer = mtCustInfoService.get(Customer.class, mtCustInfo.getCustCode());
			mtCustInfo = mtCustInfoService.get(MtCustInfo.class, mtCustInfo.getPK());
			mtCustInfo.setCustCode(customer.getCode());
			
			
			/*if(customer != null && customer.getMtCustInfoPK() != null){
				ServletUtils.outPrintFail(request, response, "修改失败,该意向客户已存在关联麦田客户");
			}*/
			
			if(customer.getPerfPK() != null){
				mtCustInfo.setStatus("3");
				mtCustInfo.setSendDate(null);
			} else {
				mtCustInfo.setStatus("2");
				mtCustInfo.setSendDate(null);
				mtCustInfo.setPerf(null);
				mtCustInfo.setPerfCompDate(null);
			}
			mtCustInfo.setLastUpdate(new Date());
			mtCustInfo.setLastUpdatedBy(getUserContext(request).getCzybh());

			if(oldCustomer != null){
				oldCustomer.setMtCustInfoPK(null);
				this.mtCustInfoService.update(oldCustomer);
			}
			
			customer.setMtCustInfoPK(mtCustInfo.getPK());
			
			List<Map<String,Object>> custPerfData = mtCustInfoService.getCustPerfData(customer);
			if(custPerfData != null && custPerfData.size() >0){
				mtCustInfo.setPerf(Double.parseDouble(custPerfData.get(0).get("PerfAmount").toString()));
				mtCustInfo.setPerfCompDate(DateUtil.parse(custPerfData.get(0).get("AchieveDate").toString(), "yyyy-MM-dd hh:mm:ss"));
			}
			
			this.mtCustInfoService.update(mtCustInfo);
			this.mtCustInfoService.update(customer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改MtCustInfo失败");
		}
	}
	
	@RequestMapping("/checkCust")
	@ResponseBody
	public String checkCust(HttpServletRequest request,
			HttpServletResponse response, MtCustInfo mtCustInfo) throws Exception {
		Customer customer = new Customer();
		
		if(StringUtils.isBlank(mtCustInfo.getCustCode())){
			return "true";
		}
		customer = mtCustInfoService.get(Customer.class, mtCustInfo.getCustCode());
		if(customer.getMtCustInfoPK() != null){
			return "false";
		}
		
		return "true";
	}
}
