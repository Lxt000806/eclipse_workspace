package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchArrDetail;

@SuppressWarnings("serial")
@Repository
public class PurchArrDetailDao extends BaseDao {

	/**
	 * PurchArrDetail分页信息
	 * 
	 * @param page
	 * @param purchArrDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchArrDetail purchArrDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,d.desc1 whDescr,t.descr itdescr from tPurchArrDetail a " +
				"left join tItem t on a.ITCode=t.code " +
				" left join tPurchaseDetail c on c.pk= a.refPk " +
				" left join tpurchase e on e.no =c.Puno" +
				" left join tWareHouse d on d.code = e.WhCode " +
				"where 1=1 ";

    	if (purchArrDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(purchArrDetail.getPk());
		}
    	if (StringUtils.isNotBlank(purchArrDetail.getPano())) {
			sql += " and a.PANo=? ";
			list.add(purchArrDetail.getPano());
		}
    	if (purchArrDetail.getRefPk() != null) {
			sql += " and a.RefPk=? ";
			list.add(purchArrDetail.getRefPk());
		}
    	if (StringUtils.isNotBlank(purchArrDetail.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(purchArrDetail.getItcode());
		}
    	if (purchArrDetail.getArrivQty() != null) {
			sql += " and a.ArrivQty=? ";
			list.add(purchArrDetail.getArrivQty());
		}
    	if (purchArrDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(purchArrDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(purchArrDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchArrDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(purchArrDetail.getExpired()) || "F".equals(purchArrDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(purchArrDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(purchArrDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByPano(
			Page<Map<String, Object>> page, String pano) {
		String sql = "select * from (select a.PK,a.PANo,a.RefPk,a.ITCode,a.ArrivQty,a.LastUpdate,a.LastUpdatedBy,"
				+"a.Expired,a.ActionLog,b.Descr ItemDescr from tPurchArrDetail a "
				+"left join tItem b on a.ItCode=b.Code "
				+"where a.pano=? ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}

		return this.findPageBySql(page, sql, new Object[]{pano});
	}

}

