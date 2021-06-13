package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.WHPosiTran;

@SuppressWarnings("serial")
@Repository
public class WHPosiTranDao extends BaseDao {

	/**
	 * WHPosiTran分页信息
	 * 
	 * @param page
	 * @param wHPosiTran
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHPosiTran wHPosiTran) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tWHPosiTran a where 1=1 ";

    	if (wHPosiTran.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(wHPosiTran.getPk());
		}
    	if (wHPosiTran.getWhppk() != null) {
			sql += " and a.WHPPk=? ";
			list.add(wHPosiTran.getWhppk());
		}
    	if (StringUtils.isNotBlank(wHPosiTran.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(wHPosiTran.getItcode());
		}
    	if (wHPosiTran.getTrsQty() != null) {
			sql += " and a.TrsQty=? ";
			list.add(wHPosiTran.getTrsQty());
		}
    	if (wHPosiTran.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(wHPosiTran.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(wHPosiTran.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(wHPosiTran.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(wHPosiTran.getExpired()) || "F".equals(wHPosiTran.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(wHPosiTran.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(wHPosiTran.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

