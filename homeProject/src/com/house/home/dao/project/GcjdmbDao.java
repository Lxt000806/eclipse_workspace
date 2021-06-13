package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.project.PrjProgTemp;

@SuppressWarnings("serial")
@Repository
public class GcjdmbDao extends BaseDao {

	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, PrjProgTemp prjProgTemp){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select a.No,a.Descr,a.Type,X1.Note TypeDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Remarks,ct.Desc1 CustTypeDescr "
				   + " 		from tPrjProgTemp a "
				   + " 		left join tXTDM X1 on a.Type=X1.cbm and X1.ID='PROGTEMPTYPE' "
				   + " 		left join tCustType ct on a.CustType=ct.Code  "
				   + " 		where 1=1 ";
		if(StringUtils.isNotBlank(prjProgTemp.getNo())){
			sql += " and a.No like ? ";
			params.add("%"+prjProgTemp.getNo()+"%");
		}
		if(StringUtils.isNotBlank(prjProgTemp.getDescr())){
			sql += " and a.Descr like ? ";
			params.add("%"+prjProgTemp.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(prjProgTemp.getType())){
			sql += " and a.type = ? ";
			params.add(prjProgTemp.getType());
		}
    	if("F".equals(prjProgTemp.getExpired())){
    		sql += " and a.Expired='F' ";
    	}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Map<String, Object> isPrjItemExist(Integer pk, String tempNo, String prjItem){
		List<Object> params = new ArrayList<Object>();
		String sql = " select 1 from tProgTempDt where 1=1 ";
		if(pk != null && pk > 0){
			sql += " and PK<>? ";
			params.add(pk);
		}
		if(StringUtils.isNotBlank(prjItem)){
			sql += " and PrjItem=? ";
			params.add(prjItem);
		}
		if(StringUtils.isNotBlank(tempNo)){
			sql += " and TempNo=? ";
			params.add(tempNo);
		}
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> getTipEvents(String tempNo, String prjItem){
		String sql = " select * from tProgTempAlarm where TempNo=? and PrjItem=? ";
		return this.findBySql(sql, new Object[]{tempNo, prjItem});
	}
	
	public Page<Map<String,Object>> goJqGridProgTempDt(Page<Map<String, Object>> page, String no,String prjItems, String custCode){
		String sql = " select a.PK,a.TempNo,a.PrjItem,b.Descr Note,a.PlanBegin,a.PlanEnd,"
					//动态模板且存在计算规则就按新的计算方式计算否则用原来的计算方法 modify by zb on 20200326
				   + " case when d.Type='2' and dbo.fGetConDayByCalcRule(?,a.PrjItem,'2',?)>-1 "	
				   + "   then dbo.fGetConDayByCalcRule(?,a.PrjItem,'2',?) else a.PlanEnd - a.PlanBegin + 1 end ConsDay, "
				   + " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.SpaceDay,a.DispSeq,a.BefPrjItem,c.Descr BefPrjItemDescr, "
				   + " e.NOTE TypeDescr,a.Type,a.BaseConDay,a.BaseWork,a.DayWork "
				   + " from tProgTempDt a" 
				   + " left join tPrjItem1 b on a.PrjItem = b.Code "
				   + " left join tPrjItem1 c on a.BefPrjItem = c.Code "
				   + " inner join tPrjProgTemp d on d.No=a.TempNo "
				   + " left join tXTDM e on a.Type = e.CBM and e.ID = 'DAYCALCTYPE' "
				   + " where a.TempNo=? ";
	   
		if(StringUtils.isNotBlank(prjItems)){
			sql+=" and a.PrjItem not in ("+SqlUtil.resetStatus(prjItems)+")";
		}
		sql+= " order by a.DispSeq,a.PK ";
		return this.findPageBySql(page, sql, new Object[]{custCode, no, custCode, no, no});
	}
	
	public Page<Map<String,Object>> goJqGridProgTempAlarm(Page<Map<String, Object>> page, String no){
		String sql = " select a.pk,a.TempNo,a.PrjItem,x1.Descr PrjItemDescr,a.DayType,x2.note DayTypeDescr,a.AddDay,a.MsgTextTemp,a.LastUpdate, "
				   + " a.LastUpdatedBy,a.Expired,a.ActionLog,a.Role,b.Descr RoleDescr,a.Type,x3.note TypeDescr,a.DealDay, "
				   + " a.CompleteDay,a.PrePK,a.ItemType1,c.Descr ItemType1Descr,a.ItemType2,h.Descr ItemType2Descr,a.IsNeedReq, "
				   + " x4.note IsNeedReqDescr,a.MsgTextTemp2,a.WorkType1,a.OfferWorkType2,e.Descr WorkType1Descr,j.Descr OfferWorkType2Descr,a.WorkType12, "
				   + " x6.NOTE RemindCZYTypeDescr,i.NameChi CzyDescr,a.CZYBH,a.RemindCZYType,"
				   + " f.Descr WorkType12Descr,a.JobType,g.Descr JobTypeDescr,a.Title,a.IsAutoJob,x5.note IsAutoJobDescr from tProgTempAlarm a "
				   + " inner join tPrjItem1 x1 on x1.Code=a.PrjItem "
				   + " inner join tXTDM x2 on x2.ID='ALARMDAYTYPE' and x2.CBM=a.DayType "
				   + " inner join tProgTempDt d on a.PrjItem=d.PrjItem and d.TempNo=? "
				   + " left join tRoll b on a.Role=b.Code "
				   + " left join tXTDM x3 on x3.ID='ALARMTYPE' and x3.CBM=a.Type "
				   + " left join tItemType1 c on c.Code=a.ItemType1 "
				   + " left join tItemType2 h on h.Code=a.ItemType2 and h.ItemType1=a.ItemType1 "
				   + " left join tXTDM x4 on x4.ID='YESNO' and x4.CBM=a.IsNeedReq "
				   + " left join tWorkType1 e on  e.Code=a.WorkType1 "
				   + " left join tWorkType12 f on  f.Code=a.WorkType12 "
				   + " left join tXTDM x5 on x5.ID='YESNO' and x5.CBM=a.IsAutoJob "
				   + " left join tJobType g on  g.Code=a.JobType  "
				   + " left join tXTDM x6 on a.RemindCZYType=x6.CBM and x6.ID='REMINCZYTYPE' "
				   + " left join tEmployee i on a.CZYBH=i.Number " 
				   + " left join tWorkType2 j on a.OfferWorkType2=j.Code "
				   + " where a.TempNo=?"
				   + " order by d.DispSeq,d.PK,a.DayType,a.AddDay,a.PK ";
		return this.findPageBySql(page, sql, new Object[]{no, no});
	}
	
	public Result doSavePrjProgTempForProc(PrjProgTemp prjProgTemp, String progTempDt, String progTempAlarm){
		Assert.notNull(prjProgTemp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pInserttPrjProgTemp_forXml(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, prjProgTemp.getDescr());
			call.setString(2, prjProgTemp.getRemarks());
			call.setString(3, prjProgTemp.getLastUpdatedBy());
			call.setString(4, prjProgTemp.getType());
			call.setString(5, progTempDt);
			call.setString(6, progTempAlarm);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.setString(9, prjProgTemp.getCustType());
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doUpdatePrjProgTempForProc(PrjProgTemp prjProgTemp, String progTempDt){
		Assert.notNull(prjProgTemp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pUpdatetPrjProgTemp_DispSeq_forXml(?,?,?,?)}");
			call.setString(1, prjProgTemp.getNo());
			call.setString(2, progTempDt);
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
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
	
	public void doUpdateDispSeq(String tempNo, int dispSeq){
		String hql = "update ProgTempDt set dispSeq=dispSeq-1 where tempNo = ? and dispSeq > ?";
		this.executeUpdate(hql, tempNo,dispSeq);
	}
}	
