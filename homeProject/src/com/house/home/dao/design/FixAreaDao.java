package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import com.house.home.entity.design.FixArea;


@SuppressWarnings("serial")
@Repository
public class FixAreaDao extends BaseDao {

	/**
	 * FixArea分页信息
	 * 
	 * @param page
	 * @param fixArea
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, FixArea fixArea) {
			List<Object> list = new ArrayList<Object>();
	    String sql = "select * from  (select top 1000 a.*, " 
	    +" b.descr custDescr,c.descr preplanareadescr,x1.note IsServiceDescr,b.address, " 
	    +" it1.descr itemtype1descr," 
	    +" case when isnull(ppa.fixAreaType,'')='' then '1' when ppa.fixAreaType='1' then '2' else '3' end custtypeitemfixarea "  
		+" from tFixArea a "
		+" left outer join tCustomer b on b.Code=a.CustCode   "
		+" left outer join tPrePlanArea c on c.pk=a.PrePlanAreaPK"
		+" left outer join tXtdm x1 on x1.ibm=a.IsService and id='YESNO' "
		+" left outer join titemType1 it1 on it1.code=a.itemtype1 "
		+" left outer join tPrePlanArea ppa on ppa.pk=a.PrePlanAreaPk "
		+" where 1=1 ";

    	if (fixArea.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(fixArea.getPk());
		}
    	if (StringUtils.isNotBlank(fixArea.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(fixArea.getCustCode());
		}
    	if (StringUtils.isNotBlank(fixArea.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+fixArea.getDescr()+"%");
		}
    	if (fixArea.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(fixArea.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(fixArea.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(fixArea.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(fixArea.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(fixArea.getActionLog());
		}
    	if (fixArea.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(fixArea.getDispSeq());
		}
    	if (StringUtils.isNotBlank(fixArea.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(fixArea.getItemType1().trim());
		}
    	if (fixArea.getIsService() != null) {
			sql += " and a.IsService=? ";
			list.add(fixArea.getIsService());
		}
    	if (StringUtils.isBlank(fixArea.getExpired())
				|| "F".equals(fixArea.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by  a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.DispSeq";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public boolean isExisted(FixArea fixArea) {
		List<Object> list = new ArrayList<Object>();
		String sql=" select 1 from tFixArea  a  where a.Expired='F' "; 
		if (StringUtils.isNotBlank(fixArea.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(fixArea.getCustCode());
		}
    	if (StringUtils.isNotBlank(fixArea.getDescr())) {
			sql += " and a.Descr =? ";
			list.add(fixArea.getDescr());
		}
    	if (StringUtils.isNotBlank(fixArea.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(fixArea.getItemType1().trim());
		}
    	if (fixArea.getIsService() != null) {
			sql += " and a.IsService=? ";
			list.add(fixArea.getIsService());
		}
    	if(fixArea.getPk()!=null){
    		sql+=" and a.PK <> ?";
    		list.add(fixArea.getPk());
    	}
    	List<Map<String,Object>> list1= this.findBySql(sql, list.toArray());
		if(list1!=null&&list1.size()>0){
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public Result addtFixArea(FixArea fixArea) {
		Assert.notNull(fixArea);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAddtFixArea(?,?,?,?,?,?,?,?)}");
			call.setString(1,fixArea.getCustCode());
			call.setString(2, fixArea.getDescr());
			call.setString(3, fixArea.getLastUpdatedBy());
			call.setString(4, fixArea.getItemType1());
			call.setInt(5, fixArea.getIsService());
			call.setInt(6, fixArea.getPrePlanAreaPK()==null?0:fixArea.getPrePlanAreaPK());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public Result insertFixArea(FixArea fixArea) {
		Assert.notNull(fixArea);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pInserttFixArea(?,?,?,?,?,?,?,?,?)}");
			call.setString(1,fixArea.getCustCode());
			call.setString(2, fixArea.getDescr());
			call.setInt(3, fixArea.getDispSeq());
			call.setString(4, fixArea.getLastUpdatedBy());
			call.setString(5, fixArea.getItemType1());
			call.setInt(6, fixArea.getIsService());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.setInt(9, fixArea.getPrePlanAreaPK()==null?0:fixArea.getPrePlanAreaPK());
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;	
	}

	@SuppressWarnings("deprecation")
	public Map<String, Object> deleteFixArea(FixArea fixArea) {
		Assert.notNull(fixArea);
		Map<String, Object> map =null;
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDeletetFixArea(?,?)}");
			if("JZ".equals(fixArea.getItemType1())) call.setString(1,"0");
			else call.setString(1,"1");
			call.setInt(2, fixArea.getPk());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=resultSetToList(rs);
			if(list!=null&&list.size()>0) map=list.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		
		return 	map;
	}
	public static List<Map<String, Object>> resultSetToList(ResultSet rs) throws java.sql.SQLException {   
        if (rs == null)   
            return Collections.emptyList();   
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> rowData = new HashMap<String, Object>(); 
        while (rs.next()) {   
         rowData = new HashMap<String, Object>(); 
         for (int i = 1; i <= columnCount; i++) {   
                 rowData.put(StringUtils.lowerCase(md.getColumnName(i)), rs.getObject(i));   
         }   
         list.add(rowData);   
        }   
        return list;   
	}

	@SuppressWarnings("deprecation")
	public void addRegular_FixArea(String custCode,String czy) {
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAdd_Regular_FixArea(?,?)}");
			call.setString(1,custCode);
			call.setString(2, czy);
			call.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
	}

	@SuppressWarnings("deprecation")
	public void addItem_FixArea(String custCode, String itemAreaDesc,
			String czy, String itemType1) {
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAdd_Item_FixArea(?,?,?,?)}");
			call.setString(1,custCode);
			call.setString(2,itemAreaDesc);
			call.setString(3, czy);
			call.setString(4, itemType1);
			call.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		
	}

	public int getFixAreaPk(String itemType1, String custCode, String descr, Integer isService) {
		String sql = "select pk from tFixArea t where itemType1=? and custCode=? and descr=? and isService=? and pk<>0";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemType1, custCode, descr, isService});
		if (list!=null && list.size()>0){
			return (Integer) list.get(0).get("pk");
		}
		return 0;
	}
}

