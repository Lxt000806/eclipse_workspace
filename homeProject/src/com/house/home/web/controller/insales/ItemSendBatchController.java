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
		logger.debug("跳转到itemSendBatch_code页面");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_code").addObject("data", itemSendBatch);
	}

	@RequestMapping("/getItemSendBatch")
	@ResponseBody
	public JSONObject getItemSendBatch(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		ItemSendBatch itemSendBatch = itemSendBatchService.get(ItemSendBatch.class, id);
		if(itemSendBatch == null){
			return this.out("系统中不存在code="+id+"的领料信息", false);
		}
		return this.out(itemSendBatch, true);
	}
	/**
	 * ItemSendBatch列表
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
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, 
			ItemSendBatch itemSendBatch){
		logger.debug("跳转到新增页面");
		try {
			if("A".equals(itemSendBatch.getM_umState())){
				itemSendBatch.setNo("保存时生成");
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
	 * 跳转到更新页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到更新页面");
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
	 * 跳转到查看页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看页面");
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
	 * 跳转到明细管理页面
	 * @return
	 */
	@RequestMapping("/goDetailManage")
	public ModelAndView goDetailManage(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到明细管理页面");
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
	 * 跳转到明细查询页面
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到明细查询页面");
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
	 * 跳转到配送费录入itemAppSend页面
	 * @return
	 */
	@RequestMapping("/goSendFee")
	public ModelAndView goSendFee(HttpServletRequest request, HttpServletResponse response, 
			ItemAppSend itemAppSend){
		logger.debug("跳转到配送费录入itemAppSend页面");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_sendDetail_sendFee")
			.addObject("itemAppSend", itemAppSend);
	}
	/**
	 * 跳转到搬运信息录入itemAppSend页面
	 * @return
	 */
	@RequestMapping("/goCarryInfo")
	public ModelAndView goCarryInfo(HttpServletRequest request, HttpServletResponse response, 
			ItemAppSend itemAppSend){
		logger.debug("跳转到配送费录入itemAppSend页面");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_sendDetail_carryInfo")
			.addObject("itemAppSend", itemAppSend);
	}
	/**
	 * 跳转到退回页面
	 * @return
	 */
	@RequestMapping("/goRerurn")
	public ModelAndView goRerurn(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到退回页面");
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
	 *ItemSendBatch导出Excel
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
				"配送管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 
	 * 司机是否有两天前的配送任务未完成
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
		logger.debug("司机是否有两天前的配送任务未完成");
		List<Map<String, Object>> list = itemSendBatchService.checkDriver(itemSendBatch);
		return list;
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param itemSendBatch
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,ItemSendBatch itemSendBatch){
		logger.debug("保存");		
		try {	
			if (!"T".equals(itemSendBatch.getExpired())) {
				itemSendBatch.setExpired("F");
			}
			String ais =request.getParameter("itemAppSendJson");//发货明细表
		    JSONObject jsonObject1 = JSON.parseObject(ais);
			JSONArray jsonArray1 = JSON.parseArray(jsonObject1.get("detailJson").toString());//先转化成json数组  
			String  itemAppSendJson=jsonArray1.toString();
			
			String ir =request.getParameter("itemReturnJson");//退货明细表
		    JSONObject jsonObject2 = JSON.parseObject(ir);
			JSONArray jsonArray2 = JSON.parseArray(jsonObject2.get("detailJson").toString());//先转化成json数组  
			String  itemReturnJson=jsonArray2.toString();
			
			String iasb =request.getParameter("itemAppSendBackJson");//发货撤销明细表
		    JSONObject jsonObject3 = JSON.parseObject(iasb);
			JSONArray jsonArray3 = JSON.parseArray(jsonObject3.get("detailJson").toString());//先转化成json数组  
			String  itemAppSendBackJson=jsonArray3.toString();
			
			itemSendBatch.setItemAppSendJson(itemAppSendJson);
			itemSendBatch.setItemReturnJson(itemReturnJson);
			itemSendBatch.setItemAppSendBackJson(itemAppSendBackJson);
			
			Result result = this.itemSendBatchService.doSaveProc(itemSendBatch);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
}
