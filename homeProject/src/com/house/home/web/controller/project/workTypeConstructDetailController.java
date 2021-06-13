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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.Worker;
import com.house.home.service.project.WorkerService;

@Controller
@RequestMapping("/admin/bzsgfx")
public class workTypeConstructDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(workTypeConstructDetailController.class);

	@Autowired
	private WorkerService workerService;
	
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
			HttpServletResponse response, Worker worker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		workerService.findworkTypeConstructDetail(page, worker,orderBy,direction);
		return new WebPage<Map<String,Object>>(page);
	}
	
	//安排工地查看
	@RequestMapping("/goJqGridarrange")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridarrange(HttpServletRequest request,
			HttpServletResponse response, Worker worker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findarrange(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}	
	//完工工地查看
	@RequestMapping("/goJqGridcomplete")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridcomplete(HttpServletRequest request,
			HttpServletResponse response, Worker worker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findcomplete(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}
	//未通过查看
	@RequestMapping("/goJqGridnoPass")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridnoPass(HttpServletRequest request,
			HttpServletResponse response, Worker worker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findnoPass(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}
	//签到天数查看
	@RequestMapping("/goJqGridcrtDate")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridcrtDate(HttpServletRequest request,
			HttpServletResponse response, Worker worker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findcrtDate(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}
	//工资发放额查看
	@RequestMapping("/goJqGridconfirmAmount")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridconfirmAmount(HttpServletRequest request,
			HttpServletResponse response, Worker worker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findconfirmAmount(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}
	//在建工地查看
	@RequestMapping("/goJqGridbuilder")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridbuilder(HttpServletRequest request,
			HttpServletResponse response, Worker worker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findbuilder(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * workTypeConstructDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Worker worker=new Worker();
		worker.setBeginDate(DateUtil.startOfTheMonth(new Date()));
		worker.setEndDate(DateUtil.endOfTheMonth(new Date()));
		worker.setExpired("F");
		worker.setCzybh(this.getUserContext(request).getCzybh());
		//通过操作员编号取到workType12		
		
		List<Map<String,Object>>  list= workerService.getWorkType12(this.getUserContext(request).getCzybh());	
		if ((list!=null)&&(list.size()>0)) {
			Map<String,Object> mapmsg=list.get(0);
			worker.setWorkType12(StringUtils.deleteWhitespace((String) mapmsg.get("Code")));
		}else {
			worker.setWorkType12("");
		}
		worker.setIsSign("1");
		return new ModelAndView("admin/project/workTypeConstructDetail/workTypeConstructDetail_list")
		.addObject("worker",worker)
		.addObject("czybm",this.getUserContext(request).getCzybh());
	}
	

	

	
	
	/**
	 * 跳转到新增worker页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增worker页面");
		Worker worker = null;
		if (StringUtils.isNotBlank(id)){
			worker = workerService.get(Worker.class, id);
			worker.setCode(null);
		}else{
			worker = new Worker();
		}
		
		return new ModelAndView("admin/project/worker/worker_save")
			.addObject("worker", worker);
	}
	/**
	 * 跳转到修改worker页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改worker页面");
		Worker worker = null;
		if (StringUtils.isNotBlank(id)){
			worker = workerService.get(Worker.class, id);
		}else{
			worker = new Worker();
		}
		
		return new ModelAndView("admin/project/worker/worker_update")
			.addObject("worker", worker);
	}
	
	/**
	 * 跳转到查看worker页面
	 * @return
	 */
	
	@RequestMapping("/goView")           
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,Worker worker){
		logger.debug("跳转到查看材料套餐包页面");
		Date d1=worker.getBeginDate();
		Date d2=worker.getEndDate();
		String department2=worker.getDepartment2();
		String workType12=worker.getWorkType12();
		String statisticalMethods=worker.getStatisticalMethods();
		if (StringUtils.isNotBlank(worker.getCode())){
			worker = workerService.get(Worker.class, worker.getCode());
		}else{
			worker = new Worker();
		}                       
		worker.setBeginDate(d1);
		worker.setEndDate(d2);
		worker.setDepartment2(department2);
		worker.setWorkType12(workType12);
		worker.setStatisticalMethods(statisticalMethods);
		return new ModelAndView("admin/project/workTypeConstructDetail/bzsgfx_View")
			.addObject("worker", worker);
	}
	
	/**
	 * 查看签到
	 */
	@RequestMapping("/goSignView")           
	public ModelAndView goSignView(HttpServletRequest request, HttpServletResponse response, Integer pk,Date beginDate,Date endDate){
		
		// fromModule 控制当前模块查看时不显示"签到图片"按钮
		return new ModelAndView("admin/project/custWorker/custWorker_view_signDetail")
				.addObject("pk", pk)
				.addObject("beginDate", beginDate)
				.addObject("endDate", endDate)
				.addObject("fromModule", "bzsgfx");
	}
	
	/**
	 * 添加worker
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Worker worker){
		logger.debug("添加worker开始");
		try{
			String str = workerService.getSeqNo("tWorker");
			worker.setCode(str);
			worker.setLastUpdate(new Date());
			worker.setLastUpdatedBy(getUserContext(request).getCzybh());
			worker.setExpired("F");
			this.workerService.save(worker);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加worker失败");
		}
	}
	
	/**
	 * 修改worker
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Worker worker){
		logger.debug("修改worker开始");
		try{
			worker.setLastUpdate(new Date());
			worker.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.workerService.update(worker);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改worker失败");
		}
	}
	
	/**
	 * 删除worker
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除worker开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "worker编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Worker worker = workerService.get(Worker.class, deleteId);
				if(worker == null)
					continue;
				worker.setExpired("T");
				workerService.update(worker);
			}
		}
		logger.debug("删除worker IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *worker导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Worker worker){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workerService.findPageBySql(page, worker);
	}

}
