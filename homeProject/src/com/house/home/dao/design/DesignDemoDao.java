package com.house.home.dao.design;
import java.beans.DesignMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.design.FixArea;
import com.sun.java_cup.internal.runtime.Scanner;
import com.taobao.api.domain.Area;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.ResolverUtil.IsA;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class DesignDemoDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DesignDemo designDemo,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from( select a.BuilderCode,a.No,b.Address,d.Descr,c.NameChi designdescr,x1.NOTE layoutDescr,x2.note styleDescr,a.Area,a.DesignRemark," +
				" Amount,x3.NOTE isPustDescr,a.LastUpdate,a.LastUpdatedBy,a.expired,a.custCode,a.actionLog from tDesignDemo a " +
				" left join tCustomer b on b.Code=a.CustCode " +
				" left join tEmployee c on c.Number=a.DesignMan" +
				" left join tBuilder d on d.Code=a.BuilderCode" +
				" left join tXTDM x1 on x1.CBM=a.Layout and x1.ID='DLAYOUT'" +
				" left join tXTDM x2 on x2.CBM=a.DesignSty and x2.ID='DDESIGNSTL'" +
				" left join tXTDM x3 on x3.CBM=a.IsPushCust and x3.ID='YESNO'" +
				" where 1=1 and "+
		 		SqlUtil.getCustRight(uc, "b", 0);
		 if(StringUtils.isNotBlank(designDemo.getIsPushCust())){
			 sql+=" and a.isPushCust = ? ";
			 list.add(designDemo.getIsPushCust());
		 }		
		 if(StringUtils.isNotBlank(designDemo.getAddress())){
			 sql+=" and b.address like ? ";
			 list.add("%"+designDemo.getAddress()+"%");
		 }
		 if(StringUtils.isNotBlank(designDemo.getBuilderCode())){
			 sql+=" and a.builderCode = ? ";
			 list.add(designDemo.getBuilderCode());
		 }
		 if(StringUtils.isNotBlank(designDemo.getDesignMan())){
			 sql+=" and a.designMan = ? ";
			 list.add(designDemo.getDesignMan());
		 }
		 if(StringUtils.isNotBlank(designDemo.getAreaArrange())){
			 if("1".equals(designDemo.getAreaArrange())){
				 sql+=" and a.area<60";
			 }else if("2".equals(designDemo.getAreaArrange())){
				 sql+=" and a.area<80 and a.area>= 60 ";
			 }else if("3".equals(designDemo.getAreaArrange())){
				 sql+=" and a.area<100 and a.area>=80 ";
			 }else if("4".equals(designDemo.getAreaArrange())){
				 sql+=" and a.area>=100 ";
			 }
		 }
		 if(StringUtils.isNotBlank(designDemo.getLayout())){
			 sql+=" and a.layOut = ? ";
			 list.add(designDemo.getLayout());
		 }
		 if (StringUtils.isBlank(designDemo.getExpired())
				|| "F".equals(designDemo.getExpired())) {
			sql += " and a.Expired='F' ";
		 }
		 if(StringUtils.isNotBlank(designDemo.getDesignSty())){
				sql += " and a.designSty in " + "('"+designDemo.getDesignSty().replaceAll(",", "','")+"')";
		 }
		 
		 if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += ") a order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += ") a order by  a.lastUpdate desc";
			}
		 
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDesignPic(Page<Map<String,Object>> page, DesignDemo designDemo,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.PK,a.No,a.PhotoName,a.LastUpdate,a.LastUpdatedBy from tDesignDemoPic a " +
				"" +
				"where 1=1 ";
		
		if(StringUtils.isNotBlank(designDemo.getNo())){
			sql+=" and a.no = ? ";
			list.add(designDemo.getNo());
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public void doDeleteDemo(String no,String custCode,String photoName) {
		String sql = "delete from tDesignDemoPic where no = ? and photoName = ? ";
		this.executeUpdateBySql(sql, new Object[]{no,photoName});
	}
	
	public void doDeleteAllDemo(String no) {
		String sql = "delete from tDesignDemoPic where no = ? ";
		this.executeUpdateBySql(sql, new Object[]{no});
	}

	public Map<String, Object> getQty(String builderCode){
		List<Object> param = new ArrayList<Object>();

		String sql =" select isnull(sum(case when a.status ='3' then 1 else 0 end ),0) orderNum " +
				"	,isnull(sum(case when a.status ='4' and ConfirmBegin is not null  then 1 else 0 end ),0)constructNum," +
				"	isnull(sum(case when a.status ='4' and a.signdate is not null and ConfirmBegin is null then 1 else 0 end ),0)notBeginNum," +
				"	isnull(sum(case when a.status ='5' and endcode = '3' then 1 else 0 end ),0) endNum  from tcustomer a " +
				" left join tCusttype b on b.Code= a.CustType" +
				" where b.IsAddAllInfo = '1' ";
		
		if(StringUtils.isNotBlank(builderCode)){
			sql+=" and a.builderCode = ?";
			param.add(builderCode);
		}else{
			sql+=" and 1=2 ";
		}
		List<Map<String, Object>> list = this.findBySql(sql, param.toArray());
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
	public Object getDesignDemoQty(String builderCode){
		List<Object> param = new ArrayList<Object>();
		
		String sql=" select count(1) designDemoNum from tDesignDemo a" +
				" left join tCustomer b on b.code = a.custCode " +
				" where a.IsPushCust = '1' and a.expired = 'F' ";
		if(StringUtils.isNotBlank(builderCode)){
			sql+=" and b.builderCode = ?";
			param.add(builderCode);
		}else{
			sql+=" and 1=2 ";
		}
		List<Map<String, Object>> list = this.findBySql(sql, param.toArray());
		if(list!=null && list.size()>0){
			return list.get(0).get("designDemoNum");
		}
		
		return null;
	}
	
	public Page<Map<String, Object>> getDesigmDemoList(
			Page<Map<String, Object>> page, String m_status) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select CustCode,STUFF(( SELECT ',' + PhotoName FROM tDesignDemoPic WHERE  " +
				"							  no = a.No FOR XML PATH('') ), 1, 1, '') from tDesignDemo a" +
				" left join tCustomer b on b.Code=a.CustCode " +
				" where 1=1  ";
		if(StringUtils.isNotBlank(m_status)){
			if("1".equals(m_status)){//意向
				sql+=" and b.status in ('1','2',3'')";
			}else if("2".equals(m_status)){//施工中
				sql+=" and b.status='4' ";
			}else if("3".equals(m_status)){//施工完成
				sql+=" and b.status='5' ";
			}
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getById(Page<Map<String, Object>> page,String id) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select '' selected,a.cbm value ,a.note descr from tXtdm a where a.id=? order by a.ibm";
		list.add(id);
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getShowBuilds(Page<Map<String, Object>> page, GetShowBuildsEvt evt) {
		List<Object> params = new ArrayList<Object>();

		String sql = " select tc.Gender, substring(tc.Descr, 1, 1) custDescr, tc.Address," +
				"        tc.Area, tc.BeginDate, tc.Code custCode, tppc.No prjProgConfirmNo," +
				"        isnull(tpi1.Descr,'无施工明细') prjItem1Descr, tppc.Date confirmDate " +
				" from tcustomer tc  " +
				"        left join ( select b.PrjItem,b.Date,b.CustCode,b.No from (select max(no) no from tPrjProgConfirm where isPass = '1' " +
				"						and isPushCust ='1' and prjItem in ('3','5','8','9','10','16') group by CustCode)a " +
				"					left join tPrjProgConfirm b on b.no =a.no " +
				"				) tppc on tc.Code = tppc.CustCode"+
				"        left join tPrjItem1 tpi1 on tpi1.Code = tppc.PrjItem" +
				"        left join tBuilder tb on tb.Code = tc.BuilderCode " +
				"        left join tCusttype c on c.code=tc.custtype " +
				" where 1=1 and c.IsAddAllInfo = '1' ";//and tppc.IsPushCust='1' and convert(varchar, tppc.LastUpdate, 112) <= convert(varchar, dateadd(day, -1, getdate()), 112) ";
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
			}else{
				startArea = 100.0;
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
			if("1".equals(evt.getM_status())){
				sql+=" and tc.status = '4' and tc.ConfirmBegin is not null ";
			} else if("2".equals(evt.getM_status())){
				sql+=" and tc.status = '5' and endCode = '3' ";
			}else {
				sql+=" and 1=1 ";
			}
		}
		sql += " order by tppc.Date desc ";

		return this.findPageBySql(page, sql, params.toArray());
	}
}
