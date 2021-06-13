package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
import com.house.home.client.service.evt.ItemSaveEvt;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.design.Customer;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.finance.LaborFee;
import com.house.home.entity.insales.ItemTransferHeader;
import com.house.home.entity.project.Worker;

@SuppressWarnings("serial")
@Repository
public class LaborFeeDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select  * from (select a.No ,a.ItemType1 ,a.Status ,a.ActName ,a.CardID ,a.bank,a.Remarks ,a.AppCZY ,a.Date ,a.ConfirmCZY ,a.ConfirmDate ,a.PayCZY ,a.PayDate ,a.DocumentNo ,"
          + " a.LastUpdate ,a.LastUpdatedBy ,a.Expired ,a.ActionLog,b.Descr ItemType1Descr,x1.note StatusDescr,c.zwxm AppCZYDecr, d.zwxm ConfirmCZYDecr,e.zwxm PayCZYDecr  "
          + ",f.Amount,case when x2.note is null then x.note else x2.note end ProcStatus, x.note ProcStatusDescr,h.no wfProcInstNo " +
          " from tLaborFee a "
          + " left outer join tItemType1 b on b.code=a.ItemType1 "
          + " left outer join tXTDM x1 ON x1.CBM=a.Status AND x1.id='LABORFEESTATUS'  "
          + " left outer join tCZYBM  c on c.czybh=a.AppCZY  "
          + " left outer join tCZYBM d on d.czybh=a.ConfirmCZY   "
          + " left outer join tCZYBM e on e.czybh=a.PayCZY   "
          + " left outer join (select isnull(sum(Amount),0) Amount,No from tLaborFeeDetail "
          + " group by No)f on a.No=f.No "
          + " left join (select max(WfProcInstNo) wfProcInstNo,a.RefNo from twfcust_constructionExpenseClaim a group by a.refNo ) g on g.RefNo = a.no "
          + " left join tWfProcInst h on h.No = g.WfProcInstNo " 
          + " left join tXtdm x on x.id = 'WFPROCINSTSTAT' and x.cbm = h.status "
          + " left join ("
          + "  select max(pk) pk,WfProcInstNo from tWfProcTrack where actionType in ('4','5','6') group by  WfProcInstNo "
          + ")j on j.WfProcInstNo = h.no"
          + " left join tWfProcTrack k on k.pk = j.pk "
          + " left join tXtdm x2 on x2.id = 'PROCACTTYPE' and x2.cbm = k.ActionType "
          + " where 1=1 ";
		if(StringUtils.isNotBlank(laborFee.getItemRight())){
			sql+=" and a.itemType1 in "+"('"+laborFee.getItemRight().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(laborFee.getNo())){
			sql+=" and a. no =? ";
			list.add(laborFee.getNo());
		}
		if(StringUtils.isNotBlank(laborFee.getItemType1())){
			sql+=" and a.itemType1 = ? ";
			list.add(laborFee.getItemType1());
		}
		if(StringUtils.isNotBlank(laborFee.getDocumentNo())){
			sql+=" and a.documentNo like  ?  ";
			list.add("%"+laborFee.getDocumentNo()+"%");
		}
		if(StringUtils.isNotBlank(laborFee.getActName())){
			sql+=" and a.ActName like ?  ";
			list.add("%"+laborFee.getActName()+"%");
		}
		if(laborFee.getDateFrom()!=null){
			sql+=" and a.date>=? ";
			list.add(laborFee.getDateFrom());
		}
		if(laborFee.getDateTo()!=null){
			sql+=" and a.date<DATEADD(d,1,?)";
			list.add(laborFee.getDateTo());
		}
		if(laborFee.getConfirmDateFrom()!=null){
			sql+=" and a.confirmDate>=? ";
			list.add(laborFee.getDateFrom());
		}
		if(laborFee.getConfirmDateTo()!=null){
			sql+=" and a.confirmDate<DATEADD(d,1,?)";
			list.add(laborFee.getDateTo());
		}
		if(StringUtils.isNotBlank(laborFee.getStatus())){
			sql += " and a.status in " + "('"+laborFee.getStatus().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page,LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( select  a.PK, a.No, a.FeeType, a.CustCode, a.AppSendNo, e.IANo, a.Amount," +
				" (case  g.Type  when 'S' then (isnull(e.CarryFee, 0) + isnull(e.TransFee, 0))" +
				" when 'R' then (isnull(j.CarryFee,0)+isnull(j.TransFee,0)  ) end) Amount1," +
				" (case g.type when 'S' then (isnull(e.AutoTransFee, 0) + isnull(e.AutoCarryFee, 0)) when 'R' then " +
				" isnull(j.AutoCarryFee,0)+isnull(j.AutoTransFee,0) end) Amount2 , a.Remarks, b.Address," +
				" b.DocumentNo , c.Descr FeeTypeDescr, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, " +
				" isnull(d.HadAmount, 0) HadAmount, b.CheckStatus, x1.Note CheckStatusDescr, b.CustCheckDate, a.ActName, a.CardId," +
				" isnull(f.SendNoHaveAmount, 0) SendNoHaveAmount,xt1.note isSetItemDescr," +
				" round(case when a.feetype='cgazf' then ( isnull(cps1.cupdownhigh, 0) + isnull(cps1.cupuphigh, 0) ) * cast(xc.qz as money) " +
                "    when a.feetype='jcazf' then isnull(sird.amount,0)  else  0 end,2) autoAzf,a.WorkerCode,a.CustWorkPk, " +
				" a.RefCustCode,a.FaultType,x2.NOTE FaultTypeDescr,case when a.FaultType='1' then l.NameChi when a.FaultType='2' then n.NameChi else '' end FaultManDescr," +
				" m.QualityFee PrjQualityFee,a.FaultMan,k.ProjectMan,o.NameChi ProjectManDescr, "+
				" b.CustType, p.Desc1 CustTypeDescr, a.CutCheckOutNo, r.Descr RegionDescr, " +
				" k.Address RefCustAddress " +
				" from    tLaborFeeDetail a" +
				" left outer join tCustomer b on b.code = a.CustCode" +
				" left outer join tLaborFeeType c on c.code = a.FeeType" +
				" left outer join tItemAppSend e on a.AppSendNo = e.no" +
				" left outer join tItemApp i on e.IANo=i.No " +
				" left outer join tXTDM xt1 on xt1.id='YESNO' and xt1.cbm=i.IsSetItem" +
				" left outer join tItemReturn j on i.SendBatchNo=j.SendBatchNo and j.CustCode=a.CustCode" +
				" left outer join tXTDM x1 on x1.CBM = b.CheckStatus" +
				"                        and x1.id = 'CheckStatus'" +
				" left outer join (select b.CustCode, b.FeeType, sum(b.Amount) HadAmount" +
				"             from   tLaborFee a" +
				"                    inner join tLaborFeeDetail b on a.No = b.No" +
				"                    left join tCustomer c on c.Code = b.CustCode" +
				"             where  a.Status in ('3', '4')" +
				"             group by b.CustCode, b.FeeType" +
				"            ) d on a.CustCode = d.CustCode" +
				"                   and a.FeeType = d.FeeType" +
				" left outer join (select b.FeeType, b.AppSendNo, sum(b.Amount) SendNoHaveAmount" +
				"             from   tLaborFee a" +
				"                    inner join tLaborFeeDetail b on a.No = b.No" +
				"             where  a.Status in ('3', '4') and b.AppSendNo<>'' group by b.FeeType, b.AppSendNo" + // 20200404 modi by xzp 增加a.AppSendNo<>''，发货单号为空不需要统计本单已报销
				"            ) f on f.FeeType = d.FeeType" +
				"                   and f.AppSendNo = a.AppSendNo" +
				" left join (select  t1.type,a.No" +
				" from    tItemAppSend a" +
				" left outer join tItemApp t1 on t1.no = a.IANo )  g on a.AppSendNo=g.No " +
				" left join tcupmeasure cps on cps.custcode = a.custcode and cps.IsCupboard='0'  " +
				" left join tcupmeasure cps1 on cps1.custcode = a.custcode and cps1.IsCupboard='1' and cps1.status= '2' " +
                "  left join txtcs xc on xc.id = 'cupinsfee' " +
                " left join (  " + //集成安装费改成累计领料发货数byzjf20200415
                "  select sum(isnull(c.IntInstallFee,0)*isnull(case when ia.type='s' then a.sendqty else (-1.0)* a.sendqty end,0))amount,CustCode " +
				"		from tItemAppDetail a  " +
				"		left join tItemApp ia on ia.no=a.no " +
				"		left join tItem b on b.Code = a.ItemCode " +
				"		left join tItemType3 c on c.Code=b.ItemType3 " +
				"		where ia.IsCupboard='0' and ia.ItemType1 ='JC' and a.SendQty<>0  " + 
				"		group by CustCode  " + 
                "  ) sird on sird.custcode = a.custcode "+
				" left join tCustomer k on a.RefCustCode=k.Code "+
				" left join tEmployee l on a.FaultMan=l.Number "+
				" left join tXTDM x2 on x2.ID='FAULTTYPE' and a.FaultType=x2.CBM "+
				" left join tWorker m on k.ProjectMan=m.EmpCode and m.empcode <> ''"+
				" left join tWorker n on a.FaultMan=n.Code "+
				" left join tEmployee o on k.ProjectMan=o.Number "+
				" left join tCusttype p on b.CustType = p.Code " +
				" left join tBuilder q on b.BuilderCode = q.Code " +
				" left join tRegion r on q.RegionCode = r.Code " +
				" where 1=1 ";
		if(StringUtils.isNotBlank(laborFee.getNo())){
			sql+=" and a.no= ?";
			list.add(laborFee.getNo());
			
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.pk ";
		}
		System.out.println(sql);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailActNamePageBySql(Page<Map<String,Object>> page,LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select a.ActName, a.CardId, sum(a.Amount) Amount "
                + "   from  tLaborFeeDetail a "
                + " where 1=1 " ;
		
		if(StringUtils.isNotBlank(laborFee.getNo())){
			sql+=" and a.no= ?";
			list.add(laborFee.getNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " group by  a.ActName, a.CardId  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "  group by  a.ActName, a.CardId    ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailCompanyPageBySql(Page<Map<String,Object>> page,LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select sum(isnull(a.Amount,0)) Amount, a.company, case when a.type = 0 and a.IsAddAllInfo = '1' then '非独立销售' " +
				" when a.type = '0' and a.IsAddAllInfo = '0' then '独立销售'" +
				"	when type = '1' then '公司售后' when a.type = '2' then '公司扣款' when a.type = '3' then '公司内部' end type" +
				"	 from (" +
				"		select	sum(a.Amount) Amount, e.Descr company,g.IsAddAllInfo,f.Code," +
				"		case when f.Code is null and b.ConstructStatus <> '7' then 0 when (select ','+QZ+',' from tXTCS where ID = 'AftSaleCust') like '%,'+a.CustCode+',%' then 1 " +
				"		when  (select ','+QZ+',' from tXTCS where ID = 'AftCustCode')  like '%,'+a.CustCode+',%'" +
				"			and (select ','+QZ+',' from tXTCS where ID = 'AftSaleCust') not like '%,'+a.CustCode+',%' then 2 else  3 end type" +
				"		from	tLaborFeeDetail a" +
				"		left join tCustomer b on b.Code = a.CustCode " +
				"		left join tCustomer f on f.Code = a.RefCustCode and b.ConstructStatus = '7' " +
				"		left join tCusttype g on g.Code= case when f.Code is null then b.CustType else f.CustType end" +
				"		left join tBuilder c on c.Code = case when f.Code is null then b.BuilderCode else f.BuilderCode end" +
				"		left join tRegion d on d.Code = c.RegionCode" +
				"		left join tTaxPayee e on e.Code = case when f.Code is null then b.PayeeCode else f.PayeeCode end " +
				"		where	1 = 1  " ;
		if(StringUtils.isNotBlank(laborFee.getNo())){
			sql+=" and a.no= ?";
			list.add(laborFee.getNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " group by e.Descr,g.IsAddAllInfo,f.Code,a.CustCode, b.ConstructStatus ) a " +
					" group by a.IsAddAllInfo,a.company,a.type  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " group by e.Descr,g.IsAddAllInfo,f.Code,a.CustCode, b.ConstructStatus  ) a " +
					" group by a.IsAddAllInfo, a.company, a.type order by a.company ,type  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findLaborFeeAccountBySql(Page<Map<String,Object>> page,LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (select a.ActName,a.Bank,a.CardId,a.Amount,"
				+ "     a.LastUpdate,a.lastUpdatedBy,a.pk,a.No, isnull(a.DeductAmount, 0) DeductAmount, "
		        + "     isnull(a.Amount, 0) - isnull(a.DeductAmount, 0) NetAmount "
                + " from  tLaborFeeAccount a "
                + " where 1=1 " ;
		
		if(StringUtils.isNotBlank(laborFee.getNo())){
			sql+=" and a.no= ?";
			list.add(laborFee.getNo());
		} else {
			sql+=" and 1<>1";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.amount ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDetailCustTypePageBySql(
            Page<Map<String, Object>> page, LaborFee laborFee) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (select b.CustType, c.Desc1 CustTypeDescr, sum(a.Amount) Amount "
                + "from tLaborFeeDetail a "
                + "left join tCustomer b on a.CustCode = b.Code "
                + "left join tCusttype c on b.CustType = c.Code "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(laborFee.getNo())) {
            sql += " and a.No = ? ";
            parameters.add(laborFee.getNo());
        }
        
        sql += "group by b.CustType, c.Desc1 ";
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.CustType";
        }
        
        return this.findPageBySql(page, sql, parameters.toArray());
    }
	
	public Page<Map<String, Object>> findProcListJqGrid(
            Page<Map<String, Object>> page, LaborFee laborFee) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		" select wfProcInstNo,EmpCode,EmpName,Company,Reason,ItemType1,DeptDescr,Amount,StartTime,RefNo, b.ActProcDefId " +
        		" from twfCust_ConstructionExpenseClaim a " +
        		" left join tWfProcInst b on b.No = a.WfProcInstNo "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(laborFee.getNo())) {
            sql += " and a.RefNo = ? ";
            parameters.add(laborFee.getNo());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.StartTime desc";
        }
        
        return this.findPageBySql(page, sql, parameters.toArray());
    }
	
	public Page<Map<String, Object>> findProcTrackJqGrid(
            Page<Map<String, Object>> page, LaborFee laborFee) {
        
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select 1 dispseq,a.lastUpdate,case when a.ActionType = '5' then '' else " +
				"	case when rt.name_ is null then x1.NOTE else case when rt.name_ = '发起人' then rt.name_+'修改' else rt.name_  end end end wfProcDescr" +
				"	,d.ZWXM nameChiDescr,x1.NOTE statusDescr" +
				" ,a.Remarks,isnull(a.FromActivityId,0) FromActivityId,case when a.actionType ='1' then 'applyMan' else rt.TASK_DEF_KEY_ end  taskDefKey,a.ToActivityDescr from tWfProcTrack  a " +
				" left join tWfProcInst b on b.No=a.WfProcInstNo" +
				" left join tWfProcess c on c.no = b.WfProcNo" +
				" left join tCZYBM d on d.CZYBH = a.operId" +
				" left join tXTDM x1 on x1.CBM = a.ActionType and x1.id ='PROCACTTYPE'" +
				" left join ACT_HI_TASKINST rt on a.FromActivityId = rt.id_ "+// id已经是唯一确定值了,去除无关条件 rt.PROC_INST_ID_ = b.ActProcInstId and rt.proc_def_id_ = b.ActProcDefId and a.OperId = rt.ASSIGNEE_ and 
				" where a.WfProcInstNo = ? " +
				" union " +
				" select 2 dispseq,isnull(a.RcvDate, '9999-12-31 00:00:000') lastUpdate , c.Descr+'(抄送)' wfProcDescr " +
				" , d.ZWXM nameChiDescr , x1.NOTE statusDescr, '' remarks, 999999,'' taskDefKey,'' ToActivityDescr from tWfProcInstCopy a " +
				"	left join tWfProcInst b on b.No = a.WfProcInstNo" +
				"	left join tWfProcess c on c.no = b.WfProcNo" +
				"	left join tCZYBM d on d.CZYBH = a.CopyCZY" +
				"	left join tXTDM x1 on x1.CBM = a.RcvStatus and x1.id = 'WFPICOPYRCVSTAT'" +
				" 	left join act_ru_task rt on rt.PROC_INST_ID_ = b.ActProcInstId and rt.proc_def_id_ = b.ActProcDefId " +
				"  where a.WfProcInstNo = ? and CopyDate is Not null " +
				" union " +
				" select 3 dispseq,a.LastUpdate,'' ,d.ZWXM,'评论' statusdescr,a.comment remarks, " +
				" '' FromActivityId,'' taskDefKey,'' ToActivityDescr from tWfProcComment a" +
				" left join tWfProcInst b on b.no = a.wfprocinstno " +
				" left join tWfProcess c on c.no = b.WfProcNo" +
				" left join tCZYBM d on d.CZYBH = a.EmpCode " +
				" where a.wfprocinstno = ? ";
		sql += " ) a where 1=1 ";
		list.add(laborFee.getWfProcInstNo());
		list.add(laborFee.getWfProcInstNo());
		list.add(laborFee.getWfProcInstNo());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by lastUpdate,FromActivityId ";//因为taskId就是随流程递增，排序根据FromActivityId
		}
		return this.findPageBySql(page, sql, list.toArray());
    }
	
	public Page<Map<String,Object>> findLaborDetailPageBySql(Page<Map<String,Object>> page,LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (select a.No ,a.ItemType1 ,a.Status ,a.ActName,a.CardID ,a.Remarks ShRemarks,a.AppCZY ,a.Date ,a.ConfirmCZY ,a.ConfirmDate ,a.PayCZY ,a.PayDate ,a.DocumentNo ,"
           + " b.Descr ItemType1Descr,x1.note StatusDescr,c.zwxm AppCZYDecr, d.zwxm ConfirmCZYDecr,e.zwxm PayCZYDecr ,f.ActName DetailActName,f.CardID DetailCardID, "
           + " f.FeeType,g.Code CustCode, f.AppSendNo ,f.Amount ,f.Remarks LaborFeeDetailRemarks ,g.Address,g.DocumentNo CustomerDocumentNo, "
           + " h.Descr FeeTypeDescr,f.pk,i.Address RefAddress "
           + " from   tLaborFee a  " +
           " left outer join tItemType1 b on b.code=a.ItemType1 "
           + " left outer join tXTDM x1 ON x1.CBM=a.Status AND x1.id='LABORFEESTATUS'  "
           + " left outer join tCZYBM  c on c.czybh=a.AppCZY   "
           + " left outer join tCZYBM d on d.czybh=a.ConfirmCZY  "
           + " left outer join tCZYBM e on e.czybh=a.PayCZY  "
           + " left outer join tLaborFeeDetail f on f.no=a.No  "
           + " left outer join tCustomer g on g.code=f.CustCode  "
           + " left outer join tLaborFeeType h on h.code=f.FeeType "
           + " left outer join tCustomer i on i.Code=f.RefCustCode "
           + " where 1=1 ";
		if(StringUtils.isNotBlank(laborFee.getNo())){
			sql+=" and a.no = ? ";
			list.add(laborFee.getNo());
		}
		if(StringUtils.isNotBlank(laborFee.getItemType1())){
			sql+=" and a.ItemType1 = ? ";
			list.add(laborFee.getItemType1());
		}
		if(StringUtils.isNotBlank(laborFee.getFeeType())){
			sql+=" and f.FeeType= ? ";
			list.add(laborFee.getFeeType());
		}
		if(StringUtils.isNotBlank(laborFee.getActName())){
			sql+=" and( a.ActName like ? or f.actName like ?) ";
			list.add("%"+laborFee.getActName()+"%");
			list.add("%"+laborFee.getActName()+"%");
		}
		if(laborFee.getDateFrom()!=null){
			sql+=" and a.date>=? ";
			list.add(laborFee.getDateFrom());
		}
		if(laborFee.getDateTo()!=null){
			sql+=" and a.date<DATEADD(d,1,?)";
			list.add(laborFee.getDateTo());
		}
		if(laborFee.getConfirmDateFrom()!=null){
			sql+=" and a.confirmDate>=? ";
			list.add(laborFee.getConfirmDateFrom());
		}
		if(laborFee.getConfirmDateTo()!=null){
			sql+=" and a.confirmDate<DATEADD(d,1,?)";
			list.add(laborFee.getConfirmDateTo());
		}
		if(laborFee.getPayDateFrom()!=null){
			sql+=" and a.PayDate>=? ";
			list.add(laborFee.getPayDateFrom());
		}
		if(laborFee.getPayDateTo()!=null){
			sql+=" and a.PayDate<DATEADD(d,1,?)";
			list.add(laborFee.getPayDateTo());
		}
		if(StringUtils.isNotBlank(laborFee.getStatus())){
			sql += " and a.status in " + "('"+laborFee.getStatus().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(laborFee.getAddress())){
			sql+=" and g.address like ?";
			list.add("%"+laborFee.getAddress()+"%");
		}
		
		if (StringUtils.isNotBlank(laborFee.getCustDocumentNo())) {
		    sql += " and g.DocumentNo  = ? ";
		    list.add(laborFee.getCustDocumentNo());
		}
		
		if(StringUtils.isNotBlank(laborFee.getDetailRemarks())){
			sql+=" and f.remarks like ? ";
			list.add("%"+laborFee.getDetailRemarks()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findItemAppSendPageBySql(Page<Map<String,Object>> page,ItemAppSend itemAppSend) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (select  t1.IsSetItem,a.no, a.IANo,a.date,a.WHCode,a.Remarks,a.WHCheckOutNo, a.CheckSeq,   "
           + " x2.NOTE ItemType1Descr,i2.Descr ItemType2Descr,d.Address Address,  "
           + " t1.CustCode,d.Descr,d.DocumentNo,t1.SupplCode,t1.type,c.NOTE TypeDescr,t1.SendDate," +
           	 " x1.NOTE SendType,t1.OtherCost,t1.OtherCostAdj "
           + " from tItemAppSend  a "
           + " left outer join tItemApp t1 on t1.no=a.IANo  "
           + " left outer join tSupplier b on b.Code=t1.SupplCode " +
           	 " left outer join tXTDM c on c.CBM=t1.Type and c.ID='ITEMAPPTYPE'"
           + " left outer join tCustomer d on d.Code=t1.CustCode   " +
             " left outer join tXTDM x1 on x1.IBM=t1.SendType and x1.id='ITEMAPPSENDTYPE'   "
           + " left outer join tXTDM x2 on x2.CBM=t1.ItemType1 and x2.id='ITEMRIGHT'   "
           + " left outer join tItemType2 i2 on t1.ItemType2=i2.Code "
           + " where a.Expired='F'";
		if(StringUtils.isNotBlank(itemAppSend.getCustCode())){
			sql+=" and  t1.CustCode=? ";
			list.add(itemAppSend.getCustCode());
		}else{
			sql+=" and 1<>1 " ;
		}
		if(StringUtils.isNotBlank(itemAppSend.getNo())){
			sql+=" and a.No= ? ";
			list.add(itemAppSend.getNo());
		}
		if(StringUtils.isNotBlank(itemAppSend.getIaNo())){
			sql+=" and a.iano = ? ";
			list.add(itemAppSend.getIaNo());
		}
		if(StringUtils.isNotBlank(itemAppSend.getWhCode())){
			sql+=" and a.WhCode= ? ";
			list.add(itemAppSend.getWhCode());
		}
		if(itemAppSend.getDateFrom()!=null){
			sql+=" and a.Date > ? ";
			list.add(itemAppSend.getDateFrom());
		}
		if(itemAppSend.getDateTo()!=null){
			sql+=" and a.date < dateAdd(d,1,?) ";
			list.add(itemAppSend.getDateTo());
		}
		if(StringUtils.isNotBlank(itemAppSend.getItemType2())){
			sql+=" and t1.itemType2 = ? ";
			list.add(itemAppSend.getItemType2());
		}
		if(StringUtils.isNotBlank(itemAppSend.getItemType1())){
			sql+=" and t1.itemType1= ? ";
			list.add(itemAppSend.getItemType1());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findItemAppSendDetailPageBySql(Page<Map<String,Object>> page,ItemAppSend itemAppSend) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select  a.sendQty*isnull(g.intinstallfee,0) azFee,b.ItemCode,c.Descr ItemDescr,a.SendQty,u.Descr uomDescr," +
				" a.SendQty*g.IntInstallFee amount,f.Descr FixAreaDescr," +
				" d.descr ItemType1descr, e.descr ItemType2descr,(c.PerWeight*a.SendQty)weight from tItemAppSendDetail a   " +
				" left outer join tItemAppDetail b on b.pk=a.RefPk  " +
				" left outer join tItem c on  c.code = b.ItemCode  " +
				" left outer join tUom u on u.code = c.uom " +
				" left outer join tItemtype1 d on d.code = c.ItemType1 "+
				" left outer join titemtype2 e on e.code = c.ItemType2 " +
				" left outer join titemType3 g on g.code= c.itemType3 " +
				" left outer join tFixArea f on f.pk=b.FixAreaPK  " +
				" where a.Expired='F' and a.no = ? ";
		list.add(itemAppSend.getNo()); //没有发货单号，显示空 modify by zb
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findItemReqBySql(Page<Map<String,Object>> page,ItemAppSend itemAppSend) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select  * from ( select a.Pk, a.ItemCode, a.qty, u.Descr uomDescr, b.descr Itemdescr, a.Remark," +
				" a.SendQty, c.descr ItemType1descr, d.descr ItemType2descr," +
				" e.descr FixAreadescr, LineAmount, DiscCost," +
				" LineAmount - DiscCost AftDiscAmount, a.ProcessCost " +
				" from   tItemReq a " +
				" left outer join tItem b on b.code = a.ItemCode " + 
				" left outer join tItemtype1 c on c.code = b.ItemType1 " +
				" left outer join titemtype2 d on d.code = b.ItemType2 " +
				" left outer  join tFixArea e on e.pk = a.FixAreaPK " +
				" left outer join tUom u on u.code = b.uom " +
				" where 1=1 and a.qty <> '0' and a.IsService = '0' ";
		if(StringUtils.isNotBlank(itemAppSend.getCustCode())){
			sql+=" and  a.custCode=? ";
			list.add(itemAppSend.getCustCode());
		}else{
			sql+=" and  a.custCode='' ";
		}
		if(StringUtils.isNotBlank(itemAppSend.getItemType1())){
			sql+=" and b.itemType1= ? ";
			list.add(itemAppSend.getItemType1());
		}
		if(StringUtils.isNotBlank(itemAppSend.getAreaDescr())){
			sql+=" and e.descr= ? ";
			list.add(itemAppSend.getAreaDescr());
		}
		if(StringUtils.isNotBlank(itemAppSend.getItemDescr())){
			sql+=" and b.descr like  ? ";
			list.add("%"+itemAppSend.getItemDescr()+"%");
		}
		if(StringUtils.isNotBlank(itemAppSend.getItemType2())){
			sql += " and d.code in " + "('"+itemAppSend.getItemType2().replaceAll(",", "','")+"')";
			
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public String getSendNoHaveAmount(String custCode, String feeType){
		String sql=" select isnull(sum(a.amount),0) SendNoHaveAmount from tLaborFeeDetail a " +
				" left outer join tLaborFee b on  b.no=a.no  where (b.Status='3'or b.status='4') " +
				" and a.CustCode=?  and a.FeeType=? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode,feeType});
		if (list!=null && list.size()>0){
			return list.get(0).get("SendNoHaveAmount").toString();
		}
		return "0";
	}
	public String getHaveAmount(String sendNo, String feeType){
		String sql=" select isnull(sum(a.amount),0) HaveAmount from tLaborFeeDetail a " +
				   " left outer join tLaborFee b on  b.no=a.no  where (b.Status='3'or b.status='4') and AppSendNo=? " +
				   " and a.FeeType=? " ;
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{sendNo,feeType});
		if (list!=null && list.size()>0){
			return list.get(0).get("HaveAmount").toString();
		}
		return "0";
	}
	
	public String getFeeTypeDescr(String feeType){
		String sql=" select Descr from tLaborFeeType where code= ? " ;
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{feeType});
		if (list!=null && list.size()>0){
			return list.get(0).get("Descr").toString();
		}
		return " ";
	}
	 
	public String isHaveSendNo(String feeType){
		String sql=" select  isHaveSendNo from tLaborFeeType where code= ? and expired ='F' ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{feeType});
		if(list!=null && list.size()>0){
			return list.get(0).get("isHaveSendNo").toString();
		}
		return "0";
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(LaborFee laborFee) {
		Assert.notNull(laborFee);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRgFygl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, laborFee.getM_umState());
			call.setString(2, laborFee.getNo());
			call.setString(3, laborFee.getItemType1());
			call.setString(4, laborFee.getStatus());
			call.setString(5, laborFee.getActName());
			call.setString(6, laborFee.getCardID());
			call.setString(7, laborFee.getBank());
			call.setString(8, laborFee.getRemarks());
			call.setString(9, laborFee.getAppCZY());
			call.setString(10, laborFee.getConfirmCZY());
			call.setString(11, laborFee.getPayCZY());
			call.setTimestamp(12, laborFee.getPayDate()==null ? null
					: new Timestamp(laborFee.getPayDate().getTime()));
			call.setString(13, laborFee.getDocumentNo());
			call.setString(14, laborFee.getLastUpdatedBy());
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.NVARCHAR);
			call.setString(17, laborFee.getLaborFeeDetailXml());
			call.setString(18, laborFee.getLaborFeeAccountDetailXml());
			System.out.println(laborFee.getLaborFeeAccountDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(15)));
			result.setInfo(call.getString(16));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public List<Map<String,Object>> findFeeType(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tPrjPromDept a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.code in ("+param.get("pCode")+") ";
		}
		sql += " order by a.code ";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findItemType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType1 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.code in ("+param.get("pCode")+") ";
		}
		sql += " order by a.dispSeq ";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findFeeTypeByItemType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tLaborFeeType a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.itemType1 = '"+param.get("pCode")+"'";
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}
	
	
	public boolean isSetItem(String no){
		String sql=" select a.no, c.CheckStatus from tItemAppSend  a "
				    +" left outer join tItemApp b on b.no=a.IANo "
				    +" left outer join tCustomer c on c.Code=b.CustCode "
				    +"  where c.CheckStatus in ('3','4') and b.IssetItem='1' and a.No= ? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{no});
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public String getCheckStatusDescr(String checkStatus){
		String sql=" select  note from txtdm where id='CheckStatus' and cbm=? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{checkStatus});
		if(list!=null && list.size()>0){
			return list.get(0).get("note").toString();
		}
		return "";
	}
	
	public void doSaveWorkCard(String actNameReal, String cardID,String lastUpdatedBy) {
		String sql = " insert into tWorkCard ( CardID, ActName, LastUpdate, LastUpdatedBy, Expired, ActionLog )" +
				"values(?,?,getdate(),?,'F','ADD')";
													
		this.executeUpdateBySql(sql, new Object[]{cardID,actNameReal,lastUpdatedBy});
	}
	public boolean getIsExistsFeeType(String itemType1,String feeType){
		String sql=" select  * from tlaborFeeType where itemType1= ? and code= ? ";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{itemType1,feeType});
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public boolean isExistsWorkCard(String aceName){
		String sql=" select * from tWorkCard where 1=1 and actName= ? ";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{aceName});
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public Page<Map<String, Object>> findIntFeePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		Assert.notNull(laborFee);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pImportIntInstallFee(?,?,?,?,?)}");
			call.setTimestamp(1, laborFee.getDateFrom() == null ? null : new Timestamp(
					laborFee.getDateFrom().getTime()));
			call.setTimestamp(2, laborFee.getDateTo() == null ? null : new Timestamp(
					laborFee.getDateTo().getTime()));
			call.setString(3, laborFee.getNos());
			call.setString(4, laborFee.getNo());
			call.setString(5, laborFee.getWorkerCode());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
		//return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findCupFeePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.ConfirmDate,x1.Note CheckStatusDescr,a.CustCode,e.Address,e.Area,isnull(a.CupDownHigh,0) CupDownHigh," +
				" isnull(a.CupUpHigh,0) CupUpHigh,e.CustCheckDate," +
				"	(isnull(a.CupDownHigh,0)+isnull(a.CupUpHigh,0))*cast(x.QZ as money) amount ,'CGAZF' feeType,'橱柜安装费' feeTypeDescr, " +
				" x.qz qtyfee ,f.desc1 custtypedescr,e.documentNo, rg.Descr RegionDescr, " +
				" g.CardID,isnull(h.ActName, g.nameChi) ActName from tCupMeasure a " +
//				" left join tCupMeasure b on b.CustCode=a.CustCode" +
				" left join tCustomer e on e.Code=a.CustCode" +
				" left join tBuilder bd on e.BuilderCode = bd.Code " +
                " left join tRegion rg on bd.RegionCode = rg.Code " +
				" left join tCusttype f on f.Code=e.CustType" +
				" left join tXTCS x on x.id='CupInsFee'" +
				" left join tWorker g on g.Code = a.WorkerCode" +
				" left join tWorkCard h on h.CardID = g.CardID " +
				" left outer join tXTDM x1 on x1.CBM = e.CheckStatus" +
				"                        and x1.id = 'CheckStatus'" +
				" where 1=1 and  not exists (select * from tLaborFeeDetail in_a " +
				"							 left join tLaborfee in_b on in_b.no = in_a.no" +
				"					 where in_a.feeType = 'CGAZF' and in_a.custCode = a.custCode and in_b.status <> '5' ";
				if(StringUtils.isNotBlank(laborFee.getNo())){
					sql +="and in_a.no<>? ";
					list.add(laborFee.getNo());
				}
				sql+=") and a.status = '2' and a.IsCupboard='1' ";
		
		if(StringUtils.isNotBlank(laborFee.getWorkerCode())){
			sql+=" and a.WorkerCode = ? ";
			list.add(laborFee.getWorkerCode());
		}
		if(laborFee.getDateFrom() !=null){
			sql+=" and a.ConfirmDate> ? ";
			list.add(laborFee.getDateFrom());
		}
		if(laborFee.getDateTo() !=null){
			sql+=" and a.ConfirmDate <= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(laborFee.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(laborFee.getCustCodes())) {
			sql += " and a.custcode not in  "+ "('"+ laborFee.getCustCodes().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findCupFeeByReqPageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select x1.Note CheckStatusDescr,a.CustCode,e.Address,e.Area," +
				" e.CustCheckDate," +
				" ir.amount ,'CGAZF' feeType,'橱柜安装费' feeTypeDescr, " +
				" x.qz qtyfee ,f.desc1 custtypedescr,e.documentNo, rg.Descr RegionDescr, " +
				" g.CardID,isnull(h.ActName, g.nameChi) ActName   " +
				" from (  " +
				" 		select max(PK) pk,CustCode " +
				" 		from tCustWorker a   " +
				" 		left join tWorkType12 b on b.Code=a.WorkType12  " +
				" 		where WorkType12 = '18'   " +
				" 		group by CustCode  " +
				" ) a  " +
				" inner join (  " + 
				" 		select sum(in_a.Qty*isnull(in_d.IntInstallFee,0))amount,CustCode  " +
				" 		from tItemReq in_a  " +
				" 		left join tIntProd in_b on in_a.IntProdPK = in_b .PK  " +
				"       left join tItem in_c on in_a.ItemCode = in_c.Code "+
				"       left join tItemType3 in_d on in_c.ItemType3 = in_d.Code "+
				" 		where in_b.IsCupboard='1' and in_a.ItemType1 ='JC' and in_a.Qty<>0    " +
				" 		group by CustCode  " +
				" )ir on ir.CustCode = a.CustCode  " +
				" left join tCustomer e on e.Code=a.CustCode" +
				" left join tBuilder bd on e.BuilderCode = bd.Code " +
                " left join tRegion rg on bd.RegionCode = rg.Code " +
				" left join tCusttype f on f.Code=e.CustType " +
				" left join tCustWorker j on j.pk=a.pk " +
				" left join tXTCS x on x.id='CupInsFee'" +
				" left join tWorker g on g.Code = j.WorkerCode" +
				" left join tWorkCard h on h.CardID = g.CardID " +
				" left outer join tXTDM x1 on x1.CBM = e.CheckStatus" +
				"                        and x1.id = 'CheckStatus'" +
				" where j.endDate is not null and not exists (select * from tLaborFeeDetail in_a " +
				"							 left join tLaborfee in_b on in_b.no = in_a.no" +
				"					 where in_a.feeType = 'CGAZF' and in_a.custCode = a.custCode and in_b.status <> '5' ";
				if(StringUtils.isNotBlank(laborFee.getNo())){
					sql +="and in_a.no<>? ";
					list.add(laborFee.getNo());
				}
				sql+=") ";
		
		if(StringUtils.isNotBlank(laborFee.getWorkerCode())){
			sql+=" and a.WorkerCode = ? ";
			list.add(laborFee.getWorkerCode());
		}
		if(laborFee.getDateFrom() !=null){
			sql+=" and j.EndDate> ? ";
			list.add(laborFee.getDateFrom());
		}
		if(laborFee.getDateTo() !=null){
			sql+=" and j.EndDate <= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(laborFee.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(laborFee.getCustCodes())) {
			sql += " and a.custcode not in  "+ "('"+ laborFee.getCustCodes().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findSendFeePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select c.Address, rg.Descr RegionDescr, a.No appSendNo,a.IANo, b.SendDate,isnull(a.TransFee,0)TransFee, "
				+ "x1.NOTE isSetItemDescr,c.DocumentNo,b.CustCode,x2.NOTE CheckStatusDescr,e.Descr feeTypeDescr, "
				+ "0 amount1,0 amount2,0 hadamount, 0 sendnohaveamount,c.CustCheckDate,isnull(a.CarryFee,0)+isnull(a.TransFee,0)+isnull(a.TransFeeAdj,0) amount, "
				+ "isnull(a.CarryFee,0)+isnull(a.TransFee,0)+isnull(a.TransFeeAdj,0) SendFee,e.Code feetype,b.IsSetItem,"
				+ "isnull(a.CarryFee,0)CarryFee,isnull(a.TransFeeAdj,0)TransFeeAdj,ManySendRemarks,b.RefCustCode  "
				+ " from tItemAppSend a  "
				+ " left join tItemApp b on a.IANo=b.No  "
				+ " left join tCustomer c on b.CustCode=c.Code "
				+"  left join tBuilder bd on c.BuilderCode = bd.Code "
                +"  left join tRegion rg on bd.RegionCode = rg.Code "
				+ " left join tXTDM x1 on b.IsSetItem=x1.IBM and x1.ID='YESNO' "
				+ " left join tXTDM x2 on c.CheckStatus=x2.IBM and x2.ID='CheckStatus' "
				+ " left join tLaborFeeType e on e.Code = case when ? = 'ZC' then '01' " +
				"					when ? = 'JC' and b.IsCupboard = '1' then 'CFPSF' " +
				"					when ? = 'JC' and b.IsCupboard = '0' then 'JCPSF' end"
				+ " left join tItemSendBatch f on a.SendBatchNo=f.No "
				+ " where not exists(select 1 from tLaborFeeDetail d where a.No=d.AppSendNo and d.No<>? and d.FeeType=e.Code ) "
				+ " and b.ItemType1=? ";
		list.add(laborFee.getItemType1());
		list.add(laborFee.getItemType1());
		list.add(laborFee.getItemType1());
		list.add(laborFee.getNo());
		list.add(laborFee.getItemType1());
		
		if (StringUtils.isNotBlank(laborFee.getDriver())) {
			sql += " and a.DriverCode=? ";
			list.add(laborFee.getDriver());
		}
		if (StringUtils.isNotBlank(laborFee.getNos())) {
			sql += " and a.no not in  "+ "('"+ laborFee.getNos().replaceAll(",", "','") + "')";
		}
		if (laborFee.getDateFrom()!=null) {
			sql += " and f.Date >= ? ";
			list.add(laborFee.getDateFrom());
		}
		if(laborFee.getDateTo()!=null){
			sql += " and f.Date <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(laborFee.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.appSendNo asc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	//浴室柜查询 add by zb on 20190625
	public Page<Map<String, Object>> findBathFeePageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		/*String sql = "select * from( "
				+ "select a.No,b.CustCode,c.Address,c.DocumentNo,c.CheckStatus,tx.NOTE CheckStatusDescr, "
				+ "c.CustCheckDate,c.CustType,g.Desc1 CustTypeDescr,isnull(f.amount,0) amount, "
				+ "'YSGAZF' feetype, '浴室柜安装费' feetypedescr,h.PK CustWorkPk,h2.WorkerCode,i.CardID,"
				+ "case when j.ActName is null then i.NameChi else j.ActName end ActName,e.PayDate "
				+ "from tItemAppSend a "
				+ "inner join tItemApp b on a.IANo=b.No "
				+ "inner join tCustomer c on b.CustCode=c.Code "
				+ "inner join ( "
				+ "  select max(in_a.PK) PK, in_a.CustCode from tWorkCostDetail in_a "
				+ "  where in_a.WorkType2='023' "
				+ "  group by in_a.CustCode "
				+ ") d on d.CustCode=b.CustCode "
				+ "inner join tWorkCostDetail d2 on d2.PK=d.PK "
				+ "inner join tWorkCost e on d2.No=e.No "
				+ "left join ( "
				+ "  select in_a.No,sum(in_a.SendQty*in_d.IntInstallFee) amount "
				+ "  from tItemAppSendDetail in_a  "
				+ "  inner join tItemAppDetail in_b on in_a.RefPk=in_b.PK "
				+ "  inner join tItem in_c on in_c.Code=in_b.ItemCode "
				+ "  inner join tItemType3 in_d on in_d.Code=in_c.ItemType3 "
				+ "  where (in_c.ItemType3 in ('"+laborFee.getToilet().replace(",", "','")+"') "
				+ "  or in_c.ItemType3 in ('"+laborFee.getCabinet().replace(",", "','")+"')) "
				+ "  group by in_a.No "
				+ ")f on f.No=a.NO "
				+ "left join tXTDM tx on tx.ID = 'CheckStatus' and c.CheckStatus = tx.CBM "
				+ "left join tCusttype g on g.Code = c.CustType "
				+ "inner join ( "
				+ "  select max(PK) PK,CustCode from tCustWorker a  "
				+ "  left join tWorkType12 b on b.Code=a.WorkType12 "
				+ "  where WorkType12 = '14' group by CustCode "
				+ ") h on h.custCode = b.custCode "
				+ "left join tCustWorker h2 on h2.PK=h.PK "
				+ "left join tWorker i on i.Code=h2.WorkerCode "
				+ "left join tWorkCard j on j.CardID=i.CardID "
				+ "where b.SendType='2' and d2.Status='2'  "
				+ "and exists ( "
				+ "  select 1 from tItemAppSendDetail in_a  "
				+ "  inner join tItemAppDetail in_b on in_a.RefPk=in_b.PK "
				+ "  inner join tItem in_c on in_c.Code=in_b.ItemCode "
				+ "  where in_a.No=a.No and (in_c.ItemType3 in ('"+laborFee.getToilet().replace(",", "','")+"') "
				+ "  or in_c.ItemType3 in ('"+laborFee.getCabinet().replace(",", "','")+"')) "
				+ ") "
				+ "and not exists ( "
				+ "  select * from tLaborFeeDetail in_a "
				+ "  left join tLaborfee in_b on in_b.no = in_a.no "
				+ "  where in_a.feeType in ('86','113') and in_a.custCode = b.custCode and in_b.status <> '5' "
				+ ")";*/
		//用于计算安装费的数量，要在发货数量和工人APP完工填写数量中进行比较，取小的计算安装费。（原来的按发货编号查询无法满足要求，现改为按客户查询）modify by zb on 20191217
		String sql = "select * from( " 
				    +"select a.CustCode, c.Address, c.DocumentNo, c.CheckStatus, tx.NOTE CheckStatusDescr, c.CustCheckDate, "
					+"c.CustType, g.Desc1 CustTypeDescr, isnull(f.amount, 0) amount, 'YSGAZF' feetype, '浴室柜安装费' feetypedescr, "
					+"h.PK CustWorkPk, h2.WorkerCode, i.CardID,f.ToiletSendAmount,f.ToiletQty,f.CabinetSendAmount,f.CabinetQty, "
					+"case when j.ActName is null then i.NameChi else j.ActName end ActName, e.PayDate, rg.Descr RegionDescr ,a.RefCustCode,a.No,k.AppSendNo "
					+"from tItemApp a "
					+"inner join tCustomer c on a.CustCode = c.Code "
					+"left join tBuilder bd on c.BuilderCode = bd.Code "
		            +"left join tRegion rg on bd.RegionCode = rg.Code "
					+"inner join ( "
					+"	select  max(in_a.PK) PK, in_a.CustCode "
					+"	from tWorkCostDetail in_a "
					+"	where in_a.WorkType2 = '023' "
					+"	group by in_a.CustCode "
					+") d on d.CustCode = a.CustCode "
					+"inner join tWorkCostDetail d2 on d2.PK = d.PK "
					+"inner join tWorkCost e on d2.No = e.No "
					+"left join ( "
					+"	select   in_a.CustCode,  "
					+"	case when in_a.ToiletSendAmount < in_b.ToiletQty then in_a.ToiletSendAmount "
					+"	else in_b.ToiletQty end * cast(in_c1.QZ as money) +  "
					+"	case when in_a.CabinetSendAmount < isnull(in_b.CabinetQty,0) then in_a.CabinetSendAmount "
					+"	else in_b.CabinetQty end * cast(in_c2.QZ as money) amount,in_a.ToiletSendAmount,in_b.ToiletQty,in_a.CabinetSendAmount,in_b.CabinetQty "
					+"	from ( "
					+"	  select in_in_e.CustCode, "
					+"	    sum(case when in_in_c.ItemType3 in ('"+laborFee.getToilet().replace(",", "','")+"') "
					+"	    then in_in_a.SendQty else 0 end) ToiletSendAmount, "
					+"	    sum(case when in_in_c.ItemType3 in ('"+laborFee.getCabinet().replace(",", "','")+"') "
					+"	    then in_in_a.SendQty else 0 end) CabinetSendAmount "
					+"	  from   tItemAppSendDetail in_in_a "
					+"	  inner join tItemAppDetail in_in_b on in_in_a.RefPk = in_in_b.PK "
					+"	  inner join tItem in_in_c on in_in_c.Code = in_in_b.ItemCode "
					+"	  inner join tItemType3 in_in_d on in_in_d.Code = in_in_c.ItemType3 "
					+"	  inner join tItemApp in_in_e on in_in_e.No=in_in_b.No "
					+"	  where in_in_e.SendType = '2' "
					+"	  group by in_in_e.CustCode "
					+"	) in_a "
					+"	inner join tCustomer in_b on in_b.Code=in_a.CustCode "
					+"	left join tXTCS in_c1 on in_c1.ID='ToiletInsFee' "
					+"	left join tXTCS in_c2 on in_c2.ID='CabinInsFee'	 "
					+") f on f.CustCode = a.CustCode "
					+"left join tXTDM tx on tx.ID = 'CheckStatus' and c.CheckStatus = tx.CBM "
					+"left join tCusttype g on g.Code = c.CustType "
					+"inner join ( "
					+"	select max(PK) PK, CustCode "
					+"	from tCustWorker a "
					+"	left join tWorkType12 b on b.Code = a.WorkType12 "
					+"	where WorkType12 = '14' "
					+"	group by CustCode "
					+") h on h.CustCode = a.CustCode "
					+"left join tCustWorker h2 on h2.PK = h.PK "
					+"left join tWorker i on i.Code = h2.WorkerCode "
					+"left join tWorkCard j on j.CardID = i.CardID "
					+"left join ( "
					+"	select max(No) AppSendNo,IANo from tItemAppSend group by IANo "
					+") k on a.No = k.IANo "
					+"where  a.SendType = '2' and d2.Status = '2' and exists (  "
					+"	select 1 "
					+"	from  ( "
					+"	  select min(in2_c.No) No "
					+"	  from tItemAppDetail in2_a "
					+"	  inner join tItem in2_b on in2_b.Code = in2_a.ItemCode "
					+"	  inner join tItemApp in2_c on in2_c.No=in2_a.No "
					+"	  where (in2_b.ItemType3 in ('"+laborFee.getToilet().replace(",", "','")+"') or "
					+"		in2_b.ItemType3 in ('"+laborFee.getCabinet().replace(",", "','")+"')) and in2_c.SendType='2' "
					+"	  group by in2_c.CustCode "
					+"	)in_a "
					+"	where in_a.No=a.No "
					+") and not exists (  "
					+"	select * "
					+"	from   tLaborFeeDetail in_a "
					+"	left join tLaborfee in_b on in_b.no = in_a.no "
					+"	where  (in_a.feeType in ('86','YSGAZF') or ( " 
					+"		exists(select 1 from tXTCS in_in_a where in_in_a.ID='CmpnyCode' and in_in_a.QZ='01') and in_a.feeType='113' "
					+"	)) and in_a.custCode = a.custCode and in_b.status <> '5'  "
					+")  ";
		if("0".equals(laborFee.getIsNotApp())){
			sql+="and (isnull(f.ToiletQty, 0) <> 0 and  isnull(f.CabinetQty, 0) <> 0) ";
		}else{
			sql+="and (isnull(f.ToiletQty, 0) = 0 or isnull(f.CabinetQty, 0) = 0)";
		}
		if(laborFee.getDateFrom() !=null){
			sql+=	" and e.PayDate>= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(laborFee.getDateFrom()).getTime()));
		}
		if(laborFee.getDateTo() !=null){
			sql+=	" and e.PayDate<= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(laborFee.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(laborFee.getCustCodes())) {
			sql += 	" and a.custcode not in  "+ "('"+ laborFee.getCustCodes().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += 	") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += 	") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String,Object>> goTransFeeJqGrid(Page<Map<String,Object>> page, LaborFee laborFee) {
		Assert.notNull(laborFee);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pImportTransFee(?,?,?)}");
			call.setTimestamp(1, laborFee.getDateFrom() == null ? null : new Timestamp(
					laborFee.getDateFrom().getTime()));
			call.setTimestamp(2, laborFee.getDateTo() == null ? null : new Timestamp(
					laborFee.getDateTo().getTime()));
			call.setString(3, laborFee.getCustCodes());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	public Page<Map<String, Object>> goPreFeeJqGrid(Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.No appsendno,b.CustCode ,b.No iano,c.CustCheckDate,c.Address,e.Desc1 custTypeDescr,f.Descr itemType2Descr,j.Descr,round(d.amount, 2) amount, "
				+ "c.DocumentNo,c.CheckStatus,x1.NOTE CheckStatusDescr,l.CardID,case when m.ActName is null then l.NameChi else m.ActName end ActName, "
				+ "b.isSetItem,x2.NOTE isSetItemDescr,i.InstallFeeType feeType,j.Descr feeTypeDescr,k2.PK custWorkPk,k2.WorkerCode,b.RefCustCode, rg.Descr RegionDescr  "
				+ "from tItemAppSend a  "
				+ "left join tItemApp b on a.IANo=b.No "
				+ "left join tCustomer c on b.CustCode=c.Code "
				+ "left join tBuilder bd on c.BuilderCode = bd.Code "
                + "left join tRegion rg on bd.RegionCode = rg.Code "
				+ "left join ( "
				+ "	select in_a.No,sum((in_a.SendQty*(in_c.UnitPrice*Markup/100)+in_c.ProcessCost*in_a.SendQty/in_c.Qty)*0.035) amount "
				+ " from tItemAppSendDetail in_a " 
				+ " left join tItemAppDetail in_b on in_a.RefPk=in_b.PK "
				+ " left join tItemReq in_c on in_b.ReqPK=in_c.PK "
				+ " left join tItemApp in_d on in_d.No = in_b.No "
				+ " left join tItemType2 in_e on in_e.Code = in_d.ItemType2 "
				+ "	left join tCustomer in_f on in_f.Code = in_c.CustCode "
				+ "	left join tCusttype in_g on in_g.Code = in_f.CustType "
				+ "	where not exists(select 1 from tItem in_d where in_d.isFee='1' and in_c.ItemCode=in_d.Code) and in_c.Qty<>0 and ( "
				+ "		in_e.ItemType12 = '2' or (in_e.ItemType12 = '3' and in_e.Code <> '0053') "
				+ "	) "
				+ "	group by in_a.NO "
				+ ")d on a.No=d.No "
				+ "left join tCusttype e on c.CustType=e.Code "
				+ "left join tItemType2 f on b.ItemType2=f.Code "
				+ "left join tItemType12 i on f.ItemType12=i.Code "
				+ "left join tLaborFeeType j on i.InstallFeeType=j.Code "
				+ "inner join ( "
				+ "	select max(PK) PK,CustCode,WorkType12 from tCustWorker a   "
				+ "	left join tWorkType12 b on b.Code=a.WorkType12  "
				+ "	where WorkType12 in('21','22') and a.EndDate is not null and ComeDate>? and ComeDate<dateadd(day,1,?) " 
				+ " and not exists(select 1 from tCustomer in_a where in_a.Code=CustCode and in_a.CustCheckDate >? and CustCheckDate<dateadd(day,1,?)) "
				+ "	group by CustCode,WorkType12  "
				+ ")k on k.custCode = b.custCode "
				+ "left join tCustWorker k2 on k.PK=k2.PK "
				+ "left join tWorker l on l.Code=k2.WorkerCode "
				+ "left join tWorkCard m on m.CardID=l.CardID "
				+ "left join tXTDM x1 on x1.ID = 'CheckStatus' and c.CheckStatus = x1.CBM "
				+ "left join tXTDM x2 on x2.ID = 'YESNO' and b.isSetItem = x2.CBM  "
				+ "where b.Type='S' and " //限制领料申请为送货 modify by zb on 20200225
				//原来灯具和辅灯都算了（根据材料分类=灯具），现在只能根据材料类型2=灯具（RZDJ）才算 modify by zb on 20200319
				+ "((f.ItemType12 ='2' and k.WorkType12='21')or(f.ItemType12 ='3' and k.WorkType12='22')) and d.amount>0 " 
				+ "and not exists ( "
				+ "select 1 from tLaborFeeDetail in_a left join tLaborfee in_b on in_b.no = in_a.no "
			    + "where exists ( "
			    + "		select 1 from tItemType12 in2_a where in2_a.Code in ('2', '3') and in2_a.InstallFeeType = in_a.FeeType "
			    + ") and in_a.custCode = b.custCode and in_b.status <> '5') ";
		list.add(laborFee.getDateFrom());
		list.add(laborFee.getDateTo());
		list.add(laborFee.getDateFrom());
		list.add(laborFee.getDateTo());
		if (StringUtils.isNotBlank(laborFee.getNos())) {
			sql += " and a.No not in  " + "('"
					+ laborFee.getNos().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> goCheckFeeJqGrid(Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select  a.CustCheckDate,b.CustCode,b.No iano,a.Address,a.DocumentNo,a.CheckStatus,c.ItemType12, round(isnull(d.LineAmount,0)-isnull(e.OutAmount,0), 2) amount,d2.appsendno, "
				+ "x1.NOTE CheckStatusDescr,l.CardID,case when m.ActName is null then l.NameChi else m.ActName end ActName, "
				+ "b.isSetItem,x2.NOTE isSetItemDescr,i.InstallFeeType feeType,n.Descr feeTypeDescr,k2.PK custWorkPk,k2.WorkerCode," +
				" i.Descr itemType12Descr,'结算调整' remarks,b.RefCustCode, rg.Descr RegionDescr  "
				+ "from tCustomer a  "
				+ "left join tBuilder bd on a.BuilderCode = bd.Code "
                + "left join tRegion rg on bd.RegionCode = rg.Code "
				+ "left join (" 
				+ " select max(No)No,CustCode from tItemApp in_a left join tItemType2 in_b on in_a.ItemType2=in_b.Code "
				+ " left join tCustomer in_c on in_c.Code = in_a.CustCode "
				+ "	left join tCusttype in_d on in_d.Code = in_c.CustType "
				+ " where in_b.ItemType12 = '2' or (in_b.ItemType12 = '3' and in_b.Code <> '0053') "
				+ " group by CustCode,in_b.ItemType12 " 
				+ ") ia on a.Code=ia.CustCode "
				+ "left join tItemApp b on ia.No=b.No "
				+ "left join tItemType2 c on b.ItemType2=c.Code "
				+ "left join ( "
				+ "	select in_a.CustCode,in_c.ItemType12,sum((LineAmount-DiscCost)*0.035) LineAmount,max(in_e.IANo)IANo "
				+ "	from tItemReq in_a "
				+ "	left join tItem in_b on in_a.ItemCode=in_b.Code "
				+ "	left join tItemType2 in_c on in_b.ItemType2=in_c.Code "
				+ "	inner join tItemAppDetail in_d on in_a.PK=in_d.ReqPK "
				+ "	inner join (select max(in_in_a.No)appsendno,in_in_a.IANo from tItemAppSend in_in_a " 
				+ " 	inner join tItemAppSendDetail in2_b on in2_b.No = in_in_a.No "
				+ "		inner join tItemAppDetail in2_c on in2_c.PK = in2_b.RefPk "
				+ " 	inner join tItemApp in2_d on in2_d.No = in2_c.No "
				+ " 	left join tItemType2 in2_e on in2_e.Code = in2_d.ItemType2 "
				+ " 	inner join tCustomer in2_f on in2_f.Code = in2_d.CustCode "
				+ " 	inner join tCusttype in2_g on in2_g.Code = in2_f.CustType "
				+ " 	where in2_e.ItemType12 = '2' or (in2_e.ItemType12 = '3' and in2_e.Code <> '0053') "
				+ " 	group by in_in_a.IANo "
				+ " )in_e on in_d.No=in_e.IANo "
				+ " left join tCustomer in_f on in_f.Code = in_a.CustCode "
				+ "	left join tCusttype in_g on in_g.Code = in_f.CustType "
				+ "	where in_b.isFee<>'1' and ("
				+ " 	in_c.ItemType12 = '2' or (in_c.ItemType12 = '3' and in_c.Code <> '0053') "
				+ " ) and exists(select 1 from tCustomer in_f where in_f.CustCheckDate>? and in_f.CustCheckDate<dateadd(day,1,?) and in_a.CustCode=in_f.Code) "
				+ "	group by in_a.CustCode,in_c.ItemType12 "
				+ ")d on b.CustCode=d.CustCode and c.ItemType12=d.ItemType12 and b.No=d.IANo "
				+ "left join (select max(in_a.No) appsendno,in_a.IANo from tItemAppSend in_a "
				+ " inner join tItemAppSendDetail in_b on in_b.No = in_a.No "
				+ " inner join tItemAppDetail in_c on in_c.PK = in_b.RefPk "
				+ "	inner join tItemApp in_d on in_d.No = in_c.No "
				+ "	left join tItemType2 in_e on in_e.Code = in_d.ItemType2 "
				+ "	inner join tCustomer in_f on in_f.Code = in_d.CustCode "
				+ "	inner join tCusttype in_g on in_g.Code = in_f.CustType "
				+ "	where in_e.ItemType12 = '2' or (in_e.ItemType12 = '3' and in_e.Code <> '0053') "
				+ "	group by in_a.IANo"
				+ ")d2 on d2.IANo=d.IANo "
				+ "left join ( "
				+ "	select sum(in_a.Amount)OutAmount,in_a.CustCode,in_d.ItemType12 "
				+ "	from tLaborFeeDetail in_a  "
				+ "	left join tItemAppSend in_b on in_a.AppSendNo=in_b.No "
				+ "	left join tItemApp in_c on in_b.IANo=in_c.No "
				+ "	left join tItemType2 in_d on in_c.ItemType2=in_d.Code "
				+ "	where in_d.ItemType12 in('2','3') and exists ( "
				+ "		select 1 from tItemType12 in2_a where in2_a.Code in ('2', '3') and in2_a.InstallFeeType = in_a.FeeType "
				+ "	) and exists(select 1 from tLaborFee in_e where in_e.No=in_a.No and in_e.Status in('1','3','4')) "
				+ "	 and exists(select 1 from tCustomer in_f where in_f.CustCheckDate>? and in_f.CustCheckDate<dateadd(day,1,?) and in_a.CustCode=in_f.Code) "
				+ "	group by in_a.CustCode,in_d.ItemType12 "
				+ ")e on b.CustCode=e.CustCode and c.ItemType12=e.ItemType12 "
				+ "inner join ( "
				+ "	select max(PK) PK,CustCode,WorkType12 from tCustWorker a   "
				+ "	left join tWorkType12 b on b.Code=a.WorkType12 "
				+ "	where WorkType12 in('21','22')  "
				+ "	group by CustCode,WorkType12  "
				+ ")k on k.custCode = b.custCode "
				+ "left join tItemType12 i on c.ItemType12=i.Code "
				+ "left join tCustWorker k2 on k.PK=k2.PK "
				+ "left join tWorker l on l.Code=k2.WorkerCode "
				+ "left join tWorkCard m on m.CardID=l.CardID "
				+ "left join tLaborFeeType n on i.InstallFeeType=n.Code "
				+ "left join tXTDM x1 on x1.ID = 'CheckStatus' and a.CheckStatus = x1.CBM "
				+ "left join tXTDM x2 on x2.ID = 'YESNO' and b.isSetItem = x2.CBM  "
				+ "where b.Type='S' and " //限制领料申请为送货 modify by zb on 20200225
				//原来灯具和辅灯都算了（根据材料分类=灯具），现在只能根据材料类型2=灯具（RZDJ）才算 modify by zb on 20200319
				+ "((c.ItemType12 ='2' and k.WorkType12='21')or(c.ItemType12 ='3' and k.WorkType12='22')) and a.CustCheckDate>? and a.CustCheckDate<dateadd(day,1,?) "
				+ " and isnull(d.LineAmount,0)<>isnull(e.OutAmount,0) ";
		list.add(laborFee.getDateFrom());
		list.add(laborFee.getDateTo());
		list.add(laborFee.getDateFrom());
		list.add(laborFee.getDateTo());
		list.add(laborFee.getDateFrom());
		list.add(laborFee.getDateTo());
		if (StringUtils.isNotBlank(laborFee.getCustCodes())) {
			sql += " and d2.appsendno not in  " + "('"
					+ laborFee.getCustCodes().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> getTransFeeList(LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select b.No iano,a.No appsendno,b.custcode,c.custcheckdate,c.address,e.Desc1 custtypedescr,f.Descr itemtype2descr,isnull(h.SoftTransFee,0)/j.noNum amount, "
				+ "c.documentno,c.checkstatus,x1.NOTE checkstatusdescr,l.cardid,case when m.ActName is null then l.NameChi else m.ActName end actname, "
				+ "b.issetitem,x2.NOTE issetitemdescr,i.TransFeeType feetype,n.Descr feetypedescr,k2.PK custworkpk,k2.workercode,b.refcustcode, rg.Descr regiondescr  "
				+ "from tItemAppSend a  "
				+ "left join tItemApp b on a.IANo=b.No "
				+ "left join tCustomer c on b.CustCode=c.Code "
				+ "left join tBuilder bd on c.BuilderCode = bd.Code "
                + "left join tRegion rg on bd.RegionCode = rg.Code "
				+ "left join tCusttype e on c.CustType=e.Code "
				+ "left join tItemType2 f on b.ItemType2=f.Code "
				+ "left join tBuilder g on c.BuilderCode=g.Code "
				+ "left join tSendRegion h on g.SendRegion=h.No "
				+ "left join tItemType12 i on f.ItemType12=i.Code "
				+ "left join tLaborFeeType n on i.TransFeeType=n.Code "
				+ "inner join ( "
				+ "	select max(PK) PK,CustCode,WorkType12 from tCustWorker a   "
				+ "	left join tWorkType12 b on b.Code=a.WorkType12  "
				+ "	where WorkType12 in('21','22','23')  "
				+ "	group by CustCode,WorkType12 "
				+ ")k on k.custCode = b.custCode "
				+ "left join ( "
				+ "	select count(a.No)noNum,b.CustCode  "
				+ "	from tItemAppSend a  "
				+ "	left join tItemApp b on a.IANo=b.No "
				+ "	left join tCustomer c on b.CustCode=c.Code "
				+ "	left join tItemType2 f on b.ItemType2=f.Code "
				+ " inner join ( "
				+ "	  select max(PK) PK,CustCode,WorkType12 from tCustWorker a   "
				+ "	  left join tWorkType12 b on b.Code=a.WorkType12  "
				+ "	  where WorkType12 in('21','22','23')  "
				+ "	  group by CustCode,WorkType12 "
				+ " )in_k on in_k.custCode = b.custCode "
				+ "	where ((f.ItemType12 ='2' and in_k.WorkType12='21')or(f.ItemType12 ='3' and in_k.WorkType12='22') or(f.ItemType12 ='1' and in_k.WorkType12='23' )) "
				+ " and CustCheckDate>=? and CustCheckDate<dateadd(day,1,?) "
				+ "	group by b.CustCode "
				+ ")j on b.CustCode=j.CustCode "
				+ "left join tCustWorker k2 on k.PK=k2.PK "
				+ "left join tWorker l on l.Code=k2.WorkerCode "
				+ "left join tWorkCard m on m.CardID=l.CardID "
				+ "left join tXTDM x1 on x1.ID = 'CheckStatus' and c.CheckStatus = x1.CBM "
				+ "left join tXTDM x2 on x2.ID = 'YESNO' and b.isSetItem = x2.CBM  "
				+ "where ((f.ItemType12 ='2' and k.WorkType12='21')or(f.ItemType12 ='3' and k.WorkType12='22') or(f.ItemType12 ='1' and k.WorkType12='23' )) and c.CustCheckDate>=? and  c.CustCheckDate<dateadd(day,1,?) "
				+ "	and not exists (  "
				+ "		select 1 from tLaborFeeDetail in_a left join tLaborfee in_b on in_b.no = in_a.no  "
				+ "		where in_a.feeType in ('63','64','65') and in_a.custCode = b.custCode and in_b.status <> '5' "
				+ "	)";
		list.add(laborFee.getDateFrom());
		list.add(laborFee.getDateTo());
		list.add(laborFee.getDateFrom());
		list.add(laborFee.getDateTo());
		if (StringUtils.isNotBlank(laborFee.getCustCodes())) {
			sql += " and b.CustCode in  " + "('"
					+ laborFee.getCustCodes().replaceAll(",", "','") + "')";
		}
		return this.findListBySql(sql, list.toArray());
	}
	
	public Page<Map<String, Object>> goWhInstallFeeJqGrid(Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.no appsendno,c.Address,c.DocumentNo,a.IANo ,isnull(d.amount,0)amount,isnull(h.hadamount,0)hadamount,  "
				+ " f.code feeType,f.Descr feeTypeDescr,b.CustCode,c.checkstatus,x1.NOTE checkstatusdescr,c.custcheckdate, b.RefCustCode, rg.descr RegionDescr  "
				+ " from tItemAppSend a "
				+ " left join tItemApp b on a.IANo=b.No  "
				+ " left join tCustomer c on c.code=b.CustCode "
				+ " left join tBuilder bd on c.BuilderCode = bd.Code "
                + " left join tRegion rg on bd.RegionCode = rg.Code "
				+ " left join ( select in_a.No,"
				+ " sum(case when in_d.ItemType12 in ('11','12') then  in_a.SendQty*In_c.PerWeight/1000*20 "
				+ " when in_c.ItemType3 in ('"+laborFee.getToilet().replace(",", "','")+"') " 
				+ "		or in_c.ItemType3 in ('"+laborFee.getCabinet().replace(",", "','")+"') " 
				+ "		or in_d.ItemType12='18' and in_c.ItemType2<>'041' then in_a.SendQty*2 " 
				+ " when in_c.itemtype3 in ('"+laborFee.getBathSet().replace(",", "','")+"') "
				+ " then in_a.sendqty* 4 "
				+ " end) amount "
				+ " from tItemAppSendDetail in_a " 
				+ " inner join tItemAppDetail in_b on in_a.RefPk=in_b.PK  "
				+ " inner join tItem in_c on in_c.Code=in_b.ItemCode  "
				+ " inner join tItemType2 in_d on in_d.Code=in_c.ItemType2 "
				+ " group by in_a.No "
				+ " )d on d.no=a.No  "
				+ " left outer join (select b.CustCode,sum(b.Amount) HadAmount" 
				+ " from tLaborFee a " 
				+ " inner join tLaborFeeDetail b on a.No = b.No" 
				+ " where a.Status in ('3','4') and FeeType='04' " 
				+ " group by b.CustCode "
				+ " )h on b.CustCode =h.CustCode " 
				+ " left join tXTDM x1 on x1.ID='CheckStatus' and c.CheckStatus =x1.CBM "
				+ " left join tLaborFeeType f on f.code='04' "
				+ " where b.ItemType1='zc' and b.delivType in('1','3','4')  "
				+ " and not exists(select 1 from tLaborFeeDetail in_a"
				+ "	left join tLaborfee in_b on in_b.no = in_a.no where a.No=in_a.AppSendNo and in_a.No<>? and in_a.FeeType='04' and in_b.status<>'5') "
				+ " and b.Status='SEND' and b.senddate>=? and b.senddate<?  ";
		list.add(laborFee.getNo());		
		list.add(laborFee.getDateFrom());
		list.add(DateUtil.addDateOneDay(laborFee.getDateTo()));
		if (StringUtils.isNotBlank(laborFee.getNos())) {
			sql += " and a.No not in  " + "('"
					+ laborFee.getNos().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a  where a.amount<>0 order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a where a.amount<>0 ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findIntQtyPageBySql(Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.CustCode ,e.Address,f.desc1 custtypedescr,sird.qty,k.Descr itemType3Descr,k.IntInstallFee,n.AppQty "
				+ "from tSpecItemReq a "
				+ "left join tCustomer e on e.Code = a.CustCode "
				+ "left join tCusttype f on f.Code = e.CustType "
				+ "inner join( "
				+ " select max(PK) pk,CustCode "
				+ " from tCustWorker a "
				+ " left join tWorkType12 b on b.Code = a.WorkType12 "
				+ " where WorkType12 = '17' "
				+ " group by CustCode "
				+ ")i on i.custCode = a.custCode "
				+ "left join tCustWorker j on j.pk = i.pk "
				+ "left join ( "
				+ " select sum(isnull(case when ia.type='s' then a.sendqty else (-1.0)* a.sendqty end,0)) Qty,b.ItemType3,CustCode "
				+ " from tItemAppDetail a " 
				+ " left join tItemApp ia on ia.no=a.no "
				+ " left join tItem b on b.Code = a.ItemCode "
				+ " left join tItemType3 c on c.Code=b.ItemType3 "
				+ " where ia.IsCupboard='0' and ia.ItemType1 ='JC' and a.SendQty<>0 and c.IntInstallFee<>0  " 
				+ " group by CustCode,b.ItemType3 " 
				+ ") sird on sird.CustCode = a.CustCode "
				+ "left join tItemType3 k on sird.ItemType3=k.Code "
				+ "left join ( "
				+ "	select c.ItemType3,b.CustCode,sum(case when b.Type = 'S' and b.Status not in ('CANCEL','RETURN') then a.Qty when b.Type = 'R' and b.Status = 'RETURN'  then -a.Qty else 0 end ) AppQty "
				+ "	from tItemAppDetail a  "
				+ "	left join tItemApp b on b.No = a.No "
				+ "	left join tItem c on a.ItemCode=c.Code "
				+ "	where a.SpecReqPk > -1 and b.ItemType1='JC' and b.IsCupboard='0' "
				+ "	group by b.CustCode,c.ItemType3 "
				+ ")n on a.CustCode=n.CustCode and k.Code=n.ItemType3 "
				+ "where k.IntInstallFee<>0 and not exists ( select * from tLaborFeeDetail in_a "
				+ " left join tLaborfee in_b on in_b.no = in_a.no "
				+ " where  in_a.feeType = 'jcazf' and in_a.custCode = a.custCode and in_b.status <> '5' ";
		if (StringUtils.isNotBlank(laborFee.getNo())) {
			sql += "and in_a.no<>? ";
			list.add(laborFee.getNo());
		}
		sql += ") and j.endDate is not null ";
		if(StringUtils.isNotBlank(laborFee.getWorkerCode())){
			sql+=" and j.WorkerCode = ? ";
			list.add(laborFee.getWorkerCode());
		}
		if (laborFee.getDateFrom() != null) {
			sql += " and j.endDate> ? ";
			list.add(laborFee.getDateFrom());
		}

		if (laborFee.getDateTo() != null) {
			sql += " and j.endDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(laborFee.getDateTo())
					.getTime()));
		}
		if (StringUtils.isNotBlank(laborFee.getNos())) {
			sql += " and a.custcode not in  " + "('"
					+ laborFee.getNos().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.CustCode ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> goTileCutFeeJqGrid(Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( select a.No CutCheckOutNo,a.IANo,b.CustCode,c.Address,b.ConfirmDate,isnull(sum(a.Qty*a.CutFee), 0) Amount,c.DocumentNo,  "
			+"min(e.No) AppSendNo,isnull(sum(f.hadamount),0)hadamount,isnull(sum(h.SendNoHaveAmount), 0) SendNoHaveAmount,i.Descr FeeTypeDescr,  "
			+"i.code FeeType,c.CheckStatus,x1.NOTE CheckStatusDescr,c.CustCheckDate ,g.CompleteDate, rg.Descr RegionDescr  "
			+" ,b.RefCustCode from  tCutCheckOutDetail a   "
			+"inner join tCutCheckOut g on a.No=g.No  "
			+"left join tItemApp b on a.IANo = b.No   "
			+"left join tCustomer c on b.CustCode = c.Code   "
			+"left join tCutFeeSet d on a.CutType = d.CutFee   "
			+"left join (   "
			+"    select in_a.RefPk, min(in_a.No) No "
            +"    from tItemAppSendDetail in_a "
            +"    group by in_a.RefPk "
            +") e on e.RefPk = a.RefPK "
			+"left join (   "
			+"	select b.CustCode,sum(b.Amount) HadAmount   "
			+"	from tLaborFee a    "
			+"	inner join tLaborFeeDetail b on a.No = b.No   "
			+"	where a.Status in ('3','4') and FeeType='10'   " 
			+"	group by b.CustCode    "
			+")f on b.CustCode =f.CustCode   "
			+"left join (   "
			+"	select b.AppSendNo, sum(b.Amount) SendNoHaveAmount   "
			+"	from   tLaborFee a   "
			+"	inner join tLaborFeeDetail b on a.No = b.No   "
			+"	where  a.Status in ('3', '4') and b.AppSendNo<>'' and b.FeeType = '10'   "
			+"	group by b.AppSendNo   "
			+") h on h.AppSendNo = e.No   "
			+"left join tLaborFeeType i on i.code='10'   "
			+"left join tXTDM x1 on x1.ID='CheckStatus' and c.CheckStatus =x1.CBM   "
			+"left join tBuilder bd on c.BuilderCode = bd.Code "
            +"left join tRegion rg on bd.RegionCode = rg.Code "
			+"where b.Status in ('SEND', 'CONFIRMED') and g.CompleteDate is not null  "
			+"and not exists(select 1 from tLaborFeeDetail in_a left join tLaborFee in_b on in_a.No = in_b.No where in_b.Status <> '5' and in_a.CutCheckOutNo = a.No and in_a.AppSendNo=e.No)  ";
			
		if (laborFee.getDateFrom() != null) {
			sql += " and g.CompleteDate > ? ";
			list.add(laborFee.getDateFrom());
		}

		if (laborFee.getDateTo() != null) {
			sql += " and g.CompleteDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(laborFee.getDateTo())
					.getTime()));
		}
		if (StringUtils.isNotBlank(laborFee.getNos())) {
			sql += " and rtrim(a.No) + '_' + rtrim(a.IANo) not in (" +SqlUtil.resetStatus(laborFee.getNos()) + ")";
		}
		if (StringUtils.isNotBlank(laborFee.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+laborFee.getNo()+"%");
		}
		sql+="group by a.No,a.IANo,b.CustCode,c.Address,rg.Descr,c.CheckStatus,i.code,i.Descr,c.CheckStatus,x1.NOTE,c.CustCheckDate," +
				" b.RefCustCode,b.ConfirmDate ,c.DocumentNo,g.CompleteDate";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a where a.amount<>0 and a.AppSendNo is not null order by a." 
			        + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a where a.amount<>0 and a.AppSendNo is not null ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public List<Map<String, Object>> getLaborFeeDetail(String no){
		
		String sql=" select b.Descr,Sum(a.Amount) Amount,b.Code from tLaborFeeDetail a" +
				" left join tLaborFeeType b on b.Code = a.FeeType" +
				" where a.No = ? " +
				" group by b.Descr,b.Code ";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{no});
		
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public Double getAmountByNo(String no){
		
		String sql=" select Sum(a.Amount) Amount from tLaborFeeDetail a" +
				" where a.No = ? ";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{no});
		
		if(list!=null && list.size()>0){
			if(list.get(0).get("Amount") != null && list.get(0).get("Amount") != ""){
				return Double.parseDouble(list.get(0).get("Amount").toString());
			}
		}
		return 0.0;
	}

	public Page<Map<String, Object>> findGoodPrjPageBySql(
			Page<Map<String, Object>> page, LaborFee laborFee) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.CustCode,b.Address,c.Desc1 CustTypeDescr,b.Area, "+
					 " '衣柜补贴' feeTypeDescr,a.PrjItem,'JCAZBT' feeType,1.5 qtyfee, "+
					 " sird.sendqty number,isnull(sird.SendQty*1.5,0) amount,g.CardID, "+
					 " isnull(h.ActName, g.nameChi) ActName,rg.Descr RegionDescr, "+
					 " x1.Note CheckStatusDescr,b.documentNo "+
					 " from tPrjProgConfirm a "+
					 " left join tCustomer b on b.Code = a.CustCode "+
					 " left join tCusttype c on c.Code = b.CustType "+
					 " left join tSpecItemReq sir on a.CustCode = sir.CustCode "+
					 " left join ( "+ 
					 " 		select sum(case when isnull(c.IntInstallFee, 0) > 0 then 1 else 0 end * isnull(case when ia.type='s' then a.sendqty else (-1.0)* a.sendqty end,0)) sendqty,CustCode "+
					 " 		from tItemAppDetail a "+ 
					 " 		left join tItemApp ia on ia.no=a.no "+
					 "		left join tItem b on b.Code = a.ItemCode "+
					 "		left join tItemType3 c on c.Code = b.ItemType3 	"+
					 " 		where ia.IsCupboard='0' and ia.ItemType1 ='JC' and a.SendQty<>0 "+  
					 " 		group by CustCode "+
					 " )sird on sird.CustCode = sir.CustCode "+
					 " inner join ( "+
					 " 		select max(PK) pk,CustCode from tCustWorker a "+ 
					 " 		left join tWorkType12 b on b.Code=a.WorkType12 "+
					 " 		where WorkType12 = '17' group by CustCode "+
					 " ) i on i.custCode = a.custCode "+
					 " left join tCustWorker j on j.pk=i.pk "+
					 " left join tworker g on g.code=j.WorkerCode "+
					 " left join tWorkCard h on h.CardID = g.CardID "+
					 " left join tBuilder bd on b.BuilderCode = bd.Code "+ 
					 " left join tRegion rg on bd.RegionCode = rg.Code "+
					 " left join tXTDM x1 on x1.CBM = b.CheckStatus and x1.id = 'CheckStatus' "+ 
					 " left join (select * from dbo.fStrToTable('"+laborFee.getNos()+"',',')) d on a.CustCode = d.item "+
					 " where a.IsPass = '1' and a.prjLevel = '2' and a.PrjItem = '17' and isnull(sird.sendqty,0)<>0 ";
			if(laborFee.getDateFrom() !=null){
				sql+=" and a.Date> ? ";
					list.add(laborFee.getDateFrom());
			}
			if(laborFee.getDateTo() !=null){
				sql+=" and a.Date <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(laborFee.getDateTo()).getTime()));
			}
			if(StringUtils.isNotBlank(laborFee.getWorkerCode())){
				sql+=" and a.WorkerCode = ? ";
				list.add(laborFee.getWorkerCode());
			}
			if(StringUtils.isNotBlank(laborFee.getJcazbtCustCodes())){
				sql += " and a.custcode not in  "+ "('"+ laborFee.getJcazbtCustCodes().replaceAll(",", "','") + "') ";
			}
				sql+=" and not exists( "+
					 " select * from tLaborFee in_a "+
					 " left join tLaborFeeDetail in_b on in_a.No = in_b.No "+
					 " where in_b.CustCode = a.CustCode and in_a.Status <> '5' and in_b.FeeType='JCAZBT' and a.PrjItem='17' ";
			if(StringUtils.isNotBlank(laborFee.getNo())){
				sql+=" and in_a.no<>? ";
				list.add(laborFee.getNo());
		    }
				sql+=" ) "+
					 " and (exists( "+
					 " select * from tLaborFeeDetail in_a "+
					 " left join tLaborFee in_c on in_c.No = in_a.No "+
					 " where in_a.CustCode = a.CustCode and in_c.Status in ('1','2','3','4') "+
					 " and in_a.FeeType = 'JCAZF' and a.PrjItem = '17'" +
					 " ) or d.item is not null) "+
					 " union all "+
					 " select a.CustCode,b.Address,c.Desc1 CustTypeDescr,b.Area, "+
					 " '衣柜经费' feeTypeDescr,a.PrjItem,'JCAZJF' feeType,0.5 qtyfee, "+
					 " sird.sendqty number,isnull(sird.sendqty*0.5,0) amount, "+
					 " '' CardID,'' ActName,rg.Descr RegionDescr,x1.Note CheckStatusDescr,b.documentNo "+
					 " from tPrjProgConfirm a "+
					 " left join tCustomer b on b.Code = a.CustCode "+
					 " left join tCusttype c on c.Code = b.CustType "+
					 " left join tSpecItemReq sir on a.CustCode = sir.CustCode "+
					 " left join ( "+ 
					 " select sum(case when isnull(c.IntInstallFee, 0) > 0 then 1 else 0 end * isnull(case when ia.type='s' then a.sendqty else (-1.0)* a.sendqty end,0)) sendqty,CustCode "+
					 " from tItemAppDetail a "+ 
					 " left join tItemApp ia on ia.no=a.no "+
					 " left join tItem b on b.Code = a.ItemCode "+
					 " left join tItemType3 c on c.Code = b.ItemType3 	"+
					 " where ia.IsCupboard='0' and ia.ItemType1 ='JC' and a.SendQty<>0 "+  
					 " group by CustCode "+
					 " )sird on sird.CustCode = sir.CustCode "+
					 " left join tBuilder bd on b.BuilderCode = bd.Code "+ 
					 " left join tRegion rg on bd.RegionCode = rg.Code "+
					 " left join tXTDM x1 on x1.CBM = b.CheckStatus and x1.id = 'CheckStatus' "+
					 " left join (select * from dbo.fStrToTable('"+laborFee.getNos()+"',',')) d on a.CustCode = d.item "+
					 " where a.IsPass = '1' and a.prjLevel = '2' and a.PrjItem = '17' and isnull(sird.sendqty,0)<>0 ";
			if(laborFee.getDateFrom() !=null){
				sql+=" and a.Date> ? ";
					list.add(laborFee.getDateFrom());
			}
			if(laborFee.getDateTo() !=null){
				sql+=" and a.Date <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(laborFee.getDateTo()).getTime()));
			}
			if(StringUtils.isNotBlank(laborFee.getWorkerCode())){
				sql+=" and a.WorkerCode = ? ";
				list.add(laborFee.getWorkerCode());
			}
			if(StringUtils.isNotBlank(laborFee.getJcazjfCustCodes())){
				sql += " and a.custcode not in  "+ "('"+ laborFee.getJcazjfCustCodes().replaceAll(",", "','") + "') ";
			}
				sql+=" and not exists( "+
					 " select * from tLaborFee in_a "+
					 " left join tLaborFeeDetail in_b on in_a.No = in_b.No "+
					 " where in_b.CustCode = a.CustCode and in_a.Status <> '5' and in_b.FeeType='JCAZJF' and a.PrjItem='17' ";
			if(StringUtils.isNotBlank(laborFee.getNo())){
				sql+=" and in_a.no<>? ";
				list.add(laborFee.getNo());
		    }
				sql+=" ) "+
					 " and( exists( "+
					 " 		select * from tLaborFeeDetail in_a "+
					 " 		left join tLaborFee in_c on in_c.No = in_a.No "+
					 " 		where in_a.CustCode = a.CustCode and in_c.Status in ('1','2','3','4') "+
					 " 		and in_a.FeeType = 'JCAZF' and a.PrjItem = '17' "+
					 " ) or d.item is not null) "+
					 " union all "+
					 " select a.CustCode,b.Address,c.Desc1 CustTypeDescr,b.Area, "+
					 " '橱柜补贴' feeTypeDescr,a.PrjItem,'CGAZBT' feeType,8 qtyfee, "+
					 " isnull(d2.CupDownHigh,0)+isnull(d2.CupUpHigh,0) number, "+
					 " isnull((isnull(d2.CupDownHigh,0)+isnull(d2.CupUpHigh,0))*8.0,0) amount, "+
					 " e2.CardID,isnull(f2.ActName, e2.nameChi) ActName,rg.Descr RegionDescr, "+
					 " x1.Note CheckStatusDescr,b.documentNo "+
					 " from tPrjProgConfirm a "+
					 " left join tCustomer b on b.Code = a.CustCode "+
					 " left join tCusttype c on c.Code = b.CustType "+
					 " left join tSpecItemReq sir on a.CustCode = sir.CustCode "+
					 " left join tCupMeasure d2 on d2.CustCode = a.CustCode "+
					 " left join tWorker e2 on e2.Code = d2.WorkerCode "+
					 " left join tWorkCard f2 on f2.CardID = e2.CardID "+ 
					 " left join tBuilder bd on b.BuilderCode = bd.Code "+ 
					 " left join tRegion rg on bd.RegionCode = rg.Code "+
					 " left join tXTDM x1 on x1.CBM = b.CheckStatus and x1.id = 'CheckStatus' "+ 
					 " left join (select * from dbo.fStrToTable('"+laborFee.getCustCodes()+"',',')) d on a.CustCode = d.item "+
					 " where a.IsPass = '1' and a.prjLevel = '2' and a.PrjItem = '26' and d2.IsCupboard = '1' and d2.Status = '2' "+
					 " and (isnull(d2.CupDownHigh,0)+isnull(d2.CupUpHigh,0))<>0 ";
			if(laborFee.getDateFrom() !=null){
				sql+=" and a.Date> ? ";
				list.add(laborFee.getDateFrom());
			}
			if(laborFee.getDateTo() !=null){
				sql+=" and a.Date <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(laborFee.getDateTo()).getTime()));
			}
			if(StringUtils.isNotBlank(laborFee.getWorkerCode())){
				sql+=" and a.WorkerCode = ? ";
				list.add(laborFee.getWorkerCode());
			}
			if(StringUtils.isNotBlank(laborFee.getCgazbtCustCodes())){
				sql += " and a.custcode not in  "+ "('"+ laborFee.getCgazbtCustCodes().replaceAll(",", "','") + "') ";
			}
				sql+=" and not exists( "+
					 " select * from tLaborFee in_a "+
					 " left join tLaborFeeDetail in_b on in_a.No = in_b.No "+
					 " where in_b.CustCode = a.CustCode and in_a.Status <> '5' and in_b.FeeType='CGAZBT' and a.PrjItem='26' ";
			if(StringUtils.isNotBlank(laborFee.getNo())){
				sql+=" and in_a.no<>? ";
				list.add(laborFee.getNo());
		    }
				sql+=" ) "+
					 " and (exists( "+
					 " select * from tLaborFeeDetail in_a "+
					 " left join tLaborFee in_c on in_c.No = in_a.No "+
					 " where in_a.CustCode = a.CustCode and in_c.Status in ('1','2','3','4') "+
					 " and in_a.FeeType = 'CGAZF' and a.PrjItem = '26' "+
					 " ) or d.item is not null) "+
					 " union all "+
					 " select a.CustCode,b.Address,c.Desc1 CustTypeDescr,b.Area, "+
					 " '橱柜经费' feeTypeDescr,a.PrjItem,'CGAZJF' feeType,2 qtyfee, "+
					 " isnull(d2.CupDownHigh,0)+isnull(d2.CupUpHigh,0) number, "+
					 " isnull((isnull(d2.CupDownHigh,0)+isnull(d2.CupUpHigh,0))*2.0,0) amount, "+
					 " '' CardID,'' ActName,rg.Descr RegionDescr,x1.Note CheckStatusDescr,b.documentNo "+
					 " from tPrjProgConfirm a "+
					 " left join tCustomer b on b.Code = a.CustCode "+
					 " left join tCusttype c on c.Code = b.CustType "+
					 " left join tCupMeasure d2 on d2.CustCode = a.CustCode "+
					 " left join tBuilder bd on b.BuilderCode = bd.Code "+ 
					 " left join tRegion rg on bd.RegionCode = rg.Code "+
					 " left join tXTDM x1 on x1.CBM = b.CheckStatus and x1.id = 'CheckStatus' "+
					 " left join (select * from dbo.fStrToTable('"+laborFee.getCustCodes()+"',',')) d on a.CustCode = d.item "+
					 " where a.IsPass = '1' and a.prjLevel = '2' and a.PrjItem = '26' and d2.IsCupboard = '1' and d2.Status = '2' "+
					 " and (isnull(d2.CupDownHigh,0)+isnull(d2.CupUpHigh,0))<>0 ";
			if(laborFee.getDateFrom() !=null){
				sql+=" and a.Date> ? ";
					list.add(laborFee.getDateFrom());
			}
			if(laborFee.getDateTo() !=null){
				sql+=" and a.Date <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(laborFee.getDateTo()).getTime()));
			}
			if(StringUtils.isNotBlank(laborFee.getWorkerCode())){
				sql+=" and a.WorkerCode = ? ";
				list.add(laborFee.getWorkerCode());
			}
			if(StringUtils.isNotBlank(laborFee.getCgazjfCustCodes())){
				sql += " and a.custcode not in  "+ "('"+ laborFee.getCgazjfCustCodes().replaceAll(",", "','") + "') ";
			}
				sql+=" and not exists( "+
					 " select * from tLaborFee in_a "+
					 " left join tLaborFeeDetail in_b on in_a.No = in_b.No "+
					 " where in_b.CustCode = a.CustCode and in_a.Status <> '5' and in_b.FeeType='CGAZJF' and a.PrjItem='26' ";
			if(StringUtils.isNotBlank(laborFee.getNo())){
				sql+=" and in_a.no<>? ";
				list.add(laborFee.getNo());
		    }
				sql+=" ) "+
					 " and (exists( "+
					 " select * from tLaborFeeDetail in_a "+
					 " left join tLaborFee in_c on in_c.No = in_a.No "+
					 " where in_a.CustCode = a.CustCode and in_c.Status in ('1','2','3','4') "+
					 " and in_a.FeeType = 'CGAZF' and a.PrjItem = '26' "+
					 " ) or d.item is not null) ";
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += ") a order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += ") a order by a.custcode";
			}
		return this.findPageBySql(page, sql, list.toArray());
	}
}
