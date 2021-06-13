package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class GcxxglDao extends BaseDao {
	
	
	/**
	 * 采购跟踪 
	 * @param page
	 * @param purchase
	 * @return
	 * 
	 **/
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from ( select (select count(*) from dbo.tPrjProgCheck where CustCode=a.code ) checkTimes ,a.lastupdate,a.Code,a.Status,a.Address as custAddress,a.realaddress," +
				" s2.note nowspeeddescr,s1.note planspeeddescr,a.Descr as 客户名称,a.Mobile1 as 联系电话1,a.Mobile2 as 联系电话2,a.ProjectMan as 项目经理编号," +
				" convert(varchar(10),a.SignDate,120) as 合同开工时间,convert(varchar(10),a.ConfirmBegin,120) as 实际开工时间,e2.NameChi as 设计师,e1.NameChi as projectDescr,e1.phone ProjectManPhone,d3.Desc1 as 工程部1" +
				" , d.Desc1 as department2descr,a.CheckMan as 巡检员编号,e3.NameChi as 巡检员,a.DesignFee as 设计费, d2.Desc2 Dept2Desc  ," +
				" a.ManageFee*ContainBase as 管理费预算,a.IntegrateFee*ContainInt as 集成费预算, a.CupboardFee * a.ContainCup as 橱柜费预算, a.BaseFee_Dirct*ContainBase as 直接费预算," +
				" a.BaseFee*ContainBase as 基础费预算,a.MainFee*ContainMain as 主材预算,i.Note as 施工状态,a.softFee*ContainSoft as 软装预算," +
				" a.MainServFee*ContainMainServ as 服务性产品,a.ContractFee as 工程总造价,isnull(bic.SumChgAmount,0) as jczj,f.Note as 施工方式,g.Note as 户型,a.Area as 面积," +
				" a.BeginComDate as 开工令时间,a.ConstructDay as 施工工期,a.ConstructStatus as 施工状态编号,isnull(b1.SumPay,0) as havePay," +
				" a.FirstPay,a.SecondPay,a.ThirdPay,a.FourPay,isnull(bic.SumChgAmount,0)+isnull(ic.SumChgAmount,0)+isnull(cfc.SumChgAmount,0) as ZJXHJ," +
				" a.ConsEndDate as 竣工日期, a.PlanCheckOut as 预计结算日期,a.CheckOutDate as 实际结算日期," +
				" case when ((CheckoutDate is not null) and (ConfirmBegin is not null)) then isnull(DATEDIFF(day,ConfirmBegin,CheckoutDate),0) - ConstructDay-dbo.fGetConstDay() " +
				" when CheckoutDate is null and ConfirmBegin is not null then DATEDIFF(day,ConfirmBegin,getdate()) - ConstructDay-dbo.fGetConstDay() else null end as delayDay," +
				" a.IntMsrDate,a.IntDlyDay,a.ConsRcvDate as 工程接收日期,a.IsComplain as 是否客诉编号,b.Note as 客户渠道, " +
				" dbo.fGetEmpNameChi(a.Code,'01') as businessFlowDescr,a.SendJobDate as 派单时间,a.ConsArea as 片区, c.Note as 是否客诉,a.LastUpdate as 最后修改时间," +
				" a.LastUpdatedBy as 最后操作人员, (isnull(a.BaseFee_Dirct,0) + isnull(a.BaseFee_Comp,0) + isnull(ManageFee,0) - isnull(BaseDisc,0))*ContainBase " +
				" as baseCost,(isnull(a.MainFee,0) - isnull(a.MainDisc,0))*ContainMain  as mainCost,(isnull(a.SoftFee,0) - isnull(SoftDisc,0))*ContainSoft " +
				" as SoftCost, (isnull(a.IntegrateFee,0) - isnull(a.IntegrateDisc,0))*ContainInt + (isnull(a.CupboardFee,0) - isnull(a.CupboardDisc,0))*ContainCup " +
				" as InteCost,isnull(bic.SumChgAmount,0) as JCZH, isnull(ContractFee,0) + isnull(DesignFee,0) + isnull(bic.SumChgAmount,0)" +
				" +isnull(ic.SumChgAmount,0) + isnull(cfc.SumChgAmount, 0) as custCountCost, (isnull(a.BaseFee_Dirct,0) + isnull(a.BaseFee_Comp,0))*ContainBase +" +
				" isnull(bic.SumChgAmount,0) as PrjManCost, (isnull(a.BaseFee_Dirct,0) + isnull(a.BaseFee_Comp,0) + isnull(ManageFee,0) - isnull(BaseDisc,0))" +
				" *ContainBase  + (isnull(a.MainFee,0) - isnull(a.MainDisc,0))*ContainMain + (isnull(a.IntegrateFee,0)  - isnull(a.IntegrateDisc,0))*ContainInt + " +
				" (isnull(a.CupboardFee,0)  - isnull(a.CupboardDisc,0))*ContainCup + isnull(bic.SumChgAmount,0) + isnull(zj.SumChgAmount,0) as consManCost," +
				" e2.Phone DesignManPhone,e_jc.NameChi JCDSMNameChi,e_cg.Number CGDSMCode ,e_cg.NameChi CGDSMNameChi,CGDS.PK cgPk,e_jc.Number jcdsman,e_JC.Phone JCDSMPhone,RZDS.EmpCode,e_rz.NameChi RZDSMNameChi,e_rz.Phone RZDSMPhone,a.DocumentNo," +
				" a.HaveReturn,a.HaveCheck,a.HavePhoto,xt1.Note HaveReturnDescr,xt2.Note HaveCheckDescr,xt3.Note HavePhotoDescr , a.DiscRemark as discRemarks," +
				" a.CheckStatus, xt4.note 客户结算状态,a.CustCheckDate 客户结算日期 , gxr.empcode PreloftsMan,e5.NameChi PreloftsManName,ct.desc1 CustTypeDescr,RZDS.pk RZPk, " +
				" ccd.toiletNum,ccd.bedRoomNum,ccd.balconyNum,x1.note kitchDoorTypeDescr,x2.note isWood, x3.note balconyTitle, " +
				" x4.note isbuilderwall,cp.LastCustPayDate ,ia.LastConfirmDate,bd.RegionCode,r.Descr RegionDescr " +
				",e_a.namechi mainmanagerDescr,e_a.Phone mainManagerPhone, " +//JCDS.EmpCode  j.Note as 计划进度  ,h.Note as 当前进度
				" emp1.Number CustSceneDesi, emp1.NameChi CustSceneDesiDescr, a.IsInitSign, x5.NOTE IsInitSignDescr, x6.NOTE relcustdescr " +
				" from tCustomer a  " +
				" left join tEmployee e1 on e1.Number = a.ProjectMan  " +
				" left join tEmployee e2 on e2.Number = a.DesignMan  " +
				" left join tEmployee e3 on e3.Number = a.CheckMan  " +
				" left join tEmployee e4 on e4.Number = a.BusinessMan  " +
				" left join tXTDM xt1 on xt1.CBM = a.HaveReturn and xt1.ID = 'HAVENO' " +
				" left join tXTDM xt2 on xt2.CBM = a.HaveCheck and xt2.ID = 'HAVENO'  " +
				" left join tXTDM xt3 on xt3.CBM = a.HavePhoto and xt3.ID = 'HAVENO'  " +
				" left join tXTDM b on b.CBM = a.Source and b.ID = 'CUSTOMERSOURCE' " +
				" left join tXTDM c on c.CBM = a.IsComplain and c.ID = 'YESNO'  " +
				" left join tDepartment2 d on d.Code = e1.Department2  " +
				" left join tXTDM  f on f.CBM = a.ConstructType and f.ID = 'CONSTRUCTTYPE'  " +
				" left join tXTDM g on g.CBM = a.Layout and g.ID = 'LAYOUT'  " +
				" left join tXTDM i on i.CBM = a.ConstructStatus and i.ID = 'CONSTRUCTSTATUS'  " +
				" left join tXTDM xt4 on xt4.CBM = a.CheckStatus and xt4.ID = 'CheckStatus' " +
				" left join tDepartment2 d2 on d2.Code=e2.Department2  " +
				" left join tDepartment1 d3 on d3.Code = e1.Department1  " +
				" left outer join (select sum(Amount) SumPay,CustCode from tCustPay group by CustCode) b1 on b1.CustCode=a.Code " +
				" left outer join (select sum(Amount) SumChgAmount,CustCode from tBaseItemChg where Status='2' group by CustCode ) bic on bic.CustCode=a.Code "+
				" left outer join (select sum(Amount) SumChgAmount,CustCode from tItemChg where Status='2' group by CustCode ) ic on ic.CustCode=a.Code  " +
				" left outer join (select sum(ChgAmount) SumChgAmount,CustCode from tConFeeChg where status='CONFIRMED'and ChgType<>'3' group by CustCode ) cfc on cfc.CustCode=a.Code  " +
				" left join(select sum(isnull(Amount,0)) SumChgAmount,CustCode from tItemChg where Status='2' and (ItemType1 = 'ZC' or ItemType1 = 'JC') " +
				" and IsService = 0 group by CustCode) zj    on zj.CustCode = a.code  " +
				" left outer join (select CustCode,max(EmpCode) EmpCode from tCustStakeHolder where role='11' group by CustCode) JCDS on a.Code=JCDS.CustCode " +
				" left outer join tEmployee e_jc on e_jc.Number=JCDS.EmpCode " +
				" left outer join (select CustCode,pk ,max(EmpCode) EmpCode from tCustStakeHolder where role='50' group by CustCode,pk) RZDS on a.Code=RZDS.CustCode  " +
				" left outer join tEmployee e_rz on e_rz.Number=RZDS.EmpCode " +
				
				" left join (select b.CustCode,Max(PrjItem) jhprjitem from tprjprog b right join (select MAX(a.planbegin) planbegin,a.CustCode  from " +
				" (select max(planbegin)as planbegin,CustCode ,PrjItem From  tprjprog where planbegin < getdate() group by CustCode ,planbegin,PrjItem) a" +
				" group by a.CustCode )a  on a.CustCode=b.CustCode and a.planbegin=b.PlanBegin group by b.CustCode " +
				" )dd on dd.custcode=a.code" +
				
				/*" left join (select MAX(a.PrjItem) prjitem ,a.CustCode from tPrjProg a " +
				" right join ( select  MAX(BeginDate)begindate,CustCode from tPrjProg  group by CustCode " +
				" )b on a.CustCode=b.CustCode and a.BeginDate=b.begindate and a.CustCode=b.CustCode " +
				" group by a.CustCode)sj on a.Code=sj.CustCode" +*/
				" LEFT JOIN (  select max(Pk) pk,Custcode from tPrjProg a  " +
				" where a.Begindate=(select max(Begindate) from tPrjProg  where custcode=a.CustCode ) " +
				" group by a.CustCode) pp ON pp.Custcode=a.Code   " +
				" left join tPrjprog sj on sj.pk=pp.pk  "+
				
				" left join tXTDM s1 on s1.cbm= dd.jhprjitem and s1.ID='PRJITEM'"+
				" left join tXTDM s2 on s2.cbm= sj.prjitem and s2.ID='PRJITEM'"+
				" left outer join (select CustCode, max(EmpCode) EmpCode from   tCustStakeHolder where  role = '61'" +
				" group by CustCode ) CGDS1 on a.Code = CGDS1.CustCode " +
				" left join tCustStakeHolder CGDS on CGDS1.custCode=CGDS.custCode and CGDS1.empCode=CGDS.empCode and CGDS.role='61'  " +
				" left outer join tEmployee e_cg on e_cg.Number = CGDS1.EmpCode " +
				" left join (select CustCode,max(EmpCode) empCode from tCustStakeHolder where role = '34' group by CustCode) csh_a " +
				" 	on csh_a.CustCode = a.Code " +
				"  left join tEmployee e_a on e_a.number = csh_a.empCode "+
				" left outer join tbuilder bd on bd.code=a.BuilderCode  " +
				" left outer join tRegion r on bd.RegionCode=r.Code  " +
				" left outer join tCustStakeholder gxr on gxr.custcode=a.code and gxr.role='60' " +
				" left outer join tEmployee e5 on e5.Number = gxr.Empcode   " +
				" left join tCustStakeholder gxr1 on gxr1.CustCode = a.Code and gxr1.Role = '63' " +
				" left join tEmployee emp1 on gxr1.EmpCode = emp1.Number " +
				" left join tcustcheckData ccd on ccd.custCode=a.code " +
				" left join tXTDm x1 on x1.cbm=ccd.kitchDoorType and x1.id='KITCHDRTYPE' " +
				" left join tXTDM x2 on x2.cbm=ccd.isWood and x2.id='YESNO' " +
				" left join tXTDM x3 on x3.cbm=ccd.balconyTitle and x3.id='yesno'" +
				" left join tXTDM x4 on x4.cbm=ccd.isBUildwall and x4.id='yesno'" +
				" left join tXTDM x5 on x5.CBM=a.IsInitSign and x5.ID='YESNO'" +
				" left join (select max(Date) LastCustPayDate,in_cp.CustCode from tCustPay in_cp group by in_cp.CustCode) cp on cp.CustCode=a.Code" +
				" left join (select max(ConfirmDate) LastConfirmDate,in_ia.CustCode from tItemApp in_ia group by in_ia.CustCode) ia on ia.CustCode=a.Code" +
				" left outer join tCustType ct on a.CustType=ct.code " +
				" left join tXTDM x6 on a.RelCust=x6.CBM and x6.ID='RELCUST' " +
				" where a.EndCode in ('0','3','4') and  " +
				 SqlUtil.getCustRight(uc, "a", 0);
		
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		
		if(StringUtils.isNotBlank(customer.getConsArea())){
			sql+=" and a.consArea like ? " ;
			list.add("%"+customer.getConsArea()+"%");
		}
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and a.projectMan =?";
			list.add(customer.getProjectMan());
		}
		if(StringUtils.isNotBlank(customer.getDiscRemark())){
			sql+=" and a.DiscRemark like ?";
			list.add("%"+customer.getDiscRemark()+"%");
		}
		if(customer.getBeginDateFrom()!=null){
			sql += " and a.ConfirmBegin>= ? ";
			list.add(customer.getBeginDateFrom());
		}
		if(customer.getBeginDateTo()!=null){
			sql += " and a.ConfirmBegin<= ? ";
			list.add(customer.getBeginDateTo());
		}
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			sql+=" and e1.Department1 = ?";
			list.add(customer.getDepartment1());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and e1.Department2 = ?";
			list.add(customer.getDepartment2());
		}
		if(StringUtils.isNotBlank(customer.getMobile1())){
			sql+=" and ( a.mobile1 =? or a.mobile2 like ? ) ";
			list.add(customer.getMobile1());
			list.add(customer.getMobile1());
		}
		if(customer.getCheckOutDateFrom()!=null){
			sql+=" and a.CheckOutDate >=?";
			list.add(customer.getCheckOutDateFrom());
		}
		if(customer.getCheckOutDateTo()!=null){
			sql+=" and a.CheckOutDate <=? ";
			list.add(customer.getCheckOutDateTo());
		}
		if(customer.getCustCheckDateFrom()!=null){
			sql+=" and a.CustCheckDate >=?";
			list.add(customer.getCustCheckDateFrom());
		}
		if(customer.getCustCheckDateTo()!=null){
			sql+=" and a.CustCheckDate-1 <=? ";
			list.add(customer.getCustCheckDateTo());
		}
		if(StringUtils.isNotBlank(customer.getCheckMan())){
			sql+=" and a.CheckMan =? ";
			list.add(customer.getCheckMan());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.custType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getBuilderCode())){
			sql+=" and a.builderCode =?";
			list.add(customer.getBuilderCode());
		}
		if (StringUtils.isNotBlank(customer.getCheckStatus())) {
			sql += " and a.CheckStatus in " + "('"+customer.getCheckStatus().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			sql += " and a.ConstructStatus in " + "('"+customer.getConstructStatus().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(customer.getStatus())) {
			sql += " and a.Status in " + "('"+customer.getStatus().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(customer.getRegion())) {
			sql += " and r.Code in " + "('"+customer.getRegion().replace(",", "','" )+ "')";
		}
		
		// 增加现场设计师查询条件
		// 张海洋 20200616
		if (StringUtils.isNotBlank(customer.getCustSceneDesi())) {
            sql += " and gxr1.EmpCode = ? ";
            list.add(customer.getCustSceneDesi());
        }
		
		if (customer.getSendJobDateFrom() != null) {
            sql += " and a.SendJobDate >= ? ";
            list.add(customer.getSendJobDateFrom());
        }
		
		if (customer.getSendJobDateTo() != null) {
            sql += " and a.SendJobDate < dateadd(day, 1, ?) ";
            list.add(customer.getSendJobDateTo());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a  order by a.lastupdate desc";
		}
	
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findTotalDelayPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select *from ( select  拖期排名  delayRank,在建排名 onDoRank,a.department2,工程部,count(*) 在建工地," +
				"sum(case when 是否拖期='拖期' then 1 else 0 end) 拖期数,   round(case when count(*)=0 then 0 else " +
				" sum(case when 是否拖期='拖期' then 1 else 0 end)*1.0/count(*) end*100,2)  拖期占比  " +
				" from " +
				" ( select  a.Code 客户编号 ,a.address 楼盘地址 ,a.area 面积 ,ConstructDay 工期 , " +
				" ( case when c.code is null then 'aa' else c.code end ) department2,c.Desc1 工程部 , Projectman 项目经理编号 , " +
				" b.NameChi 项目经理名称 ,ConfirmBegin 实际开工时间 ,BeginComDate 开工令时间 , dateadd(day, ConstructDay+dbo.fGetConstDay(), isnull(BeginComDate, ConfirmBegin)) 合同完工时间 ," +
				" case when ((a.CheckoutDate is not null) and (a.ConfirmBegin is not null)) then isnull(DATEDIFF(day,a.ConfirmBegin,a.CheckoutDate),0) - a.ConstructDay-dbo.fGetConstDay()" +
				" when a.CheckoutDate is null and a.ConfirmBegin is not null then DATEDIFF(day,a.ConfirmBegin,getdate()) - a.ConstructDay-dbo.fGetConstDay() else null end as 拖期天数," +
				" case when   (case when ((a.CheckoutDate is not null) and (a.ConfirmBegin is not null)) then " +
				" isnull(DATEDIFF(day,a.ConfirmBegin,a.CheckoutDate),0) - a.ConstructDay-dbo.fGetConstDay() when a.CheckoutDate is null and a.ConfirmBegin is not null then " +
				" DATEDIFF(day,a.ConfirmBegin,getdate()) - a.ConstructDay-dbo.fGetConstDay() else null end <=0)   or ( isnull(BeginComDate, ConfirmBegin) is null ) then '正常'   " +
				" else '拖期' end 是否拖期   from tCustomer a " +
				" left join tEmployee b on a.ProjectMan = b.Number   " +
				" left join tDepartment2 c on b.Department2 = c.code   " +
				" where a.status = '4' and CheckOutDate is null and a.projectman <> '10000') a " +
			
				" left join  (select * from dbo.getDelayRank())d on d.department2=a.department2 " ;
		

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " group by 工程部 ,a.department2,d.department2,d.拖期排名,d.在建排名 ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();	
		} else {
			sql += "  group by 工程部 ,a.department2,d.department2,d.拖期排名 ,d.在建排名) a order by a.onDoRank,a.delayRank ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public Page<Map<String, Object>> findDelayDetailPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( select  c.code,a.address 楼盘地址 ,a.area 面积 ,ConstructDay 工期 ,s2.note nowspeeddescr,s1.note planspeeddescr,c.Desc1 工程部 ,  b.NameChi 项目经理,ConfirmBegin 实际开工时间 ,BeginComDate 开工令时间 ," +
				" x1.note 施工状态, k.LastUpdate 更新时间,a.PrgRemark 延误说明, dateadd(day, ConstructDay + dbo.fGetConstDay(), ConfirmBegin) 合同完工时间 ," +
				" case when i.prjItem = '16' and i.EndDate is not null then datediff(day, i.PlanEnd, i.Enddate) else" +
				" ( case when datediff(day, i.PlanBegin, i.BeginDate) > datediff(day,i.PlanEnd,getdate()) then datediff(day, i.PlanBegin, i.BeginDate)else" +
				" datediff(day, i.PlanEnd, getdate()) end ) end 节点拖期 , a.CheckoutDate 实际结算时间, " +
				" case when ((a.CheckoutDate is not null) and (a.ConfirmBegin is not null)) then " +
				" isnull(DATEDIFF(day,a.ConfirmBegin,a.CheckoutDate),0) - a.ConstructDay- dbo.fGetConstDay() when a.CheckoutDate is null " +
				" and a.ConfirmBegin is not null then DATEDIFF(day,a.ConfirmBegin,getdate()) - a.ConstructDay-dbo.fGetConstDay() else null end as 拖期天数," +
				" case when (case when ((a.CheckoutDate is not null) and (a.ConfirmBegin is not null)) " +
				" then isnull(DATEDIFF(day,a.ConfirmBegin,a.CheckoutDate),0) - a.ConstructDay-dbo.fGetConstDay() when " +
				" a.CheckoutDate is null and a.ConfirmBegin is not null then DATEDIFF(day,a.ConfirmBegin,getdate()) - a.ConstructDay-dbo.fGetConstDay() else null end <=0) " +
				" or ( isnull(BeginComDate, ConfirmBegin) is null ) then '正常' else '拖期' end 是否拖期    from  tCustomer a     " +
				" left join tEmployee b on a.ProjectMan = b.Number  " +
				" left join tDepartment2 c on b.Department2 = c.code    " +
				" left outer join  txtdm  x1 on x1.cbm=a.ConstructStatus and id='CONSTRUCTSTATUS' " +
				" left  outer join (select CustCode ,max(LastUpdate) LastUpdate from tPrjProg group by CustCode )k  on k.CustCode=a.code  " +
				
				" left join (select b.CustCode,Max(PrjItem) jhprjitem from tprjprog b right join (select MAX(a.planbegin) planbegin,a.CustCode  from " +
				" (select max(planbegin)as planbegin,CustCode ,PrjItem From  tprjprog where planbegin < getdate() group by CustCode ,planbegin,PrjItem) a" +
				" group by a.CustCode )a  on a.CustCode=b.CustCode and a.planbegin=b.PlanBegin group by b.CustCode " +
				" )dd on dd.custcode=a.code" +
				" left join (select MAX(a.PrjItem) prjitem ,a.CustCode from tPrjProg a " +
				" right join ( select  MAX(BeginDate)begindate,CustCode from tPrjProg  group by CustCode " +
				" )b on a.CustCode=b.CustCode and a.BeginDate=b.begindate and a.CustCode=b.CustCode " +
				" group by a.CustCode)sj on a.Code=sj.CustCode" +
				" left join tXTDM s1 on s1.cbm= dd.jhprjitem and s1.ID='PRJITEM'"+
				" left join tXTDM s2 on s2.cbm= sj.prjitem and s2.ID='PRJITEM'"+
				
				" left join ( select  y.CustCode ,y.PrjItem ,y.PlanBegin ,y.BeginDate ,y.PlanEnd ,y.EndDate   from    ( select    max(PK) ProgPK   from      tPrjProg p  " +
				" where p.BeginDate = ( select max(BeginDate)  from  tPrjProg o    where o.CustCode = p.CustCode   )   group by  p.CustCode     ) x ,  tPrjProg y  " +
				" where x.ProgPK = y.PK  ) i on i.CustCode = a.Code  where   a.status = '4' and CheckOutDate is null and a.projectman <> '10000'    " +
				" ";
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and b.department2=?";
			list.add(customer.getDepartment2());
		}
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and a.projectMan =?";
			list.add(customer.getProjectMan());
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.Address like ?";
			list.add("%"+customer.getAddress()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else if(StringUtils.isNotBlank(customer.getDepartment2())||StringUtils.isNotBlank(customer.getProjectMan())){
			sql += " ) a order by  case when a.合同完工时间 is null then 1 else 0 end ,a.合同完工时间 ";
		}else{
			sql += " ) a order by a.code,拖期天数  desc";
		}
		
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,
					Customer customer,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select a.code,a.descr,a.checkMan,a.address,a.remarks,a.lastupdate,a.lastupdatedby,a.Expired,a.actionlog,a.crtdate,a.area,b.NOTE SourceDescr,c.NOTE GenderDescr,d.NOTE StatusDescr, e.NOTE DesignStyleDescr,f.NOTE LayoutDescr," +
				" h.NOTE EndCodeDescr,e3.NameChi CheckManDescr,e1.NameChi DesignManDescr,e2.NameChi BusinessManDescr from tCustomer a  " +
				" left outer join tEmployee e1 on a.DesignMan=e1.Number  " +
				" left outer join tEmployee e2 on a.BusinessMan=e2.Number  " +
				" left outer join tEmployee e3 on a.CheckMan=e3.Number  " +
				" left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' " +
				" left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER'  " +
				" left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS'  " +
				" left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE'  " +
				" left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT'  " +
				" left outer join tXTDM h on a.EndCode=h.CBM and h.ID='CUSTOMERENDCODE'  " +
				" where  1=1  and a.Status in ( 4) and a.ConstructStatus in( 0,1,2,3) and " +
				"" +
				 SqlUtil.getCustRight(uc, "a", 0);
		
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			sql += " and a.ConstructStatus in " + "('"+customer.getConstructStatus().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getCheckMan())){
			sql+=" and a.checkMan =?";
			list.add(customer.getCheckMan());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "   order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.code ";
		}
		
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	
	public void doChengeCheckMan(String custCode,String chengeCheckMan,Date lastUpdate,String lastUpdatedBy){
		
		String sql=" update tCustomer set CheckMan =?,lastUpdate =? ,lastUpdatedBy =?  where Code =?  " ;
		
		this.executeUpdateBySql(sql, new Object[]{chengeCheckMan,lastUpdate,lastUpdatedBy,custCode});

	}
	
	public void updatePreloftsMan(String code,String preloftsMan,String preloftsManDescr,String lastUpdatedBy,String oldPreloftsMan) {
		if((StringUtils.isNotBlank(preloftsMan))&&(StringUtils.isBlank(oldPreloftsMan))){  // !StringUtils.isNotBlank(oldPreloftsMan) 判断写法修改byzjf 20201023
			String sql =" insert into tCustStakeholder(CustCode,Role,EmpCode,LastUpdate,LastUpdatedBy,Expired,ActionLog) " +
					"  values(?,'60',?,getdate(),?,'F','ADD') " ;
			this.executeUpdateBySql(sql, new Object[]{code,preloftsMan,lastUpdatedBy});
			
			String sql1=" insert into tCustStakeholderHis(OperType,Role,OldRole,EmpCode,OldEmpCode,CustCode,LastUpdate,LastUpdatedBy,Expired,ActionLog) " +
					"  values('1','60',null,?,null,?,getdate(),?,'F','ADD') ";
			this.executeUpdateBySql(sql1, new Object[]{preloftsMan,code,lastUpdatedBy});
		}
		if(StringUtils.isNotBlank(preloftsMan)&&StringUtils.isNotBlank(oldPreloftsMan)){
			String sql=" update tCustStakeholder set EmpCode=?,lastupdate = getdate(),lastUpdatedBy=? where CustCode=? and Role='60' ";
			this.executeUpdateBySql(sql, new Object[]{preloftsMan,lastUpdatedBy,code});
			
			String sql1="  insert into tCustStakeholderHis(OperType,Role,OldRole,EmpCode,OldEmpCode,CustCode,LastUpdate,LastUpdatedBy,Expired,ActionLog) " +
					" values('2','60','60',?,?,?,getdate(),?,'F','ADD') ";
			this.executeUpdateBySql(sql1, new Object[]{preloftsMan,oldPreloftsMan,code,lastUpdatedBy});
		}
	}
	
	public void updateCustomer(String custCode,String projectMan,String oldProjectMan,String lastUpdatedBy){
		if(StringUtils.isNotBlank(projectMan)){ //  projectMan!=""&&projectMan!=null 判断改写 byzjf 20201023
			if(StringUtils.isNotBlank(oldProjectMan)){
				String sql=" update tCustStakeholder set EmpCode=? where CustCode=? and Role='20' ";
				this.executeUpdateBySql(sql, new Object[]{projectMan,custCode});
				if(!projectMan.equals(oldProjectMan)){
					String sql1=" insert into tCustStakeholderHis(OperType,Role,OldRole,EmpCode,OldEmpCode,CustCode,LastUpdate," +
							"LastUpdatedBy,Expired,ActionLog) " +
							" values('2','20','20',?,?,?,getdate(),?,'F','ADD')";
					this.executeUpdateBySql(sql1, new Object[]{projectMan,oldProjectMan,custCode,lastUpdatedBy});
				}
			}else{
				String sql=" insert into tCustStakeholder(CustCode,Role,EmpCode,LastUpdate,LastUpdatedBy,Expired,ActionLog) " +
						" values(?,'20',?,getdate(),?,'F','ADD')";
				this.executeUpdateBySql(sql, new Object[]{custCode,projectMan,lastUpdatedBy});
				String sql1="  insert into tCustStakeholderHis(OperType,Role,OldRole,EmpCode,OldEmpCode,CustCode,LastUpdate," +
						"LastUpdatedBy,Expired,ActionLog) " +
						" values('1','20',null,?,?,?,getdate(),?,'F','ADD')";
				this.executeUpdateBySql(sql1, new Object[]{projectMan,oldProjectMan,custCode,lastUpdatedBy});
			}
		}else {
			String sql=" delete from tCustStakeholder where CustCode=? and role='20' ";
			this.executeUpdateBySql(sql, new Object[]{custCode});
			if(StringUtils.isNotBlank(oldProjectMan)){
				String sql1="insert into tCustStakeholderHis(OperType,Role,OldRole,EmpCode,OldEmpCode,CustCode,LastUpdate," +
						"LastUpdatedBy,Expired,ActionLog) " +
						" values('3','','20',?,?,?,getdate(),?,'F','ADD') ";
				this.executeUpdateBySql(sql1, new Object[]{projectMan,oldProjectMan,custCode,lastUpdatedBy});
			}
		}
	}
	
	public String getConstDay() {
		String sql =" select QZ from tXTCS where ID='AddConstDay'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			return (String) list.get(0).get("QZ");
		}
		return "0";
	}
	
	public Integer getCustStakeholderPk(String custCode,String role) {
		String sql =" select PK from tCustStakeholder where custCode= ? and role = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,role});
		if (list!=null && list.size()>0){
			return (Integer)list.get(0).get("PK");
		}
		return null;
	}
}
	
		









