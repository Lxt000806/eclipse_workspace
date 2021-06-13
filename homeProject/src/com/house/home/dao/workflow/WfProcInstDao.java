package com.house.home.dao.workflow;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
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
import com.house.framework.commons.workflow.WorkflowUtils;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcess;


@SuppressWarnings("serial")
@Repository
public class WfProcInstDao extends BaseDao {
	
	/**
	 * 查询流程实例
	 * @param page
	 * @param type
	 * @param czybh
	 * @return
	 */
	public Page<Map<String, Object>> findWfProcInstPageBySql(Page<Map<String, Object>> page, 
			WfProcInst  wfProcInst) {
		
		if (wfProcInst.getType() == null) {
			throw new RuntimeException("未指定查询类型！");
		}
		
		List<Object> list = new ArrayList<Object>();
		String sql = " select case	when e.ASSIGNEE_ is not null  then m.namechi  when o.group_id_ is not null then dbo.fGetOAOptionCzy(o.GROUP_ID_, '', '', '')" +
				" when o.USER_ID_ is not null then m.namechi else '' end AssigneeDescr,a.no,d.ZWXM StartUserDescr, c.NAME_ ProcDefName, d.ZWXM + '提交的' + twp.descr Title,twp.GroupCode, "
				+ " b.START_TIME_ StartTime, b.END_TIME_ EndTime, a.Status, a.LastUpdate,"
				+ " a.Summary, a.ActProcInstId, e.ID_ TaskId,case when e.name_ = '发起人' then '退回到发起人' else e.NAME_+'审批' end TaskName, case when b.END_TIME_ is null then '审批中' "
				+ " when a.Status='2' then (case when a.IsPass = '1' then '审批通过' else '审批拒绝' end) "
				+ " else x1.NOTE end StatusDescr, ";
			if (StringUtils.isNotBlank(wfProcInst.getUserId())) {
				if (wfProcInst.getType() == 1) { // 2.我已审批
						sql += " k.CREATE_TIME_ OrderTime ";
				}else if(wfProcInst.getType() == 2){
					sql += " l.END_TIME_ OrderTime ";
				}else if(wfProcInst.getType() == 4){
					sql += " g.CopyDate OrderTime ";
				}else{
					sql += " b.START_TIME_ OrderTime ";
				}
			}else{
				sql += " b.START_TIME_ OrderTime ";
			}
			sql+=",a.startUserId,twp.ProcKey,a.WfProcNo,c.ID_ procId, case when b.END_TIME_ is not null then '1' else '0' end isEnd,g.RcvStatus "
					+ " from tWfProcInst a"
					+ " inner join ACT_HI_PROCINST b on a.ActProcInstId=b.ID_"
					+ " left join ACT_RE_PROCDEF c on b.PROC_DEF_ID_=c.ID_"
					+ " left join tCZYBM d on b.START_USER_ID_=d.CZYBH " 
					+ " left join tXTDM x1 on x1.ID='WFPROCINSTSTAT' and x1.CBM=a.Status"
					+ " left join ACT_RU_TASK e on e.PROC_INST_ID_=a.ActProcInstId " +
					"   left join tWfProcInstCopy g on g.WfProcInstNo = a.No and g.CopyCzy = ?  " +
					//"   left join ACT_HI_TASKINST h on h.PROC_INST_ID_=a.ActProcInstId and h.assignee_ = ? and h.END_TIME_ is not null" +
					" left join (" + //Modify by xzy Date：20200225 --一条审批记录只显示一次
					"	select max(a.id_) id_,a.ASSIGNEE_,a.PROC_INST_ID_ " +
					" 	from ACT_HI_TASKINST a " +
					"	where a.END_TIME_ is not null and a.assignee_ = ? group by ASSIGNEE_,a.proc_inst_id_) aht on " +
					"		aht.PROC_INST_ID_ = a.actProcInstId" +
					"	left join ACT_HI_TASKINST h on h.ID_=aht.id_"+
					"   left join (select max(pk) pk,WfProcInstNo from tWfProcTrack a group by WfProcInstNo) i on i.WfProcInstNo=a.no" +
					"   left join tWfProcTrack j on j.pk=i.pk " +
					"   left join tXTDM x2 on x2.id ='PROCACTTYPE' and x2.CBM = j.ActionType" +
					" 	left join tWfProcess twp on twp.No = a.WfProcNo " +
					"	left join ACT_RU_IDENTITYLINK o on o.TASK_ID_ = e.ID_ and ((o.USER_ID_ is null ) or (o.USER_ID_ = ?)) " +
					"	left join tCZYBM n on ((n.CZYBH = e.ASSIGNEE_ and e.ASSIGNEE_ is not null )" +
					"		or(e.ASSIGNEE_ is null and o.USER_ID_ is not null and n.CZYBH = o.USER_ID_)" +
					"	)" +
					"	left join tEmployee m on m.Number = n.EmNum";
					//"   left join tWfProcTrack f on f.WfProcInstNo = a.No and f.ActionType='3'" +
					list.add(StringUtils.isNotBlank(wfProcInst.getUserId())?wfProcInst.getUserId():"");
					list.add(StringUtils.isNotBlank(wfProcInst.getUserId())?wfProcInst.getUserId():"");
					list.add(StringUtils.isNotBlank(wfProcInst.getUserId())?wfProcInst.getUserId():"");
					
		if(StringUtils.isNotBlank(wfProcInst.getUserId())){
			if(wfProcInst.getType() == 1){
				sql += "   outer apply ( " +
					   "   		select top 1 task.CREATE_TIME_  from ACT_RU_TASK task " +
					   "	 	left join ACT_RU_IDENTITYLINK link on task.ID_=link.TASK_ID_ " +
					   "		where task.SUSPENSION_STATE_='1' and ( a.ActProcInstId = task.PROC_INST_ID_ and (task.ASSIGNEE_=? " +
					   "		or (task.ASSIGNEE_ is null and (link.USER_ID_=?  or link.GROUP_ID_ in (select  GROUP_ID_ " +
					   "		from ACT_ID_MEMBERSHIP where USER_ID_=? ))))) "+	
					   "   ) k ";

				list.add(wfProcInst.getUserId());
				list.add(wfProcInst.getUserId());
				list.add(wfProcInst.getUserId());
			}else if(wfProcInst.getType() == 2){
				sql += " outer apply ( " +
					   " 	select top 1 task.END_TIME_  from ACT_HI_TASKINST task " +
					   " 	inner join tWfProcTrack in_twpt on task.ID_ = in_twpt.FromActivityId and in_twpt.ActionType <> '5' " +
					   "	where task.PROC_INST_ID_=a.ActProcInstId and task.END_TIME_ is not null and task.ASSIGNEE_=? " +
					   " ) l ";
				list.add(wfProcInst.getUserId());
			}
		}
		sql += " where 1=1 ";
				
		if (StringUtils.isNotBlank(wfProcInst.getUserId())) {
			if (wfProcInst.getType() == 1) { // 1.待我审批
				sql += " and a.Status='1' and k.CREATE_TIME_ is not null ";
				
			} else if (wfProcInst.getType() == 2) { // 2.我已审批
				sql += " and h.assignee_ is not null and l.END_TIME_ is not null and j.actionType not in ('7','6') ";// 退回 转交 不算我已审批
			} else if (wfProcInst.getType() == 3) { // 3.我发起的
				sql += " and b.START_USER_ID_=?";
				list.add(wfProcInst.getUserId());
			}else if(wfProcInst.getType() == 4){
				sql +=" and g.copyCzy = ? and g.CopyDate is not null and g.CopyDate <> '' ";
				list.add(wfProcInst.getUserId());
			}
		}
		if(StringUtils.isNotBlank(wfProcInst.getProcKey())){
			sql += " and twp.ProcKey=? ";
			list.add(wfProcInst.getProcKey());
		}
		if(StringUtils.isNotBlank(wfProcInst.getRcvStatus())){
			sql+=" and g.rcvStatus = ? ";
			list.add(wfProcInst.getRcvStatus());
		}
		if (StringUtils.isNotBlank(wfProcInst.getStartUserId())) {
			sql += " and b.START_USER_ID_=? ";
			list.add(wfProcInst.getStartUserId());
		}
		if (StringUtils.isNotBlank(wfProcInst.getSummary())) {
			sql += " and a.Summary like ? ";
			list.add("%" + wfProcInst.getSummary()+ "%");
		}
		if (wfProcInst.getStartTimeFrom() != null) {
			sql += " and b.START_TIME_ >= ? ";
			list.add(wfProcInst.getStartTimeFrom());
		}
		if (wfProcInst.getStartTimeTo() != null) {
			sql += " and b.START_TIME_ <= ? ";
			list.add(DateUtil.startOfTheDay(wfProcInst.getStartTimeTo()));
		}
		if (wfProcInst.getEndTimeFrom() != null) {
			sql += " and b.End_Time_ >= ? ";
			list.add(wfProcInst.getEndTimeFrom());
		}
		if (wfProcInst.getEndTimeTo() != null) {
			sql += " and b.End_Time_ <= ? ";
			list.add(DateUtil.endOfTheDay(wfProcInst.getEndTimeTo()));
		}
		if (StringUtils.isNotBlank(wfProcInst.getWfProcNo())) {
			sql += " and a.WfProcNo in " + "('"+wfProcInst.getWfProcNo().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(wfProcInst.getNo())){
			sql+=" and a.no = ? ";
			list.add(wfProcInst.getNo());
		}
		if (StringUtils.isNotBlank(wfProcInst.getStatus())) {
			sql += " and a.Status in " + "('"+wfProcInst.getStatus().replaceAll(",", "','")+"')";
		}
		sql = "select * from (" + sql;
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.OrderTime desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getApplyListByJqgrid(Page<Map<String, Object>> page, 
			WfProcInst  wfProcInst) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select twpg.descr groupDescr,b.ID_ procID,a.No, a.Descr, a.ProcKey, a.Version, a.TableCode, a.LastUpdate," +//, a.GroupCode
				" a.LastUpdatedBy, a.Expired, a.ActionLog, " +
				" case when twpg.Code is not null and twpg.Code <> '' then a.GroupCode " +
				" else 'other' end GroupCode, " +
				" case when twpg.Code is not null and twpg.Code <> '' then twpg.Descr " +
				" else '其他' end GroupCodeDescr," +
				" case when twpg.Code is not null and twpg.Code <> '' then twpg.DispSeq " +
				" else 99999 end DispSeq, " +
				" a.Icon src,a.Remarks " + //新增oa备注
				" from tWfProcess a " +
				" left join ACT_RE_PROCDEF b on b.KEY_= a.ProcKey and a.Version = b.Version_ " +
				" left join tWfProcGroup twpg on twpg.Code = a.GroupCode " +
				" where 1=1 and a.expired ='F' and a.GroupCode is not null";
		
		if(StringUtils.isNotBlank(wfProcInst.getDescr())){
			sql+=" and a.descr like  ? ";
			list.add("%"+wfProcInst.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.DispSeq,a.no";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getAllListByJqgrid(Page<Map<String, Object>> page, 
			WfProcInst  wfProcInst) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select g.desc1 department2descr,isnull(f.tableCode ,'') tableCode ,a.no,d.ZWXM StartUserDescr, " +
				"	c.NAME_ ProcDefName, d.ZWXM + '提交的' + f.descr Title,"
				+ " b.START_TIME_ StartTime, b.END_TIME_ EndTime, a.Status, x1.NOTE StatusDescr, a.LastUpdate,"
				+ " a.Summary, a.ActProcInstId, e.ID_ TaskId, x2.note TaskName,a.StartUserId ,e1.namechi OperDescr "
				+ " from tWfProcInst a "
				+ " inner join ACT_HI_PROCINST b on a.ActProcInstId=b.ID_"
				+ " left join ACT_RE_PROCDEF c on b.PROC_DEF_ID_=c.ID_"
				+ " left join tCZYBM d on b.START_USER_ID_=d.CZYBH "+
				" left join temployee h on h.number =a.startUserId " +
				" left join tdepartment2 g on g.code =h.department2 "
				+ " left join tXTDM x1 on x1.ID='WFPROCINSTSTAT' and x1.CBM=a.Status"
				+ " left join ACT_RU_TASK e on e.PROC_INST_ID_=a.ActProcInstId " +
				" 	left join tWfProcess f on f.no =a.WfProcNo " +
				"	left join (select b.* from  (select max(pk) pk from tWfProcTrack group by WfProcInstNo) a" +
				"					left join tWfProcTrack b on b.pk = a.pk" +
				"			  ) j on j.WfProcInstNo = a.no " +
				"	left join tCzybm k on k.Czybh = j.OperId " +
				" 	left join tEmployee e1 on e1.Number = k.EMNum " +
				"	left join tXTDM x2 on x2.id='PROCACTTYPE' and x2.CBM=j.ActionType" +
				" where 1=1 ";
		
		if (StringUtils.isNotBlank(wfProcInst.getSummary())) {
			sql += " and a.Summary like ? ";
			list.add("%" + wfProcInst.getSummary()+ "%");
		}
		if (wfProcInst.getDateFrom()!= null) {
			sql += " and b.START_TIME_ >= ? ";
			list.add(wfProcInst.getDateFrom());
		}
		if (wfProcInst.getDateTo() != null) {
			sql += " and b.START_TIME_ <= ? ";
			list.add(DateUtil.endOfTheDay(wfProcInst.getDateTo()));
		}
		if (wfProcInst.getEndTimeFrom() != null) {
			sql += " and b.End_Time_ >= ? ";
			list.add(wfProcInst.getEndTimeFrom());
		}
		if (wfProcInst.getEndTimeTo()!= null) {
			sql += " and b.End_Time_ <= ? ";
			list.add(DateUtil.endOfTheDay(wfProcInst.getEndTimeTo()));
		}
		if (StringUtils.isNotBlank(wfProcInst.getWfProcNo())) {
			sql += " and a.WfProcNo in " + "('"+wfProcInst.getWfProcNo().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(wfProcInst.getStatus())) {
			sql += " and a.Status in " + "('"+wfProcInst.getStatus().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(wfProcInst.getCzybh())){
			sql+=" and exists (select 1 from tWfGroupAuthority in_a " +
					"		left join ACT_ID_MEMBERSHIP in_b on in_b.GROUP_ID_ = in_a.GroupId" +
					"		where in_b.USER_ID_ = ? and a.WfprocNo = in_a.WfProcNo" +
					"	)";
			list.add(wfProcInst.getCzybh());
		}
		sql = "select * from (" + sql;
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public WfProcess getWfProcessByProcKey(String procKey){
		String hql = "from WfProcess where ProcKey=? ";
		List<WfProcess> list = this.find(hql, 1, new Object[]{procKey});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void autoSaveSql(String tableName, Map<String, Object> data){
		String sql = WorkflowUtils.autoProduceSql(tableName, data);
		if(StringUtils.isNotBlank(sql)){
			this.executeUpdateBySql(sql, new Object[]{});
		}
		return;
	}
	
	public Page<Map<String, Object>> findByProcInstId(Page<Map<String,Object>> page, String wfProcInstNo) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select 1 dispseq,a.lastUpdate,case when a.ActionType = '5' then '' else " +
				"	case when rt.name_ is null then x1.NOTE else case when rt.name_ = '发起人' then rt.name_+'修改' else rt.name_  end end end wfProcDescr" +
				"	,d.ZWXM nameChiDescr,x1.NOTE statusDescr" +
				" ,a.Remarks,isnull(a.FromActivityId,0) FromActivityId,case when a.actionType ='1' then 'applyMan' else rt.TASK_DEF_KEY_ end  taskDefKey,a.ToActivityDescr from tWfProcTrack  a " +
				" left join tWfProcInst b on b.No=a.WfProcInstNo" +
				" left join tWfProcess c on c.no = b.WfProcNo" +
				" left join tCZYBM d on d.CZYBH = a.operId" +
				" left join tXTDM x1 on x1.CBM = a.ActionType and x1.id ='PROCACTTYPE'" +
				" left join ACT_HI_TASKINST rt on a.FromActivityId = rt.id_ "+// id已经是唯一确定值了,去除无关条件 rt.PROC_INST_ID_ = b.ActProcInstId and rt.proc_def_id_ = b.ActProcDefId and a.OperId = rt.ASSIGNEE_ and 
				" where a.WfProcInstNo = ? " +
				" union " +
				" select 2 dispseq,isnull(a.RcvDate, '9999-12-31 00:00:000') lastUpdate , c.Descr+'(抄送)' wfProcDescr " +
				" , d.ZWXM nameChiDescr , x1.NOTE statusDescr, '' remarks, 999999,'' taskDefKey,'' ToActivityDescr from tWfProcInstCopy a " +
				"	left join tWfProcInst b on b.No = a.WfProcInstNo" +
				"	left join tWfProcess c on c.no = b.WfProcNo" +
				"	left join tCZYBM d on d.CZYBH = a.CopyCZY" +
				"	left join tXTDM x1 on x1.CBM = a.RcvStatus and x1.id = 'WFPICOPYRCVSTAT'" +
				" 	left join act_ru_task rt on rt.PROC_INST_ID_ = b.ActProcInstId and rt.proc_def_id_ = b.ActProcDefId " +
				"  where a.WfProcInstNo = ? and CopyDate is Not null " +
				" union " +
				" select 3 dispseq,a.LastUpdate,'' ,d.ZWXM,'评论' statusdescr,a.comment remarks, " +
				" '' FromActivityId,'' taskDefKey,'' ToActivityDescr from tWfProcComment a" +
				" left join tWfProcInst b on b.no = a.wfprocinstno " +
				" left join tWfProcess c on c.no = b.WfProcNo" +
				" left join tCZYBM d on d.CZYBH = a.EmpCode " +
				" where a.wfprocinstno = ? ";
		sql += " ) a where 1=1 ";
		list.add(wfProcInstNo);
		list.add(wfProcInstNo);
		list.add(wfProcInstNo);
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by lastUpdate,FromActivityId ";//因为taskId就是随流程递增，排序根据FromActivityId
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public List<Map<String, Object>> getTables(String wfProcNo, String isSummary, String isVariable){
		List<Object> params = new ArrayList<Object>();
		String sql = " select b.Code, b.Descr, '1' isMain, "
				   + " isnull('{'+(select stuff((select ','+Code+'='+Descr from tWfTableStru where TableCode=b.Code ";
		if(StringUtils.isNotBlank(isSummary)){
			sql += " and IsSummary = ? ";
			params.add(isSummary);
		}
		if(StringUtils.isNotBlank(isVariable)){
			sql += " and IsVariable = ? ";
			params.add(isVariable);
		}
		sql += " order by DispSeq for xml path('')), 1,1,''))+'}', '') columns"
		     + " from tWfProcess a "
		     + " left join tWfTable b on a.TableCode = b.Code "
		     + " where a.No = ? ";
		params.add(wfProcNo);
		sql += " union all "
		     + " select a.Code, a.Descr, '0' isMain, "
		     + " isnull('{'+(select stuff((select ','+Code+'='+Descr from tWfTableStru where TableCode=a.Code ";
		if(StringUtils.isNotBlank(isSummary)){
			sql += " and IsSummary = ? ";
			params.add(isSummary);
		}
		if(StringUtils.isNotBlank(isVariable)){
			sql += " and IsVariable = ? ";
			params.add(isVariable);
		}
		sql += " order by DispSeq for xml path('')), 1,1,''))+'}', '') columns"
		     + " from tWfTable a "
		     + " where a.MainTable in (select TableCode from tWfProcess in_a where in_a.No = ?) ";
		params.add(wfProcNo);
		List<Map<String, Object>> list =this.findBySql(sql, params.toArray());
		return list;
	}
	
	public WfProcInst getWfProcInstByActProcInstId(String actProcInstId){
		String hql = "from WfProcInst where ActProcInstId = ? ";
		List<WfProcInst> list = this.find(hql, new Object[]{actProcInstId});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public String getProcKeyByNo(String no){
		String sql = "select top 1 procKey from tWfProcess where no  = ? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0).get("procKey").toString();
		}
		return null;
	}
	
	public List<Map<String, Object>> getTableInfo(String tableName, String wfProcInstNo){
		String sql = "select * from "+tableName+" where wfProcInstNo = ? ";
		return this.findBySql(sql, new Object[]{wfProcInstNo});
	}	
	public Map<String, Object> getSeqNoBySql(String tableName){
		String sql = " set nocount on "
				   + " declare @ret int "
				   + " declare @errmsg nvarchar(400) "
				   + " exec pGetSerialNo_forProc ?, @ret output, @errmsg output "
				   + " select @ret ret,@errmsg no "
				   + " set nocount off "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{tableName});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> getWfProcTrack(Page<Map<String, Object>> page, String wfProcInstNo){
		String sql = " select twpt.remarks,twpt.LastUpdate checkDate,tx1.NOTE actionTypeDescr,tc.ZWXM checkCZYDescr "
				   + " from tWfProcTrack twpt "
				   + " left join tXTDM tx1 on tx1.ID='PROCACTTYPE' and twpt.ActionType = tx1.CBM "
				   + " left join tCZYBM tc on tc.CZYBH = twpt.OperId "
				   + " where twpt.WfProcInstNo=? "
				   + " order by twpt.LastUpdate desc ";
		return this.findPageBySql(page, sql, new Object[]{wfProcInstNo});
	}
	
	public String getDetailFromWfCustTable(String tableName,String no){
		String sql="";
		if(StringUtils.isNotBlank(tableName)){
			sql="select * from tWfTableStru where TableCode = ? and Code='type'";
			List<Map<String, Object>> list = this.findBySql(sql, new Object[]{tableName});
			if(list != null && list.size()>0){
				sql = " select top 1 type from "+tableName+" where WfProcInstNo = ? ";
				list = this.findBySql(sql, new Object[]{no});
				if(list != null && list.size() > 0){
					if(list.get(0).get("type") != null){
						return list.get(0).get("type").toString();
					}
					else {
						return null;
					}
				}
				return null;
			}else{
				return null;
			}
		}
		return null;
	}
	
	public String getDetailFromWfProcTruck(String no){
		String sql = " select b.ZWXM+'|'+cast(a.LastUpdate as nvarchar(40))+'|'+c.note+'|'+a.Remarks+';\r' result," +
				" (select datediff(d,min(LastUpdate),max(LastUpdate)) from tWfProcTrack where WfProcInstNo = ? ) timeDiff " +
				" from tWfProcTrack a" +
				" left join tCZYBM b on b.CZYBH = a.OperId" +
				" left join txtdm c on c.CBM = a.ActionType and c.id='PROCACTTYPE'" +
				" where WfProcInstNo= ? group by b.ZWXM,c.note,a.Remarks,a.LastUpdate"; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no,no});
		if(list != null && list.size() > 0){
			String result = "";
			for (int i = 0; i < list.size(); i++) {
				result+= list.get(i).get("result");
			}
			return result;
		}
		return null;
	}
	
	public void doUpdateCopyStatus(String czybh,String wfProcInstNo) {
		String sql = " update tWfProcInstCopy set RcvStatus='1' ,RcvDate = getDate() where CopyCzy = ? and wfProcInstNo = ? and RcvStatus = '0' ";
		this.executeUpdateBySql(sql, new Object[]{czybh,wfProcInstNo});
	}
	
	@SuppressWarnings("deprecation")
	public Result doSaveWfProcInstOption(WfProcInst wfProcInst) {
		Assert.notNull(wfProcInst);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSaveWfProcInstOption(?,?,?,?,?)}");
			call.setString(1, wfProcInst.getNo());
			call.setString(2, wfProcInst.getLastUpdatedBy());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.setString(5, wfProcInst.getDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public String getOptionAssignee(String wfProcInstNo,String taskKey){
		String sql = "select top 1 Assignee from tWfProcInstOption where TaskKey  = ? and  WfProcInstNo = ? "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{taskKey,wfProcInstNo});
		if(list != null && list.size() > 0){
			return list.get(0).get("Assignee").toString();
		}
		return null;
	}
	
	/**
	 * 一级部门领导 ，若没有部门领导, 取高级部门编号 重新查找
	 * @param departmentCode
	 * @return
	 */
	public String getDeptLeader(String departmentCode){
		String result ="";
		String sql = "select HigherDep,isProcDept ,LeaderCode from tdepartment where code = ? and expired = 'F'"; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{departmentCode});
		if(list != null && list.size() > 0){
			if("1".equals(list.get(0).get("isProcDept").toString())){
				if(list.get(0).get("LeaderCode") !=null && list.get(0).get("LeaderCode") !="" ){
					if(StringUtils.isNotBlank(list.get(0).get("LeaderCode").toString())){
						return list.get(0).get("LeaderCode").toString();
					}
				}
			}else {
				result = getDeptLeader(list.get(0).get("HigherDep").toString());
				return result;
			}
		} 
		return result;
	}
	
