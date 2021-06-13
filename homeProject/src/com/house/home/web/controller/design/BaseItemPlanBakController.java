package com.house.home.web.controller.design;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.BaseItemPlanBak;
import com.house.home.service.design.BaseItemPlanBakService;

@Controller
@RequestMapping("/admin/baseItemPlanBak")
public class BaseItemPlanBakController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseItemPlanBakController.class);

	@Autowired
	private BaseItemPlanBakService baseItemPlanBakService;

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
			HttpServletResponse response, BaseItemPlanBak baseItemPlanBak) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemPlanBakService.findPageBySql(page, baseItemPlanBak);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseItemPlanBak列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/baseItemPlanBak/baseItemPlanBak_list");
	}
	/**
	 * BaseItemPlanBak查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,BaseItemPlanBak  baseItemPlanBak) throws Exception {

		return new ModelAndView("admin/design/baseItemPlanBak/baseItemPlanBak_code").addObject("baseItemPlanBak", baseItemPlanBak);
	}
	/**
	 * 跳转到新增BaseItemPlanBak页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItemPlanBak页面");
		BaseItemPlanBak baseItemPlanBak = null;
		if (StringUtils.isNotBlank(id)){
			baseItemPlanBak = baseItemPlanBakService.get(BaseItemPlanBak.class, id);
			baseItemPlanBak.setNo(null);
		}else{
			baseItemPlanBak = new BaseItemPlanBak();
		}
		
		return new ModelAndView("admin/design/baseItemPlanBak/baseItemPlanBak_save")
			.addObject("baseItemPlanBak", baseItemPlanBak);
	}
	/**
	 * 跳转到修改BaseItemPlanBak页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BaseItemPlanBak页面");
		BaseItemPlanBak baseItemPlanBak = null;
		if (StringUtils.isNotBlank(id)){
			baseItemPlanBak = baseItemPlanBakService.get(BaseItemPlanBak.class, id);
		}else{
			baseItemPlanBak = new BaseItemPlanBak();
		}
		
		return new ModelAndView("admin/design/baseItemPlanBak/baseItemPlanBak_update")
			.addObject("baseItemPlanBak", baseItemPlanBak);
	}
	
	/**
	 * 跳转到查看BaseItemPlanBak页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BaseItemPlanBak页面");
		BaseItemPlanBak baseItemPlanBak = baseItemPlanBakService.get(BaseItemPlanBak.class, id);
		
		return new ModelAndView("admin/design/baseItemPlanBak/baseItemPlanBak_detail")
				.addObject("baseItemPlanBak", baseItemPlanBak);
	}
	/**
	 * 添加BaseItemPlanBak
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseItemPlanBak baseItemPlanBak){
		logger.debug("添加BaseItemPlanBak开始");
		try{
			String str = baseItemPlanBakService.getSeqNo("tBaseItemPlanBak");
			baseItemPlanBak.setNo(str);
			baseItemPlanBak.setLastUpdate(new Date());
			baseItemPlanBak.setLastUpdatedBy(getUserContext(request).getCzybh());
			baseItemPlanBak.setExpired("F");
			this.baseItemPlanBakService.save(baseItemPlanBak);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseItemPlanBak失败");
		}
	}
	
	/**
	 * 修改BaseItemPlanBak
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseItemPlanBak baseItemPlanBak){
		logger.debug("修改BaseItemPlanBak开始");
		try{
			baseItemPlanBak.setLastUpdate(new Date());
			baseItemPlanBak.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseItemPlanBakService.update(baseItemPlanBak);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItemPlanBak失败");
		}
	}
	
	/**
	 * 删除BaseItemPlanBak
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseItemPlanBak开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseItemPlanBak编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseItemPlanBak baseItemPlanBak = baseItemPlanBakService.get(BaseItemPlanBak.class, deleteId);
				if(baseItemPlanBak == null)
					continue;
				baseItemPlanBak.setExpired("T");
				baseItemPlanBakService.update(baseItemPlanBak);
			}
		}
		logger.debug("删除BaseItemPlanBak IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BaseItemPlanBak导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseItemPlanBak baseItemPlanBak){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemPlanBakService.findPageBySql(page, baseItemPlanBak);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"BaseItemPlanBak_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 根据id查询BaseItemPlanBak详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBaseItemPlanBak")
	@ResponseBody
	public JSONObject getBaseItemPlanBak(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		BaseItemPlanBak baseItemPlanBak= baseItemPlanBakService.get(BaseItemPlanBak.class, id);
		if(baseItemPlanBak == null){
			return this.out("系统中不存在No="+id+"的备份", false);
		}
		return this.out(baseItemPlanBak, true);
	}
	
	

}
