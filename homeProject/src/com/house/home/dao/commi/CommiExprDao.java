package com.house.home.dao.commi;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiExpr;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class CommiExprDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiExpr commiExpr) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select PK,Descr, Expr, ExprRemarks, Remarks, LastUpdate, " +
				" LastUpdatedBy, Expired, ActionLog,RightCardinalExprRemarks,RightCardinalExpr " +
				" from tCommiExpr  " +
				" where 1=1 ";
    	
		if(StringUtils.isNotBlank(commiExpr.getDescr())){
			sql+=" and descr like  ? ";
			list.add("%"+commiExpr.getDescr()+"%");
		}
		if (StringUtils.isBlank(commiExpr.getExpired())
				|| "F".equals(commiExpr.getExpired())) {
			sql += " and Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}
