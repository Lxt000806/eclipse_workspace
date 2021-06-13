package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Table;
import com.house.home.entity.project.PrjJob;

@SuppressWarnings("serial")
@Repository
public class TableDao extends BaseDao {

	/**
	 * 获取所有字段
	 * 
	 * @param 
	 * @return
	 */
	public List<Map<String,Object>> getColumns(Table table) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select b.PK ColumnPK,b.Descr, " 
					+"case when isnull(d.IsShow,'1') = '1' then 'checked' else '' end IsCheck  "
					+"from tTable a " 
					+"left join tColumn b on a.PK = b.TablePK  "
					+"left join tTableCzyMapper c on a.PK = c.TablePK and c.Czybh = ? "
					+"left join tTableCzyMapperDetail d on c.PK = d.TableCzyMapperPK and b.PK = d.ColumnPK  "
					+"where a.ModuleUrl = ? and (isnull(a.TableCode,'') = '' or a.TableCode = ?) and b.IsShow = '1' ";
		list.add(table.getCzybh());
		list.add(table.getModuleUrl());
		list.add(table.getTableCode());
		
    	if (table.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(table.getPk());
		}
    	sql+=" order by d.IsShow desc,d.DispSeq ";
		return this.findBySql(sql, list.toArray());
	}
	
	/**
	 * 获取ColModel
	 * 
	 * @param table
	 * @return
	 */
	public List<Map<String, Object>> getColModel(Table table) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Model,a.IsShow,a.DispSeq,ColumnPK,Descr "
					+"from ( "
					+"	select c.Model,d.DispSeq,d.IsShow,c.PK ColumnPK,c.Descr from tTable a  "
					+"	left join tTableCzyMapper b on a.PK = b.TablePK  "
					+"	left join tColumn c on a.PK = c.TablePK "
					+"	inner join tTableCzyMapperDetail d on c.PK = d.ColumnPK and b.PK = d.TableCzyMapperPK "
					+"	where a.ModuleUrl = ? and b.Czybh = ?  ";
		if ("1".equals(table.getIsMain())) {//如果是主界面查询调用，必须为启用才查出来
			sql += "and b.IsEnable = '1' ";
		}
		sql += "  and (isnull(a.TableCode,'') = '' or a.TableCode = ?)"
					+"	union "
					+"	select b.Model,b.DispSeq,b.IsShow,b.PK ColumnPK ,b.Descr from tTable a  "
					+"	left join tColumn b on a.PK = b.TablePK "
					+"	left join tTableCzyMapper c on a.PK = c.TablePK "
					+"	where a.ModuleUrl = ? and c.Czybh = ?  and (isnull(a.TableCode,'') = '' or a.TableCode = ?) "
					+"	and not exists (select 1 from tTableCzyMapperDetail in_a where in_a.ColumnPK = b.PK) " 
				+"union "
				+"  select b.Model ,b.DispSeq ,b.IsShow, b.PK ColumnPK ,b.Descr "
				+"  from tTable a "
				+"  left join tColumn b on a.PK = b.TablePK "
				+"  where a.ModuleUrl = ? and (isnull(a.TableCode,'') = '' or a.TableCode = ?) "
				+"  and not exists (select 1 from tTableCzyMapper in_a where in_a.TablePK = a.PK and in_a.Czybh = ? ";
		if ("1".equals(table.getIsMain())) {//如果是主界面查询调用，必须为启用才查出来
			sql += "and IsEnable = '1' ";
		}
		sql+=") ";
		list.add(table.getModuleUrl());
		list.add(table.getCzybh());
		list.add(table.getTableCode());
		list.add(table.getModuleUrl());
		list.add(table.getCzybh());
		list.add(table.getTableCode());
		list.add(table.getModuleUrl());
		list.add(table.getTableCode());
		list.add(table.getCzybh());
		sql+=")a where 1=1 ";
		if (!"1".equals(table.getIsMain())) {
			sql+="and a.IsShow = '1' ";
		}
		sql+="order by a.DispSeq ";
		System.out.println(sql);
		System.out.println(list);
		return this.findBySql(sql, list.toArray());
	}
	
	/**
	 * 获取表格操作员映射表
	 * 
	 * @param table
	 * @return
	 */
	public Map<String, Object> getTableCzyMapper(Table table) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK TablePK,b.PK,isnull(b.FrozenNum,a.FrozenNum)FrozenNum,"
				+"isnull(b.IsEnable,'0') IsEnable "
				+"from tTable a " 
				+"left join tTableCzyMapper b on a.PK = b.TablePk and b.Czybh = ? " 
				+"where a.ModuleUrl = ? and (isnull(a.TableCode,'') = '' or a.TableCode = ?) ";
		list.add(table.getCzybh());
		list.add(table.getModuleUrl());
		list.add(table.getTableCode());
		List<Map<String, Object>> result = this.findBySql(sql, list.toArray());
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		
		return null;
	}
	
	/**
	 * 保存
	 * @param table
	 * @return
	 */
	public Result doSave(Table table) {
		Assert.notNull(table);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pTableCzyMapper(?,?,?,?,?,?,?,?)}");
			call.setString(1, table.getCzybh());
			call.setInt(2, table.getPk());
			if(table.getTableCzyMapperPk()==null){
				call.setNull(3, Types.INTEGER);
			}else{
				call.setInt(3, table.getTableCzyMapperPk());
			}
			call.setString(4, table.getIsEnable());
			call.setInt(5, table.getFrozenNum());
			call.setString(6, table.getDetailJson());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
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
}

