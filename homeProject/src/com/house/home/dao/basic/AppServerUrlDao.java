package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.AppServerUrl;

@SuppressWarnings("serial")
@Repository
public class AppServerUrlDao extends BaseDao {

	/**
	 * AppServerUrl分页信息
	 * 
	 * @param page
	 * @param appServerUrl
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AppServerUrl appServerUrl) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tAppServerUrl a where 1=1 ";

    	if (StringUtils.isNotBlank(appServerUrl.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(appServerUrl.getDescr());
		}
    	if (StringUtils.isNotBlank(appServerUrl.getUrl())) {
			sql += " and a.Url=? ";
			list.add(appServerUrl.getUrl());
		}
    	if (StringUtils.isNotBlank(appServerUrl.getPort())) {
			sql += " and a.Port=? ";
			list.add(appServerUrl.getPort());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Descr";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

