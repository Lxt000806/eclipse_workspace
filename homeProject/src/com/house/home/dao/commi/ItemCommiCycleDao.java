package com.house.home.dao.commi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.commi.CommiCycle;
import com.house.home.entity.commi.ItemCommiCycle;

@SuppressWarnings("serial")
@Repository
public class ItemCommiCycleDao extends BaseDao {

	/**
	 * ItemCommiCycle分页信息
	 * 
	 * @param page
	 * @param itemCommiCycle
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiCycle itemCommiCycle) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.No, a.BeginDate, a.EndDate, a.Status, b.NOTE StatusDescr, a.Remarks,"
          + " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Mon"
          + " from tItemCommiCycle a"
          + " left join tXTDM b on b.ID='SOFTPERFSTATUS' and b.CBM=a.Status"
          + " where 1=1 ";
		
		if(StringUtils.isNotBlank(itemCommiCycle.getNo())){
			sql+=" and a.no like ? ";
			list.add("%"+itemCommiCycle.getNo()+"%");
		}
		if(itemCommiCycle.getDateFrom()!=null){
			sql+=" and a.beginDate>= ?  ";
			list.add(itemCommiCycle.getDateFrom());
		}
		if(itemCommiCycle.getDateTo()!=null){
			sql+=" and a.beginDate< dateAdd(d,1,?) ";
			list.add(itemCommiCycle.getDateTo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.beginDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 查询计算状态
	 * 
	 * @param no
	 * @return
	 */
	public String checkStatus(String no) {
		String sql = "select status from tItemCommiCycle where no = ? ";
		return this.findBySql(sql, new Object[] { no }).get(0).get("status")
				.toString();
	}
	
	/**
	 * 检查周期
	 * 
	 * @param no
	 * @param mon
	 * @return
	 */
	public String isExistsPeriod(String no, String beginDate) {
		String sql = " select BeginDate from tItemCommiCycle where No= ?  and Status='1'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			String sqlDate = " select 1 from tItemCommiCycle where BeginDate< ? and Status='1' ";
			List<Map<String, Object>> listDate = this.findBySql(sqlDate,
					new Object[] { list.get(0).get("BeginDate") });
			if (listDate != null && listDate.size() > 0) {
				return "2";// 之前的统计周期未计算完成，不允许计算本周期业绩!
			} else {
				return null;
			}
		} else {
			return "1";// 没有找到相应的业绩统计周期或该统计周期已计算完成!
		}
	}
	
	/**
	 * 计算完成
	 * 
	 * @param no
	 */
	public void doComplete(String no) {
		String sql = " update tItemCommiCycle set Status='2' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 退回
	 * 
	 * @param no
	 */
	public void doReturn(String no) {
		String sql = " update tItemCommiCycle set Status='1' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}
	
	/**
	 * 生成提成数据
	 * 
	 * @param itemCommiCycle
	 */
	public Map<String, Object> doCount(ItemCommiCycle itemCommiCycle) {
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCalcItemCommi(?,?)}");
			call.setString(1, itemCommiCycle.getNo());
			call.setString(2, itemCommiCycle.getLastUpdatedBy());
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			if (list.size() != 0 && list != null) {
				if (list.get(0).get("errmsg") != null)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
	
	/**
	 * 获取独立销售周期
	 * 
	 * @param mon
	 * @return
	 */
	public String getItemCommiNoByMon(Integer mon) {
		String sql = " select no from tItemCommiCycle where Mon= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {mon});
		if (list != null && list.size() > 0) {
			return list.get(0).get("no").toString();
		} else {
			return " ";
		}
	}
}

