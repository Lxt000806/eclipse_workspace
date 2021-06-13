package com.house.home.web.controller.project;

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
import com.house.home.entity.basic.CmpCustType;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.WorkCon;
import com.house.home.service.project.WorkConService;

@Controller
@RequestMapping("/admin/workCon")
public class WorkConController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkConController.class);

	@Autowired
	private WorkConService workConService;

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
			HttpServletResponse response, WorkCon workCon) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workConService.findPageBySql(page, workCon);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * WorkCon列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/workCon/workCon_list");
	}
	/**
	 * WorkCon查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/workCon/workCon_code");
	}
	/**
	 * 跳转到新增WorkCon页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增WorkCon页面");
		WorkCon workCon = null;
		if (StringUtils.isNotBlank(id)){
			workCon = workConService.get(WorkCon.class, Integer.parseInt(id));
			workCon.setPk(null);
		}else{
			workCon = new WorkCon();
		}
		return new ModelAndView("admin/project/workCon/workCon_save")
			.addObject("workCon", workCon);
	}
	/**
	 * 跳转到新增WorkCon页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增WorkCon页面");
		WorkCon workCon = null;
		if (StringUtils.isNotBlank(id)){
			workCon = workConService.get(WorkCon.class, Integer.parseInt(id));
			workCon.setPk(null);
		}else{
			workCon = new WorkCon();
		}
		WorkType2 workType2=workConService.get(WorkType2.class, workCon.getWorkType2());
		Customer customer=workConService.get(Customer.class, workCon.getCustCode());
		workCon.setAddress(customer.getAddress());
		workCon.setCustDescr(customer.getDescr());
		workCon.setWorkType1(workType2.getWorkType1());
		workCon.setWorkType2Descr(workType2.getDescr());
		return new ModelAndView("admin/project/workCon/workCon_save")
			.addObject("workCon", workCon);
	}
	/**
	 * 跳转到修改WorkCon页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改WorkCon页面");
		WorkCon workCon = null;
		if (StringUtils.isNotBlank(id)){
			workCon = workConService.get(WorkCon.class, Integer.parseInt(id));
		}else{
			workCon = new WorkCon();
		}
		WorkType2 workType2=workConService.get(WorkType2.class, workCon.getWorkType2());
		Customer customer=workConService.get(Customer.class, workCon.getCustCode());
		workCon.setAddress(customer.getAddress());
		workCon.setCustDescr(customer.getDescr());
		workCon.setWorkType1(workType2.getWorkType1());
		workCon.setWorkType2Descr(workType2.getDescr());
		return new ModelAndView("admin/project/workCon/workCon_update")
			.addObject("workCon", workCon);
	}
	
	/**
	 * 跳转到查看WorkCon页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看WorkCon页面");
		WorkCon workCon = workConService.get(WorkCon.class, Integer.parseInt(id));
		WorkType2 workType2=workConService.get(WorkType2.class, workCon.getWorkType2());
		Customer customer=workConService.get(Customer.class, workCon.getCustCode());
		workCon.setAddress(customer.getAddress());
		workCon.setCustDescr(customer.getDescr());
		workCon.setWorkType1(workType2.getWorkType1());
		workCon.setWorkType2Descr(workType2.getDescr());
		return new ModelAndView("admin/project/workCon/workCon_view")
				.addObject("workCon", workCon);
	}
	/**
	 * 添加WorkCon
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WorkCon workCon){
		logger.debug("添加WorkCon开始");
		try{
			workCon.setExpired("F");
			workCon.setActionLog("ADD");
			workCon.setLastUpdate(new Date());
			workCon.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.workConService.save(workCon);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加WorkCon失败");
		}
	}
	
	/**
	 * 修改WorkCon
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, WorkCon workCon){
		logger.debug("修改WorkCon开始");
		try{
			if (!"T".equals(workCon.getExpired())) {
				workCon.setExpired("F");
			}
			workCon.setActionLog("EDIT");
			workCon.setLastUpdate(new Date());
			workCon.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.workConService.update(workCon);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改WorkCon失败");
		}
	}
	
	/**
	 * 删除WorkCon
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除WorkCon开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "WorkCon编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				WorkCon workCon = workConService.get(WorkCon.class, Integer.parseInt(deleteId));
				if(workCon == null)
					continue;
				workCon.setExpired("T");
				workConService.update(workCon);
			}
		}
		logger.debug("删除WorkCon IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *WorkCon导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WorkCon workCon){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workConService.findPageBySql(page, workCon);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工人人工合同管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkExist")
	@ResponseBody
	public List<Map<String, Object>> checkExist(HttpServletRequest request,HttpServletResponse response,WorkCon workCon){
		List<Map<String, Object>>list=workConService.checkExist(workCon);
		return list;
	}
}
