package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchaseDelay;


@SuppressWarnings("serial")
@Repository
public class PurchaseDelayDao extends BaseDao {
	
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PurchaseDelay purchaseDelay) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select a.Pk,a.PUNo,a.ArriveDate,a.Remarks,a.LastUpdate,a.LastUpdatedBy  from tPurchaseDelay a where 1=1 ";
		
		if (purchaseDelay.getPk() != null) { 
			sql += " and a.PK=? ";
			list.add(purchaseDelay.getPk());
		}
		
		if (purchaseDelay.getPuno() != null) { 
			sql += " and a.PUNo=? ";
			list.add(purchaseDelay.getPuno());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a. desc" + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.Pk desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	
	}
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
