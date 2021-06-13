package com.house.home.serviceImpl.basic;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.CustGiftItemDao;
import com.house.home.service.basic.CustGiftItemService;

@SuppressWarnings("serial")
@Service 
public class CustGiftItemServiceImpl extends BaseServiceImpl implements CustGiftItemService {
	@Autowired
	private  CustGiftItemDao custGiftItemDao;

}
