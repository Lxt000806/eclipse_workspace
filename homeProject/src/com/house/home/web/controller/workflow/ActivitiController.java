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
 * ?????????????????????
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
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/process/goList")
	public ModelAndView processList(HttpServletRequest request,ActProcdef actProcdef) {
		ModelAndView mav = new ModelAndView("admin/workflow/process-list").addObject("actProcdef", actProcdef);
		return mav;
	}
	
	/**
	 * ????????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/process/goImport")
	public ModelAndView modelList(HttpServletRequest request) {          
		ModelAndView mav = new ModelAndView("admin/workflow/process_save");
		return mav;
	}
	
	
	/**
	 * ??????????????????
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
			// ??????????????????????????????????????? 500k
			/*upload.setSizeMax(500 * 1024);*/
			// ????????????????????????
			List fileList = upload.parseRequest(request);	
			// ??????????????????????????????
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				// ??????????????? ????????????
				if (obit.isFormField()) { //?????????,??????????????????
					String fieldName = obit.getFieldName();
	
				}
				// ??????????????????
				if (obit instanceof DiskFileItem) {
					DiskFileItem item = (DiskFileItem) obit;
					// ??????item????????????????????????
					// ????????????????????????
					String fileName = item.getName();
					if (fileName != null) {
						if(fileName.indexOf("?")!=-1){
							fileName=fileName.substring(0,fileName.indexOf("?"));
						}
						BufferedInputStream in = new BufferedInputStream(
								item.getInputStream());// ?????????????????????
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
						ServletUtils.outPrintSuccess(request, response, "?????????????????????ID=" + deployment.getId());
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
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

		ServletUtils.outPrintSuccess(request, response, "??????????????????");
		} catch (Exception e) {
			logger.error("?????????????????????modelId={}", processDefinitionId, e);
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	
	}
	
	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @param deploymentId
	 *            ????????????ID
	 */
	@RequestMapping(value = "/process/doDelete/{deploymentId}")
	public void delete(@PathVariable("deploymentId") String deploymentId,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			repositoryService.deleteDeployment(deploymentId, true);
			ServletUtils.outPrintSuccess(request, response, "????????????");
		}catch (Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	
	/**
	 * ????????????????????????
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
	 * ????????????????????????
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
		// ?????????spring??????????????????????????????
		// ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl)
		// ProcessEngines.getDefaultProcessEngine();
		// Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// ??????spring??????????????????????????????????????????
		Context.setProcessEngineConfiguration(processEngine
				.getProcessEngineConfiguration());

		InputStream imageStream = ProcessDiagramGenerator.generateDiagram(
				bpmnModel, "png", activeActivityIds);

		// ?????????????????????????????????
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	/**
	 * ???????????????????????????
	 */
	@RequestMapping(value = "/process/processdefinition/update")
	public void updateState(
			HttpServletRequest request, HttpServletResponse response) {
		String state=request.getParameter("state");
		String processDefinitionId=request.getParameter("processDefinitionId");
		ServletUtils.outPrintSuccess(request, response, "?????????ID???["
				+ processDefinitionId + "]??????????????????");
		if (state.equals("?????????")) {
			repositoryService.activateProcessDefinitionById(
					processDefinitionId, true, null);
			ServletUtils.outPrintSuccess(request, response, "?????????ID???["
					+ processDefinitionId + "]??????????????????");
		} else if (state.equals("?????????")) {
			repositoryService.suspendProcessDefinitionById(processDefinitionId,
					true, null);
			ServletUtils.outPrintSuccess(request, response, "?????????ID???["
					+ processDefinitionId + "]??????????????????");
		}
	}
	
	/**
	 * ???????????????????????????ID
	 * 
	 * @param processDefinitionId
	 *            ????????????
	 * @param resourceType
	 *            ????????????(xml|image)
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
