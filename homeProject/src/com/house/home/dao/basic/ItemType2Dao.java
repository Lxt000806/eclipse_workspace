package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.dao.MenuDao;
import com.house.framework.entity.Menu;
import com.house.home.entity.basic.ItemType2;

@SuppressWarnings("serial")
@Repository
public class ItemType2Dao extends BaseDao {

	/**
	 * ItemType2分页信息
	 * @param page
	 * @param itemType2
	 * @return
	 */
	@Autowired
	private MenuDao menuDao;
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType2 itemType2) {
		List<Object> list = new ArrayList<Object>();
        String sql = " select * from ( SELECT a.code,a.descr,a.itemType1,a.dispSeq,a.length,a.lastNumber,a.lastUpdate,a.lastUpdatedBy,a.expired,a.actionLog,"
		+" a.arriveDay,a.otherCostPer_Sale,a.otherCostPer_Cost,a.itemType12,a.appPrjItem,"
		+" b.Descr itemType1Descr,c.Descr itemType12Descr,d.Descr worktype2,e.Descr workType1,f.Descr prjDescr,x1.Note OrderTypeDescr "
		+" FROM dbo.tItemType2 a "
		+" LEFT JOIN dbo.tItemType1 b ON a.ItemType1 = b.Code "
		+" LEFT JOIN dbo.tItemType12 c ON c.Code = a.ItemType12 "
		+" LEFT JOIN dbo.tWorkType2 d ON d.Code = a.MaterWorkType2"
		+" LEFT JOIN dbo.tWorkType1 e ON e.Code = d.WorkType1"
		+" LEFT JOIN dbo.tPrjItem1 f ON f.Code = a.appPrjItem"
	    +" LEFT JOIN dbo.tXTDM x1 on x1.ID='OrderType' and x1.CBM=a.OrderType " 
		+" WHERE 1=1 ";
    	if (StringUtils.isNotBlank(itemType2.getCode())) {
			sql += " and a.Code = ? ";
			list.add(itemType2.getCode());
		}
    	if (StringUtils.isNotBlank(itemType2.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+itemType2.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(itemType2.getItemType1())) {
			if(!("NULL".equals(itemType2.getItemType1()))){
				sql += " and a.ItemType1=? ";
				list.add(itemType2.getItemType1());
			}
		}
    	if (itemType2.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemType2.getDispSeq());
		}
    	if (itemType2.getLength() != null) {
			sql += " and a.Length=? ";
			list.add(itemType2.getLength());
		}
    	if (itemType2.getLastNumber() != null) {
			sql += " and a.LastNumber=? ";
			list.add(itemType2.getLastNumber());
		}
    	if (itemType2.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemType2.getDateFrom());
		}
		if (itemType2.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemType2.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemType2.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemType2.getLastUpdatedBy());
		}
    	//条件为空或者为F都只查F，为T的话全部查找
		if (StringUtils.isBlank(itemType2.getExpired()) || "F".equals(itemType2.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemType2.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemType2.getActionLog());
		}
    	if (itemType2.getArriveDay() != null) {
			sql += " and a.ArriveDay=? ";
			list.add(itemType2.getArriveDay());
		}
    	if (StringUtils.isNotBlank(itemType2.getItemType12())) {
			sql += " and a.ItemType12=? ";
			list.add(itemType2.getItemType12());
		}
    	if(StringUtils.isNotBlank(itemType2.getWorkerCode())){
    		sql+= " and d.workType12 = (select workType12 from tWorker where Code= ? )";
    		list.add(itemType2.getWorkerCode());
    	}
    	if(StringUtils.isNotBlank(itemType2.getWorkType12())){
    		sql+=" and d.WOrkType12 = ? ";
    		list.add(itemType2.getWorkerCode());
    	}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.DispSeq asc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//三级联动
	public List<Map<String,Object>> findItemType2(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType2 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.itemType1=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}

	public List<Menu> findAllItemType2ByitemType1(
			String itemType1) {
		String sql="select rtrim(a.code) code,a.descr descr,rtrim(b.Code) code2, "
		         + " b.Descr code2Descr from tItemType2 a " 
		          +" left outer join tBrand b on b.ItemType2=a.Code " 
		          +" where ltrim(rtrim(a.ItemType1))=? "
		          +"  and a.Expired='F' and b.Expired='F' "
		        + " group by a.Code,b.Code,a.Descr,b.Descr,a.DispSeq " 
		         +" order by a.DispSeq,a.Code";
		List<Map<String,Object>> list= this.findBySql(sql, new Object[]{itemType1});
		return menuDao.getMenuListByList(list,"/admin/item/goItemSelect","code2","code", "code2Descr","descr", null, null,null);
		
	}

	@SuppressWarnings("unchecked")
	public List<ItemType2> findByItemType1(String itemType1) {
		String hql = "from ItemType2 a where a.itemType1=? and a.expired='F' order by dispSeq";
		return this.find(hql, new Object[]{itemType1});
	}
	
	public Map<String , Object>  getItemType2sDetail(String code) {
		String sql = " select * from tItemType2 where Code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;	
	}
	
	//材料类型2,材料分类联动做第一级
	public List<Map<String,Object>> findByItemType2(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tWorkType2 a where a.expired='F'";
		//排序
		sql += " order by a.dispSeq";
		//sql携带着第一次选择的行号,list携带查找出来的数据
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findPrjType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name  from tPrjItem1 where expired='F' order by  cast(Code as int) ";
		return this.findBySql(sql, list.toArray());
	}
	//材料类型2做联动第一级,材料类型3做第二级 
	public List<Map<String,Object>> findByItemType2Fir(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType2 a where a.expired='F'";
		//排序
		sql += " order by a.dispSeq";
		//sql携带着第一次选择的行号,list携带查找出来的数据
		return this.findBySql(sql, list.toArray());
	}
	
	public Map<String,Object> getItemType1ByItemType2(String itemType2){
		String sql = " select ItemType1 from tItemType2 where Code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemType2});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;	
	}
}











