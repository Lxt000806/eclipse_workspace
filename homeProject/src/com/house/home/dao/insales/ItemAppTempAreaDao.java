package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppTempArea;

@SuppressWarnings("serial")
@Repository
public class ItemAppTempAreaDao extends BaseDao {

	/**
	 * ItemAppTempArea分页信息
	 * 
	 * @param page
	 * @param itemAppTempArea
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTempArea itemAppTempArea) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemAppTempArea a where 1=1 ";

    	if (StringUtils.isNotBlank(itemAppTempArea.getNo())) {
			sql += " and a.No=? ";
			list.add(itemAppTempArea.getNo());
		}
    	if (StringUtils.isNotBlank(itemAppTempArea.getIatno())) {
			sql += " and a.IATNo=? ";
			list.add(itemAppTempArea.getIatno());
		}
    	if (itemAppTempArea.getFromArea() != null) {
			sql += " and a.FromArea=? ";
			list.add(itemAppTempArea.getFromArea());
		}
    	if (itemAppTempArea.getToArea() != null) {
			sql += " and a.ToArea=? ";
			list.add(itemAppTempArea.getToArea());
		}
    	if (itemAppTempArea.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemAppTempArea.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemAppTempArea.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemAppTempArea.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemAppTempArea.getExpired()) || "F".equals(itemAppTempArea.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemAppTempArea.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemAppTempArea.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

