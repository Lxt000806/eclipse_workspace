package com.house.home.web.controller.project;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.BaseAlgorithm;
import com.house.home.entity.basic.BasePlanTemp;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.CzyGnqx;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.FixArea;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.entity.insales.BaseItem;
import com.house.home.entity.insales.BaseItemReq;
import com.house.home.entity.project.BaseItemChg;
import com.house.home.entity.project.BaseItemChgDetail;
import com.house.home.entity.project.ItemChg;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzyGnqxService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.FixAreaService;
import com.house.home.service.insales.BaseItemReqService;
import com.house.home.service.insales.BaseItemService;
import com.house.home.service.project.BaseItemChgService;

@Controller
@RequestMapping("/admin/baseItemChg")
public class BaseItemChgController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseItemChgController.class);

	@Autowired
	private BaseItemChgService baseItemChgService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private BaseItemReqService baseItemReqService;
	@Autowired
	private BaseItemService baseItemService;
	@Autowired
	private FixAreaService fixAreaService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private CzyGnqxService czyGnqxService;

	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemChg baseItemChg) throws Exception {
		if (baseItemChg.getStatus()==null){
			baseItemChg.setStatus("1");
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemChgService.findPageBySql(page, baseItemChg, getUserContext(request));
		return new WebPage<Map<String,Object>>(page);
	}
	 
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemChg baseItemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemChgService.findDetailPageBySql(page, baseItemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????????????????
	 * @author	created by zb
	 * @date	2019-12-24--??????11:36:58
	 */
	@RequestMapping("/getBaseChgStakeholderList")
	@ResponseBody
	public WebPage<Map<String,Object>> getBaseChgStakeholderList(HttpServletRequest request,
			HttpServletResponse response, BaseItemChg baseItemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemChgService.findBaseChgStakeholderPageBySql(page, baseItemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseItemChg??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, BaseItemChg baseItemChg) throws Exception {
		if (baseItemChg.getStatus()==null){
			baseItemChg.setStatus("1");
		}
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_list")
			.addObject("baseItemChg", baseItemChg);
	}
	/**
	 * BaseItemChg??????code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/baseItemChg/baseItemChg_code");
	}
	/**
	 * ???????????????BaseItemChg??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String operType){
		logger.debug("???????????????BaseItemChg??????");
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_customerCode")
			.addObject("operType", operType);
	}
	/**
	 * ???????????????ItemChg??????
	 * @return
	 */
	@RequestMapping("/goBaseItemChgAdd")
	public ModelAndView goBaseItemChgAdd(HttpServletRequest request, HttpServletResponse response, 
			BaseItemChg baseItemChg){
		if(baseItemChg.getCustCode()==null){
			return null;
		}
		UserContext uc= this.getUserContext(request);
		String operType = request.getParameter("operType");//????????????1??????????????? 2??????????????????
		CustType custType = null;
		String longFeeCode = xtcsService.getQzById("LONGFEECODE");
		boolean haveBaseChgRight = false;
		CzyGnqx czyGnqx = czyGnqxService.getCzyGnqx("0302", "????????????", uc.getCzybh());
		String projectMan = null;
		if (czyGnqx!=null){
			haveBaseChgRight = true;
		}
		if(baseItemChg!=null){
			Customer customer=customerService.get(Customer.class,baseItemChg.getCustCode());
			custType= custTypeService.get(CustType.class, baseItemChg.getCustType());
			projectMan = customer.getProjectMan();
			baseItemChg.setCustTypeType(custType.getType());
			baseItemChg.setDocumentNo(customer.getDocumentNo());
			baseItemChg.setLastUpdatedBy(uc.getCzybh());
			baseItemChg.setDate(new Date());
			baseItemChg.setBaseFeeDirct(customer.getBaseFeeDirct());
			baseItemChg.setArea(customer.getArea());
			baseItemChg.setSignQuoteType(custType.getSignQuoteType());
			
		}
		if("A".equals(operType) && "1".equals(custType.getType()) && "0".equals(custType.getIsPartDecorate()) ){
			baseItemChg.setBaseDiscPer(baseItemChgService.getLastBaseDiscPer(baseItemChg.getCustCode()));
			baseItemChg.setBaseFeeDirct(baseItemChgService.getBaseFeeDirct(baseItemChg.getCustCode()));
		}
		baseItemChg.setCustType(baseItemChg.getCustType().trim());
		boolean existsTemp = baseItemChgService.getExistsTemp(baseItemChg.getCustCode(),baseItemChg.getNo());
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_add")
			.addObject("baseItemChg", baseItemChg)
			.addObject("custType", custType)
			.addObject("longFeeCode", longFeeCode)
			.addObject("operType", operType)
			.addObject("haveBaseChgRight", haveBaseChgRight)
			.addObject("custType", custType)
			.addObject("existsTemp", existsTemp)
			.addObject("projectMan", projectMan);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goQuickSave")
	public ModelAndView goQuickSave(HttpServletRequest request, HttpServletResponse response, 
			BaseItemChg baseItemChg){
		
		
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_quickSave")
			.addObject("baseItemChg", baseItemChg);
	}
	
	@RequestMapping("/goQPrint")
	public ModelAndView goQPrint(HttpServletRequest request, HttpServletResponse response, 
			BaseItemChg baseItemChg){
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_qPrint")
			.addObject("baseItemChg", baseItemChg);
	}
	/**
	 * ???????????????BaseItemChg??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????BaseItemChg??????");
		BaseItemChg baseItemChg = null;
		if (StringUtils.isNotBlank(id)){
			baseItemChg = baseItemChgService.get(BaseItemChg.class, id);
			resetBaseItemChg(baseItemChg);
		}
		Customer customer = new Customer();
		UserContext uc=this.getUserContext(request);
		String operType = request.getParameter("operType");
		CustType custType = null;
		String longFeeCode = xtcsService.getQzById("LONGFEECODE");
		boolean haveBaseChgRight = false;
		CzyGnqx czyGnqx = czyGnqxService.getCzyGnqx("0302", "????????????", uc.getCzybh());
		if (czyGnqx!=null){
			haveBaseChgRight = true;
		}
		if(baseItemChg!=null){
			customer = customerService.get(Customer.class,baseItemChg.getCustCode());
			custType = custTypeService.get(CustType.class, customer.getCustType());
			baseItemChg.setCustTypeType(custType.getType());
			baseItemChg.setCustType(customer.getCustType());
			baseItemChg.setDocumentNo(customer.getDocumentNo());
			baseItemChg.setLastUpdatedBy(uc.getCzybh());
			baseItemChg.setCanUseComBaseItem(custType.getCanUseComBaseItem());
			baseItemChg.setArea(customer.getArea());
			baseItemChg.setSignQuoteType(custType.getSignQuoteType());
			if(StringUtils.isNotBlank(baseItemChg.getTempNo())){
				 BasePlanTemp  basePlanTemp= baseItemChgService.get(BasePlanTemp.class, baseItemChg.getTempNo());
				 if(basePlanTemp!=null){
					 baseItemChg.setTempDescr(baseItemChg.getTempNo()+"|"+basePlanTemp.getDescr());
				 }
			} 
		}
		
		if("1".equals(custType.getType()) && "0".equals(custType.getIsPartDecorate())){
			//baseItemChg.setBaseDiscPer(baseItemChgService.getLastBaseDiscPer(baseItemChg.getCustCode()));
			baseItemChg.setBaseFeeDirct(baseItemChgService.getBaseFeeDirct(baseItemChg.getCustCode()));
		}
		baseItemChg.setCustType(baseItemChg.getCustType().trim());
		boolean existsTemp = baseItemChgService.getExistsTemp(baseItemChg.getCustCode(),baseItemChg.getNo());
		
		String notice = "";
		notice = baseItemChgService.getConfirmNotice(baseItemChg.getNo());
		
		 Map<String, Object> map = new HashMap<String, Object>();
		map = customerService.getMaxDiscByCustCode(baseItemChg.getCustCode());
		double totalRemainDisc = 0.0;
		if(customer != null){
			double designRiskFund = 0.0;
			if (map != null) {
				double designerMaxDiscAmount = customer.getDesignerMaxDiscAmount();
				double directorMaxDiscAmount = customer.getDirectorMaxDiscAmount();
				double usedDisc = Double.parseDouble(map.get("zskzsjyh").toString());
				double totalDiscAmtTran = Double.parseDouble(map.get("ExtraDiscChgAmount").toString());
				double usedDiscAmount = Double.parseDouble(map.get("UsedDiscAmount").toString());
				double usedRiskFund = Double.parseDouble(map.get("UsedRiskFund").toString());
				map.put("DesignerMaxDiscAmount", designerMaxDiscAmount);
				map.put("DirectorMaxDiscAmount", directorMaxDiscAmount);
				
				map.put("RemainDisc", designerMaxDiscAmount + directorMaxDiscAmount - usedDisc);
				if(customer.getDesignRiskFund() != null){
					designRiskFund = customer.getDesignRiskFund();
				} 
				totalRemainDisc = designerMaxDiscAmount + directorMaxDiscAmount + totalDiscAmtTran + usedDiscAmount + designRiskFund + usedRiskFund;
			}
		}
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_update")
			.addObject("baseItemChg", baseItemChg)
			.addObject("custType", custType)
			.addObject("longFeeCode", longFeeCode)
			.addObject("operType", operType)
			.addObject("haveBaseChgRight", haveBaseChgRight)
			.addObject("existsTemp",existsTemp)
			.addObject("notice", notice).addObject("totalRemainDisc", totalRemainDisc);
	}
	
	/**
	 * ???????????????BaseItemChg??????
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response){
		logger.debug("???????????????BaseItemChg??????");
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_detail");
	}
	/**
	 * ????????????????????????????????????
	 * @author	created by zb
	 * @date	2019-12-20--??????3:59:53
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goChgStakeholderEdit")
	public ModelAndView goChgStakeholderEdit(HttpServletRequest request, HttpServletResponse response, String chgStakeholderList){
		logger.debug("????????????????????????????????????");
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_chgStakeholder")
			.addObject("chgStakeholderList", chgStakeholderList.replace("},", "}\\@zb"));
	}
	/**
	 * @author	created by zb
	 * @date	2019-12-23--??????3:55:29
	 */
	@RequestMapping("/goEmpAdd")
	public ModelAndView goEmpAdd(HttpServletRequest request, HttpServletResponse response, BaseItemChg baseItemChg, String keys){
		logger.debug("????????????????????????????????????");
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_chgStakeholderAdd")
			.addObject("baseItemChg", baseItemChg)
			.addObject("keys", keys);
	}
	
	@RequestMapping("/goBaseFromTemp")
	public ModelAndView goBaseFromTemp(HttpServletRequest request, HttpServletResponse response, BaseItemChg baseItemChg){
		logger.debug("???????????????????????????");
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_basePlanFromTemp")
			.addObject("baseItemChg", baseItemChg);
	}
	
	@RequestMapping("/goFixAreaAdd")
	public ModelAndView goFixAreaAdd(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		
		Customer customer = new Customer();
		CustType custType = new CustType();
		
		if(StringUtils.isNotBlank(baseItemChg.getCustCode())){
			customer = customerService.get(Customer.class, baseItemChg.getCustCode());
			if(customer != null) {
				custType = custTypeService.get(CustType.class, customer.getCustType());
				baseItemChg.setBaseTempNo(customer.getBaseTempNo());
			}
		}
		
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_fixAreaAdd")
			.addObject("baseItemChg", baseItemChg).addObject("custType", custType);
	}
	
	@RequestMapping("/goFixAreaEdit")
	public ModelAndView goFixAreaEdit(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		
		FixArea fixArea = new FixArea();
		PrePlanArea prePlanArea = new PrePlanArea();
		if(baseItemChg.getFixAreaPk() != null){
			
			fixArea = baseItemChgService.get(FixArea.class, Integer.parseInt(baseItemChg.getFixAreaPk()));
		}
		if(fixArea.getPrePlanAreaPK() != null){
			prePlanArea = baseItemChgService.get(PrePlanArea.class, fixArea.getPrePlanAreaPK());
		}
		if(prePlanArea != null){
			fixArea.setPrePlanAreaDescr(prePlanArea.getDescr());
		}
		
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_fixAreaEdit")
			.addObject("baseItemChg", baseItemChg).addObject("fixArea", fixArea);
	}
	
	@RequestMapping("/goFixAreaInsert")
	public ModelAndView goFixAreaInsert(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		
		Customer customer = new Customer();
		CustType custType = new CustType();
		
		if(StringUtils.isNotBlank(baseItemChg.getCustCode())){
			customer = customerService.get(Customer.class, baseItemChg.getCustCode());
			if(customer != null) {
				custType = custTypeService.get(CustType.class, customer.getCustType());
				baseItemChg.setBaseTempNo(customer.getBaseTempNo());
			}
		}
		
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_fixAreaInsert")
			.addObject("baseItemChg", baseItemChg).addObject("custType", custType);
	}
	
	/**
	 * ??????BaseItemChg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseItemChg baseItemChg){
		logger.debug("??????BaseItemChg??????");
		try{
			String detailJson = request.getParameter("detailJson");
			String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
			String operStatus = request.getParameter("operStatus");
			if (StringUtils.isNotBlank(operStatus)){
				baseItemChg.setStatus(operStatus);
			}
			baseItemChg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = baseItemChgService.saveForProc(baseItemChg, xml);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????BaseItemChg??????");
		}
	}
	
	/**
	 * ??????BaseItemChg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseItemChg baseItemChg){
		logger.debug("??????BaseItemChg??????");
		try{
			baseItemChg.setLastUpdate(new Date());
			baseItemChg.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseItemChgService.update(baseItemChg);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????BaseItemChg??????");
		}
	}
	
	/**
	 * ??????BaseItemChg
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????BaseItemChg??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseItemChg??????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseItemChg baseItemChg = baseItemChgService.get(BaseItemChg.class, deleteId);
				if(baseItemChg == null)
					continue;
				baseItemChg.setExpired("T");
				baseItemChgService.update(baseItemChg);
			}
		}
		logger.debug("??????BaseItemChg IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}

	/**
	 *BaseItemChg??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseItemChg baseItemChg){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemChgService.findPageBySql(page, baseItemChg, getUserContext(request));
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseItemChg baseItemChg){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemChgService.findDetailPageBySql(page, baseItemChg);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * ???????????????
	 */
	@RequestMapping("/goTransAction")
	public ModelAndView goTransAction(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		Czybm czybm=czybmService.get(Czybm.class,uc.getCzybh());
		Customer customer = null;
		CustType custType = null;
		if(StringUtils.isNotBlank(baseItemChg.getCustCode())){
			customer = customerService.get(Customer.class, baseItemChg.getCustCode());
			if(customer != null){
				custType = custTypeService.get(CustType.class, customer.getCustType());
				if(custType != null){
					baseItemChg.setCustTypeType(custType.getType());
				}
			}
		}
		baseItemChg.setCostRight(czybm.getCostRight());
		if(StringUtils.isBlank(baseItemChg.getM_umState())){
			baseItemChg.setM_umState("A");
		}
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_transAction")
			.addObject("baseItemChg", baseItemChg);
		
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param baseItemChg
	 * @return
	 */
	@RequestMapping("/goInSetChg")
	public ModelAndView goInSetChg(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		Czybm czybm=czybmService.get(Czybm.class,uc.getCzybh());
		Customer customer = null;
		CustType custType = null;
		if(StringUtils.isNotBlank(baseItemChg.getCustCode())){
			customer = customerService.get(Customer.class, baseItemChg.getCustCode());
			if(customer != null){
				custType = custTypeService.get(CustType.class, customer.getCustType());
				if(custType != null){
					baseItemChg.setCustTypeType(custType.getType());
				}
			}
		}
		baseItemChg.setCostRight(czybm.getCostRight());
		if(StringUtils.isBlank(baseItemChg.getM_umState())){
			baseItemChg.setM_umState("A");
		}
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_inSetChg")
			.addObject("baseItemChg", baseItemChg);
		
	}
	
	/**
	 * ?????????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goTransActionJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getTransActionJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemReq baseItemReq) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemReqService.findBaseItemReqList(page, baseItemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 
	 * ???????????????excel??????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request,
			HttpServletResponse response,BaseItemChg baseItemChg) throws Exception {
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_import")
			.addObject("baseItemChg", baseItemChg);
	}
	/**
	 * 
	 * ??????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("/loadImport")
	@ResponseBody
	public Map<String, Object> loadImport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ExcelImportUtils<BaseItemChg> eUtils = new ExcelImportUtils<BaseItemChg>();
		String custCode = "";
		String canUseComBaseItem = "";
		boolean hasInvalid = false;
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// ??????????????? ????????????
			String fieldName = obit.getFieldName();
			String fieldValue = obit.getString();
			if ("custCode".equals(fieldName)){
				custCode = fieldValue.trim();
			}
			if ("canUseComBaseItem".equals(fieldName)){
				canUseComBaseItem = fieldValue.trim();
			}
			if ("file".equals(fieldName)){
				in=obit.getInputStream();
			}
		}
		
		try {
			List<Map<String,Object>> result = eUtils.importExcel(in);
			List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
			String custTypeType = customerService.getCustTypeTypeByCode(custCode);
			String customerType = customerService.get(Customer.class, custCode).getCustType();
			int i = 1;
			for(Map<String,Object> baseItemChg:result){
				Map<String,Object> data = new HashMap<String, Object>();
				String fixareadescr = (String) baseItemChg.get("????????????");
				String baseitemcode = (String) baseItemChg.get("??????????????????");
				String qty = (String) baseItemChg.get("????????????");
				String remarks = (String) baseItemChg.get("??????");
				String baseitemsetno=(String) baseItemChg.get("?????????");
				String ismainitemdescr=(String) baseItemChg.get("?????????");
				String isinvalid, isinvaliddescr;
				if (StringUtils.isBlank(fixareadescr) || StringUtils.isBlank(baseitemcode) || StringUtils.isBlank(qty)){
					isinvalid = "1";
					isinvaliddescr = "??????,??????????????????";
				}else{
					isinvalid = "0";
					isinvaliddescr = "??????";
				}
				BaseItem baseItem = null;
				//????????????????????????
				if (StringUtils.isNotBlank(baseitemcode)){
					baseItem = baseItemService.get(BaseItem.class, baseitemcode);
					if (baseItem!=null){
						if (StringUtils.isBlank(remarks)){
							remarks = baseItem.getRemark();
						}
						if(StringUtils.isNotBlank(baseItem.getCustType())){
							if(!baseItem.getCustType().equals(customerType)){
								isinvalid = "2";
								isinvaliddescr = "??????????????????";
							}
						}else if("0".equals(canUseComBaseItem)){
							isinvalid = "2";
							isinvaliddescr = "??????????????????????????????????????????";
						}
						
						if ("0".equals(isinvalid) && !"F".equals(baseItem.getExpired())){
							isinvalid = "2";
							isinvaliddescr = "?????????????????????";
						}
					}else{
						if ("0".equals(isinvalid)){
							isinvalid = "2";
							isinvaliddescr = "?????????????????????";
						}
					}
				}
				if ("1".equals(custTypeType)){
					//??????????????????????????????????????????
					if ("0".equals(isinvalid) && baseItem!=null && 
							("2".equals(baseItem.getCategory()) || "3".equals(baseItem.getCategory()) || "4".equals(baseItem.getCategory()))){
						isinvalid = "3";
						isinvaliddescr = "????????????????????????";
					}
				}else{
					//??????????????????????????????????????????????????????
					if ("0".equals(isinvalid) && baseItem!=null 
							&& !"4".equals(baseItem.getCategory()) && StringUtils.isNotBlank(qty) && Float.parseFloat(qty)<0){
						isinvalid = "4";
						isinvaliddescr = "???????????????????????????";
					}
				}
				
				data.put("isinvalid", isinvalid);
				data.put("isinvaliddescr", isinvaliddescr);
				data.put("fixareadescr", fixareadescr);
				data.put("baseitemcode", baseitemcode);
				data.put("baseitemdescr", baseItemChg.get("??????????????????"));
				data.put("qty", qty);
				data.put("remarks", remarks);
				data.put("reqpk", null);
				data.put("dispseq", i);
				data.put("preqty", 0);
				data.put("baseitemsetno", baseitemsetno);
				if (StringUtils.isNotBlank(ismainitemdescr)){
					if ("???".equals(ismainitemdescr)||"0".equals(ismainitemdescr)){
						data.put("ismainitem", '0');
						data.put("ismainitemdescr", '???');
					}else{
						data.put("ismainitem", '1');
						data.put("ismainitemdescr", '???');
					}
				}else{
					data.put("ismainitem", '1');
					data.put("ismainitemdescr", '???');
				}
				if (baseItem!=null){
					data.put("unitprice", baseItem.getOfferPri());
					data.put("material", baseItem.getMaterial());
					data.put("uom", baseItemService.getUomByBaseItemCode(baseItem.getCode()));
					data.put("cost", baseItem.getCost());
				}
				if (!"0".equals(isinvalid)){
					hasInvalid = true;
				}
				datas.add(data);
				i++;
			}
			map.put("success", true);
			map.put("returnInfo", "??????????????????");
			map.put("hasInvalid", hasInvalid);
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "???????????????????????????????????????,??????????????????????????????????????????!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	/**
	 * ??????BaseItemChg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doImport")
	public void doImport(HttpServletRequest request, HttpServletResponse response){
		logger.debug("??????BaseItemChg??????");
		try{
			String rows = request.getParameter("rows");
			String custCode = request.getParameter("custCode");
			net.sf.json.JSONArray arryRows = net.sf.json.JSONArray.fromObject(rows);
			UserContext uc = this.getUserContext(request);
			List<JSONObject> list = new ArrayList<JSONObject>();
			for (int i = 0; i < arryRows.size(); i++) {
	            JSONObject jsonObject = arryRows.getJSONObject(i);
	            String reqpk = jsonObject.getString("reqpk");
	            String fixareadescr = jsonObject.getString("fixareadescr");
	            String baseitemcode = jsonObject.getString("baseitemcode");
	            FixArea fixArea = new FixArea();
	            fixArea.setCustCode(custCode);
	            fixArea.setDescr(fixareadescr);
	            fixArea.setLastUpdatedBy(uc.getCzybh());
	            fixArea.setItemType1("JZ");
	            fixArea.setIsService(0);
	            if (StringUtils.isBlank(reqpk)){
	            	int pk = fixAreaService.getFixAreaPk(fixArea.getItemType1(),fixArea.getCustCode(),
	            			fixArea.getDescr(),fixArea.getIsService());
	            	if (pk==0){
	            		fixAreaService.addtFixArea(fixArea);
	            		pk = fixAreaService.getFixAreaPk(fixArea.getItemType1(),fixArea.getCustCode(),
		            			fixArea.getDescr(),fixArea.getIsService());
	            	}else{
	            		FixArea oldFixArea = new FixArea();
	            		PrePlanArea prePlanArea = new PrePlanArea();
	            		oldFixArea = fixAreaService.get(FixArea.class, pk);
	            		if(oldFixArea != null){
	            			jsonObject.put("preplanareapk", oldFixArea.getPrePlanAreaPK());
	            			
	            			if(oldFixArea.getPrePlanAreaPK() != null){
	            				prePlanArea = fixAreaService.get(PrePlanArea.class, oldFixArea.getPrePlanAreaPK());
	            				if(prePlanArea != null){
	            					jsonObject.put("preplanareadescr", prePlanArea.getDescr());
	            				}
	            			}
	            		}
	            	}
	            	
	            	jsonObject.put("fixareapk", pk);
	            }else{
	            	BaseItemReq baseItemReq = baseItemReqService.get(BaseItemReq.class, Integer.parseInt(reqpk));
	            	if (baseItemReq==null){
	            		String str = "???????????????"+baseitemcode+"??????????????????????????????PK???";
	            		ServletUtils.outPrintFail(request, response, str);
	            		return;
	            	}else{
	            		jsonObject.put("fixareapk", baseItemReq.getFixAreaPk());
	            		jsonObject.put("prjctrltype", baseItemReq.getPrjCtrlType());
	            		jsonObject.put("offerctrl", baseItemReq.getOfferCtrl());
	            		jsonObject.put("materialctrl", baseItemReq.getMaterialCtrl());
	            	}
	            }
	            if (StringUtils.isNotBlank(baseitemcode)){
	            	BaseItem baseItem = baseItemService.get(BaseItem.class, baseitemcode);
	            	if (baseItem!=null){
	            		jsonObject.put("category", baseItem.getCategory());
	            		jsonObject.put("iscalmangefee", baseItem.getIsCalMangeFee());
	            		jsonObject.put("prjctrltype", baseItem.getPrjCtrlType());
	            		jsonObject.put("offerctrl", baseItem.getOfferCtrl());
	            		jsonObject.put("materialctrl", baseItem.getMaterialCtrl());
	            	}
	            }
	            list.add(jsonObject);
	        }
			JSONObject jo = new JSONObject();
			jo.put("success", true);
			jo.put("datas", list);
			ServletUtils.outPrintSuccess(request, response, "????????????", list);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/beforeGoZjyj")
	public void beforeGoZjyj(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		try {
			BaseItemChg itemChg = baseItemChgService.get(BaseItemChg.class, baseItemChg.getNo());
			if (!itemChg.getStatus().equals(baseItemChg.getStatus())){
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????????????????!");
				return;
			}
			if (itemChg.getPerfPk()!=null && !itemChg.getPerfPk().equals(baseItemChg.getPerfPk())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????????????????????????????!");
				return;
			}
			if (!itemChg.getIscalPerf().equals(baseItemChg.getIscalPerf())){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????????????????!");
				return;
			}
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/goZjyj")
	public ModelAndView goZjyj(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_zjyj")
			.addObject("baseItemChg",baseItemChg);
	}
	
	@RequestMapping("/doPrjReturn")
	public void doPrjReturn(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("??????PrjProg??????");
		
		this.baseItemChgService.doPrjReturn(no,this.getUserContext(request).getCzybh());
		
		ServletUtils.outPrintSuccess(request, response,"??????????????????");
	}
	
	@RequestMapping("/doPrjReceive")
	public void doPrjReceive(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("??????PrjProg??????");
		this.baseItemChgService.doPrjReceive(no,this.getUserContext(request).getCzybh());
		
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/doZjyj")
	public void doZjyj(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		try {
			UserContext uc = this.getUserContext(request);
			BaseItemChg itemChg = baseItemChgService.get(BaseItemChg.class, baseItemChg.getNo());
			itemChg.setIscalPerf(baseItemChg.getIscalPerf());
			itemChg.setLastUpdate(new Date());
			itemChg.setLastUpdatedBy(uc.getCzybh());
			itemChg.setActionLog("EDIT");
			baseItemChgService.update(itemChg);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
		
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/goDiscRemark")
	public ModelAndView goDiscRemark(HttpServletRequest request, HttpServletResponse response, String custCode){
		Customer customer = customerService.get(Customer.class, custCode);
		
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_discRemark")
			.addObject("customer",customer);
	}
	
	/**
	 * ?????????baseItemChg??????????????????
	 * @return
	 */
	@RequestMapping("/goBaseItemChg_detailEdit")
	public ModelAndView goBaseItemChg_detailEdit(HttpServletRequest request, HttpServletResponse response,
			BaseItemChgDetail baseItemChgDetail){
		String operType = request.getParameter("operType");
		String custTypeType = request.getParameter("custTypeType");
		String customerType=request.getParameter("custType");
		String canUseComBaseItem=request.getParameter("canUseComBaseItem");
		String algorithm = request.getParameter("algorithm");
		String prePlanAreaDescr = request.getParameter("prePlanAreaDescr");
		String prePlanAreaPK = request.getParameter("prePlanAreaPK");
		UserContext uc = this.getUserContext(request);
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_detailEdit")
			.addObject("baseItemChgDetail", baseItemChgDetail)
			.addObject("operType", operType)
			.addObject("custTypeType", custTypeType)
			.addObject("canUseComBaseItem", canUseComBaseItem)
			.addObject("customerType",customerType)
			.addObject("prePlanAreaDescr", prePlanAreaDescr)
			.addObject("algorithm", algorithm)
			.addObject("prePlanAreaPK", prePlanAreaPK);
	}
	
	private void resetBaseItemChg(BaseItemChg baseItemChg){
		if (baseItemChg!=null){
			Customer customer = customerService.get(Customer.class, baseItemChg.getCustCode());
			if (customer!=null){
				baseItemChg.setAddress(customer.getAddress());
				baseItemChg.setDocumentNo(customer.getDocumentNo());
				CustType custType = custTypeService.get(CustType.class, customer.getCustType());
				if (custType!=null){
					baseItemChg.setCustTypeType(custType.getType());
				}
			}
			if (baseItemChg.getAppCzy()!=null){
				Czybm czybm = czybmService.get(Czybm.class, baseItemChg.getAppCzy());
				baseItemChg.setAppCzyDescr(czybm==null?"":czybm.getZwxm());
			}
			if (baseItemChg.getConfirmCzy()!=null){
				Czybm czybm = czybmService.get(Czybm.class, baseItemChg.getConfirmCzy());
				baseItemChg.setConfirmCzyDescr(czybm==null?"":czybm.getZwxm());
			}
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param itemChg  
	 */
	@RequestMapping("/doBaseItemPlanFromTemp")
	public void doBaseItemPlanFromTemp(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		baseItemChg.setLastUpdatedBy(uc.getCzybh());
		Result result= baseItemChgService.doBaseItemChgTempProc(baseItemChg);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/getRegenBaseItemChgFromPrePlanTemp")
	public void getRegenBaseItemChgFromPrePlanTemp(HttpServletRequest request, HttpServletResponse response,BaseItemChg baseItemChg){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		baseItemChg.setLastUpdatedBy(uc.getCzybh());
		Result result= baseItemChgService.doRegenBaseItemChgFromPrePlanTemp(baseItemChg);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @author ?????????
	 */
    @RequestMapping("/goDeptLeaderConfirm")
    public ModelAndView goDeptLeaderConfirm(HttpServletRequest request,
            HttpServletResponse response, String id) {
        
        BaseItemChg baseItemChg = null;
        if (StringUtils.isNotBlank(id)) {
            baseItemChg = baseItemChgService.get(BaseItemChg.class, id);
            resetBaseItemChg(baseItemChg);
        }
        
        UserContext userContext = getUserContext(request);
        String longFeeCode = xtcsService.getQzById("LONGFEECODE");
        CustType custType = null;

        if (baseItemChg != null) {
            Customer customer = customerService.get(Customer.class, baseItemChg.getCustCode());
            custType = custTypeService.get(CustType.class, customer.getCustType());
            baseItemChg.setCustTypeType(custType.getType());
            baseItemChg.setCustType(customer.getCustType());
            baseItemChg.setDocumentNo(customer.getDocumentNo());
            baseItemChg.setLastUpdatedBy(userContext.getCzybh());
            baseItemChg.setCanUseComBaseItem(custType.getCanUseComBaseItem());
            baseItemChg.setArea(customer.getArea());
            
            if (StringUtils.isNotBlank(baseItemChg.getTempNo())) {
                BasePlanTemp basePlanTemp = baseItemChgService.get(BasePlanTemp.class, baseItemChg.getTempNo());
                if (basePlanTemp != null) {
                    baseItemChg.setTempDescr(baseItemChg.getTempNo() + "|"
                            + basePlanTemp.getDescr());
                }
            }
        }

        if ("1".equals(custType.getType())
                && "0".equals(custType.getIsPartDecorate())) {
            baseItemChg.setBaseFeeDirct(baseItemChgService.getBaseFeeDirct(baseItemChg.getCustCode()));
        }
        
        baseItemChg.setCustType(baseItemChg.getCustType().trim());
        
        return new ModelAndView("admin/project/baseItemChg/baseItemChg_deptLeaderConfirm")
                .addObject("baseItemChg", baseItemChg)
                .addObject("custType", custType)
                .addObject("longFeeCode", longFeeCode);
    }

    
    @RequestMapping("/doDeptLeaderConfirm")
    public void doDeptLeaderConfirm(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        try {
            Result result = baseItemChgService.doDeptLeaderConfirm(no, getUserContext(request));
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "???????????????????????????");
        }
    }
    
    /**
	 * @author	created by rlx
	 * @date	2020-12-09--??????4:18:29
	 */
	@RequestMapping("/goViewChg")
	public ModelAndView goViewChg(HttpServletRequest request, HttpServletResponse response, String code){
		logger.debug("?????????????????????????????????");
		Customer customer = customerService.get(Customer.class,code);
		UserContext uc = this.getUserContext(request);
		String costRight = uc.getCostRight();
		String itemRight = uc.getItemRight();
		return new ModelAndView("admin/project/baseItemChg/baseItemChg_viewChg").addObject("customer", customer)
				.addObject("costRight", costRight).addObject("itemRight", itemRight);	
	}
	
	/**
	 * @author	created by rlx
	 * @date	2020-12-10--??????3:47:29
	 */
	@RequestMapping("/goClzj")
	public ModelAndView goClzj(HttpServletRequest request, HttpServletResponse response, String code) throws Exception{
		Customer customer = customerService.get(Customer.class,code);
		return new ModelAndView("admin/query/customerXx/tab_clzj")
		.addObject("customer", customer);
	}
}
