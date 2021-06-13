package com.house.home.web.controller.design;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.excel.FixAreaModel_ZC;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Algorithm;
import com.house.home.entity.basic.BasePlanTemp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustGift;
import com.house.home.entity.basic.CustGiftItem;
import com.house.home.entity.basic.CustPlanReport;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.CustTypeItem;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Gift;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.ItemType3;
import com.house.home.entity.basic.ItemTypeConfirm;
import com.house.home.entity.basic.PrePlanTemp;
import com.house.home.entity.basic.SupplPromItem;
import com.house.home.entity.basic.Uom;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.CustContract;
import com.house.home.entity.design.CustPayPre;
import com.house.home.entity.design.CustProfit;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.design.ItemPlanTemp;
import com.house.home.entity.design.PrePlan;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.entity.finance.DiscToken;
import com.house.home.entity.insales.BaseItem;
import com.house.home.entity.project.CustIntProg;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.entity.workflow.Department;
import com.house.home.entity.workflow.WfProcess;
import com.house.home.service.basic.BaseAlgorithmService;
import com.house.home.service.basic.BasePlanTempService;
import com.house.home.service.basic.CmpCustTypeService;
import com.house.home.service.basic.CustGiftService;
import com.house.home.service.basic.CustPlanReportService;
import com.house.home.service.basic.CustTypeItemService;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.basic.ItemType2Service;
import com.house.home.service.basic.ItemType3Service;
import com.house.home.service.basic.PrePlanDoor;
import com.house.home.service.basic.PrePlanTempService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.BaseItemPlanService;
import com.house.home.service.design.CustContractService;
import com.house.home.service.design.CustPayPreService;
import com.house.home.service.design.CustPayService;
import com.house.home.service.design.CustProfitService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.FixAreaService;
import com.house.home.service.design.ItemPlanService;
import com.house.home.service.design.PrePlanAreaService;
import com.house.home.service.finance.DiscTokenService;
import com.house.home.service.insales.BaseItemService;
import com.house.home.service.workflow.WfProcInstService;
import com.house.home.bean.design.CustPaypreSaveBean;
import com.house.home.bean.design.ItemPlanBean;
import com.house.home.bean.design.ProfitAnalyseBean;
import com.house.home.bean.insales.BaseItemBean;
import com.sun.org.apache.bcel.internal.generic.IFNULL;


