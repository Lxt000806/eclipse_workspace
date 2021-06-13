package com.house.home.dao.project;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;

import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class CompletionReportDao extends BaseDao{
	
	
	public Map<String, Object> getCompletionReportInfo(int custWkPk) {
		String sql=  "select c.Address, b.Descr WorkType12Descr, a.PlanEnd, a.WorkType12, a.ConPlanEnd  from tCustWorker a "
				+" inner join tWorkType12 b on b.Code=a.WorkType12 "
				+" inner join tCustomer c on a.CustCode=c.Code "
				+" where a.PK = ? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custWkPk});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> getNowProgress(int custWkPk) {
		String sql = " select case when DATEDIFF(day,a.beginDate,GETDATE())*1.0/ "
				+" (case when a.contructDay=0 or a.ContructDay is null then 1 else a.contructDay end)>=0.6 then '1' else '0' end nowProgress,a.ConPlanEnd "
				+" from( select "
				+" (select min(CrtDate) from tWorkSign where CustWkPk=tcw.PK and IsComplete <> '1') beginDate, "
				+" (select dbo.fGetWorkTypeConDay(tcw.CustCode,tcw.WorkType12)) contructDay,tcw.PK, tcw.ConPlanEnd "
				+" from tCustWorker tcw  "
				+" )a "
				+" where a.PK=?";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custWkPk});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
