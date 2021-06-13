package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustItemConfProgDt;

@SuppressWarnings("serial")
@Repository
public class CustItemConfProgDtDao extends BaseDao {

	/**
	 * CustItemConfProgDt分页信息
	 * 
	 * @param page
	 * @param custItemConfProgDt
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfProgDt custItemConfProgDt) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select b.Descr ConfItemTypeDescr,a.LastUpdatedBy from tCustItemConfProgDt a " +
				" LEFT JOIN tConfItemType b ON a.ConfItemType=b.Code where 1=1 ";

    	if (custItemConfProgDt.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custItemConfProgDt.getPk());
		}
    	if (StringUtils.isNotBlank(custItemConfProgDt.getConfProgNo())) {
			sql += " and a.ConfProgNo=? ";
			list.add(custItemConfProgDt.getConfProgNo());
		}
    	if (StringUtils.isNotBlank(custItemConfProgDt.getConfItemType())) {
			sql += " and a.ConfItemType=? ";
			list.add(custItemConfProgDt.getConfItemType());
		}
    	if (StringUtils.isNotBlank(custItemConfProgDt.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custItemConfProgDt.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(custItemConfProgDt.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custItemConfProgDt.getActionLog());
		}
		if (StringUtils.isBlank(custItemConfProgDt.getExpired()) || "F".equals(custItemConfProgDt.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (custItemConfProgDt.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custItemConfProgDt.getLastUpdate());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

