package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.CustMapped;
@SuppressWarnings("serial")
@Repository
public class CustAccountDao extends BaseDao {

	public CustAccount getCustAccountByPhone(String phone, String mm) {
		String hql="from CustAccount where Mobile1=?";
		List<Object> list=new ArrayList<Object>();
		list.add(phone);
		if(StringUtils.isNotBlank(mm)){
			hql+=" and MM=?";
			list.add(mm);
		}
		List<CustAccount> lists=this.find(hql, list.toArray());
		if(lists!=null&&lists.size()>0){
			return lists.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getCustCodeListByPhone(String phone) {
		String sql="select tc.code,tc.address,tc.confirmBegin beginDate,tc.status,tc.endDate,tc.endCode,b.CustAccountPK "
				+" from tCustAccount a "
				+" left join tCustMapped b on a.PK = b.CustAccountPK "
				+" left join tCustomer tc on tc.Code = b.CustCode and tc.Expired='F' "
				+" where a.Mobile1 = ? and tc.Status in ('2','3','4','5') and tc.EndCode in ('0','3','4')"
				+" ORDER BY tc.CrtDate ASC ";
		return this.findBySql(sql, new Object[]{phone});
	}
	
	public List<Map<String, Object>> getCustCodeListByPhoneFromCustomer(String portalAccount,String phone) {
		String sql="select code from tCustomer "
				+" where Expired='F' and code not in( select CustCode from tCustMapped a left join tCustAccount b on a.CustAccountPK=b.PK where b.Mobile1= ? ) "
				+" and Mobile1=? and Status in ('2','3','4','5') and EndCode in ('0','3','4')";
		return this.findBySql(sql, new Object[]{portalAccount,phone});
	}
	
	public void saveCustMapped(CustMapped custMapped){
		String sql=" insert into tCustMapped ( CustCode, CustAccountPK ) values  (?,?) ";
		this.executeUpdateBySql(sql, new Object[]{custMapped.getCustCode(),custMapped.getCustAccountPK()});
	}
	
	public List<Map<String, Object>> getGiftCustCodeListByPhone(String phone) {
		String sql="select tc.code custCode,tc.address,tc.confirmBegin beginDate,tc.status,tc.endDate,tc.endCode,b.CustAccountPK "
				+" from tCustAccount a "
				+" inner join tCustMapped b on a.PK = b.CustAccountPK "
				+" left join tCustomer tc on tc.Code = b.CustCode "
				+" where a.Mobile1 = ? and tc.Expired='F' "
				+" ORDER BY tc.CrtDate ASC ";
		return this.findBySql(sql, new Object[]{phone});
	}
}
