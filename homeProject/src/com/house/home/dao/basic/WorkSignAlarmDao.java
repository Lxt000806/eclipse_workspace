package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkSignAlarm;

@SuppressWarnings("serial")
@Repository
public class WorkSignAlarmDao extends BaseDao {

	/**
	 * WorkSignAlarm分页信息
	 * 
	 * @param page
	 * @param workSignAlarm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkSignAlarm workSignAlarm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.pk,a.PrjItem2, a.IsComplete, a.IsNeedReq, a.ItemType1,a.ItemType2, a.JobType, a.LastUpdate, a.LastUpdatedBy, "
				   + " a.Expired, a.ActionLog , b.Descr PrjItem2Descr,X1.NOTE IsCompleteDescr,X2.NOTE IsNeedReqDescr,it1.Descr ItemType1Descr,it2.Descr ItemType2Descr,c.Descr JobTypeDescr, "
				   + " wt.descr NotExistOffWorkType12Descr,a.ConfirmPrjItem,d.Descr ConfirmPrjItemDescr, "
				   + " x3.note WorkerClassifyDescr, x4.NOTE TypeDescr "
				   + " from  tWorkSignAlarm a " 
				   + " left outer join tPrjItem2 b on b.Code=a.PrjItem2 "
				   + " left outer join tItemType1 it1 on it1.code=a.ItemType1 "
				   + " left outer join tItemType2 it2 on it2.code=a.ItemType2 "
				   + " left outer join tXTDM x1 on x1.cbm=a.IsComplete and x1.id='YESNO' "
				   + " left outer join tXTDM x2 on x2.cbm=a.IsNeedReq and x2.id='YESNO' " 
				   + " left outer join tJobType c on c.code=a.JobType "  
				   + " left outer join tWorkType2 wt on wt.code=a.NotExistOffWorkType12 "
				   + " left outer join tPrjItem1 d on d.code=a.ConfirmPrjItem "
				   + " left join tXtdm x3 on x3.id = 'WORKERCLASSIFY' and x3.cbm = a.WorkerClassify "
				   + " left join tXTDM x4 on x4.ID = 'WORKALARMTYPE' and x4.CBM = a.Type "
				   + " where 1=1 ";

    
    	if (StringUtils.isNotBlank(workSignAlarm.getPrjItem2())) {
			sql += " and a.PrjItem2=? ";
			list.add(workSignAlarm.getPrjItem2());
		}
    	if (StringUtils.isNotBlank(workSignAlarm.getIsComplete())) {
			sql += " and a.IsComplete=? ";
			list.add(workSignAlarm.getIsComplete());
		}
    	if (StringUtils.isNotBlank(workSignAlarm.getJobType())) {
			sql += " and a.JobType=? ";
			list.add(workSignAlarm.getJobType());
		}
    	
    	if (StringUtils.isNotBlank(workSignAlarm.getType())) {
            sql += " and a.Type = ? ";
            list.add(workSignAlarm.getType());
        }
    	
		if (StringUtils.isBlank(workSignAlarm.getExpired()) || "F".equals(workSignAlarm.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdatedBy";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

