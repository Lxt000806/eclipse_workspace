package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.Purchase;

@SuppressWarnings("serial")
@Repository
public class PurchasePartArriveDao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();

		String sql = "";
		if("1".equals(purchase.getStatistcsMethod())){
			sql += "  select a.*,b.Descr ItemDescr,c.Descr ItemType2Descr,d.AvgCost, "
			        +"d.QtyCal "
					+"from ( "
					+" 	select in_a.ITCode ItemCode,sum(in_a.ArrivQty) ArrivQty, sum(in_a.ArrivQty * in_e.Cost) TotalCost from tPurchArrDetail in_a "
					+" 	left join tPurchaseDetail in_b on in_a.RefPk = in_b.PK "
					+" 	left join tPurchase in_c on in_b.PUNo = in_c.No "
					+" 	left join tItem in_d on in_a.ITCode = in_d.Code "
					+"  left join tItemTransaction in_e on in_a.ITCode = in_e.ITCode and in_a.ArrivQty = in_e.TrsQty and in_b.PUNo = in_e.Document and CONVERT(VARCHAR(11),in_a.LastUpdate,120) = CONVERT(VARCHAR(11),in_e.Date,120) "
					+"  left join tPurchase in_f on in_f.No = in_c.OldPUNo "
					+" 	where ((in_c.Type='S' and in_c.Status = 'OPEN') or (in_c.Type='R' and in_c.Status <> 'CANCEL' and in_f.Status='OPEN')) "
					+"  and in_c.WHCode=? and in_d.ItemType1 = ? and in_c.ArriveDate < ? "
					+" 	group by in_a.ITCode "
					+" ) a "
					+" left join tItem b on a.ItemCode = b.Code "
					+" left join tItemType2 c on b.ItemType2 = c.Code "
					+" left join tItemWHBal d on a.ItemCode = d.ITCode and d.WHCode = ? ";
			list.add(purchase.getWhcode());
			list.add(purchase.getItemType1());
			list.add(purchase.getArriveDate() != null ? purchase.getArriveDate() : new Date());
			list.add(purchase.getWhcode());
		}else {
			sql += "select e.Code ItemType2,e.Descr ItemType2Descr,sum(a.ArrivQty) ArrivQty,sum(a.ArrivQty*f.Cost) TotalCost from tPurchArrDetail a "
					+" left join tPurchaseDetail b on a.RefPk = b.PK "
					+" left join tPurchase c on b.PUNo = c.No "
					+" left join tItem d on a.ITCode = d.Code "
					+" left join tItemType2 e on d.ItemType2 = e.Code "
					+" left join tItemTransaction f on a.ITCode = f.ITCode and a.ArrivQty = f.TrsQty and b.PUNo = f.Document and CONVERT(VARCHAR(11),a.LastUpdate,120) = CONVERT(VARCHAR(11),f.Date,120) "
					+" left join tPurchase g on g.No = c.OldPUNo "
					+" where ((c.Type='S' and c.Status = 'OPEN') or (c.Type='R' and c.Status <> 'CANCEL' and g.Status='OPEN')) "
					+" and c.WHCode=? and d.ItemType1 = ? and c.ArriveDate < ? "
					+" group by e.Code,e.Descr";
			list.add(purchase.getWhcode());
			list.add(purchase.getItemType1());
			list.add(purchase.getArriveDate() != null ? purchase.getArriveDate() : new Date());
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.ITCode ItemCode,d.Descr ItemDescr,c.No,e.Date ArriveDate,a.ArrivQty,f.Cost, "
				+" isnull(f.Cost, 0)*isnull(a.ArrivQty, 0) allCost "
				+" from tPurchArrDetail a "
				+" left join tPurchaseDetail b on a.RefPk = b.PK "
				+" left join tPurchase c on b.PUNo = c.No "
				+" left join tItem d on a.ITCode = d.Code "
				+" left join tPurchArr e on a.PANo = e.no "
				+" left join tItemTransaction f on a.ITCode = f.ITCode and a.ArrivQty = f.TrsQty and b.PUNo = f.Document and CONVERT(VARCHAR(11),a.LastUpdate,120) = CONVERT(VARCHAR(11),f.Date,120) "
				+" left join tPurchase g on g.No = c.OldPUNo "
				+" where ((c.Type='S' and c.Status = 'OPEN') or (c.Type='R' and c.Status <> 'CANCEL' and g.Status='OPEN')) "
				+" and c.WHCode=?  and c.ArriveDate < ? ";
		list.add(purchase.getWhcode());
		list.add(purchase.getArriveDate() != null ? purchase.getArriveDate() : new Date());
		if("1".equals(purchase.getStatistcsMethod())){
			sql += " and d.Code = ? ";
			list.add(purchase.getItCode());
		}else {
			sql += " and d.ItemType2 = ?";
			list.add(purchase.getItemType2());
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}

