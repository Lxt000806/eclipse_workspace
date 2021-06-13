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
public class ActivitySetDao extends BaseDao {

	public Page<Map<String,Object>> getActivitySetList(Page<Map<String,Object>> page, String  actNo,String ticketNo,String tokenCzyDescr){
		List<Object> params = new ArrayList<Object>();
		String sql = " select tas.PK,tas.TicketNo,tt.Descr custDescr,tspl.Descr+'('+tx1.NOTE+')' supplCodeDescr,tas.SupplCode "
				   + " from tActivitySet tas "
				   + " left join tTicket tt on tas.TicketNo = tt.TicketNo "
				   + " left join tSupplier tspl on tspl.Code = tas.SupplCode "
				   + " left join tEmployee te on te.Number = tas.LastUpdatedBy "
				   + " left join tXTDM tx1 on  tx1.ID='ACTSPLTYPE' and tx1.CBM = tas.SupplType"
				   + " where tas.Expired='F' ";
		if(StringUtils.isNotBlank(actNo)){
			sql += " and tas.actNo = ? ";
			params.add(actNo);
		}
		if(StringUtils.isNotBlank(ticketNo)){
			sql += " and tas.TicketNo like ? ";
			params.add("%"+ticketNo+"%");
		}
		if(StringUtils.isNotBlank(tokenCzyDescr)){
			sql += " and te.nameChi like ? ";
			params.add("%"+tokenCzyDescr+"%");
		}
		sql += " order by tas.LastUpdate desc ";
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> getActSupplList(Page<Map<String,Object>> page,String actNo, String  supplType,String supplCodeDescr){
		List<Object> params = new ArrayList<Object>();
		String sql = " select tcaspl.SupplCode,tspl.Descr supplCodeDescr,tcaspl.SupplType "
				   + " from tCmpActivitySuppl tcaspl "
				   + " left join tSupplier tspl on tspl.Code = tcaspl.SupplCode "
				   + " left join tActivity ta on ta.CmpActNo = tcaspl.No "
				   + " where ta.No=? ";
		params.add(actNo);
		if(StringUtils.isNotBlank(supplType)){
			sql += " and tcaspl.SupplType=? ";
			params.add(supplType);
		}
		if(StringUtils.isNotBlank(supplCodeDescr)){
			sql += " and tspl.Descr like ? ";
			params.add("%"+supplCodeDescr+"%");
		}
		sql += " order by tspl.Descr ";
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public boolean exisitActivitySet(String actNo,String ticketNo,String supplCode){
		String sql = " select 1 from tActivitySet where Expired='F' and ticketNo=? and supplCode=? and actNo=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{ticketNo,supplCode,actNo});
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	public Map<String,Object> getActSupplDetail(String supplCode){
		String sql = " select tcaspl.SupplCode,tspl.Descr supplCodeDescr,tcaspl.SupplType "
				   + " from tCmpActivitySuppl tcaspl "
				   + " left join tSupplier tspl on tspl.Code = tcaspl.SupplCode "
				   + " where tcaspl.SupplCode=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{supplCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getActSetDetail(Integer pk){
		String sql = " select a.pk,a.ActNo,a.TicketNo,a.SupplType,a.SupplCode,a.SupplCodeDescr,a.LastUpdate,a.lastUpdatedByDescr, "
				   + " isnull(a.Descr,'')+( case when a.setCount > 0 then ' 下定:'+convert(varchar,a.setCount)+'家' else '' end )+(case when a.typeCount > 0 then ' 品类:'+convert(varchar,a.typeCount)+'种' else '' end) Descr"
				   + " from ("
				   + " 		select tas.pk,tas.ActNo,tas.TicketNo,tas.SupplType,tas.SupplCode,tspl.Descr SupplCodeDescr, "
				   //+ " 	  tt.Descr,tas.LastUpdate,te.NameChi lastUpdatedByDescr "
				   + " 		tt.Descr,(select count(1) from tActivitySet where Expired='F' and ActNo = tas.ActNo and TicketNo = tas.TicketNo ) setCount,(select count(1) from ( select distinct SupplType from tActivitySet where Expired='F' and ActNo = tas.ActNo and TicketNo = tas.TicketNo ) a ) typeCount,"
				   + " 		tas.LastUpdate,te.NameChi lastUpdatedByDescr "
				   + " 		from tActivitySet tas "
				   + " 		left join tTicket tt on tt.TicketNo = tas.TicketNo "
				   + " 		left join tEmployee te on te.Number = tas.LastUpdatedBy "
				   + " 		left join tSupplier tspl on tspl.Code = tas.SupplCode "
				   + " 		where tas.pk=? "
				   + " ) a ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}

