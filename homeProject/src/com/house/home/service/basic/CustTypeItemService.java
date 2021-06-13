package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustTypeItem;
import com.house.home.entity.basic.Item;


public interface CustTypeItemService extends BaseService{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustTypeItem custTypeItem );

	public Page<Map<String,Object>> getItemBySqlCode(Page<Map<String, Object>> page, Item item);
	
	public void doSaveCustTypeItem(CustTypeItem custTypeItem);
	
	public String getCustTypeDescr(String custType);
	
	public Result doSaveBatch(CustTypeItem custTypeItem);

	public boolean hasExist(String itemCode,String custType,String remark);
 
	public Page<Map<String,Object>> findItemPageBySql(Page<Map<String, Object>> page, Item item);

	public boolean hasExists(String itemCode,String custType,String remark,Integer pk);
	public boolean hasItem(Integer pk,String custType, String itemType1);
	//获取材料编号对应的唯一的pk值，如有多条记录返回空
	public Integer getUniquePk(String itemCode, String custType, String itemType1,String remarks);
	
	public String getDiscAmtCalcTypeDescr(String discAmtCalcType);
	
	public Result doSaveBatchAddNoExcelForProc(CustTypeItem custTypeItem);
	
	public boolean hasExistsICP(String itemCode,String custType,Double price,Integer pk);
	
	public boolean hasExistsICP(String itemCode,String custType,Double price);
	
	public boolean checkFixAreaType(CustTypeItem custTypeItem);
	
	public void doBatchDeal(CustTypeItem custTypeItem);
}
