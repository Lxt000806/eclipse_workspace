package com.house.home.dao.insales;

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
import com.house.home.entity.insales.BaseItemTemp;
@SuppressWarnings("serial")
@Repository
public class BaseItemTempDao extends BaseDao {
	/**
	 * BaseItemTemp分页信息
	 * 
	 * @param page
	 * @param baseItemType2
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemTemp baseItemTemp) {
		List<Object> list = new ArrayList<Object>();

		String sql ="select a.*,rtrim(a.Type)+ ' ' + b.NOTE TypeDescr from tBaseItemTemp a "
                + " left outer join tXTDM b on a.Type=b.CBM and b.id='BASETEMPTYPE' "
                + " where a.expired='F' order by b.ibm";
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 主页查询
	 * @author	created by zb
	 * @date	2019-5-22--下午5:55:41
	 * @param page
	 * @param baseItemTemp
	 * @return
	 */
	public Page<Map<String, Object>> findListPageBySql(
			Page<Map<String, Object>> page, BaseItemTemp baseItemTemp) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.No,a.Type,b.NOTE TypeDescr,a.Descr,a.Remark, " +
					"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog  " +
					"from tBaseItemTemp a  " +
					"left outer join tXTDM b on b.CBM=a.Type and b.iD='BASETEMPTYPE' " +
					"where 1=1 ";
		if (StringUtils.isBlank(baseItemTemp.getExpired()) || "F".equals(baseItemTemp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(baseItemTemp.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+baseItemTemp.getDescr()+"%");
		}
		// 排序
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 查看明细
	 * @author	created by zb
	 * @date	2019-5-23--上午10:36:11
	 * @param page
	 * @param baseItemTemp
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, BaseItemTemp baseItemTemp) {
		List<Object> list = new ArrayList<Object>();
		String sql = "Select a.pk,a.No,a.BaseItemCode,b.Descr BaseItemCodeDescr,a.Qty,a.DispSeq, " +
					"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog  " +
					"from ( " +
					"	select * from tBaseItemTempDetail " ;
		if (StringUtils.isNotBlank(baseItemTemp.getM_umState()) && !"A".equals(baseItemTemp.getM_umState())) {
			sql +=	"	where no=? ";
			list.add(baseItemTemp.getNo());
		} else {
			sql +=	"	where 1<>1 ";
		}
			sql +=	") a " +
					"left outer join tBaseItem b on b.Code=a.BaseItemCode " +
					"ORDER BY a.DispSeq";
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 存储过程保存
	 * @author	created by zb
	 * @date	2019-5-23--下午6:07:16
	 * @param baseItemTemp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(BaseItemTemp baseItemTemp) {
		Assert.notNull(baseItemTemp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJcbjmb_forXml(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, baseItemTemp.getM_umState());
			call.setString(2, baseItemTemp.getNo());
			call.setString(3, baseItemTemp.getType());
			call.setString(4, baseItemTemp.getDescr());
			call.setString(5, baseItemTemp.getRemark());
			call.setString(6, baseItemTemp.getLastUpdatedBy());
			call.setString(7, baseItemTemp.getExpired());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.setString(10, baseItemTemp.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
}
