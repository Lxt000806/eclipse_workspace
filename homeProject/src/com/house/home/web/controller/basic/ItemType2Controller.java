package com.house.home.web.controller.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import java.util.ArrayList;
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
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.entity.Menu;
import com.house.framework.web.controller.BaseController;
import com.house.framework.bean.WebPage;
import com.house.home.service.basic.ItemType2Service;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.WorkType2;

@Controller
@RequestMapping("/admin/itemType2")
public class ItemType2Controller extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(ItemType2Controller.class);

	@Autowired
	private ItemType2Service itemType2Service;

	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @param itemType2
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemType2 itemType2) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		itemType2Service.findPageBySql(page, itemType2);
		return new WebPage<Map<String, Object>>(page);

	}

	/**
	 * ItemType2列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/insales/itemType2/itemType2_list");
	}

	/**
	 * 跳转到新增ItemType2页面
	 * 
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("跳转到新增ItemType2页面");
		return new ModelAndView("admin/insales/itemType2/itemType2_save");
		}
	/**
	 * 添加ItemType2
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, ItemType2 itemType2) {
		logger.debug("添加ItemType2开始");
		try {
			String str = itemType2Service.getSeqNo("tItemType2");
			
			itemType2.setCode(str);
			itemType2.setLastUpdate(new Date());
			itemType2.setExpired("F");
			itemType2.setActionLog("ADD");
			itemType2.setLastUpdatedBy(getUserContext(request).getCzybh());
			if(StringUtils.isNotBlank(itemType2.getWorkType2())){
			   itemType2.setMaterWorkType2(itemType2.getWorkType2());
			}
			this.itemType2Service.save(itemType2);		
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemType2失败");
		}
	}

	/**
	 * 跳转到修改itemType2页面
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, ItemType2 itemType2) {
		logger.debug("跳转到修改系统参数页面");
		ItemType2 item= null;
		if (StringUtils.isNotBlank(itemType2.getCode())) {
			item = itemType2Service.get(ItemType2.class, itemType2.getCode());
			WorkType2 workType2 = null;
			if(StringUtils.isNotBlank(item.getMaterWorkType2())){
				workType2 = itemType2Service.get(WorkType2.class, item.getMaterWorkType2());
			}
			if(workType2 != null){
				item.setWorkType1(workType2.getWorkType1());
				item.setWorkType2(workType2.getCode());
			}
			//item.setWorkType2(itemType2.getMaterWorkType2());
		} else {
			item = new ItemType2();
		}
		return new ModelAndView("admin/insales/itemType2/itemType2_update").addObject(
				"itemType2", item);
		
	}

	/**
	 * 开始修改itemType2
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, ItemType2 itemType2) {
		logger.debug("修改系统参数开始");
		try {
			//ProgTempDt ptd = gcjdmbService.get(ProgTempDt.class, progTempDt.getPk());
			itemType2.setLastUpdate(new Date());
			itemType2.setActionLog("EDIT");
			itemType2.setLastUpdatedBy(getUserContext(request).getCzybh());
			if(StringUtils.isNotBlank(itemType2.getWorkType2())){
				   itemType2.setMaterWorkType2(itemType2.getWorkType2());
				   //itemType2.setAppPrjItem(itemType2.getPrjDescr());
				}
			itemType2Service.update(itemType2);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改系统参数失败");
			e.printStackTrace();
		}
	}


	/**
	 * 跳转到查看ItemType2页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response,ItemType2 itemType2) {
		logger.debug("跳转到查看ItemType2页面");
		ItemType2 item = itemType2Service.get(ItemType2.class, itemType2.getCode());
		item.setWorkType1(itemType2.getWorkType1());
		item.setWorkType2(itemType2.getWorkType2());		
		item.setItemType1Descr(itemType2.getItemType1Descr());
		item.setItemType12Descr(itemType2.getItemType12Descr());
		item.setPrjDescr(itemType2.getPrjDescr());
		return new ModelAndView("admin/insales/itemType2/itemType2_detail")
				.addObject("itemType2", item);
	}

	/**
	 * ItemType2导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, ItemType2 itemType2) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemType2Service.findPageBySql(page, itemType2);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"材料类型2_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
     /**itemType2复制*/
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response, ItemType2 itemType2) {
		logger.debug("跳转到复制BankPos页面");
		ItemType2 item= null;
		if (StringUtils.isNotBlank(itemType2.getCode())) {
			item = itemType2Service.get(ItemType2.class, itemType2.getCode());
			WorkType2 workType2 = null;
			if(StringUtils.isNotBlank(item.getMaterWorkType2())){
				workType2 = itemType2Service.get(WorkType2.class, item.getMaterWorkType2());
			}
			if(workType2 != null){
				item.setWorkType1(workType2.getWorkType1());
				item.setWorkType2(workType2.getCode());
			}
		}
		return new ModelAndView("admin/insales/itemType2/itemType2_copy").addObject(
				"itemType2", item);	 
	}
	/**
	 *ItemType2树
	 * @param request
	 * @param response
	 */
	@RequestMapping("/goTree")
	public ModelAndView getItemType2List(HttpServletRequest request, 
			HttpServletResponse response,String itemType1,String itemType2){
		logger.debug("跳转到权限树页面");
		List<Menu> list = itemType2Service.findAllItemType2ByitemType1(itemType1);
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);

		return new ModelAndView("admin/insales/itemType2/itemType2_tree")
		.addObject("nodes", json)
		.addObject("itemType2", itemType2);
	}
	//验收节点
	@RequestMapping(value = "/prjType/{type}/{pCode}") 
	@ResponseBody
	public JSONObject getPrjType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.itemType2Service.findPrjType(type, pCode);
		return this.out(regionList, true);
	}
	/**
	 * 材料2,3联动
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/byItemType2/{type}/{pCode}") 
	@ResponseBody
	public JSONObject findItemType2(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.itemType2Service.findItemType2(type, pCode);
		return this.out(regionList, true);
	}
	
	@RequestMapping("/changeItemType2s")
	public void changeItemType2s(HttpServletRequest request,HttpServletResponse response,String code) {
		logger.debug("异步加载材料类型3");
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
				+"from tItemType3 a inner join (select * from fStrToTable('"+code+"',',')) t on a.ItemType2=t.item "
				+"where a.Expired='F' order by a.ItemType2";
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
