package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Version;

@SuppressWarnings("serial")
@Repository
public class VersionDao extends BaseDao {

	/**
	 * Version分页信息
	 * 
	 * @param page
	 * @param version
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Version version) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tVersion a where 1=1 ";

    	if (StringUtils.isNotBlank(version.getName())) {
			sql += " and a.Name=? ";
			list.add(version.getName());
		}
    	if (StringUtils.isNotBlank(version.getVersionNo())) {
			sql += " and a.VersionNo=? ";
			list.add(version.getVersionNo());
		}
    	if (StringUtils.isNotBlank(version.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(version.getDescr());
		}
    	if (StringUtils.isNotBlank(version.getUrl())) {
			sql += " and a.Url=? ";
			list.add(version.getUrl());
		}
    	if (StringUtils.isNotBlank(version.getCompatible())) {
			sql += " and a.Compatible=? ";
			list.add(version.getCompatible());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Name";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}

