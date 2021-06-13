package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PrePlanTempDetail;

@SuppressWarnings("serial")
@Repository
public class PrePlanTempDetailDao extends BaseDao {

	/**
	 * PrePlanTempDetail分页信息
	 * 
	 * @param page
	 * @param prePlanTempDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrePlanTempDetail prePlanTempDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  a.PK, a.No,a.FixAreaType,a.ItemTypeDescr,a.Remark,a.Qty,a.ProcessCost,a.LastUpdate,"
				+ " a.LastUpdatedBy, a.Expired, a.ActionLog,b.Descr FixAreaTypeDescr,c.Descr AlgorithmDescr,"
				+ " d.Descr ItemDescr,a.DispSeq,IsService,x1.note IsServiceDescr,a.Markup,a.Algorithm,a.ItemCode,"
				+ " a.isoutset,x2.note isoutsetdescr, a.custtypeitempk,a.cuttype,x3.note cuttypedescr,d.expired itemexpired, "
				+ " a.PaveType,x4.note pavetypedescr, case when a.CustTypeItemPK>0 and cti.PK is null  then 'T' else cti.Expired end CustTypeItemExpired,a.AlgorithmPer,a.AlgorithmDeduct, "
				+ " a.CanModiQty,x5.note canModiQtyDescr  "
				+ " from  tPrePlanTempDetail a   "
				+ " left outer join tFixAreaType b on b.code=a.FixAreaType "
				+ " left outer join tAlgorithm c on c.code=a.Algorithm "
				+ " left outer join tItem d on d.code=a.ItemCode "
				+ " left outer join txtdm x1 on x1.cbm=IsService  and x1.id='YESNO'"
				+ " left outer join txtdm x2 on x2.cbm=a.isoutset and x2.id='YESNO'"
				+ " left outer join txtdm x3 on x3.cbm=a.cuttype  and x3.id='CUTTYPE'"
				+ " left outer join txtdm x4 on x4.cbm=a.PaveType and x4.id='PAVETYPE'"
				+ " left outer join txtdm x5 on x5.cbm=a.CanModiQty and x5.id='YESNO'"
				+ " left outer join tCustTypeItem cti on cti.pk=a.CustTypeItemPK "
				+ " where 1=1 and a.No=?";
		list.add(prePlanTempDetail.getNo());
		if (prePlanTempDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(prePlanTempDetail.getPk());
		}
		if (StringUtils.isNotBlank(prePlanTempDetail.getFixAreaType())) {
			sql += " and a.FixAreaType=? ";
			list.add(prePlanTempDetail.getFixAreaType());
		}
		if (StringUtils.isNotBlank(prePlanTempDetail.getItemTypeDescr())) {
			sql += " and a.ItemTypeDescr=? ";
			list.add(prePlanTempDetail.getItemTypeDescr());
		}
		if (StringUtils.isNotBlank(prePlanTempDetail.getAlgorithm())) {
			sql += " and a.Algorithm=? ";
			list.add(prePlanTempDetail.getAlgorithm());
		}
		if (StringUtils.isNotBlank(prePlanTempDetail.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(prePlanTempDetail.getItemCode());
		}
		if (prePlanTempDetail.getIsService() != null) {
			sql += " and a.IsService=? ";
			list.add(prePlanTempDetail.getIsService());
		}
		if (StringUtils.isNotBlank(prePlanTempDetail.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(prePlanTempDetail.getRemark());
		}
		if (prePlanTempDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(prePlanTempDetail.getQty());
		}
		if (prePlanTempDetail.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(prePlanTempDetail.getDispSeq());
		}
		if (StringUtils.isNotBlank(prePlanTempDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prePlanTempDetail.getLastUpdatedBy());
		}
		if (prePlanTempDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(prePlanTempDetail.getLastUpdate());
		}
		if (StringUtils.isNotBlank(prePlanTempDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prePlanTempDetail.getActionLog());
		}
		if (StringUtils.isBlank(prePlanTempDetail.getExpired())
				|| "F".equals(prePlanTempDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (prePlanTempDetail.getProcessCost() != null) {
			sql += " and a.ProcessCost=? ";
			list.add(prePlanTempDetail.getProcessCost());
		}
		if (prePlanTempDetail.getMarkup() != null) {
			sql += " and a.Markup=? ";
			list.add(prePlanTempDetail.getMarkup());
		}
		if (StringUtils.isNotBlank(prePlanTempDetail.getIsOutSet())) {
			sql += " and a.IsOutSet=? ";
			list.add(prePlanTempDetail.getIsOutSet());
		}
		if (prePlanTempDetail.getCustTypeItemPk() != null) {
			sql += " and a.CustTypeItemPK=? ";
			list.add(prePlanTempDetail.getCustTypeItemPk());
		}
		sql += " order by  a.DispSeq";

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 根据no查找快速预报价明细
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findDetailByNo(
			PrePlanTempDetail prePlanTempDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select  a.pk, a.no,a.fixareatype,a.itemtypedescr,a.remark,a.qty,a.processcost,a.lastupdate,"
				+ " a.lastupdatedby, a.expired, a.actionLog,b.descr fixareatypedescr,c.descr algorithmdescr,"
				+ " d.descr itemdescr,a.dispSeq,isservice,x1.note isservicedescr,a.markup,a.algorithm,a.itemcode,"
				+ " a.isoutset,x2.note isoutsetdescr, a.custtypeitempk,'ADD' actionlog,a.dispseq,a.cuttype,x3.note cuttypedescr, "
				+ " a.pavetype,x4.note pavetypedescr,algorithmper,algorithmdeduct, a.canmodiqty,x5.note canmodiqtydescr  "
				+ " from  tPrePlanTempDetail a   "
				+ " left outer join tFixAreaType b on b.code=a.FixAreaType "
				+ " left outer join tAlgorithm c on c.code=a.Algorithm "
				+ " left outer join tItem d on d.code=a.ItemCode "
				+ " left outer join txtdm x1 on x1.cbm=a.IsService  and x1.id='YESNO'"
				+ " left outer join txtdm x2 on x2.cbm=a.isoutset  and x2.id='YESNO'"
				+ " left outer join txtdm x3 on x3.cbm=a.cuttype  and x3.id='CUTTYPE'"
				+ " left outer join txtdm x4 on x4.cbm=a.PaveType and x4.id='PAVETYPE'"
				+ " left outer join txtdm x5 on x5.cbm=a.CanModiQty and x5.id='YESNO'"
				+ " where a.no=? ";
		sql += " order by a.DispSeq";
		list.add(prePlanTempDetail.getNo());
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 根据切割类型匹配瓷砖尺寸
	 * 
	 * @param prePlanTempDetail
	 * @return
	 */
	public List<Map<String, Object>> getQtyByCutType(
			PrePlanTempDetail prePlanTempDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select cutType Code,c.note fd,c.note Descr from tCutFeeSet a "
				+ "inner join tItem b on a.Size=b.Size "
				+ "left join txtdm c on c.cbm=a.CutType and c.id='CutType' "
				+ "left join tItemType2 it2 on b.ItemType2=it2.Code  "
				+ "where b.Code=? and it2.ItemType12='11' ";
		list.add(prePlanTempDetail.getItemCode());
		return this.findBySql(sql, list.toArray());
	}
}
