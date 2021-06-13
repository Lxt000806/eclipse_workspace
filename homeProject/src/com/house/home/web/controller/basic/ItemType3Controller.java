package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.bean.WebPage;
import com.house.home.service.basic.ItemType1Service;
import com.house.home.service.basic.ItemType2Service;
import com.house.home.service.basic.ItemType3Service;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.ItemType3;
import com.sun.org.apache.bcel.internal.generic.IXOR;

@Controller
@RequestMapping("/admin/itemType3")
public class ItemType3Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemType3Controller.class);

	@Autowired
	private ItemType3Service itemType3Service;
	@Autowired
	private ItemType2Service itemType2Service;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemType3
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemType3 itemType3) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemType3.setItemType1(itemType3.getItemType1());
		itemType3Service.findPageBySql(page, itemType3);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ItemType3列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemType3/itemType3_list");
	}
	
	/**
	 * 跳转到新增ItemType3页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增材料类型3页面");
		ItemType3 itemType3 = null;
		if (StringUtils.isNotBlank(id)){
			itemType3 = itemType3Service.get(ItemType3.class, id);
			itemType3.setCode(null);
		}else{
			itemType3 = new ItemType3();
		}
		
		return new ModelAndView("admin/insales/itemType3/itemType3_save")
			.addObject("itemType3", itemType3);
	}
	/**
	 * 跳转到修改ItemType3页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,HttpServletResponse response,String id){
		logger.debug("跳转到复制材料类型3页面");
		ItemType3 itemType3 = null;
		if (StringUtils.isNotBlank(id)) {
			itemType3 = itemType3Service.get(ItemType3.class,id);
			ItemType2 itemType2 = null;
			if (itemType3!=null) {
				itemType2 = itemType2Service.get(ItemType2.class, itemType3.getItemType2());
				itemType3.setItemType1(itemType2.getItemType1());
			}
		} else {
			itemType3 = new ItemType3();
		}
		return new ModelAndView("admin/insales/itemType3/itemType3_copy").addObject("itemType3", itemType3);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改材料类型3页面");
		ItemType3 itemType3 = null;
		if (StringUtils.isNotBlank(id)){
			itemType3 = itemType3Service.get(ItemType3.class, id);
			ItemType2 itemType2 =null;
			if (itemType3!=null) {
				itemType2 = itemType2Service.get(ItemType2.class, itemType3.getItemType2());
				itemType3.setItemType1(itemType2.getItemType1());
			}
			itemType3.setLastUpdate(new Date());
		    itemType3.setActionLog("EDIT");
		}else{
			itemType3 = new ItemType3();
		}
		return new ModelAndView("admin/insales/itemType3/itemType3_update")
			.addObject("itemType3", itemType3);
	}
	
	/**
	 * 跳转到查看ItemType3页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看材料类型3页面");
		ItemType3 itemType3 = itemType3Service.get(ItemType3.class, id);
		ItemType2 itemType2 =null;
		if (itemType3!=null) {
			itemType2 = itemType2Service.get(ItemType2.class, itemType3.getItemType2());
			itemType3.setItemType1(itemType2.getItemType1());
		}	
		return new ModelAndView("admin/insales/itemType3/itemType3_detail")
				.addObject("itemType3", itemType3);
	}
	/**
	 * 添加ItemType3
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemType3 itemType3){
		logger.debug("添加ItemType3开始");
		try{
			if (itemType3!=null) {
				if(itemType3Service.findThItemType3(itemType3)!=null){
					ServletUtils.outPrintFail(request,response,"材料类型3重复");
					return;
				};	
			}
//			String descr = itemType3.getDescr();
//			if(descr!=null){
//				if (itemType3Service.findAllItemType3(descr)!=null) {
//					ServletUtils.outPrintFail(request, response,"材料类型3重复");
//					return;
//				}
//			}
			String str = itemType3Service.getSeqNo("tItemType3");
			itemType3.setCode(str);
			itemType3.setLastUpdate(new Date());
			itemType3.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemType3.setExpired("F");
		    itemType3.setActionLog("ADD");
			this.itemType3Service.save(itemType3);
			ServletUtils.outPrintSuccess(request, response,"添加材料类型3成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加材料类型3失败");
		}
	}
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request,HttpServletResponse response,ItemType3 itemType3){
		logger.debug("复制开始");
		try {	
			if (itemType3!=null) {
				if(itemType3Service.findThItemType3(itemType3)!=null){
					ServletUtils.outPrintFail(request,response,"材料类型3重复");
					return;
				};	
			}
			if(itemType3!=null){
				String str = itemType3Service.getSeqNo("tItemType3");
				itemType3.setCode(str);
				itemType3.setLastUpdate(new Date());
				itemType3.setLastUpdatedBy(getUserContext(request).getCzybh());
				itemType3.setActionLog("ADD");
				itemType3Service.save(itemType3);
				ServletUtils.outPrintSuccess(request, response, "保存材料类型3成功");
			}	
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response, "复制材料类型3失败");
		}
	}
	
	/**
	 * 修改ItemType3
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemType3 itemType3){
		logger.debug("修改材料类型3开始");
		try{
			ItemType3 xt = this.itemType3Service.get(ItemType3.class, itemType3.getCode());
			if (xt!=null){
				itemType3.setActionLog("EDIT");
				itemType3.setLastUpdate(new Date());
				this.itemType3Service.update(itemType3);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.itemType3Service.save(itemType3);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改材料类型3失败");
		}
	}
	
	/**
	 * 删除ItemType3
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除材料类型3开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "材料类型3编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemType3 itemType3 = itemType3Service.get(ItemType3.class, deleteId);
				if(itemType3 == null)
					continue;
				itemType3.setExpired("T");
				itemType3Service.update(itemType3);
			}
		}
		logger.debug("删除材料类型3 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemType3导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, ItemType3 itemType3) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemType3Service.findPageBySql(page,itemType3);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"材料类型3_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}

}
