package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Types;
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
import com.house.home.entity.basic.cmpActivity;
import com.house.home.entity.basic.cmpActivityGift;

@SuppressWarnings("serial")
@Repository
public class cmpActivityDao extends BaseDao {

	/**
	 * cmpActivity分页信息
	 * 
	 * @param page
	 * @param cmpActivity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, cmpActivity cmpactivity) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.No,a.Descr,a.BeginDate,a.EndDate,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired," +
				"a.ActionLog from tcmpActivity a " +
				"where 1=1  ";							
		if (StringUtils.isNotBlank(cmpactivity.getGiftCode())) {
			sql += "and exists (select 1 from tCmpActivityGift in_a where in_a.No=a.No and in_a.ItemCode in ('"+
					cmpactivity.getGiftCode().replace(",", "','")+"')) ";
		}
		if (StringUtils.isNotBlank(cmpactivity.getNo())) {
			sql += " and a.No= ? ";
			list.add(cmpactivity.getNo());
		}    
    	if (StringUtils.isNotBlank(cmpactivity.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+cmpactivity.getDescr()+"%");
		}     	
    	if (cmpactivity.getBeginDate() != null) {
			sql += " and a.BeginDate >= ? ";
			list.add(cmpactivity.getBeginDate());
		}
		if (cmpactivity.getEndDate() != null) {
			sql += " and a.BeginDate <= ? ";
			list.add(cmpactivity.getEndDate());
		} 
		if (!"T".equals(cmpactivity.getExpired())) {
			sql += " and a.Expired='F' ";
		}	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//编辑时候查询明细表
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, cmpActivityGift cmpactivitygift) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.PK,a.No,a.Type,x1.Note TypeDescr,a.ItemCode,t1.Descr ItemCodeDescr,a.LastUpdate,a.LastUpdatedBy," +
				"a.Expired,a.ActionLog from tcmpActivityGift a " +
				"left join tXTDM x1 on a.Type=x1.cbm and x1.id='ACTGIFTTYPE' " +
				"left join tItem t1 on a.ItemCode=t1.Code and ItemType1='LP' "
					+ " where 1=1";									  
		if (cmpactivitygift.getNo() != null ){
			sql += " and No="+cmpactivitygift.getNo();
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findSupplierPageBySql(
			Page<Map<String, Object>> page, cmpActivityGift cmpactivitygift) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.*,b.descr ,x1.note typedescr from tCmpActivitySuppl a " +
				" left join tSupplier b on b.code=a.supplCode " +
				" left join txtdm x1 on x1.cbm=a.supplType and x1.id='ACTSPLTYPE' " +
				" where 1=1 ";									  
		if (StringUtils.isNotBlank(cmpactivitygift.getNo())){
			sql += " and a.No= ? ";
			list.add(cmpactivitygift.getNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.lastUpdate Desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result docmpActivitySave(cmpActivity cmpactivity) {
		Assert.notNull(cmpactivity);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pcmpActivity(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, cmpactivity.getM_umState());
			call.setString(2, cmpactivity.getNo());
			call.setString(3, cmpactivity.getDescr());
			call.setTimestamp(4, new java.sql.Timestamp(cmpactivity.getBeginDate().getTime()));
			call.setTimestamp(5, new java.sql.Timestamp(cmpactivity.getEndDate().getTime()));
			call.setString(6, cmpactivity.getRemarks());
			call.setTimestamp(7, new java.sql.Timestamp(cmpactivity.getLastUpdate().getTime()));
			call.setString(8, cmpactivity.getLastUpdatedBy());
			call.setString(9, cmpactivity.getExpired());
			call.setString(10, cmpactivity.getActionLog());			
			call.setString(11, cmpactivity.getcmpActivityGiftXml());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;		
	}	

	
	@SuppressWarnings("unchecked") 
	public cmpActivity getByDescr(String descr) {
		String hql = "from cmpActivity a where a.expired='F' and a.descr=? ";
		List<cmpActivity> list = this.find(hql, new Object[]{descr});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked") 
	public cmpActivity getByDescrUpdate(String descr,String descr1) {
		String hql = "from cmpActivity a where a.expired='F' and a.descr=? and a.descr!=?";
		List<cmpActivity> list = this.find(hql, new Object[]{descr,descr1});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public void doSaveSuppl(String no, String code, String type, String czybm) {
		String sql = " insert  into tCmpActivitySuppl ( No ,SupplCode ,SupplType ,LastUpdate ,LastUpdatedBy ,Expired ,ActionLog )" +
				" values(?,?,?,getDate(),?,'F','Add') ";
		this.executeUpdateBySql(sql, new Object[]{no,code,type,czybm});
	}
	
	public void doDelSuppl(Integer pk) {
		String sql = " delete from tCmpActivitySuppl where pk=? ";
		this.executeUpdateBySql(sql, new Object[]{pk});
	}

	public boolean  existSuppl(String no, String code){
		String sql = " select * from tCmpActivitySuppl where no = ? and supplCode = ? and expired='F' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no,code});
		
		if(list != null && list.size()==1){
			return false;
		}
		return true;
	}
	
	
}

