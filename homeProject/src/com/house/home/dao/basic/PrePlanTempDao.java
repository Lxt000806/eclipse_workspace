package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
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
import com.house.home.entity.basic.PrePlanTemp;
import com.house.home.entity.project.WorkCost;

@SuppressWarnings("serial")
@Repository
public class PrePlanTempDao extends BaseDao {

	/**
	 * PrePlanTemp分页信息
	 * 
	 * @param page
	 * @param prePlanTemp
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrePlanTemp prePlanTemp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No , a.Descr,x1.note CustTypeDescr,"
				+ " a.LastUpdate, a.LastUpdatedBy, a.Expired,a.ActionLog "
				+ " from tPrePlanTemp a " 
				+" left join tXtdm x1 on x1.cbm=a.CustType and x1.Id='CustType' "
				+" where 1=1  ";

		if (StringUtils.isNotBlank(prePlanTemp.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+prePlanTemp.getNo()+"%");
		}
		if (StringUtils.isNotBlank(prePlanTemp.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+prePlanTemp.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(prePlanTemp.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prePlanTemp.getLastUpdatedBy());
		}
		if (prePlanTemp.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(prePlanTemp.getLastUpdate());
		}
		if (StringUtils.isNotBlank(prePlanTemp.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prePlanTemp.getActionLog());
		}
		if (StringUtils.isBlank(prePlanTemp.getExpired())
				|| "F".equals(prePlanTemp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(prePlanTemp.getCustType())){
			sql += " and a.custType in " + "('"+prePlanTemp.getCustType().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 保存
	 * 
	 * @param prePlanTemp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(PrePlanTemp prePlanTemp) {
		Assert.notNull(prePlanTemp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pKsybjmbGl_forXml(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, prePlanTemp.getM_umState());
			call.setString(2, prePlanTemp.getNo());	
			call.setString(3, prePlanTemp.getDescr());
			call.setString(4, prePlanTemp.getExpired());
			call.setString(5, prePlanTemp.getLastUpdatedBy());
			call.setString(6, prePlanTemp.getCustType());
			call.setString(7, prePlanTemp.getPrePlanTempDetailJson());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
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
}
