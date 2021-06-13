package com.house.home.serviceImpl.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.LaborFeeDao;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.finance.LaborFee;
import com.house.home.entity.project.Worker;
import com.house.home.service.finance.LaborFeeService;

@SuppressWarnings("serial")
@Service
public class LaborFeeServiceImpl extends BaseServiceImpl implements LaborFeeService{
	
	@Autowired
	private LaborFeeDao laborFeeDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,LaborFee laborFee) {
		// TODO Auto-generated method stub
		return laborFeeDao.findPageBySql(page,laborFee);
	}
	
	@Override
	public Page<Map<String, Object>> findLaborFeeAccountBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
	
		return laborFeeDao.findLaborFeeAccountBySql(page,laborFee);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		// TODO Auto-generated method stub
		return laborFeeDao.findDetailPageBySql(page,laborFee);
	}

	@Override
	public Page<Map<String, Object>> findLaborDetailPageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		// TODO Auto-generated method stub
		return laborFeeDao.findLaborDetailPageBySql(page,laborFee);
	}

	@Override
	public String getSendNoHaveAmount(String custCode, String feeType) {
		// TODO Auto-generated method stub
		return laborFeeDao.getSendNoHaveAmount(custCode,feeType);
	}

	@Override
	public String getHaveAmount(String sendNo, String feeType) {
		// TODO Auto-generated method stub
		return laborFeeDao.getHaveAmount(sendNo, feeType);
	}

	@Override
	public Result doSaveLaborFee(LaborFee laborFee) {

		return laborFeeDao.doSave(laborFee);
	}
	
	@Override
	public Result doUpdateLaborFee(LaborFee laborFee) {
		// TODO Auto-generated method stub
		return laborFeeDao.doSave(laborFee);
	}

	@Override
	public String getFeeTypeDescr(String feeType) {
		// TODO Auto-generated method stub
		return laborFeeDao.getFeeTypeDescr(feeType);
	}

	@Override
	public Result doCheckLaborFee(LaborFee laborFee) {
		// TODO Auto-generated method stub
		return laborFeeDao.doSave(laborFee);
	}

	@Override
	public List<Map<String,Object>> findFeeType(int type,String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.laborFeeDao.findItemType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.laborFeeDao.findFeeTypeByItemType1(param);
		}
		
		return resultList;
	}

	@Override
	public String isHaveSendNo(String feeType) {
		// TODO Auto-generated method stub
		return laborFeeDao.isHaveSendNo(feeType);
	}

	@Override
	public Page<Map<String, Object>> findItemSendNoPageBySql(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		// TODO Auto-generated method stub
		return laborFeeDao.findItemAppSendPageBySql(page, itemAppSend);
	}

	@Override
	public Page<Map<String, Object>> findItemAppSendDetailPageBySql(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		// TODO Auto-generated method stub
		return laborFeeDao.findItemAppSendDetailPageBySql(page, itemAppSend);
	}

	@Override
	public boolean getIsSetItem(String no) {
		// TODO Auto-generated method stub
		return laborFeeDao.isSetItem(no);
	}

	@Override
	public String getCheckStatusDescr(String checkStatus) {
		// TODO Auto-generated method stub
		return laborFeeDao.getCheckStatusDescr(checkStatus);
	}

	@Override
	public void doSaveWorkCard(String actNameReal, String cardID,String lastUpdatedBy) {
		// TODO Auto-generated method stub
		laborFeeDao.doSaveWorkCard(actNameReal,cardID,lastUpdatedBy);
	}

	@Override
	public boolean getIsExistsFeeType(String itemType1, String feeType) {
		// TODO Auto-generated method stub
		return laborFeeDao.getIsExistsFeeType(itemType1,feeType);
	}

	@Override
	public Page<Map<String, Object>> findItemReqBySql(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		// TODO Auto-generated method stub
		return laborFeeDao.findItemReqBySql(page, itemAppSend);
	}

	@Override
	public boolean isExistsWorkCard(String actName) {
		// TODO Auto-generated method stub
		return laborFeeDao.isExistsWorkCard(actName);
	}

	@Override
	public Page<Map<String, Object>> findDetailActNamePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		// TODO Auto-generated method stub
		return laborFeeDao.findDetailActNamePageBySql(page, laborFee);
	}
	
    @Override
	public Page<Map<String, Object>> findDetailCompanyPageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {

    	return laborFeeDao.findDetailCompanyPageBySql(page, laborFee);
	}

	@Override
    public Page<Map<String, Object>> findDetailCustTypePageBySql(
            Page<Map<String, Object>> page, LaborFee laborFee) {
        
        return laborFeeDao.findDetailCustTypePageBySql(page, laborFee);
    }
    

	@Override
	public Page<Map<String, Object>> findProcListJqGrid(
			Page<Map<String, Object>> page, LaborFee laborFee) {
	
		return laborFeeDao.findProcListJqGrid(page, laborFee);
	}

	@Override
	public Page<Map<String, Object>> findProcTrackJqGrid(
			Page<Map<String, Object>> page, LaborFee laborFee) {

		return laborFeeDao.findProcTrackJqGrid(page, laborFee);
	}

	@Override
	public Page<Map<String, Object>> findSendFeePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.findSendFeePageBySql(page, laborFee);
	}
	
	@Override
	public Page<Map<String, Object>> findIntFeePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.findIntFeePageBySql(page, laborFee);
	}
	
	@Override
	public Page<Map<String, Object>> findCupFeePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		Xtcs xtcs = laborFeeDao.get(Xtcs.class, "CupInsCalTyp");
		if ("1".equals(xtcs.getQz())) {//计算模式：1.解单 2.需求
			return laborFeeDao.findCupFeePageBySql(page, laborFee);
		}
		return laborFeeDao.findCupFeeByReqPageBySql(page, laborFee);
	}

	@Override
	public Page<Map<String, Object>> findBathFeePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.findBathFeePageBySql(page, laborFee);
	}

	@Override
	public Page<Map<String, Object>> goTransFeeJqGrid(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.goTransFeeJqGrid(page, laborFee);
	}

	@Override
	public Page<Map<String, Object>> goPreFeeJqGrid(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.goPreFeeJqGrid(page, laborFee);
	}

	@Override
	public Page<Map<String, Object>> goCheckFeeJqGrid(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.goCheckFeeJqGrid(page, laborFee);
	}

	@Override
	public List<Map<String, Object>> getTransFeeList(LaborFee laborFee) {
		return laborFeeDao.getTransFeeList(laborFee);
	}

	@Override
	public Page<Map<String, Object>> goWhInstallFeeJqGrid(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.goWhInstallFeeJqGrid(page, laborFee);
	}

	@Override
	public Page<Map<String, Object>> findIntQtyPageBySql(Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.findIntQtyPageBySql(page, laborFee);
	}
	
	@Override
	public Page<Map<String, Object>> goTileCutFeeJqGrid(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.goTileCutFeeJqGrid(page, laborFee);
	}

	@Override
	public List<Map<String, Object>> getLaborFeeDetail(String no) {
		
		return laborFeeDao.getLaborFeeDetail(no);
	}

	@Override
	public Double getAmountByNo(String no) {

		return laborFeeDao.getAmountByNo(no);
	}

	@Override
	public Page<Map<String, Object>> findGoodPrjPageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		return laborFeeDao.findGoodPrjPageBySql(page, laborFee);
	}
	
	

}
