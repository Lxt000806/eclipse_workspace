package com.house.home.web.controller.oa;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.UserUtil;
import com.house.framework.commons.workflow.Variable;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.token.FormToken;
import com.house.framework.web.token.impl.FormTokenManagerImpl;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.oa.Leave;
import com.house.home.entity.oa.OaAll;
import com.house.home.entity.workflow.ActTask;
import com.house.home.entity.workflow.ActVarinst;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.oa.LeaveService;
import com.house.home.service.oa.LeaveWorkflowService;
import com.house.home.service.oa.OaAllService;
import com.house.home.service.workflow.ActTaskService;
import com.house.home.service.workflow.ActVarinstService;

/**
 * 请假控制器，包含保存、启动流程
 * @author HenryYan
 */
@Controller
@RequestMapping("/admin/oa/leave")
public class LeaveController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected LeaveService leaveService;

	@Autowired
	protected LeaveWorkflowService workflowService;

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected ActTaskService actTaskService;
	@Autowired
	protected CzybmService czybmService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ActVarinstService actVarinstService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private OaAllService oaAllService;
	
	//private String procDefKey = "leave";
	private String deptLeaderAudit = "deptLeaderAudit";
	private String deptLeaderTwoAudit = "deptLeaderTwoAudit";
	private String bossAudit = "bossAudit";
	private String modifyApply = "modifyApply";
	private String reportBack = "reportBack";

	/**
	 * 员工出门条
	 * @param model
	 * @return
	 */
	@RequestMapping("/goApplyCmt")
	public String goApplyCmt(Model model, HttpServletRequest request) {
		List<Map<String,Object>> list = employeeService.getDeptLeadersList(UserUtil.getUserFromSession(request).getId());
		model.addAttribute("leave", new Leave());
		model.addAttribute("leaderList", list);
		return "admin/oa/leave/leaveApplyCmt";
	}
	
	/**
	 * 调休单
	 * @param model
	 * @return
	 */
	@RequestMapping("/goApplyTxd")
	public String goApplyTxd(Model model, HttpServletRequest request) {
		List<Map<String,Object>> list = employeeService.getDeptLeadersList(UserUtil.getUserFromSession(request).getId());
		model.addAttribute("leave", new Leave());
		model.addAttribute("leaderList", list);
		return "admin/oa/leave/leaveApplyTxd";
	}
	
	/**
	 * 请假申请
	 * @param model
	 * @return
	 */
	@RequestMapping("/goApply")
	public String goApply(Model model, HttpServletRequest request) {
		List<Map<String,Object>> list = employeeService.getDeptLeadersList(UserUtil.getUserFromSession(request).getId());
		model.addAttribute("leave", new Leave());
		model.addAttribute("leaderList", list);
		return "admin/oa/leave/leaveApply";
	}
	
	/**
	 * 调整申请
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/goDealApply")
	public ModelAndView goDealApply(HttpServletRequest request, String id) {
		String flag = request.getParameter("flag");
		Leave leave = leaveService.getByProcessInstanceId(id);
		if (leave!=null){
			ActVarinst actVarinst = actVarinstService.getByInstIdandName(leave.getProcessInstanceId(), "leaderBackReason");
			leave.setLeaderBackReason(actVarinst==null?"":actVarinst.getText());
		}
		ModelAndView mav = new ModelAndView("admin/oa/leave/dealApply");
		mav.addObject("taskId", getTaskIdByLeave(leave));
		mav.addObject("leave", leave);
		mav.addObject("flag", flag);
		mav.addObject("showBackReason", true);
		return mav;
	}
	
	/**
	 * 销假
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/goDealBack")
	public ModelAndView goDealBack(HttpServletRequest request, String id) {
		Leave leave = leaveService.get(Leave.class, Long.valueOf(id));
		ModelAndView mav = new ModelAndView("admin/oa/leave/dealBack");
		mav.addObject("taskId", getTaskIdByLeave(leave));
		mav.addObject("leave", leave);
		return mav;
	}
	
	/**
	 * 启动请假流程
	 * @param leave
	 */
	@RequestMapping("/doStart")
	public void startWorkflow(Leave leave, HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = UserUtil.getUserFromSession(request);
			// 用户未登陆不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
			if (user == null || StringUtils.isBlank(user.getId())) {
				ServletUtils.outPrintFail(request, response, "用户未登录");
				return;
			}
			leave.setUserId(user.getId());
			leave.setApplyTime(new Date());
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = workflowService.startWorkflow(leave, variables);
			FormToken formToken = SpringContextHolder.getBean("formTokenManagerImpl", FormTokenManagerImpl.class).newFormToken(request);
			JSONObject json = new JSONObject();
			json.put("token", formToken.getToken());
			ServletUtils.outPrintSuccess(request, response, "流程已启动，流程ID：" + processInstance.getId(), json);
		} catch (Exception e) {
			logger.error("启动请假流程失败：", e);
			ServletUtils.outPrintFail(request, response, "流程启动失败");
		}
	}

	/**
	 * 待我审批的列表
	 * @param request
	 * @param response
	 * @param actTask
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ActTask actTask) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		User user = UserUtil.getUserFromSession(request);
//		actTask.setProcDefKey(procDefKey);
		actTask.setAssignee(user==null?null:user.getId());
		actTaskService.findTodoTasks(page, actTask);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 进入待我审批的
	 * @param leave
	 */
	@RequestMapping("/goTask")
	public ModelAndView taskList(HttpSession session, HttpServletRequest request, ActTask actTask) {
		ModelAndView mav = new ModelAndView("admin/oa/leave/taskList");
		mav.addObject("actTask", actTask);
		mav.addObject("modifyApply", modifyApply);
		mav.addObject("reportBack", reportBack);
		return mav;
	}
	
	/**
	 * 我发起的列表
	 * @param request
	 * @param response
	 * @param actTask
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridMy")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridMy(HttpServletRequest request,
			HttpServletResponse response, ActTask actTask) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		User user = UserUtil.getUserFromSession(request);
		actTask.setUserId(user==null?null:user.getId());
		actTaskService.findMyTasks(page, actTask);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 进入我发起的列表
	 * @param leave
	 */
	@RequestMapping("/goRunning")
	public ModelAndView goRunning(HttpSession session, HttpServletRequest request, ActTask actTask) {
		ModelAndView mav = new ModelAndView("admin/oa/leave/myTaskList");
		mav.addObject("actTask", actTask);
		return mav;
	}

	/**
	 * 读取运行中的流程实例
	 * @return
	 */
	@RequestMapping("/goRunning2")
	public ModelAndView runningList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/oa/leave/running");
		Page<Leave> page = new Page<Leave>();
