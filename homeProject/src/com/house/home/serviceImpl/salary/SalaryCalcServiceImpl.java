package com.house.home.serviceImpl.salary;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.cmd.NeedsActiveExecutionCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalaryCalcDao;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryDataAdjust;
import com.house.home.entity.salary.SalaryStatusCtrl;
import com.house.home.service.salary.SalaryCalcService;
import com.sun.org.apache.regexp.internal.RESyntaxException;

@SuppressWarnings("serial")
@Service 
public class SalaryCalcServiceImpl extends BaseServiceImpl implements SalaryCalcService{

	
	@Autowired
	private SalaryCalcDao salaryCalcDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		return salaryCalcDao.findPageBySql(page, salaryData);
	}
	
	
	
	@Override
	public Page<Map<String, Object>> getPaymentDetail(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		
		return salaryCalcDao.getPaymentDetail(page, salaryData);
	}

	@Override
	public List<Map<String, Object>> getMainPageData(SalaryData salaryData) {

		return salaryCalcDao.getMainPageData(salaryData);
	}
	
	@Override
	public List<Map<String, Object>> getPaymentDetailPageData(
			SalaryData salaryData) {

		return salaryCalcDao.getPaymentDetailPageData(salaryData);
	}

	@Override
	public Page<Map<String, Object>> getSalaryChgByJqgrid(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		
		return salaryCalcDao.getSalaryChgByJqgrid(page, salaryData);
	}

	@Override
	public Integer getCalcMon() {
		
		return salaryCalcDao.getCalcMon();
	}
	
	@Override
	public Integer getMaxCalcMon() {
		
		return salaryCalcDao.getMaxCalcMon();
	}
	
	@Override
	public Integer getSalaryScheme() {

		return salaryCalcDao.getSalaryScheme();
	}

	@SuppressWarnings("finally")
	@Override
	public Result doCalc(SalaryData salaryData) {
		
		Result result = new Result();
		result.setCode("1");
		result.setInfo("操作成功");
		try {
			
			SalaryStatusCtrl salaryStatusCtrl = new SalaryStatusCtrl();
			salaryStatusCtrl.setSalaryMon(salaryData.getSalaryMon());
			salaryStatusCtrl.setSalaryScheme(salaryData.getSalaryScheme());
			salaryStatusCtrl.setProcessTime(new Date());
			salaryStatusCtrl.setStatus("2");
			Serializable serializable =null;
			
			Integer statusCtrlPk= salaryCalcDao.chekcStatusCtrl(salaryStatusCtrl);
			
			if(statusCtrlPk != null){
				salaryStatusCtrl = get(SalaryStatusCtrl.class, statusCtrlPk);
				
				//薪酬月份控制如果为已结算的 无法继续结算
				if(salaryStatusCtrl != null && "3".equals(salaryStatusCtrl.getStatus())){
					result.setCode("0");
					result.setInfo("计算失败,该薪酬当月已经结算,无法继续计算");
				} else {
					//已经存在薪酬月份控制 并且不是已结算 设置为结算中 修改操作时间
					salaryStatusCtrl.setProcessTime(new Date());
					salaryStatusCtrl.setStatus("2");
					update(salaryStatusCtrl);
				}
			} else {
				//不存在薪酬月份控制 新增一条薪酬月份控制
				serializable = this.save(salaryStatusCtrl);
				if(serializable == null){
					result.setCode("-1");
					result.setInfo("计算薪酬失败，薪酬月份控制记录出现异常。");
				} 
			}
			
			//薪酬月份控制操作成功才继续计算薪酬
			if("1".equals(result.getCode())){
			
				result = salaryCalcDao.doCalc(salaryData);
			}
			
		} finally {
			
			return result;
		}
	}
	
	@Override
	public Result doCheck(SalaryData salaryData) {

		return salaryCalcDao.doCheck(salaryData);
	}
	
	@Override
	public Result doCheckReturn(SalaryData salaryData) {

		return salaryCalcDao.doCheckReturn(salaryData);
	}

	@SuppressWarnings("finally")
	@Override
	public Result doChgEmpSalary(SalaryDataAdjust salaryDataAdjust) {
		Result result = new Result();
		try {
			salaryDataAdjust.setLastUpdate(new Date());
			salaryDataAdjust.setExpired("F");
			salaryDataAdjust.setActionLog("ADD");
			Serializable serializable = this.save(salaryDataAdjust);
			if(serializable == null){
				
				result.setCode("-1");
				result.setInfo("修改薪酬失败，添加薪酬调整记录异常。");
			} else {

				result = salaryCalcDao.doChgEmpSalary(salaryDataAdjust);
			}
			
		} finally {
		
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@Override
	public Result doChgEmpSalaryUpdate(SalaryDataAdjust salaryDataAdjust) {
		Result result = new Result();
		try {
			salaryDataAdjust.setLastUpdate(new Date());
			salaryDataAdjust.setExpired("F");
			salaryDataAdjust.setActionLog("EDIT");
			this.update(salaryDataAdjust);

			result = salaryCalcDao.doChgEmpSalary(salaryDataAdjust);
			
		} catch (Exception e) {
			result.setCode("-1");
			result.setInfo("修改薪酬失败，修改薪酬调整记录异常。");
		} finally {
		
			return result;
		}
	}
	
	@Override
	public Result doSchemeEmpSave(SalaryData salaryData) {

		return salaryCalcDao.doSchemeEmpSave(salaryData);
	}

	@Override
	public String getCmpEmpCount(Integer salaryScheme) {

		return salaryCalcDao.getCmpEmpCount(salaryScheme);
	}

	@Override
	public List<Map<String,Object>> getSalaryStatusCtrl(SalaryData salaryData) {
		return salaryCalcDao.getSalaryStatusCtrl(salaryData);
	}

	@Override
	public List<Map<String, Object>> getPaymentSubreport(SalaryData salaryData) {

		return salaryCalcDao.getPaymentSubreport(salaryData);
	}

	@SuppressWarnings("finally")
	@Override
	public Result doDelSalaryChg(String pks) {
		
		Result result = new Result();
		SalaryDataAdjust salaryDataAdjust = new SalaryDataAdjust();
		List<Map<String, Object>> salaryChgDatas = salaryCalcDao.getSalaryChgDataByPks(pks);
		try {
			Long res = salaryCalcDao.doDelSalaryChg(pks);
			if(res <= 0 ){
				result.setCode("-1");
				result.setInfo("删除薪酬失败，添加薪酬调整记录异常。");
			} else {
				if(salaryChgDatas.size()>0){
					for(Map<String, Object> map : salaryChgDatas){
						salaryDataAdjust.setSalaryEmp(map.get("SalaryEmp").toString());
						salaryDataAdjust.setSalaryMon(Integer.parseInt(map.get("SalaryMon").toString()));
						salaryDataAdjust.setSalaryScheme(Integer.parseInt(map.get("SalaryScheme").toString()));
						result = salaryCalcDao.doChgEmpSalary(salaryDataAdjust);
					}
				}
			}
		} finally {
		
			return result;
		}
	}

	@Override
	public Result doImportSalaryChg(SalaryData salaryData) {

		return salaryCalcDao.doImportSalaryChg(salaryData);
	}

	@Override
	public String getSalaryEmpByIdNum(String idNum) {

		return salaryCalcDao.getSalaryEmpByIdNum(idNum);
	}

	@Override
	public String IsLastCheckedCtrl(SalaryData salaryData) {
		
		return salaryCalcDao.IsLastCheckedCtrl(salaryData);
	}



	@Override
	public String getFirstCalcTime(Integer salaryMon) {
		return salaryCalcDao.getFirstCalcTime(salaryMon);
	}



	@Override
	public String getSalaryStatus(SalaryData salaryData) {
		return salaryCalcDao.getSalaryStatus(salaryData);
	}
	
}
