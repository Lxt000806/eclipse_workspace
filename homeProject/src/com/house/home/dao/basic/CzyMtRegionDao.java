package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzyMtRegion;
import com.house.home.entity.basic.Czybm;

@SuppressWarnings("serial")
@Repository
public class CzyMtRegionDao extends BaseDao {

	/**
	 * CzyMtRegion分页信息
	 * 
	 * @param page
	 * @param czyMtRegion
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyMtRegion czyMtRegion) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCZYMtRegion a where 1=1 ";

    	if (czyMtRegion.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(czyMtRegion.getPk());
		}
    	if (StringUtils.isNotBlank(czyMtRegion.getCzybh())) {
			sql += " and a.CZYBH=? ";
			list.add(czyMtRegion.getCzybh());
		}
    	if (StringUtils.isNotBlank(czyMtRegion.getMtRegionCode())) {
			sql += " and a.MtRegionCode=? ";
			list.add(czyMtRegion.getMtRegionCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 判断操作员是否有区域
	 * @param czybm 
	 * @return
	 */
	public boolean isHasRegion(CzyMtRegion czyMtRegion) {
		String sql =" select 1 from tCzyMtRegion where CZYBH=? and MtRegionCode=? " ;	
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {czyMtRegion.getCzybh(),czyMtRegion.getMtRegionCode()});
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

}

