package com.house.home.web.controller.query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Doc;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.DocService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.query.DocQuerySerivce;

@Controller
@RequestMapping("/admin/docQuery")
public class DocQueryController extends BaseController{

	@Autowired
	private  DocService docService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private DocQuerySerivce docQuerySerivce;
	@Autowired
	private CzybmService czybmService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, Doc doc) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		if("2".equals(doc.getStatus()) && StringUtils.isBlank(page.getPageOrderBy())){
			page.setPageOrderBy("fileddate");
			page.setPageOrder("Desc");
		}
		
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9040", "查看所有目录权限");
		doc.setHasAuthByCzybh(hasAuthByCzybh);
		doc.setCzybh(this.getUserContext(request).getCzybh());
		docQuerySerivce.findPageBySql(page, doc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response, Doc doc) throws Exception{
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9040", "查看所有目录权限");

		List<Map<String, Object>> node = docQuerySerivce.getAuthNode(this.getUserContext(request).getCzybh(), hasAuthByCzybh);
		String json = "[]";
		if(node != null){
			json = JSON.toJSONString(node);
		}
		FileUploadUtils.getBaseFileUrl(request);
		return new ModelAndView("admin/query/docQuery/docQuery_list")
			.addObject("doc", doc).addObject("node", json);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,
			Integer pk) throws Exception{
		
		Doc doc = new Doc();
		if(pk != null){
			doc = docService.get(Doc.class, pk);
		}

		String docAttMaxCnt = xtcsService.getQzById("DocAttMaxCnt");
		String docAttMaxSize = xtcsService.getQzById("DocAttMaxSz");
		
		List<Map<String, Object>> docAttrs = docService.getDocAtt(doc.getPk());
		JSONArray jsonData = new JSONArray();
		if(docAttrs != null){
			jsonData = JSONArray.fromObject(docAttrs);
		}
		
		boolean hasFiledDoc = docService.getHasFiledDoc(doc.getDocCode());
		
		return new ModelAndView("admin/basic/doc/doc_update")
		.addObject("doc", doc).addObject("hasFiledDoc", hasFiledDoc)
		.addObject("docAttMaxCnt", docAttMaxCnt)
		.addObject("docAttMaxSize", docAttMaxSize).addObject("docAttrs", jsonData);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,
			Integer pk) throws Exception{
		
		Doc doc = new Doc();
		if(pk != null){
			doc = docService.get(Doc.class, pk);
		}
		return new ModelAndView("admin/basic/doc/doc_view")
		.addObject("doc", doc);
	}
	
	@RequestMapping("/goViewFiledDoc")
	public ModelAndView goViewFiledDoc(HttpServletRequest request, HttpServletResponse response,
			Integer pk) throws Exception{
		
		Doc doc = new Doc();
		if(pk != null){
			doc = docService.get(Doc.class, pk);
		}
		
		return new ModelAndView("admin/basic/doc/doc_viewFiledDoc")
		.addObject("doc", doc).addObject("modal", "docQuery");
	}
	
	@RequestMapping("/doFile")
	public void doFile(HttpServletRequest request,HttpServletResponse response, Integer pk){
		logger.debug("添加文档开始");
		try{
			
			Doc doc = new Doc();
			
			if(pk != null){
				doc = docService.get(Doc.class, pk);
			}
			
			doc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Map<String, Object> map = checkValid(doc, "F");
			if(!Boolean.parseBoolean(map.get("isValid").toString())){
				ServletUtils.outPrintFail(request, response, map.get("msg").toString());
				return;
			}
			
			doc.setLastUpdate(new Date());
			doc.setFiledCZY(this.getUserContext(request).getCzybh());
			doc.setFiledDate(new Date());
			doc.setStatus("2");
			doc.setActionLog("EDIT");
			docService.update(doc);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "归档文档失败");
		}
	}
	
	public Map<String, Object> checkValid(Doc doc, String m_umState){
		Map<String, Object> result = new HashMap<String, Object>();
		
		Integer pk = -1;
		result.put("isValid", true);
		result.put("msg", "操作成功");
		
		if(doc == null){
			result.put("isValid", false);
			result.put("msg", "操作失败,文档不存在");
			return result;
		}
		
		if("A".equals(m_umState)){ //
			pk = -1; 
			boolean docNameUnique = docService.checkDocNameUnique(doc.getDocName(), pk);
			if(!docNameUnique){
				result.put("isValid", false);
				result.put("msg", "文档名称已存在，保存失败");
			}
			
			boolean docCodeUnique = docService.checkDocCodeUnique(doc.getDocCode(), pk);
			if(!docCodeUnique){
				result.put("isValid", false);
				result.put("msg", "文档编号已经存在，保存失败");
				
			}
		} else {
			pk = doc.getPk();
		}
			
		if ("M".equals(m_umState)){ //编辑
			if("2".equals(doc.getStatus())){
				result.put("isValid", false);
				result.put("msg", "该文档已归档,无法编辑");
				return result;
			}
			
			boolean docNameUnique = docService.checkDocNameUnique(doc.getDocName(), pk);
			if(!docNameUnique){
				result.put("isValid", false);
				result.put("msg", "文档名称已存在，保存失败");
			}
			
			boolean docCodeUnique = docService.checkDocCodeUnique(doc.getDocCode(), pk);
			if(!docCodeUnique){
				result.put("isValid", false);
				result.put("msg", "文档编号已经存在，保存失败");
				
			}
		} else if ("D".equals(m_umState)) { //删除
			if("2".equals(doc.getStatus())){
				result.put("isValid", false);
				result.put("msg", "该文档已归档,无法编辑");
				return result;
			}
			
			if(!doc.getLastUpdatedBy().equals(doc.getCreateCZY().trim())){
				result.put("isValid", false);
				result.put("msg", "删除失败,只能删除本人创建的文档");
				return result;
			}
		} else if ("F".equals(m_umState)){ // 归档
			
			if("2".equals(doc.getStatus())){
				result.put("isValid", false);
				result.put("msg", "该文档已归档,无需做归档操作");
				return result;
			}
			
		}
		
		return result;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Doc doc){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		doc.setCzybh(this.getUserContext(request).getCzybh());
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9040", "查看所有目录权限");
		doc.setHasAuthByCzybh(hasAuthByCzybh);
		docQuerySerivce.findPageBySql(page, doc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"文档管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
