package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.IntMeasureBrand;

@SuppressWarnings("serial")
@Repository
public class IntMeasureBrandDao extends BaseDao {

	/**
	 * IntMeasureBrand分页信息
	 * 
	 * @param page
	 * @param intMeasureBrand
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntMeasureBrand intMeasureBrand) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tIntMeasureBrand a where 1=1 ";

    	if (StringUtils.isNotBlank(intMeasureBrand.getCode())) {
			sql += " and a.Code=? ";
			list.add(intMeasureBrand.getCode());
		}
    	if (StringUtils.isNotBlank(intMeasureBrand.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(intMeasureBrand.getDescr());
		}
    	if (StringUtils.isNotBlank(intMeasureBrand.getType())) {
			sql += " and a.Type=? ";
			list.add(intMeasureBrand.getType());
		}
    	if (intMeasureBrand.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(intMeasureBrand.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(intMeasureBrand.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(intMeasureBrand.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(intMeasureBrand.getExpired()) || "F".equals(intMeasureBrand.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(intMeasureBrand.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(intMeasureBrand.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> getByType(String type) {
		String sql = "select * from tIntMeasureBrand where type=? order by code";
		return this.findBySql(sql, new Object[]{type});
	}

}

