package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.GiftDao;
import com.house.home.entity.basic.Gift;
import com.house.home.service.basic.GiftService;

@SuppressWarnings("serial")
@Service
public class GiftServiceImpl extends BaseServiceImpl implements GiftService {

	@Autowired
	private GiftDao giftDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Gift gift){
		return giftDao.findPageBySql(page, gift);
	}

	@Override
	public Page<Map<String, Object>> goCustTypeJqGrid(Page<Map<String, Object>> page, Gift gift) {
		return giftDao.goCustTypeJqGrid(page, gift);
	}

	@Override
	public Page<Map<String, Object>> goItemJqGrid(Page<Map<String, Object>> page, Gift gift) {
		return giftDao.goItemJqGrid(page, gift);
	}

	@Override
	public Result doSaveProc(Gift gift) {
		return giftDao.doSaveProc(gift);
	}

    @Override
    public void doSetCustType(Map<String, String> params) {
        
        String giftPkString = params.get("giftPkString");
        if (StringUtils.isBlank(giftPkString)) {
            throw new IllegalArgumentException("缺少赠送项目主键");
        }
        
        String custType = params.get("custType");
        if (StringUtils.isBlank(custType)) {
            throw new IllegalArgumentException("缺少客户类型");
        }
        
        String czybh = params.get("czybh");
        
        String[] giftPks = giftPkString.split(",");
        for (String giftPk : giftPks) {
            giftDao.addCustTypeForGiftIfAbsent(Integer.valueOf(giftPk), custType, czybh);
        }
        
    }

}
