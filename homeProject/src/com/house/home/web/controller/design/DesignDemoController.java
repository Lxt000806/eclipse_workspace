package com.house.home.web.controller.design;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.DesignDemoPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileHelper;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.design.DesignDemoPic;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.DesignDemoPicService;
import com.house.home.service.design.DesignDemoService;
@Controller
@RequestMapping("/admin/designDemo")
public class DesignDemoController extends BaseController{
	
	@Autowired
	private  DesignDemoService designDemoService;
	@Autowired
	private DesignDemoPicService designDemoPicService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, DesignDemo designDemo) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=this.getUserContext(request);
		designDemoService.findPageBySql(page, designDemo,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/findDesignPic")
	@ResponseBody
	public WebPage<Map<String,Object>> findDesignPic(HttpServletRequest request,
			HttpServletResponse response, DesignDemo designDemo) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=this.getUserContext(request);
		designDemoService.findDesignPic(page, designDemo,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ,DesignDemo designDemo) throws Exception{
		
		return new ModelAndView("admin/design/designDemo/designDemo_list").addObject("designDemo",designDemo);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response ,DesignDemo designDemo) throws Exception{
		String url = FileUploadUtils.DOWNLOAD_URL+DesignDemoPicUploadRule.FIRST_LEVEL_PATH;
		String str = designDemoService.getSeqNo("tDesignDemo");
		designDemo.setNo(str);
		return new ModelAndView("admin/design/designDemo/designDemo_save")
		.addObject("designDemo",designDemo)
		.addObject("url",url);
	}
	
	@RequestMapping("/goAddPic")
	public ModelAndView goAddPic(HttpServletRequest request ,
			HttpServletResponse response ,DesignDemo designDemo) throws Exception{
		
		
		return new ModelAndView("admin/design/designDemo/designDemo_addPic")
		.addObject("designDemo",designDemo);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response ,DesignDemo designDemo) throws Exception{
		DesignDemo dd= null;
		Customer customer=null;
		Employee employee=null;
		Builder builder=null;
		String url = FileUploadUtils.DOWNLOAD_URL+DesignDemoPicUploadRule.FIRST_LEVEL_PATH;
		if(StringUtils.isNotBlank(designDemo.getNo())){
			dd=designDemoService.get(DesignDemo.class, designDemo.getNo());
			if(StringUtils.isNotBlank(dd.getCustCode())){
				customer=customerService.get(Customer.class, dd.getCustCode());
			}
			if(StringUtils.isNotBlank(dd.getDesignMan())){
				employee=customerService.get(Employee.class, dd.getDesignMan());
			}
			if(StringUtils.isNotBlank(dd.getBuilderCode())){
				builder=customerService.get(Builder.class,dd.getBuilderCode());
			}
		}
		return new ModelAndView("admin/design/designDemo/designDemo_update")
		.addObject("designDemo",dd)
		.addObject("customer",customer)
		.addObject("employee",employee)
		.addObject("builder",builder)
		.addObject("url",url);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response ,DesignDemo designDemo) throws Exception{
		DesignDemo dd= null;
		Customer customer=null;
		Employee employee=null;
		Builder builder=null;
		String url ="";
		if(StringUtils.isNotBlank(designDemo.getNo())){
			dd=designDemoService.get(DesignDemo.class, designDemo.getNo());
			url= getCustDocDownloadPath(request,"",dd.getCustCode());
			if(StringUtils.isNotBlank(dd.getCustCode())){
				customer=customerService.get(Customer.class, dd.getCustCode());
			}
			if(StringUtils.isNotBlank(dd.getDesignMan())){
				employee=customerService.get(Employee.class, dd.getDesignMan());
			}
			if(StringUtils.isNotBlank(dd.getBuilderCode())){
				builder=customerService.get(Builder.class,dd.getBuilderCode());
			}
		}
		return new ModelAndView("admin/design/designDemo/designDemo_view")
		.addObject("designDemo",dd)
		.addObject("customer",customer)
		.addObject("employee",employee)
		.addObject("builder",builder)
		.addObject("url",url);
	}
	
	@RequestMapping("/doDeleteDemo")
	@ResponseBody
	public boolean doDeleteDemo(HttpServletRequest request, HttpServletResponse response,
			String custCode,String photoName,String no){//D:/homePhoto/custDoc/CT002697/0000000001/1515225450156.png
		boolean i;
		try {
			String[] photoNames = photoName.split(",");
			for(int x=0;x<photoNames.length;x++){
				designDemoService.doDeleteDemo(no,custCode,photoNames[x]);
				FileUploadUtils.deleteFile(FileUploadUtils.getFileUrl(custCode+"/"+no+"/"+photoNames[x]));
			}
			i=true;
		} catch (Exception e) {
			i=false;
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		return i;
	}
	
	@RequestMapping("/doDeleteAllDemo")
	@ResponseBody
	public boolean doDeleteAllDemo(HttpServletRequest request, HttpServletResponse response,
			String custCode,String no){
		boolean result=false;
		try {
			designDemoService.doDeleteAllDemo(no);
			
			DesignDemoPicUploadRule rule = new DesignDemoPicUploadRule("", custCode, no);
			OssManager.deleteFile(rule.getFilePath());
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			result=false;
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		return result;
	}
	
	@RequestMapping("/doUpdateDemo")
	@ResponseBody
	public void doUpdateDemo(HttpServletRequest request, HttpServletResponse response,
			DesignDemo designDemo){
		try {
			DesignDemo dd=new DesignDemo();
			if(StringUtils.isNotBlank(designDemo.getNo())){
				dd=designDemoService.get(DesignDemo.class, designDemo.getNo());
				dd.setCustCode(designDemo.getCustCode());
				dd.setArea(designDemo.getArea());
				dd.setDesignMan(designDemo.getDesignMan());
				dd.setBuilderCode(designDemo.getBuilderCode());
				dd.setLayout(designDemo.getLayout());
				dd.setDesignSty(designDemo.getDesignSty());
				dd.setDesignRemark(designDemo.getDesignRemark());
				dd.setLastUpdate(new Date());
				dd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				dd.setActionLog("Edit");
				dd.setIsPushCust(designDemo.getIsPushCust());
				dd.setAmount(designDemo.getAmount());
				dd.setExpired(designDemo.getExpired());
				designDemoService.update(dd);
				
			}
			ServletUtils.outPrintSuccess(request, response,"编辑成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}

	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes", "null" })
	@RequestMapping("/uploadDesignDemoPic")
	public void uploadDesignDemoPic(HttpServletRequest request,
			HttpServletResponse response) {
		UploadPhotoResp respon = new UploadPhotoResp();
		DesignDemo designDemo =new DesignDemo();
		try {
			Result uploadResult = null;
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String custCode = multipartFormData.getParameter("custCode");
			String no = multipartFormData.getParameter("no");
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			List<String> photoPathList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
        		DesignDemoPicUploadRule rule = new DesignDemoPicUploadRule(attatchment.getName(), custCode, no);
        		
        		//超过300kb进行压缩
        		if (attatchment.getSize()/1024.0 > 300.0) {
        			//Tomcat临时目录
        			String tempFilePath = System.getProperty("catalina.home").replaceAll("\\\\", "/")
        					+"/temp/photo/"; 
        			File file = new File(tempFilePath);
        			if(!file.exists()){
        				file.mkdirs();
        			}
        			ServletUtils.compressImage(attatchment.getInputStream(),tempFilePath+attatchment.getName(), 300, 1.0f);
        			FileInputStream compressedFileInputStream = new FileInputStream(tempFilePath+attatchment.getName());
        			uploadResult = FileUploadUtils.upload(compressedFileInputStream, 
        					rule.getFileName(),rule.getFilePath());
        			FileHelper.delFile(tempFilePath);
				}else {
					uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
        					rule.getFileName(),rule.getFilePath());
				}
        		
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
               
                //保存资料信息
		        doSavePicDetail(no, rule.getFileName(), this.getUserContext(request).getCzybh());
				
                fileNameList.add(rule.getFileName());
                photoPathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
            }
			
			respon.setPhotoPathList(photoPathList);
			respon.setPhotoNameList(photoPathList);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "上传失败");
		}
	}
	
	@RequestMapping("/doSaveDesignDemo")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, String no ){
		logger.debug("保存设计案例");
		try{
			DesignDemo designDemo=null;
			String custCode=request.getParameter("custCode"),
				    designMan=request.getParameter("designMan"),
					builderCode=request.getParameter("builderCode"),
					area=request.getParameter("area"),
					designSty=request.getParameter("designSty"),
					layout=request.getParameter("layout"),
					designRemark=request.getParameter("designRemark"),
					isPushCust=request.getParameter("isPushCust"),
					amount=request.getParameter("amount"),
					custName=request.getParameter("custName");
			designDemo=designDemoService.get(DesignDemo.class, no);
	        if(designDemo==null){
	        	designDemo=new DesignDemo();
	        	designDemo.setNo(no);
	        	designDemo.setCustCode(custCode);
	        	designDemo.setLastUpdate(new Date());
	        	designDemo.setLastUpdatedBy(this.getUserContext(request).getCzybh());
	        	designDemo.setExpired("F");
	        	designDemo.setActionLog("Add");
	        	designDemo.setIsPushCust(isPushCust);
	        	designDemo.setDesignMan(designMan);
	        	designDemo.setCustName(custName);
	        	designDemo.setBuilderCode(builderCode);
	        	designDemo.setArea(area==null||area==""?0:Integer.parseInt(area));
	        	designDemo.setAmount(StringUtils.isBlank(amount)?0d:Double.parseDouble(amount));
	        	designDemo.setLayout(layout);
	        	designDemo.setDesignSty(designSty);
	        	designDemo.setDesignRemark(designRemark);

	        	this.designDemoService.save(designDemo);
				ServletUtils.outPrintSuccess(request, response);
	        }
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存设计案例失败");
		}
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response,String photoNameArr,
			String designDemoDescr,String custCode,String no){
		try{ 
			String tempUserName = request.getParameter("designDemoDescr");
			tempUserName=java.net.URLDecoder.decode(tempUserName,"UTF-8");
			//String url = SystemConfig.getProperty("designDemoPic", "", "client");
			String address="";
			if(StringUtils.isNotBlank(custCode)){
				address=customerService.get(Customer.class, custCode).getAddress();
			}else {
				address = "客户";
			}
			String zip = address+"_"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss")+".zip";
			String[] docName = photoNameArr.split(",");
			StringBuilder[] files = null;
				files = new StringBuilder[docName.length];
				for(int i=0;i<docName.length;i++){
					files[i] = new StringBuilder();
					if(StringUtils.isNotBlank(custCode)){
						files[i].append(FileUploadUtils.DOWNLOAD_URL+"designDemoPic/"+custCode+"/"+no.trim()+"/"+ docName[i]);
					}else {
						files[i].append(FileUploadUtils.DOWNLOAD_URL+"designDemoPic/"+no.trim()+"/"+docName[i]);
					}
				}
			ServletUtils.downLoadFiles(request,response,files,zip,true);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doSavePicDetail(String no,String photoName,String lastUpdatedBy){
		
		DesignDemoPic designDemoPic = new DesignDemoPic();
		designDemoPic.setNo(no);
		designDemoPic.setPhotoName(photoName);
		designDemoPic.setActionLog("Add");
		designDemoPic.setExpired("F");
		designDemoPic.setLastUpdate(new Date());
		designDemoPic.setLastUpdatedBy(lastUpdatedBy);
		designDemoPic.setIsSendYun("1");
		designDemoPic.setSendDate(new Date());
		
		designDemoPicService.save(designDemoPic);
	}
	
	public static String getCustDocPath(String fileName,String fileCustCode){
		String custDocNameNew = SystemConfig.getProperty("designDemoPic", "", "photo");
		System.out.println(custDocNameNew);
		if (StringUtils.isBlank(custDocNameNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			if(StringUtils.isNotBlank(fileCustCode)){
				return custDocNameNew + fileCustCode + "/";
			}else {
				return custDocNameNew;
			}
		}else{
			return SystemConfig.getProperty("designDemoPic", "", "photo");
		}
	}
	
	public static String getCustDocDownloadPath(HttpServletRequest request, String fileName,String fileCustCode){
		String path = getCustDocPath(fileName,fileCustCode);
		System.out.println(path);
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			DesignDemo designDemo){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		designDemoService.findPageBySql(page,designDemo,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"设计案例表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
	
}
