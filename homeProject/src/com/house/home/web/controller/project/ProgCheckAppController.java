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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ProgCheckApp;
import com.house.home.entity.project.ProgCheckPlanDetail;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.ProgCheckAppService;
import com.house.home.service.project.ProgCheckPlanDetailService;

@Controller
@RequestMapping("/admin/progCheckApp")
public class ProgCheckAppController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProgCheckAppController.class);

	@Autowired
	private ProgCheckAppService progCheckAppService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProgCheckPlanDetailService progCheckPlanDetailService;

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
			HttpServletResponse response, ProgCheckApp progCheckApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		progCheckAppService.findPageBySql(page, progCheckApp, this.getUserContext(request));
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ProgCheckApp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/progCheckApp/progCheckApp_list");
	}
	/**
	 * ProgCheckApp查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/progCheckApp/progCheckApp_code");
	}
	/**
	 * 跳转到新增ProgCheckApp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ProgCheckApp页面");
		ProgCheckApp progCheckApp = null;
		if (StringUtils.isNotBlank(id)){
			progCheckApp = progCheckAppService.get(ProgCheckApp.class, Integer.parseInt(id));
			progCheckApp.setPk(null);
		}else{
			progCheckApp = new ProgCheckApp();
		}
		
		return new ModelAndView("admin/project/progCheckApp/progCheckApp_save")
			.addObject("progCheckApp", progCheckApp);
	}
	/**
	 * 跳转到修改ProgCheckApp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ProgCheckApp页面");
		ProgCheckApp progCheckApp = null;
		if (StringUtils.isNotBlank(id)){
			progCheckApp = progCheckAppService.get(ProgCheckApp.class, Integer.parseInt(id));
			if (StringUtils.isNotBlank(progCheckApp.getCustCode())){
				Customer customer = customerService.get(Customer.class, progCheckApp.getCustCode());
				if (customer!=null){
					progCheckApp.setAddress(customer.getAddress());
					progCheckApp.setDescr(customer.getDescr());
				}
			}
		}else{
			progCheckApp = new ProgCheckApp();
		}
		
		return new ModelAndView("admin/project/progCheckApp/progCheckApp_update")
			.addObject("progCheckApp", progCheckApp);
	}
	
	/**
	 * 跳转到查看ProgCheckApp页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ProgCheckApp页面");
		Map<String,Object> map = progCheckAppService.getByPk(Integer.parseInt(id));
		ProgCheckApp progCheckApp = new ProgCheckApp();
		BeanConvertUtil.mapToBean(map, progCheckApp);
		
		return new ModelAndView("admin/project/progCheckApp/progCheckApp_detail")
				.addObject("progCheckApp", progCheckApp);
	}
	/**
	 * 添加ProgCheckApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ProgCheckApp progCheckApp){
		logger.debug("添加ProgCheckApp开始");
		try{
			progCheckApp.setAppDate(new Date());
			progCheckApp.setAppCzy(this.getUserContext(request).getCzybh());
			progCheckApp.setLastUpdate(new Date());
			progCheckApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			progCheckApp.setExpired("F");
			progCheckApp.setActionLog("ADD");
			this.progCheckAppService.save(progCheckApp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ProgCheckApp失败");
		}
	}
	
	/**
	 * 修改ProgCheckApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ProgCheckApp progCheckApp){
		logger.debug("修改ProgCheckApp开始");
		try{
			Integer appPk = progCheckApp.getPk();
			ProgCheckPlanDetail progCheckPlanDetail = progCheckPlanDetailService.getByAppPk(appPk);
			if (progCheckPlanDetail!=null){
				ServletUtils.outPrintFail(request, response, "已安排巡检的不可以编辑");
				return;
			}
			ProgCheckApp app = progCheckAppService.get(ProgCheckApp.class, progCheckApp.getPk());
			app.setRemarks(progCheckApp.getRemarks());
			app.setLastUpdate(new Date());
			app.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			app.setActionLog("EDIT");
			this.progCheckAppService.update(app);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ProgCheckApp失败");
		}
	}
	
	/**
	 * 删除ProgCheckApp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ProgCheckApp开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ProgCheckApp编号不能为空,删除失败");
			return;
		}
		
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		int iAll=0,iFail=0;
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ProgCheckApp progCheckApp = progCheckAppService.get(ProgCheckApp.class, Integer.parseInt(deleteId));
				if(progCheckApp == null){
					iFail++;
					continue;
				}
				ProgCheckPlanDetail progCheckPlanDetail = progCheckPlanDetailService.getByAppPk(Integer.parseInt(deleteId));
				if (progCheckPlanDetail!=null){
					iFail++;
					continue;
				}
				progCheckApp.setExpired("T");
				progCheckAppService.update(progCheckApp);
			}
			iAll++;
		}
		logger.debug("删除ProgCheckApp IDS={} 完成",deleteIdList);
		if (iFail==0){
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}else{
			if (iAll==1){
				ServletUtils.outPrintFail(request, response, "已安排巡检的不可以删除");
			}else{
				ServletUtils.outPrintSuccess(request, response,"删除成功"+(iAll-iFail)+"条，失败"+iFail+"条");
			}
		}
		
	}

	/**
	 *ProgCheckApp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ProgCheckApp progCheckApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		progCheckAppService.findPageBySql(page, progCheckApp, this.getUserContext(request));
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ProgCheckApp_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
