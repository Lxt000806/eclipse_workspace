package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.ItemChgDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ItemChg;
import com.house.home.service.project.ItemChgService;

@SuppressWarnings("serial")
@Service
public class ItemChgServiceImpl extends BaseServiceImpl implements ItemChgService {

	@Autowired
	private ItemChgDao itemChgDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemChg itemChg,UserContext uc){
		return itemChgDao.findPageBySql(page, itemChg,uc);
	}

	@Override
	public Page<Map<String, Object>> findPageByCustCode(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		return itemChgDao.findPageByCustCode(page,itemChg);
	}

	@Override
	public Page<Map<String, Object>> findPlzjyjPageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		return itemChgDao.findPlzjyjPageBySql(page, itemChg);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg, UserContext uc) {
		// TODO Auto-generated method stub
		return itemChgDao.findDetailPageBySql(page, itemChg, uc);
	}

	@Override
	public void doComfirm(ItemChg itemChg,String status) {
		// TODO Auto-generated method stub
		itemChgDao.doComfirm(itemChg,status);
	}

	@Override
	public Result doItemChgForProc(ItemChg itemChg,String status) {
		// TODO Auto-generated method stub
		return itemChgDao.doItemChgForProc(itemChg,status);
	}

	@Override
	public boolean isAllowChg(Customer customer) {
		// TODO Auto-generated method stub
		return itemChgDao.isAllowChg(customer);
	}

	@Override
	public Page<Map<String, Object>> findReferencePageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		// TODO Auto-generated method stub
		return itemChgDao.findReferencePageBySql(page,itemChg);
	}

	@Override
	public List<Map<String, Object>> getChangeParameterItemType2(
			String custCode, String itemType1, String isService ) {
		// TODO Auto-generated method stub
		return itemChgDao.getChangeParameterItemType2(custCode,itemType1,isService);
	}

	@Override
	public boolean getItemChgStatus(String no) {
		// TODO Auto-generated method stub
		return itemChgDao.getItemChgStatus(no);
	}

	@Override
	public int getCountByCustCode(String custCode) {
		return itemChgDao.getCountByCustCode(custCode);
	}

	@Override
	public boolean hasOpenRecord(String custCode) {
		return itemChgDao.hasOpenRecord(custCode);
	}

	@Override
	public String getArfCustCodeList() {
		return itemChgDao.getArfCustCodeList();
	}

	@Override
	public boolean existsItemCmp(String itemCode, String custCode) {
		// TODO Auto-generated method stub
		return itemChgDao.existsItemCmp(itemCode,custCode);
	}

	@Override
	public Result doPlzjyj(ItemChg itemChg) {
		return itemChgDao.doPlzjyj(itemChg);
	}

	@Override
	public Page<Map<String, Object>> findItemStatusPageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		return itemChgDao.findItemStatusPageBySql(page, itemChg);
	}

	@Override
	public Page<Map<String, Object>> findItemChgStakeholderPageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		return itemChgDao.findItemChgStakeholderPageBySql(page, itemChg);
	}

	@Override
	public Result doItemChgTempProc(ItemChg itemchg) {
		return itemChgDao.doItemChgTempProc(itemchg);
	}

	@Override
	public Result doRegenFromPrePlanTemp(ItemChg itemChg) {
		return itemChgDao.doRegenFromPrePlanTemp(itemChg);
	}

	@Override
	public boolean getExistsTemp(String custCode, String no) {
		return itemChgDao.getExistsTemp(custCode,no);
	}

	@Override
	public Double getAmountByItemType(String itemType1, String custCode) {
		
		return itemChgDao.getAmountByItemType(itemType1, custCode);
	}

    @Override
    public Page<Map<String, Object>> findSetDeductions(Page<Map<String, Object>> page,
            ItemChg itemChg) {

        return itemChgDao.findSetDeductions(page, itemChg);
    }

	@Override
	public Result doGenChgMainItemSet(ItemChg itemChg) {
		return itemChgDao.doGenChgMainItemSet(itemChg);
	}
    
 
	
}
