package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustTypeBrandProfitPer;

@SuppressWarnings("serial")
@Repository
public class CustTypeBrandProfitPerDao extends BaseDao {

	/**
	 * CustTypeBrandProfitPer分页信息
	 * 
	 * @param page
	 * @param custTypeBrandProfitPer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustTypeBrandProfitPer custTypeBrandProfitPer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.ProfitPer,b.Desc1 CustTypeDescr,c.Descr BrandDescr, " 
				+"a.LastUpdate,a.Expired,a.LastUpdatedBy,a.ActionLog " 
				+"from tCustTypeBrandProfitPer a "
				+"left join tCustType b on a.CustType=b.Code "
				+"left join tBrand c on a.BrandCode=c.Code where 1=1 ";

    	if (StringUtils.isNotBlank(custTypeBrandProfitPer.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(custTypeBrandProfitPer.getCustType());
		}
    	if (StringUtils.isNotBlank(custTypeBrandProfitPer.getBrandCode())) {
			sql += " and a.BrandCode=? ";
			list.add(custTypeBrandProfitPer.getBrandCode());
		}
		if (StringUtils.isBlank(custTypeBrandProfitPer.getExpired()) || "F".equals(custTypeBrandProfitPer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.PK";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

