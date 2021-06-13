package com.house.home.serviceImpl.query;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.ItemSzQueryDao;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemSzQueryService;
@Service
@SuppressWarnings("serial")
public class ItemSzQueryServiceImpl extends BaseServiceImpl implements ItemSzQueryService {
	@Autowired
	private ItemSzQueryDao itemSzQueryDao; 
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		// TODO Auto-generated method stub
		return itemSzQueryDao.findPageBySql(page,customer,uc);
	}

	@Override
	public Map<String,Object> getXmjljsxx(String code){
		return itemSzQueryDao.getXmjljsxx(code);
	}
	
	@Override
	public List<Map<String,Object>> goJcszxxJqGrid(String custCode){
		return itemSzQueryDao.goJcszxxJqGrid(custCode);
	}
	
	@Override
	public List<Map<String,Object>> goYshfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		return itemSzQueryDao.goYshfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
	}
	
	@Override
	public List<Map<String,Object>> goYshfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		return itemSzQueryDao.goYshfbdemxJqGrid(page,workType1Name,custCode,workType2);
	}
	
	@Override
	public List<Map<String,Object>> goZjhfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		return itemSzQueryDao.goZjhfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
	}
	
	@Override
	public List<Map<String,Object>> goZjhfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		return itemSzQueryDao.goZjhfbdemxJqGrid(page,workType1Name,custCode,workType2);
	}
	
	@Override
	public List<Map<String,Object>> goZfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		return itemSzQueryDao.goZfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
	}
	
	@Override
	public List<Map<String,Object>> goZfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		return itemSzQueryDao.goZfbdemxJqGrid(page,workType1Name,custCode,workType2);
	}
	
    @Override
    public List<Map<String, Object>> goRgcbmxJqGrid(Page<Map<String, Object>> page,
            String costType, String workType1Name, String custCode, String workType2) {
        
        return itemSzQueryDao.goRgcbmxJqGrid(page, costType, workType1Name, custCode, workType2);
    }
	
	@Override
	public List<Map<String,Object>> goClcbmxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		return itemSzQueryDao.goClcbmxJqGrid(page,workType1Name,custCode,workType2);
	}
	
	@Override
	public List<Map<String,Object>> goLlmxJqGrid(Page<Map<String,Object>> page,String no,String custCode){
		return itemSzQueryDao.goLlmxJqGrid(page,no,custCode);
	}
	
	@Override
	public List<Map<String,Object>> goRgfymxJqGrid(Page<Map<String,Object>> page,String custCode){
		return itemSzQueryDao.goRgfymxJqGrid(page,custCode);
	}
	
	@Override
	public Map<String,Object> getCustPayPlan(String custCode){
		return itemSzQueryDao.getCustPayPlan(custCode);
	}
	
	@Override
	public List<Map<String,Object>> goKhfkJqGrid(Page<Map<String,Object>> page,String custCode){
		return itemSzQueryDao.goKhfkJqGrid(page,custCode);
	}
	
	@Override
	public List<Map<String,Object>> goYxxmjljlJqGrid(Page<Map<String,Object>> page,String custCode){
		return itemSzQueryDao.goYxxmjljlJqGrid(page,custCode);
	}
	
	@Override
	public List<Map<String,Object>> goYkmxJqGrid(Page<Map<String,Object>> page,String custCode){
		return itemSzQueryDao.goYkmxJqGrid(page,custCode);
	}
	
	@Override
	public Map<String,Object> getZjzje(String custCode){
		return itemSzQueryDao.getZjzje(custCode);
	}
	
	@Override
	public Map<String,Object> getHasPay(String custCode){
		return itemSzQueryDao.getHasPay(custCode);
	}
	
	@Override
	public Map<String,Object> getJccbzcxx(String custCode){
		return itemSzQueryDao.getJccbzcxx(custCode);
	}
	
	@Override
	public Map<String,Object> getSzhz(String custCode){
		List<Map<String,Object>> list = itemSzQueryDao.getSzhz(custCode);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<Map<String,Object>> goJcbgJqGrid(Page<Map<String,Object>> page,String custCode){
		return itemSzQueryDao.goJcbgJqGrid(page,custCode);
	}
	
	@Override
	public List<Map<String,Object>> goJcclcbmxJqGrid(Page<Map<String,Object>> page,String custCode,String workType2){
		return itemSzQueryDao.goJcclcbmxJqGrid(page,custCode,workType2);
	}
	
	@Override
	public List<Map<String,Object>> goTcnclcbmxJqGrid(Page<Map<String,Object>> page,String custCode,String itemType1){
		return itemSzQueryDao.goTcnclcbmxJqGrid(page,custCode,itemType1);
	}
	
	@Override
	public List<Map<String,Object>> goJcxqJqGrid(Page<Map<String,Object>> page,String workType1,String baseItem,String custCode,String isOutSet){
		return itemSzQueryDao.goJcxqJqGrid(page,workType1,baseItem,custCode,isOutSet);
	}
	
    @Override
    public List<Map<String, Object>> goRgcbmxTcJqGrid(Page<Map<String, Object>> page, String costType,
            String custCode, String workType2) {
        return itemSzQueryDao.goRgcbmxTcJqGrid(page, costType, custCode, workType2);
    }
	
	@Override
	public List<Map<String,Object>> goRgfymxTcJqGrid(Page<Map<String,Object>> page,String custCode){
		return itemSzQueryDao.goRgfymxTcJqGrid(page,custCode);
	}
	
	@Override
	public List<Map<String,Object>> goZcrzszxxJqGrid(String custCode){
		return itemSzQueryDao.goZcrzszxxJqGrid(custCode);
	}
	
	@Override
	public List<Map<String,Object>> goLldJqGrid(Page<Map<String,Object>> page,String custCode,String type,String itemType2,String itemAppStatus){
		return itemSzQueryDao.goLldJqGrid(page,custCode,type,itemType2,itemAppStatus);
	}
	
	@Override
	public List<Map<String,Object>> goLldLlmxJqGrid(Page<Map<String,Object>> page,String no,String custCode){
		return itemSzQueryDao.goLldLlmxJqGrid(page,no,custCode);
	}
	
	@Override
	public List<Map<String,Object>> goClzjJqGrid(Page<Map<String,Object>> page,String custCode,String type){
		return itemSzQueryDao.goClzjJqGrid(page,custCode,type);
	}
	
	@Override
	public List<Map<String,Object>> goClzjZjmxJqGrid(Page<Map<String,Object>> page,String no){
		return itemSzQueryDao.goClzjZjmxJqGrid(page,no);
	}
	
	@Override
	public List<Map<String,Object>> goRgfymxZcrzszxxJqGrid(Page<Map<String,Object>> page,String custCode,String itemType1,String laborFeeStatus){
		return itemSzQueryDao.goRgfymxZcrzszxxJqGrid(page,custCode,itemType1,laborFeeStatus);
	}
	
	@Override
	public List<Map<String,Object>> goRzmxJqGrid(String custCode){
		return itemSzQueryDao.goRzmxJqGrid(custCode);
	}

	@Override
	public List<Map<String,Object>> goCgdJqGrid(Page<Map<String,Object>> page,String custCode,String itemType2,String purchaseStatus,String isCheckOut,String checkOutStatus){
		return itemSzQueryDao.goCgdJqGrid(page,custCode,itemType2,purchaseStatus,isCheckOut,checkOutStatus);
	}
	
	@Override
	public List<Map<String,Object>> goCgdmxJqGrid(Page<Map<String,Object>> page,String no){
		return itemSzQueryDao.goCgdmxJqGrid(page,no);
	}

	@Override
	public List<Map<String, Object>> findWaterContractQuotaJqGrid(
			Page<Map<String, Object>> page, String custCode,String workType2) {
		WorkType2  wt2 = new WorkType2();
		wt2 = this.get(WorkType2.class, workType2);
		if("1".equals(wt2.getCalType())){
			return itemSzQueryDao.findWaterContractQuotaJqGridOffer(page, custCode, workType2);
		}else{
			return itemSzQueryDao.findWaterContractQuotaJqGrid(page, custCode,workType2);
		}
		
	}

	@Override
	public Map<String, Object> getFixAreaTypeCount(String custCode) {
		return itemSzQueryDao.getFixAreaTypeCount(custCode);
	}

	@Override
	public List<Map<String, Object>> goTcnzcjcJqGrid(Page<Map<String, Object>> page, String custCode) {
		return itemSzQueryDao.goTcnzcjcJqGrid(page, custCode);
	}

	@Override
	public Map<String, Object> getExprByCust(String custCode, String type) {
		return itemSzQueryDao.getExprByCust(custCode, type);
	}

	@Override
	public List<Map<String, Object>> goShclmxJqGrid(Page<Map<String, Object>> page, String workType1Name,String custCode, String workType2) {
		return itemSzQueryDao.goShclmxJqGrid(page, workType1Name, custCode, workType2);
	}

	@Override
	public List<Map<String, Object>> goShrgmxJqGrid(Page<Map<String, Object>> page, String workType1Name,String custCode, String workType2) {
		return itemSzQueryDao.goShrgmxJqGrid(page, workType1Name, custCode, workType2);
	}

	@Override
	public List<Map<String, Object>> isRefCustCode(String custCode) {
		return itemSzQueryDao.isRefCustCode(custCode);
	}

	@Override
	public List<Map<String, Object>> isRefCustCode_wc(String custCode) {
		return itemSzQueryDao.isRefCustCode_wc(custCode);
	}

	@Override
	public List<Map<String, Object>> goSgbtJqGrid(Page<Map<String, Object>> page, String custCode) {
		return itemSzQueryDao.goSgbtJqGrid(page, custCode);
	}
}
