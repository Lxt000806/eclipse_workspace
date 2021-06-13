package com.house.home.dao.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.CommiCal;

@SuppressWarnings("serial")
@Repository
public class CommiCalDao extends BaseDao {

	/**
	 * CommiCal分页信息
	 * 
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCal commiCal) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCommiCal a where 1=1 ";

    	if (StringUtils.isNotBlank(commiCal.getNo())) {
			sql += " and a.No=? ";
			list.add(commiCal.getNo());
		}
    	if (StringUtils.isNotBlank(commiCal.getType())) {
			sql += " and a.Type=? ";
			list.add(commiCal.getType());
		}
    	if (StringUtils.isNotBlank(commiCal.getStatus())) {
			sql += " and a.Status=? ";
			list.add(commiCal.getStatus());
		}
    	if (StringUtils.isNotBlank(commiCal.getMon())) {
			sql += " and a.Mon=? ";
			list.add(commiCal.getMon());
		}
    	if (StringUtils.isNotBlank(commiCal.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(commiCal.getRemarks());
		}
    	if (commiCal.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(commiCal.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(commiCal.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(commiCal.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(commiCal.getExpired()) || "F".equals(commiCal.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(commiCal.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(commiCal.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, CommiCal commiCal) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select distinct a.No,a.Type,b.NOTE TypeDescr,a.Status,e.CustCode,"
         +"d.NOTE StatusDescr,a.Mon,a.Remarks,"
         +"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
         +"from tCommiCal a "
         +"join tCommiDetail e on a.No = e.CalNo and e.CustCode=? "
         +"left outer join tXTDM b on a.Type=b.CBM and b.ID='COMMICALTYPE' "
         +"left outer join tXTDM d on a.Status=d.CBM and d.ID='COMMICALSTATUS' "
         +"where 1=1 ";
		if (StringUtils.isNotBlank(commiCal.getCustCode())) {
    		list.add(commiCal.getCustCode());
    	}else{
    		return null;
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}

