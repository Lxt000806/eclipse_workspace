package com.house.home.web.controller.workflow;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.workflow.ActProcdef;
import com.house.home.service.workflow.WorkflowTraceService;


/**
 * 流程管理控制器
 * 
 * @author HenryYan
 */
@Controller
@RequestMapping(value = "/admin/workflow")
public class ActivitiController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	ProcessEngineFactoryBean processEngine;
		
	/**
	 * 流程定义列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/process/goList")
	public ModelAndView processList(HttpServletRequest request,ActProcdef actProcdef) {
		ModelAndView mav = new ModelAndView("admin/workflow/process-list").addObject("actProcdef", actProcdef);
		return mav;
	}
	
	/**
	 * 流程导入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/process/goImport")
	public ModelAndView modelList(HttpServletRequest request) {          
		ModelAndView mav = new ModelAndView("admin/workflow/process_save");
		return mav;
	}
	
	
	/**
	 * 流程图片上传
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/process/loadDeploy")
	public void loadDeploy(HttpServletRequest request,HttpServletResponse response) {
		try { 
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 设置允许上传的最大文件大小 500k
			/*upload.setSizeMax(500 * 1024);*/
			// 获取多个上传文件
			List fileList = upload.parseRequest(request);	
			// 遍历上传文件写入磁盘
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				// 如果是普通 表单参数
				if (obit.isFormField()) { //普通域,获取页面参数
					String fieldName = obit.getFieldName();
	
				}
				// 如果是多媒体
				if (obit instanceof DiskFileItem) {
					DiskFileItem item = (DiskFileItem) obit;
					// 如果item是文件上传表单域
					// 获得文件名及路径
					String fileName = item.getName();
					if (fileName != null) {
						if(fileName.indexOf("?")!=-1){
							fileName=fileName.substring(0,fileName.indexOf("?"));
						}
						BufferedInputStream in = new BufferedInputStream(
								item.getInputStream());// 获得文件输入流
						Deployment deployment = null;
						String extension = FilenameUtils.getExtension(fileName);
						if (extension.equals("zip") || extension.equals("bar")) {
							ZipInputStream zip = new ZipInputStream(in);
							deployment = repositoryService.createDeployment()
									.addZipInputStream(zip).deploy();
						} else {
							deployment = repositoryService.createDeployment()
									.addInputStream(fileName,in).deploy();
						}

						in.close();
						ServletUtils.outPrintSuccess(request, response, "部署成功，部署ID=" + deployment.getId());
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "部署失败");
		}
	}


	@RequestMapping(value = "/process/doConvertToModel/{processDefinitionId}")
	public void convertToModel(
			@PathVariable("processDefinitionId") String processDefinitionId,
			HttpServletRequest request, HttpServletResponse response){
	 	
     try{
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(),
				processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modelNode = converter.convertToJson(bpmnModel);
		Model modelData = repositoryService.newModel();
		modelData.setKey(processDefinition.getKey());
		modelData.setName(processDefinition.getResourceName());
		modelData.setCategory(processDefinition.getDeploymentId());
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));

		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME,
				processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
				processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());

		repositoryService.saveModel(modelData);

		repositoryService.addModelEditorSource(modelData.getId(), modelNode
				.toString().getBytes("utf-8"));

		ServletUtils.outPrintSuccess(request, response, "转化模型成功");
		} catch (Exception e) {
			logger.error("转化模型失败：modelId={}", processDefinitionId, e);
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "转化模型失败");
		}
	
	}
	
	/**
	 * 删除部署的流程，级联删除流程实例
	 * 
	 * @param deploymentId
	 *            流程部署ID
	 */
	@RequestMapping(value = "/process/doDelete/{deploymentId}")
	public void delete(@PathVariable("deploymentId") String deploymentId,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			repositoryService.deleteDeployment(deploymentId, true);
			ServletUtils.outPrintSuccess(request, response, "删除成功");
		}catch (Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	
	/**
	 * 输出跟踪流程信息
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/process/trace")
	@ResponseBody
	public List<Map<String, Object>> traceProcess(
			@RequestParam("pid") String processInstanceId) throws Exception {
		List<Map<String, Object>> activityInfos = traceService
				.traceProcess(processInstanceId);
		return activityInfos;
	}
	
	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "/process/trace/auto/{executionId}")
	public void readResource(@PathVariable("executionId") String executionId,
			HttpServletResponse response) throws Exception {
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery().processInstanceId(executionId)
				.singleResult();
		if (processInstance==null){
			return;
		}
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance
				.getProcessDefinitionId());
		List<String> activeActivityIds = runtimeService
				.getActiveActivityIds(executionId);
		// 不使用spring请使用下面的两行代码
		// ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl)
		// ProcessEngines.getDefaultProcessEngine();
		// Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngine
				.getProcessEngineConfiguration());

		InputStream imageStream = ProcessDiagramGenerator.generateDiagram(
				bpmnModel, "png", activeActivityIds);

		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	/**
	 * 挂起、激活流程实例
	 */
	@RequestMapping(value = "/process/processdefinition/update")
	public void updateState(
			HttpServletRequest request, HttpServletResponse response) {
		String state=request.getParameter("state");
		String processDefinitionId=request.getParameter("processDefinitionId");
		ServletUtils.outPrintSuccess(request, response, "已挂起ID为["
				+ processDefinitionId + "]的流程定义。");
		if (state.equals("已挂起")) {
			repositoryService.activateProcessDefinitionById(
					processDefinitionId, true, null);
			ServletUtils.outPrintSuccess(request, response, "已激活ID为["
					+ processDefinitionId + "]的流程定义。");
		} else if (state.equals("已激活")) {
			repositoryService.suspendProcessDefinitionById(processDefinitionId,
					true, null);
			ServletUtils.outPrintSuccess(request, response, "已挂起ID为["
					+ processDefinitionId + "]的流程定义。");
		}
	}
	
	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param processDefinitionId
	 *            流程定义
	 * @param resourceType
	 *            资源类型(xml|image)
	 * @throws Exception
	 */
	@RequestMapping(value = "/process/resource/read")
	public void loadByDeployment(
			@RequestParam("processDefinitionId") String processDefinitionId,
			@RequestParam("resourceType") String resourceType,
			HttpServletResponse response) throws Exception {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
		if (resourceType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		InputStream resourceAsStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	
	
}
