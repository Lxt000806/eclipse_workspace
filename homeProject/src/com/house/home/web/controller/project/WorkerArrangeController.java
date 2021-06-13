package com.house.home.web.controller.project;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.WorkerArrange;
import com.house.home.service.project.WorkerArrangeService;

/**
 * 
 * @author 张海洋
 *
 */

@Controller
@RequestMapping("/admin/workerArrange")
public class WorkerArrangeController extends BaseController {
	
	@Autowired
	private WorkerArrangeService workerArrangeService;
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
            HttpServletResponse response, WorkerArrange workerArrange) {
		
	    Date today = DateUtil.getToday();
	    workerArrange.setFromDate(today);
	    Date aWeekLater = DateUtil.addWeek(today, 1);
	    workerArrange.setToDate(aWeekLater);
	    
		return new ModelAndView("admin/project/workerArrange/workerArrange_list")
		    .addObject("workerArrange", workerArrange);
	}
	
	@RequestMapping("/goJqGridList")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridList(HttpServletRequest request, 
			HttpServletResponse response, WorkerArrange workerArrange) throws Exception {
		
		Page<Map<String,Object>> page = newPageForJqGrid(request);
		workerArrangeService.findPageBySqlList(page, workerArrange, getUserContext(request));
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goBatchArrange")
	public ModelAndView goBatchArrange() {
		
		WorkerArrange workerArrange = new WorkerArrange();
		
		Date tomorrow = DateUtil.getTomorrow();
		workerArrange.setFromDate(tomorrow);
		Date aWeekLater = DateUtil.addWeek(tomorrow, 1);
		workerArrange.setToDate(aWeekLater);
		
		return new ModelAndView("admin/project/workerArrange/workerArrange_batchArrange")
			.addObject("workerArrange", workerArrange);
	}
	
	@RequestMapping("/doBatchArrange")
	public void doBatchArrange(HttpServletRequest request,
			HttpServletResponse response, WorkerArrange workerArrange) {
		
		try {
			Result result = workerArrangeService.doBatchArrange(request, response, workerArrange, getUserContext(request));
			if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, e.getMessage());
		}
		
	}
	
	@RequestMapping("/goBatchDel")
	public ModelAndView goBatchDel(HttpServletRequest request,
			HttpServletResponse response, String pks, String expired) {
		
		return new ModelAndView("admin/project/workerArrange/workerArrange_batchDel")
			.addObject("pks", pks)
			.addObject("expired", expired);
	}
	
	@RequestMapping("/doBatchDel")
	public void doBatchDel(HttpServletRequest request,
			HttpServletResponse response, String pks) {
	    
		Result result = workerArrangeService.doBatchDel(request, response, pks);
		
		if (result.isSuccess()) {
            ServletUtils.outPrintSuccess(request, response, result.getInfo());
        } else {
            ServletUtils.outPrintFail(request, response, result.getInfo());
        }
	}
	
	@RequestMapping("/goOrder")
	public ModelAndView goOrder(Integer id) {
		
		WorkerArrange workerArrange = workerArrangeService.getWorkerArrangeWithInfo(id);
		
		return new ModelAndView("admin/project/workerArrange/workerArrange_order")
			.addObject("workerArrange", workerArrange);
	}
	
	@RequestMapping("/doOrder")
	public void doOrder(HttpServletRequest request,
			HttpServletResponse response, WorkerArrange workerArrange) {
	    
	    try {
	        Result result = workerArrangeService.doOrder(getUserContext(request), workerArrange);
            
	        if (result.isSuccess()) {
	            ServletUtils.outPrintSuccess(request, response, result.getInfo());
	        } else {
	            ServletUtils.outPrintFail(request, response, result.getInfo());
	        }
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, e.getMessage());
        }
		
	}
	
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request,
			HttpServletResponse response, Integer pk) {
	    
		Result result = workerArrangeService.doReturn(request, response, getUserContext(request), pk);
		
		if (result.isSuccess()) {
            ServletUtils.outPrintSuccess(request, response, result.getInfo());
        } else {
            ServletUtils.outPrintFail(request, response, result.getInfo());
        }
		
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(Integer id){	
		
		return new ModelAndView("admin/project/workerArrange/workerArrange_view")
			.addObject("workerArrange", workerArrangeService.getWorkerArrangeWithInfo(id));
	}
	
}
