package com.house.home.serviceImpl.finance;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.finance.PrjManCheckDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PrjCheck;
import com.house.home.service.finance.PrjManCheckService;


@SuppressWarnings("serial")
@Service
public class PrjManCheckServiceImpl extends BaseServiceImpl implements PrjManCheckService {
	
	@Autowired
	private PrjManCheckDao prjManCheckDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer,UserContext uc) {
		return prjManCheckDao.findPageBySql(page,  customer,uc);
	}
	@Override
	public boolean isCheckWorkCost(String custCode) {
	
		return prjManCheckDao.isCheckWorkCost(custCode);
	}

	public Map<String, Object> getSendQty( String itemCode,String custCode){
		return prjManCheckDao.getSendQty(itemCode,custCode);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_totalQualityFee(
			Page<Map<String, Object>> page,PrjCheck prjCheck) {
		return prjManCheckDao.findPageBySql_totalQualityFee(page, prjCheck);
	}
	
	
	@Override
	public List<Map<String, Object>> getTypeDescr() {
		return prjManCheckDao.getTypeDescr();
	}
	@Override
	public List<Map<String, Object>> getWorkType1Descr() {
		return prjManCheckDao.getWorkType1Descr();
	}
	@Override
	public Page<Map<String, Object>> findPageBySqlPrjCheckDetail(
			Page<Map<String, Object>> page, PrjCheck prjCheck) {
		// TODO Auto-generated method stub
		return prjManCheckDao.findPageBySqlPrjCheckDetail(page, prjCheck);
	}
	@Override
	public boolean isAbnormalItemApp(String custCode,String custTypeType) {
		// TODO Auto-generated method stub
		return prjManCheckDao.isAbnormalItemApp(custCode,custTypeType);
	}
	@Override
	public Result doPrjCheckForProc(PrjCheck prjCheck) {
		if ("1".equals(prjCheck.getPrjCtrlType())){
			return  prjManCheckDao.doPrjCheckForProc(prjCheck);
		}else{
			return  prjManCheckDao.doPrjCheckForProc_sdj(prjCheck);
		}
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_prjWithHold(
			Page<Map<String, Object>> page, PrjCheck prjCheck) {
		return prjManCheckDao.findPageBySql_prjWithHold(page, prjCheck);
	}
	@Override
	public Map<String, Object> getQualityFee(String custCode) {
		return prjManCheckDao.getQualityFee(custCode);
	}
	@Override
	public Map<String, Object> getPrjCheck(String custCode ,String prjCtrlType) {
		return prjManCheckDao.getPrjCheck(custCode, prjCtrlType);
	}

	@Override
	public boolean isOneWorker(String custCode) {
		return prjManCheckDao.isOneWorker(custCode);
	}
	@Override
	public boolean isQualityFeeZero(String custCode) {
		return prjManCheckDao.isQualityFeeZero(custCode);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_qualityDetail(
			Page<Map<String, Object>> page, PrjCheck prjCheck) {
		// TODO Auto-generated method stub
		return prjManCheckDao.findPageBySql_qualityDetail(page, prjCheck);
		
	}
	//用于项目经理提成领取部分信息查询
	@Override
	public Map<String, Object> findBySql(String custCode) {
		// TODO Auto-generated method stub
		return prjManCheckDao.findBySql(custCode);
	}
	@Override
	public Map<String, Object> getRemainQualityFee(String custCode) {
		// TODO Auto-generated method stub
		return prjManCheckDao.getRemainQualityFee(custCode);
	}
	
	@Override
	public Page<Map<String,Object>> findFixDutyPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck) {
		return prjManCheckDao.findFixDutyPageBySql(page, prjCheck);
	}
	
	@Override
	public Page<Map<String,Object>> findFixDutyDetailPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck) {
		return prjManCheckDao.findFixDutyDetailPageBySql(page, prjCheck);
	}
	
	@Override
	public Page<Map<String,Object>> findBaseItemChgDetailPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck) {
		return prjManCheckDao.findBaseItemChgDetailPageBySql(page, prjCheck);
	}
	
}
 
