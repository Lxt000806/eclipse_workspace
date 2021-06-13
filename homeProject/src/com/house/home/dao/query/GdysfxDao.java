package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class GdysfxDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer,String orderBy,String direction) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pGdysfx ?,?,?,?,?,?,? ";
		 list.add(customer.getDateFrom());
		 list.add(customer.getDateTo());
		 list.add(customer.getDepartment2());
		 list.add(customer.getPrjItem());
		list.add(orderBy);
		list.add(direction);
		list.add(customer.getRegion());
		page.setResult(findListBySql(sql, list.toArray())); 			
		page.setTotalCount(page.getResult().size());
		return page;
	}
}
