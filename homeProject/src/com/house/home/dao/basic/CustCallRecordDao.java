package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;

@SuppressWarnings("serial")
@Repository
public class CustCallRecordDao extends BaseDao {

	public Long getLastCustConByCZY(String czybh) {
		String sql = " select Max(CONVERT(BIGINT,DATEDIFF(MI,'1970-01-01 08:00:00.000', ConDate)) * 60000 + DATEPART(S,ConDate) * 1000 + DATEPART(MS, ConDate)) + 100 MaxConDate from tCustCon where ConMan = ? and Type='1' and ConDate >= dateadd(day, -1, getdate()) ";

    	List<Map<String, Object>> list = this.findBySql(sql, new Object[]{czybh});
    	if(list != null && list.size() > 0 && list.get(0).get("MaxConDate") != null){
    		return Long.parseLong(list.get(0).get("MaxConDate").toString());
    	}
    	
		return DateUtil.addDate(new Date(), -1).getTime();
	}
	
	@SuppressWarnings("deprecation")
	public Result doSaveCallList(String callList, String czybh, Long maxConDate){
		Assert.notNull(callList);
		Assert.notNull(czybh);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDoSaveCallList_forXml(?,?,?,?,?)}");
			call.setString(1, czybh);
			call.setString(2, callList);
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.setLong(5, maxConDate);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public List<Map<String, Object>> getUnUploadCallRecordList(String czybh){
		String sql = " select distinct tcr.mobileFilePath,tcr.pk "
				   + " from tCallRecord tcr "
				   + " inner join tCustCon tcc on tcr.PK = tcc.CallRecordPK "
				   + " where tcc.ConMan = ? and tcr.Status = '0' and tcc.IsCallValid = '1' and tcc.ConDuration > cast(( "
				   + " 		select in_a.QZ from tXTCS in_a where in_a.ID='DurationCtrl' "
				   + " ) as int) ";
		return this.findBySql(sql, new Object[]{czybh});
	}
	

	public Page<Map<String, Object>> getCallListOneMonth(Page<Map<String, Object>> page, String czybh, Date date){
		String sql = " select  case when isnull(tcc.ResrCustCode, '') <> '' then trc.Descr else tc.Descr end name,isnull(tx1.NOTE, '其他') callTypeDescr, "
				   + " isnull(tcc.conDuration, 0) conDuration, tcc.conDate, tcr.Name callRecord, tcr.mobileFilePath, tcr.status, tcc.remarks,tcc.type "
				   + " from ( "
				   + " 		select min(in_tcc.PK) PK "
				   + " 		from tCustCon in_tcc "
				   + " 		left join tResrCust in_trc on in_trc.Code = in_tcc.ResrCustCode "
				   + " 		where in_tcc.ConMan = ? and in_tcc.ConDate > ? and in_tcc.Type in ('1', '3') "
				   + " 		group by in_trc.Mobile1, in_tcc.Type,in_tcc.LastUpdate "
				   + " ) a"
				   + " left join tCustCon tcc on tcc.PK = a.PK " 
				   + " left join tXTDM tx1 on tx1.ID='CALLTYPE' and tx1.CBM = tcc.CallType "
				   + " left join tCZYBM tczy on tczy.CZYBH = tcc.ConMan "
				   + " left join tResrCust trc on trc.Code = tcc.ResrCustCode "
				   + " left join tCustomer tc on tc.Code = tcc.CustCode "
				   + " left join tCallRecord tcr on tcr.PK = tcc.CallRecordPK "
				   + " order by tcc.ConDate desc ";
		return this.findPageBySql(page, sql, new Object[]{czybh, date});
	}
}

