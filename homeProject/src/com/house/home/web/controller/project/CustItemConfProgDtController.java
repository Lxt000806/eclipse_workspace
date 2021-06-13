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
import com.house.home.entity.project.CustItemConfProgDt;
import com.house.home.service.project.CustItemConfProgDtService;
import com.house.home.bean.project.CustItemConfProgDtBean;

@Controller
@RequestMapping("/admin/custItemConfProgDt")
public class CustItemConfProgDtController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustItemConfProgDtController.class);

	@Autowired
	private CustItemConfProgDtService custItemConfProgDtService;

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
			HttpServletResponse response, CustItemConfProgDt custItemConfProgDt) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custItemConfProgDtService.findPageBySql(page, custItemConfProgDt);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustItemConfProgDt列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/custItemConfProgDt/custItemConfProgDt_list");
	}
	/**
	 * CustItemConfProgDt查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/custItemConfProgDt/custItemConfProgDt_code");
	}
	/**
	 * 跳转到新增CustItemConfProgDt页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustItemConfProgDt页面");
		CustItemConfProgDt custItemConfProgDt = null;
		if (StringUtils.isNotBlank(id)){
			custItemConfProgDt = custItemConfProgDtService.get(CustItemConfProgDt.class, Integer.parseInt(id));
			custItemConfProgDt.setPk(null);
		}else{
			custItemConfProgDt = new CustItemConfProgDt();
		}
		
		return new ModelAndView("admin/project/custItemConfProgDt/custItemConfProgDt_save")
			.addObject("custItemConfProgDt", custItemConfProgDt);
	}
	/**
	 * 跳转到修改CustItemConfProgDt页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustItemConfProgDt页面");
		CustItemConfProgDt custItemConfProgDt = null;
		if (StringUtils.isNotBlank(id)){
			custItemConfProgDt = custItemConfProgDtService.get(CustItemConfProgDt.class, Integer.parseInt(id));
		}else{
			custItemConfProgDt = new CustItemConfProgDt();
		}
		
		return new ModelAndView("admin/project/custItemConfProgDt/custItemConfProgDt_update")
			.addObject("custItemConfProgDt", custItemConfProgDt);
	}
	
	/**
	 * 跳转到查看CustItemConfProgDt页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustItemConfProgDt页面");
		CustItemConfProgDt custItemConfProgDt = custItemConfProgDtService.get(CustItemConfProgDt.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/custItemConfProgDt/custItemConfProgDt_detail")
				.addObject("custItemConfProgDt", custItemConfProgDt);
	}
	/**
	 * 添加CustItemConfProgDt
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustItemConfProgDt custItemConfProgDt){
		logger.debug("添加CustItemConfProgDt开始");
		try{
			this.custItemConfProgDtService.save(custItemConfProgDt);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CustItemConfProgDt失败");
		}
	}
	
	/**
	 * 修改CustItemConfProgDt
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustItemConfProgDt custItemConfProgDt){
		logger.debug("修改CustItemConfProgDt开始");
		try{
			custItemConfProgDt.setLastUpdate(new Date());
			custItemConfProgDt.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.custItemConfProgDtService.update(custItemConfProgDt);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustItemConfProgDt失败");
		}
	}
	
	/**
	 * 删除CustItemConfProgDt
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustItemConfProgDt开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustItemConfProgDt编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustItemConfProgDt custItemConfProgDt = custItemConfProgDtService.get(CustItemConfProgDt.class, Integer.parseInt(deleteId));
				if(custItemConfProgDt == null)
					continue;
				custItemConfProgDt.setExpired("T");
				custItemConfProgDtService.update(custItemConfProgDt);
			}
		}
		logger.debug("删除CustItemConfProgDt IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}


}
