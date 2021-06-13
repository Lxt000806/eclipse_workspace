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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.AgainSign;
import com.house.home.service.design.AgainSignService;

@Controller
@RequestMapping("/admin/againSign")
public class AgainSignController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AgainSignController.class);

	@Autowired
	private AgainSignService againSignService;

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
			HttpServletResponse response, AgainSign againSign) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		againSignService.findPageBySql(page, againSign);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * AgainSign列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/againSign/againSign_list");
	}
	/**
	 * AgainSign查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/againSign/againSign_code");
	}
	/**
	 * 跳转到新增AgainSign页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增AgainSign页面");
		AgainSign againSign = null;
		if (StringUtils.isNotBlank(id)){
			againSign = againSignService.get(AgainSign.class, Integer.parseInt(id));
			againSign.setPk(null);
		}else{
			againSign = new AgainSign();
		}
		
		return new ModelAndView("admin/design/againSign/againSign_save")
			.addObject("againSign", againSign);
	}
	/**
	 * 跳转到修改AgainSign页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改AgainSign页面");
		AgainSign againSign = null;
		if (StringUtils.isNotBlank(id)){
			againSign = againSignService.get(AgainSign.class, Integer.parseInt(id));
		}else{
			againSign = new AgainSign();
		}
		
		return new ModelAndView("admin/design/againSign/againSign_update")
			.addObject("againSign", againSign);
	}
	
	/**
	 * 跳转到查看AgainSign页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看AgainSign页面");
		AgainSign againSign = againSignService.get(AgainSign.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/design/againSign/againSign_detail")
				.addObject("againSign", againSign);
	}
	/**
	 * 添加AgainSign
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, AgainSign againSign){
		logger.debug("添加AgainSign开始");
		try{
			this.againSignService.save(againSign);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加AgainSign失败");
		}
	}
	
	/**
	 * 修改AgainSign
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, AgainSign againSign){
		logger.debug("修改AgainSign开始");
		try{
			againSign.setLastUpdate(new Date());
			againSign.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.againSignService.update(againSign);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改AgainSign失败");
		}
	}
	
	/**
	 * 删除AgainSign
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除AgainSign开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "AgainSign编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				AgainSign againSign = againSignService.get(AgainSign.class, Integer.parseInt(deleteId));
				if(againSign == null)
					continue;
				againSign.setExpired("T");
				againSignService.update(againSign);
			}
		}
		logger.debug("删除AgainSign IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *AgainSign导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, AgainSign againSign){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		againSignService.findPageBySql(page, againSign);
	}

}
