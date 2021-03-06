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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.bean.insales.ItemAppImportExcelBean;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Item;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.SpecItemReq;
import com.house.home.entity.project.SpecItemReqDt;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.project.SpecItemReqService;
@Controller
@RequestMapping("/admin/specItemReq")
public class SpecItemReqController extends BaseController{
	@Autowired
	private  SpecItemReqService specItemReqService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CzybmService czybmService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,SpecItemReq specItemReq) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		specItemReqService.findPageBySql(page, specItemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	
	// ??????
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,String custCode, String iscupboard) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		specItemReqService.findDetailPageBySql(page, custCode, iscupboard);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		return new ModelAndView("admin/project/specItemReq/specItemReq_list");
	}
	
	// ????????????
	@RequestMapping("/goCupSpec")
	public ModelAndView goCupSpec(HttpServletRequest request,
			HttpServletResponse response, SpecItemReq specItemReq) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		SpecItemReq specItemReqSource = this.specItemReqService.get(SpecItemReq.class, specItemReq.getCustCode());
		if (null != specItemReqSource) {
			specItemReq.setHasStove(specItemReqSource.getHasStove());
			specItemReq.setIsSelfMetal_Cup(specItemReqSource.getIsSelfMetal_Cup());
			specItemReq.setIsSelfMetal_Int(specItemReqSource.getIsSelfMetal_Int());
			specItemReq.setIsSelfSink(specItemReqSource.getIsSelfSink());
		}
		return new ModelAndView("admin/project/specItemReq/specItemReq_cupSpec")
			.addObject("specItemReq", specItemReq)
			.addObject("czybm", czybm);
	}
	
	// ????????????
	@RequestMapping("/goIntSpec")
	public ModelAndView goIntSpec(HttpServletRequest request,
			HttpServletResponse response, SpecItemReq specItemReq) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		SpecItemReq specItemReqSource = this.specItemReqService.get(SpecItemReq.class, specItemReq.getCustCode());
		if (null != specItemReqSource) {
			specItemReq.setHasStove(specItemReqSource.getHasStove());
			specItemReq.setIsSelfMetal_Cup(specItemReqSource.getIsSelfMetal_Cup());
			specItemReq.setIsSelfMetal_Int(specItemReqSource.getIsSelfMetal_Int());
			specItemReq.setIsSelfSink(specItemReqSource.getIsSelfSink());
		}
		return new ModelAndView("admin/project/specItemReq/specItemReq_intSpec")
			.addObject("specItemReq", specItemReq)
			.addObject("czybm", czybm);
	}
	
	// ??????
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, SpecItemReq specItemReq) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		SpecItemReq specItemReqSource = this.specItemReqService.get(SpecItemReq.class, specItemReq.getCustCode());
		if (null != specItemReqSource) {
			specItemReq.setHasStove(specItemReqSource.getHasStove());
			specItemReq.setIsSelfMetal_Cup(specItemReqSource.getIsSelfMetal_Cup());
			specItemReq.setIsSelfMetal_Int(specItemReqSource.getIsSelfMetal_Int());
			specItemReq.setIsSelfSink(specItemReqSource.getIsSelfSink());
		}
		return new ModelAndView("admin/project/specItemReq/specItemReq_view")
			.addObject("specItemReq", specItemReq)
			.addObject("czybm", czybm);
	}
	
	//??????Excel
	@RequestMapping("/goImportExcel")
	public ModelAndView goImportExcel(HttpServletRequest request,
			HttpServletResponse response, String isCupboard, String keys, String custCode) throws Exception {
	
		return new ModelAndView("admin/project/specItemReq/specItemReq_importExcel")
			.addObject("isCupboard", isCupboard)
			.addObject("itemCodes", keys)
			.addObject("custCode", custCode);
	}
	//????????????
	@RequestMapping("/goDetailSave")
	public ModelAndView goDetailSave(HttpServletRequest request,
			HttpServletResponse response, String keys, SpecItemReqDt specItemReqDt, String m_umState) throws Exception {
	
		return new ModelAndView("admin/project/specItemReq/specItemReq_detailSave")
			.addObject("specItemReqDt", specItemReqDt)
			.addObject("itemCodes", keys);
	}
	
	//????????????
	@RequestMapping("/goQuicklySave")
	public ModelAndView goQuicklySave(HttpServletRequest request, HttpServletResponse response,String itemType1,String itemType2,String isSetItem,String custCode,String appNo,String itemCodes){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("itemType1", itemType1);
		map.put("itemType2", itemType2);
		map.put("isSetItem", isSetItem);
		map.put("custCode", custCode);
		map.put("appNo", appNo);
		map.put("itemCodes", itemCodes);
		map.put("itemRight", getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/project/specItemReq/specItemReq_quicklySave").addObject("data", map);
	}
	
	//???????????????
	@RequestMapping("/doSave")
	@ResponseBody
	public void doSave(HttpServletRequest request,HttpServletResponse response,SpecItemReq specItemReq){
		logger.debug("????????????");
		try {
			specItemReq.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			/*???????????????????????????*/
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "??????????????????");
				return;
			}
			specItemReq.setDetailJson(detailJson);
			// ??????????????????
			Result result = this.specItemReqService.doSave(specItemReq);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SpecItemReq specItemReq){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		specItemReqService.findPageBySql(page, specItemReq);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"??????????????????-"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	// ??????Excel??????
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<ItemAppImportExcelBean> eUtils=new ExcelImportUtils<ItemAppImportExcelBean>();// ??????EXCEL????????????, ????????????pojo ??????
		String isCupboard="";
		String custCode="";
		String[] itemCodeArr = null;// ??????????????????????????????
		DiskFileItemFactory fac = new DiskFileItemFactory();// ??????FileItem ???????????????????????????
		ServletFileUpload upload = new ServletFileUpload(fac);// ???????????????
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);// ????????????????????????
		Iterator it = fileList.iterator();// ?????????
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// ???????????????????????????
			String fieldName = obit.getFieldName();
			String fieldValue = obit.getString();
			if ("isCupboard".equals(fieldName)){
				isCupboard = fieldValue;
			}
			if ("itemCodes".equals(fieldName)){
				String itemCodes = fieldValue.trim();
				itemCodeArr = itemCodes.split(",");
			}
			if ("custCode".equals(fieldName)){
				custCode = fieldValue.trim();
			}
			if ("file".equals(fieldName)){
				in=obit.getInputStream();
			}
		}
		// ?????????????????????
		titleList.add("????????????");
		titleList.add("??????");
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("??????");
		
		try {
			List<ItemAppImportExcelBean> result=eUtils.importExcel(in, ItemAppImportExcelBean.class,titleList);// ??????????????????????????????null????????????
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			int i=0;
			for(ItemAppImportExcelBean itemAppImportExcelBean:result){
				i++;
				if(StringUtils.isNotBlank(itemAppImportExcelBean.getError())){
					map.put("success", false);
					map.put("returnInfo", itemAppImportExcelBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				// ???????????????map???
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid", "0");// ??????
				
				// ????????????????????????????????????
				Item item = new Item();
				//????????????????????????pk
				Map<String,Object> intprod=specItemReqService.getIntProd(custCode,itemAppImportExcelBean.getProductName());
				
				if (StringUtils.isNotBlank(itemAppImportExcelBean.getItemCode())) {
					item = itemService.get(Item.class, itemAppImportExcelBean.getItemCode().trim());
					if(null==item) {
						data.put("isinvalid", "1");
						data.put("isinvaliddescr", "??????,????????????????????????");
					}else{
						if (!"JC".equals(item.getItemType1().trim())) {
							data.put("isinvalid", "1");
							data.put("isinvaliddescr", "??????,???????????????");
						} else {
							if (null != intprod) {
								// ???????????????????????????????????????????????????????????????
								if (StringUtils.isNotBlank(isCupboard)) {
									if ("1".equals(intprod.get("IsCupboard"))) {
										data.put("iscupboard", "1");
										data.put("iscupboarddescr", "???");
										data.put("intprodpk", intprod.get("PK"));
										data.put("intprodpkdescr", itemAppImportExcelBean.getProductName());
									} else {
										data.put("isinvalid", "1");
										data.put("isinvaliddescr", "??????,???????????????????????????");
									}
								} else {
									if ("0".equals(intprod.get("IsCupboard"))) {
										data.put("iscupboard", "0");
										data.put("iscupboarddescr", "???");
										data.put("intprodpk", intprod.get("PK"));
										data.put("intprodpkdescr", itemAppImportExcelBean.getProductName());
									} else {
										data.put("isinvalid", "1");
										data.put("isinvaliddescr", "??????,???????????????????????????");
									}
								}
							} else {
								data.put("isinvalid", "1");
								data.put("isinvaliddescr", "??????,????????????????????????");
							}
						}
					}
				} else {
					data.put("isinvalid", "1");
					data.put("isinvaliddescr", "??????,??????????????????");
				}
				
				// ???hasInvalid???1???????????????????????????
				if("1".equals(data.get("isinvalid"))){
					map.put("hasInvalid", true);
				}else{
					data.put("isinvaliddescr", "??????");
					// ?????????????????????
					/*Map<String,Object> appQty = specItemReqService.getAppQty(custCode,itemAppImportExcelBean.getItemCode());
					if (null == appQty) {
						data.put("appqty", 0);
					} else {
						data.put("isexist", appQty.get("isExist"));
						data.put("appqty", appQty.get("AppQty"));
						Double AppQty = new Double(appQty.get("AppQty").toString());
						if (0 != AppQty&& AppQty > itemAppImportExcelBean.getQty()) {
							data.put("isinvaliddescr", "????????????????????????????????????????????????????????????");
						}
					}*/
					//?????????????????????pk???????????????????????????????????????????????????????????????0
					data.put("appqty", 0);
					
					//???????????????????????????
					/*for(int j = 0;itemCodeArr != null && j < itemCodeArr.length;j++){
						if(itemCodeArr[j].equals(itemAppImportExcelBean.getItemCode())){
							data.put("isinvalid", "1");
							data.put("isinvaliddescr", "??????,???????????????");
						}
					}*/
					
					data.put("itemtype1", item.getItemType1());
					data.put("itemcode", itemAppImportExcelBean.getItemCode());
					data.put("itemdescr", item.getDescr());
					data.put("qty", itemAppImportExcelBean.getQty()==null?0:itemAppImportExcelBean.getQty());
					data.put("movecost", item.getAvgCost()==null?0:item.getAvgCost());
					data.put("price", item.getPrice()==null?0:item.getPrice());
					data.put("cost", item.getCost()==null?0:item.getCost());
					data.put("totalcost", (itemAppImportExcelBean.getQty()==null?0:itemAppImportExcelBean.getQty())
							* (item.getCost()==null?0:item.getCost()));
					data.put("remark", itemAppImportExcelBean.getProductName()+
							(itemAppImportExcelBean.getItem()==null?"":"???"+itemAppImportExcelBean.getItem())+
							(itemAppImportExcelBean.getRemarks()==null?"":"???"+itemAppImportExcelBean.getRemarks()));
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
	
	//?????????????????????
	@RequestMapping("/getAppQty")
	@ResponseBody
	public Map<String, Object> getAppQty(HttpServletRequest request,
			HttpServletResponse response, String custCode, String itemCode) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> appQty = specItemReqService.getAppQty(custCode,itemCode);
		if (null == appQty) {
			map.put("AppQty", "0");
			map.put("isExist", null);
		} else {
			map = appQty;
		}
		return map;
	}
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request,
			HttpServletResponse response, SpecItemReq specItemReq) throws Exception {
		return new ModelAndView("admin/project/specItemReq/specItemReq_detailQuery")
			.addObject("specItemReq", specItemReq)
			.addObject("costRight", getUserContext(request).getCostRight());
	}
	@RequestMapping("/goDetailQueryJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goDetailQueryJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,SpecItemReq specItemReq) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		specItemReqService.goDetailQuery(page,specItemReq );
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDetailQueryExcel")
	public void doDetailQueryExcel(HttpServletRequest request,HttpServletResponse response ,SpecItemReq specItemReq){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		specItemReqService.goDetailQuery(page, specItemReq);;
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/goSetStack")
	public ModelAndView goSetStack(HttpServletRequest request,
			HttpServletResponse response, SpecItemReq specItemReq) throws Exception {
		Customer customer =specItemReqService.get(Customer.class, specItemReq.getCustCode());
		Map<String, Object> map=specItemReqService.getStakeholderInfo(specItemReq.getCustCode());
		return new ModelAndView("admin/project/specItemReq/specItemReq_setStack")
			.addObject("map", map).addObject("customer", customer)
			.addObject("costRight", getUserContext(request).getCostRight());
	}
}
