package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchArr;

@SuppressWarnings("serial")
@Repository
public class PurchArrDao extends BaseDao {

	/**
	 * PurchArr到货明细分页信息
	 * 
	 * @param page
	 * @param purchArr
	 * @return
	 */
	public Page<Map<String,Object>> findPurchArrPageBySql(Page<Map<String,Object>> page, PurchArr purchArr) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select " +
				" i.cost,p.CustCode,c.Address,pa.No,pa.PUNo,pa.Date,p.ItemType1,it1.Descr ItemType1Descr,it2.Descr ItemType2Descr, " +
				"  p.WHCode,wh.Desc1 WHDescr,pa.Remarks,pd.ITCode,d.Descr ITDescr,pdArr.ArrivQty,u.Descr UnitDescr,pd.QtyCal,tb.Descr SqlCodeDescr" +
				" from tPurchArr pa" +
				"  inner join tPurchase p on p.No=pa.PUNo" +
				"  left join tCustomer c on c.Code=p.CustCode" +
				" left join tItemType1 it1 on it1.Code=p.ItemType1" +
				" left join tWareHouse wh on wh.Code=p.WHCode" +
				" left join tPurchArrDetail pdArr on pa.no=pdArr.pano" +
				" left join tPurchaseDetail pd on pdArr.refpk=pd.pk " +
				" left join tItem d on pd.ITCode = d.Code " +
				"  left join tItemType2 it2 on it2.Code = d.ItemType2 " +
				" left join tBrand tb on tb.Code = d.sqlCode" +
				" left join tUOM u on d.UOM = u.Code " +
				" left join titem i on i.Code=pdArr.ITCode " +
				" where 1=1";

		if (StringUtils.isNotBlank(purchArr.getNo())) {
			sql += " and pa.No = ? ";
			list.add(purchArr.getNo());
		}
		if (StringUtils.isNotBlank(purchArr.getItCode())) {
			sql += " and pd.itCode = ? ";
			list.add(purchArr.getItCode());
		}
		if (StringUtils.isNotBlank(purchArr.getPuno())) {
			sql += " and pa.PUNo = ? ";
			list.add(purchArr.getPuno());
		}
		
		if (StringUtils.isNotBlank(purchArr.getRemarks())) {
			sql += " and pa.remarks like ? ";
			list.add("%"+purchArr.getRemarks()+"%");
		}
		
		if (StringUtils.isNotBlank(purchArr.getCustCode())) {
			sql += " and p.custCode = ? ";
			list.add(purchArr.getCustCode());
		}
		if (StringUtils.isNotBlank(purchArr.getItemType1())) {
			sql += " and p.itemType1 = ? ";
			list.add(purchArr.getItemType1());
		}
		if (StringUtils.isNotBlank(purchArr.getWhcode())) {
			sql += " and p.whcode = ? ";
			list.add(purchArr.getWhcode());
		}
		
		if (purchArr.getDateFrom() != null) {
			sql += " and pa.Date>= ? ";
			list.add(purchArr.getDateFrom());
		}
		if (purchArr.getDateTo() != null) {
			sql += " and pa.Date-1<= ? ";
			list.add(purchArr.getDateTo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by pa." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by pa.No desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}
	/**
	 * PurchArr分页信息
	 * 
	 * @param page
	 * @param purchArr
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchArr purchArr) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select (select sum(apportionFee) apportionFee from tPurchArrDetail where Pano = a.No ) purchFee ,b.Desc1 whCodeDescr,a.* from tPurchArr a " +
				" left join tWareHouse b on a.arriveWHCode = b.code " +
				" where 1=1 ";

    	if (StringUtils.isNotBlank(purchArr.getNo())) {
			sql += " and a.No=? ";
			list.add(purchArr.getNo());
		}
    	if (StringUtils.isNotBlank(purchArr.getPuno())) {
			sql += " and a.PUNo=? ";
			list.add(purchArr.getPuno());
		}
    	if (purchArr.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(purchArr.getDateFrom());
		}
		if (purchArr.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(purchArr.getDateTo());
		}
    	if (StringUtils.isNotBlank(purchArr.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(purchArr.getRemarks());
		}
    	if (purchArr.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(purchArr.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(purchArr.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchArr.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(purchArr.getExpired()) || "F".equals(purchArr.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(purchArr.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(purchArr.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByPuno(
			Page<Map<String, Object>> page, String puno) {
		String sql = "select * from (select a.no,a.PUNo,a.Date,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,"
				+"a.ActionLog from tPurchArr a where a.puno=? ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.date";
		}

		return this.findPageBySql(page, sql, new Object[]{puno});
	}

}

