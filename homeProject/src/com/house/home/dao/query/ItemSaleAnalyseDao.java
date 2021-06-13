package com.house.home.dao.query;
import com.house.framework.commons.orm.BaseDao;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.Page;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;

@SuppressWarnings("serial")
@Repository
public class ItemSaleAnalyseDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select d.descr uomdescr,isnull(c.planQty,0) planQty,isnull(c.planPrice,0) planPrice,isnull(b.chgqty,0)chgQty," +
				" isnull(b.ChgPrice,0) chgPrice, e.Descr supplDescr," +
				" a.Descr,a.code ,isnull(c.planQty,0)+ isnull(b.chgqty,0) saleQty ,isnull(b.ChgPrice,0)+isnull(c.planPrice,0) salePrice," +
				" isnull(chgCost,0) chgCost, isnull(planCost,0)planCost," +
				" isnull(c.planPrice,0)+ isnull(b.chgPrice,0)-isnull(c.planCost,0) -isnull(b.chgCost,0) profit" +
				" from tItem a " +
				" left join (" +
				"	select sum(b.Qty) chgQty,sum(b.Qty*b.UnitPrice)chgPrice,b.ItemCode,sum(cost*b.Qty)chgCost from tItemChg a" +
				"	left join tItemChgDetail b on b.No = a.No" +
				"	where a.Status = '2'" ;
		if(item.getDateFrom() != null){
			sql+=" and a.confirmDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( item.getDateFrom()).getTime()));
		}
		if(item.getDateTo() != null){
			sql+=" and a.confirmDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(item.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(item.getIsOutSet())){
			sql+=" and b.isOutSet = ? ";
			list.add("1".equals(item.getIsOutSet()) ? "0":"1");
		}
		sql+="	group by b.ItemCode" +
				" ) b on b.ItemCode = a.Code" +
				" left join (" +
				"	select sum(a.Qty) planQty ,sum(a.Qty*a.UnitPrice) planPrice, a.ItemCode,sum(Cost*a.Qty)planCost  from tItemPlan a " +
				"	left join tCustomer b on b.Code = a.CustCode" +
				"	where 1=1 ";
		
		if(item.getDateFrom() != null){
			sql+=" and b.SignDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( item.getDateFrom()).getTime()));
		}
		if(item.getDateTo() != null){
			sql+=" and b.SignDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(item.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(item.getIsOutSet())){
			sql+=" and a.isOutSet = ?";
			list.add("1".equals(item.getIsOutSet()) ? "0":"1");
		}
		sql+= "	group by a.ItemCode" +
				" ) c on c.ItemCode = a.Code" +
				" left join tUOM d on d.Code = a.uom " +
				" left join tSupplier e on e.Code = a.SupplCode " +
				" where 1=1 " +
				" and( c.ItemCode is not null or b.itemCode is not null) " +
				"  " ;
		if(StringUtils.isNotBlank(item.getItemType1())){
			sql+=" and a.ItemType1 = ? ";
			list.add(item.getItemType1());
		}
		if(StringUtils.isNotBlank(item.getSupplCode())){
			sql+=" and a.SupplCode = ? ";
			list.add(item.getSupplCode());
		}
		if(StringUtils.isNotBlank(item.getCode())){
			sql+=" and a.Code = ? ";
			list.add(item.getCode());
		}
		if(StringUtils.isNotBlank(item.getItemType2())){
			sql+=" and a.ItemType2 = ? ";
			list.add(item.getItemType2());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.salePrice desc";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Page<Map<String,Object>> findChgPageBySql(Page<Map<String,Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (	" +
				" select	c.Address,b.ItemCode, d.descr itemdescr , e.Descr uomDescr, f.Descr supplDescr,b.Qty," +
				" b.UnitPrice, b.qty*b.UnitPrice Amount" +
				" from	tItemChg a" +
				" left join tItemChgDetail b on b.No = a.No" +
				" left join tCustomer c on c.Code = a.CustCode" +
				" left join titem d on d.Code = b.ItemCode" +
				" left join tUOM e on e.Code = d.Uom" +
				" left join tSupplier f on f.Code = d.SupplCode " +
				" where	1=1 and a.Status = '2' " +
				" ";
		
		if(StringUtils.isNotBlank(item.getItemType1())){
			sql+=" and d.ItemType1 = ? ";
			list.add(item.getItemType1());
		}
		if(item.getDateFrom() != null){
			sql+=" and a.confirmDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( item.getDateFrom()).getTime()));
		}
		if(item.getDateTo() != null){
			sql+=" and a.confirmDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(item.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(item.getSupplCode())){
			sql+=" and d.SupplCode = ? ";
			list.add(item.getSupplCode());
		}
		if(StringUtils.isNotBlank(item.getCode())){
			sql+=" and d.Code = ? ";
			list.add(item.getCode());
		}
		if(StringUtils.isNotBlank(item.getIsOutSet())){
			sql+=" and b.isOutSet = ? ";
			list.add("1".equals(item.getIsOutSet()) ? "0":"1");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Page<Map<String,Object>> findPlanPageBySql(Page<Map<String,Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (	" +
				" select c.Address,a.ItemCode, d.descr itemdescr , e.Descr uomDescr, f.Descr supplDescr,a.Qty, a.UnitPrice, " +
				" a.qty*a.UnitPrice Amount from tItemPlan a " +
				" left join tCustomer c on c.Code = a.CustCode" +
				" left join titem d on d.Code = a.ItemCode" +
				" left join tUOM e on e.Code = d.Uom" +
				" left join tSupplier f on f.Code = d.SupplCode " +
				" where 1=1  ";
		
		if(StringUtils.isNotBlank(item.getItemType1())){
			sql+=" and d.ItemType1 = ? ";
			list.add(item.getItemType1());
		}
		if(item.getDateFrom() != null){
			sql+=" and c.SignDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( item.getDateFrom()).getTime()));
		}
		if(item.getDateTo() != null){
			sql+=" and c.SignDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(item.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(item.getSupplCode())){
			sql+=" and d.SupplCode = ? ";
			list.add(item.getSupplCode());
		}
		if(StringUtils.isNotBlank(item.getCode())){
			sql+=" and d.Code = ? ";
			list.add(item.getCode());
		}
		if(StringUtils.isNotBlank(item.getIsOutSet())){
			sql+=" and a.isOutSet = ? ";
			list.add("1".equals(item.getIsOutSet()) ? "0":"1");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}

}
