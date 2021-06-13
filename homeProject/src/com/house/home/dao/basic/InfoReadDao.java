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
import com.house.home.entity.basic.InfoRead;

@SuppressWarnings("serial")
@Repository
public class InfoReadDao extends BaseDao {

	/**
	 * InfoRead分页信息
	 * 
	 * @param page
	 * @param infoRead
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InfoRead infoRead) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tInfoRead a where 1=1 ";

    	if (infoRead.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(infoRead.getPk());
		}
    	if (StringUtils.isNotBlank(infoRead.getInfoNum())) {
			sql += " and a.InfoNum=? ";
			list.add(infoRead.getInfoNum());
		}
    	if (StringUtils.isNotBlank(infoRead.getRcvCzy())) {
			sql += " and a.RcvCZY=? ";
			list.add(infoRead.getRcvCzy());
		}
    	if (StringUtils.isNotBlank(infoRead.getStatus())) {
			sql += " and a.Status=? ";
			list.add(infoRead.getStatus());
		}
    	if (infoRead.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(infoRead.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(infoRead.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(infoRead.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(infoRead.getExpired()) || "F".equals(infoRead.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(infoRead.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(infoRead.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("deprecation")
	public Result updateForProc(InfoRead infoRead) {
		Assert.notNull(infoRead);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pInfoSetStatus_forProc(?,?,?,?,?,?)}");
			call.setString(1, infoRead.getInfoNum());
			call.setString(2, infoRead.getRcvCzy());
			call.setString(3, infoRead.getStatus());
			call.setString(4, infoRead.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

}

