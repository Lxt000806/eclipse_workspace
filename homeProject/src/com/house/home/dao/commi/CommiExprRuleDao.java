package com.house.home.dao.commi;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.commi.CommiExprRule;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class CommiExprRuleDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiExprRule commiExprRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.pk, a.Role, a.CustType, a.Department, a.Prior, a.PreCommiExprPK, a.CheckCommiExprPK, a.LastUpdate," +
				" a.LastUpdatedBy, a.Expired, a.ActionLog,b.Descr CheckCommiExprDescr, c.Descr PreCommiExprDescr,d.Desc1 departmentdescr,e.descr roledescr," +
				" f.Desc1 custTypeDescr,c.expr preCommiExpr,b.expr checkCommiexpr,c.ExprRemarks PreCommiExprRemarks,b.ExprRemarks CHeckCommiExprRemarks " +
				" from tCommiExprRule a" +
				" left join tCommiExpr b on b.PK = a.CheckCommiExprPK" +
				" left join tCommiExpr c on c.PK = a.PreCommiExprPK" +
				" left join tDepartment d on d.Code = a.Department" +
				" left join tRoll e on e.Code = a.Role" +
				" left join tCustType f on f.Code = a.CustType" +
				" where 1=1";
    	
		if(StringUtils.isNotBlank(commiExprRule.getCustType())){
			sql+=" and a.custType = ? ";
			list.add(commiExprRule.getCustType());
		}
		if(StringUtils.isNotBlank(commiExprRule.getDepartment())){
			sql+=" and a.Department in ( "+SqlUtil.resetStatus(commiExprRule.getDepartment())+")";
		}
		if(StringUtils.isNotBlank(commiExprRule.getRole())){
			sql+=" and a.role = ? ";
			list.add(commiExprRule.getRole());
		}
		if (StringUtils.isBlank(commiExprRule.getExpired())
				|| "F".equals(commiExprRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}
