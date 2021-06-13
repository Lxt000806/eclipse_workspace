package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustAccount;

@SuppressWarnings("serial")
@Repository
public class WxCustomerDao extends BaseDao {

	public Page<Map<String, Object>> getCustCodeListByOpenId(
			Page<Map<String, Object>> page, String openid) {
		List<Object> list = new ArrayList<Object>();
		list.add(openid);
		String sql = " select c.address,c.code custCode, c.area,c.CrtDate,c.EndDate ,c.Status from tCustAccount a "
				+" left join tCustMapped b on a.pk=b.CustAccountPK "
				+" left join tCustomer c on b.CustCode=c.Code "
				+" where a.WeChatOpenid =? ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	

	@SuppressWarnings("unchecked")
	public CustAccount getCustAccountByOpenId(String openId) {
		String hql="from CustAccount where WeChatOpenid=?";
		List<CustAccount> lists=this.find(hql, new Object[] { openId });
		if(lists!=null&&lists.size()>0){
			return lists.get(0);
		}
		return null;
	}
}
