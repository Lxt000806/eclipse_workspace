package com.house.home.dao.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.ManualPerfCust;

@SuppressWarnings("serial")
@Repository
public class ManualPerfCustDao extends BaseDao {

	/**
	 * ManualPerfCust分页信息
	 * 
	 * @param page
	 * @param manualPerfCust
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ManualPerfCust manualPerfCust) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tManualPerfCust a where 1=1 ";

    	if (manualPerfCust.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(manualPerfCust.getPk());
		}
    	if (StringUtils.isNotBlank(manualPerfCust.getPerfCycleNo())) {
			sql += " and a.PerfCycleNo=? ";
			list.add(manualPerfCust.getPerfCycleNo());
		}
    	if (StringUtils.isNotBlank(manualPerfCust.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(manualPerfCust.getCustCode());
		}
    	if (StringUtils.isNotBlank(manualPerfCust.getIsCalcPerf())) {
			sql += " and a.IsCalcPerf=? ";
			list.add(manualPerfCust.getIsCalcPerf());
		}
    	if (manualPerfCust.getAchieveDate() != null) {
			sql += " and a.AchieveDate=? ";
			list.add(manualPerfCust.getAchieveDate());
		}
    	if (StringUtils.isNotBlank(manualPerfCust.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(manualPerfCust.getRemarks());
		}
    	if (manualPerfCust.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(manualPerfCust.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(manualPerfCust.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(manualPerfCust.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(manualPerfCust.getExpired()) || "F".equals(manualPerfCust.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(manualPerfCust.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(manualPerfCust.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

