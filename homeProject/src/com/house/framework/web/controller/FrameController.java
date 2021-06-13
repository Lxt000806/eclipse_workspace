package com.house.framework.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.UserUtil;
import com.house.framework.service.MenuService;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.entity.basic.Information;
import com.house.home.entity.workflow.ActTask;
import com.house.home.service.basic.InformationService;
import com.house.home.service.workflow.ActTaskService;

/**
 * 总框架页面
 *
 */
@Controller
public class FrameController extends BaseController{
	@Autowired
	private MenuService menuService;
	@Autowired
	private InformationService informationService;
	@Autowired
	private ActTaskService actTaskService;
	
	private String procDefKey = "leave";
	
//	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("frame/main");
	}
	
	@RequestMapping("/frame/goTop")
	public ModelAndView goTop(HttpServletRequest request,HttpServletResponse response){
		UserContext uc = this.getUserContext(request);
		request.setAttribute("userName", uc.getZwxm());
		
		return new ModelAndView("frame/frame_top");
	}
	
	@RequestMapping("/frame/goBottom")
	public ModelAndView goBottom(HttpServletRequest request,HttpServletResponse response){
		
		return new ModelAndView("frame/frame_bottom");
	}
	
	@RequestMapping("/frame/goFlag")
	public ModelAndView goFlag(HttpServletRequest request,HttpServletResponse response){
		
		return new ModelAndView("frame/frame_flag");
	}
	
	@RequestMapping("/frame/goQuery")
	public ModelAndView goQuery(HttpServletRequest request,HttpServletResponse response){
		
		return new ModelAndView("frame/frame_query");
	}
	@RequestMapping("/frame/goRight")
	public ModelAndView goRight(HttpServletRequest request,HttpServletResponse response,
			String title, Information information){
		boolean showMsg = false;
		String needSendInfoNum="";
		Long unReadNum = 0l;
		Long unReadNumDb = 0l;
		UserContext uc = this.getUserContext(request);
		if (uc==null){
			return new ModelAndView("login");
		}
		if ("1".equals(uc.getCzylb())){//erp平台
			Information info = new Information();
			info.setInfoType("2");
			info.setStatus("3");
			info.setReadStatus("0");
			info.setRcvCzy(uc.getEmnum());
			unReadNum = informationService.getCount(info);
			showMsg = true;
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			User user = UserUtil.getUserFromSession(request);
			System.out.println(this.getUserContext(request).getEmnum());
			needSendInfoNum = informationService.getNeedSendInfomation(this.getUserContext(request).getEmnum());
			ActTask actTask = new ActTask();
			actTask.setProcDefKey(procDefKey);
			actTask.setAssignee(user==null?null:user.getId());
			actTaskService.findTodoTasks(page, actTask);
			unReadNumDb = page.getTotalCount();
		}
		
		// 预留自定义板块部分
		List<String> plateList = new ArrayList<String>();
		
		return new ModelAndView("frame/frame_right")
			.addObject("title", title)
			.addObject("showMsg", showMsg)
			.addObject("information", information)
			.addObject("unReadNum", String.valueOf(unReadNum))
			.addObject("unReadNumDb", String.valueOf(unReadNumDb))
			.addObject("needSendInfoNum",needSendInfoNum)
			.addObject("plateList", plateList);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frame/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Information information) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		information.setInfoType("2");
		information.setRcvCzy(this.getUserContext(request).getEmnum());
		informationService.findPageBySql(page, information);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 
	 *功能说明:获取自定义列表
	 *@param request
	 *@param response
	 *@return ModelAndView
	 *
	 */
	@RequestMapping("/goSelfDefinition")
	public ModelAndView goSelfDefinition(HttpServletRequest request,HttpServletResponse response){
		
		return new ModelAndView("frame/frame_menu_add");
	}
	
	@RequestMapping("/frame/goLeft")
	public ModelAndView goLeft(){
		List<Map<String, Object>> tabList = menuService.getTabMenus();
		List<Map<String, Object>> tabMenus = new ArrayList<Map<String,Object>>();
		UserContext userContext = UserContextHolder.getUserContext();
		if(!userContext.isOneAdmin() && !userContext.isSuperAdmin()){
			//获取所有菜单
			List<Map<String, Object>> menuList = menuService.getMenusByUser(userContext.getCzybh());
			//判断菜单的个数 
			for(Map<String, Object> tab: tabList){
				String tabId =tab.get("MENU_ID").toString();
				boolean flag = false;
				for(Map<String, Object> menu: menuList){
					String menuParentId = menu.get("PARENT_ID").toString();
					if(menuParentId.equals(tabId)){
						flag = true;
						break;
					}
				}
				if(flag){
					tabMenus.add(tab);
				}
			}
			
		}else{
			tabMenus = tabList;
		}

		return new ModelAndView("frame/frame_left")
			.addObject("tabMenus", tabMenus)
			.addObject("MENU_OPEN_TYPE_FULL", DictConstant.DICT_MENU_OPEN_FULL)
			.addObject("MENU_OPEN_TYPE_INNER", DictConstant.DICT_MENU_OPEN_INNER)
			.addObject("MENU_TYPE_URL", DictConstant.DICT_MENU_TYPE_URL)
			.addObject("MENU_TYPE_FOLDER", DictConstant.DICT_MENU_TYPE_FOLDER);
		
	}
	
}
