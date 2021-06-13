package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ConfItemTime;

@SuppressWarnings("serial")
@Repository
public class ConfItemTimeDao extends BaseDao {

	/**
	 * ConfItemTime分页信息
	 * 
	 * @param page
	 * @param confItemTime
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemTime confItemTime) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tConfItemTime a where 1=1 ";

    	if (StringUtils.isNotBlank(confItemTime.getCode())) {
			sql += " and a.Code=? ";
			list.add(confItemTime.getCode());
		}
    	if (StringUtils.isNotBlank(confItemTime.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(confItemTime.getDescr());
		}
    	if (StringUtils.isNotBlank(confItemTime.getPrjItem())) {
			sql += " and a.PrjItem=? ";
			list.add(confItemTime.getPrjItem());
		}
    	if (StringUtils.isNotBlank(confItemTime.getDayType())) {
			sql += " and a.DayType=? ";
			list.add(confItemTime.getDayType());
		}
    	if (confItemTime.getAddDay() != null) {
			sql += " and a.AddDay=? ";
			list.add(confItemTime.getAddDay());
		}
    	if (confItemTime.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(confItemTime.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(confItemTime.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(confItemTime.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(confItemTime.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(confItemTime.getActionLog());
		}
		if (StringUtils.isBlank(confItemTime.getExpired()) || "F".equals(confItemTime.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String,Object>> findConfItemCodeListBySql(String custCode) {
		String sql= "SELECT code FROM  dbo.tConfItemTime "	 
				   +"WHERE Code  NOT in  (SELECT DISTINCT(ItemTimeCode)  FROM tConfItemType  WHERE Code NOT  IN (SELECT ConfItemType FROM  dbo.tCustItemConfirm WHERE CustCode=? AND  ItemConfStatus IN ('2','3')) and ItemType1 = 'ZC' ) "
				   +" AND  Code NOT IN  (SELECT ItemTimeCode FROM tCustItemConfDate a WHERE a.CustCode =?) ";
		return this.findBySql(sql, new Object[]{custCode,custCode});
	
	}

}

