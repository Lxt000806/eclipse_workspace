package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustItemConfDate;
import com.house.home.entity.project.CustItemConfirm;

@SuppressWarnings("serial")
@Repository
public class CustItemConfDateDao extends BaseDao {

	/**
	 * CustItemConfDate分页信息
	 * 
	 * @param page
	 * @param custItemConfDate
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfDate custItemConfDate) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCustItemConfDate a where 1=1 ";

    	if (StringUtils.isNotBlank(custItemConfDate.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custItemConfDate.getCustCode());
		}
    	if (StringUtils.isNotBlank(custItemConfDate.getItemTimeCode())) {
			sql += " and a.ItemTimeCode=? ";
			list.add(custItemConfDate.getItemTimeCode());
		}
    	if (custItemConfDate.getConfirmDate() != null) {
			sql += " and a.ConfirmDate=? ";
			list.add(custItemConfDate.getConfirmDate());
		}
    	if (custItemConfDate.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custItemConfDate.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custItemConfDate.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custItemConfDate.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(custItemConfDate.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custItemConfDate.getActionLog());
		}
		if (StringUtils.isBlank(custItemConfDate.getExpired()) || "F".equals(custItemConfDate.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.CustCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public CustItemConfDate getCustItemConfDate(String custCode,
			String itemTimeCode) {
		String hql="from CustItemConfDate WHERE CustCode=? AND itemTimeCode=?";
		List<CustItemConfDate> list=this.find(hql, new Object[]{custCode,itemTimeCode});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

