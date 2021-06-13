package com.house.home.web.controller.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.SendTime;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.WorkCost;
import com.house.home.service.basic.SendTimeService;

@Controller
@RequestMapping("/admin/sendTime")
public class SendTimeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SendTimeController.class);

	@Autowired
	private SendTimeService sendTimeService;

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
			HttpServletResponse response, SendTime sendTime) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sendTimeService.findPageBySql(page, sendTime);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SendTime列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/sendTime/sendTime_list");
	}
	/**
	 * SendTime查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/sendTime/sendTime_code");
	}
	/**
	 * 跳转到新增SendTime页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SendTime页面");
		SendTime sendTime = null;
		if (StringUtils.isNotBlank(id)){
			sendTime = sendTimeService.get(SendTime.class, id);
			sendTime.setNo(null);
		}else{
			sendTime = new SendTime();
		}
		sendTime.setPrior(0);
		sendTime.setSendDay(0);
		sendTime.setM_umState("A");
		sendTime.setIsSetItem("0");
		sendTime.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView("admin/basic/sendTime/sendTime_save")
			.addObject("sendTime", sendTime);
	}
	/**
	 * 跳转到修改SendTime页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改SendTime页面");
		SendTime sendTime = sendTimeService.get(SendTime.class, id);
		sendTime.setM_umState("M");
		sendTime.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView("admin/basic/sendTime/sendTime_save")
			.addObject("sendTime", sendTime);
	}
	
	/**
	 * 跳转到查看SendTime页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SendTime页面");
		SendTime sendTime = sendTimeService.get(SendTime.class, id);
		sendTime.setM_umState("V");
		return new ModelAndView("admin/basic/sendTime/sendTime_save")
				.addObject("sendTime", sendTime);
	}
	
	/**
	 * 修改SendTime
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SendTime sendTime){
		logger.debug("修改SendTime开始");
		try{
			sendTime.setLastUpdate(new Date());
			sendTime.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.sendTimeService.update(sendTime);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SendTime失败");
		}
	}
	
	/**
	 * 删除SendTime
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SendTime开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SendTime编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SendTime sendTime = sendTimeService.get(SendTime.class, deleteId);
				if(sendTime == null)
					continue;
				sendTime.setExpired("T");
				sendTimeService.update(sendTime);
			}
		}
		logger.debug("删除SendTime IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SendTime导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SendTime sendTime){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		sendTimeService.findPageBySql(page, sendTime);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"发货时限管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到新增SendTime页面
	 * @return
	 */
	@RequestMapping("/goDetailAdd")
	public ModelAndView goDetailAdd(HttpServletRequest request, HttpServletResponse response, 
			SendTime sendTime){
		logger.debug("跳转到新增SendTimeDetail页面");
		return new ModelAndView("admin/basic/sendTime/sendTime_detail_add")
			.addObject("sendTime", sendTime);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, SendTime sendTime) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sendTimeService.goDetailGrid(page, sendTime);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 供应商是否引用发货时限
	 * @param request
	 * @param sendTime
	 * @return
	 */
	@RequestMapping("/isSupplierTime")
	@ResponseBody
	public List<Map<String, Object>> isSupplierTime(HttpServletRequest request,SendTime sendTime){
		return sendTimeService.isSupplierTime(sendTime);
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param workCost
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,SendTime sendTime){
		logger.debug("保存");		
		try {	
			sendTime.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.sendTimeService.doSaveProc(sendTime);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
}
