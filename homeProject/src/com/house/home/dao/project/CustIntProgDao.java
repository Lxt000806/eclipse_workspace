package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.project.CustIntProg;

@SuppressWarnings("serial")
@Repository
public class CustIntProgDao extends BaseDao {

	/**
	 * CustIntProg分页信息
	 * 
	 * @param page
	 * @param custIntProg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustIntProg custIntProg) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select s1.Code SupplCode,a.Code CustCode,a.Address,a.LastUpdate, a.Status, x1.NOTE StatusDescr,a.Layout,x2.Note Layoutdescr,a.Area, a.CustType, ct.Desc1 CustTypeDescr, e1.Department1 DesignDept1, d1.Desc2 DesignDept1Descr,"
		         + " a.DesignMan, e1.NameChi DesignManDescr, d2.Desc2 PrjDept1Descr, a.ProjectMan, e2.NameChi ProjectManDescr, "
		         + " cast(dbo.fGetDept2Descr(a.Code,'11') as nvarchar(100)) IntDept2Descr,s1.Descr CupsplDescr,s2.Descr IntsplDescr,s3.Descr DoorsplDescr,s4.descr tablespldescr,"
		         + " cast(dbo.fGetEmpNameChi(a.Code,'11') as nvarchar(1000)) IntDesignDescr,"
		         + " cast(dbo.fGetEmpNameChi(a.Code,'14') as nvarchar(1000)) IntPlanManDescr,"
		         + " case when ipd.CustCode is not null and tit.CustCode is not null then '退单' when getdate() >= b.IntInstallDate then '交付' when getdate() >= b.IntSendDate then '安装'"
		         + " when getdate() >= b.IntAppDate then '生产' when getdate() >= cast(c.Date as date) then '制单' else '' end NowIntProgDescr, "
		         + " case when ipdCup.CustCode is not null and titCup.CustCode is not null then '退单' when getdate() >= b.CupInstallDate then '交付' when getdate() >= b.CupSendDate then '安装' when getdate() >= b.CupAppDate then '生产' "
		         + " when getdate() >= cast(c1.Date as date) then '制单' else '' end NowCupProgDescr, "
		         + " a.ConfirmBegin, c.Date MeasureAppDate, c1.Date MeasureCupAppDate,"
		         + " b.CupSpl,b.IntSpl,b.DoorSpl,b.TableSpl,b.CupAppDate,b.IntAppDate,b.DoorAppDate,b.TableAppDate,isnull(b.EcoArea,0)EcoArea,isnull(b.MdfArea,0)MdfArea,isnull(b.OtherArea,0)OtherArea ,  "
		         + " b.CupMaterial,b.IntMaterial,b.CupSendDays,b.IntSendDays,b.CupSendDate,b.IntSendDate,b.TableInstallDate, "
		         + " b.CupInstallDate,b.IntInstallDate,b.CupDeliverDate,b.IntDeliverDate,isnull(b.CupCheckDate,j.Date)CupCheckDate,isnull(b.IntCheckDate,J.Date)IntCheckDate,b.Remarks ,"
		         + " datediff(day, c.Date, b.IntAppDate) IntAppDays, datediff(day, c1.Date, b.CupAppDate) CupAppDays, "
		         + " isnull(datediff(day, c.Date, b.IntAppDate)-10,0) AppDelayDdays,"
		         + " isnull(datediff(day, c1.Date, b.CupAppDate)-10, 0) CupAppDelayDdays,b.DelayRemarks,"
		         + " datediff(day, b.IntAppDate, b.IntSendDate) IntProduceDays, datediff(day, b.CupAppDate, b.CupSendDate) CupProduceDays,"
		         + " datediff(day, dateadd(day, m.SendDay, b.IntAppDate), b.IntSendDate) IntSendDelayDays, "
		         + " datediff(day, dateadd(day, k.SendDay, b.CupAppDate), b.CupSendDate) CupSendDelayDays, "
		         + " datediff(day,  a.ConfirmBegin, h.DealDate) intMeasureStandardDays,datediff(day,  a.ConfirmBegin, i.DealDate)cupMeasureStandardDays," 
		         + " datediff(day,  a.ConfirmBegin, c.Date) intMeasureDays, datediff(day, a.ConfirmBegin, c1.Date) cupMeasureDays, "
		         + " datediff(day, b.IntSendDate, b.IntDeliverDate) IntDeliverDays, datediff(day, b.CupSendDate, b.CupDeliverDate) CupDeliverDays,"
		         + " isnull(datediff(day, c.Date, b.IntAppDate),0)+isnull(datediff(day, b.IntAppDate, b.IntSendDate),0)+isnull(datediff(day, b.IntSendDate, b.IntDeliverDate),0) IntAllDays,"
		         + " isnull(datediff(day, c1.Date, b.CupAppDate),0)+isnull(datediff(day, b.CupAppDate, b.CupSendDate),0)+isnull(datediff(day, b.CupSendDate, b.CupDeliverDate),0) CupAllDays,"
		         + " datediff(day, b.IntSendDate, b.IntDeliverDate)-10 IntInstallDelayDays, datediff(day, b.CupSendDate, b.CupDeliverDate)-10 CupInstallDelayDays,"
		         + " isnull((a.IntegrateFee - a.IntegrateDisc) * a.ContainInt,0) + isnull((a.CupboardFee - a.CupboardDisc) * a.ContainCup,0) AllPlan, isnull(f.IntAmount,0)+"
		         + " isnull(f.CupAmount,0) OrderAmount,isnull(g.zjljjcje,0)+isnull(g.zjljcgje,0) addreduceAll,"
		         + " isnull((a.IntegrateFee - a.IntegrateDisc) * a.ContainInt,0) IntYs, "
		         + " isnull((a.CupboardFee - a.CupboardDisc) * a.ContainCup,0) CupYs,  "
		         + " isnull(f.IntAmount, 0) IntXd, isnull(f.CupAmount, 0) CupXd,   "
		         + " isnull(g.zjljjcje,0) IntZj,isnull(g.zjljcgje,0) CupZj, "
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '1' ) then '是' else '否' end IsErrorDescr,"
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '2' ) then '是' else '否' end NoInstallDescr,"
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '3' ) then '是' else '否' end IsPayDescr,"
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '4' ) then '是' else '否' end IsBusiDescr,"
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '5' ) then '是' else '否' end IsReturnDescr,"
		         + " case when exists (select 1 from tCustComplaint where CustCode = a.Code) then '是' else '否' end IsCmplDescr,"
		         + " case when exists (select 1 from tPrjJob where CustCode = a.Code and ItemType1 = 'JC' and JobType = '02' and Status in ('2', '3', '4')) then '是' else '否' end IsSaleDescr,"
		         + " h.DealDate DealDate,i.DealDate CupDealDate,cast(dbo.fGetEmpNameChi(a.Code,'61') as nvarchar(1000)) CupDesignDescr,b.TableSendDate,b.IntDesignDate,b.CupDesignDate "
		         + " from tCustomer a "
		         + " left join tCusttype ct on ct.Code=a.CustType"
		         + " left join tEmployee e1 on e1.Number=a.DesignMan"
		         + " left join tDepartment1 d1 on d1.Code=e1.Department1"
		         + " left join tEmployee e2 on e2.Number=a.ProjectMan"
		         + " left join tDepartment1 d2 on d2.Code=e2.Department1"
		         + " left join tCustIntProg b on b.CustCode=a.Code"
		         + " left join ("
		         + " select max(Date) Date, CustCode from tPrjJob where  ItemType1 = 'JC' and JobType = '01' and Status in ('2','3','4') " //01集成测量申请日期
		         + " group by CustCode"
		         + " ) c on c.CustCode=a.Code"
		         + " left join ("
		         + " select max(Date) Date, CustCode from tPrjJob where  ItemType1 = 'JC' and JobType = '07' and Status in ('2','3','4') " //07橱柜测量申请日期
		         + " group by CustCode"
		         + " ) c1 on c1.CustCode=a.Code"
				 + " left join ( "
				 + " select CustCode,isnull(sum(case when IsCupboard='1' then case when Type='S' and Status<>'CANCEL' then Amount "
				 + " when Type='R' and Status='RETURN' then -Amount end else 0 end),0)+isnull(sum(case when IsCupboard='1' then a.OtherCost+a.OtherCostAdj else 0 end),0) CupAmount, "
				 + " isnull(sum(case when IsCupboard='0' then case when Type='S' and Status<>'CANCEL' then Amount "
				 + " when Type='R' and Status='RETURN' then -Amount end else 0 end),0)+isnull(sum(case when IsCupboard='0' then a.OtherCost+a.OtherCostAdj else 0 end),0) IntAmount "
				 + " from tItemApp a where ItemType1='JC'  and  ((Type='S' and Status<>'CANCEL') or (Type='R' and Status='RETURN')) "
				 + " group by CustCode "
				 + " )f on a.Code=f.CustCode "//改成从领料表获取数据 update by cjg 20181226
		         + " left join ("
		         + " select f.CustCode, "
		         + " sum(case when f.isCupBoard='0' then case when f.BefAmount>=0 then f.BefAmount-f.DiscCost else (f.BefAmount-f.DiscCost) end else 0 end) zjljjcje,"
		         + " sum(case when f.isCupBoard='1' then case when f.BefAmount>=0 then f.BefAmount-f.DiscCost else (f.BefAmount-f.DiscCost) end else 0 end) zjljcgje"
		         + " from tItemChg f "
		         + " where f.ItemType1='JC' and f.Status='2' group by f.CustCode"
		         + " ) g on g.CustCode=a.Code"
		         + " left outer join tSupplier s1 on b.CupSpl = s1.Code"
		         + " left outer join tSupplier s2 on b.Intspl = s2.Code"
		         + " left outer join tSupplier s3 on b.Doorspl = s3.Code"
		         + " left outer join tSupplier s4 on b.Tablespl = s4.Code"
		         + " left outer join tXTDM x1 on x1.CBM = a.Status and x1.ID='CUSTOMERSTATUS'"
		         + " left outer join tXTDM x2 on x2.CBM = a.layout and x2.ID='layout'"
		         + " left outer join ("
		         + " select CustCode, max(DealDate) DealDate from tPrjJob where endcode='1' and status='4' and JobType='01' group by CustCode"
		         + " ) h on h.CustCode=a.Code"
		         + " left outer join ("
		         + " select CustCode, max(DealDate) DealDate from tPrjJob where endcode='1' and status='4' and JobType='07' group by CustCode"
		         + " ) i on i.CustCode=a.Code"
		         + " left join (select CustCode,sum(Qty)Qty from tItemReq a "                        //集成的需求量是否为0
		         + " left outer join tintprod ip on a.intprodpk = ip.pk  where ip.IsCupboard='0' "
		         + " group by a.CustCode HAVING sum(a.Qty)=0)tit on a.Code=tit.CustCode "
		         + " left join (select CustCode,sum(Qty)Qty from tItemReq a "                        //橱柜的需求量是否为0
		         + " left outer join tintprod ip on a.intprodpk = ip.pk  where ip.IsCupboard='1' "
		         + " group by a.CustCode HAVING sum(a.Qty)=0)titCup on a.Code=titCup.CustCode "
				 + " left join (select CustCode from tIntProgDetail where type='5' and IsCupboard='0' group by CustCode)ipd on a.Code=ipd.CustCode "
		         + " left join (select CustCode from tIntProgDetail where type='5' and IsCupboard='1' group by CustCode)ipdCup on a.Code=ipdCup.CustCode "
		         + " left join (select CustCode,MAX(date)date from tPrjProgCheck where PrjItem='17' group by CustCode,PrjItem) j on j.CustCode=a.Code "
		         + " left join tIntSendDay k on k.ItemType12='30' and k.MaterialCode=b.CupMaterial"
		         + " left join tIntSendDay m on m.ItemType12='31' and m.MaterialCode=b.IntMaterial"
				 + " where 1=1 " ;
		
		if (StringUtils.isNotBlank(custIntProg.getCustCode())) {
            sql += " and a.Code = ? ";
            list.add(custIntProg.getCustCode());
        }
		
		if(StringUtils.isNotBlank(custIntProg.getAddress())){	
			sql+=" and a.address like ?  ";
			list.add("%"+custIntProg.getAddress()+"%");
		}
		
		if(StringUtils.isNotBlank(custIntProg.getCustType())){	
			sql+=" and a.CustType in ( "+custIntProg.getCustType()+")";
		}else{
			sql+=" and a.CustType not in ('2','6','7')";
		}

		if(StringUtils.isNotBlank(custIntProg.getDesignDept1Descr())){	
			sql+=" and d1.Code = ? ";
			list.add(custIntProg.getDesignDept1Descr());
		}
		if(StringUtils.isNotBlank(custIntProg.getPrjDept1Descr())){	
			sql+=" and d2.Code = ? ";
			list.add(custIntProg.getPrjDept1Descr());
		}
		if(StringUtils.isNotBlank(custIntProg.getIntDept2Descr())){	
			sql+="  and  exists (select 1 from tCustStakeholder inner join dbo.tEmployee e on EmpCode=e.Number " +
					"where Role='11' and CustCode=a.Code and e.Department2= ? )";
			list.add(custIntProg.getIntDept2Descr());
		}
		if(StringUtils.isNotBlank(custIntProg.getIsErrorDescr())){	
			if("0".equals(custIntProg.getIsErrorDescr())){
				sql+=" and not  exists (select 1 from tIntProgDetail where CustCode=b.CustCode and Type='1')";
			}else{
				sql+="and exists (select 1 from tIntProgDetail where CustCode=b.CustCode and Type='1')";
			}
		}
		if(StringUtils.isNotBlank(custIntProg.getIsBusiDescr())){	
			if("0".equals(custIntProg.getIsBusiDescr())){
				sql+=" and not  exists (select 1 from tIntProgDetail where CustCode=b.CustCode and Type='4')";
			}else{
				sql+="and exists (select 1 from tIntProgDetail where CustCode=b.CustCode and Type='4')";
			}
		}
		if(StringUtils.isNotBlank(custIntProg.getIsPayDescr())){	
			if("0".equals(custIntProg.getIsPayDescr())){
				sql+=" and not  exists (select 1 from tIntProgDetail where CustCode=b.CustCode and Type='3')";
			}else{
				sql+="and exists (select 1 from tIntProgDetail where CustCode=b.CustCode and Type='3')";
			}
		}
		if(StringUtils.isNotBlank(custIntProg.getIsSaleDescr())){	
			if("0".equals(custIntProg.getIsSaleDescr())){
				sql+=" and not exists (select CustCode from tPrjJob tp where  tp.CustCode=b.CustCode and tp.ItemType1='JC' and tp.JobType='02' and tp.Status in ('2','3','4') )";
			}else{
				sql+=" and exists (select CustCode from tPrjJob where b.Custcode=CustCode and ItemType1='JC' and JobType='02' and Status in ('2','3','4'))";
			}
		}
		if(StringUtils.isNotBlank(custIntProg.getNoInstallDescr())){	
			if("0".equals(custIntProg.getNoInstallDescr())){
				sql+=" and not  exists (select 1 from tIntProgDetail where CustCode=b.CustCode and Type='2')";
			}else{
				sql+="and exists (select 1 from tIntProgDetail where CustCode=b.CustCode and Type='2')";
			}
		}
		if(StringUtils.isNotBlank(custIntProg.getCheckDelayDays())){	
			if("1".equals(custIntProg.getCheckDelayDays())){
				sql+="and( isnull(datediff(day, c.Date, b.IntAppDate) - 10, 0)  > 0 or isnull(datediff(day, c1.Date, b.CupAppDate) - 10, 0)  > 0 )";
			}else if("0".equals(custIntProg.getCheckDelayDays())){
				sql+="and( isnull(datediff(day, c.Date, b.IntAppDate) - 10, 0)  = 0 and isnull(datediff(day, c1.Date, b.CupAppDate) - 10, 0)  = 0 )";
			}
		}
		
		if(custIntProg.getBuyDateFrom()!=null &&  custIntProg.getBuyDateTo()!=null){
			sql += " and ((b.CupAppDate>=? and b.CupAppDate<?) or  (b.IntAppDate>=? and b.IntAppDate<?)  or (b.DoorAppDate>=? and b.DoorAppDate<?) or (b.TableAppDate>=? and b.TableAppDate<?)) ";
			list.add(custIntProg.getBuyDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getBuyDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getBuyDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getBuyDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getBuyDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getBuyDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getBuyDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getBuyDateTo(), Calendar.DATE, 1));
		}
		if (custIntProg.getBuyDateFrom()!=null &&  custIntProg.getBuyDateTo()==null) {
			sql += " and ((b.CupAppDate>=? ) or (b.IntAppDate>=?) or (b.DoorAppDate>=? ) or (b.TableAppDate>=?)) ";
			list.add(custIntProg.getBuyDateFrom());
			list.add(custIntProg.getBuyDateFrom());
			list.add(custIntProg.getBuyDateFrom());
			list.add(custIntProg.getBuyDateFrom());
		}
		if(custIntProg.getBuyDateFrom()==null &&  custIntProg.getBuyDateTo()!=null){
			sql += " and (( b.CupAppDate<?) or  ( b.IntAppDate<?)  or (b.DoorAppDate<?) or (b.TableAppDate<?)) ";
			list.add(DateUtil.addInteger(custIntProg.getBuyDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getBuyDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getBuyDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getBuyDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getSendDateFrom()!=null && custIntProg.getSendDateTo() != null){
			sql += " and ((b.CupSendDate>=? and b.CupSendDate<?) or  (b.IntSendDate>=? and b.IntSendDate<?) or (b.TableSendDate>=? and b.TableSendDate<?)) ";
			list.add(custIntProg.getSendDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getSendDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getSendDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getSendDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getSendDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getSendDateTo(), Calendar.DATE, 1));
		}
		if (custIntProg.getSendDateFrom()!=null && custIntProg.getSendDateTo() == null) {
			sql += " and ((b.CupSendDate>=?) or  (b.IntSendDate>=?) or (b.TableSendDate>=?)) ";
			list.add(custIntProg.getSendDateFrom());
			list.add(custIntProg.getSendDateFrom());
			list.add(custIntProg.getSendDateFrom());
		}
		if(custIntProg.getSendDateFrom()==null && custIntProg.getSendDateTo() != null){
			sql += " and (( b.CupSendDate<?) or  (b.IntSendDate<?) or ( b.TableSendDate<?)) ";
			list.add(DateUtil.addInteger(custIntProg.getSendDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getSendDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getSendDateTo(), Calendar.DATE, 1));
		}
		
		if(custIntProg.getInstallDateFrom()!=null && custIntProg.getInstallDateTo() != null){
			sql += " and ((b.IntInstallDate>=? and b.IntInstallDate<?) or  (b.CupInstallDate>=? and b.CupInstallDate<?) or (b.TableInstallDate>=? and b.TableInstallDate<?) )  ";
			list.add(custIntProg.getInstallDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getInstallDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getInstallDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getInstallDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getInstallDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getInstallDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getInstallDateFrom()!=null && custIntProg.getInstallDateTo() == null){
			sql += " and ((b.IntInstallDate>=? ) or  (b.CupInstallDate>=? ) or (b.TableInstallDate>=? ) )  ";
			list.add(custIntProg.getInstallDateFrom());
			list.add(custIntProg.getInstallDateFrom());
			list.add(custIntProg.getInstallDateFrom());
		}
		if(custIntProg.getInstallDateFrom()==null && custIntProg.getInstallDateTo() != null){
			sql += " and (( b.IntInstallDate<?) or  ( b.CupInstallDate<?) or ( b.TableInstallDate<?) )  ";
			list.add(DateUtil.addInteger(custIntProg.getInstallDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getInstallDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getInstallDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getDeliverDateFrom()!=null && custIntProg.getDeliverDateTo()!= null){
			sql += " and ((b.IntDeliverDate>=? and b.IntDeliverDate<?) or  (b.CupDeliverDate>=? and b.CupDeliverDate<?))  ";
			list.add(custIntProg.getDeliverDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getDeliverDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getDeliverDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getDeliverDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getDeliverDateFrom()!=null && custIntProg.getDeliverDateTo()== null){
			sql += " and ((b.IntDeliverDate>=? ) or  (b.CupDeliverDate>=?))  ";
			list.add(custIntProg.getDeliverDateFrom());
			list.add(custIntProg.getDeliverDateFrom());
		}
		if(custIntProg.getDeliverDateFrom()==null && custIntProg.getDeliverDateTo()!= null){
			sql += " and (( b.IntDeliverDate<?) or  ( b.CupDeliverDate<?))  ";
			list.add(DateUtil.addInteger(custIntProg.getDeliverDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getDeliverDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getCheckDateFrom()!=null &&custIntProg.getCheckDateTo()!= null){
			sql += " and ((b.IntCheckDate>=? and b.IntCheckDate<?) or  (b.CupCheckDate>=? and b.CupCheckDate<?))  ";
			list.add(custIntProg.getCheckDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getCheckDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getCheckDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getCheckDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getCheckDateFrom()!=null &&custIntProg.getCheckDateTo()== null){
			sql += " and ((b.IntCheckDate>=? ) or  (b.CupCheckDate>=?))  ";
			list.add(custIntProg.getCheckDateFrom());
			list.add(custIntProg.getCheckDateFrom());
		}
		if(custIntProg.getCheckDateFrom()==null &&custIntProg.getCheckDateTo()!= null){
			sql += " and ((b.IntCheckDate<?) or  (b.CupCheckDate<?))  ";
			list.add(DateUtil.addInteger(custIntProg.getCheckDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getCheckDateTo(), Calendar.DATE, 1));
		}

		if(custIntProg.getMeasureAppDateFrom()!=null && custIntProg.getMeasureAppDateTo()!= null){
			sql += " and ((c.Date>=? and c.Date<?) or (c1.Date>=? and c1.Date<?))  ";
			list.add(custIntProg.getMeasureAppDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getMeasureAppDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getMeasureAppDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getMeasureAppDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getMeasureAppDateFrom()!=null && custIntProg.getMeasureAppDateTo()== null){
			sql += " and ((c.Date>=? ) or (c1.Date>=?))  ";
			list.add(custIntProg.getMeasureAppDateFrom());
			list.add(custIntProg.getMeasureAppDateFrom());
		}
		if(custIntProg.getMeasureAppDateFrom()==null && custIntProg.getMeasureAppDateTo()!= null){
			sql += " and (( c.Date<?) or ( c1.Date<?))  ";
			list.add(DateUtil.addInteger(custIntProg.getMeasureAppDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getMeasureAppDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getDealDateFrom()!=null && custIntProg.getDealDateTo()!= null){
			sql += " and ((h.DealDate>=? and h.DealDate<?) or (i.DealDate>=? and i.DealDate<?))  ";
			list.add(custIntProg.getDealDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getDealDateTo(), Calendar.DATE, 1));
			list.add(custIntProg.getDealDateFrom());
			list.add(DateUtil.addInteger(custIntProg.getDealDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getDealDateFrom()!=null && custIntProg.getDealDateTo()== null){
			sql += " and ((h.DealDate>=? ) or (i.DealDate>=? ))  ";
			list.add(custIntProg.getDealDateFrom());
			list.add(custIntProg.getDealDateFrom());
		}
		if(custIntProg.getDealDateFrom()==null && custIntProg.getDealDateTo()!= null){
			sql += " and (( h.DealDate<?) or ( i.DealDate<?))  ";
			list.add(DateUtil.addInteger(custIntProg.getDealDateTo(), Calendar.DATE, 1));
			list.add(DateUtil.addInteger(custIntProg.getDealDateTo(), Calendar.DATE, 1));
		}
		if(custIntProg.getConfirmBeginFrom()!=null){
			sql += " and a.ConfirmBegin>=?";
			list.add(custIntProg.getConfirmBeginFrom());
		}
		if (custIntProg.getConfirmBeginTo()!= null) {
			sql += " and a.ConfirmBegin<?";
			list.add(DateUtil.addInteger(custIntProg.getConfirmBeginTo(), Calendar.DATE, 1));
		}
		if(StringUtils.isNotBlank(custIntProg.getCupDesignDescr())){	
			sql+=" and exists (select * from tCustStakeholder where CustCode=a.Code and Role='61' and EmpCode=?) ";
			list.add(custIntProg.getCupDesignDescr());
		}
		if(StringUtils.isNotBlank(custIntProg.getIntDesignDescr())){	
			sql+=" and exists (select * from tCustStakeholder where CustCode=a.Code and Role='11' and EmpCode=?) ";
			list.add(custIntProg.getIntDesignDescr());
		}
		if(StringUtils.isNotBlank(custIntProg.getIntPlanManDescr())){	
			sql+=" and exists (select * from tCustStakeholder where CustCode=a.Code and Role='14' and EmpCode=?) ";
			list.add(custIntProg.getIntPlanManDescr());
		}
		if(StringUtils.isNotBlank(custIntProg.getStatus())){	
			sql+=" and a.Status in  ("+custIntProg.getStatus()+")";
		}
		
		if (StringUtils.isNotBlank(custIntProg.getCupSpl())) {
			sql += " and b.CupSpl=? ";
			list.add(custIntProg.getCupSpl());
		}
    	if (StringUtils.isNotBlank(custIntProg.getCupSpl())) {
			sql += " and b.CupSpl=? ";
			list.add(custIntProg.getCupSpl());
		}
    	if (StringUtils.isNotBlank(custIntProg.getIntSpl())) {
			sql += " and b.IntSpl=? ";
			list.add(custIntProg.getIntSpl());
		}
    	if (StringUtils.isNotBlank(custIntProg.getDoorSpl())) {
			sql += " and b.DoorSpl=? ";
			list.add(custIntProg.getDoorSpl());
		}
    	if (StringUtils.isNotBlank(custIntProg.getTableSpl())) {
			sql += " and b.TableSpl=? ";
			list.add(custIntProg.getTableSpl());
		}
    	if (custIntProg.getEcoArea() != null) {
			sql += " and b.EcoArea=? ";
			list.add(custIntProg.getEcoArea());
		}
    	if (custIntProg.getMdfArea() != null) {
			sql += " and b.MdfArea=? ";
			list.add(custIntProg.getMdfArea());
		}
    	if (custIntProg.getOtherArea() != null) {
			sql += " and b.OtherArea=? ";
			list.add(custIntProg.getOtherArea());
		}
    	if (StringUtils.isNotBlank(custIntProg.getCupMaterial())) {
			sql += " and b.CupMaterial=? ";
			list.add(custIntProg.getCupMaterial());
		}
    	if (StringUtils.isNotBlank(custIntProg.getIntMaterial())) {
			sql += " and b.IntMaterial=? ";
			list.add(custIntProg.getIntMaterial());
		}
    	if (custIntProg.getCupSendDays() != null) {
			sql += " and b.CupSendDays=? ";
			list.add(custIntProg.getCupSendDays());
		}
    	if (custIntProg.getIntSendDays() != null) {
			sql += " and b.IntSendDays=? ";
			list.add(custIntProg.getIntSendDays());
		}
    	if (StringUtils.isNotBlank(custIntProg.getRemarks())) {
			sql += " and b.Remarks=? ";
			list.add(custIntProg.getRemarks());
		}
    	if (custIntProg.getLastUpdate() != null) {
			sql += " and b.LastUpdate=? ";
			list.add(custIntProg.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custIntProg.getLastUpdatedBy())) {
			sql += " and b.LastUpdatedBy=? ";
			list.add(custIntProg.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(custIntProg.getExpired()) || "F".equals(custIntProg.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custIntProg.getActionLog())) {
			sql += " and b.ActionLog=? ";
			list.add(custIntProg.getActionLog());
		}
    	if (StringUtils.isNotBlank(custIntProg.getDelayRemarks())) {
			sql += " and b.DelayRemarks=? ";
			list.add(custIntProg.getDelayRemarks());
		}
    	if(custIntProg.getDesignDateFrom() != null){
    		sql+=" and (b.intDesignDate>=? or b.CupDesignDate >=? ) ";
    		list.add(new Timestamp(
    				DateUtil.startOfTheDay( custIntProg.getDesignDateFrom()).getTime()));
    		list.add(new Timestamp(
    				DateUtil.startOfTheDay( custIntProg.getDesignDateFrom()).getTime()));
    	}
    	if(custIntProg.getDesignDateTo() != null){
    		sql+=" and (b.IntDesignDate <= ? or b.CupDesignDate <= ?)";
    		list.add(new Timestamp(
    				DateUtil.endOfTheDay(custIntProg.getDesignDateTo()).getTime()));
    		list.add(new Timestamp(
    				DateUtil.endOfTheDay(custIntProg.getDesignDateTo()).getTime()));
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String , Object>  getCustIntProgDetail(String custCode) {
		String sql = " select s1.Code SupplCode,a.Code CustCode,a.Address, a.Status, x1.NOTE StatusDescr,a.Layout,x2.Note Layoutdescr,a.Area, a.CustType, ct.Desc1 CustTypeDescr, e1.Department1 DesignDept1, d1.Desc2 DesignDept1Descr,"
		         + " a.DesignMan, e1.NameChi DesignManDescr, d2.Desc2 PrjDept1Descr, a.ProjectMan, e2.NameChi ProjectManDescr, "
		         + " cast(dbo.fGetDept2Descr(a.Code,'11') as nvarchar(100)) IntDept2Descr,s1.Descr CupsplDescr,s2.Descr IntsplDescr,s3.Descr DoorsplDescr,s4.descr tablespldescr,"
		         + " cast(dbo.fGetEmpNameChi(a.Code,'11') as nvarchar(1000)) IntDesignDescr,"
		         + " cast(dbo.fGetEmpNameChi(a.Code,'14') as nvarchar(1000)) IntPlanManDescr,"
		         + " case when ipd.CustCode is not null and tit.CustCode is not null then '退单' when getdate() >= b.IntInstallDate then '交付' when getdate() >= b.IntSendDate then '安装'"
		         + " when getdate() >= b.IntAppDate then '生产' when getdate() >= cast(c.Date as date) then '制单' else '' end NowIntProgDescr, "
		         + " case when ipdCup.CustCode is not null and titCup.CustCode is not null then '退单' when getdate() >= b.CupInstallDate then '交付' when getdate() >= b.CupSendDate then '安装' when getdate() >= b.CupAppDate then '生产' "
		         + " when getdate() >= cast(c1.Date as date) then '制单' else '' end NowCupProgDescr, "
		         + " a.ConfirmBegin, c.Date MeasureAppDate, c1.Date MeasureCupAppDate,"
		         + " b.CupSpl,b.IntSpl,b.DoorSpl,b.TableSpl,b.CupAppDate,b.IntAppDate,b.DoorAppDate,b.TableAppDate,b.EcoArea,b.MdfArea,b.OtherArea ,  "
		         + " b.CupMaterial,b.IntMaterial,b.CupSendDays,b.IntSendDays,b.CupSendDate,b.IntSendDate, "
		         + " b.CupInstallDate,b.IntInstallDate,b.CupDeliverDate,b.IntDeliverDate,isnull(b.CupCheckDate,j.Date)CupCheckDate,isnull(b.IntCheckDate,J.Date)IntCheckDate,b.Remarks ,"
		         + " datediff(day, c.Date, b.IntAppDate) IntAppDays, datediff(day, c1.Date, b.CupAppDate) CupAppDays, "
		         + " isnull(datediff(day, c.Date, b.IntAppDate)-10,0) AppDelayDdays,"
		         + " isnull(datediff(day, c1.Date, b.CupAppDate)-10, 0) CupAppDelayDdays,b.DelayRemarks,"
		         + " datediff(day, b.IntAppDate, b.IntSendDate) IntProduceDays, datediff(day, b.CupAppDate, b.CupSendDate) CupProduceDays,"
		         + " datediff(day, dateadd(day, m.SendDay, b.IntAppDate), b.IntSendDate) IntSendDelayDays, "
		         + " datediff(day, dateadd(day, k.SendDay, b.CupAppDate), b.CupSendDate) CupSendDelayDays, " 
		         + " datediff(day,  a.ConfirmBegin, h.DealDate) intMeasureStandardDays,datediff(day,  a.ConfirmBegin, i.DealDate)cupMeasureStandardDays," 
		         + " datediff(day,  a.ConfirmBegin, c.Date) intMeasureDays, datediff(day, a.ConfirmBegin, c1.Date) cupMeasureDays, "
		         + " datediff(day, b.IntSendDate, b.IntDeliverDate) IntDeliverDays, datediff(day, b.CupSendDate, b.CupDeliverDate) CupDeliverDays,"
		         + " isnull(datediff(day, c.Date, b.IntAppDate),0)+isnull(datediff(day, b.IntAppDate, b.IntSendDate),0)+isnull(datediff(day, b.IntSendDate, b.IntDeliverDate),0) IntAllDays,"
		         + " isnull(datediff(day, c1.Date, b.CupAppDate),0)+isnull(datediff(day, b.CupAppDate, b.CupSendDate),0)+isnull(datediff(day, b.CupSendDate, b.CupDeliverDate),0) CupAllDays,"
		         + " datediff(day, b.IntSendDate, b.IntDeliverDate)-10 IntInstallDelayDays, datediff(day, b.CupSendDate, b.CupDeliverDate)-10 CupInstallDelayDays,"
		         + " isnull((a.IntegrateFee - a.IntegrateDisc) * a.ContainInt,0) + isnull((a.CupboardFee - a.CupboardDisc) * a.ContainCup,0) AllPlan, isnull(f.IntAmount,0)+"
		         + " isnull(f.CupAmount,0) OrderAmount,isnull(g.zjljjcje,0)+isnull(g.zjljcgje,0) addreduceAll,"
		         + " isnull((a.IntegrateFee - a.IntegrateDisc) * a.ContainInt,0) IntYs, "
		         + " isnull((a.CupboardFee - a.CupboardDisc) * a.ContainCup,0) CupYs,  "
		         + " isnull(f.IntAmount, 0) IntXd, isnull(f.CupAmount, 0) CupXd,   "
		         + " isnull(g.zjljjcje,0) IntZj,isnull(g.zjljcgje,0) CupZj, "
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '1' ) then '是' else '否' end IsErrorDescr,"
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '2' ) then '是' else '否' end NoInstallDescr,"
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '3' ) then '是' else '否' end IsPayDescr,"
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '4' ) then '是' else '否' end IsBusiDescr,"
		         + " case when exists (select 1 from tIntProgDetail where CustCode = a.Code and Type = '5' ) then '是' else '否' end IsReturnDescr,"
		         + " case when exists (select 1 from tCustComplaint where CustCode = a.Code) then '是' else '否' end IsCmplDescr,"
		         + " case when exists (select 1 from tPrjJob where CustCode = a.Code and ItemType1 = 'JC' and JobType = '02' and Status in ('2', '3', '4')) then '是' else '否' end IsSaleDescr,"
		         + " h.DealDate DealDate,i.DealDate CupDealDate,cast(dbo.fGetEmpNameChi(a.Code,'61') as nvarchar(1000)) CupDesignDescr,b.TableSendDate "
		         + " from tCustomer a "
		         + " left join tCusttype ct on ct.Code=a.CustType"
		         + " left join tEmployee e1 on e1.Number=a.DesignMan"
		         + " left join tDepartment1 d1 on d1.Code=e1.Department1"
		         + " left join tEmployee e2 on e2.Number=a.ProjectMan"
		         + " left join tDepartment1 d2 on d2.Code=e2.Department1"
		         + " left join tCustIntProg b on b.CustCode=a.Code"
		         + " left join ("
		         + " select max(Date) Date, CustCode from tPrjJob where  ItemType1 = 'JC' and JobType = '01' and Status in ('2','3','4') " //01集成测量申请日期
		         + " group by CustCode"
		         + " ) c on c.CustCode=a.Code"
		         + " left join ("
		         + " select max(Date) Date, CustCode from tPrjJob where  ItemType1 = 'JC' and JobType = '07' and Status in ('2','3','4') " //07橱柜测量申请日期
		         + " group by CustCode"
		         + " ) c1 on c1.CustCode=a.Code"
				 + " left join ( "
				 + " select CustCode,isnull(sum(case when IsCupboard='1' then case when Type='S' and Status<>'CANCEL' then Amount "
				 + " when Type='R' and Status='RETURN' then -Amount end else 0 end),0)+isnull(sum(case when IsCupboard='1' then a.OtherCost+a.OtherCostAdj else 0 end),0) CupAmount, "
				 + " isnull(sum(case when IsCupboard='0' then case when Type='S' and Status<>'CANCEL' then Amount "
				 + " when Type='R' and Status='RETURN' then -Amount end else 0 end),0)+isnull(sum(case when IsCupboard='0' then a.OtherCost+a.OtherCostAdj else 0 end),0) IntAmount "
				 + " from tItemApp a where ItemType1='JC'  and  ((Type='S' and Status<>'CANCEL') or (Type='R' and Status='RETURN')) "
				 + " group by CustCode "
				 + " )f on a.Code=f.CustCode "//改成从领料表获取数据 update by cjg 20181226
		         + " left join ("
		         + " select f.CustCode, "
		         + " sum(case when f.isCupBoard='0' then case when f.BefAmount>=0 then f.BefAmount-f.DiscCost else (f.BefAmount-f.DiscCost) end else 0 end) zjljjcje,"
		         + " sum(case when f.isCupBoard='1' then case when f.BefAmount>=0 then f.BefAmount-f.DiscCost else (f.BefAmount-f.DiscCost) end else 0 end) zjljcgje"
		         + " from tItemChg f "
		         + " where f.ItemType1='JC' and f.Status='2' group by f.CustCode"
		         + " ) g on g.CustCode=a.Code"
		         + " left outer join tSupplier s1 on b.CupSpl = s1.Code"
		         + " left outer join tSupplier s2 on b.Intspl = s2.Code"
		         + " left outer join tSupplier s3 on b.Doorspl = s3.Code"
		         + " left outer join tSupplier s4 on b.Tablespl = s4.Code"
		         + " left outer join tXTDM x1 on x1.CBM = a.Status and x1.ID='CUSTOMERSTATUS'"
		         + " left outer join tXTDM x2 on x2.CBM = a.layout and x2.ID='layout'"
		         + " left outer join ("
		         + " select CustCode, max(DealDate) DealDate from tPrjJob where endcode='1' and status='4' and JobType='01' group by CustCode"
		         + " ) h on h.CustCode=a.Code"
		         + " left outer join ("
		         + " select CustCode, max(DealDate) DealDate from tPrjJob where endcode='1' and status='4' and JobType='07' group by CustCode"
		         + " ) i on i.CustCode=a.Code"
		         + " left join (select CustCode,sum(Qty)Qty from tItemReq a "                        //集成的需求量是否为0
		         + " left outer join tintprod ip on a.intprodpk = ip.pk  where ip.IsCupboard='0' "
		         + " group by a.CustCode HAVING sum(a.Qty)=0)tit on a.Code=tit.CustCode "
		         + " left join (select CustCode,sum(Qty)Qty from tItemReq a "                        //橱柜的需求量是否为0
		         + " left outer join tintprod ip on a.intprodpk = ip.pk  where ip.IsCupboard='1' "
		         + " group by a.CustCode HAVING sum(a.Qty)=0)titCup on a.Code=titCup.CustCode "
				 + " left join (select CustCode from tIntProgDetail where type='5' and IsCupboard='0' group by CustCode)ipd on a.Code=ipd.CustCode "
		         + " left join (select CustCode from tIntProgDetail where type='5' and IsCupboard='1' group by CustCode)ipdCup on a.Code=ipdCup.CustCode "
		         + " left join (select CustCode,MAX(date)date from tPrjProgCheck where PrjItem='17' group by CustCode,PrjItem) j on j.CustCode=a.Code " 
		         + " left join tIntSendDay k on k.ItemType12='30' and k.MaterialCode=b.CupMaterial"
		         + " left join tIntSendDay m on m.ItemType12='31' and m.MaterialCode=b.IntMaterial"
				 + " where 1=1 and a.Code=?" ;
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
		
	}
	
	public Map<String , Object>  findSenddaysByMaterial(String material,String itemType12) {
		String sql ="select SendDay from tIntSendDay where MaterialCode=? and ItemType12=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{material,itemType12});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
		
	}
	
	public void doDelayAdd(CustIntProg custIntProg) {
		String sql = "update  tCustIntProg set DelayRemarks=? WHERE CustCode=?";
		this.executeUpdateBySql(
				sql,
				new Object[] {custIntProg.getDelayRemarks(),custIntProg.getCustCode() });
	}
	
	public List<Map<String , Object>>  checkRegistered(String custCode) {
		String sql ="select 1 from tCustIntProg where CustCode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		return list;
	}
	public List<Map<String , Object>>  checkDelayed(String custCode) {
		String sql ="select  1 from    tcustomer a   left join tCustIntProg b on b.CustCode = a.Code " 
                +"left join (select max(Date) Date, CustCode " 
                +"from tPrjJob where ItemType1 = 'JC' and JobType in ('01','04') and " 
                +"Status in ('2', '3', '4') group by CustCode) c on c.CustCode = a.Code " 
                +"left join (select max(Date) Date, CustCode " 
                +"from tPrjJob where ItemType1 = 'JC' and JobType in ('07','25') and " 
                +"Status in ('2', '3', '4')group by CustCode) c1 on c1.CustCode = a.Code " 
                +"where(isnull(datediff(day, c1.Date, b.CupAppDate) - 10, 0)> 0 or isnull(datediff(day, c.Date, b.IntAppDate) - 10, 0) >0) and a.Code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		return list;
	}
	
	//保存
		@SuppressWarnings("deprecation")
		public Result doSaveProc(CustIntProg custIntProg) {
			Assert.notNull(custIntProg);
			Result result = new Result();
			Connection conn = null;
			CallableStatement call = null;
			try {
				HibernateTemplate hibernateTemplate = SpringContextHolder
						.getBean("hibernateTemplate");
				Session session = SessionFactoryUtils.getSession(
						hibernateTemplate.getSessionFactory(), true);
				conn = session.connection();
				call = conn.prepareCall("{Call pJcjdgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				call.setString(1, custIntProg.getCustCode());
				call.setString(2, custIntProg.getIntMaterial());	
				call.setString(3, custIntProg.getCupMaterial());
				call.setString(4, custIntProg.getDoorSpl());
				call.setString(5, custIntProg.getTableSpl());
				call.setString(6, custIntProg.getCupSpl());
				call.setString(7, custIntProg.getIntSpl());
				call.setDouble(8, custIntProg.getEcoArea()==null?0.0:custIntProg.getEcoArea());
				call.setDouble(9, custIntProg.getMdfArea()==null?0.0:custIntProg.getMdfArea());
				call.setDouble(10, custIntProg.getOtherArea()==null?0.0:custIntProg.getOtherArea());
				call.setInt(11, custIntProg.getCupSendDays()==null?0:custIntProg.getCupSendDays());
				call.setInt(12, custIntProg.getIntSendDays()==null?0:custIntProg.getIntSendDays());
				call.setTimestamp(13, custIntProg.getCupAppDate()==null?null:new Timestamp(custIntProg.getCupAppDate().getTime()));
				call.setTimestamp(14, custIntProg.getIntAppDate()==null?null:new Timestamp(custIntProg.getIntAppDate().getTime()));
				call.setTimestamp(15, custIntProg.getDoorAppDate()==null?null:new Timestamp(custIntProg.getDoorAppDate().getTime()));
				call.setTimestamp(16, custIntProg.getTableAppDate()==null?null:new Timestamp(custIntProg.getTableAppDate().getTime()));
				call.setTimestamp(17, custIntProg.getCupSendDate()==null?null:new Timestamp(custIntProg.getCupSendDate().getTime()));
				call.setTimestamp(18, custIntProg.getIntSendDate()==null?null:new Timestamp(custIntProg.getIntSendDate().getTime()));
				call.setTimestamp(19, custIntProg.getCupInstallDate()==null?null:new Timestamp(custIntProg.getCupInstallDate().getTime()));
				call.setTimestamp(20, custIntProg.getIntInstallDate()==null?null:new Timestamp(custIntProg.getIntInstallDate().getTime()));
				call.setTimestamp(21, custIntProg.getCupDeliverDate()==null?null:new Timestamp(custIntProg.getCupDeliverDate().getTime()));
				call.setTimestamp(22, custIntProg.getIntDeliverDate()==null?null:new Timestamp(custIntProg.getIntDeliverDate().getTime()));
				call.setString(23, custIntProg.getRemarks());
				call.setString(24, custIntProg.getLastUpdatedBy());
				call.setString(25, custIntProg.getIntSpl());
				call.setTimestamp(26, custIntProg.getTableSendDate()==null?null:new Timestamp(custIntProg.getTableSendDate().getTime()));
				call.setString(27, custIntProg.getIntProgDetailJson());
				call.registerOutParameter(28, Types.INTEGER);
				call.registerOutParameter(29, Types.NVARCHAR);
				call.setTimestamp(30, custIntProg.getIntDesignDate()==null?null:new Timestamp(custIntProg.getIntDesignDate().getTime()));
				call.setTimestamp(31, custIntProg.getCupDesignDate()==null?null:new Timestamp(custIntProg.getCupDesignDate().getTime()));
				call.execute();
				result.setCode(String.valueOf(call.getInt(28)));
				result.setInfo(call.getString(29));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbUtil.close(null, call, conn);
			}
			return result;
			
		}
}

