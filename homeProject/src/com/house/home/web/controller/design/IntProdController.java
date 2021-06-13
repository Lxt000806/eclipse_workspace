package com.house.home.web.controller.design;

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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Item;
import com.house.home.entity.design.IntProd;
import com.house.home.service.design.IntProdService;

@Controller
@RequestMapping("/admin/intProd")
public class IntProdController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IntProdController.class);

	@Autowired
	private IntProdService intProdService;

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
			HttpServletResponse response, IntProd intProd) throws Exception {
		if(intProd.getFixAreaPk()==null){
			return null;
		} else if(-1 == intProd.getFixAreaPk()) {//当传入为-1时，将fixAreaPk设为null，显示所有区域的成品名称 --add by zb
			intProd.setFixAreaPk(null);
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		intProdService.findPageBySql(page, intProd);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * IntProd列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/intProd/intProd_list");
	}
	
	/**
	 * IntProd查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,IntProd intProd) throws Exception {

		return new ModelAndView("admin/design/intProd/intProd_code").addObject("intProd", intProd);
	}
	/**
	 * 跳转到新增IntProd页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			IntProd intProd){
		
		return new ModelAndView("admin/design/intProd/intProd_save")
			.addObject("intProd", intProd);
	}
	/**
	 * 跳转到新增IntProd(包含橱柜选择)页面
	 * @return
	 */
	@RequestMapping("/goSaveWithCupboard")
	public ModelAndView goSaveWithCupboard(HttpServletRequest request, HttpServletResponse response, 
			IntProd intProd){
		
		return new ModelAndView("admin/design/intProd/intProd_save_cupboard")
			.addObject("intProd", intProd);
	}
	/**
	 * 跳转到修改IntProd(包含橱柜选择)页面
	 * @return
	 */
	@RequestMapping("/goUpdateWithCupboard")
	public ModelAndView goUpdateWithCupboard(HttpServletRequest request, HttpServletResponse response, 
			IntProd intProd){
		return new ModelAndView("admin/design/intProd/intProd_update_cupboard")
		.addObject("intProd", intProd);
	}
	/**
	 * 跳转到修改IntProd页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			IntProd intProd){
		return new ModelAndView("admin/design/intProd/intProd_update")
		.addObject("intProd", intProd);
	}
	
	/**
	 * 跳转到查看IntProd页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看IntProd页面");
		IntProd intProd = intProdService.get(IntProd.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/design/intProd/intProd_detail")
				.addObject("intProd", intProd);
	}
	/**
	 * 添加IntProd
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, IntProd intProd){
		logger.debug("添加IntProd开始");
		if(intProdService.isExisted(intProd)){
			ServletUtils.outPrintFail(request, response, "该装修区域的集成成品名称已存在!");
			return;
		}
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		intProd.setLastUpdatedBy(uc.getCzybh());
		intProd.setExpired("F");
		intProd.setActionLog("ADD");
		try{
			intProdService.save(intProd);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加集成成品失败");
		}
	}
	
	/**
	 * 修改IntProd
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, IntProd intProd){
		logger.debug("修改IntProd开始");
		if(intProdService.isExisted(intProd)){
			ServletUtils.outPrintFail(request, response, "该装修区域的集成成品名称已存在!");
			return;
		}
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		intProd.setLastUpdatedBy(uc.getCzybh());
		intProd.setExpired("F");
		intProd.setActionLog("EDIT");
		try{
			intProdService.update(intProd);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改集成成品失败");
		}
	}
	
	/**
	 * 删除IntProd
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除IntProd开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "IntProd编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				IntProd intProd = intProdService.get(IntProd.class, Integer.parseInt(deleteId));
				if(intProd == null)
					continue;
				intProd.setExpired("T");
				intProdService.update(intProd);
			}
		}
		logger.debug("删除IntProd IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *IntProd导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, IntProd intProd){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intProdService.findPageBySql(page, intProd);
	}
	/**
	 * 根据id查询材料详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getIntProd")
	@ResponseBody
	public JSONObject getIntProd(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		IntProd intProd = intProdService.get(IntProd.class, Integer.parseInt(id));
		if(intProd == null){
			return this.out("系统中不存在code="+id+"的材料信息", false);
		}
		return this.out(intProd, true);
	}
}
