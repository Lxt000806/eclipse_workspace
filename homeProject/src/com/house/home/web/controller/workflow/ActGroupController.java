package com.house.home.web.controller.workflow;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.workflow.ActGroup;
import com.house.home.entity.workflow.ActUser;
import com.house.home.entity.workflow.WfGroupAuthority;
import com.house.home.service.workflow.ActGroupService;

@Controller
@RequestMapping("/admin/actGroup")
public class ActGroupController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActGroupController.class);

	@Autowired
	private ActGroupService actGroupService;

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
			HttpServletResponse response, ActGroup actGroup) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actGroupService.findPageBySql(page, actGroup);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goUserJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goUserJqGrid(HttpServletRequest request,
			HttpServletResponse response, ActGroup actGroup) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actGroupService.findUserPageBySql(page, actGroup);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goRoleAuthorityJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goRoleAuthorityJqGrid(HttpServletRequest request,
			HttpServletResponse response, ActGroup actGroup) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actGroupService.getRoleAuthorityJqGrid(page, actGroup);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActGroup列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actGroup/actGroup_list");
	}
	/**
	 * ActGroup查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actGroup/actGroup_code");
	}
	/**
	 * 跳转到新增ActGroup页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增ActGroup页面");
		return new ModelAndView("admin/workflow/actGroup/actGroup_save");
	}
	/**
	 * 跳转到修改ActGroup页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id, String m_umState){
		logger.debug("跳转到修改ActGroup页面");
		ActGroup actGroup = null;
		if (StringUtils.isNotBlank(id)){
			actGroup = actGroupService.get(ActGroup.class, id);
		}else{
			actGroup = new ActGroup();
		}
		actGroup.setM_umState(m_umState);
		return new ModelAndView("admin/workflow/actGroup/actGroup_save")
			.addObject("actGroup", actGroup);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id, String m_umState){
		logger.debug("跳转到修改ActGroup页面");
		ActGroup actGroup = null;
		if (StringUtils.isNotBlank(id)){
			actGroup = actGroupService.get(ActGroup.class, id);
		}else{
			actGroup = new ActGroup();
		}
		actGroup.setM_umState(m_umState);
		return new ModelAndView("admin/workflow/actGroup/actGroup_save")
			.addObject("actGroup", actGroup);
	}
	
	@RequestMapping("/goAuthority")
	public ModelAndView goAuthority(HttpServletRequest request, HttpServletResponse response, 
			String id, String m_umState){
		logger.debug("跳转到修改ActGroup页面");
		ActGroup actGroup = null;
		if (StringUtils.isNotBlank(id)){
			actGroup = actGroupService.get(ActGroup.class, id);
		}else{
			actGroup = new ActGroup();
		}
		actGroup.setM_umState(m_umState);
		return new ModelAndView("admin/workflow/actGroup/actGroup_authority")
			.addObject("actGroup", actGroup);
	}
	
	/**
	 * 跳转到查看角色管理页面
	 * @author	created by zb
	 * @date	2019-3-30--下午2:21:57
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/goRoleMember")
	public ModelAndView goRoleMember(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看角色管理页面");
		ActGroup actGroup = actGroupService.get(ActGroup.class, id);
		return new ModelAndView("admin/workflow/actGroup/actGroup_roleMember")
				.addObject("actGroup", actGroup);
	}
	
	/**
	 * 跳转到角色新增页面、编辑
	 * @author	created by zb
	 * @date	2019-3-30--上午10:36:12
	 */
	@RequestMapping("/goWin")
	public ModelAndView goWin(HttpServletRequest request, HttpServletResponse response, 
			String m_umState, String keys, ActUser actUser){
		logger.debug("跳转到角色新增、编辑页面");
		actUser.setM_umState(m_umState);
		return new ModelAndView("admin/workflow/actGroup/actGroup_win")
				.addObject("actGroup", actUser)
				.addObject("ids", keys);
	}
	
	@RequestMapping("/goWfProcessAdd")
	public ModelAndView goWfProcessAdd(HttpServletRequest request, HttpServletResponse response, 
			String m_umState, ActUser actUser){
		logger.debug("跳转到角色新增、编辑页面");
		actUser.setM_umState(m_umState);
		return new ModelAndView("admin/workflow/actGroup/actGroup_wfProcessAdd")
				.addObject("actGroup", actUser);
	}
	
	/**
	 * 添加ActGroup
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActGroup actGroup){
		logger.debug("添加ActGroup开始");
		try{
			ActGroup aGroup = this.actGroupService.get(ActGroup.class, actGroup.getId());
			if (null != aGroup) {
				ServletUtils.outPrintFail(request, response, "角色编号重复");
				return;
			}
			this.actGroupService.save(actGroup);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActGroup失败");
		}
	}
	
	@RequestMapping("/doSaveRoleAuth")
	public void doSaveRoleAuth(HttpServletRequest request, HttpServletResponse response, ActGroup actGroup){
		logger.debug("添加ActGroup开始");
		try{
			boolean result = actGroupService.existsAuth(actGroup);
			if (!result) {
				WfGroupAuthority wfGroupAuthority = new WfGroupAuthority();
				wfGroupAuthority.setGroupId(actGroup.getId());
				wfGroupAuthority.setWfProcNo(actGroup.getWfProcNo());
				wfGroupAuthority.setActionLog("ADD");
				wfGroupAuthority.setExpired("F");
				wfGroupAuthority.setLastUpdate(new Date());
				wfGroupAuthority.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				this.actGroupService.save(wfGroupAuthority);
				ServletUtils.outPrintSuccess(request, response);
			} else{
				ServletUtils.outPrintFail(request, response, "角色权限已经存在");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加角色权限失败");
		}
	}
	
	@RequestMapping("/doDelAuth")
	public void doDelAuth(HttpServletRequest request, HttpServletResponse response, WfGroupAuthority wfGroupAuthority){
		logger.debug("添加ActGroup开始");
		try{
			if (wfGroupAuthority.getPk()!= null) {
				wfGroupAuthority = this.actGroupService.get(WfGroupAuthority.class, wfGroupAuthority.getPk());
				if(wfGroupAuthority != null ){
					this.actGroupService.delete(wfGroupAuthority);
				}		
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除角色权限失败");
		}
	}
	
	/**
	 * 修改ActGroup
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActGroup actGroup){
		logger.debug("修改ActGroup开始");
		try{
			this.actGroupService.update(actGroup);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActGroup失败");
		}
	}
	
	/**
	 * 删除ActGroup
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActGroup开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActGroup编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActGroup actGroup = actGroupService.get(ActGroup.class, deleteId);
				if(actGroup == null)
					continue;
				actGroupService.update(actGroup);
			}
		}
		logger.debug("删除ActGroup IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	/**
	 * 操作员信息保存
	 * @author	created by zb
	 * @date	2019-3-30--下午4:02:55
	 * @param request
	 * @param response
	 * @param actGroup
	 */
	@RequestMapping("/doUserSave")
	public void doUserSave(HttpServletRequest request,HttpServletResponse response,ActGroup actGroup){
		logger.debug("操作员信息保存");
		try {
			/*客户表是否有加入信息*/
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "无操作员信息");
				return;
			}
			/*执行存储过程*/
			Result result = this.actGroupService.doSave(actGroup);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 *ActGroup导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActGroup actGroup){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actGroupService.findPageBySql(page, actGroup);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActGroup_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
