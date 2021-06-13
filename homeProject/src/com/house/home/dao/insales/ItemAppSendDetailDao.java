package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppSendDetail;

@SuppressWarnings("serial")
@Repository
public class ItemAppSendDetailDao extends BaseDao {

	/**
	 * ItemAppSendDetail分页信息
	 * 
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppSendDetail itemAppSendDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemAppSendDetail a where 1=1 ";

    	if (itemAppSendDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemAppSendDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemAppSendDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(itemAppSendDetail.getNo());
		}
    	if (itemAppSendDetail.getRefPk() != null) {
			sql += " and a.RefPk=? ";
			list.add(itemAppSendDetail.getRefPk());
		}
    	if (itemAppSendDetail.getSendQty() != null) {
			sql += " and a.SendQty=? ";
			list.add(itemAppSendDetail.getSendQty());
		}
    	if (itemAppSendDetail.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(itemAppSendDetail.getCost());
		}
    	if (itemAppSendDetail.getAftQty() != null) {
			sql += " and a.AftQty=? ";
			list.add(itemAppSendDetail.getAftQty());
		}
    	if (itemAppSendDetail.getAftCost() != null) {
			sql += " and a.AftCost=? ";
			list.add(itemAppSendDetail.getAftCost());
		}
    	if (itemAppSendDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemAppSendDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemAppSendDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemAppSendDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemAppSendDetail.getExpired()) || "F".equals(itemAppSendDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemAppSendDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemAppSendDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String no) {
		String sql = "select * from (select a.pk,a.No,a.RefPk,a.SendQty,a.Cost,a.AftQty,a.AftCost,a.LastUpdate,a.LastUpdatedBy,a.Expired,"
				+ "a.ActionLog,b.descr itemDescr,c.itemCode from tItemAppSendDetail a "
				+ "left join tItemAppDetail c on a.RefPK=c.pk "
		        + "left join tItem b on c.Itemcode=b.Code "
		        + "where a.no=? ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.pk";
		}
		return this.findPageBySql(page, sql, new Object[]{no});
	}
	/**
	 * 送货详情
	 * 
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String,Object>> findSendDetailByNo(Page<Map<String,Object>> page, ItemAppSendDetail itemAppSendDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select  a.No,a.SendQty,b.ItemCode,c.descr  Itemdescr,d.descr ItemType1descr,u.Descr uomDescr,b.Remarks," 
        +" round(c.PerWeight*a.SendQty ,2) totalPerWeight "
        +" from tItemAppSendDetail a "
        +" left outer join tItemAppDetail b on b.PK=a.RefPk "
        +" left outer join  tItem c on c.code=b.ItemCode "
        +" left outer join tItemtype1 d on  d.code=c.ItemType1 "
        +" left outer join tUom u on u.code = c.uom "
        +" where a.no=?";
		list.add(itemAppSendDetail.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 已发货详情——app
	 * @author	created by zb
	 * @date	2019-9-21--下午3:45:53
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String, Object>> findDetailBySqlNoAPP(
			Page<Map<String, Object>> page, ItemAppSendDetail itemAppSendDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.PK,a.SendQty,a.AftQty,iad.ItemCode,fa.Descr fixareadescr, "
					+"i.Descr ItemDescr,i.SqlCode, b.Descr SqlDescr "
					+"from tItemAppSendDetail a  "
					+"left join tItemAppDetail iad on iad.PK=a.RefPk "
					+"left join tItem i on iad.ItemCode = i.Code "
					+"left join tFixArea fa on iad.FixAreaPK = fa.PK "
					+"left join tItemReq IR on IR.pk = iad.ReqPK "
					+"left join tBrand b on b.Code = i.SqlCode "
					+"where a.No=? and a.Expired='F' ";
		list.add(itemAppSendDetail.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
}

