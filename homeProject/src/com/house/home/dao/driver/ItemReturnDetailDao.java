package com.house.home.dao.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemReturnDetail;
import com.house.home.entity.insales.ItemAppSendDetail;

@SuppressWarnings("serial")
@Repository
public class ItemReturnDetailDao extends BaseDao {

	/**
	 * ItemReturnDetail分页信息
	 * 
	 * @param page
	 * @param itemReturnDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemReturnDetail itemReturnDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemReturnDetail a where 1=1 ";

		if (itemReturnDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemReturnDetail.getPk());
		}
		if (StringUtils.isNotBlank(itemReturnDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(itemReturnDetail.getNo());
		}
		if (itemReturnDetail.getAppDtpk() != null) {
			sql += " and a.AppDTPK=? ";
			list.add(itemReturnDetail.getAppDtpk());
		}
		if (StringUtils.isNotBlank(itemReturnDetail.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemReturnDetail.getItemCode());
		}
		if (itemReturnDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemReturnDetail.getQty());
		}
		if (StringUtils.isNotBlank(itemReturnDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemReturnDetail.getRemarks());
		}
		if (StringUtils.isNotBlank(itemReturnDetail.getArriveNo())) {
			sql += " and a.ArriveNo=? ";
			list.add(itemReturnDetail.getArriveNo());
		}
		if (itemReturnDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemReturnDetail.getLastUpdate());
		}
		if (StringUtils.isNotBlank(itemReturnDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemReturnDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemReturnDetail.getExpired())
				|| "F".equals(itemReturnDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(itemReturnDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemReturnDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getReturnDetailById(String no) {
		String sql = "select  a.custCode,c.number , e.descr, b.Address,a.Date,a.status,c.NameChi,c.Phone from tItemReturn a "
				+ "inner join tCustomer b on a.CustCode=b.Code "
				+ "inner join tEmployee c on b.ProjectMan=c.Number INNER JOIN  tItemType1 e ON a.ItemType1=e.Code where a.No=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getMaterialList(String no) {
		String sql = "select h.Descr, a.pk,a.qty,a.realQty,c.descr,d.descr uom, case when e.SendType=1 then f.address else g.address end address, "
				+ " case when  a.ArriveNo is not null then '已退货' else '待退货' end "
				+ "status,e.sendDate from tItemReturnDetail a "
				+ "inner join tItemAppDetail b on a.AppDTPK=b.pk "
				+ "inner join titem c on b.ItemCode=c.Code "
				+ "inner join tItemApp e on b.No=e.No "
				+ "left join tUOM d on c.Uom=d.code "
				+ "left join tSupplier f on e.SupplCode=f.Code "
				+ "left join tWareHouse g on e.WHCode=g.Code INNER JOIN  tItemType2 h ON c.ItemType2 =h.Code "
				+ "where a.No=?";
		return this.findBySql(sql, new Object[] { no });

	}

	public List<Map<String, Object>> getReturnMaterial(String no) {
		String sql = "select a.pk,a.qty,c.descr,d.descr uom,a.realQty ,case when  a.ArriveNo is not null then '已退货' else '待退货' end "
				+ "status from tItemReturnDetail a "
				+ "inner join tItemAppDetail b on a.AppDTPK=b.pk "
				+ "inner join titem c on b.ItemCode=c.Code "
				+ "left join tUOM d on c.Uom=d.code  where a.No=? and a.ArriveNo is null";

		return this.findBySql(sql, new Object[] { no });
	}

	public List<Map<String, Object>> getReturnMaterialNotIn(String no, String pk) {
		String[] arr = pk.split(",");
		String sql = "select a.pk,a.qty,c.descr,d.descr uom, case when  a.ArriveNo is not null then '已退货' else '待退货' end "
				+ "status from tItemReturnDetail a "
				+ "inner join tItemAppDetail b on a.AppDTPK=b.pk "
				+ "inner join titem c on b.ItemCode=c.Code "
				+ "left join tUOM d on c.Uom=d.code  where a.No=? and a.ArriveNo is null and a.PK not in (";
		Object[] obj = new Object[1 + arr.length];
		obj[0] = no;
		for (int i = 0; i < arr.length; i++) {
			sql += "?,";
			obj[i + 1] = arr[i];
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		return this.findBySql(sql, obj);
	}

	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id) {

		String sql = "select t.pk,a.itemCode,b.descr itemCodeDescr,a.qty,c.descr uom,"
				+ "fa.descr fixAreaDescr,a.remarks,t.sendQty,a.appDtpk,t.qty oldQty,m.sendDate,t.cost  "
				+ "from tItemReturnDetail a "
				+ "inner join tItemAppDetail t on a.appdtPk=t.pk "
				+ "inner join tItemApp m on t.no=m.no "
				+ "left join tFixArea fa on t.FixAreaPK=fa.PK "
				+ "left join tItem b on a.itemCode=b.code "
				+ "left join tuom c on b.uom=c.code "
				+ "where a.no=? order by a.itemcode,m.senddate";
		return this.findPageBySql(page, sql, new Object[] { id });
	}

	public Map<String, Object> getByPk(int pk) {
		String sql = "select a.pk,a.no,a.appDtpk,a.arriveNo,a.remarks,a.itemCode,b.descr itemCodeDescr,a.qty,c.descr uom "
				+ "from tItemReturnDetail a "
				+ "left join tItem b on a.itemCode=b.code "
				+ "left join tuom c on b.uom=c.code " + "where a.pk=? ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { pk });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * 退货详情
	 * 
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String, Object>> findReturnDetailByNo(
			Page<Map<String, Object>> page, ItemReturnDetail itemReturnDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select  a.No ,a.Qty ,A.RealQty,b.ItemCode ,c.descr Itemdescr ,d.descr ItemType1descr ,"
				+ "h.ArriveDate,h.address ,h.DriverRemark ,dv.Namechi DriverName ,u.Descr uomDescr, "
				+ "( case when e.SendType = '1' then g.address when e.SendType = '2' then w.address end ) returnAddress, "
				+ "round(c.PerWeight*a.qty,2) totalPerWeight , "
				+ "cm.address  lpaddress, ey.Namechi AppCZYName ,ey.phone,ir.Date AppDate,ir.CompleteDate SendDate "
				+ "from    tItemReturnDetail a "
				+ "left outer join tItemAppDetail b on b.PK = a.AppDTPK "
				+ "left outer join tItem c on c.code = b.ItemCode  "
				+ "left outer join tItemtype1 d on d.code = c.ItemType1 "
				+ "left outer join tUom u on u.code = c.uom  "
				+ "left outer join titemapp e on e.no = b.no  "
				+ "left outer join tWareHouse w on w.code = e.WHCode "
				+ "left outer join tSupplier g on g.code = e.SupplCode "
				+ "left outer join tItemReturnArrive h on h.no = a.arriveNo "
				+ "left outer join tDriver dv on dv.Code = h.DriverCode "
				+ "left outer join tItemReturn ir on ir.No = a.No "
				+ "left outer join tCZYBM  cb  on Cb.CZYBH=ir.AppCZY "
				+ "left outer join tCustomer cm on cm.code = ir.CustCode "
				+ "left outer join tEmployee ey on ey.Number=cb.EMNum "
				+ "where a.no=? ";
		list.add(itemReturnDetail.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
}
