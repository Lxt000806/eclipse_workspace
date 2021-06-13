package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustItemConfProg;

@SuppressWarnings("serial")
@Repository
public class CustItemConfProgDao extends BaseDao {

	/**
	 * CustItemConfProg分页信息
	 * 
	 * @param page
	 * @param custItemConfProg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfProg custItemConfProg) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.no,a.custCode,a.confirmDate,b.note ItemConfStatusDescr,a.lastupdatedby from tCustItemConfProg a " +
				" LEFT  JOIN tXTDM b ON a.ItemConfStatus=b.CBM AND  b.ID='ITEMCONFSTS' where 1=1  ";

    	if (StringUtils.isNotBlank(custItemConfProg.getNo())) {
			sql += " and a.No=? ";
			list.add(custItemConfProg.getNo());
		}
    	if (StringUtils.isNotBlank(custItemConfProg.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custItemConfProg.getCustCode());
		}
    	if (custItemConfProg.getConfirmDate() != null) {
			sql += " and a.ConfirmDate=? ";
			list.add(custItemConfProg.getConfirmDate());
		}
    	if (StringUtils.isNotBlank(custItemConfProg.getItemConfStatus())) {
			sql += " and a.ItemConfStatus=? ";
			list.add(custItemConfProg.getItemConfStatus());
		}
    	if (custItemConfProg.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custItemConfProg.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custItemConfProg.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custItemConfProg.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(custItemConfProg.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custItemConfProg.getActionLog());
		}
		if (StringUtils.isBlank(custItemConfProg.getExpired()) || "F".equals(custItemConfProg.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custItemConfProg.getRemarks())) {
			sql += " and a.remarks=? ";
			list.add(custItemConfProg.getRemarks());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

