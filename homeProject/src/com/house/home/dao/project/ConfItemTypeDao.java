package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ConfItemType;

@SuppressWarnings("serial")
@Repository
public class ConfItemTypeDao extends BaseDao {

	/**
	 * ConfItemType分页信息
	 * 
	 * @param page
	 * @param confItemType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemType confItemType) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tConfItemType a where 1=1 ";

    	if (StringUtils.isNotBlank(confItemType.getCode())) {
			sql += " and a.Code=? ";
			list.add(confItemType.getCode());
		}
    	if (StringUtils.isNotBlank(confItemType.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(confItemType.getDescr());
		}
    	if (StringUtils.isNotBlank(confItemType.getItemTimeCode())) {
			sql += " and a.ItemTimeCode=? ";
			list.add(confItemType.getItemTimeCode());
		}
    	if (confItemType.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(confItemType.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(confItemType.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(confItemType.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(confItemType.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(confItemType.getActionLog());
		}
		if (StringUtils.isBlank(confItemType.getExpired()) || "F".equals(confItemType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

