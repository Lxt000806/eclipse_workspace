package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;

@SuppressWarnings("serial")
@Repository
public class ActivityGiftDao extends BaseDao {
	
	public Page<Map<String,Object>> getActivityGiftList(Page<Map<String,Object>> page, String  actNo,String ticketNo,String tokenCzyDescr){
		List<Object> params = new ArrayList<Object>();
		String sql = " select tag.PK,tag.TicketNo,tt.Descr,ti.Descr itemCodeDescr,tag.Qty "
				   + " from tActivityGift tag "
				   + " left join tTicket tt on tag.TicketNo = tt.TicketNo "
				   + " left join tItem ti on ti.Code = tag.ItemCode "
				   + " left join tEmployee te on te.Number = tag.LastUpdatedBy "
				   + " where tag.Expired='F' ";
		if(StringUtils.isNotBlank(actNo)){
			sql += " and tag.ActNo = ? ";
			params.add(actNo);
		}
		if(StringUtils.isNotBlank(ticketNo)){
			sql += " and tag.TicketNo like ? ";
			params.add("%"+ticketNo+"%");
		}
		if(StringUtils.isNotBlank(tokenCzyDescr)){
			sql += " and te.nameChi like ? ";
			params.add("%"+tokenCzyDescr+"%");
		}
		sql += " order by tag.LastUpdate desc ";
		return this.findPageBySql(page, sql, params.toArray());
	}


	public Page<Map<String,Object>> getActGiftList(Page<Map<String,Object>> page,String actNo, String  type,String itemCodeDescr){
		List<Object> params = new ArrayList<Object>();
		String sql = " select tcag.ItemCode,ti.Descr itemCodeDescr "
				   + " from tCmpActivityGift tcag "
				   + " left join tItem ti on ti.Code = tcag.ItemCode "
				   + " left join tActivity ta on ta.CmpActNo = tcag.No "
				   + " where ta.No=? and tcag.type='2' ";
		params.add(actNo);
/*		if(StringUtils.isNotBlank(type)){
			sql += " and tcag.type=? ";
			params.add(type);
		}*/
		if(StringUtils.isNotBlank(itemCodeDescr)){
			sql += " and ti.Descr like ? ";
			params.add("%"+itemCodeDescr+"%");
		}
		sql += " order by ti.DispSeq,ti.Descr ";
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public boolean exisitActivityGift(String actNo,String ticketNo,String itemCode){
		String sql = " select 1 from tActivityGift where Expired='F' and actNo=? and ticketNo=? and itemCode=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{actNo,ticketNo,itemCode});
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	public Map<String,Object> getActGiftDetail(String itemCode){
		String sql = " select tcag.ItemCode,ti.Descr itemCodeDescr "
				   + " from tCmpActivityGift tcag "
				   + " left join tItem ti on ti.Code = tcag.ItemCode "
				   + " where tcag.itemCode=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public Map<String,Object> getActGiftDetailByPk(Integer pk){
		String sql = " select tag.PK,tag.ActNo,tag.TicketNo,tag.ItemCode,ti.Descr itemCodeDescr, "
				   + " tt.Descr custDescr,tag.LastUpdate,te.NameChi lastUpdatedByDescr,tag.qty "
				   + " from tActivityGift tag "
				   + " left join tTicket tt on tag.TicketNo = tt.TicketNo "
				   + " left join tEmployee te on te.Number = tag.LastUpdatedBy "
				   + " left join tItem ti on ti.Code = tag.ItemCode "
				   + " where tag.pk=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public boolean existCmpActGift(String actNo,String itemCode){
		boolean result = false;
		String sql = " select 1 "
				   + " from tCmpActivityGift tcag "
				   + " left join tActivity ta on ta.CmpActNo = tcag.No "
				   + " where ta.No=? and tcag.ItemCode=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{actNo,itemCode});
		if(list != null && list.size() > 0){
			result = true;
		}
		return result;
	}
}

