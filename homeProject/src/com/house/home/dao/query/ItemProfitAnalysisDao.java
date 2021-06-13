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
public class ItemProfitAnalysisDao extends BaseDao {

	/**
	 * ItemProfitAnalysis分页信息
	 * 
	 * @param page
	 * @param custome
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select isnull(b.Profit,0)Profit,isnull(b.LineAmount,0)LineAmount,"
				+ "case when b.LineAmount=0 or b.LineAmount is null then 0  else round(b.Profit/b.LineAmount,4)*100 end profitPer"
				+ ",a.DocumentNo,a.address,a.CustType,a.status,a.SignDate,x1.note statusDescr,et.Desc1 custTypeDescr,a.lastupdate,a.code  "
				+ "from tCustomer a "
				+ "left join ( "
				+ "select a.CustCode, "
				+ "sum(case when a.IsOutSet = '1' then case when a.LineAmount <> 0 then  a.LineAmount-a.Qty*case when a.Cost = 0 " +
				" then a.UnitPrice else a.Cost end-a.ProcessCost else 0 end else case when " +
				" round(round(a.Qty * i.ProjectCost * a.Markup / 100, 0)+ a.ProcessCost, 0) <>0 then " +
				" round(round(a.Qty * i.ProjectCost * a.Markup / 100, 0)+ a.ProcessCost, 0) - a.Qty*case when a.Cost = 0 " +
				" then a.UnitPrice else a.Cost end-a.ProcessCost else 0 end end) Profit,  "
				+ "sum(case when a.IsOutSet = '1' then a.LineAmount else " +
				" round(round(a.Qty * i.ProjectCost * a.Markup / 100, 0)+ a.ProcessCost, 0) end * ";
		if ("ZC".equals(customer.getItemType1())
				&& "0".equals(customer.getIsServiceItem())) {
			sql += "b.ContainMain";
		} else if ("ZC".equals(customer.getItemType1())
				&& "1".equals(customer.getIsServiceItem())) {
			sql += "b.ContainMainServ";
		} else if ("RZ".equals(customer.getItemType1())) {
			sql += "b.ContainSoft";
		} else if ("JC".equals(customer.getItemType1())) {
			sql += "b.ContainInt";
		}else{
			sql+="0";
		}
		sql += ") LineAmount "
				+ "from tItemPlan a left join tCustomer b on a.custcode=b.code inner join tItem i on a.ItemCode=i.Code ";
		if ("JC".equals(customer.getItemType1())) {
			sql += "left join tIntProd c on a.IntProdPK=c.Pk ";
		}
		sql += "where 1=1 and  a.ItemType1=? and a.IsService=? ";
		if (StringUtils.isNotBlank(customer.getIsCupboard())) {
			sql += " and c.IsCupboard= ? ";
		}
		if(StringUtils.isNotBlank(customer.getIsOutSet())){
			sql+=" and a.IsOutSet = ? ";
		}
		sql += "group by a.CustCode "
				+ ") b on a.Code=b.CustCode "
				+ "left outer join txtdm x1  on x1.id='CUSTOMERSTATUS' and x1.cbm=a.Status "
				+ "left outer join tCusttype et on a.custtype=et.code "
				+ "where 1=1 ";
		
		list.add(customer.getItemType1());
		list.add(customer.getIsServiceItem());
		if(StringUtils.isNotBlank(customer.getIsOutSet())){
			list.add(customer.getIsOutSet());
		}

		if (StringUtils.isNotBlank(customer.getIsCupboard())) {
			list.add(customer.getIsCupboard());
		}
		if (customer.getSignDateFrom() != null) {
			sql += " and a.SignDate>= ? ";
			list.add(customer.getSignDateFrom());
		}
		if (customer.getSignDateTo() != null) {
			sql += " and a.SignDate< ? ";
			list.add(DateUtil.addInteger(customer.getSignDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.address like ?  ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getStatus())) {
			String str = SqlUtil.resetStatus(customer.getStatus());
			sql += " and a.status in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			String str = SqlUtil.resetStatus(customer.getCustType());
			sql += " and a.custType in (" + str + ")";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  order by a.LastUpdate desc,a.Code desc";
		}
		Page<Map<String, Object>> pageMap=this.findPageBySql(page, sql, list.toArray());
		List<Map<String, Object>> resultList= pageMap.getResult();
		for (int i = 0; i < resultList.size(); i++) {
			if("0.0".equals(resultList.get(i).get("lineamount").toString())){
				resultList.get(i).put("profit", 0);
			}
			resultList.get(i).put("profitper", resultList.get(i).get("profitper").toString()+"%");
			resultList.get(i).put("signdate", resultList.get(i).get("signdate").toString().substring(0,11));
		}
		pageMap.setResult(resultList);
		return pageMap;
	}

}
