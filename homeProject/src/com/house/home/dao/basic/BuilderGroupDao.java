package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BuilderGroup;

@SuppressWarnings("serial")
@Repository
public class BuilderGroupDao extends BaseDao {

	/**
	 * 项目大类分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BuilderGroup builderGroup) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tBuilderGroup a where 1=1 ";

		if (StringUtils.isNotBlank(builderGroup.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+builderGroup.getCode()+"%");
		}
		if (StringUtils.isNotBlank(builderGroup.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+builderGroup.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(builderGroup.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%"+builderGroup.getRemarks()+"%");
		}
		if (builderGroup.getLastUpdate() != null){
			sql += " and a.LastUpdate>=CONVERT(VARCHAR(10),?,120) and a.LastUpdate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(builderGroup.getLastUpdate());
			list.add(builderGroup.getLastUpdate());
		}
		if (StringUtils.isNotBlank(builderGroup.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy = ? ";
			list.add(builderGroup.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(builderGroup.getExpired())
				|| "F".equals(builderGroup.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.lastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**获取项目大类
	 * @param descr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BuilderGroup getByDescr(String descr) {
		String hql = "from BuilderGroup a where a.descr=? ";
		List<BuilderGroup> list = this.find(hql, new Object[]{descr});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<BuilderGroup> findByNoExpired() {
		String hql = "from BuilderGroup a where a.expired<>'T' ";
		return this.find(hql);
	}
}
