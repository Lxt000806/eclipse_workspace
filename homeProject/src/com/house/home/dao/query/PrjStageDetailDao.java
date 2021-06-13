package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class PrjStageDetailDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pThisStageProgAnaly ?,?,?,?,?,?,?";
		list.add(customer.getBeginDateFrom());
		list.add(customer.getBeginDateTo());
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getDepartment2());
		list.add(customer.getDepartment1());
		list.add(customer.getIsComplete());
		page.setResult(findListBySql(sql, list.toArray())); 		
		page.setTotalCount(page.getResult().size());
		return page;
	}
	
}
