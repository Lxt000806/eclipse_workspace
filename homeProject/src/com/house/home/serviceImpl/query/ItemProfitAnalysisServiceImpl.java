package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemProfitAnalysisDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemProfitAnalysisService;

@SuppressWarnings("serial")
@Service
public class ItemProfitAnalysisServiceImpl extends BaseServiceImpl implements ItemProfitAnalysisService {

	@Autowired
	private ItemProfitAnalysisDao itemProfitAnalysisDao;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer){
		return itemProfitAnalysisDao.findPageBySql(page, customer);
	}

}
