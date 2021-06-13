package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustLog;

@SuppressWarnings("serial")
@Repository
public class ResrCustLogDao extends BaseDao{
	
	public Page<Map<String, Object>> findResrLogPageBySql(
			Page<Map<String, Object>> page, ResrCustLog resrCustLog) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.*,e.NameChi businessManDescr,b.Descr builderDescr,x1.NOTE GenderDescr,x2.Note StatusDescr " +
				" from tResrCustLog a " +
				" left join tEmployee e on e.Number =a.BusinessMan " +
				" left join tBuilder b on b.code= a.builderCode " +
				" left join tXTDM x1 on x1.CBM =a.Gender and x1.ID='GENDER' " +
				" left join tXTDM x2 on x2.CBm = a.status and x2.ID='RESRCUSTSTS' " +
				" where exists(select 1 from tResrCust in_a where a.ResrCode=in_a.Code and (in_a.BusinessMan=? or in_a.CrtCZY=?) ) ";
		list.add(resrCustLog.getLastUpdatedBy());
		list.add(resrCustLog.getLastUpdatedBy());
		if(StringUtils.isNotBlank(resrCustLog.getResrCode())){
			sql+=" and a.ResrCode = ?";
			list.add(resrCustLog.getResrCode());
		}
		if(StringUtils.isNotBlank(resrCustLog.getDescr())){
			sql+=" and a.Descr = ?";
			list.add(resrCustLog.getDescr());
		}
		if(StringUtils.isNotBlank(resrCustLog.getAddress())){
			sql+=" and a.address like ?";
			list.add("%"+resrCustLog.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(resrCustLog.getMobile1())){
			sql+=" and a.mobile1 = ?";
			list.add(resrCustLog.getMobile1());
		}
		if(StringUtils.isNotBlank(resrCustLog.getBuilderCode())){
			sql+=" and a.buildercode = ?";
			list.add(resrCustLog.getBuilderCode());
		}
		if(StringUtils.isNotBlank(resrCustLog.getStatus())){
			sql+=" and a.Status = ?";
			list.add(resrCustLog.getStatus());
		}
		if(StringUtils.isNotBlank(resrCustLog.getBusinessMan())){
			sql+=" and a.businessMan = ? ";
			list.add(resrCustLog.getBusinessMan());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.lastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
}
