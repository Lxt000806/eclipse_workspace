package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class ItemReqDetailQueryDao extends BaseDao{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		                                                                                                        
		String sql = " select *from (select iad.projectCost,bb.SendQtyByWH,cc.SendQtyBySpl,c.area ,c.DocumentNo,c.Address,c.Code custCode ," +
				" j.descr intDescr,j.Descr, j.IsCupboard, xt2.NOTE IsCupboardDescr,a.ItemCode,e.Descr itemDescr" +
				",f.descr item2Descr,ct.desc1 custtypedescr,d.descr item1Descr,b.descr areadescr ,xt.note isOutsideDescr,c.custcheckdate ," +
				" a.Qty,a.SendQty,h.Descr uomdescrcost,a.UnitPrice,a.cost,a.qty*a.cost costAmount ,a.BefLineAmount*a.markUp/100 amount ,a.Markup,a.Remark Remarks,a.BefLineAmount," +
				" a.LineAmount,a.discCost,g.descr sqldescr,h.descr uomdescr,a.processCost,a.LineAmount-a.discCost afterDiscCost," +
				" k.descr itemSetDescr,l3.NameChi IntDesigner,m3.NameChi CupDesigner " +
				" from tItemReq a " +
				" left join tFixArea b on b.pk=a.FixAreaPK" +
				" left join tcustomer c on c.code=a.CustCode " +
				" left join tcustType ct on ct.code=c.custType " +
				" left join titemtype1 d on d.Code=a.ItemType1" +
				" left join titem e on e.code=a.ItemCode " +
				" left join tItemType2 f on f.Code=e.ItemType2 " +
				" left join tBrand g on g.code=e.SqlCode" +
				" left join tUOM h on h.code=e.Uom " +
				" left join tIntProd j on j.PK=a.IntProdPK " +
				" left join tItemSet k on k.no =a.itemSetNo " +
				
				" left join (select max(pk) pk,ReqPK from tItemAppDetail group by ReqPK)iad_a on iad_a.reqPk=a.Pk"+
				" left join tItemAppDetail iad on iad.PK=iad_a.pk " 
				
	            +" left join txtdm xt on a.isoutset=xt.cbm and xt.id='YESNO' "
	            +" left join txtdm xt2 on j.IsCupboard=xt2.cbm and xt2.id='YESNO' "
				+" left join (select sum(case when bb.type='S' then aa.SendQty else -aa.SendQty end) SendQtyByWH,aa.ReqPK "
				+" from  tItemAppDetail aa "
				+" left join tItemApp bb on bb.No=aa.No "
				+" where bb.SendType='2' group by aa.ReqPk) bb "
				+" on bb.ReqPK=a.PK "
				
				+"left join (select sum(case when bb.type='S' then aa.SendQty else -aa.SendQty end) SendQtyBySpl,aa.ReqPK "
				+" from  tItemAppDetail aa "
				+" left join tItemApp bb on bb.No=aa.No "
				+" where bb.SendType='1' group by aa.ReqPk) cc "
				+" on cc.ReqPK=a.PK "
				+" left join (" //集成设计师、橱柜设计师 add by zb on 20200312
			    +"   select a.CustCode,max(a.PK) PK "
			    +"   from tCustStakeholder a "
			    +"   where a.Role='11' and a.Expired='F' "
			    +"   group by a.CustCode "
			    +" )l on l.CustCode=a.CustCode "
			    +" left join tCustStakeholder l2 on l2.PK=l.PK "
			  	+" left join tEmployee l3 on l3.number=l2.EmpCode "
				+" left join ("
			    +"   select a.CustCode,max(a.PK) PK "
			    +"   from tCustStakeholder a "
			    +"   where a.Role='61' and a.Expired='F' "
			    +"   group by a.CustCode "
			    +" )m on m.CustCode=a.CustCode "
			    +" left join tCustStakeholder m2 on m2.PK=m.PK "
			  	+" left join tEmployee m3 on m3.number=m2.EmpCode "
				+" where 1=1 "
				+" and e.itemType1 in " + "('"+customer.getItemRight().replace(",", "','" )+ "') ";
		if(StringUtils.isNotBlank(customer.getItemType1())){
			sql+=" and e.itemType1= ? ";
			list.add(customer.getItemType1());
		}
		if (StringUtils.isNotBlank(customer.getItemType2())) {
			sql += " and e.ItemType2 = ? ";
			list.add(customer.getItemType2());
		}
		if (StringUtils.isNotBlank(customer.getIsCupboard())) {
			sql += " and j.IsCupboard = ? ";
			list.add(customer.getIsCupboard());
		}
		if(StringUtils.isNotBlank(customer.getIsServiceItem())){
			if("1".equals(customer.getIsServiceItem())){
				sql+=" and a.IsService = '1' ";
			}else if("0".equals(customer.getIsServiceItem())){
				sql+=" and a.IsService='0' ";
			}
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and c.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		if(customer.getDateFrom()!=null){
			sql+=" and c.custCheckDate >= ? ";
			list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			sql+=" and c.custCheckDate <dateAdd(d,1,?)";
			list.add(customer.getDateTo());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql+=" and c.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "') ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a where (a.lineamount<>0 or a.qty<>0) order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a where (a.lineamount<>0 or a.qty<>0) order by a.custcode ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	
	
	
	
	
	
}
