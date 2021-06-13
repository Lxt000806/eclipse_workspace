package com.house.home.dao.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class IntProgMonDao extends BaseDao{
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		                                                                                                        
		String sql = "select * from ( select " ;
		if(StringUtils.isNotBlank(customer.getItemType2())){
					if("CG".equals(customer.getItemType2())){
						sql+=" ipd.remarks remarks, ";
					}else if("YG".equals(customer.getItemType2())){
						sql+=" ipdYg.remarks remarks, ";
					}
				}
				sql+="" +
				" b.address 楼盘,a.IntAppDate 衣柜下单时间 ,a.CupAppDate 橱柜下单时间,dateadd(d,d.SendDay,a.CupAppDate) 橱柜计划发货时间" +
				" ,dateadd(d,c.SendDay,a.IntAppDate) 衣柜计划发货时间,pj.date 橱柜出货申请日期,pj2.date 衣柜出货申请日期,cs.descr 橱柜供应商,ispl.descr 衣柜供应商,d2.desc2 集成部," +
				" e1.NameChi 项目经理 ,e.NameChi 集成设计师 ,smpp.ConfirmDate 饰面验收时间,dqpp.ConfirmDate 底漆验收时间  " +
				",d1.Desc2 department2Descr from tCustIntProg a " +
				" inner join tcustomer b on a.custcode=b.Code" +
				" left join tintsendDay c on c.MaterialCode= a.IntMaterial and c.ItemType12='31'" +
				" left join tintsendDay d on d.MaterialCode= a.CupMaterial and d.ItemType12='30'" + 
				" left join tsupplier cs on cs.code=a.CupSpl " +
				" left join tSupplier ispl on ispl.code=a.intspl " +
				" left join (select max(lastupdate) lastupdate ,CustCode from tIntProgDetail a where type='2' and IsCupboard='1' group  by  CustCode)ipd_a on ipd_a.custCode=a.custCode " +
				" left join tIntProgDetail ipd on ipd.lastupdate =ipd_a.lastupdate and ipd.custcode = ipd_a.CustCode  " +//橱柜备注
				" left join (select max(lastupdate) lastupdate ,CustCode from tIntProgDetail a where type='2' and IsCupboard<>'1' group  by  CustCode)ipd_b on ipd_b.custCode=a.custCode " +
				" left join tIntProgDetail ipdYg on ipdYg.lastupdate =ipd_b.lastupdate and ipdYg.custcode = ipd_b.CustCode  " +//衣柜备注
				" left join tCustStakeholder csh on csh.custcode=a.CustCode and ";
		if ("CG".equals(customer.getItemType2()) && StringUtils.isNotBlank(customer.getItemType2())) { //橱柜的，要显示橱柜设计师及其部门 modify by zb
			sql += " csh.role='61'" ;
		} else {
			sql += " csh.role='11'" ;
		}
		sql += " left join tEmployee e1 on e1.Number =b.ProjectMan " +
				" left join tDepartment2 d1 on e1.Department2 = d1.Code " +
				" left join temployee e on e.Number=csh.EmpCode " +
				" left join tDepartment2 d2 on d2.Code=e.Department2 " +
				" left join tprjprog dqpp on dqpp.PrjItem='19' and dqpp.CustCode=a.CustCode" +
				" left join tPrjProg smpp on smpp.PrjItem='8' and smpp.CustCode=a.CustCode " +
				" left  join ( select b.* from (  select max(Date) date, custcode,JobType from tPrjJob  group by custcode,JobType  ) a" +
				" left join tPrjJob b on b.date = a.date where b.Status in(2,3,4)) pj on pj.custcode = a.custcode and pj.JobType = '08'" +
				" left  join ( select b.* from (  select max(Date) date, custcode,JobType from tPrjJob  group by custcode,JobType  ) a" +
				" left join tPrjJob b on b.date = a.date and b.CustCode = a.custcode where b.Status in(2,3,4)) pj2 on pj2.custcode = a.custcode and pj2.JobType = '09'" +
				" where  1=1 and b.status='4' " +
				"  ";
		if(StringUtils.isNotBlank(customer.getItemType2())){
			if("CG".equals(customer.getItemType2())){
				sql+=" and a.CupAppDate is not null and a.CupAppDate<>''  " +
						" and (a.CupSendDate is null or a.CupSendDate = '') " +
						" and dateadd(d,isnull(d.SendDay,0),a.CupAppDate)<getdate() ";
				if(StringUtils.isNotBlank(customer.getCantInstall())){
					if("1".equals(customer.getCantInstall())){
						sql+=" and exists (select 1 from tIntProgDetail in_a " +
								"where in_a.type='2' and in_a.IsCupboard='1' and in_a.custCode=a.custcode) ";
					}else{
						sql+=" and not exists (select 1 from tIntProgDetail in_a " +
								"where  in_a.type='2' and in_a.IsCupboard='1' and in_a.custCode=a.custcode) ";
					}
				}
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and a.IntAppDate is not null and a.IntAppDate<>'' " +
						" and (a.IntSendDate is null or a.IntSendDate = '') " +
						" and dateadd(d,isnull(c.SendDay,0),a.IntAppDate)<getdate()";
			
				if(StringUtils.isNotBlank(customer.getCantInstall())){
					if("1".equals(customer.getCantInstall())){
						sql+=" and exists (select 1 from tIntProgDetail in_a where " +
								"in_a.type='2' and in_a.IsCupboard<>'1' and in_a.custCode=a.custcode) ";
					}else{
						sql+=" and not exists (select 1 from tIntProgDetail in_a where " +
								" in_a.type='2' and in_a.IsCupboard<>'1' and in_a.custCode=a.custcode) ";
					}
				}
			}
		}
		if(StringUtils.isNotBlank(customer.getSupplierCode())){
			if("CG".equals(customer.getItemType2())){
				sql+=" and cs.code = ?  " ;
				list.add(customer.getSupplierCode());
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and ispl.code=? " ;
				list.add(customer.getSupplierCode());
			}
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and d2.code= ? ";
			list.add(customer.getDepartment2());
		}
		
		if(customer.getDateTo()!=null){
			if("CG".equals(customer.getItemType2())){
				sql+=" and dateadd(d,d.SendDay,a.CupAppDate) < ?  " ;
				list.add(new Timestamp(
						DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and dateadd(d,isnull(c.SendDay,0),a.IntAppDate)<? " ;
				list.add(new Timestamp(
						DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
			}
		}
		// 集成进度跟踪，增加施工状态查询条件。 add by zb
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			String str = SqlUtil.resetStatus(customer.getConstructStatus());
			sql += " and b.ConstructStatus in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and b.Address like ?";
			list.add("%"+customer.getAddress()+"%");;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			if("CG".equals(customer.getItemType2())){
				sql += ") a order by a.橱柜下单时间";
			}else if ("YG".equals(customer.getItemType2())){
				sql += ") a order by a.衣柜下单时间";
			}else{
				sql += ") a ";
			}
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findNotInstallPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		                                                                                                        
		String sql = "select * from ( select  c.Address 楼盘,a.CupSendDate 橱柜发货时间,a.IntSendDate 衣柜发货时间,dateadd(d,2,a.CupSendDate) 橱柜预计安装日期 ," +
				" dateadd(d,2,a.IntSendDate) 衣柜预计安装日期, a.CupAppDate 橱柜下单时间, a.IntAppDate 衣柜下单时间, " +
				" ipd.remarks ,cs.Descr 橱柜供应商,ispl.descr 衣柜供应商,d2.desc2 集成部,e1.NameChi 项目经理, e.NameChi 集成设计师, " +
				" d1.Desc2 department2Descr from tCustIntProg a " +
				" inner join tcustomer c on c.code=a.CustCode and c.status='4' " +
				" left join tCustStakeholder csh on csh.custcode=a.CustCode and " ;
		if ("CG".equals(customer.getItemType2()) && StringUtils.isNotBlank(customer.getItemType2())) { //橱柜的，要显示橱柜设计师及其部门 modify by zb
			sql += " csh.role='61'" ;
		} else {
			sql += " csh.role='11'" ;
		}
		sql += " left join tsupplier cs on cs.code=a.CupSpl " +
				" left join tSupplier ispl on ispl.code=a.intspl" +
				" left join (select max(pk) pk ,CustCode from tIntProgDetail a where type='2' group  by  CustCode)ipd_a on ipd_a.custCode=a.custCode " +
				" left join tIntProgDetail ipd on ipd.pk =ipd_a.pk " +
				" left join tEmployee e1 on e1.Number =c.ProjectMan " +
				" left join tDepartment2 d1 on e1.Department2 = d1.Code " +
				" left join temployee e on e.Number=csh.EmpCode " +
				" left join tDepartment2 d2 on d2.Code=e.Department2 " +
				" where c.Status='4' " ;
		if(StringUtils.isNotBlank(customer.getItemType2())){
			if("CG".equals(customer.getItemType2())){
				sql+=" and a.CupSendDate is not null and a.CupSendDate<>''   " +
						" and (a.CupInstallDate is null  or  a.CupInstallDate = '') " +
						" and dateadd(d,2,a.CupSendDate)<getDate() ";
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and a.IntSendDate is not null and a.IntSendDate<>''  " +
						" and (a.IntInstallDate is null  or  a.IntInstallDate = '') " +
						" and dateadd(d,2,a.IntSendDate)<getDate() ";
			}
		}
		if(StringUtils.isNotBlank(customer.getSupplierCode())){
			if("CG".equals(customer.getItemType2())){
				sql+=" and cs.code = ?  " ;
				list.add(customer.getSupplierCode());
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and ispl.code=? " ;
				list.add(customer.getSupplierCode());
			}
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and d2.code= ? ";
			list.add(customer.getDepartment2());
		}
		if(customer.getDateTo()!=null){
			if("CG".equals(customer.getItemType2())){
				sql+=" and dateadd(d,2,a.CupSendDate)< ?  " ;
				list.add(new Timestamp(
						DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and dateadd(d,2,a.IntSendDate)<? " ;
				list.add(new Timestamp(
						DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
			}
		}
		// 集成进度跟踪，增加施工状态查询条件。 add by zb
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			String str = SqlUtil.resetStatus(customer.getConstructStatus());
			sql += " and c.ConstructStatus in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and c.Address like ?";
			list.add("%"+customer.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			if("CG".equals(customer.getItemType2())){
				sql += ") a order by a.橱柜发货时间";
			}else if ("YG".equals(customer.getItemType2())){
				sql += ") a order by a.衣柜发货时间";
			}else{
				sql += ") a ";
			}
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findNotDeliverPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		                                                                                                        
		String sql = "select * from ( select c.address 楼盘,a.CupInstallDate 橱柜实际安装时间,a.IntInstallDate 衣柜实际安装时间,dateadd(d,3,a.CupInstallDate) 橱柜预计交付时间" +
				" ,ipd.remarks ,dateadd(d,5,a.IntInstallDate) 衣柜预计交付时间,ispl.descr 衣柜供应商,cs.descr 橱柜供应商,e.NameChi 集成设计师 ,d2.Desc2 集成部,e1.NameChi 项目经理" +
				" ,d1.Desc2 department2Descr from tCustIntProg a " +
				" inner join tcustomer c on c.code=a.CustCode " +
				" left join tCustStakeholder csh on csh.custcode=a.CustCode and " ;
		if ("CG".equals(customer.getItemType2()) && StringUtils.isNotBlank(customer.getItemType2())) { //橱柜的，要显示橱柜设计师及其部门 modify by zb
			sql += " csh.role='61'" ;
		} else {
			sql += " csh.role='11'" ;
		}
		sql += " left join tsupplier cs on cs.code=a.CupSpl " +
				" left join tSupplier ispl on ispl.code=a.intspl" +
				" left join (select max(pk) pk ,CustCode from tIntProgDetail a where type='2' group  by  CustCode)ipd_a on ipd_a.custCode=a.custCode " +
				" left join tIntProgDetail ipd on ipd.pk =ipd_a.pk " +
				" left join tEmployee e1 on e1.Number =c.ProjectMan " +
				" left join tDepartment2 d1 on e1.Department2 = d1.Code " +
				" left join temployee e on e.Number=csh.EmpCode " +
				" left join tDepartment2 d2 on d2.Code=e.Department2 " +
				" where  c.Status='4'  ";
		if(StringUtils.isNotBlank(customer.getItemType2())){
			if("CG".equals(customer.getItemType2())){
				sql+=" and a.CupInstallDate is not null and a.CupInstallDate <> ''    " +
						" and ( a.CupDeliverDate is null or CupDeliverDate ='') " +
						" and dateadd(d,3,a.CupInstallDate)< getDate() ";
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and a.IntInstallDate is not null and a.IntInstallDate <> ''    " +
						" and ( a.IntDeliverDate is null or IntDeliverDate ='') " +
						" and dateadd(d,5,a.IntInstallDate)< getDate() ";
			}
		}
		if(StringUtils.isNotBlank(customer.getSupplierCode())){
			if("CG".equals(customer.getItemType2())){
				sql+=" and cs.code = ?  " ;
				list.add(customer.getSupplierCode());
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and ispl.code = ? " ;
				list.add(customer.getSupplierCode());
			}
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and d2.code= ? ";
			list.add(customer.getDepartment2());
		}
		if(customer.getDateTo()!=null){
			if("CG".equals(customer.getItemType2())){
				sql+=" and dateadd(d,3,a.CupInstallDate)< ? ";
				list.add(new Timestamp(
						DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and dateadd(d,5,a.IntInstallDate)< ? " ;
				list.add(new Timestamp(
						DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
			}
		}
		// 集成进度跟踪，增加施工状态查询条件。 add by zb
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			String str = SqlUtil.resetStatus(customer.getConstructStatus());
			sql += " and c.ConstructStatus in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and c.Address like ?";
			list.add("%"+customer.getAddress()+"%");;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			if("CG".equals(customer.getItemType2())){
				sql += ") a order by a.橱柜实际安装时间";
			}else if ("YG".equals(customer.getItemType2())){
				sql += ") a order by a.衣柜实际安装时间";
			}else{
				sql += ") a ";
			}
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: 未下单明细分页查询
	 * @author	created by zb
	 * @date	2018-10-20--下午6:36:12
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findNotAppPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		                                                                                                        
		String sql = " select * from(select a.Code,a.Descr,a.Status,a.Address,b.Date AppDate,dateadd(d," +
				" case when exists (select 1 from tXTCS where ID='CmpnyCode' and QZ='04') then 5 else 7 end,b.Date) PreAppDate, " +
				" c.CupAppDate,c.IntAppDate,csh.EmpCode IntDesign,e.NameChi IntDesignDescr,e.Department2 IntDept, " +
				" d2.Desc1 IntDeptDescr,csh2.EmpCode CupDesign,e2.NameChi CupDesignDescr,datediff(day,dateadd(d," +
				" case when exists (select 1 from tXTCS where ID='CmpnyCode' and QZ='04') then 5 else 7 end,b.Date),getDate()) overTime, ";
				
        if ("YG".equals(customer.getItemType2())) {
            sql += " c.IntDesignDate DesignDate, ";
        } else if ("CG".equals(customer.getItemType2())) {
            sql += " c.CupDesignDate DesignDate, ";
        }
				
		sql += "  b.DealRemark, ia.Date IaAppDate "+
				" from tCustomer a " +
//				" left join (select max(Date) Date, CustCode from tPrjJob where  ItemType1 = 'JC' " +
//				" 	and JobType = ? and Status in ('2','3','4') group by CustCode " +
//				" ) b on b.CustCode=a.Code " +
				" left join ( " +
				"	 select max(no)no,Custcode from tPrjJob in_a " +
				"	 where in_a.ItemType1 ='JC' and in_a.JobType = ? and in_a.Status in ('2','3','4') " +
				"		  and in_a.Date =(select max(in_b.Date) from tPrjJob in_b  " +
				"			   where in_a.CustCode=in_b.CustCode and in_b.ItemType1 = in_a.ItemType1 " + 
				"			   and in_b.JobType = in_a.JobType and in_b.Status in ('2','3','4') " +
				"		  ) " +
				"	 group by in_a.CustCode " +  				
				" )mj on mj.CustCode=a.Code " +
				" left join tPrjJob b on b.no=mj.no " + 
				" left join tCustIntProg c on c.CustCode = a.Code " +
				" left join tsupplier s1 on s1.code=c.CupSpl " +
				" left join tsupplier s2 on s2.code=c.IntSpl " +
				" left join tCustStakeholder csh on csh.custcode=a.Code and csh.role='11' " +
				" left join tCustStakeholder csh2 on csh2.custcode=a.Code and csh2.role='61' " +
				" left join temployee e on e.Number=csh.EmpCode " +
				" left join tDepartment2 d2 on d2.Code=e.Department2 " +
				" left join temployee e2 on e2.Number=csh2.EmpCode " +
				" left join (select min(Date) Date,CustCode from tItemApp in_a " + 
				"	where ItemType1='JC' and Status<>'CANCEL' " +
				"    and exists(select 1 from tItemAppDetail in_b " +
				"				inner join tItemReq in_c on in_c.pk=in_b.ReqPK " +
				"				inner join tIntProd in_d on in_c.IntProdPK=in_d.PK" +
				"				where in_b.no=in_a.no and in_d.IsCupboard=? " +
				"    ) group by CustCode " +
				" )ia on ia.CustCode=a.Code  " +
				" where 1=1 and a.Status = '4' " +
				" and getdate() > dateadd(d," +
				" case when exists (select 1 from tXTCS where ID='CmpnyCode' and QZ='04') then 5 else 7 end,b.Date) " +
				" and exists ( " + // 存在需求才显示
				" 	select * from tItemReq in_a " +
				" 	inner join tIntProd in_b on in_a.IntProdPK=in_b.PK " +
				" 	where a.Code=in_a.CustCode and in_a.ItemType1='JC' and in_a.Qty>0 and in_b.IsCupboard=? " +
				" )";
		
		if ("YG".equals(customer.getItemType2())) {
			list.add("01");
		} else {
			list.add("07");
		}
		if ("YG".equals(customer.getItemType2())) {
			list.add("0");
		} else {
			list.add("1");
		}
		if ("YG".equals(customer.getItemType2())) {
			list.add("0");
		} else {
			list.add("1");
		}
		if ("YG".equals(customer.getItemType2())) {
			sql += " and c.IntAppDate is null ";
		} else {
			sql += " and c.CupAppDate is null ";
		}
		if(null != customer.getDateTo()){
			sql+=" and dateadd(d,case when exists (select 1 from tXTCS where ID='CmpnyCode' and QZ='04') then 5 else 7 end,b.Date)< ? " ;
			list.add(new Timestamp(DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(customer.getSupplierCode())){
			if("YG".equals(customer.getItemType2())){
				sql+=" and c.IntSpl=? " ;
				list.add(customer.getSupplierCode());
			}else{
				sql+=" and c.CupSpl = ? " ;
				list.add(customer.getSupplierCode());
			}
		}
		// 集成进度跟踪，增加施工状态查询条件。 add by zb
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			String str = SqlUtil.resetStatus(customer.getConstructStatus());
			sql += " and a.ConstructStatus in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.Address like ?";
			list.add("%"+customer.getAddress()+"%");;
		}
		
		// 设计完成查询条件
		if ("YG".equals(customer.getItemType2())) {
            if ("0".equals(customer.getDesignCompleted())) {
                sql += " and c.IntDesignDate is null ";
            } else if ("1".equals(customer.getDesignCompleted())) {
                sql += " and c.IntDesignDate is not null ";
            }
        } else if ("CG".equals(customer.getItemType2())) {
            if ("0".equals(customer.getDesignCompleted())) {
                sql += " and c.CupDesignDate is null ";
            } else if ("1".equals(customer.getDesignCompleted())) {
                sql += " and c.CupDesignDate is not null ";
            }
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.AppDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 未安排工人明细
	 * @author	created by zb
	 * @date	2019-9-6--下午5:04:12
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findNotSetWorkerPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		                                                                                                        
		String sql = "select * from ( select  c.Address 楼盘,ct.Desc1 CustTypeDescr,a.CupSendDate 橱柜发货时间,a.IntSendDate 衣柜发货时间," +
				" dateadd(d,2,a.CupSendDate) 橱柜预计安装日期 , dateadd(d,2,a.IntSendDate) 衣柜预计安装日期 " +
				" ,ipd.remarks ,cs.Descr 橱柜供应商,ispl.descr 衣柜供应商,d2.desc2 集成部,e1.NameChi 项目经理, e1.Phone 项目经理电话, e.NameChi 集成设计师 " +
				" ,d1.Desc2 department2Descr,isnull(Ins.qty,0) 板材面积, isnull(Ins.totalfee,0) 安装费  " +
				" from tCustIntProg a " +
				" inner join tcustomer c on c.code=a.CustCode and c.status='4' " +
				" left join tCustType ct on ct.Code=c.CustType " +
				" left join tCustStakeholder csh on csh.custcode=a.CustCode and " ;
		if ("CG".equals(customer.getItemType2()) && StringUtils.isNotBlank(customer.getItemType2())) { //橱柜的，要显示橱柜设计师及其部门 modify by zb
			sql += " csh.role='61'" ;
		} else {
			sql += " csh.role='11'" ;
		}	  
		sql += 	" left join tsupplier cs on cs.code=a.CupSpl " +
				" left join tSupplier ispl on ispl.code=a.intspl" +
				" left join (select max(pk) pk ,CustCode from tIntProgDetail a where type='2' group  by  CustCode)" +
				" ipd_a on ipd_a.custCode=a.custCode " +
				" left join tIntProgDetail ipd on ipd.pk =ipd_a.pk " +
				" left join tEmployee e1 on e1.Number =c.ProjectMan " +
				" left join tDepartment2 d1 on e1.Department2 = d1.Code " +
				" left join temployee e on e.Number=csh.EmpCode " +
				" left join tDepartment2 d2 on d2.Code=e.Department2 " ;
				
		if ("CG".equals(customer.getItemType2()) && StringUtils.isNotBlank(customer.getItemType2())) { //橱柜衣柜增加安装费显示，衣柜显示板材面积byzjf20191209
			sql += " left join ( select a.CustCode,0 Qty, "+
				   " round(sum((a.CupUpHigh+a.CupDownHigh)*cast(isnull(x.qz,'0') as float)),2) totalfee " +
				   " from tSpecItemReq a "+
				   " left join tXTCS x on x.ID='CupInsFee' "+
				   " group by  CustCode "+
				   " )Ins on Ins.CustCode=a.CustCode ";
		} else {
			sql += " left join (select  CustCode,round(sum(c.Qty),2) Qty ,"+
				   " round(sum(isnull(e.IntInstallFee,0)*c.Qty),2) totalfee " +
				   " from tSpecItemReqDt c  " +
				   " left join tItem d on c.ItemCode=d.Code " +  
				   " inner join tItemType3 e on d.ItemType3=e.Code  " +
				   " where c.IsCupboard='0' and isnull(e.IntInstallFee,0)*c.Qty<>0 " +
				   " group by  CustCode " +
				   " )Ins on Ins.CustCode=a.CustCode " ;
		}						
		sql += " where c.Status='4' " ;
		if(StringUtils.isNotBlank(customer.getItemType2())){
			if("CG".equals(customer.getItemType2())){
				sql+=" and a.CupSendDate is not null and a.CupSendDate<>''   " +
						" and not exists (select 1 from tCustWorker in_a where in_a.WorkType12='18' and in_a.CustCode=a.CustCode) ";
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and a.IntSendDate is not null and a.IntSendDate<>''  " +
						" and not exists (select 1 from tCustWorker in_a where in_a.WorkType12='17' and in_a.CustCode=a.CustCode) ";
			}
		}
		if(StringUtils.isNotBlank(customer.getSupplierCode())){
			if("CG".equals(customer.getItemType2())){
				sql+=" and cs.code = ?  " ;
				list.add(customer.getSupplierCode());
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and ispl.code=? " ;
				list.add(customer.getSupplierCode());
			}
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and d2.code= ? ";
			list.add(customer.getDepartment2());
		}
		if(customer.getDateTo()!=null){
			if("CG".equals(customer.getItemType2())){
				sql+=" and a.CupSendDate< ?  " ;
				list.add(new Timestamp(
						DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
			}else if("YG".equals(customer.getItemType2())){
				sql+=" and a.IntSendDate< ? " ;
				list.add(new Timestamp(
						DateUtil.startOfTheDay(customer.getDateTo()).getTime()));
			}
		}
		// 集成进度跟踪，增加施工状态查询条件。 add by zb
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			String str = SqlUtil.resetStatus(customer.getConstructStatus());
			sql += " and c.ConstructStatus in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and c.Address like ?";
			list.add("%"+customer.getAddress()+"%");;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			if("CG".equals(customer.getItemType2())){
				sql += ") a order by a.橱柜发货时间";
			}else if ("YG".equals(customer.getItemType2())){
				sql += ") a order by a.衣柜发货时间";
			}else{
				sql += ") a ";
			}
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}
