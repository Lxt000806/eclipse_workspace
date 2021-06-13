package com.house.home.web.controller.insales;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.fileUpload.impl.ItemPreAppUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.MathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.web.controller.BaseController;
import com.house.home.bean.insales.ItemAppImportExcelBean;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.LaborFee;
import com.house.home.entity.insales.ItemPreApp;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.ItemAppTempAreaDetailService;
import com.house.home.service.insales.ItemPreAppDetailService;
import com.house.home.service.insales.ItemPreAppPhotoService;
import com.house.home.service.insales.ItemPreAppService;

@Controller
@RequestMapping("/admin/itemPreApp")
public class ItemPreAppController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPreAppController.class);

	@Autowired
	private ItemPreAppService itemPreAppService;
	
	@Autowired
	private ItemPreAppPhotoService itemPreAppPhotoService;
	
	@Autowired
	private ItemAppTempAreaDetailService itemAppTempAreaDetailService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustTypeService custTypeService;
	
	@Autowired
	private ItemPreAppDetailService itemPreAppDetailService;
	@Autowired
	private CustStakeholderService custStakeholderService;
	
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
			HttpServletResponse response, ItemPreApp itemPreApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.findPageBySql(page, itemPreApp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemPreApp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ItemPreApp itemPreApp = new ItemPreApp();
		itemPreApp.setStatus("2,3,4");
		String czybh = getUserContext(request).getCzybh();
		itemPreApp.setConfirmCzy(czybh);
		Czybm czybm = itemPreAppService.get(Czybm.class, czybh);
		itemPreApp.setConfirmCzyDescr(czybm.getZwxm());
		itemPreApp.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		//itemPreApp.setConfirmCzyDescr(itemPreAppService.get(Employee.class, czybh).getNameChi());
		//return new ModelAndView("admin/insales/itemPreApp/itemPreApp_list");
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * ItemPreApp查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,ItemPreApp itemPreApp) throws Exception {
		itemPreApp.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		return new ModelAndView("admin/insales/itemPreApp/itemPreApp_code").addObject("data", itemPreApp);
	}
	/**
	 * 跳转到新增ItemPreApp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemPreApp页面");
		ItemPreApp itemPreApp = null;
		if (StringUtils.isNotBlank(id)){
			itemPreApp = itemPreAppService.get(ItemPreApp.class, id);
			itemPreApp.setNo(null);
		}else{
			itemPreApp = new ItemPreApp();
		}

		return new ModelAndView("admin/insales/itemPreApp/itemPreApp_save")
			.addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到修改ItemPreApp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemPreApp页面");
		ItemPreApp itemPreApp = null;
		if (StringUtils.isNotBlank(id)){
			itemPreApp = itemPreAppService.get(ItemPreApp.class, id);
		}else{
			itemPreApp = new ItemPreApp();
		}
		
		return new ModelAndView("admin/insales/itemPreApp/itemPreApp_update")
			.addObject("itemPreApp", itemPreApp);
	}
	
	/**
	 * 跳转到查看ItemPreApp页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemPreApp页面");
		ItemPreApp itemPreApp = itemPreAppService.get(ItemPreApp.class, id);
		
		return new ModelAndView("admin/insales/itemPreApp/itemPreApp_detail")
				.addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 添加ItemPreApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		logger.debug("添加ItemPreApp开始");
		try{
			String str = itemPreAppService.getSeqNo("tItemPreApp");
			itemPreApp.setNo(str);
			itemPreApp.setLastUpdate(new Date());
			itemPreApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemPreApp.setExpired("F");
			this.itemPreAppService.save(itemPreApp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemPreApp失败");
		}
	}
	
	/**
	 * 修改ItemPreApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		logger.debug("修改ItemPreApp开始");
		try{
			itemPreApp.setLastUpdate(new Date());
			itemPreApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemPreAppService.update(itemPreApp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemPreApp失败");
		}
	}
	
	/**
	 * 删除ItemPreApp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemPreApp开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemPreApp编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemPreApp itemPreApp = itemPreAppService.get(ItemPreApp.class, deleteId);
				if(itemPreApp == null)
					continue;
				itemPreApp.setExpired("T");
				itemPreAppService.update(itemPreApp);
			}
		}
		logger.debug("删除ItemPreApp IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemPreApp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemPreApp itemPreApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		//itemPreAppService.findPageBySql(page, itemPreApp);
		itemPreApp.setItemRight(getUserContext(request).getItemRight().trim());
		itemPreAppService.findItemPreAppManageListPageBySql(page, itemPreApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"领料预申请--"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}

	//20171011领料预申请管理修改
	
	//----------------------表格查询相关
	/**
	 * 查询JqGrid表格数据,主页面 itemPreAppManage_list
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPreAppManageListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemPreAppManageListJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreApp itemPreApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreApp.setItemRight(getUserContext(request).getItemRight().trim());
		itemPreAppService.findItemPreAppManageListPageBySql(page, itemPreApp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *  查询JqGrid表格数据,领料预申请明细页面 itemPreAppManage_tabView_applyList
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @param supplCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goApplyListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goApplyListJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreApp itemPreApp,String supplCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);

		itemPreAppService.getApplyListByNo(page, itemPreApp.getNo(),supplCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据,材料图片页面 itemPreAppManage_tabView_materialPhoto
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goItemPreAppPhotoListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemPreAppPhotoListJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreApp itemPreApp){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.getItemPreAppPhotoListByNo(page, itemPreApp);
		return new WebPage<Map<String,Object>>(page);		
	}

	/**
	 * 查询JqGrid表格数据,测量下单页面 itemPreAppManage_tabView_measure
	 * @param request 
	 * @param response
	 * @param itemPreApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goMeasureJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goMeasureJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreApp itemPreApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.getMeasureByCondition(page, itemPreApp.getNo(),null);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *  查询JqGrid表格数据,测量后下单页面 itemPreAppManage_list_clhxd
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@RequestMapping("/goClhxdJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goClhxdJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreMeasure.setItemRight(getUserContext(request).getItemRight().trim());
		itemPreAppService.goClhxdJqGrid(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);		
	}
	/**
	 *  查询JqGrid表格数据,已有项新增页面 itemPreAppManage_list_addll_yyxxz
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@RequestMapping("/goYyxxzJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goYyxxzJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
//		page.setPageSize(-1); // 20191122 mark by xzp 有些 客户（内部客户）的材料太多导致浏览器显示不出来，改为分页显示
		itemPreAppService.goYyxxzJqGrid(page,itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);		
	}
	/**
	 * 查询JqGrid表格数据,从预申请导入页面 itemPreAppManage_list_addll_preApply
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@RequestMapping("/goPreApplyJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPreApplyJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemPreAppService.goPreApplyJqGrid(page,itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);		
	}
	/**
	 * 查询JqGrid表格数据,已领材料页面 itemPreAppManage_list_addll_ylcl
	 * @param request
	 * @param response
	 * @param custCode
	 * @param appNo
	 * @param itemCodes
	 * @return
	 */
	@RequestMapping("/goYlclJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goYlclJqGrid(HttpServletRequest request,
			HttpServletResponse response,String custCode,String appNo,String itemCodes){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemPreAppService.goYlclJqGrid(page,custCode,appNo,itemCodes);
		return new WebPage<Map<String,Object>>(page);		
	}
	/**
	 * 查询JqGrid表格数据,快速新增页面 itemPreAppManage_list_addll_ksxz
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@RequestMapping("/goKsxzJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goKsxzJqGrid(HttpServletRequest request,HttpServletResponse response,ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//page.setPageSize(-1);
		itemPreAppService.goKsxzJqGrid(page,itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goItemAppDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemAppDetailJqGrid(HttpServletRequest request,HttpServletResponse response,ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemPreAppService.goItemAppDetailJqGrid(page,itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCodeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCodeJqGrid(HttpServletRequest request,HttpServletResponse response,ItemPreApp itemPreApp){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreApp.setCzybh(getUserContext(request).getCzybh());
		itemPreAppService.goCodeJqGrid(page,itemPreApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCodeDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCodeDetailJqGrid(HttpServletRequest request,HttpServletResponse response,String no){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goCodeDetailJqGrid(page,no);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goJqGridSend")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridSend(HttpServletRequest request,HttpServletResponse response, String no){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goJqGridSend(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGridSendMx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridSendMx(HttpServletRequest request,HttpServletResponse response,String no){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goJqGridSendMx(page,no);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGridSendCountView")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridSendCountView(HttpServletRequest request,HttpServletResponse response, String no){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goJqGridSendCountView(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGirdUnItemAppMaterial")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGirdUnItemAppMaterial(HttpServletRequest request,HttpServletResponse response, String custCode, String itemType1,
																String itemType2, String whCode, String supplCode){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goJqGirdUnItemAppMaterial(page, custCode, itemType1, itemType2, whCode, supplCode);
		return new WebPage<Map<String,Object>>(page);
	}
	//---------------------------------页面跳转相关
	/**
	 * 跳转到接收界面 itemPreAppManage_list_view
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goReceive")
	public ModelAndView goReceive(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){		
		String itemRight = getUserContext(request).getItemRight();
		itemPreApp.setItemRight("('"+itemRight.trim().replace(",", "','")+"')");
		itemPreApp.setItemType1(itemPreApp.getItemType1().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_view").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到新增领料界面 itemPreAppManage_list_addll
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goAddll")
	public ModelAndView goAddll(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){		
		Map<String,Object> map = itemPreAppService.getMeasureInfoByNo(itemPreApp.getNo());
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		Xtcs xtcs=itemPreAppService.get(Xtcs.class, "AftCustCode");
		BeanConvertUtil.mapToBean(map, itemPreMeasure);
		itemPreMeasure.setM_umState("A");
		itemPreMeasure.setItemAppStatus("OPEN");
		itemPreMeasure.setItemAppType("S");
		itemPreMeasure.setIsService("0");
		itemPreMeasure.setAppNo("保存时生成");
		itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
		itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		if(itemPreAppService.containServiceItem(itemPreMeasure.getPreAppNo())){
			itemPreMeasure.setServiceTip("服务性产品需下单");
		}
		
		if(itemPreAppService.hasInSetReq(itemPreMeasure.getCustCode(), itemPreMeasure.getItemType1())){
			itemPreMeasure.setHasInSetReq("1");
		}else{
			itemPreMeasure.setHasInSetReq("0");
		}
		
		//是否存在套内套外材料,用于设置是否套餐的下拉选项
		boolean existsInSetItem=itemPreAppDetailService.existsInSetItem(itemPreApp.getNo()).size()>0?true:false;
		boolean existsOutSetItem=itemPreAppDetailService.existsOutSetItem(itemPreApp.getNo()).size()>0?true:false;
		String unShowIsSetItem="";
		if(existsInSetItem && !existsOutSetItem){
			unShowIsSetItem="0";
			itemPreMeasure.setIsSetItem("1");
		}
		if(!existsInSetItem && existsOutSetItem){
			unShowIsSetItem="1";
			itemPreMeasure.setIsSetItem("0");
		}
		
		itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
		itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll")
		.addObject("itemPreMeasure", itemPreMeasure)
		.addObject("AftCustCode", xtcs.getQz())
		.addObject("unShowIsSetItem", unShowIsSetItem);
	}
	/**
	 * 跳转到新增测量界面 itemPreAppManage_list_view
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goAddcl")
	public ModelAndView goAddcl(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){	
		String itemRight = getUserContext(request).getItemRight();
		itemPreApp.setItemRight("('"+itemRight.trim().replace(",", "','")+"')");
		itemPreApp.setItemType1(itemPreApp.getItemType1().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_view").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到测量后下单界面 itemPreAppManage_list_clhxd
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goClhxd")
	public ModelAndView goClhxd(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		String itemRight = getUserContext(request).getItemRight();
		itemPreApp.setItemRight("('"+itemRight.trim().replace(",", "','")+"')");
		itemPreApp.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_clhxd").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到取消申请界面 itemPreAppManage_list_view
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goQxsq")
	public ModelAndView goQxsq(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		String itemRight = getUserContext(request).getItemRight();
		itemPreApp.setItemRight("('"+itemRight.trim().replace(",", "','")+"')");
		itemPreApp.setItemType1(itemPreApp.getItemType1().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_view").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到退回提交界面 itemPreAppManage_list_view
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goThtj")
	public ModelAndView goThtj(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		String itemRight = getUserContext(request).getItemRight();
		itemPreApp.setItemRight("('"+itemRight.trim().replace(",", "','")+"')");
		itemPreApp.setItemType1(itemPreApp.getItemType1().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_view").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到退回申请界面 itemPreAppManage_list_view
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goThsq")
	public ModelAndView goThsq(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		String itemRight = getUserContext(request).getItemRight();
		itemPreApp.setItemRight("('"+itemRight.trim().replace(",", "','")+"')");
		itemPreApp.setItemType1(itemPreApp.getItemType1().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_view").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到下单完成界面 itemPreAppManage_list_view
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goXdwc")
	public ModelAndView goXdwc(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		String itemRight = getUserContext(request).getItemRight();
		itemPreApp.setItemRight("('"+itemRight.trim().replace(",", "','")+"')");
		itemPreApp.setItemType1(itemPreApp.getItemType1().trim());
		itemPreApp.setUnShowEndCode("0,1");
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_view").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到查看界面 itemPreAppManage_list_view
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		String itemRight = getUserContext(request).getItemRight();
		itemPreApp.setItemRight("('"+itemRight.trim().replace(",", "','")+"')");
		itemPreApp.setItemType1(itemPreApp.getItemType1().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_view").addObject("itemPreApp", itemPreApp);
	}
	/**
	 * 跳转到新增测量的新增页面 itemPreAppManage_tabView_measureAdd
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping("/goAdd_tabView_measure")
	public ModelAndView goAdd_tabView_measure(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("跳转到查看ItemPreAppManage_tabView_measureAdd页面");

		ItemPreMeasure ipm = new ItemPreMeasure();
		ipm.setPreAppNo(no);
		ipm.setDate(new Date());
		ipm.setUnShowStatus("2,3,4,5");
		ipm.setStatus("1");
		Czybm czybm = itemPreAppService.get(Czybm.class, getUserContext(request).getCzybh());
		ipm.setAppCzy(czybm.getCzybh());
		ipm.setAppCzyDescr(czybm.getZwxm());
		ipm.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_tabView_measureAdd")
				.addObject("itemPreMeasure", ipm);
	}
	/**
	 * 跳转到新增测量的修改页面 itemPreAppManage_tabView_measureUpdate
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("/goUpdate_tabView_measure")
	public ModelAndView goUpdate_tabView_measure(HttpServletRequest request, HttpServletResponse response,Integer pk){
		logger.debug("跳转到查看ItemPreAppManage_tabView_measureUpdate页面");	
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		page.setPageSize(1);
		page.setPageNo(1);
		itemPreAppService.getMeasureByCondition(page, null,pk);	
		ItemPreMeasure ipm = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(page.getResult().get(0), ipm);
		ipm.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_tabView_measureUpdate")
				.addObject("itemPreMeasure", ipm);
	}
	/**
	 * 跳转到新增测量的查看页面 itemPreAppManage_tabView_measureDetail
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("/goDetail_tabView_measure")
	public ModelAndView goDetail_tabView_measure(HttpServletRequest request, HttpServletResponse response,Integer pk){
		logger.debug("跳转到查看ItemPreApp_tabView_measureDetail页面");

		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		page.setPageSize(1);
		page.setPageNo(1);
		itemPreAppService.getMeasureByCondition(page, null,pk);	
		ItemPreMeasure ipm = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(page.getResult().get(0), ipm);
		ipm.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_tabView_measureDetail")
				.addObject("itemPreMeasure", ipm);
	}
	/**
	 * 跳转到测量后下单的测量下单页面 itemPreAppManage_list_addll
	 * @param request
	 * @param response
	 * @param pk
	 * @param m_umState
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goClxd_clhxd")
	public ModelAndView goClxd_clhxd(HttpServletRequest request,
			HttpServletResponse response,Integer pk,String m_umState) throws Exception {
		Map<String,Object> map = itemPreAppService.getMeasureInfoByPk(pk);
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(map, itemPreMeasure);
		itemPreMeasure.setM_umState("A");
		itemPreMeasure.setItemAppStatus("OPEN");
		itemPreMeasure.setItemAppType("S");
		itemPreMeasure.setIsService("0");
		itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
		itemPreMeasure.setAppNo("保存时生成");
		itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		if(itemPreAppService.containServiceItem(itemPreMeasure.getPreAppNo())){
			itemPreMeasure.setServiceTip("服务性产品需下单");
		}
		
		if(itemPreAppService.hasInSetReq(itemPreMeasure.getCustCode(), itemPreMeasure.getItemType1())){
			itemPreMeasure.setHasInSetReq("1");
		}else{
			itemPreMeasure.setHasInSetReq("0");
		}
		itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
		itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll").addObject("itemPreMeasure", itemPreMeasure);
	}
	/**
	 * 跳转到测量后下单的下单取消页面 itemPreAppManage_list_clhxd_view
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@RequestMapping("/goXdqx_clhxd")
	public ModelAndView goXdqx_clhxd(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		ItemPreMeasure ipm = itemPreAppService.get(ItemPreMeasure.class, itemPreMeasure.getPk());
		ipm.setM_umState("M");
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_clhxd_view").addObject("itemPreMeasure", ipm);
	}
	/**
	 * 跳转到测量后下单的查看页面 itemPreAppManage_list_clhxd_view
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@RequestMapping("/goView_clhxd")
	public ModelAndView goView_clhxd(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		ItemPreMeasure ipm = itemPreAppService.get(ItemPreMeasure.class, itemPreMeasure.getPk());
		ipm.setM_umState("V");
		Map<String, Object> map = itemPreAppService.getByPreAppNo(ipm.getPreAppNo());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_clhxd_view")
			.addObject("itemPreMeasure", ipm).addObject("map", map);
	}
	/**
	 * 跳转到新增领料的已有项新增页面 itemPreAppManage_list_addll_yyxxz
	 * @param request
	 * @param response
	 * @param custCode
	 * @param itemType1
	 * @param isService
	 * @param itemType2
	 * @param isSetItem
	 * @param pks
	 * @param appNo
	 * @param m_umState
	 * @return
	 */
	@RequestMapping("/goYyxxz_addll")
	public ModelAndView goYyxxz_addll(HttpServletRequest request, HttpServletResponse response, 
			String custCode,String itemType1,String isService,String itemType2,String isSetItem,String pks,
			String appNo,String m_umState,String isCupboard,String isAutoOrder){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = itemPreAppService.custItemConf(custCode);
		Xtcs xtcs = itemPreAppService.get(Xtcs.class, "ItemConfCtrl");
		map.put("itemConfCtrl", xtcs.getQz());
		map.put("custItemConf",list);
		map.put("custCode", custCode);
		map.put("itemType1", itemType1);
		map.put("isService", isService);
		map.put("itemType2", itemType2);
		map.put("isSetItem", isSetItem);
		map.put("pks", pks);
		map.put("appNo", appNo);
		map.put("m_umState", m_umState);
		map.put("isCupboard", isCupboard);
		map.put("isAutoOrder", isAutoOrder);
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_yyxxz").addObject("data", map);
	}
	/**
	 * 跳转到新增领料的从预申请导入页面 itemPreAppManage_list_addll_preApply
	 * @param request
	 * @param response
	 * @param custCode
	 * @param preAppNo
	 * @param appNo
	 * @param itemType1
	 * @param itemType2
	 * @param isService
	 * @param m_umState
	 * @param preAppDTPks
	 * @param reqPks
	 * @param itemCodes
	 * @param isSetItem
	 * @return
	 */
	@RequestMapping("/goPreApply_addll")
	public ModelAndView goPreApply_addll(HttpServletRequest request, HttpServletResponse response,String custCode,String preAppNo,String appNo,String itemType1,
			String itemType2,String isService,String m_umState,String preAppDTPks,String reqPks,String itemCodes,String isSetItem){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = itemPreAppService.custItemConf(custCode);
		Xtcs xtcs = itemPreAppService.get(Xtcs.class, "ItemConfCtrl");
		map.put("itemConfCtrl", xtcs.getQz());
		map.put("custItemConf",list);
		map.put("custCode", custCode);
		map.put("preAppNo", preAppNo);
		map.put("appNo", appNo);
		map.put("itemType1", itemType1);
		map.put("itemType2", itemType2);
		map.put("isService", isService);
		map.put("m_umState", m_umState);
		map.put("reqPks", reqPks);
		map.put("preAppDTPks", preAppDTPks);
		map.put("itemCodes", itemCodes);
		map.put("isSetItem", isSetItem);
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_preApply").addObject("data", map);
	}
	/**
	 * 跳转到新增领料的已领材料页面 itemPreAppManage_list_addll_ylcl
	 * @param request
	 * @param response
	 * @param custCode
	 * @param appNo
	 * @param itemCodes
	 * @return
	 */
	@RequestMapping("/goYlcl_addll")
	public ModelAndView goYlcl_addll(HttpServletRequest request, HttpServletResponse response,String custCode,String appNo,String itemCodes){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("custCode", custCode);
		map.put("appNo", appNo);
		map.put("itemCodes", itemCodes);
		map.put("costRight",getUserContext(request).getCostRight());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_ylcl").addObject("data", map);
	}
	/**
	 * 跳转到新增领料的快速新增页面 itemPreAppManage_list_addll_ksxz
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param itemType2
	 * @param isSetItem
	 * @param custCode
	 * @param appNo
	 * @param itemCodes
	 * @return
	 */
	@RequestMapping("/goKsxz_addll")
	public ModelAndView goKsxz_addll(HttpServletRequest request, HttpServletResponse response,String itemType1,String itemType2,String isSetItem,String custCode,String appNo,String itemCodes){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("itemType1", itemType1);
		map.put("itemType2", itemType2);
		map.put("isSetItem", isSetItem);
		map.put("custCode", custCode);
		map.put("appNo", appNo);
		map.put("itemCodes", itemCodes);
		map.put("itemRight", getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_ksxz").addObject("data", map);
	}
	/**
	 * 跳转到新增领料的快速新增页面 itemPreAppManage_list_addll_ksxz
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param itemType2
	 * @param isSetItem
	 * @param custCode
	 * @param appNo
	 * @param itemCodes
	 * @return
	 */
	@RequestMapping("/goTipClickPage")
	public ModelAndView goTipClickPage(HttpServletRequest request, HttpServletResponse response,
			String resultList,String m_umState,String appNo,String custCode){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("resultList", resultList);
		map.put("m_umState", m_umState);
		map.put("appNo", appNo);
		map.put("custCode", custCode);
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_tabView_tipClickPage").addObject("data", map);
	}
	/**
	 * 领料新增页面,分批发货明细
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping("/goSendView")
	public ModelAndView goSendView(HttpServletRequest request, HttpServletResponse response, String no){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("no", no);
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_sendView").addObject("data", map);
	}
	/**
	 * 跳转到从Excel导入页面
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param itemType2
	 * @return
	 */
	@RequestMapping("/goImportExcel")
	public ModelAndView goImportExcel(HttpServletRequest request, HttpServletResponse response, String itemType1, String itemType2, String itemCodes, String no, String custCode){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("itemType1", itemType1);
		map.put("itemType2", itemType2);
		map.put("m_umState", "I");
		map.put("itemCodes", itemCodes);
		map.put("no", no);
		map.put("custCode", custCode);
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_importExcel").addObject("data", map);
	}
	
	@RequestMapping("/goEditRemark")
	public ModelAndView goEditRemark(HttpServletRequest request, HttpServletResponse response, String no, Integer pk, String from, String custCode){
		Map<String,Object> map = new HashMap<String, Object>();
		Customer customer = new Customer();
		if(StringUtils.isNotBlank(custCode)){
			customer = customerService.get(Customer.class, custCode);
			List<Map<String, Object>> list = custStakeholderService.getListByCustCodeAndRole(custCode, "34");
			if(list != null && list.size() > 0){
				map.put("mainManager", list.get(0).get("NameChi"));
				map.put("mainManagerCode", list.get(0).get("EmpCode"));
			}
		}
		
		map.put("from", from);
		if("1".equals(from)){
			map.put("no", no);
			ItemPreApp itemPreApp = itemPreAppService.get(ItemPreApp.class, no);
			if(itemPreApp != null){
				map.put("remarks", itemPreApp.getRemarks());
			}
		}else{
			map.put("pk", pk);
			ItemPreMeasure itemPreMeasure = itemPreAppService.get(ItemPreMeasure.class, pk);
			if(itemPreMeasure != null){
				map.put("remarks", itemPreMeasure.getRemarks());
			}
		}
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_editRemark")
			.addObject("data", map).addObject("customer", customer);
	}
	
	@RequestMapping("/goSendCountView")
	public ModelAndView goSendCountView(HttpServletRequest request, HttpServletResponse response, String no){
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_sendCountView").addObject("no", no);
	}

	@RequestMapping("/goUnItemAppMaterial")
	public ModelAndView goUnItemAppMaterial(HttpServletRequest request, HttpServletResponse response, String custCode, String itemType1,
											String itemType2, String whCode, String supplCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custCode", custCode);
		map.put("itemType1", itemType1);
		map.put("itemType2", itemType2);
		map.put("whCode", whCode);
		map.put("supplCode", supplCode);
		map.put("whCodeDescr", "");
		map.put("supplCodeDescr", "");
		if(StringUtils.isNotBlank(whCode)){
			WareHouse wareHouse = this.itemPreAppService.get(WareHouse.class, whCode);
			if(wareHouse != null){
				map.put("whCodeDescr", wareHouse.getDesc1());
			}
		}
		if(StringUtils.isNotBlank(supplCode)){
			Supplier supplier = this.itemPreAppService.get(Supplier.class, supplCode);
			if(supplier != null){
				map.put("supplCodeDescr", supplier.getDescr());
			}
		}
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_unItemAppMaterial").addObject("data", map);
	}
	//--------------------------------操作相关
	/**
	 * 获取材料图片路径
	 * @param request
	 * @param response
	 * @param photoName
	 * @return
	 */
	@RequestMapping("/getItemPreAppPhoto")
	@ResponseBody
	public ItemPreApp getItemPreAppPhoto(HttpServletRequest request, HttpServletResponse response,String photoName){
		ItemPreApp itemPreApp = new ItemPreApp();
		ItemPreAppUploadRule rule = new ItemPreAppUploadRule(photoName,"");
		itemPreApp.setPhotoPath(FileUploadUtils.getFileUrl(rule.getOriginalPath()));
		return itemPreApp;
	}
	/**
	 * 接收
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @throws Exception
	 */
	@RequestMapping("/doReceive")
	public void doReceive(HttpServletRequest request,HttpServletResponse response,ItemPreApp itemPreApp) throws Exception{
		logger.debug("领料预申请接收开始");
		try{
			itemPreApp.setStatus(itemPreApp.getM_umState());
			itemPreApp.setConfirmCzy(getUserContext(request).getCzybh());
			itemPreApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = itemPreAppService.updatePreItemAppStatus(itemPreApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"接收成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "领料预申请接收失败");
		}
	}
	/**
	 * 取消申请
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @throws Exception
	 */
	@RequestMapping("/doQxsq")
	public void doQxsq(HttpServletRequest request,HttpServletResponse response,ItemPreApp itemPreApp) throws Exception{
		logger.debug("领料预申请管理取消申请开始");
		try{
			itemPreApp.setStatus(itemPreApp.getM_umState());
			itemPreApp.setConfirmCzy(getUserContext(request).getCzybh());
			itemPreApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = itemPreAppService.updatePreItemAppStatus(itemPreApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"取消成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "领料预申请管理取消申请失败");
		}
	}
	/**
	 * 退回提交
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @throws Exception
	 */
	@RequestMapping("/doThtj")
	public void doThtj(HttpServletRequest request,HttpServletResponse response,ItemPreApp itemPreApp) throws Exception{
		logger.debug("领料预申请管理退回提交开始");
		try{
			itemPreApp.setStatus(itemPreApp.getM_umState());
			itemPreApp.setConfirmCzy(getUserContext(request).getCzybh());
			itemPreApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = itemPreAppService.updatePreItemAppStatus(itemPreApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"退回提交成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "领料预申请管理退回提交失败");
		}
	}
	/**
	 * 退回申请
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @throws Exception
	 */
	@RequestMapping("/doThsq")
	public void doThsq(HttpServletRequest request,HttpServletResponse response,ItemPreApp itemPreApp) throws Exception{
		logger.debug("领料预申请管理退回申请开始");
		try{
			itemPreApp.setStatus(itemPreApp.getM_umState());
			itemPreApp.setConfirmCzy(getUserContext(request).getCzybh());
			itemPreApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = itemPreAppService.updatePreItemAppStatus(itemPreApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"退回申请成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "领料预申请管理退回申请失败");
		}
	}
	/**
	 * 下单完成
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @throws Exception
	 */
	@RequestMapping("/doXdwc")
	public void doXdwc(HttpServletRequest request,HttpServletResponse response,ItemPreApp itemPreApp) throws Exception{
		logger.debug("领料预申请管理下单完成开始");
		try{
			itemPreApp.setStatus(itemPreApp.getM_umState());
			itemPreApp.setConfirmCzy(getUserContext(request).getCzybh());
			itemPreApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = itemPreAppService.updatePreItemAppStatus(itemPreApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"下单完成成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "领料预申请管理下单完成失败");
		}
	}
	/**
	 * 新增测量 新增测量单 
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doSave_preMeasure")
	public void doSave_preMeasure(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("添加PreMeasure开始");
		try{
			if(itemPreAppService.existPreMeasure(null,itemPreMeasure.getSupplCode(),itemPreMeasure.getPreAppNo()) != null){
				ServletUtils.outPrintFail(request, response, "该供应商已经存在，不允许重复");
				return;
			}
			itemPreMeasure.setAppCzy(getUserContext(request).getCzybh());
			itemPreMeasure.setLastUpdate(new Date());
			itemPreMeasure.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemPreMeasure.setExpired("F");
			itemPreMeasure.setActionLog("ADD");
			itemPreMeasure.setDelayReson("0");
			itemPreMeasure.setChgStatus("0");
			this.itemPreAppService.save(itemPreMeasure);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PreMeasure失败");
		}
	}
	/**
	 * 新增测量 修改测量单
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doUpdate_preMeasure")
	public void doUpdate_preMeasure(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("修改PreMeasure开始");
		try{
			if(itemPreAppService.existPreMeasure(itemPreMeasure.getPk(),itemPreMeasure.getSupplCode(),itemPreMeasure.getPreAppNo()) != null){
				ServletUtils.outPrintFail(request, response, "该供应商已经存在，不允许重复");
				return;
			}
			ItemPreMeasure ipm = itemPreAppService.get(ItemPreMeasure.class, itemPreMeasure.getPk());
			ipm.setSupplCode(itemPreMeasure.getSupplCode());
			ipm.setRemarks(itemPreMeasure.getRemarks());
			ipm.setLastUpdate(new Date());
			ipm.setLastUpdatedBy(getUserContext(request).getCzybh());
			ipm.setActionLog("EDIT");
			this.itemPreAppService.update(ipm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PreMeasure失败");
		}
	}
	/**
	 * 新增测量 删除测量单
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doDel_preMeasure")
	public void doDel_preMeasure(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("删除PreMeasure开始");
		try{
			ItemPreMeasure ipm = itemPreAppService.get(ItemPreMeasure.class, pk);
			this.itemPreAppService.delete(ipm);
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除PreMeasure失败");
		}
	}
	/**
	 * 新增测量 取消测量单
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doCancel_preMeasure")
	public void doCancel_preMeasure(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("取消PreMeasure开始");
		try{
			ItemPreMeasure ipm = itemPreAppService.get(ItemPreMeasure.class, pk);
			ipm.setStatus("5");
			ipm.setActionLog("EDIT");
			ipm.setLastUpdate(new Date());
			ipm.setLastUpdatedBy(getUserContext(request).getCzybh());	
			this.itemPreAppService.update(ipm);
			ServletUtils.outPrintSuccess(request, response,"取消成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "取消PreMeasure失败");
		}
	}
	/**
	 * 新增测量 恢复测量单
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doRecovery_preMeasure")
	public void doRecovery_preMeasure(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("恢复PreMeasure开始");
		try{
			ItemPreMeasure ipm = itemPreAppService.get(ItemPreMeasure.class, pk);
			ipm.setStatus("1");
			ipm.setActionLog("EDIT");
			ipm.setLastUpdate(new Date());
			ipm.setLastUpdatedBy(getUserContext(request).getCzybh());	
			this.itemPreAppService.update(ipm);
			ServletUtils.outPrintSuccess(request, response,"恢复成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "恢复PreMeasure失败");
		}
	}
	/**
	 * 测量后下单 下单完成
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doXdwc_clhxd")
	public void doXdwc_clhxd(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("测量后下单_下单完成开始");
		try{	
			ItemPreMeasure ipm = itemPreAppService.get(ItemPreMeasure.class, itemPreMeasure.getPk());

			if(ipm.getStatus().trim().equals("4")||ipm.getStatus().trim().equals("7")){	
				ipm.setStatus("6");
				ipm.setCompleteCZY(getUserContext(request).getCzybh());
				ipm.setCompleteDate(new Date());
				ipm.setLastUpdate(new Date());
				ipm.setLastUpdatedBy(getUserContext(request).getCzybh());
				ipm.setActionLog("EDIT");
				itemPreAppService.update(ipm);
			}
			List<Map<String,Object>> list = itemPreAppService.findOtherMaterial(itemPreMeasure.getPreAppNo());
			if(list == null || list.size()==0){
				ItemPreApp ipa = itemPreAppService.get(ItemPreApp.class, itemPreMeasure.getPreAppNo());
				ipa.setStatus("5");
				ipa.setLastUpdate(new Date());
				ipa.setLastUpdatedBy(getUserContext(request).getCzybh());
				ipa.setActionLog("EDIT");
				itemPreAppService.update(ipa);
			}			

			ServletUtils.outPrintSuccess(request, response,"下单完成成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "测量后下单_下单完成失败");
		}
		
	}	
	/**
	 * 测量后下单 导出Excel
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doExcel_clhxd")
	public void doExcel_clhxd(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemPreMeasure.setItemRight(getUserContext(request).getItemRight().trim());
		itemPreAppService.goClhxdJqGrid(page, itemPreMeasure);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"测量信息--"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	/**
	 * 获取测量单详情
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("/getMeasureDetail")
	@ResponseBody
	public ItemPreMeasure getMeasureDetail(HttpServletRequest request, HttpServletResponse response, Integer pk){
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		page.setPageSize(1);
		page.setPageNo(1);
		itemPreAppService.getMeasureByCondition(page, null,pk);
		ItemPreMeasure ipm = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(page.getResult().get(0), ipm);
		return ipm;
	}
	/**
	 * 预算材料、无预算材料是否可领
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/getCanInOutPlan")
	@ResponseBody
	public ItemPreMeasure getCanInOutPlan(HttpServletRequest request, HttpServletResponse response, String itemType1,String custCode){
		ItemPreMeasure ipm = new ItemPreMeasure();
		Map<String,Object> map = itemPreAppService.getCanInOutPlan(itemType1,custCode);
		if(map != null){
			ipm.setIsQueryEmpty("0");
			BeanConvertUtil.mapToBean(map, ipm);
		}else{
			ipm.setIsQueryEmpty("1");
		}
		return ipm;
	}
	/**
	 * 获取材料类型2 选项Options
	 * @param request
	 * @param response
	 * @param itemType1
	 * @return
	 */
	@RequestMapping("/getItemType2Opt")
	@ResponseBody
	public JSONObject getOptions(HttpServletRequest request, HttpServletResponse response, String itemType1){
		StringBuilder html = new StringBuilder();
		html.append("<option value=\"\">请选择...</option>");
		List<Map<String,Object>> list = itemPreAppService.getItemType2Opt(itemType1.trim());
		if(list != null && list.size()>0 ){
			for(int i=0;i<list.size();i++){
				html.append("<option value=\""+list.get(i).get("Code").toString().trim()+"\">"+list.get(i).get("fd").toString().trim()+"</option>");
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("html", html.toString());
		return jsonObject;
	}
	/**
	 * 表单编辑保存时获取相关值 itemPreAppManage_tabView_applyDetail 
	 * @param request
	 * @param response
	 * @param itemCode
	 * @param qty
	 * @return
	 */
	@RequestMapping("/getBeforeSaveCellInfo")
	@ResponseBody
	public JSONObject getBeforeSaveCellInfo(HttpServletRequest request, HttpServletResponse response, String itemCode,Double qty){
		JSONObject jsonObject = new JSONObject();
		if(itemPreAppService.judgeItemInTitles(itemCode)){
			jsonObject.put("result", "1");
		}else{
			jsonObject.put("result", "0");
		}
		Map<String,Object> map = itemPreAppService.getItemNum(itemCode, qty);
		if(map != null){
			jsonObject.put("numDescr", map.get("NumDescr").toString());
		}else{
			jsonObject.put("numDescr", "");
		}
		return jsonObject;	
	}
	/**
	 * 新增领料 保存
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doSave_addll")
	public void doSave_addll(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("新增领料开始");
		try{
			Xtcs xtcs = itemPreAppService.get(Xtcs.class, "CmpCustCode");
			String[] qzs = xtcs.getQz().split(",");
			itemPreMeasure.setIsCmpCustCode("0");
			if(!"R".equals(itemPreMeasure.getM_umState()) && !"D".equals(itemPreMeasure.getM_umState())){
				for(int i=0;i<qzs.length;i++){
					if(qzs[i].equals(itemPreMeasure.getCustCode())){
						itemPreMeasure.setIsCmpCustCode("1");
						break;
					}
				}
			}
			String detailJson = request.getParameter("detailJson");
			itemPreMeasure.setSendTypeCheck(request.getParameter("sendTypeCheck"));
			itemPreMeasure.setApplyQtyCheck(request.getParameter("applyQtyCheck"));
			itemPreMeasure.setAppCzy(getUserContext(request).getCzybh());
			if("A".equals(itemPreMeasure.getM_umState())){
				itemPreMeasure.setItemAppStatus("");
			}
			Result result = itemPreAppService.AddllForProc(itemPreMeasure, XmlConverUtil.jsonToXmlNoHead(detailJson));
			Map<String,Object> returnMap = new HashMap<String, Object>();
			if(result.isSuccess()){
				//StringBuilder sb = new StringBuilder();
				if("A".equals(itemPreMeasure.getM_umState()) && itemPreAppService.isStayAddPage(itemPreMeasure.getPreAppNo())){
					returnMap.put("code", "1");
					//sb.append("[{code=1,info=保存成功}]");
				}else{
					returnMap.put("code", "0");
					//sb.append("[{code=0,info=保存成功}]");
				}
				returnMap.put("info", "保存成功");
				//ServletUtils.outPrintSuccess(request, response,sb.toString());
				ServletUtils.outPrint(request, response, true, null, returnMap, true);
			}else{
				returnMap.put("code", result.getCode());
				returnMap.put("sendTypeCheck",itemPreMeasure.getSendTypeCheck());
				returnMap.put("applyQtyCheck",itemPreMeasure.getApplyQtyCheck());
				returnMap.put("info",result.getInfo());
				//ServletUtils.outPrintFail(request, response, "[{code="+result.getCode()+",sendTypeCheck= "+itemPreMeasure.getSendTypeCheck()+",applyQtyCheck= "+itemPreMeasure.getApplyQtyCheck()+",info= "+result.getInfo()+"}]");
				ServletUtils.outPrintFail(request, response, returnMap);
			}
		}catch(Exception e){
			Map<String,Object> returnExceptionMap = new HashMap<String, Object>();
			returnExceptionMap.put("code", "0");
			returnExceptionMap.put("info", "新增领料失败");
			//ServletUtils.outPrintFail(request, response, "[{code=0,info=新增领料失败}]");
			ServletUtils.outPrintFail(request, response, returnExceptionMap);
		}
	}
	/**
	 * 打包下载材料图片
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/downLoad")
	public void downLoad(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			String urlString = FileUploadUtils.DOWNLOAD_URL+ItemPreAppUploadRule.FIRST_LEVEL_PATH;
			String zip = SystemConfig.getProperty("itemPreApp", "", "photo")+no+"_"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss")+".zip";
			List<Map<String,Object>> list = itemPreAppPhotoService.getPhotoList(no);
			StringBuilder[] files = null;
			if(list != null && list.size() > 0){
				files = new StringBuilder[list.size()];
				for(int i=0;i<list.size();i++){
					files[i] = new StringBuilder();
					files[i].append(urlString+list.get(i).get("photoName"));
				}
			}
			ServletUtils.downLoadFiles(request,response,files,zip,true);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 测量后下单 下单取消
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doXdqx_clhxd")
	public void doXdqx_clhxd(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("测量后下单_下单取消开始");
		try{
			ItemPreMeasure ipm = itemPreAppService.get(ItemPreMeasure.class, itemPreMeasure.getPk());
			
			if(ipm.getStatus().trim().equals("1") || ipm.getStatus().trim().equals("2") || ipm.getStatus().trim().equals("3") || ipm.getStatus().trim().equals("4")){
				ipm.setStatus("5");
				ipm.setCancelRemark(itemPreMeasure.getCancelRemark());
				ipm.setCompleteCZY(getUserContext(request).getCzybh());
				ipm.setLastUpdatedBy(getUserContext(request).getCzybh());
				ipm.setCompleteDate(new Date());
				ipm.setLastUpdate(new Date());
				ipm.setActionLog("EDIT");
				itemPreAppService.update(ipm);
			}
			
			if(itemPreMeasure.getIsSend().equals("1")){
				Map<String,Object> map = itemPreAppService.findMessageInfo(itemPreMeasure.getPk());
				if(StringUtils.isNotBlank(map.get("CZYBH").toString())){
					PersonMessage pm = new PersonMessage();
					pm.setMsgType("8");
					pm.setMsgText(map.get("Address").toString()+":"+map.get("Descr").toString()+"测量申请被取消,取消原因:"+itemPreMeasure.getCancelRemark());
					pm.setMsgRelNo(map.get("pk").toString());
					pm.setMsgRelCustCode(map.get("CustCode").toString());
					pm.setCrtDate(new Date());
					pm.setSendDate(new Date());
					pm.setRcvType("1");
					pm.setRcvCzy(map.get("CZYBH").toString().trim());
					pm.setRcvDate(null);
					pm.setRcvStatus("0");
					pm.setIsPush("1");
					pm.setPushStatus("0");
					itemPreAppService.save(pm);
				}
			}
			ServletUtils.outPrintSuccess(request, response,"下单取消成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "测量后下单_下单取消失败");
		}
	}
	
	@RequestMapping("/getFsArea")
	@ResponseBody
	public Double getFsArea(HttpServletRequest request,HttpServletResponse response, String custCode){
		Double area = 0.0;
		Map<String,Object> map = itemPreAppService.getFsArea(custCode);
		if(map != null){
			area = Double.parseDouble(map.get("Area") == null ?"0":map.get("Area").toString());
		}
		return area; 
	}	
	
	@RequestMapping("getCustPayInfo")
	@ResponseBody
	public Map<String,Object> getCustPayInfo(HttpServletRequest request,HttpServletResponse response, String custCode){
		Map<String,Object> custPayInfoMap = itemPreAppService.getCustPayInfo(custCode);
		Map<String, Object> shouldPayInfoMap = customerService.getShouldBanlance(custCode);
		if(custPayInfoMap != null && shouldPayInfoMap != null){
			custPayInfoMap.putAll(shouldPayInfoMap);
		}
		return custPayInfoMap;
	}
	
	@RequestMapping("getSendCount")
	@ResponseBody
	public Integer getSendCount(HttpServletRequest request,HttpServletResponse response, String no){
		Map<String,Object> map = itemPreAppService.getSendCount(no);
		if(map != null ){
			return Integer.parseInt(map.get("num").toString());
		}
		return 0;
	}
	
	@RequestMapping("/getHasInSetReq")
	@ResponseBody
	public String getHasInSetReq(HttpServletRequest request,HttpServletResponse response,String custCode,String itemType1){
		if(itemPreAppService.hasInSetReq(custCode,itemType1)){
			return "1";
		}else{
			return "0";
		}
	}
	
	@RequestMapping("/checkCost")
	@ResponseBody
	public JSONObject checkCost(HttpServletRequest request,HttpServletResponse response,String custCode,String itemType2,String custType,String itemType1){
		JSONObject returnJson = new JSONObject();
		String alarmStr_cost = "";
		String alarmStr_allCost = "";
		
		String materWorkType2 = StringUtils.isNotBlank(itemType2)?itemPreAppService.get(ItemType2.class, itemType2).getMaterWorkType2():"";
		Map<String,Object> map = itemPreAppService.checkCost(custCode,materWorkType2);
		
		Double itemCost = Double.valueOf(map.get("itemCost").toString());
		Double itemCtrl = Double.valueOf(map.get("itemCtrl").toString());
		Double allItemCtrl = Double.valueOf(map.get("allItemCtrl").toString());
		Double allItemCost = Double.valueOf(map.get("allItemCost").toString());
		Double itemCostPer = 0.0;
		Double allItemCostPer = 0.0;
		Double ratio = 0.0;
		if(itemCtrl > 0){
			itemCostPer = (double) Math.round(itemCost/itemCtrl*100);
		}else{
			if("1".equals(custType) && "JZ".equals(itemType1.trim())){
				itemCostPer = 200.0;
			}else{
				itemCostPer = 0.0;
			}
		}
		if(allItemCtrl > 0){
			allItemCostPer = (double) Math.round(allItemCost/allItemCtrl*100);
		}
		if("JZMZ".equals(itemType2.trim())){
			ratio = 120.0;
			alarmStr_cost = "材料成本金额超过材料发包额120%";
		}else{
			ratio = 110.0;
			alarmStr_cost = "材料成本金额超过材料发包额110%";
		}

		if(itemCostPer > ratio || allItemCostPer > 90){
			if(itemCostPer > ratio){
				if(itemCostPer == 200){
					alarmStr_cost = "没有对应工种预算。";
				}else{
					alarmStr_cost = alarmStr_cost + ",占比为"+ MathUtil.stripTrailingZeros(itemCostPer) + "%,超出额:" +MathUtil.stripTrailingZeros(itemCost-itemCtrl) + "元.";
				}
			}else{
				alarmStr_cost = "";
			}
			if(allItemCostPer > 90){
				alarmStr_allCost = alarmStr_allCost + "总成本超过总发包额的90%,占比为"+MathUtil.stripTrailingZeros(allItemCostPer)+"%";
			}
			returnJson.put("tipVisible", true);
			returnJson.put("tips", alarmStr_cost+" "+alarmStr_allCost);
		}else{
			returnJson.put("tipVisible", false);
		}
		return returnJson;
	}
	
	@RequestMapping("/checkQty")
	@ResponseBody
	public JSONObject checkQty(HttpServletRequest request,HttpServletResponse response,String custCode,String appNo ,String itemType1){
		JSONObject returnJson = new JSONObject();

		String detailJson = request.getParameter("detailJson");
		Result result = itemPreAppService.checkQty(custCode,appNo,itemType1,XmlConverUtil.jsonToXmlNoHead(detailJson));

		if(result.isSuccess()){
			returnJson.put("tipVisible", true);
			returnJson.put("tips", result.getInfo());
		}else{
			returnJson.put("tipVisible", false);
		}
		returnJson.put("resultList", result.getJson());
		return returnJson;
	}
	
	@RequestMapping("/checkQtyByItemType2")
	@ResponseBody
	public JSONObject checkQtyByItemType2(HttpServletRequest request,HttpServletResponse response,String custCode,String appNo ,String itemType1){
		JSONObject returnJson = new JSONObject();
		
		List<Map<String,Object>> list = itemPreAppService.checkQtyByItemType2(custCode,appNo,itemType1);
		
		if(list != null && list.size() > 0){
			returnJson.put("tipVisible", true);
			returnJson.put("tips","审核之后,有些品类的已领料数量将大于需求数量(点击查看明细)!");
		}else{
			returnJson.put("tipVisible", false);
		}
		returnJson.put("resultList", list.toString());
		return returnJson;
	}
	
	@RequestMapping("/checkCustStatus")
	@ResponseBody
	public JSONObject checkCustStatus(HttpServletRequest request,HttpServletResponse response,String custCode){
		JSONObject returnJson = new JSONObject();
		String alarmStr = "";
		
		Map<String,Object> map = itemPreAppService.checkCustStatus(custCode);
		
		if(map != null && "5".equals(map.get("status").toString())){
			if("5".equals(map.get("status").toString())){
				alarmStr = "楼盘状态:"+map.get("StatusDescr")+",客户结算状态:"+map.get("CheckStatusDescr");
			}
		}
		
		if(alarmStr.length()>0){
			returnJson.put("tipVisible", true);
			returnJson.put("tips",alarmStr);
		}else{
			returnJson.put("tipVisible", false);
		}
		return returnJson;
	}
	
	@RequestMapping("/checkCustPay")
	@ResponseBody
	public JSONObject checkCustPay(HttpServletRequest request,HttpServletResponse response,String custCode,String appNo,
									Double firstPay,Double secondPay,Double thirdPay,Double fourPay,Double designFee,Double chgFee,Double custPay){
		JSONObject returnJson = new JSONObject();
		StringBuilder alarmStr = new StringBuilder();
		String payNumStr = "";
		String payType = itemPreAppService.get(Customer.class, custCode).getPayType();
		String payNum = "";
		Double payPer = 0.0;
		Double diffAmount = 0.0;
		Double itemCost = 0.0;
		Double stdDesignFee = 0.0;
		Double resultDesignFee = 0.0;
		Double chgPer = 0.0;
		Double isRcvDesignFee = 0.0;
		
		Map<String,Object> map = itemPreAppService.checkCustPay(appNo);
		
		if(map != null){
			payNum = map.get("PayNum").toString();
			payPer = Double.valueOf(map.get("PayPer").toString());
			diffAmount = Double.valueOf(map.get("DiffAmount").toString());
			itemCost = Double.valueOf(map.get("ItemCost").toString());
			stdDesignFee = Double.valueOf(map.get("StdDesignFee").toString());
			chgPer = Double.valueOf(map.get("ChgPer").toString());
			
			if("1".equals(map.get("IsRcvDesignFee").toString())){
				isRcvDesignFee = 1.0;
			}
			
			if("2".equals(map.get("DesignFeeType").toString())){//"1".equals(map.get("Custtype"))  && "2".equals(payType) 根据规则配置
				resultDesignFee = stdDesignFee;
			}else{
				resultDesignFee = designFee;
			}

			if("1".equals(payNum)){
				if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay*payPer-custPay, 4) > diffAmount){
					alarmStr.append("一期:");
					if(resultDesignFee * isRcvDesignFee > 0){
						alarmStr.append(MathUtil.stripTrailingZeros(resultDesignFee));
					}
					
					if(chgFee * chgPer > 0){
						alarmStr.append("+"+MathUtil.stripTrailingZeros(chgFee*chgPer));
					}else if(chgFee * chgPer < 0){
						alarmStr.append(MathUtil.stripTrailingZeros(chgFee*chgPer));
					}
					
					if(resultDesignFee * isRcvDesignFee > 0 || chgFee * chgPer != 0){
						alarmStr.append("+");
					}
					alarmStr.append(MathUtil.stripTrailingZeros(firstPay));
					if(payPer == 1){
						alarmStr.append("-");
					}else{
						alarmStr.append("*"+MathUtil.stripTrailingZeros(payPer*100)+"%-");
					}
					alarmStr.append(MathUtil.stripTrailingZeros(custPay)+"="
									+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay*payPer-custPay, 4)));
				}
			}else if("2".equals(payNum)){
				if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay*payPer-custPay, 4) > diffAmount){
					alarmStr.append("二期:"+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay*payPer-custPay, 4)));
				}
			}else if("3".equals(payNum)){
				if(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay > diffAmount){
					if(fourPay == 0){//没有四期款，三期款即尾款
						payNumStr = "尾款:";
					}else{
						payNumStr = "三期:";
					}
		        	if(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay > thirdPay){
		    	            alarmStr.append(payNumStr+MathUtil.stripTrailingZeros(thirdPay)+ " 二期:"
		    	            			    +MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay-thirdPay, 4)));
		        	}else{
		        		alarmStr.append(payNumStr+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay, 4)));
		        	}
				}
			}else if("4".equals(payNum)){
		        if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay+fourPay*payPer-custPay,4) > diffAmount){
		        	alarmStr.append("尾款:"+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay+fourPay*payPer-custPay, 4)));
		        }
			}
			
		}
		
		if(alarmStr.length()>0){
			returnJson.put("tipVisible", true);
			returnJson.put("tips",alarmStr.toString());
		}else{
			returnJson.put("tipVisible", false);
		}
		return returnJson;
	}
	
	/**
	 * itemPreApp_code获取信息
	 * @param request
	 * @param response
	 * @param photoName
	 * @return
	 */
	@RequestMapping("/getItemPreApp")
	@ResponseBody
	public JSONObject getItemPreApp(HttpServletRequest request, HttpServletResponse response,String no){
		if(StringUtils.isEmpty(no)){
			return this.out("传入的no为空", false);
		}
		ItemPreApp itemPreApp = itemPreAppService.get(ItemPreApp.class, no);
		if(itemPreApp == null){
			return this.out("系统中不存在code="+no+"的领料预申请单信息", false);
		}
		return this.out(itemPreApp, true);
	}
	/**
	 * 加载导入的Excel数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> resultMap=new HashMap<String, Object>();
		ExcelImportUtils<ItemAppImportExcelBean> eUtils=new ExcelImportUtils<ItemAppImportExcelBean>();
		String itemType1 = "";
		String itemType2 = "";
		String no = "";
		String custCode = "";
		String[] itemCodeArr = null;
		InputStream inputStream=null;
		
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			String fieldName = obit.getFieldName();
			String fieldValue = obit.getString();
			if ("itemType1".equals(fieldName)){
				itemType1 = fieldValue.trim();
			}
			if ("itemType2".equals(fieldName)){
				itemType2 = fieldValue.trim();
			}
			if ("itemCodes".equals(fieldName)){
				String itemCodes = fieldValue.trim();
				itemCodeArr = itemCodes.split(",");
			}
			if ("no".equals(fieldName)){
				no = fieldValue.trim();
			}
			if ("custCode".equals(fieldName)){
				custCode = fieldValue.trim();
			}
			if ("file".equals(fieldName)){
				inputStream=obit.getInputStream();
			}
		}
		   
		
		List<String> titleList=new ArrayList<String>();
		titleList.add("材料编号");
		titleList.add("备注");
		titleList.add("采购数量");
		titleList.add("成品名称");
		titleList.add("项目");
		try{
			List<ItemAppImportExcelBean> result=eUtils.importExcel(inputStream, ItemAppImportExcelBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			if(result == null || result.size() == 0){
				resultMap.put("success", false);
				resultMap.put("returnInfo", "无数据导入");
				resultMap.put("hasInvalid", true);
				if(inputStream != null){
					inputStream.close();
				}
				return resultMap;
			}
			for(ItemAppImportExcelBean itemAppImportExcelBean:result){
				if(StringUtils.isNotBlank(itemAppImportExcelBean.getError())){
					resultMap.put("success", false);
					resultMap.put("returnInfo", itemAppImportExcelBean.getError());
					resultMap.put("hasInvalid", true);
					if(inputStream != null){
						inputStream.close();
					}
					return resultMap;
				}
				Map<String,Object> data=new HashMap<String, Object>();
				if(StringUtils.isBlank(itemAppImportExcelBean.getItemCode())){
					resultMap.put("success", false);
					resultMap.put("returnInfo", "请检查材料编号是否填写");
					resultMap.put("hasInvalid", true);
					if(inputStream != null){
						inputStream.close();
					}
					return resultMap;
				}
				if(itemAppImportExcelBean.getQty() == null){
					resultMap.put("success", false);
					resultMap.put("returnInfo", "请检查采购数量是否填写");
					resultMap.put("hasInvalid", true);
					if(inputStream != null){
						inputStream.close();
					}
					return resultMap;
				}
				
				Map<String, Object> itemMap = itemPreAppService.getItemInfoByCode(itemAppImportExcelBean.getItemCode(), itemType1, itemType2, no, custCode);
				
				//验证材料是否存在
				if("1".equals(itemMap.get("codeError"))){
					data.put("isinvalid", "F");
					data.put("isinvaliddescr", "无效,材料无效");
				}
				//验证材料的材料类型1、材料类型2是否同主表一致
				if(!"1".equals(itemMap.get("itemTypeCheck").toString())){
					data.put("isinvalid", "F");
					data.put("isinvaliddescr", "无效,材料类型1或材料类型2有误");
				}
				//验证是否有重复数据
				for(int i = 0;itemCodeArr != null && i < itemCodeArr.length;i++){
					if(itemCodeArr[i].equals(itemAppImportExcelBean.getItemCode())){
						data.put("isinvalid", "F");
						data.put("isinvaliddescr", "无效,存在重复数据");
						break;
					}
				}
				
				Map<String, Object> map = itemPreAppService.getIntProd(itemAppImportExcelBean.getProductName(), custCode);
				
				//验证结束,获取该记录
				data.put("itemcode", itemAppImportExcelBean.getItemCode());
				data.put("qty", itemAppImportExcelBean.getQty() == null?0.0:itemAppImportExcelBean.getQty());
				StringBuilder remarks = new StringBuilder();
				if(StringUtils.isNotBlank(itemAppImportExcelBean.getProductName())){
					remarks.append(itemAppImportExcelBean.getProductName());
				}
				if(StringUtils.isNotBlank(itemAppImportExcelBean.getItem())){
					if(remarks.toString().length()>0){
						remarks.append(",");
					}
					remarks.append(itemAppImportExcelBean.getItem());
				}
				if(StringUtils.isNotBlank(itemAppImportExcelBean.getRemarks())){
					if(remarks.toString().length()>0){
						remarks.append(",");
					}
					remarks.append(itemAppImportExcelBean.getRemarks());
				}
				data.put("productname", itemAppImportExcelBean.getProductName());
				data.put("remarks", remarks.toString());
				data.put("itemdescr", itemMap.get("Descr"));
				data.put("cost", itemMap.get("Cost") == null ? 0.0:itemMap.get("Cost"));
				data.put("projectcost", itemMap.get("ProjectCost") == null ? 0.0:itemMap.get("ProjectCost"));
				data.put("uomdescr", itemMap.get("UomDescr"));
				data.put("itemtype1descr", itemMap.get("ItemType1Descr"));
				data.put("itemtype2descr", itemMap.get("ItemType2Descr"));
				data.put("itemtype3descr", itemMap.get("ItemType3Descr"));
				data.put("sqlcodedescr", itemMap.get("SqlDescr"));
				data.put("sendtypedescr", itemMap.get("SendTypeDescr"));
				data.put("color", itemMap.get("Color"));
				data.put("model", itemMap.get("Model"));
				data.put("sizedesc", itemMap.get("SizeDesc"));
				data.put("barcode", itemMap.get("BarCode"));
				data.put("isfixpricedescr", itemMap.get("IsFixPriceDescr"));
				data.put("price", itemMap.get("Price"));
				data.put("no", 1);
				data.put("reqpk", 0);
				data.put("fixareapk", map == null ? 0: Integer.valueOf(map.get("FixAreaPK").toString()));
				data.put("intprodpk", map == null ? 0: Integer.valueOf(map.get("intProdPk").toString()));
				data.put("intproddescr", map == null ? "": map.get("intProdDescr").toString());
				data.put("fixareadescr", map == null ? "": map.get("fixAreaDescr").toString());
				data.put("reqqty", 0);
				data.put("sendedqty", 0);
				data.put("declareqty", itemAppImportExcelBean.getQty() == null?0.0:itemAppImportExcelBean.getQty());
				data.put("supplcodedescr", itemMap.get("SplCodeDescr"));
				data.put("reqprocesscost", itemMap.get("ReqProcessCost") == null ? 0.0:itemMap.get("ReqProcessCost"));
				data.put("applyqty", itemMap.get("ApplyQty") == null ? 0.0:itemMap.get("ApplyQty"));
				data.put("confirmedqty", itemMap.get("ConfirmedQty") == null ? 0.0:itemMap.get("ConfirmedQty"));
				data.put("allcost", itemMap.get("Cost") == null ?0.0:Double.parseDouble(itemMap.get("Cost").toString())*itemAppImportExcelBean.getQty());
				data.put("allprojectcost", itemMap.get("ProjectCost") == null ?0.0:Double.parseDouble(itemMap.get("ProjectCost").toString())*itemAppImportExcelBean.getQty());
				data.put("weight", itemMap.get("PerWeight") == null ?0.0:Double.parseDouble(itemMap.get("PerWeight").toString())*itemAppImportExcelBean.getQty());
				data.put("sendtype", itemMap.get("SendType"));
				data.put("supplcode",itemMap.get("SupplCode"));
				data.put("whcode", itemMap.get("WHCode"));
				Double packageNum = itemMap.get("PackageNum") == null ? 0.0:Double.parseDouble(itemMap.get("PackageNum").toString());
				Double perNum = itemMap.get("PerNum") == null ? 0.0:Double.parseDouble(itemMap.get("PerNum").toString());
				if(packageNum == 1.0 || packageNum == 0.0 || itemAppImportExcelBean.getQty()*perNum < packageNum){
					data.put("numdescr", itemAppImportExcelBean.getQty()*perNum);
				}else if(itemAppImportExcelBean.getQty()*perNum/packageNum == Math.floor(itemAppImportExcelBean.getQty()*perNum/packageNum)){
					data.put("numdescr", (itemAppImportExcelBean.getQty()*perNum/packageNum)+"箱");
				}else{
					data.put("numdescr", Math.floor(itemAppImportExcelBean.getQty()*perNum/packageNum)+"箱"
										+(itemAppImportExcelBean.getQty()*perNum-packageNum*Math.floor(itemAppImportExcelBean.getQty()*perNum/packageNum))+"片");
				}
				if("F".equals(data.get("isinvalid"))){
					resultMap.put("hasInvalid", true);
				}else{
					data.put("isinvalid", "T");
					data.put("isinvaliddescr", "有效");
				}
				
				datas.add(data);
			}
			if(inputStream != null){
				inputStream.close();
			}
			resultMap.put("success", true);
			resultMap.put("returnInfo", "数据加载完成");
			resultMap.put("datas", datas);
			return resultMap;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(inputStream != null){
				inputStream.close();
			}
			resultMap.put("success", false);
			resultMap.put("returnInfo", "当前操作使用了错误类型的值,请检查数值列是否包含非法字符");
			resultMap.put("hasInvalid", true);
			return resultMap;
		}
	}
	
	@RequestMapping("/doEditRemark")
	public void doEditRemark(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure measure){
		logger.debug("录入备注开始");
		try{
			if("1".equals(measure.getFrom())){
				ItemPreApp itemPreApp = itemPreAppService.get(ItemPreApp.class, measure.getNo());
				if(itemPreApp != null){
					itemPreApp.setRemarks(measure.getRemarks());
					itemPreApp.setLastUpdate(new Date());
					itemPreApp.setLastUpdatedBy(getUserContext(request).getCzybh());
					itemPreApp.setActionLog("EDIT");
					itemPreAppService.update(itemPreApp);
					ServletUtils.outPrintSuccess(request, response,"录入备注成功");
				}else{
					ServletUtils.outPrintFail(request, response, "录入备注异常");
				}
			}else{
				ItemPreMeasure itemPreMeasure = itemPreAppService.get(ItemPreMeasure.class, measure.getPk());
				if(itemPreMeasure != null){
					itemPreMeasure.setRemarks(measure.getRemarks());
					itemPreMeasure.setLastUpdate(new Date());
					itemPreMeasure.setLastUpdatedBy(getUserContext(request).getCzybh());
					itemPreMeasure.setActionLog("EDIT");
					// 勾选通知管家变更，并且为未变更才生成通知
					if("1".equals(measure.getInformManager()) && "0".equals(itemPreMeasure.getChgStatus())){
						itemPreMeasure.setChgStatus("1");
						itemPreMeasure.setInformDate(new Date());
						
						// 生成消息
						PersonMessage personMessage = new PersonMessage();
						personMessage.setMsgType("2");
						personMessage.setMsgText(measure.getAddress()+":"+measure.getRemarks());
						personMessage.setMsgRelNo(itemPreMeasure.getPk().toString());
						personMessage.setMsgRelCustCode(measure.getCustCode());
						personMessage.setCrtDate(new Date());
						personMessage.setSendDate(new Date());
						personMessage.setRcvType("3");
						personMessage.setRcvCzy(measure.getInformManagerCode());
						personMessage.setIsPush("1");
						personMessage.setPushStatus("0");
						personMessage.setRcvStatus("0");
						itemPreAppService.save(personMessage);
					}
					itemPreAppService.update(itemPreMeasure);
					
					ServletUtils.outPrintSuccess(request, response,"录入备注成功");
				}else{
					ServletUtils.outPrintFail(request, response, "录入备注异常");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "录入备注失败");
		}
	}
	
	@RequestMapping("/remeasure")
	public void remeasure(HttpServletRequest request, HttpServletResponse response,
	        ItemPreMeasure itemPreMeasure) {
	    
	    if (itemPreMeasure.getPk() == null) {
            ServletUtils.outPrintFail(request, response, "错误：缺少材料预申请主键");
            return;
        }
	    
	    itemPreMeasure = itemPreAppService.get(ItemPreMeasure.class, itemPreMeasure.getPk());
	    if (itemPreMeasure == null) {
	        ServletUtils.outPrintFail(request, response, "错误：不存在的材料预申请记录");
            return;
        }
	    
	    if (!itemPreMeasure.getStatus().trim().equals("2")) {
	        ServletUtils.outPrintFail(request, response, "错误：非退回状态的材料预申请记录不能重新测量");
	        return;
	    }
	    
        try {
            
            Date now = new Date();
            itemPreMeasure.setStatus("1");
            itemPreMeasure.setDate(now);
            itemPreMeasure.setRecvDate(null);
            itemPreMeasure.setLastUpdate(now);
            itemPreMeasure.setLastUpdatedBy(getUserContext(request).getCzybh());
            itemPreMeasure.setActionLog("EDIT");
            
            itemPreAppService.update(itemPreMeasure);

            ServletUtils.outPrintSuccess(request, response, "操作成功");
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "操作失败");
            e.printStackTrace();
        }
	    
	}
	
	@RequestMapping("/isIndependCust")
	@ResponseBody
	public JSONObject isIndependCust(HttpServletRequest request, HttpServletResponse response, String custCode){
		JSONObject returnJson = new JSONObject();
		
		boolean isIndependCust = false;
		
		if(StringUtils.isNotBlank(custCode)){
			Customer customer = itemPreAppService.get(Customer.class, custCode);
			if(customer != null){
				CustType custType = itemPreAppService.get(CustType.class, customer.getCustType());
				if(custType != null){
					if("0".equals(custType.getIsAddAllInfo().trim())){
						returnJson.put("phone", customer.getMobile1());
						isIndependCust = true;
					}
				}
			}
		}
		returnJson.put("isIndependCust", isIndependCust);
		return returnJson;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/checkAllInfo")
	public void checkAllInfo(HttpServletRequest request, HttpServletResponse response, String appItemCodes, String itemCode, String custCode, String isSetItem){		
		logger.debug("接收检查开始");
		try{
			Page page = new Page();
			page.setPageNo(1);
			page.setPageSize(-1);
			itemAppTempAreaDetailService.findCheckAppItemAllPageBySql(page, appItemCodes+",", itemCode, custCode, isSetItem);
			ServletUtils.outPrintSuccess(request, response, page.getResult());
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "接收检查异常");
			e.printStackTrace();
		}
	}
	/**
	 * 跳转到新增领料的快速新增（集成解单导入）页面 itemPreAppManage_list_addll_jddr
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param itemType2
	 * @param isSetItem
	 * @param custCode
	 * @param appNo
	 * @param itemCodes
	 * @return
	 */
	@RequestMapping("/goJddr_addll")
	public ModelAndView goJddr_addll(HttpServletRequest request, HttpServletResponse response,String custCode,
			String specreqpks,String isCupboard,String appNo){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("custCode", custCode);
		map.put("specreqpks", specreqpks);
		map.put("isCupboard", isCupboard);
		map.put("appNo", appNo);
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_jddr").addObject("data", map);
	}
	/**
	 *  查询JqGrid表格数据,快速新增（集成解单导入）itemPreAppManage_list_addll_jddr
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@RequestMapping("/goJddrJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJddrJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemPreAppService.goJddrJqGrid(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);		
	}
	/**
	 * 查是否解单材料
	 * @param request
	 * @param response
	 * @param itemType1
	 * @return
	 */
	@RequestMapping("/checkIsSpecReq")
	@ResponseBody
	public String checkIsSpecReq(HttpServletRequest request,HttpServletResponse response,String itemType1){
		return itemPreAppService.checkIsSpecReq(itemType1);
	}
	/**
	 * 跳转到核对成本控制
	 * @param request
	 * @param response
	 * @param resultList
	 * @param isCupboard
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/goIntCostTipClickPage")
	public ModelAndView goIntCostTipClickPage(HttpServletRequest request, HttpServletResponse response,String resultList,String isCupboard,String custCode){
		Xtcs intInsCalTyp = itemPreAppService.get(Xtcs.class, "IntInsCalTyp");
		Xtcs cupInsCalTyp = itemPreAppService.get(Xtcs.class, "CupInsCalTyp");
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_tabView_intCostTipClickPage")
		.addObject("resultList", resultList).addObject("isCupboard", isCupboard).addObject("custCode", custCode)
		.addObject("intInsCalTyp", intInsCalTyp.getQz()).addObject("cupInsCalTyp", cupInsCalTyp.getQz());
	}
	/**
	 * 领料成本明细
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLlcbJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLlcbJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goLlcbJqGrid(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 橱柜安装成本明细
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCgazcbJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCgazcbJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goCgazcbJqGrid(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 衣柜安装成本明细
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goYgazcbJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goYgazcbJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goYgazcbJqGrid(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 衣柜安装成本明细
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goXqazcbJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goXqazcbJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goXqazcbJqGrid(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 集成利润分析材料明细
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goIntProDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goIntProDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goIntProDetailJqGrid(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到自动拆单 itemPreAppManage_list_zdcd
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/goAutoOrder")
	public ModelAndView goAutoOrder(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){		
		Map<String,Object> map = itemPreAppService.getMeasureInfoByNo(itemPreApp.getNo());
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(map, itemPreMeasure);
		itemPreMeasure.setM_umState("A");
		itemPreMeasure.setItemAppStatus("OPEN");
		itemPreMeasure.setItemAppType("S");
		itemPreMeasure.setIsService("0");
		itemPreMeasure.setAppNo("保存时生成");
		itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
		itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		if(itemPreAppService.containServiceItem(itemPreMeasure.getPreAppNo())){
			itemPreMeasure.setServiceTip("服务性产品需下单");
		}
		
		if(itemPreAppService.hasInSetReq(itemPreMeasure.getCustCode(), itemPreMeasure.getItemType1())){
			itemPreMeasure.setHasInSetReq("1");
		}else{
			itemPreMeasure.setHasInSetReq("0");
		}
		itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
		itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_autoOrder").addObject("itemPreMeasure", itemPreMeasure);
	}
	/**
	 * 查询JqGrid表格数据,自动拆单
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/goAutoOrderJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goAutoOrderJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		itemPreAppService.goAutoOrderJqGrid(page,itemPreMeasure);
		Xtcs xtcs = itemPreAppService.get(Xtcs.class, "ItemConfCtrl");
		Integer cantOrderNum=0;//不能拆单的记录数
		if("1".equals(xtcs.getQz()) || "2".equals(xtcs.getQz())){
			List<Map<String,Object>> custItemConfList = itemPreAppService.custItemConf(itemPreMeasure.getCustCode());
			List<Map<String,Object>> oldPageList=page.getResult();
			for (int i = 0; i < oldPageList.size(); i++) {
				int j=0;
				boolean flag=true;
				for (; j < custItemConfList.size(); j++) {
					if(oldPageList.get(i).get("itemtype2").toString().trim().equals(custItemConfList.get(j).get("ItemType2").toString().trim())){
						flag=false;
					}
					if("1".equals(custItemConfList.get(j).get("isConf").toString()) && ((
							(null==custItemConfList.get(j).get("ItemType3")||(null!=custItemConfList.get(j).get("ItemType3")&&StringUtils.isBlank(custItemConfList.get(j).get("ItemType3").toString()))) 
							&& (oldPageList.get(i).get("itemtype2").toString().trim().equals(custItemConfList.get(j).get("ItemType2").toString().trim()))
							)||(!"".equals(oldPageList.get(i).get("itemtype3"))&&custItemConfList.get(j).get("ItemType3")!=null
							&&oldPageList.get(i).get("itemtype3").toString().trim().equals(custItemConfList.get(j).get("ItemType3").toString().trim()
							))&&(oldPageList.get(i).get("itemtype2").toString().trim().equals(custItemConfList.get(j).get("ItemType2").toString().trim()
							)))){
						break;
					}
				}
				if(!(("1".equals(oldPageList.get(i).get("ordertype").toString()) || "2".equals(oldPageList.get(i).get("ordertype").toString()))&&j<custItemConfList.size() || flag)||"不下单".equals(oldPageList.get(i).get("ordernamecode").toString())){
					oldPageList.get(i).put("ordername", "不下单");
					oldPageList.get(i).put("ordernamecode", "不下单");
					oldPageList.get(i).put("delivtype", "");
					cantOrderNum+=1;
				}
			}
			request.getSession().setAttribute("cantordernum", cantOrderNum);
			Collections.sort(oldPageList, new ListCompareUtil("ordername"));//排序
			page.setResult(oldPageList);
		}
		return new WebPage<Map<String,Object>>(page);		
	}
	/**
	 * 跳转到新增领料的从预申请导入页面 itemPreAppManage_list_addll_preApply
	 * @param request
	 * @param response
	 * @param custCode
	 * @param preAppNo
	 * @param appNo
	 * @param itemType1
	 * @param itemType2
	 * @param isService
	 * @param m_umState
	 * @param preAppDTPks
	 * @param reqPks
	 * @param itemCodes
	 * @param isSetItem
	 * @return
	 */
	@RequestMapping("/goInsertAutoOrder")
	public ModelAndView goInsertAutoOrder(HttpServletRequest request, HttpServletResponse response,String custCode,String preAppNo,String appNo,String itemType1,
			String itemType2,String isService,String m_umState,String preAppDTPks,String reqPks,String itemCodes,String isSetItem,String ordername,String isInsert,String ordernamecode){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = itemPreAppService.custItemConf(custCode);
		Xtcs xtcs = itemPreAppService.get(Xtcs.class, "ItemConfCtrl");
		map.put("itemConfCtrl", xtcs.getQz());
		map.put("custItemConf",list);
		map.put("custCode", custCode);
		map.put("preAppNo", preAppNo);
		map.put("appNo", appNo);
		map.put("itemType1", itemType1);
		map.put("itemType2", itemType2);
		map.put("isService", isService);
		map.put("m_umState", m_umState);
		map.put("reqPks", reqPks);
		map.put("preAppDTPks", preAppDTPks);
		map.put("itemCodes", itemCodes);
		map.put("isSetItem", isSetItem);
		map.put("ordername", ordername);
		map.put("isInsert", isInsert);
		map.put("ordernamecode", ordernamecode);
		
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll_insertAutoOrder").addObject("data", map);
	}
	
	/**
	 * 新增领料 保存
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doSave_autoOrder")
	public void doSave_autoOrder(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("新增领料开始");
		try{
			Xtcs xtcs = itemPreAppService.get(Xtcs.class, "CmpCustCode");
			String[] qzs = xtcs.getQz().split(",");
			itemPreMeasure.setIsCmpCustCode("0");
			if(!"R".equals(itemPreMeasure.getM_umState()) && !"D".equals(itemPreMeasure.getM_umState())){
				for(int i=0;i<qzs.length;i++){
					if(qzs[i].equals(itemPreMeasure.getCustCode())){
						itemPreMeasure.setIsCmpCustCode("1");
						break;
					}
				}
			}
			String detailJson = request.getParameter("detailJson");
			itemPreMeasure.setSendTypeCheck(request.getParameter("sendTypeCheck"));
			itemPreMeasure.setApplyQtyCheck(request.getParameter("applyQtyCheck"));
			itemPreMeasure.setAppCzy(getUserContext(request).getCzybh());
			if("A".equals(itemPreMeasure.getM_umState())){
				itemPreMeasure.setItemAppStatus("");
			}
			Result result = itemPreAppService.autoOrderForProc(itemPreMeasure, XmlConverUtil.jsonToXmlNoHead(detailJson));
			Map<String,Object> returnMap = new HashMap<String, Object>();
			if(result.isSuccess()){
				returnMap.put("code", "0");
				returnMap.put("info", "保存成功");
				//ServletUtils.outPrintSuccess(request, response,sb.toString());
				ServletUtils.outPrint(request, response, true, null, returnMap, true);
			}else{
				returnMap.put("code", result.getCode());
				returnMap.put("sendTypeCheck",itemPreMeasure.getSendTypeCheck());
				returnMap.put("applyQtyCheck",itemPreMeasure.getApplyQtyCheck());
				returnMap.put("info",result.getInfo());
				//ServletUtils.outPrintFail(request, response, "[{code="+result.getCode()+",sendTypeCheck= "+itemPreMeasure.getSendTypeCheck()+",applyQtyCheck= "+itemPreMeasure.getApplyQtyCheck()+",info= "+result.getInfo()+"}]");
				ServletUtils.outPrintFail(request, response, returnMap);
			}
		}catch(Exception e){
			
			Map<String,Object> returnExceptionMap = new HashMap<String, Object>();
			returnExceptionMap.put("code", "0");
			returnExceptionMap.put("info", "新增领料失败");
			//ServletUtils.outPrintFail(request, response, "[{code=0,info=新增领料失败}]");
			ServletUtils.outPrintFail(request, response, returnExceptionMap);
		}
	}
	/**
	 * 获取不能拆单的记录数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCantOrderNum")
	public String getCantOrderNum(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("获取不能拆单的记录数量");
		return request.getSession().getAttribute("cantordernum").toString();
	}
	/**
	 * 更新备注
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateRemarks")
	public void updateRemarks(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) {
		logger.debug("更新备注");
		itemPreMeasure.setLastUpdatedBy(getUserContext(request).getCzybh());
		itemPreAppService.updateRemarks(itemPreMeasure);
	}
	/**
	 * 跳转到通知监理页面
	 * @date 2019-7-2
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 * @return
	 */
	@RequestMapping("/goNotify")
	public ModelAndView goNotify(HttpServletRequest request, HttpServletResponse response,ItemPreMeasure itemPreMeasure){
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_notify").addObject("data", itemPreMeasure);
	}
	/**
	 * 通知监理
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doNotify")
	public void doNotify(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) {
		logger.debug("通知监理");
		itemPreAppService.doNotify(itemPreMeasure);
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", "0");
		returnMap.put("info", "保存成功");
		ServletUtils.outPrint(request, response, true, null, returnMap, true);
	}
	@RequestMapping("/checkKgQty")
	@ResponseBody
	public String checkKgQty(HttpServletRequest request,HttpServletResponse response,ItemPreMeasure itemPreMeasure){
		String overQty= itemPreAppService.checkKgQty(itemPreMeasure);
		return overQty;
	}
	@RequestMapping("/isStopWork")
	@ResponseBody
	public String isStopWork(HttpServletRequest request,HttpServletResponse response,ItemPreMeasure itemPreMeasure){
		List<Map<String, Object>>list= itemPreAppService.isStopWork(itemPreMeasure.getCustCode());
		if(list.size()>0){
			return "1";
		}
		return "0";
	}
	@RequestMapping("/checkSendCmpWh")
	@ResponseBody
	public String checkSendCmpWh(HttpServletRequest request,HttpServletResponse response,ItemPreMeasure itemPreMeasure){
		List<Map<String, Object>>list= itemPreAppService.checkSendCmpWh(itemPreMeasure);
		if(list.size()>0){
			return "1";
		}
		return "0";
	}

	@RequestMapping("/goJqGridItemPlanSoftSend")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridItemPlanSoftSend(HttpServletRequest request,HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.goJqGridItemPlanSoftSend(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 获取发货次数
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping("/getSoftSendCount")
	@ResponseBody
	public String getSoftSendCount(HttpServletRequest request,HttpServletResponse response,
			String custCode, String itemType1){
		return itemPreAppService.getSoftSendCount(custCode, itemType1);
	}
	
	/**
	 * 后台拆单 保存
	 * @param request
	 * @param response
	 * @param itemPreMeasure
	 */
	@RequestMapping("/doBackstageOrder")
	public void doBackstageOrder(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.info("后台拆单");
		try{
			Xtcs xtcs = itemPreAppService.get(Xtcs.class, "CmpCustCode");
			String[] qzs = xtcs.getQz().split(",");
			itemPreMeasure.setIsCmpCustCode("0");
			for(int i=0;i<qzs.length;i++){
				if(qzs[i].equals(itemPreMeasure.getCustCode())){
					itemPreMeasure.setIsCmpCustCode("1");
					break;
				}
			}
			itemPreMeasure.setAppCzy(getUserContext(request).getCzybh());
			Result result = itemPreAppService.doBackstageOrder(itemPreMeasure);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据预申请单号查数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMeasureInfoByNo")
	public Map<String,Object> getMeasureInfoByNo(HttpServletRequest request,
			HttpServletResponse response,String no) {
		logger.info("根据预申请单号查数据");
		return itemPreAppService.getMeasureInfoByNo(no);
	}
	
	@RequestMapping("/goBatchSave")
	public ModelAndView goBatchSave(HttpServletRequest request, HttpServletResponse response, ItemPreApp itemPreApp){
		itemPreApp.setDateFrom(DateUtil.addDate(new Date(), -7));
		itemPreApp.setDateTo(new Date());
		
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_batchSave").addObject("itemPreApp", itemPreApp);
	}
	
	@RequestMapping("/goItemShouldOrderJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemShouldOrderJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppService.findItemShouldOrderJqGrid(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *ItemPreApp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doBatchSaveExcel")
	public void doBatchSaveExcel(HttpServletRequest request, 
			HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemPreAppService.findItemShouldOrderJqGrid(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"批量新增--"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	
	/**
	 * 批量新增保存数据
	 * @param request
	 * @param response
	 * @param laborFee
	 */
	@RequestMapping("/doBatchSave")
	public void doBatchSave(HttpServletRequest request,
			HttpServletResponse response, ItemPreApp itemPreApp) {
		logger.debug("领料预申请管理批量新增");
		try {
			String detailJson = request.getParameter("detailJson");
			itemPreApp.setDetailJson(detailJson);
			itemPreApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.itemPreAppService.doBatchSave(itemPreApp);
			if (result.isSuccess()) {
				ServletUtils.outPrint(request, response, true, "保存成功", null, true);
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "批量新增失败");
			e.printStackTrace();
		}
	}
}
