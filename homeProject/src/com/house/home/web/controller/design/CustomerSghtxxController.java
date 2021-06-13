package com.house.home.web.controller.design;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustGift;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.DesignFeeSd;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Gift;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustProfit;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.DiscToken;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.DesignFeeSdService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.BaseItemPlanService;
import com.house.home.service.design.CustPayService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.ItemPlanService;
import com.house.home.service.finance.DiscTokenService;
import com.house.home.service.insales.ItemReqService;

@Controller
@RequestMapping("/admin/customerSghtxx")
public class CustomerSghtxxController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerSghtxxController.class);
	
	//public final static String EMPLOYEEAPP_TYPE 	= "3";
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private BaseItemPlanService baseItemPlanService;
	@Autowired
	private ItemPlanService itemPlanService;
	@Autowired
	private CustPayService custPayService;
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private CustStakeholderService custStakeholderService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private ItemReqService itemReqService;
	//@Autowired
	//private AppGroupManageService appGroupManageService;
	@Autowired
	private DesignFeeSdService designFeeSdService;
	@Autowired
	private DiscTokenService discTokenService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		/*if (StringUtils.isBlank(customer.getStatus())){
			customer.setStatus("1,2,3");
		}*/
		customerService.findPageBySql_sghtxx(page, customer, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getCountDiscDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getCountDiscDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		itemReqService.getCountDiscDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getResignDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getResignDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		/*if (StringUtils.isBlank(customer.getStatus())){
				customer.setStatus("1,2,3");
		}*/
		customerService.findPageBySql_viewResign(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 风控基金明细
	 * @author	created by zb
	 * @date	2019-10-28--下午5:37:29
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRiskFundJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getRiskFundJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.findRiskFundPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 施工合同信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		if (StringUtils.isBlank(customer.getStatus())){
			customer.setStatus("1,2,3");
		}
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_list")
		.addObject("customer", customer).addObject("czybh",this.getUserContext(request).getCzybh());
	}
	
	/**
	 * 跳转到施工合同信息录入页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("跳转到施工合同信息录入页面");
		Customer customer = null;
		Department1 department1=new Department1();
		Department2 department2=new Department2();
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
			resetCustomer(customer);
		}else{
			customer = new Customer();
		}
		if(!"4".equals(customer.getStatus())){
			// 修改施工工期没有匹配到规则为空的问题 modify by zb
			Integer constructDay = customerService.getCustConsDay(id);
			if (null == constructDay) {
				customer.setConstructDay(customer.getContractDay());
			} else {
				customer.setConstructDay(constructDay);
			}
		}
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			department1=customerService.get(Department1.class, customer.getDepartment1());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			department2=customerService.get(Department2.class, customer.getDepartment2());
		}
		customer.setCustType(customer.getCustType().trim());
		
        CustProfit custProfit = customerService.get(CustProfit.class, customer.getCode());
        if (custProfit == null) {
            customer.setPosition("");
            customer.setStdDesignFee(0.0);
            customer.setDesignFee(0.0);
            customer.setReturnDesignFee(0.0);
        } else {
            if (StringUtils.isNotBlank(custProfit.getPosition())) {
                DesignFeeSd designFeeSd =
                        designFeeSdService.getDesignFeeSdByDesignFee(Double.parseDouble(custProfit.getPosition()));
                
                customer.setPosition(designFeeSd != null ? designFeeSd.getPosition().trim() : "");
            }
            
            customer.setStdDesignFee(custProfit.getStdDesignFee() != null ? custProfit.getStdDesignFee() : 0.0);
            customer.setDesignFee(custProfit.getDesignFee() != null ? custProfit.getDesignFee() : 0.0);
            customer.setReturnDesignFee(custProfit.getReturnDesignFee() != null ? custProfit.getReturnDesignFee() : 0.0);

            if (customer.getBaseDisc() == null || customer.getBaseDisc() == 0.00) {
                customer.setBaseDisc(custProfit.getBaseDisc1()
                        + custProfit.getBaseDisc2()
                        + Math.round((1 - custProfit.getBaseDiscPer()) * customer.getBaseFeeDirct()));
            }
        }
		
		customer.setJzyjjs(customerService.getJzyjjs(customer.getCode()));
		CustType custType = null;
		if(StringUtils.isNotBlank(customer.getCustType())){
			custType= custTypeService.get(CustType.class, customer.getCustType());
		}
		
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1237);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0209", "修改签单时间");
		
		//将多个翻单员连起来显示 add by zb
		String againManDescr = this.custStakeholderService.getStakeholderLinkedByRole(customer.getCode(), "24");
		if (StringUtils.isNotBlank(againManDescr)) {
			customer.setAgainManDescr(againManDescr);
		}
		
		// 计算实物赠送金额
		CustGift custGift = new CustGift();
		custGift.setCustCode(customer.getCode());
		Page<Map<String, Object>> custGiftAll =
		        itemPlanService.getCustGiftAllJqGrid(new Page<Map<String,Object>>(), custGift);
		// 取存储过程返回结果集第8行也是最后一行中"zszeyh"的值
		double physicalGiftAmount = Double.valueOf((String) custGiftAll.getResult().get( (int) (custGiftAll.getTotalCount()-1) ).get("zszeyh"));
		
		if (customer.getGift() == null || customer.getGift().doubleValue() == 0) {
            customer.setGift(physicalGiftAmount);
        }
		
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_update")
			.addObject("customer", customer)
			.addObject("department1", department1)
			.addObject("department2", department2)
			.addObject("hasAuthByCzybh",hasAuthByCzybh)
			.addObject("taxRate",Double.parseDouble(xtcsService.getQzById("TaxRate")))
			.addObject("custType", custType)
			.addObject("physicalGiftAmount", physicalGiftAmount);
	}
	
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("跳转到施工合同信息录入页面");
		Customer customer = null;
		Department1 department1=new Department1();
		Department2 department2=new Department2();
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
			resetCustomer(customer);
		}else{
			customer = new Customer();
		}
		if("4"!=customer.getStatus()){
			// 修改施工工期没有匹配到规则为空的问题 modify by zb 
			Integer constructDay = customerService.getCustConsDay(id);
			if (null == constructDay) {
				customer.setConstructDay(customer.getContractDay());
			} else {
				customer.setConstructDay(constructDay);
			}
		}
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			department1=customerService.get(Department1.class, customer.getDepartment1());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			department2=customerService.get(Department2.class, customer.getDepartment2());
		}
		customer.setCustType(customer.getCustType().trim());
		customer.setPosition(customer.getPosition()==null?"":customer.getPosition().trim());
		customer.setJzyjjs(customerService.getJzyjjs(customer.getCode()));
		
		//将多个翻单员连起来显示 add by zb
		String againManDescr = this.custStakeholderService.getStakeholderLinkedByRole(customer.getCode(), "24");
		if (StringUtils.isNotBlank(againManDescr)) {
			customer.setAgainManDescr(againManDescr);
		}
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_Detail")
			.addObject("customer", customer)
			.addObject("department1", department1)
			.addObject("department2", department2)
			.addObject("taxRate",Double.parseDouble(xtcsService.getQzById("TaxRate")));
	}
	
	@RequestMapping("/goCountDisc")
	public ModelAndView goCountDisc(HttpServletRequest request, HttpServletResponse response, String id,String m_umState){
		logger.debug("跳转到施工合同信息录入页面");
		CustType custType=null;
		Customer customer=null;
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
			if(customer!=null){
				custType=customerService.get(CustType.class,customer.getCustType());
			} 	
		}
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_countDisc")
			.addObject("custCode", id)
			.addObject("m_umState", m_umState)	
		    .addObject("custType", custType)
		    .addObject("customer", customer);
	}
	
	/**
	 * 跳转到施工合同信息转施工
	 * @return
	 */
	@RequestMapping("/doGoZsg")
	public ModelAndView goZsg(HttpServletRequest request, HttpServletResponse response
			, String id,String status){
		logger.debug("跳转到施工合同信息录入页面");
		Customer customer = null;
		CustType custType = null;
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
			resetCustomer(customer);
			custType = customerService.get(CustType.class, customer.getCustType());
		}else{
			customer = new Customer();
			custType = new CustType();
		}
		Double riskFund = customerService.getRiskFund(id);//风控基金提示 add by zb on 20191028
		CustProfit custProfit = customerService.get(CustProfit.class, id);
		Date date =new Date();
		String designFeeType=customerService.getDesignFeeType(customer.getCustType(),customer.getPayType());
		customer.setPayType(customer.getPayType().trim());
		if(null != custProfit){
			customer.setPayType(custProfit.getPayType().trim());
		}
		customer.setPayeeCode(customerService.getPayeeCode(customer.getCode()));
		
	    // 获取最高优惠额度 已使用额度  {usedDisc=16353.85, DirectorMaxDiscAmount=888.8, DesignerMaxDiscAmount=999.9}
        Map<String, Object> map = customerService.getMaxDiscByCustCode(customer.getCode());
        if (map != null) {
            DecimalFormat df = new DecimalFormat( "0.00");  
            double designerMaxDiscAmount = Double.parseDouble(map.get("DesignerMaxDiscAmount").toString());
            double directorMaxDiscAmount = Double.parseDouble(map.get("DirectorMaxDiscAmount").toString());
            double usedDisc = Double.parseDouble(map.get("zskzsjyh").toString());
            
            
            map.put("RemainDisc", designerMaxDiscAmount + directorMaxDiscAmount - usedDisc);
        }
		
		Double hasPay = customerService.getCustomerHaspayByCode(id);
		
		Double discTokenAmount=0.0;
		DiscToken discToken = new DiscToken();
		if("3".equals(custType.getIsPartDecorate())){
			discToken.setStatus("3");
			discToken.setUseCustCode(customer.getCode());
			discTokenAmount = discTokenService.getDiscTokenAmountTotal(discToken);
		}else{
			discToken.setStatus("1");
			discToken.setCustCode(customer.getCode());
			discTokenAmount = discTokenService.getDiscTokenAmountTotal(discToken);
		}
		customer.setDiscTokenNo(discTokenService.getDiscTokenNo(discToken));
		customer.setDiscTokenAmount(discTokenAmount);
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_zsg")
			.addObject("customer", customer).addObject("designFeeType",designFeeType)
			.addObject("nowDate", date).addObject("custType",custType)
			.addObject("riskFund", riskFund).addObject("map", map)
			.addObject("hasPay", hasPay);
	}
	
	/**
	 * 跳转到施工合同信息结转
	 * @return
	 */
	@RequestMapping("/goJz")
	public ModelAndView goJz(HttpServletRequest request, HttpServletResponse response
			, String id,String status){
		logger.debug("跳转到施工合同信息录入页面");
		Customer customer = null;
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
		}else{
			customer = new Customer();
		}
		customer.setEndDate(new Date());
		if(!"1".equals(customer.getEndCode())&&!"2".equals(customer.getEndCode())
				&&!"6".equals(customer.getEndCode())&&!"5".equals(customer.getEndCode())){
			customer.setEndCode("1");
		}else {
			customer.setEndCode(customer.getEndCode().trim());
		}
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_jz")
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goResign")
	public ModelAndView goResign(HttpServletRequest request, HttpServletResponse response
			, String id){
		logger.debug("跳转到施工合同信息录入页面");
		Customer customer = null;
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
		}else{
			customer = new Customer();
		}
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_resign")
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goResignView")
	public ModelAndView goResignView(HttpServletRequest request, HttpServletResponse response
			, Customer customer){
		logger.debug("跳转到施工合同信息录入页面");

		
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_resignView")
			.addObject("customer", customer);
	}
	/**
	 * 跳转到风控基金明细页面
	 * @author	created by zb
	 * @date	2019-10-28--下午5:17:50
	 * @param request
	 * @param response
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/goRiskFundDetail")
	public ModelAndView goRiskFundDetail(HttpServletRequest request, HttpServletResponse response
			, String custCode){
		logger.debug("跳转到风控基金明细页面");
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_riskFundDetail")
			.addObject("custCode", custCode);
	}
	
	/**
	 * 退回
	 * @param request
	 * @param response
	 * @param custCode
	 * @param status
	 * @param endCode
	 */
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request, HttpServletResponse response,String custCode,String status,String endCode,String checkStatus){
		String notify="";
		Customer customer=new Customer();
		try {
			if("0".equals(checkStatus)){
				if(StringUtils.isBlank(custCode)||StringUtils.isBlank(status)||StringUtils.isBlank(endCode)){
					ServletUtils.outPrintFail(request, response, "存在客户编号、状态或结束原因为空,不能进行回退操作");
					return;
				}
				notify = customerService.getConBackNotify(custCode,status,endCode,"B");
				if(StringUtils.isNotBlank(notify)){
					ServletUtils.outPrintFail(request, response, notify);
					return;
				}else {
					ServletUtils.outPrintSuccess(request, response);
					return;
				}
			}else{
				//做回退操作
				customer = customerService.get(Customer.class, custCode);
				customer.setM_umState("B");
				customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				customer.setToStatus("3");
				Result result = customerService.doSghtxx_return(customer);
				if(result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,result.getInfo());
				}else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	/**
	 * 高级退回
	 * @param request
	 * @param response
	 * @param custCode
	 * @param status
	 * @param endCode
	 */
	@RequestMapping("/doGjReturn")
	public void doGjReturn(HttpServletRequest request, HttpServletResponse response,String custCode,String status,String endCode,String checkStatus){
		String notify="";
		Customer customer=new Customer();
		try {
			if("0".equals(checkStatus)){
				if(StringUtils.isBlank(custCode)||StringUtils.isBlank(status)||StringUtils.isBlank(endCode)){
					ServletUtils.outPrintFail(request, response, "存在客户编号、状态或结束原因为空,不能进行回退操作");
					return;
				}
				notify = customerService.getConBackNotify(custCode,status,endCode,"G");
				if(StringUtils.isNotBlank(notify)){
					if("此客户存在非取消状态的领料单!".equals(notify)||"此客户已经进行增减操作!".equals(notify)){
						ServletUtils.outPrintSuccess(request, response,notify);
						return;
					}else {
						ServletUtils.outPrintFail(request, response, notify);
						return;
					}
				}else {
					ServletUtils.outPrintSuccess(request, response);
					return;
				}
			}else{
				//做回退操作
				customer = customerService.get(Customer.class, custCode);
				customer.setM_umState("G");
				customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				customer.setToStatus("3");
				Result result = customerService.doSghtxx_return(customer);
				if(result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,result.getInfo());
				}else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
			
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}

	/**
	 * 转施工
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/doSaveZsg")
	public void doSaveZsg(HttpServletRequest request, HttpServletResponse response,Customer customer){
		Customer ct=new Customer();
		//JMessageClient client = getClient(EMPLOYEEAPP_TYPE);
		try {
			if(StringUtils.isNotBlank(customer.getCode())){
				ct=customerService.get(Customer.class, customer.getCode());
//				customer.setPayType(customerService.getDesignFeeType(ct.getCustType(),ct.getPayType())); modify by zb on 20190102
				customer.setM_umState("S");
				customer.setFromStatus(customer.getStatus());
				customer.setToStatus("4");
				customer.setConPhone(customer.getMobile1());
				customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result =	customerService.doSaveZsg(customer);
				if(result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"操作成功");
				} else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
				
				/*if(result.isSuccess() && ct.getGroupId() == 0l){
					AppGroup appGroup = new AppGroup();
					appGroup.setAddress(customer.getAddress());
					appGroup.setCustCode(customer.getCode());
					appGroup.setFlag("1");
					appGroup.setCustMobile(customer.getMobile1());
					CreateGroupResult res = appGroupManageService.doCreateGroupAndAddMember(client,appGroup);
					if (res != null) {
						ServletUtils.outPrintSuccess(request, response,result.getInfo());
					}else {
						ServletUtils.outPrintFail(request, response, "转施工成功，创建群组失败");
					}
				}else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}*/
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		
	}
	
	/**
	 * 结转
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/doSaveJz")
	public void doSaveJz(HttpServletRequest request, HttpServletResponse response,Customer customer){
		Customer ct=new Customer();
		try {
			if(StringUtils.isNotBlank(customer.getCode())){
				ct=customerService.get(Customer.class, customer.getCode());
				customer.setM_umState("J");
				customer.setToStatus("5");
				customer.setFromStatus(ct.getStatus());
				customer.setStatus(ct.getStatus());
				customer.setEndDate(new Date());
				customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result =	customerService.doSaveJz(customer);
				if(result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,result.getInfo());
				}else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doJzReturn")
	public void doJzReturn(HttpServletRequest request, HttpServletResponse response,String custCode,String status,String endCode,String checkStatus){
		String notify="";
		Customer customer=new Customer();
		try {
			if("0".equals(checkStatus)){
				if(StringUtils.isBlank(custCode)||StringUtils.isBlank(status)||StringUtils.isBlank(endCode)){
					ServletUtils.outPrintFail(request, response, "存在客户编号、状态或结束原因为空,不能进行回退操作");
					return;
				}
				notify = customerService.getJzReturnNotify(custCode,status,endCode);
				if(StringUtils.isNotBlank(notify)){
					ServletUtils.outPrintFail(request, response, notify);
					return;
				}else {
					ServletUtils.outPrintSuccess(request, response,"success");
					return;
				}
			}else{
				//做回退操作
				customer = customerService.get(Customer.class, custCode);
				customer.setM_umState("B");
				customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				customer.setToStatus("3");
				customer.setEndCode("0");
				customer.setEndDate(new Date());
				customer.setFromStatus(customerService.getOldStatus(custCode));
				customer.setEndRemark("");
				Result result = customerService.doJzReturn(customer);
				if(result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,result.getInfo());
				}else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doResign")
	public void doResign(HttpServletRequest request, HttpServletResponse response,Customer customer){
		Customer ct=new Customer();
		try {
			if(StringUtils.isNotBlank(customer.getCode())){
				ct=customerService.get(Customer.class, customer.getCode());
				ct.setRemarks(customer.getRemarks());
				ct.setFromStatus(ct.getStatus());
				ct.setToStatus("3");
				ct.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				
				Result result =	customerService.doReSign(ct);
				if(result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,result.getInfo());
				}else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}else {
				ServletUtils.outPrintFail(request, response, "操作失败,未读取到客户编号");
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	/**
	 * 跳转到查看施工合同信息页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getNotify")
    public String getNotify(HttpServletRequest request, HttpServletResponse response,
            String id, String status) {
	    
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(status)) {

            ServletUtils.outPrintSuccess(request, response,
                    customerService.getZsgStatus(id, status));
            
            return null;
        }
        ServletUtils.outPrintFail(request, response, "操作失败无客户编号或状态");
        return null;
    }
	
	@ResponseBody
	@RequestMapping("/getJzNotify")
	public String getJzNotify(HttpServletRequest request, HttpServletResponse response, String id, String status){
		if(StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(status)){
			ServletUtils.outPrintSuccess(request, response,customerService.getJzNotify(id, status) );
			return null;
		}
		ServletUtils.outPrintFail(request, response,"操作失败无客户编号或状态");
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/checkDesignFee")
	public String checkDesignFee(HttpServletRequest request,HttpServletResponse response,String custCode){
		if(StringUtils.isNotBlank(custCode)){
			return customerService.checkDesignFee(custCode);
		}else {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping("/getPosiDesignFee")
	public String getPosiDesignFee(HttpServletRequest request,HttpServletResponse response,String position, String custType){
		if(StringUtils.isNotBlank(position)){
			return customerService.getPosiDesignFee(position, custType);
		}else {
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/resignNotify")
	public String resignNotify(HttpServletRequest request,HttpServletResponse response,String custCode,String status,String endCode ){
		if(StringUtils.isNotBlank(custCode)&&StringUtils.isNotBlank(status)){
			ServletUtils.outPrintSuccess(request, response,customerService.resignNotify(custCode,status,endCode));
			return null;
		}
		ServletUtils.outPrintFail(request, response,"操作失败无客户编号或状态");
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/getResignNotify")
	public String getResignNotify(HttpServletRequest request,HttpServletResponse response,String custCode){
		Customer customer = new Customer();
		if(StringUtils.isNotBlank(custCode)){
			customer = customerService.get(Customer.class, custCode);
			ServletUtils.outPrintSuccess(request, response,customerService.resignNotify(custCode,customer.getStatus(),customer.getEndCode()));
			return null;
		}
		ServletUtils.outPrintFail(request, response,"操作失败无客户编号或状态");
		return null;
	}
	
	/**
	 * 保存施工合同信息
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("保存施工合同信息开始");
		Customer ct= new Customer();
		try{
			if(StringUtils.isNotBlank(customer.getCode())){
				ct=customerService.get(Customer.class, customer.getCode());
				if(StringUtils.isEmpty(customer.getPosition())){
					if(custTypeService.isFdlCust(ct.getCustType())){
						ServletUtils.outPrintFail(request, response, "非独立销售客户，设计费标准必选!");
						return;
					}
				}
				ct.setSignDate(customer.getSignDate());
				ct.setArea(customer.getArea());
				ct.setConstructType(customer.getConstructType());
				ct.setDesignFee(customer.getDesignFee());
				ct.setAchievDed(customer.getAchievDed());
				ct.setWallArea(customer.getWallArea());
				ct.setPerfPercCode(customer.getPerfPercCode());
				ct.setMeasureFee(customer.getMeasureFee());
				ct.setDrawFee(customer.getDrawFee());
				ct.setColorDrawFee(customer.getColorDrawFee());
				ct.setBaseDisc(customer.getBaseDisc());
				ct.setGift(customer.getGift());
				ct.setConstructDay(customer.getConstructDay());
				ct.setSoftBaseFee(customer.getSoftBaseFee());
				ct.setDiscRemark(customer.getDiscRemark());
				ct.setConstructRemark( customer.getConstructRemark());
				ct.setRemarks(customer.getRemarks());
				ct.setContainBase(customer.getContainBase());
				ct.setContainMain(customer.getContainMain());
				ct.setContainSoft(customer.getContainSoft());
				ct.setContainCup(customer.getContainCup());
				ct.setContainInt(customer.getContainInt());
				ct.setContainMainServ(customer.getContainMainServ());
				ct.setMarketFund(customer.getMarketFund());
				ct.setLastUpdate(new Date());
				ct.setLastUpdatedBy( this.getUserContext(request).getCzybh());
				ct.setSoftTokenNo(customer.getSoftTokenNo());
				ct.setSoftTokenAmount(customer.getSoftTokenAmount()==null ? 0 : customer.getSoftTokenAmount());
				ct.setTax(customer.getTax()==null ? 0 : customer.getTax());
				ct.setActionLog("Edit");
				ct.setPosition(customer.getPosition());
				ct.setStdDesignFee(customer.getStdDesignFee());
				ct.setReturnDesignFee(customer.getReturnDesignFee());
				ct.setContractDay(customer.getContractDay());
				System.out.println(ct.getPerfPercCode());
				this.customerService.update(ct);
				this.customerService.doUpdateContractFee(customer);
				
				ServletUtils.outPrintSuccess(request, response,"保存成功");
				
			}
		
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存施工合同信息失败");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getFourPay")
	@ResponseBody 	
	public String[] getQty(HttpServletRequest request,HttpServletResponse response,String custCode,String payType){
		logger.debug("ajax获取数据"); 
		Customer customer =null;
		if(StringUtils.isNotBlank(custCode)){
			customer=customerService.get(Customer.class, custCode);
		}
		String designFeeType=customerService.getDesignFeeType(customer.getCustType(),payType);

		Map<String , Object> fourPayMap=customerService.getFourPay(custCode, payType);
		if(fourPayMap != null){
			String[] str={fourPayMap.get("FirstPay").toString(),fourPayMap.get("SecondPay").toString(),
						fourPayMap.get("ThirdPay").toString(),fourPayMap.get("FourPay").toString(),designFeeType};
			
			return str;
		}else {
			return null;
		}
	}
	
	@RequestMapping("/getMatchConDay")
	public void getMatchConDay(HttpServletRequest request,HttpServletResponse response,String custCode){
		logger.debug("ajax获取数据"); 
		if(customerService.getMatchConDay(custCode)){
			ServletUtils.outPrintSuccess(request, response);
		}else{
			ServletUtils.outPrintFail(request, response,"");
		}
	}
	
	/**
	 *客户导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		customerService.findPageBySql_sghtxx(page, customer, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"施工合同管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doResignExcel")
	public void doResignExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		customerService.findPageBySql_viewResign(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"施工合同重签_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**设置code对应的名称
	 * @param customer
	 */
	private void resetCustomer(Customer customer){
		if (customer!=null){
			if (StringUtils.isNotBlank(customer.getBuilderCode())){
				Builder builder = builderService.get(Builder.class, customer.getBuilderCode());
				if (builder!=null){
					customer.setBuilderCodeDescr(builder.getDescr());
				}
			}
			if (StringUtils.isNotBlank(customer.getDesignMan())){
				Employee employee = employeeService.get(Employee.class, customer.getDesignMan());
				if (employee!=null){
					customer.setDesignManDescr(employee.getNameChi());
					customer.setDesignPhone(employee.getPhone());
					customer.setDepartment1(employee.getDepartment1());
					customer.setDepartment2(employee.getDepartment2());
				}
			}
			if (StringUtils.isNotBlank(customer.getAgainMan())){
				Employee employee = employeeService.get(Employee.class, customer.getAgainMan());
				if (employee!=null){
					customer.setAgainManDescr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(customer.getBusinessMan())){
				Employee employee = employeeService.get(Employee.class, customer.getBusinessMan());
				if (employee!=null){
					customer.setBusinessManDescr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(customer.getAgainMan())){
				Employee employee = employeeService.get(Employee.class, customer.getAgainMan());
				if (employee!=null){
					customer.setAgainManDescr(employee.getNameChi());
				}
			}else{
				customer.setAgainMan("");
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/getGiftDiscAmount")
	public Map<String, Object> getGiftDiscAmount(HttpServletRequest request,HttpServletResponse response,String custCode){
		if(StringUtils.isNotBlank(custCode)){
			return customerService.getGiftDiscAmount(custCode);
		}else {
			return null;
		}
	}
	
	@RequestMapping("/goDiscConfirmRemarks")
	public ModelAndView goCustGiftView(HttpServletRequest request,
			HttpServletResponse response,CustGift custGift) throws Exception {
		if(custGift.getpK() != null){
			custGift = customerService.get(CustGift.class, custGift.getpK());
		}
		Gift gift = null;
		if(custGift != null){
			gift = customerService.get(Gift.class, custGift.getGiftPK());	
		}
		if(gift != null ){
			custGift.setDiscPer(gift.getDiscPer());
			custGift.setGiftDescr( gift.getDescr());
		}
		return new ModelAndView("admin/design/customerSghtxx/customerSghtxx_discConfirmRemarks")
			.addObject("custGift",custGift);
	}
	
	/**
	 * 优惠审批说明
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/doDiscConfirmRemarks")
	public void doDiscConfirmRemarks(HttpServletRequest request, HttpServletResponse response,CustGift custGift){
		try {
			if(custGift.getpK()!=null){
				CustGift upCustGift=customerService.get(CustGift.class, custGift.getpK());
				upCustGift.setDiscConfirmRemarks(custGift.getDiscConfirmRemarks());
				upCustGift.setLastUpdate(new Date());
				upCustGift.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				customerService.update(upCustGift);
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	/**
	 * 自动转订单的，要判断客户付款必须有付款记录。没有付款记录进行提示
	 * @param request
	 * @param response
	 * @param Item
	 * @return
	 */
	@RequestMapping("/getAutoToOrderHasPay")
	@ResponseBody
	public JSONObject getAutoToOrderHasPay(HttpServletRequest request, HttpServletResponse response, 
			String custCode) {
		
		Customer customer = customerService.get(Customer.class, custCode);
		if(customer!=null){
			if(customer.getSetDate() == null && !"1".equals(customerService.hasPay(custCode))){
				return this.out("该客户没有付款记录，是否继续转施工？", true);
			}else {
				return null;
			} 
			
		}else{
			return null;
		}
	}

}
