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
import com.house.home.entity.basic.Algorithm;

@SuppressWarnings("serial")
@Repository
public class AlgorithmDao extends BaseDao {

	/**
	 * Algorithm分页信息
	 * 
	 * @param page
	 * @param algorithm
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Algorithm algorithm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.Code , a.Descr,  a.LastUpdate, a.LastUpdatedBy, a.Expired,a.ActionLog,a.iscalcutfee, "
				+ "b.note iscalcutfeedescr,a.remarks from  tAlgorithm a "
				+ "left join tXTDM b on a.iscalcutfee=b.CBM and b.id='YESNO' where 1=1  ";

		if (StringUtils.isNotBlank(algorithm.getCode())) {
			sql += " and a.Code=? ";
			list.add(algorithm.getCode());
		}
		if (StringUtils.isNotBlank(algorithm.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + algorithm.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(algorithm.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(algorithm.getLastUpdatedBy());
		}
		if (algorithm.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(algorithm.getLastUpdate());
		}
		if (StringUtils.isNotBlank(algorithm.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(algorithm.getActionLog());
		}
		if (StringUtils.isBlank(algorithm.getExpired())
				|| "F".equals(algorithm.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(algorithm.getIsCalCutFee())) {
			sql += " and a.IsCalCutFee=? ";
			list.add(algorithm.getIsCalCutFee());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.lastupdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * Algorithm明细分页信息
	 * 
	 * @param page
	 * @param algorithm
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, Algorithm algorithm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  a.PK, a.code, a.Uom,a.ItemType2, a.ItemType3,  a.LastUpdate, "
				+ "a.LastUpdatedBy, a.Expired, a.ActionLog, b.descr ItemType2descr, "
				+ "c.descr ItemType3descr "
				+ "from  tAlgorithmItem a "
				+ "left outer join tItemType2 b on b.code = a.ItemType2 "
				+ "left outer join tItemType3 c on c.code = a.ItemType3 "
				+ "where a.Code=?";
		list.add(algorithm.getCode());
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 判断是否存在descr
	 * 
	 * @param algorithm
	 * @return
	 */
	public boolean hasDescr(Algorithm algorithm) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from tAlgorithm where Descr = ? ";
		list.add(algorithm.getDescr());
		List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		return !page.isEmpty();
	}
	/**
	 * 保存
	 * @param algorithm
	 * @return
	 */
	public Result doSave(Algorithm algorithm) {
		Assert.notNull(algorithm);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pKsybjsfGl_forXml(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, algorithm.getM_umState());
			call.setString(2, algorithm.getCode());
			call.setString(3, algorithm.getDescr());
			call.setString(4, algorithm.getExpired());
			call.setString(5, algorithm.getLastUpdatedBy());
			call.setString(6, algorithm.getIsCalCutFee());
			call.setString(7, algorithm.getDetailJson());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.setString(10,algorithm.getRemarks());
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 判断是否切割费
	 * 
	 * @param algorithm
	 * @return
	 */
	public List<Map<String, Object>> checkIsCalCutFee(Algorithm algorithm) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select isCalCutFee from tAlgorithm where code = ? ";
		list.add(algorithm.getCode());
		List<Map<String, Object>> para = this.findBySql(sql, list.toArray());
		return para;
	}
}
