package com.house.home.dao.basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.ProjectCtrlPrice;

import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class ProjectCtrlPriceDao extends BaseDao{

	/**
	 * @Description: TODO 发包单价分页查询
	 * @author	created by zb
	 * @date	2018-10-25--上午9:49:12
	 * @param page
	 * @param projectCtrlPrice
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ProjectCtrlPrice projectCtrlPrice) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from(select a.Pk,a.FromArea,ToArea,a.CustType,a.Price,b.Desc1 CustTypeDescr, " +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.ManageFee,a.BaseQuotaPrice,a.MinArea " +
				" from tProjectCtrlPrice a " +
				" left outer join tCustType b on b.Code=a.CustType " +
				" where 1=1 ";
		
		if (StringUtils.isBlank(projectCtrlPrice.getExpired()) || "F".equals(projectCtrlPrice.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (null != projectCtrlPrice.getpK()) {
			sql += " and a.PK like ? ";
			list.add("%"+projectCtrlPrice.getpK()+"%");
		}
		if (StringUtils.isNotBlank(projectCtrlPrice.getCustType())) {
			sql += " and a.CustType= ? ";
			list.add(projectCtrlPrice.getCustType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdatedBy desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}



}
