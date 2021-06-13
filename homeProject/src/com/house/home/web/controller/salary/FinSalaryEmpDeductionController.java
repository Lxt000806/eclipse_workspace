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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.design.CustPayBean;
import com.house.home.bean.design.SalaryEmpDeductionBean;
import com.house.home.entity.basic.BankPos;
import com.house.home.entity.basic.RcvAct;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryEmpDeduction;
import com.house.home.entity.salary.SalaryMon;
import com.house.home.service.salary.FinSalaryEmpDeductionService;
import com.house.home.service.salary.SalaryEmpService;
import com.house.home.service.salary.SalaryMonService;

@Controller
@RequestMapping("/admin/finSalaryEmpDeduction")
public class FinSalaryEmpDeductionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FinSalaryEmpDeductionController.class);

	@Autowired
	private FinSalaryEmpDeductionService finSalaryEmpDeductionService;
	
	@Autowired
	private SalaryEmpService salaryEmpService;
	
	@Autowired
	private SalaryMonService salaryMonService;

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
			HttpServletResponse response, SalaryEmpDeduction finSalaryEmpDeduction) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		finSalaryEmpDeductionService.findPageBySql(page, finSalaryEmpDeduction);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * FinSalaryEmpDeduction列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/finSalaryEmpDeduction/finSalaryEmpDeduction_list");
	}
	/**
	 * FinSalaryEmpDeduction查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/finSalaryEmpDeduction/finSalaryEmpDeduction_code");
	}
	/**
	 * 跳转到新增FinSalaryEmpDeduction页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增FinSalaryEmpDeduction页面");
		SalaryEmpDeduction finSalaryEmpDeduction = null;
		if (StringUtils.isNotBlank(id)){
			finSalaryEmpDeduction = finSalaryEmpDeductionService.get(SalaryEmpDeduction.class, Integer.parseInt(id));
			finSalaryEmpDeduction.setPk(null);
		}else{
			finSalaryEmpDeduction = new SalaryEmpDeduction();
		}
		finSalaryEmpDeduction.setM_umState("A");
		
		List<Map<String, Object>> monList=salaryMonService.getCheckingMon();
		if(monList!=null && monList.size()>0){
			Integer salaryMon=Integer.parseInt(monList.get(0).get("SalaryMon").toString());
			finSalaryEmpDeduction.setSalaryMon(salaryMon);
		}
		
		return new ModelAndView("admin/salary/finSalaryEmpDeduction/finSalaryEmpDeduction_save")
			.addObject("finSalaryEmpDeduction", finSalaryEmpDeduction);
	}
	/**
	 * 跳转到修改FinSalaryEmpDeduction页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改FinSalaryEmpDeduction页面");
		SalaryEmpDeduction finSalaryEmpDeduction = finSalaryEmpDeductionService.get(SalaryEmpDeduction.class, Integer.parseInt(id));
		finSalaryEmpDeduction.setM_umState("M");
		SalaryEmp salaryEmp = finSalaryEmpDeductionService.get(SalaryEmp.class, finSalaryEmpDeduction.getSalaryEmp());
		return new ModelAndView("admin/salary/finSalaryEmpDeduction/finSalaryEmpDeduction_save")
				.addObject("finSalaryEmpDeduction", finSalaryEmpDeduction)
				.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 跳转到查看FinSalaryEmpDeduction页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看FinSalaryEmpDeduction页面");
		SalaryEmpDeduction finSalaryEmpDeduction = finSalaryEmpDeductionService.get(SalaryEmpDeduction.class, Integer.parseInt(id));
		finSalaryEmpDeduction.setM_umState("V");
		SalaryEmp salaryEmp = finSalaryEmpDeductionService.get(SalaryEmp.class, finSalaryEmpDeduction.getSalaryEmp());
		return new ModelAndView("admin/salary/finSalaryEmpDeduction/finSalaryEmpDeduction_save")
				.addObject("finSalaryEmpDeduction", finSalaryEmpDeduction)
				.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 跳转到从Excel导入页面
	 * @return
	 */
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到从Excel导入页面");
		return new ModelAndView("admin/salary/finSalaryEmpDeduction/finSalaryEmpDeduction_import");
	}
	
	/**
	 * 添加FinSalaryEmpDeduction
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SalaryEmpDeduction finSalaryEmpDeduction){
		logger.debug("添加FinSalaryEmpDeduction开始");
		try{
			finSalaryEmpDeduction.setDeductDate(new Date());
			finSalaryEmpDeduction.setDeductType1("1");
			finSalaryEmpDeduction.setLastUpdate(new Date());
			finSalaryEmpDeduction.setLastUpdatedBy(getUserContext(request).getCzybh());
			finSalaryEmpDeduction.setExpired("F");
			finSalaryEmpDeduction.setActionLog("ADD");
			this.finSalaryEmpDeductionService.save(finSalaryEmpDeduction);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加FinSalaryEmpDeduction失败");
		}
	}
	
	/**
	 * 修改FinSalaryEmpDeduction
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryEmpDeduction finSalaryEmpDeduction){
		logger.debug("修改FinSalaryEmpDeduction开始");
		try{
			finSalaryEmpDeduction.setDeductType1("1");
			finSalaryEmpDeduction.setLastUpdate(new Date());
			finSalaryEmpDeduction.setLastUpdatedBy(getUserContext(request).getCzybh());
			finSalaryEmpDeduction.setExpired("F");
			finSalaryEmpDeduction.setActionLog("EDIT");
			this.finSalaryEmpDeductionService.update(finSalaryEmpDeduction);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改FinSalaryEmpDeduction失败");
		}
	}
	
	/**
	 * 删除FinSalaryEmpDeduction
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除FinSalaryEmpDeduction开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "FinSalaryEmpDeduction编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SalaryEmpDeduction finSalaryEmpDeduction = finSalaryEmpDeductionService.get(SalaryEmpDeduction.class, Integer.parseInt(deleteId));
				if(finSalaryEmpDeduction == null)
					continue;
				//不允许删除已结算的薪酬月份
				SalaryMon salaryMon= finSalaryEmpDeductionService.get(SalaryMon.class, finSalaryEmpDeduction.getSalaryMon());
				if("3".equals(salaryMon.getStatus())){
					ServletUtils.outPrintFail(request, response, "薪酬月份已结算，不允许删除");
					return;
				}
				finSalaryEmpDeductionService.delete(finSalaryEmpDeduction);
			}
		}
		logger.debug("删除FinSalaryEmpDeduction IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *FinSalaryEmpDeduction导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SalaryEmpDeduction finSalaryEmpDeduction){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		finSalaryEmpDeductionService.findPageBySql(page, finSalaryEmpDeduction);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"财务扣款管理"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
		ExcelImportUtils<SalaryEmpDeductionBean> eUtils=new ExcelImportUtils<SalaryEmpDeductionBean>();
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
		titleList.add("财务编码");
		titleList.add("扣款科目");
		titleList.add("扣款内容");
		titleList.add("扣款金额");
		try {		
			List<SalaryEmpDeductionBean> result=eUtils.importExcel(in,SalaryEmpDeductionBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(SalaryEmpDeductionBean salaryEmpDeductionBean:result){
				
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "有效");
				data.put("amount", salaryEmpDeductionBean.getAmount()==null?0:salaryEmpDeductionBean.getAmount());
				data.put("remarks", salaryEmpDeductionBean.getRemarks());
				if(StringUtils.isNotBlank(salaryEmpDeductionBean.getFinancialCode())){
					data.put("financialcode", salaryEmpDeductionBean.getFinancialCode());
					List<Map<String,Object>> empList=salaryEmpService.getEmpByParam("", "",salaryEmpDeductionBean.getFinancialCode());
					if (empList!=null && empList.size()>0){
						Map<String,Object> empMap=empList.get(0);
						data.put("salaryemp", empMap.get("EmpCode").toString());
						data.put("empname", empMap.get("EmpName").toString());
					}else{
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，财务编码不匹配");
					}	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "无效，财务编码不匹配");
				}
				
				if(StringUtils.isNotBlank(salaryEmpDeductionBean.getDeductType2())){
					data.put("deducttype2descr", salaryEmpDeductionBean.getDeductType2());
					List<Map<String,Object>> deductTypeList=finSalaryEmpDeductionService.getDeductType2(salaryEmpDeductionBean.getDeductType2());
					if(deductTypeList!=null && deductTypeList.size()>0){
						Map<String,Object> deductTypeMap=deductTypeList.get(0);
						data.put("deducttype2", deductTypeMap.get("Code").toString());
					}else{
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，扣款科目有误");
					}
				}
				if("0".equals(data.get("isinvalid"))){
					data.put("isinvaliddescr", data.get("isinvaliddescr")
							+ "【姓名：" + salaryEmpDeductionBean.getEmpName()
							+ "，岗位：" + salaryEmpDeductionBean.getPositionDescr() 
							+ "，一级部门："+salaryEmpDeductionBean.getDepartment1()
							+ "】"
					);
				};
				data.put("empname", salaryEmpDeductionBean.getEmpName());
				data.put("positiondescr", salaryEmpDeductionBean.getPositionDescr());
				data.put("department1", salaryEmpDeductionBean.getDepartment1());
				data.put("department2", salaryEmpDeductionBean.getDepartment2());
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
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,SalaryEmpDeduction finSalaryEmpDeduction){
		logger.debug("财务扣款批量导入");
		try {
			finSalaryEmpDeduction.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			finSalaryEmpDeduction.setDeductType1("1");
			Result result = this.finSalaryEmpDeductionService.doSaveBatch(finSalaryEmpDeduction);
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
