package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Item;
import com.house.home.entity.commi.CommiBasePersonal;

@SuppressWarnings("serial")
@Repository
public class CommiBasePersonalDao extends BaseDao {

	/**
	 * CommiBasePersonal分页信息
	 * 
	 * @param page
	 * @param commiBasePersonal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiBasePersonal commiBasePersonal) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select  a.PK, a.BaseItemType1, b.Descr BaseItemType1Descr, a.CommiPer, "
					+" a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog " 
				    +" from tCommiBasePersonal a "
					+" left join tBaseItemType1 b on a.BaseItemType1 = b.Code where 1=1 ";
    	if (StringUtils.isNotBlank(commiBasePersonal.getBaseItemType1())) {
			sql += " and a.BaseItemType1=? ";
			list.add(commiBasePersonal.getBaseItemType1());
		}
		if (StringUtils.isBlank(commiBasePersonal.getExpired()) || "F".equals(commiBasePersonal.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean checkCommiBasePersonalExist(CommiBasePersonal commiBasePersonal) {
		List<Object> list = new ArrayList<Object>();
		String sql="";
		if("A".equals(commiBasePersonal.getM_umState())){
			sql = "select 1 from tCommiBasePersonal where BaseItemType1 =? ";
			list.add(commiBasePersonal.getBaseItemType1());
		}else {
			sql = "select 1 from tCommiBasePersonal where BaseItemType1 =? and pk<>? ";
			list.add(commiBasePersonal.getBaseItemType1());
			list.add(commiBasePersonal.getPk());
		}
		List<Map<String,Object>> list1= this.findBySql(sql, list.toArray());
		if (list1!=null && list1.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
}

