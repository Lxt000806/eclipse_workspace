package com.house.home.dao.finance;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.LaborFeeType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class LaborFeeTypeDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, LaborFeeType laborFeeType) {
		List<Object> list = new ArrayList<Object>();
		String sql= " select a.Code,a.ItemType1,a.Descr,a.IsCalCost,a.IsHaveSendNo,b.Descr ItemType1Descr, "
				+" x1.note IsCalCostDescr ,x2.note IsHaveSendNoDescr, "
				+" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.itemtype12,it12.descr itemtype12descr "
	            +" from  dbo.tLaborFeeType a "
	            +" left outer join titemtype1 b on  b.code=a.ItemType1 "
	            +" left outer join txtdm x1 on x1.cbm=a.IsCalCost and x1.ID= 'YESNO' "
	            +" left outer join txtdm x2 on x2.cbm=a.IsHaveSendNo and x2.ID= 'YESNO' "
	            +" left outer join tItemType12 it12 on a.itemtype12=it12.code "
	            +" where 1=1 ";
			
		if (StringUtils.isNotBlank(laborFeeType.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+laborFeeType.getCode()+"%");
		}
		if (StringUtils.isNotBlank(laborFeeType.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+laborFeeType.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(laborFeeType.getItemType1())) {
			sql += "  and a.ItemType1= ? ";
			list.add(laborFeeType.getItemType1());
		}
		if (StringUtils.isBlank(laborFeeType.getExpired())
				|| "F".equals(laborFeeType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public LaborFeeType getByCode(String code) {
		String hql = "from LaborFeeType a where a.code=? ";
		List<LaborFeeType> list = this.find(hql, new Object[]{code});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}

}
