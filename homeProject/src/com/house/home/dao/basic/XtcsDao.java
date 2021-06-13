package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Xtcs;

@SuppressWarnings("serial")
@Repository
public class XtcsDao extends BaseDao {

	/**
	 * Xtcs分页信息
	 * 
	 * @param page
	 * @param xtcs
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Xtcs xtcs) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tXTCS a where 1=1 ";

    	if (StringUtils.isNotBlank(xtcs.getId())) {
			sql+=" and 	a.ID like ? ";
			list.add("%"+xtcs.getId()+"%");
		}
    	if (StringUtils.isNotBlank(xtcs.getQz())) {
			sql += " and a.QZ=? ";
			list.add(xtcs.getQz());
		}
    	if (StringUtils.isNotBlank(xtcs.getSm())) {
			sql+=" and a.SM like ? ";
			list.add("%"+xtcs.getSm()+"%");
		}
    	if (StringUtils.isNotBlank(xtcs.getSmE())) {
			sql += " and a.SM_E=? ";
			list.add(xtcs.getSmE());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getQzById(String id) {
		Xtcs xtcs = new Xtcs();
		xtcs.setId(id);
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		page = this.findPageBySql(page, xtcs);
		if (!page.getResult().isEmpty()) {
			return ((String)(page.getResult().get(0).get("QZ")));
		} else {
			return "";
		}
	}

}

