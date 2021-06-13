package com.house.home.web.controller.insales;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.fileUpload.impl.ItemAppSendUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.entity.insales.PurchArrDetail;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.WorkCostDetail;
import com.house.home.service.driver.ItemAppSendService;

@Controller
@RequestMapping("/admin/itemAppSend")
public class ItemAppSendController extends BaseController {


	@Autowired
	private ItemAppSendService itemAppSendService;

	/**
	 * 查询JqGrid表格数据-分批发货明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_fhmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_fhmx(HttpServletRequest request,
			HttpServletResponse response, String iaNo) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSendService.findPageByIaNo(page, iaNo);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 分批发货明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFhmx")
	public ModelAndView goFhmx(HttpServletRequest request,
			HttpServletResponse response,String iaNo) throws Exception {

		return new ModelAndView("admin/insales/itemAppSend/itemAppSend_fhmx")
			.addObject("iaNo", iaNo);
	}
	/**
	 * 查询goSendDetailJqGrid表格数据-发货明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSendDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSendDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppSend itemAppSend) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSendService.findSendDetail(page, itemAppSend);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到新增发货页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAddSendDetail")
	public ModelAndView goAddSendDetail(HttpServletRequest request,
			HttpServletResponse response,ItemAppSend itemAppSend) throws Exception {

		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_sendDetail_add")
			.addObject("itemAppSend", itemAppSend);
	}
	/**
	 * 查询goSendDetailAddJqGrid表格数据-新增发货
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSendDetailAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSendDetailAddJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppSend itemAppSend) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSend.setItemRight(getUserContext(request).getItemRight());
		itemAppSendService.findSendDetailAdd(page, itemAppSend);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询goSendDetailMngJqGrid表格数据-明细管理-发货明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSendDetailMngJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSendDetailMngJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppSend itemAppSend) throws Exception {

		UserContext uc = getUserContext(request);
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSend.setItemRight(uc.getItemRight());
		itemAppSendService.findSendDetailMng(page, itemAppSend);

		if(uc == null || !"1".equals(uc.getCostRight())){
			for(int i = 0; i < page.getResult().size(); i++){
				page.getResult().get(i).put("allCost", 0);
			}
		}
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *ItemAppSend导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSendDetailMngExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemAppSend itemAppSend){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppSend.setItemRight(getUserContext(request).getItemRight());
		itemAppSendService.findSendDetailMng(page, itemAppSend);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"配送明细管理（发货）_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到发货页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goArrive")
	public ModelAndView goArrive(HttpServletRequest request,
			HttpServletResponse response,ItemAppSend itemAppSend) throws Exception {

		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_detailManage_arrive")
			.addObject("itemAppSend", itemAppSend);
	}
	/**
	 * 发货
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doArrive")
	public void doArrive(HttpServletRequest request, HttpServletResponse response, ItemAppSend itemAppSend){
		logger.debug("发货");
		try{
			itemAppSend.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemAppSendService.doArrive(itemAppSend);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "操作失败！");
		}
	}
	/**
	 * 查询goSendDetailQryJqGrid表格数据-明细查询-发货明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSendDetailQryJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSendDetailQryJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppSend itemAppSend) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSend.setItemRight(getUserContext(request).getItemRight());
		itemAppSendService.findSendDetailQry(page, itemAppSend);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *ItemAppSend导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSendDetailQryExcel")
	public void doSendDetailQryExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemAppSend itemAppSend){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppSend.setItemRight(getUserContext(request).getItemRight());
		itemAppSendService.findSendDetailQry(page, itemAppSend);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"明细查询（发货）_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 
	 * 自动生成搬运费
	 * @param request
	 * @param response
	 * @param itemAppSend
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/autoCteateFee")
	public Map<String, Object> autoCteateFee(HttpServletRequest request,
			HttpServletResponse response, ItemAppSend itemAppSend) {
		logger.debug("自动生成搬运费");
		Map<String, Object> map=new HashMap<String, Object>();
		try {	
			String appSend =request.getParameter("itemAppSendJson");
		    JSONObject jsonObject = JSON.parseObject(appSend);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  itemAppSendJson=jsonArray.toString();
			itemAppSend.setItemAppSendJson(itemAppSendJson);
			map =itemAppSendService.autoCteateFee(itemAppSend);
		} catch (Exception e) {
		}
		return map;
	}
	/**
	 * 跳转到查看发货图片页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewSendPhoto")
	public ModelAndView goViewSendPhoto(HttpServletRequest request,
			HttpServletResponse response,ItemAppSend itemAppSend) throws Exception {

		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_sendDetail_viewPhoto")
			.addObject("itemAppSend", itemAppSend);
	}
	/**
	 * 查看图片页面
	 * 
	 * */
	@RequestMapping("/ajaxGetPicture")
	@ResponseBody
	public ItemAppSend getPicture(HttpServletRequest request, HttpServletResponse response,ItemAppSend itemAppSend){
		ItemAppSendUploadRule rule = new ItemAppSendUploadRule(itemAppSend.getPhotoName(),"");
		itemAppSend.setPhotoPath(FileUploadUtils.getFileUrl(rule.getOriginalPath()));
		return itemAppSend;
	}
}
