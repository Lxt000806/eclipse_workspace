package com.house.home.dao.basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.TaxPayee;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class TaxPayeeDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, TaxPayee taxPayee) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( " +
				" select a.Code, a.Descr, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog " +
				" from tTaxPayee a " +
				" where 1=1 ";
		if(StringUtils.isNotBlank(taxPayee.getCode())){
			sql+=" and a.code = ? ";
			list.add(taxPayee.getCode());
		}
		if(StringUtils.isNotBlank(taxPayee.getDescr())){
			sql+=" and a.descr like ? ";
			list.add("%"+taxPayee.getDescr()+"%");
		}
		if (StringUtils.isBlank(taxPayee.getExpired())
				|| "F".equals(taxPayee.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> getTaxPayeeList() {
		String sql = " select rTrim(Code) Code, Descr from tTaxPayee where Expired = 'F' ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {});
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

}
