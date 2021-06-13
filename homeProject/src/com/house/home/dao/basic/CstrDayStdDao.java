package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CstrDayStd;

@SuppressWarnings("serial")
@Repository
public class CstrDayStdDao extends BaseDao {

	/**
	 * CstrDayStd分页信息
	 * 
	 * @param page
	 * @param cstrDayStd
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CstrDayStd cstrDayStd) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.pk,a.CustType,c.Desc1 CustTypeDescr,a.Layout,f.NOTE LayoutDescr,"
				+ "a.fromarea,a.toarea,a.constructday,a.prior,a.lastupdatedby,a.lastupdate,a.expired,a.actionlog "
				+ "from tCstrDayStd  a "
				+ "left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ "left outer join tCustType c on a.custType=c.Code where 1=1 ";

		if (StringUtils.isBlank(cstrDayStd.getExpired())
				|| "F".equals(cstrDayStd.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(cstrDayStd.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(cstrDayStd.getCustType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by lastupdate  desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

