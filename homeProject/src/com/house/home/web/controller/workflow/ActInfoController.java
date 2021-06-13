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
import com.house.home.entity.workflow.ActInfo;
import com.house.home.service.workflow.ActInfoService;

@Controller
@RequestMapping("/admin/actInfo")
public class ActInfoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActInfoController.class);

	@Autowired
	private ActInfoService actInfoService;

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
			HttpServletResponse response, ActInfo actInfo) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actInfoService.findPageBySql(page, actInfo);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActInfo列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actInfo/actInfo_list");
	}
	/**
	 * ActInfo查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actInfo/actInfo_code");
	}
	/**
	 * 跳转到新增ActInfo页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActInfo页面");
		ActInfo actInfo = null;
		if (StringUtils.isNotBlank(id)){
			actInfo = actInfoService.get(ActInfo.class, id);
			actInfo.setId(null);
		}else{
			actInfo = new ActInfo();
		}
		
		return new ModelAndView("admin/workflow/actInfo/actInfo_save")
			.addObject("actInfo", actInfo);
	}
	/**
	 * 跳转到修改ActInfo页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActInfo页面");
		ActInfo actInfo = null;
		if (StringUtils.isNotBlank(id)){
			actInfo = actInfoService.get(ActInfo.class, id);
		}else{
			actInfo = new ActInfo();
		}
		
		return new ModelAndView("admin/workflow/actInfo/actInfo_update")
			.addObject("actInfo", actInfo);
	}
	
	/**
	 * 跳转到查看ActInfo页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActInfo页面");
		ActInfo actInfo = actInfoService.get(ActInfo.class, id);
		
		return new ModelAndView("admin/workflow/actInfo/actInfo_detail")
				.addObject("actInfo", actInfo);
	}
	/**
	 * 添加ActInfo
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActInfo actInfo){
		logger.debug("添加ActInfo开始");
		try{
			String str = actInfoService.getSeqNo("ACT_ID_INFO");
			actInfo.setId(str);
			this.actInfoService.save(actInfo);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActInfo失败");
		}
	}
	
	/**
	 * 修改ActInfo
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActInfo actInfo){
		logger.debug("修改ActInfo开始");
		try{
			this.actInfoService.update(actInfo);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActInfo失败");
		}
	}
	
	/**
	 * 删除ActInfo
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActInfo开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActInfo编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActInfo actInfo = actInfoService.get(ActInfo.class, deleteId);
				if(actInfo == null)
					continue;
				actInfoService.update(actInfo);
			}
		}
		logger.debug("删除ActInfo IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActInfo导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActInfo actInfo){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actInfoService.findPageBySql(page, actInfo);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActInfo_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
