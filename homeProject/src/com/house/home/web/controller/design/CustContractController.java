package com.house.home.web.controller.design;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.JasperReportUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.esign.ESignUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.CustContract;
import com.house.home.entity.design.CustContractPrintLog;
import com.house.home.entity.design.CustContractValue;
import com.house.home.entity.design.CustPayPre;
import com.house.home.entity.design.CustProfit;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ItemPlan;
import com.house.home.service.basic.CmpCustTypeService;
import com.house.home.service.design.CustContractService;
import com.house.home.service.design.CustPayPreService;
import com.house.home.service.design.ItemPlanService;

@Controller
@RequestMapping("/admin/custContract")
public class CustContractController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustContractController.class);

	@Autowired
	private CustContractService custContractService;
	
	@Autowired
	private ItemPlanService itemPlanService;
	
	@Autowired
	private CmpCustTypeService cmpCustTypeService;
	
	@Autowired
	private CustPayPreService custPayPreService;

	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustContract custContract) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custContractService.findPageBySql(page, custContract);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustContract??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custContract/custContract_list");
	}
	/**
	 * CustContract??????code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custContract/custContract_code");
	}
	/**
	 * ???????????????CustContract??????
	 * @return
	 */
	@RequestMapping("/goCreate")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			CustContract custContract){
		logger.debug("???????????????????????????");
		Map<String, Object> dataMap = null;
		Map<String, Object> hisDataMap = this.custContractService.getHisConInfo(custContract);
		Map<String, Object> conDataMap = this.custContractService.getConInfo(custContract);
		
		//?????????????????????????????????????????????????????????
		if(hisDataMap != null){
			//???????????????????????????????????????????????????????????????????????????
			hisDataMap.put("partBdescr", conDataMap.get("partBdescr").toString());
			hisDataMap.put("partBrepName", conDataMap.get("partBrepName").toString());
			dataMap = hisDataMap;
		}else{
			dataMap = conDataMap;
		}
		
		//???????????????????????????????????????
		if (dataMap != null ) {
			String czybh = getUserContext(request).getCzybh();
			if(!czybh.equals(dataMap.get("designMan").toString())){
				String partyAphone = dataMap.get("partyAphone").toString();
				dataMap.put("partyAphone", partyAphone.substring(0,partyAphone.length()-2)+"**");
			} else {
				dataMap.put("isDesigner", "true");
			}
		}
		
		//???????????????????????????
		dataMap.put("m_umState", "A");
		
		return new ModelAndView("admin/design/custContract/custContract_create")
			.addObject("data", dataMap);
	}
	
	/**
	 * ???????????????CustContract??????
	 * @return
	 */
	@RequestMapping("/goCreateDesign")
	public ModelAndView goCreateDesign(HttpServletRequest request, HttpServletResponse response, 
			CustContract custContract){
		logger.debug("?????????????????????????????????");
		Map<String, Object> dataMap = null;
		Map<String, Object> hisDataMap = this.custContractService.getHisConInfo(custContract);
		Map<String, Object> conDataMap = this.custContractService.getConInfo(custContract);
		
		//?????????????????????????????????????????????????????????
		if(hisDataMap != null){
			//???????????????????????????????????????????????????????????????????????????
			hisDataMap.put("partBdescr", conDataMap.get("partBdescr").toString());
			hisDataMap.put("partBrepName", conDataMap.get("partBrepName").toString());
			dataMap = hisDataMap;
		}else{
			dataMap = conDataMap;
		}
		
		//???????????????????????????????????????
		if (dataMap != null ) {
			String czybh = getUserContext(request).getCzybh();
			if(!czybh.equals(dataMap.get("designMan").toString())){
				String partyAphone = dataMap.get("partyAphone").toString();
				dataMap.put("partyAphone", partyAphone.substring(0,partyAphone.length()-2)+"**");
			} else {
				dataMap.put("isDesigner", "true");
			}
		}
		
		//???????????????????????????
		dataMap.put("m_umState", "A");
		
		return new ModelAndView("admin/design/custContract/custContract_design_create")
			.addObject("data", dataMap);
	}
	
	/**
	 * ???????????????CustContract??????
	 * @return
	 */
	@RequestMapping("/goSubmit")
	public ModelAndView goSubmit(HttpServletRequest request, HttpServletResponse response,CustContract cc){
		logger.debug("???????????????????????????");
		
		Customer customer = custContractService.get(Customer.class, cc.getCustCode());
		CustType custType = custContractService.get(CustType.class, customer.getCustType());
		CustContract custContract = custContractService.get(CustContract.class, cc.getPk());
		custContract.setIsPreView(cc.getIsPreView());
		
		//?????????????????????
		String hasGiftItem = "";
		if (itemPlanService.hasGiftItem(customer.getCode())){
			hasGiftItem = "1";
		}
		
		//??????????????????
		String contractFeeRepType = "";
		CustProfit custProfit = custContractService.get(CustProfit.class, custContract.getCustCode());
		
		CustPayPre custPayPre=custPayPreService.getCustPayPre(customer.getCode(), "1");
		CustPayPre custPayPre2=custPayPreService.getCustPayPre(customer.getCode(), "2");
		CustPayPre custPayPre3=custPayPreService.getCustPayPre(customer.getCode(), "3");
		CustPayPre custPayPre4=custPayPreService.getCustPayPre(customer.getCode(), "4");
		Double basePaySum = 0.0;
		if(custPayPre != null && custPayPre3 != null && custPayPre3 != null && custPayPre4 != null){
			basePaySum = custPayPre.getBasePay()+custPayPre2.getBasePay()+custPayPre3.getBasePay()+custPayPre4.getBasePay();

		}
		if(custProfit != null){
			customer.setPayType(custProfit.getPayType());
			List<Map<String, Object>> list = itemPlanService.getContractFeeRepType(customer);
			if(list != null && list.size() > 0){
				contractFeeRepType = list.get(0).get("contractFeeRepType").toString();
			}
		}
		ItemPlan itemPlan = new ItemPlan();
		itemPlan.setPayType(custProfit.getPayType());
		itemPlan.setCustType(customer.getCustType());
		Map<String,Object> payRemark=itemPlanService.getPayRemark(itemPlan);
		
		//?????????????????????????????????????????????????????????
		String mainConTemp = "";
		String appendConTemp = "";
		Map<String, Object> mainConMap = custContractService.getContractTemp(customer.getCustType(), "1" , customer.getBuilderCode());
		Map<String, Object> appendConMap =  custContractService.getContractTemp(customer.getCustType(), "2" , customer.getBuilderCode());
		Map<String, Object> latelySignedConMap = custContractService.latelySignedCon(custContract);//???????????????????????????
		Integer oldConPk = null;
		if(latelySignedConMap != null){
			oldConPk = Integer.parseInt(latelySignedConMap.get("PK").toString());//???????????????????????????pk
		}
		Map<String, Object> againSignMap = custContractService.getOldCustType(custContract.getCustCode());//???????????????
		
		//??????????????????????????????????????????????????????????????????????????? ?????????????????????????????????????????????
		if(againSignMap == null || againSignMap != null && !customer.getCustType().equals(againSignMap.get("CustType").toString())){
			mainConMap = custContractService.getContractTemp(customer.getCustType(), "1" , customer.getBuilderCode());
			appendConMap = custContractService.getContractTemp(customer.getCustType(), "2" , customer.getBuilderCode());
		}else {
			mainConMap = custContractService.getHisContractTemp(oldConPk, null, "1");
			appendConMap = custContractService.getHisContractTemp(oldConPk, null, "2");
			//?????????????????????????????????????????????
			if (mainConMap == null) {
				mainConMap = custContractService.getContractTemp(customer.getCustType(), "1" , customer.getBuilderCode());
			}
			if (appendConMap == null) {
				appendConMap = custContractService.getContractTemp(customer.getCustType(), "2" , customer.getBuilderCode());
			}
		}
		
		if(mainConMap != null){
			mainConTemp = mainConMap.get("Descr").toString() + "(" + mainConMap.get("Version").toString() + ")";
		}
		if(appendConMap != null){
			appendConTemp = appendConMap.get("Descr").toString() + "(" + appendConMap.get("Version").toString() + ")";
		}
		
		Map<String, Object> hasItemPlanMap = custContractService.hasItemPlan(custContract.getCustCode());
		
		//????????????logo
		String logoFile=cmpCustTypeService.getLogoFile(custContract.getCustCode());
		
		String defaultRep = "";
		Map<String,Object> defaultRepMap=custContractService.getDefaultReport(customer);
		if (defaultRepMap != null) {
			defaultRep = defaultRepMap.get("DefaultReport").toString();
		}
		
		return new ModelAndView("admin/design/custContract/custContract_submit")
			.addObject("hasGiftItem", hasGiftItem)
			.addObject("contractFeeRepType", contractFeeRepType)
			.addObject("customer",customer)
			.addObject("custType",custType)
			.addObject("mainConTemp",mainConTemp)
			.addObject("appendConTemp",appendConTemp)
			.addObject("logoFile", logoFile)
			.addObject("custContract", custContract)
			.addObject("hasItemPlanMap", hasItemPlanMap)
			.addObject("custProfit",custProfit)
			.addObject("basePaySum",basePaySum)
			.addObject("payRemark", payRemark)
			.addObject("defaultRep", defaultRep);
	}
	
	/**
	 * ???????????????CustContract??????
	 * @return
	 */
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request, HttpServletResponse response, 
			CustContract custContract){
		logger.debug("???????????????????????????");
		Map<String, Object> dataMap = this.custContractService.getHisConInfo(custContract);
		
		//?????????????????????*
		if (dataMap != null ) {
			dataMap.put("partyAphone", "***********");
		}
		
		//???????????????????????????
		dataMap.put("m_umState", "C");
		
		return new ModelAndView("admin/design/custContract/custContract_create")
			.addObject("data", dataMap);
	}
	
	/**
	 * ???????????????CustContract??????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			CustContract custContract){
		logger.debug("???????????????????????????");
		Map<String, Object> dataMap = this.custContractService.getHisConInfo(custContract);
		
		//?????????????????????*
		if (dataMap != null ) {
			dataMap.put("partyAphone", "***********");
		}
		
		//???????????????????????????
		dataMap.put("m_umState", "V");
		
		return new ModelAndView("admin/design/custContract/custContract_create")
			.addObject("data", dataMap);
	}
	
	/**
	 * ???????????????CustContract??????
	 * @return
	 */
	@RequestMapping("/goViewDesign")
	public ModelAndView goViewDesign(HttpServletRequest request, HttpServletResponse response, 
			CustContract custContract){
		logger.debug("???????????????????????????");
		Map<String, Object> dataMap = this.custContractService.getHisConInfo(custContract);
		
		//?????????????????????*
		if (dataMap != null ) {
			dataMap.put("partyAphone", "***********");
		}
		
		//???????????????????????????
		dataMap.put("m_umState", "V");
		
		return new ModelAndView("admin/design/custContract/custContract_design_create")
			.addObject("data", dataMap);
	}
	
	/**
	 * ???????????????CustContract??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			CustContract custContract){
		logger.debug("???????????????CustContract??????");
		Map<String, Object> dataMap = this.custContractService.getHisConInfo(custContract);
		//???????????????????????????????????????
		if (dataMap != null ) {
			String czybh = getUserContext(request).getCzybh();
			if(!czybh.equals(dataMap.get("designMan").toString())){
				String partyAphone = dataMap.get("partyAphone").toString();
				dataMap.put("partyAphone", partyAphone.substring(0,partyAphone.length()-2)+"**");
			}
		}
		//???????????????????????????
		dataMap.put("m_umState", "M");
		return new ModelAndView("admin/design/custContract/custContract_create")
			.addObject("data", dataMap);
	}
	
	/**
	 * ???????????????CustContract??????
	 * @return
	 */
	@RequestMapping("/goUpdateDesign")
	public ModelAndView goUpdateDesign(HttpServletRequest request, HttpServletResponse response, 
			CustContract custContract){
		logger.debug("???????????????CustContract??????");
		Map<String, Object> dataMap = this.custContractService.getHisConInfo(custContract);
		//???????????????????????????????????????
		if (dataMap != null ) {
			String czybh = getUserContext(request).getCzybh();
			if(!czybh.equals(dataMap.get("designMan").toString())){
				String partyAphone = dataMap.get("partyAphone").toString();
				dataMap.put("partyAphone", partyAphone.substring(0,partyAphone.length()-2)+"**");
			}
		}
		//???????????????????????????
		dataMap.put("m_umState", "M");
		return new ModelAndView("admin/design/custContract/custContract_design_create")
			.addObject("data", dataMap);
	}
	
	/**
	 * ??????CustContract
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCreate")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustContract custContract,Customer ct){
		logger.debug("??????CustContract??????");
		try{
			Customer customer = custContractService.get(Customer.class,ct.getCode());
			customer.setLayout(ct.getLayout());
			customer.setContractDay(ct.getContractDay());
			customer.setBeginDate(ct.getBeginDate());
			customer.setDescr(custContract.getPartyAname());
			customer.setConId(custContract.getPartyAid());
			customer.setMobile1(custContract.getPartyAphone());
			customer.setConPhone(custContract.getPartyAphone());
			customer.setLastUpdate(new Date());
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			customer.setActionLog("EDIT");
			
			//??????????????????
			String conNo = custContractService.getConNo(customer.getCustType()).getInfo();
			custContract.setConNo(conNo);
			
			//???????????????
			custContract.setCustCode(customer.getCode());
			custContract.setStatus("1");
			custContract.setConType("1");
			custContract.setLastUpdate(new Date());
			custContract.setLastUpdatedBy(getUserContext(request).getCzybh());
			custContract.setExpired("F");
			custContract.setActionLog("ADD");
			custContract.setSignDate(new Date());
			Result result = this.custContractService.doCreate(custContract,customer);
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????CustContract??????");
		}
	}
	
	/**
	 * ??????CustContract
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCreateDesign")
	public void doCreateDesign(HttpServletRequest request, HttpServletResponse response, CustContract custContract,Customer ct,CustProfit cp){
		logger.debug("??????CustContract??????");
		try{
			Customer customer = custContractService.get(Customer.class,ct.getCode());
			customer.setLayout(ct.getLayout());
			customer.setBeginDate(ct.getBeginDate());
			customer.setDescr(custContract.getPartyAname());
			customer.setConId(custContract.getPartyAid());
			customer.setMobile1(custContract.getPartyAphone());
			customer.setConPhone(custContract.getPartyAphone());
			customer.setDesignStyle(ct.getDesignStyle());
			customer.setConstructType(ct.getConstructType());
			customer.setExpectMoveIntoDate(ct.getExpectMoveIntoDate());
			customer.setPlanAmount(ct.getPlanAmount());
			customer.setLastUpdate(new Date());
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			customer.setActionLog("EDIT");
			
			//???????????????
			custContract.setConNo("");
			custContract.setCustCode(customer.getCode());
			custContract.setStatus("1");
			custContract.setConType("2");
			custContract.setLastUpdate(new Date());
			custContract.setLastUpdatedBy(getUserContext(request).getCzybh());
			custContract.setExpired("F");
			custContract.setActionLog("ADD");
			custContract.setSignDate(new Date());
			
			CustProfit custProfit = custContractService.get(CustProfit.class,ct.getCode());
			if(custProfit == null){
				custProfit = new CustProfit();
				custProfit.setCustCode(ct.getCode());
				custProfit.setLastUpdate(new Date());
				custProfit.setLastUpdatedBy(getUserContext(request).getCzybh());
				custProfit.setExpired("F");
				custProfit.setActionLog("ADD");
			}
			custProfit.setPosition(cp.getPosition());
			custProfit.setPrepay(cp.getPrepay());
			
			Result result = this.custContractService.doCreate(custContract,customer,custProfit);
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????CustContract??????");
		}
	}
	
	/**
	 * ??????CustContract
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustContract cc,Customer ct){
		logger.debug("??????CustContract??????");
		try{
			Customer customer = custContractService.get(Customer.class,ct.getCode());
			customer.setLayout(ct.getLayout());
			customer.setContractDay(ct.getContractDay());
			customer.setBeginDate(ct.getBeginDate());
			customer.setDescr(cc.getPartyAname());
			customer.setConId(cc.getPartyAid());
			customer.setMobile1(cc.getPartyAphone());
			customer.setConPhone(cc.getPartyAphone());
			customer.setLastUpdate(new Date());
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			customer.setActionLog("EDIT");
			//???????????????
			CustContract custContract = custContractService.get(CustContract.class,cc.getPk());
			custContract.setConcreteAddress(cc.getConcreteAddress());
			custContract.setPartBdescr(cc.getPartBdescr());
			custContract.setPartBrepName(cc.getPartBrepName());
			custContract.setPartyAid(cc.getPartyAid());
			custContract.setPartyAname(cc.getPartyAname());
			custContract.setPartyAphone(cc.getPartyAphone());
			custContract.setConMode(cc.getConMode());
			custContract.setIsFutureCon(cc.getIsFutureCon());
			custContract.setLastUpdate(new Date());
			custContract.setLastUpdatedBy(getUserContext(request).getCzybh());
			custContract.setActionLog("EDIT");
			
			if(!"1".equals(custContract.getStatus())){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????");
				return;
			}
			
			this.custContractService.doUpdate(custContract,customer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ??????CustContract
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateDesign")
	public void doUpdateDesign(HttpServletRequest request, HttpServletResponse response, CustContract cc,Customer ct,CustProfit cp){
		logger.debug("??????CustContract??????");
		try{
			Customer customer = custContractService.get(Customer.class,ct.getCode());
			customer.setLayout(ct.getLayout());
			customer.setBeginDate(ct.getBeginDate());
			customer.setDescr(cc.getPartyAname());
			customer.setConId(cc.getPartyAid());
			customer.setMobile1(cc.getPartyAphone());
			customer.setConPhone(cc.getPartyAphone());
			customer.setDesignStyle(ct.getDesignStyle());
			customer.setConstructType(ct.getConstructType());
			customer.setExpectMoveIntoDate(ct.getExpectMoveIntoDate());
			customer.setPlanAmount(ct.getPlanAmount());
			customer.setLastUpdate(new Date());
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			customer.setActionLog("EDIT");
			
			//???????????????
			CustContract custContract = custContractService.get(CustContract.class,cc.getPk());
			custContract.setPartBdescr(cc.getPartBdescr());
			custContract.setPartBrepName(cc.getPartBrepName());
			custContract.setPartyAid(cc.getPartyAid());
			custContract.setPartyAname(cc.getPartyAname());
			custContract.setPartyAphone(cc.getPartyAphone());
			custContract.setPlaneDrawDay(cc.getPlaneDrawDay());
			custContract.setFullDrawDay(cc.getFullDrawDay());
			custContract.setLastUpdate(new Date());
			custContract.setLastUpdatedBy(getUserContext(request).getCzybh());
			custContract.setActionLog("EDIT");
			
			CustProfit custProfit = custContractService.get(CustProfit.class,ct.getCode());
			custProfit.setPosition(cp.getPosition());
			custProfit.setPrepay(cp.getPrepay());
			
			if(!"1".equals(custContract.getStatus())){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????");
				return;
			}
			
			this.custContractService.doUpdate(custContract,customer,custProfit);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}

	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hasCon")
	@ResponseBody
	public String hasCon(HttpServletRequest request, HttpServletResponse response, CustContract custContract){
		List<Map<String, Object>> list = this.custContractService.hasCon(custContract);
		if(list != null && list.size() > 0){
			return "1";
		}
		return "0";
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hasDraftCon")
	@ResponseBody
	public String hasDraftCon(HttpServletRequest request, HttpServletResponse response, CustContract custContract){
		List<Map<String, Object>> list = this.custContractService.hasDraftCon(custContract);
		if(list != null && list.size() > 0){
			return "1";
		}
		return "0";
	}

	/**
	 * ??????CustContract
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSubmit")
	public void doSubmit(HttpServletRequest request, HttpServletResponse response, CustContract cc,Customer ct){
		logger.debug("??????????????????");
		try{
			String data = request.getParameter("data");
			Customer customer = custContractService.get(Customer.class, ct.getCode());
			CustContract custContract = custContractService.get(CustContract.class, cc.getPk());
			custContract.setAppCzy(getUserContext(request).getCzybh());
			custContract.setAppDate(new Date());
			custContract.setLastUpdatedBy(getUserContext(request).getCzybh());
			custContract.setActionLog("EDIT");
			custContract.setSignDate(new Date());
			Result result = custContractService.doSubmit(custContract, customer, data);
			if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doConfirmPass")
	public void doConfirmPass(HttpServletRequest request, HttpServletResponse response, CustContract cc){
		logger.debug("????????????");
		try{
			CustContract custContract = custContractService.get(CustContract.class, cc.getPk());
			if(!"2".equals(custContract.getStatus())){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
				return;
			}
			custContract.setStatus("3");
			custContract.setConfirmCzy(getUserContext(request).getCzybh());
			custContract.setConfirmDate(new Date());
			custContract.setConfirmRemarks(cc.getConfirmRemarks());
			
			Customer customer = custContractService.get(Customer.class, custContract.getCustCode());
			if("4".equals(customer.getStatus()) || "5".equals(customer.getStatus())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????????????????");
				return;
			}
			String msgText =customer.getAddress()+"????????????????????????????????????????????????";
			if(StringUtils.isNotBlank(cc.getConfirmRemarks())){
				msgText += "???????????????"+cc.getConfirmRemarks();
			}
			PersonMessage personMessage = new PersonMessage();
			personMessage.setMsgType("19");
			personMessage.setMsgText(msgText);
			personMessage.setRcvCzy(custContract.getAppCzy());
			personMessage.setCrtDate(new Date());
			personMessage.setSendDate(new Date());
			personMessage.setRcvType("3");
			personMessage.setRcvStatus("0");
			personMessage.setIsPush("1");
			personMessage.setPushStatus("0");
			personMessage.setTitle("????????????????????????");
			
			Result result = custContractService.doConfirmPass(custContract,personMessage);
			if(Result.FAIL_CODE.equals(result.getCode())){
        		ServletUtils.outPrintFail(request, response, result.getInfo());
        	}else {
        		ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doConfirmCancel")
	public void doConfirmCancel(HttpServletRequest request, HttpServletResponse response, CustContract cc){
		logger.debug("????????????");
		try{
			
			CustContract custContract = custContractService.get(CustContract.class, cc.getPk());
			if(!"2".equals(custContract.getStatus())){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
				return;
			}
			custContract.setStatus("7");
			custContract.setConfirmCzy(getUserContext(request).getCzybh());
			custContract.setConfirmDate(new Date());
			custContract.setEndDate(new Date());
			custContract.setEndRemarks("???????????????");
			custContract.setConfirmRemarks(cc.getConfirmRemarks());
			custContract.setEndCzy(getUserContext(request).getCzybh());

			Customer customer = custContractService.get(Customer.class, custContract.getCustCode());
			String msgText =customer.getAddress()+"????????????????????????????????????????????????";
			if(StringUtils.isNotBlank(cc.getConfirmRemarks())){
				msgText += "???????????????"+cc.getConfirmRemarks();
			}
			PersonMessage personMessage = new PersonMessage();
			personMessage.setMsgType("19");
			personMessage.setMsgText(msgText);
			personMessage.setRcvCzy(custContract.getAppCzy());
			personMessage.setCrtDate(new Date());
			personMessage.setSendDate(new Date());
			personMessage.setRcvType("3");
			personMessage.setRcvStatus("0");
			personMessage.setIsPush("1");
			personMessage.setPushStatus("0");
			personMessage.setTitle("????????????????????????");
			
			custContractService.doConfirmCancel(custContract, personMessage);
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping(value={"/doCancel","/doCancelDesign"})
	public void doCancel(HttpServletRequest request, HttpServletResponse response, CustContract cc){
		logger.debug("????????????");
		try{
			CustContract custContract = custContractService.get(CustContract.class, cc.getPk());
			custContract.setEndCzy(getUserContext(request).getCzybh());
			custContract.setEndRemarks("???????????????"); 
			custContract.setEndDate(new Date());
			Result result = custContractService.doCancel(custContract);
			
			ServletUtils.outPrintSuccess(request, response,result.getInfo());
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * e????????????????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/eSignCallBack")
	public void eSignCallBack(@RequestBody Map<String, Object> map){
		logger.debug("e????????????????????????");
		try{
			custContractService.eSignCallBack(map);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doRefreshStatus")
	public void refreshStatus(HttpServletRequest request, HttpServletResponse response,CustContract cc){
		logger.debug("????????????");
		try{
			CustContract custContract = custContractService.get(CustContract.class, cc.getPk());
			Customer customer = custContractService.get(Customer.class, custContract.getCustCode());
			
			//????????????
			if("3".equals(custContract.getStatus()) &&
			("1".equals(customer.getStatus()) || "2".equals(customer.getStatus()) || "3".equals(customer.getStatus()) ) ){
				JSONObject jsonObject = ESignUtils.queryFlows(custContract.getFlowId());
				Map<String, Object> map = new HashMap<String, Object>();
				if(jsonObject != null){
					map.put("action", "SIGN_FLOW_FINISH");
					map.put("flowId", jsonObject.getString("flowId"));
					map.put("flowStatus", jsonObject.getString("flowStatus"));
					map.put("statusDescription", jsonObject.getString("flowDesc"));
				}
				custContractService.eSignCallBack(map);
				
				ServletUtils.outPrintSuccess(request, response);
			}else {
				ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????????????????????????????");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doRefreshStatusDesign")
	public void refreshStatusDesign(HttpServletRequest request, HttpServletResponse response,CustContract cc){
		logger.debug("????????????");
		try{
			CustContract custContract = custContractService.get(CustContract.class, cc.getPk());
			Customer customer = custContractService.get(Customer.class, custContract.getCustCode());
			
			//????????????
			if("2".equals(custContract.getStatus()) 
					&& ("1".equals(customer.getStatus()) || "2".equals(customer.getStatus()) ) ){
				JSONObject jsonObject = ESignUtils.queryFlows(custContract.getFlowId());
				Map<String, Object> map = new HashMap<String, Object>();
				if(jsonObject != null){
					map.put("action", "SIGN_FLOW_FINISH");
					map.put("flowId", jsonObject.getString("flowId"));
					map.put("flowStatus", jsonObject.getString("flowStatus"));
					map.put("statusDescription", jsonObject.getString("flowDesc"));
				}
				custContractService.eSignCallBack(map);
				
				ServletUtils.outPrintSuccess(request, response);
			}else {
				ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????????????????");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param pk
	 * @throws IOException
	 */
	@RequestMapping("/beforePrint")
	public void beforePrint(HttpServletRequest request,
	        HttpServletResponse response, Integer pk,String m_umState) throws IOException{
		
		CustContract custContract = custContractService.get(CustContract.class, pk);
		Customer customer = custContractService.get(Customer.class, custContract.getCustCode());
	    
        String doc = "";
        
        //????????????????????????
        if("C".equals(m_umState)){
        	doc = custContract.getOriginalDoc();
        }else if(StringUtils.isNotBlank(custContract.getEffectDoc())){//?????????????????????????????????
        	doc = custContract.getEffectDoc();
        }
        
        //????????????????????????????????????????????????????????????????????????
        if(StringUtils.isBlank(doc)){
        	String data = request.getParameter("data");
        	Result result = custContractService.createDoc(custContract, customer, data, false);
        	if(Result.FAIL_CODE.equals(result.getCode())){
        		ServletUtils.outPrintFail(request, response, result.getInfo());
        	}else {
        		ServletUtils.outPrintSuccess(request, response,result.getCode());
			}
        }
        
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param pk
	 * @throws IOException
	 */
	@RequestMapping(value={"/doPrint","/doPrintDesign"})
	public void doPrint(HttpServletRequest request,
	        HttpServletResponse response, Integer pk,String m_umState,String tempFile) throws IOException{
		
		CustContract custContract = custContractService.get(CustContract.class, pk);
		response.setContentType("application/pdf;charset=utf-8"); 
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(custContract.getConDescr()+".pdf", "utf-8"));
       
        String doc = "";
        InputStream in = null;
        OutputStream out = null;
        
        try {
        	//????????????????????????
        	if("C".equals(m_umState)){
        		doc = custContract.getOriginalDoc();
        	}else if(StringUtils.isNotBlank(custContract.getEffectDoc())){//?????????????????????????????????
        		doc = custContract.getEffectDoc();
        	}
        	
        	//?????????????????????????????????????????????????????????????????????????????????????????????
        	if(StringUtils.isNotBlank(doc)){
        		in = FileUploadUtils.download(doc);
        	}else{
        		in = new FileInputStream(tempFile);
        	}
        	
        	out = response.getOutputStream();
        	Streams.copy(in, out, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
		}
	    
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param pk
	 * @throws IOException
	 */
	@RequestMapping("/updatePrint")
	public void updatePrint(HttpServletRequest request,
	        HttpServletResponse response, Integer pk) throws IOException{
		
		try{
			CustContractPrintLog custContractPrintLog = new CustContractPrintLog(pk, 
					getUserContext(request).getCzybh(), new Date(), new Date(),
					getUserContext(request).getCzybh(), "F", "ADD");
			
			custContractService.save(custContractPrintLog);
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	    
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSendDesign")
	public void doSendDesign(HttpServletRequest request, HttpServletResponse response, CustContract cc){
		logger.debug("????????????");
		try{
			CustContract custContract = custContractService.get(CustContract.class, cc.getPk());
			if(!"1".equals(custContract.getStatus())){
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????");
				return;
			}
			custContract.setStatus("2");
			custContract.setAppCzy(getUserContext(request).getCzybh());
			custContract.setAppDate(new Date());
			
			Customer customer = custContractService.get(Customer.class, custContract.getCustCode());
			if("3".equals(customer.getStatus()) || "4".equals(customer.getStatus()) || "5".equals(customer.getStatus())){
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????????????????????????????????????????");
				return;
			}
			
			Result result = custContractService.doSendDesign(custContract, customer);
			if(Result.FAIL_CODE.equals(result.getCode())){
        		ServletUtils.outPrintFail(request, response, result.getInfo());
        	}else {
        		ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
}
