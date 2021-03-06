package com.house.home.web.controller.insales;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
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
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.insales.ItemBalAdjDetailBean;
import com.house.home.bean.project.ItemChgDetailBean;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.Uom;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemBalAdjHeader;
import com.house.home.entity.insales.ItemReq;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.insales.ItemBalAdjDetailService;
import com.house.home.service.insales.ItemBalAdjHeaderService;
import com.house.home.service.insales.WareHouseService;

@Controller      
@RequestMapping("/admin/itemBalAdjHeader")
public class ItemBalAdjHeaderController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ItemBalAdjHeaderController.class);
	
	@Autowired
	private ItemBalAdjHeaderService itemBalAdjHeaderService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemBalAdjDetailService itemBalAdjDetailService;
	
	
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request, HttpServletResponse response,ItemBalAdjHeader itemBalAdjHeader) throws Exception {
		UserContext uc=getUserContext(request);
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemBalAdjHeaderService.findPageBySql(page, itemBalAdjHeader, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * ??????id??????ItemBalAdjHeader????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getItemBalAdjHeader")
	@ResponseBody
	public JSONObject getItemBalAdjHeader(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("?????????id??????", false);
		}
		ItemBalAdjHeader itemBalAdjHeader= itemBalAdjHeaderService.get(ItemBalAdjHeader.class, id);
		if(itemBalAdjHeader == null){
			return this.out("??????????????????No="+id+"???????????????", false);
		}
		return this.out(itemBalAdjHeader, true);
	}
	
	
	/**
	 * ItemBalAdjHeader??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response
			,ItemBalAdjHeader itemBalAdjHeader) throws Exception {
		itemBalAdjHeader.setStatus("1,2,3");

		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_list")
		.addObject("ItemBalAdjHeader",itemBalAdjHeader);
	}
	
	
	/**
	 * ????????????????????????-???????????????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,ItemBalAdjHeader itemBalAdjHeader){
		logger.debug("?????????????????????????????????");
		itemBalAdjHeader.setAdjType("1");
		itemBalAdjHeader.setAdjReason("1");
		itemBalAdjHeader.setStatus("1");
		itemBalAdjHeader.setAppDate(DateUtil.getToday());
		itemBalAdjHeader.setAppEmp(this.getUserContext(request).getCzybh());
		Employee employee = employeeService.get(Employee.class, itemBalAdjHeader.getAppEmp());
		itemBalAdjHeader.setAppEmpDescr(employee==null?"":employee.getNameChi());
		itemBalAdjHeader.setConfirmDate(null);
		itemBalAdjHeader.setConfirmEmp(null);
		itemBalAdjHeader.setDate(DateUtil.getToday());
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_save")
		.addObject("itemBalAdjHeader", itemBalAdjHeader)
		.addObject("czybh",this.getUserContext(request).getCzybh());
	}
	
	
	/**
	 * ??????????????????-??????
	 */
	@RequestMapping("/goSaveAdd")
	public ModelAndView goAdd(HttpServletRequest request,HttpServletResponse response, ItemBalAdjHeader itemBalAdjHeader){
		logger.debug("???????????????????????????-??????"); 
		ItemBalAdjDetail itemBalAdjDetail=new ItemBalAdjDetail();
		
		itemBalAdjDetail.setWhCode(itemBalAdjHeader.getWhCode());
		itemBalAdjDetail.setLastUpdate(new Date());
		itemBalAdjDetail.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		itemBalAdjDetail.setAdjType(itemBalAdjHeader.getAdjType());
		itemBalAdjDetail.setAllQty(0.0);
		itemBalAdjDetail.setCost(0.0);
		itemBalAdjDetail.setAllItCode(itemBalAdjHeader.getAllItCode());
		itemBalAdjDetail.setNoRepeat(itemBalAdjHeader.getNoRepeat());
		
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_save_add").addObject("itemBalAdjDetail",itemBalAdjDetail);
	}

	/**
	 *ajax???????????? 
	 */
	@RequestMapping("/getAjaxDetail")
	@ResponseBody
	public ItemBalAdjDetail getAjaxDetail(HttpServletRequest request,HttpServletResponse response, ItemBalAdjDetail itemBalAdjDetail){
		logger.debug("ajax????????????");  

		//?????????????????? 
		Map<String , Object> map = itemBalAdjDetailService.getAvgCost(itemBalAdjDetail.getWhCode(), itemBalAdjDetail.getItCode());
		if(map!=null){
			itemBalAdjDetail.setAllQty((Double)map.get("QtyCal"));
			itemBalAdjDetail.setCost((Double)map.get("AvgCost"));
		}else{
			itemBalAdjDetail.setAllQty(0.0);
			itemBalAdjDetail.setCost(0.0);
		}
		
		return itemBalAdjDetail;
	}
	@RequestMapping("/getAjaxNoPosi")
	@ResponseBody
	public ItemBalAdjDetail getAjaxNoPosi(HttpServletRequest request,HttpServletResponse response ,
			ItemBalAdjDetail itemBalAdjDetail ){
		
		double allQty=0.0;
		if(itemBalAdjDetail.getWhCode()!=null&&itemBalAdjDetail.getItCode()!=null){
			Map<String , Object> map= itemBalAdjDetailService.getPosiQty(itemBalAdjDetail.getWhCode(),itemBalAdjDetail.getItCode());
			Map<String , Object> map1= itemBalAdjDetailService.getAllQty(itemBalAdjDetail.getWhCode(),itemBalAdjDetail.getItCode());
			double posiQty= (Double) map.get("QtyCal");
			itemBalAdjDetail.setPosiQty(posiQty);	
			if(map1!=null){
				 allQty= (Double) map1.get("QtyCal");
				 itemBalAdjDetail.setNoPosiQty(allQty-posiQty);
			}else{
				 itemBalAdjDetail.setNoPosiQty(0);
			}
		}	
		return itemBalAdjDetail;
	}
	
	
	/**
	 * ??????????????????-????????????
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 **/
	@RequestMapping("/goSaveQuickAdd")
	public ModelAndView goQuickAdd(HttpServletRequest request,HttpServletResponse response, ItemBalAdjDetail itemBalAdjDetail){
		logger.debug("???????????????????????????-????????????");
		
		itemBalAdjDetail.setLastUpdate(new Date());
		itemBalAdjDetail.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		//itemBalAdjDetail.setAdjCost(0.0);
		itemBalAdjDetail.setAllQty(0.0);
		itemBalAdjDetail.setCost(0.0);
		

		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_save_quickAdd")
		.addObject("itemBalAdjDetail",itemBalAdjDetail);
	}
	
	/**
	 *??????????????????-??????
	 * 
	 **/
	@RequestMapping("/goSaveUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request,HttpServletResponse response, ItemBalAdjDetail itemBalAdjDetail){
		logger.debug("???????????????????????????-??????");
		
		itemBalAdjDetail.setLastUpdate(new Date());
		itemBalAdjDetail.setLastUpdatedBy(this.getUserContext(request).getCzybh());
 
		Item item = itemService.get(Item.class, itemBalAdjDetail.getItCode());
		itemBalAdjDetail.setItDescr(item==null?"":item.getDescr());
		itemBalAdjDetail.setRetItCode(itemBalAdjDetail.getItCode());
		itemBalAdjDetail.setUom1(itemBalAdjDetail.getUom());
		itemBalAdjDetail.setUom2(itemBalAdjDetail.getUom());
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_save_update")
		.addObject("itemBalAdjDetail",itemBalAdjDetail)
		.addObject("czybh",this.getUserContext(request).getCzybh());
	}
	
	
	/**
	 * ??????????????????-??????
	 *
	 **/
	@RequestMapping("/goSaveView")
	public ModelAndView goAddView(HttpServletRequest request, HttpServletResponse response, ItemBalAdjDetail itemBalAdjDetail){
		logger.debug("???????????????????????????-??????");
				
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_save_view").addObject("itemBalAdjDetail",itemBalAdjDetail);
	}
	
	
	/**
	 * ??????????????????-?????? 
	 *
	 */
	@RequestMapping("/doItemBalAdjHeaderSave")
	public void doItemBalAdjHeaderSave(HttpServletRequest request,HttpServletResponse response,ItemBalAdjHeader itemBalAdjHeader){
		logger.debug("????????????????????????");
		try {
			String detailJson = request.getParameter("detailJson");
			itemBalAdjHeader.setDetailJson(detailJson);
			itemBalAdjHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemBalAdjHeader.setLastUpdate(new Date());

			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
				return;
			}
			Result result = this.itemBalAdjHeaderService.doItemBalAdjHeaderSave(itemBalAdjHeader);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????????????????-?????????????????????
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response,ItemBalAdjHeader itemBalAdjHeader){
		logger.debug("?????????????????????????????????");
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_detailQuery").addObject("itemBalAdjHeader", itemBalAdjHeader);
	}
	/**
	 * ????????????????????????-???????????????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("?????????????????????????????????");
		ItemBalAdjHeader itemBalAdjHeader = null;
		if (StringUtils.isNotBlank(id)){
			itemBalAdjHeader = itemBalAdjHeaderService.get(ItemBalAdjHeader.class, id);
		}else{
			itemBalAdjHeader = new ItemBalAdjHeader();
		}

		Employee employee = employeeService.get(Employee.class, itemBalAdjHeader.getAppEmp());
		itemBalAdjHeader.setAppEmpDescr(employee==null?"":employee.getNameChi());
		
		WareHouse warehouse = wareHouseService.get(WareHouse.class, itemBalAdjHeader.getWhCode());
		itemBalAdjHeader.setWhDescr(warehouse==null?"":warehouse.getDesc1());
		
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_update").addObject("itemBalAdjHeader", itemBalAdjHeader);
	}
	
	
	/**
	 * ??????????????????-?????? 
	 *
	 */
	@RequestMapping("/doItemBalAdjHeaderUpdate")
	public void doItemBalAdjHeaderUpdate(HttpServletRequest request,HttpServletResponse response,ItemBalAdjHeader itemBalAdjHeader){
		logger.debug("????????????????????????");
		try {
			String detailJson = request.getParameter("detailJson");
			itemBalAdjHeader.setDetailJson(detailJson);
			itemBalAdjHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemBalAdjHeader.setLastUpdate(new Date());

			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
				return;
			}
			Result result = this.itemBalAdjHeaderService.doItemBalAdjHeaderUpdate(itemBalAdjHeader);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"????????????");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	
	/**
	 * ????????????????????????-???????????????
	 * @return
	 */
	@RequestMapping("/goVerify")
	public ModelAndView goVerify(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("?????????????????????????????????");
		ItemBalAdjHeader itemBalAdjHeader = null;
		if (StringUtils.isNotBlank(id)){
			itemBalAdjHeader = itemBalAdjHeaderService.get(ItemBalAdjHeader.class, id);
		}else{
			itemBalAdjHeader = new ItemBalAdjHeader();
		}
		
		itemBalAdjHeader.setConfirmDate(DateUtil.getToday());
		itemBalAdjHeader.setConfirmEmp(this.getUserContext(request).getCzybh());
		Employee employee = employeeService.get(Employee.class, itemBalAdjHeader.getConfirmEmp());
		itemBalAdjHeader.setConfirmEmp(employee==null?"":employee.getNameChi());
		employee = employeeService.get(Employee.class, itemBalAdjHeader.getAppEmp());
		itemBalAdjHeader.setAppEmpDescr(employee==null?"":employee.getNameChi());
		
		WareHouse warehouse = wareHouseService.get(WareHouse.class, itemBalAdjHeader.getWhCode());
		itemBalAdjHeader.setWhDescr(warehouse==null?"":warehouse.getDesc1());
		
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_verify").addObject("itemBalAdjHeader", itemBalAdjHeader);
	}
	
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping("/doVerify")
	public void doVerify(HttpServletRequest request,HttpServletResponse response,ItemBalAdjHeader itemBalAdjHeader){
		logger.debug("??????????????????????????????");
		try {
			itemBalAdjHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemBalAdjHeader.setLastUpdate(new Date());

			Result result= itemBalAdjHeaderService.doItemBalAdjHeaderVerify(itemBalAdjHeader,"2");
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"??????????????????");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping("/doVerifyCancel")
	public void doVerifyCancel(HttpServletRequest request,HttpServletResponse response,ItemBalAdjHeader itemBalAdjHeader){
		logger.debug("??????????????????????????????");
		try {
			itemBalAdjHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemBalAdjHeader.setLastUpdate(new Date());

			Result result= itemBalAdjHeaderService.doItemBalAdjHeaderVerify(itemBalAdjHeader,"3");
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"??????????????????");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	
	/**
	 * ????????????????????????-???????????????
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, String id){ 
		logger.debug("?????????????????????????????????");
		ItemBalAdjHeader itemBalAdjHeader = itemBalAdjHeaderService.get(ItemBalAdjHeader.class, id);
		
		if(itemBalAdjHeader.getAppEmp()!=null){
			Employee employee = employeeService.get(Employee.class, itemBalAdjHeader.getAppEmp());
			itemBalAdjHeader.setAppEmpDescr(employee==null?"":employee.getNameChi());
			
		}
		if(itemBalAdjHeader.getConfirmEmp()!=null){
			Employee employee = employeeService.get(Employee.class, itemBalAdjHeader.getConfirmEmp());
			itemBalAdjHeader.setConfirmEmp(employee==null?"":employee.getNameChi());
		}
		
		WareHouse warehouse = wareHouseService.get(WareHouse.class, itemBalAdjHeader.getWhCode());
		itemBalAdjHeader.setWhDescr(warehouse==null?"":warehouse.getDesc1());
		
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_detail").addObject("itemBalAdjHeader", itemBalAdjHeader);
	}
	
	@RequestMapping("/goImportExcel")
	public ModelAndView goImportExcel(HttpServletRequest request,HttpServletResponse response,
			ItemBalAdjHeader itemBalAdjHeader){
		
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_importExcel").
				addObject("itemBalAdjHeader",itemBalAdjHeader);
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
		ExcelImportUtils<ItemBalAdjDetailBean> eUtils=new ExcelImportUtils<ItemBalAdjDetailBean>();
		DecimalFormat dff = new DecimalFormat("0.0000");
		String whCode="";
		String adjType ="";
		String noRepeat="";
		String allItCode="";
		int disable =0;
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
				if ("whCode".equals(fieldName)){
					whCode = fieldValue.trim();
				}
				if ("noRepeat".equals(fieldName)){
					noRepeat = fieldValue.trim();
				}
				if ("adjType".equals(fieldName)){
					adjType = fieldValue.trim();
				}
				if ("allItCode".equals(fieldName)){
					allItCode = fieldValue.trim();
				}
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList.add("????????????");
		//titleList.add("????????????");//4
		//titleList.add("??????");//3
		titleList.add("????????????");//???????????? adjCost
		//titleList.add("???????????????");//2
		titleList.add("????????????");//adjQty
		//titleList.add("???????????????");//1
		titleList.add("??????");
		String[] array=allItCode.split(",");
		try {
			List<ItemBalAdjDetailBean> result=eUtils.importExcel(in, ItemBalAdjDetailBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(ItemBalAdjDetailBean itemBalAdjDetailBean:result){
				Map<String,Object> data=new HashMap<String, Object>();
				BigDecimal   bcost   =   new   BigDecimal(itemBalAdjDetailBean.getAdjCost());  
				double   bdcost   =   bcost.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

				data.put("whCode", whCode);
				data.put("lastupdatedby", uc.getCzybh());
				data.put("adjqty",changeBigDecimal(Double.parseDouble(itemBalAdjDetailBean.getAdjQty())));
				data.put("cost",bdcost);
				data.put("remarks", itemBalAdjDetailBean.getRemarks());
				data.put("lastupdate",new Date());
				//??????????????????????????????????????????
				Map<String , Object> map1 = itemBalAdjDetailService.getAvgCost(whCode, itemBalAdjDetailBean.getItCode());
				if(map1!=null){
					try {
						DecimalFormat df = new DecimalFormat("0.0000");
						double qtyCal=(Double)map1.get("QtyCal");
						double AvgCost=(Double)map1.get("AvgCost");
						data.put("allqty", qtyCal);
						data.put("avgCost", AvgCost);
	
						if(adjType.equals("1")){
							data.put("aftqty",changeBigDecimal(qtyCal+Double.parseDouble(itemBalAdjDetailBean.getAdjQty())));
							double adjqty=Double.parseDouble(itemBalAdjDetailBean.getAdjQty()==null?"0.0":itemBalAdjDetailBean.getAdjQty());
							double cost=Double.parseDouble(itemBalAdjDetailBean.getAdjCost()==null?"0.0":itemBalAdjDetailBean.getAdjCost());
				
							BigDecimal   b   =   new   BigDecimal((((qtyCal*AvgCost)+(adjqty*cost))/(qtyCal+adjqty)));  
							double   f1   =   b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
							data.put("aftcost", f1);
//							data.put("aftcost", df.format((((qtyCal*AvgCost)+(adjqty*cost))/(qtyCal+adjqty))));
						}else{
							double adjqty=Double.parseDouble(itemBalAdjDetailBean.getAdjQty()==null?"0.0":itemBalAdjDetailBean.getAdjQty());
							data.put("aftqty", changeBigDecimal(qtyCal-Double.parseDouble(itemBalAdjDetailBean.getAdjQty()==null?"0.0":itemBalAdjDetailBean.getAdjQty())));
							if(qtyCal-adjqty<0){
								disable=1;
							}else{
								disable=0;
							}
							double cost=Double.parseDouble(itemBalAdjDetailBean.getAdjCost()==null?"0.0":itemBalAdjDetailBean.getAdjCost());
							if(qtyCal-adjqty!=0){
								data.put("aftcost", changeBigDecimal((((qtyCal*AvgCost)-(adjqty*cost))/(qtyCal-adjqty))));
							}else {
								data.put("aftcost", changeBigDecimal(AvgCost));
							}
						}
					} catch (Exception e) {
						//data.put("", "1");
						data.put("aftqty",0.0);
						data.put("aftcost",0.0);
						data.put("isinvalid", "1");
						data.put("isinvaliddescr", "???????????????????????????");
					}
				}else{
					data.put("aftcost",changeBigDecimal(itemBalAdjDetailBean.getAdjCost()==null?0.00:Double.parseDouble(itemBalAdjDetailBean.getAdjCost())));
					data.put("aftqty", changeBigDecimal(itemBalAdjDetailBean.getAdjQty()==null?0.00: Double.parseDouble(itemBalAdjDetailBean.getAdjQty())));
				}
				Item item=new Item();
				item=itemService.get(Item.class, itemBalAdjDetailBean.getItCode());
				if(item!=null){
					if(noRepeat.equals("0")||noRepeat.equals("")){
						for(int i=0;i<array.length;i++){
							if(array[i].equals(item.getCode())){
								data.put("isinvalid", "1");
								data.put("isinvaliddescr", "???????????????????????????");
							}else{
								if(disable==0){
									data.put("isinvalid", "0");
									data.put("isinvaliddescr", "??????");
								}else{
									data.put("isinvalid", "1");
									data.put("isinvaliddescr", "???????????????????????????????????????");
								}
							}
						}
					}else{
						data.put("isinvalid", "0");
						data.put("isinvaliddescr", "??????");
						data.put("itemcode",itemBalAdjDetailBean.getItCode());
					}
					data.put("itemcode", item.getCode()==null?itemBalAdjDetailBean.getItCode():item.getCode());
					data.put("itemdescr", item.getDescr());
					data.put("uom", item.getUom());
					Uom uom = itemBalAdjHeaderService.get(Uom.class, item.getUom());
					data.put("uomdescr",uom==null?"":uom.getDescr());
				}else{
					data.put("itemcode",itemBalAdjDetailBean.getItCode());
					data.put("isinvalid", "1");
					data.put("isinvaliddescr", "???????????????????????????");
				}
				if(!"??????".equals(itemBalAdjDetailBean.getItCode())){//?????????????????????
					datas.add(data);
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
			map.put("returnInfo", "?????????????????????????????????????????????????????????????????????????????????????????????");
			map.put("hasInvalid", true);
			return map;
		}
	}
	
	/**
	 * ??????????????????-??????
	 *
	 **/
	@RequestMapping("/goDetailView")
	public ModelAndView goDetailView(HttpServletRequest request, HttpServletResponse response, ItemBalAdjDetail itemBalAdjDetail){
		logger.debug("???????????????????????????-??????");
		double allQty=0.0;
		if(itemBalAdjDetail.getWhCode()!=null&&itemBalAdjDetail.getItCode()!=null){
			Map<String , Object> map= itemBalAdjDetailService.getPosiQty(itemBalAdjDetail.getWhCode(),itemBalAdjDetail.getItCode());
			Map<String , Object> map1= itemBalAdjDetailService.getAllQty(itemBalAdjDetail.getWhCode(),itemBalAdjDetail.getItCode());
			double posiQty= (Double) map.get("QtyCal");
			itemBalAdjDetail.setPosiQty(posiQty);	
			if(map1!=null){
				 allQty= (Double) map1.get("QtyCal");
				 itemBalAdjDetail.setNoPosiQty(allQty-posiQty);
			}else{
				 itemBalAdjDetail.setNoPosiQty(0);
			}
		}	
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_detail_view").addObject("itemBalAdjDetail",itemBalAdjDetail);
	}
	
	/**
	 * ??????????????????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, ItemBalAdjHeader itemBalAdjHeader){
		Page<Map<String,Object>> page = this.newPage(request);
		UserContext uc=getUserContext(request);
		page.setPageSize(-1);
		itemBalAdjHeaderService.findPageBySql(page, itemBalAdjHeader,uc);  
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
	/**
	 * ????????????????????????-???????????????
	 */
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request, HttpServletResponse response,String no ){
		logger.debug("?????????????????????????????????");
		
		ItemBalAdjHeader itemBalAdjHeader=null;
		itemBalAdjHeader = itemBalAdjHeaderService.get(ItemBalAdjHeader.class, no);
		
		return new ModelAndView("admin/insales/itemBalAdjHeader/itemBalAdjHeader_print").addObject("itemBalAdjHeader", itemBalAdjHeader);
	}
	
	public double changeBigDecimal(Double a){
		BigDecimal   b   =   new   BigDecimal(a);  
		double   f   =   b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
		return f;
	}
	
}
