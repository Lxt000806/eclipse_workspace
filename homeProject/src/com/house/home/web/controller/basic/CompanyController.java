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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Company;
import com.house.home.entity.basic.SignPlace;
import com.house.home.service.basic.CompanyService;

@Controller
@RequestMapping("/admin/company")
public class CompanyController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;
	
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
			HttpServletResponse response, Company company) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		companyService.findPageBySql(page, company);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 获取打卡地点列表
	 * @author	created by zb
	 * @date	2019-5-10--下午5:49:13
	 * @param request
	 * @param response
	 * @param company
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSignPlace")
	@ResponseBody
	public WebPage<Map<String,Object>> getSignPlace(HttpServletRequest request,
			HttpServletResponse response, Company company) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		companyService.findSignPlacePageBySql(page, company);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 公司列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/company/company_list");
	}
	/**
	 * 公司Code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/company/company_code");
	}
	
	/**
	 * 跳转到新增公司页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增公司页面");
		Company company = new Company();
		
		return new ModelAndView("admin/basic/company/company_save")
			.addObject("company", company);
	}
	/**
	 * 跳转到修改公司页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id, String m_umState){
		logger.debug("跳转到修改公司页面");
		Company company = null;
		if (StringUtils.isNotBlank(id)){
			company = companyService.get(Company.class, id);
		}else{
			company = new Company();
		}
		company.setM_umState(m_umState);
		return new ModelAndView("admin/basic/company/company_save")
			.addObject("company", company);
	}
	
	/**
	 * 跳转到查看公司页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id, String m_umState){
		logger.debug("跳转到查看公司页面");
		Company company = companyService.get(Company.class, id);
		company.setM_umState(m_umState);
		return new ModelAndView("admin/basic/company/company_save")
				.addObject("company", company);
	}
	/**
	 * 跳转到打卡地点页面
	 * @author	created by zb
	 * @date	2019-5-10--下午5:47:12
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/goSignSite")
	public ModelAndView goSignSite(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到打卡地点页面");
		Company company = companyService.get(Company.class, id);
		return new ModelAndView("admin/basic/company/company_signSite")
				.addObject("company", company);
	}
	/**
	 * 打卡地点新增与编辑
	 * @author	created by zb
	 * @date	2019-5-13--下午3:29:50
	 * @param signPlace
	 * @return
	 */
	@RequestMapping("/goSignItem")
	public ModelAndView goTechItem(SignPlace signPlace){
		SignPlace sp = new SignPlace();
		if (null != signPlace.getpK()) {
			sp = this.companyService.get(SignPlace.class, signPlace.getpK());
		}
		if (StringUtils.isNotBlank(signPlace.getM_umState())) {
			sp.setM_umState(signPlace.getM_umState());
		} else {
			sp.setCmpCode(signPlace.getCmpCode());
		}
		sp.setCmpCodeDescr(signPlace.getCmpCodeDescr());
	    return new ModelAndView("admin/basic/company/company_signItem")
        	.addObject("signPlace", sp);
	}
	/**
	 * 添加公司
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Company company){
		logger.debug("添加公司开始");
		try{
			/* 过期 modify by zb on 20190510
			 * Company xt = this.companyService.getByDesc2(company.getDesc2());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "公司重复");
				return;
			}
			String str = companyService.getSeqNo("tCompany");
			company.setCode(str);*/
			Company c = this.companyService.get(Company.class, company.getCode());
			if (null != c) {
				ServletUtils.outPrintFail(request, response, "公司编号重复");
			} else {
				company.setLastUpdate(new Date());
				company.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				company.setActionLog("ADD");
				company.setExpired("F");
				this.companyService.save(company);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加公司失败");
		}
	}
	
	/**
	 * 修改公司
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, 
			Company company){
		logger.debug("修改公司开始");
		try{
			/* 过期 modify by zb on 20190510
			 * Company xt = this.companyService.get(Company.class, company.getCode());
			if (xt!=null){
				this.companyService.update(company);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.companyService.save(company);
				ServletUtils.outPrintSuccess(request, response);
			}*/
			Company c = this.companyService.get(Company.class, company.getCode());
			if (null != c) {
				company.setLastUpdate(new Date());
				company.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				company.setActionLog("Edit");
				this.companyService.update(company);
				ServletUtils.outPrintSuccess(request, response);
			} else {
				ServletUtils.outPrintFail(request, response, "数据库不存在该数据");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改公司失败");
		}
	}
	
	/**
	 * 删除公司
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除公司开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "公司编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Company company = this.companyService.get(Company.class, deleteId);
				if(company == null)
					continue;
				this.companyService.delete(company);
			}
		}
		logger.debug("删除公司 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	@RequestMapping("/doSignAdd")
	public void doSignAdd(HttpServletRequest request, HttpServletResponse response, SignPlace signPlace){
		logger.debug("添加打卡地点");
		try{
			if(StringUtils.isNotBlank(signPlace.getTude())){
				String[] array=signPlace.getTude().split("\\|");
				signPlace.setLongitudetppc(Double.parseDouble(array[0]));
				signPlace.setLatitudetppc(Double.parseDouble(array[1]));
			} else {
				ServletUtils.outPrintFail(request, response, "无经纬度");
				return;
			}
			signPlace.setLastUpdate(new Date());
			signPlace.setExpired("F");
			signPlace.setActionLog("ADD");
			this.companyService.save(signPlace);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加打卡地点失败");
		}
	}
	@RequestMapping("/doSignUpdate")
	public void doSignUpdate(HttpServletRequest request, HttpServletResponse response, SignPlace signPlace){
		logger.debug("更新打卡地点");
		try{
			if(StringUtils.isNotBlank(signPlace.getTude())){
				String[] array=signPlace.getTude().split("\\|");
				signPlace.setLongitudetppc(Double.parseDouble(array[0]));
				signPlace.setLatitudetppc(Double.parseDouble(array[1]));
			} else {
				ServletUtils.outPrintFail(request, response, "无经纬度");
				return;
			}
			signPlace.setLastUpdate(new Date());
			signPlace.setActionLog("Edit");
			this.companyService.update(signPlace);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "更新打卡地点失败");
		}
	}
	@RequestMapping("/doSignDelete")
	public void doSignDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除打卡地点");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "pk不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					SignPlace signPlace = this.companyService.get(SignPlace.class, Integer.parseInt(deleteId));
					if (null == signPlace) {
						ServletUtils.outPrintSuccess(request, response, "pk不存在");
						return;
					}
					signPlace.setExpired("T");
					this.companyService.update(signPlace);
					ServletUtils.outPrintSuccess(request, response, "删除成功");
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除打卡地点失败");
		}
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Company company){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		companyService.findPageBySql(page, company);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"公司信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