	/**
	 * 返回高级部门的部门编号，用于二级部门领导的开始查找
	 * @param departmentCode
	 * @return
	 */
	public String getDeptLeaderDeptCode(String departmentCode){
		
		String result ="";
		String result_ =""; 
		String sql = " select c.*,b.Department from tDepartment a " +
				" left join tDepEmp b on a.LeaderCode = b.EmpCode and b.Department <> ? " +
				" left join tDepartment c on c.Code=b.Department " +
				" where a.Code= ? and c.IsProcDept = '1' and c.Expired = 'F'"; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{departmentCode,departmentCode});
		
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				if("0".equals(list.get(i).get("IsActual").toString())){
					result_ = list.get(i).get("Department").toString();
				}
				result = list.get(i).get("Department").toString();
			}
		}else {
			sql = " select c.*,b.Department from tDepartment a " +
					" left join tDepEmp b on a.LeaderCode = b.EmpCode " +
					" left join tDepartment c on c.Code=b.Department " +
					" where a.Code= ? and c.IsProcDept = '1' and c.Expired = 'F'"; 
			list = this.findBySql(sql, new Object[]{departmentCode});
			for (int i = 0; i < list.size(); i++) {
				if("0".equals(list.get(i).get("IsActual").toString())){
					result_ = list.get(i).get("HigherDep").toString();
				}
				result = list.get(i).get("HigherDep").toString();
			}
		} 
		if(StringUtils.isNotBlank(result)){
			result_ = result;
		}
		return result_;
	}
	
	/**
	 * 二级部门领导
	 * @param departmentCode
	 * @return
	 */
	public String getDeptLeaderTow(String departmentCode){
		String sql = "select HigherDep,case when isProcDept = '0' then '' else LeaderCode end " +
				" LeaderCode from tdepartment where code = ? "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{departmentCode});
		if(list != null && list.size() > 0){
			if(list.get(0).get("LeaderCode") != null){
				return list.get(0).get("LeaderCode").toString();
			}
		}
		return null;
	}
	
	public List<Map<String , Object>> getDeptListByCzybh(String czybh){
		String sql = "select case when b.isProcDept ='1' then a.Department else b.higherDep end Department,b.desc1 from tdepemp a " +
				" left join tdepartment b on b.code = a.department " +
				" where a.EmpCode = ? "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{czybh});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	/**
	 * 
	 * @param groupId 或者 wfProcNo+taskKey
	 * @param wfProcNo+taskKey
	 * @return
	 */
	public String getActUser(String groupId){
		return getActUser(groupId,"","","");
	}
	
	public String getActUser(String wfProcNo,String taskKey,String wfProcInstNo){
		return getActUser("",wfProcNo,taskKey,wfProcInstNo);
	}
	
	public String getActUser(String groupId,String wfProcNo,String taskKey,String wfProcInstNo){
		String sql = " select dbo.fGetOAOptionCzy(?,?,?,?) namechi "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{groupId,wfProcNo,taskKey,wfProcInstNo});
		if(list != null && list.size() > 0 && list.get(0).get("namechi") != null){
			return list.get(0).get("namechi").toString();
		}
		return "";
	}
	
	public int getDetailNum(String tableCode,String wfProcInstNo){
		String sql = "select count(1) detailNum from "+tableCode+" where wfProcInstNo = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{wfProcInstNo});
		if(list != null && list.size() > 0){
			return Integer.parseInt(list.get(0).get("detailNum").toString());
		}
		return 0;
	}
	
	public List<Map<String , Object>> getDetails(String tableCode,String wfProcInstNo){
		String sql = "select * from "+tableCode+" where wfProcInstNo = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{wfProcInstNo});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public String getStartTaskKey(String wfProcNo){
		String sql = "select TASK_DEF_KEY_ taskKey from tWfProcInst a" +
				" left join ACT_RU_TASK b on b.PROC_INST_ID_ = a.ActProcInstId" +
				" where a.EndTime is null and a.no  = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{wfProcNo});
		if(list != null && list.size() > 0 && list.get(0).get("taskKey") != null){
			return list.get(0).get("taskKey").toString();
		}
		return "";
	}
	
	public boolean IsHisTask(String wfProcNo,String taskDefKey){
		String sql="select 1 from tWfProcTrack a" +
				"            left join tWfProcInst b on b.No = a.WfProcInstNo" +
				"				left  join ACT_HI_TASKINST rt on rt.PROC_INST_ID_ = b.ActProcInstId and rt.proc_def_id_ = b.ActProcDefId " +
				"            and a.OperId = rt.ASSIGNEE_ and a.FromActivityId = rt.id_" +
				" where     a.WfProcInstNo = ? and TASK_DEF_KEY_= ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{wfProcNo,taskDefKey});
		if(list != null && list.size() > 0){
			return false;
		}
		return false;
	}
	
	public Map<String, Object> getCustStakeholder(String roll,String custCode){
		String sql=" select top 1 a.EmpCode,b.NameChi from tCustStakeholder a " +
				" left join tEmployee b on b.number = a.empCode " +
				" left join tRoll c on c.code = a.role" +
				" where CustCode =? and c.descr= ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode,roll});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void updateCopyDate(String wfProcInNo,String startUserId){
		String sql = " update tWfProcInstCopy set CopyDate = getDate(),lastUpdate = getDate() where wfProcInstNo = ? and (CopyDate is null or CopyDate = '') ";
		this.executeUpdateBySql(sql, new Object[]{wfProcInNo});
	}
	
	public List<Map<String, Object>> getProcTaskTableStru(String wfProcNo, String taskDefkey){
		String sql="select a.*,case when c.MainTable ='' or C.mainTable is null then '1' else '0' end mainTable " +
				" from tWfProcTaskTableStru a " +
				" left join tWfTable c on c.Code = a.TableCode " +
				" where a.wfprocNo = ? and taskDefkey = ?   " +
				" ";
		
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{wfProcNo, taskDefkey});
		if(list != null && list.size() > 0){
			return list;
			
		}
		return null;
	}
	
	public String getCzybhByEmpNum(String empNum){
		String sql="select czybh from tCzybm where emNum = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{empNum});
		if(list != null && list.size() > 0){
			return list.get(0).get("czybh").toString();
		}
		return null;
	}
	
	public void doPushTaskToOperator(String wfProcInstNo) {
		String sql =" insert into tPersonMessage ( MsgType, MsgText, MsgRelNo, MsgRelCustCode,  CrtDate, SendDate, RcvType, RcvCZY, RcvDate," +
				" RcvStatus, IsPush, PushStatus, ProgMsgType)" + 
				" select '18','流程审批:'+g.zwxm+'提交的'+f.NAME_, ? ,null,getdate(),getDate(),'3',case when b.ASSIGNEE_ is null then d.USER_ID_ else b.ASSIGNEE_ end ,null," +
				" '0','1','0','1' from tWfProcInst a " +
				" left join ACT_RU_TASK b on b.PROC_INST_ID_ = a.actprocinstId" +
				" left join ACT_RU_IDENTITYLINK c on c.TASK_ID_ = b.ID_" +
				" left join ACT_ID_MEMBERSHIP d on d.GROUP_ID_ = c.GROUP_ID_" +
				 " inner join ACT_HI_PROCINST e on a.ActProcInstId= e.ID_" +
				" left join ACT_RE_PROCDEF f on b.PROC_DEF_ID_= f.ID_ " +
				" left join tCzybm g on g.czybh = e.START_USER_ID_  " +
				" where a.No = ? and (b.Assignee_ is not null or d.User_id_ is not null)";
		this.executeUpdateBySql(sql, new Object[]{wfProcInstNo,wfProcInstNo});
	}
	
	public void doPushToAppMan(String wfProcInstNo){
		String sql = "insert	into tPersonMessage (MsgType, MsgText, MsgRelNo, MsgRelCustCode, CrtDate, SendDate," +
				" RcvType, RcvCZY, RcvDate, RcvStatus, IsPush, PushStatus, ProgMsgType)" +
				" select	'18', '流程审批:' + '您提交的' + b.Descr+ case when a.IsPass ='1'then '已审批通过' else '已审批拒绝' end, " +
				" ?, null, getdate(), getdate(), '3',a.StartUserId, null, '0', '1', '0', '1'" +
				" from	tWfProcInst a" +
				" left  join tWfProcess b on b.No = a.WfProcNo" +
				" where a.no = ? and status = '2'";
		this.executeUpdateBySql(sql, new Object[]{wfProcInstNo,wfProcInstNo});
	}
	
	public void doUpdatePustRcvStatus(String wfProcInstNo) {
		String sql =" update tPersonMessage set rcvStatus = '1', RcvDate = getDate() ,PushStatus = '1' " +
				" where MsgRelNo = ? and RcvStatus = '0' ";
		this.executeUpdateBySql(sql, new Object[]{wfProcInstNo});
	}
	
	public void doPushCommentToOperator(String wfProcInstNo){
		String sql =" insert into tPersonMessage ( MsgType, MsgText, MsgRelNo, MsgRelCustCode,  CrtDate, SendDate, RcvType, RcvCZY, RcvDate," +
				" RcvStatus, IsPush, PushStatus, ProgMsgType)" +
				" select distinct '18','收到流程评论，请注意查收', ? ,null,getdate(),getDate(),'3',OperId,null," +
				" '0','1','0','1' from tWfProcTrack a " +
				" left join tWfProcInst b on b.no = a.wfProcinstNo" +
				" where a.WfprocInstNo = ? and b.status='1'";
		this.executeUpdateBySql(sql, new Object[]{wfProcInstNo,wfProcInstNo});
	}

	public Map<String , Object> getNextOperatorIdByNo(String no){
		String sql = "select b.Id_ id,case when b.ASSIGNEE_ is null then d.USER_ID_ else b.ASSIGNEE_ end number from twfprocInst a " +
				" left join ACT_RU_TASK b on b.PROC_INST_ID_ = a.ActProcInstId " +
				" left join ACT_RU_IDENTITYLINK c on c.TASK_ID_ = b.ID_" +
				" left join ACT_ID_MEMBERSHIP d on d.GROUP_ID_ = c.GROUP_ID_" +
				" where a.No = ? and (b.Assignee_ is not null or d.User_id_ is not null)" ;
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<String> getHisOperatorByNo(String wfProcInstNo){
		List<String> result = new ArrayList<String>();
		String sql = "select operid from tWfProcTrack  where WfProcInstNo = ? " ;
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{wfProcInstNo});
		if(list != null && list.size() > 0){
			for(Map<String, Object> map: list){
				result.add(map.get("operid").toString());
			}
			return result;
		}
		return null;
	}
	
	public Page<Map<String,Object>> findWfProcInstPic(Page<Map<String,Object>> page,
				String wfProcInstNo,String photoPK) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.PK,a.wfProcInstNo No,a.Descr PhotoName,a.LastUpdate,a.LastUpdatedBy,b.nameChi lastupdatedbydescr " +
				"	from tWfProcPhoto a " +
				"	left join tEmployee b on b.Number = a.LastUpdatedBy" +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(wfProcInstNo)){
			sql+=" and a.wfProcInstNo = ? ";
			list.add(wfProcInstNo);
		}else if(StringUtils.isNotBlank(photoPK)){
			sql+=" and a.pk in ('"+photoPK.replaceAll(",", "','")+"') ";
		}else{
			sql+=" and 1<>1 ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public void doUpdatePhotoNo(String wfProcInstNo,String pks){
		String sql =" update tWfProcPhoto set WfProcInstNo = ? " +
				" where (WfProcInstNo ='' or wfProcInstNo is null) and  pk in "+ "('"+pks.replaceAll(",", "','")+"')";
		this.executeUpdateBySql(sql, new Object[]{wfProcInstNo});
		
	}
	
	public void doDelPhoto(String pks){
		String sql =" delete from tWfProcPhoto where pk in ('"+pks.replaceAll(",", "','")+"')";
		this.executeUpdateBySql(sql, new Object[]{});
		
	}
	
	public List<Map<String, Object>> findWfProcInstPicByNo(String wfProcInstNo){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sql = "select descr photoName from tWfProcPhoto where wfProcInstNo = ?  " ;
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{wfProcInstNo});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public String getDeptManager(String departmentCode){
		String result ="";
		String sql = "select case when len(a.Path)-len(replace(a.Path,'/',''))+1 = 1 then " +
				"	LeaderCode else HigherDep end value," +
				"  case when len(a.Path)-len(replace(a.Path,'/',''))+1 = 1 then " +
				"	'LeaderCode' else 'higherDeptCode' end code" +
				"  from tdepartment a  where a.Code = ? "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{departmentCode});
		if(list != null && list.size() > 0){
			if("LeaderCode".equals(list.get(0).get("code").toString())){
				return list.get(0).get("value").toString();
			}else {
				result = getDeptManager(list.get(0).get("value").toString());
				return result;
			}
		} 
		return result;
	}
	
	/**
	 * 按审批人汇总待审批的记录数
	 * @return
	 */
	public List<Map<String, Object>> findTodoTaskGroupByUserId() {
		
		String sql = "select a.CZYBH, c.Phone, count(*) Cnt from (" 
			+ "  select a.ASSIGNEE_ CZYBH from ACT_RU_TASK a where a.SUSPENSION_STATE_='1' and a.ASSIGNEE_ is not null" 
			+ "  union all" 
			+ "  select b.USER_ID_ CZYBH from ACT_RU_TASK a" 
			+ "  inner join ACT_RU_IDENTITYLINK b on a.ID_=b.TASK_ID_ and b.USER_ID_ is not null" 
			+ "  where a.SUSPENSION_STATE_='1' and a.ASSIGNEE_ is null" 
			+ "  union all" 
			+ "  select c.USER_ID_ CZYBH from ACT_RU_TASK a" 
			+ "  inner join ACT_RU_IDENTITYLINK b on a.ID_=b.TASK_ID_ and b.USER_ID_ is null" 
			+ "  inner join ACT_ID_MEMBERSHIP c on b.GROUP_ID_=c.GROUP_ID_" 
			+ "  where a.SUSPENSION_STATE_='1' and a.ASSIGNEE_ is null" 
			+ " ) a" 
			+ " inner join tCZYBM b on a.CZYBH=b.CZYBH"
			+ " inner join tEmployee c on b.EMNum=c.Number"
			+ " group by a.CZYBH, c.Phone";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public void doUpdateCust_Table(String wfProcInstNo,Map<String, Object> map){
		String sql = WorkflowUtils.getUpdateCust_tableSql(wfProcInstNo, map);
		executeUpdateBySql(sql);
	}
	
	public void doUpdateCust_TableByPk(String wfProcInstNo,Map<String, Object> map){
		String sql = WorkflowUtils.getUpdateCust_tableByPkSql(wfProcInstNo, map);
		executeUpdateBySql(sql);
	}
	
	public List<Map<String, Object>> getPresentOperater(String actProcInstId, String activityId){
		String sql=" select	case	when a.ASSIGNEE_ is null and b.USER_ID_ is null then c.ID_" +
				" else case when d.ZWXM is null then e.ZWXM else d.zwxm end  " +
				" end assignee, case	when a.ASSIGNEE_ is null and b.USER_ID_ is null then 'group'" +
				" else 'zwxm' " +
				" end role" +
				" from	ACT_RU_TASK a" +
				" left join ACT_RU_IDENTITYLINK b on b.TASK_ID_ = a.ID_" +
				" left join ACT_ID_GROUP c on c.ID_ = b.GROUP_ID_" +
				" left join tCZYBM d on d.CZYBH = a.ASSIGNEE_" +
				" left join tCZYBM e on e.CZYBH = b.USER_ID_" +
				" where a.PROC_INST_ID_ = ? and a.TASK_DEF_KEY_ = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{actProcInstId ,activityId});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<Map<String , Object>> getExpenseAdvanceAmount(String wfProcInstNo,String tableCode){
		String sql=" select a.EmpCode ChgEmpCode,a.Amount ChgAmount,isnull(b.Amount,0) Amount,isNull(b.EmpCode,'') EmpCode,c.PK StruPk" +
				"  from "+tableCode+" a " +
				" left join tExpenseAdvance b on b.EmpCode = a.EmpCode " +
				" left join tWfTableStru c on c.TableCode = ? and c.Code = 'CardId'" +
				" where a.WfProcInstNo = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{tableCode, wfProcInstNo});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public Page<Map<String , Object>> getEmpAccountJqGrid(Page<Map<String , Object>> page, String czybh,String actName){
		List<Object> list = new ArrayList<Object>();
		String sql = "";
		sql="select a.pk, a.EmpCode, replace(a.CardId,' ','') CardId,a.ActName, a.Bank, a.SubBranch, a.type from ( " +
				"	select a.pk, a.EmpCode, a.ActName,replace(a.CardId,' ','') CardId, a.Bank, a.SubBranch,'流程卡号' type from tEmpCard a" +
				"	inner join (" +
				"		select max(a.PK) pk from  tEmpCard a" +
				"		where a.EmpCode = ? and expired='F' " +
				"		group by replace(a.CardId,' ',''),a.ActName" +
				"	) b on b.pk = a.PK " +
				"	union all" +
				"	select a.PK, a.SalaryEmp,a.ActName,replace(a.CardId,' ','') CardID, b.note bank, '' SubBranch, '薪酬卡号' type from tSalaryEmpBankCard a" +
				"	left join tXTDM b on b.cbm = a.BankType and b.ID = 'SALBANKTYPE'" +
				"	left join tSalaryEmp c on c.EmpCode = a.SalaryEmp" +
				"	where a.SalaryEmp = ? and a.expired='F' " +
				" )a " +
				" where (exists (select 1 from (" +
				"		select * from tWfCust_ExpenseClaim in_in_a" +
				"		where not exists (select 1 from tWfProcTrack in_in_b where in_in_a.WfProcInstNo = in_in_b.WfProcInstNo and in_in_b.ActionType = '5')" +
				"	) in_a where in_a.ActName = a.ActName and replace(in_a.CardId,' ','') = a.CardId" +
				" ) or exists (select 1 from (" +
				"		select * from tWfCust_ExpenseAdvance in_in_a" +
				"		where not exists (select 1 from tWfProcTrack in_in_b where in_in_a.WfProcInstNo = in_in_b.WfProcInstNo and in_in_b.ActionType = '5')" +
				"	) in_a where in_a.ActName = a.ActName and replace(in_a.CardId,' ','') = a.CardId" +
				" ) or exists (select 1 from (" +
				"		select * from tWfCust_TravelExpenseClaim in_in_a" +
				"		where not exists (select 1 from tWfProcTrack in_in_b where in_in_a.WfProcInstNo = in_in_b.WfProcInstNo and in_in_b.ActionType = '5')" +
				"	) in_a where in_a.ActName = a.ActName and replace(in_a.CardId,' ','') = a.CardId" +
				" ) or exists (select 1 from (" +
				"		select * from tWfCust_MarketExpenseClaimMainDtl in_in_a" +
				"		where not exists (select 1 from tWfProcTrack in_in_b where in_in_a.WfProcInstNo = in_in_b.WfProcInstNo and in_in_b.ActionType = '5')" +
				"	) in_a where in_a.ActName = a.ActName and replace(in_a.CardId,' ','') = a.CardId" +
				" ) or a.type = '薪酬卡号'" +
				")" ;
		
		list.add(czybh);
		list.add(czybh);
		
		if(StringUtils.isNotBlank(actName)){
			sql+=" and a.actName like ? ";
			list.add("%"+actName+"%");
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String , Object>> getRcvActByJqGridPage(Page<Map<String , Object>> page, String czybh,String descr){
		List<Object> list = new ArrayList<Object>();
		String sql=" select Code,Descr,CardId ,Admin from tRcvAct where 1=1 and AllowTrans = '1' ";
		if(StringUtils.isNotBlank(descr)){
			sql+=" and descr like ? ";
			list.add("%"+descr+"%");
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String , Object>> getAdvanceNoByJqgrid(Page<Map<String , Object>> page, String czybh,String searchData){
		List<Object> list = new ArrayList<Object>();
		String sql=" 	select	d.deductionamount, a.No, a.StartTime, b.ZWXM, x1.NOTE statusDescr, isnull(c.ApproveAmount, c.Amount) amount," +
				"			isnull(d.deductionamount, 0) noDeductionAmount, c.ActName, c.CardId" +
				"	from	tWfProcInst a" +
				"	left join tCZYBM b on b.CZYBH = a.StartUserId" +
				"	left join tXTDM x1 on x1.ID = 'WFPROCINSTSTAT'" +
				"							and x1.CBM = a.Status" +
				"	inner join tWfCust_ExpenseAdvance c on c.WfProcInstNo = a.no" +
				"	left join (select	a.WfProcInstNo," +
				"						isnull(e.DeductionAmount, 0) + isnull(b.DeductionAmount, 0) + isnull(c.DeductionAmount, 0)" +
				"						+ isnull(d.deductionamount, 0) deductionamount" +
				"				from	tWfCust_ExpenseAdvance a" +
				"				left join (select	sum(isnull(case when in_b.WfExpenseAdvanceNo is null then in_d.DeductionAmount" +
				"									else in_b.DeductionAmount end * -1, 0)) DeductionAmount, " +
				"							isnull(in_b.WfExpenseAdvanceNo,in_d.WfExpenseAdvanceNo)WfExpenseAdvanceNo" +
				"							from	tExpenseAdvanceTran in_a" +
				"							inner join tWfCust_ExpenseClaim in_b on in_a.WfProcInstNo = in_b.WfProcInstNo" +
				"							left join tWfProcInst in_c on in_c.No = in_b.WfProcInstNo" +
				"							left join tWfCust_ExpenseClaimAdvanceDtl in_d on in_d.WfProcInstNo = in_a.WfProcInstNo" +
				"							where	in_c.IsPass = '1'" +
				"							group by isnull(in_b.WfExpenseAdvanceNo,in_d.WfExpenseAdvanceNo)) b on b.WfExpenseAdvanceNo = a.WfProcInstNo" +
				"				left join (select	sum(isnull(case when in_b.WfExpenseAdvanceNo is null then in_d.DeductionAmount " +
				"									else in_b.DeductionAmount end * -1, 0)) DeductionAmount, " +
				"							isnull(in_b.WfExpenseAdvanceNo,in_d.WfExpenseAdvanceNo)WfExpenseAdvanceNo" +
				"							from	tExpenseAdvanceTran in_a" +
				"							inner join tWfCust_MarketExpenseClaim in_b on in_a.WfProcInstNo = in_b.WfProcInstNo" +
				"							left join tWfProcInst in_c on in_c.No = in_b.WfProcInstNo" +
				"							left join tWfCust_MarketClaimAdvanceDtl in_d on in_d.WfProcInstNo = in_a.WfProcInstNo" +
				"							where	in_c.IsPass = '1'" +
				"							group by isnull(in_b.WfExpenseAdvanceNo,in_d.WfExpenseAdvanceNo)) c on c.WfExpenseAdvanceNo = a.WfProcInstNo" +
				"				left join (select	sum(isnull(case when in_b.WfExpenseAdvanceNo is null then in_d.DeductionAmount " +
				"									else in_b.DeductionAmount end * -1, 0)) DeductionAmount, " +
				"							isnull(in_b.WfExpenseAdvanceNo,in_d.WfExpenseAdvanceNo)WfExpenseAdvanceNo" +
				"							from	tExpenseAdvanceTran in_a" +
				"							inner join tWfCust_TravelExpenseClaim in_b on in_a.WfProcInstNo = in_b.WfProcInstNo" +
				"							left join tWfProcInst in_c on in_c.No = in_b.WfProcInstNo" +
				"							left join tWfCust_TravelClaimAdvanceDtl in_d on in_d.WfProcInstNo = in_a.WfProcInstNo" +
				"							group by isnull(in_b.WfExpenseAdvanceNo,in_d.WfExpenseAdvanceNo)) d on d.WfExpenseAdvanceNo = a.WfProcInstNo" +
				"				left join (select	sum(isnull(in_a.ChgAmount, 0)) DeductionAmount, in_b.WfProcInstNo" +
				"							from	tExpenseAdvanceTran in_a" +
				"							inner join tWfCust_ExpenseAdvance in_b on in_a.WfProcInstNo = in_b.WfProcInstNo" +
				"							left join tWfProcInst in_c on in_c.No = in_b.WfProcInstNo" +
				"							group by in_b.WfProcInstNo) e on e.WfProcInstNo = a.WfProcInstNo) d on d.WfProcInstNo = c.WfProcInstNo" +
				"	where	isnull(d.deductionamount, 0) > 0" +
				"			and ispass = '1' " +
				"  ";
		if(StringUtils.isNotBlank(czybh)){
			sql+=" and a.StartUserId = ? ";
			list.add(czybh);
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String , Object>> getExpenseAdvanceJqGrid(Page<Map<String , Object>> page, Employee employee){
		List<Object> list = new ArrayList<Object>();
		String sql="select * from ( select b.NameChi, a.Amount , a.EmpCode,a.LastUpdate,a.LastUpdatedBy from tExpenseAdvance a " +
				" left join tEmployee b on b.Number = a.EmpCode" +
				" where 1=1  " +
				"  ";
		if(StringUtils.isNotBlank(employee.getNameChi())){
			sql+=" and b.NameChi like ? ";
			list.add("%"+employee.getNameChi()+"%");
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.lastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getExpenseAdvanceTran(Page<Map<String, Object>> page, 
			Employee employee) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.*,c.descr from tExpenseAdvanceTran a" +
				" left join tWfProcInst b on b.no = a.WfProcInstNo" +
				" left join tWfProcess c on c.no = b.WfProcNo " +
				" where 1=1 and a.empCode = ? ";
		
		list.add(employee.getNumber());
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.AdvanceDate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Double getAdvanceAmount(String czybh){
		String sql=" select Amount from tExpenseAdvance where empCode = ?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{czybh});
		if(list != null && list.size() > 0){
			return Double.parseDouble(list.get(0).get("Amount").toString());
		}
		return 0.0;
	}
	
	public void doSaveAccount(String actName, String bank, String cardId,String subBranch,
			String czybh){
		String sql=" select 1 from tEmpCard where empCode = ? and ActName = ? and replace(CardId,' ','') = replace(?,' ','') and Bank = ? and (SubBranch = ? or SubBranch is null)";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{czybh,actName,cardId,bank,subBranch});
		if(list != null && list.size() > 0){
			return;
		} else {
			sql = "insert into tEmpCard(EmpCode,ActName,CardId,Bank,LastUpdate,LastUpdatedBy,Expired,ActionLog,SubBranch) " +
					" values(?,?,?,?,getdate(),?,'F','ADD',?)";
			executeUpdateBySql(sql,new Object[]{czybh,actName,cardId,bank,czybh,subBranch});
		}
	}
	
	public String getMainTableName(String wfProcNo){
		String sql=" select tableCode from tWfProcess where no = ?  ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{wfProcNo});
		if(list != null && list.size() > 0){
			return list.get(0).get("tableCode").toString();
		} 
		return "tWfCust_ExpenseAdvance";
	}
	
	public Map<String, Object> getEmpCardData(String wfProcInstNo, String tableName){
		
		String sql = " select CardId, ActName, Bank from " + tableName + " where wfProcInstNo = ?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{wfProcInstNo});
		
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}
	
	/**
	 * 根据角色ID 找审批人
	 * @param wfProcNo
	 * @return
	 */
	public List<Map<String, Object>> getAssigneesByGroupId(String groupId){
		
		String sql=" select USER_ID_ Number from ACT_ID_MEMBERSHIP where GROUP_ID_ = ?  ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{groupId});
		if(list != null && list.size() > 0){
			
			return list; 
		} 
		return null;
	}
	
	public Map<String, Object> getEmpCompany(String department){
		String sql=" select b.Code,b.Desc2 CmpDescr from tDepartment a " +
				" left join tCompany b on b.Code = a.CmpCode " +
				" where a.Code = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{department});
		if(list != null && list.size() > 0){
			
			return list.get(0);
		} 
		
		return null;
	}
	
	public void saveMarketClaimMessage(String wfProcInstNo) {
		String sql =" insert into tPersonMessage (MsgType, MsgText, MsgRelNo, MsgRelCustCode, CrtDate, SendDate, RcvType, RcvCZY, RcvDate," +
				"							RcvStatus, IsPush, PushStatus, ProgMsgType, ItemType1, ItemType2, WorkType12, PrjItem," +
				"							JobType, PlanDealDate, PlanArrDate, Remarks, Title, DealNo) " +
				"select 18,e.NameChi+'提交的:'+d.Descr+'',?,null,getdate(),getdate(),'3',b.LeaderCode,null," +
				"		'0' ,'1','0', '1',null,null,null,null," +
				"		null,null,null,null,null,null from tWfCust_MarketExpenseClaimDtl a" +
				" inner join tDepartment b on b.Code = a.DeptCode and b.LeaderCode is not null and b.LeaderCode <>''" +
				" left join tWfProcInst c on c.No = a.WfProcInstNo " +
				" left join tWfProcess d on d.No = c.WfProcNo" +
				" left join tEmployee e on e.Number = c.StartUserId " +
				" where a.WfProcInstNo = ? ";
		this.executeUpdateBySql(sql, new Object[]{wfProcInstNo, wfProcInstNo});
		
		String sql2 = "insert into tWfProcInstCopy (WfProcInstNo, TaskKey, CopyCZY, CopyDate, RcvDate, RcvStatus, LastUpdate, LastUpdatedBy," +
				"								Expired, ActionLog)" +
				" select  ?,'',b.LeaderCode,getdate(),null,'0',getdate(),'1','F','Add' from tWfCust_MarketExpenseClaimDtl a " +
				" inner join tDepartment b on b.Code = a.DeptCode and b.LeaderCode is not null and b.LeaderCode <>''" +
				" where a.WfProcInstNo = ?";
		this.executeUpdateBySql(sql2, new Object[]{wfProcInstNo, wfProcInstNo});
	}
	
	public boolean isProcOperator(String taskId, String czybh){
		
		String sql = " select b.* from ACT_RU_TASK a" +
				" left join ACT_RU_IDENTITYLINK b on a.ID_= b.TASK_ID_ " +
				" where a.ID_ = ? and " +
				" (exists(select 1 from ACT_ID_MEMBERSHIP in_a  " +
				"  			where in_a.USER_ID_ = ? and in_a.GROUP_ID_ = b.GROUP_ID_" +
				" 		) or b.USER_ID_ = ? or a.Assignee_ = ? " +
				" ) ";
		
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{taskId, czybh, czybh, czybh});
		if(list != null && list.size() > 0){
			return true;
		} 
		return false;
	}
	
	public Page<Map<String, Object>> getSupplAccountJqGrid(Page<Map<String,Object>> page, SplCheckOut splCheckOut) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select b.RcvActName,b.RcvBank,b.RcvCardId from tSplCheckOut a "
					+"left join tWfCust_PurchaseExpense b on a.No = b.RefNo "
					+"where a.SplCode = (select SplCode from tSplCheckOut where No = ?)  "
					+"and (isnull(b.RcvActName,'')<>'' or isnull(b.RcvCardId,'')<>'' or isnull(b.RcvBank,'')<>'')"
					+"group by b.RcvActName,b.RcvCardId,b.RcvBank ";
		list.add(splCheckOut.getNo());

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getWfPrjCardInfo(String wfProcNo, String wfProcInstNo){
		
		String sql = " select dbo.fGetWfPrjCardInfo(?, ?) remarks";
		
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{wfProcNo, wfProcInstNo});
		if(list != null && list.size() > 0 && list.get(0).get("remarks") != null){
			return list.get(0).get("remarks").toString();
		} 
		return "";
	}
	
	public String getTaskCommntByPIIDTaskName(String procInstId, String taskName){
		String sql = " select top 1 isnull(b.Remarks,'') remakrs from ACT_HI_TASKINST a " +
				" left join tWfProcTrack b on b.FromActivityId = a.ID_" +
				" where a.PROC_INST_ID_ = ? and a.NAME_ = ? " +
				" order by a.END_TIME_ desc";
		
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{procInstId, taskName});
		if(list != null && list.size() > 0 && list.get(0).get("remakrs") != null){
			return list.get(0).get("remakrs").toString();
		} 
		return "";
	}
	
	public Serializable doDelEmpCard(String actName, String cardId){
		
		String sql = "update tEmpCard set expired = 'T',lastUpdate = getDate() from tEmpCard where actName = ? and replace(cardId,' ','') = ? ";
		
		return this.executeUpdateBySql(sql, new Object[]{actName, cardId.replaceAll(" ", "")});
		
	}
}
