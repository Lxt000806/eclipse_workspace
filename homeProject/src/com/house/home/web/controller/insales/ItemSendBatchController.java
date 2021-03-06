package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.project.WorkCostDetail;
import com.house.home.service.insales.ItemSendBatchService;
import com.house.home.service.project.WorkCostService;

@Controller
@RequestMapping("/admin/itemSendBatch")
public class ItemSendBatchController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemSendBatchController.class);

	@Autowired
	private ItemSendBatchService itemSendBatchService;
	@Autowired
	private WorkCostService workCostService;
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,HttpServletResponse response,ItemSendBatch itemSendBatch) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSendBatchService.getJqGrid(page,itemSendBatch);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goReturnDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReturnDetailJqGrid(HttpServletRequest request,HttpServletResponse response,ItemSendBatch itemSendBatch) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSendBatchService.goReturnDetailJqGrid(page,itemSendBatch);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,HttpServletResponse response, ItemSendBatch itemSendBatch) throws Exception {
		logger.debug("?????????itemSendBatch_code??????");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_code").addObject("data", itemSendBatch);
	}

	@RequestMapping("/getItemSendBatch")
	@ResponseBody
	public JSONObject getItemSendBatch(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("?????????id??????", false);
		}
		ItemSendBatch itemSendBatch = itemSendBatchService.get(ItemSendBatch.class, id);
		if(itemSendBatch == null){
			return this.out("??????????????????code="+id+"???????????????", false);
		}
		return this.out(itemSendBatch, true);
	}
	/**
	 * ItemSendBatch??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_list");
	}
	
	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, 
			ItemSendBatch itemSendBatch){
		logger.debug("?????????????????????");
		try {
			if("A".equals(itemSendBatch.getM_umState())){
				itemSendBatch.setNo("???????????????");
				itemSendBatch.setAppCZY(getUserContext(request).getEmnum());
				itemSendBatch.setDate(new Date());
				itemSendBatch.setStatus("1");
				itemSendBatch.setAppCZYDescr(workCostService.findNameByEmnum(getUserContext(request).getEmnum()));
			}else{
				itemSendBatch.setAppCZYDescr(workCostService.findNameByEmnum(itemSendBatch.getAppCZY()));
			}
		} catch (Exception e) {
		}
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_add")
			.addObject("itemSendBatch", itemSendBatch);
	}

	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("?????????????????????");
		ItemSendBatch itemSendBatch = null;
		if (StringUtils.isNotBlank(id)){
			itemSendBatch = itemSendBatchService.get(ItemSendBatch.class, Integer.parseInt(id));
		}else{
			itemSendBatch = new ItemSendBatch();
		}
		
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_save")
			.addObject("itemSendBatch", itemSendBatch);
	}
	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("?????????????????????");
		ItemSendBatch itemSendBatch = null;
		if (StringUtils.isNotBlank(id)){
			itemSendBatch = itemSendBatchService.get(ItemSendBatch.class, Integer.parseInt(id));
		}else{
			itemSendBatch = new ItemSendBatch();
		}
		
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_save")
			.addObject("itemSendBatch", itemSendBatch);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goDetailManage")
	public ModelAndView goDetailManage(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????????????????");
		ItemSendBatch itemSendBatch = null;
		if (StringUtils.isNotBlank(id)){
			itemSendBatch = itemSendBatchService.get(ItemSendBatch.class, Integer.parseInt(id));
		}else{
			itemSendBatch = new ItemSendBatch();
		}
		System.out.println(getUserContext(request).getCostRight()+".");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_detailManage")
			.addObject("itemSendBatch", itemSendBatch)
			.addObject("CostRight", getUserContext(request).getCostRight());
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????????????????");
		ItemSendBatch itemSendBatch = null;
		if (StringUtils.isNotBlank(id)){
			itemSendBatch = itemSendBatchService.get(ItemSendBatch.class, Integer.parseInt(id));
		}else{
			itemSendBatch = new ItemSendBatch();
		}
		itemSendBatch.setCostRight(getUserContext(request).getCostRight());
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_detailQuery")
			.addObject("itemSendBatch", itemSendBatch);
	}
	/**
	 * ????????????????????????itemAppSend??????
	 * @return
	 */
	@RequestMapping("/goSendFee")
	public ModelAndView goSendFee(HttpServletRequest request, HttpServletResponse response, 
			ItemAppSend itemAppSend){
		logger.debug("????????????????????????itemAppSend??????");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_sendDetail_sendFee")
			.addObject("itemAppSend", itemAppSend);
	}
	/**
	 * ???????????????????????????itemAppSend??????
	 * @return
	 */
	@RequestMapping("/goCarryInfo")
	public ModelAndView goCarryInfo(HttpServletRequest request, HttpServletResponse response, 
			ItemAppSend itemAppSend){
		logger.debug("????????????????????????itemAppSend??????");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_sendDetail_carryInfo")
			.addObject("itemAppSend", itemAppSend);
	}
	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping("/goRerurn")
	public ModelAndView goRerurn(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("?????????????????????");
		ItemSendBatch itemSendBatch = null;
		if (StringUtils.isNotBlank(id)){
			itemSendBatch = itemSendBatchService.get(ItemSendBatch.class, Integer.parseInt(id));
		}else{
			itemSendBatch = new ItemSendBatch();
		}
		
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_save")
			.addObject("itemSendBatch", itemSendBatch);
	}
	/**
	 *ItemSendBatch??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemSendBatch itemSendBatch){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemSendBatchService.getJqGrid(page, itemSendBatch);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 
	 * ????????????????????????????????????????????????
	 * @param request
	 * @param response
	 * @param workCostDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkDriver")
	public List<Map<String, Object>> checkDriver(
			HttpServletRequest request, HttpServletResponse response,
			ItemSendBatch itemSendBatch) {
		logger.debug("????????????????????????????????????????????????");
		List<Map<String, Object>> list = itemSendBatchService.checkDriver(itemSendBatch);
		return list;
	}

	/**
	 * ??????
	 * @param request
	 * @param response
	 * @param itemSendBatch
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,ItemSendBatch itemSendBatch){
		logger.debug("??????");		
		try {	
			if (!"T".equals(itemSendBatch.getExpired())) {
				itemSendBatch.setExpired("F");
			}
			String ais =request.getParameter("itemAppSendJson");//???????????????
		    JSONObject jsonObject1 = JSON.parseObject(ais);
			JSONArray jsonArray1 = JSON.parseArray(jsonObject1.get("detailJson").toString());//????????????json??????  
			String  itemAppSendJson=jsonArray1.toString();
			
			String ir =request.getParameter("itemReturnJson");//???????????????
		    JSONObject jsonObject2 = JSON.parseObject(ir);
			JSONArray jsonArray2 = JSON.parseArray(jsonObject2.get("detailJson").toString());//????????????json??????  
			String  itemReturnJson=jsonArray2.toString();
			
			String iasb =request.getParameter("itemAppSendBackJson");//?????????????????????
		    JSONObject jsonObject3 = JSON.parseObject(iasb);
			JSONArray jsonArray3 = JSON.parseArray(jsonObject3.get("detailJson").toString());//????????????json??????  
			String  itemAppSendBackJson=jsonArray3.toString();
			
			itemSendBatch.setItemAppSendJson(itemAppSendJson);
			itemSendBatch.setItemReturnJson(itemReturnJson);
			itemSendBatch.setItemAppSendBackJson(itemAppSendBackJson);
			
			Result result = this.itemSendBatchService.doSaveProc(itemSendBatch);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"????????????");
				}else{
					ServletUtils.outPrintFail(request, response,"???????????????"+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
}
