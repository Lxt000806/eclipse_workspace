package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ResrCustEvt;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustExcelFailed;
import com.house.home.entity.design.ResrCustPool;

@SuppressWarnings("serial")
@Repository
public class ResrCustDao extends BaseDao{
	@Autowired
	private HttpServletRequest request;
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ResrCust resrCust) {
		UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Object> list = new ArrayList<Object>();

		String sql = "select *,RecentlyConcat lastUpdateTypeDescr from ( select a.addrProperty,a.Code,a.builderCode,a.Descr,d.desc2 departmenr2descr," 
				+ " case when a.Area > 0 then a.Address+ '('+convert(varchar,a.Area)+'㎡)' else a.Address end Address, ";
		if(StringUtils.isNotBlank(resrCust.getAppType())){//erp电话加密，app不加密
			sql+=" a.Mobile1,a.Mobile2, ";
		}else{
			sql += " case when isnull(a.Mobile1,'')<>'' then left(a.Mobile1,3)+'****'+right(a.Mobile1,4) else a.Mobile1 end Mobile1," 
				 + " case when isnull(a.Mobile2,'')<>'' then left(a.Mobile2,3)+'****'+right(a.Mobile2,4) else a.Mobile2 end Mobile2,";
		}
		sql+=" a.BusinessMan,a.CrtDate,e.department2,a.LastUpdatedBy,a.LastUpdate,e.nameChi businessmandescr,b.descr builderDescr,a.status,x1.NOTE statusDescr, " +
		" x2.NOTE CustKindDescr,x3.NOTE SourceDescr,x4.NOTE CustResStatDescr,case when o.IsHideChannel ='1' and a.CustResStat in ('0','2')then '*' else cn.Descr end NetChanelDescr," +
		" f.Descr RegionDescr,e2.NameChi CrtCzyDescr, "+
		" case when h.ConDay is null then '尚未联系' when h.ConDay=0 then '今天联系' when h.ConDay>0 and h.ConDay<=30 then cast(h.ConDay as nvarchar(10))+'天前联系' else '超过30天未联系' end RecentlyConcat, "+
		" g2.Desc2 CrtCzyDept,a.Remark,a.Expired,a.ActionLog,a.CustResStat,a.DispatchDate,isnull(h.ConDay,-1)ConDay, h.RecentlyConDate," +
		" case when CONVERT(nvarchar(100), h.RecentlyConDate, 102) = CONVERT(nvarchar(100), GETDATE(), 102) then '今天' when " +
		"	DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102)) = 1 then '1天前'" +
		" when DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))>1 and DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))<=6 then cast(DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102)) as nvarchar)+'天前'" +
		" when DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102)) >6 and DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))<=30 then cast(DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))/7 as nvarchar)+'周前'" +
		" when DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))>28 and DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))<=365 then cast(DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))/30 as nvarchar)+'个月前' " +
		" when DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))>=365 then cast(DateDiff(d,CONVERT(nvarchar(100), h.RecentlyConDate, 102),CONVERT(nvarchar(100), GETDATE(), 102))/365 as nvarchar)+'年前' end RecentlyConDateDescr," +
		" i.MeasureDate,i.VisitDate,i.SetDate,i.SignDate,i.CustAddress,i.CustStatusDescr,j.Remarks,x6.NOTE ConstructTypeDescr,i.HouseTypeDescr,i.DesignManDescr,j.NextConDate, "+
		" case when isnull(k.ResrCustTagInfo,'')<>'' then left(k.ResrCustTagInfo,len(k.ResrCustTagInfo)-1) else '' end TagInfo,m.NameChi ShareCZYDescr,a.ShareCZY,a.Department," +
		" o.Type poolType,o.DispatchRule "+
		" from tResrCust a " +
		" left join tEmployee e on e.Number=a.BusinessMan " +
		" left join tBuilder b on b.code= a.builderCode " +
		" left join tDepartment2 d on d.code=e.department2 " +
		" left join tRegion f on a.RegionCode=f.Code "+
		" left join tEmployee e2 on e2.Number=a.CrtCzy "+
		" left join tDepartment g on e.Department=g.Code "+
		" left join tDepartment g2 on e2.Department=g2.Code "+
		" left join ("+
		"    select ResrCustCode,datediff(dd,max(ConDate),getdate()) ConDay,max(ConDate) RecentlyConDate,max(case when Type='3' "+
		" 		then PK else null end) PK " + 
		" 	 from tCustCon "+ 
		"    where Type in ('1','3') and ResrCustCode is not null ";
		if(!"1".equals(resrCust.getHasConAuth())){
			sql+=" and ConMan=? ";
			list.add(resrCust.getCzybh());
		}
		sql += "    group by ResrCustCode "+
		" ) h on a.Code=h.ResrCustCode "+
		" left join tXTDM x1 on x1.CBM = a.status and x1.Id='RESRCUSTSTS'" +
		" left join tXTDM x2 on x2.CBM = a.CustKind and x2.Id='CUSTKIND' "+
		" left join tXTDM x3 on x3.CBM = a.Source and x3.Id='CUSTOMERSOURCE' "+
		" left join tXTDM x4 on x4.CBM = a.CustResStat and x4.Id='CUSTRESSTAT' "+
		" left join tCustNetCnl cn on cn.Code = a.NetChanel "+
		" left join tXTDM x6 on x6.CBM = a.ConstructType and x6.Id='CUSTCLASS' "+
		" left join ( "+
		"  select a.ResrCustCode,b.MeasureDate,b.VisitDate,b.SetDate,b.SignDate," +
		"  a.CustCode,b.Address CustAddress,c.NOTE CustStatusDescr,e.NOTE HouseTypeDescr,f.NameChi DesignManDescr "+
		"  from tResrCustMapper a  "+
		"  left join tCustomer b on a.CustCode=b.Code "+
		"  left join tXTDM c on b.Status=c.CBM and c.Id='CUSTOMERSTATUS' "+
		"  left join tBuilder d on d.Code=b.BuilderCode "+
		"  left join tXTDM e on d.HouseType=e.CBM and e.Id='HOUSETYPE' "+
		"  left join tEmployee f on f.Number=b.DesignMan "+
		"  where PK in ( "+
		" 	 select max(in_a.PK)PK "+
		" 	 from tResrCustMapper in_a  "+
		" 	 inner join tCustomer in_b on in_a.CustCode=in_b.Code "+
		" 	 group by in_a.ResrCustCode "+
		"  ) "+
		" )i on a.Code=i.ResrCustCode " +
		" left join tCustCon j on h.PK = j.PK " +
		" left join ( " +
		" 	select ResrCustCode, " +
		" 	(select b.Descr+ '/' + c.Color+ ','  " +
		" 	from tCustTagMapper a " +
		" 	left join tCustTag b on a.TagPK = b.PK " +
		" 	left join tCustTagGroup c on b.TagGroupPK = c.PK " +
		" 	where a.ResrCustCode=a1.ResrCustCode " +
		" 	for xml path('')  ) as ResrCustTagInfo " +
		" 	from tCustTagMapper a1 " +
		" 	left join tCustTag b1 on a1.TagPK = b1.PK " +
		" 	left join tCustTagGroup c1 on b1.TagGroupPK = c1.PK " +
		"   left join tResrCust d1 on a1.ResrCustCode = d1.Code " +
		"   left join tResrCustPool e1 on d1.ResrCustPoolNo = e1.No " +
		"   where (e1.IsVirtualPhone = '0' or d1.CustResStat not in('0','2') or (d1.CustResStat in ('0','2') and a1.LastUpdatedBy = ? )) " +
		" 	group by ResrCustCode " +
		" ) k on a.Code=k.ResrCustCode " +
/*		" left join ( " +//意向客户标签，目前没有这个功能，先去掉
		" 	select CustCode, " +
		
		" 	(select b.Descr+ '/' + c.Color+ ','  " +
		" 	from tCustTagMapper a " +
		" 	left join tCustTag b on a.TagPK = b.PK " +
		" 	left join tCustTagGroup c on b.TagGroupPK = c.PK " +
		" 	where a.CustCode=a1.CustCode " +
		" 	for xml path('')) as CustTagInfo " +
		" 	from tCustTagMapper a1 " +
		" 	left join tCustTag b1 on a1.TagPK = b1.PK " +
		" 	left join tCustTagGroup c1 on b1.TagGroupPK = c1.PK " +
		" 	group by a1.CustCode " +
		" ) l on i.CustCode=l.CustCode " +*/
		" left join tEmployee m on a.ShareCZY=m.Number "+
		" left join tDepartment n on a.Department=n.Code " +
		" left join tResrCustPool o on o.No = a.ResrCustPoolNo "+
		" where 1=1 ";
		
		list.add(resrCust.getCzybh());
		if("2".equals(resrCust.getType())){//团队客户管理客户权限控制
			sql+=" and a.PublicResrLevel='0' ";
			if("1".equals(resrCust.getCustRight())){
				sql += " and a.BusinessMan = ? ";
				list.add(resrCust.getCzybh());
			} else if("2".equals(resrCust.getCustRight())){
				sql+=" and (exists ( select 1 from tResrCustPoolEmp in_a where in_a.czybh = ? and in_a.ResrCustPoolNo = a.ResrCustPoolNo and in_a.Type='0') " ;
				String custRightsql = SqlUtil.getCustRightByCzy(uc, "a.BusinessMan", "g.Path");
				if(StringUtils.isNotBlank(custRightsql)) {
					sql += " or ( 1=1 " + custRightsql + ")";
				}
				sql+=" )";
				list.add(resrCust.getCzybh());
			} else if(!"3".equals(resrCust.getCustRight())){
				sql += " and 1 <> 1 ";
			}
		}else if("3".equals(resrCust.getType())){//部门公海权限控制，公海等级为1
			sql+="and (( a.PublicResrLevel='1' ";
			if("1".equals(uc.getCustRight())){//查看本人权限，可查看与操作员同二级部门的，或者只有一级部门且跟操作员同一级部门
				sql+="and ( " +
						"  exists (" +
						"    select 1 from tEmployee in_a " +
						"    left join tDepartment2 in_b on in_a.Department2=in_b.Code " +
						"    where in_b.Department=a.Department and in_a.Number=? " +
						"  ) " +
						" or " +
						"  exists (" +
						"    select 1 from tEmployee in_c " +
						"    where in_c.Department=a.Department and in_c.Number=? " +
						"    and isnull(in_c.Department2,'')='' and isnull(in_c.Department3,'')='' " +
						"  ) " +
						")";
				list.add(resrCust.getCzybh());
				list.add(resrCust.getCzybh());
			}else if("2".equals(uc.getCustRight())){//查看本部门权限，可查看本部门+权限部门 （均包含子部门）
				sql+="and (" +
						 " exists(" +
						 "    select 1 from (  "+
						 "       select case when in_dd.Department is not null then in_dd.Department when in_cc.Department is not null then in_cc.Department   "+ 
						 "		 else in_bb.Department end Department   "+
						 "		 from tCzyDept in_aa    "+
						 "		 left join tDepartment1 in_bb on in_aa.Department1=in_bb.Code  "+
						 "		 left join tDepartment2 in_cc on in_aa.Department2=in_cc.Code  "+
						 "		 left join tDepartment3 in_dd on in_aa.Department3=in_dd.Code  "+
						 "		 where CZYBH=?    "+
						 " 	 )in_a  where n.Path like '%'+in_a.Department+'%' " +
						 " ) "+
						 " or " +
						 " exists(" +
						 "    select 1 from tEmployee in_b " +
						 "    left join tDepartment in_c on in_b.Department=in_c.Code " +
						 "    where in_b.Number=? and n.Path like '%'+in_b.Department+'%'" +
						 " ) " +
						 ")";
				list.add(resrCust.getCzybh());
				list.add(resrCust.getCzybh());
			}
			sql+=") or(exists ("+
					" select 1 from tResrCust in_a " +
	    			" left join tResrCustPool in_b on in_b.No = in_a.ResrCustPoolNo" +
	    			" left join tResrCustPoolEmp in_c on in_c.ResrCustPoolNo = in_b.No" +
	    			" where 1=1 and in_a.Code = a.code"+
	    			" and ((in_b.ReceiveRule = '0') " +
	    			" or (in_c.CZYBH = ? and in_b.ReceiveRule = '2' and in_c.Type = '0') or (in_c.CZYBH = ? and in_b.ReceiveRule = '1' ) ) " +
	    			" and in_a.ResrCustPoolNo is not null and in_a.ResrCustPoolNo <> '') and isnull(a.BusinessMan ,'')= '' ) )";
			list.add(resrCust.getCzybh());
			list.add(resrCust.getCzybh());
		}else{//资源客户管理客户权限控制 （包含APP）
			if("1".equals(resrCust.getResrType())){
				sql+=" and (a.ShareCZY=? and PublicResrLevel='0' ) ";
				list.add(resrCust.getCzybh());
			}else{
				sql+=" and (a.BusinessMan=? and PublicResrLevel='0'  ";
				if("1".equals(resrCust.getHasCreateAuth())){
					sql+=" or CrtCzy=? ";
					list.add(resrCust.getCzybh());
				}
				sql+=" ) ";
				list.add(resrCust.getCzybh());
			}

		}
		
		if(StringUtils.isNotBlank(resrCust.getBuilderCode())){
			sql+=" and a.builderCode = ?";
			list.add(resrCust.getBuilderCode());
		}
		if(StringUtils.isNotBlank(resrCust.getAddress())){
			sql+=" and a.address like ?";
			list.add("%"+resrCust.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(resrCust.getStatus())){
			sql+=" and a.status =?";
			list.add(resrCust.getStatus());
		}
		if (resrCust.getDateFrom()!= null) {
			sql += " and a.crtDate>=convert(varchar(10),?,120) ";
			list.add(resrCust.getDateFrom());
		}
		if (resrCust.getDateTo() != null){
			sql += " and a.crtDate< convert(varchar(10),dateadd(dd,1,?),120) ";
			list.add(resrCust.getDateTo());
		}
		if(StringUtils.isNotBlank(resrCust.getBusinessMan())){
			sql+=" and a.businessMan = ?";
			list.add(resrCust.getBusinessMan());
		}
		if(StringUtils.isNotBlank(resrCust.getBusinessManDept())){
			sql+=" and e.Department = ?";
			list.add(resrCust.getBusinessManDept());
		}
		if(StringUtils.isNotBlank(resrCust.getCrtCzy())){
			sql+=" and a.CrtCzy = ?";
			list.add(resrCust.getCrtCzy());
		}
		if(StringUtils.isNotBlank(resrCust.getDescr())){
			sql+=" and a.Descr like ?";
			list.add("%"+resrCust.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(resrCust.getMobile1())){
			sql+=" and a.Mobile1 like ?";
			list.add("%"+resrCust.getMobile1()+"%");
		}
		if(StringUtils.isNotBlank(resrCust.getCustResStat())){
			sql+=" and a.CustResStat in ('" + resrCust.getCustResStat().replaceAll(",", "','") + "')";
		}
		if(StringUtils.isNotBlank(resrCust.getConstructType())){
			sql+=" and a.ConstructType = ?";
			list.add(resrCust.getConstructType());
		}
		if(StringUtils.isNotBlank(resrCust.getSource())){
			sql+=" and a.Source = ?";
			list.add(resrCust.getSource());
		}
		
		if (StringUtils.isNotBlank(resrCust.getExtraOrderNo())) {
            sql += " and a.ExtraOrderNo like ?";
            list.add("%" + resrCust.getExtraOrderNo() + "%");
        }
		
		if (StringUtils.isBlank(resrCust.getExpired()) || "F".equals(resrCust.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(resrCust.getRecentlyConcat())){//最近联系时间改为多选 20200112 by cjg
			String sql_rc=" and (";
			if(resrCust.getRecentlyConcat().indexOf("1")!=-1){
				sql_rc+=" h.ConDay is null ";
			}
			if(resrCust.getRecentlyConcat().indexOf("2")!=-1){
				if(!" and (".equals(sql_rc)){//不是最初的sql_rc，要加or
					sql_rc+="or";
				}
				sql_rc+=" h.ConDay <=3 ";
			}
			if(resrCust.getRecentlyConcat().indexOf("3")!=-1){
				if(!" and (".equals(sql_rc)){
					sql_rc+="or";
				}
				sql_rc+=" h.ConDay>3 and h.ConDay<=7 ";
			}
			if(resrCust.getRecentlyConcat().indexOf("4")!=-1){
				if(!" and (".equals(sql_rc)){
					sql_rc+="or";
				}
				sql_rc+=" h.ConDay>7 and h.ConDay <=30 ";
			}
			if(resrCust.getRecentlyConcat().indexOf("5")!=-1){
				if(!" and (".equals(sql_rc)){
					sql_rc+="or";
				}
				sql_rc+=" h.ConDay >30 ";
			}
			if(resrCust.getRecentlyConcat().indexOf("6")!=-1){
				if(!" and (".equals(sql_rc)){
					sql_rc+="or";
				}
				sql_rc+=" h.ConDay <=1 ";
			}
			sql_rc+=" )";
			sql+=sql_rc;
		}
		if(StringUtils.isNotBlank(resrCust.getLastUpdateType())){
			if("6".equals(resrCust.getLastUpdateType())){
				sql+=" and h.ConDay <= 1 ";
			}else if("2".equals(resrCust.getLastUpdateType())){
				sql+=" and h.ConDay <= 3 ";
			}else if("3".equals(resrCust.getLastUpdateType())){
				sql+=" and h.ConDay <= 7 ";
			}else if("4".equals(resrCust.getLastUpdateType())){
				sql+=" and h.ConDay <= 30 ";
			}else{	//自定义时间区间 modify by zb on 20200411
				if (null != resrCust.getContactBeginDate()) {
					sql += " and h.RecentlyConDate >= ? ";
					list.add(resrCust.getContactBeginDate());
				}
				if (null != resrCust.getContactEndDate()) {
					sql += " and h.RecentlyConDate <= ? ";
					list.add(DateUtil.endOfTheDay(resrCust.getContactEndDate()));
				}
			}
		}
		if(StringUtils.isNotBlank(resrCust.getNotContLastUpdateType())){	//最近未联系
			if("1".equals(resrCust.getNotContLastUpdateType())){
				sql+=" and h.ConDay is null ";
			}else if("6".equals(resrCust.getNotContLastUpdateType())){
				sql+=" and h.ConDay >= 1 ";
			}else if("2".equals(resrCust.getNotContLastUpdateType())){
				sql+=" and h.ConDay >= 3 ";
			}else if("3".equals(resrCust.getNotContLastUpdateType())){
				sql+=" and h.ConDay >= 7 ";
			}else if("4".equals(resrCust.getNotContLastUpdateType())){
				sql+=" and h.ConDay >= 30 ";
			}else{
				if (null != resrCust.getNotContactBeginDate()) {
					sql += " and h.RecentlyConDate < ? ";
					list.add(resrCust.getNotContactBeginDate());
				}
				if (null != resrCust.getNotContactEndDate()) {
					sql += " and h.MaxConDate > ? ";
					list.add(DateUtil.endOfTheDay(resrCust.getNotContactEndDate()));
				}
			}
		}
		
		if(StringUtils.isNotBlank(resrCust.getDispatchDateType())){	//派单时间
			if("1".equals(resrCust.getDispatchDateType())){
				sql+=" and DATEDIFF(day,a.DispatchDate,getdate())=0 ";
			}else if("2".equals(resrCust.getDispatchDateType())){
				sql+=" and (DATEDIFF(day,a.DispatchDate,getdate())>=-3 and DATEDIFF(day,a.DispatchDate,getdate())<=3)";
			}else if("3".equals(resrCust.getDispatchDateType())){
				sql+=" and (DATEDIFF(day,a.DispatchDate,getdate())>=-7 and DATEDIFF(day,a.DispatchDate,getdate())<=7) ";
			}else{
				if (null != resrCust.getDispatchBeginDate()) {
					sql += " and a.DispatchDate < ? ";
					list.add(resrCust.getDispatchBeginDate());
				}
				if (null != resrCust.getDispatchEndDate()) {
					sql += " and a.DispatchDate > ? ";
					list.add(DateUtil.endOfTheDay(resrCust.getDispatchEndDate()));
				}
			}
		}
		if (resrCust.getDispatchDateFrom() != null){
			sql += " and a.DispatchDate>= ? ";
			list.add(resrCust.getDispatchDateFrom());
		}
		if (resrCust.getDispatchDateTo() != null){
			sql += " and a.DispatchDate< ? ";
			list.add(DateUtil.addInteger(resrCust.getDispatchDateTo(),Calendar.DATE, 1));
		}
		if(StringUtils.isNotBlank(resrCust.getCodes())){
			sql+=" and a.Code in "+"('"+resrCust.getCodes().replaceAll(",", "','")+"')";
		}
		if (resrCust.getMeasureDateFrom()!= null) {
			sql += " and i.MeasureDate>= ? ";
			list.add(resrCust.getMeasureDateFrom());
		}
		if (resrCust.getMeasureDateTo() != null){
			sql += " and i.MeasureDate< ? ";
			list.add(DateUtil.addInteger(resrCust.getMeasureDateTo(),Calendar.DATE, 1));
		}
		if (resrCust.getVisitDateFrom()!= null) {
			sql += " and i.VisitDate>= ? ";
			list.add(resrCust.getVisitDateFrom());
		}
		if (resrCust.getVisitDateTo() != null){
			sql += " and i.VisitDate< ? ";
			list.add(DateUtil.addInteger(resrCust.getVisitDateTo(),Calendar.DATE, 1));
		}
		if (resrCust.getSetDateFrom()!= null) {
			sql += " and i.SetDate>= ? ";
			list.add(resrCust.getSetDateFrom());
		}
		if (resrCust.getSetDateTo() != null){
			sql += " and i.SetDate< ? ";
			list.add(DateUtil.addInteger(resrCust.getSetDateTo(),Calendar.DATE, 1));
		}
		if (resrCust.getSignDateFrom()!= null) {
			sql += " and i.SignDate>= ? ";
			list.add(resrCust.getSignDateFrom());
		}
		if (resrCust.getSignDateTo() != null){
			sql += " and i.SignDate< ? ";
			list.add(DateUtil.addInteger(resrCust.getSignDateTo(),Calendar.DATE, 1));
		}
		if(StringUtils.isNotBlank(resrCust.getCustKind())){
			sql += " and a.CustKind in " + "('"+resrCust.getCustKind().replaceAll(",", "','")+"')";
		}
		if (resrCust.getNextConDateFrom()!= null) {
			sql += " and j.NextConDate>= ? ";
			list.add(resrCust.getNextConDateFrom());
		}
		if (resrCust.getNextConDateTo() != null){
			sql += " and j.NextConDate< ? ";
			list.add(DateUtil.addInteger(resrCust.getNextConDateTo(),Calendar.DATE, 1));
		}
		
		if(StringUtils.isNotBlank(resrCust.getHaveCustTag())){
			if("1".equals(resrCust.getHaveCustTag())){
				sql += " and exists(select 1 from tCustTagMapper in_a " 
					+" where (a.Code=in_a.ResrCustCode or i.CustCode=in_a.CustCode " 
					+" and isnull(i.CustCode,'')<>'' ))";
			}else{
				sql += " and not exists(select 1 from tCustTagMapper in_a " 
					+" where (a.Code=in_a.ResrCustCode or i.CustCode=in_a.CustCode " 
					+" and isnull(i.CustCode,'')<>'' ))";
			}
		}
		
		if(StringUtils.isNotBlank(resrCust.getCustTag())){
			sql += " and exists(select 1 from tCustTagMapper in_a " 
				+" where a.Code=in_a.ResrCustCode and in_a.TagPK in " 
				+"('"+resrCust.getCustTag().replaceAll(",", "','")+"') )";
		}
		if(StringUtils.isNotBlank(resrCust.getShareCzy())){
			sql+=" and a.ShareCZY = ?";
			list.add(resrCust.getShareCzy());
		}
		if(StringUtils.isNotBlank(resrCust.getResrCustPoolNo())){
			sql+=" and a.ResrCustPoolNo in ("+SqlUtil.resetStatus(resrCust.getResrCustPoolNo())+")";
		}
		if(StringUtils.isNotBlank(resrCust.getNetChanel())) {
			sql += " and a.NetChanel = ? ";
			list.add(resrCust.getNetChanel());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			if("2".equals(resrCust.getType())){
				sql += ")a  order by a.ConDay asc ";
			}else{
				sql += ")a  order by a.CrtDate desc ";
			}
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public ResrCust getByAddress(String address) {
		String hql = "from ResrCust where address=?";
		List<ResrCust> list = this.find(hql, new Object[] { address });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取映射的客户编号
	 * @author cjg
	 * @date 2019-12-26
	 * @param resrCustCode
	 * @return
	 */
	public String getMapperCustCode(String resrCustCode) {
		String sql = "select CustCode from tResrCustMapper where ResrCustCode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{resrCustCode});
		if (list != null && list.size() > 0) {
			return list.get(0).get("CustCode").toString();
		}
		return null;
	}
	
	public Page<Map<String, Object>> findResrCustMapperPageBySql(
			Page<Map<String, Object>> page, ResrCust resrCust) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select c.address,x.note statusDescr from tResrCust a " +
				" inner join tResrCustMapper b on b.ResrCustCode = a.Code" +
				" left join tCustomer c on c.code= b.CustCode " +
				" left join tXtdm x on x.id='CUSTOMERSTATUS' and x.cbm = c.status " +
				" where 1=1  " ;
		if(StringUtils.isNotBlank(resrCust.getCode())){
			sql+=" and a.code = ? ";
			list.add(resrCust.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.statusDescr desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public int getConRemMinLen(){
		String sql=" select qz from tXtcs where id='ConRemMinLen' ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			return Integer.parseInt(list.get(0).get("qz").toString());
		}
		return 0;
	}
	
	/**
	 * 查询跟踪记录
	 * @author cjg
	 * @date 2019-12-26
	 * @param page
	 * @param resrCust
	 * @return
	 */
	public Page<Map<String, Object>> goConJqGrid(Page<Map<String, Object>> page, ResrCust resrCust) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.ConDate,a.Remarks,c.NameChi ConManDescr," 
					+"case when isnull(a.CustCode,'')='' and isnull(b.CustCode,'')='' then  isnull(b.ResrCustCode,a.ResrCustCode) "
					+"else case when isnull(a.CustCode,'')<>'' then a.CustCode else b.CustCode end end CustCode, "
					+"a.NextConDate,d.NOTE TypeDescr,e.NOTE ConWayDescr,isnull(a.ConDuration,0) ConDuration, "
					+"case when f.Status = '1' then '/' + substring(f.Name, 0, 6) + '/' + f.Name else '' end callRecordPath "
					+"from tCustCon a  "
					+"left join (" 
					+"  select max(PK) PK,ResrCustCode from tResrCustMapper group by ResrCustCode " 
					+") g on a.ResrCustCode=g.ResrCustCode " 
					+"left join tResrCustMapper b on b.PK=g.PK "
					+"left join tEmployee c on a.ConMan=c.Number "
					+"left join tXTDM d on a.Type=d.CBM and d.ID='CUSTCONTYPE' "
					+"left join tXTDM e on a.ConWay=e.CBM and e.ID='CONWAY' "
					+"left join tCallRecord f on f.PK = a.CallRecordPK and f.status = '1' "
					+"where a.ResrCustCode=?";
		list.add(resrCust.getCode());
		if(StringUtils.isNotBlank(resrCust.getType())){
			sql+=" and a.Type in ("+SqlUtil.resetStatus(resrCust.getType())+")";
		}
		if(!"1".equals(resrCust.getHasConAuth())){
			sql+=" and a.ConMan=?";
			list.add(resrCust.getCzybh());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a  order by a.ConDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 查找重复客户
	 * @author cjg
	 * @date 2020-2-21
	 * @return
	 */
	public Map<String,Object> findExsitsCust(ResrCust resrCust,String crtCzy) {
		String sql = "select Descr,BusinessMan "
			+"from tResrCust a " 
			+"left join tEmployee b on a.CrtCzy=b.Number " 
			+"left join tDepartment c on b.Department=c.Code "
			+"where a.Mobile1=? and a.Address=? and c.Path like '%'+(select Department from tEmployee where Number=?)+'%'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{resrCust.getMobile1(),resrCust.getAddress(),crtCzy});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 修改创建人列表
	 * @author cjg
	 * @date 2020-2-24
	 * @param page
	 * @param resrCust
	 * @return
	 */
	public Page<Map<String, Object>> goUpdateCrtJqGrid(Page<Map<String, Object>> page, ResrCust resrCust) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.LastUpdate,a.Code,a.CrtCZY,a.CrtDate,x1.NOTE CustResStat,x2.NOTE CustStatus,a.Descr," 
				+"a.Address,c.Address custAddress,c.CrtDate custCrtDate,e.Desc1 CrtCzyDept,f.NameChi CrtCZYDescr "
				+"from tResrCust a  "
				+"left join tResrCustMapper b on a.Code=b.ResrCustCode " 
				+"left join tCustomer c on b.CustCode=c.Code "
				+"left join tXTDM x1 on a.CustResStat=x1.CBM and x1.ID='CUSTRESSTAT' "
				+"left join tXTDM x2 on c.Status=x2.CBM and x2.ID='CUSTOMERSTATUS' "
				+"left join tDepartment e on a.CrtCzyDept=e.Code "
				+"left join tEmployee f on a.CrtCzy=f.Number "
				+"where 1=1 ";
		if(StringUtils.isNotBlank(resrCust.getCustResStat())){
			sql+="and a.CustResStat in ("+SqlUtil.resetStatus(resrCust.getCustResStat())+") ";
		}else{
			sql+="and a.CustResStat in ('0','1','3')";
		}
		if(StringUtils.isNotBlank(resrCust.getStatus())){
			sql+="and c.Status in ("+SqlUtil.resetStatus(resrCust.getStatus())+") ";
		}else{
			sql+="and ((c.Status in('1','2','3') and b.PK is not null)or b.PK is null)";
		}
		if(StringUtils.isNotBlank(resrCust.getCrtCzy())){
			sql+="and a.CrtCzy=?";
			list.add(resrCust.getCrtCzy());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a  order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 修改创建人
	 * @author cjg
	 * @date 2020-2-24
	 * @param resrCust
	 */
	public void doUpdateCrtCzy(ResrCust resrCust) {
		String sql = " update tResrCust set CrtCzy=?,LastUpdate=getdate(),ActionLog='EDIT',LastUpdatedBy=? where Code in ("+SqlUtil.resetStatus(resrCust.getCodes())+")";
		this.executeUpdateBySql(sql, new Object[] {resrCust.getCrtCzy(),resrCust.getLastUpdatedBy()});
	}
	
	
	/**
	 * 查看失败任务列表
	 * @author cjg
	 * @date 2020-2-25
	 * @param page
	 * @param resrCust
	 * @return
	 */
	public Page<Map<String, Object>> goFailedExcelJqGrid(Page<Map<String, Object>> page, ResrCustExcelFailed resrCustExcelFailed) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.FileRows,'第'+cast(a.FileRows as nvarchar)+'行' FileRowsDescr,left(b.Emps,len(b.Emps)-1)Emps,a.FailedReason,a.ExistsCustName, " 
				+"a.FailedFieldContent,a.FailedFieldName,b.LastUpdate,c.NameChi ExistsBusinessMan " 
				+"from tResrCustExcelFailed a " 
				+"left join ( " 
				+"  select pk,LastUpdate,LastUpdatedBy, "
				+"  (select NameChi+',' from tEmployee " 
				+"  where emps like '%'+Number+'%' " 
				+"  for xml path('')) as Emps "
				+"  from tExcelTask " 
				+") b on a.TaskPK=b.PK  "
				+"left join tEmployee c on a.ExistsBusinessMan=c.Number "
				+"where a.TaskPK=?";
		list.add(resrCustExcelFailed.getTaskPK());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a  order by a.FileRows ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 根据项目名称查编号
	 * @author cjg
	 * @date 2020-2-25
	 * @param page
	 * @param resrCust
	 * @return
	 */
	public String getBuilderCode(String buildDescr) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select Code from tBuilder where Descr=?";
		list.add(buildDescr);
		List<Map<String, Object>> resultList=this.findBySql(sql, list.toArray());
		if(resultList!=null && resultList.size()>0){
			return resultList.get(0).get("Code").toString();
		}
		return "";
	}
	
	/**
	 * 是否关联意向客户
	 * @param code
	 * @return
	 */
	public String hasCustCode(String code) {
		String sql = "select 1 hasCustCode from tResrCustMapper where ResrCustCode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (null != list && list.size()>0) {
			return list.get(0).get("hasCustCode").toString();
		}
		return "";
	}	
	
	/**
	 * 关联意向客户保存
	 * @param resrCust
	 * @return
	 */
	public void doAddCustCode(ResrCust resrCust) {
		String sql = "delete from tResrCustMapper where ResrCustCode=? ";
		sql+=" insert into tResrCustMapper(ResrCustCode,CustCode,LastUpdate,LastUpdatedBy,Expired,ActionLog)"
			+" values(?,?,getdate(),?,'F','ADD')";
		this.executeUpdateBySql(sql, new Object[]{resrCust.getCode(),resrCust.getCode(),resrCust.getCustCode(),resrCust.getLastUpdatedBy()});
	}	
	
	/**
	 * 跟单员领取资源客户数(非转意向)
	 * @param code
	 * @return
	 */
	public Integer getResrCustNum(String code) {
		String sql = "select 1 from tResrCust where BusinessMan=? and CustResStat not in('3','5','6','7','8') ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		return list.size();
	}	
	
	public Map<String, Object> getResrCustDetail(String code){
		String sql = "select a.*, b.NameChi CrtCzyDescr, c.NameChi BusinessManDescr, d.NOTE CustKindDescr, e.NOTE CustResStatDescr, "
				+" f.Descr BuilderDescr, g.Descr RegionDescr, h.Desc2 CrtCZYDeptDescr, j.Address custAddress, k.NOTE CustStatDescr, l.ChannelType, l.NeedMeasureDate, "
				+" m.IsVirtualPhone, m.IsHideChannel, m.DispatchRule, m.Descr ResrCustPoolDescr "
				+" from tResrCust a "
				+" left join tEmployee b on a.CrtCZY = b.Number "
				+" left join tEmployee c on a.BusinessMan = c.Number "
				+" left join tXTDM d on a.CustKind = d.CBM and d.ID = 'CUSTKIND' "
				+" left join tXTDM e on a.CustResStat = e.CBM and e.ID = 'CUSTRESSTAT' "
				+" left join tBuilder f on a.BuilderCode = f.Code "
				+" left join tRegion g on a.RegionCode = g.Code "
				+" left join tDepartment2 h on a.CrtCZYDept = h.Code "
				+" left join (select top 1 CustCode,ResrCustCode from tResrCustMapper where ResrCustCode = ? order by pk desc) i on a.Code = i.ResrCustCode "
				+" left join tCustomer j on i.CustCode = j.Code "
				+" left join tXTDM k on j.Status = k.CBM and k.ID = 'CUSTOMERSTATUS' "
				+" left join tCustNetCnl l on a.NetChanel = l.Code "
				+" left join tResrCustPool m on a.ResrCustPoolNo = m.No "
				+" where a.Code = ? ";
			List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code,code});
			if(list!=null && list.size()>0){
				return list.get(0);
			}
			return null;
	}

	/**
	 * 保存共享客户
	 * @param resrCust
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doShareCust(ResrCust resrCust) {
		Assert.notNull(resrCust);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pShareCust(?,?,?,?,?)}");
			call.setTimestamp(1, resrCust.getShareDate()==null?null:new Timestamp(resrCust.getShareDate().getTime()));
			call.setString(2, resrCust.getCzybh());
			call.setString(3, resrCust.getResrCustJson());	
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	/**
	 * 查询所有网络渠道
	 * 
	 * @return 所有未过期的网络渠道列表
	 */
    public List<Map<String, Object>> findAllNetChannels() {
        String sql = "select * from tCustNetCnl where Expired = 'F' ";

        List<Map<String, Object>> netChannels = findBySql(sql, new Object[] {});

        return netChannels != null ? netChannels : new ArrayList<Map<String, Object>>();
    }
    
    /**
	 * 来源
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findCustSource(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.CBM id,a.NOTE name from tXTDM a where Expired='F' and ID='CUSTOMERSOURCE' order by a.DispSeq";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 网络渠道
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findCustCnl(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code id,a.Descr name  from tCustNetCnl a where a.expired='F'  ";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and a.CustSource=?";
			list.add((String) param.get("pCode"));
		}
		sql += " order by a.DispSeq  ";
		return this.findBySql(sql, list.toArray());
	}
	
    public List<Map<String, Object>> findNetChannelsBySource(String source) {
        String sql = "select * from tCustNetCnl where Expired = 'F' and CustSource = ? ";

        List<Map<String, Object>> netChannels = findBySql(sql, new Object[] {source});

        return netChannels != null ? netChannels : new ArrayList<Map<String, Object>>();
    }
    
    public List<Map<String, Object>> getResrCustPoolList(ResrCustEvt evt) {
        String sql = " select a.No, a.Descr, a.Type, a.IsVirtualPhone, a.IsHideChannel, a.DispatchRule from tResrCustPool a "
        			+" where a.Expired = 'F' "
        			+" and (a.Type = '1' or (exists(select 1 from tResrCustPoolEmp where a.No = ResrCustPoolNo and Expired = 'F' and CZYBH = ?))) ";

        List<Map<String, Object>> resrCustPoolList = findBySql(sql, new Object[] {evt.getCzybh()});

        return resrCustPoolList != null ? resrCustPoolList : new ArrayList<Map<String, Object>>();
    }
    
    public String getDefaultResrCustPoolNo(ResrCustEvt evt) {
    	String sql = " select top 1 a.DefaultPoolNo from ( "
    			+" 	select DefaultPoolNo, 1 Seq from tCZYBM a "
    			+" 	where exists(select 1 from tResrCustPoolEmp in_a where a.DefaultPoolNo = in_a.ResrCustPoolNo and in_a.CZYBH = a.CZYBH and in_a.Expired = 'F') and a.CZYBH = ? "
    			+" 	union all "
    			+" 	select top 1 ResrCustPoolNo DefaultPoolNo, 2 Seq from tResrCustPoolEmp a "
    			+"  left join tResrCustPool b on a.ResrCustPoolNo = b.No "
    			+" 	where a.CZYBH = ? and a.Expired = 'F' "
    			+" 	order by b.Priority desc,b.No desc "
    			+" 	union all "
    			+"  select top 1 No DefaultPoolNo, 3 Seq from tResrCustPool a "
    			+"  where a.Type = '1' "
    			+"  order by Priority desc,No desc "
    			+" ) a "
    			+" order by a.Seq ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{evt.getCzybh(), evt.getCzybh()});
		if (list != null && list.size() > 0) {
			return list.get(0).get("DefaultPoolNo").toString();
		}
		return null;
    }
    
    public String getDefPoolNoByCzybm(String czybh) {
		
    	String sql = "select	case	when a.DefaultPoolNo is not null" +
    			"						and a.DefaultPoolNo <> '' then a.DefaultPoolNo" +
    			"				when a.MaxPoolNo is not null" +
    			"						and a.MaxPoolNo <> '' then a.MaxPoolNo" +
    			"				else a.No" +
    			"		end DefPoolNo" +
    			" from	(select	a.CZYBH, a.DefaultPoolNo, max(b.ResrCustPoolNo) MaxPoolNo, c.No" +
    			"		from tCZYBM a" +
    			"			left join (select max(b.Priority) Priority,a.CZYBH from tResrCustPoolEmp a " +
    			"				left join tResrCustPool b on b.No = a.ResrCustPoolNo" +
    			"				where a.CZYBH = ?  group by a.CZYBH) e on 1=1" +
    			"			left join tResrCustPoolEmp b on e.CZYBH = b.CZYBH" +
    			"			left join tResrCustPool d on d.No = b.ResrCustPoolNo" +
    			"			left join tResrCustPool c on c.Descr = '默认线索池'" +
    			"			where 	a.CZYBH = ?" +
    			"			group by a.CZYBH, a.DefaultPoolNo, c.No) a";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh, czybh});
		if(list!=null && list.size()>0 && list.get(0).get("DefPoolNo") != null){
			return list.get(0).get("DefPoolNo").toString();
		}
		return "";
	}
    
	 public boolean getCanReceiveCust(String codes, String czybh) {
		
    	String sql = " select 1 from tResrCust a " +
    			" where 1=1 and a.Code in ("+SqlUtil.resetStatus(codes)+")"+
    			" and a.BusinessMan is not null and a.businessMan <> '' ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list!=null && list.size()>0){
			return false;
		}
		return true;
	}
	 
	 public boolean hasPoolAuth(ResrCustPool resrCustPoolNo, String czybh){
		 String sql = "select * from tResrCustPool a" +
		 		" where (a.Descr = '默认线索池' or " +
		 		" exists (select 1 from tResrCustPoolEmp in_a where in_a.ResrCustPoolNo = a.No and in_a.CZYBH = ? ))" +
		 		" and a.No = ? and expired = 'F'";
			
			List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh, resrCustPoolNo});
			if(list!=null && list.size()>0){
				return true;
			}
		 
		 return false;
	 }
}
