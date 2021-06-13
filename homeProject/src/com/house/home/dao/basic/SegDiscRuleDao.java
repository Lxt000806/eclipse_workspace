package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SegDiscRule;

@SuppressWarnings("serial")
@Repository
public class SegDiscRuleDao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SegDiscRule segDiscRule) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.CustType,b.Desc1 CustTypeDescr,a.DiscAmtType,c.Descr DiscAmtTypeDescr, "
					+ "a.MinArea,a.MaxArea,a.MaxDiscAmount,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DirectorDiscAmount "
					+ "from tSegDiscRule a "
					+ "left join tCusttype b on b.Code=a.CustType "
					+ "left join tItemType1 c on c.Code=a.DiscAmtType "	
					+ "where 1=1 ";

		if (StringUtils.isBlank(segDiscRule.getExpired()) || "F".equals(segDiscRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(segDiscRule.getCustType())) {
			sql += " and a.CustType in ('"+segDiscRule.getCustType().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(segDiscRule.getDiscAmtType())) {
			sql += " and a.DiscAmtType in ('"+segDiscRule.getDiscAmtType().replace(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}

