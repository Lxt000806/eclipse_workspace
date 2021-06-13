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
import com.house.home.entity.workflow.ActMemberShip;
import com.house.home.service.workflow.ActMemberShipService;

@Controller
@RequestMapping("/admin/actMemberShip")
public class ActMemberShipController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActMemberShipController.class);

	@Autowired
	private ActMemberShipService actMemberShipService;

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
			HttpServletResponse response, ActMemberShip actMemberShip) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actMemberShipService.findPageBySql(page, actMemberShip);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActMemberShip列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actMemberShip/actMemberShip_list");
	}
	/**
	 * ActMemberShip查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actMemberShip/actMemberShip_code");
	}
	/**
	 * 跳转到新增ActMemberShip页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActMemberShip页面");
		ActMemberShip actMemberShip = null;
		if (StringUtils.isNotBlank(id)){
			actMemberShip = actMemberShipService.get(ActMemberShip.class, id);
			actMemberShip.setUserId(null);
		}else{
			actMemberShip = new ActMemberShip();
		}
		
		return new ModelAndView("admin/workflow/actMemberShip/actMemberShip_save")
			.addObject("actMemberShip", actMemberShip);
	}
	/**
	 * 跳转到修改ActMemberShip页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActMemberShip页面");
		ActMemberShip actMemberShip = null;
		if (StringUtils.isNotBlank(id)){
			actMemberShip = actMemberShipService.get(ActMemberShip.class, id);
		}else{
			actMemberShip = new ActMemberShip();
		}
		
		return new ModelAndView("admin/workflow/actMemberShip/actMemberShip_update")
			.addObject("actMemberShip", actMemberShip);
	}
	
	/**
	 * 跳转到查看ActMemberShip页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActMemberShip页面");
		ActMemberShip actMemberShip = actMemberShipService.get(ActMemberShip.class, id);
		
		return new ModelAndView("admin/workflow/actMemberShip/actMemberShip_detail")
				.addObject("actMemberShip", actMemberShip);
	}
	/**
	 * 添加ActMemberShip
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActMemberShip actMemberShip){
		logger.debug("添加ActMemberShip开始");
		try{
			String str = actMemberShipService.getSeqNo("ACT_ID_MEMBERSHIP");
			actMemberShip.setUserId(str);
			this.actMemberShipService.save(actMemberShip);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActMemberShip失败");
		}
	}
	
	/**
	 * 修改ActMemberShip
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActMemberShip actMemberShip){
		logger.debug("修改ActMemberShip开始");
		try{
			this.actMemberShipService.update(actMemberShip);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActMemberShip失败");
		}
	}
	
	/**
	 * 删除ActMemberShip
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActMemberShip开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActMemberShip编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActMemberShip actMemberShip = actMemberShipService.get(ActMemberShip.class, deleteId);
				if(actMemberShip == null)
					continue;
				actMemberShipService.update(actMemberShip);
			}
		}
		logger.debug("删除ActMemberShip IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActMemberShip导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActMemberShip actMemberShip){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actMemberShipService.findPageBySql(page, actMemberShip);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActMemberShip_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
