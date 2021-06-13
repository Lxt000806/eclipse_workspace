package com.house.home.web.controller.workflow;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.house.home.entity.workflow.ActProcdef;
import com.house.home.service.workflow.ActProcdefService;

@Controller
@RequestMapping("/admin/actProcdef")
public class ActProcdefController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActProcdefController.class);

	@Autowired
	private ActProcdefService actProcdefService;
	@Autowired
	private RepositoryService repositoryService;

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
			HttpServletResponse response, ActProcdef actProcdef) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actProcdefService.findPageBySql(page, actProcdef);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActProcdef列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,ActProcdef actProcdef) throws Exception {

		return new ModelAndView("admin/workflow/actProcdef/actProcdef_list").addObject("actProcdef",actProcdef);
	}
	/**
	 * ActProcdef查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actProcdef/actProcdef_code");
	}
	/**
	 * 跳转到新增ActProcdef页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActProcdef页面");
		ActProcdef actProcdef = null;
		if (StringUtils.isNotBlank(id)){
			actProcdef = actProcdefService.get(ActProcdef.class, id);
			actProcdef.setId(null);
		}else{
			actProcdef = new ActProcdef();
		}
		
		return new ModelAndView("admin/workflow/actProcdef/actProcdef_save")
			.addObject("actProcdef", actProcdef);
	}
	/**
	 * 跳转到修改ActProcdef页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActProcdef页面");
		ActProcdef actProcdef = null;
		if (StringUtils.isNotBlank(id)){
			actProcdef = actProcdefService.get(ActProcdef.class, id);
		}else{
			actProcdef = new ActProcdef();
		}
		
		return new ModelAndView("admin/workflow/actProcdef/actProcdef_update")
			.addObject("actProcdef", actProcdef);
	}
	
	/**
	 * 跳转到查看ActProcdef页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActProcdef页面");
		ActProcdef actProcdef = actProcdefService.get(ActProcdef.class, id);
		
		return new ModelAndView("admin/workflow/actProcdef/actProcdef_detail")
				.addObject("actProcdef", actProcdef);
	}
	/**
	 * 添加ActProcdef
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActProcdef actProcdef){
		logger.debug("添加ActProcdef开始");
		try{
			String str = actProcdefService.getSeqNo("ACT_RE_PROCDEF");
			actProcdef.setId(str);
			this.actProcdefService.save(actProcdef);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActProcdef失败");
		}
	}
	
	/**
	 * 修改ActProcdef
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActProcdef actProcdef){
		logger.debug("修改ActProcdef开始");
		try{
			this.actProcdefService.update(actProcdef);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActProcdef失败");
		}
	}
	
	/**
	 * 删除ActProcdef
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActProcdef开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActProcdef编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActProcdef actProcdef = actProcdefService.get(ActProcdef.class, deleteId);
				if(actProcdef == null)
					continue;
				actProcdefService.update(actProcdef);
			}
		}
		logger.debug("删除ActProcdef IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActProcdef导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActProcdef actProcdef){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actProcdefService.findPageBySql(page, actProcdef);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActProcdef_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 * 跳转到流程导入界面
	 * @return
	 */
	@RequestMapping("/goImport")
	public ModelAndView goImportProcessDefinition(HttpServletRequest request) {          
		return new ModelAndView("admin/workflow/actProcdef/actProcdef_import");
	}
	
	/**
	 * 导入流程
	 * @param request
	 * @param response
	 * @param deploymentName
	 */
	@RequestMapping("/importProcdef")
	public void importProcDef(HttpServletRequest request, HttpServletResponse response) {          
		try {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 设置允许上传的最大文件大小 20M
			upload.setSizeMax(20 * 1024 * 1024);
			// 获取多个上传文件
			List fileList = upload.parseRequest(request);	
			// 遍历上传文件
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (!(fileItem instanceof DiskFileItem)) continue; // fileItem有可能是表单字段，我们只要上传的文件
				
				DiskFileItem diskFileItem = (DiskFileItem) fileItem;
				String fileName = diskFileItem.getName(); // 获得文件名及路径
				if (StringUtils.isBlank(fileName)) continue;
				
				String deploymentName = request.getParameter("deploymentName"); // 部署说明
				
				Deployment deployment = null;
				InputStream in = diskFileItem.getInputStream();
				String extension = FilenameUtils.getExtension(fileName);
				if (extension.equals("zip") || extension.equals("bar")) {
					deployment = repositoryService.createDeployment().name(deploymentName)
							.addZipInputStream(new ZipInputStream(in)).deploy();
				} else {
					deployment = repositoryService.createDeployment().name(deploymentName)
							.addInputStream(fileName, in).deploy();
				}
				
				in.close();
				actProcdefService.updateProcVersion(fileName);
				ServletUtils.outPrintSuccess(request, response, "部署成功，部署ID=" + deployment.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "部署失败");
		}
	}
	
	/**
	 * 获取流程资源，只支持获取图片和xml资源
	 * @param request
	 * @param response
	 * @param processDefinitionId
	 * @param resourceType
	 * @throws Exception
	 */
	@RequestMapping("/getResource")
	public void getProcDefResource(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("processDefinitionId") String processDefinitionId,
			@RequestParam("resourceType") String resourceType) throws Exception {
		
		if (!resourceType.equals("image") && !resourceType.equals("xml")) {
			ServletUtils.outPrintFail(request, response, "资源类型错误！");
			return;
		}
		
		InputStream inputStream = null;
		if (resourceType.equals("image")) {
			inputStream = repositoryService.getProcessDiagram(processDefinitionId);
		} else if (resourceType.equals("xml")) {
			inputStream = repositoryService.getProcessModel(processDefinitionId);
		}
		IOUtils.copy(inputStream, response.getOutputStream());
	}
}
