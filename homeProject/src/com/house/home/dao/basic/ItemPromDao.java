package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemProm;

@SuppressWarnings("serial")
@Repository
public class ItemPromDao extends BaseDao {

	/**
	 * ItemProm分页信息
	 * 
	 * @param page
	 * @param itemProm
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemProm itemProm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from  (select ip.PK,ip.ItemCode,i.Descr ItemDescr,it1.Descr ItemType1Descr,it2.Descr ItemType2Descr, "
				+ "spl.Descr SupplDescr,ip.PromPrice,ip.PromCost,ip.BeginDate,ip.EndDate, "
				+ "ip.LastUpdate,ip.LastUpdatedBy,ip.Expired,ip.ActionLog "
				+ "from tItemProm ip "
				+ "left join tItem i on ip.ItemCode=i.Code "
				+ "left join tItemType1 it1 on i.ItemType1=it1.Code "
				+ "left join tItemType2 it2 on i.ItemType2=it2.Code "
				+ "left join tSupplier spl on i.SupplCode=spl.Code "
				+ "where 1=1  ";

		if (StringUtils.isNotBlank(itemProm.getItemCode())) {
			sql += " and ip.ItemCode=? ";
			list.add(itemProm.getItemCode());
		}
		if (StringUtils.isNotBlank(itemProm.getSupplCode())) {
			sql += " and i.SupplCode=? ";
			list.add(itemProm.getSupplCode());
		}
		if (StringUtils.isNotBlank(itemProm.getItemType1())) {
			sql += " and i.itemType1=? ";
			list.add(itemProm.getItemType1());
		}
		if (itemProm.getBeginDate() != null) {
			sql += " and ip.BeginDate>=? ";
			list.add(itemProm.getBeginDate());
		}
		if (itemProm.getEndDate() != null) {
			sql += " and ip.EndDate<DATEADD(d,1,?) ";
			list.add(itemProm.getEndDate());
		}
		if (itemProm.getLastUpdate() != null) {
			sql += " and ip.LastUpdate=? ";
			list.add(itemProm.getLastUpdate());
		}
		if (StringUtils.isBlank(itemProm.getExpired())
				|| "F".equals(itemProm.getExpired())) {
			sql += " and ip.Expired='F' ";
		}
		if (StringUtils.isNotBlank(itemProm.getActionLog())) {
			sql += " and ip.ActionLog=? ";
			list.add(itemProm.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 材料是否已存在促销记录
	 * 
	 * @param itemProm
	 */
	public List<Map<String, Object>> checkIsExists(ItemProm itemProm) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select 1 from tItemProm where Expired='F' and ItemCode=? ";
		list.add(itemProm.getItemCode());
		if(itemProm.getPk()!=null){
			sql+="and pk<>?";
			list.add(itemProm.getPk());
		}
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 选中行数据
	 * 
	 * @param itemProm
	 */
	public List<Map<String, Object>> findListByPk(Integer pk) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select ip.pk,ip.itemCode,i.Descr itemDescr,it1.Descr ItemType1Descr,it2.Descr ItemType2Descr, "
				+ "spl.Descr SupplDescr,ip.promPrice,ip.promCost,ip.beginDate,ip.endDate, "
				+ "ip.LastUpdate,ip.LastUpdatedBy,ip.expired,ip.ActionLog,i.oldprice itemPrice,i.oldcost itemCost "
				+ "from tItemProm ip "
				+ "left join tItem i on ip.ItemCode=i.Code "
				+ "left join tItemType1 it1 on i.ItemType1=it1.Code "
				+ "left join tItemType2 it2 on i.ItemType2=it2.Code "
				+ "left join tSupplier spl on i.SupplCode=spl.Code "
				+ "where ip.pk=?  ";
		list.add(pk);
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 材料是否存在
	 * 
	 * @param itemProm
	 */
	public List<Map<String, Object>> checkIsItemCode(ItemProm itemProm) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select 1 from tItem where Code=? ";
		list.add(itemProm.getItemCode());
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 更新促销价
	 * 
	 * @param itemProm
	 */
	public void updatePrice(ItemProm itemProm) {
		String sql = " exec pUpPromPrice ?";
		this.executeUpdateBySql(sql, new Object[] {itemProm.getLastUpdatedBy() });
	}
	/**
	 * 材料促销查询
	 * 
	 * @param page
	 * @param itemProm
	 * @return
	 */
	public Page<Map<String, Object>> itemQuery(Page<Map<String, Object>> page,
			ItemProm itemProm, String itemRight) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from  (select i.Code ItemCode,i.Descr ItemDescr,it1.Descr ItemType1Descr,it2.Descr ItemType2Descr, "
				+ "spl.Descr SupplDescr,i.Price,i.Cost,i.OldPrice,i.OldCost,i.Remark,i.LastUpdate "
				+ "from tItem i "
				+ "left join tItemType1 it1 on i.ItemType1=it1.Code "
				+ "left join tItemType2 it2 on i.ItemType2=it2.Code "
				+ "left join tSupplier spl on i.SupplCode=spl.Code  "
				+ "where i.IsProm='1' ";

		if (StringUtils.isNotBlank(itemProm.getItemCode())) {
			sql += " and i.Code=? ";
			list.add(itemProm.getItemCode());
		}
		if (StringUtils.isNotBlank(itemProm.getSupplCode())) {
			sql += " and i.SupplCode=? ";
			list.add(itemProm.getSupplCode());
		}
		if (StringUtils.isNotBlank(itemProm.getItemType1())) {
			sql += " and i.itemType1=? ";
			list.add(itemProm.getItemType1());
		} else {
			sql += " and i.itemType1 in " + "('"+ itemRight.replace(",", "','") + "')";
		}
		if (StringUtils.isBlank(itemProm.getExpired())
				|| "F".equals(itemProm.getExpired())) {
			sql += " and i.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 材料数据
	 * 
	 * @param itemProm
	 */
	public List<Map<String, Object>> findItemData(String itemCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select i.Descr itemdescr,it1.Descr itemtype1descr,it2.Descr itemtype2descr,i.expired, "
				+ "spl.Descr suppldescr from tItem i  "
				+ "left join tItemType1 it1 on i.ItemType1=it1.Code "
				+ "left join tItemType2 it2 on i.ItemType2=it2.Code "
				+ "left join tSupplier spl on i.SupplCode=spl.Code "
				+ "where i.Code=?  ";
		list.add(itemCode);
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * excel导入
	 * @param page
	 * @param itemProm
	 * @return
	 */
	public Map<String, Object> importExcel(ItemProm itemProm) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pClcxgl_Import ?,?,?,?,?,? ";
		list.add(itemProm.getItemCode());
		list.add(itemProm.getPromPrice());
		list.add(itemProm.getPromCost());
		list.add(itemProm.getBeginDate());
		list.add(itemProm.getEndDate());
		list.add(itemProm.getLastUpdatedBy());
		return findListBySql(sql, list.toArray()).get(0);
	}
}

