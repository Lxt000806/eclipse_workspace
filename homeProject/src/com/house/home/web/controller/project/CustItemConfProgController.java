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
import com.house.home.entity.project.CustItemConfProg;
import com.house.home.service.project.CustItemConfProgService;
import com.house.home.bean.project.CustItemConfProgBean;

@Controller
@RequestMapping("/admin/custItemConfProg")
public class CustItemConfProgController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustItemConfProgController.class);

	@Autowired
	private CustItemConfProgService custItemConfProgService;

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
			HttpServletResponse response, CustItemConfProg custItemConfProg) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custItemConfProgService.findPageBySql(page, custItemConfProg);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustItemConfProg列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/custItemConfProg/custItemConfProg_list");
	}
	/**
	 * CustItemConfProg查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/custItemConfProg/custItemConfProg_code");
	}
	/**
	 * 跳转到新增CustItemConfProg页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustItemConfProg页面");
		CustItemConfProg custItemConfProg = null;
		if (StringUtils.isNotBlank(id)){
			custItemConfProg = custItemConfProgService.get(CustItemConfProg.class, id);
			custItemConfProg.setNo(null);
		}else{
			custItemConfProg = new CustItemConfProg();
		}
		
		return new ModelAndView("admin/project/custItemConfProg/custItemConfProg_save")
			.addObject("custItemConfProg", custItemConfProg);
	}
	/**
	 * 跳转到修改CustItemConfProg页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustItemConfProg页面");
		CustItemConfProg custItemConfProg = null;
		if (StringUtils.isNotBlank(id)){
			custItemConfProg = custItemConfProgService.get(CustItemConfProg.class, id);
		}else{
			custItemConfProg = new CustItemConfProg();
		}
		
		return new ModelAndView("admin/project/custItemConfProg/custItemConfProg_update")
			.addObject("custItemConfProg", custItemConfProg);
	}
	
	/**
	 * 跳转到查看CustItemConfProg页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustItemConfProg页面");
		CustItemConfProg custItemConfProg = custItemConfProgService.get(CustItemConfProg.class, id);
		
		return new ModelAndView("admin/project/custItemConfProg/custItemConfProg_detail")
				.addObject("custItemConfProg", custItemConfProg);
	}
	/**
	 * 添加CustItemConfProg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustItemConfProg custItemConfProg){
		logger.debug("添加CustItemConfProg开始");
		try{
			String str = custItemConfProgService.getSeqNo("tCustItemConfProg");
			custItemConfProg.setNo(str);
			custItemConfProg.setLastUpdate(new Date());
			custItemConfProg.setLastUpdatedBy(getUserContext(request).getCzybh());
			custItemConfProg.setExpired("F");
			this.custItemConfProgService.save(custItemConfProg);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CustItemConfProg失败");
		}
	}
	
	/**
	 * 修改CustItemConfProg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustItemConfProg custItemConfProg){
		logger.debug("修改CustItemConfProg开始");
		try{
			custItemConfProg.setLastUpdate(new Date());
			custItemConfProg.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.custItemConfProgService.update(custItemConfProg);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustItemConfProg失败");
		}
	}
	
	/**
	 * 删除CustItemConfProg
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustItemConfProg开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustItemConfProg编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustItemConfProg custItemConfProg = custItemConfProgService.get(CustItemConfProg.class, deleteId);
				if(custItemConfProg == null)
					continue;
				custItemConfProg.setExpired("T");
				custItemConfProgService.update(custItemConfProg);
			}
		}
		logger.debug("删除CustItemConfProg IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}


}
