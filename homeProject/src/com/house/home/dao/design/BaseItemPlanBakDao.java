package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.BaseItemPlanBak;

@SuppressWarnings("serial")
@Repository
public class BaseItemPlanBakDao extends BaseDao {

	/**
	 * BaseItemPlanBak分页信息
	 * 
	 * @param page
	 * @param baseItemPlanBak
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemPlanBak baseItemPlanBak) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No, a.CustCode, a.CustType, a.BaseTempNo,b.descr basetempdescr, a.Remark,a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog from tBaseItemPlanBak a " +
				"left join tBasePlanTemp b on b.no=a.BaseTempNo " +
				"where 1=1 ";

    	if (StringUtils.isNotBlank(baseItemPlanBak.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+baseItemPlanBak.getNo().trim()+"%");
		}
    	if (StringUtils.isNotBlank(baseItemPlanBak.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(baseItemPlanBak.getCustCode());
		}
    	if (StringUtils.isNotBlank(baseItemPlanBak.getCustType()) && !"1".equals(baseItemPlanBak.getIsOutSet()) ) {
			sql += " and a.CustType=? ";
			list.add(baseItemPlanBak.getCustType());
		}
    	if (StringUtils.isNotBlank(baseItemPlanBak.getBaseTempNo())) {
			sql += " and a.BaseTempNo=? ";
			list.add(baseItemPlanBak.getBaseTempNo());
		}
    	if (StringUtils.isNotBlank(baseItemPlanBak.getRemark())) {
			sql += " and a.Remark like ? ";
			list.add("%"+baseItemPlanBak.getRemark().trim()+"%");
		}
    	if (baseItemPlanBak.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseItemPlanBak.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseItemPlanBak.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItemPlanBak.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(baseItemPlanBak.getExpired()) || "F".equals(baseItemPlanBak.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(baseItemPlanBak.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItemPlanBak.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

