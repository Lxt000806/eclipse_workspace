package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustomerQueryEvt;
import com.house.home.client.service.evt.GetCustomerEvt;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class CustomerDao extends BaseDao {
	
	/**
	 * 客户分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select ";
		if(StringUtils.isNotBlank(customer.getReturnCount())){
			sql+="top "+customer.getReturnCount();
		}
		sql+=" tc.canUseComBaseItem,a.ContractFee,a.isPushCust,a.buildernum,a.Code,a.realAddress,a.PrjProgTempNo,a.CustType,a.ConfirmBegin,tc.Desc1 CustTypeDescr,a.Source,b.NOTE SourceDescr,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,a.Mobile1,a.Mobile2,"
				+ "a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE DesignStyleDescr,a.BeginDate,a.ConstructType,g.NOTE ConstructTypeDescr,a.PlanAmount,"
				+ "a.DocumentNo,a.AgainMan,e3.NameChi AgainManDescr,a.BuilderCode,m.Descr BuilderCodeDescr,m.RegionCode,a.ProjectMan,e4.NameChi ProjectManDescr,e4.Phone ProjectManPhone, j.note CheckStatusDescr,a.CustCheckDate,"
				+ "a.oldCode,a.saleType,pp.minPk,i.note saleTypeDescr,ppt.Descr PrjProgTempNoDescr, a.iswateritemctrl,k.note iswateritemctrldescr,a.IsInitSign, "
				+ "a.constructstatus,a.setdate,a.signdate,d1.Desc1 dept1Descr,d2.Desc1 dept2Descr,tc.type custTypeType,tc.isAddAllInfo,a.perfPk,x1.note Enddescr,x2.note IsPubReturnDescr,tct.ReturnAmount,a.CheckStatus, "
				+ "a.MeasureDate, a.NetChanel, cnc.Descr NetChanelDescr,pf.AchieveDate, "
				+ " case when  xt.QZ='2' and a.saleType='2' and isnull(oc.PayeeCode, '')<>'' and isnull(tp.Code, '')<>'' then oc.PayeeCode " +
				"   when isnull(tct.PayeeCode,'')<>'' then tct.PayeeCode when isnull(a.PayeeCode,'')<>'' " +
				"	then a.PayeeCode when isnull(n.PayeeCode,'')<>'' " +
				" then n.PayeeCode when isnull(q.PayeeCode,'')<>'' then q.PayeeCode else tc.PayeeCode end PayeeCode,tc.CmpnyName,o.Desc2 CmpName, "
				+ "case when n.CustCode is null then '0' else '1' end HasPrjPay,a.VisitDate, " // HasPrjPay: 是否交过工程款，未转施工&第一次交工程款，使用POS收银系统交款时将提示选择收款单位
				+ "case when (exists(select 1 from tCustStakeholder pub_CS  where pub_CS.CustCode=a.Code and pub_CS.EmpCode=? and pub_CS.Role in('00','01')))then '1' else '0' end showTellPhone,"
				+ "case when isnull(q.DesignPayeeCode,'')<>'' then q.DesignPayeeCode else tc.DesignPayeeCode end DesignPayeeCode, "
				+ "a.PrjManRiskFund,a.DesignRiskFund,case when a.perfPk is not null then pf.PayeeCode else a.payeeCode end custPayeeCode,r.QualityFee PrjQualityFee,a.setAdd, p.Descr RegionDescr,  "
				+ "a.PrjDeptLeader, e5.NameChi PrjDeptLeaderName "
				+ " from tCustomer a "
				+ " left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ " left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ " left outer join tBuilder m on a.BuilderCode=m.Code "
				+ " left outer join tPrjProgTemp ppt on ppt.No=a.PrjProgTempNo "
				+ " left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
				+ " left outer join tCustNetCnl cnc on a.NetChanel = cnc.Code "
				+ " left outer join (select min(pk) minPK,CustCode  from tPrjProg group by CustCode )pp on pp.CustCode=a.Code "
				+ " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " left outer join tXTDM g on a.ConstructType=g.CBM and g.ID='CONSTRUCTTYPE' "
				+ " left outer join tXTDM h on a.CustType=h.CBM and h.ID='CUSTTYPE' "
				+ " left outer join tXTDM i on a.saleType=i.CBM and i.ID='SALETYPE' "
				+ " left outer join tXTDM j on a.CheckStatus=j.CBM and j.ID='CheckStatus' "
				+ " left outer join tXTDM k on a.isWaterItemCtrl=k.CBM and k.ID='YESNO' "
				+ " left outer join tRegion p on m.RegionCode=p.Code "
				+ " left outer join tCompany o on p.CmpCode=o.Code "
				+ " left outer join tEmployee e4 on a.ProjectMan = e4.Number "
				+ " left outer join tDepartment1 d1 on d1.Code = e4.Department1 "
				+ " left outer join tDepartment2 d2 on d2.Code = e4.Department2 "
				+ " left join tCusttype tc on tc.code=a.custType "
				+ " left join tXTDM x1 on  a.EndCode=x1.cbm  and x1.id='CUSTOMERENDCODE' "
				+ " left join tCustTax tct on a.Code=tct.CustCode "
				+ " left join tXTDM x2 on tct.IsPubReturn=x2.cbm and x2.id='YESNO' "
				+ " left join ( "
				+ "   select CustCode, max(b.PayeeCode) PayeeCode from tCustPay a"
				+ "   inner join tRcvAct b on a.RcvAct=b.Code"
				+ "   where isnull(b.PayeeCode,'')<>'' and a.Type<>'1' "
				+ "   and a.Date=(select max(Date) from tCustPay in_a, tRcvAct in_b where in_a.CustCode=a.CustCode and in_a.RcvAct=in_b.Code and isnull(in_b.PayeeCode,'')<>'' and in_a.Type<>'1')"
				+ "   group by CustCode"
				+ " ) n on a.Code=n.CustCode "
				+ " left join tCmpCustType q on q.CmpCode=o.Code and q.CustType=a.CustType and q.Expired='F' "
				+ " left join tPerformance pf on pf.pk = a.perfPk "
				+ " left join tWorker r on a.ProjectMan=r.EmpCode and isnull(a.ProjectMan,'')<>'' "
				+ " left join tEmployee e5 on a.PrjDeptLeader = e5.Number "
				+ " left join tcustomer oc on oc.code=a.oldCode "
				+ " left join txtcs xt on xt.id='SECONDSALESPAYEE' "
				+ " left join tTaxPayee tp on tp.Code=oc.PayeeCode and tp.Expired='F'  "
				+ " where 1=1 ";
		list.add(uc.getEmnum());
		if(!"1".equals(customer.getIgnoreCustRight())){
			sql += " and " + SqlUtil.getCustRight(uc, "a", 0);
		}
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and (a.Address like ? or a.realAddress like ? )";
			
			String keyword = SqlUtil.insertPercentSignBeforeFirstDigit(customer.getAddress());
			list.add("%" + keyword + "%");
			list.add("%" + keyword + "%");
		}
		
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and (a.Code like ? or a.address like ?) ";
			list.add("%"+customer.getCode()+"%");
			list.add("%"+customer.getCode()+"%");
		}
		if (StringUtils.isNotBlank(customer.getBuilderCode())) {
			sql += " and a.BuilderCode=? ";
			list.add(customer.getBuilderCode());
		}
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			sql += " and a.ConstructStatus=? ";
			list.add(customer.getConstructStatus());
		}
		if (StringUtils.isNotBlank(customer.getGroupCode())) {
			sql += " and m.GroupCode=? ";
			list.add(customer.getGroupCode());
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(customer.getProjectMan());
		}
		if (StringUtils.isNotBlank(customer.getProjectManDescr())) {
			sql += " and e4.NameChi like ? ";
			list.add("%"+customer.getProjectManDescr()+"%");
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (StringUtils.isNotBlank(customer.getMobile1())) {
			sql += " and (a.Mobile1 like ? or a.Mobile2 like ?) ";
			list.add("%"+customer.getMobile1()+"%");
			list.add("%"+customer.getMobile1()+"%");
		}
		if (StringUtils.isNotBlank(customer.getSource())) {
			sql += " and a.Source=? ";
			list.add(customer.getSource());
		}
		if(StringUtils.isNotBlank(customer.getProjectManDescr())){
			sql+=" and e4.NameChi like ?";
			list.add("%"+customer.getProjectManDescr()+"%");
		}
		if(StringUtils.isBlank(customer.getPurchStatus())){
			if (StringUtils.isNotBlank(customer.getStatus())) {
				String str = SqlUtil.resetStatus(customer.getStatus());
				sql += " and a.status in (" + str + ")";
			}
		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(customer.getPurchStatus())){
			sql+=" and a.Status='4' ";
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			String str = SqlUtil.resetStatus(customer.getCustType());
			sql += " and a.CustType in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getDesignChgDoc())){
			sql+=" and exists ( select 1 from tDesignPicPrg in_a where in_a.custcode = a.code and in_a.status = '4' )";
		}
		if(StringUtils.isNotBlank(customer.getExistsPrePlan())){
			sql+=" and exists (select 1 from tPrePlanArea ina " +
				" left join tFixAreaType inb on ina.FixAreaType=inb.code where inb.IsDefaultArea<>1 and ina.CustCode=a.Code)";
		}
		/*if(StringUtils.isNotBlank(customer.getDesignDemo())){
			sql+=" and not exists(select 1 from tdesignDemo indd where indd.custCode = a.code )";
		}*/
		if (StringUtils.isNotBlank(customer.getEndCode())) {
			sql += " and a.EndCode=? ";
			list.add(customer.getEndCode());
		}
		if (StringUtils.isNotBlank(customer.getPhoneOrConId())) {
			sql += " and (a.Mobile1=? or a.Mobile2=? or a.ConId=? or a.ConId=? or a.Code=?) ";
			list.add(customer.getPhoneOrConId());
			list.add(customer.getPhoneOrConId());
			
			list.add(customer.getPhoneOrConId());
			String conId = customer.getPhoneOrConId();
			if ("0".equals(conId.substring(conId.length()-1, conId.length()))) { // 身份证最后一位0可能代表字母X,因为Pos端不允许输入字母
				conId = conId.substring(0, conId.length()-1) + "X";
			}
			list.add(conId);
			
			String custCode;
			if (customer.getPhoneOrConId().length() <= 6) {
				custCode = "CT" + StringUtils.leftPad(customer.getPhoneOrConId(), 6, '0'); // 客户编号数字部分固定6位
			} else {
				custCode = customer.getPhoneOrConId();
			}
			list.add(custCode);
		}
		if (StringUtils.isNotBlank(customer.getItemPlanCustCode())) {
			sql += " and exists(select 1 from tRegion in_r where m.RegionCode=in_r.Code and CmpCode=(select in_r2.CmpCode from tCustomer in_c  "
                 + " left join tBuilder in_b on in_b.code = in_c.BuilderCode "
                 + " left join tRegion in_r2 on in_r2.code = in_b.regionCode "
                 + " where in_c.code=? ) )  " ;
			list.add(customer.getItemPlanCustCode());
		}
		if (customer.getCrtDateFrom() != null) {
			sql += " and a.CrtDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getCrtDateFrom());
		}
		if (customer.getCrtDateTo() != null) {
			sql += " and a.CrtDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getCrtDateTo());
		}
		if (customer.getVisitDateFrom() != null) {
			sql += " and a.VisitDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getVisitDateFrom());
		}
		if (customer.getVisitDateTo() != null) {
			sql += " and a.VisitDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getVisitDateTo());
		}
		if (StringUtils.isNotBlank(customer.getResrCustCode())) {
			sql += " and not exists(select 1 from tResrCustMapper where CustCode=a.Code and ResrCustCode<>?) ";
			list.add(customer.getResrCustCode());
		}
		if(StringUtils.isNotBlank(customer.getIsInitSign())){
			sql+=" and a.isInitSign = ?";
			list.add(customer.getIsInitSign());
		}
		
        if (StringUtils.isNotBlank(customer.getDepartment1())) {
            sql += " and exists(select 1 from tCustStakeholder m inner join tEmployee te1 on m.EmpCode=te1.Number "
                    + "where m.CustCode=a.Code and m.Role in ('00', '01') ";
            
            if (StringUtils.isNotBlank(customer.getDepartment1())) {
                String str = SqlUtil.resetStatus(customer.getDepartment1());
                sql = sql + " and te1.Department1 in (" + str + ") ";
            }
            
            if (StringUtils.isNotBlank(customer.getDepartment2())) {
                String str = SqlUtil.resetStatus(customer.getDepartment2());
                sql = sql + " and te1.Department2 in (" + str + ") ";
            }
            
            if (StringUtils.isNotBlank(customer.getDepartment3())) {
                String str = SqlUtil.resetStatus(customer.getDepartment3());
                sql = sql + " and te1.Department3 in (" + str + ") ";
            }
            
            sql += " )";
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc,a.Code desc";
		}
		
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 工程进度客户分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findPrjProgPageBySql(
			Page<Map<String, Object>> page, Customer customer,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( " +
				" select " +
				//isnull(twc.waitcheck,0)waitcheck,去掉的字段
				//" isnull(tct.waitmodify,0)waitmodify,isnull(tct.checktimes,0) checktimes,isnull(tct.modifytimes,0)modifytimes," +
				" isnull(tcpt.complainttimes,0)complainttimes  ," +
				/*" convert(varchar(20),case when tr.delrep='0' or tr.delrep is null then 0 else " +
				" isnull(convert(money,tr.allrep)/convert(money,tr.delrep),0)*100 end)+'%' reprate  ,case when tr.delrep='0' or tr.delrep is null " +
				" then 0 else isnull(convert(money,tr.allrep)/convert(money,tr.delrep),0)*100 end orderreprate,tr.allrep deldays," +*/
				" s3.note buildstadescr," +
				" s4.note relcustdescr,sj.prjitem sjprjitem,pi1.Descr nowspeeddescr,c.desc1 department2descr,s1.note planspeeddescr,dd.jhprjitem ,em.department2, " +
				" a.code,a.descr,em.namechi designmandescr,em2.namechi businessmandescr,de.desc1 department3descr, a.mobile1, st.note statusdescr,a.prjprogtempno," +
				" a.signdate ,a.lastupdate,a.lastupdatedby,b.department2 empdepartment2,a.expired,a.actionlog,a.custtype,a.layout," +
				" x1.note constructstatusdescr, a.address , a.area  ,constructday  ,c.desc1  工程部 ,"+
					//--convert(varchar(10),k.lastupdate,120) 更新时间, 去掉的字段
				" a.prgremark ," +
				" a.status,constructstatus,  b.namechi projectmandescr,confirmbegin ,begincomdate  , a.contractfee, ct.desc1 custtypedescr, " +
				" dateadd(day,convert(int,constructday+dbo.fgetconstday()),confirmbegin) 合同完工时间  ,case when sj.prjitem = '16'   " +
				" and sj.enddate is not null then " +
				" datediff(day, sj.planend, sj.enddate)   else (case when datediff(day, sj.planbegin, sj.begindate) >" +
				" datediff(day, sj.planend, getdate()) then datediff(day, sj.planbegin, sj.begindate)  else datediff(day, sj.planend, getdate()) end) " +
				" end delaytime, a.checkoutdate 实际结算时间, " +
				" datediff(day, i.AssessmentStartDate, j.AssessmentEndDate) + 1 " +
                "     - k.HolidayDays - a.ConstructDay - l.QZ Delay, " +
				" case when (datediff(day, i.AssessmentStartDate, j.AssessmentEndDate) + 1 " +
                "     - k.HolidayDays - a.ConstructDay - l.QZ) > 0 then '拖期' " +
                " else '正常' end 是否拖期, h.ConfirmDate CompletionDate, " +
                " dateadd(day, a.ConstructDay + l.QZ - 1, i.AssessmentStartDate) PlannedCompletionDate, " +
				" ct.isPartDecorate,tws.lastsigndate,tws.lastsignworktype12descr, " +
				" isnull(a.ConfirmBegin,'29991231') DefaultOrderBy " + // DefaultOrderBy：默认排序字段，按ConfirmBegin升序排，但是null值需排在最后
				" ,cw.comedate " +
				" from tcustomer a " +
				" left join temployee b on a.projectman = b.number  " +
				" left join tdepartment2 c on b.department2 = c.code   " +
				" left join tcusttype ct on a.custtype = ct.code   " +
				" left outer join temployee em on em.number=a.designman  " +
				" left outer join tdepartment3 de on de.code=em.department3 " +
				" left outer join temployee em2 on em2.number=a.businessman " +
				" left outer join  txtdm  x1 on x1.cbm=a.constructstatus and id='constructstatus' " +
				" left outer join (select * from txtdm where id='customerstatus') st on st.cbm=a.status   " +
				" left join " +
				" (select max(prjitem) jhprjitem,custcode from tprjprog a  " +// --查询计划施工节点
				"   where a.planbegin=(select max(planbegin) from tprjprog  " +
				"      where custcode=a.custcode and planbegin < getdate() )  " +
				"    group by a.custcode) dd " +
				"       on dd.custcode=a.code" +
//				" left join " +
//				" (  select max(pk) pk,custcode from tprjprog a " +// --查询实际施工节点
//				" where a.begindate=(select max(begindate) from tprjprog  where custcode=a.custcode )  group by a.custcode) " +
//				" ppp on ppp.custcode=a.code    " +		
				" outer apply ( " +
          		"   	select max(PK) pk " +
          		" 		from tPrjProg in_a " + 
          		"		where in_a.CustCode=a.Code " +
          		"		and in_a.PlanBegin = (  " +
          		"			select max(in_in_a.PlanBegin) a " +
          		"			from tPrjProg in_in_a " +
          		"			where in_in_a.CustCode = in_a.CustCode and in_in_a.BeginDate = ( " +
				"				select max(in_in_in_a.BeginDate) " +
				"				from tPrjProg in_in_in_a " +
				"				where in_in_in_a.CustCode=in_in_a.CustCode and in_in_in_a.BeginDate < getdate() " +
				"			) " +
				"		) " +
          		" ) ppp " +
				" left join tprjprog sj on sj.pk=ppp.pk   " +
				" left join txtdm s1 on s1.cbm= dd.jhprjitem and s1.id='prjitem' " +
				" left join txtdm s3 on s3.cbm=a.buildsta and s3.id='buildsta'  " +
				" left join txtdm s4 on s4.cbm=a.relcust  and s4.id='relcust'  " +
				/*" left join (select count(*) delrep,  " +//--报备天数
				"    sum(case when buildstatus<>'1' then 1 else 0 end) allrep," +//--停工天数
				"        custcode from tbuilderrep group by custcode) tr on tr.custcode=a.code  " +*/
				/*" left join (select custcode,count(*) checktimes, " +
				"   sum(case when ismodify='1' and modifycomplete='1' then 1 else 0 end) modifytimes," +//--整改完成次数
				"   sum(case when ismodify='1' and modifycomplete='0' then 1 else 0 end) waitmodify " +//--待整改次数
				"   from tprjprogcheck group by custcode )tct on tct.custcode=a.code  " +*/
				" left join (select count(*) complainttimes ,custcode from tcustcomplaint group by custcode )tcpt on tcpt.custcode=a.code"+
				" left join (	select a.CustCode,a.CrtDate lastsigndate,c.Descr lastsignworktype12descr from tWorkSign a "+
				" 				left join tCustWorker b on a.CustWkPk=b.PK "+
				" 				left join tWorkType12 c on b.WorkType12=c.Code "+
				" where a.PK=(select max(PK) from tWorkSign where CustCode=a.CustCode group by CustCode) ) tws on tws.custcode=a.code"+
				" left join tPrjItem1 pi1 on pi1.Code = sj.PrjItem "
				+ " left join ( "
				+ "     select in_a.CustCode, min(in_a.ComeDate) ComeDate "
				+ "     from tCustWorker in_a "
				+ "     left join tWorkType12 in_b on in_b.Code = in_a.WorkType12 "
				+ "     where in_b.BeginCheck = '1' "
				+ "     group by in_a.CustCode "
				+ " ) cw on cw.CustCode = a.Code "
				+ " left join tPrjProg h on h.PrjItem = '16' and h.CustCode = a.Code "
				+ " outer apply ( "
				+ "     select "
				+ "         case when cw.ComeDate is not null then cw.ComeDate "
				+ "              else a.ConfirmBegin "
				+ "         end AssessmentStartDate "
				+ " ) i "
				+ " outer apply ( "
				+ "     select "
				+ "         case when h.ConfirmDate is not null then h.ConfirmDate "
				+ "              when a.CheckOutDate is not null then a.CheckOutDate "
				+ "              else getdate() "
				+ "         end AssessmentEndDate "
				+ " ) j "
				+ " outer apply ( "
				+ "     select count(in_a.Date) HolidayDays "
				+ "     from tCalendar in_a "
				+ "     where in_a.HoliType = '3' "
				+ "         and in_a.Date >= i.AssessmentStartDate "
				+ "         and in_a.Date <= j.AssessmentEndDate "
				+ " ) k "
				+ " left join tXTCS l on l.ID = 'AddConstDay' "
				+ " where 1=1 and  a.Status in ('4','5') and ct.isPartDecorate <> '3' and " +
				 SqlUtil.getCustRight(uc, "a", 0);
		
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.Code=? ";
			list.add( customer.getCode());
		}
		
		if (StringUtils.isNotBlank(customer.getPrjProgTempNo())) {
			String str = SqlUtil.resetStatus(customer.getPrjProgTempNo());
			sql += " and sj.prjitem in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getDesignMan())){
			sql+=" and a.designMan = ?";
			list.add( customer.getDesignMan());
		}
		
		if(StringUtils.isNotBlank(customer.getBusinessMan())){
			sql+=" and a.BusinessMan = ? ";
			list.add(customer.getBusinessMan());
		}
		
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		
		if (StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and b.Department2 in('" + customer.getDepartment2().replace(",", "', '") + "') ";
		}
		
		if (StringUtils.isNotBlank(customer.getStatus())) {
			String str = SqlUtil.resetStatus(customer.getStatus());
			sql += " and a.status in (" + str + ")";
		}
		
		if (StringUtils.isNotBlank(customer.getCustType())) {
			String str = SqlUtil.resetStatus(customer.getCustType());
			sql += " and a.CustType in (" + str + ")";
		}
		
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and a.ProjectMan = ? ";
			list.add(customer.getProjectMan());
		}
		if(StringUtils.isNotBlank(customer.getConstructType())){
			sql+=" and a.ConstructType = ? ";
			list.add(customer.getConstructType());
		}
		
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			String str = SqlUtil.resetStatus(customer.getConstructStatus());
			sql += " and a.ConstructStatus in (" + str + ")";
		}
		
		if (StringUtils.isNotBlank(customer.getBuildStatus())) {
			String str = SqlUtil.resetStatus(customer.getBuildStatus());
			sql += " and br.buildStatus in (" + str + ")";
		}
		
		if ("F".equals(customer.getExpired())) {
			sql +=" and not exists (select 1 from tBuilder where IsPrjSpc='1' and PrjLeader<>? and code=a.BuilderCode) ";
			list.add(uc.getEmnum());
		}
		
		// 增加【开工日期】（从…到…）查询条件 --add by zb
		if(null != customer.getConfirmBeginFrom()){
			sql += " and a.ConfirmBegin >= ? ";
			list.add(customer.getConfirmBeginFrom());
		}
		if(null != customer.getConfirmBeginTo()){
			sql += " and a.ConfirmBegin <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getConfirmBeginTo()).getTime()));
		}
		
		if (StringUtils.isNotBlank(customer.getAcceptedPrjItem())) {
            sql += " and exists (select 1 from tPrjProg in_a " +
            	   "     where in_a.CustCode = a.Code and in_a.PrjItem = ? and in_a.ConfirmDate is not null) ";
            list.add(customer.getAcceptedPrjItem());
        }
		
		if (StringUtils.isNotBlank(customer.getNotAcceptedPrjItem())) {
            sql += " and exists (select 1 from tPrjProg in_a " +
            	   "      where in_a.CustCode = a.Code and in_a.PrjItem = ? and in_a.ConfirmDate is null) ";
            list.add(customer.getNotAcceptedPrjItem());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			if("reprate".equals(page.getPageOrderBy())){
				sql += ") a order by orderRepRate "  + " "
						+ page.getPageOrder();
			}else{
				sql += ") a order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			}
		} else {
			sql += ") a order by a.DefaultOrderBy ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	
	}
	
	/**
	 * 客户分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findSoftNotAppQueryPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select f.code,f.address,f.ConfirmBegin,(case when f.CustType='2' then f.DesignMan else g.EmpCode end ) RzDesignManCode" +
				",(case when f.CustType='2' then e1.Phone else e2.Phone end ) empphone" +
				",(case when f.CustType='2' then e1.namechi else e2.namechi end ) rzdesignmandescr,ic.chgConfirmDate," +
				"f.AppSoftRemark" + //增加软装未下单说明 add by zb
				" from tCustomer f " +
				" left join tCustStakeHolder g on g.CustCode=f.Code and g.Role='50'" +
				" left join tEmployee e1 on e1.Number = f.DesignMan" +
				" left join tEmployee e2 on e2.Number= g.EmpCode" +
				" left join (select max(confirmdate)chgConfirmDate,CustCode from tItemChg group by CustCode)ic on f.Code=ic.CustCode"+
				" where f.Status='4' and " +
				" exists( " ;
				
		
		if(StringUtils.isNotBlank(customer.getItemType2())){
			sql+=" select a.PK,a.Qty,a.SendQty,sum(isnull(case when b.Type='S' then c.qty else 0 end,0)) AppQty" +
					" from titemreq  a " +
					" left join tItemAppDetail c on a.PK=c.ReqPK" +
					" left join tItemApp b on c.No=b.no and b.Status in  ('CONFIRMED','CONRETURN','OPEN') " +
					" left join tItem d on d.Code=a.ItemCode " +
					" where a.ItemType1='rz' and d.ItemType2=?  and a.CustCode=f.code and a.Qty>0 and d.isFee<>'1'" +
					" group by a.PK,a.Qty,a.SendQty ,d.ItemType2 " +
					" having a.Qty-a.SendQty-sum(isnull(case when b.Type='S' then c.qty else 0 end,0)) > 0)" +
					" and exists(select 1 from tItemApp where CustCode=f.Code and ItemType1='RZ' and Status<>'CANCEL' )" ;
			list.add(customer.getItemType2());
		}else{
			sql+=" select a.PK,a.Qty,a.SendQty,sum(isnull(case when b.Type='S' then c.qty else 0 end,0)) AppQty" +
					" from titemreq  a " +
					" left join tItemAppDetail c on a.PK=c.ReqPK" +
					" left join tItemApp b on c.No=b.no and b.Status in  ('CONFIRMED','CONRETURN','OPEN') " +
					" left join tItem d on d.Code=a.ItemCode " +
					" where a.ItemType1='rz'  and a.CustCode=f.code and a.Qty>0 and d.isFee<>'1'" +
					" group by a.PK,a.Qty,a.SendQty" +
					" having a.Qty-a.SendQty-sum(isnull(case when b.Type='S' then c.qty else 0 end,0)) > 0)" +
					" and exists(select 1 from tItemApp where CustCode=f.Code and ItemType1='RZ' and Status<>'CANCEL' )" ;
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "order by f." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by f.ConfirmBegin";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
		
	}
			
	
	
	/**
	 * 客户列表接口
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_client(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.CustType,a.ConfirmBegin,h.NOTE CustTypeDescr,a.Source,b.NOTE SourceDescr,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,a.Mobile1,a.Mobile2,"
				+ "a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE DesignStyleDescr,a.BeginDate,a.ConstructType,g.NOTE ConstructTypeDescr,a.PlanAmount,"
				+ "a.DocumentNo,a.AgainMan,e3.NameChi AgainManDescr,a.BuilderCode,m.Descr BuilderCodeDescr,a.ProjectMan,e4.NameChi ProjectManDescr,e4.Phone ProjectManPhone,a.IsInitSign "
				+ "from tCustomer a "
				+ " left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ " left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ " left outer join tBuilder m on a.BuilderCode=m.Code "
				+ " left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
				+ " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " left outer join tXTDM g on a.ConstructType=g.CBM and g.ID='CONSTRUCTTYPE' "
				+ " left outer join tXTDM h on a.CustType=h.CBM and h.ID='CUSTTYPE' "
				+ " left outer join tEmployee e4 on a.ProjectMan = e4.Number "
				+ " Left join tPrjCheck pc on a.Code=pc.CustCode "
				+ " where (a.status='4' ";
				if(StringUtils.isEmpty(customer.getExcludeComplete()) || "0".equals(customer.getExcludeComplete())){
					sql += " or (a.Status='5' and a.EndCode='3' and (DateDiff(day,a.EndDate,GetDate())<=10 "
						+ " or DateDiff(day, pc.confirmdate,Getdate())<=10)) ";
				}
				sql += " ) ";

		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and ( a.ProjectMan=? ";
			list.add(customer.getProjectMan());
			if("1".equals(customer.getSaleIndependence())){
				sql += " or ( a.OldCode is not null and a.OldCode <> '' and exists ( "
					 + " select 1 from tCustomer in_a where in_a.Code = a.OldCode and in_a.Status = '4' and in_a.ProjectMan=? "
					 + " )) ";
				list.add(customer.getProjectMan());
			}
			sql += " ) ";
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and a.Code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (StringUtils.isNotBlank(customer.getMobile1())) {
			sql += " and (a.Mobile1=? or a.Mobile2=?) ";
			list.add(customer.getMobile1());
			list.add(customer.getMobile1());
		}
		if (StringUtils.isNotBlank(customer.getSource())) {
			sql += " and a.Source=? ";
			list.add(customer.getSource());
		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 客户列表接口(任务申请用)
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_job(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.CustType,a.ConfirmBegin,h.NOTE CustTypeDescr,a.Source,b.NOTE SourceDescr,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,a.Mobile1,a.Mobile2,"
				+ "a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE DesignStyleDescr,a.BeginDate,a.ConstructType,g.NOTE ConstructTypeDescr,a.PlanAmount,"
				+ "a.DocumentNo,a.AgainMan,e3.NameChi AgainManDescr,a.BuilderCode,m.Descr BuilderCodeDescr,a.ProjectMan,e4.NameChi ProjectManDescr,e4.Phone ProjectManPhone "
				+ "from tCustomer a "
				+ " left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ " left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ " left outer join tBuilder m on a.BuilderCode=m.Code "
				+ " left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
				+ " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " left outer join tXTDM g on a.ConstructType=g.CBM and g.ID='CONSTRUCTTYPE' "
				+ " left outer join tXTDM h on a.CustType=h.CBM and h.ID='CUSTTYPE' "
				+ " left outer join tEmployee e4 on a.ProjectMan = e4.Number "
				+ " Left join tPrjCheck pc on a.Code=pc.CustCode "
				+ " where (a.status='4' "
				+ " or (a.Status='5' and a.EndCode='3') )";

		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and (a.ProjectMan=? or (a.SaleType='2' and exists(select 1 from tCustomer where ProjectMan = ? and Code = a.OldCode))) ";
			list.add(customer.getProjectMan());
			list.add(customer.getProjectMan());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(customer.getStatus())) {
			sql += "and a.status=? ";
			list.add(customer.getStatus());
		}
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and a.Code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (StringUtils.isNotBlank(customer.getMobile1())) {
			sql += " and (a.Mobile1=? or a.Mobile2=?) ";
			list.add(customer.getMobile1());
			list.add(customer.getMobile1());
		}
		if (StringUtils.isNotBlank(customer.getSource())) {
			sql += " and a.Source=? ";
			list.add(customer.getSource());
		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 根据客户权限获取客户列表接口
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_custRight(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.Descr,a.Address,a.ContractFee,a.layout,f.Note layoutDescr,a.Area,a.Status,b.NOTE StatusDescr,a.mobile1, "
				+ "a.DesignMan,d.NameChi DesignManDescr,a.BusinessMan,c.NameChi BusinessManDescr,a.CustType,a.LastUpdate,a.ConfirmBegin,"
				+ "a.ContainMain,a.ContainMainServ,a.ContainSoft,a.ContainInt,a.DocumentNo,a.ProjectMan,a.SignDate,a.CheckStatus,a.RealAddress, "
				+ "ACOS(SIN( ISNULL( e.Latitude , 0 ) / ( 180/PI() ) )*SIN( ? / ( 180/PI() ) ) + COS( ISNULL( e.Latitude , 0 ) / ( 180/PI() ) )*COS( ? / ( 180/PI() ) )*COS( ? / ( 180/PI() ) - ISNULL( e.longitude , 0 ) / ( 180/PI() ) ) )*6371.004 distance "
				+ " from tCustomer a  "
				+ " left outer join tEmployee d on d.Number=a.DesignMan "
				+ " left outer join tEmployee c on c.Number=a.BusinessMan "
				+ " left outer join tXTDM b on a.Status=b.CBM and b.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " left outer join tBuilder e on a.BuilderCode = e.Code "
				+ " left join tCusttype ct on ct.Code = a.CustType "
				+ " where a.Expired='F' and a.ConstructStatus <>'7' and "
				+ SqlUtil.getCustRight(uc, "a", 0);
		if(customer.getLatitude() == null ) list.add(0.0); else list.add(customer.getLatitude());
		if(customer.getLatitude() == null ) list.add(0.0); else list.add(customer.getLatitude());
		if(customer.getLontitude() == null ) list.add(0.0); else list.add(customer.getLontitude());
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and (a.Address like ? or a.RealAddress like ?) ";
			list.add("%" + customer.getAddress() + "%");
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getStakeholder())) {
			sql += " and exists(select 1 from tCustStakeholder m inner join tEmployee n on m.EmpCode=n.Number"
					+ " where m.custCode=a.code and n.nameChi like ?) ";
			list.add("%" + customer.getStakeholder() + "%");
		}
		if ("0".equals(customer.getHaveGd())) {
			sql += " and a.status in ('4') ";
		}
		if ("1".equals(customer.getHaveGd())) {
			sql += " and a.status in ('4','5') ";
		}
		if("0".equals(customer.getSaleIndependence())){
			sql += " and ct.IsAddAllInfo='1' ";
		}
		if(StringUtils.isNotBlank(customer.getPrjItem())){
			sql += " and a.Code in ( select custCode from tPrjProg where PrjItem = ? and BeginDate is not null and EndDate is null and ConfirmDate is null )";
			list.add(customer.getPrjItem());
		}
		if(StringUtils.isNotBlank(customer.getStakeholderRoll())){
			sql += " and exists(select 1 from tCustStakeholder where CustCode=a.Code and Role = ?)";
			list.add(customer.getStakeholderRoll());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2Descr())){
			sql += " and exists(select 1 from tCustStakeholder tcs left join tEmployee te on te.Number = tcs.EmpCode left join tDepartment2 td2 on td2.Code = te.Department2 "
			     + " where tcs.CustCode=a.Code and td2.Desc1=? )";
			list.add(customer.getDepartment2Descr());
		}
		if(StringUtils.isNotBlank(customer.getWorkType12())){
			sql +=" and a.Code in (select in_a.CustCode from tWorkSign in_a" 
					+" left join tCustWorker in_b on in_a.CustWkPk = in_b.PK "
					+" where DateDiff(dd,CrtDate,getdate())=0 and in_b.WorkType12= ? )";
			list.add(customer.getWorkType12());
		}
		sql += " ) a where 1=1 ";
		if(customer.getDistance()!=null && customer.getDistance() > 0){
			sql += " and a.distance < ? ";
			list.add(customer.getDistance());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		}else{
			sql += " order by a.distance asc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 搜寻客户分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_code(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.Descr,a.Address,a.ContractFee,a.layout,f.Note layoutDescr,a.Area,a.Status,b.NOTE StatusDescr,a.mobile1, "
				+ "a.DesignMan,d.NameChi DesignManDescr,a.BusinessMan,c.NameChi BusinessManDescr,a.CustType,a.LastUpdate,"
				+ "a.ContainMain,a.ContainMainServ,a.ContainSoft,a.ContainInt,a.DocumentNo,a.ProjectMan,a.SignDate,a.CheckStatus "
				+ " from tCustomer a  "
				+ " left outer join tEmployee d on d.Number=a.DesignMan "
				+ " left outer join tEmployee c on c.Number=a.BusinessMan "
				+ " left outer join tXTDM b on a.Status=b.CBM and b.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " where a.Expired='F' and "
				+ SqlUtil.getCustRight(uc, "a", 0);

		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getStatus())) {
			String str = SqlUtil.resetStatus(customer.getStatus());
			sql += " and a.status in (" + str + ")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc,a.Code desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 客户信息查询
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_xxcx(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sqlhead = "select * from (select isnull(a.tax,0) tax,txt.note HouseTypeDescr,tr.descr regionDescr,a.Code,a.Source,h.NOTE EndCodeDescr,a.EndRemark,b.NOTE SourceDescr,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,"//,a.Mobile1,a.Mobile2,
			+ "a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr1,dbo.fGetEmpNameChi(a.Code,'01') BusinessManDescr,"
			+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE DesignStyleDescr,a.EndDate,a.SetDate,a.SignDate,a.CheckOutDate,a.CustCheckDate,"
			+ "a.ContractFee+isnull(a.tax,0) contractFee,a.DesignFee,a.MeasureFee,a.DrawFee,a.ColorDrawFee,ps.Desc2 PositionDescr,"
			+ "a.ManageFee*ContainBase ManageFee,a.ConsArea,a.IsInternal,a.ConstructType,p.note ConstructTypeDescr,"
			+ "a.BaseFee*ContainBase BaseFee,a.BaseDisc*ContainBase BaseDisc,dbo.fGetNowPrjItemDescr(a.Code) NowPrjItemDescr,"
			+ "a.BaseFee_Dirct*ContainBase BaseFee_Dirct,a.BaseFee_Comp*ContainBase BaseFee_Comp,"
			+ "a.MainFee*a.ContainMain MainFee,a.MainDisc*a.ContainMain MainDisc, a.VisitDate, "
			+ "a.SoftFee*a.ContainSoft SoftFee,a.SoftDisc*a.ContainSoft SoftDisc,a.SoftOther*a.ContainSoft SoftOther,"
			+ "a.IntegrateFee*a.ContainInt IntegrateFee,a.IntegrateDisc*a.ContainInt IntegrateDisc,a.MainServFee*a.ContainMainServ MainServFee,"
			+ "a.CupboardFee*a.ContainCup CupboardFee,a.CupBoardDisc*a.ContainCup CupBoardDisc,a.Gift,a.DocumentNo, "
			+ "dpt1.Desc1 DesiDpt1,dpt2.Desc1 DesiDpt2,dpt3.Desc1 DesiDpt3,a.CustType,n.NOTE CustTypeDescr,"
			+ "cast(dbo.fGetDept1Descr(a.Code,'01') as nvarchar(100)) BusiDpt1,cast(dbo.fGetDept2Descr(a.Code,'01') as nvarchar(100)) BusiDpt2,dpt33.Desc1 BusiDpt3,"
			+ "cast(dbo.fGetEmpNameChi(a.Code,'50') as nvarchar(1000)) SoftDesignDescr,a.AgainMan,cast(dbo.fGetEmpNameChi(a.Code,'24') as nvarchar(255)) AgainManDescr, "//,e3.NameChi AgainManDescr 翻单员列，如果有两个翻单员，要都显示 modify by zb on 20200424
			+ "a.SaleType,o.NOTE SaleTypeDescr,a.SoftFee_WallPaper*a.ContainSoft SoftFee_WallPaper,tc.Desc2 cmpName,"
			+ "a.SoftFee_Curtain*a.ContainSoft SoftFee_Curtain,a.SoftFee_Light*a.ContainSoft SoftFee_Light,"
			+ "a.SoftFee_Furniture*a.ContainSoft SoftFee_Furniture,a.SoftFee_Adornment*a.ContainSoft SoftFee_Adornment,"
	        + "a.BuilderCode,m.Descr BuilderDescr,e4.NameChi ProjectManDescr,dptprj.Desc1 ProjectManDpt2,"
	        + "isnull(bc.jzzj,0) Jzzj,isnull(ic.zczj,0) Zczj,isnull(ic.fwzj,0) Fwzj,isnull(ic.rzzj,0) Rzzj,isnull(ic.jczj,0) Jczj, isnull(ic.CupboardChg, 0) CupboardChg, "
	        + "a.LongFee,a.NetChanel,q.Descr NetChanelDescr,isnull(tt.DelivDate,m.DelivDate) DelivDate,x1.NOTE BuilderTypeDescr,"
	        + "case when ts.SignDate is null then '否' else '是' end IsCq, u.YearMonth SignDateFirst,tp.hasPay,a.canCancel," +
	        " case when isNUll(a.cancancel,0) ='1' then '是' else '否' end cancanceldescr,a.ConfirmBegin,a.BuilderNum,tpe.Descr CmpnyName, " +
	        " ehty.nameChi htydescr,exgt.nameChi xgtDescr,exc.nameChi xcDescr,eZCGJ.nameChi mainStewardDescr,a.StdDesignFee, " +
	        " a.ContractDay,isnull(bc.jzzj,0)+isnull(ic.SumChgAmount,0)+isnull(cfc.SumChgAmount,0) as zjxje,r.Note relCustDescr,a.FirstSignDate, "
	        + "a.RealDesignFee, dbo.fGetEmpNameChi(a.Code, '11') IntegrationDesigner, dbo.fGetEmpNameChi(a.Code, '61') CupboardDesigner "; //增加StdDesignFee标准设计费、ContractDay合同工期 add by zb
		String sqltxt = " from tCustomer a "
			+ " left outer join tEmployee e1 on a.DesignMan=e1.Number "
			+ " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
			+ " left outer join tEmployee e3 on a.AgainMan=e3.Number "
			+ " left outer join tEmployee e4 on a.ProjectMan=e4.Number "
			+ " left outer join tDepartment1 dpt1 on dpt1.Code=e1.department1 "
			+ " left outer join tDepartment2 dpt2 on dpt2.Code=e1.department2 "
			+ " left outer join tDepartment3 dpt3 on dpt3.Code=e1.Department3 "
	        + " left outer join tDepartment2 dpt22 on dpt22.Code=e2.department2 "
	        + " left outer join tDepartment3 dpt33 on dpt33.Code=e2.Department3 "
	        + " left outer join tDepartment2 dptprj on dptprj.Code=e4.department2 "
			+ " left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
			+ " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
			+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
			+ " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
			+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
			+ " left outer join tXTDM h on a.EndCode=h.CBM and h.ID='CUSTOMERENDCODE' "
			+ " left outer join tBuilder m on a.BuilderCode=m.Code "
			+ " left outer join tXTDM xt on m.BuilderType=xt.CBM and xt.ID='BUILDERTYPE' " +
			" left join tRegion tr on tr.code=m.regioncode " +
			" left join tCompany tc on tc.code=tr.cmpCode " +
			" left join tXTDM txt on txt.cbm=m.housetype and txt.id='HOUSETYPE' "
			+ " left outer join tXTDM n on a.CustType = n.CBM and n.ID = 'CUSTTYPE' "
	        + " left outer join tXTDM o on a.SaleType = o.CBM and o.ID = 'SALETYPE' "
			
	        + " left outer join tXTDM p on a.ConstructType = p.CBM and p.ID = 'CONSTRUCTTYPE' "
	        + " left outer join tCustNetCnl q on a.NetChanel = q.Code "
	        + " left outer join tPosition ps on e1.Position=ps.Code "
	        + " left outer join (select bc.CustCode,sum(bc.Amount) jzzj from tBaseItemChg bc "
	        + " where bc.Status='2' group by bc.CustCode) bc on a.Code=bc.CustCode "
	        + " left outer join (select ic.CustCode,"
	        + " sum(case when ic.ItemType1='ZC' and ic.IsService='0' then ic.Amount else 0 end) zczj,"
	        + " sum(case when ic.ItemType1='ZC' and ic.IsService='1' then ic.Amount else 0 end) fwzj,"
	        + " sum(case when ic.ItemType1='RZ' then ic.Amount else 0 end) rzzj,"
	        + " sum(case when ic.ItemType1='JC' and ic.IsCupboard='0' then ic.Amount else 0 end) jczj, "
	        + " sum(case when ic.ItemType1='JC' and ic.IsCupboard='1' then ic.Amount else 0 end) CupboardChg, "
	        + " sum(Amount) SumChgAmount"
	        + " from tItemChg ic where ic.Status='2' group by ic.CustCode) ic on a.Code=ic.CustCode "
	        + " left join (select ts.CustCode,min(ts.SignDate) SignDate from tAgainSign ts group by ts.CustCode) ts on a.Code=ts.CustCode "
	        + " left  join (select sum(ChgAmount) SumChgAmount,CustCode "
			+ " from tConFeeChg where status='CONFIRMED' group by CustCode ) cfc on cfc.CustCode=a.Code "
	        /*+ " left join (select bn.BuilderNum,bn.BuilderDelivCode,bd.BuilderCode,bd.DelivDate,tb.Descr,tc.Code "
			+ " from tBuilderDeliv bd "
			+ " inner join tBuilder tb on bd.BuilderCode=tb.Code "
			+ " inner join tBuilderNum bn on bn.BuilderDelivCode=bd.Code "
			+ " inner join tcustomer tc on bd.BuilderCode=tc.BuilderCode "
			+ " where bn.BuilderNum=substring(replace(tc.Address,tb.Descr,''),1,charindex('#',replace(tc.Address,tb.Descr,''))) "
			+ " ) tt on a.code=tt.code "*/
			+"left join (" +
			" select  a.BuilderNum,b.BuilderCode,b.DelivDate,b.builderType from tbuildernum a " +
			" left join tBuilderDeliv b on b.Code=a.BuilderDelivCode" +
			" and a.expired ='F' ) tt on a.BuilderNum=tt.BuilderNum and a.BuilderCode=tt.BuilderCode " +
			" left join txtdm x1 on x1.cbm=tt.builderType and x1.ID='BUILDERTYPE' " +
			
			" left join  ( select  max(a.PK)pk,a.CustCode from tCustStakeholder a where role='04' group by a.custCode) xgt on xgt.custCode=a.Code" +
			" left join tCustStakeholder cXGT on cXGT.pk=xgt.pk" +
			" left join temployee eXGT on eXGT.number=cXGT.empCode " +
			
			" left join  ( select  max(a.PK)pk,a.CustCode from tCustStakeholder a where role='03' group by a.custCode) hty on hty.custCode=a.Code" +
			" left join tCustStakeholder cHTY on cHTY.pk=hty.pk" +
			" left join temployee eHTY on eHTY.number=cHTY.empCode " +
			
			" left join  ( select  max(a.PK)pk,a.CustCode from tCustStakeholder a where role='34' group by a.custCode) zcgj on zcgj.custCode=a.Code" +
			" left join tCustStakeholder cZCGJ on cZCGJ.pk=zcgj.pk" +
			" left join temployee eZCGJ on eZCGJ.number=cZCGJ.empCode " +
			
			" left join  ( select  max(a.PK)pk,a.CustCode from tCustStakeholder a where role='63' group by a.custCode) xc on xc.custCode=a.Code" +
			" left join tCustStakeholder cxc on cxc.pk=xc.pk" +
			" left join temployee exc on exc.number=cxc.empCode " 
			 
			+ " left join (select custcode,isnull(sum(Amount),0) haspay from tCustPay group by CustCode) tp on a.code=tp.custcode "
			+ " left join ( select a.CmpnyName,a.CustType,b.Code from tCmpCustType a left join tRegion b on a.CmpCode=b.CmpCode)cct on cct.CustType=a.CustType and tr.code=cct.Code "
			+ " left join tTaxPayee tpe on a.PayeeCode=tpe.Code "
			+ " left join tXTDM r on a.RelCust=r.CBM and r.ID='RELCUST' "
			+ " outer apply ( "
			+ "     select cast(in_a.Y as nchar(4)) + '-' + replace(str(in_a.M, 2), ' ', '0') YearMonth "
			+ "     from tPerfCycle in_a "
			+ "     left join tPerformance in_b on in_b.PerfCycleNo = in_a.No "
			+ "     where in_b.PK = ( "
			+ "             select min(in1_a.PK) "
			+ "             from tPerformance in1_a "
			+ "             where in1_a.Expired = 'F' "
			+ "                 and in1_a.CustCode = a.Code) "
			+ " ) u "
	        + " where 1=1 and "
			+ SqlUtil.getCustRight(uc, "a", 0);  
		// 部门、干系人、角色不为空，限制干系人表有相应记录
		if (StringUtils.isNotBlank(customer.getDepartment1())
				|| StringUtils.isNotBlank(customer.getRole())
				|| StringUtils.isNotBlank(customer.getEmpCode())) {
			sqltxt = sqltxt
					+ " and exists(select 1 from tCustStakeholder m inner join tEmployee te1 on m.EmpCode=te1.Number "
					+ "where m.CustCode=a.Code ";
			if (StringUtils.isNotBlank(customer.getDepartment1())) {
				String str = SqlUtil.resetStatus(customer.getDepartment1());
				sqltxt = sqltxt + " and te1.Department1 in (" + str +") ";
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				String str = SqlUtil.resetStatus(customer.getDepartment2());
				sqltxt = sqltxt + " and te1.Department2 in (" + str +") ";
			}
			if (StringUtils.isNotBlank(customer.getDepartment3())) {
				String str = SqlUtil.resetStatus(customer.getDepartment3());
				sqltxt = sqltxt + " and te1.Department3 in (" + str +") ";
			}
			if (StringUtils.isNotBlank(customer.getRole())) {
				sqltxt = sqltxt + " and m.Role='" + customer.getRole() + "' ";
			}
			if (StringUtils.isNotBlank(customer.getEmpCode())) {
				sqltxt = sqltxt + " and m.EmpCode='" + customer.getEmpCode() + "' ";
			}
			sqltxt = sqltxt + " )";

		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sqltxt += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(customer.getStatus())) {
			String str = SqlUtil.resetStatus(customer.getStatus());
			sqltxt += " and a.status in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getEndCode())) {
			String str = SqlUtil.resetStatus(customer.getEndCode());
			sqltxt += " and a.endCode in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			String str = SqlUtil.resetStatus(customer.getCustType());
			sqltxt += " and a.custType in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sqltxt += " and a.Code = ? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sqltxt += " and a.Address like ? ";
			list.add("%" + customer.getAddress().trim() + "%");
		}
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sqltxt += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(customer.getSource())) {
			sqltxt += " and a.Source=? ";
			list.add(customer.getSource());
		}
		if (StringUtils.isNotBlank(customer.getNetChanel())) {
			sqltxt += " and a.NetChanel=? ";
			list.add(customer.getNetChanel());
		}
		if (StringUtils.isNotBlank(customer.getConstructType())) {
			sqltxt += " and a.ConstructType=? ";
			list.add(customer.getConstructType());
		}
		if (customer.getSignDateFrom() != null) {
			sqltxt += " and a.signDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getSignDateFrom());
		}
		if (customer.getSignDateTo() != null) {
			sqltxt += " and a.signDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getSignDateTo());
		}
		if (customer.getSetDateFrom() != null) {
			sqltxt += " and a.SetDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getSetDateFrom());
		}
		if (customer.getSetDateTo() != null) {
			sqltxt += " and a.SetDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getSetDateTo());
		}
		if (customer.getCrtDateFrom() != null) {
			sqltxt += " and a.CrtDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getCrtDateFrom());
		}
		if (customer.getCrtDateTo() != null) {
			sqltxt += " and a.CrtDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getCrtDateTo());
		}
		if (customer.getEndDateFrom() != null) {
			sqltxt += " and a.EndDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getEndDateFrom());
		}
		if (customer.getEndDateTo() != null) {
			sqltxt += " and a.EndDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getEndDateTo());
		}
		if (customer.getCheckOutDateFrom() != null) {
			sqltxt += " and a.CheckOutDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getCheckOutDateFrom());
		}
		if (customer.getCheckOutDateTo() != null) {
			sqltxt += " and a.CheckOutDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getCheckOutDateTo());
		}
		if (customer.getCustCheckDateFrom() != null) {
			sqltxt += " and a.CustCheckDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getCustCheckDateFrom());
		}
		if (customer.getCustCheckDateTo() != null) {
			sqltxt += " and a.CustCheckDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getCustCheckDateTo());
		}
		if (customer.getVisitDateFrom() != null) {
		    sqltxt += " and a.VisitDate>=CONVERT(VARCHAR(10),?,120) ";
            list.add(customer.getVisitDateFrom());
        }
        if (customer.getVisitDateTo() != null) {
            sqltxt += " and a.VisitDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
            list.add(customer.getVisitDateTo());
        }
        if (customer.getFirstSignDateFrom() != null) {
		    sqltxt += " and a.FirstSignDate>=? ";
            list.add(customer.getFirstSignDateFrom());
        }
        if (customer.getFirstSignDateTo() != null) {
            sqltxt += " and a.FirstSignDate<? ";
            list.add(DateUtil.addInteger(customer.getFirstSignDateTo(),Calendar.DATE, 1));
        }
        if (StringUtils.isNotBlank(customer.getSignDateFirstFrom())) {
		    sqltxt += " and u.YearMonth >= ? ";
            list.add(customer.getSignDateFirstFrom());
        }
        
        if (StringUtils.isNotBlank(customer.getSignDateFirstTo())) {
            sqltxt += " and u.YearMonth <= ? ";
            list.add(customer.getSignDateFirstTo());
        }
        
		if (StringUtils.isNotBlank(customer.getCode())) {
			sqltxt += " and a.Code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getGroupCode())) {
			sqltxt += " and m.groupCode=? ";
			list.add(customer.getGroupCode());
		}
		if (StringUtils.isNotBlank(customer.getBuilderCode())) {
			sqltxt += " and a.builderCode=? ";
			list.add(customer.getBuilderCode());
		}
		if(StringUtils.isNotBlank(customer.getCanCancel())){
			sqltxt+=" and a.cancancel=? ";
			list.add(customer.getCanCancel());
		}
		if(StringUtils.isNotBlank(customer.getRegion())){
			String str = SqlUtil.resetStatus(customer.getRegion());
			sqltxt += " and m.regioncode in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getHouseType())){
			sqltxt+=" and m.HouseType = ? ";
			list.add(customer.getHouseType());
		}
		if(StringUtils.isNotBlank(customer.getViewAll())){
			sqltxt+=" and a.status in ('4','5')";
		}
		if(StringUtils.isNotBlank(customer.getSoftTokenNo())){
			sqltxt+=" and a.softTokenNo like ?";
			list.add("%" + customer.getSoftTokenNo().trim() + "%");
		}
		if (StringUtils.isNotBlank(customer.getRelCust())) {
			String str = SqlUtil.resetStatus(customer.getRelCust());
			sqltxt += " and a.relCust in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getDoExcelBefore())) {
			
			return this.findPageBySql(page, "select a.code "+sqltxt, list.toArray());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sqltxt += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sqltxt += ") a order by a.Code";
		}
		return this.findPageBySql(page, sqlhead+sqltxt, list.toArray());
	}

	/**
	 * 添加客户存储过程
	 * 
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result saveForProc(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pInserttCustomer_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getCustType());
			call.setString(2, customer.getSource());
			call.setString(3, customer.getDescr());
			call.setString(4, customer.getGender());
			call.setString(5, customer.getBuilderCode());
			call.setString(6, customer.getAddress());
			call.setString(7, customer.getLayout());
			call.setInt(8, customer.getArea());
			call.setString(9, customer.getDesignStyle());
			call.setString(10, customer.getMobile1());
			call.setString(11, customer.getMobile2());
			call.setString(12, customer.getQq());
			call.setString(13, customer.getEmail2());
			call.setString(14, customer.getRemarks());
			call.setTimestamp(15, customer.getCrtDate() == null ? null : new Timestamp(
					customer.getCrtDate().getTime()));
			call.setString(16, customer.getStatus());
			call.setString(17, customer.getDesignMan());
			call.setString(18, customer.getBusinessMan());
			call.setString(19, customer.getConstructType());
			call.setDouble(20,customer.getPlanAmount() == null ? 0 : customer.getPlanAmount());
			call.setTimestamp(21, customer.getBeginDate() == null ? null : new Timestamp(
					customer.getBeginDate().getTime()));
			call.setString(22, customer.getLastUpdatedBy());
			call.setString(23, customer.getAgainMan());
			call.setString(24, customer.getSaleType());
			call.setString(25, customer.getOldCode());
			call.setString(26, customer.getNetChanel());
			call.setString(27, customer.getBuilderNum());
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.NVARCHAR);
			call.setInt(30, customer.getComeTimes()==null?0:customer.getComeTimes());
			call.setInt(31, customer.getMtCustInfoPK()==null?0:customer.getMtCustInfoPK());
			call.setTimestamp(32, customer.getVisitDate() == null ? null : new Timestamp(
					customer.getVisitDate().getTime()));
			call.setString(33, customer.getAccountRoomMan());
			call.setString(34, customer.getResrCustCode());
			call.setTimestamp(35, customer.getMeasureDate() == null ? null : new Timestamp(
					customer.getMeasureDate().getTime()));
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

	/**
	 * 修改客户存储过程
	 * 
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result updateForProc(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pUpdatetCustomer_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getCode());
			call.setString(2, customer.getCustType());
			call.setString(3, customer.getSource());
			call.setString(4, customer.getDescr());
			call.setString(5, customer.getGender());
			call.setString(6, customer.getBuilderCode());
			call.setString(7, customer.getAddress());
			call.setString(8, customer.getLayout());
			call.setInt(9, customer.getArea());
			call.setString(10, customer.getDesignStyle());
			call.setString(11, customer.getMobile1());
			call.setString(12, customer.getMobile2());
			call.setString(13, customer.getQq());
			call.setString(14, customer.getEmail2());
			call.setString(15, customer.getRemarks());
			call.setTimestamp(16, customer.getCrtDate() == null ? null : new Timestamp(
					customer.getCrtDate().getTime()));
			call.setString(17, customer.getOldStatus());
			call.setString(18, customer.getStatus());
			call.setString(19, customer.getDesignMan());
			call.setString(20, customer.getBusinessMan());
			call.setString(21, customer.getConstructType());
			call.setDouble(
					22,
					customer.getPlanAmount() == null ? 0 : customer
							.getPlanAmount());
			call.setTimestamp(23, customer.getBeginDate() == null ? null : new Timestamp(
					customer.getBeginDate().getTime()));
			call.setString(24, customer.getLastUpdatedBy());
			call.setString(25, customer.getExpired());
			call.setString(26, customer.getSaleType());
			call.setString(27, customer.getOldCode());
			call.setString(28, customer.getAgainMan());
			call.setString(29, customer.getNetChanel());
			call.setString(30, customer.getBuilderNum());
			call.registerOutParameter(31, Types.INTEGER);
			call.registerOutParameter(32, Types.NVARCHAR);
			call.setInt(33,customer.getComeTimes()==null?0:customer.getComeTimes());
			call.setString(34,customer.getAccountRoomMan());
			call.execute();
			result.setCode(String.valueOf(call.getInt(31)));
			result.setInfo(call.getString(32));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * jdbc修改客户存储过程
	 * 
	 * @param customer
	 * @return
	 */
	public Result updateForJdbc(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConnection();
			call = conn
					.prepareCall("{Call pUpdatetCustomer(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getCode());
			call.setString(2, customer.getCustType());
			call.setString(3, customer.getSource());
			call.setString(4, customer.getDescr());
			call.setString(5, customer.getGender());
			call.setString(6, customer.getBuilderCode());
			call.setString(7, customer.getAddress());
			call.setString(8, customer.getLayout());
			call.setInt(9, customer.getArea());
			call.setString(10, customer.getDesignStyle());
			call.setString(11, customer.getMobile1());
			call.setString(12, customer.getMobile2());
			call.setString(13, customer.getQq());
			call.setString(14, customer.getEmail2());
			call.setString(15, customer.getRemarks());
			call.setTimestamp(16, customer.getCrtDate() == null ? null : new Timestamp(customer.getCrtDate().getTime()));
			call.setString(17, customer.getOldStatus());
			call.setString(18, customer.getStatus());
			call.setString(19, customer.getDesignMan());
			call.setString(20, customer.getBusinessMan());
			call.setString(21, customer.getConstructType());
			call.setDouble(22,customer.getPlanAmount() == null ? 0 : customer.getPlanAmount());
			call.setTimestamp(23, customer.getBeginDate() == null ? null : new Timestamp(customer.getBeginDate().getTime()));
			call.setString(24, customer.getLastUpdatedBy());
			call.setString(25, customer.getExpired());
			call.execute();
			rs = call.getResultSet();
			if (rs != null && rs.next()) {
				result.setCode(String.valueOf(rs.getInt(0)));
				result.setInfo(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, call, conn);
		}
		return result;
	}

	/**
	 * 根据档案号获取客户
	 * 
	 * @param documentNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Customer getByDocumentNo(String documentNo) {
		String hql = "from Customer where documentNo=?";
		List<Customer> list = this.find(hql, new Object[] { documentNo });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 重复楼盘
	 * 
	 * @param address
	 * @param czybh
	 * @return
	 */
	public Map<String,Object> getByAddress(String address,String czybh) {
		String sql = "select a.Code,a.Status "
			+"from tCustomer a " 
			+"left join tEmployee b on a.BusinessMan=b.Number " 
			+"left join tEmployee c on c.Number=? "
			+"where a.Address=? and a.CustType<>2 "
			+"and (b.Department2=c.Department2 and a.Status in ('1','2','3','4')" 
			+" 	 or b.Department2<>c.Department2 and a.Status in ('3','4') ) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh,address});
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getCustomerPayByCode(String code) {
		String sql = "SELECT a.FirstPay+isnull(case when tpr.DesignFeeType = '2'" +
				" then a.StdDesignFee  else a.DesignFee  end, 0)" +
				"           * ( case when exists ( select 1" +
				"          from tPayRuleDetail" +  
				"          where No = tpr.No and IsRcvDesignFee = '1' and Expired = 'F' and PayNum = '1')" +
				"          then 1 else 0 end ) firstPay,a.stdDesignFee,a.returnDesignFee,a.code,a.descr,a.address,a.contractFee,a.designFee, "
				+ " a.secondPay + isnull(case when tpr.DesignFeeType = '2' "
				+ " 	then a.StdDesignFee  else a.DesignFee  end, 0) "
				+ " 	* ( case when exists ( select 1 "
				+ " 	from tPayRuleDetail  "
				+ "		where No = tpr.No and IsRcvDesignFee = '1' and Expired = 'F' and PayNum = '2') "
				+ " 	then 1 else 0 end ) secondPay, "
				+ " 	a.thirdPay  + isnull(case when tpr.DesignFeeType = '2' "
				+ " 	then a.StdDesignFee  else a.DesignFee  end, 0) "
				+ " 	* ( case when exists ( select 1 "
				+ " 	from tPayRuleDetail  "
				+ "		where No = tpr.No and IsRcvDesignFee = '1' and Expired = 'F' and PayNum = '3') "
				+ " 	then 1 else 0 end ) thirdPay, "
				+ " 	a.fourPay  + isnull(case when tpr.DesignFeeType = '2' "
				+ " 	then a.StdDesignFee  else a.DesignFee  end, 0) "
				+ " 	* ( case when exists ( select 1 "
				+ " 	from tPayRuleDetail  "
				+ "		where No = tpr.No and IsRcvDesignFee = '1' and Expired = 'F' and PayNum = '4') "
				+ " 	then 1 else 0 end ) fourPay,"
				+ "a.area,b.NOTE StatusDescr ,c.NOTE LayOutDescr ,a.DiscRemark,a.PayRemark,"
				+ "CupBoardDisc * a.ContainCup + IntegrateDisc * a.ContainInt + SoftDisc "
				+ "* a.ContainSoft + MainDisc * a.ContainMain + a.BaseDisc * a.ContainBase SumDisc ,"
				+ " FirstPay+isnull(case when tpr.DesignFeeType = '2'" +
				" then a.StdDesignFee  else a.DesignFee  end, 0)" +
				"           * ( case when exists ( select 1" +
				"          from tPayRuleDetail" +
				"          where No = tpr.No and IsRcvDesignFee = '1' and Expired = 'F' )" +
				"          then 1 else 0 end ) + SecondPay + ThirdPay + FourPay SumPay ,"//2018-7-23 @xzy 与CS客户付款查看页面信息对不上，去掉设计费（DesignFee）
				+ "a.ContractFee + a.DesignFee ContractDesign ,"
				+ "a.BaseFee_Dirct * a.ContainBase BaseFee_DirctContainBase ,"
				+ "a.BaseFee_Comp * a.ContainBase BaseFee_CompContainBase ,"
				+ "a.ManageFee * a.ContainBase ManageFeeContainBase ,"
				+ "a.MainFee * a.ContainMain MainFeeContainMain ,"
				+ "a.SoftFee * a.ContainSoft SoftFeeContainSoft ,"
				+ "a.IntegrateFee * a.ContainInt IntegrateFeeContainInt ,"
				+ "a.CupboardFee * a.ContainCup CupboardFeeContainCup ,"
				+ "a.MainServFee * a.ContainMainServ MainServFeeContainMainServ ,"
				+ "ISNULL(CONVERT(CHAR(10), signdate, 120), '') sSignDate ,"
				+ "CAST(dbo.fGetEmpNameChi(a.Code, '00') AS NVARCHAR(1000)) DesignManDescr ,"
				+ "CAST(dbo.fGetEmpNameChi(a.Code, '01') AS NVARCHAR(1000)) BusinessManDescr ,"
				+ " isnull(( select sum(LineAmount) BaseFee0240 from   tBaseItemPlan b " 
				+ " inner join txtcs xc on xc.id = 'LongFeeCode' and ',' + xc.QZ + ',' like '%,' + b.BaseItemCode + ',%' "
				+ "         where  b.custcode = a.code ), 0) BaseFee0240,"
				+ " isnull(d.zcAmount, 0) zcAmount,isnull(e.jcAmount, 0) jcAmount,isnull(f.rzAmount, 0) rzAmount,isnull(g.baseAmount, 0) baseAmount, "
				+ " isnull(a.firstPay,0)+isnull(a.secondPay,0)+isnull(a.thirdPay,0)+isnull(a.fourPay,0)+isnull(case when tpr.DesignFeeType='2' then a.StdDesignFee else a.DesignFee end,0)*(case when exists(select 1 from tPayRuleDetail where No=tpr.No and IsRcvDesignFee='1' and Expired='F') then 1 else 0 end) wdz ,"
				+ " ct.IsPubReturn,isnull(ct.ReturnAmount,0) ReturnAmount, isnull(a.Tax, 0) Tax,a.MainSetFee,a.SetMinus,a.SetAdd,tp.Descr payeeDescr,cty.desc1 custTypeDescr,  "
				+ " case when tprd1.PayTip is not null and tprd1.PayTip <>'' then tprd1.PayTip when tprd1.Descr is not null and tprd1.Descr <>'' then tprd1.Descr+'验收后收款' else null end  SecondPayTime, " 
				+ " case when tprd2.PayTip is not null and tprd2.PayTip <>'' then tprd2.PayTip when tprd2.Descr is not null and tprd2.Descr <>'' then tprd2.Descr+'验收后收款' else null end  ThirdPayTime, " 
				+ " case when tprd3.PayTip is not null and tprd3.PayTip <>'' then tprd3.PayTip when tprd3.Descr is not null and tprd3.Descr <>'' then tprd3.Descr+'验收后收款' else null end  FourPayTime, "
				+ " case when a.Status in('1','2','3') then isnull(x2.NOTE,x1.NOTE) else x1.NOTE end PayType "
				+ "FROM    tCustomer a "
				+ " left join tCustProfit cp on a.Code = cp.CustCode "
				+ "LEFT OUTER JOIN tXTDM b ON a.Status = b.CBM "
				+ "                           AND b.ID = 'CUSTOMERSTATUS' "
				+ "LEFT OUTER JOIN tXTDM c ON a.LayOut = c.CBM "
				+ "                           AND c.ID = 'LAYOUT' "
				+ "LEFT OUTER JOIN tXTDM x1 ON a.PayType = x1.CBM "
				+ "                           AND x1.ID = 'TIMEPAYTYPE' "
				+ "LEFT OUTER JOIN tXTDM x2 ON cp.PayType = x2.CBM "
				+ "                           AND x2.ID = 'TIMEPAYTYPE' "
				+ " left join (select CustCode,sum(Amount) zcAmount from tItemChg where Expired='F' and Status='1' and ItemType1='ZC' group by CustCode) d on d.CustCode = a.Code "
				+ " left join (select CustCode,sum(Amount) jcAmount from tItemChg where Expired='F' and Status='1' and ItemType1='JC' group by CustCode) e on e.CustCode = a.Code "
				+ " left join (select CustCode,sum(Amount) rzAmount from tItemChg where Expired='F' and Status='1' and ItemType1='RZ' group by CustCode) f on f.CustCode = a.Code "
				+ " left join (select sum(Amount) baseAmount,CustCode from tBaseItemChg where Expired='F' and Status='1' group by CustCode) g on g.CustCode = a.Code "
				+ " left join tPayRule tpr on tpr.CustType=a.Custtype and tpr.PayType=a.PayType "
				+ " left join tCustTax ct on ct.CustCode = a.Code "
				+ " left join tTaxPayee tp on tp.Code = ct.PayeeCode "
				+ " left join tcusttype cty on cty.Code = a.Custtype  "
				+ " left join (select in_a.No,in_b.Descr,in_a.PayTip from tPayRuleDetail in_a left join tPrjItem1 in_b on in_a.ConfirmPrjItem = in_b.Code where in_a.PayNum='2') tprd1 on tprd1.No=tpr.No "
				+ " left join (select in_a.No,in_b.Descr,in_a.PayTip from tPayRuleDetail in_a left join tPrjItem1 in_b on in_a.ConfirmPrjItem = in_b.Code where in_a.PayNum='3') tprd2 on tprd2.No=tpr.No "
				+ " left join (select in_a.No,in_b.Descr,in_a.PayTip from tPayRuleDetail in_a left join tPrjItem1 in_b on in_a.ConfirmPrjItem = in_b.Code where in_a.PayNum='4') tprd3 on tprd3.No=tpr.No "
				+ "WHERE   a.code =? ";
		List<Map<String, Object>> list = this.findBySql(sql.toLowerCase(),new Object[] { code });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Double getCustomerZjzjeByCode(String id) {
		String sql = "select sum(zjzje) zjzje from ( "
				+ "select isnull(sum(a.Amount),0) zjzje from tItemChg a "
				+ "where custcode=? and status='2'" + " union all "
				+ "select isnull(sum(b.Amount),0) from tBaseItemChg b "
				+ "where custcode=? and status='2'" + " UNION ALL "
				+ "select isnull(sum(c.ChgAmount),0) from tConFeeChg c "
				+ " where CustCode=? and status='CONFIRMED' ) d";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { id,
				id, id });
		if (list != null && list.size() > 0) {
			return Double.parseDouble(String.valueOf(list.get(0).get("zjzje")));
		}
		return 0.0;
	}

	public Double getCustomerHaspayByCode(String id) {
		String sql = "select isnull(sum(Amount),0) haspay from tCustPay where CustCode=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { id });
		if (list != null && list.size() > 0) {
			return Double
					.parseDouble(String.valueOf(list.get(0).get("haspay")));
		}
		return 0.0;
	}

	public Page<Map<String, Object>> findPageByCustomerCode_itemApp(	
			Page<Map<String, Object>> page, String code, String itemType1,String refCustCode) {
		List<Object> list = new ArrayList<Object>();
		list.add(code);
		list.add(refCustCode);
		String sql = "select a.No,s2.Note typeDescr,a.ItemType1,i.Descr ItemType1Descr,a.remarks, a.arriveDate, "
				+ "case when a.sendDate is null then a.date else a.senddate end showDate,"
	            + "a.Status,s1.Note StatusDescr,a.Date,a.SendDate,a.ConfirmDate,a.CustCode,isnull(i2.Descr,it.descr) ItemType2Descr "
				+ " from tItemApp a "
				+ " inner join (select min(PK) pk,a.No from tItemAppDetail a group by a.No) t on a.no=t.no "
				+ "	inner join tItemAppDetail m on t.pk=m.pk "
				+ " left join titem n on m.itemCode=n.code "
				+ " left join tItemtype2 it on n.itemType2=it.code "
	            + " left outer join tItemType1 i on a.ItemType1=i.Code "
	            + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
	            + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
	            + " left outer join tXTDM s2 on a.type=s2.CBM and s2.ID='ITEMAPPTYPE' "
	            + " where (a.CustCode = ? or a.RefCustCode = ? )";
		if (StringUtils.isNotBlank(itemType1)) {
			sql = sql + " and a.ItemType1=? ";
			list.add(itemType1);
		}
		sql = sql + " order by a.ConfirmDate desc";

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByCustomerCode_itemChange(
			Page<Map<String, Object>> page, String code, String itemType1, String itemType2) {
		List<Object> list = new ArrayList<Object>();
		list.add(code);
		String sql = "select f.itemType2Descr ,a.No,a.ItemType1,b.Descr ItemType1Descr,a.CustCode,c.Descr CustomerDescr,c.Address,a.Status,"
                     + "d.NOTE StatusDescr,a.Date,a.BefAmount,a.DiscAmount,a.Amount,a.Remarks,"
                     + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
                     + "from tItemChg a left outer join tItemType1 b on ltrim(rtrim(a.ItemType1))=ltrim(rtrim(b.Code)) "
                     + "left outer join tCustomer c on a.CustCode=c.Code "
                     + "left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS'  " +
                     "left join ( select  a.no ,d.descr itemType2Descr " +
		             "            from    ( select no,max(itemCode) itemCode from tItemChgDetail group by No ) a " +
		             "                    left join titem b on b.Code= a.itemCode " +
		             "                    left join tItemType2 d on d.code = b.ItemType2 " +
                     " ) f on f.no = a.no "
                     + "where a.CustCode = ? ";
		if (StringUtils.isNotBlank(itemType1)) {
			sql = sql + " and a.ItemType1=? ";
			list.add(itemType1);
		}
		if(StringUtils.isNotBlank(itemType2)){
			sql+=" and exists (select 1 from titemChgDetail ina " +
					" left join tItem inb on inb.code =ina.itemCode " +
					" where 1=1 and ina.no =a.no and inb.itemType2 = ? ) ";
			list.add(itemType2);
		}
		sql = sql + " order by a.date desc";

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByCustomerCode_itemReq(
			Page<Map<String, Object>> page, String code, String itemType1, String itemDescr,String itemType2) {
		List<Object> list = new ArrayList<Object>();
		list.add(code);
		String sql = "select a.*,f.descr intProdDescr ,isnull(d.IsCheckOutQty,0) IsCheckOutQty,c.Descr FixAreaDescr,b.Descr ItemDescr,e.Descr Uom "
				+ " from tItemReq a left join "
				+ "   (select sum(case when bb.Type='S' then aa.Qty else -aa.Qty end) IsCheckOutQty,aa.ReqPK from  tItemAppDetail aa "
				+ "   left outer join tItemApp bb on bb.No=aa.No "
				+ "   left outer join tPurchase cc on cc.No=bb.PUNo "
				+ "   where cc.IsCheckOut='1' group by aa.ReqPk) d "
				+ " on d.ReqPK=a.PK "
				+ " left outer join tItem b on b.Code=a.ItemCode "
				+ " left outer join tFixArea c on c.Pk=a.FixAreaPK "
				+ " left outer join tUom e on b.Uom=e.Code " +
				" left join tIntProd f on f.PK=a.IntProdPK " 
				+ " where (a.qty<>0 or a.ProcessCost<>0 or a.SendQty<>0) and a.CustCode=? ";
		if (StringUtils.isNotBlank(itemType1)) {
			sql = sql + " and a.ItemType1=? ";
			list.add(itemType1);
		}
		if (StringUtils.isNotBlank(itemDescr)) {
			sql = sql + " and b.Descr like ? ";
			list.add("%" + itemDescr + "%");
		}
		if(StringUtils.isNotBlank(itemType2)){
			sql+=" and b.itemType2 = ? ";
			list.add(itemType2);
		}
		sql = sql + " order by a.itemType1,b.itemType2,a.FixAreaPk,a.DispSeq";

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> getPayInfoDetailListByCode(String id) {
		String sql = "SELECT b.Date,b.Amount FROM tCustomer a "
				+"LEFT JOIN tCustPay b on a.Code=b.CustCode WHERE a.Code=?";
		return this.findBySql(sql, new Object[]{id});
	}

	public List<Map<String, Object>> getGxrListByCode(String id,UserContext uc) {
		String sql = "select a.Role,b.Descr RoleDescr,a.EmpCode,c.NameChi EmpName,"
               +"a.CustCode,d.Descr CustName,c.Phone,f.desc1 department2 "
               +"from tCustStakeholder a  "
               +"inner join tCustomer d on d.Code=a.CustCode and d.Expired='F' "
               +"left join tRoll b on b.Code=a.Role "
               +"left join tEmployee c on c.Number=a.EmpCode "
               +"left join tDepartment2 f on c.Department2=f.code  "
               +"WHERE a.CustCode=? and a.Expired='F' "// AND "+SqlUtil.getCustRight(uc, "a", 0) 
               +"ORDER BY a.Role";
		return this.findBySql(sql, new Object[]{id});
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_baseItemReq(
			Page<Map<String, Object>> page, String custCode, String fixAreaDescr) {
		List<Object> list = new ArrayList<Object>();
		list.add(custCode);
		String sql = "select a.*,"
				+"c.Descr FixAreaDescr,b.Descr BaseItemDescr,d.Descr Uom "
				+"from tBaseItemReq a "
				+"left outer join tBaseItem b on b.Code=a.BaseItemCode "
				+"left outer join tFixArea c on c.Pk=a.FixAreaPK "
				+"left outer join tUom d on b.Uom=d.Code "
				+"WHERE a.Qty<>0 and a.CustCode=? ";
		if (StringUtils.isNotBlank(fixAreaDescr)) {
			sql = sql + " and (c.Descr like ? or ? ='不限' or b.Descr like ?) ";
			list.add("%" + fixAreaDescr + "%");
			list.add(fixAreaDescr);
			list.add("%" + fixAreaDescr + "%");
		}
		
		sql = sql +" order by  a.FixAreaPk,c.DispSeq,a.dispseq";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_areaDescr(
			Page<Map<String, Object>> page, String custCode, String fixAreaDescr) {
		List<Object> list = new ArrayList<Object>();
		list.add(custCode);
		String sql = "select c.Descr value,c.Descr descr  "
				+"from tBaseItemReq a "
				+"left outer join tBaseItem b on b.Code=a.BaseItemCode "
				+"left outer join tFixArea c on c.Pk=a.FixAreaPK "
				+"left outer join tUom d on b.Uom=d.Code "
				+"WHERE a.Qty<>0 and a.CustCode=? " +
				" group  by c.Descr ";
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByCustomerCode_baseItemChange(
			Page<Map<String, Object>> page, String custCode) {
		String sql = "SELECT a.No,a.CustCode,c.Descr CustomerDescr,c.Address,a.Status,"
				+" d.NOTE StatusDescr,a.Date,a.BefAmount,a.DiscAmount,a.Amount,a.Remarks,"
				+" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+" from tBaseItemChg a "
				+" left outer join tCustomer c on a.CustCode=c.Code "
				+" left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
				+" where a.CustCode=? " +
				" order by a.date Desc ";
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}

	@SuppressWarnings("deprecation")
	public Result getCustomerCostByCode(String id) {
		Assert.notNull(id);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pJcszcx_forProc(?,?,?,?)}");
			call.setString(1, id);
			call.registerOutParameter(2, Types.LONGNVARCHAR);
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setJson(call.getString(2));
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		
		return result;
	}

	public List<Map<String, Object>> getByUserCustRight(String id,String status,String address) {
		UserContext uc = null;
		String sql = "select emnum,custright from tczybm where czybh=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{id});
		if (list!=null && list.size()>0){
			uc = new UserContext();
			Map<String,Object> map = list.get(0);
			uc.setCzybh(id);
			uc.setCustRight((String) map.get("custright"));
			uc.setEmnum((String) map.get("emnum"));
		}
		List<Object> listObj = new ArrayList<Object>();
		sql = "select top 15 a.code,a.address from tcustomer a where 1=1 and " + SqlUtil.getCustRight(uc, "a", 0);
		if (StringUtils.isNotBlank(status)){
			sql += " and a.status=?";
			listObj.add(status);
		}
		if (StringUtils.isNotBlank(address)){
			sql += " and a.address like ?";
			listObj.add("%"+address+"%");
		}
		return this.findBySql(sql,listObj.toArray());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<Map<String, Object>> getCheckCustomerByPage(Page page,String id, String status,
			String address) {
		UserContext uc = null;
		String sql = "select emnum,custright from tczybm where czybh=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{id});
		if (list!=null && list.size()>0){
			uc = new UserContext();
			Map<String,Object> map = list.get(0);
			uc.setCzybh(id);
			uc.setCustRight((String) map.get("custright"));
			uc.setEmnum((String) map.get("emnum"));
		}
		List<Object> listObj = new ArrayList<Object>();
		sql = "select  a.code,a.address,a.realAddress from tcustomer a inner join tcustType b on a.custType=b.code and b.IsPartDecorate <> '3' where 1=1 and " + SqlUtil.getCustRight(uc, "a", 0);
		if (StringUtils.isNotBlank(status)){
			sql += " and a.status in (" +status+")";
		}
		if (StringUtils.isNotBlank(address)){
			sql += " and a.address like ? or a.realAddress like ? ";
			listObj.add("%"+address+"%");
			listObj.add("%"+address+"%");
		}
		sql+=" and a.endCode in('0','3') ";
		return this.findPageBySql(page,sql,listObj.toArray());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<Map<String, Object>> getPrjCheckCustomerByPage(Page page,String id, String status,
			String address) {
		UserContext uc = null;
		String sql = " ";
		
		List<Object> listObj = new ArrayList<Object>();
		sql = "select  a.code,a.address,b.isPrjConfirm from tcustomer a " +
				" left join tCustType b on b.code=a.custType " +
				" where 1=1  ";
		if(StringUtils.isNotBlank(id)){
			sql+=" and a.projectMan = ? ";
			listObj.add(id);
		}
		if (StringUtils.isNotBlank(status)){
			sql += " and a.status in (" +status+")";
		}
		if (StringUtils.isNotBlank(address)){
			sql += " and a.address like ?";
			listObj.add("%"+address+"%");
		}
		sql+=" and a.endCode in('0','3') ";
		return this.findPageBySql(page,sql,listObj.toArray());
	}

	public Page<Map<String, Object>> getAllCustomerList(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.CustType,a.ConfirmBegin,h.NOTE CustTypeDescr,a.Source,b.NOTE SourceDescr,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,a.Mobile1,a.Mobile2,"
				+ "a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE DesignStyleDescr,a.BeginDate,a.ConstructType,g.NOTE ConstructTypeDescr,a.PlanAmount,"
				+ "a.DocumentNo,a.AgainMan,e3.NameChi AgainManDescr,a.BuilderCode,m.Descr BuilderCodeDescr,a.ProjectMan,e4.NameChi ProjectManDescr,e4.Phone ProjectManPhone,j.BuildStatus SafeBuildStatus, "
				+ "case when j.BuildStatus is null then '否' else '是' end isSafePreparation, "
				+ "l.BuildStatus StopBuildStatus,a.RealAddress, "
				+ " ACOS(SIN( ISNULL( m.Latitude , 0 ) / ( 180/PI() ) )*SIN( ? / ( 180/PI() ) ) + COS( ISNULL( m.Latitude , 0 ) / ( 180/PI() ) )*COS( ? / ( 180/PI() ) )*COS( ? / ( 180/PI() ) - ISNULL( m.longitude , 0 ) / ( 180/PI() ) ) )*6371.004 distance "
				+ " from tCustomer a "
				+ " left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ " left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ " left outer join tBuilder m on a.BuilderCode=m.Code "
				+ " left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
				+ " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " left outer join tXTDM g on a.ConstructType=g.CBM and g.ID='CONSTRUCTTYPE' "
				+ " left outer join tXTDM h on a.CustType=h.CBM and h.ID='CUSTTYPE' "
				+ " left outer join tEmployee e4 on a.ProjectMan = e4.Number "
				+ " inner join tcustType ct on a.custType=ct.code and ct.isaddallinfo='1' "
				+ " left outer join ( "
				+ " 	select  i.CustCode , "
				+ "		( "
				+ " 		select  top 1 case when BeginDate is not null then '1' else null end BuildStatus  " 
				+ "			from tBuilderRep " 
				+ "			where ( BeginDate <= CAST(GETDATE() as date) and EndDate >= CAST(GETDATE() as date)) and Type = '2' and CustCode = i.CustCode " 	
				+ " 	) BuildStatus "
				+ " 	from tBuilderRep i "
				+ " 	group by i.CustCode "
				+ " ) j on j.CustCode=a.Code "
				+ " left outer join ( "
				+ " 	select  k.CustCode , "
				+ " 	( "
				+ " 		select top 1 BuildStatus " 
				+ "			from tBuilderRep " 
				+ " 		where ( BeginDate <= CAST(GETDATE() as date) and EndDate >= CAST(GETDATE() as date)) and Type = '1' and CustCode = k.CustCode and BuildStatus != '1' "
				+ " 	) BuildStatus "
				+ " 	from tBuilderRep k "
				+ " 	group by k.CustCode "
				+ " ) l on l.CustCode=a.Code "
				+ " where ";	
		if(customer.getLatitude() == null ) list.add(0.0); else list.add(customer.getLatitude());
		if(customer.getLatitude() == null ) list.add(0.0); else list.add(customer.getLatitude());
		if(customer.getLontitude() == null ) list.add(0.0); else list.add(customer.getLontitude());
		if(StringUtils.isNotBlank(customer.getEndCode())){
			//包含完工工地
			sql+="((a.status='4')  or (a.status='5' and a.endCode='3' )) ";
		}else{
			sql+="(a.status='4') ";
		}
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? or a.RealAddress like ? ";
			list.add("%" + customer.getAddress() + "%");
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and a.Code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (StringUtils.isNotBlank(customer.getProjectManDescr())) {
			sql += " and e4.NameChi like ? ";
			list.add("%"+customer.getProjectManDescr()+"%");
		}
		if (StringUtils.isNotBlank(customer.getMobile1())) {
			sql += " and (a.Mobile1=? or a.Mobile2=?) ";
			list.add(customer.getMobile1());
			list.add(customer.getMobile1());
		}
		if (StringUtils.isNotBlank(customer.getSource())) {
			sql += " and a.Source=? ";
			list.add(customer.getSource());
		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		sql += " ) a where 1=1 ";
		if(customer.getDistance()!=null && customer.getDistance() > 0){
			sql += " and a.distance < ? ";
			list.add(customer.getDistance());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		}else{
			sql += " order by a.distance asc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	@SuppressWarnings("unchecked")
	public Customer getCustomerByMobile1(String phone) {
		String hql="from Customer WHERE Mobile1=? and status='4' ";
		List<Customer> list=this.find(hql, new Object[]{phone});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getCustomerByCode(String code) {
		String sql = "select a.code,a.CustType,b.Desc1 custTypeDescr,a.Layout,x1.NOTE layOutDescr,a.Area,"
		+"a.Status,x2.NOTE statusDescr,a.ConfirmBegin,a.ConstructDay,"
		+"case when a.ConfirmBegin is null then null else dateadd(day,a.ConstructDay,a.ConfirmBegin) end planEndDate, "
		+"case when e.BuildStatus is null then '否' else '是' end isSafePreparation, "
		+"case when x3.NOTE is null then '正在施工' else x3.NOTE end buildStatusDescr, "
		+"case when a.InnerArea=0 then round(b.InnerAreaPer*a.Area,0) else round(a.InnerArea,0) end InnerArea,"
		+"e.BuildStatus,c.NameChi ProjectMan,d.Desc1 department2,a.BuildPass,x4.NOTE constructTypeDescr,a.ConstructType,c.phone,a.RealAddress,a.DiscRemark, "
		+"case when a.IsInitSign='1' then '是' else '否' end IsInitSign "
		+"from tCustomer a "
		+"left join tCusttype b on a.CustType=b.Code "
		+"left join tXTDM x1 on a.Layout=x1.CBM and x1.ID='LAYOUT' "
		+"left join txtdm x2 on a.Status=x2.CBM and x2.id='CUSTOMERSTATUS' " 
		+"left join tEmployee c on c.Number = a.ProjectMan "
		+"left join tDepartment2 d on c.Department2 = d.Code "
		+"left join ("
		+"	select top 1 CustCode,case when BeginDate is not null then '1' else null end BuildStatus "
		+"  from tBuilderRep "
		+"  where ( BeginDate <= CAST(GETDATE() as date) and EndDate >= CAST(GETDATE() as date)) "
		+"  and Type = '2' and CustCode=? "
		+") e on a.Code = e.CustCode "
		+" left join ( " 
		+" 		select  f.CustCode , "
		+" 		( " 
		+" 			select  top 1 BuildStatus " 
		+" 			from tBuilderRep " 
		+" 			where ( BeginDate <= CAST(GETDATE() as date) and EndDate >= CAST(GETDATE() as date)) and Type = '1' and CustCode = f.CustCode and BuildStatus != '1'  "  	
		+" 		) BuildStatus "
		+" 		from tBuilderRep f "
		+" 		group by f.CustCode "
		+" ) g on g.CustCode=a.Code "
		+"left join tXTDM x3 on x3.CBM = g.BuildStatus and x3.ID = 'BUILDSTATUS' "
		+"left join tXTDM x4 on x4.CBM = a.ConstructType and x4.ID = 'CONSTRUCTTYPE' "
		+" where a.code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code,code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getCustomerByCode_htxx(String code) {
		String sql = "select a.tax,a.Code,a.Descr,a.Address,a.Status,d.NOTE StatusDescr,a.DesignFee,a.MeasureFee,"
                + "a.BaseFee_Dirct*ContainBase BaseFee_Dirct,a.BaseFee_Comp*ContainBase BaseFee_Comp,"
                + "a.DrawFee,a.ColorDrawFee,a.ManageFee*ContainBase ManageFee,a.BaseDisc*ContainBase BaseDisc,a.BaseFee*ContainBase BaseFee,"
                + "a.MainFee*a.ContainMain MainFee,a.MainDisc*a.ContainMain MainDisc,"
                + "a.MaterialFee,a.MainFee_Per*a.ContainMain MainFee_Per,"
                + "a.SoftFee*a.ContainSoft SoftFee,a.SoftDisc*a.ContainSoft SoftDisc,a.SoftOther*a.ContainSoft SoftOther,"
                + "a.IntegrateFee*a.ContainInt IntegrateFee,a.IntegrateDisc*a.ContainInt IntegrateDisc,"
                + "a.CupboardFee*a.ContainCup CupboardFee,a.CupBoardDisc*a.ContainCup CupBoardDisc,a.Gift,"
                + "a.MainServFee*ContainMainServ MainServFee,"
                + "t1.ChgBaseFee,t2.ChgMainFee,t2.ChgSoftFee,t2.ChgIntFee,t2.ChgCupFee,t2.ChgMainServFee,t3.ChgMainFee_Per,"
                + "a.PlanSign,a.SignDate,a.ContractFee,a.AchievDed,"
                + "e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
                + "isnull(t.ChgDesignFee,0) ChgDesignFee,isnull(t.ChgManageFee,0) ChgManageFee,isnull(t.ChgGift,0) ChgGift,"
                + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.SoftTokenNo,a.SoftTokenAmount "
                + "from tCustomer a left outer join tEmployee e1 on a.DesignMan=e1.Number "
                + "left outer join tEmployee e2 on a.BusinessMan=e2.Number "
                + "left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
                
                + "left join (select custcode,isnull(sum(case when chgtype='1' then ChgAmount else 0 end),0) as ChgDesignFee,"
                + "isnull(sum(case when chgtype='2' then ChgAmount else 0 end),0) as ChgManageFee,"
                + "isnull(sum(case when chgtype='3' then ChgAmount else 0 end),0)as ChgGift from tConFeeChg "
                + " where status='CONFIRMED' and custcode=? group by custCode) t on a.code=t.custCode "
                
				+ "left join (Select CustCode,ISNULL(SUM(AMOUNT),0) ChgBaseFee FROM tBaseItemChg WHERE CustCode=? "
				+ "and Status = '2' group by CustCode) t1 on a.code=t1.CustCode "
				+ "left join (Select CustCode,ISNULL(SUM(case when IsService=0 and ItemType1 = 'ZC' then AMOUNT else 0 end),0) ChgMainFee,"
				+ "ISNULL(SUM(case when IsService=1 and ItemType1 = 'ZC' then AMOUNT else 0 end),0) ChgMainServFee,"
				+ "ISNULL(SUM(case when ItemType1 = 'RZ' then AMOUNT else 0 end),0) ChgSoftFee,"
				/* 修改bug modify by zb on 20200224
				+ "ISNULL(SUM(case when ItemType1 = 'JC' then AMOUNT else 0 end),0) ChgIntFee,"
				+ "ISNULL(SUM(case when ItemType1 = 'CG' then AMOUNT else 0 end),0) ChgCupFee "*/
				+ "ISNULL(SUM(case when ItemType1 = 'JC' and IsCupboard='0' then AMOUNT else 0 end),0) ChgIntFee,"
				+ "ISNULL(SUM(case when ItemType1 = 'JC' and IsCupboard='1' then AMOUNT else 0 end),0) ChgCupFee "
				+ "FROM tItemChg "
				+ "WHERE CustCode=? and Status = '2' group by CustCode) t2 on a.code=t2.custCode "
				+ "left join (SELECT Custcode,ISNULL(SUM(LineAmount),0) ChgMainFee_Per FROM tItemChgDetail,tItemChg,tItem "
				+ "WHERE tItemChg.ItemType1='ZC' AND tItemChg.CustCode =? AND tItemChg.No = tItemChgDetail.No "
				+ "AND tItem.Code = tItemChgDetail.ItemCode AND tItem.CommiType='2' AND tItemChg.Status='2' "
				+ "group by CustCode) t3 on a.code=t3.custcode "
             
                + "where a.Code = ? and a.Expired = 'F'";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{code,code,code,code,code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
         
		return null;
	}

	public Page<Map<String, Object>> findPageByCustCode_payDetail(Page<Map<String, Object>> page,
			String custCode) {
		String sql = "select * from (select b.CustCode,a.Descr,a.Address,a.FirstPay,a.SecondPay,a.ThirdPay,a.FourPay,"
		        + " a.ContractFee,b.Amount,b.Date,a.Code,b.LastUpdate,b.LastUpdatedBy,"
		        + " b.Remarks,isnull(PK,0) as PK,case when c.DocumentNo is not null and c.DocumentNo <> '' then c.DocumentNo"
		        + " when e.DocumentNo is not null and e.DocumentNo <> '' then e.DocumentNo "
		        + " when  f.DocumentNo is not null and f.DocumentNo <> '' then f.DocumentNo"
		        + " when  g.DocumentNo is not null and g.DocumentNo <> '' then g.DocumentNo "
		        + " when h.DocumentNo is not null and h.DocumentNo <> '' then h.DocumentNo"
		        + " else c.DocumentNo end DocumentNo " 
		        + ",b.PayNo,d.ZWXM printCZYName,b.PrintDate "
		        + " from tCustomer a"
		        + " left join tCustPay b on b.CustCode = a.Code"
		        + " left join tPayCheckOut c on c.No=b.PayCheckOutNo"
				+ " left join tCZYBM d on b.PrintCZY = d.CZYBH "
		        + " left join tWfCust_PrjRefund e on e.WfProcInstNo = b.WfProcInstNo"
		        + " left join tWfCust_PrjReturnOrder f on f.WfProcInstNo = b.WfProcInstNo"
		        + " left join tWfCust_PrjReturnSet g on g.WfProcInstNo = b.WfProcInstNo"
		        + " left join tWfCust_OrderToIndependent h on h.WfProcInstNo = b.WfProcInstNo"
		        + " where Code = ?";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}

	public Map<String, Object> getCustomerByCode_gcxx(String code) {
		String sql = "declare @AddConstDay varchar select @AddConstDay=QZ from tXTCS where ID='AddConstDay' "
				+"select a.Code,a.Address,a.Descr,a.Mobile1,a.Mobile2,cast(a.ProjectMan as nvarchar(5)),"
                + "a.SignDate,a.ConfirmBegin,e2.NameChi designManDescr,e1.NameChi projectManDescr,d.Desc1 department2Descr,"
                + "a.CheckMan,e3.NameChi checkManDescr,a.DesignFee,a.ManageFee*ContainBase ManageFee,a.IntegrateFee*ContainInt IntegrateFee,"
                + "a.BaseFee_Dirct*ContainBase BaseFee_Dirct,a.BaseFee*ContainBase BaseFee,a.MainFee*ContainMain MainFee,i.Note as ConstructStatusDescr,"
                + "a.softFee*ContainSoft softFee,a.MainServFee*ContainMainServ MainServFee,a.ContractFee ContractFee,f.Note as ConstructTypeDescr,g.Note LayOutDescr,"
                + "a.Area,a.BeginComDate,a.ConstructDay,isnull(a.ConstructStatus,0) ConstructStatus,"
                + "isnull(b1.SumPay,0) HasPay,a.FirstPay,a.SecondPay,a.ThirdPay,a.FourPay,"
                + "isnull(bic.SumChgAmount,0)+isnull(ic.SumChgAmount,0)+isnull(cfc.SumChgAmount,0) as zjxhj,a.ConsEndDate,"
                + "a.PlanCheckOut,a.CheckOutDate,a.IsWaterCtrl,x1.note IsWaterCtrlDescr,a.IsWaterItemCtrl,x2.note IsWaterItemCtrlDescr,"
                + "case when ((CheckoutDate is not null) and (ConfirmBegin is not null)) then "
                + "isnull(DATEDIFF(day,ConfirmBegin,CheckoutDate),0) - ConstructDay-@AddConstDay "
                + "when CheckoutDate is null and ConfirmBegin is not null then "
                + "DATEDIFF(day,ConfirmBegin,getdate()) - ConstructDay-@AddConstDay "
                + "else null end as DelayDay,"
                + "h.Note as NowJd,j.Note as PlanJd,a.ConsRcvDate,"
                + "isnull(a.IsComplain,0) as IsComplain,b.Note SourceDescr, dbo.fGetEmpNameChi(a.Code,'01') as BusinessManDescr,"
                + "a.SendJobDate,a.ConsArea,c.Note IsComplainDescr,a.LastUpdate,a.LastUpdatedBy,"
                + "(isnull(a.BaseFee_Dirct,0) + isnull(a.BaseFee_Comp,0) + isnull(ManageFee,0) - isnull(BaseDisc,0))*ContainBase as jzhtFee,"
                + "(isnull(a.MainFee,0) - isnull(a.MainDisc,0))*ContainMain  as zchtFee,(isnull(a.SoftFee,0) - isnull(SoftDisc,0))*ContainSoft as rzhtFee, "
                + "(isnull(a.IntegrateFee,0) - isnull(a.IntegrateDisc,0))*ContainInt + (isnull(a.CupboardFee,0) - isnull(a.CupboardDisc,0))*ContainCup as jchtFee, "
                + "a.ContractFee,isnull(bic.SumChgAmount,0) jzzj,"
                + "isnull(ContractFee,0) + isnull(DesignFee,0) + isnull(bic.SumChgAmount,0)+isnull(ic.SumChgAmount,0)+isnull(cfc.SumChgAmount,0) as khJsje, "
	            + "(isnull(a.BaseFee_Dirct,0) + isnull(a.BaseFee_Comp,0))*ContainBase + isnull(bic.SumChgAmount,0) as xmjlJsje, "
                + "(isnull(a.BaseFee_Dirct,0) + isnull(a.BaseFee_Comp,0) + isnull(ManageFee,0) - isnull(BaseDisc,0))*ContainBase "
                + " + (isnull(a.MainFee,0) - isnull(a.MainDisc,0))*ContainMain + (isnull(a.IntegrateFee,0) "
                + " - isnull(a.IntegrateDisc,0))*ContainInt + (isnull(a.CupboardFee,0) "
                + " - isnull(a.CupboardDisc,0))*ContainCup + isnull(bic.SumChgAmount,0) + isnull(zj.SumChgAmount,0) as gcjlJsje "
                + "from tCustomer a "
                + "left join tEmployee e1 on e1.Number = a.ProjectMan "
                + "left join tEmployee e2 on e2.Number = a.DesignMan "
                + "left join tEmployee e3 on e3.Number = a.CheckMan "
                + "left join tEmployee e4 on e4.Number = a.BusinessMan "
                + "left join tXTDM b on b.IBM = a.Source and b.ID = 'CUSTOMERSOURCE' "
                + "left join tXTDM c on c.IBM = a.IsComplain and c.ID = 'YESNO' "
                + "left join tDepartment2 d on d.Code = e1.Department2 "
                + "left join tXTDM  f on f.IBM = a.ConstructType and f.ID = 'CONSTRUCTTYPE' "
                + "left join tXTDM g on g.IBM = a.Layout and g.ID = 'LAYOUT' "
                + "left join tXTDM i on i.IBM = a.ConstructStatus and i.ID = 'CONSTRUCTSTATUS' "
                + "left join tXTDM x1 on x1.IBM = a.IsWaterCtrl and x1.ID = 'YESNO' "
                + "left join tXTDM x2 on x2.IBM = a.IsWaterItemCtrl and x2.ID = 'YESNO' "
                + "left join (select p.CustCode,n.note from( "
	            + "select m.CustCode, (select top 1 PRJITEM from tPrjProg q "
			   	+ "where q.CustCode= m.CustCode and BeginDate < getdate() "
			   	+ "order by BeginDate desc) as PRJITEM "
	            + "from tPrjProg m "
	            + "group by m.CustCode) p "
                + "left join tXTDM n on p.PRJITEM = n.IBM and n.ID = 'PRJITEM') h "
                + "on h.CustCode = a.Code "
                + "left outer join (select sum(Amount) SumPay,CustCode from tCustPay group by CustCode) b1 on b1.CustCode=a.Code "
                + "left outer join (select sum(Amount) SumChgAmount,CustCode "
                + "from tBaseItemChg where Status<>'3' group by CustCode ) bic on bic.CustCode=a.Code "
                + "left outer join (select sum(Amount) SumChgAmount,CustCode "
                + "from tItemChg where Status<>'3' group by CustCode ) ic on ic.CustCode=a.Code "
                + "left join (select sum(ChgAmount) SumChgAmount,CustCode from tConFeeChg where Status in ('CONFIRMED','OPEN') "
                + "and ChgType in ('1','2','4') group by CustCode) cfc on cfc.CustCode=a.Code "
                + "left join(select sum(isnull(Amount,0)) SumChgAmount,CustCode from tItemChg "
                + "where Status='2' and (ItemType1 = 'ZC' or ItemType1 = 'JC') and IsService = 0 group by CustCode) zj "
                + "on zj.CustCode = a.code "
                + "left join (select p.CustCode,n.note from( "
                + "     select m.CustCode, (select top 1 PRJITEM from tPrjProg q "
                + "                 where q.CustCode= m.CustCode and planBegin < getdate() "
                + "                 order by planBegin desc) as PRJITEM "
                + "     from tPrjProg m "
                + "     group by m.CustCode) p "
                + "left join tXTDM n on p.PRJITEM = n.IBM and n.ID = 'PRJITEM') j "
                + "on j.CustCode = a.Code "
                + "where a.Code=? ";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{code});
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String, Object>> findPageBySql_ykCustomer(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.code,a.Descr,a.CheckStatus,a.ProjectMan,a.CustCheckDate,a.address from tCustomer a "
		+ " Left join tPrjCheck pc on a.Code=pc.CustCode "
		+ " where (a.status='4' "
		+ " or (a.Status='5' and a.EndCode='3' and (DateDiff(day,a.EndDate,GetDate())<=10 "
		+ " or DateDiff(day, pc.confirmdate,Getdate())<=10)) ) ";
		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(customer.getProjectMan());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += "and a.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		sql += "order by a.CustCheckDate desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_ykCustomer_new(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.code,a.Descr,a.CheckStatus,a.ProjectMan,a.CustCheckDate,a.address from tCustomer a where a.checkStatus in('3','4')  ";
		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(customer.getProjectMan());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += "and a.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		sql += "order by a.CustCheckDate desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("deprecation")
	public Result deleteForProc(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pDeleteCustomer_forProc(?,?,?,?)}");
			call.setString(1, customer.getCode());
			call.setString(2, customer.getLastUpdatedBy());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	public Page<Map<String, Object>> getExecuteCustomerList(
			Page<Map<String, Object>> page, CustomerQueryEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql =" SELECT a.PK,c.Code,c.Descr,c.Address,c.CustType,c.Mobile1,c.DocumentNo,d.NameChi,d.Phone,e.Desc1,b.CrtDate,g.BuildStatus SafeBuildStatus, "
				+" case when g.BuildStatus is null then '否' else '是' end isSafePreparation "
				+" ,i.BuildStatus StopBuildStatus,c.RealAddress "
				+" FROM tProgCheckPlanDetail a " 
				+" LEFT JOIN dbo.tCustomer c  ON a.CustCode=c.Code "
				+" LEFT JOIN dbo.tEmployee d on c.ProjectMan = d.Number "
				+" LEFT JOIN dbo.tDepartment2 e on d.Department2 = e.Code"
				+" INNER JOIN dbo.tProgCheckPlan b ON b.No = a.PlanNo "
				+" left join ( "
				+" 		select  f.CustCode , "
				+" 		( " 
				+" 			select  top 1 case when BeginDate is not null then '1' else null end BuildStatus  " 
				+"			from tBuilderRep " 
				+" 			where ( BeginDate <= CAST(GETDATE() as date) and EndDate >= CAST(GETDATE() as date)) and Type = '2' and CustCode = f.CustCode  "	
				+" 		) BuildStatus "
				+"		from tBuilderRep f "
				+" 		group by f.CustCode "
				+" ) g on g.CustCode=c.Code "
				+" left join ( " 
				+" 		select  h.CustCode , "
				+" 		( " 
				+" 			select  top 1 BuildStatus " 
				+" 			from tBuilderRep " 
				+" 			where ( BeginDate <= CAST(GETDATE() as date) and EndDate >= CAST(GETDATE() as date)) and Type = '1' and CustCode = h.CustCode and BuildStatus != '1' "  	
				+" 		) BuildStatus "
				+" 		from tBuilderRep h "
				+" 		group by h.CustCode "
				+" ) i on i.CustCode=c.Code "
				+" WHERE b.CrtDate >= cast(DATEADD(DAY,-1,GETDATE()) as date) and c.Status<>'5' ";	//巡检执行功能，主界面不要显示‘归档’状态的楼盘 add by zb on 20200318

		if(StringUtils.isNotBlank(evt.getCzybh())){
			sql+=" and b.CheckCZY=? ";
			list.add(evt.getCzybh());
		}
		if (StringUtils.isNotBlank(evt.getStatus())) {
			sql += " AND  a.Status=? ";
			list.add(evt.getStatus());
		}
		if (StringUtils.isNotBlank(evt.getAddress())) {
			sql += " and c.Address like ? ";
			list.add("%" + evt.getAddress() + "%");
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public void updateForJcysTcProc(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJcys_TC_BS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getCode());
			call.setDouble(2, customer.getManageFee());
			call.setDouble(3, customer.getBaseFeeDirct());
			call.setDouble(4, customer.getBaseFeeComp());
			call.setDouble(5, customer.getBaseFee());
			call.setDouble(6, customer.getArea());
			call.setString(7, customer.getLastUpdatedBy());
			call.setString(8, customer.getCustType());
			call.setString(9, customer.getDescr());
			call.registerOutParameter(10, Types.DOUBLE);
			call.registerOutParameter(11, Types.DOUBLE);
			call.registerOutParameter(12, Types.DOUBLE);
			call.registerOutParameter(13, Types.DOUBLE);
			call.registerOutParameter(14, Types.DOUBLE);
			call.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		
	}
	
	public void prjProgDeleteCustCode(String code ) {
		String sql = " delete from tPrjProg where CustCode= ?";
		this.executeUpdateBySql(sql, new Object[]{code});
	}
	
	public Page<Map<String, Object>> findItemConfirmPageBySql(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql="select *from ( select a.ConstructStatus,a.Code,a.CustType,e6.nameChi zcmanagerdescr ,a.ConfirmBegin,h.NOTE CustTypeDescr,a.Source,a.Descr,a.Gender,a.Address,a.Layout,a.Area,a.Mobile1,a.Mobile2, "
				+"a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,e1.Phone  DesignManPhone,l.Desc1 DesignManPart,a.BusinessMan,e2.NameChi BusinessManDescr,e5.NameChi ZCDesignManDescr, "
				+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,a.BeginDate,a.ConstructType,a.PlanAmount,(a.MainFee-a.MainDisc)*a.ContainMain mainPreFee, "
				+"a.DocumentNo,a.AgainMan,e3.NameChi AgainManDescr,a.BuilderCode,m.Descr BuilderCodeDescr,a.ProjectMan,e4.NameChi ProjectManDescr,e4.Phone ProjectManPhone,n.Desc1 ProjectManPart,j.note CheckStatusDescr,a.CustCheckDate, "
				+"a.oldCode,a.saleType, o.Descr   confItemdescr,o.code confItem,CASE WHEN o.DayType='1' AND p.BeginDate IS NOT NULL THEN DATEADD(DAY,o.AddDay,p.BeginDate) ELSE  CASE WHEN o.DayType='2' AND p.EndDate IS NOT NULL THEN DATEADD(DAY,o.AddDay,p.EndDate) ELSE  CASE WHEN o.DayType='3' THEN DATEADD(DAY,o.AddDay, isnull(p.EndDate,p.ConfirmDate)) ELSE  NULL END end end confirmBeginDate, "
				+" CASE WHEN o.DayType='1' then p.BeginDate else CASE WHEN o.DayType='3' then isnull(p.EndDate,p.ConfirmDate) else p.endDate end end confirmStartDate, "
				+" case when tpp.confirmDate is null then 0 else dateDiff(d,tpp.confirmDate,getDate()) end runDay,q.informDate,q.InformRemark,r.ConfirmDate,r.remarks ConfirmRemarks, "
				+"s1.ItemConfStatus ItemConfStatus1,s2.ItemConfStatus ItemConfStatus2,s3.ItemConfStatus ItemConfStatus3,s4.ItemConfStatus ItemConfStatus4,s5.ItemConfStatus ItemConfStatus5,s6.ItemConfStatus ItemConfStatus6,s7.ItemConfStatus ItemConfStatus7,s8.ItemConfStatus ItemConfStatus8,s9.ItemConfStatus ItemConfStatus9,s10.ItemConfStatus ItemConfStatus10,ss1.ItemConfStatus ItemConfStatus11, "
				+"s11.NOTE ItemConfStatus1Descr,s12.NOTE ItemConfStatus2Descr,s13.NOTE ItemConfStatus3Descr,s14.NOTE ItemConfStatus4Descr,"
				+"s15.NOTE ItemConfStatus5Descr,s16.NOTE ItemConfStatus6Descr,s17.NOTE ItemConfStatus7Descr,s18.NOTE ItemConfStatus8Descr," 
				+"s19.NOTE ItemConfStatus9Descr,s20.NOTE ItemConfStatus10Descr,s21.NOTE ItemConfStatus11Descr,z.empCode," 
				+"z1.EmpCode ZCManager,xp.note prjitemDescr,e7.namechi custscencedescr, "
				+" tpp.ConfirmDate checkConfirmDate ,case when o.code is not null and tpp.ConfirmDate<getdate() then DATEDIFF(day,tpp.ConfirmDate,getdate()) else 0 end overDate "; //增加确认结束时间、 超期天数
		if("02".equals(customer.getShowType())){
			sql+=",isnull(ys.czysAmount,0) czysAmount,isnull(ys.dbysamount,0)dbysamount,isnull(ys.ddysamount,0)ddysamount," 
				+"isnull(ys.dlysamount,0) dlysamount,isnull(ys.dssysamount,0) dssysamount,isnull(ys.kgysamount,0) kgysamount," 
				+"isnull(ys.lyfysamount,0) lyfysamount,isnull(ys.msysamount,0) msysamount,isnull(ys.scaiysamount,0) scaiysamount," 
				+"isnull(ys.scaoysamount,0) scaoysamount,isnull(ys.wyysamount,0) wyysamount, "
				+"isnull(qr.czqrAmount,0)czqrAmount,isnull(qr.dbqramount,0) dbqramount,isnull(qr.ddqramount,0) ddqramount," 
				+"isnull(qr.dlqramount,0) dlqramount,isnull(qr.dssqramount,0) dssqramount," 
				+"isnull(qr.kgqramount,0) kgqramount,isnull(qr.lyfqramount,0) lyfqramount,isnull(qr.msqramount,0) msqramount," 
				+"isnull(qr.scaiqramount,0) scaiqramount,isnull(qr.scaoqramount,0) scaoqramount,isnull(qr.wyqramount,0) wyqramount, "
                +"isnull(xd.czxdAmount,0) czxdAmount,isnull(xd.dbxdamount,0) dbxdamount,isnull(xd.ddxdamount,0) ddxdamount," 
                +"isnull(xd.dlxdamount,0) dlxdamount,isnull(xd.dssxdamount,0) dssxdamount,isnull(xd.kgxdamount,0) kgxdamount," 
                +"isnull(xd.lyfxdamount,0) lyfxdamount,isnull(xd.msxdamount,0) msxdamount,isnull(xd.scaixdamount,0) scaixdamount," 
                +"isnull(xd.scaoxdamount,0) scaoxdamount,isnull(xd.wyxdamount,0) wyxdamount ";
		}
			sql+=" from tCustomer a "
				+" left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+" left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+" left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+" left outer join tBuilder m on a.BuilderCode=m.Code "
				+" left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+" left outer join tXTDM h on a.CustType=h.CBM and h.ID='CUSTTYPE' "
				+" left outer join tXTDM j on a.CheckStatus=j.CBM and j.ID='CheckStatus' "
				+" left outer join tEmployee e4 on a.ProjectMan = e4.Number "
				+" LEFT  JOIN  dbo.tDepartment2 l ON l.Code=e1.Department2 "
				+" LEFT  JOIN  dbo.tDepartment2 n ON n.Code=e4.Department2 "
				+" LEFT  JOIN  (SELECT MIN(b.Code) code , a.Code ctCode FROM  dbo.tCustomer a " +
				"					LEFT  JOIN dbo.tConfItemTime b ON  1=1 " +
				"					LEFT JOIN tCustItemConfDate  c  ON c.ItemTimeCode=b.Code AND c.CustCode=a.Code" +
				"  					WHERE a.status='4' and  c.CustCode IS  NULL  GROUP BY a.Code) oo ON oo.ctCode=a.code "
				+" left join tConfItemTime o on o.code=oo.code "
				+ " left join tPrjProg tpp on tpp.prjItem =o.endPrjItem and a.code = tpp.custCode "
				+" LEFT  JOIN  dbo.tPrjProg p ON o.PrjItem=p.PrjItem AND p.CustCode=a.Code "
				+" LEFT JOIN (  select max(Pk) pk,Custcode from tPrjProg a  "
				+" where a.Begindate=(select max(Begindate) from tPrjProg  where custcode=a.CustCode ) "
				+" group by a.CustCode) pp ON pp.Custcode=a.Code "
				+" LEFT  JOIN  dbo.tPrjProg ppp ON ppp.pk=pp.pk"
				+" left join tXTDM xp on xp.CBM=ppp.prjitem and xp.ID = 'PRJITEM' "
				+" LEFT JOIN (  select Custcode ,max(PK) pk from tItemConfirmInform a"
				+" where a.InformDate=(select max(InformDate) from tItemConfirmInform where custcode=a.CustCode) "
				+" group by a.CustCode) qq ON qq.Custcode=a.Code "
				+" LEFT JOIN (  select Custcode ,max(no) no from tCustItemConfProg a "
				+" where a.ConfirmDate=(select max(ConfirmDate) from tCustItemConfProg where custcode=a.CustCode AND ItemConfStatus IN ('2','3')) "
				+" group by a.CustCode) rr ON rr.Custcode=a.Code "
				+" LEFT JOIN  tItemConfirmInform  q ON q.PK= qq.pk"
				+" LEFT JOIN tCustItemConfProg  r ON r.No =rr.no and r.custCode=rr.custCode "
				+" LEFT  JOIN dbo.tCustItemConfirm s1 ON s1.CustCode=a.Code AND  s1.ConfItemType='01' "				
				+" LEFT  JOIN dbo.tCustItemConfirm s2 ON s2.CustCode=a.Code AND  s2.ConfItemType='02' "
				+" LEFT  JOIN dbo.tCustItemConfirm s3 ON s3.CustCode=a.Code AND  s3.ConfItemType='03' "
				+" LEFT  JOIN dbo.tCustItemConfirm s4 ON s4.CustCode=a.Code AND  s4.ConfItemType='04' "
				+" LEFT  JOIN dbo.tCustItemConfirm s5 ON s5.CustCode=a.Code AND  s5.ConfItemType='05' "
				+" LEFT  JOIN dbo.tCustItemConfirm s6 ON s6.CustCode=a.Code AND  s6.ConfItemType='06' "
				+" LEFT  JOIN dbo.tCustItemConfirm s7 ON s7.CustCode=a.Code AND  s7.ConfItemType='07' "
				+" LEFT  JOIN dbo.tCustItemConfirm s8 ON s8.CustCode=a.Code AND  s8.ConfItemType='08' "
				+" LEFT  JOIN dbo.tCustItemConfirm s9 ON s9.CustCode=a.Code AND  s9.ConfItemType='09' "
				+" LEFT  JOIN dbo.tCustItemConfirm s10 ON s10.CustCode=a.Code AND  s10.ConfItemType='10' "
				+" LEFT  JOIN dbo.tCustItemConfirm ss1 ON ss1.CustCode=a.Code AND  ss1.ConfItemType='11' " 
				+" LEFT  JOIN tXTDM s11 ON s1.ItemConfStatus=s11.CBM AND  s11.ID='ITEMCONFSTS' " 
				+" LEFT  JOIN tXTDM s12 ON s2.ItemConfStatus=s12.CBM AND  s12.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s13 ON s3.ItemConfStatus=s13.CBM AND  s13.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s14 ON s4.ItemConfStatus=s14.CBM AND  s14.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s15 ON s5.ItemConfStatus=s15.CBM AND  s15.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s16 ON s6.ItemConfStatus=s16.CBM AND  s16.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s17 ON s7.ItemConfStatus=s17.CBM AND  s17.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s18 ON s8.ItemConfStatus=s18.CBM AND  s18.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s19 ON s9.ItemConfStatus=s19.CBM AND  s19.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s20 ON s10.ItemConfStatus=s20.CBM AND  s20.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tXTDM s21 ON ss1.ItemConfStatus=s21.CBM AND  s21.ID='ITEMCONFSTS' "
				+" LEFT  JOIN tCustStakeholder z ON z.CustCode=a.Code AND z.ROLE='32' " 
				+" left outer join tEmployee e5 on z.empCode=e5.Number " 
				+" LEFT  JOIN tCustStakeholder z1 ON z1.CustCode=a.Code AND z1.ROLE='34' " 
				+" left outer join tEmployee e6 on z1.empCode=e6.Number " +
				" left join (select max(a.PK) pk,a.CustCode  from tCustStakeholder a where a.role='63' group by a.CustCode) cscs " +
				" on a.Code=cscs.custCode " +
				" left join tCustStakeholder z2 ON z2.pk = cscs.pk " +
				" left join tEmployee e7 on e7.number=z2.empCode  ";
		if("02".equals(customer.getShowType())){		
			sql+=" left join (select "
				+"      sum(case when c.Code='01' then LineAmount else 0 end) czysAmount, "
				+"      sum(case when c.Code='02' then LineAmount else 0 end) dlysamount, "
				+"      sum(case when c.Code='03' then LineAmount else 0 end) dssysamount, "
				+"      sum(case when c.Code='04' then LineAmount else 0 end) lyfysamount, "
				+"      sum(case when c.Code='05' then LineAmount else 0 end) scaiysamount, "
				+"      sum(case when c.Code='06' then LineAmount else 0 end) msysamount, "
				+"      sum(case when c.Code='07' then LineAmount else 0 end) ddysamount, "
				+"      sum(case when c.Code='08' then LineAmount else 0 end) dbysamount, "
				+"      sum(case when c.Code='09' then LineAmount else 0 end) wyysamount, "
				+"      sum(case when c.Code='10' then LineAmount else 0 end) kgysamount, "
				+"      sum(case when c.Code='11' then LineAmount else 0 end) scaoysamount, "
				+"      a.CustCode from tItemPlan  a "
				+"      inner join tCustomer d on a.CustCode=d.Code "
				+" 		left join titem b on a.ItemCode=b.Code " 
				+" 		left join tConfItemType c on 1=1 " 
				+" 		where 1=1    and d.Status='4' and a.ItemType1='ZC' "
				+"      and exists (select 1 from tconfitemtypedt cit where cit.ConfItemType=c.Code "
				+"					and b.ItemType2=cit.ItemType2 and "
				+"					(cit.ItemType3='' or cit.ItemType3 is null or cit.ItemType3=b.ItemType3)) "
				+"      group by a.CustCode "
				+" )ys on a.Code=ys.CustCode " 
			    +" left join (select " 
			    +"      sum(case when c.Code='01' then LineAmount else 0 end)czqrAmount, "
			    +"      sum(case when c.Code='02' then LineAmount else 0 end)dlqramount, "
			    +"      sum(case when c.Code='03' then LineAmount else 0 end)dssqramount, "
			    +"      sum(case when c.Code='04' then LineAmount else 0 end)lyfqramount, "
			    +"      sum(case when c.Code='05' then LineAmount else 0 end)scaiqramount, "
			    +"      sum(case when c.Code='06' then LineAmount else 0 end)msqramount, "
			    +"      sum(case when c.Code='07' then LineAmount else 0 end)ddqramount, "
			    +"      sum(case when c.Code='08' then LineAmount else 0 end)dbqramount, "
			    +"      sum(case when c.Code='09' then LineAmount else 0 end)wyqramount, "
			    +"      sum(case when c.Code='10' then LineAmount else 0 end)kgqramount, "
			    +"      sum(case when c.Code='11' then LineAmount else 0 end)scaoqramount, "
			    +"      a.CustCode from tItemReq a " 
				+" 		inner join tCustomer d on a.CustCode=d.Code "
			    +"      left join titem b on a.ItemCode=b.Code "
				+" 	    left join tConfItemType c on 1=1  " 
				+"  	where 1=1 and d.Status='4' and a.ItemType1='ZC' "
				+"      and exists (select 1 from tconfitemtypedt cit where cit.ConfItemType=c.Code "
				+"					and b.ItemType2=cit.ItemType2 and "
				+"					(cit.ItemType3='' or cit.ItemType3 is null or cit.ItemType3=b.ItemType3)) "
				+"      group by a.CustCode "
				+" )qr on a.Code=qr.CustCode "
			    +" left join (select " 
			    +"      sum(case when e.Code='01' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)czxdAmount , "
			    +"      sum(case when e.Code='02' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)dlxdamount , "
			    +"      sum(case when e.Code='03' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)dssxdamount , "
			    +"      sum(case when e.Code='04' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)lyfxdamount , " 
			    +"      sum(case when e.Code='05' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)scaixdamount , "
			    +"      sum(case when e.Code='06' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)msxdamount , "
			    +"      sum(case when e.Code='07' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)ddxdamount , "
			    +"      sum(case when e.Code='08' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)dbxdamount , "
			    +"      sum(case when e.Code='09' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)wyxdamount , "
			    +"      sum(case when e.Code='10' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)kgxdamount , "
			    +"      sum(case when e.Code='11' then (case when a.Type='S' then 1 else -1 end)*(b.Qty*c.UnitPrice*c.Markup/100+case when c.qty=0 then 0 else c.ProcessCost*b.Qty/c.Qty end) else 0 end)scaoxdamount , "
			    +"      a.CustCode from tItemApp a " 
			    +"      inner join tCustomer f on a.CustCode=f.Code "
			    +"      left join tItemAppDetail b on a.No=b.No "
				+" 		left join titemreq c on b.ReqPK=c.PK " 
				+" 		left join titem d on c.ItemCode=d.Code " 
				+" 		left join tConfItemType e on 1=1 " 
				+" 		where a.Status in('CONFIRMED','SEND','RETURN') " 
				+" 		and f.Status='4' and a.ItemType1='ZC' "
				+"      and exists (select 1 from tconfitemtypedt cit where cit.ConfItemType=e.Code "
				+"	                and d.ItemType2=cit.ItemType2 and " 
				+"		            (cit.ItemType3='' or cit.ItemType3 is null or cit.ItemType3=d.ItemType3)) "
				+" 		group by a.CustCode "						      
				+" )xd on a.Code=xd.CustCode ";
		}
		sql+=" )a "
			+"where a.status='4' and " + SqlUtil.getCustRight(uc, "a", 0);
		        
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getConfItem())) {
			//确认节点
			sql += " and a.confItem=? ";
			list.add(customer.getConfItem());
		}
		if (customer.getConfirmBeginFrom() != null){
			sql += " and a.ConfirmBegin>= ? ";
			list.add(customer.getConfirmBeginFrom());
		}
		if (customer.getConfirmBeginTo() != null){
			sql += " and a.ConfirmBegin<= ? ";
			list.add(customer.getConfirmBeginTo());
		}
		if (customer.getInformDateFrom() != null){
			sql += " and a.InformDate>= CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getInformDateFrom());
		}
		if (customer.getInformDateTo() != null){
			sql += " and a.InformDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getInformDateTo());
		}
		if (customer.getConfirmDateFrom() != null){
			sql += " and a.ConfirmDate>= CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getConfirmDateFrom());
		}
		if(StringUtils.isNotBlank(customer.getConstructStatus())){
			sql+=" and a.ConstructStatus = ? ";
			list.add(customer.getConstructStatus());
			
		}
		if (customer.getConfirmDateTo() != null){
			sql += " and a.ConfirmDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getConfirmDateTo());
		}
		if ("1".equals(customer.getConfirmFinish())) {
			//sql += "  and not exists ( SELECT 1 FROM  dbo.tConfItemTime x LEFT JOIN tCustItemConfDate y ON x.Code=y.ItemTimeCode  AND y.CustCode=a.code where y.ConfirmDate IS NULL) ";
			 sql+=" and a.confItem is  null ";
		}else if("0".equals(customer.getConfirmFinish())){
			//sql += "  and  exists ( SELECT 1 FROM  dbo.tConfItemTime x LEFT JOIN tCustItemConfDate y ON x.Code=y.ItemTimeCode  AND y.CustCode=a.code where y.ConfirmDate IS NULL) ";
			sql+=" and a.confItem is not null ";
		}
		if((StringUtils.isNotBlank(customer.getMainBusinessMan()))){
			sql += " and a.ZCManager=? ";
			list.add(customer.getMainBusinessMan());	
		}
		
		if ("1".equals(customer.getIsOrder())) {
			//待预约
			sql += "  and a.confirmStartDate is not null AND DATEDIFF(DAY,a.confirmStartDate,GETDATE())>=0  and (exists (SELECT 1 FROM  tItemConfirmInform WHERE  CustCode=a.code  AND  ItemTimeCode=a.confItem AND  (PlanComeDate IS NULL OR DATEDIFF(DAY,PlanComeDate,GETDATE())>0) )or not exists (SELECT 1 FROM  tItemConfirmInform WHERE  CustCode=a.code  AND  ItemTimeCode=a.confItem)) ";
		}
		if ("1".equals(customer.getIsContainOutPlan())) {
			//默认只含有预算客户
			sql += " and exists (SELECT 1 FROM  dbo.tItemReq tr WHERE tr.ItemType1='ZC'  AND tr.Qty>0 and tr.custCode=a.code) ";
		}else if ("0".equals(customer.getIsContainOutPlan())) {
			sql += " and not exists (SELECT 1 FROM  dbo.tItemReq tr WHERE tr.ItemType1='ZC'  AND tr.Qty>0 and tr.custCode=a.code) ";	
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and a.custtype in (" + customer.getCustType() + ")";
		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(customer.getConfirmStartDateFrom() != null){
			sql += " and a.confirmStartDate >= ? ";
			list.add(customer.getConfirmStartDateFrom());
		}
		if(customer.getConfirmStartDateTo() != null){
			sql += " and a.confirmStartDate < ? ";
			list.add(DateUtil.addDate(customer.getConfirmStartDateTo(), 1));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by runday desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	

	public Page<Map<String,Object>> getCustomerByConditions(Page<Map<String,Object>> page,GetCustomerEvt evt){
		List<Object> list = new ArrayList<Object>();
		String sql = " SELECT a.Address,a.Code,a.CustType,a.IsInitSign "
					+" FROM dbo.tCustomer a "
					+" WHERE a.Expired='F' ";
		if(StringUtils.isNotBlank(evt.getAddress())){
			sql += " AND a.Address like ? ";
			list.add("%"+evt.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(evt.getConstructStatus())){
			sql += " AND a.ConstructStatus=? ";
			list.add(evt.getConstructStatus());
		}
		if(StringUtils.isNotBlank(evt.getProjectMan())){
			sql += " AND a.projectMan=? ";
			list.add(evt.getProjectMan());
		}
		if(StringUtils.isNotBlank(evt.getStatus())){
			sql += " AND a.Status=? ";
			list.add(evt.getStatus());
		}
		if(StringUtils.isNotBlank(evt.getCustCode())){
			sql += " AND a.Code=? ";
			list.add(evt.getCustCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "+ page.getPageOrder();
		}
		
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_report(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.CustType,a.ConfirmBegin,h.NOTE CustTypeDescr,a.Source,b.NOTE SourceDescr,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,a.Mobile1,a.Mobile2,"
				+ "a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE DesignStyleDescr,a.BeginDate,a.ConstructType,g.NOTE ConstructTypeDescr,a.PlanAmount,"
				+ "a.DocumentNo,a.AgainMan,e3.NameChi AgainManDescr,a.BuilderCode,m.Descr BuilderCodeDescr,a.ProjectMan,e4.NameChi ProjectManDescr,e4.Phone ProjectManPhone "
				+ "from tCustomer a "
				+ " left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ " left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ " left outer join tBuilder m on a.BuilderCode=m.Code "
				+ " left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
				+ " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " left outer join tXTDM g on a.ConstructType=g.CBM and g.ID='CONSTRUCTTYPE' "
				+ " left outer join tXTDM h on a.CustType=h.CBM and h.ID='CUSTTYPE' "
				+ " left outer join tEmployee e4 on a.ProjectMan = e4.Number "
				+ " Left join tPrjCheck pc on a.Code=pc.CustCode "
				+ " where a.status='4' and NOT EXISTS (SELECT 1 FROM   tBuilderRep br WHERE ? BETWEEN br.BeginDate AND br.EndDate  AND br.CustCode=a.Code) and a.constructStatus='1' ";
		SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
		Date date=DateUtil.addDate(new Date(), 1);
		list.add(sdf.format(date));
		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(customer.getProjectMan());
		}else{
			return null;
		}
		
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and a.Code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (StringUtils.isNotBlank(customer.getMobile1())) {
			sql += " and (a.Mobile1=? or a.Mobile2=?) ";
			list.add(customer.getMobile1());
			list.add(customer.getMobile1());
		}
		if (StringUtils.isNotBlank(customer.getSource())) {
			sql += " and a.Source=? ";
			list.add(customer.getSource());
		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	// add by hc findPageBySql_lktj_Tjfs    来客统计 2018/01/03 begin 
		/**
		 * 来客统计
		 * @param page
		 * @param pLktj
		 * @return
		 */
		@SuppressWarnings("deprecation")
		public Page<Map<String,Object>> findPageBySql_lktj_Tjfs(Page<Map<String,Object>> page, Customer customer,String orderBy,String direction) {
			Assert.notNull(customer);
			Connection conn = null; 
			CallableStatement call = null;
			try{
				HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
				Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
				conn = session.connection();
				call = conn.prepareCall("{Call pLktj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//pLktj 来客统计
				call.setInt(1, page.getPageNo());
				call.setInt(2,page.getPageSize());
				call.setString(3, customer.getStatistcsMethod());
				call.setString(4, customer.getDepartment1());
				call.setString(5, customer.getDepartment2());
				call.setString(6, customer.getDepartment3());
				call.setString(7, customer.getCustType());
				call.setTimestamp(8, new java.sql.Timestamp(customer.getBeginDate().getTime()));
				call.setTimestamp(9, new java.sql.Timestamp(customer.getEndDate().getTime()));
				call.setString(10, customer.getRole());
				call.setString(11, customer.getTeam());
				call.setString(12, customer.getBuilderCode());			
				call.setString(13, orderBy);
				call.setString(14, direction);
				call.setString(15, customer.getRegion());
				call.setString(16, customer.getSource());
				call.setString(17, customer.getCustNetCnl());
				call.setString(18, customer.getCzybh());
				call.execute();
				ResultSet rs = call.getResultSet(); 
				List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
				page.setResult(list); 			
				if (!list.isEmpty()) {
					if ("1".equals(customer.getStatistcsMethod())){
						page.setTotalCount((Integer)list.get(0).get("totalcount"));
					}
				} else {
					page.setTotalCount(1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DbUtil.close(null, call, conn);
			}
			return page;
		} 
		// end by hc 来客统计    2018/01/03 end 

	public String getCustTypeTypeByCode(String custCode) {
		String sql = "select b.type from tcustomer a,tcusttype b where a.custtype=b.code and a.code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return (String) list.get(0).get("type");
		}
		return null;
	}

		public List<Map<String, Object>> getRollList(){
			String sql = " select Code,Descr from tRoll where Expired='F' ";
			return this.findBySql(sql, new Object[]{});
		}
		
		public boolean getIsDelivBuilder(String builderCode){
			String sql=" select * from tbuilder where AddressType='4' and code= ? ";
			
			List<Map<String,Object>> list=this.findBySql(sql, new Object[]{builderCode});
			
			if (list!=null && list.size()>0){
				 return true;
			}
			return false;
		}
	
	public String getAddressType(String builderCode) {
		String sql = "select AddressType from tbuilder where code= ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{builderCode});
		if (list!=null && list.size()>0){
			return list.get(0).get("AddressType").toString();
		}
		return null;
	}	
	
	public boolean getIsExistBuilderNum(String builderCode, String builderNum) {
		String sql = " select  a.*,c.descr builderDescr from tBuilderNum a  " +
				" left  join tBuilderDeliv b on b.Code=a.BuilderDelivCode" +
				" left join tbuilder c on c.code=b.builderCode " +
				" where 1=1  and c.Code= ? and a.BuilderNum= ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{builderCode,builderNum});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	public Page<Map<String, Object>> findPageBySql_gcwg(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sqltxt = "select * from (select a.Code,a.Descr,a.Address,a.Status,a.EndCode,z.NOTE EndCodeDescr,d.NOTE StatusDescr,a.DesignFee,a.MeasureFee,"
		+"a.DrawFee,a.ColorDrawFee,a.ManageFee,a.BaseDisc,a.BaseFee,a.BaseFee_Dirct,a.BaseFee_Comp,a.MainFee*a.ContainMain MainFee,a.MainDisc,"
		+"a.SoftFee*a.ContainSoft SoftFee,a.SoftDisc,a.IntegrateFee*a.ContainInt IntegrateFee,a.IntegrateDisc,a.CupboardFee*a.ContainCup CupboardFee,a.CupBoardDisc,a.ContractFee,"
		+"e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,a.MaterialFee,a.ChgBaseFee,a.ChgMainFee,a.ChgIntFee,a.ConstructStatus,x3.Note ConstructStatusDescr,"
		+"a.ChgSoftFee,a.ChgCupFee,a.ChgMainFee_Per,a.MainFee_Per,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.hadcalcbackperf,a.ConStaDate,"
		+"convert(nvarchar(10),a.EndDate,120) EndDate,a.CheckStatus,x1.Note CheckStatusDescr,a.CustType,convert(nvarchar(10),a.CustCheckDate,120) CustCheckDate,a.CheckDocumentNo,"
		+"a.DocumentNo,a.ProjectMan,tE.Namechi projectManDescr,tE1.Namechi projectManDescr1,a.IsItemCheck,x2.Note IsItemCheckDescr,convert(nvarchar(10),"
		+"a.ItemCheckDate,120) ItemCheckDate,a.Area,ct.Desc1 custTypeDescr,a.SoftTokenAmount "
		+"from tCustomer a "
		+"left outer join tEmployee e1 on a.DesignMan=e1.Number "
		+"left outer join tEmployee e2 on a.BusinessMan=e2.Number "
		+"left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
		+"left outer join tXTDM z on a.EndCode=z.CBM and z.ID='CUSTOMERENDCODE' " 
		+"left outer join tXTDM x1 on x1.CBM = a.CheckStatus and x1.id='CheckStatus' "
		+"left join tEmployee tE on a.PrjDeptLeader=tE.Number "
		+"left join tEmployee tE1 on a.ProjectMan=tE1.Number "
		+"left outer join tCustType ct on a.CustType=ct.Code "
		+"left outer join tXTDM x2 on a.IsItemCheck=x2.CBM and x2.ID='YESNO' "
		+"left outer join tXTDM x3 on a.ConstructStatus=x3.CBM and x3.ID='CONSTRUCTSTATUS' "
		+"where a.EndCode in ('0','3','4')  and a.Expired = 'F'";
		
		if (StringUtils.isNotBlank(customer.getStatus())) {
			String str = SqlUtil.resetStatus(customer.getStatus());
			sqltxt += " and a.status in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getEndCode())) {
			String str = SqlUtil.resetStatus(customer.getEndCode());
			sqltxt += " and a.endCode in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			String str = SqlUtil.resetStatus(customer.getCustType());
			sqltxt += " and a.custType in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sqltxt += " and a.code=? ";
			list.add(customer.getCode());
		}
		/*增加档案号查询——zb*/
		if (StringUtils.isNotBlank(customer.getDocumentNo())) {
			sqltxt += " and a.DocumentNo = ? ";
			list.add(customer.getDocumentNo());
		}
		//增加凭证号的模糊查询 --add by zb --2018-08-07
		if (StringUtils.isNotBlank(customer.getCheckDocumentNo())) {
			sqltxt += " and a.CheckDocumentNo like ? ";
			list.add("%"+customer.getCheckDocumentNo()+"%");
		}
		//增加施工状态查询 --add by cjg --2018-08-21
		if (StringUtils.isNotBlank(customer.getConstructStatus())) {
			sqltxt += " and a.ConstructStatus  in " + "('"
					+ customer.getConstructStatus().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sqltxt += " and a.descr=? ";
			list.add(customer.getDescr());
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sqltxt += " and a.designMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sqltxt += " and a.businessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sqltxt += " and a.Address like ? ";
			list.add("%" + customer.getAddress().trim() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCheckStatus())) {
			String str = SqlUtil.resetStatus(customer.getCheckStatus());
			sqltxt += " and a.checkStatus in (" + str + ")";
		}
		if (customer.getEndDateFrom() != null) {
			sqltxt += " and a.EndDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getEndDateFrom());
		}
		if (customer.getEndDateTo() != null) {
			sqltxt += " and a.EndDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getEndDateTo());
		}
		if (customer.getCustCheckDateFrom() != null) {
			sqltxt += " and a.CustCheckDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getCustCheckDateFrom());
		}
		if (customer.getCustCheckDateTo() != null) {
			sqltxt += " and a.CustCheckDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getCustCheckDateTo());
		}
		if (StringUtils.isNotBlank(customer.getIsItemCheck())) {
			sqltxt += " and a.IsItemCheck=? ";
			list.add(customer.getIsItemCheck());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sqltxt += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sqltxt += ") a order by a.LastUpdate desc";
		}
	
		return this.findPageBySql(page, sqltxt, list.toArray());
	}

	public Map<String, Object> getCustomerByCode_gcwg(String code) {
		String sql = "select a.code,a.status,a.descr,a.address,a.designMan,e1.NameChi designManDescr,a.businessMan,"
		+"e2.NameChi businessManDescr,a.LastUpdate,a.lastUpdatedBy,xt1.NOTE statusDescr,"
		+"a.ContractFee,a.ManageFee,a.DesignFee,a.MeasureFee,a.DrawFee,a.ColorDrawFee,"
        +"a.BaseFee*a.ContainBase BaseFee,a.BaseDisc*a.ContainBase BaseDisc,"
        +"a.BaseFee_Dirct*a.ContainBase BaseFeeDirct,a.BaseFee_Comp*a.ContainBase BaseFeeComp,"
        +"a.MainFee*a.ContainMain MainFee,a.MainDisc*a.ContainMain MainDisc,"
        +"a.SoftFee*a.ContainSoft SoftFee,a.SoftDisc*a.ContainSoft SoftDisc,"
        +"a.IntegrateFee*a.ContainInt IntegrateFee,a.IntegrateDisc*a.ContainInt IntegrateDisc,"
        +"a.CupboardFee*a.ContainCup CupboardFee,a.CupBoardDisc*a.ContainCup CupBoardDisc,"
        +"a.MainServFee*a.ContainMainServ MainServFee,"
        +"a.MaterialFee,a.MainFee_Per MainFeePer,a.SpecialDisc,a.EndRemark "
        +"from tCustomer a "
        +"left join tXTDM xt1 on xt1.ID='CUSTOMERSTATUS' and xt1.CBM=a.Status "
        +"left join temployee e1 on e1.Number=a.DesignMan "
        +"left join temployee e2 on e2.Number=a.BusinessMan "
        +"where Code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public Customer getChgData(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pGetChgData_forProc(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getCode());
			call.setString(2, customer.getIsSave());
			call.registerOutParameter(3, Types.DOUBLE);
			call.registerOutParameter(4, Types.DOUBLE);
			call.registerOutParameter(5, Types.DOUBLE);
			call.registerOutParameter(6, Types.DOUBLE);
			call.registerOutParameter(7, Types.DOUBLE);
			call.registerOutParameter(8, Types.DOUBLE);
			call.registerOutParameter(9, Types.DOUBLE);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(10)));
			result.setInfo(call.getString(11));
			if ("1".equals(result.getCode())){
				customer.setChgBaseFee(call.getDouble(3));
				customer.setChgMainFee(call.getDouble(4));
				customer.setChgSoftFee(call.getDouble(5));
				customer.setChgIntFee(call.getDouble(6));
				customer.setChgCupFee(call.getDouble(7));
				customer.setChgMainServFee(call.getDouble(8));
				customer.setChgMainFeePer(call.getDouble(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return customer;
	}
	
	@SuppressWarnings({ "deprecation" })
	public Result doGcwg_jz(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGcwg_jz_forProc(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getM_umState());
			call.setString(2, customer.getCode());
			call.setString(3, customer.getFromStatus());
			call.setString(4, customer.getToStatus());
			call.setTimestamp(5, customer.getEndDate()==null?null:new java.sql.Timestamp(customer.getEndDate().getTime()));
			call.setString(6, customer.getEndCode());
			call.setString(7, customer.getEndRemark());
			call.setString(8, customer.getLastUpdatedBy());
			call.setDouble(9, customer.getRealDesignFee());
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(10)));
			result.setInfo(call.getString(11));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}

	@SuppressWarnings("deprecation")
	public Result doGcwg_Khjs(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGcwg_khjs_forProc(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getCode());
			call.setString(2, customer.getCheckDocumentNo());
			call.setString(3, customer.getPrjDeptLeader());
			call.setTimestamp(4, customer.getCustCheckDate()==null?null:new java.sql.Timestamp(customer.getCustCheckDate().getTime()));
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, customer.getPrjDepartment1());
			call.setString(8, customer.getPrjDepartment2());
			call.setString(9, customer.getM_umState());
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public Map<String, Object> getShouldBanlance(String code) {
		Assert.notNull(code);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		Map<String, Object> map = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGetShouldBanlance(?,?,?,?,?,?,?)}");
			call.setString(1, code);
			call.registerOutParameter(2, Types.NVARCHAR);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.registerOutParameter(4, Types.DOUBLE);
			call.registerOutParameter(5, Types.DOUBLE);
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
			if(result.isSuccess()){
				map = new HashMap<String, Object>();
				map.put("nowNum", call.getString(2));
				map.put("nextNum", call.getString(3));
				map.put("nowShouldPay", call.getDouble(4));
				map.put("nextShouldPay", call.getDouble(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return map;
	}
	
	public List<Map<String, Object>> getHasZCReq(String code) {
		String sql = "select top 1 a.custCode code from titemReq a " +
				" left join tItem b on b.code= a.itemCode " +
				" where a.custCode = ? and a.itemType1= 'ZC' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public String getIsNeedPlanSendDate(String custCode) {
		String sql = " select b.Code,a.OldCode,a.CustType,a.SaleType,* from tCustomer a " +
				" left join tcustomer b on a.OldCode=b.Code " +//b 原客户
				" left join tCusttype c on c.Code=a.CustType"+
				" left join tCusttype d on d.Code=b.CustType" +
				" where (" +
				" 	(" +
				"		(c.IsAddAllInfo='0'  and isnull(a.OldCode,'') ='' ) " +
				" 		or (c.IsAddAllInfo='0'  and d.IsAddAllInfo='0' ) " +
				" 		or ( b.Status ='5' and c.IsAddAllInfo='0' ) " +
				" 		or (b.code is not null and b.Status <>'4' )" +
				"	)" +
				"   and a.Code= ? " +//and a.CustType='2'
				" 	and not exists (" +
				"		select 1 from tPrjProg in_a where " +
				"		case when c.IsAddAllInfo = '0' then a.Code " +
				"		else b.Code end =in_a.CustCode " +
				"	)" +
				" )" +
				" or ( a.Code= ? and isnull(a.OldCode,'') <> '' and " +
				"	not exists (" +
				"		select 1 from tprjprog in_b where in_b.custcode= b.code " +
				"	)" +
				" ) " +
				" or ( a.Code= ? and isnull(a.OldCode,'') = '' and " +
				"	not exists (" +
				"		select 1 from tprjprog in_b where in_b.custcode= a.code " +
				"	)" +
				" )";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,custCode,custCode});
		if (list!=null && list.size()>0){
			return "true";//必填 
		}
		return "false";
	}
	
	public String getIsDefaultStatic() {
		String custTypes="";
		String sql = "select Code from tCustType where IsDefaultStatic='1' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			for(Map<String, Object> map:list){
				custTypes=custTypes+map.get("Code").toString()+",";
			}
			return custTypes.substring(0,custTypes.length() - 1);
		}
		return "";
	}

	public Page<Map<String, Object>> findPageBySql_sghtxx(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select x4.note initSignDescr,a.SetDate,a.Code,a.Descr,a.Address,a.Status,a.EndCode,z.NOTE EndCodeDescr,d.NOTE StatusDescr,a.DesignFee,a.MeasureFee,"
                + "a.DrawFee,a.ColorDrawFee,a.ManageFee*ContainBase ManageFee,a.Tax,"
                + "a.BaseDisc*ContainBase BaseDisc,a.BaseFee*ContainBase BaseFee,"
                + "a.BaseFee_Dirct*ContainBase BaseFee_Dirct,a.BaseFee_Comp*ContainBase BaseFee_Comp,"
                + "a.MainFee*a.ContainMain MainFee,a.MainDisc*a.ContainMain MainDisc,"
                + "a.SoftFee*a.ContainSoft SoftFee,a.SoftDisc*a.ContainSoft SoftDisc,a.SoftOther*a.ContainSoft SoftOther,"
                + "a.IntegrateFee*a.ContainInt IntegrateFee,a.IntegrateDisc*a.ContainInt IntegrateDisc,"
                + "a.CupboardFee*a.ContainCup CupboardFee,a.CupBoardDisc*a.ContainCup CupBoardDisc,a.Gift,a.ConstructDay,"
                + "a.ContractFee,a.ConstructType,x2.Note ConstructTypeDescr,"
                + "e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,a.Area ,"
                + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,CONVERT(varchar(100),a.SignDate, 23) SignDate,a.Remarks, "
                + "ContainBase,ContainMain,ContainSoft,ContainInt,a.MainServFee*a.ContainMainServ MainServFee,"
                + "a.CustType,x1.NOTE CustTypeDescr,a.SoftBaseFee,a.MainSetFee,a.SetMinus,a.SetAdd,a.LongFee,a.ManageFee_Base,"
                + "a.ManageFee_Main,a.ManageFee_Int,a.ManageFee_Serv,a.ManageFee_Soft,a.ManageFee_Cup,"
               // + "isnull(t.CzAmount,0)*a.ContainMain CzAmount,isnull(t.WyAmount,0)*a.ContainMain WyAmount,"
                + "e1.phone DesignPhone,dt1.Desc1 DesignDept1,dt2.Desc1 DesignDept2,x3.Note LayOutDescr,a.DesignMan,a.Position,StdDesignFee,ReturnDesignFee,"
                + " g.CmpCode,h.Desc2 CmpDescr,a.ToConstructDate,tr.Descr RegionDescr,x5.NOTE ContractStatusDescr, "
                + " a.DocumentNo, q.BasePersonalPlan, q.ManageFee_BasePersonal "
                + " from tCustomer a "
                + " left outer join tEmployee e1 on a.DesignMan=e1.Number "
                + " left outer join tDepartment1 dt1 on e1.Department1=dt1.code "
                + " left outer join tDepartment2 dt2 on e1.Department2=dt2.code "
                + " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
                + " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
                + " left outer join tXTDM z on a.EndCode=z.CBM and z.ID='CUSTOMERENDCODE' "
                + " left outer join tXTDM x1 on x1.CBM=a.CustType and x1.id='CUSTTYPE' "
                + " left outer join tXTDM x2 on x2.CBM=a.ConstructType and x2.id='CONSTRUCTTYPE' "
                + " left outer join tXTDM x3 on x3.CBM=a.layout and x3.id='LAYOUT' "
                + " left join tXtdm x4 on x4.cbm = a.isInitSign and x4.id='YESNO'"
               /* + " left join (select a.CustCode,sum(case d.Code when '11' then isnull(a.LineAmount,0) else 0 end) czAmount,"
                + "sum(case d.code when '15' then isnull(a.LineAmount,0) else 0 end) wyAmount "
                + "from tItemPlan a left join tItem b on a.ItemCode=b.Code "
                + "left join tItemType2 c on b.itemtype2=c.Code "
                + "left join titemtype12 d on c.ItemType12=d.Code "
                + "where a.ItemType1='ZC' and a.IsService='0' "
                + "group by a.CustCode) t on a.code=t.CustCode "*/
                + " left join tBuilder f on a.BuilderCode=f.Code "
                + " left join tRegion g on g.Code=f.RegionCode "
                + " left join tCompany h on h.Code=g.CmpCode "
				+ " left join tBuilder tb on a.BuilderCode=tb.code "
				+ " left join tRegion tr on tb.RegionCode=tr.code "
				+ " left join (select max(pk)conPk,in_a.CustCode from tCustContract in_a group by in_a.CustCode) o on o.CustCode = a.Code "
                + " left join tCustContract p on o.conPk = p.PK "
                + " left join tXTDM x5 on p.Status = x5.CBM and x5.ID = 'CONTRACTSTAT' "
                + " outer apply ( "
                + "     select in_a.BasePersonalPlan, "
                + "         in_a.ManageFee_BasePersonal "
                + "     from tPerformance in_a "
                + "     where in_a.PK = ( "
                + "         select max(in1_a.PK) "
                + "         from tPerformance in1_a "
                + "         where in1_a.Type = '1' "
                + "             and in1_a.CustCode = a.Code "
                + "     ) "
                + " ) q "
                + " where 1=1  and a.Expired = 'F' and " + SqlUtil.getCustRight(uc, "a", 1);
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and (a.Address like ? or a.realAddress like ? )";
			list.add("%" + customer.getAddress() + "%");
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and a.Code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (customer.getSignDateFrom() != null) {
			sql += " and a.SignDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getSignDateFrom());
		}
		if (customer.getSignDateTo() != null) {
			sql += " and a.SignDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getSignDateTo());
		}
		if (customer.getToConstructDateFrom() != null) {
			sql += " and a.toConstructDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getToConstructDateFrom());
		}
		if (customer.getToConstructDateTo() != null) {
			sql += " and a.toConstructDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getToConstructDateTo());
		}
		if (StringUtils.isNotBlank(customer.getConstructType())) {
			sql += " and a.ConstructType=? ";
			list.add(customer.getConstructType());
		}
		if (StringUtils.isNotBlank(customer.getStatus())) {
			String str = SqlUtil.resetStatus(customer.getStatus());
			sql += " and a.status in (" + str + ")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Map<String, Object> findMapByCode(String code) {
		String sql = "select a.buildernum,a.Code,a.realAddress,a.PrjProgTempNo,a.CustType,a.ConfirmBegin,h.NOTE CustTypeDescr,a.Source,b.NOTE SourceDescr,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,a.Mobile1,a.Mobile2,"
				+ "a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE DesignStyleDescr,a.BeginDate,a.ConstructType,g.NOTE ConstructTypeDescr,a.PlanAmount,"
				+ "a.DocumentNo,a.AgainMan,e3.NameChi AgainManDescr,a.BuilderCode,m.Descr BuilderCodeDescr,a.ProjectMan,e4.NameChi ProjectManDescr,e4.Phone ProjectManPhone, j.note CheckStatusDescr,a.CustCheckDate,"
				+ "a.oldCode,a.saleType,pp.minPk,i.note saleTypeDescr,ppt.Descr PrjProgTempNoDescr, "
				+ "a.constructstatus,setdate,signdate,d1.Desc1 dept1Descr,d2.Desc1 dept2Descr,tc.type custTypeType " 
				+ " from tCustomer a "
				+ " left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ " left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ " left outer join tBuilder m on a.BuilderCode=m.Code "
				+ " left outer join tPrjProgTemp ppt on ppt.No=a.PrjProgTempNo "
				+ " left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
				+ " left outer join (select min(pk) minPK,CustCode  from tPrjProg group by CustCode )pp on pp.CustCode=a.Code "
				+ " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " left outer join tXTDM g on a.ConstructType=g.CBM and g.ID='CONSTRUCTTYPE' "
				+ " left outer join tXTDM h on a.CustType=h.CBM and h.ID='CUSTTYPE' "
				+ " left outer join tXTDM i on a.saleType=i.CBM and i.ID='SALETYPE' "
				+ " left outer join tXTDM j on a.CheckStatus=j.CBM and j.ID='CheckStatus' "
				+ " left outer join tEmployee e4 on a.ProjectMan = e4.Number "
				+ " left outer join tDepartment1 d1 on d1.Code = e4.Department1 "
				+ " left outer join tDepartment2 d2 on d2.Code = e4.Department2 "
				+ " left join tCusttype tc on tc.code=a.custType "
				+ " where a.code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Double getJzyjjs(String custCode) {
		String sql = "select (BaseFee_Dirct+BaseFee_Comp-BaseDisc-MainDisc*ContainMain-SoftDisc*ContainSoft"
				+"-IntegrateDisc*ContainInt-CupBoardDisc*ContainCup)*ContainBase-MarketFund ret "
				+"from tCustomer where Code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return (Double) list.get(0).get("ret");
		}
		return null;
	}
	public void doQzjs(Customer customer){
		String sql="update tCustomer set ConstructStatus='5',constadate=getdate() where code=?";
		this.executeUpdateBySql(sql, new Object[]{customer.getCode()});
	}
	public void doQzjsth(Customer customer){
		String sql="update tCustomer set ConstructStatus='1',constadate=null where code=?";
		this.executeUpdateBySql(sql, new Object[]{customer.getCode()});
	}
	public String getZsgStatus(String custCode,String status) {
		String sql = " select  case when (select 1 from tXTCS where  id='paytype') <> 1 then '请先设置套餐类付款方式!' " +
				" when isnull((select 1 from tcustomer where code=? and status = ?), 0) <> 1 then '客户状态发生变化，请重新查询!'" +
				" when (select 1 where ?='4' or ?='5' ) =1 then '此状态客户不能进行转施工操作!' " +
				" when isnull((select  1 from tcustomer a " +
				"     where a.code=? and exists (" +
				"			select 1 from tCusttype where IsAddAllInfo='1' and Code=a.CustType and isnull(a.Position,'' ) ='' " +
				"			)" +
				"		  ),'')=1 then '设计费标准未填,不能进行转施工!' " +
				" when (select 1 from tCustomer a left join tCusttype ct on a.CustType=ct.Code " +
				"     where a.Code=? and signDate is null " +
				"         and ct.isPartDecorate not in('2','3') )= 1 then '【签单时间】未设置不允许转施工，请先设置【签单时间】，再进行转施工!' " +
				" when (select 1 from tCustomer a left join tCusttype ct on a.CustType=ct.Code " +
				"     where a.Code=? and a.setDate is null and signDate is not null " +
				"         and ct.isPartDecorate not in('2','3') )= 1 then '【下定时间】未设置【签单时间】已设置!' " +
				" when (select 1 from tCustomer a left join tCusttype ct on a.CustType=ct.Code " +
				"     where a.Code=? and a.signDate is null " +
				"         and ct.isPartDecorate in('2','3') )= 1 then '【签单时间】未设置不允许转施工，请先设置【签单时间】，再进行转施工!' " +
				" else '' end status ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,status,status,status,custCode,custCode,custCode,custCode});
		if (list!=null && list.size()>0){
			return list.get(0).get("status").toString();
		}
		
		return null;
	}
	
	public String getConBackNotify(String custCode,String status,String endCode,String m_umStatus) {
		String sql = "select case when (select 1 from tcustomer where code= ? and status = ? )<> '1' then '客户状态发生变化，请重新查询!' " +
				"  when (select 1 from tcustomer where code=? and EndCode = ? )<> '1' then '客户结束原因发生变化，请重新查询!'" +
				"    when ? <>'4' then '此状态客户不能进行施工回退操作!'" +
				"      when (select case when (" +
				"					select count(*) as Count from tItemApp where Status='SEND' and  CustCode = ? " +
				"			)>1 then 1 else 0 end)= 1 then '此客户已经进行领料发货,无法进行回退操作!' " +
				"		when (select 1 from tcustomer where HadCalcPerf='1' and code=? )='1' then '此客户已计算业绩,无法进行回退操作!'" +
				"		  when ( select case when (" +//
				"					select count(*) as Count from tItemChg where CustCode =? " +
				"				)>0 and ?='B' then 1 else 0 end " +
				"		  )=1 then '此客户已经进行材料增减,无法进行回退操作!'" +
				"		   when (select case when (" +
				"					select count(*) as Count from tBaseItemChg where CustCode = ? " +
				"				)>0  and ?='B' then 1 else 0 end" +
				"		   )= 1 then '此客户已经进行基础增减,无法进行回退操作!'" +
				"			 when (select case when (" +
				"					select count(*) Count from tItemApp where Expired='F' and Status<>'CANCEL' and CustCode = ? " +
				"				)>0  and ?='B' then 1 else 0 end " +
				"			 )= 1 then '此客户存在非取消状态的领料单,无法进行回退操作!' " +
				"		  when ( select case when (" +
				"					select count(*) as Count from tItemChg where CustCode =? " +
				"				)>0 and ?='G' then 1 else 0 end " +
				"		  )=1 then '此客户已经进行增减操作!'" +
				"		   when (select case when (" +
				"					select count(*) as Count from tBaseItemChg where CustCode = ? " +
				"				)>0  and ?='G' then 1 else 0 end" +
				"		   )= 1 then '此客户已经进行增减操作!'" +
				"			 when (select case when (" +
				"					select count(*) Count from tItemApp where Expired='F' and Status<>'CANCEL' and CustCode = ? " +
				"				)>0  and ?='G' then 1 else 0 end " +
				"			 )= 1 then '此客户存在非取消状态的领料单!' " +
				" else '' end notify " +
				"";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,status,custCode,endCode,status,custCode,custCode,
															custCode,m_umStatus,custCode,m_umStatus,custCode,m_umStatus,
															custCode,m_umStatus,custCode,m_umStatus,custCode,m_umStatus});
		if (list!=null && list.size()>0){
			return list.get(0).get("notify").toString();
		}
		return null;
	}
	
	public String getJzNotify(String custCode,String status) {
		String sql = " select case when (select status from tcustomer where code= ? ) <> ? then'客户状态发生变化，请重新查询!'" +
				" when  (select count(*) num from tAgainSign where CustCode=? ) >0 then '重签的楼盘不允许做结转操作!'" +
				" when (?='4' or ?='5') then  '此状态客户不能进行结转操作!' " +
				" when ((select count(*) as Count from tItemApp where Status='SEND' and  CustCode = ?)>0 and ?<>'4') then '已领材料，不允许在非施工状态做结转操作' " +
				" else '' end  notify " +
				"";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,status,custCode,status,status,custCode,status});
		if (list!=null && list.size()>0){
			return list.get(0).get("notify").toString();
		}
		return null;
	}
	
	public String getJzReturnNotify(String custCode,String status,String endCode) {
		String sql = " select case when (select status from tcustomer where code = ? ) <> ? then '客户状态发生变化，请重新查询'" +
				" when (select endcode from tCustomer where code=? ) <> ? then '客户结束原因发生变化，请重新查询!'" +
				"  when '5'<> ? then '此状态客户不能进行结转回退操作!' " +
				"   when ('1'<>? and '2'<>? and '5'<>? and '6'<>? ) then '非结转客户不允许进行结转回退！'" +
				"    when (select count(*) as Count from tItemApp where Status='SEND' and  CustCode =?) >0 then '此客户已经进行领料发货,无法进行回退操作!'" +
				"	  when (select count(*) as Count from tItemChg where CustCode = ?)>0 then  '此客户已经进行材料增减,无法进行回退操作!'" +
				"	   when (select count(*) as Count from tBaseItemChg where CustCode = ?)> 0 then '此客户已经进行基础增减,无法进行回退操作!'" +
				"       when (select 1 from tCustomer a where a.HadCalcPerf='1' and a.Code=?)=1 then '此客户业绩已计算业绩,无法进行回退操作!'" +
				"   else '' end  notify" +
				"";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,status,custCode,endCode,status,
																endCode,endCode,endCode,endCode,
																custCode,custCode,custCode,custCode});
		if (list!=null && list.size()>0){
			return list.get(0).get("notify").toString();
		}
		return null;
	}
	
	public String getResignNotify(String custCode,String status,String endCode) {
		String sql = " select case when (select status from tcustomer where Code=?) <>? then '客户状态发生变化，请重新查询!'" +
				" when (select endCode from tcustomer where code= ? )<>? then '客户结束原因发生变化，请重新查询!'" +
				"  when ? <>'4' then '此状态客户无需进行重签操作！'" +
				"   when (select count(*) as Count from tItemApp where Status='SEND' and ItemType1<>'JZ' and  CustCode = ? )>0  " +
				"			then '此客户已经进行领料发货,无法进行重签操作!'" +
				"    when (select count(*) as Count from tItemApp where " +
				"		Status in ('OPEN','CONFIRMED','RETURN') and ItemType1='ZC' and  CustCode = ? ) >0 " +
				"			then '此客户存在已申请、已审核、审核退回的主材领料单,无法进行重签操作!'" +
				"     when ? ='5' and ? <>'1' then '归档状态，只有结转为纯设计的客户才能进行重签操作！' else '' end " +
				"";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,status,custCode,endCode,
											status,custCode,custCode,status,endCode});
		if (list!=null && list.size()>0){
			return list.get(0).get("notify").toString();
		}
		return null;
	}
	
	public String getReSignNotify(String custCode,String status,String endCode) {
		String sql = "select case when (select status from tCustomer where Code= ? ) <>? then '客户状态发生变化，请重新查询!'" +
				"  when (select endCode from tCustomer where Code=?)<>? then '客户结束原因发生变化，请重新查询!'" +
				"   when (? <>'4' and ?<>'5') then '此状态客户无需进行重签操作！' " +
				"    when (select count(*) as Count from tItemApp where " +
				"		Status='SEND' and ItemType1<>'JZ' and  CustCode = ?)>0 then '此客户已经进行领料发货,无法进行重签操作!'" +
				"	 when (select count(*) as Count from tItemApp where " +
				"		Status in ('OPEN','CONFIRMED','RETURN') and ItemType1='ZC' and  CustCode = ?)>0 " +
				"		 then '此客户存在已申请、已审核、审核退回的主材领料单,无法进行重签操作!'" +
				"	  when (?='5' and ?<>'1') then '归档状态，只有结转为纯设计的客户才能进行重签操作！' " +
				"  		when (select count(*) as Count from tItemChg where CustCode = ? )>0 then 'notify'" +
				" 			when (select count(*) as Count from tBaseItemChg where CustCode = ? ) >0 then 'notify'  " +
				" else '' end notify " +
				"";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,status,custCode,endCode,status,
																		status,custCode,custCode,status,endCode,custCode,custCode});
		if (list!=null && list.size()>0){
			return list.get(0).get("notify").toString();
		}
		return null;
	}
	
	public String getPosiDesignFee(String position, String custType) {
		String sql = "select DesignFee from tDesignFeeSd a where a.Position=? "
				   +" and (a.CustType=? "
				   +" or (not exists(select 1 from tDesignFeeSd in_d where in_d.Position=? and in_d.CustType=?) "
				   +" and (a.CustType is null or a.CustType=''))) ";    
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{position, custType, position, custType});
		if (list!=null && list.size()>0){
			return list.get(0).get("DesignFee").toString();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public Result doSghtxx_return(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSGhtxx_sg_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getM_umState());
			call.setString(2, customer.getCode());
			call.setString(3, customer.getStatus());
			call.setString(4, customer.getToStatus());
			call.setString(5, customer.getLastUpdatedBy());
			call.setDouble(6, 0);
			call.setDouble(7, 0);
			call.setDouble(8, 0);
			call.setDouble(9, 0);
			call.setString(10, "0");
			call.setString(11, "1");
			call.setString(12, "1");
			call.setString(13, "1");
			call.setString(14, "");
			call.setString(15, "");
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
			call.setDouble(18, customer.getPerfMarkup());
			call.setTimestamp(19, customer.getToConstructDate()== null ? null
					: new Timestamp(customer.getToConstructDate().getTime()));
			call.execute();
			result.setCode(String.valueOf(call.getInt(16)));
			result.setInfo(call.getString(17));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public String getDesignPicStatus(String custCode) {
		String sql = " select  status from tDesignPicPrg  where custCode = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return list.get(0).get("status").toString();
		}
		return "0";
	}
	
	public String checkDesignFee(String custCode) {
		String sql = " select 1 from tCustPay a " +
				" inner join tCustomer b on a.CustCode=b.Code " +
				" where b.Area<>0 and a.CustCode= ? " +
				" group by b.Area having  sum(a.Amount)/b.Area>=40 ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return "true";
		}
		return "false";
	}
	
	public String getDesignFeeType(String custType,String payType) {
		String sql = " select case when isnull(prd1.IsRcvDesignFee,'0')='1' or isnull(prd2.IsRcvDesignFee,'0')='1' or isnull(prd3.IsRcvDesignFee,'0')='1' or isnull(prd4.IsRcvDesignFee,'0')='1' then pr.DesignFeeType else '3' end  DesignFeeType "
				    + " from tPayRule pr  "
			        + " left outer join tPayRuleDetail prd1 on prd1.no=pr.No and prd1.paynum='1' "
			        + " left outer join tPayRuleDetail prd2 on prd2.no=pr.No and prd2.paynum='2' "
			        + " left outer join tPayRuleDetail prd3 on prd3.no=pr.No and prd3.paynum='3' "
			        + " left outer join tPayRuleDetail prd4 on prd4.no=pr.No and prd4.paynum='4' "
					+ " where pr.CustType=? and pr.PayType=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custType,payType});
		if (list!=null && list.size()>0){
			return list.get(0).get("DesignFeeType").toString();
		}
		return "";
	}
	
	@SuppressWarnings("deprecation")
	public Result doSaveZsg(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSGhtxx_sg_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getM_umState());
			call.setString(2, customer.getCode());
			call.setString(3, customer.getFromStatus());
			call.setString(4, customer.getToStatus());
			call.setString(5, customer.getLastUpdatedBy());
			call.setDouble(6, customer.getFirstPay());
			call.setDouble(7, customer.getSecondPay());
			call.setDouble(8, customer.getThirdPay());
			call.setDouble(9, customer.getFourPay());
			call.setString(10, customer.getIsInternal());
			call.setString(11, customer.getTileStatus());
			call.setString(12, customer.getBathStatus());
			call.setString(13, customer.getPayType());
			call.setString(14, customer.getConPhone());
			call.setString(15, customer.getConId());
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
			call.setDouble(18, customer.getPerfMarkup());
			call.setTimestamp(19, customer.getToConstructDate()== null ? null
					: new Timestamp(customer.getToConstructDate().getTime()));
			call.setDouble(20, customer.getDesignRiskFund()==null?0:customer.getDesignRiskFund());
			call.setDouble(21, customer.getPrjManRiskFund()==null?0:customer.getPrjManRiskFund());
			call.setString(22, customer.getPayeeCode());
			call.setDouble(23, customer.getFrontEndDiscAmount()==null?0:customer.getFrontEndDiscAmount());
			call.setDouble(24, customer.getCmpDiscAmount()==null?0:customer.getCmpDiscAmount());
			call.setString(25, customer.getIsInitSign());
			call.setDouble(26, customer.getDesignerMaxDiscAmount()==null?0:customer.getDesignerMaxDiscAmount());
			call.setDouble(27, customer.getDirectorMaxDiscAmount()==null?0:customer.getDirectorMaxDiscAmount());
			call.execute();
			result.setCode(String.valueOf(call.getInt(16)));
			result.setInfo(call.getString(17));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doSaveJz(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSghtxx_jz_forProc(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getM_umState());
			call.setString(2, customer.getCode());
			call.setString(3, customer.getEndCode());
			call.setTimestamp(4, customer.getEndDate() == null ? null
					: new Timestamp(customer.getEndDate().getTime()));
			call.setString(5, customer.getStatus().trim());
			call.setString(6, customer.getToStatus());
			call.setString(7, customer.getEndRemark());
			call.setString(8, customer.getLastUpdatedBy());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(9)));
			result.setInfo(call.getString(10));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doReSign(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSGhtxx_cq_forXml(?,?,?,?,?,?,?)}");
			call.setString(1, customer.getCode());
			call.setString(2, customer.getFromStatus());
			call.setString(3, customer.getToStatus());
			call.setString(4, customer.getRemarks());
			call.setString(5, customer.getLastUpdatedBy());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public String getOldStatus(String custCode) {
		String sql = "select a.FromStatus FromStatus from tCustStatusConv a where  a.pk= (select  max(pk)from tCustStatusConv where CustCode= ? ) " ;
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return list.get(0).get("FromStatus").toString();
		}
		return null;
	}
	
	public Page<Map<String, Object>> findPageBySql_viewResign(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select a.CustCode,b.Address,a.CustType,c.Desc1 custTypeDescr,a.SignDate,b.SignDate signDateNow,a.ContractType, " +
				" d.NOTE contractTypeDescr,a.ContractFee,a.NewSignDate,a.Remarks, a.DocumentNo OldDocumentNo, b.DocumentNo NewDocumentNo, " +
				" a.DesignFee " +
				" from tAgainSign a " +
				" inner join tCustomer b on a.CustCode=b.Code " +
				" left join tCusttype c on a.CustType=c.Code " +
				" left join tXTDM d on a.ContractType=d.CBM and d.ID='CONTYPE'" +
				" where 1=1 ";
		
		if(customer.getSignDateFrom()!=null){
			sql+=" and b.signDate >= ?";
			list.add(new Timestamp(
				DateUtil.startOfTheDay( customer.getSignDateFrom()).getTime()));
		}
		if(customer.getSignDateTo()!=null){
			sql+=" and b.signDate <= ?";
			list.add(new Timestamp(
				DateUtil.endOfTheDay( customer.getSignDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and b.address like ?";
			list.add("%"+customer.getAddress()+"%");
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getFourPay(String custCode,String payType) {
		String sql = " exec pGetFourCustPay ?,2,?,0,0,0,0 " ;
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,payType});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Integer getCustConsDay(String custCode) {
		String sql = " select b.ConstructDay from tCustomer a " +
				" inner join tCstrDayStd b " +
				" on ((isnull(b.CustType,'')='' or a.CustType=b.CustType) and (isnull(b.Layout,'')='' or a.Layout=b.Layout)) " +
				" and a.Area>=b.FromArea and a.Area<=b.ToArea " +
				" where a.Code= ? order by b.Prior desc   " ;
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return Integer.parseInt(list.get(0).get("ConstructDay").toString());
		}
		return null;
	}
	
	public List<Map<String, Object>> getItemReqCount(String custCode) {
		String sql = " select b.CustCode,a.Code ItemType1,isnull(b.LineAmount,0) LineAmount,a.Descr ItemType1Descr "+
				" from tItemType1 a "+
				" left join (select in_b.CustCode,in_b.ItemType1,sum(LineAmount) LineAmount from tItemType1 in_a "+
				" left join tItemReq in_b on in_b.ItemType1=in_a .code "+
				" where CustCode=? and ItemType1 in ('RZ','ZC','JC') "+ //修改custcode=?的错误 modify by zb,zzr on 20190711
				" group by in_b.CustCode,in_b.ItemType1,in_a .Descr )b on a.Code=b.ItemType1"+
				" where a.Code in ('RZ','ZC','JC') "+
				" order by case when a.Code='ZC' then 1 "+
				" when a.Code='JC' then 2 "+
				" when a.Code='RZ' then 3 end ";
		return this.findBySql(sql, new Object[]{custCode});
	}
	public Page<Map<String, Object>> getPrjReport(
			Page<Map<String, Object>> page, String custCode) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select '' preUrl,*,STUFF(( SELECT ',' + PhotoName FROM tPrjProgPhoto WHERE IsPushCust = '1' " +
				"		and  RefNo = a.No and sendDate is not null and a.typeCode in ('1','3') FOR XML PATH('') ), 1, 1, '') photoList," +
				" STUFF(( SELECT ',' + PhotoName FROM tPrjProgPhoto WHERE IsPushCust = '1' " +
				"		and  RefNo = a.No and sendDate is null and a.typeCode in ('1','3') FOR XML PATH('') ), 1, 1, '') photoListNoSend ," +
				" STUFF(( SELECT ',' + PhotoName FROM tSignInPic WHERE 1=1" +
				" 	and  No = a.No and sendDate is not null and  a.typeCode = '4' FOR XML PATH('') ), 1, 1, '') empSignPic,  " +
				" STUFF(( SELECT ',' + PhotoName FROM tSignInPic WHERE 1=1" +
				" 	and  No = a.No and sendDate is null and a.typeCode = '4' FOR XML PATH('') ), 1, 1, '') empSignNotSendPic," +
				" STUFF(( SELECT ',' + PhotoName FROM tWorkSignPic WHERE 1=1 and IsPushCust = '1'" +
				"				 	and  No = a.No and a.typeCode = '2' and sendDate is not null FOR XML PATH('') ), 1, 1, '') workSignPic,  " +
				"				 STUFF(( SELECT ',' + PhotoName FROM tWorkSignPic WHERE 1=1 and IsPushCust = '1'" +
				"				 	and  No = a.No and a.typeCode = '2' and sendDate is null FOR XML PATH('') ), 1, 1, '') workSignNotSendPic  " +
				" from ( select a.No,'工地巡检' type,date, case when (d.Desc1 is null or d.Desc1 = '') and (d.Desc2 is null or d.Desc2 = '')  then b.NameChi" +
				"        else b.NameChi + '-' + case when d.Desc2 is null or d.Desc2 = '' then d.Desc1 else d.Desc2 end" +
				"              end comeCzy ,'' isPass,'' prjDescr,'' itemType,'1' typeCode,'' workTypeDescr,a.CustScore,a.CustRemarks,case when a.CustScore>0 then '0' else '1' end isEval,-1 pks  from tPrjProgCheck a " +
				" left join tEmployee b on b.Number=a.AppCZY " +
				" left join tPosition d on d.code=b.Position " +
				" where a.CustCode= ? and a.isPushCust = '1' " +
				" union all" +
				" select a.no no,'工人签到' type,a.CrtDate date ,b.NameChi comeCzy,'' isPass,c.Descr prjDescr,'' itemType,'2' typeCode,e.descr workTypeDescr,a.CustScore,a.CustRemarks,case when a.CustScore>0 then '0' else '1' end isEval,a.PK pks from tWorkSign a " +
				" left join tWorker b on b.Code=a.WorkerCode " +
				" left join tPrjItem2 c on c.Code=a.PrjItem2 " +
				" left join tCustWorker d on d.pk=a.CustWkPk" +
				" left join tWorkType12 e on e.code=d.WorkType12" +
				" where a.CustCode= ? and a.IsComplete <> '1' " +
				" union all" +
				" select a.no,'工地验收' type,a.date, case when (d.Desc1 is null or d.Desc1 = '') and (d.Desc2 is null or d.Desc2 = '')  then b.NameChi" +
				"      else b.NameChi + '-' + case when d.Desc2 is null or d.Desc2 = '' then d.Desc1 else d.Desc2 end" +
				"                    end comeCzy ," +
				" case when a.IsPass =1 then '是' else '否' end isPass,c.Descr prjDescr,'' itemType,'3' typeCode,'' workTypeDescr,a.CustScore,a.CustRemarks,case when a.CustScore>0 then '0' else '1' end isEval,-1 pks  from tPrjProgConfirm a " +
				" left join tEmployee b on b.Number=a.LastUpdatedBy" +
				" left join tPrjItem1 c on a.PrjItem=c.Code" +
				" left join tPosition d on d.code=b.Position " +
				" where a.CustCode= ? and a.isPushCust = '1' " +
				" union all" +
				" select a.no ,case when c.Descr is null or c.Descr ='' then '工地巡检' else c.Descr end prjDescr,CrtDate date," +
				"	 case when (d.Desc1 is null or d.Desc1 = '') and (d.Desc2 is null or d.Desc2 = '')  then b.NameChi " +
				" else b.NameChi + '-' + case when d.Desc2 is null or d.Desc2 = '' then d.Desc1 else d.Desc1 end end comeCzy," +
				"	'' isPass,'' prjDescr ,'' itemType,'4' typeCode,'' workTypeDescr,a.CustScore,a.CustRemarks,case when a.CustScore>0 then '0' else '1' end isEval,a.PK pks  from tSignIn a " +
				" left join tEmployee b on b.Number=a.SignCZY" +
				" left join tPosition d on d.Code=b.Position" +
				" left join tSignInType2 c on c.Code=a.SignInType2" +
				" where a.CustCode= ?" +
				" union all" +
				" select  a.no,'材料进场' Type,a.SendDate date,'' comeCzy,'' isPass ,'' prjDescr,e.Descr +'-'+d.Descr itemType,'5' typeCode,'' workTypeDescr,'' CustScore,'' CustRemarks ,'0' isEval, -1 pks " +
				"  from tItemApp a" +
				" left join (" +
				"	select MAX(itemCode) itemCode,No  from tItemAppDetail a group by No " +
				" ) b on b.No=a.No" +
				" left join tItem c on c.Code=b.ItemCode " +
				" left join tItemType1 e on e.Code=a.ItemType1" +
				" left join tItemType2 d on d.Code =c.ItemType2" +
				" where a.CustCode= ? and a.SendDate is not null and a.Status ='SEND' and  a.ItemType1 in ('RZ','JZ','ZC') " +
				/*" union all" +
				" select a.no,'员工签到' type,a.CrtDate date,b.NameChi comeCzy,'' IsPass,c.Descr prjDescr,'' itemType,'6' typecode from tSignIn a " +
				" left join tEmployee b on b.Number=a.SignCZY" +
				" left join tPrjItem1 c on c.Code=a.PrjItem" +
				" where a.CustCode= ? " +*/
				" ) a  order by DATE desc,type";
		list.add(custCode);
		list.add(custCode);
		list.add(custCode);
		list.add(custCode);
		list.add(custCode);
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> getItemType12ReqCount(String custCode) {
		String sql = " select isnull(b.LineAmount,0) LineAmount,a.ItemType1 ,a.Code ItemType12,a.Descr ItemType12Descr,b.qty from tItemType12 a " 
				+" left join ( "
				+" select sum(LineAmount) LineAmount,c.ItemType12,a.ItemType1,sum(qty) qty from tItemReq a "
				+" left join tItem b on a.ItemCode=b.Code "
				+" left join tItemType2 c on b.ItemType2=c.Code "
				+" where CustCode=? "
				+" group by a.ItemType1,c.ItemType12 "
				+" ) b on a.code=b.ItemType12 "
				+" where a.Expired='F'  and a.ItemType1 in ('ZC','RZ') "
				+" order by case when a.ItemType1='ZC' then 1 "
				+" when a.ItemType1='RZ' then 2 end,a.DispSeq";
		return this.findBySql(sql, new Object[]{custCode});
	}
	public Page<Map<String, Object>> findItemSendListPage(
			Page<Map<String, Object>> page, String custCode,String itemType1) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select a.no,a.SendDate date,e.Descr itemType1,d.Descr itemType2 from " +
				" tItemApp a left join (	" +
				" select MAX(itemCode) itemCode,No  from tItemAppDetail a group by No  ) b on b.No=a.No " +
				" left join tItem c on c.Code=b.ItemCode  " +
				" left join tItemType1 e on e.Code=a.ItemType1 " +
				" left join tItemType2 d on d.Code =c.ItemType2 " +
				" where a.CustCode= ? and a.SendDate is not null and a.Status ='SEND'  and a.ItemType1 not in ('JC','LP')" +
				"  ";
		list.add(custCode);
		if(StringUtils.isNotBlank(itemType1)){
			sql+=" and c.itemType1 = ? ";
			list.add(itemType1);
		}
		sql+=" order by a.SendDate desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> getJCReqCount(String custCode) {
		String sql = " select a.LineAmount,'0' IsCupboard,tit1.Code ItemType1,a.qty  "
					+" from tItemType1 tit1 "
					+" ,( "
					+"	select isnull(sum(a.LineAmount), 0) LineAmount,isnull(sum(a.qty), 0) qty "
					+"	from tItemReq a "
					+"	left join tIntProd b on a.IntProdPK = b.PK "
					+"	where a.CustCode=? and b.IsCupboard<>'1' "
					+" ) a "
					+" where tit1.Code='JC' "
					+" union all "
					+" select a.LineAmount,'1' IsCupboard,tit1.Code ItemType1,a.qty  "
					+" from tItemType1 tit1 "
					+" ,( "
					+"	select isnull(sum(a.LineAmount), 0) LineAmount,isnull(sum(a.qty), 0) qty "
					+"	from tItemReq a "
					+"	left join tIntProd b on a.IntProdPK = b.PK "
					+"	where a.CustCode=? and b.IsCupboard='1' "
					+" ) a "
					+" where tit1.Code='JC' ";
		return this.findBySql(sql, new Object[]{custCode,custCode});
	}

	public List<Map<String, Object>> getCustomerItemType12DetailNeeds(Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.*,isnull(d.IsCheckOutQty,0) IsCheckOutQty,c.Descr FixAreaDescr,b.Descr ItemDescr,e.Descr Uom "
				+ " from tItemReq a left join "
				+ "   (select sum(case when bb.Type='S' then aa.Qty else -aa.Qty end) IsCheckOutQty,aa.ReqPK from  tItemAppDetail aa "
				+ "   left outer join tItemApp bb on bb.No=aa.No "
				+ "   left outer join tPurchase cc on cc.No=bb.PUNo "
				+ "   where cc.IsCheckOut='1' group by aa.ReqPk) d "
				+ " on d.ReqPK=a.PK "
				+ " left outer join tItem b on b.Code=a.ItemCode "
				+ " left outer join tFixArea c on c.Pk=a.FixAreaPK "
				+ " left outer join tUom e on b.Uom=e.Code "
				+ " left join tItemType2 f on b.ItemType2=f.Code "
				+ " left join tIntProd g on a.IntProdPK = g.PK "
				+ " where (a.qty<>0 or a.ProcessCost<>0 or a.SendQty<>0) ";
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode=? ";
			list.add(customer.getCode());
		}
		if(StringUtils.isNotBlank(customer.getItemType1())){
			sql+=" and f.ItemType12=? ";
			list.add(customer.getItemType1());
		}
		if(StringUtils.isNotBlank(customer.getIsCupboard())){
			sql+=" and g.IsCupboard=? ";
			list.add(customer.getIsCupboard());
		}
		sql+= " order by a.itemType1,a.DispSeq,a.FixAreaPk";
		return this.findBySql( sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findItemSendDetailListPage(
			Page<Map<String, Object>> page, String no) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select e.descr uomDescr,a.SendQty qty ,b.Descr itemdescr ,c.Descr itemType2Descr,d.Descr fixAreaDescr from tItemAppDetail a " +
				" left join titem b on b.Code=a.ItemCode" +
				" left join tItemType2 c on c.code=b.ItemType2" +
				" left join tFixArea d on d.PK=a.FixAreaPK " +
				" left join tuom e on e.code=b.uom " +
				" where No = ? " +
				"";
		list.add(no);
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getConstractPayPage(
			Page<Map<String, Object>> page, String custCode) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select a.FirstPay,a.SecondPay,a.ThirdPay,a.FourPay," +
				" a.ContractFee,b.Amount," +
				"  b.Remarks,isnull(PK,0) as PK,c.DocumentNo  from tCustomer a " +
				"  left join tCustPay b on b.CustCode = a.Code " +
				"  left join tPayCheckOut c on c.No=b.PayCheckOutNo where Code = ? ) a  " +
				"";
		list.add(custCode);
		
		return this.findPageBySql(page, sql, list.toArray());
	}	
	
	public Result doUpdaetContractFee(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pUpdateContractFee_forProc(?,?,?)}");
			call.setString(1, customer.getCode());
			call.registerOutParameter(2, Types.INTEGER);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(2)));
			result.setInfo(call.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public List<Map<String,Object>> getBuildPassword(String code,int custWkPk){
		String sql = "select top 1 a.Address,a.BuildPass,d.IsPass from tCustomer a "
				+" left join tCustWorker b on a.Code=b.CustCode "
				+" left join tPrjItem1 c on b.WorkType12=c.worktype12 "
				+" left join tPrjProgConfirm d on b.CustCode=d.CustCode and c.Code=d.PrjItem and d.IsPass='1'"
				+" where b.PK=? and a.Code= ?";
		return this.findBySql(sql, new Object[]{custWkPk,code});
	}

	public boolean getMatchConDay(String custCode) {
		
		String sql = "select b.ConstructDay from tCustomer a inner join tCstrDayStd b "
        + " on (a.CustType=b.CustType or a.Layout=b.Layout) and a.Area>=b.FromArea and a.Area<=b.ToArea "
        + " where a.Code= ? order by b.Prior desc   ";

		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}	

	
	public void updateCustAccountPk(String mobile1 ){
		String sql = " insert tCustMapped(CustCode, CustAccountPK) "
				   + " select tc.Code,tca.PK "
				   + " from tCustAccount tca "
				   + " left join tCustomer tc on tca.Mobile1 = tc.Mobile1 "
				   + " where tca.Mobile1 = ? and datediff(day,tc.CrtDate,getdate()) < 360 ";
		this.executeUpdateBySql(sql, new Object[]{mobile1});
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemTypeByCustCode(
			Page<Map<String, Object>> page, String code) {
		List<Object> list = new ArrayList<Object>();
		list.add(code);
		String sql = " select  c.Code value ,c.descr  from tItemReq a " +
				" left join titem b on a.ItemCode=b.Code" +
				" left join tItemType1 c on c.Code=b.ItemType1" +
				" where a.CustCode= ?  " +
				" group by c.Code,c.descr ";

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemType2ByCustCode(
			Page<Map<String, Object>> page, String code,String itemType1) {
		List<Object> list = new ArrayList<Object>();
		list.add(code);
		String sql = "select  d.Code value ,d.descr  from tItemReq a " +
				" left join titem b on a.ItemCode=b.Code" +
				" left join tItemType1 c on c.Code=b.ItemType1 " +
				" left join titemType2 d on d.code=b.itemType2 " +
				" where a.CustCode= ? " ;
			if(StringUtils.isNotBlank(itemType1)){
				sql+=" and b.itemType1= ? ";
				list.add(itemType1);
			}
			sql+=" group by d.Code,d.descr  ";

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemTypeChgByCustCode(
			Page<Map<String, Object>> page, String code) {
		List<Object> list = new ArrayList<Object>();
		list.add(code);
		String sql = " select  b.Code value ,b.descr  from tItemChg a " +
				" left join tItemType1 b on b.Code=a.ItemType1" +
				" where a.CustCode= ? " +
				" group by b.Code,b.descr ";

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemType2ChgByCustCode(
			Page<Map<String, Object>> page, String code,String itemType1) {
		List<Object> list = new ArrayList<Object>();
		list.add(code);
		String sql = "select d.Descr descr,d.code value from tItemChg a" +
				" left join tItemChgDetail b on b.No =a.No" +
				" left join titem c on c.Code=b.ItemCode " +
				" left join tItemType2 d on d.Code=c.ItemType2" +
				" where a.CustCode= ? " ;
			
		if(StringUtils.isNotBlank(itemType1)){
				sql+=" and c.itemType1= ? ";
				list.add(itemType1);
			}
			sql+=" group by d.Code,d.descr  ";

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findItemType1ListPage(
			Page<Map<String, Object>> page) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select code value,descr descr from titemType1 where expired ='F' and Code not in ('JC','LP') ";

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 修改安装电梯，搬运楼层
	 * @author cjg
	 * @date 2019-4-20
	 * @param customer
	 */
	public void updateCarryFloor(Customer customer ){
		String sql = "update tCustomer set InstallElev=? , CarryFloor=?,LastUpdate=getdate(),LastUpdatedBy=?,ActionLog='Edit',CarryRemark=? where Code=? ";
		this.executeUpdateBySql(sql, new Object[]{customer.getInstallElev(),customer.getCarryFloor(),customer.getLastUpdatedBy(),customer.getCarryRemark(),customer.getCode()});
	}
	
	
	public Page<Map<String, Object>> findPageBySql_jcys(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (  select (select ProjectCtrlAdj from tCustomer where code = ? )ProjectCtrlAdj," +
				"				(select CtrlAdjRemark from tCustomer where code = ? ) CtrlAdjRemark" +
				"				,wt1.dispseq,wt1.code workType1,wt1.Descr WorkType1Descr," +
				"             isnull(aa.sumUnitPrice,0) sumUnitPrice,isnull(bb.sumMaterial,0) sumMaterial," +
				"             isnull(aa.sumUnitPrice,0)+isnull(bb.sumMaterial,0) sumLineAmount," +
				"             isnull(aa.sumUnitPriceCtrl,0) sumUnitPriceCtrl,isnull(bb.sumMaterialCtrl,0) sumMaterialCtrl," +
				"             isnull(aa.sumUnitPriceCtrl,0)+isnull(bb.sumMaterialCtrl,0) sumLineAmountCtrl " +
				"             from " +
				"             tWorkType1 wt1" +
				"             left outer join (" +
				"				select wt2.WorkType1, " +
				"             sum(round(a.qty*a.UnitPrice,0)) sumUnitPrice, " +
				"            sum(round((case when b.BaseCtrlPer Is not null then " +
				"                                 (isnull(a.qty * a.unitprice, 0) * b.BaseCtrlPer) else " +
				"						 		(case when a.PrjCtrlType='1' then a.UnitPrice*a.OfferCtrl else a.OfferCtrl end)*a.qty " +
				"			end  ),2)) sumUnitPriceCtrl " +
				"             from tBaseItemPlan a " +
				"             inner join tCustomer c on a.CustCode=c.code " +
				"				inner join tBaseItem bi on bi.code=a.BaseItemCode " +
				"             inner join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2 " +
				"             inner join tWorkType2 wt2 on wt2.Code=bit2.OfferWorkType2" +
				"             left outer join tCustBaseCtrl b on  a.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
				"             where a.custcode = ? " +
				"             group by wt2.WorkType1 " +
				"             ) aa  on wt1.code=aa.WorkType1" +
				"             left outer join (" +
				"             select wt2.WorkType1, " +
				"             sum(round(a.qty*a.Material,0)) sumMaterial, " +
				"             sum(round(a.qty*a.Material,0)) sumUnitPrice, " +
				"             sum(round((case when b.BaseCtrlPer Is not null then " +
				"                                 (isnull(a.qty * a.Material, 0) * b.BaseCtrlPer) else " +
				"						 		(case when a.PrjCtrlType='1' then a.Material*a.MaterialCtrl else a.MaterialCtrl end)*a.qty " +
				"                                 end " +
				"                                 ),2)) sumMaterialCtrl             " +
				"             from tBaseItemPlan a " +
				"             inner join tCustomer c on a.CustCode=c.code " +
				"             inner join tBaseItem bi on bi.code=a.BaseItemCode " +
				"             inner join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2 " +
				"             inner join tWorkType2 wt2 on wt2.Code=bit2.MaterWorkType2" +
				"             left outer join tCustBaseCtrl b on  a.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
				"             where a.custcode = ? " +
				"             group by wt2.WorkType1 " +
				"             ) bb on wt1.code=bb.WorkType1" +
				"             where aa.WorkType1 is not null or bb.WorkType1 is not null ";
		list.add(customer.getCode());
		list.add(customer.getCode());
		list.add(customer.getCode());
		list.add(customer.getCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a   order by a.dispseq ";//wt1.dispseq
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_jczj(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.No,a.CustCode,c.Descr CustomerDescr,c.Address,c.Area,a.Status," +
				"           d.NOTE StatusDescr,a.Date,a.BefAmount,a.DiscAmount,a.Amount,a.Remarks," +
				"           a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
				"            from tBaseItemChg a " +
				"            left outer join tCustomer c on a.CustCode=c.Code " +
				"            left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS'" +
				"            where a.Status='2' and a.custCode = ?  ";
			list.add(customer.getCode());
		
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_jczjDetail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select wt1.dispseq,wt1.code workType1,wt1.Descr WorkType1Descr," +
				"             isnull(aa.sumUnitPrice,0) sumUnitPrice,isnull(bb.sumMaterial,0) sumMaterial," +
				"             isnull(aa.sumUnitPrice,0)+isnull(bb.sumMaterial,0) sumLineAmount," +
				"             isnull(aa.sumUnitPriceCtrl,0) sumUnitPriceCtrl,isnull(bb.sumMaterialCtrl,0) sumMaterialCtrl," +
				"             isnull(aa.sumUnitPriceCtrl,0)+isnull(bb.sumMaterialCtrl,0) sumLineAmountCtrl " +
				"				from " +
				"             tWorkType1 wt1" +
				"             left outer join (" +
				"             select wt2.WorkType1, " +
				"             sum(round(a.qty*a.UnitPrice,0)) sumUnitPrice, " +
				"            sum(round((case when b.BaseCtrlPer Is not null then " +
				"                                 (isnull(a.qty * a.unitprice, 0) * b.BaseCtrlPer) else " +
				"						 		(case when a.PrjCtrlType='1' then a.UnitPrice*a.OfferCtrl else a.OfferCtrl end)*a.qty " +
				"				end " +
				"                                 ),2)) sumUnitPriceCtrl " +
				"             from tBaseItemChgDetail a " +
				"             inner join tBaseItemChg bic on bic.no=a.no " +
				"             inner join tCustomer c on bic.CustCode=c.code" +
				"             inner join tBaseItem bi on bi.code=a.BaseItemCode" +
				"             inner join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2 " +
				"             inner join tWorkType2 wt2 on wt2.Code=bit2.OfferWorkType2 " +
				"			  left outer join tCustBaseCtrl b on bic.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
				"             where a.no = ? " +
				"             group by wt2.WorkType1" +
				"             ) aa on wt1.code=aa.WorkType1" +
				"             left outer join (" +
				"				select wt2.WorkType1," +
				"             sum(round(a.qty*a.Material,0)) sumMaterial, " +
				"           sum(round((case when b.BaseCtrlPer Is not null then " +
				"				(isnull(a.qty * a.Material, 0) * b.BaseCtrlPer) else " +
				"						 		(case when a.PrjCtrlType='1' then a.Material*a.MaterialCtrl else a.MaterialCtrl end)*a.qty " +
				"                                 end " +
				"                                 ),2)) sumMaterialCtrl " +
				"             from tBaseItemChgDetail a " +
				"             inner join tBaseItemChg bic on bic.no=a.no " +
				"             inner join tCustomer c on bic.CustCode=c.code " +
				"             inner join tBaseItem bi on bi.code=a.BaseItemCode" +
				"             inner join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2 " +
				"             inner join tWorkType2 wt2 on wt2.Code=bit2.MaterWorkType2 " +
				"             left outer join tCustBaseCtrl b on bic.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
				"             where a.no = ? " +
				"             group by wt2.WorkType1" +
				"             ) bb on wt1.code=bb.WorkType1" +
				"             where aa.WorkType1 is not null or bb.WorkType1 is not null";
		list.add(customer.getItemChgNo());
		list.add(customer.getItemChgNo());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.dispseq";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_jczfb(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (  select wt1.dispseq,wt1.code worktype1,wt1.Descr WorkType1Descr," +
				"             isnull(aa.sumUnitPrice,0) sumUnitPrice,isnull(bb.sumMaterial,0) sumMaterial," +
				"				isnull(aa.sumUnitPrice,0)+isnull(bb.sumMaterial,0) sumLineAmount," +
				"             isnull(aa.sumUnitPriceCtrl,0) sumUnitPriceCtrl,isnull(bb.sumMaterialCtrl,0) sumMaterialCtrl," +
				"             isnull(aa.sumUnitPriceCtrl,0)+isnull(bb.sumMaterialCtrl,0) sumLineAmountCtrl " +
				"             from " +
				"             tWorkType1 wt1" +
				"             left outer join (" +
				"             select wt2.WorkType1, " +
				"             sum(round(a.qty*a.UnitPrice,0)) sumUnitPrice, " +
				"             sum(round((case when b.BaseCtrlPer Is not null then " +
				"                                 (isnull(a.qty * a.unitprice, 0) * b.BaseCtrlPer) else " +
				"						 		(case when a.PrjCtrlType='1' then a.UnitPrice*a.OfferCtrl else a.OfferCtrl end)*a.qty " +
				"                                end " +
				"                ),2)) sumUnitPriceCtrl " +
				"             from tBaseItemReq a " +
				"             inner join tCustomer c on a.CustCode=c.code " +
				"             inner join tBaseItem bi on bi.code=a.BaseItemCode " +
				"             inner join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2 " +
				"             inner join tWorkType2 wt2 on wt2.Code=bit2.OfferWorkType2" +
				"             left outer join tCustBaseCtrl b on  a.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
				"             where a.custcode = ? " +
				"             group by wt2.WorkType1 " +
				"             ) aa  on wt1.code=aa.WorkType1" +
				"" +
				"             left outer join (" +
				"             select wt2.WorkType1, " +
				"             sum(round(a.qty*a.Material,0)) sumMaterial, " +
				"             sum(round((case when b.BaseCtrlPer Is not null then " +
				"                                 (isnull(a.qty * a.Material, 0) * b.BaseCtrlPer) else " +
				"						 		(case when a.PrjCtrlType='1' then a.Material*a.MaterialCtrl else a.MaterialCtrl end)*a.qty " +
				"                                 end " +
				"                                 ),2)) sumMaterialCtrl " +
				"             from tBaseItemReq a " +
				"             inner join tCustomer c on a.CustCode=c.code " +
				"             inner join tBaseItem bi on bi.code=a.BaseItemCode " +
				"             inner join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2 " +
				"             inner join tWorkType2 wt2 on wt2.Code=bit2.MaterWorkType2" +
				"             left outer join tCustBaseCtrl b on  a.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
				"             where a.custcode = ? " +
				"             group by wt2.WorkType1 " +
				"             ) bb on wt1.code=bb.WorkType1" +
				"             where aa.WorkType1 is not null or bb.WorkType1 is not null" ;
			list.add(customer.getCode());
			list.add(customer.getCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.dispseq ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getReqCtrlList(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select b.Descr BaseItemDescr,a.* from tCustBaseCtrl a " +
				" left outer join tBaseItem b on a.BaseItemCode=b.Code " +
				" where a.CustCode= ? ";
			list.add(customer.getCode());
		
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_detailReport(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select wt2.code,wt2.Descr WorkType2Descr, isnull(aa.sumUnitPrice,0) sumUnitPrice" +
			",isnull(bb.sumMaterial,0) sumMaterial,isnull(aa.sumUnitPrice,0)+isnull(bb.sumMaterial,0) sumLineAmount," +
			" isnull(aa.sumUnitPriceCtrl,0) sumUnitPriceCtrl,isnull(bb.sumMaterialCtrl,0) sumMaterialCtrl," +
			" isnull(aa.sumUnitPriceCtrl,0)+isnull(bb.sumMaterialCtrl,0) sumLineAmountCtrl " +
			" from tWorkType2 wt2" +
			" left outer join ( select wt2.code,sum(round(a.qty*a.UnitPrice,0)) sumUnitPrice," +
			"  		sum(round((case when b.BaseCtrlPer Is not null then (isnull(a.qty * a.unitprice, 0) * b.BaseCtrlPer) else" +
			" (case when a.PrjCtrlType='1' then a.UnitPrice*a.OfferCtrl else a.OfferCtrl end)*a.qty end ),2)) sumUnitPriceCtrl";
	  		if("Y".equals(customer.getM_umState())){
	  			sql+=" from tBaseItemPlan a " +
	  				 " inner join tCustomer c on a.CustCode=c.code ";
	  		}
	  		if("X".equals(customer.getM_umState())){
	  			sql+=" from tBaseItemReq a " +
	  				 " inner join tCustomer c on a.CustCode=c.code ";
	  		}
	  		if("Z".equals(customer.getM_umState())){
	  			sql+=" from tBaseItemChgDetail a  " +
	  				 " inner join tBaseItemChg bic on bic.no=a.no " +
	  				 " inner join tCustomer c on bic.CustCode=c.code ";
	  		}
	  		sql+=" inner join tBaseItem bi on bi.code=a.BaseItemCode " +
	  				" inner join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2 " +
	  				" inner join tWorkType2 wt2 on wt2.Code=bit2.OfferWorkType2	";
	  		if("Z".equals(customer.getM_umState())){
	  			sql+=" left outer join tCustBaseCtrl b on  bic.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
	  					" where a.no = ? " ;
	  					list.add(customer.getNo());
	  		}else {
	  			sql+=" left outer join tCustBaseCtrl b on  a.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
	  					" where a.custcode = ? ";
	  			list.add(customer.getCode());
			}
	  		sql+=" group by wt2.code " +
	  				" ) aa  on wt2.code=aa.code " +
	  				" left outer join (select wt2.code,sum(round(a.qty*a.Material,0)) sumMaterial, " +
	  				" sum(round((case when b.BaseCtrlPer Is not null then " +
	  				"			(isnull(a.qty * a.Material, 0) * b.BaseCtrlPer) else " +
	  				"  (case when a.PrjCtrlType='1' then a.Material*a.MaterialCtrl else a.MaterialCtrl end)*a.qty" +
	  				"  end ),2)) sumMaterialCtrl ";
	  		if("Y".equals(customer.getM_umState())){
	  			sql+=" from tBaseItemPlan a " +
	  				 " inner join tCustomer c on a.CustCode=c.code ";
	  		}
	  		if("X".equals(customer.getM_umState())){
	  			sql+=" from tBaseItemReq a " +
	  				 " inner join tCustomer c on a.CustCode=c.code ";
	  		}
	  		if("Z".equals(customer.getM_umState())){
	  			sql+=" from tBaseItemChgDetail a  " +
	  				 " inner join tBaseItemChg bic on bic.no=a.no " +
	  				 " inner join tCustomer c on bic.CustCode=c.code ";
	  		}
	  		sql+=" inner join tBaseItem bi on bi.code=a.BaseItemCode " +
	  				" inner join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2 " +
	  				" inner join tWorkType2 wt2 on wt2.Code= bit2.MaterWorkType2 ";
	  		if("Z".equals(customer.getM_umState())){
	  			sql+=" left outer join tCustBaseCtrl b on  bic.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
	  					" where a.no = ? " ;
	  					list.add(customer.getNo());
	  					
	  		} else {
	  			sql+= " left outer join tCustBaseCtrl b on  a.custcode=b.custcode and a.BaseItemCode=b.BaseItemCode " +
	  					" where a.custcode = ? ";
	  			list.add(customer.getCode());
			}
	  		sql+="  group by wt2.code ) bb on wt2.code=bb.code " +
	  				" where (aa.code is not null or bb.code is not null) and wt2.WorkType1= ? " +
	  				" order by wt2.dispseq";
	  		list.add(customer.getWorkType1());
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_detailReportMX(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "";
		String sqltxt = " select a.DispSeq,a.BaseItemCode,bi.Descr BaseItemDescr,a.FixAreaPK,fa.Descr FixAreaPkDescr,a.qty,u.descr UomDescr," +
				" round(a.qty*a.UnitPrice,0) sumUnitPrice,  0 sumMaterial,round(a.qty*a.UnitPrice,0) sumLineAmount," +
				" round((case when b.BaseCtrlPer Is not null then (isnull(a.qty * a.unitprice, 0) * b.BaseCtrlPer) else " +
				" (case when a.PrjCtrlType='1' then a.UnitPrice*a.OfferCtrl else a.OfferCtrl end)*a.qty " +
				" end  ),2) sumUnitPriceCtrl ,0 sumMaterialCtrl,round((case when b.BaseCtrlPer Is not null then" +
				" ((round(round(a.qty*a.unitprice,0)*isnull(b.BaseCtrlPer,c.BaseCtrlPer),2))) else " +
				" (case when a.PrjCtrlType='1' then a.UnitPrice*a.OfferCtrl else a.OfferCtrl end)*a.qty" +
				" end ),2) sumLineAmountCtrl ";
		String sqltxt2 = "select a.DispSeq,a.BaseItemCode,bi.Descr BaseItemDescr,a.FixAreaPK,fa.Descr FixAreaPkDescr,a.qty,u.descr UomDescr," +
				" 0 sumUnitPrice,round(a.qty*a.Material,0) sumMaterial,round(a.qty*a.Material,0) sumLineAmount," +
				" 0 sumUnitPriceCtrl,round((case when b.BaseCtrlPer Is not null then (isnull(a.qty * a.Material, 0) * b.BaseCtrlPer) else" +
				" (case when a.PrjCtrlType='1' then a.Material*a.MaterialCtrl else a.MaterialCtrl end)*a.qty " +
				" end  ),2) sumMaterialCtrl ,round((case when b.BaseCtrlPer Is not null then " +
				" ((round(round(a.qty*a.Material,0)*isnull(b.BaseCtrlPer,c.BaseCtrlPer),2))) else " +
				" 	(case when a.PrjCtrlType='1' then a.Material*a.MaterialCtrl else a.MaterialCtrl end)*a.qty " +
				" end  ),2) sumLineAmountCtrl";
		String sqltail = " ";
		if("Y".equals(customer.getM_umState())){
			sqltail +=  " from tBaseItemPlan a " +
					" inner join tCustomer c on a.CustCode=c.code";
		}
		if("X".equals(customer.getM_umState())){
			sqltail +=  " from tBaseItemReq a " +
					" inner join tCustomer c on a.CustCode=c.code";
		}
		if("Z".equals(customer.getM_umState())){
			sqltail +=  " from tBaseItemChgDetail a " +
					" inner join tBaseItemChg bic on bic.no=a.no " +
					" inner join tCustomer c on bic.CustCode=c.code ";
		}
		sqltail += " left outer join tBaseItem bi on bi.code=a.BaseItemCode " +
				" left outer join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2" +
				" left outer join tWorkType2 wt2 on wt2.code=bit2.OfferWorkType2" +
				" left outer join tFixArea fa on fa.pk=a.FixAreaPK" +
				" left outer join tUom u on u.code=bi.uom" +
				" left outer join tCustBaseCtrl b on c.code=b.custcode and a.BaseItemCode=b.BaseItemCode" +
				"";
		if("Z".equals(customer.getM_umState())){
			sqltail +=  " where a.no = ? ";
			list.add(customer.getNo());
		}else {
			sqltail += " where a.custcode = ? ";
			list.add(customer.getCode());
		}
		list.add(customer.getWorkType1());
  		list.add(customer.getWorkType2());
		String sqltail2 ="";
		if("Y".equals(customer.getM_umState())){
			sqltail2 +=  " from tBaseItemPlan a" +
					" inner join tCustomer c on a.CustCode=c.code ";
		}
		if("X".equals(customer.getM_umState())){
			sqltail2 +=  " from tBaseItemReq a" +
					" inner join tCustomer c on a.CustCode=c.code";
		}
		if("Z".equals(customer.getM_umState())){
			sqltail2 +=  " from tBaseItemChgDetail a " +
					" inner join tBaseItemChg bic on bic.no=a.no" +
					" inner join tCustomer c on bic.CustCode=c.code ";
		}
		sqltail2 +=" left outer join tBaseItem bi on bi.code=a.BaseItemCode" +
				" left outer join tBaseItemType2 bit2 on bit2.code=bi.BaseItemType2" +
				" left outer join tWorkType2 wt2 on wt2.code=bit2.MaterWorkType2" +
				" left outer join tFixArea fa on fa.pk=a.FixAreaPK" +
				" left outer join tUom u on u.code=bi.uom" +
				" left outer join tCustBaseCtrl b on c.code=b.custcode and a.BaseItemCode=b.BaseItemCode";
		if("Z".equals(customer.getM_umState())){
			sqltail2+=" where a.no = ?";
	  		list.add(customer.getNo());
		}else {
			sqltail2+=" where a.custcode = ? ";
	  		list.add(customer.getCode());
		}
		String sqlwhere =" and wt2.WorkType1= ? and wt2.code= ? ";
		list.add(customer.getWorkType1());
  		list.add(customer.getWorkType2());
  		
		sql = "Select *from("+ sqltxt + sqltail +sqlwhere +" union "+sqltxt2 + sqltail2+ sqlwhere+") a order by a.dispseq ";
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doSaveReqCtrlPer(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pJcfbkzmx_forxml(?,?,?,?,?)}");
			call.setString(1, customer.getCode());
			call.setString(2, customer.getLastUpdatedBy());
			call.setString(3, customer.getDetailXml());
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
	
	public Page<Map<String, Object>> findPageForOABySql(
			Page<Map<String, Object>> page, Customer customer,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select top 5 isnull(a.DesignFee,0) designfee,isnull(a.RealDesignFee,0) realdesignfee," +
				" o.Code company ,o.desc1 CompanyDescr,tc.canUseComBaseItem,a.ContractFee,a.isPushCust,a.buildernum,a.Code,a.realAddress,a.PrjProgTempNo,a.CustType," +
				" a.ConfirmBegin,tc.Desc1 CustTypeDescr,a.Source,b.NOTE SourceDescr,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,a.Mobile1,a.Mobile2,"
				+ "a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE StatusDescr,a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE DesignStyleDescr,a.BeginDate,a.ConstructType,g.NOTE ConstructTypeDescr,a.PlanAmount,"
				+ "a.DocumentNo,a.AgainMan,e3.NameChi AgainManDescr,a.BuilderCode,m.Descr BuilderCodeDescr,a.ProjectMan,e4.NameChi ProjectManDescr,e4.Phone ProjectManPhone, j.note CheckStatusDescr,a.CustCheckDate,"
				+ "a.oldCode,a.saleType,pp.minPk,i.note saleTypeDescr,ppt.Descr PrjProgTempNoDescr, a.iswateritemctrl,k.note iswateritemctrldescr, "
				+ "a.constructstatus,setdate,signdate,d1.Desc1 dept1Descr,d2.Desc1 dept2Descr,tc.type custTypeType,tc.isAddAllInfo,a.perfPk,x1.note Enddescr,x2.note IsPubReturnDescr,tct.ReturnAmount,CheckStatus, "
				+ "case when isnull(tct.PayeeCode,'')<>'' then tct.PayeeCode when isnull(a.PayeeCode,'')<>'' then a.PayeeCode when isnull(n.PayeeCode,'')<>'' then n.PayeeCode else tc.PayeeCode end PayeeCode,tc.CmpnyName" +
				"  ,a.endCode,a.tax,a.isInitSign  "
				+ " from tCustomer a "
				+ " left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ " left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ " left outer join tBuilder m on a.BuilderCode=m.Code "
				+ " left outer join tPrjProgTemp ppt on ppt.No=a.PrjProgTempNo "
				+ " left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
				+ " left outer join (select min(pk) minPK,CustCode  from tPrjProg group by CustCode )pp on pp.CustCode=a.Code "
				+ " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
				+ " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ " left outer join tXTDM g on a.ConstructType=g.CBM and g.ID='CONSTRUCTTYPE' "
				+ " left outer join tXTDM h on a.CustType=h.CBM and h.ID='CUSTTYPE' "
				+ " left outer join tXTDM i on a.saleType=i.CBM and i.ID='SALETYPE' "
				+ " left outer join tXTDM j on a.CheckStatus=j.CBM and j.ID='CheckStatus' "
				+ " left outer join tXTDM k on a.isWaterItemCtrl=k.CBM and k.ID='YESNO' "
				+ " left outer join tEmployee e4 on a.ProjectMan = e4.Number "
				+ " left outer join tDepartment1 d1 on d1.Code = e4.Department1 "
				+ " left outer join tDepartment2 d2 on d2.Code = e4.Department2 "
				+ " left join tCusttype tc on tc.code=a.custType "
				+ " left join tXTDM x1 on  a.EndCode=x1.cbm  and x1.id='CUSTOMERENDCODE' "
				+ " left join tCustTax tct on a.Code=tct.CustCode "
				+ " left join tXTDM x2 on tct.IsPubReturn=x2.cbm and x2.id='YESNO' "
				+ " left join ( "
				+ "   select CustCode, max(b.PayeeCode) PayeeCode from tCustPay a"
				+ "   inner join tRcvAct b on a.RcvAct=b.Code"
				+ "   where isnull(b.PayeeCode,'')<>'' and a.Type<>'1' "
				+ "   and a.Date=(select max(Date) from tCustPay in_a, tRcvAct in_b where in_a.CustCode=a.CustCode and in_a.RcvAct=in_b.Code and isnull(in_b.PayeeCode,'')<>'' and in_a.Type<>'1')"
				+ "   group by CustCode"
				+ " ) n on a.Code=n.CustCode " +
				" left join tRegion r on r.code = m.RegionCode " +
				" left join tCompany o on o.code = r.CmpCode "
				+ " where 1=1 and a.expired ='F' ";
		//控制选择的客户权限
		if(StringUtils.isNotBlank(customer.getAuthorityCtrl())){
			sql+=" and "+SqlUtil.getCustRight(uc, "a", 0);
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.address like ? ";
			list.add("%"+customer.getAddress().trim()+"%");
		}
		if(StringUtils.isNotBlank(customer.getStatus())){
			String str = SqlUtil.resetStatus(customer.getStatus());
			sql += " and a.status in (" + str + ")";
		}
		//相关干系人的楼盘才可以进行申请
		if(StringUtils.isNotBlank(customer.getEmpCode())){
			sql+=" and exists(select 1 from tCustStakeholder in_a where in_a.CustCode=a.Code and in_a.EmpCode=?)";
			list.add(customer.getEmpCode());
		}
		if(StringUtils.isNotBlank(customer.getDesignMan())){
			sql += " and a.designMan =? ";
			list.add(customer.getDesignMan());
		}
		if(StringUtils.isNotBlank(customer.getIsAddAllInfo())){
			sql+=" and tc.IsAddAllInfo = ? ";
			list.add(customer.getIsAddAllInfo());
		}
		if(StringUtils.isNotBlank(customer.getCodes())){
			String str = SqlUtil.resetStatus(customer.getCodes());
			sql += " and a.code not in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getCode())){
			sql += " and a.Code = ? ";
			list.add(customer.getCode());
		}
		// 限制只有客户状态：归档，结束代码：退定金，的楼盘可以申请 add by zb on 20200319
		if (StringUtils.isNotBlank(customer.getEndCode())) {
			sql += " and a.EndCode in ('"+customer.getEndCode().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc,a.Code desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getTechPhotoUrlList(
			Page<Map<String, Object>> page, String custCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select *from "
				+" ( select a.No ,c.WorkType12 ,b.PhotoName ,c.Descr ,c.DisSeq ,b.LastUpdate, "
				+" case when b.IsSendYun='1' then '1' else '0' end empSignPic,null workSignPic,c.SourceType "
				+" from tSignIn a "
				+" left join tSignInPic b on a.No = b.No "
				+" left join tTechnology c on b.TechCode = c.Code "
				+" left join tWorkType12 d on c.WorkType12 = d.Code "
				+" where a.CustCode = ? and d.IsTechnology = '1' and c.Expired='F'"
				+" union all "
				+" select a.No ,c.WorkType12 ,b.PhotoName ,c.Descr ,c.DisSeq ,b.LastUpdate,null empSignPic, "
				+" case when b.IsSendYun='1' then '1' else '0' end workSignPic,c.SourceType  "
				+" from tWorkSign a "
				+" left join tWorkSignPic b on a.No = b.No "
				+" left join tTechnology c on b.TechCode = c.Code "
				+" left join tWorkType12 d on c.WorkType12 = d.Code "
				+" where a.CustCode = ? and d.IsTechnology = '1' and c.Expired='F'"
				+" ) t "
				+"  order by t.SourceType,t.DisSeq ,t.LastUpdate desc ";
		list.add(custCode);
		list.add(custCode);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_modifyPhone(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.CustCode,b.Address,b.SignDate,c.NameChi DesignManDescr,e.NameChi BusinessManDescr, "
				+" d.Desc2 DesignDept2Descr,f.Desc2 BusinessDept2Descr,a.OldPhone,a.NewPhone,x1.NOTE ModuleDescr,a.Date,a.CZYBH from tPhoneModifyDetail a "
				+" left join tCustomer b on a.CustCode=b.Code "
				+" left join tEmployee c on b.DesignMan=c.Number "
				+" left join tDepartment2 d on c.Department2=d.Code "
				+" left join tEmployee e on b.BusinessMan=e.Number "
				+" left join tDepartment2 f on e.Department2=f.Code"
				+" left join tXTDM x1 on a.Module=x1.CBM and x1.ID='PhoneModModule' "
				+" where 1=1";
		if (customer.getSignDateFrom() != null) {
			sql += " and b.signDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(customer.getSignDateFrom());
		}
		if (customer.getSignDateTo() != null) {
			sql += " and b.signDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(customer.getSignDateTo());
		}
		if(StringUtils.isNotBlank(customer.getModule())){
			sql+=" and a.Module = ? ";
			list.add(customer.getModule());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getConStatAndIsAddAll(String code){
		String sql = "select  case when a.ConstructStatus='1' or (a.ConstructStatus='0' and b.IsAddAllInfo='0') then '1' "
			+" else '0' end isAllowPreItem from  tcustomer a "
			+" left join tCusttype b on a.CustType=b.Code where a.code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return (String) list.get(0).get("isAllowPreItem");
		}
		return null;
	}
	
	public Map<String, Object> getGiftDiscAmount(String custCode) {
		
		String sql = "select sum(case when Type = '3' and IsSoftToken ='0' then discAmount else 0 end ) swyh," +
				"sum( case when IsSoftToken='1' then discAmount else 0 end ) softToken" +
				" from tCustGift where custCode = ? ";

		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public Map<String, Object> getContractInfo(String code) {
		String sql = "select d.NOTE CustType,a.SignDate,a.ContractFee,a.DesignFee, "
                + "(a.BaseFee*ContainBase-a.BaseDisc*ContainBase) BaseFee,t1.ChgBaseFee, "
                + "(a.MainFee*a.ContainMain-a.MainDisc*a.ContainMain) MainFee,t2.ChgMainFee, "
                + "a.MainServFee*ContainMainServ MainServFee,t2.ChgMainServFee, "
                + "(a.IntegrateFee*a.ContainInt-a.IntegrateDisc*a.ContainInt) IntegrateFee,t2.ChgIntFee, "
                + "(a.CupboardFee*a.ContainCup-a.CupBoardDisc*a.ContainCup) CupboardFee,t2.ChgCupFee, "
                + "(a.SoftFee*a.ContainSoft-a.SoftDisc*a.ContainSoft) SoftFee,t2.ChgSoftFee "
                + "from tCustomer a left outer join tXTDM d on a.CustType=d.CBM and d.ID='CUSTTYPE' "
				+ "left join (Select CustCode,ISNULL(SUM(AMOUNT),0) ChgBaseFee FROM tBaseItemChg WHERE CustCode=? "
				+ "and Status = '2' group by CustCode) t1 on a.code=t1.CustCode "
				+ "left join (Select CustCode,ISNULL(SUM(case when IsService=0 and ItemType1 = 'ZC' then AMOUNT else 0 end),0) ChgMainFee,"
				+ "ISNULL(SUM(case when IsService=1 and ItemType1 = 'ZC' then AMOUNT else 0 end),0) ChgMainServFee,"
				+ "ISNULL(SUM(case when ItemType1 = 'RZ' then AMOUNT else 0 end),0) ChgSoftFee,"
				+ "ISNULL(SUM(case when ItemType1 = 'JC' then AMOUNT else 0 end),0) ChgIntFee,"
				+ "ISNULL(SUM(case when ItemType1 = 'CG' then AMOUNT else 0 end),0) ChgCupFee "
				+ "FROM tItemChg "
				+ "WHERE CustCode=? and Status = '2' group by CustCode) t2 on a.code=t2.custCode "
                + "where a.Code = ? and a.Expired = 'F'";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{code,code,code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
         
		return null;
	}
	
	public Page<Map<String, Object>> getBaseCheckItemPlanList(Page<Map<String, Object>> page,String custCode,String workType12){
		List<Object> list = new ArrayList<Object>();
		String sql = " select c.Descr fixAreaDescr,b.WorkType12,d.Descr WorkType12Descr,b.Descr baseCheckItemDescr,a.Qty " +
				" from tBaseCheckItemPlan a" +
				" left join tBaseCheckItem b on b.code = a.BaseCheckItemCode" +
				" left join tFixArea c on c.pk=a.FixAreaPK " +
				" left join tWorkType12 d on b.WorkType12 = d.Code "+
				" where a.CustCode = ? ";
		list.add(custCode);
		if(StringUtils.isNotBlank(workType12)){
			sql+=" and b.WorkType12 = ? ";
			list.add(workType12);
		}
		sql +=" order by a.LastUpdate DESC ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean getExistBuilderNum(String builderCode) {
		String sql = " select  a.*,c.descr builderDescr from tBuilderNum a  " +
				" left  join tBuilderDeliv b on b.Code=a.BuilderDelivCode" +
				" left join tbuilder c on c.code=b.builderCode " +
				" where 1=1  and c.Code= ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{builderCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * 风控基金统计
	 * @author	created by zb
	 * @date	2019-10-25--下午6:20:11
	 * @param id
	 * @return
	 */
	public Double getRiskFund(String id) {
		String sql = "select sum(RiskFund) RiskFund "
					+"from tFixDutyMan in_a "
					+"inner join tFixDuty in_c on in_c.No = in_a.No "
					+"inner join tCustomer in_b on in_b.DesignMan=in_a.EmpCode and in_c.CustCode = in_b.Code "
					+"where in_a.FaultType='1' and in_b.Code=? and RiskFund<>0 "
					+"group by in_a.EmpCode";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{id});
		if (list!=null && list.size()>0){
			return (Double) list.get(0).get("RiskFund");
		}
		return 0.0;
	}
	/**
	 * 风控基金明细
	 * @author	created by zb
	 * @date	2019-10-28--下午5:38:55
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findRiskFundPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		String sql = "select a.PK,a.No,a.FaultType,a.EmpCode,a.WorkerCode,a.SupplCode,a.Amount,a.IsSalary,  "
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,f.NOTE isSalaryDescr,  "
					+"b.NameChi empNameChi,c.NameChi workerNameChi,d.Descr supplDescr,e.NOTE faultTypeDescr,a.Remark  "
					+",isnull(tt.ConfirmAmount,0) ConfirmAmount, a.RiskFund "
					+"from tFixDutyMan a   "
					+"left join tEmployee b on a.EmpCode=b.Number   "
					+"left join tWorker c on a.WorkerCode=c.Code   "
					+"left join tSupplier d on a.SupplCode=d.Code   "
					+"left join tXTDM e on a.FaultType=e.CBM and e.ID='FAULTTYPE'  "
					+"left join tXTDM f on a.IsSalary=f.CBM and f.ID='YESNO'  "
					+"left join tFixDuty g on g.no = a.no  "
					+"left join ( "
					+"	select bb.WorkerCode,bb.CustCode,sum(isnull(bb.ConfirmAmount,0)) ConfirmAmount  "
					+"	from tWorkCost aa left join tWorkCostDetail bb on aa.No=bb.No  "
					+"	where aa.Status='2' and aa.PayCZY is not null  "
					+"	group by bb.WorkerCode,bb.CustCode "
					+") tt on a.WorkerCode=tt.WorkerCode and g.CustCode=tt.CustCode  "
					+"inner join tCustomer tc on tc.DesignMan=a.EmpCode "
					+"where a.FaultType='1' and tc.Code=? and a.RiskFund<>0";
		return this.findPageBySql(page, sql, new Object[]{customer.getCode()});
	}
	
	public String getPayeeCode(String code){
		String sql = " select case when a.payeeCode is null or a.payeeCode = '' then b.payeeCode " +
				" else a.payeeCode end payeeCode from tCustomer a" +
				" left join tBuilder c on c.Code = a.BuilderCode" +
				" left join tRegion d on d.code = c.RegionCode" +
				" left join tCmpCustType b on a.CustType = b.CustType and d.CmpCode = b.CmpCode " +
				" where a.Code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			if(list.get(0).get("payeeCode") != null){
				return list.get(0).get("payeeCode").toString();
			}
		}
		return "";
	}
	/**
	 * 根据小区表，交房（tBuilder.DelivDate）超过1年，默认不能施工。1年之内默认可施工。
	 * @author	created by zb
	 * @date	2020-2-20--上午10:01:24
	 * @param builderDeliv
	 * @param builderNum
	 * @return
	 */
	public String isHoliConstruct(String builderCode, String builderNum) {
		String sql = "select case when  "
					+"	datediff(day,dateadd(yy,1, "
					+"	  case when b.DelivDate is null then a.DelivDate else b.DelivDate end), "
					+"	  getdate())>=0 "
					+"	then 0 else 1 end isHoliConstruct "
					+"from tBuilder a "
					+"left join ( "
					+"	select in_a.BuilderCode,in_a.DelivDate from tBuilderDeliv in_a  "
					+"	left join tBuilderNum in_b on in_b.BuilderDelivCode=in_a.Code "
					+"	where rtrim(in_b.BuilderNum)=? "
					+") b on b.BuilderCode=a.Code "
					+"where a.Code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{builderNum, builderCode});
		if (null != list && list.size()>0) {
			return list.get(0).get("isHoliConstruct").toString();
		}
		return "";
	}	
	
	/**
	 * 是否需要跟单员
	 * @param code
	 * @return
	 */
	public Map<String,Object> isNeedAgainMan(String code) {
		String sql = "select NeedAgainMan,NeedMeasureDate,ChannelType from tCustNetCnl where Code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (null != list && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 是否有客户付款
	 * @param code
	 * @return
	 */
	public String hasPay(String code) {
		String sql = "select 1 hasPay from tCustPay where CustCode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (null != list && list.size()>0) {
			return list.get(0).get("hasPay").toString();
		}
		return "";
	}	
	
	public Map<String, Object> getWaterMarkInfo(String code) {
		String sql = " select  d.Descr BuilderDescr,b.NameChi ProjectManDescr,getdate() TakePhotoDate from tcustomer a "
				+" left join tEmployee b on a.ProjectMan = b.Number "
				+" left join tBuilder d on a.BuilderCode = d.Code "
				+" where a.Code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询楼盘所属工程大区
	 * @param code
	 * @return
	 */
	public Map<String, Object> getPrjRegion(String code) {
		String sql = "select d.Descr prjRegionDescr from tCustomer a  "
			+"left join tBuilder b on a.BuilderCode=b.Code  " 
			+"left join tRegion2 c on b.RegionCode2=c.Code  " 
			+"left join tPrjRegion d on c.PrjRegionCode=d.Code  "
			+"where a.Code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> getMaxDiscByCustCode(String code) {
		String sql = " exec pZsxmhz ? ,'1'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Result doWfCustReSign(Customer customer){
		Assert.notNull(customer);
		Result result = new Result();
		String sql = " set nocount on "
				   + " declare @ret int "
				   + " declare @errmsg nvarchar(400) "
				   + " exec pSGhtxx_cq_forXml ?,?,?,?,?,@ret output, @errmsg output"
				   + " select @ret ret,@errmsg errmsg "
				   + " set nocount off "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{customer.getCode(),customer.getFromStatus(),
				customer.getToStatus(),customer.getRemarks(),customer.getLastUpdatedBy()});
		if(list != null && list.size() > 0){
			int ret = (Integer) list.get(0).get("ret");
			String errmsg = (String) list.get(0).get("errmsg");
			if (ret == 1) {
				result.setCode(String.valueOf(ret));
				result.setInfo(errmsg);
			} else {
				logger.error("重签失败");
			}
		}
		return result;
	}
	
	public String getEndCodeByCustCode(String custCode) {
		String sql = " select EndCode from tCustomer where code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			if(list.get(0).get("EndCode") != null){
				return list.get(0).get("EndCode").toString();
			} else {
				return "";
			}
		}
		return "";
	}
	
	public String getContractStatus(Customer customer) {
		String sql = " select b.Status from (" +
				"	select max(pk) pk from tCustContract where CustCode = ? and ConType = '1' group by CustCode " +
				" ) a" +
				" left join tCustContract b on b.pk = a.pk ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{customer.getCode()});
		if (list!=null && list.size()>0){
			if(list.get(0).get("Status") != null){
				return list.get(0).get("Status").toString();
			} else {
				return "";
			}
		}
		return "";
	}
	
	public Map<String, Object> getPerfEstimate(String custCode, String type, String empCode) {
		String sql = "exec pPerfEstimate ?, ?, ?  ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode, type, empCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> findPageBySql_basePersonalEstimate(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select b.descr, bip.qty, bip.UnitPrice, bip.Material,bip.LineAmount,d.CommiPer CommiPerc," 
				   +" round(bip.LineAmount*g.PerfPer*d.CommiPer,2) Commi,b.BaseItemType1, g.PerfPer "
				   +" from tbaseitemplan bip  "
				   +" left join tBaseItem b on bip.BaseItemCode = b.code "
				   +" inner join tCommiBasePersonal d on d.BaseItemType1=b.BaseItemType1 "
				   +" left join tUom u on u.Code = b.Uom "
				   +" left join (select cast(qz as money) PerfPer from tXTCS where id='SoftPerfPer')g on 1=1 " 
				   +" where 1=1 ";	
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and bip.CustCode = ? ";
			list.add(customer.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.BaseItemType1 ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_zcEstimate(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select b.Descr, a.Qty, a.UnitPrice, a.ProcessCost, a.LineAmount,  "
				   +" case when isnull(c.ProfitPer,0)<=0 then 0 else isnull(case when d.PK is not null then e.CommiPerc else f.CommiPerc end,0) end CommiPerc, "
				   +" round(a.LineAmount * case when isnull(c.ProfitPer,0)<=0 then 0 " +
				   "	else isnull(case when d.PK is not null then e.CommiPerc else f.CommiPerc end,0) end * g.PerfPer ,2) Commi, "
				   +" case when a.isoutset='0' then '是' else '否' end issetdescr, g.PerfPer  "
				   +" from tItemPlan a "
				   +" inner join tItem b on b.Code=a.ItemCode "
				   +" left join ( "
				   +"	select PK,case when isnull(LineAmount,0)=0 then 0 else (LineAmount-(isnull(Qty, 0)* " 
				   + "  isnull(case when IsOutSet ='0' then ProjectCost else Cost end, 0)+case when isnull(ProcessCost, 0)>0 then isnull(ProcessCost, 0) else 0 end))/LineAmount end ProfitPer "  
				   +" 	from tItemPlan " 
				   +" )c on c.PK=a.PK "
				   +" left join tMainCommiRuleItemNew d on d.ItemType2=b.ItemType2 and d.ItemType3=b.ItemType3 and d.Expired='F' "
				   +" left join tMainCommiRuleNew e on e.No=d.No and e.FromProfit<=c.ProfitPer and e.ToProfit>c.ProfitPer and e.Expired='F'" 
				   +"  	and e.IsUpgItem = b.IsSetItem and ((e.IsUpgItem = '1' and a.IsOutSet = '0') or (e.IsUpgItem = '0' and a.IsOutSet= '1')) "
				   +" left join ( "
				   +"	select in_a.* "
				   +"	from tMainCommiRuleNew in_a "
				   +"	where in_a.Expired='F'  and not exists(select 1 from tMainCommiRuleItemNew in_b where in_a.No = in_b.No) "
				   +" ) f on f.FromProfit<=c.ProfitPer and f.ToProfit>c.ProfitPer and ((f.IsUpgItem = '1' and a.IsOutSet= '0') or (f.IsUpgItem = '0' and a.IsOutSet= '1')) "
				   +" left join (select cast(qz as money) PerfPer from tXTCS where id='SoftPerfPer')g on 1=1 " 
				   +" where a.ItemType1='ZC' and a.LineAmount<>0 ";
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode = ? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.descr";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_rzEstimate(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select b.Descr, a.Qty, a.UnitPrice, a.ProcessCost, a.LineAmount," 
				+"  isnull(f.BusinessManCommiPer,0) CommiPerc, g.PerfPer, "
				+"  round(isnull(a.LineAmount*f.BusinessManCommiPer * g.PerfPer,0),2) Commi "
				+" from  titemplan a  "
				+"	 inner join tItem b on b.Code=a.ItemCode "
				+"	 left join ( "
				+"		select PK,case when in_a.LineAmount=0 then 0 else  "
				+"			(in_a.LineAmount-in_a.Cost*in_a.Qty-in_a.LineAmount*it2.OtherCostPer_Sale-in_a.Cost*in_a.Qty-in_a.LineAmount*it2.OtherCostPer_Cost)/in_a.LineAmount end ProfitPer  "
				+"		from tItemPlan in_a "
				+"		inner join tItem i on i.Code=in_a.ItemCode "
				+"		inner join tItemType2 it2 on i.ItemType2=it2.Code "	
				+"	)c on c.PK=a.PK "
				+" inner join tItemType2 d on d.Code=b.ItemType2 "
				+" left join tProfitPerf f on f.ItemType12=d.ItemType12 and c.ProfitPer>=f.FromProfit and c.ProfitPer<f.ToProfit and b.IsClearInv=f.IsClearInv "
			    +" left join (select cast(qz as money) PerfPer from tXTCS where id='SoftPerfPer')g on 1=1 "
				+" where  a.ItemType1='RZ'  and a.LineAmount<>0 ";		
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode = ? ";
			list.add(customer.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.descr ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_jcEstimate(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select  b.Descr, a.Qty, a.UnitPrice, a.ProcessCost, a.LineAmount, " 
				   + " b.PerfPer ItemPerfPer, c.IntCommiPer CommiPerc, g.PerfPer, "
				   +" round(a.LineAmount * b.PerfPer * c.IntCommiPer *g.PerfPer,2) Commi "
				   +" from tItemPlan a "
				   +" left join tItem b on b.Code=a.ItemCode "
				   +" left join (select cast(qz as money) IntCommiPer from tXTCS where id='IntCommiPer')c on 1=1 "
				   +" left join (select cast(qz as money) PerfPer from tXTCS where id='SoftPerfPer')g on 1=1 "   
				   +" where  a.ItemType1='JC' and a.IsService='0' and a.LineAmount<>0  ";	
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode = ? ";
			list.add(customer.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.descr ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean isSignDateAfterNewCommiDate(String custCode){
		String sql = "select 1 from tCustomer where Code=? and " +
				" ( FirstSignDate>=(select cast(qz as date) from tXTCS where ID='NEWCOMMIDATE') or FirstSignDate is null) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (null != list && list.size()>0) {
			return true;
		}
		return false;
	}
	
	public Result doWfProcSaveZsg(Customer customer){
		Assert.notNull(customer);
		Result result = new Result();
		String sql = " set nocount on "
				   + " declare @ret int "
				   + " declare @errmsg nvarchar(400) "
				   + " exec pSGhtxx_sg_forXml ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,@ret output, @errmsg output,?,?,?,?,?,?,?,?,?,?"
				   + " select @ret ret,@errmsg errmsg "
				   + " set nocount off "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{ customer.getM_umState()
			, customer.getCode()
			, customer.getFromStatus()
			, customer.getToStatus()
			, customer.getLastUpdatedBy()
			, customer.getFirstPay()
			, customer.getSecondPay()
			, customer.getThirdPay()
			, customer.getFourPay()
			, customer.getIsInternal()
			, customer.getTileStatus()
			, customer.getBathStatus()
			, customer.getPayType()
			, customer.getConPhone()
			, customer.getConId()
			, customer.getPerfMarkup()
			, customer.getToConstructDate()== null ? null : new Timestamp(customer.getToConstructDate().getTime())
			, customer.getDesignRiskFund()==null?0:customer.getDesignRiskFund()
			, customer.getPrjManRiskFund()==null?0:customer.getPrjManRiskFund()
			, customer.getPayeeCode()
			, customer.getFrontEndDiscAmount()==null?0:customer.getFrontEndDiscAmount()
			, customer.getCmpDiscAmount()==null?0:customer.getCmpDiscAmount()
			, customer.getIsInitSign()
			, customer.getDesignerMaxDiscAmount()==null?0:customer.getDesignerMaxDiscAmount()
			, customer.getDirectorMaxDiscAmount()==null?0:customer.getDirectorMaxDiscAmount()
		});
		if(list != null && list.size() > 0){
			int ret = (Integer) list.get(0).get("ret");
			String errmsg = (String) list.get(0).get("errmsg");
			if (ret == 1) {
				result.setCode(String.valueOf(ret));
				result.setInfo(errmsg);
			} else {
				logger.error("转施工失败");
			}
		}
		return result;
	}

}
