package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.BaseItemChgDetail;

@SuppressWarnings("serial")
@Repository
public class BaseItemChgDetailDao extends BaseDao {

	/**
	 * BaseItemChgDetail分页信息
	 * 
	 * @param page
	 * @param baseItemChgDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemChgDetail baseItemChgDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tBaseItemChgDetail a where 1=1 ";

    	if (baseItemChgDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(baseItemChgDetail.getPk());
		}
    	if (StringUtils.isNotBlank(baseItemChgDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(baseItemChgDetail.getNo());
		}
    	if (baseItemChgDetail.getReqPk() != null) {
			sql += " and a.ReqPK=? ";
			list.add(baseItemChgDetail.getReqPk());
		}
    	if (baseItemChgDetail.getFixAreaPk() != null) {
			sql += " and a.FixAreaPK=? ";
			list.add(baseItemChgDetail.getFixAreaPk());
		}
    	if (StringUtils.isNotBlank(baseItemChgDetail.getBaseItemCode())) {
			sql += " and a.BaseItemCode=? ";
			list.add(baseItemChgDetail.getBaseItemCode());
		}
    	if (baseItemChgDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(baseItemChgDetail.getQty());
		}
    	if (baseItemChgDetail.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(baseItemChgDetail.getCost());
		}
    	if (baseItemChgDetail.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(baseItemChgDetail.getUnitPrice());
		}
    	if (baseItemChgDetail.getLineAmount() != null) {
			sql += " and a.LineAmount=? ";
			list.add(baseItemChgDetail.getLineAmount());
		}
    	if (StringUtils.isNotBlank(baseItemChgDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(baseItemChgDetail.getRemarks());
		}
    	if (baseItemChgDetail.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(baseItemChgDetail.getDateFrom());
		}
		if (baseItemChgDetail.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(baseItemChgDetail.getDateTo());
		}
    	if (StringUtils.isNotBlank(baseItemChgDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItemChgDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(baseItemChgDetail.getExpired()) || "F".equals(baseItemChgDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(baseItemChgDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItemChgDetail.getActionLog());
		}
    	if (baseItemChgDetail.getMaterial() != null) {
			sql += " and a.Material=? ";
			list.add(baseItemChgDetail.getMaterial());
		}
    	if (baseItemChgDetail.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(baseItemChgDetail.getDispSeq());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id) {
		String sql = "select * from (select case when a.isoutset ='1' then '是' else '否' end isoutsetdescr," 
		  +" CASE WHEN RTRIM(b.Descr) in ('全房','水电项目','土建项目','安装项目','综合项目') THEN 1 ELSE 0 END isgroup,"		
		  +"a.isOutset,a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.BaseItemCode,d.Descr BaseItemDescr,"
          +"a.Qty,e.Descr Uom,a.Cost,a.UnitPrice,a.LineAmount,a.dispseq,a.material,a.PrjCtrlType,a.OfferCtrl,a.MaterialCtrl,"
          +"a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,d.Category,d.IsCalMangeFee,t.Qty PreQty,x1.note IsMainItemDescr," +
          " a.IsMainItem,a.BaseItemSetNo ,a.BaseAlgorithm,f.descr AlgorithmDescr,a.BaseAlgorithm Algorithm,g.descr prePlanAreaDescr  "
          +" from tBaseItemChgDetail a "
          +"left outer join tFixArea b on b.PK=a.FixAreaPK "
          +"left outer join tBaseItem d on d.Code=a.BaseItemCode "
          +"left outer join tUom e on d.Uom=e.Code "
          +"left join tBaseItemReq t on a.ReqPK=t.PK "
          +"left outer join tXTDM x1 on x1.ID='YESNO' and x1.CBM=a.IsMainItem " +
          " left join tBaseAlgorithm f on f.code = a.BaseAlgorithm " +
          " left join tPrePlanArea g on g.pk = b.PrePlanAreaPk "
          +"where a.No=? ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.dispseq";
		}
		return this.findPageBySql(page, sql, new Object[]{id});
	}

}

