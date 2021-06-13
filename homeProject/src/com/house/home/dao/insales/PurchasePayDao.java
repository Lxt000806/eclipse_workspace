package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.PurchasePay;

@SuppressWarnings("serial")
@Repository
public class PurchasePayDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql( Page<Map<String, Object>> page, PurchasePay purchasePay) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select a.*,s1.Note typedescr,s2.Note Statusdescr from tPurchasePay a  " +
				" left join tXTDM s1 on s1.CBM = a.type and s1.ID = 'PURPAYTYPE' " +
				" left join tXTDM s2 on s2.CBM = a.Status and s2.ID = 'PURPAYSTATUS' " +
				"where 1=1 ";
		
		if(StringUtils.isNotBlank(purchasePay.getPuno())){
			sql+=" and a.PUNo= ?";
			list.add(purchasePay.getPuno());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.pk ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	
	}
}
