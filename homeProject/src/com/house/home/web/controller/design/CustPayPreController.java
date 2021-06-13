package com.house.home.web.controller.design;

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
import com.house.home.entity.design.CustPayPre;
import com.house.home.service.design.CustPayPreService;
import com.house.home.bean.design.CustPayPreBean;

@Controller
@RequestMapping("/admin/custPayPre")
public class CustPayPreController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustPayPreController.class);

	@Autowired
	private CustPayPreService custPayPreService;

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
			HttpServletResponse response, CustPayPre custPayPre) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayPreService.findPageBySql(page, custPayPre);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustPayPre列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custPayPre/custPayPre_list");
	}
	/**
	 * CustPayPre查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custPayPre/custPayPre_code");
	}
	/**
	 * 跳转到新增CustPayPre页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustPayPre页面");
		CustPayPre custPayPre = null;
		if (StringUtils.isNotBlank(id)){
			custPayPre = custPayPreService.get(CustPayPre.class, Integer.parseInt(id));
			custPayPre.setPk(null);
		}else{
			custPayPre = new CustPayPre();
		}
		
		return new ModelAndView("admin/design/custPayPre/custPayPre_save")
			.addObject("custPayPre", custPayPre);
	}
	/**
	 * 跳转到修改CustPayPre页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustPayPre页面");
		CustPayPre custPayPre = null;
		if (StringUtils.isNotBlank(id)){
			custPayPre = custPayPreService.get(CustPayPre.class, Integer.parseInt(id));
		}else{
			custPayPre = new CustPayPre();
		}
		
		return new ModelAndView("admin/design/custPayPre/custPayPre_update")
			.addObject("custPayPre", custPayPre);
	}
	
	/**
	 * 跳转到查看CustPayPre页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustPayPre页面");
		CustPayPre custPayPre = custPayPreService.get(CustPayPre.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/design/custPayPre/custPayPre_detail")
				.addObject("custPayPre", custPayPre);
	}
	/**
	 * 添加CustPayPre
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustPayPre custPayPre){
		logger.debug("添加CustPayPre开始");
		try{
			this.custPayPreService.save(custPayPre);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CustPayPre失败");
		}
	}
	
	/**
	 * 修改CustPayPre
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustPayPre custPayPre){
		logger.debug("修改CustPayPre开始");
		try{
			custPayPre.setLastUpdate(new Date());
			custPayPre.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.custPayPreService.update(custPayPre);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustPayPre失败");
		}
	}
	
	/**
	 * 删除CustPayPre
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustPayPre开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustPayPre编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustPayPre custPayPre = custPayPreService.get(CustPayPre.class, Integer.parseInt(deleteId));
				if(custPayPre == null)
					continue;
				custPayPre.setExpired("T");
				custPayPreService.update(custPayPre);
			}
		}
		logger.debug("删除CustPayPre IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}



}
