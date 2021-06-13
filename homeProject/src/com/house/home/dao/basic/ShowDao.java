package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.entity.basic.APIKey;

@SuppressWarnings("serial")
@Repository
public class ShowDao extends BaseDao {

	
	public Page<Map<String, Object>> getShowBuilds(Page<Map<String, Object>> page, GetShowBuildsEvt evt) {
		List<Object> params = new ArrayList<Object>();

		String sql = " select tc.Gender,substring(tc.Descr,1,1) custDescr,tb.Descr Address,tc.Area,tc.BeginDate,tc.Code custCode,a.No prjProgConfirmNo,tpi1.Descr prjItem1Descr,tppc.Date confirmDate "
				   + " from ( "
				   + " 		select max(No) No "
				   + " 		from tPrjProgConfirm tppc "
				   + " 		where IsPass='1' and PrjItem in ('3','5','8','9','10','16') "
				   + " 		and tppc.IsPushCust='1' and convert(varchar, tppc.LastUpdate, 112) <= convert(varchar, dateadd(day, -1, getdate()), 112) "
				   //+ " 		and convert(varchar, tppc.LastUpdate, 112) >= '20180101' "
				   + " 		group by CustCode,PrjItem "
				   + " ) a "
				   + " left join tPrjProgConfirm tppc on a.No = tppc.No "
				   + " left join tCustomer tc on tc.Code = tppc.CustCode "
				   + " left join tPrjItem1 tpi1 on tpi1.Code = tppc.PrjItem "
				   + " left join tBuilder tb on tb.Code = tc.BuilderCode"
				   + " left join tCustType tct on tct.Code = tc.Custtype "
				   + " where 1=1 and tct.IsPushPub = '1' and tc.IsPushCust='1' ";//and tppc.IsPushCust='1' and convert(varchar, tppc.LastUpdate, 112) <= convert(varchar, dateadd(day, -1, getdate()), 112) ";
		if(StringUtils.isNotBlank(evt.getPrjItem1())){
			sql += " and tppc.PrjItem=? ";
			params.add(evt.getPrjItem1());
		}
		if(StringUtils.isNotBlank(evt.getAreaSizeType())){
			Double startArea = 0.0;
			Double endArea = 0.0;
			if("1".equals(evt.getAreaSizeType())){
				endArea = 60.0;
			}else if("2".equals(evt.getAreaSizeType())){
				startArea = 60.0;
				endArea = 80.0;
			}else if("3".equals(evt.getAreaSizeType())){
				startArea = 80.0;
				endArea = 100.0;
			}else if("4".equals(evt.getAreaSizeType())){
				startArea = 100.0;
			}else if("5".equals(evt.getAreaSizeType())){
				startArea = 100.0;
				endArea = 150.0;
			}else{
				startArea = 150.0;
			}
			if(startArea > 0.0){
				sql += " and tc.Area >= ? ";
				params.add(startArea);
			}
			if(endArea > 0.0){
				sql += " and tc.Area < ? ";
				params.add(endArea);
			}
		}
		if(StringUtils.isNotBlank(evt.getBuildCode())){
			sql+=" and tc.builderCode = ? ";
			params.add(evt.getBuildCode());
		}
		if(StringUtils.isNotBlank(evt.getM_status())){
			System.out.println(evt.getM_status());
			if("1".equals(evt.getM_status())){
				sql+=" and tc.status = '4'";
			} else if("2".equals(evt.getM_status())){
				sql+=" and tc.status = '5' ";
			}else {
				sql+=" and 1=1 ";
			}
		}
		
		sql += " order by tppc.Date desc ";
		
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public List<Map<String, Object>> getPrjProgConfirm(String custCode){
		String sql = " select tppc.Date confirmDate,tpi1.Descr prjItemDescr,tpi1.seq,tppc.prjItem,tppc.No prjProgConfirmNo "
				   + " from ( "
				   + " 		select max(No) No "
				   + " 		from tPrjProgConfirm "
				   + " 		where CustCode=? and IsPass='1' and PrjItem in ('3','5','8','9','10','16') and IsPushCust = '1'"
				   + " 		group by CustCode,PrjItem "
				   + " ) a "
				   + " left join tPrjProgConfirm tppc on tppc.No = a.No "
				   + " left join tPrjItem1 tpi1 on tpi1.Code=tppc.PrjItem " 
				   + " order by tpi1.Seq ";
		return this.findBySql(sql, new Object[]{custCode});
	}
	
	public List<Map<String, Object>> getPrjProgConfirmPhoto(String prjProgConfirmNo, Integer number){
		String sql = " select " + (number == null || number == 0 ? "": "top " + number) + " tppp.PhotoName src,isnull(tppp.isSendYun, '0') isSendYun "
				   + " from tPrjProgPhoto tppp "
				   + " where tppp.RefNo=? and tppp.IsPushCust = '1' ";
		return this.findBySql(sql, new Object[]{prjProgConfirmNo});
	}

	public List<Map<String, Object>> getCityAppUrlList(Double longitude, Double latitude){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from (select Code value, Descr,OuterUrl,Mobile, "
				   + " ACOS(SIN( ISNULL( Latitude , 0 ) / ( 180/PI() ) )*SIN( ? / ( 180/PI() ) ) + "
				   + " COS( ISNULL( Latitude , 0 ) / ( 180/PI() ) )*COS( ? / ( 180/PI() ) )*COS( ? / ( 180/PI() ) - "
				   + " ISNULL( longitude , 0 ) / ( 180/PI() ) ) )*6371.004 distance "
				   + " from tCityAppUrl ) a order by a.distance ";
		if(latitude == null ){
			params.add(0.0); 
		}else{
			params.add(latitude);
		}
		if(latitude == null ){
			params.add(0.0); 
		}else{
			params.add(latitude);
		}
		if(longitude == null ){
			params.add(0.0); 
		}else{
			params.add(longitude);
		}
		return this.findBySql(sql, params.toArray());
	}

	public List<Map<String, Object>> getPrjItem1List(){
		String sql = " select Code value,Descr "
				   + " from tPrjItem1 "
				   + " where IsConfirm='1' and Expired='F' and Code in ('3','5','8','9','10','16') "
				   + " order by Seq ";
		return this.findBySql(sql, new Object[]{});
	}
	
	public Page<Map<String, Object>> getDesignDemoList(Page<Map<String, Object>> page, GetDesignDemoListEvt evt) {
		List<Object> params = new ArrayList<Object>();

		String sql = " select tdd.No,(select min(PK) from tDesignDemoPic where No = tdd.No) picAddrPk, "
				   + " tb.Descr builderDescr,tx1.NOTE designStyleDescr,tx2.NOTE layoutDescr,tdd.Area,e.nameChi designDescr  "
				   + " from tDesignDemo tdd "
				   + " left join tBuilder tb on tdd.BuilderCode = tb.Code " +
				   " left join temployee e on e.number = tdd.designMan " +
				   " left join tCustomer c on tdd.custcode=c.code " 
				   + " left join tXTDM tx1 on tx1.ID='DDESIGNSTL' and tx1.cbm = tdd.DesignSty "
				   + " left join tXTDM tx2 on tx2.ID='DLAYOUT' and tx2.cbm = tdd.Layout "
				   + " where tdd.Expired='F' and tdd.IsPushCust='1' ";
		if(StringUtils.isNotBlank(evt.getAreaSizeType())){
			Double startArea = 0.0;
			Double endArea = 0.0;
			if("1".equals(evt.getAreaSizeType())){
				endArea = 60.0;
			}else if("2".equals(evt.getAreaSizeType())){
				startArea = 60.0;
				endArea = 80.0;
			}else if("3".equals(evt.getAreaSizeType())){
				startArea = 80.0;
				endArea = 100.0;
			}else if("4".equals(evt.getAreaSizeType())){
				startArea = 100.0;
			}else if("5".equals(evt.getAreaSizeType())){
				startArea = 100.0;
				endArea = 150.0;
			}else{
				startArea = 150.0;
			}
			if(startArea > 0.0){
				sql += " and tdd.Area >= ? ";
				params.add(startArea);
			}
			if(endArea > 0.0){
				sql += " and tdd.Area < ? ";
				params.add(endArea);
			}
		}
		if(StringUtils.isNotBlank(evt.getM_status())){
			if("1".equals(evt.getM_status())){
				sql+=" and c.status in ('1','2','3')";
			}else if("2".equals(evt.getM_status())){
				sql+=" and c.status = '4' ";
			}else if("3".equals(evt.getM_status())){
				sql+=" and c.status = '5' ";
			}else if("4".equals(evt.getM_status())){
				sql+=" and 1 = 1 ";

			}
		}
		if(StringUtils.isNotBlank(evt.getLayout())){
			sql += " and tdd.Layout=? ";
			params.add(evt.getLayout());
		}
		if(StringUtils.isNotBlank(evt.getStyle())){
			sql += " and tdd.DesignSty=? ";
			params.add(evt.getStyle());
		}
		if (StringUtils.isNotBlank(evt.getBuilderCode())){
			sql +=" and tb.code = ? ";
			params.add(evt.getBuilderCode());
		}
		sql += " order by tdd.No ";

		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Map<String, Object> getDesignDemoDetail(String no){
		String sql = " select substring(tc.Descr,1,1) custDescr,tb.Descr builderDescr,te.NameChi designManDescr, "
				   + " tx1.NOTE layoutDescr,tx2.NOTE designStyleDescr,tdd.Area,tdd.Amount,tdd.DesignRemark,tc.Gender "
				   + " from tDesignDemo tdd "
				   + " left join tCustomer tc on tc.Code = tdd.CustCode "
				   + " left join tBuilder tb on tdd.BuilderCode = tb.Code "
				   + " left join tEmployee te on te.Number = tdd.DesignMan "
				   + " left join tXTDM tx1 on tx1.ID='DLAYOUT' and tx1.CBM = tdd.Layout "
				   + " left join tXTDM tx2 on tx2.ID='DDESIGNSTL' and tx2.CBM = tdd.DesignSty "
				   + " where tdd.Expired='F' and tdd.IsPushCust='1' and tdd.No=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getDesignDemoDetailPhotos(String no){
		String sql = " select photoName src,No,IsSendYun from tDesignDemoPic where No=? ";
		return this.findBySql(sql, new Object[]{no});
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAPIKey(String apiKey){
		System.out.println(apiKey);
		String sql = "select * from tAPIKey where APIKey=? and Expired='F' ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{apiKey});
		System.out.println(list.size());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}

