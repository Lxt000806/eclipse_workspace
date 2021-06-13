package com.house.home.web.controller.project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.components.barbecue.BarcodeProviders.NW7Provider;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.InfoAttachUploadRule;
import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.project.JobType;
import com.house.home.entity.project.PrjJob;
import com.house.home.entity.project.PrjJobPhoto;
import com.house.home.entity.project.SupplJob;
import com.house.home.service.project.PrjJobPhotoService;
import com.house.home.service.project.PrjJobService;
import com.house.home.service.project.SupplJobService;
@Controller
@RequestMapping("/admin/prjJobManage")
public class PrjJobManageController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CustItemConfProgDtController.class);
	@Autowired
	private PrjJobService prjJobService;
	@Autowired
	private PrjJobPhotoService prjJobPhotoService;
	@Autowired
	private SupplJobService supplJobService;
	
	@RequestMapping(value = "/prjTypeByItemType1/{type}/{pCode}") //获取任务类型
	@ResponseBody
	public JSONObject getPrjTypeByitemType1(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Map<String,Object>> regionList = this.prjJobService.findPrjTypeByItemType1(type,pCode,uc);
		return this.out(regionList, true);
	}
	
	@RequestMapping(value = "/prjTypeByItemType1Auth/{type}/{pCode}") //获取任务类型
	@ResponseBody
	public JSONObject getPrjTypeByitemType1Auth(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Map<String,Object>> regionList = this.prjJobService.findPrjTypeByItemType1Auth(type,pCode,uc);
		return this.out(regionList, true);
	}
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
			HttpServletResponse response, PrjJob prjJob) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=this.getUserContext(request);
		prjJobService.findManagePageBySql(page, prjJob, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSupplJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSupplJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjJob prjJob) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=this.getUserContext(request);
		prjJobService.findSupplPageBySql(page, prjJob);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goSupplListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSupplListJqGrid(HttpServletRequest request,
			HttpServletResponse response,  SupplJob supplJob) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=this.getUserContext(request);
		prjJobService.findSupplListPageBySql(page, supplJob,this.getUserContext(request).getItemRight());
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * prjJobManage列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		return new ModelAndView("admin/project/prjJobManage/prjJobManage_list").addObject("uc", uc);
	}
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goOperation")
	public ModelAndView goOperation(HttpServletRequest request,
			HttpServletResponse response, PrjJob prjJob) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String m_umState=prjJob.getM_umState();
		UserContext uc=this.getUserContext(request);
		prjJobService.findManagePageBySql(page, prjJob, uc);
		List<PrjJob> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), PrjJob.class);
		prjJob=listBean.get(0);
		prjJob.setM_umState(m_umState);
		if("W".equals(prjJob.getM_umState())){
			prjJob.setDealDate(new Date());
		}
		return new ModelAndView("admin/project/prjJobManage/prjJobManage_operation").addObject("prjJob", prjJob)
				.addObject("uc", uc);
	}
	
	@RequestMapping("/goAddSuppl")
	public ModelAndView goAddSuppl(HttpServletRequest request,
			HttpServletResponse response, String no,String jobType) throws Exception {
		SupplJob supplJob=new SupplJob();
		PrjJob prjJob=new PrjJob();
		prjJob=prjJobService.get(PrjJob.class, no);
		supplJob.setAppCzy(this.getUserContext(request).getCzybh());
		supplJob.setAppDescr(this.getUserContext(request).getZwxm());
		supplJob.setDate(new Date());
		prjJob.setJobType(jobType);
		return new ModelAndView("admin/project/prjJobManage/prjJobManage_addSuppl").addObject("prjJob", prjJob)
				.addObject("supplJob", supplJob);
	}
	
	@RequestMapping("/goUpdateSuppl")
	public ModelAndView goUpdateSuppl(HttpServletRequest request,
			HttpServletResponse response, Integer pk) throws Exception {
		SupplJob supplJob=new SupplJob();
		Employee employee=new Employee();
		Supplier supplier =new Supplier();
		if(pk!=null){
			supplJob=supplJobService.get(SupplJob.class, pk);
		}
		if(StringUtils.isNotBlank(supplJob.getAppCzy())){
			employee=supplJobService.get(Employee.class, supplJob.getAppCzy());
			supplJob.setAppDescr(employee==null?"":employee.getNameChi());
		}
		if(StringUtils.isNotBlank(supplJob.getSupplCode())){
			supplier=supplJobService.get(Supplier.class, supplJob.getSupplCode());
			supplJob.setSupplDescr(supplier==null?"":supplier.getDescr());
		}
		
		return new ModelAndView("admin/project/prjJobManage/prjJobManage_updateSuppl").addObject("supplJob", supplJob);
	}
	
	@RequestMapping("/goViewSuppl")
	public ModelAndView goViewSuppl(HttpServletRequest request,
			HttpServletResponse response, Integer pk) throws Exception {
		SupplJob supplJob=new SupplJob();
		Employee employee=new Employee();
		Supplier supplier =new Supplier();
		if(pk!=null){
			supplJob=supplJobService.get(SupplJob.class, pk);
		}
		if(StringUtils.isNotBlank(supplJob.getAppCzy())){
			employee=supplJobService.get(Employee.class, supplJob.getAppCzy());
			supplJob.setAppDescr(employee==null?"":employee.getNameChi());
		}
		if(StringUtils.isNotBlank(supplJob.getSupplCode())){
			supplier=supplJobService.get(Supplier.class, supplJob.getSupplCode());
			supplJob.setSupplDescr(supplier==null?"":supplier.getDescr());
		}
		
		return new ModelAndView("admin/project/prjJobManage/prjJobManage_viewSuppl").addObject("supplJob", supplJob);
	}
	
	@RequestMapping("/goSupplJobList")
	public ModelAndView goSupplJobList(HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		return new ModelAndView("admin/project/prjJobManage/prjJobManage_supplJobList").addObject("uc", this.getUserContext(request));
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param prjJob
	 * @return
	 */
	@RequestMapping("/goPrjJobPhotoListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPrjJobPhotoListJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjJobPhoto prjJobPhoto){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjJobPhotoService.findPageBySql(page, prjJobPhoto);
		return new WebPage<Map<String,Object>>(page);		
	}
	/**
	 *任务管理 接收 指派 完成，取消 
	 * @param request
	 * @param response 
	 */
	@RequestMapping("/doOperation")
	public void doOperation(HttpServletRequest request, 
			HttpServletResponse response, PrjJob prjJob){
		UserContext uc=this.getUserContext(request);
		prjJob.setLastUpdatedBy(uc.getCzybh());
		Result result= prjJobService.doPrjJobForProc(prjJob);
		if("1".equals(result.getCode())) ServletUtils.outPrintSuccess(request, response, result.getInfo());
		else ServletUtils.outPrintFail(request, response,result.getInfo());
	}
	
	@RequestMapping("/doSaveSuppl")
	public void doSaveSuppl(HttpServletRequest request, 
			HttpServletResponse response ,SupplJob supplJob){
		logger.debug("任务管理——安排供应商");
		try{
			PrjJob prjJob=new PrjJob();
			prjJob=prjJobService.get(PrjJob.class, supplJob.getPrjJobNo());
			supplJob.setExpired("F");
			supplJob.setActionLog("ADD");
			supplJob.setLastUpdate(new Date());
			supplJob.setStatus("0");
			supplJob.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.supplJobService.save(supplJob);
			if(!"3".equals(prjJob.getStatus())){
				prjJob.setStatus("3");
				prjJob.setLastUpdate(new Date());
				prjJob.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				prjJobService.update(prjJob);
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "指派供应商失败");
		}
	}
	
	@RequestMapping("/doUpdateSuppl")
	public void doUpdateSuppl(HttpServletRequest request, 
		HttpServletResponse response ,SupplJob supplJob){
		logger.debug("任务管理——修改供应商");
		try{
			SupplJob sj=new SupplJob();
			sj=prjJobService.get(SupplJob.class, supplJob.getPk());
			sj.setRemarks(supplJob.getRemarks());
			sj.setLastUpdate(new Date());
			sj.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			sj.setActionLog("EDIT");
			this.supplJobService.update(sj);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改指派供应商失败");
		}
	}
	
	@RequestMapping("/doDelSuppl")
	public void doDelSuppl(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("删除供应商任务");
		try{
			SupplJob supplJob =new SupplJob();
			PrjJob prjJob = new PrjJob();
			supplJob=supplJobService.get(SupplJob.class, pk);
			if(StringUtils.isNotBlank(supplJob.getPrjJobNo())){
				prjJob=prjJobService.get(PrjJob.class, supplJob.getPrjJobNo());
			}
			this.supplJobService.doDelSuppl(pk);
			if(!hasSupplJob(supplJob.getPrjJobNo())){
				if(prjJob!=null && StringUtils.isNotEmpty(prjJob.getNo())){
					prjJob.setStatus("2");
					prjJob.setLastUpdate(new Date());
					prjJob.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					prjJobService.update(prjJob);
				}
			}
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	public boolean hasSupplJob (String prjJobNo){
		return prjJobService.hasSupplJob(prjJobNo);//存在true 不存在 false
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjJob prjJob){
		logger.debug("更新prjJob开始");
		try{
			String dealRemark=prjJob.getDealRemark();
			prjJob=prjJobService.get(PrjJob.class, prjJob.getNo());
			prjJob.setDealRemark(dealRemark);
			prjJob.setLastUpdate(new Date());
			prjJob.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjJob.setActionLog("EDIT");
			this.prjJobService.update(prjJob);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "更新prjJob失败");
		}
	}
	
	/**
	 *prjjob导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PrjJob prjJob){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		prjJobService.findManagePageBySql(page, prjJob, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"任务管理"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doSupplListExcel")
	public void doSupplListExcel(HttpServletRequest request, 
			HttpServletResponse response, SupplJob supplJob){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjJobService.findSupplListPageBySql(page, supplJob,this.getUserContext(request).getItemRight());
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"供应商任务"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		String downLoadPath=FileUploadUtils.DOWNLOAD_URL+PrjProgNewUploadRule.FIRST_LEVEL_PATH;
		return new ModelAndView("admin/project/prjJobManage/prjJobManage_save")
		.addObject("url",downLoadPath);
	}
	
	@RequestMapping("/goAddPic")
	public ModelAndView goAddPic(HttpServletRequest request ,
			HttpServletResponse response,String no ) throws Exception{
		
		return new ModelAndView("admin/project/prjJobManage/prjJobManage_addPic").addObject("no", no);
	}
	
	@RequestMapping("/doDeletePic")
	@ResponseBody
	public boolean doDeleteDemo(HttpServletRequest request, HttpServletResponse response,String photoName){//D:/homePhoto/custDoc/CT002697/0000000001/1515225450156.png
		boolean i;
		try {
			String[] photoNames = photoName.split(",");
			for(int x=0;x<photoNames.length;x++){
				prjJobPhotoService.deleteByPrjJobNo("", photoNames[x]);
				FileUploadUtils.deleteFile(photoNames[x]);
			}
			i=true;
		} catch (Exception e) {
			i=false;
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		return i;
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadPrjJobPic")
	public void uploadPrjJobPic(HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String prjJobNo = multipartFormData.getParameter("no");
			String fileNameNew = "";
			String firstFileName = "";
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
            	firstFileName = attatchment.getName().substring(
            			attatchment.getName().lastIndexOf("\\") + 1);
				String formatName = firstFileName
						.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
				fileNameNew = System.currentTimeMillis()+getUserContext(request).getCzybh().trim()+formatName;
				
                PrjProgNewUploadRule progNewUploadRule =
                        new PrjProgNewUploadRule(fileNameNew, fileNameNew.substring(0,5));
                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
                        progNewUploadRule.getFileName(),progNewUploadRule.getFilePath());
                fileNameList.add(fileNameNew);
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
            }
			if (StringUtils.isNotBlank(prjJobNo)){
				if (fileNameList!=null && fileNameList.size()>0){
					for (String str : fileNameList){
						PrjJobPhoto photo = new PrjJobPhoto();
						photo.setPhotoName(str);
						photo.setLastUpdate(new Date());
						photo.setLastUpdatedBy(getUserContext(request).getCzybh());
						photo.setActionLog("ADD");
						photo.setExpired("F");
						photo.setType("2");
						photo.setPrjJobNo(prjJobNo);
						photo.setIsSendYun("1");
						photo.setSendDate(new Date());
						prjJobPhotoService.save(photo);
					}
				}
			}
			
			ServletUtils.outPrintSuccess(request, response,fileNameNew);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "上传失败");
		}
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 */
/*	@SuppressWarnings({ "unused", "rawtypes", "null" })
	@RequestMapping("/uploadPrjJobPic")
	public void uploadPrjJobPic(HttpServletRequest request,
			HttpServletResponse response) {
		UploadPhotoResp respon = new UploadPhotoResp();
		DesignDemo designDemo =new DesignDemo();
		try {
			String fileRealPath = "";//文件存放真实地址 
			String firstFileName = ""; 
			String fileNameNew="";
			String photoPath = "";
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 设置允许上传的最大文件大小 500k
			//upload.setSizeMax(500 * 1024);
			// 获取多个上传文件
			List fileList = upload.parseRequest(request);
			Iterator it = fileList.iterator();
			String lastUpdatedBy="",prjJobNo="";
			//int fileSize=Integer.parseInt(request.getParameter("allowDocSize"));
			List<String> fileRealPathList = new ArrayList<String>();
			List<String> PhotoPathList = new ArrayList<String>();
			List<String> fileNameList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				// 如果是普通 表单参数
				if (obit.isFormField()) { //普通域,获取页面参数
					String fieldName = obit.getFieldName();
					String fieldValue = obit.getString();
					if ("no".equals(fieldName)){
						prjJobNo = fieldValue;
					}
				}
				// 如果是多媒体
				if (obit instanceof DiskFileItem) {
					DiskFileItem item = (DiskFileItem) obit;
					// 如果item是文件上传表单域
					// 获得文件名及路径
					String fileName = item.getName();
					if (fileName != null) {
						firstFileName = item.getName().substring(
								item.getName().lastIndexOf("\\") + 1);
						String formatName = firstFileName
								.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
						fileNameNew = System.currentTimeMillis()+getUserContext(request).getCzybh().trim()+formatName;
						String filePath = getPrjProgPhotoUploadPath(fileNameNew);
						FileUploadServerMgr.makeDir(filePath);
						fileRealPath = filePath + fileNameNew;// 文件存放真实地址
						photoPath = getPrjProgPhotoDownloadPath(request,fileNameNew) + fileNameNew;
						BufferedInputStream in = new BufferedInputStream(
								item.getInputStream());// 获得文件输入流
						BufferedOutputStream outStream = new BufferedOutputStream(
								new FileOutputStream(new File(fileRealPath)));// 获得文件输出流
						Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹
						in.close();
						outStream.close();
						fileRealPathList.add(photoPath);
						fileNameList.add(fileNameNew);
					}
				}
			}
			if (StringUtils.isNotBlank(prjJobNo)){
				if (fileNameList!=null && fileNameList.size()>0){
					for (String str : fileNameList){
						PrjJobPhoto photo = new PrjJobPhoto();
						photo.setPhotoName(str);
						photo.setLastUpdate(new Date());
						if (StringUtils.isNotBlank(lastUpdatedBy)){//delphi调用接口用
							photo.setLastUpdatedBy(lastUpdatedBy);
						}else{
							photo.setLastUpdatedBy(getUserContext(request).getCzybh());
						}
						photo.setActionLog("ADD");
						photo.setExpired("F");
						photo.setType("2");
						photo.setPrjJobNo(prjJobNo);
						prjJobPhotoService.save(photo);
					}
				}
			}
			respon.setPhotoPathList(PhotoPathList);
			respon.setPhotoNameList(PhotoPathList);
			ServletUtils.outPrintSuccess(request, response,fileNameNew);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "上传失败");
		}
	}*/
	
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param no
	 * @param m_umState
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,PrjJob prjJob){
		try{
			JobType jobType = prjJobService.get(JobType.class, prjJob.getJobType());
			Customer customer = prjJobService.get(Customer.class, prjJob.getCustCode());
			if ("0".equals(jobType.getCanEndCust()) && "5".equals(customer.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "完工工地不允许保存");
				return;
			}
			if(prjJobService.existPrjJob(prjJob.getCustCode(), prjJob.getJobType(), "2,3") != null){
				ServletUtils.outPrintFail(request, response, "存在提交、指派的任务单,无法重复提交");
				return;
			}
			String empCode = prjJobService.getDefaultDealMan(prjJob.getCustCode(),jobType.getRole(),jobType.getCode());
			if (StringUtils.isNotBlank(empCode)){
				prjJob.setDealCzy(empCode);
			}
			if("1".equals(jobType.getIsNeedReq())){
				if(!prjJobService.isNeedReq(prjJob.getCustCode(),prjJob.getItemType1())){
					ServletUtils.outPrintFail(request, response, "该工地没有软装预算,不允许申请");
					return;
				}
			}
			prjJob.setAppCzy(getUserContext(request).getCzybh());
			prjJob.setFromInfoAdd("0");
			if (!prjJobService.addPrjJob(prjJob)){
				ServletUtils.outPrintFail(request, response, "新增项目任务失败");
				return;
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "点击失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * prjJobManage列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getIsNeedPic")
	@ResponseBody
	public String getIsNeedPic(HttpServletRequest request,
			HttpServletResponse response,String jobType) throws Exception {
		JobType type = prjJobService.get(JobType.class,jobType);
		return type.getIsNeedPic();
	}
}
