package com.house.home.web.controller.workflow;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.workflow.WFBpmnXMLConverter;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.workflow.ActModel;
import com.house.home.entity.workflow.ActProcdef;
import com.house.home.service.workflow.ActModelService;

/**
 * 流程模型控制器
 * 
 * @author henryyan
 */
@Controller
@RequestMapping(value = "/admin/workflow/model")
public class ModelController extends BaseController{

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	ActModelService actModelService;

	/**
	 * 模型列表查询
	 */
	@RequestMapping(value = "goList")
	public ModelAndView goList() {
		ModelAndView mav = new ModelAndView("admin/workflow/model_list");
		return mav;
	}
	/**
	 * 流程版本监控
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goListen")
	public ModelAndView processList(HttpServletRequest request,ActProcdef actProcdef) {
		ModelAndView mav = new ModelAndView("admin/workflow/process-listen").addObject("actProcdef", actProcdef);
		return mav;
	}
	
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ActModel actModel) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actModelService.findPageBySql(page, actModel);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGrid2")
	@ResponseBody
	public WebPage<Model> getJqGrid2(HttpServletRequest request,
			HttpServletResponse response,String modelName) throws Exception {
		Page<Model> page = this.newPageForJqGrid(request);
		page.setPageSize(5);
		List<Model> list = null;
		if (StringUtils.isNotBlank(modelName)){
			list = repositoryService.createModelQuery().modelNameLike(modelName)
					.listPage(page.getPageNo(), page.getPageSize());
		}else{
			list = repositoryService.createModelQuery()
					.listPage(page.getPageNo()+page.getPageSize()*(page.getPageNo()-1),page.getPageSize()*page.getPageNo());
		} 
		page.setResult(list);
		return new WebPage<Model>(page);
	}
	
	/**
	 * 添加模型列表
	 */
	@RequestMapping(value = "goAdd")
	public ModelAndView goAdd() {
		ModelAndView mav = new ModelAndView("admin/workflow/model_add");
		return mav;
	}
	
	/**
	 * 创建模型
	 */
	@RequestMapping(value = "doCreate", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam("name") String name,
			@RequestParam("key") String key,
			@RequestParam("description") String description,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("admin/workflow/model_design");
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(StringUtils.defaultString(key));
			modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));
			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

			mav.addObject("modelId", modelData.getId());
		} catch (Exception e) {
			logger.error("创建模型失败：", e);
		}
		
		return mav;
	}

	/**
	 * 根据Model部署流程
	 */
	@RequestMapping(value = "doDeploy/{modelId}")
	public void deploy(@PathVariable("modelId") String modelId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
				.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new WFBpmnXMLConverter().convertToXML(model);

			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment()
				.name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
			ServletUtils.outPrintSuccess(request, response, "部署成功，部署ID=" + deployment.getId());
		} catch (Exception e) {
			logger.error("根据模型部署流程失败：modelId={}", modelId, e);
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "部署失败");
		}
	}

	/**
	 * 导出model的xml文件
	 */
	@RequestMapping(value = "doExport/{modelId}")
	public void export(@PathVariable("modelId") String modelId,
			HttpServletResponse response) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService
					.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			WFBpmnXMLConverter xmlConverter = new WFBpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("导出model的xml文件失败：modelId={}", modelId, e);
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "doDelete/{modelId}")
	public void delete(@PathVariable("modelId") String modelId,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			repositoryService.deleteModel(modelId);
			ServletUtils.outPrintSuccess(request, response, "删除成功");
		}catch (Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "模型编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Model model = repositoryService.getModel(deleteId);
				if(model == null)
					continue;
				repositoryService.deleteModel(deleteId);
			}
		}
		logger.debug("删除模型 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

}
