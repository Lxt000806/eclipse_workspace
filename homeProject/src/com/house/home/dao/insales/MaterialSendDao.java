package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemApp;

@SuppressWarnings("serial")
@Repository
public class MaterialSendDao extends BaseDao {

	/**
	 * ItemApp分页信息
	 * 
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		
		String sendDateFromWhere = "";
		if (itemApp.getSendDateFrom() != null){
			sendDateFromWhere = " and a.SendDate >= ? ";
			list.add(itemApp.getSendDateFrom());
		} 
		
		String sendDateToWhere = "";
		if (itemApp.getSendDateTo() != null){
			sendDateToWhere += " and a.SendDate<= ? ";
			list.add(new java.util.Date(itemApp.getSendDateTo().getTime()+1000*60*60*24-1));
		}
		
		String sql = "select i.Code ItemCode,i.Descr ItemDescr,it2.Descr ItemType2Descr,u.Descr UomDescr, "
				+ " i.AllQty,isnull(b.SendQty,0) SendQty,isnull(c.ReqQty,0) ReqQty,isnull(d.yqrXqqty,0)yqrXqqty "
				+ " from tItem i"
				+ " left join tItemType2 it2 on i.ItemType2=it2.Code"
				+ " left join tUom u on i.UOM=u.Code"
				+ " left join (select  b.ItemCode,"
				+ "   sum(case when a.Type = 'S' then b.SendQty else b.SendQty * -1 end) SendQty"
				+ "   from tItemApp a"
				+ "   inner join tItemAppDetail b on a.No = b.No"
				+ "   where a.Status in ('SEND', 'RETURN') " + sendDateFromWhere + sendDateToWhere
				+ "   group by b.ItemCode"
				+ " ) b on i.Code = b.ItemCode"
				+ " left join (select  b.ItemCode, sum(b.Qty - b.SendQty) ReqQty"
				+ "   from tCustomer a"
				+ "   inner join tItemReq b on a.code = b.CustCode"
				+ "   where a.Status = '4' and b.Qty - b.SendQty > 0"
				+ "   group by b.ItemCode"
				+ " ) c on i.Code=c.ItemCode "
				+ "left join (select d.Code,sum(yqrXqqty)yqrXqqty from( "
				+ "	  select c.Code,case when exists " 
				+ "	  (select 1 from dbo.tConfItemTypeDt cfd " 
				+ "	  inner join tCustItemConfirm cif on cfd.ConfItemType=cif.ConfItemType " 
				+ "	  where cif.CustCode=a.code and c.ItemType2=cfd.ItemType2 and (cfd.ItemType3 is null or cfd.ItemType3='' or c.ItemType3=cfd.ItemType3) " 
				+ "	  and cif.ItemConfStatus  in ('2','3')) then isnull(b.qty-b.SendQty,0) else 0 end yqrXqqty "
				+ "	  from tItem c "
				+ "	  inner join titemreq b on b.ItemCode=c.Code and qty-b.SendQty>0  "
				+ "	  inner join tCustomer a on a.Code=b.CustCode and a.Status='4' and a.ConstructStatus='1' "
				+ "   )d group by d.Code" 
				+ " ) d on c.ItemCode=d.Code "
				+ " where i.SupplCode = ? " 
				+ " and (i.AllQty<>0 or isnull(b.SendQty,0)<>0 or isnull(c.ReqQty,0)<>0)";
		
		list.add(itemApp.getSupplCode());
 
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by b.SendQty desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}

