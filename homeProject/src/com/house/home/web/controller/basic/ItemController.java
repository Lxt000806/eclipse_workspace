package com.house.home.web.controller.basic;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.fileUpload.FileUploadRule;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.ItemPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.ItemBean;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemPic;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.ItemType3;
import com.house.home.entity.basic.Uom;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.insales.Brand;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ItemPicService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.basic.ItemType2Service;
import com.house.home.service.basic.UomService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.ItemPlanService;
import com.house.home.service.insales.BrandService;
import com.house.home.service.insales.SupplierService;
import com.house.home.service.insales.WareHouseService;

@Controller
@RequestMapping("/admin/item")
public class ItemController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemService itemService;
	@Autowired
	CzybmService czybmService; 
	@Autowired 
	ItemType2Service itemType2Service;
	@Autowired 
	ItemType2Service itemType3Service;
	@Autowired
	BrandService brandService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	UomService uomService;
	@Autowired
	ItemPicService itemPicService;
	@Autowired
	ItemPlanService itemPlanService;
	@Autowired
	XtdmService xtdmService;
	@Autowired
	XtcsService xtcsService;
	@Autowired
	WareHouseService wareHouseService;
	
	String savePhotoPath;
	String savePath;
	
	private void resetItem(Item item){
		if (item!=null){
			if (StringUtils.isNotBlank(item.getItemType2())){
				ItemType2 itemType2 = itemType2Service.get(ItemType2.class, item.getItemType2());
				if (itemType2!=null){
					item.setItemType2Descr(itemType2.getDescr());
				}
			}	
			if (StringUtils.isNotBlank(item.getItemType3())){
				ItemType3 itemType3 = itemType3Service.get(ItemType3.class, item.getItemType3());
				if (itemType3!=null){
					item.setItemType3Descr(itemType3.getDescr());
				}
			}	
			if (StringUtils.isNotBlank(item.getSqlCode())){
				Brand brand= brandService.get(Brand.class, item.getSqlCode());
				if (brand!=null){
					item.setSqlDescr(brand.getDescr());
				}
			}	
			if (StringUtils.isNotBlank(item.getSupplCode())){
				Supplier supplier = supplierService.get(Supplier.class, item.getSupplCode());
				if (supplier!=null){
					item.setSupplDescr(supplier.getDescr());
					item.setIsContainTax(supplier.getIsContainTax());
				}
			}
			if (StringUtils.isNotBlank(item.getBuyer1())){
				Employee employee = employeeService.get(Employee.class, item.getBuyer1());
				if (employee!=null){
					item.setBuyer1Descr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(item.getBuyer2())){
				Employee employee = employeeService.get(Employee.class, item.getBuyer2());
				if (employee!=null){
					item.setBuyer2Descr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(item.getUom())){
				Uom uom = uomService.get(Uom.class, item.getUom());
				if (uom!=null){
					item.setUomDescr(uom.getDescr());
				}
			}
			if (StringUtils.isNotBlank(item.getWhCode())){
				WareHouse wareHouse = wareHouseService.get(WareHouse.class, item.getWhCode());
				if (wareHouse!=null){
					item.setWhDescr(wareHouse.getDesc1());
				}
			}
			if (StringUtils.isNotBlank(item.getWareHouseItemCode())){
				Item item1 = itemService.get(Item.class, item.getWareHouseItemCode());
				if (item1!=null){
					item.setWareHouseItemDescr(item1.getDescr());
				}
			}

		}
	}
	
	/**
	 * ????????????1???2???3????????????
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/itemType/{type}/{pCode}") //??????????????????1,2,3
	@ResponseBody
	public JSONObject getItemType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.itemService.findItemType(type, pCode);
		return this.out(regionList, true);
	}
	/**
	 * ????????????1???2???3????????????
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/itemTypeByAuthority/{type}/{pCode}") //??????????????????1,2,3
	@ResponseBody
	public JSONObject getItemTypeByAuthority(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Map<String,Object>> regionList = this.itemService.findItemTypeByAuthority(type, pCode,uc);
		return this.out(regionList, true);
	}
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if("false".equalsIgnoreCase(request.getParameter("isCount"))){
			page.autoCount(false);
		}
		if (StringUtils.isNotBlank(item.getCustType())) {
			CustType custType=itemService.get(CustType.class,item.getCustType());
			if(custType!=null){
				item.setCanUseComItem(custType.getCanUseComItem());
			}
		} 
		itemService.findPageBySql(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSuggestJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSuggestJqGrid(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if("false".equalsIgnoreCase(request.getParameter("isCount"))){
			page.autoCount(false);
		}
		if (StringUtils.isNotBlank(item.getCustType())) {
			CustType custType=itemService.get(CustType.class,item.getCustType());
			if(custType!=null){
				item.setCanUseComItem(custType.getCanUseComItem());
			}
		} 
		itemService.findSuggestPageBySql(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSupplCostJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goSupplCostJqGrid(HttpServletRequest request,
	        HttpServletResponse response, Item item) {
	    
	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    itemService.findSupplCostPageBySql(page, item);
	    
	    return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * ??????BatchUpdateJqGrid????????????
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridBatchUpdate")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridBatchUpdate(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemService.findPageBySql_updateBatch(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????UpdatePrePrice????????????
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridUpdatePrePrice")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridUpdatePrePrice(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemService.findPageBySql_updatePrePrice(page, item);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goPurchJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPurchJqGrid(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemService.findPurchPageBySql(page, item);
		return new WebPage<Map<String,Object>>(page);
	}

	
	/**
	 * ????????????????????????JqGrid????????????
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemMessageJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemMessageJqGrid(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//		if(czybmService.hasAuthByCzybh(uc.getCzybh(),1072)&&!czybmService.isSuperAdmin(uc.getCzybh())){
//			item.setHasBuyerRight("1");
//		}
//		if(czybmService.hasAuthByCzybh(uc.getCzybh(),1073)&&!czybmService.isSuperAdmin(uc.getCzybh())){
//			item.setHasBuyerDeptRight("1");
//		}
	
		
		if (czybmService.hasGNQXByCzybh(uc.getCzybh(), "0038", "?????????????????????")) {
			item.setHasBuyerRight("1");
		}
		if (czybmService.hasGNQXByCzybh(uc.getCzybh(), "0038", "?????????????????????")) {
			item.setHasBuyerDeptRight("1");
		}
		itemService.findPageBySql_itemMessage(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Item??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		UserContext uc = getUserContext(request);
		return new ModelAndView("admin/insales/item/item_list")
			.addObject("item", item)
		    .addObject("isCostRight",uc.getCostRight());
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {

		return new ModelAndView("admin/insales/item/item_code")
			.addObject("item", item);
	}
	
	/**
	 * ??????id????????????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getItem")
	@ResponseBody
	public JSONObject getItem(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("?????????id??????", false);
		}
		Item item = itemService.get(Item.class, id);
		if(item == null){
			return this.out("??????????????????code="+id+"???????????????", false);
		}
		return this.out(item, true);
	}
	
	/**
	 * ???????????????Item??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			Item item){
		logger.debug("???????????????Item??????");
		UserContext uc = getUserContext(request);
		item.setM_umState("A");
		item.setItemSize(0);
		item.setPerWeight(0.0);
	    item.setPerNum(0.0);
	    item.setPackageNum(1);
	    item.setIsSetItem("0");
	    item.setDispSeq(10000000);
	    item.setHasSample("0");
	    item.setProjectCost(0.0);
	    item.setIsFixPrice("1");
	    item.setCommiType("2");
	    item.setMinQty(0.0);
	    item.setCommiType("2");
	    item.setMinQty(0.0);
	    item.setIsFee("0");
	    item.setIsInv("1");
	    item.setPerfPer(1.0);
	    item.setIsClearInv("0");
	    item.setwHFee(0.0);
	    item.setLampNum(0.0);
	    item.setInstallFeeType("1");
	    item.setInstallFee(0.0);
	    item.setWhFeeType("1");
	    item.setLastUpdatedBy(uc.getCzybh());
	    item.setIsActualItem("1");
	    item.setAdditionalCost(0.0);
		return new ModelAndView("admin/insales/item/item_save")
			.addObject("item", item)
			// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//			.addObject("isUpdatePrice",czybmService.hasAuthByCzybh(uc.getCzybh(),1061));
			.addObject("isUpdatePrice",czybmService.hasGNQXByCzybh(uc.getCzybh(),"0038","????????????"));
	}
	
	@RequestMapping("/addSupplierCost")
    public ModelAndView addSupplierCost(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/insales/item/item_supplierCost_add")
            .addObject("map", object);
    }

    @RequestMapping("/updateSupplierCost")
    public ModelAndView updateSupplierCost(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/insales/item/item_supplierCost_update")
            .addObject("map", object);
    }
	
	/**
	 * ???????????????Item??????
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????Item??????");
		Item item = null;
		if (StringUtils.isNotBlank(id)){
			item = itemService.get(Item.class, id);
		}else{
			item = new Item();
		}
		UserContext uc = getUserContext(request);
		item.setM_umState("C");
		item.setCode("");
		item.setDescr("");
		item.setAllQty(null);
		item.setCost(null);
		item.setCrtDate(null);
		resetItem(item);
		item.setPerfPer(1.0);
		item.setLastUpdatedBy(uc.getCzybh());
		return new ModelAndView("admin/insales/item/item_copy")
			.addObject("item", item);
	}
	
	/**
	 * ???????????????Item??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????Item??????");
		UserContext uc = getUserContext(request);
		Item item = null;
		if (StringUtils.isNotBlank(id)){
			item = itemService.get(Item.class, id);
		}else{
			item = new Item();
		}
		item.setM_umState("M");
		String isEditDescr="";
		String hasItemPlan="";
		item.setLastUpdatedBy(uc.getCzybh());
		// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//		if(czybmService.hasAuthByCzybh(uc.getCzybh(),1067)){
		if(czybmService.hasGNQXByCzybh(uc.getCzybh(),"0038","??????????????????")){
			isEditDescr="1";	
		}else{
			isEditDescr="0";
		}
		if(itemService.hasItemPlan(item.getCode())){
			hasItemPlan="1";	
		}else{
			hasItemPlan="0";
		}
		resetItem(item);
		Employee employee = employeeService.get(Employee.class, item.getLastUpdatedBy());
		return new ModelAndView("admin/insales/item/item_update")
			.addObject("item", item)
			.addObject("isEditDescr", isEditDescr)
			.addObject("isCostRight",uc.getCostRight())
			.addObject("hasItemPlan",hasItemPlan)
			// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//			.addObject("isUpdatePrice",czybmService.hasAuthByCzybh(uc.getCzybh(),1061))
			.addObject("isUpdatePrice",czybmService.hasGNQXByCzybh(uc.getCzybh(),"0038","????????????"))
			.addObject("employee", employee);
	}

	/**
	 * ???????????????Item??????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????Item??????");
		UserContext uc = getUserContext(request);
		Item item = null;
		if (StringUtils.isNotBlank(id)){
			item = itemService.get(Item.class, id);
		}else{
			item = new Item();
		}
		String isEditDescr="";
		if(
				// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//				czybmService.hasAuthByCzybh(uc.getCzybh(),895)
				czybmService.hasGNQXByCzybh(uc.getCzybh(),"0038","??????????????????")
				&&!itemService.hasItemPlan(item.getCode())){
			isEditDescr="1";	
		}else{
			isEditDescr="0";
		}
		
		item.setM_umState("V");
		
		resetItem(item);
		Employee employee = employeeService.get(Employee.class, item.getLastUpdatedBy());
		return new ModelAndView("admin/insales/item/item_view")
			.addObject("item", item)
			.addObject("isEditDescr", isEditDescr)
			.addObject("isCostRight",uc.getCostRight())
			.addObject("employee", employee);
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/goUpdateValue")
	public ModelAndView goUpdateValue(HttpServletRequest request, HttpServletResponse response,Item item){
		return new ModelAndView("admin/insales/item/item_valueUpdate").addObject("item",item);
	}
	
	/**
	 * ????????????excel????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemImportExcel")
	public ModelAndView goItemImportExcel(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		return new ModelAndView("admin/insales/item/item_importExcel").addObject("item", item);
	}
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goUpdatePrePrice")
	public ModelAndView goUpdatePrePrice(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????Item??????");
		Item item = null;
		if (StringUtils.isNotBlank(id)){
			item = itemService.get(Item.class, id);
		}else{
			item = new Item();
		}		
		return new ModelAndView("admin/insales/item/item_prePriceUpdate")
			.addObject("item", item);
	}
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goUpdateInvinfo")
	public ModelAndView goUpdateInvinfo(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("?????????????????????????????????");
		Item item = null;
		if (StringUtils.isNotBlank(id)){
			item = itemService.get(Item.class, id);
		}else{
			item = new Item();
		}
		resetItem(item);
		return new ModelAndView("admin/insales/item/item_invInfo")
			.addObject("item", item);
	}
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goUpdatePerfPer")
	public ModelAndView goUpdatePerfPer(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("?????????????????????????????????");
		Item item = null;
		if (StringUtils.isNotBlank(id)){
			item = itemService.get(Item.class, id);
		}else{
			item = new Item();
		}
		resetItem(item);
		return new ModelAndView("admin/insales/item/item_perfPerUpdate")
			.addObject("item", item);
	}
	
	/**
	 * ???????????????Item??????
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????Item??????");
		Item item = itemService.get(Item.class, id);
		
		return new ModelAndView("admin/insales/item/item_detail")
				.addObject("item", item);
	}
	/**
	 * ???????????????????????????????????????
	 * @return 
	 */
	@RequestMapping("/goUpdateBatch")
	public ModelAndView goBatchUpdate(HttpServletRequest request,HttpServletResponse response,
			Item item){
		logger.debug("???????????????????????????");
		
		return new ModelAndView("admin/insales/item/item_batchUpdate")
					.addObject("item",item);
	}
	
	/**
	 * ??????Item
	 * @param request
	 * @param response
	 * @param item
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Item item){
		logger.debug("?????? ??????????????????");
		try{
			item.setDescr(getItemDescr(item));
			item.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = itemService.saveForProc(item);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ??????Item
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Item item){
		logger.debug("??????Item??????");
		try{
			item.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Item it = itemService.get(Item.class, item.getCode());
			if (it!=null){
				item.setOldCost(it.getAvgCost());
			}
			if (StringUtils.isBlank(item.getExpired())) {
				item.setExpired("F");
			} 
			Result result = itemService.saveForProc(item);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	/**
	 * ????????????????????????
	 * @return
	 * */
	@RequestMapping("/doUpdatePrePrice")
	public void doUpdatePrePrice(HttpServletRequest request,HttpServletResponse response,
			Item item){
		logger.debug("????????????");
		try {
			UserContext uc = getUserContext(request);
			item.setLastUpdatedBy(uc.getCzybh());
			Result result =itemService.doUpdatePrePrice(item) ;
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	/**
	 * ??????????????????????????????????????????
	 * @return
	 * */  
	@RequestMapping("/doUpdatePreValue")
	public void doUpdatePreValue(HttpServletRequest request,
			HttpServletResponse response,ItemPlan itemPlan){
		logger.debug("??????????????????");
		try {
			ItemPlan it = new ItemPlan();
			it=itemPlanService.get(ItemPlan.class,itemPlan.getPk());
			it.setQty(itemPlan.getQty());
			it.setProcessCost(itemPlan.getProcessCost());
			itemPlanService.update(it);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @return
	 * */             
	@RequestMapping("/doUpdateInvInfo")
	public void doUpdateInvinfo(HttpServletRequest request,HttpServletResponse response,Item item){
		logger.debug("??????????????????");
		Item it = null;
		it=itemService.get(Item.class, item.getCode());
		try{		
			it.setPerWeight(item.getPerWeight());
			it.setPerNum(item.getPerNum());
			it.setMinQty(item.getMinQty());
			it.setPackageNum(item.getPackageNum());
			it.setVolume(item.getVolume());
			it.setSize(item.getSize());
			it.setActionLog("EDIT");
			it.setLastUpdate(new Date());
			it.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemService.update(it);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	/**
	 * ????????????????????????
	 * @return
	 * 
	 * */
	@RequestMapping("/doUpdatePerfPer")
	public void doUpdatePerfPer(HttpServletRequest request,HttpServletResponse response,Item item){
		logger.debug("????????????????????????????????????");
		Item it = null;
		it=itemService.get(Item.class, item.getCode());
		try{
			it.setLastUpdate(new Date());
			it.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			it.setPerfPer(item.getPerfPer());
			itemService.update(it);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	/**
	 * ??????Item
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????Item??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Item??????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Item item = itemService.get(Item.class, deleteId);
				if(item == null)
					continue;
				item.setExpired("T");
				itemService.update(item);
			}
		}
		logger.debug("??????Item IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}

	/**
	 *Item??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Item item){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		itemService.findPageBySql_itemMessage(page, item);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 *Item??????????????????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel_prePrice")
	public void doExcelPrePrice(HttpServletRequest request, 
			HttpServletResponse response, Item item){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemService.findPageBySql_updatePrePrice(page, item);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/goItemSelect")
	public ModelAndView goItemSelect(HttpServletRequest request, 
			HttpServletResponse response, Item item){
		return new ModelAndView("admin/insales/item/item_select")
					.addObject("MENU_TYPE_URL", DictConstant.DICT_MENU_TYPE_URL)
					.addObject("item",item);
	}
	/**
	 * ??????????????????1???sqlcode??????????????????
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 */
	@RequestMapping("/goItemSelectJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemSelectJqGrid(HttpServletRequest request, 
			HttpServletResponse response, Item item){
		if("00".equals(item.getSqlCode())){
			return null;
		}
		if (StringUtils.isNotBlank(item.getCustType())) {
			CustType custType=itemService.get(CustType.class,item.getCustType());
			if(custType!=null){
				item.setCanUseComItem(custType.getCanUseComItem());
			}
		} 
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemService.getItemBySqlCode(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * excel????????????
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
		ExcelImportUtils<ItemBean> eUtils=new ExcelImportUtils<ItemBean>();
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
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList.add("?????????");
		titleList.add("??????");
		titleList.add("????????????1");
		titleList.add("????????????2");
		titleList.add("????????????3");
		titleList.add("????????????");
		titleList.add("??????");
		titleList.add("??????");
		titleList.add("??????");
		titleList.add("??????");
		titleList.add("??????");
		titleList.add("???????????????");
		titleList.add("??????");
		titleList.add("?????????");
		titleList.add("??????????????????");
		titleList.add("??????");
		titleList.add("?????????????????????");
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("???????????????");
		titleList.add("????????????");
		titleList.add("???????????????");
		titleList.add("???????????????");
		titleList.add("??????1");
		titleList.add("???????????????");
		titleList.add("?????????");
		titleList.add("????????????");
		try {		
			List<ItemBean> result=eUtils.importExcel(in,ItemBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(ItemBean itemBean:result){
				if(StringUtils.isNotBlank(itemBean.getError())){
					map.put("success", false);
					map.put("returnInfo", itemBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "??????");
				data.put("remcode", itemBean.getRemCode());
				data.put("barcode", itemBean.getBarCode());
				data.put("itemtype1", itemBean.getItemType1());
				if(StringUtils.isNotBlank(itemBean.getItemType1())){
					ItemType1 itemTpye1=itemService.get(ItemType1.class,itemBean.getItemType1());
					if (itemTpye1==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "?????????????????????1??????");
					}else{
						data.put("itemtype1descr", itemTpye1.getDescr());
						if("".equals(itemTpye1.getDescr())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "?????????????????????1??????");
						}else{
							if("LP".equals(itemTpye1.getCode())){
								data.put("isinv","0");
							}else{
								data.put("isinv","1");
							}
						}
					}	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????1????????????");
				}
				data.put("itemtype2", itemBean.getItemType2());
				if(StringUtils.isNotBlank(itemBean.getItemType2())){
					ItemType2 itemTpye2=itemService.get(ItemType2.class,itemBean.getItemType2());
					if(itemTpye2==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "?????????????????????2????????????");
					}else{
						data.put("itemtype2descr", itemTpye2.getDescr());
						if("".equals(itemTpye2.getDescr())||!"F".equals(itemTpye2.getExpired())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "?????????????????????2??????");
						}
					}
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????2????????????");
				}
				data.put("itemtype3", itemBean.getItemType3());
				if(StringUtils.isNotBlank(itemBean.getItemType3())){
					ItemType3 itemTpye3=itemService.get(ItemType3.class,itemBean.getItemType3());
					if (itemTpye3==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "?????????????????????3??????");
					}else{
						data.put("itemtype3descr", itemTpye3.getDescr());
						if("".equals(itemTpye3.getDescr())||!"F".equals(itemTpye3.getExpired())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "?????????????????????3??????");
						}
					}
				}
				Item item=new Item();
				item.setDescr(itemBean.getDescr());
				item.setSupplCode(itemBean.getSupplCode());
				item.setItemType2(itemBean.getItemType2());
				item.setItemType3(itemBean.getItemType3());
				item.setSqlCode(itemBean.getSqlCode());
				item.setModel(itemBean.getModel());
				item.setSizeDesc(itemBean.getSizeDesc());
				item.setM_umState("I");
				if(StringUtils.isBlank(itemBean.getDescr())){
					item.setDescr(getItemDescr(item));
					if (StringUtils.isBlank(item.getDescr())){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "?????????????????????????????????");
					}	
				}
				if(itemService.isExistDescr(item)){
					data.put("descr", item.getDescr());
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????");		
				}else{
					data.put("descr", item.getDescr());
				}
				data.put("sqlcode", itemBean.getSqlCode());
				if(StringUtils.isNotBlank(itemBean.getSqlCode())){
					Brand brand =itemService.get(Brand.class,itemBean.getSqlCode());
					if(brand==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "?????????????????????");
					}else{
						data.put("sqldescr", brand.getDescr());
						if("".equals(brand.getDescr())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "?????????????????????");
						}
					}
				}
				data.put("uom", itemBean.getUom());
				if(StringUtils.isNotBlank(itemBean.getUom())){
					Uom uom =itemService.get(Uom.class,itemBean.getUom());
					data.put("uomdescr", uom.getDescr());
					if(uom==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "?????????????????????");
					}else{
						if("".equals(uom.getDescr())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "?????????????????????");
						}
					}
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "???????????????????????????");
				}
				data.put("sizedesc", itemBean.getSizeDesc());//??????????????????
				data.put("model", itemBean.getModel());
				data.put("color", itemBean.getColor());
				data.put("supplcode", itemBean.getSupplCode());
				if(StringUtils.isNotBlank(itemBean.getSupplCode())){
					Supplier supplier =itemService.get(Supplier.class,itemBean.getSupplCode());
					if(supplier==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "????????????????????????");
					}else{
						data.put("suppldescr",supplier.getDescr());
						if("".equals(supplier.getDescr())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "????????????????????????");
						}
					}
						
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "??????????????????????????????");
				}
				data.put("price", itemBean.getPrice());
				data.put("marketprice", itemBean.getMarketPrice());
				data.put("cost", itemBean.getCost());
				data.put("projectcost", itemBean.getProjectCost());
				data.put("commiperc", itemBean.getCommiPerc());
				data.put("remark", itemBean.getRemark());
				data.put("sendtype", itemBean.getSendType());
				if("1".equals(itemBean.getSendType())){;
					data.put("sendtypedescr", "???????????????");
				}else if("2".equals(itemBean.getSendType())) {
					data.put("sendtypedescr", "????????????");
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "???????????????????????????");
				}
				data.put("remark", itemBean.getRemark());
				data.put("whcode", itemBean.getWhCode());
				if(StringUtils.isNotBlank(itemBean.getWhCode())){
					WareHouse wareHouse=itemService.get(WareHouse.class,itemBean.getWhCode());
					if(wareHouse==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "???????????????????????????");
					}else{
						if("".equals(wareHouse.getDesc1())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "???????????????????????????");
						}else{
							data.put("whdescr",wareHouse.getDesc1());
						}
					}
				}
				data.put("minqty", itemBean.getMinQty());
				data.put("hassample", itemBean.getHassample());
				if("1".equals(itemBean.getHassample())){;
					data.put("hassampledescr", "???");
				}else if("0".equals(itemBean.getHassample())){
					data.put("hassampledescr", "???");
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "???????????????????????????");
				}
				if(StringUtils.isNotBlank(itemBean.getCommiType())){
					 data.put("commitype", itemBean.getCommiType());
					 Xtdm xtdmCommiType=xtdmService.getByIdAndCbm("COMMITYPE",itemBean.getCommiType());
					 if(xtdmCommiType==null){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "???????????????????????????");
					 }else{
						 data.put("commitypedescr", xtdmCommiType.getIdnote()); 
					 }	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????????????????");
				}
				if(StringUtils.isNotBlank(itemBean.getIsFixPrice())){
					 data.put("isfixprice", itemBean.getIsFixPrice());
					 Xtdm xtdmIsFixPrice=xtdmService.getByIdAndCbm("ISFIXPRICE",itemBean.getIsFixPrice());
					 if(xtdmIsFixPrice==null){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "??????????????????????????????");
					 }else{
						 data.put("isfixpricedescr", xtdmIsFixPrice.getNote()); 
					 }	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "????????????????????????????????????");
				}  
				if(StringUtils.isNotBlank(itemBean.getInstallFeeType())){
					 data.put("installfeetype", itemBean.getInstallFeeType());
					 Xtdm xtdmInstallfeetype=xtdmService.getByIdAndCbm("INSTALLFEETYPE",itemBean.getInstallFeeType());
					 if(xtdmInstallfeetype==null){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "??????????????????????????????");
					 }else{
						 data.put("installfeetypedescr", xtdmInstallfeetype.getNote());
					 }	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "????????????????????????????????????");
				}
				data.put("installfee", itemBean.getInstallFee());
				if(StringUtils.isNotBlank(itemBean.getBuyer1())){
					Employee employee =itemService.get(Employee.class,itemBean.getBuyer1());
					if(employee==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "???????????????1??????");
					}else{
						data.put("buyer1", itemBean.getBuyer1());
						data.put("buyer1descr",employee.getNameChi());
					}
				}
				if(StringUtils.isNotBlank(itemBean.getWhFeeType())){
					 data.put("whfeetype", itemBean.getIsFixPrice());
					 Xtdm xtdmWhfeetype=xtdmService.getByIdAndCbm("WHCALTYPE",itemBean.getWhFeeType());
					 if(xtdmWhfeetype==null){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "??????????????????????????????");
					 }else{
						 data.put("whfeetypedescr", xtdmWhfeetype.getNote()); 
					 }	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "????????????????????????????????????");
				} 
				data.put("whfee", itemBean.getWhFee());
				if(StringUtils.isNotBlank(itemBean.getCustType())){
					CustType custtype =itemService.get(CustType.class,itemBean.getCustType());
					if(custtype==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "???????????????????????????");
					}else{
						data.put("custtype", itemBean.getCustType());
						data.put("custtypedescr", custtype.getDesc1());
					}	
				}
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "??????????????????");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo","???????????????????????????????????????,??????????????????????????????????????????!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	
	/**
	 * ??????????????????1???sqlcode??????????????????  ???????????????
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 */
	@RequestMapping("/goItemSelectJqGrid2")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemSelectJqGrid2(HttpServletRequest request, 
			HttpServletResponse response, Item item){
		if("00".equals(item.getSqlCode())){
			return null;
		}
		if (StringUtils.isNotBlank(item.getCustType())) {
			CustType custType=itemService.get(CustType.class,item.getCustType());
			if(custType!=null){
				item.setCanUseComItem(custType.getCanUseComItem());
			}
		} 
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemService.getItemBySqlCode2(page, item);
		System.out.println(page.getResult());
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????
	 *
	 **/
	@RequestMapping("/lablePrint")
	public void doPrint(HttpServletRequest request,HttpServletResponse response){
        try {        	
        	String jsonString = request.getParameter("jsonString");
    		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(jsonString); 
    		String codes = (String) jo.get("codes");
    		String type = (String) jo.get("type");
        	StringBuffer slabelContent=new StringBuffer();
        	String[] arr = codes.split(",");
        	slabelContent.append("???????????? ?????? ?????? ?????? ?????? ????????? ??????  ??????  ??????  ??????");
        	slabelContent.append("\r\n");
        	Map<String, Object> map=null;
        	for (String str : arr){
        		map=itemService.getLabelContent(str);
        		if(map!=null){
	        		for (String s : map.keySet()) {
	        			if("Price".equals(s)||"MarketPrice".equals(s)){
	        				slabelContent.append(subDotEndZero(map.get(s).toString())+"  ");
	        			}else{
	        				slabelContent.append(map.get(s).toString()+"  ");
	        			}    
	                }
	        		slabelContent.append("\r\n");	
        		}	
        	}
        	
        	String fileName=type+"print";
        	response.setContentType("application/x-download"); 	
        	response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".txt");  
        	BufferedOutputStream buff = null;   
        	ServletOutputStream outSTr = null;   
        	try {   
				outSTr = response.getOutputStream(); 
				buff = new BufferedOutputStream(outSTr); 
				buff.write(slabelContent.toString().getBytes("UTF-8"));   
				buff.flush();   
				buff.close();   
        	} catch (Exception e) {   
				  e.printStackTrace();   
        	} finally {   
				 try {   
				    buff.close();   
				    outSTr.close();   
				 } catch (Exception e) {   
				    e.printStackTrace();   
				 }   
        	}   

        } catch (Exception e) {  
        	ServletUtils.outPrintFail(request, response, "????????????");  
        }    
	}

	/**
	 *????????????????????????
	 *
	 */
	@RequestMapping("/getPerfPer")
	@ResponseBody
	public Item getPerfPer(HttpServletRequest request,HttpServletResponse response,Item item){
		logger.debug("???????????????????????????");  
		//?????????????????? 
		double dPerfPer =itemService.getPerfPer(item.getItemType2(),item.getCost(),item.getPrice());
		item.setPerfPer(dPerfPer); 
		return item;
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadPictrue")
	public void uploadPictrue(HttpServletRequest request, HttpServletResponse response) {
		// UploadPhotoResp respon = new UploadPhotoResp();
		try {
	
			/*String fileRealPath = "";//???????????????????????? 
			String firstFileName = ""; 
			String PhotoPath="";
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// ??????????????????????????????????????? 500k
			//upload.setSizeMax(500 * 1024);
			// ????????????????????????
			List fileList = upload.parseRequest(request);
			List<String> fileRealPathList = new ArrayList<String>();
			List<String> PhotoPathList = new ArrayList<String>();
			List<String> fileNameList = new ArrayList<String>();
			String itemCode ="",itemType1="",itemType2="",itemType3="",theNumsString="";
			itemCode=request.getParameter("code");
			Item item=itemService.get(Item.class,itemCode);
			itemType1=item.getItemType1();
			itemType2=item.getItemType2();
			itemType3=item.getItemType3();
			int itemPicNum=itemPicService.getCountNum(itemCode);
			int i=itemPicNum+1;
			// ??????????????????????????????
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				// ??????????????? ????????????
				if (obit.isFormField()) { //?????????,??????????????????
					String fieldName = obit.getFieldName();
					String fieldValue = obit.getString();
					if ("itemCode".equals(fieldName)){
						itemCode = fieldValue.trim();
					
					}
					if ("itemType1".equals(fieldName)){
						itemType1 = fieldValue.trim();
					}
					if ("itemType2".equals(fieldName)){
						itemType2 = fieldValue.trim();
					}
					if ("itemType3".equals(fieldName)){
						itemType3 = fieldValue.trim();
					}
				}
				// ??????????????????
				if (obit instanceof DiskFileItem) {
					DiskFileItem diskFileItem = (DiskFileItem) obit;
					// ??????item????????????????????????
					// ????????????????????????
					String fileName = diskFileItem.getName();
					if (fileName != null) {
						if(fileName.indexOf("?")!=-1){
							fileName=fileName.substring(0,fileName.indexOf("?"));
						}
						firstFileName = fileName.substring(
								fileName.lastIndexOf("\\") + 1);
						String formatName = firstFileName
								.substring(firstFileName.lastIndexOf("."));//?????????????????????
						String fileNameNew = itemCode+"_"+ Integer.toString(i)+formatName;
						savePhotoPath=fileNameNew;
						String filePath = getItemPhotoUploadPath("",itemType1,itemType2,itemType3);
						PhotoPath=getItemPhotoDownloadPath(request,"",itemType1,itemType2,itemType3)+fileNameNew;
						savePath=PhotoPath;
						FileUploadServerMgr.makeDir(filePath);
						fileRealPath = filePath + fileNameNew;// ????????????????????????
						BufferedInputStream in = new BufferedInputStream(
								diskFileItem.getInputStream());// ?????????????????????
						BufferedOutputStream outStream = new BufferedOutputStream(
								new FileOutputStream(new File(fileRealPath)));// ?????????????????????
						Streams.copy(in, outStream, true);// ????????????????????????????????????????????????
						in.close();
						outStream.close();
						fileRealPathList.add(fileRealPath);
						PhotoPathList.add(PhotoPath);
						fileNameList.add(fileNameNew);

					}
				}
				
			}
			//????????????
			if (StringUtils.isNotBlank(itemCode)){
				if (fileNameList!=null && fileNameList.size()>0){
					  String s ="ItemImage"+savePhotoPath;
						ItemPic itemPic = new ItemPic();
						itemPic.setItemCode(itemCode);
						itemPic.setDispSeq(i);
						if (i==1){
							itemPic.setPicType("1");
						}else{
							itemPic.setPicType("0");
						}
					    itemPic.setLastUpdate(new Date());
						itemPic.setLastUpdatedBy(getUserContext(request).getCzybh());
						itemPic.setActionLog("Add");
						itemPic.setExpired("F");
						itemPic.setPicFile("ItemImage"+"\\"+savePhotoPath.trim()); 
						itemPicService.save(itemPic);
				}
			}
			respon.setPhotoPathList(PhotoPathList);
			respon.setPhotoNameList(PhotoPathList);*/
	
            MultipartFormData multipartFormData = new MultipartFormData(request); 
			String itemCode = multipartFormData.getParameter("code");
			Item item=itemService.get(Item.class,itemCode);
			String itemType1=item.getItemType1();
			String itemType2=item.getItemType2();
			String itemType3=item.getItemType3();

			int itemPicNum=itemPicService.getCountNum(itemCode);
			int dispSeq=itemPicNum+1;
			
			FileItem fileItem = multipartFormData.getFileItem();
			
			ItemPicUploadRule rule  = new ItemPicUploadRule(fileItem.getName(), itemType1, itemType2,
					 itemType3, itemCode, dispSeq);
			
			Result result = FileUploadUtils.upload(fileItem.getInputStream(), rule.getFileName(),rule.getFilePath());
			if (!Result.SUCCESS_CODE.equals(result.getCode())) {
				logger.error("?????????????????????itemCode:{}", itemCode);
				ServletUtils.outPrintFail(request, response, "??????????????????");
				return;
			}

			//????????????
			if (StringUtils.isNotBlank(itemCode)){
				doItemPic(itemCode, dispSeq, rule.getFullName(), getUserContext(request).getCzybh());
				
			}
			ServletUtils.outPrintSuccess(request, response, rule.getFullName());
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}

	/**
	 * ??????????????????????????????
	 * @return
	 */
//	protected static String getItemPhotoUploadPath(String fileName, String itemType1,  String itemType2,  String itemType3){
//		//String ftpFilePath = "d:/homePhoto/ItemImage/";
//		String ftpFilePath=SystemConfig.getProperty("itemPic", "", "photo");
//		if (StringUtils.isNotBlank(itemType1)){
//			 ftpFilePath = ftpFilePath+itemType1.trim()+'/'; 
//		}
//		if (StringUtils.isNotBlank(itemType2)){
//			 ftpFilePath = ftpFilePath + itemType2.trim() +'/';
//		}
//		if (StringUtils.isNotBlank(itemType3)){
//			 ftpFilePath = ftpFilePath + itemType3.trim() +'/';
//		}
//		if (StringUtils.isNotBlank(fileName)){
//			return ftpFilePath=ftpFilePath + fileName.trim();
//		}
//		return ftpFilePath;
//	  
//	}


	/**
	 * ??????????????????????????????
	 * @return 
	 */
//	protected static String getItemPhotoDownloadPath(HttpServletRequest request, String fileName,String itemType1,  String itemType2,  String itemType3){
//		String path = getItemPhotoUploadPath(fileName,itemType1, itemType2,itemType3);
//		return  path;
//	}

	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param code
	 * @param photoName
	 * @return
	 */
	@RequestMapping("/ajaxGetPicture")
	@ResponseBody
	public JSONObject getPicture(HttpServletRequest request, HttpServletResponse response, String code, String photoName){
		
//		String fileName=photoName.substring(photoName.lastIndexOf("\\")+1);
//		Item item=itemService.get(Item.class,code);
//		String photoPath= getItemPhotoDownloadPath(request, fileName,item.getItemType1(),item.getItemType2(),item.getItemType3());
//		photoPath=PathUtil.getWebRootAddress(request)+photoPath.substring(photoPath.indexOf("/")+1);
//		
//		String photoPath=SystemConfig.getProperty("itemPic", "", "photo");
//	    String photoPath = SystemConfig.getProperty("downloadUrl", "", "fileUpload");
		
	    String photoPath = FileUploadUtils.getFileUrl(photoName);
		if(" ".equals(photoPath)){
			return null;
		}else
			return this.out(photoPath, true);
	}
	
	/**
	 * ????????????
	 * 
	 * */
	@RequestMapping("/doDeletePicture")
	@ResponseBody
	public boolean doDeleteDoc(HttpServletRequest request, HttpServletResponse response,
			String code,String photoName,int pk){
		boolean i;
		try {
//			String fileName=photoName.substring(photoName.lastIndexOf("\\")+1);
//			 Item item=itemService.get(Item.class,code);		
//			 String photoPath= getItemPhotoDownloadPath(request, fileName,item.getItemType1(),item.getItemType2(),item.getItemType3());
//			 File file = new File(photoPath);
//			 file.delete();	
//			
//			String photoPath = FileUploadUtils.getFileUrl(request,photoName);
//			File file = new File(photoPath);
//			if (file.exists()){
//				file.delete();	
//			}
			FileUploadUtils.deleteFile(photoName);
			ItemPic itemPic = itemPicService.get(ItemPic.class,pk);
			if (itemPic != null ) {
				itemPicService.delete(itemPic);
			}
			i=true; 
			
		} catch (Exception e) {
			i=false;
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
		return i;
	}
	
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSaveBatch")
	@ResponseBody
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,Item item){
		logger.debug("????????????????????????");
		try {
			
			item.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.itemService.doSaveBatch(item);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doUpdateBatch")
	public void doChengeCheckMan(HttpServletRequest request, HttpServletResponse response,
			Item item){
		logger.debug("??????????????????");
		try{
			item.setLastUpdate(new Date());
			item.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemService.doUpdateBatch(item);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param custDoc
	 * 
	 * */
	@RequestMapping("/goItemPicAdd")
	public ModelAndView goAdd(HttpServletRequest request ,
			HttpServletResponse response,Item item) throws Exception{
		
		return new ModelAndView("admin/insales/item/item_picAdd").addObject("item",item);
	}
	@RequestMapping("/doUpdateItemPic")
	@ResponseBody
	public boolean doDeleteDoc(HttpServletRequest request, HttpServletResponse response,
			String code,int pk,int mainPicPk){
		boolean i;
		try {
				ItemPic itemPic = itemPicService.get(ItemPic.class,pk);
				itemPic.setPicType("1");
				itemPic.setLastUpdate(new Date());
				itemPic.setLastUpdatedBy(getUserContext(request).getCzybh());
				itemPic.setActionLog("Edit");
				itemPicService.update(itemPic);
				if(mainPicPk!=0){
					ItemPic MainItemPic = itemPicService.get(ItemPic.class,mainPicPk);
					MainItemPic.setPicType("0");
					MainItemPic.setLastUpdate(new Date());
					MainItemPic.setLastUpdatedBy(getUserContext(request).getCzybh());
					MainItemPic.setActionLog("Edit");
					itemPicService.update(MainItemPic);
				}
			i=true;
		} catch (Exception e) {
			i=false;
			ServletUtils.outPrintFail(request, response, "?????????????????????");
		}
		return i;
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param Item
	 * @return
	 */
	@RequestMapping("/checkIsExistItem")
	@ResponseBody
	public JSONObject checkIsExistItem(HttpServletRequest request, HttpServletResponse response,Item item) {
		if ("A".equals(item.getM_umState())||"C".equals(item.getM_umState())){
			item.setDescr(getItemDescr(item));	
		}
		if(itemService.isExistDescr(item)){
			return this.out("????????????????????????", true);
			
		}else {
			return this.out("???????????????????????????", false);
		}
	}
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAlgorithm")
	@ResponseBody
	public List<Map<String, Object>> getAlgorithm(HttpServletRequest request,HttpServletResponse response,Item item){
		List<Map<String, Object>>list=itemService.getAlgorithmByCode(item);
		return list;
	}
	/**
	 * ???????????????????????????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	public String getItemDescr(Item item){
			String sItemDescr="";
			resetItem(item);
			if((item.getItemType2()).equals(xtcsService.getQzById("Furniture"))||!"RZ".equals(item.getItemType1())){
				if(StringUtils.isNotBlank(item.getSqlDescr())){
					sItemDescr=item.getSqlDescr().trim();
				}		
			}
			if(StringUtils.isNotBlank(item.getItemType3())){
				sItemDescr=sItemDescr+item.getItemType3Descr().trim();	
			}else{
				sItemDescr=sItemDescr+item.getItemType2Descr().trim();	  
			}
			if(StringUtils.isNotBlank(item.getModel())){
				sItemDescr=sItemDescr+item.getModel().trim();	
			}
			if(StringUtils.isNotBlank(item.getSizeDesc())){
				sItemDescr=sItemDescr+" "+item.getSizeDesc().trim();	
			}
			return sItemDescr.trim();
	}
	/**
	 * ?????????????????????????????????0
	 * @param id
	 * @return
	 */
	public static String subDotEndZero(String s){
	    if(s.indexOf(".") > 0){
	      s = s.replaceAll("0+?$", "");//???????????????0
	      s = s.replaceAll("[.]$", "");//??????????????????.?????????
	    }
	    return s;
	}
	
	public void doItemPic(String itemCode, Integer dispSeq, String fileName, String lastUpdatedBy){
		if(StringUtils.isNotBlank(fileName)){
			ItemPic itemPic = new ItemPic();
			itemPic.setItemCode(itemCode);
			itemPic.setDispSeq(dispSeq);
			if (dispSeq==1){
				itemPic.setPicType("1");
			}else{
				itemPic.setPicType("0");
			}
		    itemPic.setLastUpdate(new Date());
			itemPic.setLastUpdatedBy(lastUpdatedBy);
			itemPic.setActionLog("Add");
			itemPic.setExpired("F");
			itemPic.setPicFile(fileName); 
			itemPicService.save(itemPic);	
		}
	}
	
	@RequestMapping("/getAuthItemType2ByItemType1")
	@ResponseBody
	public List<Map<String, Object>> getAuthItemType2ByItemType1(HttpServletRequest request, HttpServletResponse response, 
				Item item){
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = itemService.getAuthItemType2ByItemType1(item);
		
		return list; 
	}
	
	@RequestMapping("/getItemType12ByItemCode")
	@ResponseBody
	public String getItemType12ByCode(HttpServletRequest request,HttpServletResponse response,Item item){
		String itemType12 = "";
		ItemType2 itemType2 = new ItemType2();
		if(StringUtils.isNotBlank(item.getCode())){
			item = itemService.get(Item.class, item.getCode());
			if(item != null && StringUtils.isNotBlank(item.getItemType2())){
				itemType2 = itemService.get(ItemType2.class, item.getItemType2());
				if(itemType2 != null && StringUtils.isNotBlank(itemType2.getItemType12())){
					itemType12 = itemType2.getItemType12();
				}
			}
		}
		return itemType12;
	}
}
