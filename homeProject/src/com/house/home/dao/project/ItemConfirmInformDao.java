package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ItemConfirmInform;

@SuppressWarnings("serial")
@Repository
public class ItemConfirmInformDao extends BaseDao {

	/**
	 * ItemConfirmInform分页信息
	 * 
	 * @param page
	 * @param itemConfirmInform
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemConfirmInform itemConfirmInform) {
		List<Object> list = new ArrayList<Object>();

		String sql = " SELECT a.ItemTimeCode,a.InformDate,a.PlanComeDate,a.InformRemark,b.descr itemTimeDescr FROM dbo.tItemConfirmInform a" +
					" left join  tConfItemTime b on a.ItemTimeCode=b.code where 1=1 ";

    	if (itemConfirmInform.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemConfirmInform.getPk());
		}
    	if (StringUtils.isNotBlank(itemConfirmInform.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemConfirmInform.getCustCode());
		}
    	if (StringUtils.isNotBlank(itemConfirmInform.getItemTimeCode())) {
			sql += " and a.ItemTimeCode=? ";
			list.add(itemConfirmInform.getItemTimeCode());
		}
    	if (itemConfirmInform.getInformDate() != null) {
			sql += " and a.InformDate=? ";
			list.add(itemConfirmInform.getInformDate());
		}
    	if (itemConfirmInform.getPlanComeDate() != null) {
			sql += " and a.PlanComeDate=? ";
			list.add(itemConfirmInform.getPlanComeDate());
		}
    	if (StringUtils.isNotBlank(itemConfirmInform.getInformRemark())) {
			sql += " and a.InformRemark=? ";
			list.add(itemConfirmInform.getInformRemark());
		}
    	if (itemConfirmInform.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemConfirmInform.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemConfirmInform.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemConfirmInform.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(itemConfirmInform.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemConfirmInform.getActionLog());
		}
		if (StringUtils.isBlank(itemConfirmInform.getExpired()) || "F".equals(itemConfirmInform.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

