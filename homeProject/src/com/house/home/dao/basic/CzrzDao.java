package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Czrz;

@SuppressWarnings("serial")
@Repository
public class CzrzDao extends BaseDao {

	/**
	 * Czrz分页信息
	 * 
	 * @param page
	 * @param czrz
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Czrz czrz) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCZRZ a where 1=1 ";

		if (czrz.getCid() != null) {
			sql += " and a.CID=? ";
			list.add(czrz.getCid());
		}
		if (czrz.getDateFrom() != null) {
			sql += " and a.CZDate>= ? ";
			list.add(czrz.getDateFrom());
		}
		if (czrz.getDateTo() != null) {
			sql += " and a.CZDate<= ? ";
			list.add(czrz.getDateTo());
		}
		if (StringUtils.isNotBlank(czrz.getCzybh())) {
			sql += " and a.CZYBH=? ";
			list.add(czrz.getCzybh());
		}
		if (StringUtils.isNotBlank(czrz.getMkdm())) {
			sql += " and a.MKDM=? ";
			list.add(czrz.getMkdm());
		}
		if (StringUtils.isNotBlank(czrz.getRefPk())) {
			sql += " and a.RefPK=? ";
			list.add(czrz.getRefPk());
		}
		if (StringUtils.isNotBlank(czrz.getCzlx())) {
			sql += " and a.CZLX=? ";
			list.add(czrz.getCzlx());
		}
		if (StringUtils.isNotBlank(czrz.getZy())) {
			sql += " and a.ZY like ? ";
			list.add("%"+czrz.getZy()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.Cid";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}
