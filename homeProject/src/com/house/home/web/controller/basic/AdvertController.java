package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Advert;
import com.house.home.service.basic.AdvertService;

@Controller
@RequestMapping("/admin/advert")
public class AdvertController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AdvertController.class);

	@Autowired
	private AdvertService advertService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param advert
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,	HttpServletResponse response, Advert advert) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		advertService.getAdvertList(page, advert);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * advert列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/advert/advert_list");
	}
	/**
	 * 跳转到新增advert页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增kindeditor页面");
		return new ModelAndView("admin/basic/advert/advert_save");
	}
	/**
	 * 跳转到修改advert页面
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("跳转到修改advert页面");
		Advert advert;
		if(pk == null){
			advert = new Advert();
		}else{
			advert = advertService.get(Advert.class, pk);
		}
		return new ModelAndView("admin/basic/advert/advert_update").addObject("data", advert);
	}
	
	/**
	 * 跳转到查看advert页面
	 * @param request
	 * @param response
	 * @param advert
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("跳转到查看advert页面");
		Advert advert;
		if(pk == null){
			advert = new Advert();
		}else{
			advert = advertService.get(Advert.class, pk);
		}
		return new ModelAndView("admin/basic/advert/advert_view").addObject("data", advert);
	}
	/**
	 * 添加advert
	 * @param request
	 * @param response
	 * @param advert
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,Advert advert){
		logger.debug("添加advert开始");
		try{
			advert.setLastUpdate(new Date());
			advert.setLastUpdatedBy(getUserContext(request).getCzybh());
			advert.setExpired("F");
			//advert.setDispSeq(0);
			advert.setActionLog("ADD");
			advertService.save(advert);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加advert失败");
		}
	}
	
	/**
	 * 修改advert
	 * @param request
	 * @param response
	 * @param advert
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,Advert advert){
		logger.debug("修改advert开始");
		try{
			Advert advertUpdate = this.advertService.get(Advert.class, advert.getPk());
			if(advertUpdate != null){
				advertUpdate.setAdvType(advert.getAdvType());
				advertUpdate.setPicAddr(advert.getPicAddr());
				advertUpdate.setContent(advert.getContent());
				advertUpdate.setExpired(advert.getExpired());
				advertUpdate.setTitle(advert.getTitle());
				advertUpdate.setDispSeq(advert.getDispSeq());
				advertUpdate.setOutUrl(advert.getOutUrl());
				advertUpdate.setActionLog("EDIT");
				advertUpdate.setLastUpdatedBy(getUserContext(request).getCzybh());
				advertUpdate.setLastUpdate(new Date());

				this.advertService.update(advertUpdate);
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改advert失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * advert删除
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response,Integer pk){
		logger.debug("删除advert记录开始");
		try{
			Advert advert;
			if(pk == null){
				advert = new Advert();
			}else{
				advert = this.advertService.get(Advert.class, pk);
				if(advert != null){
					this.advertService.delete(advert);
				}
			}
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除advert记录失败");
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/uploadImage")
	public void uploadImage(HttpServletRequest request, HttpServletResponse response){
		JSONObject returnMap = new JSONObject();
		try{
			String firstFileName = ""; 
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 设置允许上传的最大文件大小 500k
//			upload.setSizeMax(500 * 1024);
			// 获取多个上传文件
			List fileList = upload.parseRequest(request);
			Iterator it = fileList.iterator();
			if(fileList.size() <= 0){
				returnMap.put("state", "上传图片失败");
				ServletUtils.outPrintFail(request, response, returnMap);
				return;
			}
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				// 如果是普通 表单参数
				if (obit.isFormField()) { //普通域,获取页面参数
					continue;
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
						firstFileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
						OssManager.uploadFile(item.getInputStream(), firstFileName, "advert", firstFileName);
						
					}
				}
			}
			returnMap.put("url", OssConfigure.cdnAccessUrl+"/advert/"+firstFileName);
			returnMap.put("state", "SUCCESS");
			ServletUtils.outPrintSuccess(request, response, returnMap);
		}catch(Exception e){
			returnMap.put("state", "上传图片失败");
			ServletUtils.outPrintFail(request, response, returnMap);
			e.printStackTrace();
		}
	}
}
