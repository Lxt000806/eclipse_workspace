package com.house.home.serviceImpl.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.PayCheckOutDao;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PayCheckOut;
import com.house.home.service.finance.PayCheckOutService;

@SuppressWarnings("serial")
@Service
public class PayCheckOutServiceImpl extends BaseServiceImpl implements PayCheckOutService{

    @Autowired
    public PayCheckOutDao payCheckOutDao;
    
    /* 显示主界面数据和查询SQL语句 */
    @Override
    public Page<Map<String, Object>> findPageBySql(
            Page<Map<String, Object>> page, PayCheckOut payCheckOut) {
        return payCheckOutDao.findPageBySql(page, payCheckOut);
    }

    /* 多表查询客户付款单信息 */
    @Override
    public Page<Map<String, Object>> findCustPayPageBySql(
            Page<Map<String, Object>> page, CustPay custPay,
            PayCheckOut payCheckOut, Customer customer) {
        return payCheckOutDao.findCustPayPageBySql(page, custPay, payCheckOut, customer);
    }

    /* 保存收入记账订单  */
    @Override
    public Result savePayCheckOut(PayCheckOut payCheckOut) {
        return payCheckOutDao.savePayCheckOut(payCheckOut);
    }

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, PayCheckOut payCheckOut) {
		return payCheckOutDao.findDetailPageBySql(page, payCheckOut);
	}

	@Override
	public Map<String, Object> findMapBySql(PayCheckOut payCheckOut) {
		return payCheckOutDao.findMapBySql(payCheckOut);
	}

	@Override
	public Result checkPayCheckOut(PayCheckOut payCheckOut) {
		return payCheckOutDao.checkPayCheckOut(payCheckOut);
	}

}
