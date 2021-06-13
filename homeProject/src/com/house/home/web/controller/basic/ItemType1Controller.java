package com.house.home.web.controller.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Activity;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.ItemType1Service;
import com.house.home.service.basic.ItemType2Service;

@Controller
@RequestMapping("/admin/itemType1")
public class ItemType1Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemType1Controller.class);

	@Autowired
	private ItemType1Service itemType1Service;
	@Autowired
	private ItemType2Service itemType2Service;
	
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
			HttpServletResponse response,ItemType1 itemType1) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemType1Service.findPageBySql(page, itemType1);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemType1列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemType1/itemType1_list");
	}
	
	/**
	 * 跳转到新增ItemType1页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		return new ModelAndView("admin/insales/itemType1/itemType1_save");			
	}
	/**
	 * 跳转到复制itemType1页面
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到复制材料类型1页面");
		ItemType1 itemType1 =null;
		if (StringUtils.isNotBlank(id)) {
			itemType1=itemType1Service.get(ItemType1.class, id);
		}
		if(StringUtils.isNotBlank(itemType1.getWhCode())){
			WareHouse wareHouse=itemType1Service.get(WareHouse.class, itemType1.getWhCode());
			itemType1.setWhDescr(wareHouse.getDesc1());
		}
		return new ModelAndView("admin/insales/itemType1/itemType1_copy").addObject("itemType1", itemType1);
	}
	/**
	 * 跳转到修改ItemType1页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改材料类型1页面");
		ItemType1 itemType1 = null;
		if (StringUtils.isNotBlank(id)){
			itemType1 = itemType1Service.get(ItemType1.class, id);
		}else{
			itemType1 = new ItemType1();
		}
		if(StringUtils.isNotBlank(itemType1.getWhCode())){
			WareHouse wareHouse=itemType1Service.get(WareHouse.class, itemType1.getWhCode());
			itemType1.setWhDescr(wareHouse.getDesc1());
		}
		return new ModelAndView("admin/insales/itemType1/itemType1_update")
			.addObject("itemType1", itemType1);
	}
	
	/**
	 * 跳转到查看ItemType1页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看材料类型1页面");
		ItemType1 itemType1 = itemType1Service.get(ItemType1.class, id);
		if(StringUtils.isNotBlank(itemType1.getWhCode())){
			WareHouse wareHouse=itemType1Service.get(WareHouse.class, itemType1.getWhCode());
			itemType1.setWhDescr(wareHouse.getDesc1());
		}
		return new ModelAndView("admin/insales/itemType1/itemType1_detail")
				.addObject("itemType1", itemType1);
	}
	/**
	 * 添加ItemType1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemType1 itemType1){
		logger.debug("添加ItemType1开始");
		try{
			ItemType1 xt = this.itemType1Service.get(ItemType1.class, itemType1.getCode());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "材料类型1编码重复");
				return;
			}
			String code = itemType1.getCode().toUpperCase();
			itemType1.setCode(code);
			itemType1.setActionLog("ADD");
			itemType1.setExpired("F");
			itemType1.setLastUpdate(new Date());
			itemType1.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			this.itemType1Service.save(itemType1);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加材料类型1失败");
		}
	}
	/**	
	 * 复制ItemType1
	 */
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request,HttpServletResponse response,ItemType1 itemType1){
		logger.debug("复制ItemType1开始");
		try {
			ItemType1 type1 = this.itemType1Service.get(ItemType1.class, itemType1.getCode());
			if (type1!=null) {
				ServletUtils.outPrintFail(request, response, "添加材料类型1编码重复");
			}
			itemType1.setDateTo(new Date());
		    String code = itemType1.getCode().toUpperCase();
		    itemType1.setCode(code);
			itemType1Service.save(itemType1);
			ServletUtils.outPrintSuccess(request, response, "添加材料类型1成功");
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response, "添加材料类型1失败");
		}

		
	} 
	/**
	 * 修改ItemType1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemType1 itemType1){
		logger.debug("修改ItemType1开始");
		try{
			ItemType1 xt = this.itemType1Service.get(ItemType1.class, itemType1.getCode());
			if (xt!=null){
				itemType1.setLastUpdate(new Date());
				itemType1.setActionLog("EDIT");
				itemType1.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
				this.itemType1Service.update(itemType1);
				ServletUtils.outPrintSuccess(request, response,"修改材料类型1成功");
			}else{
				itemType1.setLastUpdate(new Date());
				itemType1.setActionLog("EDIT");
				this.itemType1Service.save(itemType1);
				ServletUtils.outPrintSuccess(request, response,"修改材料类型1成功");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemType1失败");
		}
	}
	
	/**
	 * 删除ItemType1
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除材料类型1开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "材料类型1编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemType1 itemType1 = itemType1Service.get(ItemType1.class, deleteId);
				if(itemType1 == null)
					continue;
				itemType1.setExpired("T");
				itemType1Service.update(itemType1);
			}
		}
		logger.debug("删除ItemType1 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemType1导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, ItemType1 itemType1) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemType1Service.findPageBySql(page,itemType1);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"材料类型1_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	
	@RequestMapping("/changeItemType1")
	public void changeItemType1(HttpServletRequest request,HttpServletResponse response,String itemType1) {
		logger.debug("异步加载材料类型2");
		List<ItemType2> list = (List<ItemType2>) itemType2Service.findByItemType1(itemType1);
		StringBuffer sb = new StringBuffer("");
		sb.append("<option value=\"\">请选择...</option>");
		if (list != null && list.size()>0){
			for (ItemType2 at :list){
				sb.append("<option value=\"").append(at.getCode()).append("\">").append(at.getCode())
				.append(" ").append(at.getDescr()).append("</option>");
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strSelect", sb.toString());
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 材料工种类型1.2联动
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/byItemType1/{type}/{pCode}") 
	@ResponseBody
	public JSONObject findItemType1(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.itemType1Service.findItemType1(type, pCode);//type代表第几级来确定查询哪张表,pcode代表选中的那一列,确定后面的过滤条件
		return this.out(regionList, true);
	}
	
	@RequestMapping("/checkItemType1")
	public void checkItemType1(HttpServletRequest request,HttpServletResponse response,String code) {
		logger.debug("异步加载材料类型分类");
		String virtualRootLabel = "请选择";
		String virtualRootId = "_VIRTUAL_RO0T_ID_";
		String sqlValueKey = "code";
		String sqlLableKey = "descr";
		String retStr = "[]";
		if (StringUtils.isBlank(code)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("strSelect", retStr);
			try {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toJSONString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String sql = "select a.code,a.descr "
				+"from titemType12 a  "
				+"where a.Expired='F' and a.itemType1 ='"+code.trim()+"' ";
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", virtualRootId);
		item.put("pId", "");
		item.put("name", virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list != null && list.size() > 0){
			for(Map<String, Object> map : list){
				item = new HashMap<String, Object>();
				item.put("id", map.get(sqlValueKey));
				item.put("pId", virtualRootId);
				item.put("name", map.get(sqlValueKey)+" "+map.get(sqlLableKey));
				rsList.add(item);
			}
		}
		retStr = JSON.toJSONString(rsList);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strSelect", retStr);
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
