package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.IntSplPerfPer;

@SuppressWarnings("serial")
@Repository
public class IntSplPerfPerDao extends BaseDao {

	/**
	 * IntSplPerfPer分页信息
	 * 
	 * @param page
	 * @param intSplPerfPer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntSplPerfPer intSplPerfPer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.Per,a.LastUpdate,a.LastUpdatedBy, "
				+"a.Expired,a.ActionLog,a.SupplCode,b.Descr SupplDescr  "
				+"from tIntSplPerfPer a " 
				+"left join tSupplier b on a.SupplCode=b.Code  where 1=1 ";

    	if (StringUtils.isNotBlank(intSplPerfPer.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(intSplPerfPer.getSupplCode());
		}
		if (StringUtils.isBlank(intSplPerfPer.getExpired()) || "F".equals(intSplPerfPer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

