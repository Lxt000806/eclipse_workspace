package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjProgCheckService;

@Controller
@RequestMapping("/admin/prjProgCheck")
public class PrjProgCheckController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjProgCheckController.class);

	@Autowired
	private PrjProgCheckService prjProgCheckService;
	
	@Autowired
	private CustomerService customerService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjProgCheck prjProgCheck) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgCheckService.findPageBySql(page, prjProgCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goConfirmJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getConfiemJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjProgCheck prjProgCheck) throws Exception {
		UserContext uc =getUserContext(request);
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgCheckService.findConfirmPageBySql(page, prjProgCheck,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCheckJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCheckJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjProgCheck prjProgCheck) throws Exception {
		UserContext uc=getUserContext(request);
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgCheckService.findCheckPageBySql(page, prjProgCheck,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 工程进度查看 -工地巡检
	 * 
	 * */
	@RequestMapping("/goPrjCheckJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPrjCheckJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjProgCheck prjProgCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgCheckService.findPrjCheckPageBySql(page, prjProgCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * PrjProgCheck列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//    add by hc  工地巡检分析   2017/11/14   begin 
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrjProgCheck prjProgCheck=new PrjProgCheck();
		prjProgCheck.setBeginDate(DateUtil.startOfTheMonth(new Date()));
		prjProgCheck.setEndDate(new Date());	
		return new ModelAndView("admin/query/prjProgCheck/prjProgCheck_list").addObject("prjProgCheck", prjProgCheck).
				addObject("czybh", this.getUserContext(request).getCzybh());
		
		//return new ModelAndView("admin/project/prjProg/prjProgCheck_list")   mark by hc 
	}
	
	/**
	 * 工地巡检分析    统计方式按明细	//
	 * @throws Exception
	 */
	@RequestMapping("/prjProgCheckMx")
	@ResponseBody
	public WebPage<Map<String,Object>> prjProgCheckMx(HttpServletRequest request,
			HttpServletResponse response,PrjProgCheck prjProgCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (prjProgCheck.getBeginDate()==null){
			prjProgCheck.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			prjProgCheck.setEndDate(new Date());	
		}
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		prjProgCheck.setBeginDate(new java.sql.Timestamp(prjProgCheck.getBeginDate().getTime()));
		prjProgCheck.setEndDate(new java.sql.Timestamp(prjProgCheck.getEndDate().getTime()));
		prjProgCheckService.findPageBySqlTJFS(page, prjProgCheck,orderBy,direction);//TJFS 统计方式
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	@RequestMapping("/doExcelcheckgdxj")
	public void doExcelcheckgdxj(HttpServletRequest request, 
			HttpServletResponse response, PrjProgCheck prjProgCheck){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		if (prjProgCheck.getBeginDate()==null){
			prjProgCheck.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			prjProgCheck.setEndDate(new Date());	
		}
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		prjProgCheckService.findPageBySqlTJFS(page, prjProgCheck, orderBy, direction);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地巡检分析_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	
	/*
	 *PrjProgCheck 明细查看页面 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看巡检分析页面");
		PrjProgCheck prjProgCheck = null;
		Customer customer=null;
		if (StringUtils.isNotBlank(id)){
			prjProgCheck = prjProgCheckService.get(PrjProgCheck.class, id);
		}else{
			prjProgCheck = new PrjProgCheck();
		}
		if (prjProgCheck.getCustCode()!=null){
			customer = customerService.get(Customer.class, prjProgCheck.getCustCode());
		}else{
			customer = new Customer();
		}
		//通过NO 取到 RemainModifyTime 剩余整改时限
		Map<String, Object>  list= prjProgCheckService.getRemainModifyTime(prjProgCheck.getNo());			
		if ((list!=null)&&(list.size()>0)) {
			prjProgCheck.setRemainModifyTime(list.get("RemainModifyTime").toString());	
		}
		return new ModelAndView("admin/query/prjProgCheck/prjProgCheck_View")
			.addObject("prjProgCheck", prjProgCheck).addObject("customer",customer);
	}
	
	
	/**
	 * 查看图片页面
	 * 
	 * */
	@RequestMapping("/ajaxGetPicture")
	@ResponseBody
	public PrjProg getPicture(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		
		String photoPath= FileUploadUtils.DOWNLOAD_URL+PrjProgNewUploadRule.FIRST_LEVEL_PATH;
		if(prjProg.getReadonly().equals("2")){
			prjProg.setXjPhotoPath(photoPath+prjProg.getPhotoName());
			
		}
		System.out.println(prjProg.getXjPhotoPath());
		return prjProg;
	}

	
	//    add by hc  工地巡检分析   2017/11/14   end  
	
	
	
	
	/**
	 * PrjProgCheck查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/prjProgCheck/prjProgCheck_code");
	}
	/**
	 * 跳转到新增PrjProgCheck页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PrjProgCheck页面");
		PrjProgCheck prjProgCheck = null;
		if (StringUtils.isNotBlank(id)){
			prjProgCheck = prjProgCheckService.get(PrjProgCheck.class, id);
			prjProgCheck.setNo(null);
		}else{
			prjProgCheck = new PrjProgCheck();
		}
		
		return new ModelAndView("admin/project/prjProgCheck/prjProgCheck_save")
			.addObject("prjProgCheck", prjProgCheck);
	}
	/**
	 * 跳转到修改PrjProgCheck页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PrjProgCheck页面");
		PrjProgCheck prjProgCheck = null;
		if (StringUtils.isNotBlank(id)){
			prjProgCheck = prjProgCheckService.get(PrjProgCheck.class, id);
		}else{
			prjProgCheck = new PrjProgCheck();
		}
		
		return new ModelAndView("admin/project/prjProgCheck/prjProgCheck_update")
			.addObject("prjProgCheck", prjProgCheck);
	}
	
	/**
	 * 跳转到查看PrjProgCheck页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PrjProgCheck页面");
		PrjProgCheck prjProgCheck = prjProgCheckService.get(PrjProgCheck.class, id);
		
		return new ModelAndView("admin/project/prjProgCheck/prjProgCheck_detail")
				.addObject("prjProgCheck", prjProgCheck);
	}
	/**
	 * 添加PrjProgCheck
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrjProgCheck prjProgCheck){
		logger.debug("添加PrjProgCheck开始");
		try{
			String str = prjProgCheckService.getSeqNo("tPrjProgCheck");
			prjProgCheck.setNo(str);
			prjProgCheck.setLastUpdate(new Date());
			prjProgCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjProgCheck.setExpired("F");
			this.prjProgCheckService.save(prjProgCheck);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PrjProgCheck失败");
		}
	}
	
	/**
	 * 修改PrjProgCheck
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjProgCheck prjProgCheck){
		logger.debug("修改PrjProgCheck开始");
		try{
			prjProgCheck.setLastUpdate(new Date());
			prjProgCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prjProgCheckService.update(prjProgCheck);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PrjProgCheck失败");
		}
	}
	
	/**
	 * 删除PrjProgCheck
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PrjProgCheck开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PrjProgCheck编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrjProgCheck prjProgCheck = prjProgCheckService.get(PrjProgCheck.class, deleteId);
				if(prjProgCheck == null)
					continue;
				prjProgCheck.setExpired("T");
				prjProgCheckService.update(prjProgCheck);
			}
		}
		logger.debug("删除PrjProgCheck IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PrjProgCheck导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PrjProgCheck prjProgCheck){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjProgCheckService.findPageBySql(page, prjProgCheck);
	}
	
	/**
	 *PrjProg导出工地验收Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doCheckExcel")
	public void doCheckExcel(HttpServletRequest request ,HttpServletResponse response,
			PrjProgCheck prjProgCheck){
		UserContext uc=getUserContext(request);
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjProgCheckService.findCheckPageBySql(page, prjProgCheck,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地巡检_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	

}
