package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.IntProgDetail;

@SuppressWarnings("serial")
@Repository
public class IntProgDetailDao extends BaseDao {

	/**
	 * IntProgDetail分页信息
	 * 
	 * @param page
	 * @param intProgDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, IntProgDetail intProgDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.IsCupboard,t4.Note IsCup,a.pk,a.CustCode,a.type,"
				+ "t1.note typedescr,a.Date,a.ResPart,t2.note ResPartDescr,a.CancelReson,b.address,"
				+ "t3.Note CancelResonDescr,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog"
				+ " from tIntProgDetail a  "
				+ "left join tXTDM t1 on a.type = t1. CBM and t1.id='INTDTTYPE'  "
				+ "left join tXTDM t2 on a.ResPart = t2. CBM and t2.id='RESPART'  "
				+ "left join tXTDM t3 on a.CancelReson = t3.CBM and t3.id='CANCELRESON' "
				+ "left join tXTDM t4 on a.IsCupboard = t4.CBM and t4.id='YESNO' "
				+ "left join tCustomer b on a.custcode=b.code "
				+ "where a.custcode =?";
		list.add(intProgDetail.getCustCode());
		if (intProgDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(intProgDetail.getPk());
		}
		if (StringUtils.isNotBlank(intProgDetail.getType())) {
			sql += " and a.Type=? ";
			list.add(intProgDetail.getType());
		}
		if (intProgDetail.getDateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(intProgDetail.getDateFrom());
		}
		if (intProgDetail.getDateTo() != null) {
			sql += " and a.Date<= ? ";
			list.add(intProgDetail.getDateTo());
		}
		if (StringUtils.isNotBlank(intProgDetail.getResPart())) {
			sql += " and a.ResPart=? ";
			list.add(intProgDetail.getResPart());
		}
		if (StringUtils.isNotBlank(intProgDetail.getCancelReson())) {
			sql += " and a.CancelReson=? ";
			list.add(intProgDetail.getCancelReson());
		}
		if (StringUtils.isNotBlank(intProgDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(intProgDetail.getRemarks());
		}
		if (intProgDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(intProgDetail.getLastUpdate());
		}
		if (StringUtils.isNotBlank(intProgDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(intProgDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(intProgDetail.getExpired())
				|| "F".equals(intProgDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(intProgDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(intProgDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(intProgDetail.getIsCupboard())) {
			sql += " and a.IsCupboard=? ";
			list.add(intProgDetail.getIsCupboard());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getIntProgDetail(String custCode) {
		String sql = "select a.IsCupboard,t4.Note IsCup,a.pk,a.CustCode,a.type,"
				+ "t1.note typedescr,a.Date,a.ResPart,t2.note ResPartDescr,a.CancelReson,b.address,"
				+ "t3.Note CancelResonDescr,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog"
				+ " from tIntProgDetail a  "
				+ "left join tXTDM t1 on a.type = t1. CBM and t1.id='INTDTTYPE'  "
				+ "left join tXTDM t2 on a.ResPart = t2. CBM and t2.id='RESPART'  "
				+ "left join tXTDM t3 on a.CancelReson = t3.CBM and t3.id='CANCELRESON' "
				+ "left join tXTDM t4 on a.IsCupboard = t4.CBM and t4.id='YESNO' "
				+ "left join tCustomer b on a.custcode=b.code "
				+ "where a.custcode =?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { custCode });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	public Map<String , Object>  findDescr(String cbm,String id) {
		String sql ="select note from tXTDM where CBM=? and id=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{cbm,id});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
		
	}
}
