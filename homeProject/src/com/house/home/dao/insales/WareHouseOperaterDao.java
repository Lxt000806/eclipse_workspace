package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.WareHouseOperater;


@SuppressWarnings("serial")
@Repository
public class WareHouseOperaterDao extends BaseDao {

	/**
	 * WareHouseOperater分页信息
	 * 
	 * @param page
	 * @param wareHouseOperater
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHouseOperater wareHouseOperater) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tWareHouseOperater a where 1=1 ";

    	if (wareHouseOperater.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(wareHouseOperater.getPk());
		}
    	if (StringUtils.isNotBlank(wareHouseOperater.getWhcode())) {
			sql += " and a.WHCode=? ";
			list.add(wareHouseOperater.getWhcode());
		}
    	if (StringUtils.isNotBlank(wareHouseOperater.getCzybh())) {
			sql += " and a.CZYBH=? ";
			list.add(wareHouseOperater.getCzybh());
		}
    	if (wareHouseOperater.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(wareHouseOperater.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(wareHouseOperater.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(wareHouseOperater.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(wareHouseOperater.getExpired()) || "F".equals(wareHouseOperater.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(wareHouseOperater.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(wareHouseOperater.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByCzy(
			Page<Map<String, Object>> page, String id) {
		String sql="SELECT a.WHCode,b.Desc1 FROM tWareHouseOperater a INNER JOIN tWareHouse b ON a.WHCode=b.Code WHERE a.CZYBH=?";
		return this.findPageBySql(page, sql, new Object[]{id});
	}

	public List<Map<String, Object>> findByCzybh(String czybh) {
		String sql = "select a.WHCode,b.Desc1 whCodeDescr "
                +"from tWareHouseOperater a "
                +"left join tWareHouse b on a.WHCode=b.Code "
                +"where a.CZYBH=? order by a.PK";
		return this.findBySql(sql.toLowerCase(), new Object[]{czybh});
	}

}

