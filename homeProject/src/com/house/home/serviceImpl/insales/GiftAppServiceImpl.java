package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.insales.GiftAppDao;
import com.house.home.entity.insales.GiftApp;
import com.house.home.service.insales.GiftAppService;


@SuppressWarnings("serial")
@Service
public class GiftAppServiceImpl extends BaseServiceImpl implements GiftAppService {

	@Autowired
	private GiftAppDao giftAppDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,GiftApp giftApp,UserContext uc) {
		return giftAppDao.findPageBySql(page, giftApp,uc);
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySqlGiftAppDetail(
	Page<Map<String, Object>> page, GiftApp giftApp) {
		return giftAppDao.findPageBySqlGiftAppDetail(page, giftApp);	
	}	
	@Override
	public Page<Map<String, Object>> findPageBySqlGiftStakeholder(
			Page<Map<String, Object>> page, GiftApp giftApp) {
		// TODO Auto-generated method stub
		return giftAppDao.findPageBySqlGiftStakeholder(page, giftApp);
	}
	@Override
	public Page<Map<String, Object>> findPageBySqlGiftStakeholderByCustCode(
			Page<Map<String, Object>> page, GiftApp giftApp) {
		// TODO Auto-generated method stub
		return giftAppDao.findPageBySqlGiftStakeholderByCustCode(page, giftApp);
	}

	@Override
	public Result doGiftAppForProc(GiftApp giftApp) {
		// TODO Auto-generated method stub
		return giftAppDao.doGiftAppForProc(giftApp);
	}
	@Override
	public Page<Map<String, Object>> findGiftAppDetailExistsReturn(
			Page<Map<String, Object>> page, Map<String, Object> param) {
		return giftAppDao.findGiftAppDetailExistsReturn(page,param);
	}
   
	@Override
	public Result doGiftAppSendBySuppForProc(GiftApp giftApp) {
		// TODO Auto-generated method stub
		return giftAppDao.doGiftAppSendBySuppForProc(giftApp);
	}
    
	 
	@Override
	public Result doGiftAppSendForProc(GiftApp giftApp) {
		// TODO Auto-generated method stub
		return giftAppDao.doGiftAppSendForProc(giftApp);
	}
	@Override
	public Result doGiftAppReturnForProc(GiftApp giftApp) {
		// TODO Auto-generated method stub
		return giftAppDao.doGiftAppReturnForProc(giftApp);
	}

	public Map<String, Object> getSendQty( String itemCode,String custCode){
		return giftAppDao.getSendQty(itemCode,custCode);
	}
	@Override
	public Page<Map<String, Object>> findPageByQPrintSql(
			Page<Map<String, Object>> page,GiftApp giftApp) {
		return giftAppDao.findPageByQPrintSql(page, giftApp);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_customerxx(
			Page<Map<String, Object>> page, GiftApp giftApp) {
		return giftAppDao.findPageBySql_customerxx(page, giftApp);
	}
	
	@Override
	public Page<Map<String, Object>> findPageByDetailSql(
			Page<Map<String, Object>> page,GiftApp giftApp) {
		return giftAppDao.findPageBySql_Detail(page, giftApp);
	}
	
	@Override
	public Result doSendBatchForProc(GiftApp giftApp) {
		if (giftApp.getM_umState().trim().equals("P")) {
			return giftAppDao.doGiftAppSendBySuppForProc(giftApp);
		} else if (giftApp.getM_umState().trim().equals("S")) {
			return giftAppDao.doGiftAppSendForProc(giftApp);
		} else {
			return null;
		}
	}

	@Override
	public double getGiftUseDisc(String custCode, String no) {
		return giftAppDao.getGiftUseDisc(custCode,  no);
	}
	
}
 
