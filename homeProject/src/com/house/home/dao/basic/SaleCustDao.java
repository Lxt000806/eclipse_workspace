package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SaleCust;

@SuppressWarnings("serial")
@Repository
public class SaleCustDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, SaleCust saleCust) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select ";
		if(StringUtils.isNotBlank(saleCust.getReturnCount())){
			sql+=" top "+saleCust.getReturnCount();
		}
		sql += " a.* from tSaleCust a where 1=1 ";

		if (StringUtils.isNotBlank(saleCust.getCode())) {
			sql += " and a.Code like ?";
			list.add("%" + saleCust.getCode() + "%");
		}
		if(StringUtils.isNotBlank(saleCust.getAddress())){
			sql+=" and a.Address like ?";
			list.add("%" + saleCust.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(saleCust.getDesc1())) {
			sql += " and a.Desc1 like ?";
			list.add("%" + saleCust.getDesc1()+"%");
		}
		if(StringUtils.isNotBlank(saleCust.getContact())){
			sql+= " and a.Contact like ?";
			list.add("%"+ saleCust.getContact()+"%");
		}
		if(StringUtils.isNotBlank(saleCust.getMobile1())){
			sql+=" and a.Mobile1 like ?";
			list.add("%" + saleCust.getMobile1() + "%");
		}
		if (saleCust.getDateFrom() != null) {
			sql += " and a.LastUpdate>= ?";
			list.add(saleCust.getDateFrom());
		}
		if (saleCust.getDateTo() != null) {
			sql += " and a.LastUpdate<=?";
			list.add(saleCust.getDateTo());
		}
		if (StringUtils.isBlank(saleCust.getExpired())
				|| "F".equals(saleCust.getExpired())) {
			sql += " and a.Expired='F'";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by  a." + page.getPageOrderBy() + " "+ page.getPageOrder();
		} else {
			sql += " )a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	@SuppressWarnings("unchecked")
	public SaleCust getByDesc1(String desc1){
		String hql="from SaleCust a where a.desc1=? ";
		List<SaleCust> list=this.find(hql, new Object[]{desc1});
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<SaleCust> findByNoExpired(){
		String hql="from SaleCust a where a.expired<>'T' ";
		return this.find(hql);
	}
	
	public List<Map<String, Object>> getDBcol(String dbName){
		String sql = " select case when b.name ='PK' then 'pk' else b.name end ColName," +
				" case when c.name = 'int' then 'Integer' when c.name = 'money' then 'Double' when c.name = 'datetime' or c.name = 'date' then 'Date' " +
				" when c.name = 'nvarchar' or c.name = 'nchar' then 'String' else 'String ' end " +
				" ColType " +
				" from sysobjects a,syscolumns b,systypes c " +
				" where a.id=b.id and a.name='"+dbName+"' and a.xtype='U' and b.xtype=c.xtype and c.uid='4' and c.allownulls='1' " +
				" order  by  b.colid ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
}
	
	
