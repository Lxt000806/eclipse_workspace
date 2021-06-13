package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.FixCRLF.AddAsisRemove;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.FixArea;
import com.house.home.entity.insales.BaseItemReq;

@SuppressWarnings("serial")
@Repository
public class BaseItemReqDao extends BaseDao {

	/**
	 * BaseItemReq分页信息
	 * 
	 * @param page
	 * @param baseItemReq
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemReq baseItemReq) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tBaseItemReq a where 1=1 ";

    	if (baseItemReq.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(baseItemReq.getPk());
		}
    	if (StringUtils.isNotBlank(baseItemReq.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(baseItemReq.getCustCode());
		}
    	if (baseItemReq.getFixAreaPk() != null) {
			sql += " and a.FixAreaPK=? ";
			list.add(baseItemReq.getFixAreaPk());
		}
    	if (StringUtils.isNotBlank(baseItemReq.getBaseItemCode())) {
			sql += " and a.BaseItemCode=? ";
			list.add(baseItemReq.getBaseItemCode());
		}
    	if (baseItemReq.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(baseItemReq.getQty());
		}
    	if (baseItemReq.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(baseItemReq.getCost());
		}
    	if (baseItemReq.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(baseItemReq.getUnitPrice());
		}
    	if (baseItemReq.getLineAmount() != null) {
			sql += " and a.LineAmount=? ";
			list.add(baseItemReq.getLineAmount());
		}
    	if (StringUtils.isNotBlank(baseItemReq.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(baseItemReq.getRemark());
		}
    	if (baseItemReq.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseItemReq.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseItemReq.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItemReq.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(baseItemReq.getExpired()) || "F".equals(baseItemReq.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(baseItemReq.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItemReq.getActionLog());
		}
    	if (baseItemReq.getMaterial() != null) {
			sql += " and a.Material=? ";
			list.add(baseItemReq.getMaterial());
		}
    	if (baseItemReq.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(baseItemReq.getDispSeq());
		}
    	if (StringUtils.isNotBlank(baseItemReq.getIsOutSet())) {
			sql += " and a.IsOutSet=? ";
			list.add(baseItemReq.getIsOutSet());
		}
    	if (StringUtils.isNotBlank(baseItemReq.getPrjCtrlType())) {
			sql += " and a.PrjCtrlType=? ";
			list.add(baseItemReq.getPrjCtrlType());
		}
    	if (baseItemReq.getOfferCtrl() != null) {
			sql += " and a.OfferCtrl=? ";
			list.add(baseItemReq.getOfferCtrl());
		}
    	if (baseItemReq.getMaterialCtrl() != null) {
			sql += " and a.MaterialCtrl=? ";
			list.add(baseItemReq.getMaterialCtrl());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findBaseItemReqList(
			Page<Map<String, Object>> page, BaseItemReq baseItemReq) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select e.cost biCost,e.offerctrl BIOfferctrl,e.MaterialCtrl BIMaterialCtrl,e.Material BIMaterial,e.offerpri,a.IsOutSet, case when a.isOutSet='1' then '是' else '否 ' end isOutSetDescr ,c.DispSeq fixareaseq,0 as IsCheck,a.PK,a.CustCode,b.Descr CustomerDescr,b.Address,a.FixAreaPK,"
          + "right('0000'+cast(c.DispSeq as nvarchar(20)),4) +' - '+ c.Descr FixAreaDescr2,c.Descr FixAreaDescr,"
          + "a.BaseItemCode,e.Descr BaseItemDescr,e.IsCalMangeFee,"
          + "a.Qty,d.Descr Uom,a.Cost,a.UnitPrice,a.Material,a.LineAmount,a.Remark,a.LastUpdate,"
          + "a.LastUpdatedBy,a.Expired,a.ActionLog,a.PrjCtrlType,x1.Note PrjCtrlTypeDescr,a.OfferCtrl, "
          + "a.MaterialCtrl,e.category,x2.note IsMainItemDescr,a.IsMainItem,a.BaseItemSetNo, "
          + "dbo.fGetBaseChgStakeholder(g.No,'01','1') ChgEmpCodes, h.IsAddAllInfo "//获取需求增简单干系人 add by zb on 20191225
          + " , i.descr PrePlanAreaDescr , j.descr baseAlgorithmDescr ,a.BaseAlgorithm "
          + " from tBaseItemReq a "
          + " left outer join tCustomer b on b.Code=a.CustCode "
          + " left outer join tFixArea c on c.PK=a.FixAreaPK "
          + " left outer join tBaseItem e on e.Code=a.BaseItemCode "
          + " left outer join tUom d on e.Uom=d.Code "
          + " left outer join tXTDM x1 on a.PrjCtrlType=x1.cbm  and x1.id='PrjCtrlType' "
          + " left outer join tXTDM x2 on x2.ID='YESNO' and x2.CBM=a.IsMainItem "
    	  + " left join ( "
		  + " 	select in_a.ReqPK,max(in_a.PK) PK "
		  + " 	from tBaseItemChgDetail in_a "
		  + " 	group by in_a.ReqPK "
          + " ) f on f.ReqPK=a.PK "
          + " left join tBaseItemChgDetail g on g.PK = f.PK "
          + " left join tBaseItemChg h on h.No=g.No "
          + " left join tPreplanArea i on i.pk = c.PrePlanAreaPk "
          + " left join tBaseAlgorithm j on j.code = a.BaseAlgorithm " 
          + " where a.Expired='F' and a.qty <> 0 ";
          //+ " and not exists(select 1 from #tBaseItemChgDetail f where f.ReqPK is not null and f.ReqPK = a.PK) "
    	if (StringUtils.isNotBlank(baseItemReq.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(baseItemReq.getCustCode());
		}
    	if ("1".equals(baseItemReq.getShowOutSet()) || "on".equals(baseItemReq.getShowOutSet()) ){
    		
    	}else{
    		sql += " and a.isOutSet='1' ";
    	}
    	if(StringUtils.isNotBlank(baseItemReq.getFixAreaDescr())){
    		sql+=" and c.descr like ? ";
    		list.add("%"+baseItemReq.getFixAreaDescr()+"%");
    	}
    	if(StringUtils.isNotBlank(baseItemReq.getIsOutSet())){
    		sql+=" and a.isOutSet = ? ";
    		list.add(baseItemReq.getIsOutSet());
    	}
    	if (StringUtils.isNotBlank(baseItemReq.getUnSelected())){
    		sql += " and a.pk not in ("+baseItemReq.getUnSelected()+") ";
    	}
//    	PrjAPP用 add by zb on 20190311
    	if (null != baseItemReq.getFixAreaPk()) {
			sql += " and a.FixAreaPK = ? ";
			list.add(baseItemReq.getFixAreaPk());
		}
    	if (StringUtils.isNotBlank(baseItemReq.getBaseItemDescr())) {
			sql += " and e.Descr like ? ";
			list.add("%"+baseItemReq.getBaseItemDescr()+"%");
		}
//    	add end
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.DispSeq";
		}		
		return this.findPageBySql(page, sql, list.toArray());
	}

}

