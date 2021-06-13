package com.house.home.web.controller.salary;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.design.SalaryEmpAttendanceBean;
import com.house.home.bean.design.SalaryEmpOverTimeBean;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryEmpDeduction;
import com.house.home.entity.salary.SalaryEmpOverTime;
import com.house.home.entity.salary.SalaryMon;
import com.house.home.service.salary.SalaryEmpOverTimeService;
import com.house.home.service.salary.SalaryEmpService;

@Controller
@RequestMapping("/admin/salaryEmpOverTime")
public class SalaryEmpOverTimeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SalaryEmpOverTimeController.class);

	@Autowired
	private SalaryEmpOverTimeService salaryEmpOverTimeService;
	
	@Autowired
	private SalaryEmpService salaryEmpService;

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
			HttpServletResponse response, SalaryEmpOverTime salaryEmpOverTime) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		salaryEmpOverTimeService.findPageBySql(page, salaryEmpOverTime);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SalaryEmpOverTime列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/salaryEmpOverTime/salaryEmpOverTime_list");
	}
	/**
	 * SalaryEmpOverTime查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/salaryEmpOverTime/salaryEmpOverTime_code");
	}
	/**
	 * 跳转到新增SalaryEmpOverTime页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SalaryEmpOverTime页面");
		SalaryEmpOverTime salaryEmpOverTime = null;
		if (StringUtils.isNotBlank(id)){
			salaryEmpOverTime = salaryEmpOverTimeService.get(SalaryEmpOverTime.class, Integer.parseInt(id));
			salaryEmpOverTime.setPk(null);
		}else{
			salaryEmpOverTime = new SalaryEmpOverTime();
		}
		salaryEmpOverTime.setM_umState("A");
		return new ModelAndView("admin/salary/salaryEmpOverTime/salaryEmpOverTime_save")
			.addObject("salaryEmpOverTime", salaryEmpOverTime);
	}
	/**
	 * 跳转到修改SalaryEmpOverTime页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改SalaryEmpOverTime页面");
		SalaryEmpOverTime salaryEmpOverTime = salaryEmpOverTimeService.get(SalaryEmpOverTime.class, Integer.parseInt(id));
		salaryEmpOverTime.setM_umState("M");
		SalaryEmp salaryEmp = salaryEmpOverTimeService.get(SalaryEmp.class, salaryEmpOverTime.getSalaryEmp());
		return new ModelAndView("admin/salary/salaryEmpOverTime/salaryEmpOverTime_save")
				.addObject("salaryEmpOverTime", salaryEmpOverTime)
				.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 跳转到查看SalaryEmpOverTime页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SalaryEmpOverTime页面");
		SalaryEmpOverTime salaryEmpOverTime = salaryEmpOverTimeService.get(SalaryEmpOverTime.class, Integer.parseInt(id));
		salaryEmpOverTime.setM_umState("V");
		SalaryEmp salaryEmp = salaryEmpOverTimeService.get(SalaryEmp.class, salaryEmpOverTime.getSalaryEmp());
		return new ModelAndView("admin/salary/salaryEmpOverTime/salaryEmpOverTime_save")
				.addObject("salaryEmpOverTime", salaryEmpOverTime)
				.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 跳转到从Excel导入页面
	 * @return
	 */
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到从Excel导入页面");
		return new ModelAndView("admin/salary/salaryEmpOverTime/salaryEmpOverTime_import");
	}
	
	/**
	 * 添加SalaryEmpOverTime
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SalaryEmpOverTime salaryEmpOverTime){
		logger.debug("添加SalaryEmpOverTime开始");
		try{
			salaryEmpOverTime.setRegisterDate(new Date());
			salaryEmpOverTime.setRegisterCzy(getUserContext(request).getCzybh());
			salaryEmpOverTime.setLastUpdate(new Date());
			salaryEmpOverTime.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryEmpOverTime.setExpired("F");
			salaryEmpOverTime.setActionLog("ADD");
			
			List<Map<String, Object>> existsTypeList=this.salaryEmpOverTimeService.isExistsMon(salaryEmpOverTime);
			if(existsTypeList!=null && existsTypeList.size()>0){
				ServletUtils.outPrintFail(request, response, "同一人员同一月份的记录不可重复");
				return;
			}
			
			this.salaryEmpOverTimeService.save(salaryEmpOverTime);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SalaryEmpOverTime失败");
		}
	}
	
	/**
	 * 修改SalaryEmpOverTime
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryEmpOverTime salaryEmpOverTime){
		logger.debug("修改SalaryEmpOverTime开始");
		try{
			salaryEmpOverTime.setLastUpdate(new Date());
			salaryEmpOverTime.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryEmpOverTime.setExpired("F");
			salaryEmpOverTime.setActionLog("EDIT");
			
			List<Map<String, Object>> existsTypeList=this.salaryEmpOverTimeService.isExistsMon(salaryEmpOverTime);
			if(existsTypeList!=null && existsTypeList.size()>0){
				ServletUtils.outPrintFail(request, response, "同一人员同一月份的记录不可重复");
				return;
			}
			
			this.salaryEmpOverTimeService.update(salaryEmpOverTime);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SalaryEmpOverTime失败");
		}
	}
	
	/**
	 * 删除SalaryEmpOverTime
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SalaryEmpOverTime开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SalaryEmpOverTime编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SalaryEmpOverTime salaryEmpOverTime = salaryEmpOverTimeService.get(SalaryEmpOverTime.class, Integer.parseInt(deleteId));
				if(salaryEmpOverTime == null)
					continue;
				//不允许删除已结算的薪酬月份
				SalaryMon salaryMon= salaryEmpOverTimeService.get(SalaryMon.class, salaryEmpOverTime.getSalaryMon());
				if("3".equals(salaryMon.getStatus())){
					ServletUtils.outPrintFail(request, response, "薪酬月份已结算，不允许删除");
					return;
				}
				salaryEmpOverTimeService.delete(salaryEmpOverTime);
			}
		}
		logger.debug("删除SalaryEmpOverTime IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SalaryEmpOverTime导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SalaryEmpOverTime salaryEmpOverTime){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		salaryEmpOverTimeService.findPageBySql(page, salaryEmpOverTime);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"加班信息管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 * excel加载数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<SalaryEmpOverTimeBean> eUtils=new ExcelImportUtils<SalaryEmpOverTimeBean>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
				// 如果是普通 表单参数
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList.add("人员工号");
		titleList.add("加班次数");
		titleList.add("备注");
		try {		
			List<SalaryEmpOverTimeBean> result=eUtils.importExcel(in,SalaryEmpOverTimeBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(SalaryEmpOverTimeBean salaryEmpOverTimeBean:result){
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "有效");
				data.put("times", salaryEmpOverTimeBean.getTimes()==null?0:salaryEmpOverTimeBean.getTimes());
				data.put("remarks", salaryEmpOverTimeBean.getRemarks());
				if(StringUtils.isNotBlank(salaryEmpOverTimeBean.getSalaryEmp())){
					data.put("salaryemp", salaryEmpOverTimeBean.getSalaryEmp());
					SalaryEmp salaryEmp = salaryEmpOverTimeService.get(SalaryEmp.class, salaryEmpOverTimeBean.getSalaryEmp());
					if (salaryEmp!=null){
						data.put("empname", salaryEmp.getEmpName());
					}else{
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，人员工号不匹配");
					}	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "无效，人员工号不匹配");
				}
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "数据加载完成");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo","当前操作使用了错误类型的值,请检查数值列是否包含非法字符!");
			map.put("hasInvalid", true);
			return map;
		}
	}

	/**
	 * 批量新增，调存储过程
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSaveBatch")
	@ResponseBody
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,SalaryEmpOverTime salaryEmpOverTime){
		logger.debug("加班信息批量导入");
		try {
			salaryEmpOverTime.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.salaryEmpOverTimeService.doSaveBatch(salaryEmpOverTime);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "批量导入失败");
		}
	}
}
