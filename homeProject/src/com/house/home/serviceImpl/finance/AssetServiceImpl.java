package com.house.home.serviceImpl.finance;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.house.home.bean.basic.AssetType;
import com.house.home.dao.finance.AssetDao;
import com.house.home.entity.basic.AssetChg;
import com.house.home.entity.finance.Asset;
import com.house.home.service.finance.AssetService;

@SuppressWarnings("serial")
@Service 
public class AssetServiceImpl extends BaseServiceImpl implements AssetService {
	@Autowired
	private  AssetDao assetDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Asset asset) {
		return assetDao.findPageBySql(page, asset);
	}
	
	@Override
	public Page<Map<String, Object>> getChgDetailBySql(
			Page<Map<String, Object>> page, Asset asset) {
		return assetDao.getChgDetailBySql(page, asset);
	}
	
	@Override
	public Page<Map<String, Object>> getDeprDetailBySql(
			Page<Map<String, Object>> page, Asset asset) {
		return assetDao.getDeprDetailBySql(page, asset);
	}
	
	@Override
	public Page<Map<String, Object>> getDeprGroupListBySql(
			Page<Map<String, Object>> page, Asset asset) {
		return assetDao.getDeprGroupListBySql(page, asset);
	}
	
	@Override
	public Page<Map<String, Object>> findAssetTypeBySql(
			Page<Map<String, Object>> page, AssetType assetType) {
		return assetDao.findAssetTypeBySql(page, assetType);
	}
	
	@Override
	public Page<Map<String, Object>> getAssetChgByCode(
			Page<Map<String, Object>> page, Asset asset) {
		return assetDao.getAssetChgByCode(page, asset);
	}

	@Override
	public Page<Map<String, Object>> getAssetDeprByCode(
			Page<Map<String, Object>> page, Asset asset) {
		return assetDao.getAssetDeprByCode(page, asset);
	}

	@Override
	public String getSufCode(String assetType) {

		return assetDao.getSufCode(assetType);
	}

	@Override
	public void doChg(AssetChg assetChg, Asset asset) {
		// 编辑固定资产
		update(asset);
		
		// 保存资产变动单
		save(assetChg);
	}

	@Override
	public Result doCalcDepr(String period, String lastUpdatedBy) {
		return assetDao.doCalcDepr(period, lastUpdatedBy);
	}

	@Override
	public Result doBatchChg(AssetChg assetChg) {
		return assetDao.doBatchChg(assetChg);
	}

	@Override
	public boolean getAssetDeprFlag(String assetCode) {
		return assetDao.getAssetDeprFlag(assetCode);
	}
	
	
}
