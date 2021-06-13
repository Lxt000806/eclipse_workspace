package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemReqDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemReq;
import com.house.home.service.insales.ItemReqService;

@SuppressWarnings("serial")
@Service
public class ItemReqServiceImpl extends BaseServiceImpl implements ItemReqService {

	@Autowired
	private ItemReqDao itemReqDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReq itemReq){
		return itemReqDao.findPageBySql(page, itemReq);
	}
	
	public Page<Map<String,Object>> findImportPageBySql(Page<Map<String,Object>> page, ItemReq itemReq,String arr){
		return itemReqDao.findImportPageBySql(page, itemReq,arr);
	}
	
	public Page<Map<String,Object>> findSoftNotAppQueryPageBySql(Page<Map<String,Object>> page, ItemReq itemReq){
		return itemReqDao.findSoftNotAppQueryPageBySql(page, itemReq);
	}
	
	public Page<Map<String,Object>> findPageBySqlForClient(Page<Map<String,Object>> page, ItemReq itemReq){
		return itemReqDao.findPageBySqlForClient(page, itemReq);
	}

	@Override
	public Page<Map<String, Object>> findItemReqList(Page<Map<String, Object>> page, ItemReq itemReq) {
		return itemReqDao.findItemReqList(page, itemReq);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_jzxq(
			Page<Map<String, Object>> page, ItemReq itemReq) {
		return itemReqDao.findPageBySql_khxxcx(page,itemReq);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_zcxq(
			Page<Map<String, Object>> page, ItemReq itemReq) {
		return itemReqDao.findPageBySql_zcxq(page,itemReq);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_dhfx(
			Page<Map<String, Object>> page, ItemReq itemReq) {
		return itemReqDao.findPageBySql_dhfx(page,itemReq);
	}

	@Override
	public List<ItemReq> getReqList(String custCode,
			String itemType1) {
		// TODO Auto-generated method stub
		return itemReqDao.getReqList(custCode,itemType1);
	}

	@Override
	public String getHintString(String custCode) {
		return itemReqDao.getHintString(custCode);
	}

	@Override
	public int getCountNum(String custCode) {
		return itemReqDao.getCountNum(custCode);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_gcwg(
			Page<Map<String, Object>> page, String custCode) {
		return itemReqDao.findPageBySql_gcwg(page, custCode);
	}

	@Override
	public Page<Map<String, Object>> getCountDiscDetail(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return itemReqDao.getCountDiscDetail(page, customer);
	}
	
	

}