@Controller
@RequestMapping("/admin/itemPlan")
public class ItemPlanController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPlanController.class);

	@Autowired
	private ItemPlanService itemPlanService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private BaseItemPlanService baseItemPlanService;
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private BaseItemService baseItemService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemType2Service itemType2Service;
	@Autowired
	private CustStakeholderService custStakeholderService;
	@Autowired
	private CustProfitService custProfitService;
	@Autowired
	private CustPayPreService custPayPreService;
	@Autowired
	private FixAreaService fixAreaService;
	@Autowired
	private CustTypeItemService custTypeItemService;
	@Autowired
	private ItemType3Service itemType3Service;
	@Autowired
	CzybmService czybmService; 
	@Autowired
	BaseAlgorithmService baseAlgorithmService;
	@Autowired
	BasePlanTempService basePlanTempService; 
	@Autowired
	PrePlanTempService prePlanTempService;
	@Autowired
	PrePlanAreaService prePlanAreaService;
	@Autowired
	private CustGiftService custGiftService;
	@Autowired
	private CustPayService custPayService;
	@Autowired
	private CustContractService custContractService;
	@Autowired
	private CmpCustTypeService cmpCustTypeService;
	@Autowired
	private DiscTokenService discTokenService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	private WfProcInstService wfProcInstService;
	
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
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlanService.findPageBySql(page, itemPlan,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询基础预算JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBaseItemPlanJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getBaseItemPlanJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemPlan baseItemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageOrderBy("isgroup,a.fixareapk,a.dispseq");
		baseItemPlanService.findPageBySql_jzys(page, baseItemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询预算JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlanJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemPlanJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlan.setContainMainServ(true);
		itemPlan.setExceptMain(true);
		itemPlanService.findPageBySql_zcys(page, itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询软装预算JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlan_RZJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemPlan_RZJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.findPageBySql_rzys(page, itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询软装参考业绩JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlan_RZYJJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemPlan_RZYJJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.findPageBySql_ckyj(page, itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 主材毛利分析JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goZCProfitJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getZCProfitJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.findPageBySql_zcmlfx(page, itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCustGiftJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCustGiftJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustGift custGift) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		// 判断礼品是否计入优惠
		if(!"1".equals(xtcsService.getQzById("GiftInDisc"))){ 
			custGift.setHasGiftAppDetail("0");
		}
		itemPlanService.getCustGiftJqGrid(page, custGift);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCustGiftAllJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCustGiftAllJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustGift custGift) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setAutoCount(false);
		itemPlanService.getCustGiftAllJqGrid(page, custGift);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goItemGiftJqgrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemGiftJqgrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.getItemGiftJqgrid(page, itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCustGiftItemJqgrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCustGiftItemJqgrid(HttpServletRequest request,
			HttpServletResponse response, CustGiftItem custGiftItem) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.getCustGiftItemJqgrid(page, custGiftItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goProcListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goProcListJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.findProcListJqGrid(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getRepClauseDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getRepClauseDetail(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String, Object>> list = itemPlanService.getCustAgreement(customer);
		if(StringUtils.isNotBlank(customer.getCode())){
			if (list == null ){
				list = new ArrayList<Map<String,Object>>();
			}
			customer = customerService.get(Customer.class, customer.getCode());
			if(StringUtils.isNotBlank(customer.getRepClause())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("detail", customer.getRepClause());
				list.add(map);
			}
			if(StringUtils.isNotBlank(customer.getOldRepClause())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("detail", customer.getOldRepClause());
				list.add(map);
			}
		
		}
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 
	 * 跳转到导入excel界面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlan_importExcel")
	public ModelAndView goItemPlan_importExcel(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_importExcel").addObject("itemPlan", itemPlan);
	}
	
	/**
	 * ItemPlan列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/itemPlan/itemPlan_list");
	}
	/**
	 * ItemPlan查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/itemPlan/itemPlan_code");
	}
	/**
	 * 跳转到基础预算界面
	 * @return
	 */
	@RequestMapping("/goItemPlan_jcys")
	public ModelAndView goItemPlan_jcys(HttpServletRequest request, HttpServletResponse response, 
			String id){
		Customer customer=null;
		String str="";
		String contractStatus = "";
		UserContext uc=this.getUserContext(request);
		CustType custType=new CustType();
		JSONArray arryPrePlanAreaDescr=new JSONArray();
		if (StringUtils.isNotBlank(id)){
			 customer=customerService.get(Customer.class, id);
			 if(customer!=null){
				 Xtcs xtcs=xtcsService.get(Xtcs.class, "LONGFEECODE");
				 Xtdm xtdm=xtdmService.getByIdAndCbm("LAYOUT", customer.getLayout());
				 Employee designMan= new Employee();
				 if(StringUtils.isNotBlank(customer.getDesignMan())){
					 designMan = employeeService.get(Employee.class, customer.getDesignMan());
				 }
				 Employee businessMan=employeeService.get(Employee.class, customer.getBusinessMan());
				 custType=custTypeService.get(CustType.class, customer.getCustType());
				// Map<String, Object> map=baseItemPlanService.getBaseFeeComp(id, "综合项目");
				 customer.setLastUpdatedBy(uc.getCzybh());
				// customer.setBaseFeeComp(Double.parseDouble(map.get("baseFeeComp").toString()));
				 customer.setAreaPer(custType.getAreaPer());
				 customer.setBaseFeeDirctPer(custType.getBaseFeeDirctPer());
				 customer.setBaseFeeCompPer(custType.getBaseFeeCompPer());
				 customer.setManageFeeBasePer(custType.getManageFeeBasePer());
				 customer.setMainSetFeePer(custType.getMainSetFeePer());
				 customer.setSetMinusPer(custType.getSetMinusPer());
				 customer.setSetAddPer(custType.getSetAddPer());
				 customer.setLongFeePer(custType.getLongFeePer());
				// customer.setManageFee((double) Math.round((customer.getBaseFeeDirct()*customer.getBaseFeeDirctPer()+customer.getBaseFeeComp()*customer.getBaseFeeCompPer()+customer.getArea()*customer.getAreaPer())*customer.getManageFeeBasePer()));
				 if(designMan!=null){
					 customer.setDesignManDescr(designMan.getNameChi());
				}
				 if(businessMan!=null){
					 customer.setBusinessManDescr(businessMan.getNameChi());
				}
				 customer.setLayout(xtdm.getNote());
				 customer.setLongFeeCode(xtcs.getQz());
				 if(StringUtils.isNotBlank(customer.getBaseTempNo())){
					 BasePlanTemp  basePlanTemp= basePlanTempService.get(BasePlanTemp.class, customer.getBaseTempNo());
					 if(basePlanTemp!=null){
						 customer.setBaseTempNo(customer.getBaseTempNo()+"|"+basePlanTemp.getDescr());
					 }
				 } 
				 if("2".equals(custType.getType())){
					 str="TC";
				 }
				 arryPrePlanAreaDescr =getPrePlanAreaArray(customer.getCode());
				 contractStatus = customerService.getContractStatus(customer);
			 }
		}else{
			 customer=new Customer();
			 customer.setLastUpdatedBy(uc.getCzybh());
		}
		return new ModelAndView("admin/design/itemPlan/itemPlan_jcys"+str)
			.addObject("customer", customer)
			.addObject("custType", custType)
		    .addObject("arryPrePlanAreaDescr", arryPrePlanAreaDescr).addObject("contractStatus", contractStatus);	
		
	}
	/**
	 * 跳转到基础预算审核界面
	 * @return
	 */
	@RequestMapping("/goItemPlan_jcys_confirm")
	public ModelAndView goItemPlan_jcys_confirm(HttpServletRequest request, HttpServletResponse response, 
			String id){
		Customer customer=null;
		String str="_confirm";
		UserContext uc=this.getUserContext(request);
		CustType custType=new CustType();
		JSONArray arryPrePlanAreaDescr=new JSONArray();
		if (StringUtils.isNotBlank(id)){
			 customer=customerService.get(Customer.class, id);
			 if(customer!=null){
				 Xtcs xtcs=xtcsService.get(Xtcs.class, "LONGFEECODE");
				 Xtdm xtdm=xtdmService.getByIdAndCbm("LAYOUT", customer.getLayout());
				 Employee designMan=employeeService.get(Employee.class, customer.getDesignMan());
				 Employee businessMan=employeeService.get(Employee.class, customer.getBusinessMan());
				 custType=custTypeService.get(CustType.class, customer.getCustType());
				// Map<String, Object> map=baseItemPlanService.getBaseFeeComp(id, "综合项目");
				 customer.setLastUpdatedBy(uc.getCzybh());
				// customer.setBaseFeeComp(Double.parseDouble(map.get("baseFeeComp").toString()));
				 customer.setAreaPer(custType.getAreaPer());
				 customer.setBaseFeeDirctPer(custType.getBaseFeeDirctPer());
				 customer.setBaseFeeCompPer(custType.getBaseFeeCompPer());
				 customer.setManageFeeBasePer(custType.getManageFeeBasePer());
				 customer.setMainSetFeePer(custType.getMainSetFeePer());
				 customer.setSetMinusPer(custType.getSetMinusPer());
				 customer.setSetAddPer(custType.getSetAddPer());
				 customer.setLongFeePer(custType.getLongFeePer());
				// customer.setManageFee((double) Math.round((customer.getBaseFeeDirct()*customer.getBaseFeeDirctPer()+customer.getBaseFeeComp()*customer.getBaseFeeCompPer()+customer.getArea()*customer.getAreaPer())*customer.getManageFeeBasePer()));
				 if(designMan!=null){
					 customer.setDesignManDescr(designMan.getNameChi());
				}
				 if(businessMan!=null){
					 customer.setBusinessManDescr(businessMan.getNameChi());
				}
				 customer.setLayout(xtdm.getNote());
				 customer.setLongFeeCode(xtcs.getQz());
				 if(StringUtils.isNotBlank(customer.getBaseTempNo())){
					 BasePlanTemp  basePlanTemp= basePlanTempService.get(BasePlanTemp.class, customer.getBaseTempNo());
				     customer.setBaseTempNo(customer.getBaseTempNo()+"|"+basePlanTemp.getDescr());
				 } 
				 if("2".equals(custType.getType())){
					 str="TC_confirm";
					// customerService.updateForJcysTcProc(customer);
				 }
				 arryPrePlanAreaDescr =getPrePlanAreaArray(customer.getCode());
			 }
			
		}else{
			 customer=new Customer();
			 customer.setLastUpdatedBy(uc.getCzybh());
		}
		
		String contractStatus = customerService.getContractStatus(customer);
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_jcys"+str)
			.addObject("customer", customer)
			.addObject("custType", custType)
			.addObject("arryPrePlanAreaDescr", arryPrePlanAreaDescr)
			.addObject("contractStatus", contractStatus);	
		
	}
	/**
	 * 跳转到预算界面
	 * @return
	 */
	@RequestMapping("/goItemPlan_ys")
	public ModelAndView goItemPlan_ys(HttpServletRequest request, HttpServletResponse response, 
			String id,String itemType1){
		Customer customer=null;
		String str="";
		String contractStatus = "";
		String wfProcStatus = "";
		UserContext uc=this.getUserContext(request);
		JSONArray arryPrePlanAreaDescr=new JSONArray();
		if (StringUtils.isNotBlank(id)){
			 customer=customerService.get(Customer.class, id);
			 if(customer!=null){
				 Xtcs wallPaper=xtcsService.get(Xtcs.class, "WallPaper");
				 Xtcs curtain=xtcsService.get(Xtcs.class, "Curtain");
				 Xtcs light=xtcsService.get(Xtcs.class, "Light");
				 Xtcs furniture=xtcsService.get(Xtcs.class, "Furniture");
				 Xtcs adornment=xtcsService.get(Xtcs.class, "Adornment");
				 Xtdm xtdm=xtdmService.getByIdAndCbm("LAYOUT", customer.getLayout());
				 if(StringUtils.isNotBlank(customer.getDesignMan())){
					 Employee designMan=employeeService.get(Employee.class, customer.getDesignMan());
					 if(designMan!=null){
						 customer.setDesignManDescr(designMan.getNameChi());
					 }	
				 }
				 if(StringUtils.isNotBlank(customer.getBusinessMan())){	
					 Employee businessMan=employeeService.get(Employee.class, customer.getBusinessMan());
					 if(businessMan!=null){
						 customer.setBusinessManDescr(businessMan.getNameChi());
					 }
				 }	
				 if(StringUtils.isNotBlank(customer.getCustType())){
					 CustType custType=custTypeService.get(CustType.class, customer.getCustType());
					 customer.setAreaPer(custType.getAreaPer());
					 customer.setBaseFeeDirctPer(custType.getBaseFeeDirctPer());
					 customer.setBaseFeeCompPer(custType.getBaseFeeCompPer());
					 customer.setManageFeeBasePer(custType.getManageFeeBasePer());
					 customer.setMainSetFeePer(custType.getMainSetFeePer());
					 customer.setSetMinusPer(custType.getSetMinusPer());
					 customer.setSetAddPer(custType.getSetAddPer());
					 customer.setLongFeePer(custType.getLongFeePer());
					 customer.setIsOutSet(custType.getType());
					 customer.setMainFeePer(custType.getMainFeePer());
					 customer.setMainDiscPer(custType.getMainDiscPer());
					 customer.setManageFeeMainPer(custType.getManageFeeMainPer());
					 customer.setMainServFeePer(custType.getMainServFeePer());
					 customer.setManageFeeServPer(custType.getManageFeeServPer());
					// customer.setManageFee((double) Math.round((customer.getBaseFeeDirct()*customer.getBaseFeeDirctPer()+customer.getBaseFeeComp()*customer.getBaseFeeCompPer()+customer.getArea()*customer.getAreaPer())*customer.getManageFeeBasePer()));
					 customer.setManageFeeCupPer(custType.getManageFeeCupPer());
					 customer.setCupboardFeePer(custType.getCupboardFeePer());
					 customer.setCupBoardDiscPer(custType.getCupBoardDiscPer());
					 customer.setSoftFeePer(custType.getSoftFeePer());
					 customer.setSoftDiscPer(custType.getSoftDiscPer());
					 customer.setManageFeeSoftPer(custType.getManageFeeSoftPer());
					 customer.setIntegrateFeePer(custType.getIntegrateFeePer());
					 customer.setIntegrateDiscPer(custType.getIntegrateDiscPer());
					 customer.setManageFeeIntPer(custType.getManageFeeIntPer());
					 customer.setMustImportTemp(custType.getMustImportTemp());
					 customer.setIsAddAllInfo(custType.getIsAddAllInfo());
					 customer.setIsPartDecorate(custType.getIsPartDecorate());
				 }
				// Map<String, Object> map=baseItemPlanService.getBaseFeeComp(id, "综合项目");
				 customer.setLastUpdatedBy(uc.getCzybh());
				// customer.setBaseFeeComp(Double.parseDouble(map.get("baseFeeComp").toString()));
				 customer.setLayout(xtdm.getNote());
				 customer.setSoftFee_WallPaper(wallPaper.getQz());
				 customer.setSoftFee_Curtain(curtain.getQz());
				 customer.setSoftFee_Light(light.getQz());
				 customer.setSoftFee_Furniture(furniture.getQz());
				 customer.setSoftFee_Adornment(adornment.getQz());
				 if (StringUtils.isNotBlank(customer.getMainTempNo())){
					 PrePlanTemp prePlanTemp=  prePlanTempService.get(PrePlanTemp.class, customer.getMainTempNo());
					 if(prePlanTemp!=null){
						 customer.setMainTempNo(customer.getMainTempNo()+"|"+prePlanTemp.getDescr());
				 	}
				 }
				 arryPrePlanAreaDescr =getPrePlanAreaArray(customer.getCode());
				 DiscToken discToken = new DiscToken();
				 discToken.setUseCustCode(customer.getCode());
				 discToken.setUseStep("1");
				 customer.setDiscTokenNo(discTokenService.getDiscTokenNo(discToken));
				 if (StringUtils.isNotBlank(customer.getDiscTokenNo())){
					 discToken  = discTokenService.get(DiscToken.class, customer.getDiscTokenNo());
					 if(discToken!=null){
						 customer.setDiscTokenAmount(discToken.getAmount());
				 	}
				 }
				 if("ZC".equals(itemType1))  str="zcys";
				 if("RZ".equals(itemType1))  str="rzys";	
				 if("JC".equals(itemType1))  str="ysjc";
				 contractStatus = customerService.getContractStatus(customer);
				 
				 wfProcStatus= itemPlanService.getSaleDiscApproveStatus(customer);
			 }
			
		}else{
			 customer=new Customer();
			 customer.setLastUpdatedBy(uc.getCzybh());
		}
	
		return new ModelAndView("admin/design/itemPlan/itemPlan_"+str)
			.addObject("customer", customer).addObject("contractStatus", contractStatus)
			.addObject("arryPrePlanAreaDescr", arryPrePlanAreaDescr).addObject("wfProcStatus", wfProcStatus);
		    
	}
	
	@RequestMapping("/goUpdateDiscountRz")
	public ModelAndView goUpdateDiscountRz(HttpServletRequest request, HttpServletResponse response
			,String itemSetNoList){
		return new ModelAndView("admin/design/itemPlan/itemPlan_UpdateDiscountRz").addObject("itemSetNoList", itemSetNoList.replaceAll("\"", "'").replaceAll(",", "','") );
		
	}
	
	/**
	 * 跳转到基础预算快速新增页面
	 * @return
	 */
	@RequestMapping("/goJzysQuickSave")
	public ModelAndView goJzysQuickSave(HttpServletRequest request, HttpServletResponse response, 
			BaseItemPlan baseItemPlan){
		return new ModelAndView("admin/design/itemPlan/itemPlan_jcys_quickSave").addObject("baseItemPlan", baseItemPlan);
	}
	/**
	 * 跳转到预算快速新增页面
	 * @return
	 */
	@RequestMapping("/goYsQuickSave")
	public ModelAndView goYsQuickSave(HttpServletRequest request, HttpServletResponse response, 
		ItemPlan itemPlan){
		UserContext uc=this.getUserContext(request);
		if("ZC".equals(itemPlan.getItemType1()) && !"1".equals(itemPlan.getMustImportTemp())){
			ExcelImportUtils<FixAreaModel_ZC> excelImportUtils = new ExcelImportUtils<FixAreaModel_ZC>();   
			File file =null;
			if(1==itemPlan.getIsService()){
	        	file = new File(PathUtil.CLASSES.replaceAll("%20", " ") + "com/house/framework/commons/excel/服务性产品区域模板.xls");    
	        }else{
	        	file = new File(PathUtil.CLASSES.replaceAll("%20", " ") + "com/house/framework/commons/excel/主材区域模板.xls");    
	        }
			 List<FixAreaModel_ZC> result=null;
				try {
					FileInputStream in = new FileInputStream(file);
					if(in!=null) result = (ArrayList<FixAreaModel_ZC>) excelImportUtils.importExcel(in, FixAreaModel_ZC.class,null);
				} catch (FileNotFoundException e) {
					
					//e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result!=null){
					 for (int i = 0; i < result.size(); i++) {   
			        	 FixAreaModel_ZC fiZc=result.get(i);
			        	 fixAreaService.addItem_FixArea(itemPlan.getCustCode(), fiZc.getName(), uc.getCzybh(), "ZC");
			         } 	
				}
		}
		if("RZ".equals(itemPlan.getItemType1()) && "2".equals(itemPlan.getCustType().trim())){
			 fixAreaService.addItem_FixArea(itemPlan.getCustCode(), "全房", uc.getCzybh(), "RZ");
		}
		return new ModelAndView("admin/design/itemPlan/itemPlan_ys_quickSave").addObject("itemPlan", itemPlan);
	}
	/**
	 * 跳转到基础预算明细查看页面
	 * @return
	 */
	@RequestMapping("/goItemPlan_jcys_detailView")
	public ModelAndView goItemPlan_jcys_detailView(HttpServletRequest request, HttpServletResponse response,BaseItemPlan baseItemPlan){
		return new ModelAndView("admin/design/itemPlan/itemPlan_jcys_detailView").addObject("baseItemPlan", baseItemPlan);
				
	}
	/**
	 * 跳转到预算(zc、rz)明细查看页面
	 * @return
	 */
	@RequestMapping("/goItemPlan_ys_detailView")
	public ModelAndView goItemPlan_ys_detailView(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		if("JC".equals(itemPlan.getItemType1().trim())){
			return new ModelAndView("admin/design/itemPlan/itemPlan_ys_detailView_JC").addObject("itemPlan", itemPlan);
		}
		return new ModelAndView("admin/design/itemPlan/itemPlan_ys_detailView").addObject("itemPlan", itemPlan);
	
				
	}
	/**
	 * 跳转到基础预算明细查看页面
	 * @return
	 */
	@RequestMapping("/goItemPlan_jcys_detailViewEdit")
	public ModelAndView goItemPlan_jcys_detailViewEdit(HttpServletRequest request, HttpServletResponse response,BaseItemPlan baseItemPlan){
		return new ModelAndView("admin/design/itemPlan/itemPlan_jcys_detailViewEdit").addObject("baseItemPlan", baseItemPlan);
				
	}
	/**
	 * 跳转到预算(zc、rz)明细编辑页面
	 * @return
	 */
	@RequestMapping("/goItemPlan_ys_detailViewEdit")
	public ModelAndView goItemPlan_ys_detailViewEdit(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		Item item=itemService.get(Item.class, itemPlan.getItemCode());
		Customer customer=customerService.get(Customer.class,itemPlan.getCustCode());
		itemPlan.setUnitPrice(item.getPrice());
		itemPlan.setCustType(customer.getCustType().trim());
		if("JC".equals(itemPlan.getItemType1().trim())){
			return new ModelAndView("admin/design/itemPlan/itemPlan_ys_detailViewEdit_JC").addObject("itemPlan", itemPlan);
		}
		return new ModelAndView("admin/design/itemPlan/itemPlan_ys_detailViewEdit").addObject("itemPlan", itemPlan);
				
	}
	/**
	 * 跳转修改单价窗口
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/goUpdatePrice")
	public ModelAndView goUpdatePrice(HttpServletRequest request, HttpServletResponse response,
			BaseItemPlan baseItemPlan){
		return new ModelAndView("admin/design/itemPlan/itemPlan_UpdatePrice")
		.addObject("baseItemPlan", baseItemPlan);
		
	}
	/**
	 * 
	 * 跳转到导入excel界面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlan_importExcel_jcys")
	public ModelAndView goItemPlan_importExcel_jcys(HttpServletRequest request,
			HttpServletResponse response,BaseItemPlan baseItemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_importExcel_jcys").addObject("baseItemPlan", baseItemPlan);
	}
	
	@RequestMapping("/goRepClauseDetail")
	public ModelAndView goItemPlan_importExcel_jcys(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_repClauseDetail").addObject("customer", customer);
	}
	/**
	 * 跳转到新增ItemPlan页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemPlan页面");
		ItemPlan itemPlan = null;
		if (StringUtils.isNotBlank(id)){
			itemPlan = itemPlanService.get(ItemPlan.class, Integer.parseInt(id));
			itemPlan.setPk(null);
		}else{
			itemPlan = new ItemPlan();
		}
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_save")
			.addObject("itemPlan", itemPlan);
	}
	/**
	 * 跳转到修改ItemPlan页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemPlan页面");
		ItemPlan itemPlan = null;
		if (StringUtils.isNotBlank(id)){
			itemPlan = itemPlanService.get(ItemPlan.class, Integer.parseInt(id));
		}else{
			itemPlan = new ItemPlan();
		}
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_update")
			.addObject("itemPlan", itemPlan);
	}
	
	/**
	 * 跳转到查看ItemPlan页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemPlan页面");
		ItemPlan itemPlan = itemPlanService.get(ItemPlan.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_detail")
				.addObject("itemPlan", itemPlan);
	}
	
	
	@RequestMapping("/goPrintContractTemp")
	public void goPrintContractTemp(HttpServletRequest request,
	        HttpServletResponse response, String fileName) throws IOException{
	    
	    response.setContentType("application/pdf;charset=utf-8"); 
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode("合同范本.pdf", "utf-8"));
        
        // InputStream in = new FileInputStream(getContractTempPath(fileName, "") + fileName);
        InputStream in = FileUploadUtils.download(fileName);
        
        OutputStream out = response.getOutputStream();
        Streams.copy(in, out, true);
	}
	
	/**
	 * 添加baseItemPlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,Customer customer){
		String contractStatus = customerService.getContractStatus(customer);
		if("2".equals(contractStatus) || "3".equals(contractStatus) || "4".equals(contractStatus)){
			ServletUtils.outPrintFail(request, response,"最近一条合同状态为已签约、已申请、已审核不允许修改预算");
			return;
		}
		Result result= baseItemPlanService.doBaseItemForProc(customer);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 添加baseItemPlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doTCSave")
	public void doTCSave(HttpServletRequest request, HttpServletResponse response,Customer customer){
		Result result= baseItemPlanService.doBaseItemTCForProc(customer);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 软装(含套餐)、主材、集成预算保存
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doClysSave")
	public void doClysSave(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		Result result= itemPlanService.doItemForProc(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 主材、集成预算套餐保存
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doClysTCSave")
	public void doClysTCSave(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		Result result= itemPlanService.doItemTCForProc(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 合同信息预录保存
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCustProfitSave")
	public void doCustProfitSave(HttpServletRequest request, HttpServletResponse response,CustProfit custProfit){
		UserContext uc=this.getUserContext(request);
		custProfit.setLastUpdatedBy(uc.getCzybh());
		Result result= custProfitService.doCustProfitForProc(custProfit);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, "保存合同信息预录成功！");
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 付款计划预录保存
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCustPaypreSave")
	public void doCustPaypreSave(HttpServletRequest request, HttpServletResponse response,CustPaypreSaveBean custPaypreSaveBean){
		UserContext uc=this.getUserContext(request);
		custPaypreSaveBean.setLastUpdatedBy(uc.getCzybh());
		Result result= custPayPreService.doCustPaypreForProc(custPaypreSaveBean);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, "保存付款计划成功！");
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 修改ItemPlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemPlan itemPlan){
		logger.debug("修改ItemPlan开始");
		try{
			itemPlan.setLastUpdate(new Date());
			itemPlan.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemPlanService.update(itemPlan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemPlan失败");
		}
	}
	
	/**
	 * 删除ItemPlan
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemPlan开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemPlan编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemPlan itemPlan = itemPlanService.get(ItemPlan.class, Integer.parseInt(deleteId));
				if(itemPlan == null)
					continue;
				itemPlan.setExpired("T");
				itemPlanService.update(itemPlan);
			}
		}
		logger.debug("删除ItemPlan IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	/**
	 * 清空预算
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doClear")
	public void doClear(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=this.getUserContext(request);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doClearPlanForProc(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 *ItemPlan导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemPlan itemPlan){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlanService.findPageBySql(page, itemPlan,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"预算管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		
	}
	/**
	 *ItemPlan主材毛利分析导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doZCmlfxExcel")
	public void doZCmlfxExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemPlan itemPlan){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemPlanService.findPageBySql_zcmlfx(page, itemPlan);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材毛利分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		
	}
	/**
	 * 套餐从模板导入数据
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doBaseBatchFromTemp")
	public void doBaseBatchFromTemp(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doBaseBatchTempProc(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 基础套装从模板导入数据
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doBaseItemPlanFromTemp")
	public void doBaseItemPlanFromTemp(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doBaseItemTempProc(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	
	/**
	 * 复制预算
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCopyPlan")
	public void doCopyPlan(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doCopyPlan(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 快速预算导入
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCopyPlanPre")
	public void doCopyPlanPre(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doCopyPlanPre(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 预算从模板导入
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doPrePlanTemp")
	public void doPrePlanTemp(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doPrePlanTemp(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 修改客户类型
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateCustType")
	public void doUpdateCustType(HttpServletRequest request, HttpServletResponse response,Customer customer){
		
	    try{
	    	UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
	    	ItemPlan itemPlan=new ItemPlan();
			itemPlan.setM_umState("M");
			itemPlan.setCustCode(customer.getCode());
			itemPlan.setLastUpdatedBy(uc.getCzybh());
	    	Result result= itemPlanService.doBaseAndItemPlanBak(itemPlan);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
			// 删除客户所有赠品
			custGiftService.deleteAllGiftsByCustCode(customer.getCode());
			
	    	Customer newCustomer=customerService.get(Customer.class,customer.getCode());
			newCustomer.setCustType(customer.getCustType());
			newCustomer.setIsInitSign("0");//修改草签标记
			newCustomer.setLastUpdate(new Date());
			newCustomer.setLastUpdatedBy(uc.getCzybh());
			customerService.update(newCustomer);
		    ServletUtils.outPrintSuccess(request, response);  
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改客户失败");
		}
	
	}
	/**
	 * 修改主材干系人
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateCustStakeholder")
	public void doUpdateCustStakeholder(HttpServletRequest request, HttpServletResponse response,Customer customer){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		CustStakeholder custStakeholder = new CustStakeholder();
		custStakeholder.setM_umState("A");
		custStakeholder.setIsRight(0);
		custStakeholder.setCustCode(customer.getCode());
		custStakeholder.setLastUpdatedBy(uc.getCzybh());
		custStakeholder.setPk(0);
		custStakeholder.setExpired("F");
//		if(StringUtils.isNotBlank(customer.getMainPlanMan())){
//			custStakeholder.setRole("32");
//			custStakeholder.setEmpCode(customer.getMainPlanMan());
//			Result result = custStakeholderService.updateForProc(custStakeholder);
//			if ("1".equals(result.getCode())){
//				if(StringUtils.isNotBlank(customer.getMainBusinessMan())){
//					custStakeholder.setRole("34");
//					custStakeholder.setEmpCode(customer.getMainBusinessMan());
//					Result result2 = custStakeholderService.updateForProc(custStakeholder);
//					if ("1".equals(result2.getCode())){
//						ServletUtils.outPrintSuccess(request, response, result2.getInfo());
//					}else{
//						ServletUtils.outPrintFail(request, response, result2.getInfo());
//					}
//				}else{
//					//只添加设计师
//					ServletUtils.outPrintSuccess(request, response, result.getInfo());
//				}
//			
//			}else{
//				//添加设计师失败直接返回
//				ServletUtils.outPrintFail(request, response, result.getInfo());
//			}
//		}else{
//			//只添加主材管家
//			custStakeholder.setRole("34");
//			custStakeholder.setEmpCode(customer.getMainBusinessMan());
//			Result result = custStakeholderService.updateForProc(custStakeholder);
//			if ("1".equals(result.getCode())){
//				ServletUtils.outPrintSuccess(request, response, result.getInfo());
//			}else{
//				ServletUtils.outPrintFail(request, response, result.getInfo());
//			}
//		}
		Result result = null; 
		if(StringUtils.isNotBlank(customer.getMainPlanMan())){
			custStakeholder.setRole("32");
			custStakeholder.setEmpCode(customer.getMainPlanMan());
			result = custStakeholderService.updateForProc(custStakeholder);
			if (!"1".equals(result.getCode())){
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}
		if(StringUtils.isNotBlank(customer.getMainBusinessMan())){
			custStakeholder.setRole("34");
			custStakeholder.setEmpCode(customer.getMainBusinessMan());
			result = custStakeholderService.updateForProc(custStakeholder);
			if (!"1".equals(result.getCode())){
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}
		if(StringUtils.isNotBlank(customer.getDeclareMan())){
			custStakeholder.setRole("31");
			custStakeholder.setEmpCode(customer.getDeclareMan());
			result = custStakeholderService.updateForProc(custStakeholder);
			if (!"1".equals(result.getCode())){
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}
		ServletUtils.outPrintSuccess(request, response, "保存成功");	
	}
	/**
	 * 修改集成干系人
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateJCCustStakeholder")
	public void doUpdateJCCustStakeholder(HttpServletRequest request, HttpServletResponse response,Customer customer){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		CustStakeholder custStakeholder = new CustStakeholder();
		custStakeholder.setM_umState("A");
		custStakeholder.setIsRight(0);
		custStakeholder.setCustCode(customer.getCode());
		custStakeholder.setLastUpdatedBy(uc.getCzybh());
		custStakeholder.setPk(0);
		custStakeholder.setExpired("F");
		CustIntProg custIntProg = new CustIntProg();
		if(StringUtils.isNotBlank(customer.getJcMan())){
			custStakeholder.setPk(custStakeholderService.getPkByCustCodeAndRole(customer.getCode(), "11"));
			custStakeholder.setRole("11");
			custStakeholder.setEmpCode(customer.getJcMan());
			Result result = custStakeholderService.updateForProc(custStakeholder);
			if (!"1".equals(result.getCode())){
				//添加集成设计师失败直接返回
				ServletUtils.outPrintFail(request, response, result.getInfo());
				return;
			}
		}
		if(StringUtils.isNotBlank(customer.getCGDesignCode())){
			custStakeholder.setPk(custStakeholderService.getPkByCustCodeAndRole(customer.getCode(), "61"));
			custStakeholder.setRole("61");
			custStakeholder.setEmpCode(customer.getCGDesignCode());
			Result result = custStakeholderService.updateForProc(custStakeholder);
			if (!"1".equals(result.getCode())){
				ServletUtils.outPrintFail(request, response, result.getInfo());
				return;
			}
		}
		if(StringUtils.isNotBlank(customer.getJCZYDesignCode())){
			custStakeholder.setPk(custStakeholderService.getPkByCustCodeAndRole(customer.getCode(), "66"));
			custStakeholder.setRole("66");
			custStakeholder.setEmpCode(customer.getJCZYDesignCode());
			Result result = custStakeholderService.updateForProc(custStakeholder);
			if (!"1".equals(result.getCode())){
				ServletUtils.outPrintFail(request, response, result.getInfo());
				return;
			}
		}
		
		if(StringUtils.isNotBlank(customer.getJcSpecMan())){
			custStakeholder.setPk(custStakeholderService.getPkByCustCodeAndRole(customer.getCode(), "67"));
			custStakeholder.setRole("67");
			custStakeholder.setEmpCode(customer.getJcSpecMan());
			Result result = custStakeholderService.updateForProc(custStakeholder);
			
			/*
			if (!"1".equals(result.getCode())){
				ServletUtils.outPrintFail(request, response, result.getInfo());
				return;
			}
			*/
		}
		
		if(StringUtils.isNotBlank(customer.getCgSpecMan())){
			custStakeholder.setPk(custStakeholderService.getPkByCustCodeAndRole(customer.getCode(), "68"));
			custStakeholder.setRole("68");
			custStakeholder.setEmpCode(customer.getCgSpecMan());
			Result result = custStakeholderService.updateForProc(custStakeholder);
			
			/*
			if (!"1".equals(result.getCode())){
				ServletUtils.outPrintFail(request, response, result.getInfo());
				return;
			}
			*/
		}
		
		if("specItemReq".equals(customer.getModule())){
			custIntProg = itemPlanService.get(CustIntProg.class, customer.getCode());
			if(custIntProg == null && (customer.getIntDesignDate() != null || customer.getCupDesignDate() != null)){
				custIntProg = new CustIntProg();
				custIntProg.setCustCode(customer.getCode());
				custIntProg.setExpired("F");
				custIntProg.setActionLog("ADD");
			}
			
			if (custIntProg != null) {
    			custIntProg.setLastUpdate(new Date());
    			custIntProg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
    			custIntProg.setIntDesignDate(customer.getIntDesignDate());
    			custIntProg.setCupDesignDate(customer.getCupDesignDate());
    			if(StringUtils.isNotBlank(custIntProg.getCustCode())){
    				itemPlanService.saveOrUpdate(custIntProg);
    			}
			}
		}
		
		ServletUtils.outPrintSuccess(request, response, "保存成功");
	}
	/**
	 * 修改软装干系人
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateRZCustStakeholder")
	public void doUpdateRZCustStakeholder(HttpServletRequest request, HttpServletResponse response,Customer customer){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		CustStakeholder custStakeholder = new CustStakeholder();
		custStakeholder.setM_umState("A");
		custStakeholder.setIsRight(0);
		custStakeholder.setCustCode(customer.getCode());
		custStakeholder.setLastUpdatedBy(uc.getCzybh());
		custStakeholder.setPk(0);
		custStakeholder.setExpired("F");
		custStakeholder.setRole("50");
		custStakeholder.setEmpCode(customer.getRzMan());
		Result result = custStakeholderService.updateForProc(custStakeholder);
		if ("1".equals(result.getCode())){
			ServletUtils.outPrintSuccess(request, response, result.getInfo());
		}else{
			ServletUtils.outPrintFail(request, response, result.getInfo());
		}
	}
	/**
	 * 修改设计干系人
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateDesignCustStakeholder")
	public void doUpdateDesignCustStakeholder(HttpServletRequest request, HttpServletResponse response,Customer customer){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		CustStakeholder custStakeholder = new CustStakeholder();
		custStakeholder.setM_umState("A");
		custStakeholder.setIsRight(0);
		custStakeholder.setCustCode(customer.getCode());
		custStakeholder.setLastUpdatedBy(uc.getCzybh());
		custStakeholder.setPk(0);
		custStakeholder.setExpired("F");
		if(StringUtils.isNotBlank(customer.getMapper())){
			custStakeholder.setRole("03");
			custStakeholder.setEmpCode(customer.getMapper());
			Result result = custStakeholderService.updateForProc(custStakeholder);
			if ("1".equals(result.getCode())){
				if(StringUtils.isNotBlank(customer.getSketcher())){
					custStakeholder.setRole("04");
					custStakeholder.setEmpCode(customer.getSketcher());
					Result result2 = custStakeholderService.updateForProc(custStakeholder);
					if ("1".equals(result2.getCode())){
						if(StringUtils.isNotBlank(customer.getDeepDesignMan())){
							custStakeholder.setRole("64");
							custStakeholder.setEmpCode(customer.getDeepDesignMan());
							Result result3 = custStakeholderService.updateForProc(custStakeholder);
							if ("1".equals(result3.getCode())){
								if(StringUtils.isNotBlank(customer.getDesignMan())){
									custStakeholder.setRole("00");
									custStakeholder.setEmpCode(customer.getDesignMan());
									Result result4 = custStakeholderService.updateForProc(custStakeholder);
									if ("1".equals(result4.getCode())){
										if(StringUtils.isNotBlank(customer.getMeasureMan())){
											custStakeholder.setRole("02");
											custStakeholder.setEmpCode(customer.getMeasureMan());
											Result result5 = custStakeholderService.updateForProc(custStakeholder);
											if("1".equals(result5.getCode())){
												ServletUtils.outPrintSuccess(request, response, result5.getInfo());
											}else{
												ServletUtils.outPrintFail(request, response, result5.getInfo());
											}
										}
									}else{
										ServletUtils.outPrintFail(request, response, result4.getInfo());
									}
								}
							}else{
								ServletUtils.outPrintFail(request, response, result3.getInfo());
							}
						}else{
							ServletUtils.outPrintSuccess(request, response, result2.getInfo());
						}
						
					}else{
						//添加效果图员失败直接返回
						ServletUtils.outPrintFail(request, response, result2.getInfo());
					}
				}else if(StringUtils.isNotBlank(customer.getDeepDesignMan())){
					custStakeholder.setRole("64");
					custStakeholder.setEmpCode(customer.getDeepDesignMan());
					Result result3 = custStakeholderService.updateForProc(custStakeholder);
					if ("1".equals(result3.getCode())){
						if(StringUtils.isNotBlank(customer.getDesignMan())){
							custStakeholder.setRole("00");
							custStakeholder.setEmpCode(customer.getDesignMan());
							Result result4 = custStakeholderService.updateForProc(custStakeholder);
							if ("1".equals(result4.getCode())){
								if(StringUtils.isNotBlank(customer.getMeasureMan())){
									custStakeholder.setRole("02");
									custStakeholder.setEmpCode(customer.getMeasureMan());
									Result result5 = custStakeholderService.updateForProc(custStakeholder);
									if("1".equals(result5.getCode())){
										ServletUtils.outPrintSuccess(request, response, result5.getInfo());
									}else{
										ServletUtils.outPrintFail(request, response, result5.getInfo());
									}
								}
							}else{
								ServletUtils.outPrintFail(request, response, result4.getInfo());
							}
						}
					}else{
						ServletUtils.outPrintFail(request, response, result3.getInfo());
					}
				}else{
					//只添加绘图员
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}
			
			}else{
				//添加绘图员失败直接返回
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}else if(StringUtils.isNotBlank(customer.getSketcher())){
			//添加效果图员
			custStakeholder.setRole("04");
			custStakeholder.setEmpCode(customer.getSketcher());
			Result result = custStakeholderService.updateForProc(custStakeholder);
			if ("1".equals(result.getCode())){
				if(StringUtils.isNotBlank(customer.getDeepDesignMan())){
					custStakeholder.setRole("64");
					custStakeholder.setEmpCode(customer.getDeepDesignMan());
					Result result2 = custStakeholderService.updateForProc(custStakeholder);
					if ("1".equals(result2.getCode())){
						custStakeholder.setRole("00");
						custStakeholder.setEmpCode(customer.getDesignMan());
						Result result3 = custStakeholderService.updateForProc(custStakeholder);
						if("1".equals(result3.getCode())){
							custStakeholder.setRole("02");
							custStakeholder.setEmpCode(customer.getMeasureMan());
							Result result4 = custStakeholderService.updateForProc(custStakeholder);
							if("1".equals(result4.getCode())){
								ServletUtils.outPrintSuccess(request, response, result4.getInfo());
							}else{
								ServletUtils.outPrintFail(request, response, result4.getInfo());
							}
						}else{
							ServletUtils.outPrintFail(request, response, result3.getInfo());
						}
					}else{
						ServletUtils.outPrintFail(request, response, result2.getInfo());
					}
				}else{
					//只添加效果图员
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}
				
			}else{
				//添加效果图员失败直接返回
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}else if(StringUtils.isNotBlank(customer.getDeepDesignMan())){
			//添加深化设计师
			custStakeholder.setRole("64");
			custStakeholder.setEmpCode(customer.getDeepDesignMan());
			Result result1 = custStakeholderService.updateForProc(custStakeholder);
			if ("1".equals(result1.getCode())){
				custStakeholder.setRole("00");
				custStakeholder.setEmpCode(customer.getDesignMan());
				Result result2 = custStakeholderService.updateForProc(custStakeholder);
				if("1".equals(result2.getCode())){
					custStakeholder.setRole("02");
					custStakeholder.setEmpCode(customer.getDesignMan());
					Result result3 = custStakeholderService.updateForProc(custStakeholder);
					if("1".equals(result3.getCode())){
						ServletUtils.outPrintSuccess(request, response, result3.getInfo());
					}else {
						ServletUtils.outPrintFail(request, response, result3.getInfo());
					}
				}else{
					ServletUtils.outPrintFail(request, response, result2.getInfo());
				}
			}else{
				ServletUtils.outPrintFail(request, response, result1.getInfo());
			}
		}else if(StringUtils.isNotBlank(customer.getDesignMan())){
			//添加设计师
			custStakeholder.setRole("00");
			custStakeholder.setEmpCode(customer.getDeepDesignMan());
			Result result1 = custStakeholderService.updateForProc(custStakeholder);
			if("1".equals(result1.getCode())){
				custStakeholder.setRole("02");
				custStakeholder.setEmpCode(customer.getMeasureMan());
				Result result2 = custStakeholderService.updateForProc(custStakeholder);
				if("1".equals(result2.getCode())){
					ServletUtils.outPrintSuccess(request, response, result2.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result2.getInfo());
				}
			}
		}else if(StringUtils.isNotBlank(customer.getMeasureMan())){
			//只添加量房员
			custStakeholder.setRole("02");
			custStakeholder.setEmpCode(customer.getMeasureMan());
			Result result = custStakeholderService.updateForProc(custStakeholder);
			if("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}

		//记录客户跟踪
		Employee employee=employeeService.get(Employee.class, this.getUserContext(request).getEmnum());
		CustCon custCon=new CustCon();
		custCon.setType("2");
		custCon.setConDate(new Date());
		custCon.setConMan(this.getUserContext(request).getCzybh());
		custCon.setCustCode(customer.getCode());
		custCon.setRemarks("修改设计干系人");
		custCon.setExpired("F");
		custCon.setActionLog("ADD");
		custCon.setLastUpdate(new Date());
		custCon.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		this.customerService.save(custCon);
		
		//绘图费，效果图费更改
		if(StringUtils.isNotBlank(customer.getCode())&&(customer.getDrawFee()!=null||customer.getColorDrawFee()!=null)   ){
			Customer ct=customerService.get(Customer.class, customer.getCode());
			if(customer.getDrawFee()!=null&&!ct.getDrawFee().equals(customer.getDrawFee()) ){
				ct.setDrawFee(customer.getDrawFee());
			}
			if(customer.getColorDrawFee()!=null&&!ct.getColorDrawFee().equals(customer.getColorDrawFee()) ){
				ct.setColorDrawFee(customer.getColorDrawFee());
			}
			customerService.update(ct);
		}
		ServletUtils.outPrintSuccess(request, response, "保存成功");
	}
	/**
	 *是否允许修改客户类型
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/checkCustType")
	public void checkCustType(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		Customer customer=customerService.get(Customer.class,itemPlan.getCustCode());
		
		if ("4".equals(customer.getStatus())||"5".equals(customer.getStatus())){
			ServletUtils.outPrintFail(request, response,"合同施工和归档状态客户不允许修改客户类型！");
			return;	
		}
		/*BaseItemPlan baseItemPlan=new BaseItemPlan();
		baseItemPlan.setCustCode(itemPlan.getCustCode());
		if(baseItemPlanService.getBaseItemPlanCount(baseItemPlan)>0){
			ServletUtils.outPrintFail(request, response,"该客户存在基础预算，不允许修改客户类型！");
			return;
		}
		if(itemPlanService.getItemPlanCount(itemPlan)>0){
			ServletUtils.outPrintFail(request, response,"该客户存在材料预算，不允许修改客户类型！");
			return;
		}*/
		ServletUtils.outPrintSuccess(request, response);
	}
	/**
	 * 
	 * 基础项目
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/goBaseItem")
	public ModelAndView goBaseItem(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_baseItem").addObject("itemPlan", itemPlan);
	}
	/**
	 * 
	 * 材料
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItem")
	public ModelAndView goItem(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_item").addObject("itemPlan", itemPlan);
	}
	/**
	 * 
	 * 跳转基础模板页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBaseItemTemp")
	public ModelAndView goBaseItemTemp(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_baseItemTemp").addObject("itemPlan", itemPlan);
	}
	/**
	 * 
	 * 跳转预算跳转到从批量报价导入界面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBaseBatchTemp")
	public ModelAndView goBaseBatchTemp(HttpServletRequest request,
			HttpServletResponse response,BaseItemPlan baseItemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_baseBatchTemp").addObject("baseItemPlan", baseItemPlan);
	}
	/**
	 * 
	 * 跳转预算跳转到从模板导入界面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBaseFromTemp")
	public ModelAndView goBaseFromTemp(HttpServletRequest request,
			HttpServletResponse response,BaseItemPlan baseItemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_basePlanFromTemp").addObject("baseItemPlan", baseItemPlan);
	}
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemTemp")
	public ModelAndView goItemBatchDetailList(HttpServletRequest request,
			HttpServletResponse response, ItemPlanTemp itemPlanTemp) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_itemPlanTemp").addObject("itemPlanTemp",itemPlanTemp);
	}
	/**
	 * 
	 * 跳转选择复制客户预算
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_customerCode").addObject("itemPlan",itemPlan);
	}
	/**
	 * 
	 * 跳转查看单价修改页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlan_priceUpdateView")
	public ModelAndView goItemPlan_priceUpdateView(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		 JSONArray arryPrePlanAreaDescr=new JSONArray();
		 if (StringUtils.isNotBlank(itemPlan.getCustCode())){
			 arryPrePlanAreaDescr =getPrePlanAreaArray(itemPlan.getCustCode());
		 }
		return new ModelAndView("admin/design/itemPlan/itemPlan_priceUpdateView").addObject("arryPrePlanAreaDescr",arryPrePlanAreaDescr);
	}
	/**
	 * 
	 * 跳转客户类型修改页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdateCustType")
	public ModelAndView goUpdateCustType(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Customer customer=customerService.get(Customer.class,itemPlan.getCustCode());
		customer.setCustType(customer.getCustType().trim());
		return new ModelAndView("admin/design/itemPlan/itemPlan_custTypeView").addObject("customer",customer);
	}
	/**
	 * 
	 * 跳转添加干系人页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAddStakeholder")
	public ModelAndView goAddStakeholder(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Customer customer=customerService.get(Customer.class,itemPlan.getCustCode());
		Map<String, Object> custStakeholder=custStakeholderService.getStakeholderInfo(customer.getCode());
		customer.setMainPlanMan(custStakeholder.get("MainPlanMan").toString());
		customer.setMainPlanManName(custStakeholder.get("MainPlanManName").toString());
		customer.setMainBusinessMan(custStakeholder.get("MainBusinessMan").toString());
		customer.setMainBusinessManName(custStakeholder.get("MainBusinessManName").toString());
		customer.setMapper(custStakeholder.get("Mapper").toString());
		customer.setMapperName(custStakeholder.get("MapperName").toString());
		customer.setSketcher(custStakeholder.get("Sketcher").toString());
		customer.setSketcherName(custStakeholder.get("SketcherName").toString());
		customer.setDeepDesignMan(custStakeholder.get("DeepDesignMan").toString());
		customer.setDeepDesignManDescr(custStakeholder.get("DeepDesignManDescr").toString());
		customer.setJcMan(custStakeholder.get("JCMan").toString());
		customer.setJcManName(custStakeholder.get("JCManName").toString());
		customer.setCGDesignCode(custStakeholder.get("CGDesignCode").toString());
		customer.setCGDesignerDescr(custStakeholder.get("CGDesignDescr").toString());
		customer.setRzMan(custStakeholder.get("RZMan").toString());
		customer.setRzManName(custStakeholder.get("RZManName").toString());
		customer.setJCZYDesignCode(custStakeholder.get("JCZYDesignCode").toString());
		customer.setJCZYDesignerDescr(custStakeholder.get("JCZYDesignDescr").toString());
		customer.setDesignMan(custStakeholder.get("DesignMan").toString());
		customer.setDesignManDescr(custStakeholder.get("DesignManDescr").toString());
		customer.setMeasureMan(custStakeholder.get("MeasureMan").toString());
		customer.setMeasureManDescr(custStakeholder.get("MeasureManDescr").toString());
		customer.setDeclareMan(custStakeholder.get("DeclareMan").toString());
		customer.setDeclareManName(custStakeholder.get("DeclareManDescr").toString());
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_stakeholderView_"+itemPlan.getItemType1()).addObject("customer",customer);
	}
	/**
	 * 
	 * 跳转毛利分析页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goProfitAnalyse")
	public ModelAndView goProfitAnalyse(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Customer customer=customerService.get(Customer.class,itemPlan.getCustCode());
		CustProfit custProfit=custProfitService.get(CustProfit.class, customer.getCode());
		CustType custType=custTypeService.get(CustType.class, customer.getCustType());
		CustContract custContract = new CustContract();
		custContract.setCustCode(itemPlan.getCustCode());
		custContract.setConType("1");
		UserContext uc = getUserContext(request);
		if(custProfit==null){
			custProfit=new CustProfit();
			custProfit.setBaseDiscPer(1.00);
			custProfit.setBaseDisc1(0.00);
			custProfit.setBaseDisc2(0.00);
			custProfit.setDesignFee(0.00);
			custProfit.setContainBase(1);
			custProfit.setContainCup(1);
			custProfit.setContainInt(1);
			custProfit.setContainMain(1);
			custProfit.setContainMainServ(1);
			custProfit.setContainSoft(1);
			custProfit.setGift(0.00);
			custProfit.setPrepay(0.00);
		}
		if(StringUtils.isNotBlank(custProfit.getPosition())){
			custProfit.setPosition(custProfit.getPosition().trim());
		}
		
		//最近一条合同记录是否为申请，审核，签约
		List<Map<String, Object>> list = custContractService.latelyCon(custContract);
		String latelyCon ="";
		if(list != null && list.size() > 0){
			latelyCon = "1";
		}
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_profitView").addObject("customer",customer)
								.addObject("custType",custType)
								.addObject("custProfit",custProfit)
								.addObject("latelyCon", latelyCon)
								// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//								.addObject("isProfitEdit",czybmService.hasAuthByCzybh(uc.getCzybh(),1093))
//								.addObject("isProfitView",czybmService.hasAuthByCzybh(uc.getCzybh(),1094))
//								.addObject("isProfitZCView",czybmService.hasAuthByCzybh(uc.getCzybh(),1095));
								.addObject("isProfitEdit",czybmService.hasGNQXByCzybh(uc.getCzybh(),"0208","毛利分析编辑"))
								.addObject("isProfitView",czybmService.hasGNQXByCzybh(uc.getCzybh(),"0208","毛利分析-查看毛利"))
								.addObject("isProfitZCView",czybmService.hasGNQXByCzybh(uc.getCzybh(),"0208","毛利分析-查看主材毛利"));
		
	}
	/**
	 * 
	 * 跳转毛利分析合同信息预录页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goProfitAnalyse_htxxyl")
	public ModelAndView goProfitAnalyse_htxxyl(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Customer customer=customerService.get(Customer.class,itemPlan.getCustCode());
		CustProfit custProfit=custProfitService.get(CustProfit.class, customer.getCode());
		UserContext uc = getUserContext(request);
		if(custProfit==null){
			custProfit=new CustProfit();
			custProfit.setBaseDiscPer(1.00);
			custProfit.setBaseDisc1(0.00);
			custProfit.setBaseDisc2(0.00);
			custProfit.setDesignFee(0.00);
			custProfit.setContainBase(1);
			custProfit.setContainCup(1);
			custProfit.setContainInt(1);
			custProfit.setContainMain(1);
			custProfit.setContainMainServ(1);
			custProfit.setContainSoft(1);
			custProfit.setGift(0.00);
			custProfit.setPrepay(custPayService.getPayDesignFee(itemPlan.getCustCode(), "1"));
		}
		if(StringUtils.isNotBlank(custProfit.getPosition())){
			custProfit.setPosition(custProfit.getPosition().trim());
		}
		
		String contractStatus = customerService.getContractStatus(customer);
		
		return new ModelAndView("admin/design/itemPlan/tab_htxxyl").addObject("customer",customer)
				.addObject("custProfit",custProfit)
				// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//				.addObject("isProfitEdit",czybmService.hasAuthByCzybh(uc.getCzybh(),1093));
				.addObject("isProfitEdit",czybmService.hasGNQXByCzybh(uc.getCzybh(),"0208","毛利分析编辑"))
				.addObject("contractStatus", contractStatus);
	}
	/**
	 * 
	 * 跳转毛利分析付款计划预录页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goProfitAnalyse_fkjhyl")
	public ModelAndView goProfitAnalyse_fkjhyl(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Customer customer=customerService.get(Customer.class,itemPlan.getCustCode());
		CustType custType=custTypeService.get(CustType.class, customer.getCustType());
		CustProfit custProfit=custProfitService.get(CustProfit.class, customer.getCode());
		CustPayPre custPayPre=custPayPreService.getCustPayPre(customer.getCode(), "1");
		CustPayPre custPayPre2=custPayPreService.getCustPayPre(customer.getCode(), "2");
		CustPayPre custPayPre3=custPayPreService.getCustPayPre(customer.getCode(), "3");
		CustPayPre custPayPre4=custPayPreService.getCustPayPre(customer.getCode(), "4");
		UserContext uc = getUserContext(request);
		if(custProfit==null){
			custProfit=new CustProfit();
			custProfit.setBaseDiscPer(1.00);
			custProfit.setBaseDisc1(0.00);
			custProfit.setBaseDisc2(0.00);
			custProfit.setDesignFee(0.00);
			custProfit.setContainBase(1);
			custProfit.setContainCup(1);
			custProfit.setContainInt(1);
			custProfit.setContainMain(1);
			custProfit.setContainMainServ(1);
			custProfit.setContainSoft(1);
		}
		custProfit.setTaxRate(Double.valueOf(xtcsService.getQzById("TaxRate")));
		itemPlan.setPayType(custProfit.getPayType());
		itemPlan.setCustType(customer.getCustType());
		Map<String,Object> fourCustPay=itemPlanService.findBySql_GetFourCustPay(itemPlan);
		Map<String,Object> payRemark=itemPlanService.getPayRemark(itemPlan);
		/*JSONObject listPayRemarks = JSONObject.fromObject(listPayRemark);*/

		if(StringUtils.isNotBlank(custProfit.getPosition())){
			custProfit.setPosition(custProfit.getPosition().trim());
		}
		
		String contractStatus = customerService.getContractStatus(customer);
		
		return new ModelAndView("admin/design/itemPlan/tab_fkjhyl").addObject("customer",customer)
				.addObject("custProfit",custProfit)
				.addObject("custPayPre",custPayPre)
				.addObject("custPayPre2",custPayPre2)
				.addObject("custPayPre3",custPayPre3)
				.addObject("custPayPre4",custPayPre4)
				.addObject("itemPlan",itemPlan)
				.addObject("custType",custType)
				// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//				.addObject("isProfitEdit",czybmService.hasAuthByCzybh(uc.getCzybh(),1093));
				.addObject("isProfitEdit",czybmService.hasGNQXByCzybh(uc.getCzybh(),"0208","毛利分析编辑"))
				.addObject("fourCustPay",fourCustPay)
				.addObject("payRemark",payRemark).addObject("contractStatus", contractStatus);
		
	}
	/**
	 * 
	 * 跳转毛利分析页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goProfitAnalyse_mlfx")
	public ModelAndView goProfitAnalyse_mlfx(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Map<String,Object> map=itemPlanService.findBySql_mlfx(itemPlan);
		ProfitAnalyseBean profitAnalyseBean=new ProfitAnalyseBean();
		BeanConvertUtil.mapToBean(map, profitAnalyseBean);
		return new ModelAndView("admin/design/itemPlan/tab_mlfx")
				.addObject("profitAnalyseBean",profitAnalyseBean);			
	}
	/**
	 * 
	 * 跳转毛利分析页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goProfitAnalyse_zcmlfx")
	public ModelAndView goProfitAnalyse_zcmlfx(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/design/itemPlan/tab_zcmlfx").addObject("customer",customer);		
	}
	/**
	 * 跳转打印页面
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping("/goPrint")
	public ModelAndView goPrintRZ(HttpServletRequest request, HttpServletResponse response, ItemPlan itemPlan){
		String path="admin/design/itemPlan/tab_printList";
		
		List<CustPlanReport> disabledPlanReports = Collections.emptyList();
		if("2".equals(itemPlan.getIsOutSet())) {
		    path+="TC";
		    disabledPlanReports = custPlanReportService.findPackageCustDisabledPlanReports();
		} else {
		    // 预留给清单客户使用
            // disabledPlanReports = custPlanReportService.findListCustDisabledPlanReports();
        }
		
		Customer customer = itemPlanService.get(Customer.class, itemPlan.getCustCode());
		
		if(customer != null){
			itemPlan.setContainInt(customer.getContainInt());
			itemPlan.setContainCup(customer.getContainCup());
			itemPlan.setContainMain(customer.getContainMain());
			itemPlan.setContainSoft(customer.getContainSoft());
			itemPlan.setContainMainServ(customer.getContainMainServ());
			itemPlan.setCustType(customer.getCustType().trim());
			if (itemPlanService.hasGiftItem(customer.getCode())){
				itemPlan.setGiftPK(1);
			}
			CustProfit custProfit=custProfitService.get(CustProfit.class, customer.getCode());
			if(custProfit==null){
				itemPlan.setIsHasCustProfit("0");
			}
		}
		
		CustPlanReport defaultReport = null;
		if ("2".equals(itemPlan.getIsOutSet())) {
		    defaultReport = custPlanReportService.findPackageCustPlanReport(customer.getCustType());
        } else {
            // 预留给清单客户使用
            // List<CustPlanReport> defaultReports = custPlanReportService.findListCustPlanReports(customer.getCustType());
        }
		
		String logoFile=cmpCustTypeService.getLogoFile(itemPlan.getCustCode());
		return new ModelAndView(path).addObject("itemPlan", itemPlan)
				.addObject("disabledPlanReports", disabledPlanReports)
				.addObject("defaultReport", defaultReport)
				.addObject("logoFile", logoFile)
				.addObject("customer", customer);
	}
	/**
	 * 
	 * 加载基础预算excel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<BaseItemBean> eUtils=new ExcelImportUtils<BaseItemBean>();
		String custCode="";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// 如果是普通 表单参数
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				
				if ("custCode".equals(fieldName)){
					custCode = fieldValue;
				}
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
	
		}
		titleList.add("区域名称");
		titleList.add("基础项目编号");
		titleList.add("数量");
		titleList.add("人工单价");
		titleList.add("材料单价");
		titleList.add("数量");
		titleList.add("备注");
		
		try {
			List<BaseItemBean> result=eUtils.importExcel(in, BaseItemBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			//Map reqpks=new HashMap();
			int i=0;
			for(BaseItemBean baseItemBean:result){
				i++;
				if(StringUtils.isNotBlank(baseItemBean.getError())){
					map.put("success", false);
					map.put("returnInfo", baseItemBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("fixareadescr", baseItemBean.getFixAreaPkDescr());
				data.put("custCode", custCode);
				data.put("lastupdatedby", uc.getCzybh());
				BaseItem baseItem=baseItemService.get(BaseItem.class, baseItemBean.getBaseItemCode());
				Customer customer=customerService.get(Customer.class,custCode);
				CustType custType=custTypeService.get(CustType.class,customer.getCustType());
				data.put("qty", baseItemBean.getQty());
				if("否".equals(baseItemBean.getIsOutSetDescr())){
					data.put("isoutset", "0");
					data.put("isoutsetdescr", "否");
				}else {
					data.put("isoutset", "1");
					data.put("isoutsetdescr", "是");
				}
				if(baseItem!=null){
					data.put("baseitemcode", baseItem.getCode());
					data.put("baseitemdescr",baseItem.getDescr());
					data.put("baseitemtype1", baseItem.getBaseItemType1().trim());
					data.put("tempmaterial", baseItem.getMaterial());
					data.put("tempunitprice", baseItem.getOfferPri());
					data.put("iscalmangefee", baseItem.getIsCalMangeFee());
					data.put("allowpricerise", baseItem.getAllowPriceRise());
					data.put("prjtype", baseItem.getPrjType());
					Uom uom=itemPlanService.get(Uom.class,baseItem.getUom());
					data.put("cost", baseItem.getCost());
					Xtdm xtdm=xtdmService.getByIdAndCbm("BASEITEMCAT",baseItem.getCategory());
					if(uom!=null) data.put("uom", uom.getDescr());
					data.put("categorydescr", xtdm.getNote());
					data.put("category", baseItem.getCategory());
					data.put("isfixprice", baseItem.getIsFixPrice());
				 	//如果该材料是固定价，则取数据库中的单价
				    if("1".equals(baseItem.getIsFixPrice())){
						data.put("unitprice", baseItem.getOfferPri());
						data.put("material", baseItem.getMaterial());
						if(baseItemBean.getQty()!=0.0){
							data.put("sumunitprice",Math.round(baseItem.getOfferPri()*baseItemBean.getQty()));
							data.put("summaterial",Math.round(baseItem.getMaterial()*baseItemBean.getQty()));
							data.put("lineamount",Math.round(baseItem.getOfferPri()*baseItemBean.getQty())+Math.round(baseItem.getMaterial()*baseItemBean.getQty()));	
						}else{
							data.put("sumunitprice",0.0);
							data.put("summaterial",0.0);
							data.put("lineamount",0);
						}
					}else{
						data.put("unitprice", baseItemBean.getUnitPrice());
						data.put("material", baseItemBean.getMaterial());
						if(baseItemBean.getQty()!=0.0){
							data.put("sumunitprice",Math.round(baseItemBean.getUnitPrice()*baseItemBean.getQty()));
							data.put("summaterial",Math.round(baseItemBean.getMaterial()*baseItemBean.getQty()));
							data.put("lineamount",Math.round(baseItemBean.getUnitPrice()*baseItemBean.getQty())+Math.round(baseItemBean.getMaterial()*baseItemBean.getQty()));	
						}else{
							data.put("sumunitprice",0.0);
							data.put("summaterial",0.0);
							data.put("lineamount",0);
						}
					}
					if(StringUtils.isNotBlank(baseItemBean.getRemark())){
						data.put("remark", baseItemBean.getRemark());
					}else{
						data.put("remark", baseItem.getRemark());
					}
				}
				if(StringUtils.isNotBlank(baseItemBean.getIsRequiredDescr())){
					if("是".equals(baseItemBean.getIsRequiredDescr())){
						data.put("isrequired", "1");
						data.put("isrequireddescr", "是");
					}else {
						data.put("isrequired", "0");
						data.put("isrequireddescr", "否");
					}
				}
				if(StringUtils.isNotBlank(baseItemBean.getIsMainItem())){
					if("否".equals(baseItemBean.getIsMainItem())){
						data.put("ismainitem", "0");
						data.put("ismainitemdescr", "否");
					}else {
						data.put("ismainitem", "1");
						data.put("ismainitemdescr", "是");
					}
				}
				data.put("autoqty", baseItemBean.getAutoQty());
				data.put("tempdtpk", baseItemBean.getTempDtPk());
				data.put("baseitemsetno", baseItemBean.getBaseItemSetNo());
				if(StringUtils.isNotBlank(baseItemBean.getCanModiQtyDescr())){
					if("是".equals(baseItemBean.getCanModiQtyDescr())){
						data.put("canmodiqty", "1");
						data.put("canmodiqtydescr", "是");
					}else {
						data.put("canmodiqty", "0");
						data.put("canmodiqtydescr", "否");
					}
				}
				if(StringUtils.isNotBlank(baseItemBean.getCanReplaceDescr())){
					if("是".equals(baseItemBean.getCanReplaceDescr())){
						data.put("canreplace", "1");
						data.put("canreplacedescr", "是");
					}else {
						data.put("canreplace", "0");
						data.put("canreplacedescr", "否");
					}
				}
				if(StringUtils.isNotBlank(baseItemBean.getBaseAlgorithmDescr())){
					String baseAlgorithmCode=baseAlgorithmService.getBaseAlgorithmByDescr(baseItemBean.getBaseAlgorithmDescr()); 
					if("".equals(baseAlgorithmCode)){
						data.put("isinvalid", "1");	
					}else {
						data.put("basealgorithm", baseAlgorithmCode);
						data.put("basealgorithmdescr", baseItemBean.getBaseAlgorithmDescr());
					}

				}
				if(baseItemBean.getGiftPk()>0){
					Gift gift=baseItemService.get(Gift.class,baseItemBean.getGiftPk());
					if(gift==null){
						data.put("isinvalid","1");	
					}else{
						data.put("giftdescr", gift.getDescr());
						data.put("isgift", "1");
						data.put("giftpk", baseItemBean.getGiftPk());	
					}	
				}
				if(!StringUtils.isNotBlank(baseItemBean.getFixAreaPkDescr())
					||!StringUtils.isNotBlank(baseItemBean.getBaseItemCode()))
					{
					data.put("isinvalid", "1");
				}else{
					data.put("isinvalid", "0");
				}
				data.put("dispseq", i);
				if("1".equals(data.get("isinvalid"))){
					data.put("isinvaliddescr", "无效,存在异常数据");
					map.put("hasInvalid", true);
				}else if(baseItem==null){
					data.put("isinvaliddescr", "无效的基础项目");
					map.put("hasInvalid", true);
					data.put("isinvalid","1");
				}else if("2,3,4".indexOf(baseItem.getCategory())!=-1&&"1".equals(custType.getType())){
					data.put("isinvaliddescr", "不能导入套餐报价");
					map.put("hasInvalid", true);
					data.put("isinvalid","1");
				}else if(baseItemBean.getQty()<0&&("4".indexOf(baseItem.getCategory())==-1
						&&"2".indexOf(baseItem.getCategory())==-1)&&"2".equals(custType.getType())){
					data.put("isinvaliddescr", "该项目数量不能为负");
					map.put("hasInvalid", true);
					data.put("isinvalid","1");
				}else if(("5,6".indexOf(baseItem.getCategory())!=-1||"0".equals(data.get("isoutset")))&&"1".equals(custType.getType())){
					data.put("isinvaliddescr", "不能导入套餐内材料");
					map.put("hasInvalid", true);
					data.put("isinvalid","1");
				}else if (StringUtils.isNotBlank(baseItem.getCustType())&&!baseItem.getCustType().equals(custType.getCode())){
						data.put("isinvaliddescr", "该材料不适用该客户类型");
						map.put("hasInvalid", true);
						data.put("isinvalid","1");
				}else if("0".equals(custType.getCanUseComBaseItem())&&StringUtils.isBlank(baseItem.getCustType())){
						data.put("isinvaliddescr", "该材料不适用该客户类型");
						map.put("hasInvalid", true);
						data.put("isinvalid","1");
				}else{
					data.put("isinvaliddescr", "有效");
					baseItemService.importDetail(data);
					if("1".equals(data.get("isinvalid").toString())){
						map.put("hasInvalid", true);
						data.put("isinvalid","1");
					}
				}
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "数据加载完成");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "当前操作使用了错误类型的值,请检查数值列是否包含非法字符!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	/**
	 * 加载excel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadYsExcel")
	@ResponseBody
	public Map<String, Object> loadYsExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<ItemPlanBean> eUtils=new ExcelImportUtils<ItemPlanBean>();
		String itemType1="";
		String custCode="";
		String isService="";
		String isCupboard="";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// 如果是普通 表单参数
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				if ("itemType1".equals(fieldName)){
					itemType1 = fieldValue.trim();
				}
				if ("custCode".equals(fieldName)){
					custCode = fieldValue;
				}
				if ("isService".equals(fieldName)){
					isService= fieldValue;
				}
				if ("isCupboard".equals(fieldName)){
					isCupboard = fieldValue;
				}
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
	
		}
		titleList.add("区域名称");
		if("JC".equals(itemType1)){
			titleList.add("集成成品");
			titleList.add("是否橱柜");
		}
		titleList.add("材料编号");
		titleList.add("预估施工量");
		titleList.add("数量");
		titleList.add("单价");
		titleList.add("折扣");
		titleList.add("其他费用");
		titleList.add("材料描述");
		
		try {
			Customer customer=customerService.get(Customer.class,custCode);
			List<ItemPlanBean> result=eUtils.importExcel(in, ItemPlanBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			int i=0;
			for(ItemPlanBean itemPlanBean:result){
				i++;
				if(StringUtils.isNotBlank(itemPlanBean.getError())){
					map.put("success", false);
					map.put("returnInfo", itemPlanBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				Map<String,Object> data=new HashMap<String, Object>();
				//新增套餐包
				if(StringUtils.isNotBlank(itemPlanBean.getItemSetDescr())){
					data.put("itemsetdescr", itemPlanBean.getItemSetDescr());
				}else{
					data.put("itemsetdescr", "");
				}
				data.put("fixareadescr", itemPlanBean.getFixAreaPkDescr());
				data.put("itemType1", itemType1);
				data.put("custCode", custCode);
				data.put("isservice", isService);
				data.put("iscupboard",isCupboard);
				data.put("lastupdatedby", uc.getCzybh());
				if("JC".equals(itemType1)){
					data.put("intproddescr", itemPlanBean.getIntProdPkDescr());
					if("否".equals(itemPlanBean.getIsCupboardDescr())){
						data.put("iscupboard", 0);
						data.put("iscupboarddescr", "否");
					}else {
						data.put("iscupboard", 1);
						data.put("iscupboarddescr", "是");
					}
				}else{
					data.put("intproddescr", "");
					data.put("iscupboard", 0);
				}
				if("ZC".equals(itemType1)){
					if (StringUtils.isNotBlank(itemPlanBean.getAlgorithm())){
						data.put("algorithm",itemPlanBean.getAlgorithm());	
					}
					if (StringUtils.isNotBlank(itemPlanBean.getAlgorithm())){
						Algorithm algorithm=itemPlanService.get(Algorithm.class, itemPlanBean.getAlgorithm());
						if(algorithm==null){
							data.put("isinvalid","1");
							data.put("isinvaliddescr", "无效，算法编号有误");
						}else{	
							data.put("algorithmdescr",algorithm.getDescr());
						}
						data.put("algorithm",itemPlanBean.getAlgorithm());	
					}
					if  (itemPlanBean.getDoorPk()!=null){
						data.put("doorpk",itemPlanBean.getDoorPk());
					}
					if  (itemPlanBean.getTempDtPk()!=null){
						data.put("tempdtpk",itemPlanBean.getTempDtPk());
					}
					if (StringUtils.isNotBlank(itemPlanBean.getPrePlanAreaDescr())){
						data.put("preplanareadescr",itemPlanBean.getPrePlanAreaDescr());	
					}
					
					if (StringUtils.isNotBlank(itemPlanBean.getCutType())){
						data.put("cuttype",itemPlanBean.getCutType());
						Xtdm xtdmCutType=xtdmService.getByIdAndCbm("CUTTYPE",itemPlanBean.getCutType());
						if(xtdmCutType==null){
								data.put("isinvalid","1");
								data.put("isinvaliddescr", "无效，切割类型有误");
						}else{	
							data.put("cuttypedescr",xtdmCutType.getNote());
						}
					}
					if (itemPlanBean.getAlgorithmPer()!=null){
						data.put("algorithmper",itemPlanBean.getAlgorithmPer());
					}else{
						data.put("algorithmper",1.0);
					}
					if (itemPlanBean.getAlgorithmDeduct()!=null){
						data.put("algorithmdeduct",itemPlanBean.getAlgorithmDeduct());
					}else{
						data.put("algorithmdeduct",0.0);
					}
				}
				Item item=new Item();
				item.setCode(itemPlanBean.getItemCode());
				item.setItemType1(itemType1);
				Item item2=null;
				ItemType2 itemType2=null;
				ItemType3 itemType3=null;
				data.put("projectqty", itemPlanBean.getProjectQty());
				data.put("qty", itemPlanBean.getQty());
				data.put("autoqty", itemPlanBean.getAutoQty());
				if("否".equals(itemPlanBean.getIsOutSetDescr())){
					data.put("isoutset", "0");
					data.put("isoutsetdescr", "否");
					if(itemPlanBean.getCustTypeItemPk()!=null&&itemPlanBean.getCustTypeItemPk()!=0){
						if(custTypeItemService.hasItem(itemPlanBean.getCustTypeItemPk(), customer.getCustType().trim(), itemType1)){
							data.put("custtypeitempk", itemPlanBean.getCustTypeItemPk());
							CustTypeItem custTypeItem=custTypeItemService.get(CustTypeItem.class, itemPlanBean.getCustTypeItemPk());
							//套餐内材料升级价和结算价取套餐材料信息表。描述取套餐升级描述
							item.setCode(custTypeItem.getItemCode());
							item2 = itemService.getItemByCondition(item);
							if(item2!=null){
								item2.setPrice(custTypeItem.getPrice());
								item2.setRemark(custTypeItem.getRemark());
								if(!"F".equals(item2.getExpired())){
									data.put("isinvalid", "1");
									data.put("isinvaliddescr", "材料信息过期");	
								}
							}
							data.put("projectcost", custTypeItem.getProjectCost());
						}
					}else if(StringUtils.isNotBlank(itemPlanBean.getItemCode())){
						Integer custTypeItemPk=custTypeItemService.getUniquePk(itemPlanBean.getItemCode(), customer.getCustType().trim(), itemType1,itemPlanBean.getRemarks());
						if(custTypeItemPk!=null){
							data.put("custtypeitempk", custTypeItemPk);
							CustTypeItem custTypeItem=custTypeItemService.get(CustTypeItem.class, custTypeItemPk);
							//套餐内材料升级价和结算价取套餐材料信息表。描述取套餐升级描述
							item.setCode(custTypeItem.getItemCode());
							item2=itemService.getItemByCondition(item);
							if(item2!=null){
								item2.setPrice(custTypeItem.getPrice());
								item2.setRemark(custTypeItem.getRemark());
							}
							data.put("projectcost", custTypeItem.getProjectCost());
						}
					}	
				}else {
					data.put("isoutset", "1");
					data.put("isoutsetdescr", "是");
					item2=itemService.getItemByCondition(item);
					if(item2!=null){
						data.put("projectcost", item2.getProjectCost());	
					}
				}
				if(item2!=null){
					if(StringUtils.isNotBlank(item2.getItemType2())){
						itemType2=itemType2Service.get(ItemType2.class, item2.getItemType2());
						data.put("itemtype2", item2.getItemType2().trim());
					}
					if(StringUtils.isNotBlank(item2.getItemType3())){
						itemType3=itemType3Service.get(ItemType3.class, item2.getItemType3());
						data.put("itemtype3", item2.getItemType3().trim());
						
					}
					data.put("expired", item2.getExpired());
					data.put("itemcode", item2.getCode());
					data.put("itemdescr", item2.getDescr());
					data.put("cost", item2.getCost());
					data.put("oldprojectcost", item2.getProjectCost());
					Uom uom=itemPlanService.get(Uom.class,item2.getUom());
					if(uom!=null){
						data.put("uom", uom.getDescr());
						data.put("uomdescr", uom.getDescr());
					}
					data.put("commitype", item2.getCommiType());
					//如果该材料是固定价，则取数据库中的单价
					if("1".equals(item2.getIsFixPrice())){
						data.put("unitprice", item2.getPrice());
					}else{
						data.put("unitprice", itemPlanBean.getUnitPrice());
					}
					//if("0".equals(data.get("isoutset"))) data.put("unitprice", 0);
					if(StringUtils.isNotBlank(itemPlanBean.getRemarks())){
						data.put("remark", itemPlanBean.getRemarks());
					}else{
						data.put("remark", item2.getRemark());
					}
					if(itemPlanBean.getQty()!=0.0){
						Double beflineamount=Math.round(Double.parseDouble(data.get("unitprice").toString())*itemPlanBean.getQty()*100)/100.0;
						Double sign = 1d;
						if(beflineamount < 0) {
							sign = -1d;
						}
						data.put("beflineamount",beflineamount);
						data.put("tmplineamount",Math.round(Math.abs(beflineamount*itemPlanBean.getMarkup()/100)) * sign);
						data.put("lineamount",Math.round(Math.round(Math.abs(beflineamount*itemPlanBean.getMarkup()/100)) * sign + itemPlanBean.getProcessCost()));	
					}else{
						data.put("beflineamount",0.0);
						data.put("tmplineamount",0.0);
						data.put("lineamount",Math.round(itemPlanBean.getProcessCost()));
					}
				}
				data.put("markup", itemPlanBean.getMarkup());
				data.put("processcost", itemPlanBean.getProcessCost());
				if(!StringUtils.isNotBlank(itemPlanBean.getFixAreaPkDescr())
					||!StringUtils.isNotBlank(itemPlanBean.getItemCode())	
					||("JC".equals(itemType1)&&!StringUtils.isNotBlank(itemPlanBean.getIntProdPkDescr()))
					){
					data.put("isinvalid", 1);
				}else{
					data.put("isinvalid", 0);
				}
				if(itemPlanBean.getGiftPk()>0){
					Gift gift=itemPlanService.get(Gift.class,itemPlanBean.getGiftPk());
					if(gift==null){
						data.put("isinvalid","1");	
					}else{
						data.put("giftdescr", gift.getDescr());
						data.put("isgift", "1");
						data.put("giftpk", itemPlanBean.getGiftPk());	
					}	
				}
				if(itemPlanBean.getSupplPromItemPk()!=null&&itemPlanBean.getSupplPromItemPk()>0){
						SupplPromItem supplPromItem=itemPlanService.get(SupplPromItem.class,Integer.toString(itemPlanBean.getSupplPromItemPk()));
						if(supplPromItem==null){
							data.put("isinvalid","1");	
						}else{
							data.put("supplpromitempk", itemPlanBean.getSupplPromItemPk());	
						}	
				}
				data.put("dispseq", i);
				if("1".equals(data.get("isinvalid"))){
					if("".equals(data.get("isinvaliddescr"))){
						data.put("isinvaliddescr", "无效,存在异常数据");
					}
					map.put("hasInvalid", true);
				}else if(item2==null){
					data.put("isinvaliddescr", "无效的材料编号");
					if("否".equals(itemPlanBean.getIsOutSetDescr()))
						data.put("isinvaliddescr", "无效的套餐材料信息编号");
					map.put("hasInvalid", true);
					data.put("isinvalid","1");
				}else if("0".equals(data.get("isoutset"))&&"1".equals(customer.getCustType().trim())){
					data.put("isinvaliddescr", "无法导入套餐内材料预算");
					map.put("hasInvalid", true);
					data.put("isinvalid","1");
				}else if(itemPlanBean.getMarkup()<=0){
					data.put("isinvaliddescr", "折扣必须>0");
					map.put("hasInvalid", true);
					data.put("isinvalid","1");
				}else if(!itemService.isSplCompanyItem(itemPlanBean.getItemCode(), custCode)){
						data.put("isinvalid", 1);
						data.put("isinvaliddescr", "无效,不是集团供应商或者供应公司为客户所在公司的材料");
						map.put("hasInvalid", true);
				}else{
					data.put("isinvaliddescr", "有效");
					itemPlanService.importDetail(data);
					if("1".equals(data.get("isinvalid").toString())){
						map.put("hasInvalid", true);
					}
				}
				if(itemType2!=null){
					data.put("itemtype2descr", itemType2.getDescr());
				}
				if(itemType3!=null){
					data.put("itemtype3descr", itemType3.getDescr());
				}
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "数据加载完成");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "当前操作使用了错误类型的值,请检查数值列是否包含非法字符!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	/**
	 * 构建合并临时JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/goTmpJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getTmpJqGrid(HttpServletRequest request,
			HttpServletResponse response,String params,String orderBy) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		JSONArray jsonArray = JSONArray.fromObject(params); 
		@SuppressWarnings("rawtypes")
		List<Map<String,Object>> list = new ArrayList(jsonArray); 
		if(orderBy!=null){
			String[] orders=orderBy.split(",");
			for(String order:orders){
				Collections.sort(list, new ListCompareUtil(order));
			}
		}	
		if(page.isAutoCount()){
			page.setTotalCount(list.size());
		}
		page.setPageSize(10000);
		page.setResult(list);	
		return new WebPage<Map<String,Object>>(page);

	}
	/** 
	 * 跳转主材预报价模板模板页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPrePlanTemp")
	public ModelAndView goPrePlanTemp(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/itemPlan_prePlanTemp").addObject("itemPlan",itemPlan);
	}
	/**
	 *获取预报价数量、预估施工量、其他费用
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPrePlanQty")
	@ResponseBody
	public Map<String, Object>  getAjaxDetail(HttpServletRequest request,HttpServletResponse response,ItemPlan itemPlan){
		logger.debug("ajax获取预报价数量、预估施工量、其他费用");
		Map<String,Object> prePlanQty=itemPlanService.findBySql_GetItemPlanQty(itemPlan);
		return prePlanQty;
	}
	
	/**
	 * 主材快速预算重新生成
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doRegenFromPrePlanTemp")
	public void doRegenFromPrePlanTemp(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doRegenFromPrePlanTemp(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 基础预算重新生成
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doRegenBasePlanFromPrePlanTemp")
	public void doRegenBasePlanFromPrePlanTemp(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doRegenBasePlanFromPrePlanTemp(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 *基础预算根据算法获取
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getBasePlanQty")
	@ResponseBody
	public Map<String, Object> getBasePlanQty(HttpServletRequest request,HttpServletResponse response,BaseItemPlan baseItemPlan){
		logger.debug("ajax获取预报价数量、预估施工量、其他费用");
		Map<String,Object> basePlanQty=itemPlanService.findBySql_GetBaseItemPlanQty(baseItemPlan);
		return basePlanQty;
	}
	/**
	 * 客户空间列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/getAddPlanAreaJqgrid")
	@ResponseBody 
	public WebPage<Map<String,Object>> getAddPlanAreaJqgrid(HttpServletRequest request,
			HttpServletResponse response, PrePlan prePlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prePlanAreaService.findPlanAreaJqgridBySql(page, prePlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getDoorWindList")
	@ResponseBody 
	public WebPage<Map<String,Object>> getDoorWindList(HttpServletRequest request,
			HttpServletResponse response, Integer pk) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prePlanAreaService.findDoorWindJqgridBySql(page, pk);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPrePlanArea")
	public ModelAndView goPrePlanArea(HttpServletRequest request,
			HttpServletResponse response,String custCode,String module) throws Exception {
		Customer customer=null;
		PrePlanArea prePlanArea=new PrePlanArea();
		String tempNo="";
		String designManDescr="";
			
		if(StringUtils.isNotBlank(custCode)){
			prePlanArea.setCustCode(custCode);
			prePlanArea.setM_umState("I");
			this.prePlanAreaService.doAutoAddDefaultArea(prePlanArea);
			customer=customerService.get(Customer.class, custCode);
			tempNo=prePlanAreaService.getNoByCustCode(custCode);
			if(StringUtils.isNotBlank(customer.getDesignMan())){
				designManDescr=employeeService.get(Employee.class, customer.getDesignMan()).getNameChi();
			}
		}
		Boolean canUpdateArea= prePlanAreaService.getCanUpdateArea(custCode);
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_prePlanArea")
		.addObject("customer",customer)
		.addObject("tempNo",tempNo)
		.addObject("designManDescr",designManDescr)
		.addObject("module",module).addObject("canUpdateArea", canUpdateArea);
	}
	
	@RequestMapping("/goGiftManage")
	public ModelAndView goGiftManage(HttpServletRequest request,
			HttpServletResponse response,String custCode) throws Exception {
		Customer customer=null;
		CustGift custGift = new CustGift();
		if(StringUtils.isNotBlank(custCode)){
			customer=customerService.get(Customer.class, custCode);
			custGift.setCustCode(custCode);
			custGift.setM_umState("P");
			Result result = custGiftService.doImportCustGift(custGift);
		}
		
		String contractStatus = customerService.getContractStatus(customer);
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_giftManage")
		.addObject("customer",customer).addObject("contractStatus", contractStatus);
	}
	
	@RequestMapping("/goCustGiftSave")
	public ModelAndView goCustGiftSave(HttpServletRequest request,
			HttpServletResponse response,String custCode) throws Exception {
		Customer customer=null;
		double wallArea = 0.00;
		
		if(StringUtils.isNotBlank(custCode)){
			customer=customerService.get(Customer.class, custCode);
			wallArea = baseItemPlanService.calculateWallArea(custCode);
		}
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_custGiftSave")
		.addObject("customer",customer).addObject("wallArea", wallArea);
	}
	
	@ResponseBody
	@RequestMapping("/getMaxDiscExpr")
	public double getMaxDiscExpr(HttpServletRequest request,
			HttpServletResponse response,Integer pk,double area,String custType){
		double maxDiscExpr = 0.0;
		maxDiscExpr = itemPlanService.getMaxDiscExpr(pk,area,custType);
		
		return maxDiscExpr;
	}
	
	@RequestMapping("/goCommitOA")
	public ModelAndView goCommitOA(HttpServletRequest request,
			HttpServletResponse response,CustGift custGift) throws Exception {
		custGift.setDetailJson(request.getParameter("detailJson"));
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_custGiftCommit")
		.addObject("custGift",custGift);
	}
	
	@RequestMapping("/goWfProcApply")
	public ModelAndView goWfProcApply(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("custCode") String custCode) {
		// 根据流程标识获取最后一个版本的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey("custGiftApp")
			.latestVersion()
			.singleResult();
		
		String url = FileUploadUtils.DOWNLOAD_URL;
		
		// 获取对应的流程
        WfProcess wfProcess = this.wfProcInstService.getWfProcessByProcKey(processDefinition.getKey());
		String wfProcNo = "";
		if(wfProcess != null){
			wfProcNo = wfProcess.getNo();
			wfProcess.setRemarks((wfProcess.getRemarks()==null?"":wfProcess.getRemarks()).replace("\r\n", "<br/>"));
		}
		
		Employee employee = wfProcInstService.get(Employee.class, this.getUserContext(request).getEmnum());
		Customer customer = wfProcInstService.get(Customer.class, custCode);
		// 获取明细 根据类型合计
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String, Object> detailJson = new HashMap<String, Object>();
		Map<String , Object> detailMap = new HashMap<String, Object>();

		String discRemarks = "";
		List<Map<String , Object>> custAgrement = itemPlanService.getCustAgreement(customer);
		
		if(custAgrement != null){
			for(int i = 0; i < custAgrement.size(); i++){
				if(StringUtils.isNotBlank(discRemarks)){
					discRemarks = discRemarks + ",\r\n" + custAgrement.get(i).get("detail").toString();
				} else {
					discRemarks = custAgrement.get(i).get("detail").toString();
				}
			}
		}
		/*
		String json = request.getParameter("detailJson");
		JSONArray jsonArray = null;
		if(StringUtils.isNotBlank(json)){
			jsonArray = JSONArray.fromObject(json);
			dataList = new ArrayList(jsonArray); 
			for(int i = 0; i < dataList.size(); i++){
				if(StringUtils.isNotBlank(discRemarks)){
					discRemarks = discRemarks + ",\r\n" + dataList.get(i).get("descr").toString()+",优惠金额："+dataList.get(i).get("discamount").toString();
				} else {
					discRemarks = dataList.get(i).get("descr").toString()+",优惠金额："+dataList.get(i).get("discamount").toString();
				}
			}
		}*/
		detailMap.put("tWfCust_CustGiftApp", 1);
		// 初始化数据
		if(employee != null){
			Department department = new Department();
			department = employeeService.get(Department.class, employee.getDepartment());
			detailJson.put("fp__tWfCust_CustGiftApp__0__EmpCode", this.getUserContext(request).getCzybh());
			detailJson.put("fp__tWfCust_CustGiftApp__0__EmpName", employee.getNameChi());
			 
			if(department != null){
				Map<String, Object> cmpData = wfProcInstService.getEmpCompany(employee.getDepartment());
    			if(cmpData != null){
    				employee.setCmpCode(cmpData.get("Code").toString());
    				employee.setCmpDescr(cmpData.get("CmpDescr").toString());
    			}
			}
		}
		
		if(customer != null){
			detailJson.put("fp__tWfCust_CustGiftApp__0__CustCode", customer.getCode());
			detailJson.put("address", customer.getAddress());
			detailJson.put("fp__tWfCust_CustGiftApp__0__CustDescr", customer.getDescr());
			detailJson.put("fp__tWfCust_CustGiftApp__0__Address", customer.getAddress());
			detailJson.put("fp__tWfCust_CustGiftApp__0__ContractFee", customer.getContractFee());
			if(StringUtils.isNotBlank(customer.getRepClause())){
				if(StringUtils.isNotBlank(discRemarks)){
					discRemarks = discRemarks + ",\r\n" + customer.getRepClause();;
				} else {
					discRemarks = customer.getRepClause();;
				}
			}
			detailJson.put("fp__tWfCust_CustGiftApp__0__DiscountRemarks", discRemarks);
		}
		
		String startMan = this.getUserContext(request).getCzybh();
		
		Xtcs xtcs = wfProcInstService.get(Xtcs.class, "CMPNYCODE");
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_apply")
			.addObject("processDefinition", processDefinition)
			.addObject("processDefinitionKey", processDefinition.getId())
			.addObject("wfProcNo",wfProcNo)
			.addObject("applyPage", processDefinition.getKey()+".jsp")
			.addObject("m_umState", "A")
			.addObject("datas", detailJson)
			.addObject("detailJson", JSONObject.toJSONString(detailJson))
			.addObject("processInstanceId", "processInstanceId")
			.addObject("detailList",JSONObject.toJSONString(detailMap))
			.addObject("startMan", startMan)
			.addObject("wfProcess", wfProcess)
			.addObject("url", url)
			.addObject("cmpcode", xtcs.getQz())
			.addObject("employee", employee).addObject("activityId", "startevent");
	}
	
	@RequestMapping("/goViewProcTrack")
	public ModelAndView goViewProcTrack(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_viewProcTrack").addObject("customer", customer);
	}
	
	@RequestMapping("/goCustGiftUpdate")
	public ModelAndView goCustGiftUpdate(HttpServletRequest request,
			HttpServletResponse response,CustGift custGift) throws Exception {
		Gift gift = null;
		Customer customer=null;
		
		if(StringUtils.isNotBlank(custGift.getCustCode())){
			customer=customerService.get(Customer.class, custGift.getCustCode());
		}
		if(custGift.getpK() != null){
			custGift = custGiftService.get(CustGift.class, custGift.getpK());
			custGift.setM_umState("M");
		}
		if(custGift != null){
			gift = custGiftService.get(Gift.class, custGift.getGiftPK());	
		}
		if(gift != null ){
			custGift.setGiftDescr( gift.getDescr());
			custGift.setDiscPer(gift.getDiscPer());
		}
		
		double wallArea = 0.00;
		if (customer != null) {
            wallArea = baseItemPlanService.calculateWallArea(customer.getCode());
        }
		
        return new ModelAndView("admin/design/itemPlan/itemPlan_custGiftUpdate")
                .addObject("custGift", custGift)
                .addObject("customer", customer)
                .addObject("wallArea", wallArea);
	}
	
	@RequestMapping("/goCustGiftView")
	public ModelAndView goCustGiftView(HttpServletRequest request,
			HttpServletResponse response,CustGift custGift) throws Exception {
		Gift gift = null;
		Customer customer=null;
		
		if(StringUtils.isNotBlank(custGift.getCustCode())){
			customer=customerService.get(Customer.class, custGift.getCustCode());
		}
		if(custGift.getpK() != null){
			custGift = custGiftService.get(CustGift.class, custGift.getpK());
		}
		if(custGift != null){
			gift = custGiftService.get(Gift.class, custGift.getGiftPK());	
		}
		if(gift != null ){
			custGift.setDiscPer(gift.getDiscPer());
			custGift.setGiftDescr( gift.getDescr());
		}
		
		double wallArea = 0.00;
        if (customer != null) {
            wallArea = baseItemPlanService.calculateWallArea(customer.getCode());
        }
		
		custGift.setM_umState("V");
        return new ModelAndView("admin/design/itemPlan/itemPlan_custGiftUpdate")
                .addObject("custGift", custGift)
                .addObject("customer", customer)
                .addObject("wallArea", wallArea);
	}
	
	@RequestMapping("/goAddItemPlan")
	public ModelAndView goAddItemPlan(HttpServletRequest request,
			HttpServletResponse response,CustGift custGift,String url) throws Exception {
		Gift gift = null;
		Customer customer=null;
		
		if(StringUtils.isNotBlank(custGift.getCustCode())){
			customer=customerService.get(Customer.class, custGift.getCustCode());
		}
		if(custGift.getGiftPK() != null){
			gift = custGiftService.get(Gift.class, custGift.getGiftPK());	
		}
		if(gift != null ){
			custGift.setGiftDescr( gift.getDescr());
		}
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_addItemPlan")
		.addObject("custGift",custGift).addObject("customer",customer).addObject("url", url);
	}
	
	/**
	 * 向上 
	 * @param request
	 * @param response
	 * @param custGift
	 */
	@RequestMapping("/doCustGiftUpward")
	public void doCustGiftUpward(HttpServletRequest request,HttpServletResponse response,CustGift custGift){
		logger.debug("赠送项目新增开始");
		
		try {
			custGift.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custGift.setLastUpdate(new Date());
			custGift.setExpired("F");
			custGift.setActionLog("ADD");
			custGift.setM_umState("V");
			Result result = custGiftService.doUpwardCustGift(custGift);
			if ("操作成功".equals(result.getInfo())){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	/**
	  * 向下
	  * @param request
	  * @param response
	  * @param custGift
	 */
	@RequestMapping("/doCustGiftDownward")
	public void doCustGiftDownward(HttpServletRequest request,HttpServletResponse response,CustGift custGift){
		logger.debug("赠送项目向下");
		
		try {
			custGift.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custGift.setLastUpdate(new Date());
			custGift.setExpired("F");
			custGift.setActionLog("EDIT");
			custGift.setM_umState("B");
			Result result = custGiftService.doDownwardCustGift(custGift);
			if ("操作成功".equals(result.getInfo())){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doImportCustGift")
	public void doImportCustGift(HttpServletRequest request,HttpServletResponse response,CustGift custGift){
		logger.debug("赠送项目新增开始");
		
		try {
			custGift.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custGift.setLastUpdate(new Date());
			custGift.setExpired("F");
			custGift.setActionLog("ADD");
			custGift.setM_umState("I");
			Result result = custGiftService.doImportCustGift(custGift);
			if ("操作成功".equals(result.getInfo())){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doCustGiftSave")
	public void doCustGiftSave(HttpServletRequest request,HttpServletResponse response,CustGift custGift){
		logger.debug("赠送项目新增开始");
		
		try {
			custGift.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custGift.setLastUpdate(new Date());
			custGift.setExpired("F");
			custGift.setActionLog("ADD");
			custGift.setM_umState("A");
			Result result = custGiftService.doCustGiftSave(custGift);
			if ("操作成功".equals(result.getInfo())){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doCustGiftUpdate")
	public void doCustGiftUpdate(HttpServletRequest request,HttpServletResponse response,CustGift custGift){
		logger.debug("赠送项目删除");
		
		try {
			custGift.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custGift.setLastUpdate(new Date());
			custGift.setExpired("F");
			custGift.setActionLog("EDIT");
			custGift.setM_umState("U");
			Result result = custGiftService.doCustGiftUpdate(custGift);
			if ("操作成功".equals(result.getInfo())){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doCustGiftDel")
	public void doCustGiftDel(HttpServletRequest request,HttpServletResponse response,CustGift custGift){
		logger.debug("赠送项目删除");
		
		try {
			custGift.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custGift.setLastUpdate(new Date());
			custGift.setExpired("F");
			custGift.setActionLog("ADD");
			custGift.setM_umState("D");
			Result result = custGiftService.doCustGiftDel(custGift);
			if ("操作成功".equals(result.getInfo())){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/goAddPlanArea")
	public ModelAndView goAddPlanArea(HttpServletRequest request,
			HttpServletResponse response,PrePlanArea prePlanArea) throws Exception {

		return new ModelAndView("admin/design/itemPlan/itemPlan_addPlanArea")
		.addObject("prePlanArea",prePlanArea);
	}
	
	@RequestMapping("/goPlanAreaCopy")
	public ModelAndView goPlanAreaCopy(HttpServletRequest request,
			HttpServletResponse response,PrePlanArea prePlanArea) throws Exception {

		return new ModelAndView("admin/design/itemPlan/itemPlan_prePlanCopy")
		.addObject("prePlanArea",prePlanArea);
	}
	
	@RequestMapping("/goBatchAddPlanArea")
	public ModelAndView goBatchAddPlanArea(HttpServletRequest request,
			HttpServletResponse response,PrePlanArea prePlanArea) throws Exception {
		
		List<Map<String, Object>> list=itemPlanService.getFixAreaTypes();
		String fixAreas="";
		String fixTypes="";
		for(int i=0;i<list.size();i++){
			fixAreas=fixAreas+list.get(i).get("descr").toString()+(i==list.size()-1?"":",");
			fixTypes=fixTypes+list.get(i).get("code").toString()+(i==list.size()-1?"":",");
		}
		
		return new ModelAndView("admin/design/itemPlan/itemPlan_addBatchPlanArea")
		.addObject("prePlanArea",prePlanArea)
		.addObject("fixAreas",fixAreas)
		.addObject("fixTypes",fixTypes);
	}
	
	@RequestMapping("/goUpdatePlanArea")
	public ModelAndView goUpdatePlanArea(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("编辑空间");
		String module="";
		if(StringUtils.isNotBlank(request.getParameter("module"))){
			module=request.getParameter("module");
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		int doorNum=0;
		int windNum=5;
		Map<String , String[]> map =request.getParameterMap();
		List<Map<String , Object>> list = prePlanAreaService.findDoorWindJqgridBySql(page, Integer.parseInt(map.get("pk")[0].toString())).getResult();
		
		Map<String , Object> dMap= new HashMap<String, Object>();
		Map<String , Object> wMap= new HashMap<String, Object>();
		for(int i =0 ;i< list.size();i++){
			if("1".equals(list.get(i).get("type").toString())){
				doorNum++;
				
				dMap.put("pk"+doorNum, list.get(i).get("pk"));
				dMap.put("height"+doorNum, list.get(i).get("height"));
				dMap.put("length"+doorNum, list.get(i).get("length"));
			}else {
				windNum++;
				wMap.put("pk"+windNum, list.get(i).get("pk"));
				wMap.put("height"+windNum, list.get(i).get("height"));
				wMap.put("length"+windNum, list.get(i).get("length"));
			}
		}

		return new ModelAndView("admin/design/itemPlan/itemPlan_updatePlanArea").addObject("map", map)
				.addObject("wMap",wMap).addObject("dMap",dMap)
				.addObject("module",module);
	}
	
	@RequestMapping("/goViewPlanArea")
	public ModelAndView goViewPlanArea(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("编辑空间");
		Map<String , String[]> map =request.getParameterMap();
		return new ModelAndView("admin/design/itemPlan/itemPlan_viewPlanArea").addObject("map", map);
	}
	
	@RequestMapping("/goAddDoorWind")
	public ModelAndView goAddDoorWind(HttpServletRequest request,
			HttpServletResponse response,PrePlanArea prePlanArea) throws Exception {

		return new ModelAndView("admin/design/itemPlan/itemPlan_addDoorWind")
		.addObject("prePlanArea",prePlanArea);
	}
	
	@RequestMapping("/goUpdateDoorWind")
	public ModelAndView goUpdateDoorWind(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("编辑空间");
		Map<String , String[]> map =request.getParameterMap();
		return new ModelAndView("admin/design/itemPlan/itemPlan_updateDoorWind").addObject("map", map);
	}
	
	@RequestMapping("/doSavePrePlanArea")
	public void doSavePrePlanArea(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("新增空间开始");
		try{
			prePlanArea.setM_umState("A");
			prePlanArea.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			prePlanArea.setDetailJson(detailJson);
			Result result =this.prePlanAreaService.doSave(prePlanArea) ;

			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增空间失败");
		}
	}
	
	@RequestMapping("/doCopyPlanArea")
	public void doCopyPlanArea(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("新增空间开始");
		try{
			prePlanArea.setM_umState("C");
			prePlanArea.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			prePlanArea.setDetailJson(detailJson);
			Result result =this.prePlanAreaService.doSave(prePlanArea) ;

			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增空间失败");
		}
	}
	
	@RequestMapping("/doBatchAddPrePlanArea")
	public void doBatchAddPrePlanArea(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("批量新增空间开始");
		try{
			prePlanArea.setM_umState("P");
			prePlanArea.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			prePlanArea.setDetailJson(detailJson);

			Result result =this.prePlanAreaService.doSave(prePlanArea) ;

			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"批量新增成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增空间失败");
		}
	}
	
	@RequestMapping("/doUpdatePrePlanArea")
	public void doUpdatePrePlanArea(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("修改空间开始");
		try{
			prePlanArea.setM_umState("M");
			prePlanArea.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			prePlanArea.setDetailJson(detailJson);
			Result result =this.prePlanAreaService.doUpdate(prePlanArea) ;

			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改空间失败");
		}
	}
	
	@RequestMapping("/doUpward")
	public void doUpward(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("修改空间开始");
		try{
			prePlanArea.setM_umState("U");
			prePlanArea.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result =this.prePlanAreaService.doUpward(prePlanArea) ;

			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改空间失败");
		}
	}
	
	@RequestMapping("/doDownward")
	public void doDownward(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("修改空间开始");
		try{
			prePlanArea.setM_umState("B");
			prePlanArea.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result =this.prePlanAreaService.doUpward(prePlanArea) ;

			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改排序失败");
		}
	}
	
	@RequestMapping("/doSaveCustArea")
	public void doSaveCustArea(HttpServletRequest request ,
			HttpServletResponse response ,Customer customer){
		logger.debug("保存楼盘面积");
		try{
			Customer ct =new Customer();
			if(StringUtils.isNotBlank(customer.getCode())){
				ct=customerService.get(Customer.class, customer.getCode());
				if("4".equals(ct.getStatus())){
					if(customer.getCarryFloor()==null){
						customer.setCarryFloor(0.0);
					}
					customer.setLastUpdatedBy(getUserContext(request).getCzybh());
					this.customerService.updateCarryFloor(customer);
				}else{
					ct.setArea(customer.getArea()==null?0:customer.getArea());
					ct.setWallArea6(customer.getWallArea6());
					ct.setWallArea12(customer.getWallArea12());
					ct.setWallArea24(customer.getWallArea24());
					ct.setWallArea18(customer.getWallArea18());
					ct.setInstallElev(customer.getInstallElev());
					ct.setCarryFloor(customer.getCarryFloor()==null?0.0:customer.getCarryFloor());
					ct.setLastUpdate(new Date());
					ct.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					ct.setActionLog("Edit");
					this.customerService.update(ct);
				}
			}
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改面积失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/doEditArea")
	public void doEditArea(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("编辑空间信息");
		PrePlanDoor prePlanDoor=new PrePlanDoor();
		PrePlanDoor prePlanwind=new PrePlanDoor();
		PrePlanArea ppa=null;
		Customer customer=null;
		try {
			if(StringUtils.isNotBlank(prePlanArea.getCustCode())
					&&StringUtils.isBlank(prePlanArea.getModule())){
				customer=customerService.get(Customer.class, prePlanArea.getCustCode());
				if("4".equals(customer.getStatus()) && StringUtils.isBlank(request.getParameter("module")) 
						&& !prePlanAreaService.getCanUpdateArea(prePlanArea.getCustCode())){
					ServletUtils.outPrintFail(request, response, "4");
					return;
				}
			}
			if(prePlanArea.getPk()!=null){
				ppa=prePlanAreaService.get(PrePlanArea.class, prePlanArea.getPk());
				ppa.setArea(prePlanArea.getArea()==null?ppa.getArea():prePlanArea.getArea());
				ppa.setPerimeter(prePlanArea.getPerimeter()==null?ppa.getPerimeter():prePlanArea.getPerimeter());
				ppa.setHeight(prePlanArea.getHeight()==null?ppa.getHeight():prePlanArea.getHeight());
				ppa.setShowerLength(prePlanArea.getShowerLength()==null?ppa.getShowerLength():prePlanArea.getShowerLength());
				ppa.setShowerWidth(prePlanArea.getShowerWidth()==null?ppa.getShowerWidth():prePlanArea.getShowerWidth());
				ppa.setBeamLength(prePlanArea.getBeamLength()==null?ppa.getBeamLength():prePlanArea.getBeamLength());
				ppa.setLastUpdate(new Date());
				ppa.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				ppa.setActionLog("Edit");
				this.prePlanAreaService.update(ppa);
			}
			if(prePlanArea.getP1pk()!=null){
				prePlanDoor=prePlanAreaService.get(PrePlanDoor.class, prePlanArea.getP1pk());
				prePlanDoor.setHeight(prePlanArea.getHeight1()==null?prePlanDoor.getHeight():prePlanArea.getHeight1());
				prePlanDoor.setLength(prePlanArea.getLength1()==null?prePlanDoor.getLength():prePlanArea.getLength1());
				prePlanDoor.setLastUpdate(new Date());
				prePlanDoor.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				prePlanDoor.setActionLog("Edit");
				this.prePlanAreaService.update(prePlanDoor);
			}
			if(prePlanArea.getP2pk()!=null){
				prePlanwind=prePlanAreaService.get(PrePlanDoor.class, prePlanArea.getP2pk());
				prePlanwind.setHeight(prePlanArea.getHeight2()==null?prePlanwind.getHeight():prePlanArea.getHeight2());
				prePlanwind.setLength(prePlanArea.getLength2()==null?prePlanwind.getLength():prePlanArea.getLength2());
				prePlanwind.setLastUpdate(new Date());
				prePlanwind.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				prePlanwind.setActionLog("Edit");
				this.prePlanAreaService.update(prePlanwind);
			}
			if(prePlanArea.getP1pk()==null && (prePlanArea.getHeight1()!=null || prePlanArea.getLength1()!=null )){
				prePlanDoor.setHeight(prePlanArea.getHeight1()==null?0.0:prePlanArea.getHeight1());
				prePlanDoor.setLength(prePlanArea.getLength1()==null?0.0:prePlanArea.getLength1());
				prePlanDoor.setLastUpdate(new Date());
				prePlanDoor.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				prePlanDoor.setExpired("F");
				prePlanDoor.setAreaPk(prePlanArea.getPk());
				prePlanDoor.setActionLog("Add");
				prePlanDoor.setType("1");
				this.prePlanAreaService.save(prePlanDoor);
				ServletUtils.outPrintSuccess(request, response,prePlanAreaService.getDWMaxPk("1",prePlanArea.getPk()));
				return;
			}
			
			if(prePlanArea.getP2pk()==null && (prePlanArea.getHeight2()!=null || prePlanArea.getLength2()!=null )){
				prePlanwind.setHeight(prePlanArea.getHeight2()==null?0.0:prePlanArea.getHeight2());
				prePlanwind.setLength(prePlanArea.getLength2()==null?0.0:prePlanArea.getLength2());
				prePlanwind.setLastUpdate(new Date());
				prePlanwind.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				prePlanwind.setExpired("F");
				prePlanwind.setAreaPk(prePlanArea.getPk());
				prePlanwind.setActionLog("Add");
				prePlanwind.setType("2");
				this.prePlanAreaService.save(prePlanwind);
				ServletUtils.outPrintSuccess(request, response,prePlanAreaService.getDWMaxPk("2",prePlanArea.getPk()));
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/doEditAreaPer")
	public void doEditAreaPer(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("编辑空间信息");
		PrePlanArea ppa=null;
		try {
			ppa=prePlanTempService.get(PrePlanArea.class, prePlanArea.getPk());
			if(ppa!=null){
				if("area".equals(request.getParameter("name"))){
					ppa.setArea(0d);
				}else if("perimeter".equals(request.getParameter("name"))){
					ppa.setPerimeter(0d);
				}
				ppa.setLastUpdate(new Date());
				ppa.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				prePlanAreaService.update(ppa);
			}
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
		
	}
	
	@RequestMapping("/getDelNotify")
	public void getDelNotify(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("判断是否有区域预算");
		try{
			Customer customer=null;
			if (StringUtils.isNotBlank(prePlanArea.getCustCode())) {
				customer=customerService.get(Customer.class, prePlanArea.getCustCode());
				if("4".equals(customer.getStatus()) && StringUtils.isBlank(prePlanArea.getModule())
						&& !prePlanAreaService.getCanUpdateArea(prePlanArea.getCustCode())){
					ServletUtils.outPrintFail(request, response, "该客户为合同施工状态，删除失败");
					return;
				}
			}
			if(itemPlanService.getDelNotify(prePlanArea.getPk(),prePlanArea.getCustCode())){
				ServletUtils.outPrintSuccess(request, response);
				return;
			}else{
				ServletUtils.outPrintFail(request, response, "此空间存在对应的区域预算,是否删除?");
				return;
			}
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除空间失败");
		}
	}
	
	@RequestMapping("/doDelPrePlanArea")
	public void doDelPrePlanArea(HttpServletRequest request ,
			HttpServletResponse response ,PrePlanArea prePlanArea){
		logger.debug("新增空间开始");
		try{
			Customer customer=null;
			if (StringUtils.isNotBlank(prePlanArea.getCustCode())) {
				customer=customerService.get(Customer.class, prePlanArea.getCustCode());
				if("4".equals(customer.getStatus()) && StringUtils.isBlank(prePlanArea.getModule())
						&& !prePlanAreaService.getCanUpdateArea(prePlanArea.getCustCode())){
					ServletUtils.outPrintFail(request, response, "该客户为合同施工状态，删除失败");
					return;
				}
			}
			prePlanArea.setM_umState("D");
			Result result =this.prePlanAreaService.doDelPrePlanArea(prePlanArea) ;

			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除空间失败");
		}
	}
	
	@RequestMapping("/doPrePlanAreaExcel")
	public void doPrePlanAreaExcel(HttpServletRequest request ,HttpServletResponse response,
			PrePlan prePlan){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prePlanAreaService.findPlanAreaJqgridBySql(page, prePlan);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"预算管理_空间管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到主材查修改明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlan_modifyDetailView")
	public ModelAndView goItemPlan_modifyDetailView(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		 JSONArray arryPrePlanAreaDescr=new JSONArray();
		 if (StringUtils.isNotBlank(itemPlan.getCustCode())){
			 arryPrePlanAreaDescr =getPrePlanAreaArray(itemPlan.getCustCode());
		 }
		return new ModelAndView("admin/design/itemPlan/itemPlan_modifyDetailView").addObject("arryPrePlanAreaDescr",arryPrePlanAreaDescr);
	}
	
	/**
	 * 基础预算备份
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doPlanBak")
	public void doPlanBak(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		try{
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			itemPlan.setM_umState("O");
			itemPlan.setLastUpdatedBy(uc.getCzybh());
			Result result= itemPlanService.doBaseAndItemPlanBak(itemPlan);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "预算备份失败");
		}
	}
	/**
	 * 查询备份JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPlanBakJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPlanBakJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		itemPlanService.findPlanBakPageBySql(page,itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 修改客户主材或基础模板编号
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateTempNo")
	public void doUpdateTempNo(HttpServletRequest request, HttpServletResponse response,Customer customer){
	    try{
	    	UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			Customer newCustomer=customerService.get(Customer.class,customer.getCode());
			if(StringUtils.isNotBlank(customer.getBaseTempNo())){
				newCustomer.setBaseTempNo(customer.getBaseTempNo());
			}
			if(StringUtils.isNotBlank(customer.getMainTempNo())){
				newCustomer.setMainTempNo(customer.getMainTempNo());
			}
			newCustomer.setLastUpdate(new Date());
			newCustomer.setLastUpdatedBy(uc.getCzybh());
			this.customerService.update(newCustomer); 
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改预算模板号失败");
		}
	
	}
	public JSONArray getPrePlanAreaArray(String custCode){
		 List<Map<String,Object>>  listPrePlanAreaDescr=itemPlanService.getPrePlanAreaDescr(custCode);
		 if  (listPrePlanAreaDescr!=null){
			ArrayList<String> list = new ArrayList<String>();

			for (Map<String, Object> m :listPrePlanAreaDescr)  
		    {  
				for (String k : m.keySet())  
				{  
					list.add(((String) m.get(k)).trim());
				}  
		    }
			return JSONArray.fromObject(list);
		}else{
			return null;
			
		}
	
	}
	/**
	 * 获取税金
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping("/getTax")
	@ResponseBody
	public String getTax(HttpServletRequest request,HttpServletResponse response,
			Customer customer){
		return itemPlanService.getTax(customer);
	}
	/**
	 * 获取合同造价表类型
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping("/getContractFeeRepType")
	@ResponseBody
	public List<Map<String, Object>> getContractFeeRepType(HttpServletRequest request,HttpServletResponse response,
			Customer customer){
     	CustProfit custProfit=custProfitService.get(CustProfit.class, customer.getCode());
     	customer.setPayType(custProfit.getPayType());
		List<Map<String, Object>>list= itemPlanService.getContractFeeRepType(customer);
		return list;
	}
	/**
	 * 修改客户主材或基础模板编号
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateInitSign")
	@ResponseBody
	public void doUpdateInitSign(HttpServletRequest request, HttpServletResponse response,String custCode,String isInitSign){
	    try{
	    	UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			Customer newCustomer=customerService.get(Customer.class,custCode);
			if(StringUtils.isNotBlank(isInitSign)){
				newCustomer.setIsInitSign(isInitSign);
			}
			newCustomer.setLastUpdate(new Date());
			newCustomer.setLastUpdatedBy(uc.getCzybh());
			this.customerService.update(newCustomer); 
			ServletUtils.outPrintSuccess(request, response,"修改草签标成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改草签标记失败");
		}
	
	}
	/**
	 * 跳转到赠品页签
	 * @param itemPlan
	 * 
	 */
	@RequestMapping("/goGift")
	public ModelAndView goItemSetDetailList(HttpServletRequest request,
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		return new ModelAndView("admin/design/itemPlan/tab_gift").addObject("itemPlan", itemPlan);
	}
	/**
	 * 获取赠品项目列表
	 * @param itemPlan
	 * 
	 */
	@RequestMapping("/goGiftByDescrJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goGiftByDescJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.findPageBySql_giftByDescr(page,itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 获取赠品材料列表
	 * @param itemPlan
	 * 
	 */
	@RequestMapping("/goGiftItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goGiftItemJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.findPageBySql_giftItem(page,itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 基础预算差异数据
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/goBaseItemPlanDiff")
	public void getBaseItemPlanDiff(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.getBaseItemTempDiff(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 主材预算差异数据
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/goItemPlanDiff")
	public void getItemmPlanDiff(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.getItemTempDiff_ZC(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 基础套装从模板导入数据
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doBaseItemSetAdd")
	public void doBaseItemSetAdd(HttpServletRequest request, HttpServletResponse response,ItemPlan itemPlan){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemPlan.setLastUpdatedBy(uc.getCzybh());
		Result result= itemPlanService.doBaseItemSetAddProc(itemPlan);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	/**
	 * 跳转到软装销售页面
	 * @return
	 */
	@RequestMapping("/goItemPlan_softSales")
	public ModelAndView itemPlan_softSales(HttpServletRequest request, HttpServletResponse response,String custCode){
		Customer customer=null;
		CustType custType=null;
		UserContext uc=this.getUserContext(request);
		if (StringUtils.isNotBlank(custCode)){
			 customer=customerService.get(Customer.class, custCode);
			 DiscToken discToken = new DiscToken();
			 discToken.setUseCustCode(customer.getCode());
			 discToken.setUseStep("1");
			 customer.setDiscTokenNo(discTokenService.getDiscTokenNo(discToken));
			 if (StringUtils.isNotBlank(customer.getDiscTokenNo())){
				 discToken  = discTokenService.get(DiscToken.class, customer.getDiscTokenNo());
				 if(discToken!=null){
					 customer.setDiscTokenAmount(discToken.getAmount());
			 	}
			 }	 
		}else{
			 customer=new Customer();
			 customer.setLastUpdatedBy(uc.getCzybh());
			 customer.setLayout("0");
			 customer.setSoftDisc(0.0);
		}
		 Xtcs wallPaper=xtcsService.get(Xtcs.class, "WallPaper");
		 Xtcs curtain=xtcsService.get(Xtcs.class, "Curtain");
		 Xtcs light=xtcsService.get(Xtcs.class, "Light");
		 Xtcs furniture=xtcsService.get(Xtcs.class, "Furniture");
		 Xtcs adornment=xtcsService.get(Xtcs.class, "Adornment");
		 if(StringUtils.isNotBlank(customer.getDesignMan())){
			 Employee designMan=employeeService.get(Employee.class, customer.getDesignMan());
			 if(designMan!=null){
				 customer.setDesignManDescr(designMan.getNameChi());
			 }	
		 }
		 if(StringUtils.isNotBlank(customer.getBusinessMan())){	
			 Employee businessMan=employeeService.get(Employee.class, customer.getBusinessMan());
			 if(businessMan!=null){
				 customer.setBusinessManDescr(businessMan.getNameChi());
			 }
		 }	
		 if(StringUtils.isNotBlank(customer.getCustType())){
			 custType=custTypeService.get(CustType.class, customer.getCustType());
		 }else{
			 custType=custTypeService.get(CustType.class, "2");
			 customer.setCustType("2");
		 }
		 Xtdm xtdm=xtdmService.getByIdAndCbm("LAYOUT", customer.getLayout());
		 
		 customer.setIsAddAllInfo(custType.getIsAddAllInfo());
		 customer.setAreaPer(custType.getAreaPer());
		 customer.setBaseFeeDirctPer(custType.getBaseFeeDirctPer());
		 customer.setBaseFeeCompPer(custType.getBaseFeeCompPer());
		 customer.setManageFeeBasePer(custType.getManageFeeBasePer());
		 customer.setMainSetFeePer(custType.getMainSetFeePer());
		 customer.setSetMinusPer(custType.getSetMinusPer());
		 customer.setSetAddPer(custType.getSetAddPer());
		 customer.setLongFeePer(custType.getLongFeePer());
		 customer.setIsOutSet(custType.getType());
		 customer.setMainFeePer(custType.getMainFeePer());
		 customer.setMainDiscPer(custType.getMainDiscPer());
		 customer.setManageFeeMainPer(custType.getManageFeeMainPer());
		 customer.setMainServFeePer(custType.getMainServFeePer());
		 customer.setManageFeeServPer(custType.getManageFeeServPer());
		 customer.setManageFeeCupPer(custType.getManageFeeCupPer());
		 customer.setCupboardFeePer(custType.getCupboardFeePer());
		 customer.setCupBoardDiscPer(custType.getCupBoardDiscPer());
		 customer.setSoftFeePer(custType.getSoftFeePer());
		 customer.setSoftDiscPer(custType.getSoftDiscPer());
		 customer.setManageFeeSoftPer(custType.getManageFeeSoftPer());
		 customer.setIntegrateFeePer(custType.getIntegrateFeePer());
		 customer.setIntegrateDiscPer(custType.getIntegrateDiscPer());
		 customer.setManageFeeIntPer(custType.getManageFeeIntPer());
		 customer.setLastUpdatedBy(uc.getCzybh());
		 customer.setLayout(xtdm.getNote());
		 customer.setSoftFee_WallPaper(wallPaper.getQz());
		 customer.setSoftFee_Curtain(curtain.getQz());
		 customer.setSoftFee_Light(light.getQz());
		 customer.setSoftFee_Furniture(furniture.getQz());
		 customer.setSoftFee_Adornment(adornment.getQz());
		 
		 String wfProcStatus= itemPlanService.getSaleDiscApproveStatus(customer);
		 return new ModelAndView("admin/design/itemPlan/itemPlan_softSales")
			.addObject("customer", customer).addObject("wfProcStatus", wfProcStatus);
	}
	
	/**
	 * 跳转到新增客户页面
	 * @return
	 */
	@RequestMapping("/goCustSave")
	public ModelAndView goCustSave(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("跳转到新增软装销售客户页面");
		Customer customer = null;
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class, id);
			if (StringUtils.isNotBlank(customer.getBuilderCode())){
				Builder builder = customerService.get(Builder.class, customer.getBuilderCode());
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
			
			if (StringUtils.isNotBlank(customer.getAgainMan())) {
			    Employee employee = employeeService.get(Employee.class, customer.getAgainMan());
			    if (employee != null) {
                    customer.setAgainManDescr(employee.getNameChi());
                }
			}
			
			customer.setM_umState("M");
		}else{
			UserContext uc=this.getUserContext(request);
			customer = new Customer();
			customer.setM_umState("A");	
			customer.setLayout("0");
			customer.setBusinessMan(uc.getEmnum());
			customer.setBusinessManDescr(uc.getZwxm());
		}
		return new ModelAndView("admin/design/itemPlan/itemPlan_custSave")
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goItemPlan_softSend")
	public ModelAndView goSendBatch(HttpServletRequest request, HttpServletResponse response, String custCode){
		logger.debug("跳转到软装发货页面");
		Customer customer = customerService.get(Customer.class, custCode);
		ItemPreMeasure itemPreMeasure=new ItemPreMeasure();
		itemPreMeasure.setCustCode(custCode);
		itemPreMeasure.setSendDate(new Date());
		itemPreMeasure.setItemType1("RZ");
		if("4".equals(customer.getStatus())){
			itemPreMeasure.setM_umState("S");
		}else{
			itemPreMeasure.setM_umState("V");
		}
		return new ModelAndView("admin/design/itemPlan/itemPlan_softSend")
			.addObject("itemPreMeasure", itemPreMeasure);
	}
	
	/**
	 * 软装发货表格
	 * @param request
	 * @param response
	 * @param custCode
	 */
	@RequestMapping("/goSoftSendJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSoftSendJqGrid(HttpServletRequest request, HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.goSoftSendJqGrid(page, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 软装发货
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doSoftSend")
	public void doSoftSend(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("软装发货开始");
		try{
			UserContext uc=this.getUserContext(request);
			String detailJson = request.getParameter("detailJson");
			itemPreMeasure.setM_umState("A");
			itemPreMeasure.setItemType1("RZ");
			itemPreMeasure.setIsSetItem("0");
			itemPreMeasure.setDelivType("3");
			itemPreMeasure.setSendType("2");
			itemPreMeasure.setItemAppType("S");
			itemPreMeasure.setIsService("0");
			itemPreMeasure.setAppCzy(uc.getCzybh());
			Result result = itemPlanService.doSoftSend(itemPreMeasure,XmlConverUtil.jsonToXmlNoHead(detailJson));
			if(result.isSuccess()){
				ServletUtils.outPrint(request, response, true, "发货成功",  null, true);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());	
			}
		}catch(Exception e){
			Map<String,Object> returnExceptionMap = new HashMap<String, Object>();
			
			ServletUtils.outPrintFail(request, response, returnExceptionMap);
		}
	}
	
	/**
	 * 判断是否能够跳转到分批发货页面
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/goSoftPlanVerify")
	public void getGoSendBatchBefore(HttpServletRequest request,HttpServletResponse response,String custCode,String m_umState,String title){
		try{
			Customer customer = customerService.get(Customer.class, custCode);
			if(!"2".equals(customer.getCustType().trim())){
				ServletUtils.outPrintFail(request, response, "只有软装客户才能进行"+title+"操作");
				return;
			}	
			if("S".equals(m_umState)){
				if(!"4".equals(customer.getStatus().trim())&&!"5".equals(customer.getStatus().trim())){
					ServletUtils.outPrintFail(request, response, "只有合同施工状态楼盘可以进行"+title+"操作");
					return;
				}
			}	
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "操作失败");
			e.printStackTrace();
		}
	}	
	@RequestMapping("/goSoftSendCountView")
	public ModelAndView goSendCountView(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		return new ModelAndView("admin/design/itemPlan/itemPlan_softSend_sendCountView").addObject("itemPreMeasure", itemPreMeasure);
	}

	public static String getContractTempPath(String fileName,String pk){
		String contractTempName= SystemConfig.getProperty("contractTemp", "", "photo");
		if (StringUtils.isBlank(contractTempName)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return contractTempName;
		}else{
			return SystemConfig.getProperty("contractTemp", "", "photo");
		}
	}
	
	@RequestMapping("/checkProcStatus")
	@ResponseBody
	public boolean checkProcStatus(HttpServletRequest request,HttpServletResponse response,ItemPlan itemPlan){
		logger.debug("ajax获取预报价数量、预估施工量、其他费用");
		boolean result = true;
		result = itemPlanService.checkProcStatus(itemPlan);
		
		return result;
	}
	
	/**
	 * 跳转到合同管理
	 * @param itemPlan
	 * 
	 */
	@RequestMapping("/goContract")
	public ModelAndView goContract(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		Customer customer = new Customer();
		if(StringUtils.isNotBlank(custCode)){
			customer = customerService.get(Customer.class, custCode);
		}
		return new ModelAndView("admin/design/custContract/custContract_list").addObject("custCode", custCode)
				.addObject("customer", customer);
	}
	
	/**
	 * 跳转到下定管理
	 * @param itemPlan
	 * 
	 */
	@RequestMapping("/goMakeOrder")
	public ModelAndView goMakeOrder(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		Customer customer = new Customer();
		if(StringUtils.isNotBlank(custCode)){
			customer = customerService.get(Customer.class, custCode);
		}
		return new ModelAndView("admin/design/custContract/custContract_design_list").addObject("custCode", custCode)
				.addObject("customer", customer);
	}
	
	/**
	 * 编辑补充协议
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doUpdateRepClause")
	public void doUpdateRepClause(HttpServletRequest request, HttpServletResponse response, Customer ct){
		logger.debug("编辑补充协议");
		try{
			Customer customer = customerService.get(Customer.class, ct.getCode());
			customer.setRepClause(ct.getRepClause());
			customerService.update(customer);
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			Map<String,Object> returnExceptionMap = new HashMap<String, Object>();
			
			ServletUtils.outPrintFail(request, response, returnExceptionMap);
		}
	}
	
	@RequestMapping("/doCommiConstruct")
	public void doCommiConstruct(HttpServletRequest request,HttpServletResponse response,ItemPlan itemPlan){
		logger.debug("");
		itemPlan.setItemType1("RZ");
		List<Map<String, Object>> list = itemPlanService.checkCommiConstruct(itemPlan);
		
		Map<String,Object> customerPayMap = null;
		Customer customer=customerService.get(Customer.class,itemPlan.getCustCode());
		double notPayAmount = 0.0;
		double hasPay = 0.0;
		double contractFee = customer.getContractFee();
		String softTokenNo = customer.getSoftTokenNo();
		DiscToken discToken = new DiscToken();
		if(StringUtils.isNotBlank(softTokenNo)){
			discToken = discTokenService.get(DiscToken.class, softTokenNo);
		}
		double softDiscAmount = 0.0;
		if(discToken != null && discToken.getAmount() !=null){
			softDiscAmount = discToken.getAmount();
		} 
		
		if(StringUtils.isBlank(softTokenNo)){
			discToken.setUseCustCode(itemPlan.getCustCode());
			discToken.setStatus("3");
			softDiscAmount = discTokenService.getDiscTokenAmountTotal(discToken);
		}
				
		CustType custType= new CustType();
		if(customer!=null){
			custType = itemPlanService.get(CustType.class,customer.getCustType());
			if("0".equals(custType.getIsAddAllInfo())&&"3".equals(custType.getIsPartDecorate())){
			    customerPayMap = customerService.getCustomerPayByCode(itemPlan.getCustCode());
				customerPayMap.put("zjzje", customerService.getCustomerZjzjeByCode(itemPlan.getCustCode()));
				customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(itemPlan.getCustCode()));
				hasPay = Double.parseDouble(customerPayMap.get("haspay").toString());
				notPayAmount = contractFee - softDiscAmount - hasPay;
			}else{
				notPayAmount = 0.0;		
			}	
		}
		
		String wfProcStatus = itemPlanService.getSaleDiscApproveStatus(customer);
		if("1".equals(wfProcStatus)){
			ServletUtils.outPrintFail(request, response, "存在正在审批的独立销售折扣单，无法提交转施工");
			return;
		}
		if(notPayAmount > 0.0 ){
			ServletUtils.outPrintFail(request, response, "客户未达账金额大于0，不允许转施工");
			return;
		}
		
		if((list != null && list.size() > 0) || (customer.getSoftDisc()==null?0.0:customer.getSoftDisc()) > 0.0){
		
			ServletUtils.outPrintFail(request, response, "提交审批");
			return;
		} else {
			customer=customerService.get(Customer.class, itemPlan.getCustCode());
			customer.setM_umState("S");
			customer.setFromStatus(customer.getStatus());
			customer.setToStatus("4");
			customer.setConPhone(customer.getMobile1());
			customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Map<String , Object> fourPayMap=customerService.getFourPay(itemPlan.getCustCode(), "1");
	        Map<String, Object> maxDiscMap = customerService.getMaxDiscByCustCode(itemPlan.getCustCode());
	        customer.setToConstructDate(new Date());
	        customer.setFirstPay(Double.parseDouble(fourPayMap.get("FirstPay").toString()));
	        customer.setSecondPay(Double.parseDouble(fourPayMap.get("SecondPay").toString()));
	        customer.setThirdPay(Double.parseDouble(fourPayMap.get("ThirdPay").toString()));
	        customer.setFourPay(Double.parseDouble(fourPayMap.get("FourPay").toString()));
	        customer.setDesignerMaxDiscAmount(Double.parseDouble(maxDiscMap.get("DesignerMaxDiscAmount").toString()));
	        customer.setDirectorMaxDiscAmount(Double.parseDouble(maxDiscMap.get("DirectorMaxDiscAmount").toString()));
			if(StringUtils.isNotBlank(customer.getPayeeCode())){
				customer.setPayeeCode(customer.getPayeeCode());
			} else {
				customer.setPayeeCode(custType.getPayeeCode());
			}
			
			Result result =	customerService.doSaveZsg(customer);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"操作成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}
	}
	
	@RequestMapping("/goSaleDiscProc")
	public ModelAndView goSaleDiscProc(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("custCode") String custCode, @RequestParam("itemType1Code") String itemType1Code) {
		// 根据流程标识获取最后一个版本的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey("saleDiscApprove")
			.latestVersion()
			.singleResult();
		
		String url = FileUploadUtils.DOWNLOAD_URL;
		
		// 获取对应的流程
        WfProcess wfProcess = this.wfProcInstService.getWfProcessByProcKey(processDefinition.getKey());
		String wfProcNo = "";
		if(wfProcess != null){
			wfProcNo = wfProcess.getNo();
			wfProcess.setRemarks((wfProcess.getRemarks()==null?"":wfProcess.getRemarks()).replace("\r\n", "<br/>"));
		}
		CustType custType = new CustType();
		ItemType1 itemType1 = wfProcInstService.get(ItemType1.class, itemType1Code);
		Employee employee = wfProcInstService.get(Employee.class, this.getUserContext(request).getEmnum());
		Customer customer = wfProcInstService.get(Customer.class, custCode);
		// 获取明细 根据类型合计
		Map<String, Object> detailJson = new HashMap<String, Object>();
		Map<String , Object> detailMap = new HashMap<String, Object>();
		
		if(itemType1 != null){
			detailJson.put("fp__tWfCust_SaleDiscApprove__0__ItemType1", itemType1.getDescr());
			detailJson.put("fp__tWfCust_SaleDiscApprove__0__ItemType1Descr", itemType1.getDescr());
		}
		ItemPlan itemPlan = new ItemPlan();
		itemPlan.setCustCode(custCode);
		itemPlan.setItemType1(itemType1Code);
		Map<String, Object> map = itemPlanService.getBefAmountByCustCodeItemType(itemPlan);
		Double befAmount = 0.0;
		Double markup = 100.0;
		Double discAmount = 0.0;
		boolean needDetail = false;
		if(map != null){
			if(map.get("BefLineAmount") != null ){
				befAmount = Double.parseDouble(map.get("BefLineAmount").toString());
			}
			if(map.get("ItemPlanDiscAmount") != null){
				if(Double.parseDouble(map.get("ItemPlanDiscAmount").toString()) != 0.0){
					needDetail = true;
				}
				//discAmount = Double.parseDouble(map.get("DiscAmount").toString());
			}
		}
		
		discAmount+= customer.getSoftDisc()==null?0.0:customer.getSoftDisc();
		String isClearInvStatus = "无样品";
		List<Map<String, Object>> clearInvList = itemPlanService.getClearInvList(itemPlan, "1");
		// List<Map<String, Object>> notClearInvList = itemPlanService.getClearInvList(itemPlan, "0");
		if(clearInvList != null && clearInvList.size() > 0){
			isClearInvStatus = "有样品";
			needDetail = true;
		} else {
			isClearInvStatus = "无样品";
		}
		detailJson.put("fp__tWfCust_SaleDiscApprove__0__IsClearInvStatus", isClearInvStatus);
		detailMap.put("tWfCust_SaleDiscApprove", 1);
		System.out.println((double)(Math.round(discAmount*100)/100.0));
		if(customer != null){
			detailJson.put("fp__tWfCust_SaleDiscApprove__0__CustCode", customer.getCode());
			detailJson.put("fp__tWfCust_SaleDiscApprove__0__CustDescr", customer.getDescr());
			detailJson.put("fp__tWfCust_SaleDiscApprove__0__Address", customer.getAddress());
			detailJson.put("fp__tWfCust_SaleDiscApprove__0__DiscAmount", discAmount);
			detailJson.put("fp__tWfCust_SaleDiscApprove__0__BefAmount", befAmount);
			detailJson.put("fp__tWfCust_SaleDiscApprove__0__DiscRemarks", "");
			custType = wfProcInstService.get(CustType.class, customer.getCustType());
			if(custType != null){
				detailJson.put("fp__tWfCust_SaleDiscApprove__0__CustType", custType.getCode());
				detailJson.put("fp__tWfCust_SaleDiscApprove__0__CustTypeDescr", custType.getDesc1());
			}
			if(befAmount != 0){
				markup = (1 - discAmount/befAmount) * 100;
				markup = (double)(Math.round(markup*100)/100.0);
				detailJson.put("fp__tWfCust_SaleDiscApprove__0__Markup", markup) ;
				detailJson.put("fp__tWfCust_SaleDiscApprove__0__MinMarkup", markup) ;
			}
		}
		
		String startMan = this.getUserContext(request).getCzybh();
		Xtcs xtcs = wfProcInstService.get(Xtcs.class, "CMPNYCODE");
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_apply")
			.addObject("processDefinition", processDefinition)
			.addObject("processDefinitionKey", processDefinition.getId())
			.addObject("wfProcNo",wfProcNo)
			.addObject("applyPage", processDefinition.getKey()+".jsp")
			.addObject("m_umState", "A")
			.addObject("datas", detailJson)
			.addObject("detailJson", JSONObject.toJSONString(detailJson))
			.addObject("processInstanceId", "processInstanceId")
			.addObject("detailList",JSONObject.toJSONString(detailMap))
			.addObject("startMan", startMan)
			.addObject("wfProcess", wfProcess)
			.addObject("url", url)
			.addObject("cmpcode", xtcs.getQz())
			.addObject("employee", employee).addObject("activityId", "startevent")
			.addObject("needDetail", needDetail);
	}
	
	@RequestMapping("/getSaleDiscApproveDetail")
	@ResponseBody
	public List<Map<String, Object>> getSaleDiscApproveDetail(HttpServletRequest request,HttpServletResponse response,ItemPlan itemPlan){
		logger.debug("导入");
		
		List<Map<String, Object>> list = itemPlanService.getSaleDiscApproveDetail(itemPlan);
		
		return list;
	}
	
	@RequestMapping("/getSaleAmountDetail")
	@ResponseBody
	public Map<String, Object> getSaleAmountDetail(HttpServletRequest request,HttpServletResponse response,ItemPlan itemPlan){
		
		Map<String, Object> map = itemPlanService.getBefAmountByCustCodeItemType(itemPlan);
		
		return map;
	}
	
	@RequestMapping("/getWfProcDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getWfProcDetail(HttpServletRequest request,
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String, Object>> list = itemPlanService.getWfProcDetail(itemPlan);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 返回基础预算或主材预算 与空间算量不等  1 基础，2主材，3主材和基础
	 * @param request
	 * @param response
	 * @param custCode
	 * @param authorityJudge 是否需判断权限
	 * @return
	 */
	@RequestMapping("/getPlanDiffByArea")
	@ResponseBody
	public String getPlanDiffByArea(HttpServletRequest request,HttpServletResponse response,
			String custCode, boolean hasAuthorityJudge){
		if(StringUtils.isNotBlank(custCode)){
			Customer customer = customerService.get(Customer.class, custCode);
			if(!"1".equals(customer.getStatus()) && !"2".equals(customer.getStatus()) && !"3".equals(customer.getStatus())){
				return "0";
			}  
		}
		boolean itemPlanDiff = false;
		boolean basePlanDiff = false;
		boolean hasBasePlanAuthority = false;
		boolean hasItemPlanAuthority = false;
		UserContext uc = getUserContext(request);
		if (hasAuthorityJudge){
			if(czybmService.hasGNQXByCzybh(uc.getCzybh(), "0208", "基础预算") 
				|| czybmService.hasGNQXByCzybh(uc.getCzybh(), "0208", "基础预算审核")) {
					hasBasePlanAuthority = true;
			}
			if(czybmService.hasGNQXByCzybh(uc.getCzybh(), "0208", "主材预算")) {
				hasItemPlanAuthority = true;	
			}
		}else{
			hasBasePlanAuthority = true;
			hasItemPlanAuthority = true;
		}
		
		if(hasBasePlanAuthority && itemPlanService.hasBasePlanQtyUnequal(custCode)){ 
			basePlanDiff = true;	
		}
	
		if(hasItemPlanAuthority && itemPlanService.hasItemPlanQtyUnequal(custCode)){ 
			itemPlanDiff = true;	
		}
		
		if(basePlanDiff && itemPlanDiff){
			return "3" ;
		}else if(basePlanDiff){
			return "1" ;
		}else if(itemPlanDiff){
			return "2" ;
		}
	    return "0";
	}
	
	
}
