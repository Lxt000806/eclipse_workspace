package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzyDept;

@SuppressWarnings("serial")
@Repository
public class CzyDeptDao extends BaseDao {

	/**
	 * CzyDept分页信息
	 * 
	 * @param page
	 * @param czyDept
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyDept czyDept) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCZYDept a where 1=1 ";

    	if (czyDept.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(czyDept.getPk());
		}
    	if (StringUtils.isNotBlank(czyDept.getCzybh())) {
			sql += " and a.CZYBH=? ";
			list.add(czyDept.getCzybh());
		}
    	if (StringUtils.isNotBlank(czyDept.getDepartment1())) {
			sql += " and a.Department1=? ";
			list.add(czyDept.getDepartment1());
		}
    	if (StringUtils.isNotBlank(czyDept.getDepartment2())) {
			sql += " and a.Department2=? ";
			list.add(czyDept.getDepartment2());
		}
    	if (StringUtils.isNotBlank(czyDept.getDepartment3())) {
			sql += " and a.Department3=? ";
			list.add(czyDept.getDepartment3());
		}
    	if (czyDept.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(czyDept.getDateFrom());
		}
		if (czyDept.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(czyDept.getDateTo());
		}
    	if (StringUtils.isNotBlank(czyDept.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(czyDept.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(czyDept.getExpired()) || "F".equals(czyDept.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(czyDept.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(czyDept.getActionLog());
		}
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<CzyDept> findByCzybh(String czybh) {
		String hql = "from CzyDept a where a.czybh=? order by a.department1,a.department2,a.department3 ";
		return this.find(hql, new Object[]{czybh});
	}

	public void deleteByCzybh(String czybh) {
		String hql = "delete from CzyDept a where a.czybh=?";
		this.executeUpdate(hql, new Object[]{czybh});
	}

}

