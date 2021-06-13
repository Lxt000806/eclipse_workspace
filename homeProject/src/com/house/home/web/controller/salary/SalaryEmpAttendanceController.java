package com.house.home.web.controller.salary;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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

import com.google.common.collect.Lists;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.design.SalaryEmpAttendanceBean;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryEmpAttendance;
import com.house.home.entity.salary.SalaryEmpAttendance;
import com.house.home.entity.salary.SalaryMon;
import com.house.home.service.salary.SalaryEmpAttendanceService;
import com.house.home.service.salary.SalaryEmpService;

@Controller
@RequestMapping("/admin/salaryEmpAttendance")
public class SalaryEmpAttendanceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SalaryEmpAttendanceController.class);

	@Autowired
	private SalaryEmpAttendanceService salaryEmpAttendanceService;
	
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
			HttpServletResponse response, SalaryEmpAttendance salaryEmpAttendance) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		salaryEmpAttendanceService.findPageBySql(page, salaryEmpAttendance);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SalaryEmpAttendance列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_list");
	}
	/**
	 * SalaryEmpAttendance查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_code");
	}
	/**
	 * 跳转到新增SalaryEmpAttendance页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SalaryEmpAttendance页面");
		SalaryEmpAttendance salaryEmpAttendance = null;
		if (StringUtils.isNotBlank(id)){
			salaryEmpAttendance = salaryEmpAttendanceService.get(SalaryEmpAttendance.class, Integer.parseInt(id));
			salaryEmpAttendance.setPk(null);
		}else{
			salaryEmpAttendance = new SalaryEmpAttendance();
		}
		salaryEmpAttendance.setM_umState("A");
		salaryEmpAttendance.setAbsentDays(0.0);
		salaryEmpAttendance.setAbsentTimes(0);
		salaryEmpAttendance.setLateTimes(0);
		salaryEmpAttendance.setLeaveDays(0.0);
		salaryEmpAttendance.setLeaveEarlyTimes(0);
		salaryEmpAttendance.setSeriousLateTimes(0);
		salaryEmpAttendance.setLateOverHourTimes(0);
		salaryEmpAttendance.setSalaryMon(salaryEmpAttendanceService.getDefSalaryMon());
		
		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_save")
			.addObject("salaryEmpAttendance", salaryEmpAttendance);
	}
	/**
	 * 跳转到修改SalaryEmpAttendance页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改SalaryEmpAttendance页面");
		SalaryEmpAttendance salaryEmpAttendance = salaryEmpAttendanceService.get(SalaryEmpAttendance.class, Integer.parseInt(id));
		salaryEmpAttendance.setM_umState("M");
		SalaryEmp salaryEmp = salaryEmpAttendanceService.get(SalaryEmp.class, salaryEmpAttendance.getSalaryEmp());

		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_save")
			.addObject("salaryEmpAttendance", salaryEmpAttendance)
			.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 跳转到查看SalaryEmpAttendance页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SalaryEmpAttendance页面");
		SalaryEmpAttendance salaryEmpAttendance = salaryEmpAttendanceService.get(SalaryEmpAttendance.class, Integer.parseInt(id));
		salaryEmpAttendance.setM_umState("V");
		SalaryEmp salaryEmp = salaryEmpAttendanceService.get(SalaryEmp.class, salaryEmpAttendance.getSalaryEmp());

		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_save")
			.addObject("salaryEmpAttendance", salaryEmpAttendance)
			.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 跳转到从Excel导入页面
	 * @return
	 */
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到从Excel导入页面");
		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_import");
	}
	
	/**
	 * 添加SalaryEmpAttendance
	 * @param request
	 * @param response
	 * @param salaryEmpAttendance
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SalaryEmpAttendance salaryEmpAttendance){
		logger.debug("添加SalaryEmpAttendance开始");
		try{
			String empName = "";
			SalaryEmp salaryEmp = new SalaryEmp();
			salaryEmpAttendance.setImportDate(new Date());
			salaryEmpAttendance.setImportCzy(getUserContext(request).getCzybh());
			salaryEmpAttendance.setLastUpdate(new Date());
			salaryEmpAttendance.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryEmpAttendance.setExpired("F");
			salaryEmpAttendance.setActionLog("ADD");
			
			if(StringUtils.isNotBlank(salaryEmpAttendance.getSalaryEmp())){
				salaryEmp = salaryEmpAttendanceService.get(SalaryEmp.class, salaryEmpAttendance.getSalaryEmp());
				if(salaryEmp != null){
					empName = salaryEmp.getEmpName();
				}
			}
			
			List<Map<String, Object>> existsTypeList=this.salaryEmpAttendanceService.isExistsMon(salaryEmpAttendance);
			if(existsTypeList!=null && existsTypeList.size()>0){
				ServletUtils.outPrintFail(request, response, empName+"已有"+salaryEmpAttendance.getSalaryMon().toString()+"考勤记录");
				return;
			}
			
			this.salaryEmpAttendanceService.save(salaryEmpAttendance);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加SalaryEmpAttendance失败");
		}
	}
	
	/**
	 * 修改SalaryEmpAttendance
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryEmpAttendance salaryEmpAttendance){
		logger.debug("修改SalaryEmpAttendance开始");
		try{
			salaryEmpAttendance.setLastUpdate(new Date());
			salaryEmpAttendance.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryEmpAttendance.setExpired("F");
			salaryEmpAttendance.setActionLog("ADD");
			
			List<Map<String, Object>> existsTypeList=this.salaryEmpAttendanceService.isExistsMon(salaryEmpAttendance);
			if(existsTypeList!=null && existsTypeList.size()>0){
				ServletUtils.outPrintFail(request, response, "同一人员同一月份的记录不可重复");
				return;
			}
			
			this.salaryEmpAttendanceService.update(salaryEmpAttendance);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改SalaryEmpAttendance失败");
		}
	}
	
	/**
	 * 删除SalaryEmpAttendance
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SalaryEmpAttendance开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SalaryEmpAttendance编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SalaryEmpAttendance salaryEmpAttendance = salaryEmpAttendanceService.get(SalaryEmpAttendance.class, Integer.parseInt(deleteId));
				if(salaryEmpAttendance == null)
					continue;
				//不允许删除已结算的薪酬月份
				SalaryMon salaryMon= salaryEmpAttendanceService.get(SalaryMon.class, salaryEmpAttendance.getSalaryMon());
				if("3".equals(salaryMon.getStatus())){
					ServletUtils.outPrintFail(request, response, "薪酬月份已结算，不允许删除");
					return;
				}
				salaryEmpAttendanceService.delete(salaryEmpAttendance);
			}
		}
		logger.debug("删除SalaryEmpAttendance IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 批量删除SalaryEmpAttendance
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDeleteBatch")
	public void doDeleteBatch(HttpServletRequest request, HttpServletResponse response, Integer salaryMon){
		logger.debug("批量删除SalaryEmpAttendance开始");
		Long rows =salaryEmpAttendanceService.doDeleteBatch(salaryMon);
		ServletUtils.outPrintSuccess(request, response,"成功删除"+rows+"记录");
	}

	/**
	 *SalaryEmpAttendance导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SalaryEmpAttendance salaryEmpAttendance){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		salaryEmpAttendanceService.findPageBySql(page, salaryEmpAttendance);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"考勤信息管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
		ExcelImportUtils<SalaryEmpAttendanceBean> eUtils=new ExcelImportUtils<SalaryEmpAttendanceBean>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		InputStream in=null;
		List<Map<String,Object>> empList=salaryEmpService.getEmpByParam("1","","");
		Integer empCount=0;
		String salaryemp="";
		
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
				// 如果是普通 表单参数
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList = Lists.newArrayList("姓名","迟到次数","严重迟到次数","早退次数","上班缺卡次数","下班缺卡次数","旷工天数",
				"年假(天)","事假(小时)","病假(小时)","调休(小时)","产假(天)","陪产假(天)","婚假(天)","丧假(天)");
		try {		
			List<SalaryEmpAttendanceBean> result=eUtils.importExcel(in,SalaryEmpAttendanceBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(SalaryEmpAttendanceBean salaryEmpAttendanceBean:result){
				DecimalFormat decimalFormat=new DecimalFormat("#.##");
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "有效");
				data.put("goabsenttimes", salaryEmpAttendanceBean.getGoAbsentTimes()==null?0:salaryEmpAttendanceBean.getGoAbsentTimes());
				data.put("outabsenttimes", salaryEmpAttendanceBean.getOutAbsentTimes()==null?0:salaryEmpAttendanceBean.getOutAbsentTimes());
				data.put("yearleave", salaryEmpAttendanceBean.getYearLeave()==null?0:salaryEmpAttendanceBean.getYearLeave());
				data.put("eventleave", salaryEmpAttendanceBean.getEventLeave()==null?0:salaryEmpAttendanceBean.getEventLeave());
				data.put("sickleave", salaryEmpAttendanceBean.getSickLeave()==null?0:salaryEmpAttendanceBean.getSickLeave());
				data.put("compensatoryleave", salaryEmpAttendanceBean.getCompensatoryLeave()==null?0:salaryEmpAttendanceBean.getCompensatoryLeave());
				data.put("maternityleave", salaryEmpAttendanceBean.getMaternityLeave()==null?0:salaryEmpAttendanceBean.getMaternityLeave());
				data.put("accompanymaternityleave", salaryEmpAttendanceBean.getAccompanyMaternityLeave()==null?0:salaryEmpAttendanceBean.getAccompanyMaternityLeave());
				data.put("marryleave", salaryEmpAttendanceBean.getMarryLeave()==null?0:salaryEmpAttendanceBean.getMarryLeave());
				data.put("bereavementleave", salaryEmpAttendanceBean.getBereavementLeave()==null?0:salaryEmpAttendanceBean.getBereavementLeave());
				data.put("absenttimes", Integer.parseInt(data.get("goabsenttimes").toString())+Integer.parseInt(data.get("outabsenttimes").toString()));
				data.put("absentdays", salaryEmpAttendanceBean.getAbsentDays()==null?0:salaryEmpAttendanceBean.getAbsentDays());
				data.put("latetimes", salaryEmpAttendanceBean.getLateTimes()==null?0:salaryEmpAttendanceBean.getLateTimes());
				data.put("leavedays", Double.parseDouble(data.get("maternityleave").toString())+
						Double.parseDouble(decimalFormat.format((Double.parseDouble(data.get("eventleave").toString()) + Double.parseDouble(data.get("sickleave").toString()))/7)));
				data.put("leaveearlytimes", salaryEmpAttendanceBean.getLeaveEarlyTimes()==null?0:salaryEmpAttendanceBean.getLeaveEarlyTimes());
				data.put("seriouslatetimes", salaryEmpAttendanceBean.getSeriousLateTimes()==null?0:salaryEmpAttendanceBean.getSeriousLateTimes());
				data.put("lateoverhourtimes", 0);
				
				if(StringUtils.isNotBlank(salaryEmpAttendanceBean.getEmpName())){
					
			     /*	这段代码太慢了,改成从数据库中一次性取值	
			        List<Map<String,Object>> empList=salaryEmpService.getEmpByParam("1", salaryEmpAttendanceBean.getEmpName(),"");
					if (empList!=null && empList.size()==1){
						Map<String,Object> empMap=empList.get(0);
						data.put("salaryemp", empMap.get("EmpCode").toString());
						data.put("empname", empMap.get("EmpName").toString());
					}else if(empList.size()>1){
						data.put("empname",salaryEmpAttendanceBean.getEmpName());
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，匹配到多个姓名");
					}else{
						data.put("empname",salaryEmpAttendanceBean.getEmpName());
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，姓名不匹配");
					}
				  */
					empCount = 0;
					salaryemp = "";
					for(Map<String, Object> empMap : empList){
						if (salaryEmpAttendanceBean.getEmpName().equals(empMap.get("EmpName").toString()) ){
							if(empCount==0){
								salaryemp=empMap.get("EmpCode").toString();
							}
							empCount++;		
						}
			            
					}
					if (empCount==1){
						data.put("salaryemp", salaryemp);
						data.put("empname", salaryEmpAttendanceBean.getEmpName());
					}else if(empCount>1){
						data.put("empname",salaryEmpAttendanceBean.getEmpName());
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，匹配到多个姓名");
					}else{
						data.put("empname",salaryEmpAttendanceBean.getEmpName());
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，姓名不匹配");
					}
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "无效，姓名为空");
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
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,SalaryEmpAttendance salaryEmpAttendance){
		logger.debug("考勤信息批量导入");
		try {
			salaryEmpAttendance.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.salaryEmpAttendanceService.doSaveBatch(salaryEmpAttendance);
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
