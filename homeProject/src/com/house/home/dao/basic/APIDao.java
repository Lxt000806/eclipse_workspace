package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.home.client.service.evt.GetCustomerInfoEvt;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.entity.basic.APIKey;

@SuppressWarnings("serial")
@Repository
public class APIDao extends BaseDao {

	public Page<Map<String, Object>> getCustomerInfo(Page<Map<String, Object>> page, GetCustomerInfoEvt evt){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select tr.Descr region,tx1.NOTE layout,tc.area,tc.contractFee, "
				   + " te1.NameChi designMan,tx2.NOTE designStyle,te2.NameChi projectMan,tb.Descr address, "
				   + " tc.Code CustCode,tc.SignDate "
				   + " from tCustomer tc "
				   + " left join tBuilder tb on tc.BuilderCode = tb.Code "
				   + " left join tRegion tr on tb.RegionCode = tr.Code "
				   + " left join tXTDM tx1 on tx1.ID='LAYOUT' and tx1.CBM = tc.Layout "
				   + " left join tXTDM tx2 on tx2.ID='DESIGNSTYLE' and tx2.CBM = tc.DesignStyle "
				   + " left join tEmployee te1 on te1.Number = tc.DesignMan "
				   + " left join tEmployee te2 on te2.Number = tc.ProjectMan "
				   + " where (tc.Status='4' or (tc.Status='5' and EndCode='3')) and SignDate >= '2018-01-01' "
				   + " and tc.ProjectMan is not null ) a where 1=1 ";

		if(StringUtils.isNotBlank(evt.getRegion())){
			sql += " and a.region like ? ";
			params.add("%"+evt.getRegion()+"%");
		}
		if(StringUtils.isNotBlank(evt.getAddress())){
			sql += " and a.address like ? ";
			params.add("%"+evt.getAddress()+"%");
		}
		if(evt.getDateFrom() != null || evt.getDateTo() != null){
			sql += " and exists ( "
			     + " 		select 1 from tPrjProgConfirm in_tppc where in_tppc.CustCode = tc.Code "
			     + " 		and in_tppc.PrjItem in ('3', '5', '8', '9', '10', '16') ";
			if(evt.getDateFrom() != null){
				sql += "	and in_tppc.Date >= ? ";
				params.add(DateUtil.format(evt.getDateFrom(), "yyyy-MM-dd"));
			}
			if(evt.getDateTo() != null){
				sql += "  	and in_tppc.Date < ? ";
				params.add(DateUtil.format(DateUtil.addDateOneDay(evt.getDateTo()), "yyyy-MM-dd"));
			}
			sql += " ) ";
		}
		sql += " order by a.SignDate desc ";
		return this.findPageBySql(page, sql, params.toArray());
	}

	public List<Map<String, Object>> getCustomerDetailInfo(String custCode, Date dateFrom, Date dateTo, String prjItems){
		List<Object> params = new ArrayList<Object>();
		String sql = " select No, tpi1.Descr prjItemDescr,tpp.beginDate, tppc.Date confirmDate "
				   + " from tPrjProgConfirm tppc "
				   + " left join tPrjItem1 tpi1 on tppc.PrjItem = tpi1.Code "
				   + " left join tPrjProg tpp on tpp.CustCode = tppc.CustCode and tpp.PrjItem = tppc.PrjItem "
				   + " where tppc.CustCode = ? and tppc.PrjItem in ('3', '5', '8', '9', '10', '16')";
		params.add(custCode);
		if(StringUtils.isNotBlank(prjItems)){
			sql += "and tppc.PrjItem in ('"+prjItems.replace(",", "','")+"') ";
		}
		
		if(dateFrom != null){
			sql += " and tppc.Date >= ? ";
			params.add(DateUtil.format(dateFrom, "yyyy-MM-dd"));
		}

		if(dateTo != null){
			sql += " and tppc.Date <= ? ";
			params.add(DateUtil.format(DateUtil.addDateOneDay(dateTo), "yyyy-MM-dd"));
		}
		sql += " order by tpi1.Seq ";
		return this.findBySql(sql, params.toArray());
	}

	public List<Map<String, Object>> getPrjProgPhoto(String custCode){
		String sql = "select top 3 photoName src, isSendYun from tPrjProgPhoto where PrjItem='1' and Type='1' and CustCode=?";
		return this.findBySql(sql, new Object[]{custCode});
	}
}

