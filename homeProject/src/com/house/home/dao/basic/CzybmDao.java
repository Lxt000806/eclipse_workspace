package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.basic.Czybm;

@SuppressWarnings("serial")
@Repository
public class CzybmDao extends BaseDao {

	/**
	 * Czybm分页信息
	 * 
	 * @param page
	 * @param czybm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Czybm czybm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (SELECT a.CZYBH,BMBH,YWXM,ZWXM,MM,KHRQ,QMCode,XHRQ,ZFBZ,"
			    + "DHHM,SJ,JSLX,EMNum,CustRight,b.NOTE CustRightDescr, "
			    + "CostRight,c.NOTE CostRightDescr,ItemRight,dbo.fGetCZYItemRightDescr(a.czybh) ItemRightDescr,CustType,x1.NOTE CustTypeDescr,"
				+ "a.SaleType,x33.NOTE SaleTypeDescr,x4.Note IsLeadDescr,"
			    + "d1.Desc2 Department1Descr,d2.Desc2 Department2Descr,"
			    + "d3.Desc2 Department3Descr,p.Desc2 PositionDescr,x2.Note StatusDescr,e.LeaveDate,x3.item_label czylbdescr,a.prjRole,pj.Descr prjRoleDescr "
			    + " ,x5.Note IsOutUserDescr,left(sr.RoleName,len(sr.RoleName)-1) RoleName  "
			    + " FROM tCZYBM a "
			    + " left outer join tXTDM b on b.CBM=a.CustRight and b.ID='CUSTRIGHT' "
			    + " left outer join tXTDM c on c.CBM=a.CostRight and c.ID='COSTRIGHT' "
			    + " left outer join tXTDM x1 on x1.CBM=a.CustType and x1.ID='CUSTTYPE' "
			    + " left outer join tEmployee e on e.Number=a.EMNum"
			    + " left outer join tXTDM x4 on x4.CBM=e.IsLead and x4.ID='YESNO' "
			    + " left outer join tXTDM x2 on x2.CBM=e.Status and x2.ID='EMPSTS' "
			    + " left outer join tXTDM x33 on x33.CBM=a.SaleType and x33.ID='SALETYPE' "
			    + " left outer join tXTDM x5 on x5.CBM=a.IsOutUser and x5.ID='YESNO' "
			    + " left outer join tDepartment1 d1 on d1.Code=e.Department1"
			    + " left outer join tDepartment2 d2 on d2.Code=e.Department2"
			    + " left outer join tDepartment3 d3 on d3.Code=e.Department3"
			    + " left outer join tPosition p on p.Code=e.Position"
			    + " left join TSYS_DICT_ITEM x3 on a.czylb=x3.item_code "
				+ " and x3.dict_id in (select dict_id from TSYS_DICT where dict_code='ptdm')"
			    + " left join tPrjRole pj on a.prjRole=pj.Code "
			    + " left join ( " //支持显示多个角色，逗号隔开 by cjg on 20201008
				+ "		select in_a.CZYBH,"
				+ "		(select in_b.ROLE_NAME+',' from TSYS_ROLE_USER in_c left join dbo.TSYS_ROLE in_b on in_c.ROLE_ID = in_b.ROLE_ID where in_c.CZYBH=in_a.CZYBH for xml path(''))RoleName "
				+ "		from  TSYS_ROLE_USER in_a  "
				+ "		group by in_a.CZYBH "
				+ ")sr on sr.CZYBH=a.CZYBH "
			    + " where 1=1 ";
		// 增加状态查询(只对员工查询) add by zb on 20200328
		if (StringUtils.isNotBlank(czybm.getEmpStatus())) {
			sql += " and (a.CZYLB<>'1' or e.Status in ('"+czybm.getEmpStatus().replace(",", "','")+"')) ";
		} 
    	if (StringUtils.isNotBlank(czybm.getCzybh())) {
			sql += " and (a.CZYBH = ? or a.zwxm like ?) ";
			list.add(czybm.getCzybh());
			list.add("%"+czybm.getCzybh()+"%");
		}
    	if(StringUtils.isNotBlank(czybm.getPosition())){
    		sql+=" and p.code= ? ";
    		list.add(czybm.getPosition());
    	}
    	if (StringUtils.isNotBlank(czybm.getZwxm())) {
			sql += " and a.zwxm like ? ";
			list.add("%"+czybm.getZwxm()+"%");
		}
    	if (StringUtils.isNotBlank(czybm.getCzylb())) {
			sql += " and a.CZYLB=? ";
			list.add(czybm.getCzylb());
		}
    	if (StringUtils.isNotBlank(czybm.getEmnum())) {
			sql += " and a.EMNum=? ";
			list.add(czybm.getEmnum());
		}
    	if (StringUtils.isNotBlank(czybm.getDepartment1())) {
			sql += " and e.Department1=? ";
			list.add(czybm.getDepartment1());
		}
    	if (StringUtils.isNotBlank(czybm.getDepartment2())) {
			sql += " and e.Department2=? ";
			list.add(czybm.getDepartment2());
		}
    	if (StringUtils.isNotBlank(czybm.getDepartment3())) {
			sql += " and e.Department3=? ";
			list.add(czybm.getDepartment3());
		}
    	if (StringUtils.isNotBlank(czybm.getCustRight())) {
			sql += " and a.CustRight=? ";
			list.add(czybm.getCustRight());
		}
    	if (StringUtils.isNotBlank(czybm.getCostRight())) {
			sql += " and a.CostRight=? ";
			list.add(czybm.getCostRight());
		}
    	if (StringUtils.isNotBlank(czybm.getIsLead())) {
			sql += " and e.IsLead=? ";
			list.add(czybm.getIsLead());
		}
    	if (czybm.getZfbz() == null || czybm.getZfbz()==false) {
			sql += " and a.ZFBZ=0 ";
		}
    	if (StringUtils.isNotBlank(czybm.getAuth())) {
    		sql += " and (exists(select 1 from TSYS_CZYBM_AUTHORITY in_a "
    		     + "  inner join TSYS_AUTHORITY in_b on in_a.AUTHORITY_ID=in_b.AUTHORITY_ID "
    			 + "  where  in_a.CZYBH=a.CZYBH and in_b.AUTHORITY_ID in ("+czybm.getAuth()+") ) " 
    			 + "  or exists (select 1 from TSYS_ROLE_USER in_a  "
    			 + "  inner join TSYS_ROLE_AUTHORITY in_b on in_a.ROLE_ID=in_b.ROLE_ID "
    			 + "  inner join TSYS_AUTHORITY in_c on in_c.AUTHORITY_ID=in_b.AUTHORITY_ID "
     			 + "  where  in_a.CZYBH=a.CZYBH and in_c.AUTHORITY_ID  in ("+czybm.getAuth()+") ) ) "  ;
		}
    	
    	if(StringUtils.isNotBlank(czybm.getUserRole())){
			sql += " and  exists(select 1 from TSYS_ROLE_USER where CZYBH=a.CZYBH and  ROLE_ID in ('"+czybm.getUserRole().replace(",", "','")+"')) ";	
    	}
  
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.ZFBZ,a.CZYBH";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**修改操作员
	 * @param czybm
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result updateForProc(Czybm czybm, String xml, String xmlCk) {
		Assert.notNull(czybm);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pUserSave_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, czybm.getM_umState());
			call.setString(2, czybm.getCzybh());
			call.setString(3, czybm.getBmbh());
			call.setString(4, czybm.getYwxm());
			call.setString(5, czybm.getZwxm());
			call.setString(6, czybm.getQmcode());
			call.setString(7, czybm.getJslx());
			call.setString(8, czybm.getEmnum());
			call.setString(9, czybm.getMm());
			call.setString(10, czybm.getDhhm());
			call.setString(11, czybm.getSj());
			call.setInt(12, czybm.getZfbz()==true?1:0);
			call.setString(13, czybm.getCustRight());
			call.setString(14, czybm.getCostRight());
			call.setString(15, czybm.getItemRight());
			call.setString(16, czybm.getCustType());
			call.setString(17, czybm.getSaleType());
			call.setString(18, czybm.getLastUpdatedBy());
			call.registerOutParameter(19, Types.INTEGER);
			call.registerOutParameter(20, Types.NVARCHAR);
			call.setString(21, xml);
			call.setString(22, xmlCk);
			call.setString(23, czybm.getCzylb());
			call.setString(24, czybm.getProjectCostRight());
			call.setString(25, czybm.getIsOutUser());
			call.setString(26, czybm.getPrjRole());
			call.setString(27, czybm.getSupplyRecvModel());
			call.execute();
			result.setCode(String.valueOf(call.getInt(19)));
			result.setInfo(call.getString(20));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Czybm getByCzybhAndMm(String czybh, String mm) {
		if (StringUtils.isBlank(czybh) || StringUtils.isBlank(mm)){
			return null;
		}
		
		String str = DesUtils.encode(mm);

		String sql = "select a.*," +
				" case when datediff(day, a.ModifyMMDate, getdate())>cast(QZ as int) then '1' else '0' end PasswordExpired from tCZYBM a " +
				"left join tEmployee b on a.EMNum = b.Number " +
				"left join tXTCS xc on xc.id = 'MODIFYMMTIME' " +
				"where (a.CZYBH = ? or b.Phone = ?) and a.MM = ? ";
		
		List<Czybm> result = BeanConvertUtil.mapToBeanList(
		        findBySql(sql, czybh, czybh, str), Czybm.class);
		
		// 查询出来的列表必须只有一条，如果有多条说明多个人的手机号与密码相同
		// 如果有多个人的手机号与密码相同，不允许登陆
		if (result != null && result.size() == 1) {
            return result.get(0);
        }
		
		return null;
	}
	
	/**
	 * 根据操作员编号或者手机号查询操作员
	 * 
	 * @param loginName 操作员编号或者手机号
	 * @return null 或者 查询到的操作员实体
	 * @author 张海洋
	 */
	public Czybm findByCzybhOrPhone(String loginName) {
	    String sql = "select a.* from tCZYBM a " +
                "left join tEmployee b on a.EMNum = b.Number " +
                "where a.CZYBH = ? or b.Phone = ? ";
	    
	    List<Czybm> result = BeanConvertUtil.mapToBeanList(
                findBySql(sql, loginName, loginName), Czybm.class);
	    
	    if (result != null && result.size() == 1) {
            return result.get(0);
        }
        
        return null;
	}
	
