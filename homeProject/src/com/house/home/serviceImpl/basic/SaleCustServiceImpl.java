package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.SaleCustDao;
import com.house.home.entity.basic.SaleCust;
import com.house.home.service.basic.SaleCustService;

@SuppressWarnings("serial")
@Service 
public class SaleCustServiceImpl extends BaseServiceImpl implements SaleCustService{
	
	@Autowired
	private  SaleCustDao saleCustDao;
	
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,SaleCust saleCust){
		return saleCustDao.findPageBySql(page,saleCust);
	}
	
	public SaleCust getByDesc1(String desc1){
		return saleCustDao.getByDesc1(desc1);
	}
	
	public List<SaleCust> findByNoExpired(){
		return saleCustDao.findByNoExpired();
		
	}

	@Override
	public List<Map<String, Object>> getDBcol(String dbName) {
		// TODO Auto-generated method stub
		return saleCustDao.getDBcol(dbName);
	}
	
	
}
