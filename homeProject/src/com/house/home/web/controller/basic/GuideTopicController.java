package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.Doc;
import com.house.home.entity.basic.GuideTopic;
import com.house.home.entity.basic.GuideTopicFolder;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.GuideTopicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/guideTopic")
public class GuideTopicController extends BaseController{
	@Autowired
	private  GuideTopicService guideTopicService;
	@Autowired
	private CzybmService czybmService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, GuideTopic guideTopic) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9043", "查看所有类目权限");
		guideTopic.setCzybh(this.getUserContext(request).getCzybh());
		guideTopicService.findPageBySql(page, guideTopic, hasAuthByCzybh);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response, GuideTopic guideTopic) throws Exception{
		
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9043", "查看所有类目权限");

		List<Map<String, Object>> node = guideTopicService.getAuthNode(this.getUserContext(request).getCzybh(),hasAuthByCzybh);
		String json = "[]";
		if(node != null){
			json = JSON.toJSONString(node);
		}
		FileUploadUtils.getBaseFileUrl(request);
		return new ModelAndView("admin/basic/guideTopic/guideTopic_list")
			.addObject("guideTopic", guideTopic).addObject("node", json);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,
			GuideTopic guideTopic) throws Exception{
		String folderCode = "";
		GuideTopicFolder guideTopicFolder= new GuideTopicFolder();
		
		if(guideTopic.getFolderPK() != null){
			guideTopicFolder = guideTopicService.get(GuideTopicFolder.class, guideTopic.getFolderPK());
			folderCode = guideTopicFolder.getFolderCode();
		}
		
		return new ModelAndView("admin/basic/guideTopic/guideTopic_save")
		.addObject("guideTopic", guideTopic)
		.addObject("folderCode", folderCode);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, GuideTopic guideTopic){
		logger.debug("添加文档开始");
		try{
			
			if(StringUtils.isBlank(guideTopic.getTopic())){
				ServletUtils.outPrintFail(request, response, "新增失败,主题不能为空");
				return;
			} 
			
			if(!guideTopicService.getCheckTopic(guideTopic.getTopic(),0)){
				ServletUtils.outPrintFail(request, response, "新增失败,该主题已经存在");
				return;
			}
			
			guideTopic.setLastUpdate(new Date());
			guideTopic.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			guideTopic.setExpired("F");
			guideTopic.setActionLog("ADD");
			guideTopic.setVisitCnt(0);
			guideTopic.setCreateCZY(this.getUserContext(request).getCzybh());
			guideTopic.setCreateDate(new Date());
			
			guideTopicService.save(guideTopic);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增文档失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, GuideTopic guideTopic){
		logger.debug("添加文档开始");
		try{
			
			if(guideTopic.getPk() == null){
				ServletUtils.outPrintFail(request, response, "编辑失败,该主题已不存在");
				return;
			}
			
			if(StringUtils.isBlank(guideTopic.getTopic())){
				ServletUtils.outPrintFail(request, response, "编辑失败,主题不能为空");
				return;
			}
			
			if(!guideTopicService.getCheckTopic(guideTopic.getTopic(),guideTopic.getPk())){
				ServletUtils.outPrintFail(request, response, "编辑失败,该主题已经存在");
				return;
			}
			
			GuideTopic newGuideTopic = new GuideTopic();
			newGuideTopic = guideTopicService.get(GuideTopic.class, guideTopic.getPk());
			newGuideTopic.setTopic(guideTopic.getTopic());
			newGuideTopic.setContent(guideTopic.getContent());
			newGuideTopic.setKeyWords(guideTopic.getKeyWords());
			newGuideTopic.setFolderPK(guideTopic.getFolderPK());
			newGuideTopic.setLastUpdate(new Date());
			newGuideTopic.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			newGuideTopic.setActionLog("ADD");
			
			guideTopicService.update(newGuideTopic);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增文档失败");
		}
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,
			GuideTopic guideTopic) throws Exception{
		
		if(guideTopic.getPk() != null){
			guideTopic = guideTopicService.get(GuideTopic.class, guideTopic.getPk());
			
			// 更新查看次数
			if(guideTopic != null){
				guideTopic.setVisitCnt(guideTopic.getVisitCnt() + 1);
				guideTopicService.update(guideTopic);
			}
			
		}
		
		return new ModelAndView("admin/basic/guideTopic/guideTopic_update")
		.addObject("guideTopic", guideTopic);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,
			GuideTopic guideTopic) throws Exception{
		
		if(guideTopic.getPk() != null){
			guideTopic = guideTopicService.get(GuideTopic.class, guideTopic.getPk());

			// 更新查看次数
			if(guideTopic != null){
				guideTopic.setVisitCnt(guideTopic.getVisitCnt() + 1);
				guideTopicService.update(guideTopic);
			}
		}
		
		
		return new ModelAndView("admin/basic/guideTopic/guideTopic_view")
		.addObject("guideTopic", guideTopic);
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request,HttpServletResponse response, Integer pk){
		logger.debug("删除文档开始");
		try{
			
			GuideTopic guideTopic = new GuideTopic();
			
			if(pk != null){
				guideTopic = guideTopicService.get(GuideTopic.class, pk);
			}
			
			
			// 用过期操作代替删除
			guideTopic.setExpired("T");
			guideTopic.setLastUpdate(new Date());
			guideTopic.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			guideTopicService.update(guideTopic);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除文档失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			GuideTopic guideTopic){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		guideTopic.setCzybh(this.getUserContext(request).getCzybh());
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9043", "查看所有类目权限");
		guideTopicService.findPageBySql(page, guideTopic, hasAuthByCzybh);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"问答管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
