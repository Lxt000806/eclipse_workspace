package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ProgCheckPlanDetail;

@SuppressWarnings("serial")
@Repository
public class ProgCheckPlanDetailDao extends BaseDao {

	/**
	 * ProgCheckPlanDetail分页信息
	 * 
	 * @param page
	 * @param progCheckPlanDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ProgCheckPlanDetail progCheckPlanDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tProgCheckPlanDetail a where 1=1 ";

    	if (progCheckPlanDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(progCheckPlanDetail.getPk());
		}
    	if (StringUtils.isNotBlank(progCheckPlanDetail.getPlanNo())) {
			sql += " and a.PlanNo=? ";
			list.add(progCheckPlanDetail.getPlanNo());
		}
    	if (StringUtils.isNotBlank(progCheckPlanDetail.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(progCheckPlanDetail.getCustCode());
		}
    	if (StringUtils.isNotBlank(progCheckPlanDetail.getStatus())) {
			sql += " and a.Status=? ";
			list.add(progCheckPlanDetail.getStatus());
		}
    	if (StringUtils.isNotBlank(progCheckPlanDetail.getAppCZY())) {
			sql += " and a.AppCZY=? ";
			list.add(progCheckPlanDetail.getAppCZY());
		}
    	if (progCheckPlanDetail.getAppDate() != null) {
			sql += " and a.AppDate=? ";
			list.add(progCheckPlanDetail.getAppDate());
		}
    	if (StringUtils.isNotBlank(progCheckPlanDetail.getCheckNo())) {
			sql += " and a.CheckNo=? ";
			list.add(progCheckPlanDetail.getCheckNo());
		}
    	if (StringUtils.isNotBlank(progCheckPlanDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(progCheckPlanDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(progCheckPlanDetail.getExpired()) || "F".equals(progCheckPlanDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(progCheckPlanDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(progCheckPlanDetail.getActionLog());
		}
    	if (progCheckPlanDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(progCheckPlanDetail.getLastUpdate());
		}
    	if (progCheckPlanDetail.getAppPk() != null) {
			sql += " and a.AppPK=? ";
			list.add(progCheckPlanDetail.getAppPk());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public ProgCheckPlanDetail getByAppPk(Integer appPk) {
		String hql = "from ProgCheckPlanDetail where appPk=?";
		List<ProgCheckPlanDetail> list = this.find(hql, new Object[]{appPk});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

