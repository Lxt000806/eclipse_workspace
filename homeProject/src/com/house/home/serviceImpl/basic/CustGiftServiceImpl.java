package com.house.home.serviceImpl.basic;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.CustGiftDao;
import com.house.home.entity.basic.CustGift;
import com.house.home.service.basic.CustGiftService;

@SuppressWarnings("serial")
@Service 
public class CustGiftServiceImpl extends BaseServiceImpl implements CustGiftService {
	@Autowired
	private  CustGiftDao custGiftDao;

	@Override
	public Result doImportCustGift(CustGift custGift) {
		return custGiftDao.doCustGiftSave(custGift);
	}
	
	@Override
	public Result doCustGiftSave(CustGift custGift) {
		return custGiftDao.doCustGiftSave(custGift);
	}
	
	@Override
	public Result doCustGiftUpdate(CustGift custGift) {
		return custGiftDao.doCustGiftSave(custGift);
	}
	
	@Override
	public Result doCustGiftDel(CustGift custGift) {
		return custGiftDao.doCustGiftSave(custGift);
	}
	
	@Override
	public Result doUpwardCustGift(CustGift custGift) {
		return custGiftDao.doCustGiftSave(custGift);
	}
	
	@Override
	public Result doDownwardCustGift(CustGift custGift) {
		return custGiftDao.doCustGiftSave(custGift);
	}

    @Override
    public void deleteAllGiftsByCustCode(String custCode) {
        custGiftDao.deleteAllGiftsByCustCode(custCode);
    }
    
}
