package com.house.home.serviceImpl.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.WorkCostDetailDao;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.project.WorkCostDetail;
import com.house.home.service.project.WorkCostDetailService;

@SuppressWarnings("serial")
@Service
public class WorkCostDetailServiceImpl extends BaseServiceImpl implements WorkCostDetailService {

	@Autowired
	private WorkCostDetailDao workCostDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCostDetail workCostDetail){
		return workCostDetailDao.findPageBySql(page, workCostDetail);
	}

	@Override
	public Page<Map<String, Object>> findCardListBySql(
			Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		return workCostDetailDao.findCardListBySql(page, workCostDetail);
	}

	@Override
	public Map<String, Object> findCostByCodeWork(String custCode,String workType2) {
		return workCostDetailDao.findCostByCodeWork(custCode, workType2);
	}

	@Override
	public Map<String, Object> findPrjByCodeWork(String custCode,
			String workType2) {
		return workCostDetailDao.findPrjByCodeWork(custCode, workType2);
	}

	@Override
	public Map<String, Object> findTotalAcountByCodeWork(String custCode,
			String workType2) {
		return workCostDetailDao.findTotalAcountByCodeWork(custCode, workType2);
	}

	@Override
	public Map<String, Object> findGotAcountByCodeWork(String custCode,
			String workType2) {
		return workCostDetailDao.findGotAcountByCodeWork(custCode, workType2);
	}

	@Override
	public Map<String, Object> findYukou(WorkCostDetail workCostDetail) {
		return workCostDetailDao.findYukou(workCostDetail);
	}

	@Override
	public Map<String, Object> findDescr(String value, String id) {
		return workCostDetailDao.findDescr(value, id);
	}

	@Override
	public List<Map<String, Object>> findSubsidyByDate(String year,
			String month,WorkCostDetail workCostDetail) {
		return workCostDetailDao.findSubsidyByDate(year, month,workCostDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql2(
			Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		return workCostDetailDao.findPageBySql2(page, workCostDetail);
	}

	@Override
	public void cancel(String pk, String lastUpdatedBy, String confirmRemark,
			String refPrePk) {
		workCostDetailDao.cancel(pk, lastUpdatedBy, confirmRemark, refPrePk);
	}

	@Override
	public void renew(String pk, String lastUpdatedBy, String confirmRemark,
			String refPrePk) {
		workCostDetailDao.renew(pk, lastUpdatedBy, confirmRemark, refPrePk);
	}

	@Override
	public List<Map<String, Object>> isAllowConfirm(
			WorkCostDetail workCostDetail) {
		return workCostDetailDao.isAllowConfirm(workCostDetail);
	}

	@Override
	public List<Map<String, Object>> overQualityFeeWorker(
			WorkCostDetail workCostDetail) {
		return workCostDetailDao.overQualityFeeWorker(workCostDetail);
	}

	@Override
	public List<Map<String, Object>> overCustCtrl(WorkCostDetail workCostDetail) {
		return workCostDetailDao.overCustCtrl(workCostDetail);
	}

	@Override
	public List<Map<String, Object>> overWithHold(WorkCostDetail workCostDetail) {
		return workCostDetailDao.overWithHold(workCostDetail);
	}

	@Override
	public List<Map<String, Object>> hasStatus3(WorkCostDetail workCostDetail) {
		return workCostDetailDao.hasStatus3(workCostDetail);
	}

	@Override
	public List<Map<String, Object>> isFz() {
		return workCostDetailDao.isFz();
	}

	// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//	@Override
//	public List<Map<String, Object>> hasAddManageRight(WorkCostDetail workCostDetail) {
//		return workCostDetailDao.hasAddManageRight(workCostDetail);
//	}

	@Override
	public String isMultiWorkType1(WorkCostDetail workCostDetail) {
		return workCostDetailDao.isMultiWorkType1(workCostDetail);
	}

	@Override
	public List<Map<String, Object>> findWorkTypeByAuthority(int type,
			String pCode, UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";			
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.workCostDetailDao.findWorkType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.workCostDetailDao.findWorkType2(param);
		}
		return resultList;
	}

	@Override
	public void doCheck(String pk, String col, String value) {
		this.workCostDetailDao.doCheck(pk, col, value);
	}

	@Override
	public Page<Map<String, Object>> createWaterItem(Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		return this.workCostDetailDao.createWaterItem(page, workCostDetail);
	}

	@Override
	public List<Map<String, Object>> isCreatedWaterItem(WorkCostDetail workCostDetail) {
		return this.workCostDetailDao.isCreatedWaterItem(workCostDetail);
	}

	@Override
	public Page<Map<String, Object>> goDeJqGrid(Page<Map<String, Object>> page,WorkCostDetail workCostDetail) {
		WorkType2 workType2=workCostDetailDao.get(WorkType2.class,workCostDetail.getWorkType2());
		if("0".equals(workType2.getSalaryCtrlType())){//根据工资控制类型算定额工资
			return workCostDetailDao.goDeByWt12JqGrid(page, workCostDetail);
		}
		return this.workCostDetailDao.goDeByWt2JqGrid(page, workCostDetail);
	}

	@Override
	public List<Map<String, Object>> findBatchCrtDetailByDate(String year,String month, WorkCostDetail workCostDetail) {
		return this.workCostDetailDao.findBatchCrtDetailByDate(year, month, workCostDetail);
	}

	@Override
	public Page<Map<String, Object>> goMemberJqGrid(Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		return this.workCostDetailDao.goMemberJqGrid(page, workCostDetail);
	}

	@Override
	public List<Map<String, Object>> getMemberSalary(WorkCostDetail workCostDetail) {
		return this.workCostDetailDao.getMemberSalary(workCostDetail);
	}

	@Override
	public Result doWorkerCostProvide(WorkCostDetail workCostDetail) {
		return this.workCostDetailDao.doWorkerCostProvide(workCostDetail);
	}

	@Override
	public Page<Map<String, Object>> goCheckOutJqGrid(Page<Map<String, Object>> page, WorkCost workCost) {
		return this.workCostDetailDao.goCheckOutJqGrid(page, workCost);
	}

	@Override
	public List<Map<String, Object>> getLaborCompny() {
		return this.workCostDetailDao.getLaborCompny();
	}

}
