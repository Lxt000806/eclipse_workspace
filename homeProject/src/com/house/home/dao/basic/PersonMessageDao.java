package com.house.home.dao.basic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.project.Worker;

@SuppressWarnings("serial")
@Repository
public class PersonMessageDao extends BaseDao {

	/**
	 * PersonMessage分页信息
	 * 
	 * @param page
	 * @param personMessage
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PersonMessage personMessage) {
		List<Object> list = new ArrayList<Object>();

		
		String sql = "select * from tPersonMessage a where 1=1 ";

    	if (personMessage.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(personMessage.getPk());
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgType())) {
			sql += " and a.MsgType=? ";
			list.add(personMessage.getMsgType());
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgText())) {
			sql += " and a.MsgText=? ";
			list.add(personMessage.getMsgText());
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgRelNo())) {
			sql += " and a.MsgRelNo=? ";
			list.add(personMessage.getMsgRelNo());
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgRelCustCode())) {
			sql += " and a.MsgRelCustCode=? ";
			list.add(personMessage.getMsgRelCustCode());
		}
    	if (personMessage.getCrtDateFrom() != null){
			sql += " and a.CrtDate>= ? ";
			list.add(personMessage.getCrtDateFrom());
		}
		if (personMessage.getCrtDateTo() != null){
			sql += " and a.CrtDate< ? ";
			//list.add(personMessage.getDateTo());
			list.add(DateUtil.addDateOneDay(personMessage.getCrtDateTo()));
		}
    	if (personMessage.getSendDateFrom() != null){
			sql += " and a.SendDate>= ? ";
			list.add(personMessage.getSendDateFrom());
		}
		if (personMessage.getSendDateTo() != null){
			sql += " and a.SendDate< ? ";
			//list.add(personMessage.getDateTo());
			list.add(DateUtil.addDateOneDay(personMessage.getSendDateTo()));
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvType())) {
			sql += " and a.RcvType=? ";
			list.add(personMessage.getRcvType());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvCzy())) {
			sql += " and a.RcvCZY=? ";
			list.add(personMessage.getRcvCzy());
		}
    	if (personMessage.getDateFrom() != null){
			sql += " and a.RcvDate>= ? ";
			list.add(personMessage.getDateFrom());
		}
		if (personMessage.getDateTo() != null){
			sql += " and a.RcvDate<= ? ";
			list.add(personMessage.getDateTo());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvStatus())) {
			sql += " and a.RcvStatus=? ";
			list.add(personMessage.getRcvStatus());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**删除消息
	 * @param msgType
	 * @param msgRelNo
	 * @param msgRelCustCode
	 * @param rcvType
	 * @param rcvCzy
	 */
	public void delete(String msgType,String msgRelNo,String msgRelCustCode,String rcvType,String rcvCzy){
		String sql = "delete from tPersonMessage where msgType=? and msgRelNo=? and msgRelCustCode=? and rcvType=? and rcvCzy=?";
		this.executeUpdateBySql(sql, new Object[]{msgType,msgRelNo,msgRelCustCode,rcvType,rcvCzy});
		
	}
	/**获取推送消息
	 * @return
	 */
	public List<Map<String, Object>> getEmployeePushList() {
		String sql = "SELECT a.phone,COUNT(*) msgnum,rcvType from "
				+"(SELECT c.phone,a.RcvType FROM tPersonMessage a "
				+"INNER JOIN tczybm b ON a.RcvCZY=b.CZYBH "
				+"INNER JOIN tEmployee c ON b.EMNum=c.Number "
				+"WHERE  a.isPush='1' AND a.pushStatus='0' AND a.RcvStatus='0' "
				+"AND a.SendDate<=GETDATE() "
				+") a GROUP BY a.phone,a.RcvType having COUNT(*)>0";
		return this.findBySql(sql);
	}
	/**更新推送消息
	 * @return
	 */
	public void updateEmployeePushList(String phone) {
		String sql = "UPDATE tPersonMessage "
				+"SET pushStatus='1' "
				+"FROM tPersonMessage a "
				+"INNER JOIN tczybm b ON a.RcvCZY=b.CZYBH "
				+"INNER JOIN tEmployee c ON b.EMNum=c.Number "
				+"WHERE  a.isPush='1' AND a.pushStatus='0' AND a.RcvStatus='0' AND a.SendDate<=GETDATE() "
				+"AND c.Phone=?";
		this.executeUpdateBySql(sql, new Object[]{phone});
	}
	/**获取消息条数
	 * @param personMessage
	 * @return
	 */
	public long getMessageCount(PersonMessage personMessage) {
		List<Object> list = new ArrayList<Object>();
		long num;
		String sql = "select a.pk,msgType from tPersonMessage a " 
				+"where a.SendDate<=GETDATE() "; 
		if (StringUtils.isNotBlank(personMessage.getRcvType())) {
			sql += " and a.RcvType=? ";
			list.add(personMessage.getRcvType());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvCzy())) {
			sql += " and a.RcvCZY=? ";
			list.add(personMessage.getRcvCzy());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvStatus())) {
			sql += " and a.RcvStatus=? ";
			list.add(personMessage.getRcvStatus());
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgType())) {
    		if(personMessage.getMsgType().equals("7")||personMessage.getMsgType().equals("4")||personMessage.getMsgType().equals("1")||personMessage.getMsgType().equals("5")
    				||personMessage.getMsgType().equals("12")||personMessage.getMsgType().equals("18")){
    			sql += " and a.MsgType=? ";
    			list.add(personMessage.getMsgType());
//    			if(personMessage.getMsgType().equals("12")) sql += " and d.PrjConfirmDate is null and d.Status='1' ";
//    			if(personMessage.getMsgType().equals("5")) sql += " and b.ConfirmStatus='0' ";
//    			if(personMessage.getMsgType().equals("7")) sql += " and c.ModifyComplete='0' ";
    		}else{
    			sql+=" and a.MsgType not in ('7','4','1','5','12','18') ";
    		}
			num=this.countSqlResult(sql, "", list.toArray());
		}else {
			//未读消息数不包含消息类型5中状态不为未确认的和消息类型7中状态不为未整改的,以及消息类型12中项目经理确认的
			//String sql1=sql+" and ((a.MsgType='5' and b.ConfirmStatus <>'0')or (a.MsgType='7' and c.ModifyComplete<>'0') or (a.MsgType='12' and d.PrjConfirmDate is not null and d.Status='1' )) ";
			num=this.countSqlResult(sql, "", list.toArray());
		}
		return num;
	}
	/**批量修改消息状态
	 * @param personMessage
	 */
	public void updateBatch(PersonMessage personMessage) {
		List<Object> list = new ArrayList<Object>();

		String sql = "update tPersonMessage set RcvStatus='1',RcvDate=GETDATE() where SendDate<=GETDATE() and RcvStatus='0' "; 
		if (StringUtils.isNotBlank(personMessage.getRcvType())) {
			sql += " and RcvType=? ";
			list.add(personMessage.getRcvType());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvCzy())) {
			sql += " and RcvCZY=? ";
			list.add(personMessage.getRcvCzy());
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgType())) {
    		if(personMessage.getMsgType().equals("5")||personMessage.getMsgType().equals("7")||personMessage.getMsgType().equals("4")||personMessage.getMsgType().equals("1")||personMessage.getMsgType().equals("12")){
    			if(personMessage.getMsgType().equals("1")){
    				sql += " and MsgType=? and ProgMsgType not in ('1','2','3','4','5','7')";
    			}else{
    				sql += " and MsgType=? ";
    			}
    			list.add(personMessage.getMsgType());
    		}else{
    			sql += " and MsgType not in ('7','4','1','5','12') ";
    		}
		}
		this.executeUpdateBySql(sql, list.toArray());
	}
	/**
	 * PersonMessage分页信息（接口用）
	 * 
	 * @param page
	 * @param personMessage
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlForClient(Page<Map<String,Object>> page, PersonMessage personMessage) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select msgType, msgRelNo, a.pk,MsgText,substring(MsgText,charindex(':',MsgText)+1,len(MsgText)) MsgTextNoAddress,"
				+"SendDate,RcvStatus,RcvType,RcvCZY,substring(MsgText,0,charindex(':',MsgText)) address, "
				+" a.ProgMsgType,a.ItemType1,a.ItemType2,a.WorkType12,a.PrjItem,a.JobType,a.PlanDealDate,a.PlanArrDate,a.MsgRelCustCode,a.Remarks "
				+" from tPersonMessage a " ;
		if(StringUtils.isNotBlank(personMessage.getAddress())){
			sql += " left join tCustomer b on a.MsgRelCustCode = b.Code ";
		}
		sql +=" where 1=1 ";
		if(StringUtils.isEmpty(personMessage.getPrjStatus())){
			sql += " and a.SendDate<=GETDATE() ";
		}
				
    	if (StringUtils.isNotBlank(personMessage.getMsgType())) {
    		if(personMessage.getMsgType().equals("18")||personMessage.getMsgType().equals("7")||personMessage.getMsgType().equals("4")
    					||personMessage.getMsgType().equals("1")||personMessage.getMsgType().equals("5")||personMessage.getMsgType().equals("12")||personMessage.getMsgType().equals("20")){
    			sql += " and a.MsgType=? ";
    			list.add(personMessage.getMsgType());
//    			if(personMessage.getMsgType().equals("12")) sql += " and d.PrjConfirmDate is null and d.Status='1' ";
//    			if(personMessage.getMsgType().equals("5")) sql += " and b.ConfirmStatus='0' ";
//    			if(personMessage.getMsgType().equals("7")) sql += " and c.ModifyComplete='0' ";
    		}else{
    			sql += " and a.MsgType not in ('7','4','1','5','12','18','20') ";
    		}
		}
		if(StringUtils.isNotBlank(personMessage.getAddress())){
			sql += " and b.Address like ?  ";
			list.add("%"+personMessage.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgText())) {
			sql += " and a.MsgText=? ";
			list.add(personMessage.getMsgText());
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgRelNo())) {
			sql += " and a.MsgRelNo=? ";
			list.add(personMessage.getMsgRelNo());
		}
    	if (StringUtils.isNotBlank(personMessage.getMsgRelCustCode())) {
			sql += " and a.MsgRelCustCode=? ";
			list.add(personMessage.getMsgRelCustCode());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvType())) {
			sql += " and a.RcvType=? ";
			list.add(personMessage.getRcvType());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvCzy())) {
			sql += " and a.RcvCZY=? ";
			list.add(personMessage.getRcvCzy());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvStatus())&&StringUtils.isNotBlank(personMessage.getMsgType())
    			 &&(personMessage.getMsgType().equals("5")||personMessage.getMsgType().equals("7")||personMessage.getMsgType().equals("12")||
    					 personMessage.getMsgType().equals("1")||personMessage.getMsgType().equals("18"))) {
			sql += " and a.RcvStatus=? ";
			list.add(personMessage.getRcvStatus());
		}
    	if (StringUtils.isNotBlank(personMessage.getTimeoutFlag())){
			sql += " and a.PlanDealDate >= cast(GETDATE() as date) ";
    	}
    	if (StringUtils.isNotBlank(personMessage.getPrjStatus())){
    		if("1".equals(personMessage.getPrjStatus())){
    			sql += " and a.SendDate <= cast(GETDATE() as date) ";
    		}else if("2".equals(personMessage.getPrjStatus())){
    			sql += " and a.PlanDealDate < cast(GETDATE() as date) ";
    		}else if("3".equals(personMessage.getPrjStatus())){
    			sql += " and a.SendDate > cast(GETDATE() as date) ";
    		}
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**获取未读待办消息条数
	 * @param personMessage
	 * @return
	 */
	public long getNotConfirmedMessageCount(PersonMessage personMessage) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.pk,msgType from tPersonMessage a " 
				+"where a.SendDate<=GETDATE() "; 
		if (StringUtils.isNotBlank(personMessage.getRcvType())) {
			sql += " and a.RcvType=? ";
			list.add(personMessage.getRcvType());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvCzy())) {
			sql += " and a.RcvCZY=? ";
			list.add(personMessage.getRcvCzy());
		}
    	if (StringUtils.isNotBlank(personMessage.getRcvStatus())) {
			sql += " and a.RcvStatus=? ";
			list.add(personMessage.getRcvStatus());
		}
		//sql+=" and ((a.MsgType='5' and b.ConfirmStatus ='0')or (a.MsgType='7' and c.ModifyComplete='0') or (a.MsgType='12' and d.PrjConfirmDate is null and d.Status='1' ) )";
    	return this.countSqlResult(sql, "", list.toArray());
	}
	public List<PersonMessage> getPersonMessageList(PersonMessage personMessage){
		List<Object> list = new ArrayList<Object>();
		String hql="from PersonMessage  where 1=1 ";
		if (StringUtils.isNotBlank(personMessage.getMsgRelNo())) {
			hql += " and MsgRelNo=? ";
			list.add(personMessage.getMsgRelNo());
		}
		if (StringUtils.isNotBlank(personMessage.getMsgType())){
			hql += " and MsgType=? ";
			list.add(personMessage.getMsgType());
		}
		if (StringUtils.isNotBlank(personMessage.getMsgRelCustCode())){
			hql += " and MsgRelCustCode=? ";
			list.add(personMessage.getMsgRelCustCode());
		}
		List<PersonMessage> list1=this.find(hql, list.toArray());
		
		return list1;
	}
	
	public void deleteWorkerMessage(String msgRelNo ) {
		String sql = " delete from tWorkerMessage where MsgRelNo= ? ";
		this.executeUpdateBySql(sql, new Object[]{msgRelNo });
	}
	
	public List<Map<String, Object>> getProjectManReportList() {
		List<Object> list = new ArrayList<Object>();
		String sql =" SELECT  a.EmpCode,b.phone FROM  dbo.tWorker a "
				+"inner join tEmployee b on a.EmpCode=b.number "
				+"inner join tCZYBM c on c.EMNum=a.EmpCode "
				+"WHERE   exists(select 1 from tcustomer a1 "
	            +" left join tBuilderRep b1 on a1.Code=b1.CustCode and ? BETWEEN b1.BeginDate AND b1.EndDate "
	            +"  where a1.Status='4' and a1.ProjectMan=a.EmpCode and b1.pk is null) ";
					SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
					Date date=DateUtil.addDate(new Date(), 1);
					list.add(sdf.format(date));
					return this.findBySql(sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPageBySqlForBS(Page<Map<String,Object>> page, PersonMessage personMessage) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.pk,a.MsgType,b.note BMsgType,a.MsgText,a.MsgRelNo,a.MsgRelCustCode,a.CrtDate,a.SendDate,a.rcvType,c.note BrcvType, "
                +" a.RcvCzy,d.NameChi BRcvCZY,a.RcvDate,a.RcvStatus,E.note BRcvStatus,a.isPush ,F.note BisPush,a.pushStatus,g.note BpushStatus, "
                +" a.progmsgtype,h.NOTE progmsgtypedescr,a.itemtype1,i.descr itemtype1descr,a.itemtype2,j.descr itemtype2descr, "
                +" a.worktype12,k.Descr worktype12descr,a.prjItem,l.NOTE prjitemdescr,a.JobType,m.Descr jobtypedescr, "
                +" a.plandealdate,a.planarrdate,a.remarks,a.title "
                +" from tPersonMessage a "
                +" left outer join tXTDM b ON b.cbm=a.MsgType AND b.id='PERSMSGTYPE' "
                +" left outer join tXTDM c ON c.cbm=a.rcvType AND c.id='PERSRCVTYPE' "   
                +" left outer join tXTDM E ON E.cbm=a.RcvStatus AND E.id='PERSRCVSTATUS' "   
                +" left outer join tXTDM F ON f.cbm=a.isPush AND F.id='YESNO' "  
                +" left outer join tXTDM g ON g.cbm=a.pushStatus AND g.id='YESNO' "   
                +" inner join temployee d ON d.Number=a.RcvCZY "
                +" left outer join tXTDM h on h.cbm = a.progmsgtype and h.id ='ALARMTYPE' "
                +" left join tItemType1 i on a.itemtype1=i.Code "
                +" left join tItemType2 j on a.itemtype2=j.Code and j.ItemType1=a.itemtype1 " 
                +" left join tWorkType12 k on a.worktype12=k.Code "
                +" left join tXTDM l on l.ID ='PRJITEM' and l.CBM=a.PrjItem "
                +" left join tJobType m on a.jobtype=m.Code "
                +" WHERE 1=1 ";
		if (StringUtils.isNotBlank(personMessage.getRcvType())){
			sql += " and a.RcvType=? ";
			list.add(personMessage.getRcvType());
		}
		if (StringUtils.isNotBlank(personMessage.getMsgRelCustCode())){
			sql += " and a.MsgRelCustCode like ? ";
			list.add("%"+personMessage.getMsgRelCustCode()+"%");
		}
		if (StringUtils.isNotBlank(personMessage.getRcvStatus())){
			sql += " and a.RcvStatus=? ";
			list.add(personMessage.getRcvStatus());
		}
		if (StringUtils.isNotBlank(personMessage.getRcvCzy())){
			sql += " and a.RcvCZY=? ";
			list.add(personMessage.getRcvCzy());
		}
		if (personMessage.getCrtDateFrom() != null){
			sql += " and a.CrtDate>= ? ";
			list.add(personMessage.getCrtDateFrom());
		}
		if (personMessage.getCrtDateTo() != null){
			sql += " and a.CrtDate<? ";
			//list.add(personMessage.getCrtDateTo());
			list.add(DateUtil.addDateOneDay(personMessage.getCrtDateTo()));
		}
    	if (personMessage.getSendDateFrom() != null){
			sql += " and a.SendDate>= ? ";
			list.add(personMessage.getSendDateFrom());
		}
		if (personMessage.getSendDateTo() != null){
			sql += " and a.SendDate< ? ";
			list.add(DateUtil.addDateOneDay(personMessage.getSendDateTo()));
		}
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Map<String, Object> getPersonMessage(String pk) {
		String sql = "select a.pk,a.MsgType,b.note BMsgType,a.MsgText,a.MsgRelNo,a.MsgRelCustCode,a.CrtDate,a.SendDate,a.rcvType,c.note BrcvType, "
                +" a.RcvCzy,d.NameChi BRcvCZY,a.RcvDate,a.RcvStatus,E.note BRcvStatus,a.isPush ,F.note BisPush,a.pushStatus,g.note BpushStatus, "
                +" a.progmsgtype,h.NOTE progmsgtypedescr,a.itemtype1,i.descr itemtype1descr,a.itemtype2,j.descr itemtype2descr, "
                +" a.worktype12,k.Descr worktype12descr,a.prjItem,l.NOTE prjitemdescr,a.JobType,m.Descr jobtypedescr, "
                +" a.plandealdate,a.planarrdate,a.remarks,a.title "
                +" from tPersonMessage a "
                +" left outer join tXTDM b ON b.cbm=a.MsgType AND b.id='PERSMSGTYPE' "
                +" left outer join tXTDM c ON c.cbm=a.rcvType AND c.id='PERSRCVTYPE' "   
                +" left outer join tXTDM E ON E.cbm=a.RcvStatus AND E.id='PERSRCVSTATUS' "   
                +" left outer join tXTDM F ON f.cbm=a.isPush AND F.id='YESNO' "  
                +" left outer join tXTDM g ON g.cbm=a.pushStatus AND g.id='YESNO' "   
                +" inner join temployee d ON d.Number=a.RcvCZY "
                +" left outer join tXTDM h on h.cbm = a.progmsgtype and h.id ='ALARMTYPE' "
                +" left join tItemType1 i on a.itemtype1=i.Code "
                +" left join tItemType2 j on a.itemtype2=j.Code and j.ItemType1=a.itemtype1 " 
                +" left join tWorkType12 k on a.worktype12=k.Code "
                +" left join tXTDM l on l.ID ='PRJITEM' and l.CBM=a.PrjItem "
                +" left join tJobType m on a.jobtype=m.Code "
                +" WHERE a.pk=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> getDelayExecList(Page<Map<String,Object>> page, Integer msgPk) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Date,a.Remarks,b.NameChi CzybhDescr from tDelayExec a " 
				+" left join tEmployee b on a.CZYBH = b.Number "
				+" where a.MsgPk = ? ";
		list.add(msgPk);
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getMsgInfo(Integer pk){
		String sql=" select b.Address,a.Title,a.MsgText,a.SendDate,a.PlanDealDate,a.PlanArrDate "
				+" from tPersonMessage a "
				+" left join tCustomer b on a.MsgRelCustCode = b.Code "
				+" where a.pk=? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{pk});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public PersonMessage getPersonMessageByCondition(PersonMessage personMessage){
		List<Object> param = new ArrayList<Object>();
		String hql = "select a from PersonMessage a where 1=1  ";
		//a.msgType = ? and a.msgRelNo = ? and progMsgType = ?
		if(StringUtils.isNotBlank(personMessage.getMsgType())) {
			hql += " and a.msgType = ? ";
			param.add(personMessage.getMsgType());
		}
		if(StringUtils.isNotBlank(personMessage.getMsgRelNo())) {
			hql += " and a.msgRelNo = ? ";
			param.add(personMessage.getMsgRelNo());
		}
		if(StringUtils.isNotBlank(personMessage.getProgmsgType())) {
			hql += " and a.progmsgType = ? ";
			param.add(personMessage.getProgmsgType());
		}
		if(StringUtils.isNotBlank(personMessage.getMsgRelCustCode())) {
			hql += " and a.msgRelCustCode = ? ";
			param.add(personMessage.getMsgRelCustCode());
		}
		if(StringUtils.isNotBlank(personMessage.getPrjItem())) {
			hql += " and a.prjItem = ? ";
			param.add(personMessage.getPrjItem());
		}
		List<PersonMessage> list =  this.find(hql, param.toArray());
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Boolean existsRelatedRecord(PersonMessage personMessage) {
		List<Object> param = new ArrayList<Object>();
		String sql = "";
		
		// 是否有安排记录
		if("2".equals(personMessage.getProgmsgType())){
			sql += "select 1 from tCustWorkerApp where CustCode = ? and WorkType12 = ? ";
			param.add(personMessage.getMsgRelCustCode());
			param.add(personMessage.getWorkType12());
		}
		
		// 是否有领料预申请记录
		if("3".equals(personMessage.getProgmsgType()) && StringUtils.isNotBlank(personMessage.getItemType2())){
			sql += "select 1 from tItemPreApp a "
				+ " left join tItemPreAppDetail b on a.No = b.No "
				+ " left join tItem c on b.ItemCode = c.Code "
				+ " where a.CustCode = ? and a.ItemType1 = ? and c.ItemType2 = ? ";
			param.add(personMessage.getMsgRelCustCode());
			param.add(personMessage.getItemType1());
			param.add(personMessage.getItemType2());
		}
		
		// 是否有初检记录
		if("9".equals(personMessage.getProgmsgType())){
			sql += "select 1 from tSignIn where CustCode = ? and PrjItem = ? ";
			param.add(personMessage.getMsgRelCustCode());
			param.add(personMessage.getPrjItem());
		}
		if(sql.length() == 0) {
			return true;
		}
		List<Map<String, Object>> list=this.findBySql(sql, param.toArray());
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
}

