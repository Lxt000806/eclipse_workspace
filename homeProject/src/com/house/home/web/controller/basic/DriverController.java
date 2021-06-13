package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Driver;
import com.house.home.service.basic.DriverService;

@Controller
@RequestMapping("/admin/driver")
public class DriverController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(DriverController.class);
	
	@Autowired
	private DriverService driverService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request, HttpServletResponse response,Driver driver) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		driverService.findPageBySql(page, driver);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 根据司机编号查询司机详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getDriver")
	@ResponseBody
	public JSONObject getDriver(HttpServletRequest request, HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Driver driver = driverService.get(Driver.class, id);
		if(driver == null){
			return this.out("系统中不存在code="+id+"的司机信息", false);
		}
		return this.out(driver, true);
	}
	
	/**
	 * Driver列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/driver/driver_list");
	}
	
	/**
	 * 司机Code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/basic/driver/driver_code");
	}
	
	/**
	 * 跳转到新增司机页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增项司机页面");
		Driver driver = new Driver();
		
		return new ModelAndView("admin/basic/driver/driver_save")
			.addObject("driver", driver);
	}
	/**
	 * 跳转到修改司机信息页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("跳转到修改司机信息页面");
		Driver driver = null;
		if (StringUtils.isNotBlank(id)){
			driver = driverService.get(Driver.class, id);
			if ("1899-12-30 00:00:00.0".equals((driver.getJoinDate()==null?"1899-12-30 00:00:00.0":driver.getJoinDate().toString()))) {
				driver.setJoinDate(null);
			}
			if ("1899-12-30 00:00:00.0".equals((driver.getLeaveDate()==null?"1899-12-30 00:00:00.0":driver.getLeaveDate()).toString())) {
				driver.setLeaveDate(null);
			}
		}else{
			driver = new Driver();
		}
		
		return new ModelAndView("admin/basic/driver/driver_update")
			.addObject("driver", driver);
	}
	
	/**
	 * 跳转到查看司机信息页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){ 
		logger.debug("跳转到查看司机信息页面");
		Driver driver = driverService.get(Driver.class, id);
		if ("1899-12-30 00:00:00.0".equals((driver.getJoinDate()==null?"1899-12-30 00:00:00.0":driver.getJoinDate()).toString())) {
			driver.setJoinDate(null);
		}
		if ("1899-12-30 00:00:00.0".equals((driver.getLeaveDate()==null?"1899-12-30 00:00:00.0":driver.getLeaveDate()).toString())) {
			driver.setLeaveDate(null);
		}
		return new ModelAndView("admin/basic/driver/driver_detail")
				.addObject("driver", driver);
	}
	
	/**
	 * 添加司机
	 * @param request
	 * @param responses  
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Driver driver){
		logger.debug("添加司机开始");
		try{
			Driver xt = this.driverService.getByName(driver.getNameChi());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "司机名字重复");
				return;
			}
			driver.setM_umState("A");
			driver.setLastUpdate(new Date());
			driver.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			driver.setExpired("F");
			driver.setActionLog("ADD");
			if (null == driver.getJoinDate()) {
				driver.setJoinDate(DateUtil.DateFormatString("1899-12-30 00:00:00.000"));
			}
			Result result = this.driverService.doSave(driver);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加司机失败"); 
		}
	}
	
	/**
	 * 修改司机信息
	 * @param request
	 * @param response
	 * @param driver 正在被修改的driver
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Driver driver){
		logger.debug("修改司机信息开始");
		try{
            Driver queryDriver = this.driverService.getByName(driver.getNameChi());
			if (queryDriver!=null && !queryDriver.getCode().equals(driver.getCode())){
				ServletUtils.outPrintFail(request, response, "司机重复");
				return;
			}else{
				driver.setM_umState("M");
				driver.setLastUpdate(new Date());
				driver.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				driver.setActionLog("Edit");
				if (null == driver.getJoinDate()) {
					driver.setJoinDate(DateUtil.DateFormatString("1899-12-30 00:00:00.000"));
				}
				Result result = this.driverService.doSave(driver);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改司机信息失败");
		}
	}
	
	/**
	 * 删除司机信息 （令Expired=T，数据不存在ERP，仍存在SQL中）
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除司机信息开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "司机编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Driver driver = this.driverService.get(Driver.class, deleteId);
				if(driver == null)
					continue;
				driver.setExpired("T");
				this.driverService.update(driver);
			}
		}
		logger.debug("删除司机信息 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 司机信息导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, Driver driver){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		driverService.findPageBySql(page, driver);  
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(), 
				"司机信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/checkBlur")
	public void checkBlur(HttpServletRequest request, HttpServletResponse response, String id, String info){
		logger.debug("check司机姓名开始");   
		try{
			if (StringUtils.isNotBlank(id)){
				Driver driver = driverService.get(Driver.class, id);
				if (driver!=null){
					JSONObject obj = new JSONObject();
					obj.put("nameChi", driver.getNameChi());
					ServletUtils.outPrintSuccess(request, response, obj);
				}else{
					if (StringUtils.isBlank(info)){
						info = "信息";
					}
					ServletUtils.outPrintFail(request, response, "该"+info+"不存在，请重新输入");
				}
			}else{
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "获取司机失败"); 
		}
	}
	
	/**
	 * @Description: TODO 检查手机号码是否重复
	 * @author	created by zb
	 * @date	2018-11-5--下午4:09:24
	 * @param request
	 * @param response
	 * @param phone
	 * @return false:重复；true:不重复
	 */
	@RequestMapping("/checkPhone")
	@ResponseBody
	public JSONObject checkPhone(HttpServletRequest request,HttpServletResponse response,String phone,
			String oldPhone){
		if(StringUtils.isEmpty(phone)){
			return this.out("", false);
		}
		//如果和原来的相等，返回true
		if (phone.equals(oldPhone)) {
			return this.out("", true);
		}
		Boolean p = driverService.checkPhone(phone);

		if(p){
			return this.out("", true);
		}
		return this.out("", false);
	}
}
