package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemPreAppDetail;

@SuppressWarnings("serial")
@Repository
public class ItemPreAppDetailDao extends BaseDao {

	/**
	 * ItemPreAppDetail分页信息
	 * 
	 * @param page
	 * @param itemPreAppDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreAppDetail itemPreAppDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemPreAppDetail a where 1=1 ";

    	if (itemPreAppDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemPreAppDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemPreAppDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(itemPreAppDetail.getNo());
		}
    	if (StringUtils.isNotBlank(itemPreAppDetail.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemPreAppDetail.getItemCode());
		}
    	if (itemPreAppDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemPreAppDetail.getQty());
		}
    	if (itemPreAppDetail.getReqPk() != null) {
			sql += " and a.ReqPK=? ";
			list.add(itemPreAppDetail.getReqPk());
		}
    	if (itemPreAppDetail.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemPreAppDetail.getDateFrom());
		}
		if (itemPreAppDetail.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemPreAppDetail.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemPreAppDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemPreAppDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemPreAppDetail.getExpired()) || "F".equals(itemPreAppDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemPreAppDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemPreAppDetail.getActionLog());
		}
    	if (StringUtils.isNotBlank(itemPreAppDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemPreAppDetail.getRemarks());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id, String supplCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.*,b.descr itemCodeDescr,c.descr uom,e.descr fixAreaDescr,m.qty xdQty,xt.note xdStatus "
			+"from tItemPreAppDetail a "
			+"left join tItem b on a.itemCode=b.code "
			+"left join tuom c on b.uom=c.code "
			+"left join tItemReq d on a.reqPk=d.pk "
			+"left join tFixArea e on d.fixAreaPk=e.pk "
			+"left join tItemAppDetail m on a.pk=m.preAppDtPk "
			+"left join tItemApp n on m.no=n.no "
			+"left join txtdm xt on n.status=xt.cbm and xt.id='ITEMAPPSTATUS' "
			+"where a.no=? ";
		list.add(id);
		if (StringUtils.isNotBlank(supplCode)){
			sql += " and b.supplCode=? ";
			list.add(supplCode);
		}
		sql += " order by a.pk";
		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<ItemPreAppDetail> findByNo(String no) {
		String hql = "from ItemPreAppDetail a where a.no=?";
		return this.find(hql, new Object[]{no});
	}

	public Map<String,Object> getByPk(int pk) {
		String sql = "select a.*,b.descr itemCodeDescr,c.descr uom,m.qty xtQty,xt.note xtStatus "
				+"from tItemPreAppDetail a left join tItem b on a.itemCode=b.code "
				+"left join tItemAppDetail m on a.pk=m.preAppDtPk "
				+"left join tItemApp n on m.no=n.no "
				+"left join txtdm xt on n.status=xt.cbm and xt.id='ITEMAPPSTATUS' "
				+"left join tuom c on b.uom=c.code where a.pk=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		 
		return null;
	}

	public String existsChange(String no) {
		String sql = "select c.descr from tItemReq a inner join tItemPreAppDetail b on a.PK=b.ReqPK "
				+"inner join tItem c on a.itemCode=c.code "
				+"where exists(select 1 from tItemChg m,tItemChgDetail n where m.no=n.no and m.status='1' "
				+"and n.fixAreaPk=a.fixAreaPk and n.itemCode=a.itemCode) and b.no=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if (list!=null && list.size()>0){
			return (String) list.get(0).get("descr");
		}
		return "";
	}

	public List<Map<String, Object>> findSqlByNo(String no) {
		String sql = "select a.ItemCode,b.Descr itemCodeDescr,a.Qty,u.Descr Uom,a.Remarks "
		+"from tItemPreAppDetail a left join tItem b on a.ItemCode=b.Code "
		+"left join tUOM u on b.Uom=u.Code "
		+"where a.no=?";
		return this.findBySql(sql, new Object[]{no});
	}

	public List<Map<String, Object>> existsOutSetItem(String no) {
		String sql = "select 1 from tItemPreAppDetail a left join tItemReq b on a.ReqPK=b.PK  "
		+"where a.No=? and b.IsOutSet='1' ";
		return this.findBySql(sql, new Object[]{no});
	}
	
	public List<Map<String, Object>> existsInSetItem(String no) {
		String sql = "select 1 from tItemPreAppDetail a left join tItemReq b on a.ReqPK=b.PK  "
		+"where a.No=? and b.IsOutSet<>'1' ";
		return this.findBySql(sql, new Object[]{no});
	}
}

