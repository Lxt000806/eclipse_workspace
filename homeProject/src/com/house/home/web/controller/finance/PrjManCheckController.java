package com.house.home.web.controller.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PrjCheck;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.finance.PrjManCheckService;

@Controller
@RequestMapping("/admin/prjManCheck")
public class PrjManCheckController extends BaseController { 
	@Autowired
	PrjManCheckService prjManCheckService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustTypeService custTypeService;

	private void resetPrjCheck(PrjCheck prjCheck){
		if (prjCheck!=null){
			if (StringUtils.isNotBlank(prjCheck.getCustCode())){
				Customer customer = customerService.get(Customer.class, prjCheck.getCustCode());
				if (customer!=null){
					prjCheck.setCustDescr(customer.getDescr());
					prjCheck.setAddress(customer.getAddress());
					prjCheck.setArea(customer.getArea());
					prjCheck.setMainSetFee(customer.getMainSetFee());
					prjCheck.setLongFee(customer.getLongFee());
					prjCheck.setCheckStatus(customer.getCheckStatus());
					if (StringUtils.isNotBlank(customer.getCustType())){
						CustType custType = custTypeService.get(CustType.class, customer.getCustType());
						if (custType!=null){
							prjCheck.setCustTypeType(custType.getType());
							prjCheck.setPrjCtrlType(custType.getPrjCtrlType());
						}
					}
				}
			}
			if (StringUtils.isNotBlank(prjCheck.getAppCzy())){
				Employee employee = employeeService.get(Employee.class, prjCheck.getAppCzy());
				if (employee!=null){
					prjCheck.setAppCZYDescr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(prjCheck.getConfirmCzy())){
				Employee employee = employeeService.get(Employee.class, prjCheck.getConfirmCzy());
				if (employee!=null){
					prjCheck.setConfirmCZYDescr(employee.getNameChi());
				}
			}	
		
		}
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param giftApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		prjManCheckService.findPageBySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	//质保金汇总数据
	@RequestMapping("/goJqGrid_totalQualityFee")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid_totalQualityFee(HttpServletRequest request,
			HttpServletResponse response,PrjCheck prjCheck ) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjManCheckService.findPageBySql_totalQualityFee(page, prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	//质保金明细
	@RequestMapping("/goJqGrid_qualityDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridgo_qualityDetail(HttpServletRequest request,
			HttpServletResponse response,PrjCheck prjCheck ) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjManCheckService.findPageBySql_qualityDetail(page, prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 跳转到批量打印页面
	 * @return
	 */            
	@RequestMapping("/goQPrint")
	public ModelAndView goQPrint(HttpServletRequest request, HttpServletResponse response, 
			Customer customer){
		logger.debug("跳转到批量打印页面");
		
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_qPrint").addObject("customer",customer);
	}
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		prjManCheckService.findPageBySql(page, customer,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"项目经理结算_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doExcel_totalQualityFee")
	public void doDetailExcel(HttpServletRequest request, 
			HttpServletResponse response, PrjCheck prjCheck){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjManCheckService.findPageBySql_totalQualityFee(page, prjCheck);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"质保金汇总_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * PrjManCheck列表
	 * @param request
	 * @param response 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, 
			HttpServletResponse response,  Customer customer) throws Exception {
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_list").addObject("customer",customer);	
	}
	/**
	 * 检查能否进行结算数据生成，完成，回退 跳转 操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/goVerifyCheck")
	public void goVerifyCheck(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("VerifyCheck检查修改开始");	
		if(StringUtils.isBlank(customer.getCode())){
			ServletUtils.outPrintFail(request, response, "客户编号不能为空,不能进行结算");
			return;
		};
		try {
			Customer cust = customerService.get(Customer.class, customer.getCode());
			if ("A".equals(customer.getM_umState())){
				if (StringUtils.isNotBlank(cust.getCustType())){
					CustType custType = custTypeService.get(CustType.class, cust.getCustType().trim());
					if (custType!=null){
						if(!"1".equals(custType.getType())){
							ServletUtils.outPrintFail(request, response, "只能对非套餐客户进行预扣录入操作");
							return;	
						}
						
					}
				}
			}	
			if("G".equals(customer.getM_umState())){
				if(!"2".equals(cust.getCheckStatus())){
					ServletUtils.outPrintFail(request, response, "只能对客户已结算的记录进行结算操作");
					return;
				};	
				if(!prjManCheckService.isCheckWorkCost(cust.getCode())){
					ServletUtils.outPrintFail(request, response, "客户【"+cust.getDescr()+"】存在未审核人工单，不允许结算");
					return;				
				}	
				if(!prjManCheckService.isCheckWorkCost(cust.getCode())){
					ServletUtils.outPrintFail(request, response, "客户【"+cust.getDescr()+"】存在未审核人工单，不允许结算");
					return;				
				}
			}
			if ("GC".equals(customer.getM_umState())){
				if(!"2".equals(cust.getCheckStatus())){
					ServletUtils.outPrintFail(request, response, "只能对客户已结算的记录进行结算完成操作");
					return;
				};	
			}
			if ("GB".equals(customer.getM_umState())){
				if(!"3".equals(cust.getCheckStatus())){
					ServletUtils.outPrintFail(request, response, "只能对项目经理要领的记录进行结算回退操作");
					return;
				};	
			}
			
			ServletUtils.outPrintSuccess(request, response, "可以进行操作", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法进行该操作");
		}
	}
	/**
	 * 检查能否进行材料升级标记操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/goVerifyItemUp")
	public void goVerifyItemUp(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("VerifyItemUp检查修改开始");	
		if(StringUtils.isBlank(customer.getCode())){
			ServletUtils.outPrintFail(request, response, "客户编号不能为空,不能进行材料升级标记操作");
			return;
		};
		try {
			Customer cust = customerService.get(Customer.class, customer.getCode());
			if(!"2".equals(cust.getCheckStatus())){
				ServletUtils.outPrintFail(request, response, "只有客户结算状态为【客户已结算】才能进行材料升级标记操作");
				return;
			};	
			if (StringUtils.isNotBlank(cust.getCustType())){
				CustType custType = custTypeService.get(CustType.class, cust.getCustType().trim());
				if (custType!=null){
					if(!"2".equals(custType.getPrjCtrlType())){
						ServletUtils.outPrintFail(request, response, "只能对公式发包客户进行材料升级标记操作");
						return;	
					}
					
				}
			}
			if(!customer.getIsItemUp().equals(cust.getIsItemUp())){
				ServletUtils.outPrintFail(request, response, "【材料免费升级】列发生变化，请刷新数据");
				return;
			}
			ServletUtils.outPrintSuccess(request, response, "可以进行修改", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法进行修改");
		}
	}
	/**
	 * 结算
	 * @param request
	 * @param response
	 * @param customer
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response,PrjCheck custPrjCheck){
		logger.debug("结算");	
		PrjCheck prjCheck=null;
		JSONArray arryTypeDescr=null;
		JSONArray arryWorkType1Descr=null;
		String str="";
		try {
			if (StringUtils.isNotBlank(custPrjCheck.getCustCode())){
			 	UserContext uc = getUserContext(request);
			    prjCheck=prjManCheckService.get(PrjCheck.class, custPrjCheck.getCustCode());
				if(prjCheck==null){
					prjCheck=new PrjCheck();
					prjCheck.setCustCode(custPrjCheck.getCustCode());
					prjCheck.setBaseChg(0.0);
					prjCheck.setBaseCtrlAmt(0.0);
					prjCheck.setCost(0.0);
					prjCheck.setWithHold(0.0);
					prjCheck.setMainCoopFee(0.0);
					prjCheck.setRecvFee(0.0);
					prjCheck.setQualityFee(0.0);
					prjCheck.setAccidentFee(0.0);
					prjCheck.setMustAmount(0.0);
					prjCheck.setRealAmount(0.0);
					prjCheck.setAllSetMinus(0.0);
					prjCheck.setAllSetAdd(0.0);
					prjCheck.setAllItemAmount(0.0);
					prjCheck.setAllManageFeeBase(0.0);
					prjCheck.setUpgWithHold(0.0);
					prjCheck.setBaseCost(0.0);
					prjCheck.setMainCost(0.0);
				}
				prjCheck.setM_umState(custPrjCheck.getM_umState());
				prjCheck.setBaseFeeDirct(custPrjCheck.getBaseFeeDirct());
				prjCheck.setMainAmount(custPrjCheck.getMainAmount());
				prjCheck.setBaseFee(custPrjCheck.getBaseFee());
				prjCheck.setChgMainAmount(custPrjCheck.getChgMainAmount());
				prjCheck.setProjectCtrlAdj(custPrjCheck.getProjectCtrlAdj());
				prjCheck.setHasCheckCZY(prjCheck.getAppCzy());
				System.out.println(custPrjCheck.getDesignFixDutyAmount());
				prjCheck.setDesignFixDutyAmount(custPrjCheck.getDesignFixDutyAmount());
				if("G".equals(prjCheck.getM_umState())){
					prjCheck.setAppCzy(uc.getEmnum());
					prjCheck.setDate(DateUtil.getNow());
				}
				if("GC".equals(prjCheck.getM_umState())){
					prjCheck.setConfirmCzy(uc.getEmnum());
					prjCheck.setConfirmDate(DateUtil.getNow());
				}
				resetPrjCheck(prjCheck);
				if ("2".equals(prjCheck.getPrjCtrlType())){
					prjCheck.setMainCost(custPrjCheck.getMainCost());
					str="_sdj";	
				}
				//处理来自项目经理提成领取的请求 add by gdf 
				if ("1".equals(custPrjCheck.getPrjNo())) {
					str="";
					prjCheck.setPrjNo(custPrjCheck.getPrjNo());
					Map<String, Object>  map= prjManCheckService.findBySql(custPrjCheck.getCustCode());
					Iterator entries = map.entrySet().iterator(); 
					while (entries.hasNext()) { 
					  Map.Entry entry = (Map.Entry) entries.next(); 
					  System.out.println(entry);
					}
					Double mainAmount =(Double) map.get("MainAmount");
					Double chgMainAmount =(Double) map.get("ChgMainAmount");
					Double accidntFee = (Double) map.get("AccidentFee");
					Double baseFee_birct = (Double) map.get("BaseFee_Dirct");
					Double baseFee = (Double) map.get("BaseFee");
					prjCheck.setMainAmount(mainAmount);
					prjCheck.setChgMainAmount(chgMainAmount);
					prjCheck.setAccidentFee(accidntFee);
					prjCheck.setBaseFeeDirct(baseFee_birct);
					prjCheck.setBaseFee(baseFee);
				}
			}
			List<Map<String,Object>>  listTypeDescr=prjManCheckService.getTypeDescr();
			ArrayList<String> list = new ArrayList<String>();
			for (Map<String, Object> m :listTypeDescr)  
		    {  
				for (String k : m.keySet())  
				{  
					list.add(((String) m.get(k)).trim());
				}  
		    }
			arryTypeDescr = JSONArray.fromObject(list); 
			List<Map<String,Object>>  listWorkType1Descr=prjManCheckService.getWorkType1Descr();
			ArrayList<String> workType1List = new ArrayList<String>();

			for (Map<String, Object> m :listWorkType1Descr)  
		    {  
				for (String k : m.keySet())  
				{  
					workType1List.add(((String) m.get(k)).trim());
				}  
		    }
			 arryWorkType1Descr = JSONArray.fromObject(workType1List); 	
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "进入结算页面失败!");
		}
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_check"+str)
			.addObject("prjCheck", prjCheck)
			.addObject("arryTypeDescr", arryTypeDescr)
			.addObject("arryWorkType1Descr", arryWorkType1Descr);
	}
	/**
	 * 预扣录入
	 * @param request
	 * @param response
	 * @param PrjCheck
	 */
	@RequestMapping("/goPreInput")
	public ModelAndView goComeGive(HttpServletRequest request, HttpServletResponse response,PrjCheck custPrjCheck){
		logger.debug("预扣录入");	
		PrjCheck prjCheck=null;
		try {
			if (StringUtils.isNotBlank(custPrjCheck.getCustCode())){
				UserContext uc = getUserContext(request);
				prjCheck=prjManCheckService.get(PrjCheck.class, custPrjCheck.getCustCode());
				if(prjCheck==null){
					prjCheck=new PrjCheck();
					prjCheck.setCustCode(custPrjCheck.getCustCode());
				}
				prjCheck.setM_umState("A"); 
				prjCheck.setBaseFeeDirct(custPrjCheck.getBaseFeeDirct());
				prjCheck.setMainAmount(custPrjCheck.getMainAmount());
				prjCheck.setBaseFee(custPrjCheck.getBaseFee());
				prjCheck.setChgMainAmount(custPrjCheck.getChgMainAmount());
				prjCheck.setDate(DateUtil.getNow());
				prjCheck.setHasCheckCZY(prjCheck.getAppCzy());
				resetPrjCheck(prjCheck);
			}	
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "进入预扣录入页面失败!");
		}
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_preInput")
			.addObject("prjCheck", prjCheck);	
	}
	/**
	 * 跳转到升级扣项
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/goItemUp")
	public ModelAndView goItemUp(HttpServletRequest request, HttpServletResponse response,Customer customer){
		logger.debug("跳转到升级扣项");	
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_itemUp").addObject("customer",customer);
	}
	//项目经理结算明细
	@RequestMapping("/goJqGridPrjCheckDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridPrjCheckDetail(HttpServletRequest request,
			HttpServletResponse response,PrjCheck prjCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjManCheckService.findPageBySqlPrjCheckDetail(page, prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 检查能否进行结算数据生成，完成，回退操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/goVerifyPrjCheck")
	public void goVerifyPrjCheck(HttpServletRequest request, HttpServletResponse response, PrjCheck prjCheck){
		logger.debug("VerifyPrjCheck检查修改开始");
		try {
			if(StringUtils.isBlank(prjCheck.getCustCode())){
				ServletUtils.outPrintFail(request, response, "客户编号不能为空,不能进行结算");
				return;
			};
			if("GC".equals(prjCheck.getM_umState())){
				PrjCheck newPrjCheck = prjManCheckService.get(PrjCheck.class, prjCheck.getCustCode());
				if (newPrjCheck==null){
					ServletUtils.outPrintFail(request, response, "该客户尚未进行结算，请先进行结算操作");
					return;	
				}
				if(!prjManCheckService.isOneWorker(prjCheck.getCustCode())){
					ServletUtils.outPrintFail(request, response, "项目经理关联不到工人表或关联到多个，不能进行结算完成操作");
					return;				
				}
				if(!prjManCheckService.isQualityFeeZero(prjCheck.getCustCode())){
					ServletUtils.outPrintFail(request, response, "工人过期或离职，当质保金不为0时，不能进行结算完成操作");
					return;				
				}
			}; 
			if("GB".equals(prjCheck.getM_umState())){
				if(!prjManCheckService.isQualityFeeZero(prjCheck.getCustCode())){
					ServletUtils.outPrintFail(request, response, "工人过期或离职，当质保金不为0时，不能进行结算回退操作");
					return;				
				}
				
			}; 
			ServletUtils.outPrintSuccess(request, response, "可以进行操作", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法进行该操作");
		}
	}
	
	/**
	 * 若存在未发货、未回退（含申请、审核、审核退回）的基础领料单，进行提示
	 * @param request
	 * @param response
	 * @param Item
	 * @return
	 */
	@RequestMapping("/isAbnormalItemApp")
	@ResponseBody
	public JSONObject isAbnormalItemApp(HttpServletRequest request, HttpServletResponse response, String custCode,String custTypeType) {
		if(!prjManCheckService.isAbnormalItemApp(custCode, custTypeType)){
			return this.out("存在未发货、未回退的基础领料单，是否进行结算", true);
		}else {
			return null;
		}
	}
	  
	/**
	 *项目经理结算修改 
	 *
	 */
	@RequestMapping("/doCheck")
	public void doReturn(HttpServletRequest request, HttpServletResponse response,PrjCheck prjCheck){
		logger.debug("项目经理结算开始");
		try{
			prjCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjCheck.setExpired("F");
			prjCheck.setIsProvide("0");
			Customer customer = customerService.get(Customer.class, prjCheck.getCustCode());
			UserContext uc = getUserContext(request);
			if (customer!=null){
				if (StringUtils.isNotBlank(customer.getCustType())){
					CustType custType = custTypeService.get(CustType.class, customer.getCustType());
					if (custType!=null){
						//prjCheck.setCustTypeType(custType.getType());
						prjCheck.setPrjCtrlType(custType.getPrjCtrlType());
					}
				}
			}
			if("G".equals(prjCheck.getM_umState())){
				prjCheck.setAppCzy(this.getUserContext(request).getEmnum());
				prjCheck.setDate(DateUtil.getNow());
			}
			if("GC".equals(prjCheck.getM_umState())){
				prjCheck.setConfirmCzy(this.getUserContext(request).getEmnum());
				prjCheck.setConfirmDate(DateUtil.getNow());
			}
			Result result = this.prjManCheckService.doPrjCheckForProc(prjCheck);
			if (result.isSuccess()){
				ServletUtils.outPrint(request, response, true, result.getInfo(), null, true);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response,"操作失败，错误信息："+e.getMessage());
		}
	}
	
	//预扣明细表格数据
	@RequestMapping("/goJqGrid_prjWithHold") 
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid_prjWithHold(HttpServletRequest request,
			HttpServletResponse response,PrjCheck prjCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjManCheckService.findPageBySql_prjWithHold(page, prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *跳转到 质保金录入页面
	 * @param request
	 * @param response
	 * @param PrjCheck
	 */
	@RequestMapping("/goQualityFeeSave")
	public ModelAndView goQualityFeeSave(HttpServletRequest request, HttpServletResponse response,PrjCheck custPrjCheck){
		logger.debug("质保金录入");	
		PrjCheck prjCheck=null;
		try {
			if (StringUtils.isNotBlank(custPrjCheck.getCustCode())){
				UserContext uc = getUserContext(request);
				prjCheck=prjManCheckService.get(PrjCheck.class, custPrjCheck.getCustCode());
				if(prjCheck==null){
					prjCheck=new PrjCheck();
					prjCheck.setCustCode(custPrjCheck.getCustCode());
					prjCheck.setQualityFee(0.0);
					prjCheck.setAccidentFee(0.0);
				}
				prjCheck.setM_umState("A"); 
				prjCheck.setBaseFeeDirct(custPrjCheck.getBaseFeeDirct());
				prjCheck.setMainAmount(custPrjCheck.getMainAmount());
				prjCheck.setBaseFee(custPrjCheck.getBaseFee());
				prjCheck.setChgMainAmount(custPrjCheck.getChgMainAmount());
				prjCheck.setDate(DateUtil.getNow());
				prjCheck.setAppCzy(uc.getEmnum());
				//获取质保金余额
				Map<String, Object> map = prjManCheckService.getRemainQualityFee(custPrjCheck.getCustCode());
				if(map!=null){
					prjCheck.setRemainQualityFee((Double)map.get("RemainQualityFee"));
					prjCheck.setRemainAccidentFee(Double.parseDouble(map.get("RemainAccidentFee").toString()));
				}else{
					prjCheck.setRemainQualityFee(0.0);
					prjCheck.setRemainAccidentFee(0.0);
				}
				resetPrjCheck(prjCheck);	
			}	
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "进入预扣录入页面失败!");
		}
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_qualityFeeSave")
			.addObject("prjCheck", prjCheck);	
	}
	
	/**
	 * 质保金录入
	 * @param request
	 * @param response
	 * @param PrjCheck
	 */
	@RequestMapping("/doSaveQualityFee")
	public void doSaveQualityFee(HttpServletRequest request, HttpServletResponse response,PrjCheck custPrjCheck){
		logger.debug("质保金录入");	
		try {
			if (StringUtils.isNotBlank(custPrjCheck.getCustCode())){
				PrjCheck prjCheck=prjManCheckService.get(PrjCheck.class, custPrjCheck.getCustCode());
				if(prjCheck==null){
					prjCheck=new PrjCheck();
					prjCheck.setCustCode(custPrjCheck.getCustCode());
					prjCheck.setQualityFee(custPrjCheck.getQualityFee());
					prjCheck.setAccidentFee(custPrjCheck.getAccidentFee());
					prjCheck.setMustAmount(-custPrjCheck.getAccidentFee()-custPrjCheck.getQualityFee());
					prjCheck.setLastUpdate(new Date());
					prjCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					prjCheck.setActionLog("ADD");
					prjCheck.setExpired("F");
					prjCheck.setBaseCtrlAmt(0.0);
					prjCheck.setWithHold(0.0);
					prjCheck.setMainCoopFee(0.0);
					prjCheck.setRecvFee(0.0);
					prjCheck.setRealAmount(0.0);
					prjCheck.setCost(0.0);
					prjCheck.setIsProvide("0");
					prjManCheckService.save(prjCheck);
					
				}else{
					prjCheck.setQualityFee(custPrjCheck.getQualityFee());
					prjCheck.setAccidentFee(custPrjCheck.getAccidentFee());
					
					double mustAmount = 0.0;
					mustAmount = Math.round(prjCheck.getBaseCtrlAmt()-prjCheck.getCost()-prjCheck.getWithHold()-custPrjCheck.getQualityFee()
							-prjCheck.getRecvFee()-custPrjCheck.getAccidentFee()+prjCheck.getMainCoopFee()-prjCheck.getRecvFeeFixDuty());
					prjCheck.setMustAmount(mustAmount);
					
					prjCheck.setLastUpdate(new Date());
					prjCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					prjCheck.setActionLog("EDIT");
					prjManCheckService.update(prjCheck);
				}
				ServletUtils.outPrintSuccess(request, response, "保存成功", null);	
			}	
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败!");
		}		
	}
	/**
	 * 修改升级扣项
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/doUpdateItemUp")
	public void doUpdateItemUp(HttpServletRequest request, HttpServletResponse response,Customer customer){	
	    try{
	    	UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
	    	Customer newCustomer=customerService.get(Customer.class,customer.getCode());
			newCustomer.setIsItemUp(customer.getIsItemUp());
			newCustomer.setLastUpdate(new Date());
			newCustomer.setLastUpdatedBy(uc.getCzybh());
			newCustomer.setActionLog("EDIT");
			customerService.update(newCustomer);
		    ServletUtils.outPrintSuccess(request, response);  
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改材料升级标记失败");
		}
	
	}
	
	/**
	 * 质保金汇总
	 * @param request
	 * @param response
	 * @param prjCheck
	 */
	@RequestMapping("/goTotalQualityFee")
	public ModelAndView goTotalQualityFee(HttpServletRequest request, HttpServletResponse response,PrjCheck prjCheck){					
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_TotalQualityFee").addObject("prjCheck", prjCheck);
	}
	/**
	 * 跳转到质保金明细
	 * @param request
	 * @param response
	 * @param prjCheck
	 */               
	@RequestMapping("/goViewQualityDetail")
	public ModelAndView goViewQualityDetail(HttpServletRequest request, HttpServletResponse response,PrjCheck prjCheck){					
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_qualityDetail")
		.addObject("prjCheck", prjCheck);
	}
	
	/**
	 * 重新获取  QualityFee,MustAmount,AccidentFee
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/getQualityFee")
	@ResponseBody
	public Map<String, Object> getQualityFee(HttpServletRequest request,
			HttpServletResponse response, String custCode)
			throws Exception {
		Map<String, Object> map = prjManCheckService.getQualityFee(custCode);
		return map;
	}
	
	@RequestMapping("/getProjectCtrlAdj")
	@ResponseBody
	public Map<String, Object> getProjectCtrlAdj(HttpServletRequest request,
	        HttpServletResponse response, String custCode) {
	    
	    Customer customer = customerService.get(Customer.class, custCode);
	    
	    Map<String, Object> data = new HashMap<String, Object>();
	    data.put("projectCtrlAdj", customer.getProjectCtrlAdj());
	    
	    return data;
	}
	
	/**
	 * 重新获取 PrjCheck 结算页面数据
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/getPrjCheck")
	@ResponseBody
	public Map<String, Object> getPrjCheck(HttpServletRequest request,
			HttpServletResponse response, String custCode,String prjCtrlType)
			throws Exception {
		Map<String, Object> map = prjManCheckService.getPrjCheck(custCode,prjCtrlType);
		return map;
	}
	
	/**
	 * 设计定额明细
	 * @param request
	 * @param response
	 * @param prjCheck
	 */
	@RequestMapping("/goDesignFixDutyAmount")
	public ModelAndView goDesignFixDutyAmount(HttpServletRequest request, HttpServletResponse response,PrjCheck prjCheck){					
		return new ModelAndView("admin/finance/prjManCheck/prjManCheck_designFixDutyAmount").addObject("prjCheck", prjCheck);
	}
	
	@RequestMapping("/goFixDutyJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFixDutyJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjCheck prjCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjManCheckService.findFixDutyPageBySql(page, prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goFixDutyDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFixDutyDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjCheck prjCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjManCheckService.findFixDutyDetailPageBySql(page, prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goBaseItemChgDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goBaseItemChgDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjCheck prjCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjManCheckService.findBaseItemChgDetailPageBySql(page, prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	
}
