package com.house.home.serviceImpl.insales;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.insales.PurchaseAppDao;
import com.house.home.service.insales.PurchaseAppService;

import com.house.framework.commons.orm.Page;

import java.util.List;
import java.util.Map;
import com.house.home.entity.insales.PurchaseApp;
@SuppressWarnings("serial")
@Service 
public class PurchaseAppServiceImpl extends BaseServiceImpl implements PurchaseAppService {
	@Autowired
	private  PurchaseAppDao purchaseAppDao;
	
	@Override	
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, PurchaseApp purchaseApp){
	
		return purchaseAppDao.findPageBySql(page, purchaseApp);
	}

    @Override   
    public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page, PurchaseApp purchaseApp){
    
        return purchaseAppDao.findDetailPageBySql(page, purchaseApp);
    }
    
    @Override
    public Page<Map<String, Object>> findPurchConPageBySql(
            Page<Map<String, Object>> page, PurchaseApp purchaseApp) {
        
        return purchaseAppDao.findPurchConPageBySql(page, purchaseApp);
    }

    @Override
    public Result doSave(PurchaseApp purchaseApp) {

        return purchaseAppDao.doSave(purchaseApp);
    }

	@Override
	public boolean checkCanReConfirm(String no) {

		return purchaseAppDao.checkCanReConfirm(no);
	}
	
    @Override
    public Page<Map<String, Object>> exportingPurchaseAppDetails(Page<Map<String, Object>> page,
            PurchaseApp purchaseApp) {

        return purchaseAppDao.exportingPurchaseAppDetails(page, purchaseApp);
    }

	@Override
	public List<Map<String, Object>> getPurchItemData(PurchaseApp purchaseApp) {
		
		return purchaseAppDao.getPurchItemData(purchaseApp);
	}

	@Override
	public List<Map<String, Object>> getItemSendQty(PurchaseApp purchaseApp) {
		
		return purchaseAppDao.getItemSendQty(purchaseApp);
	}
	
}
