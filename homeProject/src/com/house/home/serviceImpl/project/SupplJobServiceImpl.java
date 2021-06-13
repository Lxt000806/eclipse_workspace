package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.SupplJobDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.SupplJob;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.SupplJobService;

@SuppressWarnings("serial")
@Service
public class SupplJobServiceImpl extends BaseServiceImpl implements SupplJobService {

	@Autowired
	private SupplJobDao supplJobDao;
	
	@Autowired
	private CustomerService customerService;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplJob supplJob){
		return supplJobDao.findPageBySql(page, supplJob);
	}
	
	@Override
	public void doDelSuppl(Integer pk) {
			supplJobDao.doDelSuppl(pk);
	}

	@Override
	public void doExec(String type,String no,Integer pk, String recvDate,String supplRemarks,String planDate,String lastUpdatedBy) {
		supplJobDao.doExec(type, no, pk, recvDate, supplRemarks, planDate, lastUpdatedBy); 
	}

	@Override
	public Page<Map<String, Object>> findCupboardPageBySql(Page<Map<String, Object>> page, SupplJob supplJob) {
		return supplJobDao.findCupboardPageBySql(page, supplJob);
	}

	@Override
	public void doUpdate(SupplJob supplJob) {
		supplJobDao.doUpdate(supplJob);
	}

    @Override
    public String getBuildPassByCustCode(String custCode) {
        
        Customer customer = customerService.get(Customer.class, custCode);
        
        return customer.getBuildPass();
    }

}
