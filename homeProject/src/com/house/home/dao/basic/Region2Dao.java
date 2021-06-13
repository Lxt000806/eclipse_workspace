package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Region2;

@SuppressWarnings("serial")
@Repository
public class Region2Dao extends BaseDao {

	/**
	 * Activity分页信息
	 * 
	 * @param page
	 * @param activity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,Region2 region2) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.Code,a.Descr,a.RegionCode,b.descr RegionDescr, a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,"
			      + " a.PrjRegionCode,t.Descr PrjRegionDescr from tRegion2 a "
			      + " left outer join  tRegion b on b.code=a.RegionCode "
			      + " left outer join  tPrjRegion t on t.code=a.PrjRegionCode "
			      + " where 1=1 ";
		if(StringUtils.isNotBlank(region2.getCode())){
			sql+=" and a.code like ? ";
			list.add("%"+region2.getCode()+"%");
		}
		if(StringUtils.isNotBlank(region2.getDescr())){
			sql+=" and a.Descr like ? ";
			list.add("%"+region2.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(region2.getRegionCode())){
			sql+=" and b.code =? ";
			list.add(region2.getRegionCode());
		}
		if (StringUtils.isBlank(region2.getExpired()) || "F".equals(region2.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	public boolean validDescr(String descr){
		String	sql = "select * from tRegion2 where descr=? ";
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(descr)) {
			params.add(descr);
		}
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if (list!=null && list.size()>0) {
			return true;
		}
		return false;
	};
}

