package com.house.home.dao.basic;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.BaseAlgorithm;
@SuppressWarnings("serial")
@Repository
public class BaseAlgorithmDao extends BaseDao{

	/**
	 * @Description: TODO 基础项目算法管理——分页查询
	 * @author	created by zb
	 * @date	2018-8-28--下午12:17:58
	 * @param page
	 * @param baseAlgorithm
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BaseAlgorithm baseAlgorithm) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.Code , a.Descr, a.Remarks, a.LastUpdate, a.LastUpdatedBy, a.Expired,a.ActionLog " +
				" from  tBaseAlgorithm a " +
				" where 1=1 ";
		
		if (StringUtils.isBlank(baseAlgorithm.getExpired()) || "F".equals(baseAlgorithm.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(baseAlgorithm.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+baseAlgorithm.getCode()+"%");
		}
		if (StringUtils.isNotBlank(baseAlgorithm.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+baseAlgorithm.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: TODO 走存储过程
	 * @author	created by zb
	 * @date	2018-8-28--下午6:04:22
	 * @param baseAlgorithm
	 * @return
	 */
	public Result doSave(BaseAlgorithm baseAlgorithm) {
		Assert.notNull(baseAlgorithm);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBaseAlgorithm(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, baseAlgorithm.getM_umState());
			call.setString(2, baseAlgorithm.getCode());
			call.setString(3, baseAlgorithm.getDescr());
			call.setString(4, baseAlgorithm.getRemarks());
			call.setString(5, baseAlgorithm.getExpired());
			call.setString(6, baseAlgorithm.getLastUpdatedBy());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.setString(9, baseAlgorithm.getDetailJson());
//			System.out.println(baseAlgorithm.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description: TODO 根据基础项目算法编码查找算法适用类型
	 * @author	created by zb
	 * @date	2018-8-31--下午4:10:51
	 * @param page
	 * @param baseAlgorithm
	 * @return
	 */
	public Page<Map<String, Object>> findPrjTypePageBySql(
			Page<Map<String, Object>> page, BaseAlgorithm baseAlgorithm) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.Code, a.PrjType, tx.NOTE PrjTypeDescr, a.LastUpdate, " +
				" a.LastUpdatedBy, a.Expired, a.ActionLog " +
				" from tBaseAlgorithmPrjType a " +
				" 	left join tXTDM tx on tx.CBM = a.PrjType and tx.ID = 'BASEITEMPRJTYPE' " +
				" where a.Code = ? " +
				" order by LastUpdate desc ";
		list.add(baseAlgorithm.getCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getBaseAlgorithmByDescr(String descr){
		String sql = " select Code from tBaseAlgorithm where Expired='F' and  descr=? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{descr});
		if (list!=null && list.size()>0){
			 return  (String)list.get(0).get("Code");
		}
		return " ";
	}

	/**
	 * @Description: TODO 判断是否存在descr
	 * @author	created by zb
	 * @date	2018-9-4--上午9:07:06
	 * @param baseAlgorithm
	 * @return
	 */
	public boolean hasDescr(BaseAlgorithm baseAlgorithm) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from tBaseAlgorithm where Descr = ? ";
		list.add(baseAlgorithm.getDescr());
		List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		return !page.isEmpty();
	}
	
}
