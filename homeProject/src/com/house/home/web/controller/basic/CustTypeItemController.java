package com.house.home.web.controller.basic;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.CustTypeItemBean;
import com.house.home.bean.insales.ItemBalAdjDetailBean;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.CustTypeItem;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemTypeConfirm;
import com.house.home.entity.basic.Uom;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustLog;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.service.basic.CustTypeItemService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.basic.XtdmService;

@Controller
@RequestMapping("/admin/custTypeItem")
public class CustTypeItemController  extends BaseController {

	@Autowired
	private CustTypeItemService custTypeItemService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private XtdmService xtdmService;
	
	/**
	 * ????????????????????????list
	 * @param request
	 * @param response
	 * @param custTypeItem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody 
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustTypeItem custTypeItem) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTypeItem.setItemRight(this.getUserContext(request).getItemRight());
		custTypeItemService.findPageBySql(page,custTypeItem);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody 
	public WebPage<Map<String,Object>> goItemJqGrid(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTypeItemService.findItemPageBySql(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String costRight=this.getUserContext(request).getCostRight();
		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_list")
		.addObject("custRight", costRight);
	}
	/**
	 * ????????????????????????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_save");
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBatchAddNoExcel")
	public ModelAndView goBatchAddNoExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_batchAddNoExcel");
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBatchAdd")
	public ModelAndView goBatchAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_batchAdd");
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,Integer id) throws Exception {
		Item item =null;
		CustTypeItem custTypeItem=null;
		custTypeItem=custTypeItemService.get(CustTypeItem.class, id);
		item=itemService.get(Item.class, custTypeItem.getItemCode());
		custTypeItem.setOldPrice(item.getPrice());
		custTypeItem.setOldProjectCost(item.getProjectCost());
		custTypeItem.setOldRemars(item.getRemark());
		custTypeItem.setCustType(custTypeItem.getCustType().trim());
		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_update")
		.addObject("custTypeItem",custTypeItem)
		.addObject("item",item);
	}
	/**
	 * ??????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response,Integer id) throws Exception {
		Item item =null;
		CustTypeItem custTypeItem=null;
		custTypeItem=custTypeItemService.get(CustTypeItem.class, id);
		item=itemService.get(Item.class, custTypeItem.getItemCode());
		custTypeItem.setOldPrice(item.getPrice());
		custTypeItem.setOldProjectCost(item.getProjectCost());
		custTypeItem.setOldRemars(item.getRemark());
		custTypeItem.setCustType(custTypeItem.getCustType().trim());
		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_copy")
		.addObject("custTypeItem",custTypeItem)
		.addObject("item",item);
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,Integer id) throws Exception {
		
		CustTypeItem custTypeItem=null;
		Employee employee=null;
		Item item=null;
		custTypeItem=custTypeItemService.get(CustTypeItem.class, id);
		employee=employeeService.get(Employee.class, custTypeItem.getLastUpdatedBy());
		custTypeItem.setLastUpdatedByDescr(employee==null?"":employee.getNameChi());
		item=itemService.get(Item.class, custTypeItem.getItemCode());
		custTypeItem.setOldPrice(item.getPrice());
		custTypeItem.setOldProjectCost(item.getProjectCost());
		custTypeItem.setOldRemars(item.getRemark());
		
		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_view")
		.addObject("custTypeItem",custTypeItem)
		.addObject("item",item);
		
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request ,
			HttpServletResponse response , CustTypeItem custTypeItem){
		logger.debug("????????????????????????????????????");
		try{
			if(custTypeItemService.hasExist(custTypeItem.getItemCode(), custTypeItem.getCustType(), custTypeItem.getRemark())){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????????????????");
				return ;
			}
			
			if(custTypeItemService.hasExistsICP(custTypeItem.getItemCode(), custTypeItem.getCustType(), custTypeItem.getPrice())){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????????????????????????????????????????");
				return ;
			}
			if(StringUtils.isBlank(custTypeItem.getFixAreaType())){
				custTypeItem.setFixAreaType("1");
			}
			custTypeItem.setLastUpdate(new Date());
			custTypeItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custTypeItem.setExpired("F");
			custTypeItem.setActionLog("ADD");
			
			this.custTypeItemService.doSaveCustTypeItem(custTypeItem);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	/**
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/goItemSelect")
	public ModelAndView goItemSelect(HttpServletRequest request, 
			HttpServletResponse response, Item item){
		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_select")
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
//		if("00".equals(item.getSqlCode())){
//			return null;
//		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTypeItemService.getItemBySqlCode(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request ,
			HttpServletResponse response , CustTypeItem custTypeItem){
		logger.debug("????????????????????????????????????");
		try{
			CustTypeItem cti=null;
			if("F".equals(custTypeItem.getExpired())){
				if(custTypeItemService.hasExists(custTypeItem.getItemCode(), custTypeItem.getCustType(), custTypeItem.getRemark(),custTypeItem.getPk())){
					ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????????????????");
					return ;
				}
				if(custTypeItemService.hasExistsICP(custTypeItem.getItemCode(), custTypeItem.getCustType(), custTypeItem.getPrice(),custTypeItem.getPk())){
					ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????????????????");
					return ;
				}
			}
			cti=custTypeItemService.get(CustTypeItem.class, custTypeItem.getPk());
			if(custTypeItem.getExpired()==null){
				custTypeItem.setExpired(cti.getExpired());
			}
			
			custTypeItem.setLastUpdate(new Date());
			custTypeItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custTypeItem.setActionLog("Edit");
			
			this.custTypeItemService.update(custTypeItem);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	/**
	 * excel????????????
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
		ExcelImportUtils<CustTypeItemBean> eUtils=new ExcelImportUtils<CustTypeItemBean>();
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
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("?????????");
		titleList.add("???????????????");
		titleList.add("????????????????????????");
		titleList.add("??????????????????");
		titleList.add("??????");
		try {
			List<CustTypeItemBean> result=eUtils.importExcel(in,CustTypeItemBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(CustTypeItemBean custTypeItemBean:result){
				Item item=new Item();
				Map<String,Object> data=new HashMap<String, Object>();
				String custTypeDescr=""; 
				custTypeDescr=custTypeItemService.getCustTypeDescr(custTypeItemBean.getCustType());
				if("".equals(custTypeDescr)){
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "???????????????????????????");
				}
				String discAmtCalcTypeDescr = "";
				discAmtCalcTypeDescr = custTypeItemService.getDiscAmtCalcTypeDescr(custTypeItemBean.getDiscAmtCalcType());
				if("".equals(discAmtCalcTypeDescr)){
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "???????????????????????????????????????");
				}
				if(StringUtils.isNotBlank(custTypeItemBean.getFixAreaType())){
					Xtdm xtdmFixArea=xtdmService.getByIdAndCbm("SetItemFixArea", custTypeItemBean.getFixAreaType());
					if(xtdmFixArea == null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "?????????????????????????????????");
					}else{
						data.put("fixareatype", custTypeItemBean.getFixAreaType());
						data.put("fixareatypedescr", xtdmFixArea.getNote());
					}
				}else{
					data.put("fixareatype", "1");
				}
				data.put("lastupdatedby", uc.getCzybh());
				data.put("remark", custTypeItemBean.getRemark());
				data.put("custtype", custTypeItemBean.getCustType());
				data.put("custtypedescr",custTypeDescr);
				data.put("discamtcalctype", custTypeItemBean.getDiscAmtCalcType());
				data.put("discamtcalctypedescr", discAmtCalcTypeDescr);
				data.put("expired", "F");
				data.put("actionlog", "ADD");
				data.put("price", custTypeItemBean.getPrice());
				data.put("projectcost", custTypeItemBean.getProjectCost());
				item=itemService.get(Item.class, custTypeItemBean.getItemCode());
				if(item!=null){
					data.put("itemcode",custTypeItemBean.getItemCode());
					data.put("isinvalid","1");
					data.put("itemdescr", item.getDescr());
					data.put("isinvaliddescr", "??????");
				}else{
					data.put("itemcode",custTypeItemBean.getItemCode());
					data.put("isinvalid","0");
					data.put("itemdescr", "??????????????????");
					data.put("isinvaliddescr", "???????????????????????????");
				}
				if(custTypeItemService.hasExist(custTypeItemBean.getItemCode(),custTypeItemBean.getCustType(),
						custTypeItemBean.getRemark())){
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????????????????");
				}
				if(custTypeItemService.hasExistsICP(custTypeItemBean.getItemCode(),custTypeItemBean.getCustType(),
						Double.parseDouble(custTypeItemBean.getPrice()))){
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????????????????");
				}
				if(!"??????".equals(custTypeItemBean.getItemCode())){//?????????????????????
					datas.add(data);
				}
				for(int i=0;i<datas.size()-1;i++){
					if(datas.get(i).get("itemcode").equals(data.get("itemcode"))
							&&datas.get(i).get("custtype").equals(data.get("custtype"))
							&&datas.get(i).get("remark").equals(data.get("remark"))){
						datas.get(i).put("isinvalid", "0");
						datas.get(i).put("isinvaliddescr", "???????????????????????????????????????????????????");
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "???????????????????????????????????????????????????");
					}
					
				}
			}
			map.put("success", true);
			map.put("returnInfo", "??????????????????");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
			map.put("hasInvalid", true);
			return map;
		}
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSaveBatch")
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,CustTypeItem custTypeItem){
		logger.debug("??????????????????");
		try {
			
			String detailJson = request.getParameter("detailJson");
			custTypeItem.setDetailJson(detailJson);
			
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????");
				return;
			}
			Result result = this.custTypeItemService.doSaveBatch(custTypeItem);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	@RequestMapping("/doSaveBatchAddNoExcelForProc")
	public void doSaveBatchAddNoExcelForProc(HttpServletRequest request,HttpServletResponse response,CustTypeItem custTypeItem){
		logger.debug("??????????????????");
		try {
			custTypeItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			if(StringUtils.isBlank(custTypeItem.getFixAreaType())){
				custTypeItem.setFixAreaType("1");
			}
			Result result = custTypeItemService.doSaveBatchAddNoExcelForProc(custTypeItem);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,"???????????????"+result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	/**
	 * ??????excel
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CustTypeItem custTypeItem){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		custTypeItem.setItemRight(this.getUserContext(request).getItemRight());
		custTypeItemService.findPageBySql(page,custTypeItem);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"?????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_code")
			.addObject("item", item);
	}
	/**
	 * ??????id??????????????????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCustTypeItem")
	@ResponseBody
	public JSONObject getCustTypeItem(HttpServletRequest request,HttpServletResponse response,Item item){
		if(StringUtils.isEmpty(item.getId())){
			return this.out("?????????id??????", false);
		}
		item.setPk(item.getId());
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTypeItemService.findItemPageBySql(page, item);
		if(page.getTotalCount()<1){
			return this.out("??????????????????code="+item.getPk()+"?????????????????????", false);
		}
		return this.out(page.getResult().get(0), true);
	}
	
	@RequestMapping("/checkFixAreaType")
	@ResponseBody
	public boolean checkFixAreaType(HttpServletRequest request,HttpServletResponse response,CustTypeItem custTypeItem){
		
		return custTypeItemService.checkFixAreaType(custTypeItem);
		
	}
	
	@RequestMapping("/goBatchDeal")
	public ModelAndView goBatchDeal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String costRight=this.getUserContext(request).getCostRight();
		return new ModelAndView("admin/basic/custTypeItem/custTypeItem_batchDeal")
		.addObject("custRight", costRight);
	}
	
	@RequestMapping("/doBatchDeal")
	public void doBatchDeal(HttpServletRequest request,HttpServletResponse response,CustTypeItem custTypeItem){
		logger.debug("??????????????????");
		try {
			if(StringUtils.isNotBlank(custTypeItem.getPks())){
				
				custTypeItemService.doBatchDeal(custTypeItem);
				ServletUtils.outPrintSuccess(request, response,"????????????");
			} else {
				
				ServletUtils.outPrintFail(request, response, "????????????????????????");
				return;
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
}
