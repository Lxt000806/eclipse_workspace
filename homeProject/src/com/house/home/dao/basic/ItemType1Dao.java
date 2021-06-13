package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemType1;

@SuppressWarnings("serial")
@Repository
public class ItemType1Dao extends BaseDao {

	/**
	 * ItemType1分页信息
	 * 
	 * @param page
	 * @param itemType1
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType1 itemType1) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.Code,a.Descr,a.LastUpdate,a.LastUpdatedBy," +
				"a.Expired,a.ActionLog,a.DispSeq,a.ProfitPer,a.isSpecReq,b.Note IsSpecReqDescr,c.Desc1 WHDescr" +
				" from tItemType1 a " +
				" left join txtdm b on a.IsSpecReq = b.CBM and b.ID = 'YESNO' " +
				" left join tWareHouse c on a.WHCode=c.Code "+
				"where 1=1 ";

    	if (StringUtils.isNotBlank(itemType1.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+itemType1.getCode()+"%");
		}
    	if (StringUtils.isNotBlank(itemType1.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(itemType1.getDescr());
		}
    	if (itemType1.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemType1.getDispSeq());
		}
    	if (itemType1.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemType1.getDateFrom());
		}
		if (itemType1.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemType1.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemType1.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemType1.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemType1.getExpired()) || "F".equals(itemType1.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemType1.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemType1.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<ItemType1> findByNoExpired() {
		String hql = "from ItemType1 a where a.expired='F' order by a.dispSeq";
		return this.find(hql);
	}
	
	public List<Map<String,Object>> findItemType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType1 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.code in ("+param.get("pCode")+") ";
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findItemByType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType1 a where a.expired='F'";
		//排序
		sql += " order by a.dispSeq";
		//sql携带着第一次选择的行号,list携带查找出来的数据
		return this.findBySql(sql, list.toArray());
	}

}

