package com.house.home.web.controller.basic;

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

import com.house.framework.web.controller.BaseController;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.ResrCustRight;
import com.house.home.service.basic.ResrCustRightService;

@Controller
@RequestMapping("/admin/ResrCustRight")
public class ResrCustRightController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ResrCustRightController.class);

	@Autowired
	private ResrCustRightService resrCustRightService;

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
			HttpServletResponse response, ResrCustRight resrCustRight) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		
		resrCustRightService.findPageBySql(page, resrCustRight);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ResrCustRight列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ResrCustRight resrCustRight) throws Exception {
		
		return new ModelAndView("admin/basic/resrCustRight/resrCustRight_list");
	}

	/**
	 * 跳转到查看ResrCustRight页面
	 * @param request
	 * @param response
	 * @param resrCustRight
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, ResrCustRight resrCustRight){
		logger.debug("跳转到查看ResrCustRight页面");
		
		Map<String,Object> map = resrCustRightService.getByPk(resrCustRight.getPk());
		
		ResrCustRight rcr = new ResrCustRight();
		BeanConvertUtil.mapToBean(map,rcr);
		
		return new ModelAndView("admin/basic/resrCustRight/resrCustRight_detail")
				.addObject("resrCustRight", rcr);
	}
	
	/**
	 * 跳转到ResrCustRight保存页面
	 * @param request
	 * @param resrCustRight
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, ResrCustRight resrCustRight){
		return new ModelAndView("admin/basic/resrCustRight/resrCustRight_save");
	}
	
	/**
	 * 新增ResrCustRight记录
	 * @param request
	 * @param response
	 * @param resrCustRight
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, ResrCustRight resrCustRight){
		try{
			if(!resrCustRightService.getByBuildandDept(resrCustRight.getBuilderCode(), resrCustRight.getDepartment2())){

				resrCustRight.setLastUpdate(new Date());
				resrCustRight.setActionLog("ADD");
				resrCustRight.setExpired("F");
				resrCustRight.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				
				resrCustRightService.save(resrCustRight);
				
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, "该项目编号"+resrCustRight.getBuilderCode()+"与二级部门"+resrCustRight.getDepartment2()+"的记录已存在!"); 
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加客户资源分配信息失败"); 
		}
	}
	
	/**
	 * 删除资源客户分配信息
	 * @param request
	 * @param response
	 * @param deleteIds
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除资源客户分配信息开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "选择为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ResrCustRight resrCustRight = resrCustRightService.get(ResrCustRight.class,Integer.parseInt(deleteId));
				if(resrCustRight == null)
					continue;
				resrCustRightService.delete(resrCustRight);
			}
		}
		logger.debug("删除资源客户分配信息 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
}
