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
import com.house.home.entity.basic.AppItemTypeBatch;

/**
 * @Description: TODO 下单材料类型批次sql查询
 * @author created by zb
 * @date   2018-7-30--上午11:33:10
 */
@SuppressWarnings("serial")
@Repository
public class AppItemTypeBatchDao extends BaseDao{

	/**
	 * @Description: TODO 分页查询
	 * @author	created by zb
	 * @date	2018-7-30--下午2:06:42
	 * @param page
	 * @param appItemTypeBatch
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, AppItemTypeBatch appItemTypeBatch) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( select a.*, tx1.NOTE IsSetItemDescr, tx2.NOTE InfoAppAllDescr " +
				" from tAppItemTypeBatch a " +
				" left join tXTDM tx1 on tx1.CBM = a.IsSetItem and tx1.ID = 'YESNO' " +
				" left join tXTDM tx2 on tx2.CBM = a.InfoAppAll and tx2.ID = 'YESNO' " +
				" where 1=1 ";
		
		if (StringUtils.isBlank(appItemTypeBatch.getExpired()) || "F".equals(appItemTypeBatch.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(appItemTypeBatch.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+appItemTypeBatch.getCode()+"%");
		}
		if (StringUtils.isNotBlank(appItemTypeBatch.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+appItemTypeBatch.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(appItemTypeBatch.getIsSetItem())) {
			sql += " and a.IsSetItem = ? ";
			list.add(appItemTypeBatch.getIsSetItem());
		}
		if (StringUtils.isNotBlank(appItemTypeBatch.getInfoAppAll())) {
			sql += " and a.InfoAppAll = ? ";
			list.add(appItemTypeBatch.getInfoAppAll());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.lastupdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 新增
	 * @author	created by zb
	 * @date	2018-8-1--上午9:33:03
	 * @param appItemTypeBatch
	 * @return
	 */
	public Result doSave(AppItemTypeBatch appItemTypeBatch) {
		Assert.notNull(appItemTypeBatch);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAppItemTypeBatch(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, appItemTypeBatch.getCode());
			call.setString(2, appItemTypeBatch.getM_umState());
			call.setString(3, appItemTypeBatch.getDescr());
			call.setString(4, appItemTypeBatch.getIsSetItem());
			call.setString(5, appItemTypeBatch.getInfoAppAll());
			call.setInt(6, appItemTypeBatch.getDispSeq());
			call.setString(7, appItemTypeBatch.getLastUpdatedBy());
			call.setTimestamp(8, appItemTypeBatch.getLastUpdate()==/*由long转为time*/
					null?null : new Timestamp(appItemTypeBatch.getLastUpdate().getTime()));
			call.setString(9, appItemTypeBatch.getExpired());
			call.setString(10, appItemTypeBatch.getActionLog());
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR);
			call.setString(13, appItemTypeBatch.getDetailJson());
			//System.out.println(appItemTypeBatch.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(11)));
			result.setInfo(call.getString(12));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description: AppItemTypeBatchDetail表查询
	 * @author	created by zb
	 * @date	2018-8-1--下午1:40:53
	 * @param page
	 * @param appItemTypeBatch 传入Code
	 * @return
	 */
	public Page<Map<String, Object>> findDetailByCode(
			Page<Map<String, Object>> page, AppItemTypeBatch appItemTypeBatch) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from (select a.*,b.Descr ConfItemTypeDescr " +
				" from tAppItemTypeBatchDetail a " +
				" left join tConfItemType b on b.Code = a.ConfItemType " +
				" where a.AppItemTypeBatch = ? " +
				" and a.Expired = 'F' ";
		
		list.add(appItemTypeBatch.getCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.lastupdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}
