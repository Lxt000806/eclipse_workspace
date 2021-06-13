package com.house.home.dao.basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.WorkerMessage;
import com.sun.org.apache.xpath.internal.operations.And;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class WorkerMessageDao extends BaseDao{

	public Page<Map<String, Object>> getWorkerMessageNum(
			Page<Map<String, Object>> page, WorkerMessage workerMessage) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select msgType, msgRelNo, a.pk,MsgText,substring(MsgText,charindex(':',MsgText)+1,len(MsgText)) MsgTextNoAddress," +
				" SendDate,RcvStatus,RcvCZY,substring(MsgText,0,charindex(':',MsgText)) address" +
				" from tWorkerMessage a " +
				" where 1=1 and a.isPush ='1' and a.pushStatus='1' " ;//
		if(StringUtils.isNotBlank(workerMessage.getMsgType())){
			sql+=" and a.msgType = ? ";
			list.add(workerMessage.getMsgType());
		}
		if(StringUtils.isNotBlank(workerMessage.getRcvCZY())){
			sql+=" and a.rcvCZY= ? ";
			list.add(workerMessage.getRcvCZY());
		}
		if(workerMessage.getPK()!=null){
			sql+=" and a.pk= ?" ;
			list.add(workerMessage.getPK());
		}
		sql+=" order by rcvStatus ,Crtdate desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getMessageDetail(
			Page<Map<String, Object>> page, Integer pk) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select b.Address,d.descr prjItemDescr,a.crtDate,c.remarks,x1.note isPassDescr from tWorkerMessage a " +
				" left join tcustomer b on b.code=a.MsgRelCustCode" +
				" left join tPrjProgConfirm c on c.No=a.MsgRelNo " +
				" left join tPrjItem1 d on d.Code=c.PrjItem" +
				" left join tXTDM x1 on x1.cbm=c.IsPass and x1.id='YESNO'" +
				" where 1=1 " ;
		if(pk!=null){
			sql+=" and a.pk= ?" ;
			list.add(pk);
			
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public Page<Map<String, Object>> getMessageNum(
			Page<Map<String, Object>> page, String rcvCzy) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select Count(1) messageNum from tWorkerMessage  " +
				" where 1=1 and rcvStatus= '0' and pushStatus='1' and IsPush='1' " ;
		if(StringUtils.isNotBlank(rcvCzy)){
			sql+=" and rcvCzy = ?" ;
			list.add(rcvCzy);
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public long getMessageCount(WorkerMessage workerMessage){
		List<Object> list = new ArrayList<Object>();
		long num;
		String sql = "select a.pk,msgType from tWorkerMessage a " 
				+"where a.SendDate<=GETDATE() "; 
		if (StringUtils.isNotBlank(workerMessage.getMsgType())) {
			sql += " and a.MsgType=? ";
			list.add(workerMessage.getMsgType());
		}
    	if (StringUtils.isNotBlank(workerMessage.getRcvCZY())) {
			sql += " and a.RcvCZY=? ";
			list.add(workerMessage.getRcvCZY());
		}
    	if (StringUtils.isNotBlank(workerMessage.getRcvStatus())) {
			sql += " and a.RcvStatus=? ";
			list.add(workerMessage.getRcvStatus());
		}
    	if (StringUtils.isNotBlank(workerMessage.getMsgType())) {
    		if(workerMessage.getMsgType().equals("1")){
    			sql += " and a.MsgType= ? ";
    			list.add(workerMessage.getMsgType());
    		}else{
    			sql+=" and a.MsgType <>'1' ";
    		}
			num=this.countSqlResult(sql, "", list.toArray());
		}else {
			//未读消息数不包含消息类型5中状态不为未确认的和消息类型7中状态不为未整改的,以及消息类型12中项目经理确认的
			//String sql1=sql+" and ((a.MsgType='5' and b.ConfirmStatus <>'0')or (a.MsgType='7' and c.ModifyComplete<>'0') or (a.MsgType='12' and d.PrjConfirmDate is not null and d.Status='1' )) ";
			num=this.countSqlResult(sql, "", list.toArray());
		}
		return num;
	}
	
	public void doUpdateMessageStatus(String rcvCzy) {
		String sql = " update tWorkerMessage set RcvDate=getDate(),RcvStatus='1' where isPush = '1' and pushStatus='1' and rcvCzy= ? ";
		this.executeUpdateBySql(sql, new Object[]{rcvCzy});
	}
	
	
	public List<Map<String, Object>> getWorkerMessagePushList() {
		String sql = " select  a.Phone,count(*) msgnum,a.MsgType from (	" +
				" select b.Phone,a.MsgType  from tWorkerMessage a " +
				" inner join tWorker b on b.Code=a.RcvCZY " +
				" where a.IsPush='1' and a.PushStatus='0' and a.RcvStatus='0') a" +
				" group by a.Phone,a.MsgType having count(*)>0 ";
		return this.findBySql(sql);
	}
	
	public void updateWorkerMessageStatus(String rcvCzy) {
		String sql = " update tWorkerMessage set PushStatus='1' from tWorkerMessage a" +
				" left join tWorker b on b.Code=a.RcvCZY where b.Phone= ? ";
		this.executeUpdateBySql(sql, new Object[]{rcvCzy});
	}
	
}
