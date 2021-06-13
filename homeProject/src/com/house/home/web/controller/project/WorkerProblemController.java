package com.house.home.web.controller.project;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.impl.WorkerPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.IntReplenish;
import com.house.home.entity.project.WorkerProblem;
import com.house.home.service.project.WorkerProblemService;

@Controller
@RequestMapping("/admin/workerProblem")
public class WorkerProblemController extends BaseController{
	@Autowired
	private  WorkerProblemService workerProblemService;

	/**
	 * 查询jqGrid表格数据
	 * @param request
	 * @param response
	 * @param workerProblem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, WorkerProblem workerProblem)
			throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workerProblemService.findPageBySql(page, workerProblem);
		return new WebPage<Map<String, Object>>(page);
	}
	
	/**
	 * 跳转
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/project/workerProblem/workerProblem_list");
	}
	
	/**
	 * 查看反馈内容和图片
	 * @param request
	 * @param response
	 * @param workerProblem
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			WorkerProblem workerProblem){
		logger.debug("跳转到查看内容和图片页面");
		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		Map<String, Object> map=workerProblemService.findPageBySql(page, workerProblem).getResult().get(0);
		System.out.println(map);
		return new ModelAndView("admin/project/workerProblem/workerProblem_viewPic")
			.addObject("workerProblem", map);
	}
	/**
	 * 查看反馈内容和图片
	 * @param request
	 * @param response
	 * @param workerProblem
	 * @return
	 */
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request, HttpServletResponse response, 
			WorkerProblem workerProblem){
		logger.debug("跳转到查看内容和图片页面");
		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		Map<String, Object> map=workerProblemService.findPageBySql(page, workerProblem).getResult().get(0);
		return new ModelAndView("admin/project/workerProblem/workerProblem_confirm")
			.addObject("workerProblem", map);
	}
	/**
	 * 查看反馈内容和图片
	 * @param request
	 * @param response
	 * @param workerProblem
	 * @return
	 */
	@RequestMapping("/goReplenish")
	public ModelAndView goReplenish(HttpServletRequest request, HttpServletResponse response, 
			WorkerProblem workerProblem){
		logger.debug("跳转到查看内容和图片页面");
		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		Map<String, Object> map=workerProblemService.findPageBySql(page, workerProblem).getResult().get(0);
		return new ModelAndView("admin/project/workerProblem/workerProblem_replenish")
			.addObject("workerProblem", map);
	}
	/**
	 * 查看反馈内容和图片
	 * @param request
	 * @param response
	 * @param workerProblem
	 * @return
	 */
	@RequestMapping("/goDeal")
	public ModelAndView goDeal(HttpServletRequest request, HttpServletResponse response, 
			WorkerProblem workerProblem){
		logger.debug("跳转到查看内容和图片页面");
		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		Map<String, Object> map=workerProblemService.findPageBySql(page, workerProblem).getResult().get(0);
		return new ModelAndView("admin/project/workerProblem/workerProblem_deal")
			.addObject("workerProblem", map);
	}
	/**
	 * 获取图片列表
	 * @param request
	 * @param response
	 * @param workerProblem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPicJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getPicJqGrid(HttpServletRequest request,
			HttpServletResponse response, WorkerProblem workerProblem)
			throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workerProblemService.findPicPageBySql(page, workerProblem);
		String url = FileUploadUtils.DOWNLOAD_URL+WorkerPicUploadRule.FIRST_LEVEL_PATH;
		/*forEach添加URL到page的result中*/
		for (Map<String, Object> workerPicMap : page.getResult()) {
			workerPicMap.put("url", url);
		}
		return new WebPage<Map<String, Object>>(page);
	}
	
	/**
	 * 获取图片上传地址
	 * @param fileName
	 * @return
	 */
	public static String getWorkerPicUploadPath(String fileName){
		String prjProgNew = SystemConfig.getProperty("workerPic", "", "photo");
		if (StringUtils.isBlank(prjProgNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return prjProgNew+ "/";
		}else{
			return SystemConfig.getProperty("workerPic", "", "photo");
		}
	}
	
	/**
	 * 获取图片下载地址
	 * @param request
	 * @param fileName
	 * @return
	 */
	public static String getWorkerPicDownloadPath(HttpServletRequest request, String fileName){
		String path = getWorkerPicUploadPath(fileName);
		
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
		//return OssConfigure.accessUrl + "/" + path.substring(path.indexOf("/")+1);
	}
	/**
	 * 工人反馈确认
	 * @param request
	 * @param response
	 * @param workerProblem
	 */
	@RequestMapping("/doSaveConfirm")
	public void doSaveConfirm(HttpServletRequest request ,
			HttpServletResponse response ,WorkerProblem workerProblem){
		logger.debug("工人反馈确认");
		try{
			workerProblem.setConfirmCzy(this.getUserContext(request).getEmnum());
			workerProblem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.workerProblemService.doConfirm(workerProblem);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "工人问题反馈管理确认失败");
		}
	}
	/**
	 * 工人反馈确认
	 * @param request
	 * @param response
	 * @param workerProblem
	 */
	@RequestMapping("/doSaveReplenish")
	public void doSaveReplenish(HttpServletRequest request ,
			HttpServletResponse response ,WorkerProblem wp){
		logger.debug("工人反馈确认并补货");
		try{
			//更新工人问题反馈信息
			WorkerProblem workerProblem = workerProblemService.get(WorkerProblem.class, wp.getNo());
			workerProblem.setStatus("2");
			workerProblem.setActionLog("EDIT");
			workerProblem.setLastUpdate(new Date());
			workerProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			workerProblem.setConfirmRemark(wp.getConfirmRemark());
			workerProblem.setCustCode(wp.getCustCode());
			workerProblem.setConfirmCzy(getUserContext(request).getCzybh());
			workerProblem.setConfirmDate(new Date());
			workerProblemService.update(workerProblem);
			
			//创建集成补货信息
			IntReplenish intReplenish=new IntReplenish();
			String no = workerProblemService.getSeqNo("tIntReplenish");
			intReplenish.setNo(no);
			intReplenish.setWkpbNo(wp.getNo());
			intReplenish.setCustCode(workerProblem.getCustCode());
			intReplenish.setStatus("1");
			intReplenish.setLastUpdate(new Date());
			intReplenish.setLastUpdatedBy(getUserContext(request).getCzybh());
			intReplenish.setActionLog("ADD");
			intReplenish.setExpired("F");
			intReplenish.setIsCupboard(wp.getIsCupboard());
			intReplenish.setSource("1");
			intReplenish.setRemarks(wp.getConfirmRemark());
			workerProblemService.save(intReplenish);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "工人问题反馈管理确认失败");
		}
	}
	/**
	 * 工人反馈处理
	 * @param request
	 * @param response
	 * @param workerProblem
	 */
	@RequestMapping("/doSaveDeal")
	public void doSaveDeal(HttpServletRequest request ,
			HttpServletResponse response ,WorkerProblem workerProblem){
		logger.debug("工人反馈处理");
		try{
			workerProblem.setDealCzy(this.getUserContext(request).getEmnum());
			workerProblem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.workerProblemService.doDeal(workerProblem);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "工人问题反馈管理处理失败");
		}
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			WorkerProblem workerProblem){		
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		workerProblemService.findPageBySql(page, workerProblem);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工人问题反馈管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
