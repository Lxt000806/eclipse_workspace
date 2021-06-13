package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;

@SuppressWarnings("serial")
@Repository
public class PrjTaskExecAnalyDao extends BaseDao {

	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String department1, String department2){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( " +
					 " select count(b.NameChi) UnExecTask ,b.NameChi bRcvCZY,a.RcvCZY, " +
					 "     sum(case when datediff(day, a.PlanDealDate, getdate()) > 0 then 1 else 0 end) DelayTask, " +
					 "     (select count(in_a.PK) from tPersonMessage in_a " +
					 "         where in_a.RcvCZY = a.RcvCZY and in_a.SendDate > getdate() and in_a.MsgType = '1' " +
					 "             and exists (select 1 from tDelayExec in_in_a where in_in_a.MsgPK = in_a.PK)) DelayNoTrriggerTask " +
					 " from tPersonMessage a " +
					 " inner join temployee b ON b.Number=a.RcvCZY " +
					 " where a.MsgType='1' and a.RcvStatus = '0' ";
		if(dateFrom != null){
			sql += " and a.sendDate >= ? ";
			params.add(DateUtil.startOfTheDay(dateFrom));
		}
		if(dateTo != null){
			sql += " and a.sendDate <= ? ";
			params.add(DateUtil.endOfTheDay(dateTo));
		}
        if (StringUtils.isNotBlank(department1)) {
            String str = SqlUtil.resetStatus(department1);
            sql = sql + " and b.Department1 in (" + str + ") ";
        }
        if (StringUtils.isNotBlank(department2)) {
            String str = SqlUtil.resetStatus(department2);
            sql = sql + " and b.Department2 in (" + str + ") ";
        }
		sql += " group by b.NameChi,a.RcvCZY ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.UnExecTask desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goJqGridView(Page<Map<String, Object>> page, Date dateFrom, Date dateTo,String rcvCZY) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select isnull(b.Address,'') address,e.NameChi RcvCZY,a.PlanDealDate,a.Title,substring(a.MsgText, charindex(':', a.MsgText)+1,100) MsgText," +
					 " d.remarks delayRemarks,d.Date delayDate,b.Code,x1.Note progmsgtypedescr,a.sendDate, " +
					 " d.LastUpdate " +
					 " from tPersonMessage a " +
					 " left join tCustomer b on a.MsgRelCustCode=b.Code" +
					 " left join (select max(pk) PK,MsgPK from tDelayExec group by MsgPK) c on c.MsgPK =a.Pk " +
					 " left join tDelayExec d on d.pk = c.pk " +
					 " inner join temployee e on e.Number = a.RcvCZY " +
					 " left join tXTDM x1 on x1.CBM = a.ProgMsgType and x1.ID = 'ALARMTYPE' " + 
					 " where a.MsgType = '1' and a.RcvStatus = '0' ";
		if(dateFrom != null){
			sql += " and a.sendDate >= ? ";
			params.add(DateUtil.startOfTheDay(dateFrom));
		}
		if(dateTo != null){
			sql += " and a.sendDate <= ? ";
			params.add(DateUtil.endOfTheDay(dateTo));
		}
		if(StringUtils.isNotBlank(rcvCZY)){
			sql += " and a.RcvCZY = ? ";
			params.add(rcvCZY);
		}
		sql += " order by a.ProgMsgType desc,b.Address asc,a.PlanDealDate asc ";
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Page<Map<String, Object>> goJqGrid_prjDelayNoTrrigerTask(Page<Map<String, Object>> page, Date dateFrom, Date dateTo,String rcvCZY) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select isnull(b.Address,'') address,e.NameChi RcvCZY,a.PlanDealDate,a.Title,substring(a.MsgText, charindex(':', a.MsgText)+1,100) MsgText," +
					 " d.remarks delayRemarks,d.Date delayDate,b.Code,x1.Note progmsgtypedescr,a.sendDate, " +
					 " d.LastUpdate " +
					 " from tPersonMessage a " +
					 " left join tCustomer b on a.MsgRelCustCode=b.Code" +
					 " left join (select max(pk) PK,MsgPK from tDelayExec group by MsgPK) c on c.MsgPK =a.Pk " +
                     " left join tDelayExec d on d.pk = c.pk " +
					 " inner join temployee e on e.Number = a.RcvCZY " +
					 " left join tXTDM x1 on x1.CBM = a.ProgMsgType and x1.ID = 'ALARMTYPE' " + 
					 " where a.MsgType = '1' and d.Date is not null and a.sendDate > getDate() ";

		if(StringUtils.isNotBlank(rcvCZY)){
			sql += " and a.RcvCZY = ? ";
			params.add(rcvCZY);
		}
		sql += " order by a.ProgMsgType desc,b.Address asc,a.PlanDealDate asc ";
		return this.findPageBySql(page, sql, params.toArray());
	}
}
