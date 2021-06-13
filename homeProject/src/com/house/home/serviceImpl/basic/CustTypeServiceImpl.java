package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustTypeDao;
import com.house.home.entity.basic.CustType;
import com.house.home.service.basic.CustTypeService;

@SuppressWarnings("serial")
@Service
public class CustTypeServiceImpl extends BaseServiceImpl implements CustTypeService {

	@Autowired
	private CustTypeDao custTypeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustType custType){
		return custTypeDao.findPageBySql(page, custType);
	}
	
	@Override
	public List<CustType> findByDefaultStatic() {
		return custTypeDao.findByDefaultStatic();
	}

	@Override
	public List<Map<String, Object>> checkExist(CustType custType) {
		return custTypeDao.checkExist(custType);
	}

	@Override
	public List<CustType> findByIsAddAllInfo() {
		// TODO Auto-generated method stub
		return custTypeDao.findByIsAddAllInfo();
	}

	@Override
	public boolean isFdlCust(String custType) {
		return custTypeDao.isFdlCust(custType);
	}

	@Override
	public List<CustType> findByAllCustType() {
		// TODO Auto-generated method stub
		return custTypeDao.findByAllCustType();
	}

	@Override
	public String getNeedCode(CustType custType) {
		String codes="";
		List<Map<String,Object>> list = this.custTypeDao.getNeedCode(custType);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String code = list.get(i).get("Code").toString();
				if (0==i) {
					codes = code;
				} else {
					codes += ","+code;
				}
			}
		}
		return codes;
	}

	@Override
	public String getSelectCustType(CustType custType){
		
		return custTypeDao.getSelectCustType(custType);
	}
	

}
