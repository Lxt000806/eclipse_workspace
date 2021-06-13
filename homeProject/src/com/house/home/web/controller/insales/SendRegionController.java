package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.SendRegion;
import com.house.home.service.insales.SendRegionService;

@Controller
@RequestMapping("/admin/sendRegion")
public class SendRegionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SendRegionController.class);

	@Autowired
	private SendRegionService sendRegionService;

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
			HttpServletResponse response, SendRegion sendRegion) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sendRegionService.findPageBySql(page, sendRegion);
		return new WebPage<Map<String,Object>>(page);
	}
	
	// 配送区域分页查询
	@RequestMapping("/goSendRegionJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSendRegionJqGrid(HttpServletRequest request,
			HttpServletResponse response, SendRegion sendRegion) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sendRegionService.findSendRegionPageBySql(page, sendRegion);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * SendRegion列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/sendRegion/sendRegion_list");
	}
	/**
	 * SendRegion查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/sendRegion/sendRegion_code");
	}
	/**
	 * 根据id查询详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSendRegion")
	@ResponseBody
	public JSONObject getSendRegion(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		SendRegion sendRegion = sendRegionService.get(SendRegion.class, id);
		if(sendRegion == null){
			return this.out("系统中不存在code="+id+"的材料信息", false);
		}
		return this.out(sendRegion, true);
	}
	/**
	 * 跳转到新增SendRegion页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增SendRegion页面");
		
		return new ModelAndView("admin/insales/sendRegion/sendRegion_save");
	}
	/**
	 * 跳转到修改SendRegion页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			SendRegion sendRegion){
		logger.debug("跳转到修改SendRegion页面");
		SendRegion sr = sendRegionService.get(SendRegion.class, sendRegion.getNo());
		if (null != sr){
			sendRegion = sr;
		} else {
			sendRegion = null;
		}
		
		return new ModelAndView("admin/insales/sendRegion/sendRegion_update")
			.addObject("sendRegion", sendRegion);
	}
	
	/**
	 * 跳转到查看SendRegion页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			SendRegion sendRegion){
		logger.debug("跳转到查看SendRegion页面");
		SendRegion sr = sendRegionService.get(SendRegion.class, sendRegion.getNo());
		if (null != sr){
			sendRegion = sr;
		} else {
			sendRegion = null;
		}
		sendRegion.setM_umState("V");
		return new ModelAndView("admin/insales/sendRegion/sendRegion_update")
				.addObject("sendRegion", sendRegion);
	}
	/**
	 * 添加SendRegion
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SendRegion sendRegion){
		logger.debug("添加SendRegion开始");
		try{
			String str = sendRegionService.getSeqNo("tSendRegion");
			sendRegion.setNo(str);
			sendRegion.setLastUpdate(new Date());
			sendRegion.setLastUpdatedBy(getUserContext(request).getCzybh());
			sendRegion.setExpired("F");
			sendRegion.setActionLog("ADD");
			this.sendRegionService.save(sendRegion);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SendRegion失败");
		}
	}
	
	/**
	 * 修改SendRegion
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SendRegion sendRegion){
		logger.debug("修改SendRegion开始");
		try{
			sendRegion.setLastUpdate(new Date());
			sendRegion.setLastUpdatedBy(getUserContext(request).getCzybh());
			sendRegion.setActionLog("Edit");
			this.sendRegionService.update(sendRegion);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SendRegion失败");
		}
	}
	
	/**
	 * 删除SendRegion
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SendRegion开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SendRegion编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SendRegion sendRegion = sendRegionService.get(SendRegion.class, deleteId);
				if(sendRegion == null)
					continue;
				sendRegion.setExpired("T");
				sendRegionService.update(sendRegion);
			}
		}
		logger.debug("删除SendRegion IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SendRegion导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SendRegion sendRegion){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		sendRegionService.findSendRegionPageBySql(page, sendRegion);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"配送区域管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	// 检查Descr是否存在
	@RequestMapping("/checkDescr")
	@ResponseBody
	public JSONObject checkDescr(HttpServletRequest request,HttpServletResponse response,
			SendRegion sendRegion, String oldDescr){
		if(StringUtils.isEmpty(sendRegion.getDescr())){
			return this.out("传入的descr为空", false);
		} else if (sendRegion.getDescr().equals(oldDescr)) {
			return this.out("存在？", true);
		}
		boolean sr = sendRegionService.hasDescr("tSendRegion", sendRegion);

		if(sr){
			return this.out("系统中已存在descr="+sendRegion.getDescr()+"的信息", false);
		}
		return this.out("存在？", true);
	}
	
}