//		int[] pageParams = PageUtil.init(page, request);
		workflowService.findRunningProcessInstaces(page, null);
		mav.addObject("page", page);
		return mav;
	}
	/**
	 * 我已审批的列表
	 * @param request
	 * @param response
	 * @param actTask
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridFinished")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridFinished(HttpServletRequest request,
			HttpServletResponse response, ActTask actTask) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		User user = UserUtil.getUserFromSession(request);
		actTask.setAssignee(user==null?null:user.getId());
		actTaskService.findFinishedTasks(page, actTask);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 进入我已审批的
	 * @param leave
	 */
	@RequestMapping("/goFinished")
	public ModelAndView goFinished(HttpSession session, HttpServletRequest request, ActTask actTask) {
		ModelAndView mav = new ModelAndView("admin/oa/leave/finished");
		mav.addObject("actTask", actTask);
		return mav;
	}
	/**
	 * 所有的列表
	 * @param request
	 * @param response
	 * @param actTask
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridAll")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridAll(HttpServletRequest request,
			HttpServletResponse response, ActTask actTask) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actTaskService.findAllTasks(page, actTask);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 进入所有的列表
	 * @param leave
	 */
	@RequestMapping("/goTaskAll")
	public ModelAndView goTaskAll(HttpSession session, HttpServletRequest request, ActTask actTask) {
		ModelAndView mav = new ModelAndView("admin/oa/leave/allTaskList");
		mav.addObject("actTask", actTask);
		return mav;
	}
	/**
	 * 读取已结束的流程实例
	 * @return
	 */
	@RequestMapping("/goFinished2")
	public ModelAndView finishedList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/oa/leave/finished");
		Page<Leave> page = new Page<Leave>();
