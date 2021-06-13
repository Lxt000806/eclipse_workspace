package com.house.home.web.controller.finance;

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
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.service.finance.SplCheckOutService;

@Controller
@RequestMapping("/admin/splCheckOut")
public class SplCheckOutController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SplCheckOutController.class);

	@Autowired
	private SplCheckOutService splCheckOutService;

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
			HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		splCheckOutService.findPageBySql(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 供应商结算选择查询
	 * @author	created by zb
	 * @date	2018-11-22--下午3:58:36
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCodeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCodeJqGrid(HttpServletRequest request,
			HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		splCheckOutService.findCodePageBySql(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * SplCheckOut列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/splCheckOut/splCheckOut_list");
	}
	/**
	 * SplCheckOut查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {

		return new ModelAndView("admin/finance/splCheckOut/splCheckOut_code")
			.addObject("splCheckOut", splCheckOut);
	}
	/**
	 * 跳转到新增SplCheckOut页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SplCheckOut页面");
		SplCheckOut splCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			splCheckOut = splCheckOutService.get(SplCheckOut.class, id);
			splCheckOut.setNo(null);
		}else{
			splCheckOut = new SplCheckOut();
		}
		
		return new ModelAndView("admin/finance/splCheckOut/splCheckOut_save")
			.addObject("splCheckOut", splCheckOut);
	}
	/**
	 * 跳转到修改SplCheckOut页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改SplCheckOut页面");
		SplCheckOut splCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			splCheckOut = splCheckOutService.get(SplCheckOut.class, id);
		}else{
			splCheckOut = new SplCheckOut();
		}
		
		return new ModelAndView("admin/finance/splCheckOut/splCheckOut_update")
			.addObject("splCheckOut", splCheckOut);
	}
	
	/**
	 * 跳转到查看SplCheckOut页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SplCheckOut页面");
		SplCheckOut splCheckOut = splCheckOutService.get(SplCheckOut.class, id);
		
		return new ModelAndView("admin/finance/splCheckOut/splCheckOut_detail")
				.addObject("splCheckOut", splCheckOut);
	}
	/**
	 * 添加SplCheckOut
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		logger.debug("添加SplCheckOut开始");
		try{
			String str = splCheckOutService.getSeqNo("tSplCheckOut");
			splCheckOut.setNo(str);
			splCheckOut.setLastUpdate(new Date());
			splCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh());
			splCheckOut.setExpired("F");
			this.splCheckOutService.save(splCheckOut);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SplCheckOut失败");
		}
	}
	
	/**
	 * 修改SplCheckOut
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut){
		logger.debug("修改SplCheckOut开始");
		try{
			splCheckOut.setLastUpdate(new Date());
			splCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.splCheckOutService.update(splCheckOut);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SplCheckOut失败");
		}
	}
	
	/**
	 * 删除SplCheckOut
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SplCheckOut开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SplCheckOut编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SplCheckOut splCheckOut = splCheckOutService.get(SplCheckOut.class, deleteId);
				if(splCheckOut == null)
					continue;
				splCheckOut.setExpired("T");
				splCheckOutService.update(splCheckOut);
			}
		}
		logger.debug("删除SplCheckOut IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SplCheckOut导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SplCheckOut splCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		splCheckOutService.findPageBySql(page, splCheckOut);
	}
	
}
