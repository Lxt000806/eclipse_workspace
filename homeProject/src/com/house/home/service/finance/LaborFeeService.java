package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.finance.LaborFee;
import com.house.home.entity.project.Worker;

public interface LaborFeeService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,LaborFee laborFee);
	
	public Page<Map<String,Object>> findLaborFeeAccountBySql(Page<Map<String,Object>> page,LaborFee laborFee);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page,LaborFee laborFee);

	public Page<Map<String,Object>> findDetailActNamePageBySql(Page<Map<String,Object>> page,LaborFee laborFee);

	public Page<Map<String,Object>> findDetailCompanyPageBySql(Page<Map<String,Object>> page,LaborFee laborFee);

    public Page<Map<String,Object>> findDetailCustTypePageBySql(Page<Map<String, Object>> page, LaborFee laborFee);

	public Page<Map<String,Object>> findLaborDetailPageBySql(Page<Map<String,Object>> page,LaborFee laborFee);

	public Page<Map<String,Object>> findProcListJqGrid(Page<Map<String,Object>> page,LaborFee laborFee);

	public Page<Map<String,Object>> findProcTrackJqGrid(Page<Map<String,Object>> page,LaborFee laborFee);

	public Page<Map<String,Object>> findItemSendNoPageBySql(Page<Map<String,Object>> page,ItemAppSend itemAppSend);

	public Page<Map<String,Object>> findItemAppSendDetailPageBySql(Page<Map<String,Object>> page,ItemAppSend itemAppSend);
	
	public Page<Map<String,Object>> findItemReqBySql(Page<Map<String,Object>> page,ItemAppSend itemAppSend);

	public String getSendNoHaveAmount(String custCode,String feeType);

	public boolean getIsSetItem(String no);
	
	public boolean getIsExistsFeeType(String itemType1,String feeType);

	public String getHaveAmount(String sendNo,String feeType) ;
	
	public String getFeeTypeDescr(String feeType) ;
	
	public Result doSaveLaborFee(LaborFee laborFee );

	public Result doUpdateLaborFee(LaborFee laborFee );

	public Result doCheckLaborFee(LaborFee laborFee );

	public List<Map<String,Object>> findFeeType(int type,String pCode);
	
	public String isHaveSendNo(String feeType);
	
	public String getCheckStatusDescr(String checkStatus);
	
	public boolean isExistsWorkCard(String actName);
	
	public void doSaveWorkCard(String actNameReal,String cardID,String lastUpdatedBy);
	
	public Page<Map<String,Object>> findSendFeePageBySql(Page<Map<String,Object>> page,LaborFee laborFee);

	public Page<Map<String,Object>> findCupFeePageBySql(Page<Map<String,Object>> page,LaborFee laborFee);
	public Page<Map<String,Object>> findIntFeePageBySql(Page<Map<String,Object>> page,LaborFee laborFee);
	/**
	 * 浴室柜查询
	 * @author	created by zb
	 * @date	2019-6-25--下午5:23:13
	 * @param page
	 * @param laborFee
	 * @return
	 */
	public Page<Map<String,Object>> findBathFeePageBySql(Page<Map<String, Object>> page,LaborFee laborFee);
	
	public Page<Map<String,Object>> goTransFeeJqGrid(Page<Map<String,Object>> page, LaborFee laborFee);
	
	public Page<Map<String, Object>> goPreFeeJqGrid(Page<Map<String, Object>> page, LaborFee laborFee);
	
	
	public Page<Map<String, Object>> goCheckFeeJqGrid(Page<Map<String, Object>> page, LaborFee laborFee); 
	
	public List<Map<String, Object>> getTransFeeList(LaborFee laborFee);
	
	public Page<Map<String, Object>> goWhInstallFeeJqGrid(Page<Map<String, Object>> page, LaborFee laborFee);
	
	public Page<Map<String, Object>> findIntQtyPageBySql(Page<Map<String, Object>> page, LaborFee laborFee);
	
	public Page<Map<String, Object>> goTileCutFeeJqGrid(Page<Map<String, Object>> page, LaborFee laborFee);
	
	public List<Map<String, Object>> getLaborFeeDetail(String no);
	
	public Double getAmountByNo(String no);

	public Page<Map<String,Object>> findGoodPrjPageBySql(Page<Map<String, Object>> page,LaborFee laborFee);
	
}
