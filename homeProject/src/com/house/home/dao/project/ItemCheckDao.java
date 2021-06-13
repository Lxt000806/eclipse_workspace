package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ItemCheck;

@SuppressWarnings("serial")
@Repository
public class ItemCheckDao extends BaseDao {

	/**
	 * ItemCheck分页信息
	 * 
	 * @param page
	 * @param itemCheck
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCheck itemCheck) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemCheck a where 1=1 ";

    	if (StringUtils.isNotBlank(itemCheck.getNo())) {
			sql += " and a.No=? ";
			list.add(itemCheck.getNo());
		}
    	if (StringUtils.isNotBlank(itemCheck.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemCheck.getCustCode());
		}
    	if (StringUtils.isNotBlank(itemCheck.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemCheck.getItemType1());
		}
    	if (StringUtils.isNotBlank(itemCheck.getStatus())) {
			sql += " and a.Status=? ";
			list.add(itemCheck.getStatus());
		}
    	if (StringUtils.isNotBlank(itemCheck.getAppRemark())) {
			sql += " and a.AppRemark=? ";
			list.add(itemCheck.getAppRemark());
		}
    	if (itemCheck.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(itemCheck.getDateFrom());
		}
		if (itemCheck.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(itemCheck.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemCheck.getAppEmp())) {
			sql += " and a.AppEmp=? ";
			list.add(itemCheck.getAppEmp());
		}
    	if (StringUtils.isNotBlank(itemCheck.getConfirmRemark())) {
			sql += " and a.ConfirmRemark=? ";
			list.add(itemCheck.getConfirmRemark());
		}
    	if (itemCheck.getConfirmDate() != null) {
			sql += " and a.ConfirmDate=? ";
			list.add(itemCheck.getConfirmDate());
		}
    	if (StringUtils.isNotBlank(itemCheck.getConfirmEmp())) {
			sql += " and a.ConfirmEmp=? ";
			list.add(itemCheck.getConfirmEmp());
		}
    	if (StringUtils.isNotBlank(itemCheck.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemCheck.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemCheck.getExpired()) || "F".equals(itemCheck.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemCheck.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemCheck.getActionLog());
		}
    	if (itemCheck.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemCheck.getLastUpdate());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public boolean isCheckItem(String custCode, String itemType1) {
		String sql="select 1 from tItemCheck where Status='2' and CustCode=? and ItemType1=?  ";
		List<Map<String,Object>> list= this.findBySql(sql, new Object[]{custCode,itemType1});
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}

}

