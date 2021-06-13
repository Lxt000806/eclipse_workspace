package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class PrjPlanPerfProfitDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
		+"select a.*,case when ContractFee<>0 then (ContractFee-BaseCtrlAmt-CostAll_ZC-CostAll_RZ-CostAll_JC)/ContractFee*100 else 0 end Profit,round(case when area=0 then 0 else AllDisc/area end,1) avgDisc  "
		+" ,round(case when area=0 then 0 else mainSetCost/area end,0) avgMainSetCost "
		+"from ( "
		+"	select a.Address,a.CustType,b.Desc1 CustTypeDescr,c.NameChi DesignManDescr,d.Desc2 Department2Descr,a.SignDate,a.Area,a.ContractFee, a.IsInitSign, "
		+"	round(isnull(dbo.fGetCustBaseCtrl_Com(a.code, 3, ''),0),0)BaseCtrlAmt, " 
		+"  round((isnull(e.Maincostall, 0) + isnull(e.Mainprocesscost, 0))*a.ContainMain +(isnull(e.Servcostall, 0) + isnull(e.Servprocesscost, 0))*a.ContainMainServ,0) CostAll_ZC, "
		+"	round((isnull(f.CostAll,0)+isnull(f.ProcessCost,0))*a.containSoft,0) CostAll_RZ, "
		+"	round((a.IntegrateFee+b.IntSaleAmount_Set)*(1-cast((select QZ from tXTCS where ID='IntProfPer') as money))*a.ContainInt " 
		+"  +(a.CupboardFee+b.CupSaleAmount_Set)*(1-cast((select QZ from tXTCS where ID='CupProfPer') as money))*a.ContainCup,0) CostAll_JC, "
		+"  round(a.BaseDisc * a.ContainBase,0) BaseDisc, "
		+"  round(a.MainDisc * a.ContainMain,0) MainDisc,  " 
        +"   round(a.CupBoardDisc * a.ContainCup+a.IntegrateDisc * a.ContainInt,0) IntDisc, "
        +"  round(a.SoftDisc * a.ContainSoft,0) SoftDisc, "
        +"  round(a.BaseDisc* a.ContainBase+a.MainDisc * a.ContainMain+a.CupBoardDisc * a.ContainCup+a.IntegrateDisc * a.ContainInt  +a.SoftDisc * a.ContainSoft+ isnull(i.SingleDisc,0),0) AllDisc, "
        +"  round(a.BaseFee_Dirct+a.BaseFee_Comp,0) BasePlan,a.setAdd,a.setMinus,isnull(e.mainSetCost,0) mainSetCost,round(isnull(OutCtrlAmt,0),0) OutCtrlAmt, "
		+"  isnull(e.OutSetmainplan,0) OutSetmainplan,isnull(f.OutSetSoftplan,0) OutSetSoftplan,isnull(l.OutSetJCPlan,0) OutSetJCPlan,isnull(k.outsetBasePlan,0) outsetBasePlan "
        +"	from tCustomer a  "
		+"	left join tCusttype b on a.CustType=b.Code  "
		+"	left join tEmployee c on a.DesignMan=c.Number "
		+"	left join tDepartment2  d on c.Department2=d.Code "
		+"	left join ( "
		+"  	select sum(case when IsService=0 then Cost * Qty else 0 end) Maincostall, "
		+" 		sum(case when IsService=0 then case when ProcessCost > 0 then ProcessCost else 0 end else 0 end) Mainprocesscost, "
        +" 		sum(case when IsService=1 then Cost * Qty else 0 end) Servcostall, "
        +" 		sum(case when IsService=1 then case when ProcessCost > 0 then processcost else 0 end else 0 end) Servprocesscost, "
        +"		sum(case when IsOutSet='0' then Cost * Qty else 0 end) mainSetCost, "
        +"		sum(case when IsOutSet='1' then lineamount end) OutSetmainplan, "
        +" 		custcode "
		+" 		from  titemplan where itemtype1 ='zc' "
		+"      group by custcode "
		+"	)e on a.Code=e.CustCode "
		+"	left join ( "
		+"		select sum(Cost*Qty)CostAll,sum(case when ProcessCost>0 then ProcessCost else 0 end)ProcessCost,CustCode  "
		+"		       ,sum(case when IsOutSet='1' then lineamount end) OutSetSoftplan "
		+"		from tItemPlan  "
		+"		where ItemType1='RZ'  "
		+"		group by CustCode  "
		+"	)f on a.Code=f.CustCode "
		+"	left join ( select sum(round(Qty*UnitPrice*(100-Markup)/100+case when processcost < 0 then processcost*(-1) else 0 end, 0)) SingleDisc,custcode " 
	    +"       from titemplan  group by custcode "
		+"	) i on a.code = i.custcode "
		+"	left join ( "
		+"	   select sum(case when e.BaseCtrlPer is not null "
		+"						 then round(round(a.Material * a.Qty, 0) * e.BaseCtrlPer, 2)+round(round(a.UnitPrice*a.Qty,0)*e.BaseCtrlPer,2) "
		+"						 else round(( case when a.PrjCtrlType = '1' "
		+"										   then a.Material * a.MaterialCtrl+a.UnitPrice*a.OfferCtrl "
		+"										   else a.MaterialCtrl+a.OfferCtrl "
		+"									  end ) * a.Qty, 2) "
		+"					end) OutCtrlAmt,a.CustCode "
		+"	    from  tBaseItemPlan a "
		+"		inner join tBaseItem b on a.BaseItemCode = b.Code "
		+"	    left join tCustBaseCtrl e on a.CustCode = e.CustCode and a.BaseItemCode = e.BaseItemCode "
		+"		where  a.IsOutSet ='1' "
		+"		group by a.CustCode "
		+"	)j on j.CustCode=a.code	 "
		+"	left join ( select sum(lineamount) outsetBasePlan,custcode " 
	    +"       from tBaseitemPlan  " 
	    +"		where IsOutSet ='1' group by custcode "
		+"	) k on a.code = k.custcode "
		+"	left join ( "
		+"		select CustCode ,sum(case when IsOutSet='1' then lineamount end) OutSetJCPlan "
		+"		from tItemPlan  "
		+"		where ItemType1='JC'  "
		+"		group by CustCode  "
		+"	)l on a.Code=l.CustCode "
		+")a "
		+"where 1=1";		
		if (customer.getSignDateFrom() != null){
			sql += " and a.SignDate>= ? ";
			list.add(customer.getSignDateFrom());
		}
		if (customer.getSignDateTo() != null){
			sql += " and a.SignDate< ? ";
			list.add(DateUtil.addInteger(customer.getSignDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and  a.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		if (StringUtils.isBlank(customer.getIsInitSign())
				|| "0".equals(customer.getIsInitSign())) {
			sql += " and a.IsInitSign='0' ";
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.SignDate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	} 

}
