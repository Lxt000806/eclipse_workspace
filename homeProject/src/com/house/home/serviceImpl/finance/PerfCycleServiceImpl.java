package com.house.home.serviceImpl.finance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.PerfCycleDao;
import com.house.home.entity.finance.PerfCycle;
import com.house.home.service.finance.PerfCycleService;

@SuppressWarnings("serial")
@Service
public class PerfCycleServiceImpl extends BaseServiceImpl implements PerfCycleService {

	@Autowired
	private PerfCycleDao perfCycleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PerfCycle perfCycle){
		return perfCycleDao.findPageBySql(page, perfCycle);
	}

	@Override
	public String checkStatus(String no) {
		return perfCycleDao.checkStatus(no);
	}

	@Override
	public String isExistsPeriod(String no, String beginDate) {
		return perfCycleDao.isExistsPeriod(no, beginDate);
	}

	@Override
	public List<Map<String, Object>> checkEmployeeInfo() {
		return perfCycleDao.checkEmployeeInfo();
	}

	@Override
	public void doComplete(String no) {
		perfCycleDao.doComplete(no);
	}

	@Override
	public void doReturn(String no) {
		perfCycleDao.doReturn(no);
	}

	@Override
	public Page<Map<String, Object>> findEmployeePageBySql(Page<Map<String, Object>> page, PerfCycle perfcycle) {
		return perfCycleDao.findEmployeePageBySql(page, perfcycle);
	}

	@Override
	public void doSyncEmployee(String numbers) {
		perfCycleDao.doSyncEmployee(numbers);
	}

	@Override
	public List<Map<String, Object>> defaultCycle() {
		return perfCycleDao.defaultCycle();
	}

	@Override
	public Page<Map<String, Object>> findReportDetailBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportDetailBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportYwbBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportYwbBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportSjbBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportSjbBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportSybBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportSybBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportGcbBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportGcbBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportYwyBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportYwyBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportSjsBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportSjsBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportFdyBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportFdyBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportHtyBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportHtyBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportYwzrBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportYwzrBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findYjsyjBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findYjsyjBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findWjsyjBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findWjsyjBySql(page, perfCycle);
	}

	@Override
	public Map<String, Object> doCount(String no, String lastUpdatedBy, String calChgPerf) {
		return perfCycleDao.doCount(no, lastUpdatedBy, calChgPerf);
	}

	@Override
	public void doPerfChgSet(PerfCycle perfCycle) {
		perfCycleDao.doPerfChgSet(perfCycle);
	}

	@Override
	public List<Map<String, Object>> findChgPefByCode(PerfCycle perfCycle) {
		return perfCycleDao.findChgPefByCode(perfCycle);
	}

	@Override
	public List<Map<String, Object>> beforePointCust(PerfCycle perfCycle) {
		return perfCycleDao.beforePointCust(perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findCyyjjsBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findCyyjjsBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findBcyyjjsBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findBcyyjjsBySql(page, perfCycle);
	}

	@Override
	public List<Map<String, Object>> checkIsCalcPerf(PerfCycle perfCycle) {
		return perfCycleDao.checkIsCalcPerf(perfCycle);
	}

	@Override
	public List<Map<String, Object>> findAllCustType() {
		return perfCycleDao.findAllCustType();
	}

	@Override
	public Page<Map<String, Object>> findGxrBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findGxrBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findGxrxglsBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findGxrxglsBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findJczjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findJczjBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findClzjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findClzjBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findHtfyzjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findHtfyzjBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findFkxxBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findFkxxBySql(page, perfCycle);
	}

	@Override
	public Map<String, Object> getAlreadyMaterPerf(PerfCycle perfCycle) {
		return perfCycleDao.getAlreadyMaterPerf(perfCycle);
	}

	@Override
	public Map<String, Object> getPayType(PerfCycle perfCycle) {
		return perfCycleDao.getPayType(perfCycle);
	}

	@Override
	public Map<String, Object> getRegRealMaterPerf(PerfCycle perfCycle) {
		return perfCycleDao.getRegRealMaterPerf(perfCycle);
	}

	@Override
	public Map<String, Object> getSumChgRealMaterPerf(PerfCycle perfCycle) {
		return perfCycleDao.getSumChgRealMaterPerf(perfCycle);
	}

	@Override
	public Map<String, Object> getIsCalcBaseDisc(PerfCycle perfCycle) {
		return perfCycleDao.getIsCalcBaseDisc(perfCycle);
	}

	@Override
	public Map<String, Object> getRegAchieveDate(PerfCycle perfCycle) {
		return perfCycleDao.getRegAchieveDate(perfCycle);
	}

	@Override
	public List<Map<String, Object>> getRegPerfPK(PerfCycle perfCycle) {
		return perfCycleDao.getRegPerfPK(perfCycle);
	}

	@Override
	public List<Map<String, Object>> getRegPerformance(PerfCycle perfCycle) {
		return perfCycleDao.getRegPerformance(perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findYyjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findYyjBySql(page, perfCycle);
	}

	@Override
	public List<Map<String, Object>> getRegImport(PerfCycle perfCycle) {
		return perfCycleDao.getRegImport(perfCycle);
	}

	@Override
	public List<Map<String, Object>> getExp(PerfCycle perfCycle) {
		return perfCycleDao.getExp(perfCycle);
	}

	@Override
	public Result doSaveProc(PerfCycle perfCycle) {
		return perfCycleDao.doSaveProc(perfCycle);
	}

	@Override
	public void changeIsCheck(PerfCycle perfCycle) {
		perfCycleDao.changeIsCheck(perfCycle);
	}

	@Override
	public List<Map<String, Object>> isExistRegPerfPk(PerfCycle perfCycle) {
		return perfCycleDao.isExistRegPerfPk(perfCycle);
	}

	@Override
	public List<Map<String, Object>> isMatchedPerf(PerfCycle perfCycle) {
		return perfCycleDao.isMatchedPerf(perfCycle);
	}

	@Override
	public List<Map<String, Object>> isExistThisPerfPk(PerfCycle perfCycle) {
		return perfCycleDao.isExistThisPerfPk(perfCycle);
	}

	@Override
	public List<Map<String, Object>> calcBaseDeduction(PerfCycle perfCycle) {
		return perfCycleDao.calcBaseDeduction(perfCycle);
	}

	@Override
	public List<Map<String, Object>> calcItemDeduction(PerfCycle perfCycle) {
		return perfCycleDao.calcItemDeduction(perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportYwyDlxxBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportYwyDlxxBySql(page, perfCycle);
	}

	@Override
	public void doBatchChecked(String sPK, String isCheck) {
		String arrPK[] = sPK.split(",");
		PerfCycle perfCycle=new PerfCycle();
		for (String str : arrPK){
			 perfCycle.setPk(Integer.parseInt(str));
			 perfCycle.setIsChecked(isCheck);
			 this.perfCycleDao.changeIsCheck(perfCycle);
		}
	}

	@Override
	public Page<Map<String, Object>> findLeaderPageBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findLeaderPageBySql(page, perfCycle);
	}

	@Override
	public void doSyncLeader(String codes) {
		perfCycleDao.doSyncLeader(codes);
	}

	@Override
	public Page<Map<String, Object>> findReportYwtdBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportYwtdBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findReportSjtdBySql(Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findReportSjtdBySql(page, perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findSignDataJqGridBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findSignDataJqGridBySql(page, perfCycle);
	}

	@Override
	public List<Map<String, Object>> getBaseChgSetAdd(PerfCycle perfCycle) {
		return perfCycleDao.getBaseChgSetAdd(perfCycle);
	}

	@Override
	public List<Map<String, Object>> getMainProPer_chg(PerfCycle perfCycle) {
		return perfCycleDao.getMainProPer_chg(perfCycle);
	}

	@Override
	public List<Map<String, Object>> getBasePersonalPlan(PerfCycle perfCycle) {
		return perfCycleDao.getBasePersonalPlan(perfCycle);
	}

	@Override
	public Page<Map<String, Object>> findIndependPerfBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		return perfCycleDao.findIndependPerfBySql(page, perfCycle);
	}

}
