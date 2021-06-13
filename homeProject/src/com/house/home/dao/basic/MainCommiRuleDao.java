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
import com.house.home.entity.basic.MainCommiRule;

@SuppressWarnings("serial")
@Repository
public class MainCommiRuleDao extends BaseDao {

	/**
	 * MainCommiRule分页信息
	 * 
	 * @param page
	 * @param mainCommiRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiRule mainCommiRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.No,a.CommiType,a.FromProfit,a.ToProfit,a.CommiPerc,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.Note CommiTypeDescr " +
				" from tMainCommiRule a left join tXTDM b on a.CommiType=b.CBM and b.ID='COMMITYPE' " +
				" where 1=1 ";

    	
    	if (StringUtils.isNotBlank(mainCommiRule.getCommiType())) {
			sql += " and a.CommiType=? ";
			list.add(mainCommiRule.getCommiType());
		}
    	if (mainCommiRule.getFromProfit() != null) {
			sql += " and a.FromProfit=? ";
			list.add(mainCommiRule.getFromProfit());
		}
    	if (mainCommiRule.getToProfit() != null) {
			sql += " and a.ToProfit=? ";
			list.add(mainCommiRule.getToProfit());
		}
    	if (mainCommiRule.getCommiPerc() != null) {
			sql += " and a.CommiPerc=? ";
			list.add(mainCommiRule.getCommiPerc());
		}
		if (StringUtils.isBlank(mainCommiRule.getExpired()) || "F".equals(mainCommiRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailByNo(Page<Map<String,Object>> page, MainCommiRule mainCommiRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.ItemType2,a.ItemType3,b.Descr ItemType2Descr,c.Descr ItemType3Descr ,a.LastUpdate , a.LastUpdatedBy , a.Expired , a.ActionLog "
					+" from tMainCommiRuleItem a "
				    +" left join tItemType2 b on a.ItemType2 = b.Code "
				    +" left join titemtype3 c on a.ItemType3 = c.Code "
				    +" where a.no=? ";

		list.add(mainCommiRule.getNo());
		if (StringUtils.isBlank(mainCommiRule.getExpired()) || "F".equals(mainCommiRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	
	@SuppressWarnings("unchecked")
	public MainCommiRule getByNo(String no){
		String hql="from MainCommiRule where no =?";
		List<MainCommiRule> list = this.find(hql, new Object[]{no});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}
	
	public Result doSave(MainCommiRule mainCommiRule) {
		Assert.notNull(mainCommiRule);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pZctcgz_forXml(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, mainCommiRule.getM_umState());
			call.setString(2, mainCommiRule.getNo());
			call.setString(3, mainCommiRule.getCommiType());
			call.setDouble(4, mainCommiRule.getFromProfit());
			call.setDouble(5, mainCommiRule.getToProfit());
			call.setDouble(6, mainCommiRule.getCommiPerc());
			call.setString(7, mainCommiRule.getRemarks());
			call.setString(8, mainCommiRule.getLastUpdatedBy());
			call.setString(9, mainCommiRule.getDetailJson());
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(10)));
			result.setInfo(call.getString(11));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
}

