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
import com.house.home.entity.basic.Driver;
@SuppressWarnings("serial")
@Repository
public class DriverDao extends BaseDao {
	public Driver getByPhoneAndMm(String phone, String mm){
		String hql = "select a from Driver a where  a.phone=? and a.mm=? ";		
		List<Driver> list =  this.find(hql, new Object[]{phone,mm});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Driver getByPhone(String phone) {
		String hql="select a from Driver a where a.phone=? and a.expired='F'";
		List<Driver> list =  this.find(hql, new Object[]{phone});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 司机分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Driver driver) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.*, b.NOTE DriverTypeDescr, c.NOTE GenderDescr from tDriver a "
				+ " left join tXTDM b on a.DriverType=b.CBM and b.ID='DRIVERTYPE' "
				+ " left join tXTDM c on a.Gender=c.CBM and c.ID='GENDER'  where 1=1 ";
		
		if (StringUtils.isNotBlank(driver.getNameChi())) {
			sql += " and a.NameChi like ? ";
			list.add("%"+driver.getNameChi()+"%");
		}
		if (StringUtils.isNotBlank(driver.getPhone())) {
			sql += " and a.Phone=? ";
			list.add(driver.getPhone());
		}
		if (StringUtils.isNotBlank(driver.getDriverType())) {
			sql += " and a.DriverType=? ";
			list.add(driver.getDriverType());
		}
		if (StringUtils.isBlank(driver.getExpired()) || "F".equals(driver.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public Driver getByName(String nameChi) {
		String hql = "from Driver where NameChi=?";
		List<Driver> list = this.find(hql, new Object[]{nameChi});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Driver> findByNoExpired() {
		String hql = "from Driver where expired<>'T' ";
		return this.find(hql);
	}

	/**
	 * @Description: TODO 司机信息存储过程
	 * @author	created by zb
	 * @date	2018-11-1--下午4:56:13
	 * @param driver
	 * @return
	 */
	public Result doSave(Driver driver) {
		Assert.notNull(driver);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSjXxGl_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, driver.getM_umState());
			call.setString(2, driver.getCode());
			call.setString(3, driver.getDriverType());
			call.setString(4, driver.getNameChi());
			call.setString(5, driver.getGender());
			call.setString(6, driver.getCarNo());
			call.setString(7, driver.getPhone());
			call.setString(8, driver.getMm());
			call.setString(9, driver.getIdNum());
			call.setString(10, driver.getAddress());
			call.setTimestamp(11, driver.getJoinDate()==null?null : new Timestamp(driver.getJoinDate().getTime()));
			call.setTimestamp(12, driver.getLeaveDate()==null?null : new Timestamp(driver.getLeaveDate().getTime()));
			call.setString(13, driver.getRemarks());
			call.setString(14, driver.getLastUpdatedBy());
			call.setString(15, driver.getExpired());
			call.setString(16, driver.getActionLog());
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(17)));
			result.setInfo(call.getString(18));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description: TODO 检查唯一值手机号码是否重复
	 * @author	created by zb
	 * @date	2018-11-5--下午4:00:00
	 * @param phone
	 * @return
	 */
	public Boolean checkPhone(String phone) {
		String sql = "select 1 from tDriver where Phone = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{phone});
		if(list != null && list.size() > 0){// 当存在时返回false
			return false;
		}
		return true;
	}
}
