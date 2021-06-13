package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.PrePlan;

@SuppressWarnings("serial")
@Repository
public class PrePlanDao extends BaseDao {

	/**
	 * PrePlan分页信息
	 * 
	 * @param page
	 * @param prePlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrePlan prePlan) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tPrePlan a where 1=1 ";

    	if (StringUtils.isNotBlank(prePlan.getNo())) {
			sql += " and a.No=? ";
			list.add(prePlan.getNo());
		}
    	if (StringUtils.isNotBlank(prePlan.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prePlan.getCustCode());
		}
    	if (prePlan.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(prePlan.getDateFrom());
		}
		if (prePlan.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(prePlan.getDateTo());
		}
    	if (StringUtils.isNotBlank(prePlan.getTempNo())) {
			sql += " and a.TempNo=? ";
			list.add(prePlan.getTempNo());
		}
    	if (StringUtils.isNotBlank(prePlan.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(prePlan.getRemarks());
		}
    	if (StringUtils.isNotBlank(prePlan.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prePlan.getLastUpdatedBy());
		}
    	if (prePlan.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(prePlan.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(prePlan.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prePlan.getActionLog());
		}
		if (StringUtils.isBlank(prePlan.getExpired()) || "F".equals(prePlan.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

