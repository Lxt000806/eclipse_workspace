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
import com.house.home.entity.workflow.ActUser;
import com.house.home.service.workflow.ActUserService;

@Controller
@RequestMapping("/admin/actUser")
public class ActUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActUserController.class);

	@Autowired
	private ActUserService actUserService;

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
			HttpServletResponse response, ActUser actUser) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actUserService.findPageBySql(page, actUser);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActUser列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actUser/actUser_list");
	}
	/**
	 * ActUser查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, ActUser actUser) throws Exception {
		return new ModelAndView("admin/workflow/actUser/actUser_code")
			.addObject("actUser", actUser);
	}
	/**
	 * 跳转到新增ActUser页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActUser页面");
		ActUser actUser = null;
		if (StringUtils.isNotBlank(id)){
			actUser = actUserService.get(ActUser.class, id);
			actUser.setId(null);
		}else{
			actUser = new ActUser();
		}
		
		return new ModelAndView("admin/workflow/actUser/actUser_save")
			.addObject("actUser", actUser);
	}
	/**
	 * 跳转到修改ActUser页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActUser页面");
		ActUser actUser = null;
		if (StringUtils.isNotBlank(id)){
			actUser = actUserService.get(ActUser.class, id);
		}else{
			actUser = new ActUser();
		}
		
		return new ModelAndView("admin/workflow/actUser/actUser_update")
			.addObject("actUser", actUser);
	}
	
	/**
	 * 跳转到查看ActUser页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActUser页面");
		ActUser actUser = actUserService.get(ActUser.class, id);
		
		return new ModelAndView("admin/workflow/actUser/actUser_detail")
				.addObject("actUser", actUser);
	}
	/**
	 * 添加ActUser
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActUser actUser){
		logger.debug("添加ActUser开始");
		try{
			String str = actUserService.getSeqNo("ACT_ID_USER");
			actUser.setId(str);
			this.actUserService.save(actUser);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActUser失败");
		}
	}
	
	/**
	 * 修改ActUser
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActUser actUser){
		logger.debug("修改ActUser开始");
		try{
			this.actUserService.update(actUser);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActUser失败");
		}
	}
	
	/**
	 * 删除ActUser
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActUser开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActUser编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActUser actUser = actUserService.get(ActUser.class, deleteId);
				if(actUser == null)
					continue;
				actUserService.update(actUser);
			}
		}
		logger.debug("删除ActUser IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActUser导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActUser actUser){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actUserService.findPageBySql(page, actUser);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActUser_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
