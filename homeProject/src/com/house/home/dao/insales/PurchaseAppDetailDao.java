package com.house.home.dao.insales;
import com.house.framework.commons.orm.BaseDao;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.insales.PurchaseAppDetail;
@SuppressWarnings("serial")
@Repository
public class PurchaseAppDetailDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchaseAppDetail purchaseAppDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.* " +
				"	from tPurchaseAppDetail a" +
				"	where 1=1 " ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}

}
