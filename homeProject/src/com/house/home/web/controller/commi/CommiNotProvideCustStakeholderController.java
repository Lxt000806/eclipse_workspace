package com.house.home.web.controller.commi;

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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Roll;
import com.house.home.entity.commi.CommiNotProvideCustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.service.commi.CommiNotProvideCustStakeholderService;

@Controller
@RequestMapping("/admin/commiNotProvideCustStakeholder")
public class CommiNotProvideCustStakeholderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiNotProvideCustStakeholderController.class);

	@Autowired
	private CommiNotProvideCustStakeholderService commiNotProvideCustStakeholderService;

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
			HttpServletResponse response, CommiNotProvideCustStakeholder commiNotProvideCustStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiNotProvideCustStakeholderService.findPageBySql(page, commiNotProvideCustStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CommiNotProvideCustStakeholder列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,String m_umState) throws Exception {

		return new ModelAndView("admin/commi/commiNotProvideCustStakeholder/commiNotProvideCustStakeholder_list")
					.addObject("m_umState", m_umState);
	}
	/**
	 * CommiNotProvideCustStakeholder查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiNotProvideCustStakeholder/commiNotProvideCustStakeholder_code");
	}
	/**
	 * 跳转到新增CommiNotProvideCustStakeholder页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CommiNotProvideCustStakeholder页面");
		CommiNotProvideCustStakeholder commiNotProvideCustStakeholder = null;
		if (StringUtils.isNotBlank(id)){
			commiNotProvideCustStakeholder = commiNotProvideCustStakeholderService.get(CommiNotProvideCustStakeholder.class, Integer.parseInt(id));
			commiNotProvideCustStakeholder.setPk(null);
		}else{
			commiNotProvideCustStakeholder = new CommiNotProvideCustStakeholder();
		}
		commiNotProvideCustStakeholder.setM_umState("A");
		return new ModelAndView("admin/commi/commiNotProvideCustStakeholder/commiNotProvideCustStakeholder_save")
			.addObject("commiNotProvideCustStakeholder", commiNotProvideCustStakeholder);
	}
	/**
	 * 跳转到修改CommiNotProvideCustStakeholder页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改CommiNotProvideCustStakeholder页面");
		CommiNotProvideCustStakeholder commiNotProvideCustStakeholder = 
				commiNotProvideCustStakeholderService.get(CommiNotProvideCustStakeholder.class, pk);
		if(StringUtils.isNotBlank(commiNotProvideCustStakeholder.getCustCode())){
			Customer customer = 
					commiNotProvideCustStakeholderService.get(Customer.class, commiNotProvideCustStakeholder.getCustCode());
			commiNotProvideCustStakeholder.setCustDescr(customer.getDescr());
		}
		
		if(StringUtils.isNotBlank(commiNotProvideCustStakeholder.getRole())){
			Roll roll = commiNotProvideCustStakeholderService.get(Roll.class, commiNotProvideCustStakeholder.getRole());
			commiNotProvideCustStakeholder.setRoleDescr(roll.getDescr());
		}
		
		commiNotProvideCustStakeholder.setM_umState("M");
		
		return new ModelAndView("admin/commi/commiNotProvideCustStakeholder/commiNotProvideCustStakeholder_save")
			.addObject("commiNotProvideCustStakeholder", commiNotProvideCustStakeholder);
	}
	
	/**
	 * 跳转到查看CommiNotProvideCustStakeholder页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看CommiNotProvideCustStakeholder页面");
		CommiNotProvideCustStakeholder commiNotProvideCustStakeholder = 
				commiNotProvideCustStakeholderService.get(CommiNotProvideCustStakeholder.class, pk);

		if(StringUtils.isNotBlank(commiNotProvideCustStakeholder.getCustCode())){
			Customer customer = 
					commiNotProvideCustStakeholderService.get(Customer.class, commiNotProvideCustStakeholder.getCustCode());
			commiNotProvideCustStakeholder.setCustDescr(customer.getDescr());
		}
		
		if(StringUtils.isNotBlank(commiNotProvideCustStakeholder.getRole())){
			Roll roll = commiNotProvideCustStakeholderService.get(Roll.class, commiNotProvideCustStakeholder.getRole());
			commiNotProvideCustStakeholder.setRoleDescr(roll.getDescr());
		}
		
		commiNotProvideCustStakeholder.setM_umState("V");
		return new ModelAndView("admin/commi/commiNotProvideCustStakeholder/commiNotProvideCustStakeholder_save")
				.addObject("commiNotProvideCustStakeholder", commiNotProvideCustStakeholder);
	}
	/**
	 * 添加CommiNotProvideCustStakeholder
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiNotProvideCustStakeholder commiNotProvideCustStakeholder){
		logger.debug("添加CommiNotProvideCustStakeholder开始");
		try{
			commiNotProvideCustStakeholder.setLastUpdate(new Date());
			commiNotProvideCustStakeholder.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiNotProvideCustStakeholder.setExpired("F");
			commiNotProvideCustStakeholder.setActionLog("ADD");
			this.commiNotProvideCustStakeholderService.save(commiNotProvideCustStakeholder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CommiNotProvideCustStakeholder失败");
		}
	}
	
	/**
	 * 修改CommiNotProvideCustStakeholder
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiNotProvideCustStakeholder commiNotProvideCustStakeholder){
		logger.debug("修改CommiNotProvideCustStakeholder开始");
		try{
			commiNotProvideCustStakeholder.setLastUpdate(new Date());
			commiNotProvideCustStakeholder.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiNotProvideCustStakeholder.setActionLog("EDIT");
			commiNotProvideCustStakeholder.setExpired("F");
			this.commiNotProvideCustStakeholderService.update(commiNotProvideCustStakeholder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CommiNotProvideCustStakeholder失败");
		}
	}
	
	/**
	 * 删除CommiNotProvideCustStakeholder
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CommiNotProvideCustStakeholder开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CommiNotProvideCustStakeholder编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CommiNotProvideCustStakeholder commiNotProvideCustStakeholder = commiNotProvideCustStakeholderService.get(CommiNotProvideCustStakeholder.class, Integer.parseInt(deleteId));
				if(commiNotProvideCustStakeholder == null)
					continue;
				commiNotProvideCustStakeholderService.delete(commiNotProvideCustStakeholder);
			}
		}
		logger.debug("删除CommiNotProvideCustStakeholder IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiNotProvideCustStakeholder导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiNotProvideCustStakeholder commiNotProvideCustStakeholder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiNotProvideCustStakeholderService.findPageBySql(page, commiNotProvideCustStakeholder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CommiNotProvideCustStakeholder_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
