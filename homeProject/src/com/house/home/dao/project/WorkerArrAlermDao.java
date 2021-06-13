package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.WorkerArrAlerm;

@SuppressWarnings("serial")
@Repository
public class WorkerArrAlermDao extends BaseDao{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,  WorkerArrAlerm workerArrAlerm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.*,b.descr Item1Descr ,c.descr item2Descr ,d.descr JobTYpeDescr,x1.note isNeedReqDescr," +
				" e.descr workType12Descr, x2.note isNeedStakeholderDescr " +
				" from tWorkerArrAlarm a " +
				" left join tItemType1 b on b.code=a.itemType1 " +
				" left join tItemType2 c on c.code=a.itemtype2 " +
				" left join tJobType d on d.code=a.JobType " +
				" left join tXTDM x1 on x1.cbm=a.Isneedreq and x1.id='YESNO'" +
				" left join tXTDM x2 on x2.cbm=a.IsNeedStakeholder and x2.id='YESNO'" +
				" left join tWorkType12 e on e.code=a.workType12 " +
				" where 1=1" ;
		if(StringUtils.isNotBlank(workerArrAlerm.getWorkType12())){
			sql += " and a.workType12 in " + "('"+workerArrAlerm.getWorkType12().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(workerArrAlerm.getItemType1())){
			sql+=" and a.ItemType1 = ? ";
			list.add(workerArrAlerm.getItemType1());
		}
		if(StringUtils.isNotBlank(workerArrAlerm.getItemType2())){
			sql+=" and a.ItemType2 = ? ";
			list.add(workerArrAlerm.getItemType2());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.lastupdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}	
}
