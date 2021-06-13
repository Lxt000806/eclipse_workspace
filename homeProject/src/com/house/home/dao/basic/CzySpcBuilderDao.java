package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzySpcBuilder;

@SuppressWarnings("serial")
@Repository
public class CzySpcBuilderDao extends BaseDao {

	/**
	 * CzySpcBuilder分页信息
	 * 
	 * @param page
	 * @param czySpcBuilder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzySpcBuilder czySpcBuilder) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.CZYBH,a.SpcBuilder,b.ZWXM,c.Descr SpcBuilderDescr "
		+"from tCZYSpcBuilder a left join tCZYBM b on a.CZYBH=b.CZYBH "
		+"left join tBuilder c on a.SpcBuilder=c.Code "
		+"where 1=1";

    	if (czySpcBuilder.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(czySpcBuilder.getPk());
		}
    	if (StringUtils.isNotBlank(czySpcBuilder.getCzybh())) {
			sql += " and a.CZYBH=? ";
			list.add(czySpcBuilder.getCzybh());
		}
    	if (StringUtils.isNotBlank(czySpcBuilder.getSpcBuilder())) {
			sql += " and a.SpcBuilder=? ";
			list.add(czySpcBuilder.getSpcBuilder());
		}
    	if (czySpcBuilder.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(czySpcBuilder.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(czySpcBuilder.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(czySpcBuilder.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(czySpcBuilder.getExpired()) || "F".equals(czySpcBuilder.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(czySpcBuilder.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(czySpcBuilder.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public CzySpcBuilder getByCzybhAndSpcBuilder(String czybh, String spcBuilder) {
		String hql = "from CzySpcBuilder where czybh=? and spcBuilder=?";
		List<CzySpcBuilder> list =  this.find(hql, new Object[]{czybh,spcBuilder});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

