package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.insales.ItemBalAdjHeaderDao;
import com.house.home.entity.insales.ItemBalAdjHeader;
import com.house.home.service.insales.ItemBalAdjHeaderService;

@SuppressWarnings("serial")
@Service
public class ItemBalAdjHeaderServiceImpl extends BaseServiceImpl implements ItemBalAdjHeaderService {

	@Autowired
	private ItemBalAdjHeaderDao itemBalAdjHeaderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBalAdjHeader itemBalAdjHeader,UserContext uc){
		return itemBalAdjHeaderDao.findPageBySql(page, itemBalAdjHeader ,uc);
	}

	@Override
	public Result doItemBalAdjHeaderSave(ItemBalAdjHeader itemBalAdjHeader){
		return itemBalAdjHeaderDao.doItemBalAdjHeaderSave(itemBalAdjHeader);
	}
	
	
	@Override
	public Result doItemBalAdjHeaderUpdate(ItemBalAdjHeader itemBalAdjHeader){
		return itemBalAdjHeaderDao.doItemBalAdjHeaderUpdate(itemBalAdjHeader);
	}
	
	@Override
	public Result doItemBalAdjHeaderVerify(ItemBalAdjHeader itemBalAdjHeader,String status){
		return itemBalAdjHeaderDao.doItemBalAdjHeaderVerify(itemBalAdjHeader,status);
	}
}
