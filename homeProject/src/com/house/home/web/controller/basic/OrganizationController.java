package com.house.home.web.controller.basic;

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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.esign.ESignUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.OrgSeal;
import com.house.home.entity.basic.Organization;
import com.house.home.service.basic.OrganizationService;
import com.house.home.service.basic.TaxPayeeESignService;

@Controller
@RequestMapping("/admin/organization")
public class OrganizationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private TaxPayeeESignService taxPayeeESignService;

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
			HttpServletResponse response, Organization organization) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		organizationService.findPageBySql(page, organization);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getOrganization")
	@ResponseBody
	public JSONObject getOrganization(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Organization organization = organizationService.get(Organization.class, Integer.parseInt(id));
		if(organization == null){
			return this.out("系统中不存在code="+id+"的员工信息", false);
		}
		return this.out(organization, true);
	}
	
	/**
	 * 机构列表code
	 * 
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,HttpServletResponse response,Organization organization) throws Exception {
		return new ModelAndView("admin/basic/organization/organization_code").addObject("organization", organization);
	}
	
	/**
	 * 跳转到新增Organization页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增Organization页面");
		Organization organization = new Organization();
		organization.setM_umState("A");
		return new ModelAndView("admin/basic/organization/organization_save")
			.addObject("organization", organization);
	}
	/**
	 * 跳转到修改Organization页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改Organization页面");
		Organization organization = organizationService.get(Organization.class, Integer.parseInt(id));
		organization.setM_umState("M");
		return new ModelAndView("admin/basic/organization/organization_update")
			.addObject("organization", organization);
	}
	
	/**
	 * 跳转到查看Organization页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看Organization页面");
		Organization organization = organizationService.get(Organization.class, pk);
		organization.setM_umState("V");
		return new ModelAndView("admin/basic/organization/organization_update")
				.addObject("organization", organization);
	}
	
	/**
	 * 添加Organization
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Organization organization){
		logger.debug("添加Organization开始");
		try{
			organization.setLastUpdate(new Date());
			organization.setLastUpdatedBy(getUserContext(request).getCzybh());
			organization.setExpired("F");
			organization.setActionLog("ADD");
			Result result = this.organizationService.doSave(organization);
			if(Result.FAIL_CODE.equals(result.getCode())){
        		ServletUtils.outPrintFail(request, response, result.getInfo());
        	}else {
        		ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加Organization失败");
		}
	}
	
	/**
	 * 修改Organization
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Integer pk,Organization org){
		logger.debug("修改Organization开始");
		try{
			Organization organization = organizationService.get(Organization.class, pk);
			organization.setOrgLegalIdNumber(org.getOrgLegalIdNumber());
			organization.setOrgLegalName(org.getOrgLegalName());
			organization.setName(org.getName());
			organization.setLastUpdate(new Date());
			organization.setLastUpdatedBy(getUserContext(request).getCzybh());
			organization.setActionLog("EDIT");
			this.organizationService.doUpdate(organization);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Organization失败");
		}
	}
	
	/**
	 * 删除Organization
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("删除Organization开始");
		
		Organization organization = organizationService.get(Organization.class,pk);
		List<Map<String, Object>> list = taxPayeeESignService.isEnableOrg(organization.getOrgId());
		if(list.size() > 0 && list != null){
			ServletUtils.outPrintFail(request, response, "该机构已启用，无法删除");
			return;
		}
		
		organizationService.doDelete(organization);
		
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Organization导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Organization organization){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		organizationService.findPageBySql(page, organization);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"机构管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 添加印章
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSaveSeal")
	public void doSaveSeal(HttpServletRequest request, HttpServletResponse response, Organization organization){
		logger.debug("添加Organization开始");
		try{
			ESignUtils.generateESignToken(true);
			ESignUtils.createOrgSeal(organization.getOrgId(), organization.getHtext(), 
					organization.getQtext(), organization.getColor(),
					organization.getCentral(),organization.getType());
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加印章失败");
		}
	}
	
	/**
	 * 编辑印章
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateSeal")
	public void doUpdateSeal(HttpServletRequest request, HttpServletResponse response, Organization organization){
		logger.debug("添加Organization开始");
		try{
			//organizationService.doUpdateSeal(organization);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加印章失败");
		}
	}
	
	/**
	 * 删除印章
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doDeleteSeal")
	public void doDeleteSeal(HttpServletRequest request, HttpServletResponse response, Organization organization){
		logger.debug("添加Organization开始");
		try{
			ESignUtils.generateESignToken(true);
			ESignUtils.deleteSealByOrgId(organization.getOrgId(), organization.getSealId());
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除印章失败");
		}
	}
	
	/**
	 * 认证
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doIdentity")
	public void doIdentity(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("添加Organization开始");
		try{
			Organization organization = organizationService.get(Organization.class, pk);
			organization.setLastUpdatedBy(getUserContext(request).getCzybh());
			organizationService.doIdentity(organization);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "认证失败");
		}
	}
	
	/**
	 * 刷新认证
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doRefreshIdentity")
	public void doRefreshIdentity(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("添加Organization开始");
		try{
			Organization organization = organizationService.get(Organization.class, pk);
			organizationService.doRefreshIdentity(organization);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "刷新认证失败");
		}
	}
}
