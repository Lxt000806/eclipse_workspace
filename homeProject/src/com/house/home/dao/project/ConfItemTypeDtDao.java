package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ConfItemTypeDt;

@SuppressWarnings("serial")
@Repository
public class ConfItemTypeDtDao extends BaseDao {

	/**
	 * ConfItemTypeDt分页信息
	 * 
	 * @param page
	 * @param confItemTypeDt
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemTypeDt confItemTypeDt) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tConfItemTypeDt a where 1=1 ";

    	if (confItemTypeDt.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(confItemTypeDt.getPk());
		}
    	if (StringUtils.isNotBlank(confItemTypeDt.getConfItemType())) {
			sql += " and a.ConfItemType=? ";
			list.add(confItemTypeDt.getConfItemType());
		}
    	if (StringUtils.isNotBlank(confItemTypeDt.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(confItemTypeDt.getItemType2());
		}
    	if (StringUtils.isNotBlank(confItemTypeDt.getItemType3())) {
			sql += " and a.ItemType3=? ";
			list.add(confItemTypeDt.getItemType3());
		}
    	if (confItemTypeDt.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(confItemTypeDt.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(confItemTypeDt.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(confItemTypeDt.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(confItemTypeDt.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(confItemTypeDt.getActionLog());
		}
		if (StringUtils.isBlank(confItemTypeDt.getExpired()) || "F".equals(confItemTypeDt.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

