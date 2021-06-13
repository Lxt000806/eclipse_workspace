package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.bean.design.CustPaypreSaveBean;
import com.house.home.dao.design.CustPayPreDao;
import com.house.home.entity.design.CustPayPre;
import com.house.home.service.design.CustPayPreService;

@SuppressWarnings("serial")
@Service
public class CustPayPreServiceImpl extends BaseServiceImpl implements CustPayPreService {

	@Autowired
	private CustPayPreDao custPayPreDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustPayPre custPayPre){
		return custPayPreDao.findPageBySql(page, custPayPre);
	}

	@Override
	public CustPayPre getCustPayPre(String custCode, String payType) {
		// TODO Auto-generated method stub
		return custPayPreDao.getCustPayPre(custCode,payType);
	}

	@Override
	public Result doCustPaypreForProc(CustPaypreSaveBean custPaypreSaveBean) {
		// TODO Auto-generated method stub
		return custPayPreDao.doCustPaypreForProc(custPaypreSaveBean);
	}

}
