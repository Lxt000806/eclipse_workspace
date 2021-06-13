package com.house.home.web.controller.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.house.home.entity.basic.DocFolder;
import com.house.home.entity.basic.DocFolderViewRole;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.DocFolderService;
import com.house.home.service.basic.DocFolderViewRoleService;

/**
 * 文件目录管理Controller
 *
 */
@Controller
@RequestMapping("/admin/docFolder")
public class DocFolderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DocFolderController.class);
	
	@Autowired
	private DocFolderService docFolderService;
	@Autowired
	private DocFolderViewRoleService docFolderViewRoleService;
	@Autowired
	private CzybmService czybmService;
	
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,DocFolder docFolder) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9040", "查看所有目录权限");
		docFolder.setHasAuthByCzybh(hasAuthByCzybh);
		docFolderService.findPageBySql(page, docFolder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getDocFolder")
	@ResponseBody
	public JSONObject getDocFolder(HttpServletRequest request,HttpServletResponse response,Integer id){
		if(id == null){
			return this.out("传入的id为空", false);
		}
		DocFolder docFolder= docFolderService.get(DocFolder.class, id);
		if(docFolder == null){
			return this.out("系统中不存在PK="+id+"目录", false);
		}
		return this.out(docFolder, true);
	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,DocFolder docFolder) throws Exception {
			
		String czybm = this.getUserContext(request).getCzybh();
		return new ModelAndView("admin/basic/docFolder/docFolder_code")
		.addObject("docFolder", docFolder).addObject("czybm", czybm);
	}
	
	private void resetDocFolder(DocFolder docFolder){
		if (docFolder!=null){
			if (StringUtils.isNotBlank(docFolder.getAdminCzy())){
				Czybm adminCzy = docFolderService.get(Czybm.class, docFolder.getAdminCzy());
				if (adminCzy!=null){
					docFolder.setAdminCzyDescr(adminCzy.getZwxm());
				}
			}
			if (StringUtils.isNotBlank(docFolder.getCreateCzy())){
				Czybm createCzy = docFolderService.get(Czybm.class, docFolder.getCreateCzy());
				if (createCzy!=null){
					docFolder.setCreateCzyDescr(createCzy.getZwxm());
				}
			}
			if (docFolder.getPk() != null){
				StringBuffer docFolderViewRole = new StringBuffer();
				StringBuffer docFolderViewRoleDescr = new StringBuffer();
				List<Map<String, Object>> list = this.docFolderViewRoleService.getDocFolderViewRoleByFolderPK(docFolder.getPk());
				if (list != null && list.size() > 0){
					for(Map<String, Object> map : list){
						docFolderViewRole.append(map.get("RolePK").toString()+",");
						docFolderViewRoleDescr.append(map.get("ROLE_NAME").toString()+",");	
					}	
				}
				if (docFolderViewRole.length() > 0){
					docFolder.setDocFolderViewRole(docFolderViewRole.toString().substring(0,
							docFolderViewRole.length()-1));
				}
				if (docFolderViewRoleDescr.length() > 0){
					docFolder.setDocFolderViewRoleDescr(docFolderViewRoleDescr.toString().substring(0,
							docFolderViewRoleDescr.length()-1));
				}
			}	
		}
	}

	/**
	 * 跳转到文件目录框架页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goMain(HttpServletRequest request, HttpServletResponse response, Integer initId){
		logger.debug("跳转到文件目录页面");
		if(initId == null || initId.longValue() == 0){
			List<DocFolder> rootDocFolders = this.docFolderService.getSubDocFolder(0);
			if(rootDocFolders != null && rootDocFolders.size() > 0){
				initId = rootDocFolders.get(0).getPk();
			}
		}
		return new ModelAndView("admin/basic/docFolder/docFolder_list").addObject("initId", initId);
	}
	
	/**
	 * 跳转到文件目录树页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goTree")
	public ModelAndView goTree(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到文件目录树页面");
		List<DocFolder> list = this.docFolderService.getAll();
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);
		return new ModelAndView("admin/basic/docFolder/docFolder_tree")
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
		DocFolder docFolder = new DocFolder();
		if(parentId != null){
			DocFolder parentDocFolder = docFolderService.get(DocFolder.class, parentId); 
			if(parentDocFolder != null){
				docFolder.setParentPk(parentId);
			}	
		}
		docFolder.setCreateDate(new Date());
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		docFolder.setCreateCzy(uc.getCzybh());
		docFolder.setCreateCzyDescr(uc.getZwxm());
		List<DocFolder> list = this.docFolderService.getAll();
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);
		docFolder.setM_umState("A");
		return new ModelAndView("admin/basic/docFolder/docFolder_save")
				.addObject("nodes", json)
				.addObject("docFolder", docFolder);
	}
	
	/**
	 * 跳转到修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goUpdate")
	@ResponseBody
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Integer pk) {
		logger.debug("跳转到文件目录修改页面");
		DocFolder docFolder = null;
		if(pk != null){
			docFolder = this.docFolderService.get(DocFolder.class, pk);	
		}
		docFolder.setM_umState("M");
		resetDocFolder(docFolder);
		//List<Map<String, Object>> adminCzyList=czybmService.getCzyByIds(docFolder.getAdminCzy());
		
		List<Map<String,Object>>  adminCzyList=czybmService.getCzyByIds(docFolder.getAdminCzy());
		String json = "[]";
		if(null != adminCzyList && adminCzyList.size()>0)
			json = JSON.toJSONString(adminCzyList);
		
	
		
		JSONArray arrAdminCzy = JSONArray.fromObject(adminCzyList);
		System.out.println(arrAdminCzy);
		
		String sAdminCzy = JSON.toJSONString(adminCzyList);
		System.out.println(sAdminCzy);
		
		
		 

		return new ModelAndView("admin/basic/docFolder/docFolder_save")
			.addObject("docFolder", docFolder)
		    .addObject("adminCzyList", adminCzyList);	
	}	

	/**
	 * 跳转到文件目录查看页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("跳转到文件目录维护页面");
		DocFolder docFolder = null;
		if(pk != null){
			docFolder = this.docFolderService.get(DocFolder.class, pk);	
		}
		resetDocFolder(docFolder);
		return new ModelAndView("admin/basic/docFolder/docFolder_detail")
			.addObject("docFolder", docFolder);	
	}

	/**
	 * 新增目录
	 * @param request
	 * @param response
	 * @param menu
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, DocFolder docFolder){
		logger.debug("新增目录开始");
		try {	
			docFolder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = docFolderService.doSaveProc(docFolder);
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
	 * @param docFolderId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, Integer docFolderId){
		logger.debug("删除目录开始");
		if(null == docFolderId){
			ServletUtils.outPrintFail(request, response, "删除目录失败,目录ID不能为空");
			return ;
		}
		List<DocFolder> subList = this.docFolderService.getSubDocFolder(docFolderId);
		if(null != subList && subList.size() > 0){
			ServletUtils.outPrintFail(request, response, "删除目录失败,存在 ["+subList.size()+"] 个子目录，请先删除子目录");
			return ;
		}
		if(docFolderService.hasDocByFolderPK(docFolderId)){
			ServletUtils.outPrintFail(request, response, "删除目录失败,该目录下存在文档");
			return ;
		}
		
		DocFolder docFolder = docFolderService.get(DocFolder.class, docFolderId);
		docFolderService.delete(docFolder);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
     * 跳转到分配角色权限
     */
    @RequestMapping("/goDocFolderViewRole")
    public ModelAndView goDocFolderViewRole(HttpServletRequest request, HttpServletResponse response, DocFolderViewRole docFolderViewRole){
    	String selectedJson = "[]";
    	String unSelectedJson = "[]";
    	if (StringUtils.isNotBlank(docFolderViewRole.getSelected())) {
    		List<Map<String, Object>> selectedList = this.docFolderViewRoleService.getDocFolderViewRole(docFolderViewRole);
    		
    		if(null != selectedList && selectedList.size() > 0)
    			selectedJson = JSON.toJSONString(selectedList);
    		docFolderViewRole.setUnSelected(docFolderViewRole.getSelected());
    		docFolderViewRole.setSelected("");
        	
		}
    	List<Map<String, Object>> unSelectedList = this.docFolderViewRoleService.getDocFolderViewRole(docFolderViewRole);
		if(null != unSelectedList && unSelectedList.size()>0)
			unSelectedJson = JSON.toJSONString(unSelectedList);
		
        return new ModelAndView("admin/basic/docFolder/docFolder_docFolderViewRole")
    			.addObject("selectedJson", selectedJson)
    	        .addObject("unSelectedJson", unSelectedJson)
        		.addObject("docFolderViewRole", docFolderViewRole);  
                   
    }
	
}
