package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustOrder;

@SuppressWarnings("serial")
@Repository
public class CustOrderDao extends BaseDao {

	public boolean existsCustOrder(String phone){
		List<Object> params = new ArrayList<Object>();
		String sql = "select 1 from tCustOrder where Mobile1=? ";
		if(StringUtils.isNotBlank(phone)){
			params.add(phone);
		}else{
			params.add("");
		}
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, CustOrder custOrder){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select a.Descr,a.Mobile1,a.Date from tCustOrder a "
				   + " 		where 1=1 ";
		if(StringUtils.isNotBlank(custOrder.getMobile1())){
			sql += " and a.Mobile1 like ? ";
			params.add("%"+custOrder.getMobile1()+"%");
		}
		if(StringUtils.isNotBlank(custOrder.getDescr())){
			sql += " and a.Descr like ? ";
			params.add("%"+custOrder.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
}

