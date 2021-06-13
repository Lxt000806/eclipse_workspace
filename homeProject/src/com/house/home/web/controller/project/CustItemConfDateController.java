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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.CustItemConfDate;
import com.house.home.service.project.CustItemConfDateService;
import com.house.home.bean.project.CustItemConfDateBean;

@Controller
@RequestMapping("/admin/custItemConfDate")
public class CustItemConfDateController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustItemConfDateController.class);

	@Autowired
	private CustItemConfDateService custItemConfDateService;

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
			HttpServletResponse response, CustItemConfDate custItemConfDate) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custItemConfDateService.findPageBySql(page, custItemConfDate);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustItemConfDate列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/custItemConfDate/custItemConfDate_list");
	}
	/**
	 * CustItemConfDate查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/custItemConfDate/custItemConfDate_code");
	}
	/**
	 * 跳转到新增CustItemConfDate页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustItemConfDate页面");
		CustItemConfDate custItemConfDate = null;
		if (StringUtils.isNotBlank(id)){
			custItemConfDate = custItemConfDateService.get(CustItemConfDate.class, id);
			custItemConfDate.setCustCode(null);
		}else{
			custItemConfDate = new CustItemConfDate();
		}
		
		return new ModelAndView("admin/project/custItemConfDate/custItemConfDate_save")
			.addObject("custItemConfDate", custItemConfDate);
	}
	/**
	 * 跳转到修改CustItemConfDate页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustItemConfDate页面");
		CustItemConfDate custItemConfDate = null;
		if (StringUtils.isNotBlank(id)){
			custItemConfDate = custItemConfDateService.get(CustItemConfDate.class, id);
		}else{
			custItemConfDate = new CustItemConfDate();
		}
		
		return new ModelAndView("admin/project/custItemConfDate/custItemConfDate_update")
			.addObject("custItemConfDate", custItemConfDate);
	}
	
	/**
	 * 跳转到查看CustItemConfDate页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustItemConfDate页面");
		CustItemConfDate custItemConfDate = custItemConfDateService.get(CustItemConfDate.class, id);
		
		return new ModelAndView("admin/project/custItemConfDate/custItemConfDate_detail")
				.addObject("custItemConfDate", custItemConfDate);
	}
	/**
	 * 添加CustItemConfDate
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustItemConfDate custItemConfDate){
		logger.debug("添加CustItemConfDate开始");
		try{
			String str = custItemConfDateService.getSeqNo("tCustItemConfDate");
			custItemConfDate.setCustCode(str);
			custItemConfDate.setLastUpdate(new Date());
			custItemConfDate.setLastUpdatedBy(getUserContext(request).getCzybh());
			custItemConfDate.setExpired("F");
			this.custItemConfDateService.save(custItemConfDate);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CustItemConfDate失败");
		}
	}
	
	/**
	 * 修改CustItemConfDate
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustItemConfDate custItemConfDate){
		logger.debug("修改CustItemConfDate开始");
		try{
			custItemConfDate.setLastUpdate(new Date());
			custItemConfDate.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.custItemConfDateService.update(custItemConfDate);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustItemConfDate失败");
		}
	}
	
	/**
	 * 删除CustItemConfDate
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustItemConfDate开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustItemConfDate编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustItemConfDate custItemConfDate = custItemConfDateService.get(CustItemConfDate.class, deleteId);
				if(custItemConfDate == null)
					continue;
				custItemConfDate.setExpired("T");
				custItemConfDateService.update(custItemConfDate);
			}
		}
		logger.debug("删除CustItemConfDate IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}


}
