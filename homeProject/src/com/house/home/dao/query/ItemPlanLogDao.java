package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemPlanLog;

@SuppressWarnings("serial")
@Repository
public class ItemPlanLogDao extends BaseDao {

	/**
	 * 预算修改日志编号
	 * 
	 * @param page
	 * @param itemPlanLog
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanLog itemPlanLog) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		String sql = "select * from (select  c.Descr FixAreaDescr, b.ItemCode, b.qty, b.UnitPrice, b.Markup,"
                  + " b.BefLineAmount, b.LineAmount, b.LastUpdate, b.LastUpdatedBy, ProcessCost, "
	              + " case when b.ActionLog='ADD' then '新增'  when b.ActionLog='DELETE' then '删除'  when b.ActionLog='EDIT_BEF' then '修改前' else '修改后' end ActionLogDescr ,"
	              + " b.PlanPK, b.ActionLog,b.Expired"
                  + " from  tItemPlanLog a "
                  + " inner  join tItemPlanLogDetail b on a.no = b.no "
                  + " left outer join tFixArea c on c.pk = b.FixAreaPK "
		          + " where 1=1 "; 
		if (StringUtils.isNotBlank(itemPlanLog.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemPlanLog.getCustCode());
		}
		if (StringUtils.isNotBlank(itemPlanLog.getItemType1())) {
			sql += " and a.ItemType1 =? ";
			list.add(itemPlanLog.getItemType1());
		}
		if (StringUtils.isNotBlank(itemPlanLog.getItemCode())) {
			sql += " and b.itemcode =? ";
			list.add(itemPlanLog.getItemCode());
		}
		if (StringUtils.isNotBlank(itemPlanLog.getFixArea())) {
			sql += " and b.FixAreaPK =? ";
			list.add(itemPlanLog.getFixArea());
		}

		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by  case when a.ActionLog='ADD' then 1 when a.ActionLog='DELETE' then 2 else 3 end,a.PlanPK,  ActionLog desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}


}

