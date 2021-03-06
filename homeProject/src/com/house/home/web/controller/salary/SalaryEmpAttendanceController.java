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
	 * ??????JqGrid????????????
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
	 * SalaryEmpAttendance??????
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
	 * SalaryEmpAttendance??????code
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
	 * ???????????????SalaryEmpAttendance??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????SalaryEmpAttendance??????");
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
	 * ???????????????SalaryEmpAttendance??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????SalaryEmpAttendance??????");
		SalaryEmpAttendance salaryEmpAttendance = salaryEmpAttendanceService.get(SalaryEmpAttendance.class, Integer.parseInt(id));
		salaryEmpAttendance.setM_umState("M");
		SalaryEmp salaryEmp = salaryEmpAttendanceService.get(SalaryEmp.class, salaryEmpAttendance.getSalaryEmp());

		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_save")
			.addObject("salaryEmpAttendance", salaryEmpAttendance)
			.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * ???????????????SalaryEmpAttendance??????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????SalaryEmpAttendance??????");
		SalaryEmpAttendance salaryEmpAttendance = salaryEmpAttendanceService.get(SalaryEmpAttendance.class, Integer.parseInt(id));
		salaryEmpAttendance.setM_umState("V");
		SalaryEmp salaryEmp = salaryEmpAttendanceService.get(SalaryEmp.class, salaryEmpAttendance.getSalaryEmp());

		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_save")
			.addObject("salaryEmpAttendance", salaryEmpAttendance)
			.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * ????????????Excel????????????
	 * @return
	 */
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request, HttpServletResponse response){
		logger.debug("????????????Excel????????????");
		return new ModelAndView("admin/salary/salaryEmpAttendance/salaryEmpAttendance_import");
	}
	
	/**
	 * ??????SalaryEmpAttendance
	 * @param request
	 * @param response
	 * @param salaryEmpAttendance
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SalaryEmpAttendance salaryEmpAttendance){
		logger.debug("??????SalaryEmpAttendance??????");
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
				ServletUtils.outPrintFail(request, response, empName+"??????"+salaryEmpAttendance.getSalaryMon().toString()+"????????????");
				return;
			}
			
			this.salaryEmpAttendanceService.save(salaryEmpAttendance);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????SalaryEmpAttendance??????");
		}
	}
	
	/**
	 * ??????SalaryEmpAttendance
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryEmpAttendance salaryEmpAttendance){
		logger.debug("??????SalaryEmpAttendance??????");
		try{
			salaryEmpAttendance.setLastUpdate(new Date());
			salaryEmpAttendance.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryEmpAttendance.setExpired("F");
			salaryEmpAttendance.setActionLog("ADD");
			
			List<Map<String, Object>> existsTypeList=this.salaryEmpAttendanceService.isExistsMon(salaryEmpAttendance);
			if(existsTypeList!=null && existsTypeList.size()>0){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
				return;
			}
			
			this.salaryEmpAttendanceService.update(salaryEmpAttendance);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????SalaryEmpAttendance??????");
		}
	}
	
	/**
	 * ??????SalaryEmpAttendance
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????SalaryEmpAttendance??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SalaryEmpAttendance??????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SalaryEmpAttendance salaryEmpAttendance = salaryEmpAttendanceService.get(SalaryEmpAttendance.class, Integer.parseInt(deleteId));
				if(salaryEmpAttendance == null)
					continue;
				//???????????????????????????????????????
				SalaryMon salaryMon= salaryEmpAttendanceService.get(SalaryMon.class, salaryEmpAttendance.getSalaryMon());
				if("3".equals(salaryMon.getStatus())){
					ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
					return;
				}
				salaryEmpAttendanceService.delete(salaryEmpAttendance);
			}
		}
		logger.debug("??????SalaryEmpAttendance IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}
	
	/**
	 * ????????????SalaryEmpAttendance
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDeleteBatch")
	public void doDeleteBatch(HttpServletRequest request, HttpServletResponse response, Integer salaryMon){
		logger.debug("????????????SalaryEmpAttendance??????");
		Long rows =salaryEmpAttendanceService.doDeleteBatch(salaryMon);
		ServletUtils.outPrintSuccess(request, response,"????????????"+rows+"??????");
	}

	/**
	 *SalaryEmpAttendance??????Excel
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
				"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * excel????????????
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
				// ??????????????? ????????????
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList = Lists.newArrayList("??????","????????????","??????????????????","????????????","??????????????????","??????????????????","????????????",
				"??????(???)","??????(??????)","??????(??????)","??????(??????)","??????(???)","?????????(???)","??????(???)","??????(???)");
		try {		
			List<SalaryEmpAttendanceBean> result=eUtils.importExcel(in,SalaryEmpAttendanceBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(SalaryEmpAttendanceBean salaryEmpAttendanceBean:result){
				DecimalFormat decimalFormat=new DecimalFormat("#.##");
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "??????");
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
					
			     /*	?????????????????????,????????????????????????????????????	
			        List<Map<String,Object>> empList=salaryEmpService.getEmpByParam("1", salaryEmpAttendanceBean.getEmpName(),"");
					if (empList!=null && empList.size()==1){
						Map<String,Object> empMap=empList.get(0);
						data.put("salaryemp", empMap.get("EmpCode").toString());
						data.put("empname", empMap.get("EmpName").toString());
					}else if(empList.size()>1){
						data.put("empname",salaryEmpAttendanceBean.getEmpName());
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "??????????????????????????????");
					}else{
						data.put("empname",salaryEmpAttendanceBean.getEmpName());
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "????????????????????????");
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
						data.put("isinvaliddescr", "??????????????????????????????");
					}else{
						data.put("empname",salaryEmpAttendanceBean.getEmpName());
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "????????????????????????");
					}
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????");
				}
				
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "??????????????????");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo","???????????????????????????????????????,??????????????????????????????????????????!");
			map.put("hasInvalid", true);
			return map;
		}
	}

	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSaveBatch")
	@ResponseBody
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,SalaryEmpAttendance salaryEmpAttendance){
		logger.debug("????????????????????????");
		try {
			salaryEmpAttendance.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.salaryEmpAttendanceService.doSaveBatch(salaryEmpAttendance);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}

}
