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
import com.house.home.dao.project.PreWorkCostDetailDao;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.project.PreWorkCostDetail;
import com.house.home.service.project.PreWorkCostDetailService;

@SuppressWarnings("serial")
@Service
public class PreWorkCostDetailServiceImpl extends BaseServiceImpl implements PreWorkCostDetailService {

	@Autowired
	private PreWorkCostDetailDao preWorkCostDetailDao;
	
	public Page<Map<String,Object>> findPageBySql1(Page<Map<String,Object>> page, PreWorkCostDetail preWorkCostDetail,UserContext uc){
		return preWorkCostDetailDao.findPageBySql(page, preWorkCostDetail,uc);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_forClient(
			Page<Map<String, Object>> page, PreWorkCostDetail preWorkCostDetail) {
		return preWorkCostDetailDao.findPageBySql_forClient(page, preWorkCostDetail);
	}

	@Override
	public Map<String, Object> getByPk(Integer pk) {
		return preWorkCostDetailDao.getByPk(pk);
	}

	@Override
	public boolean canCommit(String custCode, String workType2) {
		return preWorkCostDetailDao.canCommit(custCode,workType2);
	}
	
	///PW
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PreWorkCostDetail pWorkCostDetail,UserContext uc) {
		return preWorkCostDetailDao.findPageBySql(page, pWorkCostDetail, uc);
	}
	
	@Override
	public List<Map<String, Object>> findWorkTypeByAuthority(int type,
			String pCode,UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";			
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.preWorkCostDetailDao.findWorkType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.preWorkCostDetailDao.findWorkType2(param);
		}
		return resultList;
	}
	
	@Override
	public Map<String,Object> getWorkType1(String workType2) {
		return preWorkCostDetailDao.getWorkType1(workType2);
	}
	
	@Override
	public Map<String,Object> getXTCS(String xtcsid) {
		return preWorkCostDetailDao.getXTCS(xtcsid);
	}
	
	@Override
	public Map<String,Object> getzlqx(String czybh,String workerCode) {
		return preWorkCostDetailDao.getzlqx(czybh,workerCode);
	}
	@Override
	public Map<String,Object> getworkType12(String workerCode) {
		return preWorkCostDetailDao.getworkType12(workerCode);
	}
	@Override
	public List<Map<String, Object>> getMsg(int PK) {
		return preWorkCostDetailDao.getMsg(PK);
	}
	
	@Override
	public Map<String,Object> getWorkCon(String custCode, String workType2) {
		return preWorkCostDetailDao.getWorkCon(custCode,workType2);
	}

	@Override
	public Map<String,Object> getAmount(String Yukou) {
		return preWorkCostDetailDao.getAmount(Yukou);
	}

	@Override
	public List<Map<String,Object>> getret(String WithHoldNo) {
		return preWorkCostDetailDao.getret(WithHoldNo);
	}

	@Override
	public List<Map<String,Object>> getPk(int PK) {
		return  preWorkCostDetailDao.getMaxPk(PK);
	}

	@Override
	public List<Map<String, Object>> getCodeType(String CustCode, String WorkType2) {
		return preWorkCostDetailDao.getCodeType(CustCode,WorkType2);
	}

	@Override
	public List<Map<String, Object>> getNotNullCustCode(String CustCode) {
		return preWorkCostDetailDao.getNotNullCustCode(CustCode);
	}

	@Override
	public List<Map<String, Object>> getNotNullWorkType2(String CustCode, String WorkType2) {
		return preWorkCostDetailDao.getNotNullWorkType2(CustCode,WorkType2);
	}

	@Override
	public Result doZhuLiSave(PreWorkCostDetail pWorkCostDetail) {
		return preWorkCostDetailDao.doZhuLiReturnCheckOut(pWorkCostDetail);
	}

	@Override
	public Map<String, Object> getCfmAmount(String custCode, String workType2) {
		WorkType2 wt2=preWorkCostDetailDao.get(WorkType2.class,workType2);
		if("0".equals(wt2.getSalaryCtrlType())){//根据工资控制类型算工种二已审核金额
			return preWorkCostDetailDao.getCfmAmountByWt12(custCode, workType2);
		}
		return preWorkCostDetailDao.getCfmAmountByWt2(custCode, workType2);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql2(
			Page<Map<String, Object>> page, PreWorkCostDetail preWorkCostDetail) {
		return preWorkCostDetailDao.findPageBySql2(page, preWorkCostDetail);
	}

	@Override
	public Map<String, Object> getCustWorkerInfo(String custCode, String workerCode){
		return preWorkCostDetailDao.getCustWorkerInfo(custCode, workerCode);
	}

	@Override
	public boolean hasCustWork(String workType2) {
		return preWorkCostDetailDao.hasCustWork(workType2);
	}

	@Override
	public Page<Map<String, Object>> getQuotaSalaryJqGrid(
			Page<Map<String, Object>> page, PreWorkCostDetail pWorkCostDetail) {
		return preWorkCostDetailDao.getQuotaSalaryJqGrid(page, pWorkCostDetail);
	}

	@Override
	public Map<String, Object> getCfmAmountByWorkType1(String custCode,String workType1) {
		return preWorkCostDetailDao.getCfmAmountByWorkType1(custCode, workType1);
	}

	@Override
	public Map<String, Object> hasBaseCheckItemPlan(String custCode) {
		return preWorkCostDetailDao.hasBaseCheckItemPlan(custCode);
	}

	@Override
	public String getQuotaSalary(PreWorkCostDetail preWorkCostDetail) {
		WorkType2 workType2=preWorkCostDetailDao.get(WorkType2.class,preWorkCostDetail.getWorkType2());
		if("0".equals(workType2.getSalaryCtrlType())){//根据工资控制类型算定额工资
			return preWorkCostDetailDao.getQuotaSalaryByWt12(preWorkCostDetail);
		}
		return preWorkCostDetailDao.getQuotaSalaryByWt2(preWorkCostDetail);
	}

	@Override
	public List<Map<String, Object>> findWorkTypeByAuthorityForPrj(int type,String pCode, UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";			
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.preWorkCostDetailDao.findWorkType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.preWorkCostDetailDao.findWorkType2ForPrj(param);
		}
		return resultList;
	}
	
	@Override
	public Page<Map<String,Object>> goWorkCostDetailJqGrid(Page<Map<String, Object>> page, PreWorkCostDetail pWorkCostDetail, String hasBaseCheckItemPlan){
		if("1".equals(hasBaseCheckItemPlan)){
			WorkType2 wt2=preWorkCostDetailDao.get(WorkType2.class, pWorkCostDetail.getWorkType2());
			if("0".equals(wt2.getSalaryCtrlType())){//根据工资控制类型算工种二已审核金额
				return preWorkCostDetailDao.goCfmAmountByWt12JqGrid(page, pWorkCostDetail.getCustCode(), pWorkCostDetail.getWorkType2());
			}
			return preWorkCostDetailDao.goCfmAmountByWt2JqGrid(page, pWorkCostDetail.getCustCode(), pWorkCostDetail.getWorkType2());
		}
		return this.preWorkCostDetailDao.goCfmAmountByWorkType1JqGrid(page, pWorkCostDetail.getCustCode(), pWorkCostDetail.getWorkType1());
	}
}
