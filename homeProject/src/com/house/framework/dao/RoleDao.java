package com.house.framework.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.entity.Role;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.design.ResrCust;

@SuppressWarnings("serial")
@Repository
public class RoleDao extends BaseDao{

	private static final Logger logger = LoggerFactory.getLogger(RoleDao.class);
	
	/**
	 * 角色分页查询方法
	 * @param page
	 * @param role
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Page findPage(Page page, Role role){
		UserContext uc = UserContextHolder.getUserContext() ;
		
		List<Object> list = new ArrayList<Object>(4);
		
		String sql ="select * from (select r.*, " +
					"	(select u.zwxm from tCzybm u " +
					"		where u.czybh = r.CREATER_ID" +
					"	)as CREATER,a.item_label sysCodeDescr " +
					" from TSYS_ROLE r " +
					" left join TSYS_DICT_ITEM a on r.sys_Code=a.item_code " +
					"and a.dict_id in (select dict_id from TSYS_DICT where dict_code='ptdm')  where 1=1 ";
		
//		//超级管理员，无需过滤
//		if(uc.isSuperAdmin()){
//			
//		//一级管理员，不能查看超级管理员角色，其他都可以
//		}else if(uc.isOneAdmin()){
//			sql += " and r.ROLE_CODE != ? ";
//			list.add(CommonConstant.SUPER_ADMIN);
//		//二级管理员，只能查看自己城市编码的角色
//		}else if(uc.isTwoAdmin()){
//			sql += " and r.CITY_CODE = ?  and r.ROLE_CODE != ? and r.ROLE_CODE != ? ";
//			list.add(uc.getCityCode());
//			list.add(CommonConstant.SUPER_ADMIN);
//			list.add(CommonConstant.ONE_ADMIN);
//		//普通用户只能查看自己创建的角色和分配给自己的角色
//		}else{
//			sql += " and r.CREATER_ID = ? or r.role_id in(select ru.ROLE_ID from TSYS_ROLE_USER ru where ru.czybh = ?)";
//			list.add(uc.getCzybh());
//			list.add(uc.getCzybh());
//		}
		
		if(StringUtils.isNotBlank(role.getRoleName())){
			sql += " and r.ROLE_NAME like ?";
			list.add("%"+role.getRoleName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(role.getSysCode())){
			sql += " and r.SYS_CODE = ?";
			list.add(role.getSysCode());
		}
		
		if(StringUtils.isNotBlank(role.getRoleCode())){
			sql += " and upper(r.ROLE_CODE) like ?";
			list.add("%"+role.getRoleCode().trim().toUpperCase()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.ORDER_NO, a.GEN_TIME";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 获取分配给用户的所有角色
	 * @param userId
	 * @return
	 */
	public List<Role> getByUserId(Long userId){
		String sql ="select t2.* " +
					"	from tsys_role_user t1,tsys_role t2 " +
					" where t1.user_id = ? " +
					"	and t2.role_id = t1.role_id order by t2.role_id";
		
		return this.mapper(this.findBySql(sql, new Object[]{userId}));
	}
	
