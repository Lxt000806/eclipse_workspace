package com.house.home.web.controller.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.FileUploadRule;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.ContractTempUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.BaseBatchTemp;
import com.house.home.entity.basic.CmpCustType;
import com.house.home.entity.basic.Company;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.DesignDemo;
import com.house.home.service.basic.CmpCustTypeService;

@Controller
@RequestMapping("/admin/cmpCustType")
public class CmpCustTypeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CmpCustTypeController.class);

	@Autowired
	private CmpCustTypeService cmpCustTypeService;

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
			HttpServletResponse response, CmpCustType cmpCustType) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cmpCustTypeService.findPageBySql(page, cmpCustType);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CmpCustType列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/cmpCustType/cmpCustType_list");
	}
	/**
	 * CmpCustType查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/cmpCustType/cmpCustType_code");
	}
	/**
	 * 跳转到新增CmpCustType页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CmpCustType页面");
		CmpCustType cmpCustType = null;
		if (StringUtils.isNotBlank(id)){
			cmpCustType = cmpCustTypeService.get(CmpCustType.class, Integer.parseInt(id));
			cmpCustType.setPk(null);
			Company company=cmpCustTypeService.get(Company.class,cmpCustType.getCmpCode() );
			CustType custType=cmpCustTypeService.get(CustType.class,cmpCustType.getCustType());
			cmpCustType.setCmpDescr(company.getDesc1());
			cmpCustType.setCustTypeDescr(custType.getDesc1());
		}else{
			cmpCustType = new CmpCustType();
		}
		
		return new ModelAndView("admin/basic/cmpCustType/cmpCustType_save")
			.addObject("cmpCustType", cmpCustType);
	}
	/**
	 * 跳转到修改CmpCustType页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CmpCustType页面");
		CmpCustType cmpCustType = null;
		if (StringUtils.isNotBlank(id)){
			cmpCustType = cmpCustTypeService.get(CmpCustType.class, Integer.parseInt(id));
		}else{
			cmpCustType = new CmpCustType();
		}
		Company company=cmpCustTypeService.get(Company.class,cmpCustType.getCmpCode() );
		CustType custType=cmpCustTypeService.get(CustType.class,cmpCustType.getCustType());
		cmpCustType.setCmpDescr(company.getDesc1());
		cmpCustType.setCustTypeDescr(custType.getDesc1());
		return new ModelAndView("admin/basic/cmpCustType/cmpCustType_update")
			.addObject("cmpCustType", cmpCustType);
	}
	
	/**
	 * 跳转到查看CmpCustType页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CmpCustType页面");
		CmpCustType cmpCustType = cmpCustTypeService.get(CmpCustType.class, Integer.parseInt(id));
		Company company=cmpCustTypeService.get(Company.class,cmpCustType.getCmpCode() );
		CustType custType=cmpCustTypeService.get(CustType.class,cmpCustType.getCustType());
		cmpCustType.setCmpDescr(company.getDesc1());
		cmpCustType.setCustTypeDescr(custType.getDesc1());
		return new ModelAndView("admin/basic/cmpCustType/cmpCustType_view")
				.addObject("cmpCustType", cmpCustType);
	}
	
	/**
	 * 合同范本上传页面
	 * 
	 */
	@RequestMapping("/goContractTemp")
	public ModelAndView goContractTemp(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CmpCustType页面");
		CmpCustType cmpCustType = cmpCustTypeService.get(CmpCustType.class, Integer.parseInt(id));
		Company company=cmpCustTypeService.get(Company.class,cmpCustType.getCmpCode() );
		CustType custType=cmpCustTypeService.get(CustType.class,cmpCustType.getCustType());
		cmpCustType.setCmpDescr(company.getDesc1());
		cmpCustType.setCustTypeDescr(custType.getDesc1());
		return new ModelAndView("admin/basic/cmpCustType/cmpCustType_addContractTemp")
				.addObject("cmpCustType", cmpCustType);
	}
	
	/**
	 * 预览合同范本PDF文件
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	@RequestMapping("/goViewContractTemp")
	public void goViewContractTemp(HttpServletRequest request,
	        HttpServletResponse response, String fileName) throws IOException{
	    	    
	    response.setContentType("application/pdf;charset=utf-8"); 
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode("合同范本.pdf", "utf-8"));
        
        // InputStream in = new FileInputStream(getContractTempPath(fileName, "") + fileName);
        InputStream in = FileUploadUtils.download(fileName);
        
        OutputStream out = response.getOutputStream();
        Streams.copy(in, out, true);
	    
	}
	
	/**
	 * 添加CmpCustType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CmpCustType cmpCustType){
		logger.debug("添加CmpCustType开始");
		try{
			cmpCustType.setLastUpdate(new Date());
			cmpCustType.setLastUpdatedBy(getUserContext(request).getCzybh());
			cmpCustType.setExpired("F");
			cmpCustType.setActionLog("ADD");
			this.cmpCustTypeService.save(cmpCustType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CmpCustType失败");
		}
	}
	
	/**
	 * 修改CmpCustType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CmpCustType cmpCustType){
		logger.debug("修改CmpCustType开始");
		try{
			if (!"T".equals(cmpCustType.getExpired())) {
				cmpCustType.setExpired("F");
			}
			cmpCustType.setLastUpdate(new Date());
			cmpCustType.setLastUpdatedBy(getUserContext(request).getCzybh());
			cmpCustType.setActionLog("EDIT");
			this.cmpCustTypeService.update(cmpCustType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CmpCustType失败");
		}
	}
	
	/**
	 * 删除CmpCustType
	 * @param request
	 * @param response
	 * @param roleId
	 */

	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CmpCustType开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CmpCustType编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CmpCustType cmpCustType = cmpCustTypeService.get(CmpCustType.class, Integer.parseInt(deleteId));
				if(cmpCustType == null)
					continue;
				cmpCustType.setExpired("T");
				cmpCustTypeService.update(cmpCustType);
			}
		}
		logger.debug("删除CmpCustType IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadContractTemp")
	public void uploadWfProcPic(HttpServletRequest request,
			HttpServletResponse response) {
//		UploadPhotoResp respon = new UploadPhotoResp();
//		DesignDemo designDemo =new DesignDemo();
//		try {
//			String fileRealPath = "";//文件存放真实地址 
//			String firstFileName = ""; 
//			String PhotoPath="";
//			DiskFileItemFactory fac = new DiskFileItemFactory();
//			ServletFileUpload upload = new ServletFileUpload(fac);
//			upload.setHeaderEncoding("UTF-8");
//			// 设置允许上传的最大文件大小 500k
//			//upload.setSizeMax(500 * 1024);
//			// 获取多个上传文件
//			List fileList = upload.parseRequest(request);
//			Iterator it = fileList.iterator();
//			String pk =request.getParameter("pk");
//			String fileNameResp= "";
//			List<String> fileRealPathList = new ArrayList<String>();
//			List<String> PhotoPathList = new ArrayList<String>();
//			List<String> fileNameList = new ArrayList<String>();
//			Serializable serializable = null;
//			// 遍历上传文件写入磁盘
//			while (it.hasNext()) {
//				FileItem obit = (FileItem) it.next();
//				// 如果是普通 表单参数
//				if (obit.isFormField()) { //普通域,获取页面参数
//					String fieldName = obit.getFieldName();
//					String fieldValue = obit.getString("UTF-8");
//					if ("pk".equals(fieldName)){
//						pk = fieldValue;
//					}
//				}
//				// 如果是多媒体
//				if (obit instanceof DiskFileItem) {
//					DiskFileItem item = (DiskFileItem) obit;
//					// 如果item是文件上传表单域
//					// 获得文件名及路径
//					String fileName = item.getName();
//					if (fileName != null) {
//						if(fileName.indexOf("?") != -1){
//							fileName = fileName.substring(0, fileName.indexOf("?"));
//						}
//						firstFileName = fileName.substring(//本地的文件名
//								fileName.lastIndexOf("\\") + 1);
//						String extension = firstFileName
//								.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
//						String fileNameNew = System.currentTimeMillis()+extension;
//						// fileNameResp = fileNameNew;
//						//saveCustDocName=fileNameNew;
//						String filePath = getContractTempPath(fileNameNew,"");
//						PhotoPath=getWfProcPicDownloadPath(request,fileNameNew,"")+fileNameNew;
//						//savePath=PhotoPath;
//						// FileUploadServerMgr.makeDir(filePath);
//						fileRealPath = filePath+fileNameNew;// 文件存放真实地址
//						
//						// 获得文件输入流 
//						BufferedInputStream in = new BufferedInputStream(item.getInputStream());
//						
////						BufferedOutputStream outStream = new BufferedOutputStream(
////								new FileOutputStream(new File(fileRealPath)));// 获得文件输出流
////						Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹
//						
//						FileUploadRule rule = new ContractTempUploadRule(fileName);
//						Result result = FileUploadUtils.upload(in, rule);
//						
//						fileNameResp = rule.getFullName();
//						
//				        //保存资料信息
//				        doUpdateCmpCustType(Integer.parseInt(pk), rule.getFullName(), getUserContext(request).getCzybh());
//				        //设计图纸上传流程
//				        fileRealPathList.add(fileRealPath);
//						PhotoPathList.add(PhotoPath);
//						fileNameList.add(fileNameNew);
//					}
//				}
//			}
//			
//			respon.setPhotoPathList(PhotoPathList);
//			respon.setPhotoNameList(PhotoPathList);
//			ServletUtils.outPrintSuccess(request, response, fileNameResp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			ServletUtils.outPrintFail(request, response, "上传失败");
//		}
	    
	    try {
            MultipartFormData multipartFormData = new MultipartFormData(request);
            String pk = multipartFormData.getParameter("pk");
            
            if (StringUtils.isBlank(pk)) {
                logger.error("上传合同范本时PK为空");
                ServletUtils.outPrintFail(request, response, "上传合同范本时PK为空");
                return;
            }
            
            FileItem fileItem = multipartFormData.getFileItem();
            if (fileItem == null) {
                logger.error("无法获取上传文件，pk:{}", pk);
                ServletUtils.outPrintFail(request, response, "无法获取上传文件");
                return;
            }
            
            ContractTempUploadRule rule = new ContractTempUploadRule(fileItem.getName());
            Result result = FileUploadUtils.upload(fileItem.getInputStream(), rule.getFileName(),rule.getFilePath());
            
            if (!Result.SUCCESS_CODE.equals(result.getCode())) {
                logger.error("上传文件失败，pk:{}", pk);
                ServletUtils.outPrintFail(request, response, "上传文件失败");
                return;
            }
            
            doUpdateCmpCustType(Integer.parseInt(pk), rule.getFullName(), getUserContext(request).getCzybh());
            ServletUtils.outPrintSuccess(request, response, rule.getFullName());
            
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "上传文件失败");
        }
	}

	/**
	 *CmpCustType导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CmpCustType cmpCustType){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		cmpCustTypeService.findPageBySql(page, cmpCustType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"公司销售产品管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkExist")
	@ResponseBody
	public List<Map<String, Object>> checkExist(HttpServletRequest request,HttpServletResponse response,CmpCustType cmpCustType){
		List<Map<String, Object>>list=cmpCustTypeService.checkExist(cmpCustType);
		return list;
	}
	
	public void doUpdateCmpCustType(Integer pk,String fileName,String lastUpdatedBy){
		if(StringUtils.isNotBlank(fileName)){
			CmpCustType cmpCustType = cmpCustTypeService.get(CmpCustType.class, pk);
			cmpCustType.setContractTempName(fileName);
			cmpCustType.setLastUpdate(new Date());
			cmpCustType.setLastUpdatedBy(lastUpdatedBy);
			cmpCustTypeService.update(cmpCustType);
		}
	}
	
	public static String getContractTempPath(String fileName,String pk){
		String appl = "";
		System.out.println(appl);
		String contractTempName= SystemConfig.getProperty("contractTemp", "", "photo");
		if (StringUtils.isBlank(contractTempName)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return contractTempName;
		}else{
			return SystemConfig.getProperty("contractTemp", "", "photo");
		}
		
	}
	

	public static String getWfProcPicDownloadPath(HttpServletRequest request, String fileName,String pk){
		String path = getContractTempPath(fileName,pk);
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
}
