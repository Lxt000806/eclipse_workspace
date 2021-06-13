package com.house.home.web.controller.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.EmpPicUploadRule;
import com.house.framework.commons.fileUpload.impl.InfoAttachUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.InfoAttach;
import com.house.home.entity.basic.InfoRead;
import com.house.home.entity.basic.Information;
import com.house.home.service.basic.InfoAttachService;
import com.house.home.service.basic.InfoReadService;
import com.house.home.service.basic.InformationService;

@Controller
@RequestMapping("/admin/information")
public class InformationController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(InformationController.class);

	@Autowired
	private InformationService informationService;
	@Autowired
	private InfoAttachService infoAttachService;
	@Autowired
	private InfoReadService infoReadService;

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
			HttpServletResponse response, Information information) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		informationService.findPageBySqlForInfo(page, information);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Information列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/information/information_list");
	}
	/**
	 * Information查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/information/information_code");
	}
	
	/**
	 * Information查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPreviewPic")
	public ModelAndView goPreviewPic(HttpServletRequest request,
			HttpServletResponse response,Integer pk) throws Exception {
	        InfoAttach infoAttach = infoAttachService.get(InfoAttach.class, pk);
	        String url = "";
	        if (infoAttach != null) {
	        	url = FileUploadUtils.getFileUrl(infoAttach.getFtpPath());	    
	        	int lastIndexOfSlash = url.lastIndexOf("/");
	        	
	        	// 解决中文转换和空格转换问题
	        	String filename = url.substring(lastIndexOfSlash + 1);
	        	filename = URLEncoder.encode(filename, "UTF-8");
	        	filename = filename.replace("+", "%20");  
	        	url = url.substring(0, lastIndexOfSlash + 1) + filename;
	        }
		
		return new ModelAndView("admin/basic/information/information_previewPic").addObject("url", url);
	}
	
	/**
	 * 跳转到新增Information页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			Information info){
		logger.debug("跳转到新增Information页面");
		Information information = null;
		JSONArray receiveZTreeJson = new JSONArray();
		if (StringUtils.isNotBlank(info.getNumber())){
			information = new Information();
			Map<String,Object> map = informationService.getByNumber(info.getNumber());
			BeanConvertUtil.mapToBean(map, information);
			
			if (StringUtils.isNotBlank(information.getInfoCata())
			        && information.getInfoCata().endsWith(",")) {
                information.setInfoCata(
                        information.getInfoCata()
                        .substring(0, information.getInfoCata().length() - 1));
            }
			
			if("C".equals(info.getM_umState())){
				information.setNumber(null);
				information.setM_umState("C");
				information.setSendDate(new Date());
			}else{
				information.setM_umState("M");
				information.setExpired("F");
			}
			List<Map<String,Object>> receiveNode = informationService.getReceiveNodes(info.getNumber());
			receiveZTreeJson = JSONArray.parseArray(JSON.toJSONString(receiveNode));
		}else{
			information = new Information();
			information.setInfoType("1");
			information.setM_umState("A");
			information.setSendDate(new Date());
		}
		List<Map<String,Object>> depEmpNodes = informationService.getDepEmpNodes();
		JSONArray depEmpZTreeJson= JSONArray.parseArray(JSON.toJSONString(depEmpNodes));
		information.setSendCZY(getUserContext(request).getCzybh());
		information.setSendCzyDescr(getUserContext(request).getZwxm());
		return new ModelAndView("admin/basic/information/information_save")
			.addObject("information", information)
			.addObject("depEmpZTreeJson", depEmpZTreeJson)
			.addObject("receiveZTreeJson", receiveZTreeJson);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			Information info){
		Map<String,Object> map = informationService.getByNumber(info.getNumber());
		Information information = new Information();
		BeanConvertUtil.mapToBean(map, information);
		
		if (StringUtils.isNotBlank(information.getInfoCata())
                && information.getInfoCata().endsWith(",")) {
            information.setInfoCata(
                    information.getInfoCata()
                    .substring(0, information.getInfoCata().length() - 1));
        }
		
		information.setM_umState(info.getM_umState());
		List<Map<String,Object>> depEmpNodes = informationService.getDepEmpNodes();
		List<Map<String,Object>> receiveNode = informationService.getReceiveNodes(info.getNumber());
		JSONArray depEmpZTreeJson= JSONArray.parseArray(JSON.toJSONString(depEmpNodes));
		JSONArray receiveZTreeJson= JSONArray.parseArray(JSON.toJSONString(receiveNode));
		List<Map<String,Object>> infoAttachList = infoAttachService.getByInfoNum(info.getNumber());
		
		return new ModelAndView("admin/basic/information/information_view")
				.addObject("information", information)
				.addObject("infoAttachList", infoAttachList)
				.addObject("depEmpZTreeJson", depEmpZTreeJson)
				.addObject("receiveZTreeJson", receiveZTreeJson);
	}
	/**
	 * 跳转到修改Information页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改Information页面");
		Information information = null;
		if (StringUtils.isNotBlank(id)){
			information = informationService.get(Information.class, id);
		}else{
			information = new Information();
		}
		
		return new ModelAndView("admin/basic/information/information_update")
			.addObject("information", information);
	}
	
	/**
	 * 跳转到查看Information页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看Information页面");
		Map<String,Object> map = informationService.getByNumber(id);
		Information information = new Information();
		BeanConvertUtil.mapToBean(map, information);
		try{
			InfoRead infoRead = new InfoRead();
			infoRead.setInfoNum(id);
			infoRead.setStatus("1");
			infoRead.setRcvCzy(this.getUserContext(request).getEmnum());
			infoRead.setLastUpdatedBy(getUserContext(request).getCzybh());
			infoReadService.updateForProc(infoRead);
		}catch(Exception e){
		}
		List<Map<String,Object>> infoAttachList = infoAttachService.getByInfoNum(id);
		return new ModelAndView("admin/basic/information/information_detail")
				.addObject("information", information)
				.addObject("infoAttachList", infoAttachList);
	}
	
	/**
	 * 改用MultipartFormData解析HttpServletRequest
	 * 
	 * @param request
	 * @param response
	 * @author 张海洋
	 */
    @RequestMapping("/uploadInfoFileNew")
    public void uploadInfoFileNew(HttpServletRequest request,
            HttpServletResponse response) {
        
        try {
        
            Information information = new Information();
            MultipartFormData multipartFormData = new MultipartFormData(request);
            
            String m_umState   = multipartFormData.getParameter("m_umState"),
                    number     = multipartFormData.getParameter("number"),
                    infoCata   = multipartFormData.getParameter("infoCata"),
                    infoType   = multipartFormData.getParameter("infoType"),
                    infoTitle  = multipartFormData.getParameter("infoTitle"),
                    infoText   = multipartFormData.getParameter("infoText"),
                    infoAttach = multipartFormData.getParameter("infoAttach"),
                    rcvCzys    = multipartFormData.getParameter("rcvCzys"),
                    expired    = multipartFormData.getParameter("expired");
            
            infoAttach = JSON.parseArray(
                    JSON.parseObject(infoAttach).get("detailJson").toString()
                    ).toString();
            
            information.setM_umState(m_umState);
            
            if ("M".equals(m_umState)) {
                information.setNumber(number);
                information.setExpired(expired);
            }
            
            information.setSendCZY(getUserContext(request).getCzybh());
            information.setStatus("1");
            information.setLastUpdate(new Date());
            information.setLastUpdatedBy(getUserContext(request).getCzybh());
            information.setExpired("F");
            information.setInfoCata(infoCata);
            information.setInfoType(infoType);
            information.setInfoTitle(infoTitle);
            information.setInfoText(infoText);
            information.setInfoAttach(infoAttach);
            information.setRcvCzys(rcvCzys);

            Result result = informationService.doSave(information);
            if (!result.isSuccess()) {
                ServletUtils.outPrintFail(request, response, "公告消息保存失败：" + result.getInfo());
                return;
            }
            
            List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
                InfoAttachUploadRule infoAttachUploadRule =
                        new InfoAttachUploadRule(attatchment.getName(), result.getInfo());
                Result uploadResult =
                        FileUploadUtils.upload(attatchment.getInputStream(), 
                        		infoAttachUploadRule.getFileName(),infoAttachUploadRule.getFilePath());
                
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "公告消息保存失败：内部转存错误");
                    return;
                }
            }
            
            ServletUtils.outPrintSuccess(request, response, "公告消息保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "公告消息保存失败：程序异常");
        }
    }

	
	/**
	 * 修改Information
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Information information){
		logger.debug("修改Information开始");
		try{
			information.setLastUpdate(new Date());
			information.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.informationService.update(information);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Information失败");
		}
	}
	
	/**
	 * 提交Information
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCommit")
	public void doCommit(HttpServletRequest request, HttpServletResponse response, Information info){
		logger.debug("提交Information开始");
		try{
			System.out.println(info.getNumber());
			Information information = informationService.get(Information.class, info.getNumber());
			if("1".equals(information.getInfoType())){
				information.setM_umState("S");
			}else{
				information.setM_umState("T");
			}
			Result result = informationService.doSave(information);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"提交成功");
			}else{
				ServletUtils.outPrintFail(request, response,"提交失败，错误信息："+result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "提交失败");
		}
	}
	
	/**
	 * 审核通过Information
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doPass")
	public void doPass(HttpServletRequest request, HttpServletResponse response, Information info){
		logger.debug("审核通过Information开始");
		try{
			Information information = informationService.get(Information.class, info.getNumber());
			information.setM_umState("P");
			Result result = informationService.doSave(information);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"审核通过");
			}else{
				ServletUtils.outPrintFail(request, response,"审核通过失败，错误信息："+result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "审核失败");
		}
	}
	
	/**
	 * 提交Information
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request, HttpServletResponse response, Information info){
		logger.debug("审核不通过Information开始");
		try{
			Information information = informationService.get(Information.class, info.getNumber());
			information.setM_umState("R");
			Result result = informationService.doSave(information);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"审核不通过成功");
			}else{
				ServletUtils.outPrintFail(request, response,"审核不通过失败，错误信息："+result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "审核不通过失败");
		}
	}
	
	/**
	 * 删除Information
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, Information info){
		logger.debug("删除Information开始");
		try{
			Information information = informationService.get(Information.class, info.getNumber());
			information.setM_umState("D");
			Result result = informationService.doSave(information);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"删除公告信息成功成功");
			}else{
				ServletUtils.outPrintFail(request, response,"删除公告信息失败，错误信息："+result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除公告信息失败");
		}
	}

	/**
	 *Information导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Information information){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		informationService.findPageBySql(page, information);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"Information_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *Information下载
	 * @param request
	 * @param response
	 */
	@RequestMapping("/download")
	public void download(HttpServletRequest request, 
			HttpServletResponse response, String id){
		//ftp://192.168.0.224/AttachFile/
		try{
//			Map<String,Object> map = xtdmService.getFtpData();
//			if (map!=null && map.size()>0){
//				String ftpaddr = String.valueOf(map.get("ftpaddr"));
//				String ftpuser = String.valueOf(map.get("ftpuser"));
//				String ftppwd = String.valueOf(map.get("ftppwd"));
//				String ftpport = String.valueOf(map.get("ftpport"));
//				FtpUtils ftp = new FtpUtils(ftpaddr, ftpport, ftpuser, ftppwd);
//				//ftp.download("2016年业绩绩效管理文件.doc", "/AttachFile/0000000335/", "d:/2016年业绩绩效管理文件.doc");
//				InfoAttach infoAttach = infoAttachService.get(InfoAttach.class, Integer.parseInt(id));
//				if (infoAttach!=null){
//					ftp.download(infoAttach.getFileName(), "/AttachFile/"+infoAttach.getFtpPath().replace(infoAttach.getFileName(), ""), 
//							"d:/"+infoAttach.getFileName(),response);
//					ftp.closeConnect();
//				}
//			}
			InfoAttach infoAttach = infoAttachService.get(InfoAttach.class, Integer.parseInt(id));
			
			String urlString = SystemConfig.getProperty("infoAttach", "", "photo")+infoAttach.getInfoNum()+"/"+infoAttach.getFileName();
			ServletUtils.downLoad(request,response,urlString);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 重构后公告消息附件下载接口
	 * 
	 * @param request
	 * @param response
	 * @param pk 公告消息附件主键
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/downloadNew")
	public void downloadNew(HttpServletRequest request,
	        HttpServletResponse response, int pk) {
	    
	    try {
	        InfoAttach infoAttach = infoAttachService.get(InfoAttach.class, pk);
	        if (infoAttach == null) {
	            ServletUtils.outPrintFail(request, response, "下载公告消息附件失败：不存在的记录");
	            return;
	        }
	        
	        String url = FileUploadUtils.getFileUrl(infoAttach.getFtpPath());	    
	        int lastIndexOfSlash = url.lastIndexOf("/");
	        
	        // 解决中文转换和空格转换问题
	        String filename = url.substring(lastIndexOfSlash + 1);
	        filename = URLEncoder.encode(filename, "UTF-8");
	        filename = filename.replace("+", "%20");  
	        url = url.substring(0, lastIndexOfSlash + 1) + filename;
	        	        
	        ServletUtils.download(request, response, url, infoAttach.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "下载公告消息附件失败：程序异常");
        }
	}
	
	/**
	 * 重构后公告消息附件下载接口
	 * 
	 * @param request
	 * @param response
	 * @param pk 公告消息附件主键
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/previewPdf")
	public void previewPdf(HttpServletRequest request,
	        HttpServletResponse response, int pk) {
	    
	    try {
	    	response.setContentType("application/pdf;charset=utf-8"); 
	        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode("公告消息附件.pdf", "utf-8"));
	       
	        InfoAttach infoAttach = infoAttachService.get(InfoAttach.class, pk);
	        if (infoAttach == null) {
	            ServletUtils.outPrintFail(request, response, "下载公告消息预览失败：不存在的记录");
	            return;
	        }
	        String url = infoAttach.getFtpPath();	    
	        int lastIndexOfSlash = url.lastIndexOf("/");
	        
	        // 解决中文转换和空格转换问题
	        String filename = url.substring(lastIndexOfSlash + 1);
	        filename = URLEncoder.encode(filename, "UTF-8");
	        filename = filename.replace("+", "%20");  
	        url = url.substring(0, lastIndexOfSlash + 1) + filename;
	        
	        InputStream in = FileUploadUtils.download(url);
	        OutputStream out = response.getOutputStream();
	        Streams.copy(in, out, true);
	        //ServletUtils.download(request, response, url, infoAttach.getFileName());
	        
	        
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "下载公告消息附件失败：程序异常");
        }
	}
	
	@RequestMapping("/goInfoRead")
	public ModelAndView goInfoRead(HttpServletRequest request,
			HttpServletResponse response,String number) throws Exception {
		Information information = new Information();
		information.setNumber(number);
		return new ModelAndView("admin/basic/information/information_infoRead").addObject("information", information);
	}
	
	
	@RequestMapping("/goInfoReadJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>>  goInfoReadJqGrid(HttpServletRequest request,
			HttpServletResponse response, Information information) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		informationService.findInfoReadPageBySql(page, information);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goInfoAttachJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>>  goInfoAttachJqGrid(HttpServletRequest request,
			HttpServletResponse response, Information information) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		try {
			informationService.findInfoAttachPageBySql(page, information);
			return new WebPage<Map<String,Object>>(page);
		} catch (Exception e) {
			//当发生异常时，传一个空的List到result中
			e.printStackTrace();
			List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
			page.setResult(arrayList);
			return new WebPage<Map<String,Object>>(page);
		}
	}
	
	@RequestMapping("/getChildrenNode")
	@ResponseBody
	public List<Map<String,Object>>  getChildrenNode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String higherDept="";
		if(request.getParameter("code")!=null){
			higherDept=request.getParameter("code");
		}
		try {
 			List<Map<String,Object>> childrenNodes = informationService.getChildrenNodes(higherDept);
			return childrenNodes;
		} catch (Exception e) {
			//当发生异常时，传一个空的List到result中
			e.printStackTrace();
			List<Map<String, Object>> childrenNodes = new ArrayList<Map<String, Object>>();
			return childrenNodes;
		}
	}
	
	@RequestMapping("/getDepEmpNodes")
	@ResponseBody
	public List<Map<String,Object>>  getDepEmpNodes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
 			List<Map<String,Object>> depEmpNodes = informationService.getDepEmpNodes();
 			String depEmpZTreeJson = new ObjectMapper().writeValueAsString(depEmpNodes);
			return depEmpNodes;
		} catch (Exception e) {
			//当发生异常时，传一个空的List到result中
			e.printStackTrace();
			List<Map<String, Object>> depEmpNodes = new ArrayList<Map<String, Object>>();
			return depEmpNodes;
		}
	}
}