//		int[] pageParams = PageUtil.init(page, request);
		workflowService.findFinishedProcessInstaces(page, null);
		mav.addObject("page", page);
		return mav;
	}

	/**
	 * 签收任务
	 */
	@RequestMapping("/doTask/claim/{id}")
	public void claim(@PathVariable("id") String taskId, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			User user = UserUtil.getUserFromSession(request);
			taskService.claim(taskId, user.getId());
			ServletUtils.outPrintSuccess(request, response, "任务签收成功");
		}catch(Exception e){
			logger.error("任务签收失败：", e);
			ServletUtils.outPrintFail(request, response, "任务签收失败");
		}
	}

	/**
	 * 读取详细数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/goDetail/{id}")
	@ResponseBody
	public Leave getLeave(@PathVariable("id") Long id) {
		Leave leave = leaveService.get(Leave.class,id);
		return leave;
	}

	/**
	 * 读取详细数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/goDetail-with-vars/{id}/{taskId}")
	@ResponseBody
	public Leave getLeaveWithVars(@PathVariable("id") Long id,
			@PathVariable("taskId") String taskId) {
		Leave leave = leaveService.get(Leave.class,id);
		Map<String, Object> variables = taskService.getVariables(taskId);
		leave.setVariables(variables);
		return leave;
	}
	/**
	 * 进入流程处理
	 * @return
	 */
	@RequestMapping("/goDeal")
	public ModelAndView goDeal(HttpServletRequest request, String id) {
		ModelAndView mav = new ModelAndView("admin/oa/leave/deal");
		Leave leave = leaveService.getByProcessInstanceId(id);
		String passKey = reportBack.toString();
		double dayNum = 0;
		if (leave!=null){
			ActTask actTask = actTaskService.getByProcInstId(leave.getProcessInstanceId());
			if (actTask!=null){
				mav.addObject("taskId", actTask.getId());
				if (deptLeaderAudit.equals(actTask.getTaskDefKey())){
					passKey = "deptLeaderPass";
				}else if(deptLeaderTwoAudit.equals(actTask.getTaskDefKey())){
					passKey = "deptLeaderTwoPass";
				}else if(bossAudit.equals(actTask.getTaskDefKey())){
					passKey = "bossPass";
				}else if(modifyApply.equals(actTask.getTaskDefKey())){
					passKey = "reApply";
				}
			}
			Czybm czybm = czybmService.get(Czybm.class, leave.getUserId());
			leave.setUserName(czybm==null?"":czybm.getZwxm());
			dayNum = leave.getDayNum()==null?0L:leave.getDayNum();
		}
		mav.addObject("leave", leave);
		mav.addObject("passKey", passKey);
		mav.addObject("dayNum", dayNum);
		return mav;
	}
	/**
	 * 完成任务
	 * @param id
	 * @return
	 */
	@RequestMapping("/doComplete")
	public void complete(HttpServletRequest request, HttpServletResponse response, 
			String taskId, Variable var) {
		String passKey = "任务完成";
		try {
			ActTask actTask = actTaskService.get(ActTask.class, taskId);
			if (actTask!=null){
				if (deptLeaderAudit.equals(actTask.getTaskDefKey())){
					passKey = "直接主管审批";
				}else if(deptLeaderTwoAudit.equals(actTask.getTaskDefKey())){
					passKey = "二级主管审批";
				}else if(bossAudit.equals(actTask.getTaskDefKey())){
					passKey = "boss审批";
				}else if(modifyApply.equals(actTask.getTaskDefKey())){
					passKey = "调整申请";
				}
			}
			Leave leave = leaveService.getByProcessInstanceId(actTask.getProcInstId());
			OaAll oa = oaAllService.get(OaAll.class, actTask.getProcInstId());
			User user = UserUtil.getUserFromSession(request);
			taskService.claim(taskId, user.getId());
			Map<String, Object> variables = var.getVariableMap();
			variables.put("type", leave.getType());
			taskService.complete(taskId, variables);
			//审批拒绝
			if (variables.get("deptLeaderPass")!=null && !(Boolean) variables.get("deptLeaderPass")){
				oa.setStatus("3");
				oaAllService.update(oa);
				ServletUtils.outPrintSuccess(request, response, passKey+"成功");
				return;
			}
			if (variables.get("deptLeaderTwoPass")!=null && !(Boolean) variables.get("deptLeaderTwoPass")){
				oa.setStatus("3");
				oaAllService.update(oa);
				ServletUtils.outPrintSuccess(request, response, passKey+"成功");
				return;
			}
			if (variables.get("bossPass")!=null && !(Boolean) variables.get("bossPass")){
				oa.setStatus("3");
				oaAllService.update(oa);
				ServletUtils.outPrintSuccess(request, response, passKey+"成功");
				return;
			}
			//取消申请
			if ("false".equals(String.valueOf(variables.get("reApply")))){
				leaveService.delete(leave);
				oaAllService.delete(oa);
				ServletUtils.outPrintSuccess(request, response, passKey+"成功");
				return;
			}
			//调整申请
			if ("true".equals(String.valueOf(variables.get("reApply")))){
				oa.setStatus("1");
				oaAllService.update(oa);
				ServletUtils.outPrintSuccess(request, response, passKey+"成功");
				return;
			}
			double dayNum = 0L;
			try{
				dayNum = Double.valueOf(String.valueOf(variables.get("dayNum")));
			}catch(Exception e){
				dayNum = 0L;
			}
			if (leave!=null){
				if ("3".equals(leave.getType())){//请假单
					if (dayNum<3){
						if (variables.get("deptLeaderPass")!=null && (Boolean) variables.get("deptLeaderPass") 
								&& actTask!=null && StringUtils.isNotBlank(actTask.getProcInstId())){
							oa.setFinishTime(new Date());
							oa.setStatus("2");
							oaAllService.update(oa);
						}
					}else if (dayNum>=3 && dayNum<31){
						if (variables.get("deptLeaderTwoPass")!=null && (Boolean) variables.get("deptLeaderTwoPass") 
								&& actTask!=null && StringUtils.isNotBlank(actTask.getProcInstId())){
							oa.setFinishTime(new Date());
							oa.setStatus("2");
							oaAllService.update(oa);
						}
						//去重复审批或上级领导为空直接结束流程
						if (variables.get("deptLeaderPass")!=null 
								&& (Boolean) variables.get("deptLeaderPass")){
							List<String> list = employeeService.getDeptLeaders(user.getId());
							ActTask actTaskNow = actTaskService.getByProcInstId(actTask.getProcInstId());
							if (list!=null && list.size()>0){
								if (actTaskNow!=null && list.contains(user.getId())){
									taskService.claim(actTaskNow.getId(), user.getId());
									Map<String,Object> va = new HashMap<String, Object>();
									va.put("deptLeaderTwoPass", true);
									taskService.complete(actTaskNow.getId(), va);
									oa.setFinishTime(new Date());
									oa.setStatus("2");
									oaAllService.update(oa);
								}
							}else{
								if (actTaskNow!=null){
									taskService.claim(actTaskNow.getId(), user.getId());
									Map<String,Object> va = new HashMap<String, Object>();
									va.put("deptLeaderTwoPass", true);
									taskService.complete(actTaskNow.getId(), va);
									oa.setFinishTime(new Date());
									oa.setStatus("2");
									oaAllService.update(oa);
								}
							}
						}
					}else{
						if (variables.get("bossPass")!=null && (Boolean) variables.get("bossPass") 
								&& actTask!=null && StringUtils.isNotBlank(actTask.getProcInstId())){
							oa.setFinishTime(new Date());
							oa.setStatus("2");
							oaAllService.update(oa);
						}
						//去重复审批或上级领导为空进入下一个流程，如果上级领导为boss则结束流程
						if (variables.get("deptLeaderPass")!=null 
								&& (Boolean) variables.get("deptLeaderPass")){
							List<String> list = employeeService.getDeptLeaders(user.getId());
							ActTask actTaskNow = actTaskService.getByProcInstId(actTask.getProcInstId());
							String boss = xtcsService.getQzById("leaderBoss");
							if (list!=null && list.size()>0){
								if (actTaskNow!=null){
									if (list.contains(user.getId())){
										taskService.claim(actTaskNow.getId(), user.getId());
										Map<String,Object> va = new HashMap<String, Object>();
										va.put("deptLeaderTwoPass", true);
										taskService.complete(actTaskNow.getId(), va);
										if (boss.equals(user.getId().trim())){
											ActTask actTask3 = actTaskService.getByProcInstId(actTask.getProcInstId());
											taskService.claim(actTask3.getId(), boss);
											Map<String,Object> va3 = new HashMap<String, Object>();
											va3.put("bossPass", true);
											taskService.complete(actTask3.getId(), va3);
											oa.setFinishTime(new Date());
											oa.setStatus("2");
											oaAllService.update(oa);
										}
									}
								}
							}else{
								if (actTaskNow!=null){
									taskService.claim(actTaskNow.getId(), user.getId());
									Map<String,Object> va = new HashMap<String, Object>();
									va.put("deptLeaderTwoPass", true);
									taskService.complete(actTaskNow.getId(), va);
									if (boss.equals(user.getId().trim())){
										ActTask actTask3 = actTaskService.getByProcInstId(actTask.getProcInstId());
										taskService.claim(actTask3.getId(), boss);
										Map<String,Object> va3 = new HashMap<String, Object>();
										va3.put("bossPass", true);
										taskService.complete(actTask3.getId(), va3);
										oa.setFinishTime(new Date());
										oa.setStatus("2");
										oaAllService.update(oa);
									}
								}
							}
						}
						if (variables.get("deptLeaderTwoPass")!=null 
								&& (Boolean) variables.get("deptLeaderTwoPass")){
							String boss = xtcsService.getQzById("leaderBoss");
							if (boss.equals(user.getId().trim())){
								ActTask actTask3 = actTaskService.getByProcInstId(actTask.getProcInstId());
								if (actTask3!=null){
									Map<String,Object> va3 = new HashMap<String, Object>();
									va3.put("bossPass", true);
									taskService.complete(actTask3.getId(), va3);
									oa.setFinishTime(new Date());
									oa.setStatus("2");
									oaAllService.update(oa);
								}
							}
						}
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
	/**
	 * 撤销申请
	 * @param request
	 * @param response
	 * @param deleteIds
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("撤销申请开始");
		String flag = request.getParameter("flag");
		String msg = "all".equals(flag)?"删除":"撤销";
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "编号不能为空,"+msg+"失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				OaAll oa = oaAllService.get(OaAll.class, deleteId);
				if (oa!=null){
					if ("all".equals(flag)){
						oaAllService.delete(oa);
					}else{
						if (oa.getFinishTime()==null){
							oaAllService.delete(oa);
						}else{
							ServletUtils.outPrintFail(request, response,"已审批通过的不能"+msg);
						}
					}
				}
			}
		}
		logger.debug("撤销申请 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,msg+"申请成功");
	}
	public String getTaskIdByLeave(Leave leave){
		String result = null;
		if (leave!=null && StringUtils.isNotBlank(leave.getProcessInstanceId())){
			ActTask actTask = actTaskService.getByProcInstId(leave.getProcessInstanceId());
			if (actTask!=null){
				result = actTask.getId();
			}
		}
		return result;
	}
	
}
