package com.house.home.dao.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.IntPerf;

@SuppressWarnings("serial")
@Repository
public class IntPerfDao extends BaseDao {

	/**
	 * IntPerf分页信息
	 * 
	 * @param page
	 * @param intPerf
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, IntPerf intPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.No, a.BeginDate, a.EndDate, a.Status, b.NOTE StatusDescr, a.Remarks,"
				+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.PrjPerfNo "
				+ " from tIntPerf a"
				+ " left join tXTDM b on b.ID='SOFTPERFSTATUS' and b.CBM=a.Status"
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(intPerf.getNo())) {
			sql += " and a.no = ? ";
			list.add(intPerf.getNo());
		}
		if (intPerf.getDateFrom() != null) {
			sql += " and a.beginDate>= ?  ";
			list.add(intPerf.getDateFrom());
		}
		if (intPerf.getDateTo() != null) {
			sql += " and a.beginDate< dateAdd(d,1,?) ";
			list.add(intPerf.getDateTo());
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
	 * 查询状态
	 * 
	 * @param no
	 * @return
	 */
	public String checkStatus(String no) {
		String sql = " select Status from tIntPerf where No= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			return list.get(0).get("Status").toString();
		} else {
			return null;
		}
	}

	/**
	 * 计算完成
	 * 
	 * @param no
	 */
	public void doSaveCount(String no) {
		String sql = " update tIntPerf set Status='2' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 退回
	 * 
	 * @param no
	 */
	public void doSaveCountBack(String no) {
		String sql = " update tIntPerf set Status='1' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 检查是否能计算周期
	 * 
	 * @param no
	 * @param beginDate
	 * @return
	 */
	public String isExistsPeriod(String no, String beginDate) {
		String sql = " select BeginDate from tIntPerf where No= ?  and Status='1'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			String sqlDate = " select 1 from tIntPerf where BeginDate< ? and Status='1' ";
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

}