	/**
	 * 根据角色编码获取角色
	 * @param roleCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Role getByRoleCode(String roleCode){
		String hql = "from Role r where r.roleCode = ?";
		List<Role> list = this.find(hql, new Object[]{roleCode});
		if(list == null || list.size()<1)
			return null;
		return list.get(0);
	}
	
	
	public List<Role> mapper(List<Map<String, Object>> list){
		if(list == null || list.size() < 1){
			return null;
		}
		List<Role> roleList = new ArrayList<Role>(list.size());
		Role role = null;
		for(Map<String,Object> map : list){
			role = this.mapper(map);
			if(role != null){
				roleList.add(role);
			}
		}
		return roleList;
	}
	
	public Role mapper(Map<String,Object> map){
		Role role = null;
		try {
			if(map == null)
				return null;
			role = new Role();
			Long roleId = null;
			Object roleID = map.get("ROLE_ID");
			if(roleID != null){
				Integer bd = (Integer)roleID;
				roleId = bd.longValue();
			}
			role.setRoleId(roleId);
			role.setCityCode((String)map.get("CITY_CODE"));
			role.setCreaterId((String) map.get("CREATER_ID"));
			role.setGenTime(DateUtil.timeStampToDate((Timestamp)map.get("GEN_TIME")));
			
			Long orderId = 0L;
			Object orderID = map.get("ORDER_NO");
			if(orderID != null){
				Integer bd = (Integer)orderID;
				orderId = bd.longValue();
			}
			role.setOrderNo(orderId);
			
			role.setRemark((String)map.get("REMARK"));
			role.setRoleCode((String)map.get("ROLE_CODE"));
			role.setRoleName((String)map.get("ROLE_NAME"));
			role.setGenTime(DateUtil.timeStampToDate((Timestamp)map.get("UPDATE_TIME")));
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("将Map转成Role对象出错", e);
			throw new RuntimeException(e);
		}
		
		return role;
	}
	
	/**
	 * 角色模块权限
	 * @param czybh 操作员编号
	 * @param ptbh 平台编号
	 * @return
	 */
	public List<Map<String, Object>> getQX_RoleQX(Long roleId, int ptbh){
	        String sql = " select  a.XTDM, a.XTMC, b.ZXTDM, b.XTMC as ZXTMC, c.MKDM, c.MKMC, f.GNMC "
	        		  + " from tSYS a "
	        		  + " join tMODULE c on a.XTDM = c.XTDM "
	        		  + " join tMenu g on c.MKDM = g.MKDM "
	        		  + " join tSUBSYS b on b.ZXTDM = c.ZXTDM "
	        		  + " join tRoleQX d on d.MKDM = c.MKDM "
	        		  + " left join tRoleGNQX f on d.MKDM = f.MKDM and d.ROLE_ID = f.ROLE_ID "
	        		  + " where d.ROLE_ID =? and g.PTDM =? "
	        		  + " order by  a.XTDM, b.ZXTDM, c.MKDM ";
	        return this.findBySql(sql, new Object[]{roleId,ptbh});
	 }
	
	public Result doAppRight(Role role){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRoleAppRight(?,?,?,?)}");
			call.setLong(1, role.getRoleId());
			call.setString(2, role.getDetailJson());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 是否分配给操作员
	 * @param czybh 操作员编号
	 * @return
	 */
	public List<Map<String, Object>> isUsedByCzy(String roleIds){
	        String sql = " select 1 from TSYS_ROLE_USER where ROLE_ID in "
	        			+"('"+roleIds.replaceAll(",", "','")+"')";
	        return this.findBySql(sql, new Object[]{});
	 }
	
	/**
	 * 是否已分配权限
	 * @param czybh 操作员编号
	 * @return
	 */
	public List<Map<String, Object>> isUsedByAuth(String roleIds){
	        String sql = " select 1 from TSYS_ROLE_AUTHORITY where ROLE_ID in  "
	        			+"('"+roleIds.replaceAll(",", "','")+"')";
	        return this.findBySql(sql, new Object[]{});
	}
	
	public List<Map<String,Object>> getRoleList(String roleId){
		String sql = " select a.role_id,'['+isnull(cast(a.ROLE_ID as nvarchar(10)),'')+']'+' ['+isnull(a.ROLE_NAME,'')+']' name " +
				" from TSYS_ROLE a " +
				" where role_id<> ? and exists(select 1 from tSYS_ROLE in_a where in_a.Role_id = ? and a.SYS_CODE = in_a.SYS_CODE)";
		return this.findBySql(sql, new Object[]{roleId,roleId});
	}
	
