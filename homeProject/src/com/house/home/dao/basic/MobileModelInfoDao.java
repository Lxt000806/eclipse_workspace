package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;

@SuppressWarnings("serial")
@Repository
public class MobileModelInfoDao extends BaseDao {

	public Map<String,Object> getMobileModelInfo(String manufacturer, String model, String version) {
		List<Object> params = new ArrayList<Object>();

		String sql = " select CallRecordPath from tMobileModelInfo where 1=1 ";
		if(StringUtils.isNotBlank(manufacturer)){
			sql+=" and Manufacturer=? ";
			params.add(manufacturer);
		}
    	if (StringUtils.isNotBlank(model)) {
			sql += " and Model=? ";
			params.add(model);
		}
    	
    	sql += "order by PK asc";
    	
    	List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
    	if(list != null && list.size() > 0){
    		return list.get(0);
    	}
    	
		return null;
	}

}

