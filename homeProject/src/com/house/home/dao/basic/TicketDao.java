package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Ticket;
import com.sun.org.apache.bcel.internal.generic.Select;

@SuppressWarnings("serial")
@Repository
public class TicketDao extends BaseDao {

	/**
	 * Ticket分页信息
	 * 
	 * @param page
	 * @param ticket
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Ticket ticket) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,b.ActName actName from tTicket a " +
				" left join tActivity b on b.no=a.ActNo " +
				"where 1=1 ";

    	if (ticket.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(ticket.getPk());
		}
    	if (StringUtils.isNotBlank(ticket.getActNo())) {
			sql += " and a.ActNo =? ";
			list.add(ticket.getActNo());
		}
    	if (StringUtils.isNotBlank(ticket.getTicketNo())) {
			sql += " and a.TicketNo like ? ";
			list.add("%"+ticket.getTicketNo()+"%");
		}
    	if (StringUtils.isNotBlank(ticket.getStatus())) {
			sql += " and a.Status=? ";
			list.add(ticket.getStatus());
		}
    	if (StringUtils.isNotBlank(ticket.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(ticket.getDescr());
		}
    	if (StringUtils.isNotBlank(ticket.getAddress())) {
			sql += " and a.Address=? ";
			list.add(ticket.getAddress());
		}
    	if (StringUtils.isNotBlank(ticket.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(ticket.getDesignMan());
		}
    	if (StringUtils.isNotBlank(ticket.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(ticket.getBusinessMan());
		}
    	if (ticket.getProvideDate() != null) {
			sql += " and a.ProvideDate=? ";
			list.add(ticket.getProvideDate());
		}
    	if (ticket.getSignDate() != null) {
			sql += " and a.SignDate=? ";
			list.add(ticket.getSignDate());
		}
    	if (ticket.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(ticket.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(ticket.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(ticket.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(ticket.getExpired()) || "F".equals(ticket.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(ticket.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(ticket.getActionLog());
		}
    	if(StringUtils.isNotBlank(ticket.getNotSend())){
    		sql+=" and a.status='1' ";
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findTicketByAct(
			Page<Map<String, Object>> page, String actNo, String status,String businessMan,String custName,String ticketNo,boolean isSign) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select TicketNo,descr from tTicket  where 1=1 ";
		if(StringUtils.isNotBlank(actNo)){
			sql += " and actNo=? ";
			list.add(actNo);
		}
		if(StringUtils.isNotBlank(status)){
			sql+=" and status=?";
			list.add(status);
		}
		if(StringUtils.isNotBlank(businessMan)){
			sql+=" and businessMan=?";
			list.add(businessMan);
		}
		if(StringUtils.isNotBlank(custName)){
			sql += " and descr like ? ";
			list.add("%"+custName+"%");
		}
		if(StringUtils.isNotBlank(ticketNo)){
			sql += " and TicketNo like ? ";
			list.add("%"+ticketNo+"%");
		}
		if(isSign){
			sql += " and SignDate is null ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> findTicketByActAndNo(String actNo,
			String ticketNo) {
		List<Object> lists=new ArrayList<Object>();
		String sql="select a.pk,a.actName,a.ActNo,a.TicketNo,a.Address,a.businessManDescr ,a.businessManPhone,"
				 +" isnull(a.Descr,'')+( case when a.setCount > 0 then ' 下定:'+convert(varchar,a.setCount)+'家' else '' end )+(case when a.typeCount > 0 then ' 品类:'+convert(varchar,a.typeCount)+'种' else '' end) Descr "
				 +" from ( "
				 +" SELECT a.pk,c.actName,a.Descr,a.ActNo,a.TicketNo,a.Address,case when a.ProvideType='1' then e.Desc1+ ISNULL('-'+f.Desc1,'') else d.zwxm end businessManDescr ,case  when b.NameChi is null then a.busiManName+' '+a.busiManPhone else b.NameChi+' '+b.Phone end businessManPhone,a.lastUpdate, "
				 +" (select count(1) from tActivitySet where Expired='F' and ActNo = a.ActNo and TicketNo = a.TicketNo) setCount,(select count(1) from ( select distinct SupplType from tActivitySet where Expired='F' and ActNo = a.ActNo and TicketNo = a.TicketNo) a ) typeCount "
				 +" FROM  tTicket a "
				 +"LEFT JOIN tEmployee b ON a.BusinessMan=b.Number " 
				 +"	left join tActivity c on a.ActNo=c.no "
	             +" LEFT JOIN  tCZYBM d  ON d.CZYBH=a.ProvideCZY "
	             +" LEFT JOIN tEmployee g ON d.EMNum=g.Number "
				 +"	LEFT JOIN dbo.tDepartment1 e ON e.Code=g.Department1 "
				 +"	LEFT JOIN dbo.tDepartment2 f ON f.Code=g.Department2 "
                 +"WHERE  a.TicketNo=?";
		lists.add(ticketNo);
		if(StringUtils.isNotBlank(actNo)){
			sql+="  and a.ActNo=? ";
			lists.add(actNo);
		}
		sql+=" ) a order by a.lastUpdate desc";
		List<Map<String, Object>> list = this.findBySql(sql,
				lists.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public boolean existsTic(String ticketNo){
		List<Object> lists=new ArrayList<Object>();
		String sql="select  * from tTicket where no = ? and expired='F' ";
		
		List<Map<String, Object>> list = this.findBySql(sql,lists.toArray());
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	
	

}

