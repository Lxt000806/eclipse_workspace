package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemReq;
@SuppressWarnings("serial")
@Repository
public class ItemReqQueryDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemReq itemReq) {
		List<Object> list = new ArrayList<Object>();
		String sql=" select c.Address,c.Descr CustDescr,c.Mobile1,b.ItemType2,t2.Descr ItemType2Descr,a.FixAreaPK, "
                  +" t3.Descr FixAreaDescr,b.Descr ItemDescr,b.Uom,u.Descr UnitDescr,b.Price NewestPrice,b.Cost NewestCost,"
                  +" a.Qty,a.SendQty,a.Qty-a.SendQty as lessQty,a.UnitPrice,a.Cost,a.Remark,ct.Desc1 CustTypeDescr, "
                  +" case when exists(select 1 "
                  +"         from tCustItemConfirm in_a "
                  +"         left join tConfItemTypeDt in_b on in_a.ConfItemType = in_b.ConfItemType "
                  +"         where in_a.CustCode = a.CustCode "
                  +"         and in_b.ItemType2 = b.ItemType2 "
                  +"         and (in_b.ItemType3 = b.ItemType3 or in_b.ItemType3 is null or in_b.ItemType3 = '') "
                  +"         and in_a.ItemConfStatus = '2') "
                  +"     then a.Qty else 0 end as ConfirmedQty "
                  +" from tItemReq a "
                  +" left outer join tItem b on a.ItemCode=b.Code "
                  +" left outer join tCustomer c on a.CustCode=c.Code "
                  +" left outer join tUOM u on u.Code = b.UOM "
                  +" left outer join tItemType2 t2 on b.ItemType2=t2.Code "
                  +" left outer join tFixArea t3 on a.FixAreaPK=t3.PK "
                  +" left outer join tCusttype ct on c.CustType=ct.Code "
                  +" where 1=1 ";
		if("1".equals(itemReq.getGroupBy())){
			sql="select distinct c.Code,c.Address,c.Descr as CustDescr,c.Mobile1,ct.Desc1 as CustTypeDescr"
	                   + " from tItemReq a "
	                   + " left outer join tItem b on a.ItemCode=b.Code "
	                   + " left outer join tCustomer c on a.CustCode=c.Code "
	                   + " left outer join tUOM u on u.Code = b.UOM "
	                   + " left outer join tItemType2 t2 on b.ItemType2=t2.Code "
	                   + " left outer join tFixArea t3 on a.FixAreaPK=t3.PK "
	                   + " left outer join tCusttype ct on c.CustType=ct.Code "
	                   + " where 1=1 ";
		}
		String[] sendStatusItems=itemReq.getSendStatus().split(",");
		String sendStatusSql="";
		for(String sendStatus:sendStatusItems){
			if("0".equals(sendStatus)){
				sendStatusSql+=" or (a.Qty > 0 and a.SendQty = 0) ";
			}else if("1".equals(sendStatus)){
				sendStatusSql+=" or (a.SendQty > 0 and a.SendQty >= a.Qty) ";
			}else if("2".equals(sendStatus)){
				sendStatusSql+=" or (a.SendQty > 0 and a.SendQty < a.Qty) ";
			}
		}
		String[] arr=itemReq.getItemRight().split(",");
	    String itemRight="";
	    for(String str:arr) itemRight+="'"+str+"',";
	    sql+="  and a.ItemType1 in("+itemRight.substring(0,itemRight.length()-1)+") ";
		if(StringUtils.isNotBlank(itemReq.getSendStatus())){
			sql+=" and (1<>1 "+sendStatusSql+") ";
		}
    	if (StringUtils.isNotBlank(itemReq.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemReq.getItemCode());
		}
    	if (StringUtils.isNotBlank(itemReq.getSqlCode())) {
			sql += " and b.SqlCode=? ";
			list.add(itemReq.getSqlCode());
		}
    	if (StringUtils.isNotBlank(itemReq.getCustomerStatus())) {
    		sql += " and c.Status in (" + itemReq.getCustomerStatus() + ")";
		}
    	
    	
		if (StringUtils.isBlank(itemReq.getExpired()) || "F".equals(itemReq.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if(StringUtils.isNotBlank(itemReq.getIsProm())){
    		sql+=" and b.IsProm=? ";
    		list.add(itemReq.getIsProm());
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			if("1".equals(itemReq.getGroupBy())){
				sql += " order by c.Code";
			}else{
				sql += " order by a.CustCode,a.ItemCode";
			}
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}
