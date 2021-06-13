package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.CustProfitDao;
import com.house.home.entity.design.CustProfit;
import com.house.home.service.design.CustProfitService;

@SuppressWarnings("serial")
@Service
public class CustProfitServiceImpl extends BaseServiceImpl implements CustProfitService {

	@Autowired
	private CustProfitDao custProfitDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustProfit custProfit){
		return custProfitDao.findPageBySql(page, custProfit);
	}

	@Override
	public Result doCustProfitForProc(CustProfit custProfit) {
		// TODO Auto-generated method stub
		return custProfitDao.doCustProfitForProc(custProfit);
	}

}
