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
import com.house.home.entity.basic.BasePlanTemp;
import com.house.home.entity.insales.BaseItem;

@SuppressWarnings("serial")
@Repository
public class BasePlanTempDao extends BaseDao {

	/**
	 * BasePlanTemp分页信息
	 * 
	 * @param page
	 * @param basePlanTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BasePlanTemp basePlanTemp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No , a.Descr,x1.note CustTypeDescr,"
				+ " a.LastUpdate, a.LastUpdatedBy, a.Expired,a.ActionLog "
				+ " from tbasePlanTemp a " 
				+" left join tXtdm x1 on x1.cbm=a.CustType and x1.Id='CustType' "
				+" where 1=1  ";

		if (StringUtils.isNotBlank(basePlanTemp.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+basePlanTemp.getNo()+"%");
		}
		if (StringUtils.isNotBlank(basePlanTemp.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+basePlanTemp.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(basePlanTemp.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(basePlanTemp.getLastUpdatedBy());
		}
		if (basePlanTemp.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(basePlanTemp.getLastUpdate());
		}
		if (StringUtils.isNotBlank(basePlanTemp.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(basePlanTemp.getActionLog());
		}
		if (StringUtils.isBlank(basePlanTemp.getExpired())
				|| "F".equals(basePlanTemp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(basePlanTemp.getCustType())){
			sql += " and ( a.custType in " + "('"+basePlanTemp.getCustType().replaceAll(",", "','")+"')" +
					" or a.custType='')  ";
			
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
	 * 名称和客户名是否已存在
	 * @param basePlanTemp
	 * @return
	 */
	public List<Map<String, Object>> checkExist(BasePlanTemp basePlanTemp) {
		List<Object> paraList = new ArrayList<Object>();
		String sql = "select 1 from tbasePlanTemp where custType=? and Descr=? ";
		paraList.add(basePlanTemp.getCustType());
		paraList.add(basePlanTemp.getDescr());
		if(StringUtils.isNotBlank(basePlanTemp.getNo())){
			sql+=" and no <> ?";
			paraList.add(basePlanTemp.getNo());
		}
		List<Map<String, Object>> list = this.findListBySql(sql,
				paraList.toArray());
		return list;
	}
	/**
	 * 保存
	 * 
	 * @param basePlanTemp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(BasePlanTemp basePlanTemp) {
		Assert.notNull(basePlanTemp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJcslmbGl_forXml(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, basePlanTemp.getM_umState());
			call.setString(2, basePlanTemp.getNo());	
			call.setString(3, basePlanTemp.getDescr());
			call.setString(4, basePlanTemp.getExpired());
			call.setString(5, basePlanTemp.getLastUpdatedBy());
			call.setString(6, basePlanTemp.getCustType());
			call.setString(7, basePlanTemp.getBasePlanTempDetailJson());
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

