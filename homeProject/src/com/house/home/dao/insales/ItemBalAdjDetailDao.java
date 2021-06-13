package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemWHBal;

@SuppressWarnings("serial")
@Repository
public class ItemBalAdjDetailDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemBalAdjDetail itemBalAdjDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK, a.IBHNo, a.ITCode, a.AdjQty, a.Qty, a.Remarks, a.LastUpdate,"
				+ " a.LastUpdatedBy, a.Expired, a.ActionLog,d.qtycal allQty,d.avgCost cost, "
				+ " a.cost adjCost, a.AftCost,  u.Descr uomdescr,"
				+ " b.Descr itdescr from tItemBalAdjDetail a "
				+ " left join tItem b on a.ITCode=b.Code "
				+ " left outer join tUom u on b.Uom = u.Code "
				+ " left join tItemBalAdjHeader c on c.no=a.ibhno "
				+ " left join tItemWHBal d on d.whcode=c.whcode and d.itCode=a.itCode "
				+ " where 1=1 ";

		if (itemBalAdjDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemBalAdjDetail.getPk());
		}
		if (itemBalAdjDetail.getIbhNo() != null) {
			sql += " and a.IBHNo=? ";
			list.add(itemBalAdjDetail.getIbhNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.pk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> detailQuery(
			Page<Map<String, Object>> page, ItemBalAdjDetail itemBalAdjDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK, a.IBHNo, a.ITCode, a.AdjQty, a.Qty, a.Remarks, a.LastUpdate,"
				+ " a.LastUpdatedBy, a.Expired, a.ActionLog,d.qtycal allQty,d.avgCost cost,f.Note headerStatus,h.descr itemtype1descr, "
				+ " a.cost adjCost, a.AftCost,u.Descr uomdescr,c.AppDate,c.ConfirmDate,c.WHCode,c.Date,g.desc1 whcodedescr,"
				+ " c.adjType,c.Remarks headerRemarks,e.Note headerType, j.NOTE AdjReasonDescr, b.Descr itdescr,b.itemType1,i.Descr itemtype2descr from tItemBalAdjDetail a "
				+ " left join tItem b on a.ITCode=b.Code "
				+ " left outer join tUom u on b.Uom = u.Code "
				+ " left join tItemBalAdjHeader c on c.no=a.ibhno "
				+ " left join tItemWHBal d on d.whcode=c.whcode and d.itCode=a.itCode "
				+ " left outer join tXTDM e on Rtrim(c.AdjType)=e.CBM and e.ID='ADJTYP' "
				+ " left outer join tXTDM f on c.Status=f.CBM and f.ID='BALADJSTATUS' "
				+ " left outer join tWareHouse g on c.WHCode =g.code"
				+ " left outer join tItemType1 h on b.itemType1 =h.code"
				+ " left outer join tItemType2 i on b.itemType2=i.code "
				+ " left outer join tXTDM j on c.AdjReason=j.CBM and j.ID='AdjReason' "
				+ " where 1=1 ";
		if (StringUtils.isNotBlank(itemBalAdjDetail.getIbhNo())) {
			sql += " and a.ibhno like ? ";
			list.add("%"+itemBalAdjDetail.getIbhNo()+"%");
		}
		if (StringUtils.isNotBlank(itemBalAdjDetail.getAdjReason())) {
			sql += " and c.AdjReason=? ";
			list.add(itemBalAdjDetail.getAdjReason());
		}
		if (StringUtils.isNotBlank(itemBalAdjDetail.getItemType1())) {
			sql += " and b.itemType1=? ";
			list.add(itemBalAdjDetail.getItemType1());
		}
		if(StringUtils.isNotBlank(itemBalAdjDetail.getItCode())){
			sql+=" and a.ItCode = ? ";
			list.add(itemBalAdjDetail.getItCode());
			
		}
		if (StringUtils.isNotBlank(itemBalAdjDetail.getWHCode())) {
			sql += " and g.desc1 like ? ";
			list.add("%" + itemBalAdjDetail.getWHCode() + "%");
		}
		if (StringUtils.isNotBlank(itemBalAdjDetail.getHeaderRemarks())) {
			sql += " and c.Remarks like ? ";
			list.add("%" + itemBalAdjDetail.getHeaderRemarks() + "%");
		}
		if (itemBalAdjDetail.getDateFrom() != null) {
			sql += " and c.Date>=? ";
			list.add(itemBalAdjDetail.getDateFrom());
		}
		if (itemBalAdjDetail.getDateTo() != null) {
			sql += " and c.Date< ? ";
			list.add(DateUtil.addInteger(itemBalAdjDetail.getDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemBalAdjDetail.getHeaderStatus())) {
			sql += " and c.Status in " + "('"
					+ itemBalAdjDetail.getHeaderStatus().replaceAll(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(itemBalAdjDetail.getHeaderType())) {
			sql += " and c.adjType in " + "('"
					+ itemBalAdjDetail.getHeaderType().replaceAll(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.pk";
		}
		System.out.println(sql);
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findSupplierPageBySql(
			Page<Map<String, Object>> page, ItemBalAdjDetail itemBalAdjDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select b.SupplCode,c.Descr supplierDescr,SUM(a.Qty*a.AftCost)allCost  from tItemBalAdjDetail a "
				+ " left join titem b on a.ITCode=b.Code "
				+ " left join tSupplier c on c.Code=b.SupplCode"
				+ "  where 1=1 ";
		if (StringUtils.isNotBlank(itemBalAdjDetail.getIbhNo())) {
			sql += " and a.IbhNo =?";
			list.add(itemBalAdjDetail.getIbhNo());
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " group by b.SupplCode,c.Descr order by a."
					+ page.getPageOrderBy() + " " + page.getPageOrder();
		} else {
			sql += "  group by b.SupplCode,c.Descr order by b.supplcode ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAvgCost(String whCode, String itCode) {
		String sql = "select QtyCal,AvgCost from tItemWHBal a where  a.whCode=? and a.itCode=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {
				whCode, itCode });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getPosiQty(String whCode, String itCode) {
		String sql = "select isnull(sum(QtyCal),0) QtyCal from tWHPosiBal a "
				+ "  inner join tWareHousePosi b on a.WHPPk=b.PK "
				+ " where b.WHCode=? and a.ItCode=?  ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {
				whCode, itCode });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAllQty(String whCode, String itCode) {
		String sql = " select isnull(QtyCal,0)QtyCal from tItemWHBal a where whcode=? and itCode=?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {
				whCode, itCode });
		if (list != null && list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			return list.get(0);
		}
		return null;
	}

}
