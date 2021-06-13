package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzyGnqx;

@SuppressWarnings("serial")
@Repository
public class CzyGnqxDao extends BaseDao {

	/**
	 * CzyGnqx分页信息
	 * 
	 * @param page
	 * @param czyGnqx
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyGnqx czyGnqx) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCZYGNQX a where 1=1 ";

    	if (StringUtils.isNotBlank(czyGnqx.getCzybh())) {
			sql += " and a.CZYBH=? ";
			list.add(czyGnqx.getCzybh());
		}
    	if (StringUtils.isNotBlank(czyGnqx.getMkdm())) {
			sql += " and a.MKDM=? ";
			list.add(czyGnqx.getMkdm());
		}
    	if (StringUtils.isNotBlank(czyGnqx.getGnmc())) {
			sql += " and a.GNMC=? ";
			list.add(czyGnqx.getGnmc());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Czybh";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public CzyGnqx getCzyGnqx(String mkdm, String gnmc, String czybh) {
		String hql = "from CzyGnqx where mkdm=? and gnmc=? and czybh=?";
		List<CzyGnqx> list = this.find(hql, new Object[]{mkdm, gnmc, czybh});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

