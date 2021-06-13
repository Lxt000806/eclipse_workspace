package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Ticket;
import com.house.home.entity.basic.Uom;
import com.sun.org.apache.bcel.internal.generic.Select;

@SuppressWarnings("serial")
@Repository
public class UomDao extends BaseDao {

	/**
	 * uom分页信息
	 * 
	 * @param page
	 * @param ticket
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Uom uom) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.Code,a.Descr,a.Lastupdate,a.Lastupdatedby,a.Expired,a.Actionlog " +
				"from tUom a " +
				"where 1=1 ";
		if (StringUtils.isNotBlank(uom.getCode())) {
			sql += " and a.Code =? ";
			list.add(uom.getCode());
		}
		if (StringUtils.isNotBlank(uom.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + uom.getDescr() + "%");
		}
		if (StringUtils.isBlank(uom.getExpired()) || "F".equals(uom.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public boolean valideUom(String id){
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(id)) {
			String sql = "select * from tUom a where a.code =? ";
			params.add(id);
			List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
			
			if (list !=null && list.size()>0) {
				return true;
			}
		}
		return false;
	}
}

