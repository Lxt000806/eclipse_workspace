package com.house.home.serviceImpl.insales;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.insales.ItemTransactionDao;
import com.house.home.entity.insales.ItemTransaction;
import com.house.home.service.insales.ItemTransactionService;
@SuppressWarnings("serial")
@Service
public class ItemTransactionServiceImpl extends BaseServiceImpl implements
		ItemTransactionService {
	@Autowired
	private ItemTransactionDao itemTransactionDao;
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemTransaction itemTransaction,UserContext uc) {
		// TODO Auto-generated method stub
		return itemTransactionDao.findPageBySql(page,itemTransaction,uc);
	}

}
