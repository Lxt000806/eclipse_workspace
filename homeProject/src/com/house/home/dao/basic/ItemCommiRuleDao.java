package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.ItemCommiRule;

@SuppressWarnings("serial")
@Repository
public class ItemCommiRuleDao extends BaseDao {

	/**
	 * ItemCommiRule分页信息
	 * 
	 * @param page
	 * @param itemCommiRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiRule itemCommiRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.CustCheckDateFrom,a.CustCheckDateTo,a.IsAddAllInfo,a.MainCommiPer," 
			 +"a.ServCommiPer,a.SoftCommiPer,a.CurtainCommiPer,a.FurnitureCommiPer, "
             +"a.MarketFundPer,a.LastUpdate,LastUpdatedBy,a.Expired,a.ActionLog,b.NOTE IsAddAllInfoDescr "
             +"from tItemCommiRule a " 
             +"left join tXTDM b on a.IsAddAllInfo=b.CBM and b.ID='YESNO' " 
             +"where 1=1 ";

    	if (itemCommiRule.getCustCheckDateFrom() != null) {
			sql += " and a.CustCheckDateFrom>=? ";
			list.add(itemCommiRule.getCustCheckDateFrom());
		}
    	if (itemCommiRule.getCustCheckDateTo() != null) {
			sql += " and a.CustCheckDateFrom<? ";
			list.add(DateUtil.addDate(itemCommiRule.getCustCheckDateTo(), 1));
		}
    	
		if (StringUtils.isBlank(itemCommiRule.getExpired()) || "F".equals(itemCommiRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

