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
import com.house.home.entity.basic.CzybmAuthority;
import com.house.home.entity.finance.PrjCheck;

@SuppressWarnings("serial")
@Repository
public class CzybmAuthorityDao extends BaseDao {

	/**
	 * CzybmAuthority分页信息
	 * 
	 * @param page
	 * @param czybmAuthority
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzybmAuthority czybmAuthority) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from TSYS_CZYBM_AUTHORITY a where 1=1 ";

    	if (czybmAuthority.getId() != null) {
			sql += " and a.ID=? ";
			list.add(czybmAuthority.getId());
		}
    	if (StringUtils.isNotBlank(czybmAuthority.getCzybh())) {
			sql += " and a.CZYBH=? ";
			list.add(czybmAuthority.getCzybh());
		}
    	if (czybmAuthority.getAuthorityId() != null) {
			sql += " and a.AUTHORITY_ID=? ";
			list.add(czybmAuthority.getAuthorityId());
		}
    	if (czybmAuthority.getGenTime() != null) {
			sql += " and a.GEN_TIME=? ";
			list.add(czybmAuthority.getGenTime());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public void addByCzybhAndAuthIds(String czybh, List<Long> addList) {
		if (addList!=null && addList.size()>0){
			StringBuffer str = new StringBuffer();
			for (Long s : addList){
				str.append(String.valueOf(s)).append(",");
			}
			String ss = str.substring(0, str.length()-1).toString();
			String sql = "insert into TSYS_CZYBM_AUTHORITY(CZYBH,AUTHORITY_ID,GEN_TIME) "
				+"select ?,a.item,getdate() from (select * from dbo.fStrToTable(?,',')) a "
				+"where not exists(select 1 from TSYS_CZYBM_AUTHORITY where CZYBH=? and AUTHORITY_ID=a.item)";
			this.executeUpdateBySql(sql, new Object[]{czybh,ss,czybh});
			
			//同步到 tCZYQX
			sql = " insert into  tCZYQX ( CZYBH, MKDM, QYRQ, TYRQ, QYSJ, TYSJ, QYXQ ) "
					+ " select distinct a.CZYBH,g.MKDM ,isnull(c1.qz,'20020101'),isnull(c2.qz,'21000101')," 
					+ " isnull(c3.qz,'000001'),isnull(c4.qz,'240000'),isnull(c5.qz,'1111111') " 
					+ " from TSYS_CZYBM_AUTHORITY a "
					+ " inner join TSYS_AUTHORITY b  on b.AUTHORITY_ID=a.AUTHORITY_ID "
					+ " inner join TSYS_MENU c on b.MENU_ID=c.MENU_ID "
					+ " inner join dbo.tMODULE g on c.MENU_NAME=g.MKMC "
					+ " left join tXTCS c1 on c1.ID='QX_QYRQ' "
					+ " left join tXTCS c2 on c2.ID='QX_TYRQ' " 
					+ " left join tXTCS c3 on c3.ID='QX_QYSJ' "
					+ " left join tXTCS c4 on c4.ID='QX_TYSJ' "
					+ " left join tXTCS c5 on c5.ID='QX_QYXQ' "
					+ " where  CZYBH=? and b.AUTHORITY_ID in (select * from dbo.fStrToTable(?,',')) "
					+ " and not exists(select 1 from tCZYQX where MKDM=g.MKDM  and CZYBH=?) " ;
			this.executeUpdateBySql(sql, new Object[]{czybh,ss,czybh});
			
			//同步到 tCZYQX
			sql =" insert into tCZYGNQX ( CZYBH, MKDM, GNMC ) "
					+ " select a.CZYBH,g.MKDM,substring(b.AUTH_NAME,charindex('>',AUTH_NAME)+1,100) GNMC "
					+ " from TSYS_CZYBM_AUTHORITY a "
					+ " inner join TSYS_AUTHORITY b  on b.AUTHORITY_ID=a.AUTHORITY_ID "
					+ " inner join TSYS_MENU c on b.MENU_ID=c.MENU_ID "
					+ " inner join dbo.tMODULE g on c.MENU_NAME=g.MKMC "
					+ " where  CZYBH=? and b.AUTHORITY_ID in (select * from dbo.fStrToTable(?,','))  " 
					+ " and not exists( "
					+ " select 1  from  tCZYGNQX cg  where cg.MKDM=g.MKDM and cg.GNMC=substring(b.AUTH_NAME,charindex('>',AUTH_NAME)+1,100) "
					+ " and cg.CZYBH=?) " ;
			this.executeUpdateBySql(sql, new Object[]{czybh,ss,czybh});
			
			//同步到 平台模块
			sql =" insert into tCZYQX ( CZYBH, MKDM, QYRQ, TYRQ, QYSJ, TYSJ, QYXQ ) "
					+ " select top 1 CZYBH, '9901', QYRQ, TYRQ, QYSJ, TYSJ, QYXQ  from  tCZYQX "
					+ " where  CZYBH=? and not exists(select * from tCZYQX where MKDM='9901' and CZYBH=?) ";		
			this.executeUpdateBySql(sql, new Object[]{czybh,czybh});   
		}
	}

	public void delByCzybhAndAuthIds(String czybh, List<Long> delList) {
		if (delList!=null && delList.size()>0){
			StringBuffer str = new StringBuffer();
			for (Long s : delList){
				str.append(String.valueOf(s)).append(",");
			}
			String ss = str.substring(0, str.length()-1).toString();
			String sql = "delete from  tCZYQX   where  CZYBH=? and exists( "
					+ " select 1 "
					+ " from TSYS_CZYBM_AUTHORITY a "
					+ " inner join TSYS_AUTHORITY b  on b.AUTHORITY_ID=a.AUTHORITY_ID "
					+ " inner join TSYS_MENU c on b.MENU_ID=c.MENU_ID "
					+ " inner join dbo.tMODULE g on c.MENU_NAME=g.MKMC "
					+ " where b.AUTHORITY_ID in (select * from dbo.fStrToTable(?,',')) and MKDM=g.MKDM   " 
					+ " and a.CZYBH=? )  ";
				this.executeUpdateBySql(sql, new Object[]{czybh,ss,czybh}); 
				
				sql=" delete from tCZYGNQX  where CZYBH=? and exists(  "
				   + " select 1  "
				   + " from TSYS_CZYBM_AUTHORITY a  "
				   + " inner join TSYS_AUTHORITY b  on b.AUTHORITY_ID=a.AUTHORITY_ID "
				   + " inner join TSYS_MENU c on b.MENU_ID=c.MENU_ID "
				   + " inner join dbo.tMODULE g on c.MENU_NAME=g.MKMC "
				   + " where b.AUTHORITY_ID in (select * from dbo.fStrToTable(?,',')) and MKDM=g.MKDM  " 
				   + " and GNMC=substring(b.AUTH_NAME,charindex('>',AUTH_NAME)+1,100) and a.CZYBH=? ) " ;
				this.executeUpdateBySql(sql, new Object[]{czybh,ss,czybh}); 
			
			
				sql = "delete from TSYS_CZYBM_AUTHORITY where czybh=? and AUTHORITY_ID in (select * from dbo.fStrToTable(?,','))";
				this.executeUpdateBySql(sql, new Object[]{czybh,ss});
		}
			
	}
	
	//设置操作员权限
	@SuppressWarnings("deprecation")
	public Result doSetCzybmAuths(String czybh, List<Long> delList, List<Long> addList) {
		Assert.notNull(czybh);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			String sDel="",sAdd="";
			if (delList!=null && delList.size()>0){
				StringBuffer strDel = new StringBuffer();
				for (Long s : delList){
					strDel.append(String.valueOf(s)).append(",");
				}
				sDel=strDel.substring(0, strDel.length()-1).toString();
			}
			if (addList!=null && addList.size()>0){
				StringBuffer strAdd = new StringBuffer();
				for (Long s : addList){
					strAdd.append(String.valueOf(s)).append(",");
				}
				sAdd=strAdd.substring(0, strAdd.length()-1).toString();
			}
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSetCzybmAuths(?,?,?,?,?)}");
			call.setString(1, czybh);
			call.setString(2, sDel);	
			call.setString(3, sAdd);
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

}

