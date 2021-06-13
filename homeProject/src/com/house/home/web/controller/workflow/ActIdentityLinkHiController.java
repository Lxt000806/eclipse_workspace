package com.house.home.web.controller.workflow;

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
import com.house.home.entity.workflow.ActIdentityLinkHi;
import com.house.home.service.workflow.ActIdentityLinkHiService;

@Controller
@RequestMapping("/admin/actIdentityLinkHi")
public class ActIdentityLinkHiController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActIdentityLinkHiController.class);

	@Autowired
	private ActIdentityLinkHiService actIdentityLinkHiService;

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
			HttpServletResponse response, ActIdentityLinkHi actIdentityLinkHi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actIdentityLinkHiService.findPageBySql(page, actIdentityLinkHi);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActIdentityLinkHi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actIdentityLinkHi/actIdentityLinkHi_list");
	}
	/**
	 * ActIdentityLinkHi查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actIdentityLinkHi/actIdentityLinkHi_code");
	}
	/**
	 * 跳转到新增ActIdentityLinkHi页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActIdentityLinkHi页面");
		ActIdentityLinkHi actIdentityLinkHi = null;
		if (StringUtils.isNotBlank(id)){
			actIdentityLinkHi = actIdentityLinkHiService.get(ActIdentityLinkHi.class, id);
			actIdentityLinkHi.setId(null);
		}else{
			actIdentityLinkHi = new ActIdentityLinkHi();
		}
		
		return new ModelAndView("admin/workflow/actIdentityLinkHi/actIdentityLinkHi_save")
			.addObject("actIdentityLinkHi", actIdentityLinkHi);
	}
	/**
	 * 跳转到修改ActIdentityLinkHi页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActIdentityLinkHi页面");
		ActIdentityLinkHi actIdentityLinkHi = null;
		if (StringUtils.isNotBlank(id)){
			actIdentityLinkHi = actIdentityLinkHiService.get(ActIdentityLinkHi.class, id);
		}else{
			actIdentityLinkHi = new ActIdentityLinkHi();
		}
		
		return new ModelAndView("admin/workflow/actIdentityLinkHi/actIdentityLinkHi_update")
			.addObject("actIdentityLinkHi", actIdentityLinkHi);
	}
	
	/**
	 * 跳转到查看ActIdentityLinkHi页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActIdentityLinkHi页面");
		ActIdentityLinkHi actIdentityLinkHi = actIdentityLinkHiService.get(ActIdentityLinkHi.class, id);
		
		return new ModelAndView("admin/workflow/actIdentityLinkHi/actIdentityLinkHi_detail")
				.addObject("actIdentityLinkHi", actIdentityLinkHi);
	}
	/**
	 * 添加ActIdentityLinkHi
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActIdentityLinkHi actIdentityLinkHi){
		logger.debug("添加ActIdentityLinkHi开始");
		try{
			String str = actIdentityLinkHiService.getSeqNo("ACT_HI_IDENTITYLINK");
			actIdentityLinkHi.setId(str);
			this.actIdentityLinkHiService.save(actIdentityLinkHi);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActIdentityLinkHi失败");
		}
	}
	
	/**
	 * 修改ActIdentityLinkHi
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActIdentityLinkHi actIdentityLinkHi){
		logger.debug("修改ActIdentityLinkHi开始");
		try{
			this.actIdentityLinkHiService.update(actIdentityLinkHi);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActIdentityLinkHi失败");
		}
	}
	
	/**
	 * 删除ActIdentityLinkHi
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActIdentityLinkHi开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActIdentityLinkHi编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActIdentityLinkHi actIdentityLinkHi = actIdentityLinkHiService.get(ActIdentityLinkHi.class, deleteId);
				if(actIdentityLinkHi == null)
					continue;
				actIdentityLinkHiService.update(actIdentityLinkHi);
			}
		}
		logger.debug("删除ActIdentityLinkHi IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActIdentityLinkHi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActIdentityLinkHi actIdentityLinkHi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actIdentityLinkHiService.findPageBySql(page, actIdentityLinkHi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActIdentityLinkHi_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
