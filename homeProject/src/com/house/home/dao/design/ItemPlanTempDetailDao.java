package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.ItemPlanTempDetail;

@SuppressWarnings("serial")
@Repository
public class ItemPlanTempDetailDao extends BaseDao {

	/**
	 * ItemPlanTempDetail分页信息
	 * 
	 * @param page
	 * @param itemPlanTempDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanTempDetail itemPlanTempDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql ="select a.ItemCode,a.DispSeq,a.Qty,a.expired,b.Cost,b.Price unitPrice,a.Qty*b.Price BefLineAmount,100 Markup,rtrim(b.itemType2) itemType2,a.Qty*b.Price TmpLineAmount,"
					+"0 ProcessCost,a.Qty*b.Price LineAmount,b.Remark remarks,b.descr itemDescr,b.IsFixPrice,b.CommiType,0 projectqty,c.descr uomDescr,b.ProjectCost "
	                +" from tItemPlanTempDetail a "
	                +" left outer join tItem b on a.ItemCode=b.Code "
	                +" left outer join tUOM c ON b.Uom=c.Code"
	                +" left outer join tSupplier s on s.code=b.SupplCode "
	                +" where 1=1 ";

    	if (itemPlanTempDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemPlanTempDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemPlanTempDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(itemPlanTempDetail.getNo());
		}
    	if (StringUtils.isNotBlank(itemPlanTempDetail.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemPlanTempDetail.getItemCode());
		}
    	if (itemPlanTempDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemPlanTempDetail.getQty());
		}
    	if (itemPlanTempDetail.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemPlanTempDetail.getDispSeq());
		}
    	if (itemPlanTempDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemPlanTempDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemPlanTempDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemPlanTempDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemPlanTempDetail.getExpired()) || "F".equals(itemPlanTempDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemPlanTempDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemPlanTempDetail.getActionLog());
		}
    	if(StringUtils.isNotBlank(itemPlanTempDetail.getCustCode())){
			sql+=" and (s.isGroup = '1' or exists (select 1 from tCustomer in_a " +
					"			left join tBuilder in_b on in_b.code = in_a.BuilderCode " +
					" 			left Join tRegion in_c on in_c.code= in_b.regionCode " +
					"			where exists ( select 1 from tSupplCmp in_d where in_d.SupplCode =s.Code and in_d.CmpCode = in_c.CmpCode ) " +
					" and in_a.Code = ?  " +
					"  ))";
			list.add(itemPlanTempDetail.getCustCode());
		}
    	
		if(StringUtils.isNotBlank(itemPlanTempDetail.getCustType())){
		    if(StringUtils.isNotBlank(itemPlanTempDetail.getCanUseComItem())){
                if("0".equals(itemPlanTempDetail.getCanUseComItem())){
                    sql+=" and ( b.custType = ? or b.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?) ) ";
                }else{
                    sql+=" and ( (b.custType = ? or b.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?))" +
                            " or ((b.custType = '' or b.custType is null) and (b.CustTypeGroupNo is null or b.CustTypeGroupNo = '')) ) ";
                }
                list.add(itemPlanTempDetail.getCustType());
                list.add(itemPlanTempDetail.getCustType());
            }
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

