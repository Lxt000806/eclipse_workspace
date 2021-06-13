package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.WareHouseDao;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.insales.WareHouseService;

@SuppressWarnings("serial")
@Service
public class WareHouseServiceImpl extends BaseServiceImpl implements WareHouseService {

	@Autowired
	private WareHouseDao wareHouseDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHouse wareHouse){
		return wareHouseDao.findPageBySql(page, wareHouse);
	}
	
    @Override
    public Page<Map<String, Object>> findLimitItemType2PageBySql(Page<Map<String, Object>> page,
            WareHouse wareHouse) {

        return wareHouseDao.findLimitItemType2PageBySql(page, wareHouse);
    }

    @Override
    public Page<Map<String, Object>> findLimitRegionPageBySql(Page<Map<String, Object>> page,
            WareHouse wareHouse) {

        return wareHouseDao.findLimitRegionPageBySql(page, wareHouse);
    }

    @Override
    public Result saveForProc(WareHouse wareHouse) {
        return wareHouseDao.saveForProc(wareHouse);
    }
    
	@Override
	public List<WareHouse> findByNoExpired() {
		return wareHouseDao.findByNoExpired();
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlCode(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		return wareHouseDao.findPageBySqlCode(page,wareHouse);
	}

	@Override
	public boolean hasWhRight(Czybm czybm, WareHouse wareHouse) {
		return wareHouseDao.hasWhRight(czybm, wareHouse);
	}

	@Override
	public Page<Map<String, Object>> getWHItemDetail(
			Page<Map<String, Object>> page2, String iaNo) {
		return wareHouseDao.getWHItemDetail(page2, iaNo);
	}

}
