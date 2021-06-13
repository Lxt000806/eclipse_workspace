package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ItemType12;
import com.house.home.entity.basic.PersonMessage;

@SuppressWarnings("serial")
@Repository
public class prjMsgDao extends BaseDao {
	@Autowired
	private HttpServletRequest request;
	/**
	 * prjmsg分页信息
	 * 
	 * @param page
	 * @param prjmsg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PersonMessage personMessage,boolean hasSelfRight,boolean hasDeptRight,boolean hasAllRight) {
		List<Object> list = new ArrayList<Object>();
		UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		String sql =  " select * from ( select a.pk,isnull(n.Address,'') Address,a.MsgType,b.note BMsgType,a.MsgText,a.MsgRelNo,a.MsgRelCustCode,a.CrtDate,"
					+ " a.SendDate,a.rcvType,c.note BrcvType, a.RcvCZY,d.NameChi BRcvCZY,isnull(d2.desc1,'') Department2Descr,a.RcvDate,a.RcvStatus,E.note BRcvStatus,"
					+ " a.isPush ,F.note BisPush,a.pushStatus,g.note BpushStatus,  "
					+ " a.ProgmsgType,h.NOTE ProgmsgTypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,isnull(j.Descr,'') ItemType2Descr," 
					+ " a.WorkType12,k.Descr WorkType12Descr,a.PrjItem,isnull(l.NOTE,'') PrjItemDescr,a.JobType,m.Descr JobTypeDescr,"  
					+ " a.PlanDealDate,a.PlanArrDate,isnull(a.Remarks,'') Remarks,isnull(a.Title,'') Title,"
					+ " (case when rcvdate is null then (case when convert(char(10), getdate(), 120) > convert(char(10), a.PlanDealDate, 120) then '是'" +
						" else '否'  end) else  (case when convert(char(10), a.RcvDate, 120) > convert(char(10), a.PlanDealDate, 120) then '是' else '否' " 
					+ " end) end )deal,e_2.namechi prjmandescr ,e_2.phone prjmanphone,d_2.Desc2 pajmanDept2 ,a.msgRelCustCode custCode" +
					"   ,q.remarks delayRemarks "
					+ " from tPersonMessage a  left outer join tXTDM b ON b.cbm=a.MsgType AND b.id='PERSMSGTYPE'  " 
					+ " left outer join tXTDM c ON c.cbm=a.rcvType AND c.id='PERSRCVTYPE'  " 
					+ " left outer join tXTDM E ON E.cbm=a.RcvStatus AND E.id='PERSRCVSTATUS'  " 
					+ " left outer join tXTDM F ON  f.cbm=a.isPush AND F.id='YESNO' left outer join tXTDM g ON g.cbm=a.pushStatus AND g.id='YESNO'  "
					+ " inner join temployee d ON d.Number=a.RcvCZY  " 
					+ " left join tDepartment o on d.Department=o.Code "
					+ " left join tDepartment1 d1 on d.Department1 = d1.Code "
					+ " LEFT join tDepartment2 d2 on d.Department2=d2.Code "
					+ " left outer join tXTDM h on h.cbm = a.ProgmsgType and h.id = 'PROGTEMPTYPE'"  
					+ " left join tItemType1 i on a.ItemType1=i.Code  left join tItemType2 j on a.ItemType2=j.Code and j.ItemType1=a.itemtype1"  
					+ " left join tWorkType12 k on a.WorkType12=k.Code  left join tXTDM l on l.ID = 'PRJITEM' and l.CBM=a.PrjItem"  
					+ " left join tJobType m on a.JobType=m.Code  "
					+ " left join tCustomer n on a.MsgRelCustCode=n.Code " +
					"   left join tEmployee e_2 on e_2.number = n.projectMan " +
					" left join tDepartment2 d_2 on d_2.Code = e_2.department2 " +
					" left join (select max(pk) PK,MsgPK from tDelayExec group by MsgPK) p on p.MsgPK =a.Pk " +
					" left join tDelayExec q on q.pk = p.pk " +
					"where a.MsgType='1' " ;
		
		if(!hasAllRight){
			if(hasDeptRight){
				sql+=" and o.Path like '%'+(select Department from tEmployee where Number=?)+'%' ";
				list.add(uc.getCzybh());
			}else if(hasSelfRight){
				sql+=" and a.RcvCZY=? ";
				list.add(uc.getCzybh());
			}else{
				sql+=" and 1<>1 ";
			}
		}

    	if (StringUtils.isNotBlank(personMessage.getPrjDept())) {
			sql += " and e_2.Department2 = ? ";
			list.add(personMessage.getPrjDept());
		}
    	if (StringUtils.isNotBlank(personMessage.getDepartment2())) {
			sql += " and d2.Code= ? ";
			list.add(personMessage.getDepartment2());
		}
    	if (StringUtils.isNotBlank(personMessage.getDepartment1())) {
			sql += " and d1.Code= ? ";
			list.add(personMessage.getDepartment1());
		}
    	    	
    	if (StringUtils.isNotBlank(personMessage.getRcvCzy())) {
			sql += " and a.RcvCZY = ? ";
			list.add(personMessage.getRcvCzy());
		}   
    	
    	if (StringUtils.isNotBlank(personMessage.getAddress())) {
			sql += " and n.address like ? ";
			list.add("%"+personMessage.getAddress()+"%");
		} 
    	
    	if (StringUtils.isNotBlank(personMessage.getItemType1())) {
			sql += " and a.itemType1 = ?  ";
			list.add(personMessage.getItemType1());
		}
    	
    	if ("1".equals(personMessage.getDeal())) {
			sql +=" and convert(char(10), " +
					" (case when a.RcvDate is not null then a.RcvDate else getDate() end )" +
					" ,120) > convert(char(10),a.PlanDealDate,120)  ";
		}
    	if ("0".equals(personMessage.getDeal())) {
			sql += " and convert(char(10), (case when a.RcvDate is not null then a.RcvDate else getdate() end) " +
					" ,120) < convert(char(10),a.PlanDealDate,120)  ";
		}
    	
    	if (StringUtils.isNotBlank(personMessage.getRcvStatus())) {
			sql += " and a.RcvStatus in " + "('"+personMessage.getRcvStatus().replace(",", "','" )+ "')";
		}
    	if (personMessage.getSendDate()!=null) {
			sql += " and a.SendDate >= ? ";
			list.add(personMessage.getSendDate());
		}
    	if(StringUtils.isNotBlank(personMessage.getTitle())){
    		sql+=" and a.title like ? ";
    		list.add("%"+personMessage.getTitle()+"%");
    	}
    	
    	if (personMessage.getSendDate1()!=null) {
    		if (personMessage.getSendDate1().getTime()==-28800000){
    			sql +=" and a.SendDate <= getdate() ";
    		}else{
				sql +=" and a.SendDate < dateadd(day,1,?) ";
				list.add(personMessage.getSendDate1());
    		}
    	}
    	if(StringUtils.isNotBlank(personMessage.getSysTrigger())){
    		if("0".equals(personMessage.getSysTrigger())){
    			sql+=" and not exists( select 1 from tPersonmessage in_a " +
    					"				inner join tProgTempAlarm in_b on in_a.MsgRelNo = cast(in_b.pk as nvarchar(20)) " +
    					"			where a.pk = in_a.pk and in_b.IsAutoJob ='1')";
    		}
    	}
    	if(StringUtils.isNotBlank(personMessage.getBefTaskCompleted())){
    		if("1".equals(personMessage.getBefTaskCompleted())){
    			sql+=" and not exists(select 1 from tPersonMessage ina where ina.MsgRelCustCode = " +
    					" a.MsgRelCustCode and ina.workType12 = a.workType12 and ina.workType12 <> '' and ina.workType12 is not null " +
    					" and ina.PlanArrDate < a.PlanArrDate and ina.rcvDate is null)";
    		}else if("0".equals(personMessage.getBefTaskCompleted())){
    			sql+="and  exists(select 1 from tPersonMessage ina where ina.MsgRelCustCode = " +
    					" a.MsgRelCustCode and ina.workType12 = a.workType12 and ina.workType12 <> '' and ina.workType12 is not null " +
    					" and ina.PlanArrDate < a.PlanArrDate and ina.rcvDate is null)";
    		}
    	}
    	
    	if(StringUtils.isNotBlank(personMessage.getMsgRelCustCode())){
    		sql+=" and a.msgRelCustCode = ?";
    		list.add(personMessage.getMsgRelCustCode());
    	}
    	if(StringUtils.isNotBlank(personMessage.getWorkType12())){
    		sql+=" and a.workType12 = ? ";
    		list.add(personMessage.getWorkType12());
    	}
    	if (StringUtils.isNotBlank(personMessage.getCustType())) {
			String str = SqlUtil.resetStatus(personMessage.getCustType());
			sql += " and n.CustType in (" + str + ")";
		}
    	if(StringUtils.isNotBlank(personMessage.getPrjItem())){
    		String str = SqlUtil.resetStatus(personMessage.getPrjItem());
			sql += " and a.PrjItem in (" + str + ")";
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.SendDate desc,a.pk asc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> goJqGridByWorkType12(Page<Map<String,Object>> page, PersonMessage personMessage) {
		List<Object> list = new ArrayList<Object>();
		UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		String sql =  " select a.pk,isnull(n.Address,'') Address,a.MsgType,b.note BMsgType,a.MsgText,a.MsgRelNo,a.MsgRelCustCode,a.CrtDate,"
					+ " a.SendDate,a.rcvType,c.note BrcvType, a.RcvCZY,d.NameChi BRcvCZY,isnull(d2.desc1,'') Department2Descr,a.RcvDate,a.RcvStatus,E.note BRcvStatus,"
					+ " a.isPush ,F.note BisPush,a.pushStatus,g.note BpushStatus,  "
					+ " a.ProgmsgType,h.NOTE ProgmsgTypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,j.Descr ItemType2Descr," 
					+ " a.WorkType12,k.Descr WorkType12Descr,a.PrjItem,isnull(l.NOTE,'') PrjItemDescr,a.JobType,m.Descr JobTypeDescr,"  
					+ " a.PlanDealDate,a.PlanArrDate,isnull(a.Remarks,'') Remarks,isnull(a.Title,'') Title,"
					+ " (case when rcvdate is null then (case when convert(char(10), getdate(), 120) > convert(char(10), a.PlanDealDate, 120) then '是'" +
						" else '否'  end) else  (case when convert(char(10), a.RcvDate, 120) > convert(char(10), a.PlanDealDate, 120) then '是' else '否' " 
					+ " end) end )deal,e_2.namechi prjmandescr ,e_2.phone prjmanphone,d_2.Desc2 pajmanDept2 ,a.msgRelCustCode custCode "
					+ " from tPersonMessage a  left outer join tXTDM b ON b.cbm=a.MsgType AND b.id='PERSMSGTYPE'  " 
					+ " left outer join tXTDM c ON c.cbm=a.rcvType AND c.id='PERSRCVTYPE'  " 
					+ " left outer join tXTDM E ON E.cbm=a.RcvStatus AND E.id='PERSRCVSTATUS'  " 
					+ " left outer join tXTDM F ON  f.cbm=a.isPush AND F.id='YESNO' left outer join tXTDM g ON g.cbm=a.pushStatus AND g.id='YESNO'  "
					+ " inner join temployee d ON d.Number=a.RcvCZY  " 
					+ " left join tDepartment o on d.Department=o.Code "
					+ " left join tDepartment1 d1 on d.Department1 = d1.Code "
					+ " LEFT join tDepartment2 d2 on d.Department2=d2.Code "
					+ " left outer join tXTDM h on h.cbm = a.ProgmsgType and h.id = 'PROGTEMPTYPE'"  
					+ " left join tItemType1 i on a.ItemType1=i.Code  left join tItemType2 j on a.ItemType2=j.Code and j.ItemType1=a.itemtype1"  
					+ " left join tWorkType12 k on a.WorkType12=k.Code  left join tXTDM l on l.ID = 'PRJITEM' and l.CBM=a.PrjItem"  
					+ " left join tJobType m on a.JobType=m.Code  "
					+ " left join tCustomer n on a.MsgRelCustCode=n.Code " +
					"   left join tEmployee e_2 on e_2.number = n.projectMan " +
					" left join tDepartment2 d_2 on d_2.Code = e_2.department2 " +
					"where 1=1 and a.MsgType='1' and a.planArrDate < ?" ;
		list.add(personMessage.getPlanArrDate());
		if(StringUtils.isNotBlank(personMessage.getWorkType12()) && StringUtils.isNotBlank(personMessage.getMsgRelCustCode())){
			sql+=" and exists(select 1 from tProgTempAlarm in_a where in_a.workType12 = ? and a.MsgRelCustCode = ? " +
					"	and in_a.workType12 = a.workType12 and cast(in_a.pk as nvarchar(20)) = a.MsgRelNo)";
			list.add(personMessage.getWorkType12());
			list.add(personMessage.getMsgRelCustCode());
		}else{
			sql+=" and 1<>1";
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.SendDate desc,a.pk asc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Map<String, Object> getByPk(Integer pk) {
		String sql = "select isnull(n.Address,'') Address,isnull(d2.desc1,'') Deparment2Descr," +
					"(case when a.RcvDate>a.PlanDealDate then '是' else '否' end ) deal from tPersonMessage a  " +
					"left join tcustomer n on a.msgrelcustcode=n.code " +
					"inner join temployee d ON d.Number=a.RcvCZY   " +
					"LEFT join tDepartment2 d2 on d.Department2=d2.Code "
					+"where a.pk=? ";

		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public Result doReturnCheckOut(PersonMessage personMessage) {
		Assert.notNull(personMessage);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjMsg(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, personMessage.getM_umState());
			call.setInt(2, personMessage.getPk());		
			call.setTimestamp(3, new java.sql.Timestamp(personMessage.getRcvDate().getTime()));
			call.setString(4, personMessage.getRcvStatus());
			call.setTimestamp(5, new java.sql.Timestamp(personMessage.getSendDate().getTime()));
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.setString(8, personMessage.getLastUpdatedBy());
			call.setString(9, personMessage.getDelayRemarks());
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	public Map<String, Object> getMaxAuthId(String czybh) {
		String sql = "select max(AUTHORITY_ID)AUTHORITY_ID from TSYS_CZYBM_AUTHORITY " 
				+"where AUTHORITY_ID in ('1757','1758','1759') and CZYBH=? ";

		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 延误信息
	 * @param page
	 * @param personMessage
	 * @return
	 */
	public Page<Map<String,Object>> goReasonJqGrid(Page<Map<String,Object>> page, PersonMessage personMessage) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select * from (select b.NameChi czyDescr,a.Date,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+"from tDelayExec a " 
				+"left join tEmployee b on a.CZYBH=b.Number "  
				+"where MsgPK=?  ";							
    	list.add(personMessage.getPk());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}

