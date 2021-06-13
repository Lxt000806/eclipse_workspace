package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.MainCommiPerc;

@SuppressWarnings("serial")
@Repository
public class MainCommiPercDao extends BaseDao {

	/**
	 * MainCommiPerc分页信息
	 * 
	 * @param page
	 * @param mainCommiPerc
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiPerc mainCommiPerc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.Code, a.Descr, a.CommiPerc, "
				+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+ " from tMainCommiPerc a"
				+ " where 1=1 ";
		
		if (StringUtils.isNotBlank(mainCommiPerc.getCode())) {
			sql += " and a.Code = ? ";
			list.add(mainCommiPerc.getCode());
		}
		if (StringUtils.isNotBlank(mainCommiPerc.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(mainCommiPerc.getDescr());
		}
    	if (StringUtils.isBlank(mainCommiPerc.getExpired()) || "F".equals(mainCommiPerc.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.Code  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean checkExistMainCommiPerc(MainCommiPerc mainCommiPerc) {
		List<Object> list = new ArrayList<Object>();
		String sql="";
		if("A".equals(mainCommiPerc.getM_umState())||"C".equals(mainCommiPerc.getM_umState())){
			sql = "select 1 from tMainCommiPerc where Descr =? or Code=?";
			list.add(mainCommiPerc.getDescr());
			list.add(mainCommiPerc.getCode());
		}else if("M".equals(mainCommiPerc.getM_umState())){
			sql = "select 1 from tMainCommiPerc where Descr =? and Code<>?";
			list.add(mainCommiPerc.getDescr());
			list.add(mainCommiPerc.getCode());
		}
		List<Map<String,Object>> listResult= this.findBySql(sql, list.toArray());
		if (listResult!=null && listResult.size()>0){
			return true;
		}else{
			return false;
		}
	}

}

