package com.house.home.dao.workflow;

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
import com.house.home.entity.workflow.ActGroup;

@SuppressWarnings("serial")
@Repository
public class ActGroupDao extends BaseDao {

	/**
	 * ActGroup分页信息
	 * 
	 * @param page
	 * @param actGroup
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActGroup actGroup) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_ID_GROUP a where 1=1 ";

    	if (StringUtils.isNotBlank(actGroup.getId())) {
			sql += " and a.ID_=? ";
			list.add(actGroup.getId());
		}
    	if (actGroup.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actGroup.getRev());
		}
    	if (StringUtils.isNotBlank(actGroup.getName())) {
			sql += " and a.NAME_ like ? ";
			list.add("%"+actGroup.getName()+"%");
		}
    	if (StringUtils.isNotBlank(actGroup.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actGroup.getType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.ID_";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 查询子表
	 * @author	created by zb
	 * @date	2019-3-30--上午10:13:44
	 * @param page
	 * @param actGroup
	 * @return
	 */
	
	public Page<Map<String, Object>> findUserPageBySql(
			Page<Map<String, Object>> page, ActGroup actGroup) {
		String sql = "select a.ID_,a.FIRST_,a.LAST_,a.EMAIL_,d.ZWXM " +
					"from ACT_ID_USER a " +
					"left join ACT_ID_MEMBERSHIP b on b.USER_ID_=a.ID_ " +
					"left join ACT_ID_GROUP c on c.ID_=b.GROUP_ID_ " +
					"left join tCZYBM d on d.CZYBH=a.ID_ " +
					"where c.ID_=?";
		return this.findPageBySql(page, sql, new Object[]{actGroup.getId()});
	}
	
	public Page<Map<String, Object>> getRoleAuthorityJqGrid(
			Page<Map<String, Object>> page, ActGroup actGroup) {
		String sql = "select a.pk,a.WfProcNo,b.Descr from tWfGroupAuthority  a " +
				" left join tWfProcess b on b.No = a.WfProcNo " +
				" where a.GroupId = ? order by a.WfProcNo";
		return this.findPageBySql(page, sql, new Object[]{actGroup.getId()});
	}
	/**
	 * 保存操作员信息
	 * @author	created by zb
	 * @date	2019-3-30--下午4:07:44
	 * @param actGroup
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(ActGroup actGroup) {
		Assert.notNull(actGroup);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pActGroup(?,?,?,?)}");
			call.setString(1, actGroup.getId());
			call.registerOutParameter(2, Types.INTEGER);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.setString(4, actGroup.getDetailJson());
//			System.out.println(custVisit.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(2)));
			result.setInfo(call.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public boolean existsAuth(ActGroup actGroup){
		
		String sql="select 1 from tWfGroupAuthority " +
				" where wfProcNo = ? and GroupId = ?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{actGroup.getWfProcNo(),actGroup.getId()});
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
}

