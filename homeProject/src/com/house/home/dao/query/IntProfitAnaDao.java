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
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class IntProfitAnaDao extends BaseDao {

	/**
	 * IntProfitAna分页信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql =" declare @CupInsFee nvarchar(10) = (select QZ from tXTCS where ID='CupInsFee')  " 
				+ "declare @CupInsCalTyp nvarchar(10) = (select QZ from tXTCS where ID='CupInsCalTyp') " 
				+ "declare @IntInsCalTyp nvarchar(10) = (select QZ from tXTCS where ID='IntInsCalTyp') " 
				+ "select * from(select a.*,case when a.CupSaleFee <> 0 then cast(round((a.CupSaleFee-CupInstallCost-CupItemAppCost)/a.CupSaleFee*100,4) as nvarchar(20))+'%' else '0' end CupPerf, "
				+ "case when a.IntSaleFee <> 0 then cast(round((a.IntSaleFee-IntInstallCost-IntItemAppCost)/a.IntSaleFee*100,4) as nvarchar(20) ) +'%' else '0' end IntPerf,  "
				+ "case when a.IntSaleFee+a.CupSaleFee<>0 then cast(round((a.IntSaleFee+a.CupSaleFee-a.CupItemAppCost-a.IntItemAppCost-a.realOfferFee)/(a.IntSaleFee+a.CupSaleFee)*100,4) as nvarchar(20))+'%' else '0' end totalPerf "
				+ "from(select a.custcheckdate,a.descr custdescr,a.Address,a.CustType,a.Status custstatus,j.NOTE custstatusdescr,e.Desc1 custtypedescr, "
				+ "a.code custcode, CupboardFee*ContainCup+isnull(d.CupChgFee,0)+e.CupSaleAmount_Set CupSaleFee,Integratedisc+isnull(d.IntChgDisc,0) Integratedisc,CupBoardDisc+isnull(d.CupChgDisc,0) CupBoardDisc, "
				+ "isnull(f.CupItemAppCost,0)CupItemAppCost,case when @CupInsCalTyp = '1' then isnull(g.CupUpHigh+g.CupDownHigh,0)*@CupInsFee else isnull(h3.CupInstallCost,0) end CupInstallCost, "
				+ "IntegrateFee*ContainInt+isnull(d.IntChgFee,0)+case when e.IntSaleAmountCalcType_Set='1' then e.IntSaleAmount_Set else a.Area*e.IntSaleAmount_Set end IntSaleFee, "
				+ "isnull(f.IntItemAppCost,0)IntItemAppCost,case when @IntInsCalTyp = '1' then isnull(h.IntInstallCost,0) else isnull(h2.IntInstallCost,0) end IntInstallCost,isnull(k.realOfferFee,0)realOfferFee,CupboardFee*ContainCup CupPlanFee,"
				+ "IntegrateFee*ContainInt IntPlanFee,l.nameChi DesignManDescr,m.Desc2 DesignManDept1Descr,n.Desc2 DesignManDept2Descr,dbo.fGetEmpNameChi(a.Code,'61') CupDesignManDescr,dbo.fGetEmpNameChi(a.Code,'11') IntDesignManDescr "
				+ "from tCustomer a  "
				+ "left join (" 
				+ " select CustCode, isnull(sum(case when IsCupboard='1' then BefAmount else 0 end),0)CupChgFee, "
				+ " isnull(sum(case when IsCupboard='1' then (case when BefAmount>=0 then DiscAmount else DiscAmount*-1 end) else 0 end),0)CupChgDisc, "
				+ "	isnull(sum(case when IsCupboard='0' then BefAmount else 0 end),0)IntChgFee,  "
				+ "	isnull(sum(case when IsCupboard='0' then (case when BefAmount>=0 then DiscAmount else DiscAmount*-1 end) else 0 end),0)IntChgDisc  "
				+ "	from tItemChg  "
				+ "	where Status='2' and ItemType1='JC' "
				+ "	group by CustCode" 
				+ ") d on a.Code=d.CustCode  "
				+ "left join tCustType e on a.CustType=e.Code "
				+ "left join ( "
				+ "	select CustCode,isnull(sum(case when IsCupboard='1' then case when Type='S' and Status<>'CANCEL' then Amount "
				+ " when Type='R' and Status='RETURN' then -Amount end else 0 end),0)+isnull(sum(case when IsCupboard='1' then a.OtherCost+a.OtherCostAdj else 0 end),0) CupItemAppCost, "
				+ "	isnull(sum(case when IsCupboard='0' then case when Type='S' and Status<>'CANCEL' then Amount "
				+ " when Type='R' and Status='RETURN' then -Amount end else 0 end),0)+isnull(sum(case when IsCupboard='0' then a.OtherCost+a.OtherCostAdj else 0 end),0) IntItemAppCost "
				+ "	from tItemApp a where ItemType1='JC'  and  ((Type='S' and Status<>'CANCEL') or (Type='R' and Status='RETURN')) "
				+ "	group by CustCode "
				+ ") f on a.Code=f.CustCode "
				+ "left join tSpecItemReq g on a.Code=g.CustCode "
				+ "left join ( "
				+ "	select CustCode,isnull(sum(isnull(e.IntInstallFee,0)*c.Qty),0) IntInstallCost "
				+ "	from tSpecItemReqDt c  "
				+ "	left join tItem d on c.ItemCode=d.Code  "
				+ "	inner join tItemType3 e on d.ItemType3=e.Code "
				+ "	where  c.IsCupboard='0' group by CustCode "
				+ ") h on a.Code=h.CustCode "
				+ "left join ( "
				+ "	select CustCode,isnull(sum(isnull(e.IntInstallFee,0)*c.Qty),0) IntInstallCost "
				+ "	from tItemReq c  "
				+ "	left join tIntProd b on c.IntProdPK=b.PK  "
				+ "	left join tItem d on c.ItemCode=d.Code  "
				+ "	inner join tItemType3 e on d.ItemType3=e.Code "
				+ "	where b.IsCupboard='0' group by CustCode "
				+ ") h2 on a.Code=h2.CustCode "
				+ "left join ( "
				+ "	select CustCode,isnull(sum(isnull(e.IntInstallFee,0)*c.Qty),0) CupInstallCost "
				+ "	from tItemReq c  "
				+ "	left join tIntProd b on c.IntProdPK=b.PK  "
				+ "	left join tItem d on c.ItemCode=d.Code  "
				+ "	inner join tItemType3 e on d.ItemType3=e.Code "
				+ "	where b.IsCupboard='1' group by CustCode "
				+ ") h3 on a.Code=h3.CustCode "
				+ "left join tCustIntProg i on a.Code=i.CustCode "
				+ "left join tXTDM j on j.CBM = a.Status and j.ID='CUSTOMERSTATUS' "
				+ "left join ( "
				+ "	select CustCode,sum(amount) realOfferFee from tLaborFeeDetail a " 
				+ "	inner join tLaborFee b on a.No=b.No " 
				+ "	where ItemType1='JC' and b.Status='4' group by CustCode "
				+ ") k on a.Code=k.CustCode "
				+ "left join tEmployee l on a.DesignMan=l.Number "
				+ "left join tDepartment1 m on m.Code=l.Department1 "
				+ "left join tDepartment2 n on n.Code=l.Department2 "
				+ "where a.Expired='F')a where 1=1 ";
			sql += " and exists(select 1 from tItemApp k where k.custcode=a.custcode and itemtype1='JC' ";
			if (customer.getSendDateFrom() != null) {
				sql += " and k.senddate>= ? ";
				list.add(customer.getSendDateFrom());
			}
			if (customer.getSendDateTo() != null) {
				sql += " and k.senddate< ? ";
				list.add(customer.getSendDateTo());
			}
			if(StringUtils.isNotBlank(customer.getIsCupboard())){
				sql += " and k.iscupboard=? ";	
				list.add(customer.getIsCupboard());
			}
			sql+=" ) ";
		if (customer.getCustCheckDateFrom() != null) {
			sql += " and a.custcheckdate>= ? ";
			list.add(customer.getCustCheckDateFrom());
		}
		if (customer.getCustCheckDateTo()!= null) {
			sql += " and a.custcheckdate< ? ";
			list.add(DateUtil.addInteger(customer.getCustCheckDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.address like ?  ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			String str = SqlUtil.resetStatus(customer.getCustType());
			sql += " and a.custType in (" + str + ")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  order by custcode asc";
		}
		List<Map<String, Object>> resultList = this.findListBySql(sql.toLowerCase(), list.toArray());
		page.setResult(resultList);
		page.setTotalCount(resultList.size());
		return page;
	}
	/**
	 * 明细表头
	 * 
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> findDetailHead(String custCode) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String sql =" declare @CupInsFee nvarchar(10) = (select QZ from tXTCS where ID='CupInsFee')  " 
				+ "declare @CupInsCalTyp nvarchar(10) = (select QZ from tXTCS where ID='CupInsCalTyp') " 
				+ "declare @IntInsCalTyp nvarchar(10) = (select QZ from tXTCS where ID='IntInsCalTyp') " 
				+"select a.*,case when a.CupSaleFee <> 0 then round((a.CupSaleFee-CupInstallCost-CupItemAppCost)/a.CupSaleFee,4)else 0 end cupPerf, "
				+"a.IntItemAppCost+a.IntInstallCost intCost,a.CupItemAppCost+a.CupInstallCost cupCost,"
				+ "case when a.IntSaleFee <> 0 then round((a.IntSaleFee-IntInstallCost-IntItemAppCost)/a.IntSaleFee,4)else 0 end intPerf,  "
				+ "case when a.IntSaleFee+a.CupSaleFee<>0 then round((a.IntSaleFee+a.CupSaleFee-a.CupItemAppCost-a.IntItemAppCost-a.realOfferFee)/(a.IntSaleFee+a.CupSaleFee),4) else 0 end totalPerf "
				+ "from(select "
				+ "a.code custcode, CupboardFee*ContainCup+isnull(d.CupChgFee,0)+e.CupSaleAmount_Set cupSaleFee, "
				+ "CupboardFee*ContainCup cupPlanFee,isnull(d.CupChgFee,0) cupChgFee,e.cupSaleAmount_Set,"
				+ "isnull(f.CupItemAppCost,0)cupItemAppCost,case when @CupInsCalTyp = '1' then isnull(g.CupUpHigh+g.CupDownHigh,0)*@CupInsFee else isnull(h3.CupInstallCost,0) end cupInstallCost, "
				+ "IntegrateFee*ContainInt+isnull(d.IntChgFee,0)+case when e.IntSaleAmountCalcType_Set='1' then e.IntSaleAmount_Set else a.Area*e.IntSaleAmount_Set end intSaleFee, "
				+ "IntegrateFee*ContainInt intPlanFee,isnull(d.IntChgFee,0) intChgFee,case when e.IntSaleAmountCalcType_Set='1' then e.IntSaleAmount_Set else a.Area*e.IntSaleAmount_Set end intSaleAmount_Set,"
				+ "isnull(f.IntItemAppCost,0)intItemAppCost,case when @IntInsCalTyp = '1' then isnull(h.IntInstallCost,0) else isnull(h2.IntInstallCost,0) end intInstallCost,isnull(k.realOfferFee,0)realOfferFee "
				+ "from tCustomer a  "
				+ "left join (" 
				+ " select CustCode, isnull(sum(case when IsCupboard='1' then BefAmount else 0 end),0)CupChgFee, "
				+ "	isnull(sum(case when IsCupboard='0' then BefAmount else 0 end),0)IntChgFee  "
				+ "	from tItemChg  "
				+ "	where Status='2' and ItemType1='JC' "
				+ "	group by CustCode" 
				+ ") d on a.Code=d.CustCode  "
				+ "left join tCustType e on a.CustType=e.Code "
				+ "left join ( "
				+ "	select CustCode,isnull(sum(case when IsCupboard='1' then case when Type='S' and Status<>'CANCEL' then Amount "
				+ " when Type='R' and Status='RETURN' then -Amount end else 0 end),0)+isnull(sum(case when IsCupboard='1' then a.OtherCost+a.OtherCostAdj else 0 end),0) CupItemAppCost, "
				+ "	isnull(sum(case when IsCupboard='0' then case when Type='S' and Status<>'CANCEL' then Amount "
				+ " when Type='R' and Status='RETURN' then -Amount end else 0 end),0)+isnull(sum(case when IsCupboard='0' then a.OtherCost+a.OtherCostAdj else 0 end),0) IntItemAppCost "
				+ "	from tItemApp a where ItemType1='JC'  and  ((Type='S' and Status<>'CANCEL') or (Type='R' and Status='RETURN')) "
				+ "	group by CustCode "
				+ ") f on a.Code=f.CustCode "
				+ "left join tSpecItemReq g on a.Code=g.CustCode "
				+ "left join ( "
				+ "	select CustCode,isnull(sum(isnull(e.IntInstallFee,0)*c.Qty),0) IntInstallCost "
				+ "	from tSpecItemReqDt c  "
				+ "	left join tItem d on c.ItemCode=d.Code  "
				+ "	inner join tItemType3 e on d.ItemType3=e.Code "
				+ "	where  c.IsCupboard='0' group by CustCode "
				+ ") h on a.Code=h.CustCode "
				+ "left join ( "
				+ " select CustCode,isnull(sum(isnull(e.IntInstallFee,0)*c.Qty),0) IntInstallCost "
				+ "	from tItemReq c  "
				+ "	left join tIntProd b on c.IntProdPK=b.PK  "
				+ "	left join tItem d on c.ItemCode=d.Code  "
				+ "	inner join tItemType3 e on d.ItemType3=e.Code "
				+ "	where  b.IsCupboard='0' group by CustCode "
				+ ") h2 on a.Code=h2.CustCode "
				+ "left join ( "
				+ "	select CustCode,isnull(sum(isnull(e.IntInstallFee,0)*c.Qty),0) CupInstallCost "
				+ "	from tItemReq c  "
				+ "	left join tIntProd b on c.IntProdPK=b.PK  "
				+ "	left join tItem d on c.ItemCode=d.Code  "
				+ "	inner join tItemType3 e on d.ItemType3=e.Code "
				+ "	where  b.IsCupboard='1' group by CustCode "
				+ ") h3 on a.Code=h3.CustCode "
				+ "left join tCustIntProg i on a.Code=i.CustCode "
				+ "left join tXTDM j on j.CBM = a.Status and j.ID='CUSTOMERSTATUS' "
				+ "left join ( "
				+ "	 select CustCode,sum(amount) realOfferFee from tLaborFeeDetail a " 
				+ "	 inner join tLaborFee b on a.No=b.No " 
				+ "	 where ItemType1='JC' and b.Status='4' group by CustCode "
				+ ") k on a.Code=k.CustCode " 
				+ "where a.Expired='F')a where a.custcode=? ";
		System.out.println(sql);
		list = this.findListBySql(sql, new Object[] { custCode });
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
