package com.house.home.web.controller.basic;

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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.GuideTopicFolder;
import com.house.home.entity.basic.TopicFolderViewRole;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.GuideTopicFolderService;
import com.house.home.service.basic.TopicFolderViewRoleService;

@Controller
@RequestMapping("/admin/guideTopicFolder")
public class GuideTopicFolderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GuideTopicFolderController.class);

	@Autowired
	private GuideTopicFolderService guideTopicFolderService;
	
	@Autowired
	private TopicFolderViewRoleService topicFolderViewRoleService;
	
	@Autowired
	private CzybmService czybmService;
	
	private void resetGuideTopicFolder(GuideTopicFolder guideTopicFolder){
		if (guideTopicFolder!=null){
			if (StringUtils.isNotBlank(guideTopicFolder.getAdminCzy())){
				Czybm adminCzy = guideTopicFolderService.get(Czybm.class, guideTopicFolder.getAdminCzy());
				if (adminCzy!=null){
					guideTopicFolder.setAdminCzyDescr(adminCzy.getZwxm());
				}
			}
			if (StringUtils.isNotBlank(guideTopicFolder.getCreateCzy())){
				Czybm createCzy = guideTopicFolderService.get(Czybm.class, guideTopicFolder.getCreateCzy());
				if (createCzy!=null){
					guideTopicFolder.setCreateCzyDescr(createCzy.getZwxm());
				}
			}
			if (guideTopicFolder.getPk() != null){
				StringBuffer topicFolderViewRole = new StringBuffer();
				StringBuffer folderViewRoleDescr = new StringBuffer();
				List<Map<String, Object>> list = this.topicFolderViewRoleService.getFolderViewRoleByFolderPK(guideTopicFolder.getPk());
				if (list != null && list.size() > 0){
					for(Map<String, Object> map : list){
						topicFolderViewRole.append(map.get("RolePK").toString() + ",");
						folderViewRoleDescr.append(map.get("ROLE_NAME").toString() + ",");	
					}	
				}
				if (topicFolderViewRole.length() > 0){
					guideTopicFolder.setTopicFolderViewRole(topicFolderViewRole.toString().substring(0,
							topicFolderViewRole.length()-1));
				}
				if (folderViewRoleDescr.length() > 0){
					guideTopicFolder.setTopicFolderViewRoleDescr(folderViewRoleDescr.toString().substring(0,
							folderViewRoleDescr.length()-1));
				}
			}	
		}
	}
	
	/**
	 * 跳转到问题类目框架页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goMain(HttpServletRequest request, HttpServletResponse response, Integer initId){
		logger.debug("跳转到问题类目页面");
		if(initId == null || initId.longValue() == 0){
			List<GuideTopicFolder> rootGuideTopicFolder = this.guideTopicFolderService.getSubGuideTopicFolder(0);
			if(rootGuideTopicFolder != null && rootGuideTopicFolder.size() > 0){
				initId = rootGuideTopicFolder.get(0).getPk();
			}
		}
		return new ModelAndView("admin/basic/guideTopicFolder/guideTopicFolder_list").addObject("initId", initId);
	}
	
	/**
	 * 跳转到问题类目树页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goTree")
	public ModelAndView goTree(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到问题类目树页面");
		List<GuideTopicFolder> list = this.guideTopicFolderService.getAll();
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);
		return new ModelAndView("admin/basic/guideTopicFolder/guideTopicFolder_tree")
					.addObject("nodes", json)
					.addObject("initId", request.getParameter("initId"));
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, Integer parentId) {
		logger.debug("跳转到添加页面");
		GuideTopicFolder guideTopicFolder = new GuideTopicFolder();
		if(parentId != null){
			GuideTopicFolder parentGuideTopicFolder = guideTopicFolderService.get(GuideTopicFolder.class, parentId); 
			if(parentGuideTopicFolder != null){
				guideTopicFolder.setParentPk(parentId);
			}	
		}
		guideTopicFolder.setCreateDate(new Date());
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		guideTopicFolder.setCreateCzy(uc.getCzybh());
		guideTopicFolder.setCreateCzyDescr(uc.getZwxm());
		List<GuideTopicFolder> list = this.guideTopicFolderService.getAll();
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);
		guideTopicFolder.setM_umState("A");
		return new ModelAndView("admin/basic/guideTopicFolder/guideTopicFolder_save")
				.addObject("nodes", json)
				.addObject("guideTopicFolder", guideTopicFolder);
	}
	
	/**
	 * 跳转到修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Integer pk) {
		logger.debug("跳转到问题类目修改页面");
		GuideTopicFolder guideTopicFolder = null;
		if(pk != null){
			guideTopicFolder = this.guideTopicFolderService.get(GuideTopicFolder.class, pk);	
		}
		guideTopicFolder.setM_umState("M");
		resetGuideTopicFolder(guideTopicFolder);
		return new ModelAndView("admin/basic/guideTopicFolder/guideTopicFolder_save")
			.addObject("guideTopicFolder", guideTopicFolder);			
	}	

	/**
	 * 跳转到问题类目查看页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("跳转到问题类目维护页面");
		GuideTopicFolder guideTopicFolder = null;
		if(pk != null){
			guideTopicFolder = this.guideTopicFolderService.get(GuideTopicFolder.class, pk);	
		}
		resetGuideTopicFolder(guideTopicFolder);
		return new ModelAndView("admin/basic/guideTopicFolder/guideTopicFolder_detail")
			.addObject("guideTopicFolder", guideTopicFolder);	
	}
	
	/**
	 * 保存目录
	 * @param request
	 * @param response
	 * @param menu
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, GuideTopicFolder guideTopicFolder){
		logger.debug("新增目录开始");
		try {	
			guideTopicFolder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = guideTopicFolderService.doSaveProc(guideTopicFolder);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增目录失败");
		}	
	}
	
	/**
	 * 删除文件目录
	 * @param request
	 * @param response
	 * @param menuId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, Integer docFolderId){
		logger.debug("删除目录开始");
		if(null == docFolderId){
			ServletUtils.outPrintFail(request, response, "删除目录失败,目录ID不能为空");
			return ;
		}
		List<GuideTopicFolder> subList = guideTopicFolderService.getSubGuideTopicFolder(docFolderId);
		if(null != subList && subList.size() > 0){
			ServletUtils.outPrintFail(request, response, "删除目录失败,存在 ["+subList.size()+"] 个子目录，请先删除子目录");
			return ;
		}
		if(guideTopicFolderService.hasGuideTopicByFolderPK(docFolderId)){
			ServletUtils.outPrintFail(request, response, "删除目录失败,该目录下存在问答记录");
			return ;
		}
		
		GuideTopicFolder guideTopicFolder = guideTopicFolderService.get(GuideTopicFolder.class, docFolderId);
		guideTopicFolderService.delete(guideTopicFolder);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
     * 跳转到分配角色
     * @param topicFolderViewRole
     */
    @RequestMapping("/goTopicFolderViewRole")
    public ModelAndView goDocFolderViewRole(HttpServletRequest request, HttpServletResponse response, TopicFolderViewRole topicFolderViewRole){
    	String selectedJson = "[]";
    	String unSelectedJson = "[]";
    	if (StringUtils.isNotBlank(topicFolderViewRole.getSelected())) {
    		List<Map<String, Object>> selectedList = topicFolderViewRoleService.getFolderViewRole(topicFolderViewRole);
    		
    		if(null != selectedList && selectedList.size() > 0)
    			selectedJson = JSON.toJSONString(selectedList);
    		topicFolderViewRole.setUnSelected(topicFolderViewRole.getSelected());
    		topicFolderViewRole.setSelected("");
        	
		}
    	List<Map<String, Object>> unSelectedList = topicFolderViewRoleService.getFolderViewRole(topicFolderViewRole);
		if(null != unSelectedList && unSelectedList.size()>0)
			unSelectedJson = JSON.toJSONString(unSelectedList);
		
        return new ModelAndView("admin/basic/guideTopicFolder/guideTopicFolder_topicFolderViewRole")
    			.addObject("selectedJson", selectedJson)
    	        .addObject("unSelectedJson", unSelectedJson)
        		.addObject("docFolderViewRole", topicFolderViewRole);  
                   
    }
    
    @RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,GuideTopicFolder guideTopicFolder) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9043", "查看所有类目权限");
		guideTopicFolder.setHasAuthByCzybh(hasAuthByCzybh);
		guideTopicFolderService.findPageBySql(page, guideTopicFolder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getGuideTopicFolder")
	@ResponseBody
	public JSONObject getGuideTopicFolder(HttpServletRequest request,HttpServletResponse response,Integer id){
		if(id == null){
			return this.out("传入的id为空", false);
		}
		GuideTopicFolder guideTopicFolder= guideTopicFolderService.get(GuideTopicFolder.class, id);
		if(guideTopicFolder == null){
			return this.out("系统中不存在PK="+id+"类目", false);
		}
		return this.out(guideTopicFolder, true);
	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,GuideTopicFolder guideTopicFolder) throws Exception {
			
		String czybm = this.getUserContext(request).getCzybh();
		return new ModelAndView("admin/basic/guideTopicFolder/guideTopicFolder_code")
		.addObject("guideTopicFolder", guideTopicFolder).addObject("czybm", czybm);
	}
	
}
