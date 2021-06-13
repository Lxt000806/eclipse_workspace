package com.house.home.dao.query;

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
import com.house.home.entity.project.CustWorker;

@Repository
@SuppressWarnings("serial")
public class QualityCheckBoardDao extends BaseDao {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustWorker custWorker) {
		Assert.notNull(custWorker);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pQualityCheckBoard(?,?,?)}");
			call.setString(1, custWorker.getWorkType12());
			call.setString(2, custWorker.getWorkType12Dept());
			call.setString(3, custWorker.getWorkerClassify());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
	
	public Page<Map<String,Object>> findDetailBySql(Page<Map<String,Object>> page, CustWorker custWorker) {
		Assert.notNull(custWorker);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pQualityCheckBoardDetail(?,?,?,?)}");
			call.setString(1, custWorker.getWorkType12());
			call.setString(2, custWorker.getWorkType12Dept());
			call.setString(3, custWorker.getWorkerClassify());
			call.setString(4, custWorker.getSourceType());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
	
}
