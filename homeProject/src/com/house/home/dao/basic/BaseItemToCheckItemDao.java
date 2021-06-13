package com.house.home.dao.basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BaseItemToCheckItem;
@SuppressWarnings("serial")
@Repository
public class BaseItemToCheckItemDao extends BaseDao{

	/**
	 * @Description: TODO 主页面分页查询
	 * @author	created by zb
	 * @date	2018-9-20--上午9:49:28
	 * @param page
	 * @param baseItemToCheckItem
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			BaseItemToCheckItem baseItemToCheckItem) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.BaseItemCode,b.Descr BaseItemDescr,a.BaseCheckItemCode,c.Descr BaseCheckItemDescr, " +
				" a.CalType,tx.NOTE CalTypeDescr,a.Qty,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
				" from tBaseItemToCheckItem a " +
				" left join tBaseItem b on b.Code = a.BaseItemCode " +
				" left join tBaseCheckItem c on c.Code = a.BaseCheckItemCode " +
				" left join tXTDM tx on tx.CBM = a.CalType and tx.ID = 'CKITEMCALTYPE' " +
				" where 1=1 ";
		
		if (StringUtils.isBlank(baseItemToCheckItem.getExpired()) || "F".equals(baseItemToCheckItem.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(baseItemToCheckItem.getBaseItemCode())) {
			sql += " and a.BaseItemCode = ? ";
			list.add(baseItemToCheckItem.getBaseItemCode());
		}
		if (StringUtils.isNotBlank(baseItemToCheckItem.getBaseCheckItemCode())) {
			sql += " and a.BaseCheckItemCode = ? ";
			list.add(baseItemToCheckItem.getBaseCheckItemCode());
		}
		if (StringUtils.isNotBlank(baseItemToCheckItem.getCalType())) {
			sql += " and a.CalType = ? ";
			list.add(baseItemToCheckItem.getCalType());
		}
		if (StringUtils.isNotBlank(baseItemToCheckItem.getBaseItemType1())) {
			sql += " and b.BaseItemType1 = ? ";
			list.add(baseItemToCheckItem.getBaseItemType1());
		}
		if (StringUtils.isNotBlank(baseItemToCheckItem.getBaseItemType2())) {
			sql += " and b.BaseItemType2 = ? ";
			list.add(baseItemToCheckItem.getBaseItemType2());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description:【基础项目编号+基础结算项目编号】不能重复
	 * @author	created by zb
	 * @date	2018-9-20--下午2:46:02
	 * @param baseItemCode
	 * @param baseCheckItemCode
	 * @return true:存在
	 */
	public boolean checkCode(String baseItemCode, String baseCheckItemCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from tBaseItemToCheckItem where BaseItemCode=? and BaseCheckItemCode=?";
		list.add(baseItemCode);
		list.add(baseCheckItemCode);
		List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		return !page.isEmpty();
	}
}
