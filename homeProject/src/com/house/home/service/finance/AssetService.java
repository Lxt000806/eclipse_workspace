package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.bean.basic.AssetType;
import com.house.home.entity.basic.AssetChg;
import com.house.home.entity.finance.Asset;

public interface AssetService extends BaseService{

	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, Asset asset);
	
	public Page<Map<String, Object>>  getChgDetailBySql(Page<Map<String,Object>> page, Asset asset);

	public Page<Map<String, Object>>  getDeprDetailBySql(Page<Map<String,Object>> page, Asset asset);
	
	public Page<Map<String, Object>>  getDeprGroupListBySql(Page<Map<String,Object>> page, Asset asset);

	public Page<Map<String, Object>>  findAssetTypeBySql(Page<Map<String,Object>> page, AssetType assetType);

	public Page<Map<String, Object>>  getAssetChgByCode(Page<Map<String,Object>> page, Asset asset);

	public Page<Map<String, Object>>  getAssetDeprByCode(Page<Map<String,Object>> page, Asset asset);

	public String getSufCode(String assetType);
	
	public void doChg(AssetChg assetChg, Asset asset);
	
	public Result doCalcDepr(String period, String lastUpdatedBy);

	public Result doBatchChg(AssetChg assetChg);

	public boolean getAssetDeprFlag(String assetCode);
}
