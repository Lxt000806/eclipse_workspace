package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppCtrl;

@SuppressWarnings("serial")
@Repository
public class ItemAppCtrlDao extends BaseDao {

	/**
	 * ItemAppCtrl分页信息
	 * 
	 * @param page
	 * @param itemAppCtrl
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppCtrl itemAppCtrl) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemAppCtrl a where 1=1 ";

    	if (StringUtils.isNotBlank(itemAppCtrl.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(itemAppCtrl.getCustType());
		}
    	if (StringUtils.isNotBlank(itemAppCtrl.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemAppCtrl.getItemType1());
		}
    	if (StringUtils.isNotBlank(itemAppCtrl.getCanInPlan())) {
			sql += " and a.CanInPlan=? ";
			list.add(itemAppCtrl.getCanInPlan());
		}
    	if (StringUtils.isNotBlank(itemAppCtrl.getCanOutPlan())) {
			sql += " and a.CanOutPlan=? ";
			list.add(itemAppCtrl.getCanOutPlan());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.CustType";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public ItemAppCtrl getByCustTypeAndItemType1(String custType,
			String itemType1) {
		String hql = "from ItemAppCtrl a where a.custType=? and a.itemType1=? ";
		List<ItemAppCtrl> list = this.find(hql, new Object[]{custType,itemType1});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

