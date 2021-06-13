package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.MtRegion;

@SuppressWarnings("serial")
@Repository
public class MtRegionDao extends BaseDao {

	/**
	 * MtRegion分页信息
	 * 
	 * @param page
	 * @param mtRegion
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MtRegion mtRegion) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.regioncode,a.descr,a.lastupdate,a.lastupdatedby,	a.expired,a.actionLog,a.BelongRegionCode,"
				 + "  b.descr BelongRegionDescr " 
				 + " from tMtRegion a" 
				 + " inner join tMtRegion b on b.regioncode=a.BelongRegionCode "
				 + " where 1=1  ";

    	if (StringUtils.isNotBlank(mtRegion.getRegionCode())) {
			sql += " and a.RegionCode=? ";
			list.add(mtRegion.getRegionCode());
		}
    	if (StringUtils.isNotBlank(mtRegion.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + mtRegion.getDescr() + "%");
		}
    	if (StringUtils.isNotBlank(mtRegion.getBelongRegionDescr())) {
			sql += " and b.Descr like ? ";
			list.add("%" + mtRegion.getBelongRegionDescr() + "%");
		}
    	if (mtRegion.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(mtRegion.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(mtRegion.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(mtRegion.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(mtRegion.getExpired()) || "F".equals(mtRegion.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(mtRegion.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(mtRegion.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.RegionCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

