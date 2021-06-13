package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class WaterCtrlAnalysisDao extends BaseDao{
	
	/**
	 * 水电发包分析
	 * */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.code,a.address,g.Desc1 custtypedescr, a.Area*g.InnerAreaPer innerarea,case when a.InnerArea<>0 then a.InnerArea else a.Area*g.InnerAreaPer end  realInnerArea, "
				+ "a.confirmbegin,e.NameChi workername,isnull(round(b.workerplanoffer,0),0)workerplanoffer,isnull(round(h.AppAmount,0),0)AppAmount ,isnull(round(h.ConfirmAmount,0),0) ConfirmAmount,"
				+ "isnull(round(j.fixAmount,0),0)fixAmount,isnull(round(h.ConfirmAmount,0),0)+isnull(round(j.fixAmount,0),0) realAmount,isnull(round(b.workerPlanMaterial,0),0)workerPlanMaterial,"
				+ "isnull(round(ClAmount,0),0)clamount,isnull(round(workerplanmaterial,0),0)-isnull(round(ClAmount,0),0) clamountjc,isnull(round(i.ConfirmAmount,0),0)cfmclamountjc ," 
				+ "isnull(round(projectPlanMaterial,0),0)projectPlanMaterial, isnull(round(f.ActualOtherCost, 2), 0) ActualOtherCost, "
				+ "case when a.InnerArea <> 0 then round((f.ClAmount + f.ActualOtherCost) / a.InnerArea, 2) "
				+ "     when a.Area * g.InnerAreaPer <> 0 then round((f.ClAmount + f.ActualOtherCost) / (a.Area * g.InnerAreaPer), 2) "
				+ "     else 0 end dfcb "
				+ "from tCustomer a "
				+ "left join ( select CustCode,sum(a.offerpri*qty) workerPlanOffer, "
				+ "sum(a.Material*qty) workerPlanMaterial,sum(a.PrjMaterial*qty) projectPlanMaterial  "
				+ "from tBaseCheckItemPlan a,tBaseCheckItem b where a.BaseCheckItemCode=b.Code AND b.WorkType12='02' group by Custcode) b on b.custcode=a.code "
				+ "left join ( select CustCode,WorkType12,max(pk) CustWkpk from tCustWorker group by CustCode,WorkType12 ) c on a.code=c.CustCode and c.WorkType12='02' "
				+ "left join tCustWorker d on c.CustWkpk=d.pk "
				+ "left join tWorker e on d.WorkerCode=e.code "
				+ "left join ( "
				+ "    select custcode, "
				+ "        sum(round(case when ia.Type = 'S' then 1 else -1 end * ia.ProjectAmount,0)) ClAmount, "
				+ "        sum(ia.OtherCost + ia.OtherCostAdj + ia.ProjectOtherCost) ActualOtherCost "
				+ "    from tItemApp ia "
				+ "    left join tItemType2 it2 on it2.Code=ia.ItemType2 "
				+ "    where ia.Status in ('SEND','RETURN','CONFIRMED') "
				+ "	       and ia.ItemType1='JZ' and it2.MaterWorkType2='007' "
				+ "    GROUP BY custcode "
				+ ") f ON a.code=f.custcode "
				+ "left join tCusttype g on a.custType=g.Code "
				+ "left join ( select CustCode,isnull(sum(ConfirmAmount),0) ConfirmAmount,isnull(sum(AppAmount),0) AppAmount "
				+ "    from tWorkCostDetail wcd "
				+ "    inner join tWorkCost wc on wc.No=wcd.No "
				+ "    left outer join tSalaryType st on st.code=wcd.SalaryType "
				+ "    where  wcd.Status='2' and wcd.IsCalProjectCost='1' "
				+ "    and wcd.WorkType2='008'  "
				+ "    group by CustCode) h on a.Code=h.CustCode "
				+ "inner join ( select CustCode,isnull(sum(ConfirmAmount),0) ConfirmAmount,isnull(sum(AppAmount),0) AppAmount "// 实发水电材料奖惩
				+ "    from tWorkCostDetail wcd "
				+ "    inner join tWorkCost wc on wc.No=wcd.No "
				+ "    where  wcd.Status='2' " 
				+ "    and wcd.WorkType2='024'  "
				+ "    group by CustCode) i on a.Code=i.CustCode    "
				+ "left join (select sum(fdm.Amount)fixAmount,fd.CustCode from tFixDutyMan fdm " 
				+ "   inner join tFixDuty fd on fdm.No=fd.No " 
				+ "   left join tCustWorker cw on fd.CustWkPk=cw.PK "
				+ "   where fdm.FaultType<>'2' and cw.WorkType12='02' group by fd.CustCode)j on a.Code=j.CustCode "
				+ "where a.IsWaterItemCtrl = '1' and a.status in ('4','5') and h.ConfirmAmount<>0  and isnull(workerPlanMaterial,0)<>0 ";
		
		if (customer.getEndDateFrom()!=null) {
			sql += " and d.EndDate>= ? ";
			list.add(customer.getEndDateFrom());
		}
		if (customer.getEndDateTo()!=null) {
			sql += " and d.EndDate< dateadd(day,1,?) ";
			list.add(customer.getEndDateTo());
		}
		if (customer.getSendDateFrom()!=null) {
			sql += " and exists(select 1 from tWorkCostDetail wcd inner join tWorkCost wc on wcd.No=wc.No " +
					"where wcd.CustCode=a.Code and wcd.Status='2' and wcd.IsCalProjectCost='1' " +
					"and wcd.WorkType2='008' and wc.PayDate>=?)";
			list.add(customer.getSendDateFrom());
		}
		if (customer.getSendDateTo()!=null) {
			sql += " and exists(select 1 from tWorkCostDetail wcd inner join tWorkCost wc on wcd.No=wc.No " +
					"where wcd.CustCode=a.Code and wcd.Status='2' and wcd.IsCalProjectCost='1' " +
					"and wcd.WorkType2='008' and wc.PayDate<dateadd(day,1,?))";
			list.add(customer.getSendDateTo());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.CustType in " + "('"
					+ customer.getCustType().replace(",", "','") + "')";
		}
		if (customer.getAreaFrom()!=null) {
			sql += " and (case when a.InnerArea<>0 then a.InnerArea else a.Area*g.InnerAreaPer end) >=? " ;
			list.add(customer.getAreaFrom());
		}
		if (customer.getAreaTo()!=null) {
			sql += " and (case when a.InnerArea<>0 then a.InnerArea else a.Area*g.InnerAreaPer end) <=? " ;
			list.add(customer.getAreaTo());
		}
		if (StringUtils.isNotBlank(customer.getWorkerCode())) {
			sql += " and d.WorkerCode =? " ;
			list.add(customer.getWorkerCode());
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? " ;
			list.add("%"+customer.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.Code";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 水电发包分析按工人汇总
	 * */
	public Page<Map<String, Object>> findPageBySql_groupbyWorke(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select d.WorkerCode,e.NameChi workername, sum(isnull(a.Area*g.InnerAreaPer,0))innerarea, sum(isnull(round(realinnerarea, 0), 0)) realInnerArea, "
				+ " sum(isnull(round(b.workerplanoffer,0),0))workerplanoffer,sum(isnull(round(h.AppAmount,0),0))AppAmount ,sum(isnull(round(h.ConfirmAmount,0),0)) ConfirmAmount,"
				+ " sum(isnull(round(j.fixAmount,0),0))fixAmount,sum(isnull(round(h.ConfirmAmount,0),0)+isnull(round(j.fixAmount,0),0)) realAmount,sum(isnull(round(b.workerPlanMaterial,0),0))workerPlanMaterial,"
				+ " sum(isnull(round(ClAmount,0),0))clamount,sum(isnull(round(workerplanmaterial,0),0)-isnull(round(ClAmount,0),0)) clamountjc,sum(isnull(round(i.ConfirmAmount,0),0))cfmclamountjc ," 
				+ " sum(isnull(round(projectPlanMaterial,0),0))projectPlanMaterial, isnull(round(sum(f.ActualOtherCost), 2), 0) ActualOtherCost, "
				+ " case when isnull(sum(realinnerarea),0)=0 then 0 "
				+ "      else round( (isnull(sum(f.ClAmount), 0) + isnull(sum(f.ActualOtherCost), 0)) / sum(realinnerarea), 2 ) end dfcb "
				+ "from tCustomer a "
				+ "left join ( select CustCode,sum(a.offerpri*qty) workerPlanOffer, "
				+ "sum(a.Material*qty) workerPlanMaterial,sum(a.PrjMaterial*qty) projectPlanMaterial,sum(case when BaseCheckItemCode='0108' then qty else 0 end) realInnerArea  "
				+ "from tBaseCheckItemPlan a,tBaseCheckItem b where a.BaseCheckItemCode=b.Code AND b.WorkType12='02' group by Custcode) b on b.custcode=a.code "
				+ "left join ( select CustCode,WorkType12,max(pk) CustWkpk from tCustWorker group by CustCode,WorkType12 ) c on a.code=c.CustCode and c.WorkType12='02' "
				+ "left join tCustWorker d on c.CustWkpk=d.pk "
				+ "left join tWorker e on d.WorkerCode=e.code "
				+ "left join ( "
				+ "    select custcode, "
				+ "        sum(round(case when ia.Type = 'S' then 1 else -1 end * ia.ProjectAmount,0)) ClAmount, "
				+ "        sum(ia.OtherCost + ia.OtherCostAdj + ia.ProjectOtherCost) ActualOtherCost "
				+ "    from tItemApp ia "
				+ "    left join tItemType2 it2 on it2.Code=ia.ItemType2 "
				+ "    where ia.Status in ('SEND','RETURN','CONFIRMED') "
				+ "        and ia.ItemType1='JZ' and it2.MaterWorkType2='007' "
				+ "    GROUP BY custcode"
				+ ") f ON a.code=f.custcode "
				+ "left join tCusttype g on a.custType=g.Code "
				+ "left join ( select CustCode,isnull(sum(ConfirmAmount),0) ConfirmAmount,isnull(sum(AppAmount),0) AppAmount "
				+ "    from tWorkCostDetail wcd "
				+ "    inner join tWorkCost wc on wc.No=wcd.No "
				+ "    left outer join tSalaryType st on st.code=wcd.SalaryType "
				+ "    where  wcd.Status='2' and wcd.IsCalProjectCost='1' "
				+ "    and wcd.WorkType2='008'  "
				+ "    group by CustCode) h on a.Code=h.CustCode "
				+ "inner join ( select CustCode,isnull(sum(ConfirmAmount),0) ConfirmAmount,isnull(sum(AppAmount),0) AppAmount "// 实发水电材料奖惩
				+ "    from tWorkCostDetail wcd "
				+ "    inner join tWorkCost wc on wc.No=wcd.No "
				+ "    where  wcd.Status='2' " 
				+ "    and wcd.WorkType2='024'  "
				+ "    group by CustCode) i on a.Code=i.CustCode    "
				+ "left join (select sum(fdm.Amount)fixAmount,fd.CustCode from tFixDutyMan fdm " 
				+ "   inner join tFixDuty fd on fdm.No=fd.No " 
				+ "   left join tCustWorker cw on fd.CustWkPk=cw.PK "
				+ "   where fdm.FaultType<>'2' and cw.WorkType12='02' group by fd.CustCode)j on a.Code=j.CustCode "
				+ "where a.IsWaterItemCtrl = '1' and a.status in ('4','5') and h.ConfirmAmount<>0  and isnull(workerPlanMaterial,0)<>0 ";
		
		if (customer.getEndDateFrom()!=null) {
			sql += " and d.EndDate>= ? ";
			list.add(customer.getEndDateFrom());
		}
		if (customer.getEndDateTo()!=null) {
			sql += " and d.EndDate< dateadd(day,1,?) ";
			list.add(customer.getEndDateTo());
		}
		if (customer.getSendDateFrom()!=null) {
			sql += " and exists(select 1 from tWorkCostDetail wcd inner join tWorkCost wc on wcd.No=wc.No " +
					"where wcd.CustCode=a.Code and wcd.Status='2' and wcd.IsCalProjectCost='1' " +
					"and wcd.WorkType2='008' and wc.PayDate>=?)";
			list.add(customer.getSendDateFrom());
		}
		if (customer.getSendDateTo()!=null) {
			sql += " and exists(select 1 from tWorkCostDetail wcd inner join tWorkCost wc on wcd.No=wc.No " +
					"where wcd.CustCode=a.Code and wcd.Status='2' and wcd.IsCalProjectCost='1' " +
					"and wcd.WorkType2='008' and wc.PayDate<dateadd(day,1,?))";
			list.add(customer.getSendDateTo());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.CustType in " + "('"
					+ customer.getCustType().replace(",", "','") + "')";
		}
		if (customer.getAreaFrom()!=null) {
			sql += " and b.realinnerarea >=? " ;
			list.add(customer.getAreaFrom());
		}
		if (customer.getAreaTo()!=null) {
			sql += " and b.realinnerarea <=? " ;
			list.add(customer.getAreaTo());
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? " ;
			list.add("%"+customer.getAddress()+"%");
		}
		sql += " group by d.WorkerCode,e.NameChi" ;
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.WorkerCode";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
}
