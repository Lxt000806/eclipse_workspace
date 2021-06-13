package com.house.home.dao.oa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.home.entity.oa.AppErp;
import com.house.home.entity.oa.Leave;

@SuppressWarnings("serial")
@Repository
public class ErpAppDao extends BaseDao{
	
	
	@SuppressWarnings("unchecked")
	public AppErp getByProcessInstanceId(String id) {
		String hql = "from AppErp a where a.processInstanceId=?";
		List<AppErp> list = this.find(hql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	
	
	
	
}
