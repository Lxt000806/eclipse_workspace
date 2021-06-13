package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Menu;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.BaseItemType1;
import com.house.home.service.insales.BaseItemType1Service;

@Controller
@RequestMapping("/admin/baseItemType1")
public class BaseItemType1Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseItemType1Controller.class);

	@Autowired
	private BaseItemType1Service baseItemType1Service;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemType1 baseItemType1) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemType1Service.findPageBySql(page, baseItemType1);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseItemType1列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/baseItemType1/baseItemType1_list");
	}
	/**
	 * BaseItemType1查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/baseItemType1/baseItemType1_code");
	}
	/**
	 * 跳转到新增BaseItemType1页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItemType1页面");
		BaseItemType1 baseItemType1 = null;
		if (StringUtils.isNotBlank(id)){
			baseItemType1 = baseItemType1Service.get(BaseItemType1.class, id);
			baseItemType1.setCode(null);
		}else{
			baseItemType1 = new BaseItemType1();
		}
		
		return new ModelAndView("admin/insales/baseItemType1/baseItemType1_save")
			.addObject("baseItemType1", baseItemType1);
	}
	/**
	 * 跳转到复制BaseItemType1页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到复制BaseItemType1页面");
		
		BaseItemType1 baseItemType1 = null;
		if (StringUtils.isNotBlank(id)){
			baseItemType1 = baseItemType1Service.get(BaseItemType1.class, id);
		}else{
			baseItemType1 = new BaseItemType1();
		}
		
		return new ModelAndView("admin/insales/baseItemType1/baseItemType1_copy")
			.addObject("baseItemType1", baseItemType1);
	}
	/**
	 * 跳转到修改BaseItemType1页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BaseItemType1页面");
		BaseItemType1 baseItemType1 = null;
		if (StringUtils.isNotBlank(id)){
			baseItemType1 = baseItemType1Service.get(BaseItemType1.class, id);
		}else{
			baseItemType1 = new BaseItemType1();
		}
		
		return new ModelAndView("admin/insales/baseItemType1/baseItemType1_update")
			.addObject("baseItemType1", baseItemType1);
	}
	
	/**
	 * 跳转到查看BaseItemType1页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BaseItemType1页面");
		BaseItemType1 baseItemType1 = baseItemType1Service.get(BaseItemType1.class, id);
		
		return new ModelAndView("admin/insales/baseItemType1/baseItemType1_detail")
				.addObject("baseItemType1", baseItemType1);
	}
	/**
	 * 添加BaseItemType1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseItemType1 baseItemType1){
		logger.debug("添加BaseItemType1开始");
		try{
			if (valideCode(baseItemType1.getCode())==true) {
				ServletUtils.outPrintFail(request, response, "基装类型1["+baseItemType1.getCode()+"]编号重复");		
				return;
			}
			baseItemType1.setActionLog("ADD");
			baseItemType1.setLastUpdate(new Date());
			baseItemType1.setLastUpdatedBy(getUserContext(request).getCzybh());
			baseItemType1.setExpired("F");
			this.baseItemType1Service.save(baseItemType1);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseItemType1失败");
		}
	}
	
	/**
	 * 复制BaseItemType1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request, HttpServletResponse response, BaseItemType1 baseItemType1){
		logger.debug("复制BaseItemType1开始");
		try{
			if (valideCode(baseItemType1.getCode())==true) {
				ServletUtils.outPrintFail(request, response, "基装类型1["+baseItemType1.getCode()+"]编号重复");		
				return;
			}
			baseItemType1.setActionLog("ADD");
			baseItemType1.setLastUpdate(new Date());
			baseItemType1.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseItemType1Service.save(baseItemType1);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseItemType1失败");
		}
	}
	
	/**
	 * 修改BaseItemType1
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseItemType1 baseItemType1){
		logger.debug("修改BaseItemType1开始");
		try{
			baseItemType1.setLastUpdate(new Date());
			baseItemType1.setLastUpdatedBy(getUserContext(request).getCzybh());
			baseItemType1.setActionLog("EDIT");
			this.baseItemType1Service.update(baseItemType1);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItemType1失败");
		}
	}
	
	/**
	 * 删除BaseItemType1
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseItemType1开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseItemType1编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseItemType1 baseItemType1 = baseItemType1Service.get(BaseItemType1.class, deleteId);
				if(baseItemType1 == null)
					continue;
				baseItemType1.setExpired("T");
				baseItemType1Service.update(baseItemType1);
			}
		}
		logger.debug("删除BaseItemType1 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BaseItemType1导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, BaseItemType1 baseItemType1) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemType1Service.findPageBySql(page,baseItemType1);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"基装类型1_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	/**
	 *baseItemType1树
	 * @param request
	 * @param response
	 */
	@RequestMapping("/goTree")
	public ModelAndView getItemType2List(HttpServletRequest request, 
			HttpServletResponse response,String baseItemType1){
		List<Menu> list = baseItemType1Service.getTreeMenu();
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);
		return new ModelAndView("admin/insales/baseItemType1/baseItemType1_tree")
		.addObject("nodes", json)
		.addObject("baseItemType1", baseItemType1);
	}
	@RequestMapping("/baseItemType/{type}/{pCode}")
	@ResponseBody
	public JSONObject getBaseItemType(@PathVariable int type, @PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.baseItemType1Service.findBaseItemType(type, pCode);
		return this.out(regionList, true);	
	}
	/**
	 *baseItemType1树
	 * @param request
	 * @param response
	 */
	@RequestMapping("/goCheckTree")
	public ModelAndView goCheckTree(HttpServletRequest request, 
			HttpServletResponse response,String baseItemType1){
		List<Menu> list = baseItemType1Service.getCheckTreeMenu();
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);
		return new ModelAndView("admin/insales/baseItemType1/baseItemType1_tree")
		.addObject("nodes", json)
		.addObject("baseItemType1", baseItemType1);
	}
	/**
	 *baseItemType1验证编号是否重复
	 * @param request
	 * @param response
	 */
	public boolean valideCode(String id){
		return baseItemType1Service.valideCode(id);	
	}
	
}