	public Result doCopyRight(Role role){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRoleCopyRight_forProc(?,?,?,?,?)}");
			call.setLong(1, role.getRoleId());
			call.setString(2, role.getDetailJson());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.setString(5, role.getOperatorType());
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 根据角色Id获取菜单
	 * @param roleId 角色编号
	 * @return
	 */
	public List<Map<String, Object>> getMenuByRoId(Long roleId){
			String sql="";    
		if(roleId==1){
	        sql = "select MENU_ID id,PARENT_ID pId,MENU_NAME name from TSYS_MENU order by ORDER_NO";
	        return this.findBySql(sql, new Object[]{});
		}else{
        	sql = "select b.MENU_ID id,b.PARENT_ID pId,b.MENU_NAME name " 
    			+ "from ( "
    			+ "  select distinct c.MENU_ID "
    			+ "  from TSYS_ROLE_AUTHORITY a " 
    			+ "  inner join TSYS_AUTHORITY b on a.AUTHORITY_ID=b.AUTHORITY_ID "
    			+ "  left join TSYS_MENU c on b.MENU_ID=c.MENU_ID "
    			+ "  left join TSYS_MENU d on c.PARENT_ID=d.MENU_ID "
    			+ "  left join TSYS_MENU e on d.PARENT_ID=e.MENU_ID "
    			+ "  where a.ROLE_ID=? "
    			+ "  union "
    			+ "  select distinct d.MENU_ID "
    			+ "  from TSYS_ROLE_AUTHORITY a " 
    			+ "  inner join TSYS_AUTHORITY b on a.AUTHORITY_ID=b.AUTHORITY_ID "
    			+ "  left join TSYS_MENU c on b.MENU_ID=c.MENU_ID "
    			+ "  left join TSYS_MENU d on c.PARENT_ID=d.MENU_ID "
    			+ "  left join TSYS_MENU e on d.PARENT_ID=e.MENU_ID "
    			+ "  where a.ROLE_ID=? and d.MENU_ID is not null "
    			+ "  group by d.MENU_ID "
    			+ "  union "
    			+ "  select distinct e.MENU_ID "
    			+ "  from TSYS_ROLE_AUTHORITY a  "
    			+ "  inner join TSYS_AUTHORITY b on a.AUTHORITY_ID=b.AUTHORITY_ID "
    			+ "  left join TSYS_MENU c on b.MENU_ID=c.MENU_ID "
    			+ "  left join TSYS_MENU d on c.PARENT_ID=d.MENU_ID "
    			+ "  left join TSYS_MENU e on d.PARENT_ID=e.MENU_ID "
    			+ "  where a.ROLE_ID=? and e.MENU_ID is not null "
    			+ "  group by e.MENU_ID "
    			+ ")a inner join TSYS_MENU b on a.MENU_ID=b.MENU_ID "
    			+ "order by b.ORDER_NO ";
	        return this.findBySql(sql, new Object[]{roleId,roleId,roleId});
	     }
	 }
	
	/**
	 * 快捷菜单查询
	 * @param page
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Page goFastMenuJqGrid(Page page, Long roleId){
		UserContext uc = UserContextHolder.getUserContext() ;
		
		List<Object> list = new ArrayList<Object>(4);
		
		String sql ="select a.ROLE_ID,a.MENU_ID,a.DispSeq,a.LastUpdate,a.LastUpdatedBy,b.MENU_NAME " +
				"from tSYS_ROLE_FAST_MENU a " +
				"left join TSYS_MENU b on a.MENU_ID=b.MENU_ID " +
				"where ROLE_ID=? "+
				"order by a.DispSeq ";
		return this.findPageBySql(page, sql, new Object[]{roleId});
	}
	
	/**
	 * 保存快捷菜单
	 * @param role
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveFastMenu(Role role) {
		Assert.notNull(role);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pFastMenu(?,?,?,?)}");
			call.setString(1, role.getRoleId()+"");
			call.setString(2, role.getDetailJson());	
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}

	public List<Map<String, Object>> getFastMenus(String czybh){
		String sql = " select c.MENU_NAME,c.MENU_ID,c.MENU_URL "
				   + " from ( "
				   + " 		select top 1 in_a.* "
				   + "		from TSYS_ROLE_USER in_a "
				   + "		inner join TSYS_ROLE in_b on in_a.ROLE_ID = in_b.ROLE_ID "
				   + "		where in_a.CZYBH = ? and exists( "
				   + "			select 1 from tSYS_ROLE_FAST_MENU in_in_a where in_in_a.ROLE_ID = in_a.ROLE_ID "
				   + " 		)"
				   + "		order by in_b.Priority desc,in_b.ORDER_NO desc "
				   + " ) a "
				   + " inner join tSYS_ROLE_FAST_MENU b on a.ROLE_ID = b.ROLE_ID "
				   + " inner join TSYS_MENU c on c.MENU_ID = b.MENU_ID "
				   + " order by b.DispSeq  "; 
		List<Map<String, Object>> result = this.findBySql(sql, new Object[]{czybh});
		if(result != null && result.size() > 0){
			return result;
		}
		return new ArrayList<Map<String,Object>>();
	}
}
