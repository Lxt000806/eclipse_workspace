package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BuilderDeliv;

@SuppressWarnings("serial")
@Repository
public class BuilderDelivDao extends BaseDao {

	/**
	 * BuilderDeliv分页信息
	 * 
	 * @param page
	 * @param builderDeliv
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BuilderDeliv builderDeliv) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Code, a.DelivDate, a.DelivNum, a.BuilderCode,a.BuilderType,t1.note BuilderTypedescr,"
			 + "a.Remarks, a.Expired,a.LastUpdate,a.LastUpdatedBy  "
             + " from  tBuilderDeliv a "
             + " left join txtdm t1 on a.BuilderType = t1.CBM and t1.id='BUILDERTYPE' "
             + " where BuilderCode=? and a.expired='F' "
             + " order by Code ";
		list.add(builderDeliv.getBuilderCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 找第一个builderDelivCode
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findFirstDelivCode(String builderCode){
		List<Object> list = new ArrayList<Object>();
		String sql = " select top 1 Code"
	             + " from  tBuilderDeliv "
	             + " where BuilderCode=? and expired='F' "
	             + " order by Code ";
		list.add(builderCode);
		return this.findBySql(sql, list.toArray());
	}
	
}

