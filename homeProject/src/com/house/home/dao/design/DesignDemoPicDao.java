package com.house.home.dao.design;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class DesignDemoPicDao extends BaseDao{

	public List<Map<String, Object>> findNoPushYunPics() {
		String sql = " select a.PK,a.PhotoName,b.CustCode,a.no from tDesignDemoPic a " +
				" inner join tDesignDemo b on b.No=a.No " +
				" where IsSendYun='0' ";
		return this.findBySql(sql);
	}

	public void updateDesignDemoPicStatus(){
		String sql = " update tDesignDemoPic set isSendYun='1' ,lastUpdate = getDate(),sendDate=getDate(),lastUpdatedBy='1' where isSendYun ='0' ";
										
		this.executeUpdateBySql(sql, new Object[]{});
		
	}
}
