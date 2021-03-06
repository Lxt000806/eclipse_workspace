package com.house.home.web.controller.project;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Menu;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.project.ItemChgDetailBean;
import com.house.home.entity.basic.Algorithm;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.CustTypeItem;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.ItemType3;
import com.house.home.entity.basic.PrePlanTemp;
import com.house.home.entity.basic.SupplPromItem;
import com.house.home.entity.basic.Uom;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.FixArea;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.entity.finance.DiscAmtTran;
import com.house.home.entity.finance.DiscToken;
import com.house.home.entity.insales.ItemReq;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.project.ItemChg;
import com.house.home.entity.project.ItemChgDetail;
import com.house.home.service.basic.CustTypeItemService;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.basic.ItemType2Service;
import com.house.home.service.basic.ItemType3Service;
import com.house.home.service.basic.PrePlanTempService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.ItemPlanService;
import com.house.home.service.finance.DiscTokenService;
import com.house.home.service.insales.ItemAppDetailService;
import com.house.home.service.insales.ItemReqService;
import com.house.home.service.project.ItemChgDetailService;
import com.house.home.service.project.ItemChgService;

@Controller
@RequestMapping("/admin/itemChg")
public class ItemChgController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemChgController.class);

	@Autowired
	private ItemChgService itemChgService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private ItemReqService itemReqService;
	@Autowired
	private ItemAppDetailService itemAppDetailService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private ItemChgDetailService itemChgDetailService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ItemPlanService itemPlanService;
	@Autowired
	private ItemType2Service itemType2Service;
	@Autowired
	private CustTypeItemService custTypeItemService;
	@Autowired
	private ItemType3Service itemType3Service;
	@Autowired
	private PrePlanTempService prePlanTempService;
	@Autowired
	private DiscTokenService discTokenService;
	@Autowired
	private XtdmService xtdmService;
	
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
			HttpServletResponse response, ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		if(itemChg.getStatus()==null){
			itemChg.setStatus("1");
		}
		itemChgService.findPageBySql(page, itemChg,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemChgService.findDetailPageBySql(page, itemChg,uc);
		return new WebPage<Map<String,Object>>(page);
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
			HttpServletResponse response, ItemReq itemReq) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReqService.findItemReqList(page, itemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ?????????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlanAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemPlanAddJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlan itemPlan) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanService.findItemPlanList(page, itemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAchievementsOfReferenceGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goAchievementsOfReferenceGrid(HttpServletRequest request,
			HttpServletResponse response, ItemChg itemChg ) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChgService.findReferencePageBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPlzjyjJqgrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPlzjyjJqgryd(HttpServletRequest request,
			HttpServletResponse response, ItemChg itemChg ) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChgService.findPlzjyjPageBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????????????????
	 * @author	created by zb
	 * @date	2020-3-18--??????5:59:43
	 * @param request
	 * @param response
	 * @param itemChg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getItemStatusJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemStatusJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChgService.findItemStatusPageBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ???????????????????????????
	 * @author	created by zb
	 * @date	2020-4-20--??????4:38:02
	 * @param request
	 * @param response
	 * @param baseItemChg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getItemChgStakeholderList")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemChgStakeholderList(HttpServletRequest request,
			HttpServletResponse response, ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChgService.findItemChgStakeholderPageBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	
    @RequestMapping("/viewSetDeductions")
    public ModelAndView viewSetDeductions(HttpServletRequest request,
            HttpServletResponse response, ItemChg itemChg) {

        return new ModelAndView("admin/project/itemChg/itemChg_viewSetDeductions")
                .addObject("itemChg", itemChg);
    }
	
	/**
	 * ???????????????????????????????????????
	 * 
	 * @param request
	 * @param response
	 * @param itemChg
	 * @return
	 */
	@RequestMapping("/goSetDeductionsGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goSetDeductionsGrid(HttpServletRequest request,
	        HttpServletResponse response, ItemChg itemChg) {

	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    page.setPageSize(-1);
        itemChgService.findSetDeductions(page, itemChg);

        return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ?????????????????????????????????????????????????????????2??????????????????
	 * ??????????????????????????????????????????2?????????ItemChg?????????????????????????????????
	 * 
	 * @param request
	 * @param response
	 * @param itemChg
	 * @return
	 */
	@RequestMapping("/existsSetDeduction")
	@ResponseBody
	public boolean existsSetDeduction(HttpServletRequest request,
	        HttpServletResponse response, ItemChg itemChg) {
	    
	    if (StringUtils.isBlank(itemChg.getCustCode())
	            || StringUtils.isBlank(itemChg.getItemType2())) {
            
	        throw new IllegalArgumentException("?????????????????????????????????2");
        }
	    
	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    page.setPageSize(-1);
	    itemChgService.findSetDeductions(page, itemChg);

	    return page.getResult().size() > 0;
	}
	
	/**
	 * ItemChg??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/project/itemChg/itemChg_list");
	}
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/goItemBatchDetailList")
    public ModelAndView goItemBatchDetailList(HttpServletRequest request, HttpServletResponse response,
            ItemChg itemChg) {

        UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);

        return new ModelAndView("admin/project/itemChg/tab_clpc")
                .addObject("czybh", uc.getCzybh())
                .addObject("itemChg", itemChg);
    }
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItem")
	public ModelAndView goItem(HttpServletRequest request,
			HttpServletResponse response,ItemChg itemChg) throws Exception {
		return new ModelAndView("admin/project/itemChg/tab_item").addObject("itemChg", itemChg);
	}
	
	/** 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemSetDetailList")
	public ModelAndView goItemSetDetailList(HttpServletRequest request,
			HttpServletResponse response, ItemChg itemChg) throws Exception {
		return new ModelAndView("admin/project/itemChg/tab_itemSet").addObject("itemChg", itemChg);
	}
	
	/**
	 * ?????????????????????
	 * 
	 * @param request
	 * @param response
	 * @param itemChg
	 * @return
	 */
	@RequestMapping("/goImportItemBatch")
	public ModelAndView goImportItemBatch(HttpServletRequest request, HttpServletResponse response,
	        ItemChg itemChg) {
	    
	    return new ModelAndView("admin/project/itemChg/itemChg_importItemBatch")
	            .addObject("itemChg", itemChg);
	}
	
	/**
	 * 
	 * ???????????????excel??????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemChg_importExcel")
	public ModelAndView goItemChg_importExcel(HttpServletRequest request,
			HttpServletResponse response,ItemChg itemChg) throws Exception {
		return new ModelAndView("admin/project/itemChg/itemChg_importExcel").addObject("itemChg", itemChg);
	}
	
	@RequestMapping("/goItemFromTemp")
	public ModelAndView goItemFromTemp(HttpServletRequest request, HttpServletResponse response, ItemChg ItemChg){
		logger.debug("???????????????????????????");
		return new ModelAndView("admin/project/itemChg/itemChg_itemFromTemp")
			.addObject("ItemChg", ItemChg);
	}
	/**
	 * 
	 * ??????excel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<ItemChgDetailBean> eUtils=new ExcelImportUtils<ItemChgDetailBean>();
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
			// ??????????????? ????????????
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
		titleList.add("????????????");
		if("JC".equals(itemType1)){
			titleList.add("????????????");
		}
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("??????");
		titleList.add("??????");
		titleList.add("????????????");
		titleList.add("????????????");
		
		try {
			Customer customer=customerService.get(Customer.class,custCode);
			List<ItemChgDetailBean> result=eUtils.importExcel(in, ItemChgDetailBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			Map reqpks=new HashMap();
			int i=0;
			for(ItemChgDetailBean itemChgDetailBean:result){
				i++;
				if(StringUtils.isNotBlank(itemChgDetailBean.getError())){
					map.put("success", false);
					map.put("returnInfo", itemChgDetailBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				Map<String,Object> data=new HashMap<String, Object>();
				//???????????????
				if(StringUtils.isNotBlank(itemChgDetailBean.getItemSetDescr())){
					data.put("itemsetdescr", itemChgDetailBean.getItemSetDescr());
				}else{
					data.put("itemsetdescr", "");
				}
				
				data.put("fixareadescr", itemChgDetailBean.getFixAreaPkDescr());
				data.put("itemType1", itemType1);
				data.put("custCode", custCode);
				data.put("isservice", isService);
				data.put("iscupboard",isCupboard);
				data.put("lastupdatedby", uc.getCzybh());
				if("JC".equals(itemType1)){
					data.put("intproddescr", itemChgDetailBean.getIntProdPkDescr());
					//data.put("iscupboard", 1);
				}else{
					data.put("intproddescr", "");
					//data.put("iscupboard", 0);
				}
				if("ZC".equals(itemType1)){
					if (StringUtils.isNotBlank(itemChgDetailBean.getAlgorithm())){
						Algorithm algorithm=itemChgService.get(Algorithm.class, itemChgDetailBean.getAlgorithm());
						if(algorithm==null){
							data.put("isinvalid","1");
							data.put("isinvaliddescr", "?????????????????????");
						}else{	
							data.put("algorithmdescr",algorithm.getDescr());
						}
						data.put("algorithm",itemChgDetailBean.getAlgorithm());	
					}
					if (itemChgDetailBean.getDoorPk()!=null){
						data.put("doorpk",itemChgDetailBean.getDoorPk());
					}
					if (StringUtils.isNotBlank(itemChgDetailBean.getPrePlanAreaDescr())){
						data.put("preplanareadescr",itemChgDetailBean.getPrePlanAreaDescr());	
					}
					if (StringUtils.isNotBlank(itemChgDetailBean.getCutType())){
						data.put("cuttype",itemChgDetailBean.getCutType());
						Xtdm xtdmCutType=xtdmService.getByIdAndCbm("CUTTYPE",itemChgDetailBean.getCutType());
						if(xtdmCutType==null){
								data.put("isinvalid","1");
								data.put("isinvaliddescr", "???????????????????????????");
						}else{	
							data.put("cuttypedescr",xtdmCutType.getNote());
						}
					}
					if (itemChgDetailBean.getAlgorithmPer()!=null){
						data.put("algorithmper",itemChgDetailBean.getAlgorithmPer());
					}else{
						data.put("algorithmper",1.0);
					}
					if (itemChgDetailBean.getAlgorithmDeduct()!=null){
						data.put("algorithmdeduct",itemChgDetailBean.getAlgorithmDeduct());
					}else{
						data.put("algorithmdeduct",0.0);
					}
				}
				Item item=new Item();
				item.setCode(itemChgDetailBean.getItemCode());
				item.setItemType1(itemType1);
				Item item2=null;
				ItemType2 itemType2=null;
				ItemType3 itemType3=null;
				data.put("qty", itemChgDetailBean.getQty());
				if("???".equals(itemChgDetailBean.getIsOutSetDescr())){
					data.put("isoutset", "0");
					data.put("isoutsetdescr", "???");
					if(itemChgDetailBean.getCustTypeItemPk()!=null&&itemChgDetailBean.getCustTypeItemPk()!=0){
						if(custTypeItemService.hasItem(itemChgDetailBean.getCustTypeItemPk(), customer.getCustType().trim(), itemType1)){
							data.put("custtypeitempk", itemChgDetailBean.getCustTypeItemPk());
							CustTypeItem custTypeItem=custTypeItemService.get(CustTypeItem.class, itemChgDetailBean.getCustTypeItemPk());
							//??????????????????????????????????????????????????????????????????????????????????????????
							item.setCode(custTypeItem.getItemCode());
							item2=itemService.getItemByCondition(item);
							if(item2!=null){
								item2.setPrice(custTypeItem.getPrice());
								item2.setRemark(custTypeItem.getRemark());
							}	 
						}
					}else if(StringUtils.isNotBlank(itemChgDetailBean.getItemCode())){
						Integer custTypeItemPk=custTypeItemService.getUniquePk(itemChgDetailBean.getItemCode(), customer.getCustType().trim(), itemType1,itemChgDetailBean.getRemarks());
						if(custTypeItemPk!=null){
							data.put("custtypeitempk", custTypeItemPk);
							CustTypeItem custTypeItem=custTypeItemService.get(CustTypeItem.class, custTypeItemPk);
							//??????????????????????????????????????????????????????????????????????????????????????????
							item.setCode(custTypeItem.getItemCode());
							item2=itemService.getItemByCondition(item);
							if(item2!=null){
								item2.setPrice(custTypeItem.getPrice());
								item2.setRemark(custTypeItem.getRemark());
							}
						}
					}
					
				}else {
					data.put("isoutset", "1");
					data.put("isoutsetdescr", "???");
					item2=itemService.getItemByCondition(item);
				}
				if(item2!=null){
					itemType2=itemType2Service.get(ItemType2.class, item2.getItemType2());
					itemType3=itemType3Service.get(ItemType3.class, item2.getItemType3());
					data.put("itemtype2", item2.getItemType2().trim());
					data.put("itemtype3", item2.getItemType3().trim());
					data.put("expired", item2.getExpired());
					data.put("itemcode", item2.getCode());
					data.put("itemdescr", item2.getDescr());
					Uom uom=itemChgService.get(Uom.class,item2.getUom());
					data.put("uom", item2.getUom());
					if(uom!=null)data.put("uomdescr", uom.getDescr());
					ItemReq itemReq = null;
					if(itemChgDetailBean.getReqPk()!=null&&itemChgDetailBean.getReqPk()!=0){
						data.put("reqpk", itemChgDetailBean.getReqPk());
						itemReq = itemReqService.get(ItemReq.class, itemChgDetailBean.getReqPk());
					}
					if(itemReq == null ){
						//?????????????????????????????????????????????????????????
						if("1".equals(item2.getIsFixPrice())){
							data.put("unitprice", item2.getPrice());
						}else{
							data.put("unitprice", itemChgDetailBean.getUnitPrice());
						}
					}else {
						data.put("unitprice", itemReq.getUnitPrice());
					}
					//if("0".equals(data.get("isoutset"))) data.put("unitprice", 0);
					if(StringUtils.isNotBlank(itemChgDetailBean.getRemarks())){
						data.put("remarks", itemChgDetailBean.getRemarks());
					}else{
						data.put("remarks", item2.getRemark());
					}
					if(itemChgDetailBean.getQty()!=0.0){
						Double beflineamount=Math.round(Double.parseDouble(data.get("unitprice").toString())*itemChgDetailBean.getQty()*100)/100.0;
						Double sign = 1d;
						if(beflineamount < 0) {
							sign = -1d;
						}
						data.put("beflineamount",beflineamount);
						data.put("tmplineamount",Math.round(Math.abs(beflineamount*itemChgDetailBean.getMarkup()/100)) * sign);
						data.put("lineamount",Math.round(Math.round(Math.abs(beflineamount*itemChgDetailBean.getMarkup()/100)) * sign + itemChgDetailBean.getProcessCost()));
						
					}else{
						data.put("beflineamount",0.0);
						data.put("tmplineamount",0.0);
						data.put("lineamount",Math.round(itemChgDetailBean.getProcessCost()));
					}
				}
			
				
				data.put("markup", itemChgDetailBean.getMarkup());
				data.put("processcost", itemChgDetailBean.getProcessCost());
				if(itemChgDetailBean.getReqPk()!=null&&itemChgDetailBean.getReqPk()!=0){
					data.put("reqpk", itemChgDetailBean.getReqPk());
					ItemReq itemReq=itemReqService.get(ItemReq.class, itemChgDetailBean.getReqPk());
					if(itemReq!=null) {data.put("preqty",itemReq.getQty());};
				}
				
				if(!StringUtils.isNotBlank(itemChgDetailBean.getFixAreaPkDescr())
					||!StringUtils.isNotBlank(itemChgDetailBean.getItemCode())	
					||("JC".equals(itemType1)&&!StringUtils.isNotBlank(itemChgDetailBean.getIntProdPkDescr()))
					){
					data.put("isinvalid", 1);
				}else{
					data.put("isinvalid", 0);
				}
				if(itemChgDetailBean.getSupplPromItemPk() != null && itemChgDetailBean.getSupplPromItemPk() > 0){
					SupplPromItem supplPromItem = itemChgDetailService.get(SupplPromItem.class, Integer.toString(itemChgDetailBean.getSupplPromItemPk()));
					if(supplPromItem == null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "??????,???????????????PK??????");
					}else{
						data.put("supplpromitempk", itemChgDetailBean.getSupplPromItemPk());	
					}	
			    }
				data.put("dispseq", i);
				if("1".equals(data.get("isinvalid"))){
					data.put("isinvaliddescr", "??????,??????????????????");
					map.put("hasInvalid", true);
				}else if(item2==null){
					data.put("isinvaliddescr", "?????????????????????");
					if("???".equals(itemChgDetailBean.getIsOutSetDescr()))
					data.put("isinvaliddescr", "?????????????????????????????????");
					map.put("hasInvalid", true);
				}else if("0".equals(data.get("isoutset"))&&"1".equals(customer.getCustType().trim())){
					data.put("isinvaliddescr", "?????????????????????????????????");
					map.put("hasInvalid", true);
				}else if(itemChgDetailBean.getMarkup()<=0||itemChgDetailBean.getMarkup()>100){
					data.put("isinvaliddescr", "????????????>0???<=100");
					map.put("hasInvalid", true);
				}else if(itemChgDetailBean.getReqPk()!=null&&itemChgDetailBean.getReqPk()!=0&&reqpks.containsKey(itemChgDetailBean.getReqPk())){
					data.put("isinvaliddescr", "?????????????????????PK");
					map.put("hasInvalid", true);
				}else{
					data.put("isinvaliddescr", "??????");
					itemChgDetailService.importDetail(data);
					if("1".equals(data.get("isinvalid").toString())){
						map.put("hasInvalid", true);
					}
				}
				
				if(itemChgDetailBean.getReqPk()!=null&&itemChgDetailBean.getReqPk()!=0){
					data.put("reqpk", itemChgDetailBean.getReqPk());
					ItemReq itemReq = itemReqService.get(ItemReq.class, itemChgDetailBean.getReqPk());
					if(itemReq!=null) {
						if(!itemChgDetailBean.getUnitPrice().equals(itemReq.getUnitPrice())){
							map.put("hasInvalid", true);
							data.put("isinvalid", 1);
							data.put("isinvaliddescr", "??????,??????????????????????????????");
						}else if(!itemReq.getItemCode().equals(itemChgDetailBean.getItemCode())){
							map.put("hasInvalid", true);
							data.put("isinvalid", 1);
							data.put("isinvaliddescr", "??????,????????????????????????????????????");
						}else if(!itemChgDetailBean.getMarkup().equals(itemReq.getMarkup())){
							map.put("hasInvalid", true);
							data.put("isinvalid", 1);
							data.put("isinvaliddescr", "??????,??????????????????????????????");
						}
					};
				}
				if(!itemChgService.existsItemCmp(itemChgDetailBean.getItemCode(),custCode)){
					map.put("hasInvalid", true);
					data.put("isinvalid", 1);
					data.put("isinvaliddescr", "??????,????????????????????????????????????");
				}
				
				if(itemType2!=null){
					data.put("itemtype2descr", itemType2.getDescr());
				}
				if(itemType3!=null){
					data.put("itemtype3descr", itemType3.getDescr());
				}
				if(itemChgDetailBean.getReqPk()!=null&&itemChgDetailBean.getReqPk()!=0){
					//????????????pk?????????????????????
					reqpks.put(itemChgDetailBean.getReqPk(),itemChgDetailBean.getReqPk());
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
			map.put("returnInfo", "???????????????????????????????????????,??????????????????????????????????????????!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	
	/**
	 * ItemChg??????code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemChg/itemChg_code");
	}
	/**
	 * ???????????????ItemChg??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String itemType1){
		logger.debug("?????????????????????");
		return new ModelAndView("admin/project/itemChg/itemChg_customerCode").addObject("itemType1", itemType1);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goQuickSave")
	public ModelAndView goQuickSave(HttpServletRequest request, HttpServletResponse response, 
			ItemChg itemChg){
		if(StringUtils.isNotBlank(itemChg.getCustCode())){
			Customer customer=customerService.get(Customer.class, itemChg.getCustCode());
			if(customer!=null){
				itemChg.setMainTempNo(customer.getMainTempNo());
			}
		}
		
		try {
            itemChg.setExcludedReqPks(URLEncoder.encode(itemChg.getExcludedReqPks(), StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		
		return new ModelAndView("admin/project/itemChg/itemChg_quickSave").addObject("itemChg", itemChg);
	}
	/**
	 * ???????????????ItemChg??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			ItemChg itemchg){
		logger.debug("???????????????ItemChg??????");
		ItemChg itemChg = null;
		String refCustCodeDescr="";
		if (StringUtils.isNotBlank(itemchg.getNo())){
			itemChg = itemChgService.get(ItemChg.class, itemchg.getNo().trim());
		}else{
			itemChg = new ItemChg();
		}
		UserContext uc=this.getUserContext(request);
		Customer customer=customerService.get(Customer.class, itemChg.getCustCode());
		itemChg.setConstructStatus(customer.getConstructStatus());
		itemChg.setAppCzyDescr(itemchg.getAppCzyDescr());
		itemChg.setDocumentNo(itemchg.getDocumentNo());
		itemChg.setAddress(itemchg.getAddress());
		CustType custType= custTypeService.get(CustType.class, itemchg.getCustType());
		itemChg.setManageFeeCupPer(custType.getManageFeeCupPer());
		itemChg.setManageFeeMainPer(custType.getManageFeeMainPer());
		itemChg.setManageFeeServPer(custType.getManageFeeServPer());
		itemChg.setChgManageFeePer(custType.getChgManageFeePer());
		itemChg.setCustType(itemchg.getCustType());
		itemChg.setIsOutSet(custType.getType());
		itemChg.setAppCzy(uc.getCzybh());
		itemChg.setContainMain(customer.getContainMain());
		itemChg.setContainMainServ(customer.getContainMainServ());
		itemChg.setContainInt(customer.getContainInt());
		itemChg.setContainSoft(customer.getContainSoft());
		itemChg.setSignQuoteType(custType.getSignQuoteType());
		itemChg.setFaultManDescr(itemchg.getFaultManDescr());
		itemChg.setPrjQualityFee(itemchg.getPrjQualityFee());
		itemChg.setProjectMan(itemchg.getProjectMan());
		itemChg.setProjectManDescr(itemchg.getProjectManDescr());
		if (StringUtils.isNotBlank(itemChg.getTempNo())){
			 PrePlanTemp prePlanTemp= prePlanTempService.get(PrePlanTemp.class, itemChg.getTempNo());
			 if(prePlanTemp!=null){
				 itemChg.setTempDescr(itemChg.getTempNo()+"|"+prePlanTemp.getDescr());
		 	}
		}
		if(StringUtils.isBlank(itemChg.getIsCupboard())){
			itemChg.setIsCupboard("0");
		}
		if(StringUtils.isNotBlank(itemchg.getConfirmCzyDescr())){
			itemChg.setConfirmCzyDescr("|"+itemchg.getConfirmCzyDescr());
		}
		if(StringUtils.isNotBlank(itemChg.getRefCustCode())){
			if(customerService.get(Customer.class,itemChg.getRefCustCode())!=null){
				Customer c = customerService.get(Customer.class,itemChg.getRefCustCode());
						refCustCodeDescr=c.getDescr();
				itemChg.setRefAddress(c.getAddress());
			}
		}
		itemChg.setStatusDescr(itemchg.getStatusDescr());
		itemChg.setLastUpdatedBy(uc.getCzybh());
		itemChg.setItemType1(itemChg.getItemType1().trim());
		boolean existsTemp = itemChgService.getExistsTemp(itemChg.getCustCode(), itemChg.getNo());
		String arfCustCodeString = ","+itemChgService.getArfCustCodeList()+",";
		boolean needRefCustCode=false;
		if(arfCustCodeString.split(itemChg.getCustCode()).length>=2){
			needRefCustCode = true;
		}	
		Map<String,Object> balanceMap = customerService.getShouldBanlance(itemChg.getCustCode());
		DiscToken discToken = new DiscToken();
		discToken.setChgNo(itemchg.getNo());
		itemChg.setDiscTokenNo(discTokenService.getDiscTokenNo(discToken));
	    if (StringUtils.isNotBlank(itemChg.getDiscTokenNo())){
			discToken  = discTokenService.get(DiscToken.class, itemChg.getDiscTokenNo());
			if(discToken!=null){
				itemChg.setDiscTokenAmount(discToken.getAmount());
		 	}
	    }
	    
        // ??????????????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????????????????????????????????????????
        if (customer.getInnerArea() != null
            && customer.getInnerArea().doubleValue() != 0) {
            
            itemChg.setInnerArea(customer.getInnerArea());
        } else {
            Double innerArea = customer.getArea() * custType.getInnerAreaPer();
            itemChg.setInnerArea(Math.round(innerArea * 100) / 100.0);
        }
	    
		return new ModelAndView("admin/project/itemChg/itemChg_update")
			.addObject("itemChg", itemChg)
			.addObject("refCustCodeDescr", refCustCodeDescr)
			.addObject("existsTemp", existsTemp)
			.addObject("needRefCustCode", needRefCustCode)
			.addObject("balanceMap",balanceMap);
		    
	}
	/**
	 * ???????????????ItemChg??????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			ItemChg itemchg){
		logger.debug("???????????????ItemChg??????");
		ItemChg itemChg = null;
		String refCustCodeDescr="";
		
		if (StringUtils.isNotBlank(itemchg.getNo())){
			itemChg = itemChgService.get(ItemChg.class, itemchg.getNo().trim());
		}else{
			itemChg = new ItemChg();
		}
		
		Customer customer = customerService.get(Customer.class, itemChg.getCustCode());
		
		itemChg.setAppCzyDescr(itemchg.getAppCzyDescr());
		itemChg.setDocumentNo(itemchg.getDocumentNo());
		itemChg.setAddress(itemchg.getAddress());
		CustType custType= custTypeService.get(CustType.class, itemchg.getCustType());
		itemChg.setManageFeeCupPer(custType.getManageFeeCupPer());
		itemChg.setManageFeeMainPer(custType.getManageFeeMainPer());
		itemChg.setManageFeeServPer(custType.getManageFeeServPer());
		itemChg.setChgManageFeePer(custType.getChgManageFeePer());
		itemChg.setCustType(itemchg.getCustType());
		itemChg.setIsOutSet(custType.getType());
		itemChg.setFaultManDescr(itemchg.getFaultManDescr());
		itemChg.setPrjQualityFee(itemchg.getPrjQualityFee());
		itemChg.setProjectMan(itemchg.getProjectMan());
		itemChg.setProjectManDescr(itemchg.getProjectManDescr());
		if(StringUtils.isBlank(itemChg.getIsCupboard())){
			itemChg.setIsCupboard("0");
		}
		if(StringUtils.isNotBlank(itemchg.getConfirmCzyDescr())){
			itemChg.setConfirmCzyDescr("|"+itemchg.getConfirmCzyDescr());
		}
		if(StringUtils.isNotBlank(itemChg.getRefCustCode())){
			if(customerService.get(Customer.class,itemChg.getRefCustCode())!=null){
				refCustCodeDescr=customerService.get(Customer.class,itemChg.getRefCustCode()).getDescr();
			}
		}
		itemChg.setStatusDescr(itemchg.getStatusDescr());
		String arfCustCodeString = ","+itemChgService.getArfCustCodeList()+",";
		boolean needRefCustCode=false;
		if(arfCustCodeString.split(itemChg.getCustCode()).length>=2){
			needRefCustCode = true;
		}
		DiscToken discToken = new DiscToken();
		discToken.setChgNo(itemchg.getNo());
		itemChg.setDiscTokenNo(discTokenService.getDiscTokenNo(discToken));
		if (StringUtils.isNotBlank(itemChg.getDiscTokenNo())){
			discToken  = discTokenService.get(DiscToken.class, itemChg.getDiscTokenNo());
			if(discToken!=null){
				itemChg.setDiscTokenAmount(discToken.getAmount());
		 	}
	    }	
		Map<String,Object> balanceMap = customerService.getShouldBanlance(itemChg.getCustCode());
		
        // ??????????????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????????????????????????????????????????
        if (customer.getInnerArea() != null
            && customer.getInnerArea().doubleValue() != 0) {
            
            itemChg.setInnerArea(customer.getInnerArea());
        } else {
            Double innerArea = customer.getArea() * custType.getInnerAreaPer();
            itemChg.setInnerArea(Math.round(innerArea * 100) / 100.0);
        }
		
		return new ModelAndView("admin/project/itemChg/itemChg_detail")
			.addObject("itemChg", itemChg).addObject("refCustCodeDescr", refCustCodeDescr)
			.addObject("needRefCustCode", needRefCustCode)
			.addObject("balanceMap", balanceMap);
	}
	/**
	 * ???????????????ItemChg??????
	 * @return
	 */
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request, HttpServletResponse response, 
			ItemChg itemchg){
		logger.debug("???????????????ItemChg??????");
		ItemChg itemChg = null;
		String refCustCodeDescr="";

		if (StringUtils.isNotBlank(itemchg.getNo())){
			itemChg = itemChgService.get(ItemChg.class, itemchg.getNo().trim());
		}else{
			itemChg = new ItemChg();
		}
		UserContext uc=this.getUserContext(request);
		Customer customer=customerService.get(Customer.class, itemChg.getCustCode());
		itemChg.setAppCzyDescr(itemchg.getAppCzyDescr());
		itemChg.setDocumentNo(itemchg.getDocumentNo());
		itemChg.setAddress(itemchg.getAddress());
		CustType custType= custTypeService.get(CustType.class, itemchg.getCustType());
		itemChg.setManageFeeCupPer(custType.getManageFeeCupPer());
		itemChg.setManageFeeMainPer(custType.getManageFeeMainPer());
		itemChg.setManageFeeServPer(custType.getManageFeeServPer());
		itemChg.setChgManageFeePer(custType.getChgManageFeePer());
		itemChg.setCustType(itemchg.getCustType());
		itemChg.setIsOutSet(custType.getType());
		itemChg.setAppCzy(uc.getCzybh());
		itemChg.setContainMain(customer.getContainMain());
		itemChg.setContainMainServ(customer.getContainMainServ());
		itemChg.setContainInt(customer.getContainInt());
		itemChg.setContainSoft(customer.getContainSoft());
		itemChg.setFaultManDescr(itemchg.getFaultManDescr());
		itemChg.setPrjQualityFee(itemchg.getPrjQualityFee());
		itemChg.setProjectMan(itemchg.getProjectMan());
		itemChg.setProjectManDescr(itemchg.getProjectManDescr());
		if(StringUtils.isBlank(itemChg.getIsCupboard())){
			itemChg.setIsCupboard("0");
		}
		if(StringUtils.isNotBlank(itemchg.getConfirmCzyDescr())){
			itemChg.setConfirmCzyDescr("|"+itemchg.getConfirmCzyDescr());
		}
		itemChg.setStatusDescr(itemchg.getStatusDescr());
		itemChg.setLastUpdatedBy(uc.getCzybh());
		itemChg.setCostRight(uc.getCostRight().trim());
		if(StringUtils.isNotBlank(itemChg.getRefCustCode())){
			if(customerService.get(Customer.class,itemChg.getRefCustCode())!=null){
				refCustCodeDescr=customerService.get(Customer.class,itemChg.getRefCustCode()).getDescr();
			}
		}
		String arfCustCodeString = ","+itemChgService.getArfCustCodeList()+",";
		boolean needRefCustCode=false;
		if(arfCustCodeString.split(itemChg.getCustCode()).length>=2){
			needRefCustCode = true;
		}	
		DiscToken discToken = new DiscToken();
		discToken.setChgNo(itemchg.getNo());
		itemChg.setDiscTokenNo(discTokenService.getDiscTokenNo(discToken));
		if (StringUtils.isNotBlank(itemChg.getDiscTokenNo())){
			discToken  = discTokenService.get(DiscToken.class, itemChg.getDiscTokenNo());
			if(discToken!=null){
				itemChg.setDiscTokenAmount(discToken.getAmount());
		 	}
	    }	
		Double checkAmount = itemChgService.getAmountByItemType(itemChg.getItemType1(), itemChg.getCustCode());
		
        // ??????????????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????????????????????????????????????????
        if (customer.getInnerArea() != null
            && customer.getInnerArea().doubleValue() != 0) {
            
            itemChg.setInnerArea(customer.getInnerArea());
        } else {
            Double innerArea = customer.getArea() * custType.getInnerAreaPer();
            itemChg.setInnerArea(Math.round(innerArea * 100) / 100.0);
        }
        Map<String, Object> map = new HashMap<String, Object>();
		map = customerService.getMaxDiscByCustCode(itemChg.getCustCode());
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
        
        return new ModelAndView("admin/project/itemChg/itemChg_confirm")
			.addObject("itemChg", itemChg).addObject("refCustCodeDescr", refCustCodeDescr)
			.addObject("needRefCustCode", needRefCustCode)
			.addObject("checkAmount", checkAmount).addObject("totalRemainDisc", totalRemainDisc);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/project/itemChg/itemChg_detailQuery");
				
	}
	/**
	 * ?????????itemchg??????????????????
	 * @return
	 */
	@RequestMapping("/goItemChg_detailView")
	public ModelAndView goItemChg_detailView(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		if("JC".equals(itemChg.getItemType1().trim())){
			return new ModelAndView("admin/project/itemChg/itemChg_detailView_JC").addObject("itemChg", itemChg);
		}
		return new ModelAndView("admin/project/itemChg/itemChg_detailView").addObject("itemChg", itemChg);
				
	}
	/**
	 * ?????????itemchg??????????????????
	 * @return
	 */
	@RequestMapping("/goItemChg_detailViewEdit")
	public ModelAndView goItemChg_detailViewEdit(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		Item item=itemService.get(Item.class, itemChg.getItemCode());
		Customer customer=customerService.get(Customer.class,itemChg.getCustCode());
		itemChg.setAmount(item.getPrice());
		itemChg.setCustType(customer.getCustType().trim());
		if("JC".equals(itemChg.getItemType1().trim())){
			return new ModelAndView("admin/project/itemChg/itemChg_detailViewEdit_JC").addObject("itemChg", itemChg);
		}
		return new ModelAndView("admin/project/itemChg/itemChg_detailViewEdit").addObject("itemChg", itemChg);
				
	}
	/**
	 * ?????????itemchg??????????????????
	 * @return
	 */
	@RequestMapping("/goItemChg_tmplineAmountView")
	public ModelAndView goItemChg_tmplineAmountView(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_tmplineAmountView");
				
	}
	
	/**
	 * ????????????????????????????????????
	 * @author	created by zb
	 * @date	2020-4-20--??????4:45:56
	 * @param request
	 * @param response
	 * @param chgStakeholderList
	 * @return
	 */
	@RequestMapping("/goChgStakeholderEdit")
	public ModelAndView goChgStakeholderEdit(HttpServletRequest request, HttpServletResponse response, 
			String chgStakeholderList, String m_umState){
		logger.debug("????????????????????????????????????");
		return new ModelAndView("admin/project/itemChg/itemChg_chgStakeholder")
			.addObject("chgStakeholderList", chgStakeholderList.replace("},", "}\\@zb"))
			.addObject("m_umState", m_umState);
	}
	
	/**
	 * @author	created by zb
	 * @date	2020-4-20--??????4:50:15
	 */
	@RequestMapping("/goEmpAdd")
	public ModelAndView goEmpAdd(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg, 
			String keys){
		logger.debug("????????????????????????????????????");
		return new ModelAndView("admin/project/itemChg/itemChg_chgStakeholderAdd")
			.addObject("baseItemChg", itemChg)
			.addObject("keys", keys);
	}
	
	@RequestMapping("/goDiscAmtTran")
	public ModelAndView goDiscAmtTran(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg){
		logger.debug("?????????????????????????????????");
		return new ModelAndView("admin/project/itemChg/itemChg_discAmtTran")
			.addObject("baseItemChg", itemChg);
	}
	
	@RequestMapping("/goSaveDiscAmtTran")
	public ModelAndView goSaveDiscAmtTran(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg){
		logger.debug("???????????????????????????????????????");
		Customer customer = customerService.get(Customer.class, itemChg.getCustCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map = customerService.getMaxDiscByCustCode(itemChg.getCustCode());
		CustType custType = new CustType();
		if(customer != null){
			if (map != null) {
				double designerMaxDiscAmount = customer.getDesignerMaxDiscAmount();
				double directorMaxDiscAmount = customer.getDirectorMaxDiscAmount();
				double usedDisc = Double.parseDouble(map.get("zskzsjyh").toString());
				double totalDiscAmtTran = Double.parseDouble(map.get("ExtraDiscChgAmount").toString());
				
				map.put("DesignerMaxDiscAmount", designerMaxDiscAmount);
				map.put("DirectorMaxDiscAmount", directorMaxDiscAmount);
				
				map.put("RemainDisc", designerMaxDiscAmount + directorMaxDiscAmount - usedDisc);
			}
			if(customer.getDesignRiskFund()== null){
				customer.setDesignRiskFund(0.0);
			} 
			custType = custTypeService.get(CustType.class, customer.getCustType());	
		}
		
		return new ModelAndView("admin/project/itemChg/itemChg_saveDiscAmtTran")
			.addObject("baseItemChg", itemChg).addObject("map", map)
			.addObject("custType", custType)
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goUpdateDiscAmtTran")
	public ModelAndView goUpdateDiscAmtTran(HttpServletRequest request, HttpServletResponse response, DiscAmtTran dat){
		logger.debug("???????????????????????????????????????");
		Customer customer = customerService.get(Customer.class, dat.getCustCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map = customerService.getMaxDiscByCustCode(dat.getCustCode());
		CustType custType = new CustType();
		if(customer != null){
			if (map != null) {
				double designerMaxDiscAmount = customer.getDesignerMaxDiscAmount();
				double directorMaxDiscAmount = customer.getDirectorMaxDiscAmount();
				double usedDisc = Double.parseDouble(map.get("zskzsjyh").toString());
				double totalDiscAmtTran = Double.parseDouble(map.get("ExtraDiscChgAmount").toString());
				
				map.put("DesignerMaxDiscAmount", designerMaxDiscAmount);
				map.put("DirectorMaxDiscAmount", directorMaxDiscAmount);
				
				map.put("RemainDisc", designerMaxDiscAmount + directorMaxDiscAmount - usedDisc);
			}
			if(customer != null){
				custType = custTypeService.get(CustType.class, customer.getCustType());	
			}
		}
		DiscAmtTran discAmtTran = itemChgService.get(DiscAmtTran.class, dat.getPk());	
		return new ModelAndView("admin/project/itemChg/itemChg_updateDiscAmtTran")
			.addObject("discAmtTran", discAmtTran)
			.addObject("map", map)
			.addObject("custType", custType);
	}
	
	@RequestMapping("/doSaveDiscAmtTran")
	public void doSaveDiscAmtTran(HttpServletRequest request, HttpServletResponse response, DiscAmtTran discAmtTran){
		try{
			discAmtTran.setLastUpdate(new Date());
			discAmtTran.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			discAmtTran.setExpired("F");
			discAmtTran.setActionLog("ADD");
			discAmtTran.setIsRiskFund("0");
			itemChgService.save(discAmtTran);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	@RequestMapping("/doUpdateDiscAmtTran")
	public void doUpdateDiscAmtTran(HttpServletRequest request, HttpServletResponse response, DiscAmtTran discAmtTran){
		try{
			discAmtTran.setLastUpdate(new Date());
			discAmtTran.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			discAmtTran.setExpired("F");
			discAmtTran.setActionLog("EDIT");
			itemChgService.update(discAmtTran);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	/**
	 * ??????ItemChg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg){
		itemChg.setManageFee((double)Math.round(itemChg.getManageFee()));
		itemChg.setConfirmStatus("4".equals(itemChg.getStatus())?"1":"0");
		Result result= itemChgService.doItemChgForProc(itemChg,"");
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	}
	
	/**
	 * ??????ItemChg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		
		String errMsg = checkStatus(itemChg.getNo());
		if (StringUtils.isNotBlank(errMsg)) {
			ServletUtils.outPrintFail(request, response, errMsg);
			return;
		}
		itemChg.setManageFee((double)Math.round(itemChg.getManageFee()));
		itemChg.setConfirmStatus("4".equals(itemChg.getStatus())?"1":"0");
		Result result= itemChgService.doItemChgForProc(itemChg,"");
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	}
	
	/**
	 * ??????ItemChg
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????ItemChg??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemChg??????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemChg itemChg = itemChgService.get(ItemChg.class, deleteId);
				if(itemChg == null)
					continue;
				itemChg.setExpired("T");
				itemChgService.update(itemChg);
			}
		}
		logger.debug("??????ItemChg IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/goZjyj")
	public ModelAndView goZjyj(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_zjyj")
		.addObject("itemChg",itemChg);
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param itemChg
	 * @return
	 */
	@RequestMapping("/goPlzjyj")
	public ModelAndView goPlzjyj(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_plzjyj")
		.addObject("itemChg",itemChg);
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/doZjyj")
	public void doZjyj(HttpServletRequest request, HttpServletResponse response,ItemChg chg){
		try {
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			ItemChg itemChg=itemChgService.get(ItemChg.class, chg.getNo());
			itemChg.setIscalPerf(chg.getIscalPerf());
			itemChg.setLastUpdate(new Date());
			itemChg.setLastUpdatedBy(uc.getCzybh());
			itemChg.setActionLog("EDIT");
			itemChgService.update(itemChg);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
		
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/doPlzjyj")
	public void doPlzjyj(HttpServletRequest request, HttpServletResponse response,ItemChg chg){
		try {
			chg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			chg.setDetailJson(detailJson);
			Result result= itemChgService.doPlzjyj(chg);
			if (result.isSuccess()) {
				ServletUtils.outPrint(request, response, true, "????????????",  null, true);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
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
	@RequestMapping("/goUpdateDiscount")
	public ModelAndView goUpdateDiscount(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/project/itemChg/itemChg_UpdateDiscount");
		
	}
	
	@RequestMapping("/goUpdateDiscountRz")
	public ModelAndView goUpdateDiscountRz(HttpServletRequest request, HttpServletResponse response
			,String itemSetNoList){
		return new ModelAndView("admin/project/itemChg/itemChg_UpdateDiscountRz")
		.addObject("itemSetNoList", itemSetNoList.replaceAll("\"", "'").replaceAll(",", "','") );
		
	}
	/**
	 * ???????????????
	 */
	@RequestMapping("/goTransAction")
	public ModelAndView goTransAction(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		Czybm czybm=czybmService.get(Czybm.class,uc.getCzybh());
		itemChg.setCostRight(czybm.getCostRight());
		Customer customer = new Customer();
		if(StringUtils.isNotBlank(itemChg.getCustCode())){
			customer = itemChgService.get(Customer.class, itemChg.getCustCode());
			itemChg.setCustType(customer.getCustType());
		}
		return new ModelAndView("admin/project/itemChg/itemChg_transAction").addObject("itemChg", itemChg);
		
	}
	/**
	 * ???????????????
	 */
	@RequestMapping("/goItemPlanAdd")
	public ModelAndView goItemPlanAdd(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_itemPlanAdd").addObject("itemChg", itemChg);
		
	}
	
	/**
	 *???????????????
	 *
	 */
	@RequestMapping("/goSupplProm")
	public ModelAndView goSupplProm(HttpServletRequest request, HttpServletResponse response,ItemChgDetail itemChgDetail){
		Item item = new Item();
		Supplier supplier =new Supplier();
		if(StringUtils.isNotBlank(itemChgDetail.getItemCode())){
			item =itemService.get(Item.class, itemChgDetail.getItemCode());
		}
		if(StringUtils.isNotBlank(item.getSupplCode())){
			supplier= itemService.get(Supplier.class, item.getSupplCode());
		}
		return new ModelAndView("admin/project/itemChg/itemChg_supplProm")
		.addObject("itemChgDetail", itemChgDetail)
		.addObject("supplier", supplier)
		.addObject("item", item);
	}
	/**
	 * ?????????????????????ItemApp??????
	 */
	@RequestMapping("/goQPrint")
	public ModelAndView goQPrint(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_qPrint").addObject("itemChg", itemChg);
		
	}
	/**
	 * ????????????????????????
	 * @author	created by zb
	 * @date	2020-3-18--??????5:43:16
	 * @param request
	 * @param response
	 * @param itemChg
	 * @return
	 */
	@RequestMapping("/goSoftChgItemStatus")
	public ModelAndView goSoftChgItemStatus(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_softChgItemStatus")
			.addObject("itemChg", itemChg);
	}
	
	@RequestMapping("/goFixAreaAdd")
	public ModelAndView goFixAreaAdd(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		
		CustType custType = new CustType();
		if(StringUtils.isNotBlank(itemChg.getCustCode())){
			Customer customer=customerService.get(Customer.class, itemChg.getCustCode());
			if(customer!=null){
				itemChg.setMainTempNo(customer.getMainTempNo());
				custType = itemChgService.get(CustType.class, customer.getCustType());
			}
		}
		
		return new ModelAndView("admin/project/itemChg/itemChg_fixAreaAdd")
			.addObject("itemChg", itemChg).addObject("custType", custType);
	}
	
	@RequestMapping("/goFixAreaEdit")
	public ModelAndView goFixAreaEdit(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		
		FixArea fixArea = new FixArea();
		PrePlanArea prePlanArea = new PrePlanArea();
		
		if(itemChg.getFixAreaPk() != null){
			fixArea = itemChgService.get(FixArea.class, itemChg.getFixAreaPk());
		}
		
		if(fixArea.getPrePlanAreaPK() != null){
			prePlanArea = itemChgService.get(PrePlanArea.class, fixArea.getPrePlanAreaPK());
		}

		if(prePlanArea != null){
			fixArea.setPrePlanAreaDescr(prePlanArea.getDescr());
		}
		if(StringUtils.isNotBlank(itemChg.getCustCode())){
			Customer customer=customerService.get(Customer.class, itemChg.getCustCode());
			if(customer!=null){
				itemChg.setMainTempNo(customer.getMainTempNo());
			}
		}
		
		return new ModelAndView("admin/project/itemChg/itemChg_fixAreaEdit")
			.addObject("itemChg", itemChg).addObject("fixArea", fixArea);
	}
	
	@RequestMapping("/goFixAreaInsert")
	public ModelAndView goFixAreaInsert(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		
		CustType custType = new CustType();
		if(StringUtils.isNotBlank(itemChg.getCustCode())){
			Customer customer=customerService.get(Customer.class, itemChg.getCustCode());
			if(customer!=null){
				itemChg.setMainTempNo(customer.getMainTempNo());
				custType = itemChgService.get(CustType.class, customer.getCustType());
			}
		}
		
		return new ModelAndView("admin/project/itemChg/itemChg_fixAreaInsert")
			.addObject("itemChg", itemChg).addObject("custType",custType);
	}
	
	/**
	 *ItemChg??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemChg itemChg){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemChgService.findPageBySql(page, itemChg,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 *ItemChg??????????????????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDetailQueryExcel")
	public void doDetailQueryExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemChg itemChg){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemChgService.findDetailPageBySql(page, itemChg,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * ??????????????????tab??????????????????????????????????????????
	 * 
	 * @param menuList
	 * @return
	 */
	private List<Menu> getTabMenuList(List<Menu> menuList) {
		if (menuList == null) {
			logger.warn("??????????????????");
			return null;
		}
		List<Menu> tabMenus = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			if (menu != null
					&& DictConstant.DICT_MENU_TYPE_TAB.equals(menu
							.getMenuType())) {
				tabMenus.add(menu);
			}
		}
		return tabMenus;
	}
	private String createHtml(List<Menu> tabMenus) {
		StringBuilder strb = new StringBuilder();
		for (Menu tabMenu : tabMenus) {
			if (tabMenu != null) {
				strb.append("<div title='").append(tabMenu.getMenuName())
						.append("' class='l-scroll' id='div_")
						.append(tabMenu.getMenuId()).append("'>")
						.append("<ul id='tab_").append(tabMenu.getMenuId())
						.append("' class='ztree' style='overflow:auto;' />")
						.append("</div>\n");
			}
		}

		return strb.toString();
	}
	private String createJs(List<Menu> allMenuList, List<Menu> tabMenus) {
		StringBuilder strb = new StringBuilder();

		List<Menu> menuTree = null;
		for (Menu tabMenu : tabMenus) {
			if (tabMenu != null) {
				menuTree = this.divideByTabMenu(allMenuList,
						tabMenu.getMenuId());
				if (menuTree == null)
					continue;
				strb.append("$.fn.zTree.init($(\"#tab_")
						.append(tabMenu.getMenuId()).append("\"), setting, ")
						.append(JSON.toJSONString(menuTree)).append(");\n\n");
			}
		}
		return strb.toString();
	}
	private List<Menu> divideByTabMenu(List<Menu> allMenuList, Long parentMenuId) {
		List<Menu> list = new ArrayList<Menu>();

		for (Menu menu : allMenuList) {
			if (menu.getParentId().longValue() == parentMenuId.longValue()) {
				list.add(menu);
				// ????????????????????????????????????????????????
				if (DictConstant.DICT_MENU_TYPE_FOLDER.equals(menu
						.getMenuType())) {
					list.addAll(this.divideByTabMenu(allMenuList,
							menu.getMenuId()));
				}
			}
		}
		return list;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param rootId
	 * @return
	 */
	@RequestMapping("/goItemCopy")
	public ModelAndView goItemCopy(HttpServletRequest request, HttpServletResponse response, String itemType1){
		
		return new ModelAndView("admin/project/itemChg/tab_copy").addObject("itemType1", itemType1);
	}
	/**
	 * ???????????????ItemChg??????
	 * @return
	 */
	@RequestMapping("/goItemChgAdd")
	public ModelAndView goItemChgAdd(HttpServletRequest request, HttpServletResponse response, 
			ItemChg itemChg){
		boolean needRefCustCode = false;
		if(itemChg.getCustCode()==null){
			return null;
		}
		if(itemChg!=null){
			Customer customer=customerService.get(Customer.class,itemChg.getCustCode());
			CustType custType= custTypeService.get(CustType.class, itemChg.getCustType());
			UserContext uc=this.getUserContext(request);
			itemChg.setTempNo(customer.getMainTempNo());
			itemChg.setConstructStatus(customer.getConstructStatus());
            if (StringUtils.isNotBlank(customer.getMainTempNo())) {
                PrePlanTemp prePlanTemp = prePlanTempService.get(PrePlanTemp.class,
                        customer.getMainTempNo());
                if (prePlanTemp != null) {
                    itemChg.setTempDescr(customer.getMainTempNo() + "|" + prePlanTemp.getDescr());
                }
            }
            
            // ??????????????????????????????????????????????????????????????????????????????
            // ????????????????????????????????????????????????????????????????????????
            if (customer.getInnerArea() != null
                && customer.getInnerArea().doubleValue() != 0) {
                
                itemChg.setInnerArea(customer.getInnerArea());
            } else {
                Double innerArea = customer.getArea() * custType.getInnerAreaPer();
                itemChg.setInnerArea(Math.round(innerArea * 100) / 100.0);
            }
			
			if(custType != null){
				itemChg.setManageFeeCupPer(custType.getManageFeeCupPer());
				itemChg.setManageFeeMainPer(custType.getManageFeeMainPer());
				itemChg.setManageFeeServPer(custType.getManageFeeServPer());
				itemChg.setChgManageFeePer(custType.getChgManageFeePer());
				itemChg.setIsOutSet(custType.getType());
				itemChg.setSignQuoteType(custType.getSignQuoteType());
			}else {
				itemChg.setManageFeeCupPer(0.0);
				itemChg.setManageFeeMainPer(0.0);
				itemChg.setManageFeeServPer(0.0);
				itemChg.setChgManageFeePer(0.0);
				itemChg.setIsOutSet(null);
			}
			itemChg.setDocumentNo(customer.getDocumentNo());
			itemChg.setAppCzy(uc.getCzybh());
			itemChg.setContainMain(customer.getContainMain());
			itemChg.setContainMainServ(customer.getContainMainServ());
			itemChg.setContainInt(customer.getContainInt());
			itemChg.setContainSoft(customer.getContainSoft());
			String itemType1Descr="";
			if("ZC".equals(itemChg.getItemType1())) itemType1Descr="??????";
			if("RZ".equals(itemChg.getItemType1())) itemType1Descr="??????";
			if("JC".equals(itemChg.getItemType1())) itemType1Descr="??????";
			itemChg.setItemType1Descr(itemType1Descr);
		}
		String arfCustCodeString = ","+itemChgService.getArfCustCodeList()+",";
		
		if(arfCustCodeString.split(itemChg.getCustCode()).length>=2){
			needRefCustCode = true;
		}
		UserContext uc=this.getUserContext(request);
		itemChg.setLastUpdatedBy(uc.getCzybh());
		
		Map<String,Object> balanceMap = customerService.getShouldBanlance(itemChg.getCustCode());
		boolean existsTemp = itemChgService.getExistsTemp(itemChg.getCustCode(),"");
		return new ModelAndView("admin/project/itemChg/itemChg_add")
			.addObject("itemChg", itemChg)
			.addObject("needRefCustCode", needRefCustCode)
			.addObject("existsTemp", existsTemp)
			.addObject("balanceMap", balanceMap);
	}
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping("/goItemChg_itemAppDetail")
	public ModelAndView goItemChg_itemAppDetail(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/project/itemChg/itemChg_itemAppDetail");
			
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param rootId
	 * @return
	 */
	@RequestMapping("/goPrintRZ")
	public ModelAndView goPrintRZ(HttpServletRequest request, HttpServletResponse response, String no){
		
		return new ModelAndView("admin/project/itemChg/tab_printListRZ").addObject("no", no);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param rootId
	 * @return
	 */
	@RequestMapping("/goPrintZC")
	public ModelAndView goPrintZC(HttpServletRequest request, HttpServletResponse response, String no){
		
		return new ModelAndView("admin/project/itemChg/tab_printListZC").addObject("no", no);
	}
	/**
	 * ???????????????????????????????????????(?????????????????????0???)
	 */
	@RequestMapping("/isExistsNotSendItemList")
	@ResponseBody
	public void isExistsNotSendItemList(HttpServletRequest request,HttpServletResponse response){
	  Xtcs xtcs=xtcsService.get(Xtcs.class, "CmpCustCode");
		String custCode=request.getParameter("custCode");
		String gridData=request.getParameter("gridData");
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(xtcs.getQz().indexOf(custCode)!=-1){
			ServletUtils.outPrintFail(request, response,"");
			return;
		}  
		List<Map<String,Object>> itemAppDetailList=itemAppDetailService.findNotSendItemList(custCode);
		if(itemAppDetailList==null||itemAppDetailList.size()==0){
			ServletUtils.outPrintFail(request, response,"");
			return;
			
		} 
	  JSONArray jsonArray = JSONArray.fromObject(gridData);  
	  List<Map<String,Object>> gridList = (List)jsonArray; 
	  for(Map<String,Object> gridmap:gridList){
		  for(Map<String,Object> detailmap:itemAppDetailList){
			  if(gridmap.get("reqpk")!=null){
				  if(gridmap.get("reqpk").toString().equals(detailmap.get("ReqPK").toString())
						  &&Double.parseDouble(gridmap.get("qty").toString())<0){
					  gridmap.put("no", detailmap.get("no"));
					  list.add(new HashMap<String, Object>(gridmap));
				  }
			  }
			
		  }
		  
	  }
	  ServletUtils.outPrintFail(request, response,list);
	}
	/**
	 * ?????????????????????
	 */
	@RequestMapping("/doComfirm")
	public void doComfirm(HttpServletRequest request,HttpServletResponse response,ItemChg itemChg){
		
		String errMsg = checkStatus(itemChg.getNo());
		if (StringUtils.isNotBlank(errMsg)) {
			ServletUtils.outPrintFail(request, response, errMsg);
			return;
		}
		itemChg.setConfirmStatus("3");
		itemChg.setManageFee((double)Math.round(itemChg.getManageFee()));
		itemChg.setPlanArriveDate(itemChgService.get(ItemChg.class, itemChg.getNo()).getPlanArriveDate());
		Result result= itemChgService.doItemChgForProc(itemChg,"2");
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
		
	}
	/**
	 * ?????????????????????
	 */
	@RequestMapping("/doComfirmCancel")
	public void doComfirmCancel(HttpServletRequest request,HttpServletResponse response,ItemChg itemChg){
		String errMsg = checkStatus(itemChg.getNo());
		if (StringUtils.isNotBlank(errMsg)) {
			ServletUtils.outPrintFail(request, response, errMsg);
			return;
		}
		itemChg.setConfirmStatus("4");
		itemChg.setPlanArriveDate(itemChgService.get(ItemChg.class, itemChg.getNo()).getPlanArriveDate());
		Result result= itemChgService.doItemChgForProc(itemChg,"3");
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
		
	}
	
	/**
	 * ?????????????????????
	 */
	@RequestMapping("/doComfirmReturn")
	public void doComfirmReturn(HttpServletRequest request,HttpServletResponse response,ItemChg itemChg){
		String errMsg = checkStatus(itemChg.getNo());
		if (StringUtils.isNotBlank(errMsg)) {
			ServletUtils.outPrintFail(request, response, errMsg);
			return;
		}
		itemChg.setManageFee((double)Math.round(itemChg.getManageFee()));
		itemChg.setPlanArriveDate(itemChgService.get(ItemChg.class, itemChg.getNo()).getPlanArriveDate());
		itemChg.setConfirmStatus("2");
		
		Result result= itemChgService.doItemChgForProc(itemChg,"1");
		
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
		
	}
	/**
	 * ??????????????????
	 */
	@RequestMapping("/isAllowChg")
	@ResponseBody
	public  boolean  isAllowChg(HttpServletRequest request,HttpServletResponse response,Customer customer){
		return itemChgService.isAllowChg(customer);
		
	}
	
	@RequestMapping("/getChangeParameterItemType2")
	@ResponseBody
	public List<Map<String, Object>> getChangeParameterItemType2(HttpServletRequest request ,HttpServletResponse response,
			String custCode,String isService,String itemType1 ){
		List<Map<String, Object>> list;
				list=this.itemChgService.getChangeParameterItemType2(custCode,itemType1,isService);
			return list;
	
	}
	
	/**
	 * ?????????????????? ???  ?????????????????????????????????
	 * @param no
	 * @return
	 */
	public String checkStatus(String no) {
		ItemChg itemChg = this.itemChgService.get(ItemChg.class, no);
		if (itemChg == null) {
			return "?????????????????????????????????????????????";
		}
		
		if (!"1".equals(itemChg.getStatus().trim())) { // 1.?????????
			return "???????????????????????????????????????,?????????????????????!";
		}
		
		return "";
	}
	/**
	 * ?????????itemchg??????????????????
	 * @return
	 */
	@RequestMapping("/goItemChg_itemReplace")
	public ModelAndView goItemChg_itemReplace(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		Item item=itemService.get(Item.class, itemChg.getItemCode());
		Customer customer=customerService.get(Customer.class,itemChg.getCustCode());
		itemChg.setAmount(item.getPrice());
		itemChg.setCustType(customer.getCustType().trim());
		/*if("JC".equals(itemChg.getItemType1().trim())){
			return new ModelAndView("admin/project/itemChg/itemChg_itemReplace_JC").addObject("itemChg", itemChg);
		}*/
		return new ModelAndView("admin/project/itemChg/itemChg_itemReplace").addObject("itemChg", itemChg);
				
	}
	
	@RequestMapping("/goItemReplace")
	public ModelAndView goItemReplace(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		
		Customer customer=customerService.get(Customer.class,itemChg.getCustCode());
		itemChg.setCustType(customer.getCustType().trim());
		JSONObject jsonObject = new JSONObject(new String(itemChg.getDetailJson()));
		return new ModelAndView("admin/project/itemChg/itemChg_multiItemReplace").addObject("itemChg", itemChg)
				.addObject("jsonObject",jsonObject);
				
	}
	
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param itemChg
	 */
	@RequestMapping("/doItemChgFromTemp")
	public void doItemChgFromTemp(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemChg.setLastUpdatedBy(uc.getCzybh());
		Result result= itemChgService.doItemChgTempProc(itemChg);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/getRegenFromPrePlanTemp")
	public void getRegenFromPrePlanTemp(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemChg.setLastUpdatedBy(uc.getCzybh());
		Result result= itemChgService.doRegenFromPrePlanTemp(itemChg);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	
	/** 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goMainItemSet")
	public ModelAndView goMainItemSet(HttpServletRequest request,
			HttpServletResponse response, ItemChg itemChg) throws Exception {
		return new ModelAndView("admin/project/itemChg/tab_mainItemSet").addObject("itemChg", itemChg);
	}
	
	/**
	 * ?????????????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/getGenChgMainItemSet")
	public void doGenChgMainItemSet(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemChg.setLastUpdatedBy(uc.getCzybh());
		Result result= itemChgService.doGenChgMainItemSet(itemChg);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo(),result.getJson());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	
	}
	
	@RequestMapping("/goViewSwitch")
	public ModelAndView goViewSwitch(HttpServletRequest request ,
			HttpServletResponse response , Customer customer) throws Exception{
		logger.debug("??????????????????");
		
		if(StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		
		return new ModelAndView("admin/project/itemChg/itemChg_viewSwitch")
			.addObject("customer",customer);
	}
	
	/**
	 * ???????????????
	 */
	@RequestMapping("/cancel")
	public void cancelItemChg(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg) {
	    
	    try {
            
	        if (StringUtils.isBlank(itemChg.getNo())) {
	            throw new IllegalStateException("?????????????????????????????????!");
	        }
	        
	        itemChg = itemChgService.get(ItemChg.class, itemChg.getNo());
	        
	        if (itemChg == null) {
                throw new IllegalStateException("???????????????????????????????????????");
            }
	        
	        if (!"1".equals(itemChg.getStatus().trim())) {
                throw new IllegalStateException("?????????????????????????????????????????????????????????");
            }
	        
	        itemChg.setStatus("3");
	        itemChg.setLastUpdate(new Date());
	        itemChg.setLastUpdatedBy(getUserContext(request).getCzybh());
	        itemChg.setActionLog("EDIT");
	        itemChgService.update(itemChg);
	        
	        ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, e.getMessage());
            e.printStackTrace();
        }
	    
	}

}
