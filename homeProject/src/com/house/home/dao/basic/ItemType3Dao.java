package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemType3;

@SuppressWarnings("serial")
@Repository
public class ItemType3Dao extends BaseDao {

	/**
	 * ItemType3分页信息
	 * 
	 * @param page
	 * @param itemType3
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType3 itemType3) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.Code,a.Descr,a.ItemType2,a.DispSeq,a.IntInstallFee,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog," +
				"b.Descr itemType2Descr,c.Descr itemType1Descr,x1.Note OrderTypeDescr " +
				"from tItemType3 a " +
				"left join tItemType2 b on b.code = a.itemtype2 " +
				"left join tItemType1 c on c.code = b.itemtype1 " +
				"left join tXTDM x1 on x1.ID='OrderType' and x1.CBM=a.OrderType "+ 
				"where 1=1 and a.itemType2 !=' ' "; 
		    	if (StringUtils.isNotBlank(itemType3.getCode())) {
					sql += " and a.Code like ? ";
					list.add("%"+itemType3.getCode()+"%");
				}
		    	if (StringUtils.isNotBlank(itemType3.getDescr())) {
					sql += " and a.Descr like ? ";
					list.add("%"+itemType3.getDescr()+"%");
				}
		    	if(StringUtils.isNotBlank(itemType3.getItemType1())){
		    		sql += " and b.ItemType1=? ";
		    		list.add(itemType3.getItemType1());
		    	}
		    	if (StringUtils.isNotBlank(itemType3.getItemType2())) {
					sql += " and a.ItemType2=? ";
					list.add(itemType3.getItemType2());
				}
		    	if (itemType3.getDispSeq() != null) {
					sql += " and a.DispSeq=? ";
					list.add(itemType3.getDispSeq());
				}
		    	if (itemType3.getDateFrom() != null){
					sql += " and a.LastUpdate>= ? ";
					list.add(itemType3.getDateFrom());
				}
				if (itemType3.getDateTo() != null){
					sql += " and a.LastUpdate<= ? ";
					list.add(itemType3.getDateTo());
				}
		    	if (StringUtils.isNotBlank(itemType3.getLastUpdatedBy())) {
					sql += " and a.LastUpdatedBy=? ";
					list.add(itemType3.getLastUpdatedBy());
				}
				if (StringUtils.isBlank(itemType3.getExpired()) || "F".equals(itemType3.getExpired())) {
					sql += " and a.Expired='F' ";
				}
		    	if (StringUtils.isNotBlank(itemType3.getActionLog())) {
					sql += " and a.ActionLog=? ";
					list.add(itemType3.getActionLog());
				}
				if (StringUtils.isNotBlank(page.getPageOrderBy())){
					sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
				}else{
					sql += ") a order by a.LastUpdate desc";
				}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String,Object>> findItemType3(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType3 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.itemType2=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}

	public Map<String, Object> findAllItemType3(String descr) {
		// TODO Auto-generated method stub
		String sql =" select * from tItemType3 where descr=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{descr});
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public Map<String, Object> findThItemType3(ItemType3 itemType3){
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		String sql =" select * from tItemType3 a " +
				"left join tItemType2 b on b.code = a.itemType2 " +
				"where ";
		if (itemType3!=null) {
			sql+="a.descr=? ";
			list.add(itemType3.getDescr());
		}
		if (itemType3!=null) {
			sql+="and a.itemType2=? ";
			list.add(itemType3.getItemType2());
		}
		if (itemType3!=null) {
			sql+="and b.itemtype1=? ";
			list.add(itemType3.getItemType1());
		}		
		List<Map<String, Object>> listf = this.findBySql(sql,list.toArray());
		if(listf != null && listf.size()>0){
			return listf.get(0);
		}
		return null;
	}
	//材料2,3联动
	public List<Map<String,Object>> findByItemByType3(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType3 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.itemType2=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
}

