package com.house.home.web.controller.design;

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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.service.design.BaseItemPlanService;

@Controller
@RequestMapping("/admin/baseItemPlan")
public class BaseItemPlanController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseItemPlanController.class);

	@Autowired
	private BaseItemPlanService baseItemPlanService;

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
			HttpServletResponse response, BaseItemPlan baseItemPlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemPlanService.findPageBySql(page, baseItemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseItemPlan列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/baseItemPlan/baseItemPlan_list");
	}
	/**
	 * BaseItemPlan查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/baseItemPlan/baseItemPlan_code");
	}
	/**
	 * 跳转到新增BaseItemPlan页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItemPlan页面");
		BaseItemPlan baseItemPlan = null;
		if (StringUtils.isNotBlank(id)){
			baseItemPlan = baseItemPlanService.get(BaseItemPlan.class, Integer.parseInt(id));
			baseItemPlan.setPk(null);
		}else{
			baseItemPlan = new BaseItemPlan();
		}
		
		return new ModelAndView("admin/design/baseItemPlan/baseItemPlan_save")
			.addObject("baseItemPlan", baseItemPlan);
	}
	/**
	 * 跳转到修改BaseItemPlan页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BaseItemPlan页面");
		BaseItemPlan baseItemPlan = null;
		if (StringUtils.isNotBlank(id)){
			baseItemPlan = baseItemPlanService.get(BaseItemPlan.class, Integer.parseInt(id));
		}else{
			baseItemPlan = new BaseItemPlan();
		}
		
		return new ModelAndView("admin/design/baseItemPlan/baseItemPlan_update")
			.addObject("baseItemPlan", baseItemPlan);
	}
	
	/**
	 * 跳转到查看BaseItemPlan页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BaseItemPlan页面");
		BaseItemPlan baseItemPlan = baseItemPlanService.get(BaseItemPlan.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/design/baseItemPlan/baseItemPlan_detail")
				.addObject("baseItemPlan", baseItemPlan);
	}
	/**
	 * 添加BaseItemPlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseItemPlan baseItemPlan){
		logger.debug("添加BaseItemPlan开始");
		try{
			this.baseItemPlanService.save(baseItemPlan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseItemPlan失败");
		}
	}
	
	/**
	 * 修改BaseItemPlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseItemPlan baseItemPlan){
		logger.debug("修改BaseItemPlan开始");
		try{
			baseItemPlan.setLastUpdate(new Date());
			baseItemPlan.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseItemPlanService.update(baseItemPlan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItemPlan失败");
		}
	}
	
	/**
	 * 删除BaseItemPlan
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseItemPlan开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseItemPlan编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseItemPlan baseItemPlan = baseItemPlanService.get(BaseItemPlan.class, Integer.parseInt(deleteId));
				if(baseItemPlan == null)
					continue;
				baseItemPlan.setExpired("T");
				baseItemPlanService.update(baseItemPlan);
			}
		}
		logger.debug("删除BaseItemPlan IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/getBaseItemPlanAutoQty")
	@ResponseBody
	public String  getBaseItemPlanAutoQty(HttpServletRequest request,HttpServletResponse response,BaseItemPlan baseItemPlan){
		logger.debug("ajax获取预报价数量、预估施工量、其他费用");
		String qty = baseItemPlanService.getBaseItemPlanAutoQty(baseItemPlan);
		return qty;
	}
	
	/**
	 *BaseItemPlan导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseItemPlan baseItemPlan){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemPlanService.findPageBySql(page, baseItemPlan);
	}
	

}
