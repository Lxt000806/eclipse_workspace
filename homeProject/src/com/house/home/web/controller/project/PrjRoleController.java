package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.PrjRole;
import com.house.home.entity.basic.PrjRolePrjItem;
import com.house.home.entity.basic.PrjRoleWorkType12;
import com.house.home.service.basic.PrjRoleService;

@Controller
@RequestMapping("/admin/prjRole")
public class PrjRoleController extends BaseController { 
	
	// add by hc  2017/11/22  begin 
	@Autowired
	private PrjRoleService prjRoleService;
	/**
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, PrjRole prjRole) throws Exception {
		return new ModelAndView("admin/basic/prjRole/PrjRole_list").addObject("prjRole", prjRole);
	}	
	/**
	 * 查询JqGrid表格数据
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjRole prjRole) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjRoleService.findPageBySql(page, prjRole);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid快速施工项目新增表
	 * @throws Exception
	 */
	@RequestMapping("/goKsxzJqGrid")
	
	@ResponseBody
	public WebPage<Map<String,Object>> goKsxzJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjRole prjRole) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjRoleService.findPageByksxzSql(page, prjRole);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid快速工种分类新增表
	 * @throws Exception
	 */
	@RequestMapping("/goKsworkJqGrid")
	
	@ResponseBody
	public WebPage<Map<String,Object>> goKsworkJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjRole prjRole) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjRoleService.findPageByksworkSql(page, prjRole);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	
	
	//查询施工任务  表格数据
	@RequestMapping("/goJqGridPrjItem")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridPrjItem(HttpServletRequest request,
			HttpServletResponse response,PrjRolePrjItem prjRolePrjItem) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjRoleService.findPageBySqlPrjItem(page, prjRolePrjItem);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	//查询施工任务  表格数据
	@RequestMapping("/goJqGridPrjWork")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridPrjWork(HttpServletRequest request,
			HttpServletResponse response,PrjRoleWorkType12 prjRoleWorkType12) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjRoleService.findPageBySqlPrjWork(page, prjRoleWorkType12);
		return new WebPage<Map<String,Object>>(page);
	}				
	/*
	 * 跳转prjRole新增
	/*
	 *prjRole新增页面
	 * */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到添加页面");						
		PrjRole prjRole = new PrjRole();		
		return new ModelAndView("admin/basic/prjRole/PrjRole_save")
			.addObject("prjRole", prjRole).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/*
	 *prjRole编辑页面 
	 * */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到编辑页面");
		PrjRole prjRole = null;
		if (StringUtils.isNotBlank(id)){
			prjRole = prjRoleService.get(PrjRole.class, id);
		}else{
			prjRole = new PrjRole();
		}
		return new ModelAndView("admin/basic/prjRole/PrjRole_update")
			.addObject("prjRole", prjRole).addObject("czy", this.getUserContext(request).getCzybh());
	}
	/*
	 *prjRole查看页面 
	 * */
	
	@RequestMapping("/goview")
	public ModelAndView goview(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看页面");
		PrjRole prjRole = null;
		if (StringUtils.isNotBlank(id)){
			prjRole = prjRoleService.get(PrjRole.class, id);
		}else{
			prjRole = new PrjRole();
		}
		return new ModelAndView("admin/basic/prjRole/PrjRole_view")
			.addObject("prjRole", prjRole).addObject("czy", this.getUserContext(request).getCzybh());
	}
	/*
	 *prjrole_worktype12新增页面
	 * */
	@RequestMapping("/goaddwork12")
	public ModelAndView goadd(HttpServletRequest request, HttpServletResponse response,PrjRoleWorkType12 prjRoleWorkType12){
		logger.debug("跳转到新增页面");	
		return new ModelAndView("admin/basic/prjRole/worktype12_add")
			.addObject("prjRoleWorkType12", prjRoleWorkType12);
	}
	/*
	 * prjrole_worktype12编辑页面
	 * */
	@RequestMapping("/goUpdatework12")
	public ModelAndView goUpdatework12(HttpServletRequest request, HttpServletResponse response,PrjRoleWorkType12 prjRoleWorkType12){
		logger.debug("跳转页面");			
		return new ModelAndView("admin/basic/prjRole/worktype12_update")
			.addObject("prjRoleWorkType12", prjRoleWorkType12);
	}
	
	/*
	 * prjrole_worktype12查看页面
	 * */
	@RequestMapping("/goViewwork12")
	public ModelAndView goViewwork12(HttpServletRequest request, HttpServletResponse response,PrjRoleWorkType12 prjRoleWorkType12){
		logger.debug("跳转到页面");			
		return new ModelAndView("admin/basic/prjRole/worktype12_view")
			.addObject("prjRoleWorkType12", prjRoleWorkType12);
	}
	
	/*
	 *prjrole_prjItem新增页面
	 * */
	@RequestMapping("/goaddPrjItem")
	public ModelAndView goaddPrjItem(HttpServletRequest request, HttpServletResponse response,PrjRolePrjItem prjRolePrjItem){
		logger.debug("跳转到新增页面");	
		return new ModelAndView("admin/basic/prjRole/rolePrjItem_add")
			.addObject("prjRolePrjItem", prjRolePrjItem);
	}
	/*
	 *prjrole_prjItem快速新增页面
	 * */
	@RequestMapping("/goksaddPrjItem")
	public ModelAndView goksaddPrjItem(HttpServletRequest request, HttpServletResponse response,PrjRolePrjItem prjRolePrjItem){
		logger.debug("跳转到新增页面");	
		return new ModelAndView("admin/basic/prjRole/rolePrjItem_ksadd")
			.addObject("prjRolePrjItem", prjRolePrjItem);
	}
	/*
	 *prjrole_worktype12快速新增页面
	 * */
	@RequestMapping("/goksaddwork12")
	public ModelAndView goksaddwork12(HttpServletRequest request, HttpServletResponse response,PrjRoleWorkType12 prjRoleWorkType12){
		logger.debug("跳转到新增页面");	
		return new ModelAndView("admin/basic/prjRole/worktype12_ksadd")
			.addObject("prjRoleWorkType12", prjRoleWorkType12);
	}
	
	/*
	 * prjrole_prjItem编辑页面
	 * */
	@RequestMapping("/goUpdatePrjItem")
	public ModelAndView goUpdatePrjItem(HttpServletRequest request, HttpServletResponse response,PrjRolePrjItem prjRolePrjItem){
		logger.debug("跳转页面");			
		return new ModelAndView("admin/basic/prjRole/rolePrjItem_update")
			.addObject("prjRolePrjItem", prjRolePrjItem);
	}
	
	/*
	 * prjrole_prjItem查看页面
	 * */
	@RequestMapping("/goViewPrjItem")
	public ModelAndView goViewPrjItem(HttpServletRequest request, HttpServletResponse response,PrjRolePrjItem prjRolePrjItem){
		logger.debug("跳转到页面");			
		return new ModelAndView("admin/basic/prjRole/rolePrjItem_view")
			.addObject("prjRolePrjItem", prjRolePrjItem);
	}
	
	
	/**
	 *修改 
	 *
	 */
	@RequestMapping("/doprjRoleUpdate")
	public void doprjRoleUpdate(HttpServletRequest request,HttpServletResponse response,PrjRole prjRole){
		logger.debug("编辑开始");		
		try {
			PrjRole iSet = this.prjRoleService.getByDescr1(prjRole.getDescr(),prjRole.getDescr1());			
			if (iSet!=null){
				ServletUtils.outPrintFail(request, response, "工程角色名称已存在！");
				return;
			}	
			if ("A".equals(prjRole.getM_umState())){
				PrjRole iCode = this.prjRoleService.getByCode(prjRole.getCode());			
				if (iCode!=null){
					ServletUtils.outPrintFail(request, response, "工程角色编号已存在！");
					return;
				}	
			}
			prjRole.setLastUpdate(new Date());			
			prjRole.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjRole.setM_umState(prjRole.getM_umState());
			prjRole.setExpired("F"); 									
			String itemAppsendDetail =request.getParameter("prjRoleJson");
			String salesInvoiceDetail =request.getParameter("salesInvoiceDetailJson");
			JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  prjRoleJson=jsonArray.toString();
			prjRole.setPrjRoleJson(prjRoleJson);
			JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
			JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());//先转化成json数组 
			String salesInvoiceDetailJson=jsonArray2.toString();
			prjRole.setSalesInvoiceDetailJson(salesInvoiceDetailJson);
			Result result = this.prjRoleService.doPrjRoleSave(prjRole);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "工程角色操作失败");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrjRole prjRole = this.prjRoleService.get(PrjRole.class, deleteId);
				prjRole.setExpired("T");
				prjRole.setM_umState("M");			
				prjRole.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = prjRoleService.deleteForProc(prjRole);
				if ("1".equals(result.getCode())){
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		}
		logger.debug(" IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	//  add by hc 2017 /11 /22 end 
	
}
