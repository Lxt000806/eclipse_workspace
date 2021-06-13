package com.house.home.web.controller.oa;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.UserUtil;
import com.house.framework.commons.workflow.Variable;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.token.FormToken;
import com.house.framework.web.token.impl.FormTokenManagerImpl;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.oa.AppErp;
import com.house.home.entity.oa.Leave;
import com.house.home.entity.oa.OaAll;
import com.house.home.entity.workflow.ActTask;
import com.house.home.entity.workflow.ActVarinst;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.oa.ErpAppService;
import com.house.home.service.oa.ErpAppWorkflowService;
import com.house.home.service.oa.LeaveWorkflowService;
import com.house.home.service.oa.OaAllService;
import com.house.home.service.workflow.ActTaskService;
import com.house.home.service.workflow.ActVarinstService;
import com.sun.jmx.snmp.tasks.TaskServer;

@Controller
@RequestMapping("/admin/oa/erpApp")
public class ErpAppController extends BaseController{

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ErpAppWorkflowService workflowService;
	@Autowired
	private ErpAppService erpAppService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private OaAllService oaAllService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ActVarinstService actVarinstService;
	
	
	private String erpCzyAudit = "erpCzyAudit";
	private String reportBack = "reportBack";

	
	
	
	/**
	 * 员工Erp账号申请
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/goApply")
	public String goAppErp(Model model, HttpServletRequest request) {
		List<Map<String,Object>> list = employeeService.getErpCzy();
		model.addAttribute("appErp", new AppErp());
		model.addAttribute("erpCzyList", list);
		return "admin/oa/leave/applyErp";
	}
	
	/**
	 * 进入流程处理
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/goDealErp")
	public ModelAndView goDeal(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView("admin/oa/leave/dealErpApp");
		String flag = request.getParameter("flag");
		AppErp appErp= erpAppService.getByProcessInstanceId(id);
		String passKey = reportBack.toString();
		if (appErp!=null){
			ActTask actTask = actTaskService.getByProcInstId(appErp.getProcessInstanceId());
			if (actTask!=null){
				mav.addObject("taskId", actTask.getId());
				if (erpCzyAudit.equals(actTask.getTaskDefKey())){
					passKey = "erpCzyPass";
				}
			}
			Czybm czybm = czybmService.get(Czybm.class, appErp.getUserId());
			appErp.setUserName(czybm==null?"":czybm.getZwxm());
		}
		mav.addObject("appErp", appErp);
		mav.addObject("passKey", passKey);
		mav.addObject("flag", flag);
		return mav;
	}
	
	/**
	 * 调整申请
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/goDealErpModefy")
	public ModelAndView goDealErpModefy(HttpServletRequest request, String id) {
		String flag = request.getParameter("flag");
		AppErp appErp = erpAppService.getByProcessInstanceId(id);
		if (appErp!=null){
			ActVarinst actVarinst = actVarinstService.getByInstIdandName(appErp.getProcessInstanceId(), "leaderBackReason");
			appErp.setErpCzyBackReason(actVarinst==null?"":actVarinst.getText());
		}
		List<Map<String,Object>> list = employeeService.getErpCzy();
		ModelAndView mav = new ModelAndView("admin/oa/applyErp/dealErpAppModefy");
		mav.addObject("taskId", getTaskIdByErpApp(appErp));
		mav.addObject("appErp", appErp);
		mav.addObject("erpCzyList", list);
		mav.addObject("flag", flag);
		mav.addObject("showBackReason", true);
		return mav;
	}
	
	
	@RequestMapping("/doStart")
	public void startWorkflow(AppErp appErp, HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = UserUtil.getUserFromSession(request);
			// 用户未登陆不能操作，实际应用使用权限框架实现
			if (user == null || StringUtils.isBlank(user.getId())) {
				ServletUtils.outPrintFail(request, response, "用户未登录");
				return;
			}
			UserContext uc = getUserContext(request);
			appErp.setUserId(user.getId());
			appErp.setApplyTime(new Date());
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = workflowService.startWorkflow(appErp, variables);
			FormToken formToken = SpringContextHolder.getBean("formTokenManagerImpl", FormTokenManagerImpl.class).newFormToken(request);
			JSONObject json = new JSONObject();
			json.put("token", formToken.getToken());
			ServletUtils.outPrintSuccess(request, response, "流程已启动，流程ID：" + processInstance.getId(), json);
		} catch (Exception e) {
			logger.error("启动Erp申请流程失败：", e);
			ServletUtils.outPrintFail(request, response, "流程启动失败");
		}
	}
	
	@RequestMapping("/doComplete")
	public void complete(HttpServletRequest request, HttpServletResponse response, 
			String taskId, Variable var) {
		String passKey = "任务完成";
		try {
			ActTask actTask = actTaskService.get(ActTask.class, taskId);
			if (actTask!=null){
				if (erpCzyAudit.equals(actTask.getTaskDefKey())){
					passKey = "erp管理员审批";
				}
			}
			AppErp appErp= erpAppService.getByProcessInstanceId(actTask.getProcInstId());
			OaAll oa = oaAllService.get(OaAll.class, actTask.getProcInstId());
			User user = UserUtil.getUserFromSession(request);
			
			taskService.claim(taskId, user.getId());
			Map<String, Object> variables = var.getVariableMap();
			//appErp.setType(variables.get("type").toString());//原本在ErpAppModifyContentProcessor里面没有做修改 
			taskService.complete(taskId, variables);
			//审批拒绝
			if (variables.get("erpCzyPass")!=null && !(Boolean) variables.get("erpCzyPass")){
				oa.setStatus("3");
				oaAllService.update(oa);
				ServletUtils.outPrintSuccess(request, response, passKey+"成功");
				return;
			}
			//取消申请
			if ("false".equals(String.valueOf(variables.get("reApply")))){
				erpAppService.delete(appErp);
				oaAllService.delete(oa);
				ServletUtils.outPrintSuccess(request, response, passKey+"成功");
				return;
			}
			//调整申请
			if ("true".equals(String.valueOf(variables.get("reApply")))){
				oa.setStatus("1");
				erpAppService.update(appErp);
				oaAllService.update(oa);
				ServletUtils.outPrintSuccess(request, response, passKey+"成功");
				return;
			}
			if (appErp!=null){
				if ("2".equals(appErp.getType())){//修改密码
					if (variables.get("erpCzyPass")!=null && (Boolean) variables.get("erpCzyPass") 
							&& actTask!=null && StringUtils.isNotBlank(actTask.getProcInstId())){
						oa.setFinishTime(new Date());
						oa.setStatus("2");
						oaAllService.update(oa);
					}
				}else{
					oa.setFinishTime(new Date());
					oa.setStatus("2");
					oaAllService.update(oa);
				}
			}
			ServletUtils.outPrintSuccess(request, response, passKey+"成功");
		} catch (Exception e) {
			logger.error("error on complete task {}, variables={}",
					new Object[] { taskId, var.getVariableMap(), e });
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, passKey+"失败");
		}
	}
	
	public String getTaskIdByErpApp(AppErp appErp){
		String result = null;
		if (appErp!=null && StringUtils.isNotBlank(appErp.getProcessInstanceId())){
			ActTask actTask = actTaskService.getByProcInstId(appErp.getProcessInstanceId());
			if (actTask!=null){
				result = actTask.getId();
			}
		}
		return result;
	}
	
	
}
