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
	 * 查询JqGrid表格数据
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
	 * 查询JqGrid表格数据
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
	 * 已有项变动数据
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
	 * 已有项变动数据
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
	 * 参考业绩数据
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
	 * 查看软装材料状态
	 * @author	created by zb
	 * @date	2020-3-18--下午5:59:43
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
	 * 获取增减干系人列表
	 * @author	created by zb
	 * @date	2020-4-20--下午4:38:02
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
	 * 基础项目增减中套内减项列表
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
	 * 判断一个客户是否存在一个或多个材料类型2的基础减项。
	 * 前端必须传客户编号与材料类型2封装到ItemChg实体中；否则抛出异常。
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
            
	        throw new IllegalArgumentException("缺少客户编号或材料类型2");
        }
	    
	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    page.setPageSize(-1);
	    itemChgService.findSetDeductions(page, itemChg);

	    return page.getResult().size() > 0;
	}
	
	/**
	 * ItemChg列表
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
	 * 从选材批次导入
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
	 * 跳转到导入excel界面
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
		logger.debug("跳转到模板导入页面");
		return new ModelAndView("admin/project/itemChg/itemChg_itemFromTemp")
			.addObject("ItemChg", ItemChg);
	}
	/**
	 * 
	 * 加载excel
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
		}
		titleList.add("材料编号");
		titleList.add("变动数量");
		titleList.add("单价");
		titleList.add("折扣");
		titleList.add("其他费用");
		titleList.add("材料描述");
		
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
				//新增套餐包
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
							data.put("isinvaliddescr", "无效，算法有误");
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
								data.put("isinvaliddescr", "无效，切割类型有误");
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
				if("否".equals(itemChgDetailBean.getIsOutSetDescr())){
					data.put("isoutset", "0");
					data.put("isoutsetdescr", "否");
					if(itemChgDetailBean.getCustTypeItemPk()!=null&&itemChgDetailBean.getCustTypeItemPk()!=0){
						if(custTypeItemService.hasItem(itemChgDetailBean.getCustTypeItemPk(), customer.getCustType().trim(), itemType1)){
							data.put("custtypeitempk", itemChgDetailBean.getCustTypeItemPk());
							CustTypeItem custTypeItem=custTypeItemService.get(CustTypeItem.class, itemChgDetailBean.getCustTypeItemPk());
							//套餐内材料升级价和结算价取套餐材料信息表。描述取套餐升级描述
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
							//套餐内材料升级价和结算价取套餐材料信息表。描述取套餐升级描述
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
					data.put("isoutsetdescr", "是");
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
						//如果该材料是固定价，则取数据库中的单价
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
						data.put("isinvaliddescr", "无效,供应商促销PK无效");
					}else{
						data.put("supplpromitempk", itemChgDetailBean.getSupplPromItemPk());	
					}	
			    }
				data.put("dispseq", i);
				if("1".equals(data.get("isinvalid"))){
					data.put("isinvaliddescr", "无效,存在异常数据");
					map.put("hasInvalid", true);
				}else if(item2==null){
					data.put("isinvaliddescr", "无效的材料编号");
					if("否".equals(itemChgDetailBean.getIsOutSetDescr()))
					data.put("isinvaliddescr", "无效的套餐材料信息编号");
					map.put("hasInvalid", true);
				}else if("0".equals(data.get("isoutset"))&&"1".equals(customer.getCustType().trim())){
					data.put("isinvaliddescr", "无法导入套餐内材料预算");
					map.put("hasInvalid", true);
				}else if(itemChgDetailBean.getMarkup()<=0||itemChgDetailBean.getMarkup()>100){
					data.put("isinvaliddescr", "折扣必须>0，<=100");
					map.put("hasInvalid", true);
				}else if(itemChgDetailBean.getReqPk()!=null&&itemChgDetailBean.getReqPk()!=0&&reqpks.containsKey(itemChgDetailBean.getReqPk())){
					data.put("isinvaliddescr", "存在相同的需求PK");
					map.put("hasInvalid", true);
				}else{
					data.put("isinvaliddescr", "有效");
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
							data.put("isinvaliddescr", "无效,单价与原需求单价不符");
						}else if(!itemReq.getItemCode().equals(itemChgDetailBean.getItemCode())){
							map.put("hasInvalid", true);
							data.put("isinvalid", 1);
							data.put("isinvaliddescr", "无效,材料编号与原需求材料不符");
						}else if(!itemChgDetailBean.getMarkup().equals(itemReq.getMarkup())){
							map.put("hasInvalid", true);
							data.put("isinvalid", 1);
							data.put("isinvaliddescr", "无效,折扣与原需求折扣不符");
						}
					};
				}
				if(!itemChgService.existsItemCmp(itemChgDetailBean.getItemCode(),custCode)){
					map.put("hasInvalid", true);
					data.put("isinvalid", 1);
					data.put("isinvaliddescr", "无效,此材料供应商不属于该公司");
				}
				
				if(itemType2!=null){
					data.put("itemtype2descr", itemType2.getDescr());
				}
				if(itemType3!=null){
					data.put("itemtype3descr", itemType3.getDescr());
				}
				if(itemChgDetailBean.getReqPk()!=null&&itemChgDetailBean.getReqPk()!=0){
					//判断需求pk是否重复后加入
					reqpks.put(itemChgDetailBean.getReqPk(),itemChgDetailBean.getReqPk());
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
	 * ItemChg查询code
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
	 * 跳转到新增ItemChg页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String itemType1){
		logger.debug("跳转到增减页面");
		return new ModelAndView("admin/project/itemChg/itemChg_customerCode").addObject("itemType1", itemType1);
	}
	/**
	 * 跳转到快速新增页面
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
	 * 跳转到修改ItemChg页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			ItemChg itemchg){
		logger.debug("跳转到修改ItemChg页面");
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
	    
        // 取客户套内面积，先取客户表套内面积，如果为空或为零；
        // 再取客户表面积乘以该客户的客户类型套内面积系数。
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
	 * 跳转到查看ItemChg页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			ItemChg itemchg){
		logger.debug("跳转到修改ItemChg页面");
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
		
        // 取客户套内面积，先取客户表套内面积，如果为空或为零；
        // 再取客户表面积乘以该客户的客户类型套内面积系数。
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
	 * 跳转到审核ItemChg页面
	 * @return
	 */
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request, HttpServletResponse response, 
			ItemChg itemchg){
		logger.debug("跳转到审核ItemChg页面");
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
		
        // 取客户套内面积，先取客户表套内面积，如果为空或为零；
        // 再取客户表面积乘以该客户的客户类型套内面积系数。
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
	 * 跳转到明细查询页面
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/project/itemChg/itemChg_detailQuery");
				
	}
	/**
	 * 跳转到itemchg明细查看页面
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
	 * 跳转到itemchg明细编辑页面
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
	 * 跳转到itemchg明细编辑页面
	 * @return
	 */
	@RequestMapping("/goItemChg_tmplineAmountView")
	public ModelAndView goItemChg_tmplineAmountView(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_tmplineAmountView");
				
	}
	
	/**
	 * 跳转到增减干系人编辑页面
	 * @author	created by zb
	 * @date	2020-4-20--下午4:45:56
	 * @param request
	 * @param response
	 * @param chgStakeholderList
	 * @return
	 */
	@RequestMapping("/goChgStakeholderEdit")
	public ModelAndView goChgStakeholderEdit(HttpServletRequest request, HttpServletResponse response, 
			String chgStakeholderList, String m_umState){
		logger.debug("跳转到增减干系人编辑页面");
		return new ModelAndView("admin/project/itemChg/itemChg_chgStakeholder")
			.addObject("chgStakeholderList", chgStakeholderList.replace("},", "}\\@zb"))
			.addObject("m_umState", m_umState);
	}
	
	/**
	 * @author	created by zb
	 * @date	2020-4-20--下午4:50:15
	 */
	@RequestMapping("/goEmpAdd")
	public ModelAndView goEmpAdd(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg, 
			String keys){
		logger.debug("跳转到增减干系人编辑页面");
		return new ModelAndView("admin/project/itemChg/itemChg_chgStakeholderAdd")
			.addObject("baseItemChg", itemChg)
			.addObject("keys", keys);
	}
	
	@RequestMapping("/goDiscAmtTran")
	public ModelAndView goDiscAmtTran(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg){
		logger.debug("跳转到优惠调度功能页面");
		return new ModelAndView("admin/project/itemChg/itemChg_discAmtTran")
			.addObject("baseItemChg", itemChg);
	}
	
	@RequestMapping("/goSaveDiscAmtTran")
	public ModelAndView goSaveDiscAmtTran(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg){
		logger.debug("跳转到新增优惠额度调整页面");
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
		logger.debug("跳转到新增优惠额度调整页面");
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
			ServletUtils.outPrintFail(request, response, "添加优惠额度调整失败");
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
			ServletUtils.outPrintFail(request, response, "编辑优惠额度调整失败");
		}
	}
	
	/**
	 * 添加ItemChg
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
	 * 修改ItemChg
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
	 * 删除ItemChg
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemChg开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemChg编号不能为空,删除失败");
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
		logger.debug("删除ItemChg IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
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
	 * 批量增减业绩
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
	 * 增减业绩
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
			ServletUtils.outPrintFail(request, response, "修改增减业绩失败");
		}
		
	}
	
	/**
	 * 批量增减业绩
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
				ServletUtils.outPrint(request, response, true, "操作成功",  null, true);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改增减业绩失败");
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
	 * 已有项变动
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
	 * 从预算添加
	 */
	@RequestMapping("/goItemPlanAdd")
	public ModelAndView goItemPlanAdd(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_itemPlanAdd").addObject("itemChg", itemChg);
		
	}
	
	/**
	 *供应商促销
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
	 * 跳转到批量打印ItemApp页面
	 */
	@RequestMapping("/goQPrint")
	public ModelAndView goQPrint(HttpServletRequest request, HttpServletResponse response,ItemChg itemChg){
		return new ModelAndView("admin/project/itemChg/itemChg_qPrint").addObject("itemChg", itemChg);
		
	}
	/**
	 * 软装查看材料状态
	 * @author	created by zb
	 * @date	2020-3-18--下午5:43:16
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
	 *ItemChg导出Excel
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
				"材料增减_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 *ItemChg明细查询导出Excel
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
				"材料增减明细查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 分离出所有的tab类型菜单，作为生成树的发起点
	 * 
	 * @param menuList
	 * @return
	 */
	private List<Menu> getTabMenuList(List<Menu> menuList) {
		if (menuList == null) {
			logger.warn("用户菜单为空");
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
				// 文件夹类型菜单，递归获取其子菜单
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
	 * 跳转到新增ItemChg页面
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
            
            // 取客户套内面积，先取客户表套内面积，如果为空或为零；
            // 再取客户表面积乘以该客户的客户类型套内面积系数。
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
			if("ZC".equals(itemChg.getItemType1())) itemType1Descr="主材";
			if("RZ".equals(itemChg.getItemType1())) itemType1Descr="软装";
			if("JC".equals(itemChg.getItemType1())) itemType1Descr="集成";
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
	 * 跳转到未发货领料页面
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
	 * 判断是否存在未发货的领料单(不含领料数量为0的)
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
	 * 增减单审核通过
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
	 * 增减单审核取消
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
	 * 增减单审核取消
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
	 * 是否允许增减
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
	 * 审核，和编辑 时  判断状态是否发生改变。
	 * @param no
	 * @return
	 */
	public String checkStatus(String no) {
		ItemChg itemChg = this.itemChgService.get(ItemChg.class, no);
		if (itemChg == null) {
			return "没有找到增减单，无法进行此操作";
		}
		
		if (!"1".equals(itemChg.getStatus().trim())) { // 1.已申请
			return "增减单未处于【已申请】状态,无法进行此操作!";
		}
		
		return "";
	}
	/**
	 * 跳转到itemchg材料替换页面
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
	 * 材料增减管理从模板导入
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
	 * 主材增减重新生成
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
	 * 主材套餐包生成
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
		logger.debug("查看开关数量");
		
		if(StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		
		return new ModelAndView("admin/project/itemChg/itemChg_viewSwitch")
			.addObject("customer",customer);
	}
	
	/**
	 * 取消增减单
	 */
	@RequestMapping("/cancel")
	public void cancelItemChg(HttpServletRequest request, HttpServletResponse response, ItemChg itemChg) {
	    
	    try {
            
	        if (StringUtils.isBlank(itemChg.getNo())) {
	            throw new IllegalStateException("取消失败：缺少增减单号!");
	        }
	        
	        itemChg = itemChgService.get(ItemChg.class, itemChg.getNo());
	        
	        if (itemChg == null) {
                throw new IllegalStateException("取消失败：此增减单不存在！");
            }
	        
	        if (!"1".equals(itemChg.getStatus().trim())) {
                throw new IllegalStateException("取消失败：增减单非申请状态，不能取消！");
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
