package com.house.home.web.controller.query;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileHelper;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.AgainSign;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.CustStakeholderHis;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.DesignPicPrg;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.finance.CommiCal;
import com.house.home.entity.finance.CommiDetail;
import com.house.home.entity.finance.ItemCommiDetail;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.ItemReq;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.PurchaseDetail;
import com.house.home.entity.project.ItemChg;
import com.house.home.entity.project.SoftInstall;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.design.AgainSignService;
import com.house.home.service.design.BaseItemPlanService;
import com.house.home.service.design.CustConService;
import com.house.home.service.design.CustStakeholderHisService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.ItemPlanService;
import com.house.home.service.finance.CommiCalService;
import com.house.home.service.finance.CommiDetailService;
import com.house.home.service.finance.ItemCommiDetailService;
import com.house.home.service.insales.GiftAppService;
import com.house.home.service.insales.ItemAppDetailService;
import com.house.home.service.insales.ItemAppService;
import com.house.home.service.insales.ItemReqService;
import com.house.home.service.insales.PurchaseDetailService;
import com.house.home.service.insales.PurchaseService;
import com.house.home.service.project.BaseItemChgDetailService;
import com.house.home.service.project.BaseItemChgService;
import com.house.home.service.project.ConFeeChgService;
import com.house.home.service.project.CustWorkerService;
import com.house.home.service.project.ItemChgDetailService;
import com.house.home.service.project.ItemChgService;
import com.house.home.service.project.PrjProgService;
import com.house.home.service.project.SoftInstallService;

@Controller
@RequestMapping("/admin/customerXx")
public class CustomerXxController extends BaseController {

//	private static final Logger logger = LoggerFactory.getLogger(CustomerXxController.class);

	@Autowired
	private CustomerService customerService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ItemAppService itemAppService;
	@Autowired
	private ItemAppDetailService itemAppDetailService;
	@Autowired
	private ItemReqService itemReqService;
	@Autowired
	private BaseItemPlanService baseItemPlanService;
	@Autowired
	private ItemPlanService itemPlanService;
	@Autowired
	private ItemChgService itemChgService;
	@Autowired
	private ItemChgDetailService itemChgDetailService;
	@Autowired
	private ConFeeChgService conFeeChgService;
	@Autowired
	private BaseItemChgService baseItemChgService;
	@Autowired
	private BaseItemChgDetailService baseItemChgDetailService;
	@Autowired
	private CommiCalService commiCalService;
	@Autowired
	private CommiDetailService commiDetailService;
	@Autowired
	private ItemCommiDetailService itemCommiDetailService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private PurchaseDetailService purchaseDetailService;
	@Autowired
	private AgainSignService againSignService;
	@Autowired
	private CustConService custConService;
	@Autowired
	private CustStakeholderHisService custStakeholderHisService;
	@Autowired
	private CustStakeholderService custStakeholderService;
	@Autowired
	private PrjProgService prjProgService;
	@Autowired
	private SoftInstallService softInstallService;
	@Autowired
	private GiftAppService giftAppService;
	@Autowired
	private CustWorkerService custWorkerService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private XtcsService xtcsService;
	
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
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		if (StringUtils.isBlank(customer.getStatus())){
			customer.setStatus("1,2,3,4,5");
		}
		