	/**
	 * 
	 *功能说明:获取所有超级管理员的id列表
	 *@return List<Long>
	 *
	 */
	public List<String> getAllSuperAdminID() {
		String sql = "select u.czybh" + "  from tczybm u, tsys_role r, tsys_role_user ru"
				+ " where r.role_code = ? " + "	and r.role_id=ru.role_id " + "	and u.czybh = ru.czybh"
				+ " order by u.czybh";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { CommonConstant.SUPER_ADMIN });
		List<String> userIds = new ArrayList<String>();
		for (Map<String, Object> obj : list) {
			Object uID = obj.get("czybh");
			if (uID != null) {
				userIds.add(String.valueOf(uID));
			}
		}
		return userIds;
	}
	
	/**
	 * 获取管理员列表
	 * @return
	 */
	public List<Czybm> getSuperAdminList(){
		String sql = "select u.czybh from tczybm u, tsys_role r, tsys_role_user ru"
				+ " where r.role_code = ? " + "	and r.role_id=ru.role_id " + "	and u.czybh = ru.czybh"
				+ " order by u.czybh";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { CommonConstant.SUPER_ADMIN });
		return this.mapper(list);
	}
	
	/**
	 * sql查询用户Map列表转换为Czybm列表
	 * 
	 * @param list
	 * @return
	 */
	public List<Czybm> mapper(List<Map<String, Object>> list) {
		if (null == list || list.size() < 1) {
			return null;
		}
		List<Czybm> userList = new ArrayList<Czybm>(list.size());
		for (Map<String, Object> map : list) {
			try {
				Czybm czybm = new Czybm();
				BeanUtilsEx.copyProperties(czybm, map);
				if (czybm != null) {
					userList.add(czybm);
					czybm = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return userList;
	}

	public List<Czybm> getByRoleId(Long roleId) {
		List<Object> list = new ArrayList<Object>(2);
		String sql = "select u.* from tCzybm u,  tsys_role_user ru" + " where  ru.role_id = ? "
				+ "	and u.czybh = ru.czybh";
		list.add(roleId);
		sql += " order by u.czybh";
		return this.mapper(this.findBySql(sql, list.toArray()));
	}
	
	/**
	 * 获取属于某个角色编码下的所有用户
	 * 
	 * @param roleCode
	 *            角色编码
	 * @param status
	 *            状态，为空不按状态过滤
	 * @return
	 */
	public List<Czybm> getByRoleCode(String roleCode) {
		List<Object> list = new ArrayList<Object>(2);
		String sql = "select u.*" + "  from tczybm u, tsys_role r, tsys_role_user ru" + " where r.role_code = ? "
				+ "	and r.role_id=ru.role_id " + "	and u.czybh = ru.czybh";
		list.add(roleCode);
		sql += " order by u.czybh";
		return this.mapper(this.findBySql(sql, list.toArray()));
	}
	
	/**
	 * 用户是否是超级管理员
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isSuperAdmin(String czybh) {
		return this.isRoleOfCode(CommonConstant.SUPER_ADMIN, czybh);
	}
	
	
	/**
	 * 用户是否属于某个角色
	 * 
	 * @param roleCode
	 *            角色编码
	 * @param userId
	 *            用户
	 * @return
	 */
	private boolean isRoleOfCode(String roleCode, String czybh) {
		if (czybh == null)
			return false;
		String sql = "select u.czybh" + "  from tCzybm u, tsys_role r, tsys_role_user ru"
				+ " where r.role_code = ? " + "	and r.role_id=ru.role_id " + "	and u.czybh = ru.czybh"
				+ " order by u.czybh";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { roleCode });
		if (list == null || list.size() < 1)
			return false;
		for (Map<String, Object> map : list) {
			if (map != null) {
				Object uID = map.get("czybh");
				if (uID != null) {
					String bd = String.valueOf(uID).trim();
					if (bd.equals(czybh)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Czybm getByEmnum(String number) {
		String hql = "from Czybm a where a.emnum=?";
		List<Czybm> list = this.find(hql, new Object[]{number});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getPermission(String id) {
		String sql="exec pQX_CZYMKQX 2,?";
		return this.findBySql(sql,new Object[] { id });
	}

	public List<Long> getAuthIdsByCzybh(String czybh) {
		String sql = "select a.authority_id "
		+"from TSYS_ROLE_AUTHORITY a where a.role_Id in (select b.role_Id from TSYS_ROLE_USER b where b.czybh=?) "
		+"union select authority_id from TSYS_CZYBM_AUTHORITY where CZYBH=?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{czybh,czybh});
		List<Long> retList = null;
		if (list!=null && list.size()>0){
			retList = new ArrayList<Long>();
			for (Map<String, Object> map : list){
				retList.add(Long.valueOf(String.valueOf(map.get("authority_id"))));
			}
		}
		return retList;
	}
	
	// 供应商不能用mkdm和gnmc权限，因此重新启用 by zjf200612 
	/**
	 * 操作员【czybh】是否有【authId】的权限,【1.超级管理员】拥有所有权限，不去判断TSYS_ROLE_AUTHORITY和TSYS_CZYBM_AUTHORITY
	 * @param czybh
	 * @param authId
	 * @return
	 */
	public boolean hasAuthByCzybh(String czybh, int authId) {
		String sql = " select top 1 czy.CZYBH from tCZYBM czy"
				   + " left join TSYS_ROLE_USER a on czy.CZYBH=a.CZYBH"
				   + " left join TSYS_ROLE_AUTHORITY b on a.ROLE_ID=b.ROLE_ID"
				   + " left join TSYS_CZYBM_AUTHORITY c on czy.CZYBH=c.CZYBH"
				   + " where czy.CZYBH=? and (a.ROLE_ID='1' or b.AUTHORITY_ID=? or c.AUTHORITY_ID=?)";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{czybh, authId, authId});
		
		return !list.isEmpty();
	}

	public String getHasAgainMan(){
		String sql=" select QZ from tXTCS where id='HasAgainMan'  ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return (String) map.get("QZ");
		}else{
			return "0";
		}
	}

	/**
	 * 判断某个操作员是否拥有某个权限
	 * @param czybh 操作员编号
	 * @param mkdm 模块代码
	 * @param gnmc 功能名称
	 * @return
	 */
	public boolean hasGNQXByCzybh(String czybh, String mkdm, String gnmc) {
		// 超级管理员拥有所有功能权限
		if (isSuperAdmin(czybh)) {
			// 一般来讲拥有某个权限代表可以进行某些操作
			// 但是，材料信息模块的只查看买手本人与只查看买手部门这两个功能权限比较特殊
			// 没有配置这两个权限，可以查看所有材料，配置了这两个权限，反而做了一些限制，这是设计时的失误，所以此处单独处理
			if ("0038".equals(mkdm) && ("只查看买手本人".equals(gnmc) || "只查看买手部门".equals(gnmc))) {
				return false;
			} else {
				return true;
			}
		}
		
		String sql=" select GNMC from tCZYGNQX where CZYBH=? and MKDM=? and GNMC=?  "
				  +" union "
				  +" select a.GNMC from tRoleGNQX a "
				  +" where exists( "
				  +" 	select 1 from TSYS_ROLE_USER in_a where in_a.CZYBH=? and in_a.ROLE_ID = a.ROLE_ID "
				  +" ) and a.MKDM = ? and a.GNMC = ?"
				  +" union "
				  +" select e.GNMC "
				  +" from TSYS_ROLE_AUTHORITY a" 
				  +" inner join TSYS_AUTHORITY b on a.AUTHORITY_ID = b.AUTHORITY_ID"
				  +" inner join TSYS_MENU c on b.MENU_ID = c.MENU_ID"
				  +" inner join tMODULE d on d.MKMC = c.MENU_NAME"
				  +" inner join tModuleFunc e on d.MKDM=e.MKDM and e.GNMC=substring(b.AUTH_NAME,charindex('>',AUTH_NAME)+1,100)"
				  +" where a.ROLE_ID in ("
				  +" select in_a.ROLE_ID from TSYS_ROLE_USER in_a where in_a.CZYBH = ?"
				  +" ) and d.MKDM=? and e.GNMC=?";
		List<Map<String, Object>> ls=this.findBySql(sql, new Object[]{czybh,mkdm,gnmc,czybh,mkdm,gnmc,czybh,mkdm,gnmc});
		return ls!=null && ls.size()>0;
	}
	
	/**
	 * 获取某个操作员某个模块的所有功能权限
	 * @param czybh 操作员编号
	 * @param mkdm 模块代码
	 * @return
	 */
	public List<Map<String,Object>> findGNQXByCzybhAndMkdm(String czybh,String mkdm) {
		String sql = "";
		
		// 超级管理员拥有所有功能权限
		if (isSuperAdmin(czybh)) {
			sql = " select GNMC from tModuleFunc where mkdm=? ";
			// 一般来讲拥有某个权限代表可以进行某些操作
			// 但是，材料信息模块的只查看买手本人与只查看买手部门这两个功能权限比较特殊
			// 没有配置这两个权限，可以查看所有材料，配置了这两个权限，反而做了一些限制，这是设计时的失误，所以此处单独处理
			if ("0038".equals(mkdm)) {
				sql += " and GNMC<>'只查看买手本人' and GNMC<>'只查看买手部门' ";
			}		
			return this.findBySql(sql, new Object[] {mkdm});
		}
		
		sql=" select GNMC from tCZYGNQX where CZYBH=? and MKDM=? "
	 	   +" union "
 		   +" select a.GNMC from tRoleGNQX a "
		   +" where exists( "
		   +" 	select 1 from TSYS_ROLE_USER in_a where in_a.CZYBH=? and in_a.ROLE_ID = a.ROLE_ID "
		   +" ) and a.MKDM = ?"
		   +" union "
		   +" select e.GNMC "
		   +" from TSYS_ROLE_AUTHORITY a" 
		   +" inner join TSYS_AUTHORITY b on a.AUTHORITY_ID = b.AUTHORITY_ID"
		   +" inner join TSYS_MENU c on b.MENU_ID = c.MENU_ID"
		   +" inner join tMODULE d on d.MKMC = c.MENU_NAME"
		   +" inner join tModuleFunc e on d.MKDM=e.MKDM and e.GNMC=substring(b.AUTH_NAME,charindex('>',AUTH_NAME)+1,100)"
		   +" where a.ROLE_ID in ("
		   +" select in_a.ROLE_ID from TSYS_ROLE_USER in_a where in_a.CZYBH = ?"
		   +" ) and d.MKDM=?";
		return this.findBySql(sql, new Object[] {czybh,mkdm,czybh,mkdm,czybh,mkdm});
	}
	
	public List<Map<String,Object>> getCZYList(String czybh){
		String sql = " select a.CZYBH id,'['+isnull(a.CZYBH,'')+']'+' ['+isnull(a.ZWXM,'')+']'+' ['+isnull(d1.Desc2,'')+']'+' ['+isnull(d2.Desc2, ' ')+']'+' ['+isnull(p.Desc2, ' ')+']' name," 
					+ " '0' pId "
				    + " from tCZYBM a "
					+ " left outer join tEmployee b on b.Number=a.EMNum "
			        + " left outer join tDepartment1 d1 on d1.Code=b.Department1 "
			        + " left outer join tDepartment2 d2 on d2.Code=b.Department2 "
			        + " left outer join tPosition p on p.Code=b.Position "
			        + " where (a.CZYLB is null or a.czylb<>'2') and a.ZFBZ = '0' and a.CZYBH<>? "
			        + " order by b.Department1,b.Department2,Position,a.czybh ";
		return this.findBySql(sql, new Object[]{czybh});
	}
	
	public Result doCopyRight(Czybm czybm){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCopyRight_forXml(?,?,?,?,?)}");
			call.setString(1, czybm.getCzybh());
			call.setString(2, czybm.getLastUpdatedBy());
			call.setString(3, czybm.getDetailJson());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	public Result doAppRight(Czybm czybm){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAppRight_forXml(?,?,?,?,?)}");
			call.setString(1, czybm.getCzybh());
			call.setString(2, czybm.getLastUpdatedBy());
			call.setString(3, czybm.getDetailJson());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 操作员模块权限
	 * @param ptbh 平台编号
	 * @return
	 */
	public List<Map<String, Object>> getQX_ALLMK(int ptbh){
	        String sql = " select c.XTDM,a.XTMC,c.ZXTDM,b.XTMC as ZXTMC,c.MKDM,c.MKMC,f.GNMC,isnull(c.ISAdminAssign,'0') MK_ISAdminAssign," 
	        		+" case when c.ISAdminAssign='1' then '1' else isnull(f.ISAdminAssign,'0') end GN_ISAdminAssign "
	        		+ " from tSYS a "
	        		+ " join tMODULE c on a.XTDM=c.XTDM "
	        		+ " join tSUBSYS b on  b.ZXTDM=c.ZXTDM "
	        		+ " join tMENU e on  c.MKDM=e.MKDM "
	        		+ " left join tMODULEFunc f on c.MKDM=f.MKDM "
	        		+ " where e.PTDM=? "
	        		+ " order by e.PTDM,c.XTDM,c.ZXTDM,c.MKDM ";
	        return this.findBySql(sql, new Object[]{ptbh});
	 }
	/**
	 * 操作员模块权限
	 * @param czybh 操作员编号
	 * @param ptbh 平台编号
	 * @return
	 */
	public List<Map<String, Object>> getQX_CZYQX(String czybh, int ptbh, String containRoleAuth){
	        String sql="select *from ( ";
	        	   sql += "select a.XTDM, a.XTMC, b.ZXTDM, b.XTMC as ZXTMC, c.MKDM, c.MKMC, f.GNMC "
	        		  + " from tSYS a "
	        		  + " left join tMODULE c on a.XTDM = c.XTDM "
	        		  + " left join tMenu g on c.MKDM = g.MKDM "
	        		  + " left join tSUBSYS b on b.ZXTDM = c.ZXTDM "
	        		  + " left join tCZYQX d on d.MKDM = c.MKDM "
	        		  + " left join tCZYGNQX f on d.MKDM = f.MKDM and d.CZYBH = f.CZYBH "
	        		  + " where  d.CZYBH =? and g.PTDM =? " ; 
	         if("1".equals(containRoleAuth)){
	        	 sql += " union select a.XTDM, a.XTMC, b.ZXTDM, b.XTMC as ZXTMC, c.MKDM, c.MKMC, f.GNMC "
		        		  + " from tSYS a "
		        		  + " join tMODULE c on a.XTDM = c.XTDM "
		        		  + " join tMenu g on c.MKDM = g.MKDM "
		        		  + " join tSUBSYS b on b.ZXTDM = c.ZXTDM "
		        		  + " join tRoleQX d on d.MKDM = c.MKDM "
		        		  + " left join tRoleGNQX f on d.MKDM = f.MKDM and d.ROLE_ID = f.ROLE_ID "
		        		  + " where d.ROLE_ID in(select ROLE_ID from TSYS_ROLE_USER where CZYBH=?) and g.PTDM =? " ; 
	        	 sql += ") a order by  a.XTDM, a.ZXTDM, a.MKDM "; 
	        	 return this.findBySql(sql, new Object[]{czybh,ptbh,czybh,ptbh}); 
	         } else{
	        	 sql += ") a order by  a.XTDM, a.ZXTDM, a.MKDM "; 
	        	 return this.findBySql(sql, new Object[]{czybh,ptbh}); 
	         } 		 
	 }
	/**
	 * 判断操作员员工是否重复
	 * @param czybm 
	 * @return
	 */
	public boolean isHasEMNum(Czybm czybm) {
		String sql =" select 1 from tCZYBM where EMNum=? " ;	
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {czybm.getEmnum()});
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	/**
	 * Czybm分页信息
	 * 
	 * @param page
	 * @param czybm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_mtRegion(Page<Map<String,Object>> page, Czybm czybm) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.pk, a.CZYBH, a.MtRegionCode, b.Descr MtRegionDescr "
					+ " ,c.NameChi, d.Descr BelongMtRegionDescr from tCZYMtRegion a "
					+ " left outer join tMtRegion b on a.MtRegionCode = b.RegionCode " 
					+ " left join tEmployee c on c.Number = b.Czybh "
					+ " left join tMtRegion d on d.RegionCode = b.BelongRegionCode"
					+ " where 1=1 ";
    	if (StringUtils.isNotBlank(czybm.getCzybh())) {
			sql += " and a.CZYBH=? ";
			list.add(czybm.getCzybh());
		}	
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.pk ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public void doCZYQXByRole(Long roleId, String czybh){
		String sql=" insert into tCZYQX(CZYBH,MKDM,QYRQ,TYRQ,QYSJ,TYSJ,QYXQ) "
				 + " select a.CZYBH,b.ROLE_ID,b.MKDM,'20020101','21000101','000001','240000','1111111' "
				 + " from TSYS_ROLE_USER a "
				 + " inner join tRoleQX b on a.ROLE_ID=b.ROLE_ID "
				 + " where a.ROLE_ID=? and a.CZYBH=? "
				 + " and not exists(select 1 from tCZYQX in_a where in_a.CZYBH=a.CZYBH and in_a.MKDM=b.MKDM) ";
		this.executeUpdateBySql(sql,new Object[]{roleId,czybh});
	}
	
	public void doCZYGNQXByRole(Long roleID, String czybh){
		String sql=" insert into tCZYGNQX(CZYBH,MKDM,GNMC)   "
				 + " select a.CZYBH,b.MKDM,b.GNMC "
				 + " from TSYS_ROLE_USER a "
				 + " inner join tRoleGNQX b on a.ROLE_ID=b.ROLE_ID "
				 + " where a.ROLE_ID=? and a.CZYBH=? "
				 + " and not exists(select 1 from tCZYGNQX in_a where in_a.CZYBH=a.CZYBH and in_a.MKDM=b.MKDM and in_a.GNMC=b.GNMC) ";
		this.executeUpdateBySql(sql,new Object[]{roleID,czybh});
	}
	
	public boolean isPasswordExpired(String czybh) {
		String sql =" select 1 from tCZYBM a where a.CZYBH=?" 
				   +" and datediff(day, a.ModifyMMDate, getdate()) >=(select cast(QZ as int) from tXTCS where ID='MODIFYMMTIME') " ;	
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {czybh});
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	public List<Long> getPersonalAuthIdsByCzybh(String czybh) {
		String sql = "select authority_id from TSYS_CZYBM_AUTHORITY where CZYBH=?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{czybh});
		List<Long> retList = null;
		if (list!=null && list.size()>0){
			retList = new ArrayList<Long>();
			for (Map<String, Object> map : list){
				retList.add(Long.valueOf(String.valueOf(map.get("authority_id"))));
			}
		}
		return retList;
	}
	
	/**
	 * Czybm权限明细
	 * @param page
	 * @param czybm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlAuthDetail(Page<Map<String,Object>> page, Czybm czybm) {
		Assert.notNull(czybm);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAuthDetail(?)}");
			call.setString(1, czybm.getCzybh());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
	
	
	/**
	 * 操作员平台权限
	 * @param page
	 * @param ptbh
	 * @param czybh
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlPlatformAuth(Page<Map<String,Object>> page, 
			String ptbh, String czybh) {
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pQX_CZYPTQX(?,?)}");
			call.setString(1, ptbh);
			call.setString(2, czybh);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}	
	
	public List<Map<String, Object>> getCzyByIds(String czybhs){
		if (StringUtils.isBlank(czybhs)) {
			return null;
		}
		String  sql = "select czybh,zwxm from tczybm where 1=1  "; 
		sql += " and czybh in " + "('"+czybhs.replaceAll(",", "','")+"')";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			 return list;
		}
		return null;

    }
	
	public boolean checkIsWhiteIp(Long ip){
		String  sql = "select 1 from " +
				" tWhiteIP " +
				" where 1 = 1 and  ? between IPFrom and IPTo and expired = 'F'  "; 
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{ip});
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
    }
}

