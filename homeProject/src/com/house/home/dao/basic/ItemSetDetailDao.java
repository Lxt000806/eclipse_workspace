package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemSetDetail;

@SuppressWarnings("serial")
@Repository
public class ItemSetDetailDao extends BaseDao {

	/**
	 * ItemSetDetail分页信息
	 * 
	 * @param page
	 * @param itemSetDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSetDetail itemSetDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.pk,a.ItemCode,i.Descr ItemDescr,i.Remark, "
		          +"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,f.descr itemType3Descr, "
		          +"i.Uom,d.Descr UomDescr,i.Price,case when cti.pk>0 then cti.Price else a.UnitPrice end SetUnitPrice,i.Cost,"
		          +"a.No itemSetNo,e.descr itemSetDescr,i.projectcost, i.ItemType2, it2.Descr ItemType2Descr, "
		          +" a.algorithmper,a.algorithmdeduct,a.algorithm,g.descr algorithmdescr,a.Remarks,x1.note cuttypedescr  " 
		          +" from tItemSetDetail a  "
		          +" left outer join tItem i on i.code=a.ItemCode "
		          +" left outer join tUom d on i.Uom=d.Code "
		          +" left outer join titemSet e on e.no=a.no "
		          +" left outer join tItemType2 it2 on it2.Code = i.ItemType2 "
		          +" left outer join titemType3 f on f.code=i.itemType3 "
		          +" left outer join tSupplier s on s.Code=i.SupplCode "
		          +" left outer join tCustTypeItem cti on cti.pk=a.CustTypeItemPk "
		          +" left outer join tAlgorithm g on g.code=a.Algorithm "
		          +" left outer join txtdm x1 on x1.id='cuttype' and x1.cbm=a.cuttype "
		          +" where 1=1  ";

    	if (itemSetDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemSetDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemSetDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(itemSetDetail.getNo());
		}
    	if (StringUtils.isNotBlank(itemSetDetail.getItemcode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemSetDetail.getItemcode());
		}
    	if(StringUtils.isNotBlank(itemSetDetail.getItemDescr())){
    		sql+=" and i.descr like ? ";
    		list.add("%"+itemSetDetail.getItemDescr()+"%");
    		
    	}
    	if (itemSetDetail.getUnitprice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(itemSetDetail.getUnitprice());
		}
    	if (itemSetDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemSetDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemSetDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemSetDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemSetDetail.getExpired()) || "F".equals(itemSetDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemSetDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemSetDetail.getActionLog());
		}
    	if(StringUtils.isNotBlank(itemSetDetail.getCustCode())){
			sql+=" and (s.isGroup = '1' or exists (select 1 from tCustomer in_a " +
					"			left join tBuilder in_b on in_b.code = in_a.BuilderCode " +
					" 			left Join tRegion in_c on in_c.code= in_b.regionCode " +
					"			where exists ( select 1 from tSupplCmp in_d where in_d.SupplCode = s.Code and in_d.CmpCode = in_c.CmpCode ) " +
					" and in_a.Code = ?  " +
					"  ))";
			list.add(itemSetDetail.getCustCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

