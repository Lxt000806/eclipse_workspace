package com.house.home.service.insales;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;

import com.house.framework.commons.orm.Page;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchaseApp;

public interface PurchaseAppService extends BaseService{
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchaseApp purchaseApp);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, PurchaseApp purchaseApp);

	public Page<Map<String,Object>> findPurchConPageBySql(Page<Map<String,Object>> page, PurchaseApp purchaseApp);
	    
	public Result doSave(PurchaseApp purchaseApp);
	
    public Page<Map<String,Object>> exportingPurchaseAppDetails(
            Page<Map<String, Object>> page, PurchaseApp purchaseApp);
    
	public boolean checkCanReConfirm(String no);
	
	public List<Map<String, Object>> getPurchItemData(PurchaseApp purchaseApp);
	
	public List<Map<String, Object>> getItemSendQty(PurchaseApp purchaseApp);
}
