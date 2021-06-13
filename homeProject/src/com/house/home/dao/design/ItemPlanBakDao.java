package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.ItemPlanBak;

@SuppressWarnings("serial")
@Repository
public class ItemPlanBakDao extends BaseDao {

	/**
	 * ItemPlanBak分页信息
	 * 
	 * @param page
	 * @param itemPlanBak
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanBak itemPlanBak) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  a.No, a.CustCode, a.CustType, a.mainTempNo, a.Remark,a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, " +
				" b.descr maintempdescr from tItemPlanBak a " + 
				" left join tPrePlanTemp b on b.no=a.mainTempNo " +
				" where 1=1 ";

    	if (StringUtils.isNotBlank(itemPlanBak.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+itemPlanBak.getNo().trim()+"%");
			
		}
    	if (StringUtils.isNotBlank(itemPlanBak.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemPlanBak.getCustCode());
		}
    	if (StringUtils.isNotBlank(itemPlanBak.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(itemPlanBak.getCustType());
		}
    	if (StringUtils.isNotBlank(itemPlanBak.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemPlanBak.getItemType1());
		}
    	if (StringUtils.isNotBlank(itemPlanBak.getMainTempNo())) {
			sql += " and a.MainTempNo=? ";
			list.add(itemPlanBak.getMainTempNo());
		}
    	if (StringUtils.isNotBlank(itemPlanBak.getRemark())) {
			sql += " and a.Remark like ? ";
			list.add("%"+itemPlanBak.getRemark().trim()+"%");
		}
    	if (itemPlanBak.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemPlanBak.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemPlanBak.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemPlanBak.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemPlanBak.getExpired()) || "F".equals(itemPlanBak.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemPlanBak.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemPlanBak.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

