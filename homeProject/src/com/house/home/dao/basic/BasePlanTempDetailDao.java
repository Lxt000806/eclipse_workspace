package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BasePlanTempDetail;

@SuppressWarnings("serial")
@Repository
public class BasePlanTempDetailDao extends BaseDao {

	/**
	 * BasePlanTempDetail分页信息
	 * 
	 * @param page
	 * @param basePlanTempDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			BasePlanTempDetail basePlanTempDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  a.pk,a.no,a.fixareatype,baseitemcode,basealgorithm,a.remark,a.qty,a.isoutset,a.isrequired,"
				+ " a.canreplace,a.canmodiqty,a.dispseq,a.pavetile,a.pavefloor,a.pavediamondfloor,a.lastupdate,a.lastupdatedby,"
				+ " a.actionlog,a.expired,b.descr FixAreaTypeDescr,c.descr BaseAlgorithmDescr, x1.note IsRequiredDescr,x2.note isoutsetDescr,x3.note CanReplaceDescr,"
				+ " x4.note  CanModiQtyDescr,x5.note PaveTileDescr,x6.note PaveFloorDescr,x7.note PaveDiamondFloorDescr,d.descr BaseItemDescr "
				+ " from  tBasePlanTempDetail a   "
				+ " left outer join tFixAreaType b on b.code=a.FixAreaType "
				+ " left outer join tBaseAlgorithm c on c.code=a.BaseAlgorithm "
				+ " left outer join tBaseItem d on d.code=a.BaseItemCode "
				+ " left outer join txtdm x1 on x1.cbm=a.IsRequired  and x1.id='YESNO'"
				+ " left outer join txtdm x2 on x2.cbm=a.isoutset  and x2.id='YESNO'"
				+ " left outer join txtdm x3 on x3.cbm=a.CanReplace  and x3.id='YESNO'"
				+ " left outer join txtdm x4 on x4.cbm=a.CanModiQty  and x4.id='YESNO'"
				+ " left outer join txtdm x5 on x5.cbm=a.PaveTile  and x5.id='YESNO'"
				+ " left outer join txtdm x6 on x6.cbm=a.PaveFloor  and x6.id='YESNO'"
				+ " left outer join txtdm x7 on x7.cbm=a.PaveDiamondFloor  and x7.id='YESNO'"
				+ " where 1=1 and a.No=?";

		list.add(basePlanTempDetail.getNo());
		if (basePlanTempDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(basePlanTempDetail.getPk());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getFixAreaType())) {
			sql += " and a.FixAreaType=? ";
			list.add(basePlanTempDetail.getFixAreaType());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getBaseItemCode())) {
			sql += " and a.BaseItemCode=? ";
			list.add(basePlanTempDetail.getBaseItemCode());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getBaseAlgorithm())) {
			sql += " and a.BaseAlgorithm=? ";
			list.add(basePlanTempDetail.getBaseAlgorithm());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(basePlanTempDetail.getRemark());
		}
		if (basePlanTempDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(basePlanTempDetail.getQty());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getIsOutSet())) {
			sql += " and a.IsOutSet=? ";
			list.add(basePlanTempDetail.getIsOutSet());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getIsRequired())) {
			sql += " and a.IsRequired=? ";
			list.add(basePlanTempDetail.getIsRequired());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getCanReplace())) {
			sql += " and a.CanReplace=? ";
			list.add(basePlanTempDetail.getCanReplace());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getCanModiQty())) {
			sql += " and a.CanModiQty=? ";
			list.add(basePlanTempDetail.getCanModiQty());
		}
		if (basePlanTempDetail.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(basePlanTempDetail.getDispSeq());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getPaveTile())) {
			sql += " and a.PaveTile=? ";
			list.add(basePlanTempDetail.getPaveTile());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getPaveFloor())) {
			sql += " and a.PaveFloor=? ";
			list.add(basePlanTempDetail.getPaveFloor());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getPaveDiamondFloor())) {
			sql += " and a.PaveDiamondFloor=? ";
			list.add(basePlanTempDetail.getPaveDiamondFloor());
		}
		if (basePlanTempDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(basePlanTempDetail.getLastUpdate());
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(basePlanTempDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(basePlanTempDetail.getExpired())
				|| "F".equals(basePlanTempDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(basePlanTempDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(basePlanTempDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.DispSeq";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 根据no查找基础算量明细
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findDetailByNo(
			BasePlanTempDetail basePlanTempDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select  a.pk,a.no,a.fixareatype,baseitemcode,basealgorithm,a.remark,a.qty,a.isoutset,a.isrequired,"
				+ " a.canreplace,a.canmodiqty,a.dispseq,a.pavetile,a.pavefloor,a.pavediamondfloor,a.lastupdate,a.lastupdatedby,"
				+ " a.actionlog,a.expired,x1.note isrequireddescr,x2.note isoutsetdescr,x3.note canreplacedescr,"
				+ " x4.note canmodiqtydescr,x5.note pavetiledescr,x6.note pavefloordescr,x7.note pavediamondfloordescr, "
				+ " b.descr fixareatypedescr,c.descr basealgorithmdescr,d.descr baseitemdescr "
				+ " from  tBasePlanTempDetail a   "
				+ " left outer join tFixAreaType b on b.code=a.FixAreaType "
				+ " left outer join tBaseAlgorithm c on c.code=a.BaseAlgorithm "
				+ " left outer join tBaseItem d on d.code=a.BaseItemCode "
				+ " left outer join txtdm x1 on x1.cbm=a.IsRequired  and x1.id='YESNO'"
				+ " left outer join txtdm x2 on x2.cbm=a.isoutset  and x2.id='YESNO'"
				+ " left outer join txtdm x3 on x3.cbm=a.CanReplace  and x3.id='YESNO'"
				+ " left outer join txtdm x4 on x4.cbm=a.CanModiQty  and x4.id='YESNO'"
				+ " left outer join txtdm x5 on x5.cbm=a.PaveTile  and x5.id='YESNO'"
				+ " left outer join txtdm x6 on x6.cbm=a.PaveFloor  and x6.id='YESNO'"
				+ " left outer join txtdm x7 on x7.cbm=a.PaveDiamondFloor  and x7.id='YESNO'"
				+ " where 1=1 and a.No=?";
		sql += " order by a.DispSeq";
		list.add(basePlanTempDetail.getNo());
		return this.findBySql(sql, list.toArray());
	}
}
