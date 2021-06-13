package com.house.home.dao.basic;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Department3;
@SuppressWarnings("serial")
@Repository
public class StaffingDao extends BaseDao{

	/**
	 * 人员编制统计查询
	 * @author	created by zb
	 * @date	2018-12-19--上午9:41:09
	 * @param page
	 * @param departType
	 * @param department3
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, String departType, Department3 department3) {
		Assert.notNull(department3);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			if ("department1".equals(departType)) {
				call = conn.prepareCall("{Call pRybztj_Dept1(?)}");
				call.setString(1, department3.getDepartment1()==null?"":department3.getDepartment1());
			} else if ("department2".equals(departType)) {
				call = conn.prepareCall("{Call pRybztj_Dept2(?,?)}");
				call.setString(1, department3.getDepartment1()==null?"":department3.getDepartment1());
				call.setString(2, department3.getDepartment2()==null?"":department3.getDepartment2());
			} else {
				call = conn.prepareCall("{Call pRybztj_Dept3(?,?,?)}");
				call.setString(1, department3.getDepartment1()==null?"":department3.getDepartment1());
				call.setString(2, department3.getDepartment2()==null?"":department3.getDepartment2());
				call.setString(3, department3.getCode()==null?"":department3.getCode());
			}
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list);
			page.setTotalCount(page.getResult().size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return page;
	}



}
