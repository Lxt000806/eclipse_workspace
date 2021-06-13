package com.house.home.dao.mall;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;

@SuppressWarnings("serial")
@Repository
public class MallBaseDao extends BaseDao {
	
	public List<Map<String, Object>> getWorkType12List(){
		String sql = " select Code, Descr "
				   + " from tWorkType12 "
				   + " where IsRegisterMall = '1' and Expired='F' "
				   + " order by DispSeq ";
		return this.findBySql(sql, new Object[]{});
	}
}
