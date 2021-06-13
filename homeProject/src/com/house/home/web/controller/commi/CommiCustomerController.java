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
import com.house.home.entity.commi.CommiCustomer;
import com.house.home.service.commi.CommiCustomerService;

@Controller
@RequestMapping("/admin/commiCustomer")
public class CommiCustomerController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiCustomerController.class);

	@Autowired
	private CommiCustomerService commiCustomerService;

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
			HttpServletResponse response, CommiCustomer commiCustomer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustomerService.findPageBySql(page, commiCustomer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CommiCustomer列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiCustomer/commiCustomer_list");
	}
	/**
	 * CommiCustomer查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiCustomer/commiCustomer_code");
	}
	/**
	 * 跳转到新增CommiCustomer页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CommiCustomer页面");
		CommiCustomer commiCustomer = null;
		if (StringUtils.isNotBlank(id)){
			commiCustomer = commiCustomerService.get(CommiCustomer.class, Integer.parseInt(id));
			commiCustomer.setPk(null);
		}else{
			commiCustomer = new CommiCustomer();
		}
		commiCustomer.setM_umState("A");
		return new ModelAndView("admin/commi/commiCustomer/commiCustomer_save")
			.addObject("commiCustomer", commiCustomer);
	}
	/**
	 * 跳转到修改CommiCustomer页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CommiCustomer页面");
		CommiCustomer commiCustomer = null;
		if (StringUtils.isNotBlank(id)){
			commiCustomer = commiCustomerService.get(CommiCustomer.class, Integer.parseInt(id));
			commiCustomer.setM_umState("M");
		}else{
			commiCustomer = new CommiCustomer();
		}
		
		return new ModelAndView("admin/commi/commiCustomer/commiCustomer_update")
			.addObject("commiCustomer", commiCustomer);
	}
	
	/**
	 * 跳转到查看CommiCustomer页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CommiCustomer页面");
		CommiCustomer commiCustomer = commiCustomerService.get(CommiCustomer.class, Integer.parseInt(id));
		commiCustomer.setM_umState("V");
		return new ModelAndView("admin/commi/commiCustomer/commiCustomer_view")
				.addObject("commiCustomer", commiCustomer);
	}
	/**
	 * 添加CommiCustomer
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiCustomer commiCustomer){
		logger.debug("添加CommiCustomer开始");
		try{
			commiCustomer.setLastUpdate(new Date());
			commiCustomer.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCustomer.setExpired("F");
			commiCustomer.setActionLog("ADD");
			this.commiCustomerService.save(commiCustomer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CommiCustomer失败");
		}
	}
	
	/**
	 * 修改CommiCustomer
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiCustomer commiCustomer){
		logger.debug("修改CommiCustomer开始");
		try{
			commiCustomer.setExpired("F");
			commiCustomer.setLastUpdate(new Date());
			commiCustomer.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCustomer.setActionLog("EDIT");
			this.commiCustomerService.update(commiCustomer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CommiCustomer失败");
		}
	}
	
	/**
	 * 删除CommiCustomer
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CommiCustomer开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CommiCustomer编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CommiCustomer commiCustomer = commiCustomerService.get(CommiCustomer.class, Integer.parseInt(deleteId));
				if(commiCustomer == null)
					continue;
				commiCustomer.setExpired("T");
				commiCustomerService.update(commiCustomer);
			}
		}
		logger.debug("删除CommiCustomer IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiCustomer导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCustomer commiCustomer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiCustomerService.findPageBySql(page, commiCustomer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CommiCustomer_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
