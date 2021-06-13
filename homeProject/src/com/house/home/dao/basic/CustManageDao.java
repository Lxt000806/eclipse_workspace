package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Advert;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.CustMapped;

@SuppressWarnings("serial")
@Repository
public class CustManageDao extends BaseDao {

	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, CustAccount custAccount){
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from (" +
				" select a.PK,a.Mobile1,a.RegisterDate,a.PicAddr,a.LastUpdate,a.Expired,a.NickName " +
				" from tCustAccount a where 1=1  ";
		
		if(StringUtils.isNotBlank(custAccount.getMobile1())){
			sql += " and a.Mobile1 like ? ";
			params.add("%"+custAccount.getMobile1()+"%");
		}
		
		if(StringUtils.isBlank(custAccount.getExpired()) || "F".equals(custAccount.getExpired())){
			sql += " and a.Expired = 'F' ";
		}
		
		if(custAccount.getRegisterDateFrom() != null){
			sql += " and a.RegisterDate >= ? ";
			params.add(custAccount.getRegisterDateFrom());
		}
		
		if(custAccount.getRegisterDateTo() != null){
			sql += " and a.RegisterDate < ? ";
			params.add(DateUtil.addDate(custAccount.getRegisterDateTo(), 1));
		}
		
		if("1".equals(custAccount.getHasBindAddress())){
			sql += " and a.pk in (select CustAccountPK from tCustMapped) ";
		}else if("0".equals(custAccount.getHasBindAddress())){
			sql += " and a.pk not in (select CustAccountPK from tCustMapped) ";
		}
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.RegisterDate desc ";
		}
		
		return this.findPageBySql(page, sql, params.toArray());
	}

	public List<Map<String, Object>> getCustomers(String phone){
		String sql = "select code,descr,address from tCustomer where (CustAccountPk is null or CustAccountPk='') and Mobile1=? ";
		return this.findBySql(sql, new Object[]{phone});
	}
	
	public Page<Map<String, Object>> goJqGridCustCode(Page<Map<String, Object>> page, CustAccount custAccount){
		String sql = " select * from ( "
				   + " 		select a.Code,a.Descr,a.Address,b.desc1 custtypedescr,x.note statusdescr from tCustMapped tcm "
				   + " 		left join tCustomer a on a.Code = tcm.CustCode " +
				   "        left join tCUstType b on b.code=a.custType " +
				   " 		left join txtdm x on x.cbm = a.status and x.id = 'CUSTOMERSTATUS'" +
				   " where tcm.CustAccountPk=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.code";
		}
		return this.findPageBySql(page, sql, new Object[]{custAccount.getPk()});
	}
	
	public CustMapped getCustMapped(Integer custAccountPK, String custCode){
		String hql = "from CustMapped where CustAccountPK = ? and CustCode = ? ";
		List<CustMapped> list = this.find(hql, 1, new Object[]{custAccountPK, custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}

