package com.house.home.service.basic;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.CustGift;

public interface CustGiftService extends BaseService{
	
	public Result doImportCustGift(CustGift custGift);
	
	public Result doCustGiftSave(CustGift custGift);

	public Result doCustGiftUpdate(CustGift custGift);

	public Result doCustGiftDel(CustGift custGift);

	public Result doUpwardCustGift(CustGift custGift);

	public Result doDownwardCustGift(CustGift custGift);

	/**
	 * 删除一个客户的所有赠品
	 * 
	 * @param custCode
	 */
	public void deleteAllGiftsByCustCode(String custCode);
}
