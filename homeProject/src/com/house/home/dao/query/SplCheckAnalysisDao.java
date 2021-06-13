package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.finance.SplCheckOut;
@Repository
@SuppressWarnings("serial")
public class SplCheckAnalysisDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.*, isnull(b.sumSaleAmount, 0) sumSaleAmount, isnull(b.GiftAmount, 0) GiftAmount, "
				   + " case when isnull(b.sumSaleAmount, 0) = 0 then 0 "
				   + "     else (isnull(b.sumSaleAmount, 0) + isnull(b.GiftAmount, 0) - isnull(a.sumamount, 0)) / (isnull(b.sumSaleAmount, 0) + isnull(b.GiftAmount, 0)) end rate,"
				   + " ts.Descr splCodeDescr "
				   + " from ( "
				   + " 		select round(sum(case when tp.Type='S' then tp.Amount else -tp.Amount end +tp.OtherCost+tp.OtherCostAdj),0) sumamount, tsco.SplCode "
				   + " 		from tPurchase tp  "
				   + " 		left join tSplCheckOut tsco on tsco.No = tp.CheckOutNo  "
				   + " 		left join tItemApp tia on tia.PUNo = tp.No "
				   + " 		where tp.IsCheckOut = '1' ";// and tp.ItemType1 not in ('JZ', 'JC')

		if(StringUtils.isNotBlank(splCheckOut.getItemRight())){
			sql += " and tp.ItemType1 in ('"+splCheckOut.getItemRight().trim().replace(",", "','")+"')";
		}else{
			sql += " and 1<>1 ";
		}
		if(splCheckOut.getConfirmDateFrom() != null){
			sql += " and tsco.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(splCheckOut.getConfirmDateFrom()));
		}
		if(splCheckOut.getConfirmDateTo() != null){
			sql += " and tsco.ConfirmDate <= ? ";
			params.add(DateUtil.endOfTheDay(splCheckOut.getConfirmDateTo()));
		}
		if("0".equals(splCheckOut.getIsIncludePurchIn())){
			sql += " and tia.No is not null ";
		}
		
	    // 增加是否服务性产品过滤条件 张海洋 20200706
        if (splCheckOut.getIsService() != null) {
            sql += " and tia.IsService = ? ";
            params.add(splCheckOut.getIsService());
        }
		
		if(StringUtils.isNotBlank(splCheckOut.getIsSetItem())){
			sql += " and tia.IsSetItem is not null and tia.IsSetItem <> '' and tia.IsSetItem = ? ";
			params.add(splCheckOut.getIsSetItem());
		}
		sql += " 	group by tsco.SplCode ) a "
			 + " left join ( "
			 + " 	select sum(( "
			 + " 			case when in_tir.qty <> 0 then Round(in_tiad.qty*(case when in_tia.IsSetItem = '1' then in_tiad.ProjectCost else in_tir.UnitPrice end) * in_tir.Markup/100 "
			 + " 			+ in_tir.ProcessCost*in_tiad.qty/in_tir.qty,0)  "		
			 + " 			else Round(in_tiad.qty*(case when in_tia.IsSetItem = '1' then in_tiad.ProjectCost else in_tir.UnitPrice end) * in_tir.Markup/100,0) end "
			 + " 		) * (case when in_tp.Type = 'S' then 1 else -1 end)"
			 + "	) sumSaleAmount, "
			 + "    sum(case when in_tir.UnitPrice * (in_tir.Markup / 100) < in_tir.Cost then in_tir.Qty * in_tir.UnitPrice * (1 - in_tir.Markup / 100) end) GiftAmount, "
			 + "    in_tsco.SplCode " 		
			 + " 	from tPurchase in_tp "
			 + "  	inner join tSplCheckOut in_tsco on in_tsco.No = in_tp.CheckOutNo  "		
			 + " 	inner join tItemApp in_tia on in_tia.PUNo = in_tp.No "
			 + " 	inner join tItemappDetail in_tiad on in_tia.no=in_tiad.No "  		
			 + " 	inner join tItemreq in_tir on in_tiad.ReqPK=in_tir.pk "		
			 + " 	inner join tItem in_ti on in_ti.Code=in_tiad.ItemCode "
			 + " 	where in_tp.IsCheckOut='1' and in_tp.ItemType1 not in ('JZ', 'JC') ";
		if(StringUtils.isNotBlank(splCheckOut.getItemRight())){
			sql += " and in_tp.ItemType1 in ('"+splCheckOut.getItemRight().trim().replace(",", "','")+"')";
		}else{
			sql += " and 1<>1 ";
		}
		if(splCheckOut.getConfirmDateFrom() != null){
			sql += " and in_tsco.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(splCheckOut.getConfirmDateFrom()));
		}
		if(splCheckOut.getConfirmDateTo() != null){
			sql += " and in_tsco.ConfirmDate <= ? ";
			params.add(DateUtil.endOfTheDay(splCheckOut.getConfirmDateTo()));
		}
		if("0".equals(splCheckOut.getIsIncludePurchIn())){
			sql += " and in_tia.No is not null ";
		}
		if(StringUtils.isNotBlank(splCheckOut.getIsSetItem())){
			sql += " and in_tia.IsSetItem is not null and in_tia.IsSetItem <> '' and in_tia.IsSetItem = ? ";
			params.add(splCheckOut.getIsSetItem());
		}
		
		// 增加是否服务性产品过滤条件 张海洋 20200706
		if (splCheckOut.getIsService() != null) {
            sql += " and in_tia.IsService = ? ";
            params.add(splCheckOut.getIsService());
        }
		
		sql += "	group by in_tsco.SplCode "
			 + " ) b on a.SplCode = b.SplCode "
			 + " left join tSupplier ts on ts.Code = a.SplCode "
			 + " where 1=1 ";
		if(StringUtils.isNotBlank(splCheckOut.getSplCode())){
			sql += " and a.SplCode = ? ";
			params.add(splCheckOut.getSplCode());
		}
		if(StringUtils.isNotBlank(splCheckOut.getItemType1())){
			sql += " and ts.ItemType1 = ? ";
			params.add(splCheckOut.getItemType1());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goJqGridDetail(Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from( select a.No PUNo,tia.No IANo,tc.Address,isnull(a.sumamount, 0) sumamount,case when tp.ItemType1 not in ('JZ','JC') then isnull(b.sumSaleAmount, 0) else 0 end sumSaleAmount," 
				   + "tx1.NOTE isSetItemDescr,tx2.NOTE isCupboard,te.Namechi AppCzyDescr  from ( "
				   + " 		select case when tp.Type='S' then tp.Amount else -tp.Amount end +tp.OtherCost+tp.OtherCostAdj sumamount,tp.No"
				   + " 		from tPurchase tp  "
				   + " 		left join tSplCheckOut tsco on tsco.No = tp.CheckOutNo "
				   + " 		left join tItemApp tia on tia.PUNo = tp.No "
				   + " 		where tp.IsCheckOut = '1' ";// and tp.ItemType1 not in ('JZ', 'JC') 
		if(StringUtils.isNotBlank(splCheckOut.getItemRight())){
			sql += " and tp.ItemType1 in ('"+splCheckOut.getItemRight().trim().replace(",", "','")+"') ";
		}else{
			sql += " and 1<>1 "; 
		}
		sql += " and tsco.ConfirmDate >= ? and tsco.ConfirmDate <= ? and tsco.SplCode = ? ";
		params.add(splCheckOut.getConfirmDateFrom());
		params.add(DateUtil.endOfTheDay(splCheckOut.getConfirmDateTo()));
		params.add(splCheckOut.getSplCode());
		if("0".equals(splCheckOut.getIsIncludePurchIn())){
			sql += " and tia.No is not null ";
		}
		
		if (splCheckOut.getIsService() != null) {
            sql += " and tia.IsService = ? ";
            params.add(splCheckOut.getIsService());
        }
		
		if(StringUtils.isNotBlank(splCheckOut.getIsSetItem())){
			sql += " and tia.IsSetItem is not null and tia.IsSetItem <> '' and tia.IsSetItem = ? ";
			params.add(splCheckOut.getIsSetItem());
		}
		sql += " ) a "
			 + " left join ( "
			 + " 	select sum((case when in_tir.qty <> 0 then Round(in_tiad.qty*(case when in_tia.IsSetItem = '1' then in_tiad.ProjectCost else in_tir.UnitPrice end) * in_tir.Markup/100 "
			 + " 	+ in_tir.ProcessCost*in_tiad.qty/in_tir.qty,0) "		
			 + " 	else Round(in_tiad.qty*(case when in_tia.IsSetItem = '1' then in_tiad.ProjectCost else in_tir.UnitPrice end) * in_tir.Markup/100,0) end) "
			 + " 	* (case when in_tp.Type = 'S' then 1 else -1 end)) sumSaleAmount, in_tp.No,in_tia.No IANo	"
			 + " 	from tPurchase in_tp "		
			 + " 	inner join tSplCheckOut in_tsco on in_tsco.No = in_tp.CheckOutNo "	
			 + " 	inner join tItemApp in_tia on in_tia.PUNo = in_tp.No " 		
			 + " 	inner join tItemappDetail in_tiad on in_tia.no=in_tiad.No "		
			 + " 	inner join tItemreq in_tir on in_tiad.ReqPK=in_tir.pk "
			 + " 	inner join tItem in_ti on in_ti.Code=in_tiad.ItemCode "
			 + " 	where in_tp.IsCheckOut='1'";
		if(StringUtils.isNotBlank(splCheckOut.getItemRight())){
			sql += " and in_tp.ItemType1 in ('"+splCheckOut.getItemRight().trim().replace(",", "','")+"') ";
		}else{
			sql += " and 1<>1 "; 
		}
		params.add(splCheckOut.getConfirmDateFrom());
		params.add(DateUtil.endOfTheDay(splCheckOut.getConfirmDateTo()));
		params.add(splCheckOut.getSplCode());
		sql += " and in_tsco.ConfirmDate >= ? and in_tsco.ConfirmDate <= ? and in_tsco.SplCode= ? ";
		if("0".equals(splCheckOut.getIsIncludePurchIn())){
			sql += " and in_tia.No is not null ";
		}
		
		if (splCheckOut.getIsService() != null) {
            sql += " and in_tia.IsService = ? ";
            params.add(splCheckOut.getIsService());
        }
		
		if(StringUtils.isNotBlank(splCheckOut.getIsSetItem())){
			sql += " and in_tia.IsSetItem is not null and in_tia.IsSetItem <> '' and in_tia.IsSetItem = ? ";
			params.add(splCheckOut.getIsSetItem());
		}
		sql += " group by in_tp.No, in_tia.No ) b on a.No = b.No "
			 + " left join tPurchase tp on tp.No = a.No "
			 + " left join tCustomer tc on tp.CustCode = tc.Code "
			 + " left join tItemApp tia on tia.PUNo = tp.No "
			 + " left join tXTDM tx1 on tx1.ID='YESNO' and tx1.CBM = tia.IsSetItem "
			 + " left join tXTDM tx2 on tx2.ID='YESNO' and tx2.CBM = tia.IsCupboard "
			 + " left join tEmployee te on te.Number=tia.AppCZY";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
}
