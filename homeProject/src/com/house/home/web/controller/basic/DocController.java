package com.house.home.web.controller.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.DocUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.Doc;
import com.house.home.entity.basic.DocAttachment;
import com.house.home.entity.basic.DocDownloadLog;
import com.house.home.entity.basic.DocFolder;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.finance.Asset;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.DocService;
import com.house.home.service.basic.XtcsService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/doc")
public class DocController extends BaseController{
	
	@Autowired
	private  DocService docService;
	@Autowired
	private XtcsService xtcsService;
	@Autowired
	private CzybmService czybmService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, Doc doc) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		if("2".equals(doc.getStatus()) && StringUtils.isBlank(page.getPageOrderBy())){
			page.setPageOrderBy("fileddate");
			page.setPageOrder("Desc");
		}
		doc.setCzybh(this.getUserContext(request).getCzybh());
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9040", "????????????????????????");
		doc.setHasAuthByCzybh(hasAuthByCzybh);
		docService.findPageBySql(page, doc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response, Doc doc) throws Exception{
		
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9040", "????????????????????????");
		
		List<Map<String, Object>> node = docService.getAuthNode(this.getUserContext(request).getCzybh(), hasAuthByCzybh);
		String json = "[]";
		if(node != null){
			json = JSON.toJSONString(node);
		}
		FileUploadUtils.getBaseFileUrl(request);
		return new ModelAndView("admin/basic/doc/doc_list")
			.addObject("doc", doc).addObject("node", json);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,
			Doc doc) throws Exception{
		String folderCode = "";
		DocFolder docFolder= new DocFolder();
		
		String docAttMaxCnt = xtcsService.getQzById("DocAttMaxCnt");
		String docAttMaxSize = xtcsService.getQzById("DocAttMaxSz");
		
		if(doc.getFolderPK() != null){
			docFolder = docService.get(DocFolder.class, doc.getFolderPK());
			folderCode = docFolder.getFolderCode();
		}
		
		doc.setDateStr(DateUtil.DateToString(new Date(),"yyyyMMdd"));
		
		return new ModelAndView("admin/basic/doc/doc_save")
		.addObject("doc", doc)
		.addObject("folderCode", folderCode)
		.addObject("docAttMaxCnt", docAttMaxCnt)
		.addObject("docAttMaxSize", docAttMaxSize);
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
	
	@RequestMapping("/getDocAtt")
	@ResponseBody
	public JSONArray getDocAtt(HttpServletRequest request, HttpServletResponse response,
			Integer pk){
		
		List<Map<String, Object>> docAttrs = docService.getDocAtt(pk);
		
		JSONArray jsonData = new JSONArray();
		
		jsonData = JSONArray.fromObject(docAttrs);
		
		return jsonData;
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
	
	@RequestMapping("/goUpdateVersion")
	public ModelAndView goUpdateVersion(HttpServletRequest request, HttpServletResponse response,
			Integer pk) throws Exception{
		
		Doc doc = new Doc();
		if(pk != null){
			doc = docService.get(Doc.class, pk);
		}
		
		String docAttMaxCnt = xtcsService.getQzById("DocAttMaxCnt");
		String docAttMaxSize = xtcsService.getQzById("DocAttMaxSz");
		
		return new ModelAndView("admin/basic/doc/doc_updateVersion")
		.addObject("doc", doc).addObject("docAttMaxCnt", docAttMaxCnt)
		.addObject("docAttMaxSize", docAttMaxSize);
	}
	
	@RequestMapping("/goViewFiledDoc")
	public ModelAndView goViewFiledDoc(HttpServletRequest request, HttpServletResponse response,
			Integer pk) throws Exception{
		
		Doc doc = new Doc();
		if(pk != null){
			doc = docService.get(Doc.class, pk);
		}
		
		return new ModelAndView("admin/basic/doc/doc_viewFiledDoc")
		.addObject("doc", doc).addObject("modal", "doc");
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, Doc doc){
		logger.debug("??????????????????");
		try{
			
			Map<String, Object> map = checkValid(doc, "A");
			if(!Boolean.parseBoolean(map.get("isValid").toString())){
				ServletUtils.outPrintFail(request, response, map.get("msg").toString());
				return;
			}
			
			JSONArray attrDatas = new JSONArray();
			
			if(StringUtils.isNotBlank(request.getParameter("attrDatas"))){
				attrDatas = net.sf.json.JSONArray.fromObject(request.getParameter("attrDatas"));
			}

			doc.setCreateCZY(this.getUserContext(request).getCzybh());
			doc.setCreateDate(new Date());
			doc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			doc.setLastUpdate(new Date());
			doc.setExpired("F");
			doc.setActionLog("ADD");
			doc.setDownloadCnt(0);
			doc.setStatus("1");
			
			docService.doSave(doc, attrDatas);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param doc
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, Doc doc){
		logger.debug("??????????????????");
		try{
			
			Doc oldDoc = new Doc();
			if(doc.getPk() != null){
				oldDoc = docService.get(Doc.class, doc.getPk());
			}
			
			doc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Map<String, Object> map = checkValid(doc, "M");
			if(!Boolean.parseBoolean(map.get("isValid").toString())){
				ServletUtils.outPrintFail(request, response, map.get("msg").toString());
				return;
			}
			
			doc.setExpired("F");
			doc.setActionLog("EDIT");
			doc.setLastUpdate(new Date());
			doc.setStatus("1");
			doc.setDownloadCnt(oldDoc.getDownloadCnt());
			doc.setCreateCZY(oldDoc.getCreateCZY());
			doc.setCreateDate(oldDoc.getCreateDate());
			docService.update(doc);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	
	@RequestMapping("/doUpdateVersion")
	public void doUpdateVersion(HttpServletRequest request,HttpServletResponse response, Doc doc){
		logger.debug("????????????????????????");
		try{
			Doc oldDoc = new Doc();
			if(doc.getPk() != null){
				oldDoc = docService.get(Doc.class, doc.getPk());
			}
			
			oldDoc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Map<String, Object> map = checkValid(doc, "M");
			if(!Boolean.parseBoolean(map.get("isValid").toString())){
				ServletUtils.outPrintFail(request, response, map.get("msg").toString());
				return;
			}
			
			Integer oldPk = oldDoc.getPk();
			oldDoc.setStatus("2");
			oldDoc.setFiledCZY(this.getUserContext(request).getCzybh());
			oldDoc.setFiledDate(new Date());
			oldDoc.setExpired("F");
			oldDoc.setActionLog("EDIT");
			oldDoc.setLastUpdate(new Date());
			// ??????????????????????????????
			docService.update(oldDoc);

			oldDoc.setDocVersion(doc.getDocVersion());
			oldDoc.setStatus("1");
			oldDoc.setFiledCZY(null);
			oldDoc.setFiledDate(null);
			oldDoc.setActionLog("ADD");

			JSONArray attrDatas = new JSONArray();
			if(StringUtils.isNotBlank(request.getParameter("attrDatas"))){
				attrDatas = net.sf.json.JSONArray.fromObject(request.getParameter("attrDatas"));
			}
			// ?????????????????????
			docService.doSave(oldDoc, attrDatas);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doFile")
	public void doFile(HttpServletRequest request,HttpServletResponse response, Integer pk){
		logger.debug("??????????????????");
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
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request,HttpServletResponse response, Integer pk){
		logger.debug("??????????????????");
		try{
			
			Doc doc = new Doc();
			
			if(pk != null){
				doc = docService.get(Doc.class, pk);
			}
			
			doc.setLastUpdate(new Date());
			doc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Map<String, Object> map = checkValid(doc, "D");
			if(!Boolean.parseBoolean(map.get("isValid").toString())){
				ServletUtils.outPrintFail(request, response, map.get("msg").toString());
				return;
			}
			
			// ???????????????????????????
			doc.setExpired("T");
			docService.update(doc);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	@RequestMapping("/doDelAtt")
	public void doDelAtt(HttpServletRequest request,HttpServletResponse response, Integer pk){
		logger.debug("??????????????????");
		try{
			
			DocAttachment docAttachment = new DocAttachment();
			
			if(pk != null){
				docAttachment = docService.get(DocAttachment.class, pk );
			}
			
			docAttachment.setLastUpdate(new Date());
			docAttachment.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			// ???????????????????????????
			docAttachment.setExpired("T");
			docService.update(docAttachment);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	@RequestMapping("/uploadDoc")
	public void uploadDoc(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			// ???????????? 
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String folderCode = multipartFormData.getParameter("folderCode"),
					u_umState = multipartFormData.getParameter("u_umState"),
					pk = multipartFormData.getParameter("pk");

			FileItem fileItem = multipartFormData.getFileItem();
			
			// ??????????????????????????????
			DocUploadRule docUploadRule = new DocUploadRule(folderCode, fileItem.getName());

			// ?????????????????????????????????????????????
			Result result = FileUploadUtils.upload(fileItem.getInputStream(), 
					docUploadRule.getFileName(),docUploadRule.getFilePath());
			
			if (!Result.SUCCESS_CODE.equals(result.getCode())) {
				logger.error("?????????????????????????????????:{}",fileItem.getName());
				ServletUtils.outPrintFail(request, response, "??????????????????");
				return;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fullName", docUploadRule.getFullName());
			map.put("originalName", docUploadRule.getOriginalName());
			
			
			if("M".equals(u_umState)){
				Doc doc = new Doc();
				doc.setPk(Integer.parseInt(pk));
				docService.doSaveFile(doc,map);
			}
			
			// ??????????????????
			ServletUtils.outPrintSuccess(request, response, map);
			
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response,DocAttachment docAttachment){
		try{ 
			String fullName = "";
			Integer pk = docService.getDocAttachmentPk(docAttachment.getAttName(),docAttachment.getDocPK());
			
			if(pk != null){
				
				docAttachment = docService.get(DocAttachment.class, pk);
			} else {
				
				ServletUtils.outPrintFail(request, response, "????????????");
				return;
			}
			
			if(StringUtils.isNotBlank(docAttachment.getAttName())){
				fullName = docAttachment.getAttachment();
			}
			
			String url= FileUploadUtils.getFileUrl(fullName);
			
			// ??????????????????
			ServletUtils.download(request, response, url, docAttachment.getAttName());
			
			// ????????????????????????
			if(docAttachment.getDocPK() != null){
				Doc doc = docService.get(Doc.class, docAttachment.getDocPK());
				if(doc != null){
					doc.setDownloadCnt(doc.getDownloadCnt()+1);
					docService.update(doc);
				}
			}
			
			// ???????????????????????????
			docAttachment.setDownloadCnt(docAttachment.getDownloadCnt()+1);
			docService.update(docAttachment);
			
			// ??????????????????  ??????????????????????????????
			Doc doc = new Doc();
			if(docAttachment.getDocPK() != null){
				doc = docService.get(Doc.class, docAttachment.getDocPK());
				if (doc != null && "1".equals(doc.getStatus())) {
					DocDownloadLog docDownloadLog = new DocDownloadLog();
					docDownloadLog.setDocPK(docAttachment.getDocPK());
					docDownloadLog.setAttachmentPK(docAttachment.getPk());
					docDownloadLog.setDownloadCZY(this.getUserContext(request).getCzybh());
					docDownloadLog.setDownloadDate(new Date());
					docService.save(docDownloadLog);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Doc doc){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		doc.setCzybh(this.getUserContext(request).getCzybh());
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "9040", "????????????????????????");
		doc.setHasAuthByCzybh(hasAuthByCzybh);
		docService.findPageBySql(page, doc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	public Map<String, Object> checkValid(Doc doc, String m_umState){
		Map<String, Object> result = new HashMap<String, Object>();
		
		Integer pk = -1;
		result.put("isValid", true);
		result.put("msg", "????????????");
		
		if(doc == null){
			result.put("isValid", false);
			result.put("msg", "????????????,???????????????");
			return result;
		}
		
		if("A".equals(m_umState)){ //
			pk = -1; 
			boolean docNameUnique = docService.checkDocNameUnique(doc.getDocName(), pk);
			if(!docNameUnique){
				result.put("isValid", false);
				result.put("msg", "????????????????????????????????????");
			}
			
			boolean docCodeUnique = docService.checkDocCodeUnique(doc.getDocCode(), pk);
			if(!docCodeUnique){
				result.put("isValid", false);
				result.put("msg", "???????????????????????????????????????");
				
			}
		} else {
			pk = doc.getPk();
		}
			
		if ("M".equals(m_umState)){ //??????
			if("2".equals(doc.getStatus())){
				result.put("isValid", false);
				result.put("msg", "??????????????????,????????????");
				return result;
			}
			
			boolean docNameUnique = docService.checkDocNameUnique(doc.getDocName(), pk);
			if(!docNameUnique){
				result.put("isValid", false);
				result.put("msg", "????????????????????????????????????");
			}
			
			boolean docCodeUnique = docService.checkDocCodeUnique(doc.getDocCode(), pk);
			if(!docCodeUnique){
				result.put("isValid", false);
				result.put("msg", "???????????????????????????????????????");
				
			}
		} else if ("D".equals(m_umState)) { //??????
			if("2".equals(doc.getStatus())){
				result.put("isValid", false);
				result.put("msg", "??????????????????,????????????");
				return result;
			}
			
			if(!doc.getLastUpdatedBy().equals(doc.getCreateCZY().trim())){
				result.put("isValid", false);
				result.put("msg", "????????????,?????????????????????????????????");
				return result;
			}
		} else if ("F".equals(m_umState)){ // ??????
			
			if("2".equals(doc.getStatus())){
				result.put("isValid", false);
				result.put("msg", "??????????????????,?????????????????????");
				return result;
			}
			
		}
		
		return result;
	}
	
	
}