		if("3".equals(this.getUserContext(request).getCustRight())){//查看所有的
			// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//			if(!czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(),999)){
			if(!czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0501","查看未施工客户")){//判断是否有未施工客户查看权限
				customer.setViewAll("1");
			}
		}
		customerService.findPageBySql_xxcx(page, customer, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 客户信息查询
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
			customer.setStatus("2,3,4");
		}
		boolean hasPhone=false;
		String authStr = this.getUserContext(request).getAuthStr();
		if (this.getUserContext(request).isSuperAdmin() || authStr.indexOf("CUSTOMERXX_PHONE")>0){
			hasPhone = true;
		}
		return new ModelAndView("admin/query/customerXx/customerXx_list")
			.addObject("customer", customer)
			.addObject("hasPhone", hasPhone);
	}
	
	/**
	 * 客户信息查询详细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, String id,String isCustPay) throws Exception {

		Customer customer = customerService.get(Customer.class, id);
		resetCustomer(customer);
		UserContext uc = this.getUserContext(request);
		String costRight = uc.getCostRight();
		String itemRight = uc.getItemRight();
		boolean showPhone = false;
		if (uc.isSuperAdmin()){
			showPhone = true;
		}else{
			showPhone = custStakeholderService.phoneShow(id, uc.getEmnum(), customer.getStatus());
		}
		customer.setCustType(customer.getCustType().trim());
		DesignPicPrg designPicPrg = customerService.get(DesignPicPrg.class, customer.getCode());
		return new ModelAndView("admin/query/customerXx/customerXx_detail")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight)
			.addObject("showPhone", showPhone)
			.addObject("isCustPay", isCustPay).addObject("designPicPrg", designPicPrg);
	}
	/**
	 * 巡检单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goXjd")
	public ModelAndView goXjd(HttpServletRequest request,
			HttpServletResponse response, String id) throws Exception {

		Customer customer = customerService.get(Customer.class, id);

		return new ModelAndView("admin/query/customerXx/customerXx_xjd")
			.addObject("customer", customer);
	}
	/**
	 * 查看联系方式
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewPhone")
	public ModelAndView goViewPhone(HttpServletRequest request,
			HttpServletResponse response, String id) throws Exception {

		Customer customer = customerService.get(Customer.class, id);

		return new ModelAndView("admin/query/customerXx/customerXx_viewPhone")
			.addObject("customer", customer);
	}
	/**
	 * 签单奖励
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdateQdjl")
	public ModelAndView goUpdateQdjl(HttpServletRequest request,
			HttpServletResponse response, String id) throws Exception {

		Customer customer = customerService.get(Customer.class, id);

		return new ModelAndView("admin/query/customerXx/customerXx_qdjl")
			.addObject("customer", customer);
	}
	/**
	 * 签单奖励
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doUpdateQdjl")
	public void doUpdateQdjl(HttpServletRequest request,
			HttpServletResponse response, Customer customer) {
		logger.debug("修改签单奖励开始");
		try{
			Customer cust = customerService.get(Customer.class, customer.getCode());
			cust.setRewardRemark(customer.getRewardRemark());
			customerService.update(cust);
			ServletUtils.outPrintSuccess(request, response, "修改签单奖励成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改签单奖励失败");
		}

	}
	/**
	 *客户信息查询导出Excel判断
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcelBefore")
	public void doExcelBefore(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		customer.setDoExcelBefore("1");
		customerService.findPageBySql_xxcx(page, customer, uc);
		if (page.getTotalCount()>100000){
			ServletUtils.outPrintFail(request, response, "记录数【"+String.valueOf(page.getTotalCount())+"】超过十万不能导出");
			return;
		}
		ServletUtils.outPrintSuccess(request, response, "成功");
	}
	/**
	 *客户信息查询导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		UserContext uc = getUserContext(request);
		if("3".equals(this.getUserContext(request).getCustRight())){//查看所有的
			// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//			if(!czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(),999)){//判断是否有未施工客户查看权限
			if(!czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0501","查看未施工客户")){//判断是否有未施工客户查看权限
				customer.setViewAll("1");
			}
		}
		getExcelList(request);
		ServletUtils.flushExcelOutputStreamForCustomer(request, response, new ArrayList<Map<String, Object >>(), "客户信息查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList, customer, uc);
	}
	
	@RequestMapping("/doExcelClxq")
	public void doExcelClxq(HttpServletRequest request, 
			HttpServletResponse response, ItemReq itemReq, String title){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		page.setPageOrderBy("itemtype2descr");
		page.setPageOrder("asc");
		itemReqService.findPageBySql_zcxq(page, itemReq);
		getExcelList(request);
		List<String> list = new ArrayList<String>();
		String s = "已发货数量|";
		if (titleList!=null && titleList.size()>0){
			for (String str : titleList){
				if ("仓库".equals(str) || "供应商".equals(str) || "总数量".equals(str)){
					str = s + str;
				}
				list.add(str);
			}
			titleList = list;
		}
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				title+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *保存设置列
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSaveCols")
	public void doSaveCols(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		String colModel = request.getParameter("jsonString");
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "customerSet");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		for (int i = 0; i < arryCols.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryCols.getJSONObject(i);
            String name = (String) jsonObject.get("name");
            if (name.equals("合同开工时间")){
            	jsonObject.put("formatter", "formatDate");
            }
            if (name.equals("实际开工时间")){
            	jsonObject.put("formatter", "formatDate");
            }
            if (name.equals("开工令时间")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("客户结算时间")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("预计结算时间")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("实际结算时间")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("集成测量时间")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("派单通知")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("最后修改时间")){
            	jsonObject.put("formatter", "formatTime");
            }
            
        }
		
		String str = arryCols.toString().replace("\"formatTime\"", "formatTime")
				.replace("\"formatDate\"", "formatDate");
		
		if (FileHelper.strToFile(filePath, fileName, str)){
			ServletUtils.outPrintSuccess(request, response, "设置成功！");
		}else{
			ServletUtils.outPrintFail(request, response, "设置失败！");
		}
		
	}
	
	@RequestMapping("/doResetCols")
	public void doResetCols(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "customerSet");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		
		File file = new File(filePath+"\\"+fileName);
		
		if(file.exists()){
			file.delete();
		}
		ServletUtils.outPrintSuccess(request, response, "设置成功！");
	}
	
	/**
	 * 查询JqGrid表格数据主材领料
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_lld_zcll")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_lld_zcll(HttpServletRequest request,
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemAppService.findPageBySql_khxxcx(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据礼品领料
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_lld_lpll")
	@ResponseBody
	public WebPage<Map<String,Object>> goAppJqGrid(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageBySql_customerxx(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据主材领料明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_lld_zcllmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_lld_zcllmx(HttpServletRequest request,
			HttpServletResponse response,String no,String custCode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemAppDetailService.findPageByNo_khxxcx(page, no, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,基础需求
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_jzxq")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_jcxq(HttpServletRequest request,
			HttpServletResponse response,ItemReq itemReq) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemReqService.findPageBySql_jzxq(page, itemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,主材需求
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_clxq_zcxq")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_clxq_zcxq(HttpServletRequest request,
			HttpServletResponse response,ItemReq itemReq) throws Exception {
		itemReq.setCostRight(this.getUserContext(request).getCostRight());
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemReqService.findPageBySql_zcxq(page, itemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,基础预算
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_jzys")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_jzys(HttpServletRequest request,
			HttpServletResponse response,BaseItemPlan baseItemPlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		baseItemPlanService.findPageBySql_jzys(page, baseItemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,主材预算
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_clys_zcys")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_clys_zcys(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan) throws Exception {
		itemPlan.setCostRight(this.getUserContext(request).getCostRight());
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemPlanService.findPageBySql_zcys(page, itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据,材料增减
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_clzj_zczj")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_clzj_zczj(HttpServletRequest request,
			HttpServletResponse response,ItemChg itemChg) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemChgService.findPageByCustCode(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,材料增减明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_clzj_zczjmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_clzj_zczjmx(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		String costRight = this.getUserContext(request).getCostRight();
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemChgDetailService.findPageByNo_khxx(page, no, costRight);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,合同费用增减
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_jzzj_htfyzj")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_jzzj_htfyzj(HttpServletRequest request,
			HttpServletResponse response,String custCode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		conFeeChgService.findPageByCustCode(page, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,合同费用增减
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_jzzj_jzxmzj")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_jzzj_jzxmzj(HttpServletRequest request,
			HttpServletResponse response,String custCode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		baseItemChgService.findPageByCustCode(page, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,合同费用增减
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_jzzj_jzxmzjmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_jzzj_jzxmzjmx(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		baseItemChgDetailService.findPageByNo(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,提成明细主表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_tcmx_main")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_tcmx_main(HttpServletRequest request,
			HttpServletResponse response,CommiCal commiCal) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		commiCalService.findPageBySql_khxx(page, commiCal);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,提成明细及员工
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_tcmx_tcmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_tcmx_tcmx(HttpServletRequest request,
			HttpServletResponse response,CommiDetail commiDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		commiDetailService.findPageBySql_khxx(page, commiDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,提成明细主材
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_tcmx_zctcmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_tcmx_zctcmx(HttpServletRequest request,
			HttpServletResponse response,ItemCommiDetail itemCommiDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemCommiDetailService.findPageBySql_khxx(page, itemCommiDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,采购单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_cgd_jzcgd")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_cgd_jzcgd(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		purchaseService.findPageBySql_khxx(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,采购单明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_cgd_jzcgdmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_cgd_jzcgdmx(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		purchaseDetailService.findPageByNo(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,材料需求及到货分析
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_dhfx_main")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_dhfx_main(HttpServletRequest request,
			HttpServletResponse response,ItemReq itemReq) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemReqService.findPageBySql_dhfx(page, itemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据软装安装信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_softInstall")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_softInstall(HttpServletRequest request,
			HttpServletResponse response,SoftInstall softInstall) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		softInstallService.findPageBySql(page, softInstall);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,材料需求及到货分析明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_dhfx_mx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_dhfx_mx(HttpServletRequest request,
			HttpServletResponse response,PurchaseDetail purchaseDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		purchaseDetailService.findPageBySql_dhfx(page, purchaseDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,客户合同信息重签合同
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_khhtxx_cqht")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_khhtxx_cqht(HttpServletRequest request,
			HttpServletResponse response,AgainSign againSign) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		againSignService.findPageBySql_khxx(page, againSign);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,客户跟踪
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_khgz")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_khgz(HttpServletRequest request,
			HttpServletResponse response,CustCon custCon) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		custConService.findPageBySql_khxx(page, custCon);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,干系人-当前干系人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_gxr_dqgxr")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_gxr_dqgxr(HttpServletRequest request,
			HttpServletResponse response, CustStakeholder custStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		custStakeholderService.findPageBySql_khxx(page, custStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,干系人-修改历史
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_gxr_xgls")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_gxr_xgls(HttpServletRequest request,
			HttpServletResponse response, CustStakeholderHis custStakeholderHis) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		custStakeholderHisService.findPageBySql_khxx(page, custStakeholderHis);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,工程进度
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_gcjd")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_gcjd(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		if (StringUtils.isBlank(page.getPageOrderBy())){
			page.setPageOrderBy("PlanBegin,PrjItem");
		}
		prjProgService.findPageByCustCode(page, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goJqGrid_sgjd")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_sgjd(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		
		custWorkerService.findPageByCustCode(page, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,客户付款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_khfk")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_khfk(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		customerService.findPageByCustCode_payDetail(page, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/go_tab")
	public ModelAndView go_tab(HttpServletRequest request,
			HttpServletResponse response, String id, String tabId) throws Exception {
		Customer customer = customerService.get(Customer.class, id);
		resetCustomer(customer);
		UserContext uc = this.getUserContext(request);
		String costRight = uc.getCostRight();
		String itemRight = uc.getItemRight();
		if ("tab_khhtxx".equals(tabId)){
			//获取客户合同信息
			Map<String,Object> customerMap = customerService.getCustomerByCode_htxx(id);
			return new ModelAndView("admin/query/customerXx/tab_khhtxx")
			.addObject("customer", customer)
			.addObject("customerMap", customerMap)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_khgz".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_khgz")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_gxr".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_gxr")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_sjjd".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_sjjd")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_gcjd".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_gcjd")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_sgjd".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_sgjd")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_jzys".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_jzys")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_clys".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_clys")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_jzxq".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_jzxq")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_clxq".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_clxq")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_jzzj".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_jzzj")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_clzj".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_clzj")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_khfk".equals(tabId)){
			//客户付款
			Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(id);
			Map<String,Object> balanceMap=customerService.getShouldBanlance(customer.getCode());
			customerPayMap.put("zjzje", customerService.getCustomerZjzjeByCode(id));
			customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(id));
			//modify by zzr 2018/09/01 修改通过计算而不是通过函数直接获取
			customerPayMap.put("wdz", Double.parseDouble(customerPayMap.get("wdz").toString())+Double.parseDouble(customerPayMap.get("zjzje").toString())-Double.parseDouble(customerPayMap.get("haspay").toString()));
			return new ModelAndView("admin/query/customerXx/tab_khfk")
			.addObject("customer", customer)
			.addObject("customerPayMap", customerPayMap)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight)
			.addObject("balanceMap", balanceMap);
		}else if ("tab_lld".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_lld")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_lld_lpll".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_lld_lpll")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_tcmx".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_tcmx")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_gcxx".equals(tabId)){
			//工程信息
			Map<String,Object> customerProjectMap = customerService.getCustomerByCode_gcxx(id);
			return new ModelAndView("admin/query/customerXx/tab_gcxx")
			.addObject("customer", customer)
			.addObject("customerProjectMap", customerProjectMap)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_cgd".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_cgd")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_dhfx".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_dhfx")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else if ("tab_softInstall".equals(tabId)){
			return new ModelAndView("admin/query/customerXx/tab_softInstall")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}else{
			return new ModelAndView("admin/query/customerXx/tab_khjbxx")
			.addObject("customer", customer)
			.addObject("costRight", costRight)
			.addObject("itemRight", itemRight);
		}
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
	/**小计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goTmplineAmountView")
	public ModelAndView goTmplineAmountView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/query/customerXx/tab_clxq_ckxj");
	}
	
	/**
	 * 查看材料增減
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goClzj")
	public ModelAndView goClzj(HttpServletRequest request,
			HttpServletResponse response, String id) throws Exception {

		Customer customer = customerService.get(Customer.class, id);

		return new ModelAndView("admin/query/customerXx/customerXx_clzj")
			.addObject("customer", customer).addObject("unShowCost", "1");
	}
	
	/**
	 * 查看签单业绩预估
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPerfEstimate")
	public ModelAndView goPerfEstimate(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		Customer customer = customerService.get(Customer.class, custCode);
		UserContext uc = getUserContext(request);
		customer.setType("2"); // 查看类型  1设计师, 2'业务员'， 3'设计师和业务员'
		customer.setEmpCode(uc.getEmnum());  // 操作员员工号
		CustType custType = customerService.get(CustType.class, customer.getCustType());
		if(customer.getInnerArea()==null || customer.getInnerArea() ==0.0 ){
			customer.setInnerArea(custType.getInnerAreaPer()*customer.getArea());
		}
		if ("ADMIN".equals(uc.getJslx()) || uc.isSuperAdmin()){
			customer.setType("3");
		}else if((custStakeholderService.hasCustAuthorityByRole(custCode, "00", uc))
				&& custStakeholderService.hasCustAuthorityByRole(custCode, "01", uc)){
			customer.setType("3");
		}else if (custStakeholderService.hasCustAuthorityByRole(custCode, "00", uc)){
			customer.setType("1");
		}else if (custStakeholderService.hasCustAuthorityByRole(custCode, "01",uc)){
			customer.setType("2");
		}
		Map<String, Object> perfEstimateMap = customerService.getPerfEstimate(custCode, customer.getType(), customer.getEmpCode());
		return new ModelAndView("admin/query/customerXx/customerXx_perfEstimate")
			.addObject("customer", customer)
			.addObject("custType", custType)
			.addObject("perfEstimateMap", perfEstimateMap);
	}
	
	/**
	 * 检查能否进行查看签单业绩预估
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/goVerifyPerfEstimate")
	public void goVerifyPerfEstimate(HttpServletRequest request, HttpServletResponse response, String custCode){
		logger.debug("VerifyPerfEstimate检查开始");
		try {
			if(StringUtils.isBlank(custCode)){
				ServletUtils.outPrintFail(request, response, "客户编号不能为空");
				return;
			};
			
			Customer customer = customerService.get(Customer.class, custCode);
			if(!customerService.isSignDateAfterNewCommiDate(custCode)){
				ServletUtils.outPrintFail(request, response, "首次签单时间大于"+xtcsService.getQzById("NEWCOMMIDATE")+"才能进行提成计算");
				return;
			}
			
			CustType custType = customerService.get(CustType.class, customer.getCustType());
			if("0".equals(custType.getIsCalcCommi())){
				ServletUtils.outPrintFail(request, response, "该客户无预估数据");
				return;
			};
//			UserContext uc = getUserContext(request);
//			if(!"ADMIN".equals(uc.getJslx()) && ! uc.isSuperAdmin() && !custStakeholderService.hasEmployeeByRole(custCode, "00", uc.getEmnum() )
//					&& !custStakeholderService.hasEmployeeByRole(custCode, "01", uc.getEmnum())){
//				ServletUtils.outPrintFail(request, response, "只有该楼盘业务员或设计师可查看签单业绩预估");
//				return;
//			}
			ServletUtils.outPrintSuccess(request, response, "可以查看", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法进行该操作");
		}
	}
	
	/**
	 * 签单业绩预估数据
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/getPerfEstimate")
	@ResponseBody
	public Map<String, Object> getPrjCheck(HttpServletRequest request,
			HttpServletResponse response, String custCode, String type, String empCode)
			throws Exception {
		Map<String, Object> map = customerService.getPerfEstimate(custCode, type, empCode);
		return map;
	}
	
	/**
	 * 签单业绩预估--木作明细
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/goBasePersonalEstimateJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goBasePersonalEstimateJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		customerService.findPageBySql_basePersonalEstimate(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 签单业绩预估--主材明细
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/goZCEstimateJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goZCEstimateJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		customerService.findPageBySql_zcEstimate(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 签单业绩预估--软装明细
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/goRZEstimateJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goRZEstimateJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		customerService.findPageBySql_rzEstimate(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 签单业绩预估--集成明细
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/goJCEstimateJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJCEstimateJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		customerService.findPageBySql_jcEstimate(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
}
