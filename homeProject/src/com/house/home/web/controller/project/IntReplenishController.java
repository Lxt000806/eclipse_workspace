package com.house.home.web.controller.project;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.IntReplenishDocUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.IntReplenish;
import com.house.home.entity.project.IntReplenishDT;
import com.house.home.entity.project.Worker;
import com.house.home.service.project.IntReplenishService;

@Controller
@RequestMapping("/admin/intReplenish")
public class IntReplenishController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IntReplenishController.class);

	@Autowired
	private IntReplenishService intReplenishService;

	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, IntReplenish intReplenish) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intReplenishService.findPageBySql(page, intReplenish);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ?????????????????????
	 * @author	created by zb
	 * @date	2019-12-9--??????4:41:00
	 * @param request
	 * @param response
	 * @param intReplenishDT
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHadJqGridByNo")
	@ResponseBody
	public WebPage<Map<String,Object>> getHadJqGridByNo(HttpServletRequest request,
			HttpServletResponse response, IntReplenish intReplenish) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intReplenishService.findHadPageByNo(page, intReplenish);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * IntReplenish??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/intReplenish/intReplenish_list");
	}
	/**
	 * ???????????????intReplenish??????
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response){
		logger.debug("?????????????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_add");
	}
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2019-11-15--??????3:12:57
	 * @param request
	 * @param response
	 * @param no
	 * @param m_umState
	 * @return
	 */
	@RequestMapping("/goInput")
	public ModelAndView goInput(HttpServletRequest request, HttpServletResponse response, 
			String no, String m_umState){
		logger.debug("?????????????????????????????????");
		IntReplenish intReplenish = this.intReplenishService.get(IntReplenish.class, no);
		if (StringUtils.isNotBlank(intReplenish.getCustCode())) {
			Customer customer = this.intReplenishService.get(Customer.class, intReplenish.getCustCode());
			intReplenish.setCustDescr(customer.getDescr());
			intReplenish.setAddress(customer.getAddress());
		}
		intReplenish.setM_umState(m_umState);
		return new ModelAndView("admin/project/intReplenish/intReplenish_input")
			.addObject("intReplenish", intReplenish);
	}
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-15--??????3:13:21
	 * @param request
	 * @param response
	 * @param intReplenishDT
	 * @return
	 */
	@RequestMapping("/goItem")
	public ModelAndView goItem(HttpServletRequest request, HttpServletResponse response, 
			IntReplenishDT intReplenishDT){
		logger.debug("?????????????????????????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_item")
			.addObject("intReplenishDT", intReplenishDT);
	}
	/**
	 * CAD??????
	 * @author	created by zb
	 * @date	2019-11-20--??????4:42:04
	 * @param request
	 * @param response
	 * @param intReplenishDT
	 * @return
	 */
	@RequestMapping("/goCAD")
	public ModelAndView goCAD(HttpServletRequest request, HttpServletResponse response, 
			IntReplenishDT intReplenishDT){
		logger.debug("?????????CAD????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_CAD")
			.addObject("intReplenishDT", intReplenishDT);
	}
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-20--??????4:42:42
	 * @param request
	 * @param response
	 * @param no
	 * @param m_umState
	 * @return
	 */
	@RequestMapping("/goSign")
	public ModelAndView goSign(HttpServletRequest request, HttpServletResponse response, 
			String no, String m_umState){
		logger.debug("???????????????????????????");
		IntReplenish intReplenish = this.intReplenishService.get(IntReplenish.class, no);
		if (StringUtils.isNotBlank(intReplenish.getCustCode())) {
			Customer customer = this.intReplenishService.get(Customer.class, intReplenish.getCustCode());
			intReplenish.setCustDescr(customer.getDescr());
			intReplenish.setAddress(customer.getAddress());
		}
		intReplenish.setM_umState(m_umState);
		return new ModelAndView("admin/project/intReplenish/intReplenish_sign")
			.addObject("intReplenish", intReplenish);
	}
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2019-11-21--??????10:23:11
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goArrive")
	public ModelAndView goArrive(HttpServletRequest request, HttpServletResponse response){
		logger.debug("???????????????????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_arrive");
	}
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-21--??????11:59:13
	 * @param request
	 * @param response
	 * @param intReplenishDT
	 * @return
	 */
	@RequestMapping("/goArriveItem")
	public ModelAndView goArriveItem(HttpServletRequest request, HttpServletResponse response, 
			IntReplenishDT intReplenishDT){
		logger.debug("???????????????????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_arriveItem")
			.addObject("intReplenishDT", intReplenishDT);
	}
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-21--??????3:45:30
	 * @param request
	 * @param response
	 * @param no
	 * @param m_umState
	 * @return
	 */
	@RequestMapping("/goArrange")
	public ModelAndView goArrange(HttpServletRequest request, HttpServletResponse response, 
			String no, String m_umState){
		logger.debug("?????????????????????????????????");
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		IntReplenish intReplenish = this.intReplenishService.goArrange(page, no);
		intReplenish.setM_umState(m_umState);
		String hadSameCustIntReplenishTip = this.intReplenishService.getIsHad(intReplenish);
		String isHad = "0";
		if(hadSameCustIntReplenishTip.length()>0){
			isHad = "1";
		}
		return new ModelAndView("admin/project/intReplenish/intReplenish_arrange")
			.addObject("intReplenish", intReplenish)
			.addObject("isHad",isHad)
			.addObject("hadSameCustIntReplenishTip", hadSameCustIntReplenishTip);
	}
	
	/**
	 * ??????????????????
	 * @author	created by cjm
	 * @date	2020???9???2???11:24:14
	 * @param request
	 * @param response
	 * @param no
	 * @param m_umState
	 * @return
	 */
	@RequestMapping("/goFixDuty")
	public ModelAndView goFixDuty(HttpServletRequest request, HttpServletResponse response, 
			String no, String m_umState){
		logger.debug("?????????????????????");
		IntReplenish intReplenish = this.intReplenishService.get(IntReplenish.class, no);
		if (StringUtils.isNotBlank(intReplenish.getCustCode())) {
			Customer customer = this.intReplenishService.get(Customer.class, intReplenish.getCustCode());
			intReplenish.setCustDescr(customer.getDescr());
			intReplenish.setAddress(customer.getAddress());
		}
		intReplenish.setM_umState(m_umState);
		return new ModelAndView("admin/project/intReplenish/intReplenish_fixDuty")
			.addObject("intReplenish", intReplenish);
	}
	@RequestMapping("/goHadDetail")
	public ModelAndView goHadDetail(HttpServletRequest request, HttpServletResponse response, 
			String no){
		logger.debug("????????????????????????????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_hadDetail")
			.addObject("no", no);
	}
	/**
	 * ?????????????????????
	 * @author	created by zb
	 * @date	2020-3-17--??????4:32:10
	 * @param request
	 * @param response
	 * @param intReplenish
	 * @return
	 */
	@RequestMapping("/goFinish")
	public ModelAndView goFinish(HttpServletRequest request, HttpServletResponse response, 
			IntReplenish intReplenish){
		logger.debug("?????????????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_modifyStatus")
			.addObject("intReplenish", intReplenish);
	}
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-21--??????3:13:38
	 * @param request
	 * @param response
	 * @param intReplenishDT
	 * @return
	 */
	@RequestMapping("/goModifyStatus")
	public ModelAndView goModifyStatus(HttpServletRequest request, HttpServletResponse response, 
			IntReplenish intReplenish){
		logger.debug("???????????????????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_modifyStatus")
			.addObject("intReplenish", intReplenish);
	}
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-22--??????2:36:42
	 * @param request
	 * @param response
	 * @param no
	 * @param m_umState
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String no, String m_umState){
		logger.debug("?????????????????????");
		IntReplenish intReplenish = this.intReplenishService.get(IntReplenish.class, no);
		if (StringUtils.isNotBlank(intReplenish.getCustCode())) {
			Customer customer = this.intReplenishService.get(Customer.class, intReplenish.getCustCode());
			intReplenish.setCustDescr(customer.getDescr());
			intReplenish.setAddress(customer.getAddress());
		}
		if (StringUtils.isNotBlank(intReplenish.getServiceMan())&&StringUtils.isNotBlank(intReplenish.getServiceManType())) {
			if("1".equals(intReplenish.getServiceManType())){
				Worker Worker=this.intReplenishService.get(Worker.class, intReplenish.getServiceMan());
				intReplenish.setServiceManDescr(Worker.getNameChi());
			}else{
				Employee employee = this.intReplenishService.get(Employee.class, intReplenish.getServiceMan());
				intReplenish.setServiceManDescr(employee.getNameChi());
			}	
		}
		intReplenish.setM_umState(m_umState);
		return new ModelAndView("admin/project/intReplenish/intReplenish_view")
			.addObject("intReplenish", intReplenish);
	}
	/**
	 * ???????????????????????????
	 * @author	created by zb
	 * @date	2019-11-22--??????3:35:49
	 * @param request
	 * @param response
	 * @param intReplenish
	 * @return
	 */
	@RequestMapping("/goDetailList")
	public ModelAndView goDetailList(HttpServletRequest request, HttpServletResponse response, 
			IntReplenish intReplenish){
		logger.debug("???????????????????????????");
		return new ModelAndView("admin/project/intReplenish/intReplenish_detailList")
			.addObject("intReplenish", intReplenish);
	}
	/**
	 * ??????intReplenish
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doAdd")
	public void doAdd(HttpServletRequest request, HttpServletResponse response, IntReplenish intReplenish){
		logger.debug("??????intReplenish??????");
		try{
			String no = intReplenishService.getSeqNo("tIntReplenish");
			intReplenish.setNo(no);
			intReplenish.setStatus("1");
			intReplenish.setLastUpdate(new Date());
			intReplenish.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			intReplenish.setActionLog("ADD");
			intReplenish.setExpired("F");
			intReplenish.setAppCzy(this.getUserContext(request).getCzybh());
			intReplenish.setDate(new Date());
			this.intReplenishService.save(intReplenish);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			System.out.println(e);
			ServletUtils.outPrintFail(request, response, "??????intReplenish??????");
		}
	}
	/**
	 * ????????????????????????
	 * @author	created by zb
	 * @date	2019-11-20--??????2:43:25
	 * @param request
	 * @param response
	 * @param intReplenish
	 */
	@RequestMapping("/doInput")
	public void doInput(HttpServletRequest request,HttpServletResponse response,IntReplenish intReplenish){
		logger.debug("????????????????????????");
		try {
			intReplenish.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			/*??????????????????????????????*/
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "???????????????");
				return;
			}
			/*??????????????????*/
			Result result = this.intReplenishService.doSave(intReplenish);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2019-11-21--??????9:20:21
	 * @param request
	 * @param response
	 * @param intReplenish
	 */
	@RequestMapping("/doSign")
	public void doSign(HttpServletRequest request,HttpServletResponse response,IntReplenish intReplenish){
		logger.debug("??????????????????");
		try {
//			IntReplenish ir = this.intReplenishService.get(IntReplenish.class, intReplenish.getNo());
//			ir.setLastUpdatedBy(this.getUserContext(request).getCzybh());
//			ir.setLastUpdate(new Date());
//			ir.setActionLog("Edit");
//			ir.setBoardOKDate(intReplenish.getBoardOKDate());
//			ir.setBoardOKRemarks(intReplenish.getBoardOKRemarks());
			intReplenishService.doSign(intReplenish);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-21--??????2:26:47
	 * @param request
	 * @param response
	 * @param intReplenishDT
	 */
	@RequestMapping("/doArriveSave")
	public void doArriveSave(HttpServletRequest request,HttpServletResponse response,
			IntReplenishDT intReplenishDT){
		logger.debug("????????????");
		try {
			if (null == intReplenishDT.getPk()) {
				ServletUtils.outPrintFail(request, response, "??????????????????");
			}
			IntReplenishDT irDt = this.intReplenishService.get(IntReplenishDT.class, intReplenishDT.getPk());
			irDt.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			irDt.setLastUpdate(new Date());
			irDt.setActionLog("Edit");
			irDt.setArriveDate(intReplenishDT.getArriveDate());
			irDt.setArriveRemarks(intReplenishDT.getArriveRemarks());
			this.intReplenishService.update(irDt);
			//?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
			this.intReplenishService.updateReadyDate(intReplenishDT.getPk());
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	/**
	 * ?????????????????????????????????
	 * @author	created by zb
	 * @date	2019-11-21--??????3:26:42
	 * @param request
	 * @param response
	 * @param intReplenish
	 */
	@RequestMapping("/doModifyStatus")
	public void doModifyStatus(HttpServletRequest request,HttpServletResponse response,
			IntReplenish intReplenish){
		logger.debug("????????????");
		try {
			if (null == intReplenish.getNo()) {
				ServletUtils.outPrintFail(request, response, "??????????????????");
			}
			IntReplenish ir = this.intReplenishService.get(IntReplenish.class, intReplenish.getNo());
			ir.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ir.setLastUpdate(new Date());
			ir.setActionLog("Edit");
			ir.setStatus(intReplenish.getStatus());
			if ("finish".equals(intReplenish.getM_umState())) {
				ir.setFinishDate(intReplenish.getFinishDate());
			}
			this.intReplenishService.update(ir);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2019-11-22--??????10:40:45
	 * @param request
	 * @param response
	 * @param intReplenish
	 */
	@RequestMapping("/doArrange")
	public void doArrange(HttpServletRequest request,HttpServletResponse response,
			IntReplenish intReplenish){
		logger.debug("??????????????????");
		try {
			if (null == intReplenish.getNo()) {
				ServletUtils.outPrintFail(request, response, "????????????????????????");
			}
			IntReplenish ir = this.intReplenishService.get(IntReplenish.class, intReplenish.getNo());
			ir.setStatus("4");
			ir.setServiceDate(intReplenish.getServiceDate());
			ir.setServiceManType(intReplenish.getServiceManType());
			ir.setServiceMan(intReplenish.getServiceMan());
			ir.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ir.setLastUpdate(new Date());
			ir.setActionLog("Edit");
			this.intReplenishService.update(ir);
			intReplenishService.updateArrivedIntReplenish(ir);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	/**
	 *IntReplenish??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, IntReplenish intReplenish){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intReplenishService.findPageBySql(page, intReplenish);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"????????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-19--??????2:39:50
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadDoc")
	public void uploadDoc(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String fileRealPath = "";//???????????????????????? 
			String firstFileName = ""; 
			String PhotoPath="";
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// ??????????????????????????????????????? 500k
			//upload.setSizeMax(500 * 1024);
			// ????????????????????????
			List fileList = upload.parseRequest(request);
			Iterator it = fileList.iterator();
			String custCode=request.getParameter("custCode"), 
					theNumsString=request.getParameter("firstNum");
			int fileSize=Integer.parseInt(request.getParameter("allowDocSize"));
			int firstNum=-1;
			if(StringUtils.isNotBlank(theNumsString)){
				firstNum=Integer.parseInt(theNumsString);
			}
			List<String> fileRealPathList = new ArrayList<String>();
			List<String> PhotoPathList = new ArrayList<String>();
			List<String> fileNameList = new ArrayList<String>();
			// ??????????????????????????????
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				// ??????????????? ????????????
				if (obit.isFormField()) { //?????????,??????????????????
					String fieldName = obit.getFieldName();
					String fieldValue = obit.getString("UTF-8");
					if ("custCode".equals(fieldName)){
						custCode = fieldValue;
					}
					if("firstNum".equals(fieldName)){
						firstNum=Integer.parseInt(fieldValue);
						theNumsString=fieldValue;
					}
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
						firstFileName = fileName.substring(
								fileName.lastIndexOf("\\") + 1);
						map.put("docDescr", firstFileName);
						String formatName = firstFileName
								.substring(firstFileName.lastIndexOf("."));//?????????????????????
						String fileNameNew = System.currentTimeMillis()+formatName;
						String filePath = getCustDocPath(fileNameNew,custCode);
						PhotoPath=getCustDocDownloadPath(request,fileNameNew,custCode)+fileNameNew;
						//savePath=PhotoPath;
						FileUploadServerMgr.makeDir(filePath);
						fileRealPath = filePath + fileNameNew;// ????????????????????????
						map.put("docName", fileNameNew);
						//Thumbnails.of(item.getStoreLocation()).scale(0.50f).toFile(fileRealPath);

						BufferedInputStream in = new BufferedInputStream(
								item.getInputStream());// ?????????????????????
						BufferedOutputStream outStream = new BufferedOutputStream(
								new FileOutputStream(new File(fileRealPath)));// ?????????????????????
						Streams.copy(in, outStream, true);// ???????????????????????????????????????????????? item.getStoreLocation();
						in.close();
						outStream.close();
						
						File srcFileJPG = new File(fileRealPath); 
						ImageIO.setUseCache(false);
						BufferedImage bim = ImageIO.read(srcFileJPG);
						float srcWdith=0f;
						float srcHeigth=0f;
						if(bim!=null&& fileSize*1024<item.getSize()){
							 srcWdith = bim.getWidth();  
							 srcHeigth = bim.getHeight();
					        float scale;
						}
				        if(srcWdith>=1200f||srcHeigth>=1200f ){
			        		Thumbnails.of(item.getStoreLocation()).size(2048, 2048).keepAspectRatio(true).outputQuality(1.0).toFile(fileRealPath);
				        }
						
				        fileRealPathList.add(fileRealPath);
						PhotoPathList.add(PhotoPath);
						fileNameList.add(fileNameNew);
					}
				}
			}
			ServletUtils.outPrintSuccess(request, response, map);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadDocNew")
	public void uploadDocNew(HttpServletRequest request, HttpServletResponse response) {
	    
       try {
            
            MultipartFormData multipartFormData = new MultipartFormData(request);
            
            String custCode = multipartFormData.getParameter("custCode");
            if (StringUtils.isBlank(custCode)) {
                ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
                return;
            }
            
            FileItem fileItem = multipartFormData.getFileItem();
            if (fileItem == null) {
                ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
                return;
            }
                        
            IntReplenishDocUploadRule uploadRule = new IntReplenishDocUploadRule(fileItem.getName(), custCode);
            Result result = FileUploadUtils.upload(fileItem.getInputStream(), 
            		uploadRule.getFileName(),uploadRule.getFileName());
            
            if (!result.isSuccess()) {
                ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
                return;
            }
            
            Map<String, String> datas = new HashMap<String, String>();
            datas.put("docDescr", uploadRule.getOriginalName());
            datas.put("docName", uploadRule.getFullName());

            ServletUtils.outPrintSuccess(request, response, "??????????????????", datas);
            
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
        }
	}
	
	/**
	 * ????????????
	 * @author	created by zb
	 * @date	2019-11-19--??????5:06:04
	 * @param request
	 * @param response
	 * @param custCode
	 * @param path
	 * @param docName
	 * @return
	 */
	@RequestMapping("/deleteDoc")
	@ResponseBody
	public boolean deleteDoc(HttpServletRequest request, HttpServletResponse response,
			String custCode, String path){//D:/homePhoto/intReplenish/CT002697/1515225450156.png
		boolean i;
		try {
			File deleteDoc = new File("D:/homePhoto/intReplenish/"+custCode+"/"+path);
			deleteDoc.delete();
			i=true;
		} catch (Exception e) {
			i=false;
			ServletUtils.outPrintFail(request, response, "????????????");
		}
		return i;
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @param request
	 * @param response
	 * @param docName
	 * @return
	 */
	@RequestMapping("/deleteDocNew")
	@ResponseBody
	public boolean deleteDocNew(HttpServletRequest request, HttpServletResponse response, String docName) {
	    
	    if (StringUtils.isBlank(docName)) {
            ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
            return false;
        }
	    
	    try {
	        Result result = FileUploadUtils.deleteFile(docName);
	        return result.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
            return false;
        }
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response,String docNameArr,
			String docType2Descr,String custCode,String docDescrArr){
		try{ 
			String tempUserName = request.getParameter("docType2Descr");
			tempUserName=java.net.URLDecoder.decode(tempUserName,"UTF-8");
			String url = SystemConfig.getProperty("intReplenish", "", "client");
			String address="";
			if(StringUtils.isNotBlank(custCode)){
				address=intReplenishService.get(Customer.class, custCode).getAddress();
			}
			String zip = address+"_"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss")+".zip";
			String[] docName = docNameArr.split(",");
			StringBuilder[] files = null;
			files = new StringBuilder[docName.length];
			//????????????????????????
			String[] docDescr = docDescrArr.split(",");
			StringBuilder[] filesDescr = null;
			filesDescr = new StringBuilder[docDescr.length];
			for(int i=0;i<docName.length;i++){
				files[i] = new StringBuilder();
				files[i].append(url+custCode+"/"+docName[i]);
				filesDescr[i] = new StringBuilder();
				filesDescr[i].append(docDescr[i]);
			}
			ServletUtils.downLoadFiles(request,response,files,zip,true,filesDescr);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ??????????????????
	 * 
	 * @param request
	 * @param response
	 * @param docNames
	 * @param custCode
	 * @param docDescrs
	 */
	@RequestMapping("/downloadNew")
    public void downloadNew(HttpServletRequest request, HttpServletResponse response,
            String custCode, String docNames, String docDescrs) {
	    
	    if (StringUtils.isBlank(docNames)) {
            ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
            return;
        }
	    
	    if (StringUtils.isBlank(custCode)) {
            ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
            return;
        }
	    
	    String zipName = DateUtil.DateToString(new Date(), "yyyyMMddhhmmss") + ".zip";
	    
	    Customer customer = intReplenishService.get(Customer.class, custCode);
	    if (customer != null
	            && StringUtils.isNotBlank(customer.getAddress())) {
            zipName = customer.getAddress() + "_" + zipName;
        }
	    
	    String[] docNameArray = docNames.split(",");
	    String[] docDescrArray = docDescrs.split(",");
	    
	    StringBuilder[] docUrls = new StringBuilder[docNameArray.length];
	    StringBuilder[] docDescrBuilders = new StringBuilder[docDescrArray.length];
	    
	    for (int i = 0; i < docNameArray.length; i++) {
            docUrls[i] = new StringBuilder(FileUploadUtils.getFileUrl(docNameArray[i]));
            docDescrBuilders[i] = new StringBuilder(docDescrArray[i]);
        }
	    
	    ServletUtils.downLoadFiles(request, response, docUrls, zipName, true, docDescrBuilders);
    }
	
	/**
	 * ????????????????????????
	 * 
	 * */
	public static String getCustDocPath(String fileName,String fileCustCode){
		String custDocNameNew = SystemConfig.getProperty("intReplenish", "", "photo");
		if (StringUtils.isBlank(custDocNameNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return custDocNameNew + fileCustCode + "/";
		}else{
			return SystemConfig.getProperty("prjProg", "", "photo");
		}
	}
	/**
	 * ??????????????????????????????
	 * 
	 * */
	public static String getCustDocDownloadPath(HttpServletRequest request, String fileName,String fileCustCode){
		String path = getCustDocPath(fileName,fileCustCode);
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
	
	/**
	 * ??????
	 * @author	created by cjm
	 * @date	2020???9???2???11:08:02
	 * @param request
	 * @param response
	 * @param intReplenish
	 */
	@RequestMapping("/doFixDuty")
	public void doFixDuty(HttpServletRequest request,HttpServletResponse response,IntReplenish intReplenish){
		logger.debug("????????????");
		try {
			intReplenish.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			/*??????????????????????????????*/
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "???????????????");
				return;
			}
			/*??????????????????*/
			Result result = this.intReplenishService.doSave(intReplenish);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
}
