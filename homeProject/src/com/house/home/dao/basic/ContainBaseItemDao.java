package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ContainBaseItem;

@SuppressWarnings("serial")
@Repository
public class ContainBaseItemDao extends BaseDao {

	/**
	 * ContainBaseItem分页信息
	 * 
	 * @param page
	 * @param containBaseItem
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ContainBaseItem containBaseItem) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.BaseItemCode,b.Descr from tContainBaseItem a inner join tBaseItem b on b.code=a.BaseItemCode    where 1=1  ";
    	if (StringUtils.isNotBlank(containBaseItem.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(containBaseItem.getCustType());
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

