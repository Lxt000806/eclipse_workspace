package com.house.home.web.controller.builderRep;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.transformer.StringToInteger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.cmpActivity;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.project.BuilderRep;
import com.house.home.service.basic.BuilderRepService;
import com.house.home.service.design.CustomerService;
@Controller
@RequestMapping("/admin/builderRep")
public class BuilderRepController extends BaseController { 	
	@Autowired
	private BuilderRepService builderRepService;
	@Autowired
	private CustomerService customerService;
	
	private void resetBuilderRep(BuilderRep builderRep){
		if (StringUtils.isNotBlank(builderRep.getCustCode())){
			Customer customer = customerService.get(Customer.class, builderRep.getCustCode());
			if (customer!=null){
				builderRep.setAddress(customer.getAddress());
			}
		}
			
	}

	/**
	 * BuilderRep列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, BuilderRep builderRep) throws Exception {
		return new ModelAndView("admin/project/builderRep/BuilderRep_list").addObject("builderRep", builderRep).
				addObject("czybh", this.getUserContext(request).getCzybh());
	}
	/**
	 * 新增跳转BuilderRep表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDealRemark")
	public ModelAndView goDealRemark(HttpServletRequest request,
			HttpServletResponse response, String pk) throws Exception {
		BuilderRep builderRep = null;
		if (StringUtils.isNotBlank(pk)){
			builderRep = builderRepService.get(BuilderRep.class, Integer.parseInt(pk));
			resetBuilderRep(builderRep);
		}else{
			builderRep = new BuilderRep();
		}
		return new ModelAndView("admin/project/builderRep/builderRep_dealRemark").addObject("builderRep", builderRep);
				
	}
//	/*this.getUserContext(request).getCzybh()   调用权限
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,BuilderRep builderRep) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (builderRep.getEndDate() != null ){
			 Calendar c = Calendar.getInstance();  
		     c.setTime(builderRep.getEndDate());  
		     c.add(Calendar.DAY_OF_MONTH, 1);
		     builderRep.setEndDate(c.getTime());
		}		
		builderRepService.findPageBySql(page, builderRep);
		return new WebPage<Map<String,Object>>(page);
	}

	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除工地报备信息开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "工地报备不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BuilderRep builderRep = this.builderRepService.get(BuilderRep.class, Integer.parseInt(deleteId));
				builderRep.setExpired("T");
				builderRep.setM_umState("M");					
				builderRep.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = builderRepService.deleteForProc(builderRep);
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

	/**
	 * 修改处理报备
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDealRemark")
	public void doUpdateMobile1(HttpServletRequest request,HttpServletResponse response,BuilderRep builderRep){
		logger.debug("修改处理报备");
		try{
			BuilderRep br = null;
			br=builderRepService.get(BuilderRep.class, builderRep.getPk());
			br.setDealRemark(builderRep.getDealRemark());
			br.setLastUpdate(new Date());
			br.setLastUpdatedBy( this.getUserContext(request).getCzybh());
			br.setActionLog("EDIT");
			builderRepService.update(br);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}

	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, BuilderRep builderRep){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		if (builderRep.getEndDate() != null ){
			 Calendar c = Calendar.getInstance();  
		     c.setTime(builderRep.getEndDate());  
		     c.add(Calendar.DAY_OF_MONTH, 1);
		     builderRep.setEndDate(c.getTime());
		}		
		builderRepService.findPageBySql(page, builderRep);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),"工地报备管理-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	
	}
}
