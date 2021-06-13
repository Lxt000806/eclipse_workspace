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
import com.house.home.entity.commi.CommiCustStakeholderProvide;
import com.house.home.entity.design.Customer;
import com.house.home.service.commi.CommiCustStakeholderProvideService;

@Controller
@RequestMapping("/admin/commiCustStakeholderProvide")
public class CommiCustStakeholderProvideController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiCustStakeholderProvideController.class);

	@Autowired
	private CommiCustStakeholderProvideService commiCustStakeholderProvideService;

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
			HttpServletResponse response, CommiCustStakeholderProvide commiCustStakeholderProvide) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustStakeholderProvideService.findPageBySql(page, commiCustStakeholderProvide);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CommiCustStakeholderProvide列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiCustStakeholderProvide/commiCustStakeholderProvide_list");
	}
	/**
	 * CommiCustStakeholderProvide查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiCustStakeholderProvide/commiCustStakeholderProvide_code");
	}
	/**
	 * 跳转到新增CommiCustStakeholderProvide页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CommiCustStakeholderProvide页面");
		CommiCustStakeholderProvide commiCustStakeholderProvide = null;
		if (StringUtils.isNotBlank(id)){
			commiCustStakeholderProvide = commiCustStakeholderProvideService.get(CommiCustStakeholderProvide.class, Integer.parseInt(id));
			commiCustStakeholderProvide.setPk(null);
		}else{
			commiCustStakeholderProvide = new CommiCustStakeholderProvide();
		}
		commiCustStakeholderProvide.setM_umState("A");
		return new ModelAndView("admin/commi/commiCustStakeholderProvide/commiCustStakeholderProvide_save")
			.addObject("commiCustStakeholderProvide", commiCustStakeholderProvide);
	}
	/**
	 * 跳转到修改CommiCustStakeholderProvide页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CommiCustStakeholderProvide页面");
		CommiCustStakeholderProvide commiCustStakeholderProvide = null;
		if (StringUtils.isNotBlank(id)){
			commiCustStakeholderProvide = commiCustStakeholderProvideService.get(CommiCustStakeholderProvide.class, Integer.parseInt(id));
			commiCustStakeholderProvide.setM_umState("M");
		}else{
			commiCustStakeholderProvide = new CommiCustStakeholderProvide();
		}
		
		return new ModelAndView("admin/commi/commiCustStakeholderProvide/commiCustStakeholderProvide_update")
			.addObject("commiCustStakeholderProvide", commiCustStakeholderProvide);
	}
	
	/**
	 * 跳转到查看CommiCustStakeholderProvide页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看CommiCustStakeholderProvide页面");
		CommiCustStakeholderProvide commiCustStakeholderProvide = commiCustStakeholderProvideService.get(CommiCustStakeholderProvide.class, pk);
		if(StringUtils.isNotBlank(commiCustStakeholderProvide.getRole())){
			Roll roll = commiCustStakeholderProvideService.get(Roll.class, commiCustStakeholderProvide.getRole());
			commiCustStakeholderProvide.setRoleDescr(roll.getDescr());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderProvide.getCustCode())){
			Customer customer = commiCustStakeholderProvideService.get(Customer.class, commiCustStakeholderProvide.getCustCode());
			commiCustStakeholderProvide.setCustDescr(customer.getDescr());
		}
		commiCustStakeholderProvide.setM_umState("V");
		return new ModelAndView("admin/commi/commiCustStakeholderProvide/commiCustStakeholderProvide_view")
				.addObject("commiCustStakeholderProvide", commiCustStakeholderProvide);
	}
	/**
	 * 添加CommiCustStakeholderProvide
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiCustStakeholderProvide commiCustStakeholderProvide){
		logger.debug("添加CommiCustStakeholderProvide开始");
		try{
			commiCustStakeholderProvide.setLastUpdate(new Date());
			commiCustStakeholderProvide.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCustStakeholderProvide.setExpired("F");
			commiCustStakeholderProvide.setActionLog("ADD");
			this.commiCustStakeholderProvideService.save(commiCustStakeholderProvide);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CommiCustStakeholderProvide失败");
		}
	}
	
	/**
	 * 修改CommiCustStakeholderProvide
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiCustStakeholderProvide commiCustStakeholderProvide){
		logger.debug("修改CommiCustStakeholderProvide开始");
		try{
			commiCustStakeholderProvide.setExpired("F");
			commiCustStakeholderProvide.setLastUpdate(new Date());
			commiCustStakeholderProvide.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCustStakeholderProvide.setActionLog("EDIT");
			this.commiCustStakeholderProvideService.update(commiCustStakeholderProvide);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CommiCustStakeholderProvide失败");
		}
	}
	
	/**
	 * 删除CommiCustStakeholderProvide
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CommiCustStakeholderProvide开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CommiCustStakeholderProvide编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CommiCustStakeholderProvide commiCustStakeholderProvide = commiCustStakeholderProvideService.get(CommiCustStakeholderProvide.class, Integer.parseInt(deleteId));
				if(commiCustStakeholderProvide == null)
					continue;
				commiCustStakeholderProvide.setExpired("T");
				commiCustStakeholderProvideService.update(commiCustStakeholderProvide);
			}
		}
		logger.debug("删除CommiCustStakeholderProvide IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiCustStakeholderProvide导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCustStakeholderProvide commiCustStakeholderProvide){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiCustStakeholderProvideService.findPageBySql(page, commiCustStakeholderProvide);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CommiCustStakeholderProvide_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
