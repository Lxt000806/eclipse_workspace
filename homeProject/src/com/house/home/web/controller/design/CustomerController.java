package com.house.home.web.controller.design;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.MtCustInfo;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.PhoneModifyDetail;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.BaseItemPlanService;
import com.house.home.service.design.CustPayService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.ItemPlanService;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

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
	/**
	 * ??????JqGrid????????????
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
		if (StringUtils.isBlank(customer.getStatus())){
			customer.setStatus("1,2,3,4");
		}
		if(StringUtils.isNotBlank(customer.getLaborFeeCustStatus())){
			customer.setStatus("4,5");
		}
		if(StringUtils.isNotBlank(customer.getCustWorkerCustStatus())){
			customer.setStatus("4");
			customer.setLaborFeeCustStatus("1,2,3,5");
		}
		customerService.findPageBySql(page, customer, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGridForOA")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridForOA(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);
		customerService.findPageForOABySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPrjProgJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPrjProgJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=getUserContext(request);
		customerService.findPrjProgPageBySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSoftNotAppQueryJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSoftNotAppQueryJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			customerService.findSoftNotAppQueryPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????ID????????????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCustomer")
	@ResponseBody
	public JSONObject getCustomer(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("?????????id??????", false);
		}
		Customer customer = customerService.get(Customer.class, id);
		if(customer == null){
			return this.out("??????????????????code="+id+"???????????????", false);
		}
		Map<String,Object> map = customerService.findMapByCode(id);
		BeanConvertUtil.mapToBean(map, customer);
		//resetCustomer(customer);
		return this.out(customer, true);
	}
	
	@RequestMapping("/getCustomerForOA")
	@ResponseBody
	public JSONObject getCustomerForOA(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("?????????id??????", false);
		}
/*		Customer customer = customerService.get(Customer.class, id);
		if(customer == null){
			return this.out("??????????????????code="+id+"???????????????", false);
		}
		Map<String,Object> map = customerService.findMapByCode(id);*/

		Customer customer = new Customer();
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);
		customer.setCode(id);
		customer.setAuthorityCtrl("1");
		customerService.findPageForOABySql(page, customer,uc);
		if(page.getResult() != null && page.getResult().size() > 0){
			return this.out(page.getResult().get(0), true);
		}else{
			return this.out("??????????????????code="+id+"???????????????", false);
		}
		//resetCustomer(customer);
	}
	
	/**
	 * ????????????
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
			customer.setStatus("1,2,3,4");
		}
		customer.setMeasureDate(new Date());
		return new ModelAndView("admin/design/customer/customer_list")
		.addObject("customer", customer)
		.addObject("today", DateUtil.format(new Date(), "yyyy-MM-dd"))
		.addObject("minDate", DateUtil.format(DateUtil.addDate(new Date(), -3), "yyyy-MM-dd"));
	}
	/**
	 * ??????Code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		// ???Status???LaborFeeCustStatus???????????????????????????????????????????????? --add by zb
		if (StringUtils.isNotBlank(customer.getStatus()) && 
			StringUtils.isNotBlank(customer.getLaborFeeCustStatus())) {
			return new ModelAndView("admin/design/customer/customer_code").addObject("customer", customer);
		} else {
			if (StringUtils.isBlank(customer.getStatus())){
				customer.setStatus("1,2,3,4");
			}
			if(StringUtils.isNotBlank(customer.getLaborFeeCustStatus())){
				customer.setStatus("4,5");
			}
			if(StringUtils.isNotBlank(customer.getCustWorkerCustStatus())){
				customer.setStatus("4");
				customer.setLaborFeeCustStatus("1,2,3,5");
			}
			return new ModelAndView("admin/design/customer/customer_code").addObject("customer", customer);
		}
	}
	
	
	@RequestMapping("/goCodeOA")
	public ModelAndView goCodeOA(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
			
		return new ModelAndView("admin/design/customer/customer_codeOA").addObject("customer", customer);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, String id,Integer mtCustInfoPK,String resrCustCode){
		logger.debug("???????????????????????????");
		Customer customer = null;
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
			customer.setMobile1("");
			customer.setMobile2("");
			resetCustomer(customer);
			customer.setCode(null);
			customer.setCrtDate(new Date());
		}else{
			customer = new Customer();
			customer.setCrtDate(new Date());
		}
		//??????????????????
		if(mtCustInfoPK!=null){
			MtCustInfo mtCustInfo=customerService.get(MtCustInfo.class, mtCustInfoPK);
			String layout="",gender="";
			if("??????".equals(mtCustInfo.getLayout())){
				layout="0";
			}else if("??????".equals(mtCustInfo.getLayout())){
				layout="1";
			}else if("??????".equals(mtCustInfo.getLayout())){
				layout="2";
			}
			if("???".equals(mtCustInfo.getGender().trim())){
				gender="M";
			}else{
				gender="F";
			}
			customer.setMtCustInfoPK(mtCustInfoPK);
			customer.setAddress(mtCustInfo.getAddress());
			customer.setSource("9");
			customer.setNetChanel("35");
			customer.setLayout(layout);
			customer.setArea(mtCustInfo.getArea());
			customer.setDescr(mtCustInfo.getCustDescr());
			customer.setGender(gender);
			customer.setMobile1(mtCustInfo.getCustPhone());
			customer.setStatus("1");
		}
		if(StringUtils.isNotBlank(resrCustCode)){
			ResrCust resrCust=customerService.get(ResrCust.class, resrCustCode);
			customer.setAddress(resrCust.getAddress());
			customer.setGender(resrCust.getGender());
			customer.setBuilderCode(resrCust.getBuilderCode());
			customer.setMobile1(resrCust.getMobile1());
			customer.setEmail2(resrCust.getEmail2());
			customer.setStatus("1");
			customer.setArea(resrCust.getArea());
			customer.setAddress(resrCust.getAddress());
			customer.setBuilderNum(resrCust.getBuilderNum());
			customer.setBusinessMan(resrCust.getBusinessMan());
			customer.setLayout(resrCust.getLayout());
			customer.setSource(resrCust.getSource());
			customer.setNetChanel(resrCust.getNetChanel());
			customer.setDescr(resrCust.getDescr());
			customer.setResrCustCode(resrCustCode);
			if(StringUtils.isNotBlank(resrCust.getBuilderCode())){
				Builder builder=customerService.get(Builder.class, resrCust.getBuilderCode());
				customer.setBuilderCodeDescr(builder.getDescr());
			}
			if(StringUtils.isNotBlank(resrCust.getBusinessMan())){
				Employee employee=customerService.get(Employee.class, resrCust.getBusinessMan());
				customer.setBusinessManDescr(employee.getNameChi());
			}
		}
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		czybm.setCustType(czybm.getCustType().trim());
		String authStr = this.getUserContext(request).getAuthStr();
		boolean hasAddress=false,hasSwt=false;
		if (this.getUserContext(request).isSuperAdmin() || authStr.indexOf("CUSTOMER_ADDRESS")>0){
			hasAddress = true;
		}
		if (this.getUserContext(request).isSuperAdmin() || authStr.indexOf("CUSTOMER_SWT")>0){
			hasSwt = true;
		}
		String hasAgainMan=czybmService.getHasAgainMan();
		
		List<CustType> custTypeList = custTypeService.loadAll(CustType.class);
		JSONArray json = JSONArray.fromObject(custTypeList);
		String custTypeJson = json.toString();
		return new ModelAndView("admin/design/customer/customer_save")
			.addObject("customer", customer)
			.addObject("hasAddress", hasAddress)
			.addObject("hasSwt", hasSwt)
			.addObject("czybm", czybm)
			.addObject("custTypeJson", custTypeJson)
			.addObject("hasAgainMan",hasAgainMan);
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("???????????????????????????");
		Customer customer = null;
		String flag = request.getParameter("flag");
		String unShowValue = "";
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
			if ("doc".equals(flag)){
				if (StringUtils.isBlank(customer.getDocumentNo())){
					customer.setDocumentNo(DateUtils.format(new Date(), "yyyy"));
				}
				return new ModelAndView("admin/design/customer/customer_documentNo")
					.addObject("customer", customer)
					.addObject("flag", flag);
			}
			resetCustomer(customer);
			if ("1".equals(customer.getStatus()) || "2".equals(customer.getStatus())){
				unShowValue = "3,4,5";
			}
			customer.setStatus(customer.getStatus());
		}else{
			customer = new Customer();
		}
		String hasAgainMan=czybmService.getHasAgainMan();
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		String authStr = this.getUserContext(request).getAuthStr();
		boolean hasAddress=false,hasSwt=false,hasBaseItemPlan=false,hasItemPlan=false,hasCustPay=false;
		if (this.getUserContext(request).isSuperAdmin() || authStr.indexOf("CUSTOMER_ADDRESS")>0){
			hasAddress = true;
		}
		if (this.getUserContext(request).isSuperAdmin() || authStr.indexOf("CUSTOMER_SWT")>0){
			hasSwt = true;
		}
		hasBaseItemPlan = baseItemPlanService.hasBaseItemPlan(id);
		hasItemPlan = itemPlanService.hasItemPlan(id);
		hasCustPay = custPayService.hasCustPay(id);
		List<CustType> custTypeList = custTypeService.loadAll(CustType.class);
		JSONArray json = JSONArray.fromObject(custTypeList);
		String custTypeJson = json.toString();
		customer.setCustType(customer.getCustType().trim());
		return new ModelAndView("admin/design/customer/customer_update")
			.addObject("customer", customer)
			.addObject("hasAddress", hasAddress)
			.addObject("hasSwt", hasSwt)
			.addObject("hasBaseItemPlan", hasBaseItemPlan)
			.addObject("hasItemPlan", hasItemPlan)
			.addObject("hasCustPay", hasCustPay)
			.addObject("czybm", czybm)
			.addObject("custTypeJson", custTypeJson)
			.addObject("flag", flag)
			.addObject("unShowValue", unShowValue)
			.addObject("hasAgainMan",hasAgainMan);
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("???????????????????????????");
		Customer customer = customerService.get(Customer.class, id);
		resetCustomer(customer);
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		String authStr = this.getUserContext(request).getAuthStr();
		boolean hasAddress=false,hasSwt=false,hasBaseItemPlan=false,hasItemPlan=false,hasCustPay=false;
		if (this.getUserContext(request).isSuperAdmin() || authStr.indexOf("CUSTOMER_ADDRESS")>0){
			hasAddress = true;
		}
		if (this.getUserContext(request).isSuperAdmin() || authStr.indexOf("CUSTOMER_SWT")>0){
			hasSwt = true;
		}
		hasBaseItemPlan = baseItemPlanService.hasBaseItemPlan(id);
		hasItemPlan = itemPlanService.hasItemPlan(id);
		hasCustPay = custPayService.hasCustPay(id);
		List<CustType> custTypeList = custTypeService.loadAll(CustType.class);
		JSONArray json = JSONArray.fromObject(custTypeList);
		String custTypeJson = json.toString();
		
		return new ModelAndView("admin/design/customer/customer_detail")
			.addObject("customer", customer)
			.addObject("hasAddress", hasAddress)
			.addObject("hasSwt", hasSwt)
			.addObject("hasBaseItemPlan", hasBaseItemPlan)
			.addObject("hasItemPlan", hasItemPlan)
			.addObject("hasCustPay", hasCustPay)
			.addObject("czybm", czybm)
			.addObject("custTypeJson", custTypeJson);
	}
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping("/goBusinessMan")
	public ModelAndView goBusinessMan(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("??????????????????????????????");
		Customer customer = customerService.get(Customer.class, id);
		resetCustomer(customer);
		
		return new ModelAndView("admin/design/customer/customer_businessMan")
			.addObject("customer", customer);
	}
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping("/goUpdateDesign")
	public ModelAndView goUpdateDesign(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("??????????????????????????????");
		Customer customer = customerService.get(Customer.class, id);
		resetCustomer(customer);
		
		return new ModelAndView("admin/design/customer/customer_designMan")
			.addObject("customer", customer);
	}
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("?????? ????????????");
		Builder builder=null;
		CustType custType=null;
		String builderAndBuilderNum="";
		if(StringUtils.isNotBlank(customer.getBuilderCode())){
			builder=builderService.get(Builder.class, customer.getBuilderCode());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			custType=custTypeService.get(CustType.class, customer.getCustType());
		}
		try{
			//?????????????????????????????????????????????
			if ("1".equals(customer.getSaleType())){
				Map<String, Object> existsAddress=customerService.getByAddress(customer.getAddress(), customer.getBusinessMan());
				if (existsAddress!=null){
					Xtdm xtdm = xtdmService.getByIdAndCbm("CUSTOMERSTATUS", existsAddress.get("Status").toString());
					String str = xtdm==null?"":xtdm.getNote();
					ServletUtils.outPrintFail(request, response, "????????????"+customer.getAddress()+"!\\n???????????????"+str+"?????????");
					return;
				}
			}													//????????????????????????4
			if(StringUtils.isNotBlank(customer.getBuilderNum())&&customerService.getIsDelivBuilder(customer.getBuilderCode())){
				if(!customerService.getIsExistBuilderNum(customer.getBuilderCode(),customer.getBuilderNum())){
					ServletUtils.outPrintFail(request, response, "??????????????????????????????"+customer.getBuilderNum());
					return;
				}
				
			}
			if ("4".equals(customerService.getAddressType(customer.getBuilderCode())) && "1".equals(custType.getIsAddAllInfo())){
				if(StringUtils.isNotBlank(customer.getBuilderNum())){
					if(builder!=null){
						 builderAndBuilderNum=builder.getDescr()+customer.getBuilderNum();
					}
					int i = customer.getAddress().indexOf(builderAndBuilderNum);
					if(i!=0){
						ServletUtils.outPrintFail(request, response, "?????????????????????????????????+?????????????????????");
						return;
					}
				}
			}
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			if("2".equals(customer.getStatus())&&null==customer.getVisitDate()){
				customer.setVisitDate(customer.getCrtDate());
			}
			String str = checkAddress(customer,request);
			if (StringUtils.isNotBlank(str)){
				ServletUtils.outPrintFail(request, response, str);
				return;
			}
			Result result = customerService.saveForProc(customer);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, "????????????????????????");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("??????????????????");
		String flag = request.getParameter("flag");
		Builder builder=null;
		CustType custType=null;
		String builderAndBuilderNum="";
		if(StringUtils.isNotBlank(customer.getBuilderCode())){
			builder=builderService.get(Builder.class, customer.getBuilderCode());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			custType=custTypeService.get(CustType.class, customer.getCustType());
		}
		try{													//??????????????????4
			if(StringUtils.isNotBlank(customer.getBuilderNum())&&customerService.getIsDelivBuilder(customer.getBuilderCode())){
				if(!customerService.getIsExistBuilderNum(customer.getBuilderCode(),customer.getBuilderNum())){
					ServletUtils.outPrintFail(request, response, "??????????????????????????????"+customer.getBuilderNum());
					return;
				}
				
			}
			if ("4".equals(customerService.getAddressType(customer.getBuilderCode())) && "1".equals(custType.getIsAddAllInfo())){
				if(StringUtils.isNotBlank(customer.getBuilderNum())){
					if(builder!=null){
						 builderAndBuilderNum=builder.getDescr()+customer.getBuilderNum();
					}
					int i = customer.getAddress().indexOf(builderAndBuilderNum);
					if(i!=0){
						ServletUtils.outPrintFail(request, response, "?????????????????????????????????+?????????????????????");
						return;
					}
				}
			}
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			if ("doc".equals(flag)){//???????????????
				if(StringUtils.isNotBlank(customer.getDocumentNo())){
					Customer cust = customerService.getByDocumentNo(customer.getDocumentNo());
					if (cust!=null && !cust.getCode().equals(customer.getCode())){
						ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????????????????");
					}else{
						Customer ct = customerService.get(Customer.class, customer.getCode());
						String hasAgainMan=czybmService.getHasAgainMan();
						if("0".equals(hasAgainMan)){
							if("6".equals(customer.getSource())){
								ct.setAgainMan("");
							}
						}
						if (ct!=null){
							ct.setDocumentNo(customer.getDocumentNo());
						}
						customerService.update(ct);
						ServletUtils.outPrintSuccess(request, response);
					}
				}else{
					ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
				}
				
			}else{
				//?????????????????????????????????????????????
				if ("1".equals(customer.getSaleType())){
					Map<String, Object> existsAddress=customerService.getByAddress(customer.getAddress(), customer.getBusinessMan());
					if (existsAddress!=null && !existsAddress.get("Code").toString().equals(customer.getCode())){
						Xtdm xtdm = xtdmService.getByIdAndCbm("CUSTOMERSTATUS", existsAddress.get("Status").toString());
						String str = xtdm==null?"":xtdm.getNote();
						ServletUtils.outPrintFail(request, response, "????????????"+customer.getAddress()+"!\\n???????????????"+str+"?????????");
						return;
					}
				}
				String str = checkAddress(customer,request);
				if (StringUtils.isNotBlank(str)){
					ServletUtils.outPrintFail(request, response, str);
					return;
				}
				Customer ct = customerService.get(Customer.class, customer.getCode());
				if (ct!=null){
					customer.setOldStatus(ct.getStatus());
				}
				String hasAgainMan=czybmService.getHasAgainMan();
				if("0".equals(hasAgainMan)){
					if("6".equals(customer.getSource())){
						customer.setAgainMan("");
					}
				}
				Result result = customerService.updateForProc(customer);
				if ("1".equals(result.getCode())){
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	/**
	 * ???????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateBusinessMan")
	public void doUpdateBusinessMan(HttpServletRequest request, 
	        HttpServletResponse response, String code,
	        String businessMan, String againMan) {
		logger.debug("?????????????????????");
		try{
			Customer customer = customerService.get(Customer.class, code);
			if (!"1".equals(customer.getStatus()) && !"2".equals(customer.getStatus())){
				ServletUtils.outPrintFail(request, response, "????????????1???????????????2???????????????????????????");
				return;
			}
			if (custStakeholderService.getCount(code, "01")>1){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????????????????");
				return;
			}
			
			if (StringUtils.isBlank(againMan)
			        && StringUtils.isNotBlank(customer.getAgainMan())) {
                ServletUtils.outPrintFail(request, response, "????????????????????????????????????");
                return;
            }
			
			if (StringUtils.isNotBlank(againMan)
			        && StringUtils.isBlank(customer.getAgainMan())) {
			    ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
                return;
            }
			
			Result result = customerService.doUpdateBusinessMan(customer,
			        businessMan, againMan, getUserContext(request));


			ServletUtils.outPrintSuccess(request, response, result.getInfo());
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "?????????????????????");
		}
	}
	/**
	 * ???????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateDesign")
	public void doUpdateDesign(HttpServletRequest request, HttpServletResponse response, String code, String designMan){
		logger.debug("?????????????????????");
		try{
			Customer customer = customerService.get(Customer.class, code);
			if (!"1".equals(customer.getStatus()) && !"2".equals(customer.getStatus())){
				ServletUtils.outPrintFail(request, response, "????????????1???????????????2???????????????????????????");
				return;
			}
			if (custStakeholderService.getCount(code, "00")>1){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????????????????");
				return;
			}
			CustStakeholder custStakeholder = new CustStakeholder();
			custStakeholder.setM_umState("M");
			custStakeholder.setIsRight(0);
			custStakeholder.setCustCode(code);
			custStakeholder.setRole("00");
			custStakeholder.setEmpCode(designMan);
			custStakeholder.setLastUpdatedBy(getUserContext(request).getCzybh());
			custStakeholder.setPk(custStakeholderService.getPkByCustCodeAndRole(code, "00"));
			custStakeholder.setExpired("F");
			Result result = custStakeholderService.updateForProc(custStakeholder);
			if ("1".equals(result.getCode())){
				//??????????????????
				CustCon custCon=new CustCon();
				custCon.setType("2");
				custCon.setConDate(new Date());
				custCon.setConMan(this.getUserContext(request).getCzybh());
				custCon.setCustCode(customer.getCode());
				custCon.setRemarks("???????????????");
				custCon.setExpired("F");
				custCon.setActionLog("ADD");
				custCon.setLastUpdate(new Date());
				custCon.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				this.customerService.save(custCon);
				
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "?????????????????????");
		}
	}
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????????????????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Customer customer = this.customerService.get(Customer.class, deleteId);
				customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = customerService.deleteForProc(customer);
				if ("1".equals(result.getCode())){
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		}
		logger.debug("???????????? IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}
	
	/**
	 *????????????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		customerService.findPageBySql(page, customer, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**??????code???????????????
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
			if(StringUtils.isNotBlank(customer.getProjectMan())){
				Employee employee = employeeService.get(Employee.class, customer.getProjectMan());
				if(employee != null){
					customer.setProjectManDescr(employee.getNameChi());
					if(StringUtils.isNotBlank(employee.getDepartment1())){
						Department1 department1 = employeeService.get(Department1.class, employee.getDepartment1());
						if(department1 != null){
							customer.setDept1Descr(department1.getDesc1());
						}
					}else{
						customer.setDept1Descr("");
					}
					if(StringUtils.isNotBlank(employee.getDepartment2())){
						Department2 department2 = employeeService.get(Department2.class, employee.getDepartment2());
						if(department2 != null){
							customer.setDept2Descr(department2.getDesc1());
						}
					}else{
						customer.setDept2Descr("");
					}
					customer.setProjectManPhone(employee.getPhone());
				}
			}
			if(StringUtils.isNotBlank(customer.getCustType())){
				CustType custType = employeeService.get(CustType.class, customer.getCustType());
				if(custType != null){
					customer.setIsAddAllInfo(custType.getIsAddAllInfo());
				}else{
					customer.setIsAddAllInfo("");
				}
			}
		}
	}
	/**???????????????????????????????????????????????????
	 * @param customer
	 * @param rule
	 * @return
	 */
	public String checkAddress(Customer customer, HttpServletRequest request){
		return customerService.checkAddress(customer,this.getUserContext(request));
	}
	
	@RequestMapping("/getIsDelivBuilder")
	@ResponseBody
	public boolean getIsDelivBuilder(HttpServletRequest request,HttpServletResponse response,
			String builderCode){
		logger.debug("ajax????????????");  
		return customerService.getIsDelivBuilder(builderCode);
	}
	
	@RequestMapping("/getIsExistBuilderNum")
	@ResponseBody
	public boolean getIsExistBuilderNum(HttpServletRequest request,HttpServletResponse response,
			String builderCode,String builderNum){
		logger.debug("ajax????????????");  
		return customerService.getIsExistBuilderNum(builderCode, builderNum);
	}
	
	@RequestMapping("/isNeedPlanEndDate")
	@ResponseBody
	public String isNeedPlanEndDate(HttpServletRequest request,HttpServletResponse response,
			String custCode){
		logger.debug("ajax????????????");  
		return customerService.isNeedPlanSendDate(custCode);
	}
	
	@RequestMapping("/getShouldBalance")
	@ResponseBody
	public Map<String, Object> getShouldBalance(HttpServletRequest request,HttpServletResponse response,
			String custCode){
		logger.debug("ajax????????????");  
		return customerService.getShouldBanlance(custCode);
	}
	
	
	/**
	 * ????????????
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @author ?????????
	 */
	@RequestMapping("/doMeasure")
	public void doMeasure(HttpServletRequest request, HttpServletResponse response, Customer customer) {
	    logger.debug("??????????????????");
	    	    
	    Result result = customerService.doMeasure(request, response, customer, getUserContext(request));
	    
	    if (result.isSuccess()) {
	        ServletUtils.outPrintSuccess(request, response, result.getInfo());
	    } else {
	        ServletUtils.outPrintFail(request, response, result.getInfo());
        }
        
	}

	@RequestMapping("/doVisit")
	public void doVisit(HttpServletRequest request, HttpServletResponse response, Customer ct){
		logger.debug("??????????????????");
		Customer customer = customerService.get(Customer.class,ct.getCode());
		customer.setComeTimes(1);
		customer.setVisitDate(ct.getVisitDate());
		customer.setStatus("2");
		customer.setLastUpdate(new Date());
		customerService.update(customer);
		ServletUtils.outPrintSuccess(request, response,"??????????????????");
	}
	
	/**
	 * ?????????????????????????????? add by cjm 2019-07-24
	 * @return
	 */
	@RequestMapping("/goModifyPhone")
	public ModelAndView goModifyPhone(HttpServletRequest request, HttpServletResponse response,String code){
		logger.debug("??????????????????????????????");
		Customer customer = customerService.get(Customer.class, code);
		
		return new ModelAndView("admin/design/customer/customer_modifyPhone")
			.addObject("customer", customer);
	}
	
	/**
	 *  ??????????????? add by cjm 2019-07-24
	 */
	@RequestMapping("/doModifyPhone")
	public void doModifyPhone(HttpServletRequest request, HttpServletResponse response,String code,String newPhone){
		logger.debug("?????????????????????");
		Customer customer = customerService.get(Customer.class, code);
		PhoneModifyDetail pmd = new PhoneModifyDetail();
		pmd.setCustCode(code);
		pmd.setStatus(customer.getStatus());
		pmd.setOldPhone(customer.getMobile1());
		pmd.setNewPhone(newPhone);
		pmd.setDate(new Date());
		pmd.setCzybh(getUserContext(request).getCzybh());
		pmd.setModule("1");
		customerService.save(pmd);
		customer.setMobile1(newPhone);
		customer.setLastUpdate(pmd.getDate());
		customerService.update(customer);
		ServletUtils.outPrintSuccess(request, response,"?????????????????????");
	}
	
	/**
	 * ?????????????????????????????? add by cjm 2019-07-24
	 * @return
	 */
	@RequestMapping("/goModifyPhoneQuery")
	public ModelAndView goModifyPhoneQuery(HttpServletRequest request, HttpServletResponse response,Customer customer){
		logger.debug("??????????????????????????????");
		
		return new ModelAndView("admin/design/customer/customer_modifyPhoneQuery")
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goModPhoneJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goModPhoneJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		customerService.findPageBySql_modifyPhone(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????????????????????????? add by cjm 2019-08-02
	 * @return
	 */
	@RequestMapping("/goModifyStat")
	public ModelAndView goModifyStat(HttpServletRequest request, HttpServletResponse response,String code){
		logger.debug("???????????????????????????");
		Customer customer = customerService.get(Customer.class, code);
		
		return new ModelAndView("admin/design/customer/customer_modifyStat")
			.addObject("customer", customer);
	}
	
	/**
	 *  ??????????????? add by cjm 2019-08-02
	 */
	@RequestMapping("/doModifyStat")
	public void doModifyStat(HttpServletRequest request, HttpServletResponse response,String code,String status){
		logger.debug("??????????????????");
		Customer customer = customerService.get(Customer.class, code);
		if("2".equals(status)){
			customer.setSetDate(null);
		}
		if("1".equals(status)){
			customer.setComeTimes(0);
			customer.setSetDate(null);
			customer.setVisitDate(null);
		}
		customer.setStatus(status);
		customer.setLastUpdate(new Date());
		customerService.update(customer);
		ServletUtils.outPrintSuccess(request, response,"??????????????????");
	}
	
	@RequestMapping("/doExcelForModPhoneQuery")
	public void doExcelForModPhoneQuery(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		customerService.findPageBySql_modifyPhone(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/isNeedAgainMan")
	@ResponseBody
	public Map<String,Object> isNeedAgainMan(HttpServletRequest request,
			HttpServletResponse response,String code) throws Exception {
		return customerService.isNeedAgainMan(code);
	}
	
	@RequestMapping("/hasPay")
	@ResponseBody
	public String hasPay(HttpServletRequest request,
			HttpServletResponse response,String code) throws Exception {
		return customerService.hasPay(code);
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSoftSalesCust")
	public void doSoftSalesCust(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("?????? ????????????");
		try{
			Result result=null;
			if (StringUtils.isBlank(customer.getAddress())) {
				customer.setAddress("???????????????"+customer.getDescr());
			}
			if ("M".equals(customer.getM_umState())){
				Customer ct = customerService.get(Customer.class, customer.getCode());
				ct.setM_umState("M");
				ct.setDescr(customer.getDescr());
				ct.setAddress(customer.getAddress());
				ct.setMobile1(customer.getMobile1());
				ct.setBuilderCode(customer.getBuilderCode());
				ct.setLayout(customer.getLayout());
				ct.setBusinessMan(customer.getBusinessMan());
				ct.setDesignMan(customer.getDesignMan());
				ct.setAgainMan(customer.getAgainMan());
				ct.setLastUpdatedBy(getUserContext(request).getCzybh());
				ct.setOldStatus(ct.getStatus());
				result = customerService.updateForProc(ct);
			}else{
				customer.setLastUpdatedBy(getUserContext(request).getCzybh());
				customer.setCustType("2");
				customer.setStatus("2");
				customer.setVisitDate(new Date());
				customer.setCrtDate(new Date());
				customer.setArea(0);
				customer.setSource("1");
				customer.setGender("F");
				customer.setRemarks("");
				customer.setSaleType("1");
                result = customerService.saveForProc(customer);
			}
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, "????????????????????????",result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????
	 *
	 */
	@RequestMapping("/goSetAgainType")
	public ModelAndView goSetAgainType(HttpServletRequest request, HttpServletResponse response,String code){
		logger.debug("?????????????????????");
		Customer customer = customerService.get(Customer.class, code);
		
		return new ModelAndView("admin/design/customer/customer_setAgainType")
			.addObject("customer", customer);
	}
	
	/**
	 * ??????????????????
	 *
	 */
	@RequestMapping("/doAgainType")
	public void doAgainType(HttpServletRequest request, HttpServletResponse response,String code,String againType){
		logger.debug("??????????????????");
		Customer customer = customerService.get(Customer.class, code);
		customer.setAgainType(againType);
		customer.setLastUpdate(new Date());
		customer.setLastUpdatedBy(getUserContext(request).getCzybh());
		customerService.update(customer);
		ServletUtils.outPrintSuccess(request, response,"??????????????????");
	}
	
	
}
