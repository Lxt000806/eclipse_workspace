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
import com.house.home.entity.basic.PrjRole;
import com.house.home.entity.basic.PrjRolePrjItem;
import com.house.home.entity.basic.PrjRoleWorkType12;

@SuppressWarnings("serial")
@Repository
public class PrjRoleDao extends BaseDao {

	/**
	 * PrjRoleDao分页信息
	 * 
	 * @param page
	 * @param PrjRoleDao
	 * @return
	 */
	
	// add by hc  2017/11/22  begin 
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjRole prjRole) {
		List<Object> list = new ArrayList<Object>();

		String sql =  "select a.Code,a.Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from tPrjRole a  where 1=1";							
		
		if (StringUtils.isNotBlank(prjRole.getCode())) {
			sql += " and a.Code= ? ";
			list.add(prjRole.getCode());
		}
		if (StringUtils.isNotBlank(prjRole.getDescr())) {
			sql += " and a.Descr like  ? ";
			list.add("%"+prjRole.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPageBykuxzSql(Page<Map<String,Object>> page, PrjRole prjRole) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select a.Code prjItem,a.Descr prjItemDescr from  tPrjItem1 a where 1=1";							
		if (StringUtils.isNotBlank(prjRole.getUnSelected())) {
			sql += " and a.Code not in " + "('"+prjRole.getUnSelected().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by Seq ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPageBykuworkSql(Page<Map<String,Object>> page, PrjRole prjRole) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select code worktype12,descr worktype12descr from tworktype12 a where 1=1 ";							
		if (StringUtils.isNotBlank(prjRole.getUnSelected())) {
			sql += " and a.Code not in " + "('"+prjRole.getUnSelected().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPageBySqlItem(Page<Map<String,Object>> page, PrjRolePrjItem prjRolePrjItem) {
		List<Object> list = new ArrayList<Object>();

		String sql =  "select a.PK,a.PrjRole,b.Descr PrjRoleDescr,a.PrjItem,x1.NOTE PrjItemDescr,a.Expired,a.ActionLog," +
				"a.LastUpdate,a.LastUpdatedBy from tPrjRolePrjItem a " +
				"left join tPrjRole b on a.PrjRole=b.Code  " +
				"left join tXTDM x1 on a.PrjItem =x1.CBM and x1.ID='PRJITEM'  where 1=1";							
				
		if (StringUtils.isNotBlank(prjRolePrjItem.getPrjRole())) {
			sql += " and a.PrjRole= ? ";
			list.add(prjRolePrjItem.getPrjRole());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.PK ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String,Object>> findPageBySqlWork(Page<Map<String,Object>> page, PrjRoleWorkType12 prjRoleWorkType12) {
		List<Object> list = new ArrayList<Object>();

		String sql =  "select a.PK,a.PrjRole, b.Descr PrjRoleDescr,a.WorkType12,c.Descr WorkType12Descr,a.LastUpdate," +
				"a.LastUpdatedBy,a.Expired,a.ActionLog from tPrjRoleWorkType12 a " +
				"left join tPrjRole b on a.PrjRole = b.Code " +
				"left join tWorkType12 c on a.WorkType12=c.Code  where 1=1";							
		
		if (StringUtils.isNotBlank(prjRoleWorkType12.getPrjRole())) {
			sql += " and a.PrjRole= ? ";
			list.add(prjRoleWorkType12.getPrjRole());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.PK ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	@SuppressWarnings("unchecked") 
	public PrjRole getByCode(String code) {
		String hql = "from PrjRole a where a.expired='F' and a.code=?";
		List<PrjRole> list = this.find(hql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked") 
	public PrjRole getByDescr(String descr) {
		String hql = "from PrjRole a where a.expired='F' and a.descr=? ";
		List<PrjRole> list = this.find(hql, new Object[]{descr});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked") 
	public PrjRole getByDescr1(String descr,String descr1) {
		String hql = "from PrjRole a where a.expired='F' and   a.descr=?  and a.descr!=?";
		List<PrjRole> list = this.find(hql, new Object[]{descr,descr1});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public Result doPrjRoleCheckOut(PrjRole prjRole) {
		Assert.notNull(prjRole);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjRole(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, prjRole.getM_umState());
			call.setString(2, prjRole.getCode());	
			call.setString(3, prjRole.getDescr());
			call.setTimestamp(4, new java.sql.Timestamp(prjRole.getLastUpdate().getTime()));
			call.setString(5, prjRole.getLastUpdatedBy());
			call.setString(6, prjRole.getExpired());
			call.setString(7, prjRole.getActionLog());	
			call.setString(8, prjRole.getSalesInvoiceDetailJson());	  //工种类型
			call.setString(9, prjRole.getPrjRoleJson());   //施工项目
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
	// add by hc  2017/11/22  end
}

