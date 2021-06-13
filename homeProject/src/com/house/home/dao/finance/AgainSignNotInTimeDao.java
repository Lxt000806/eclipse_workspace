package com.house.home.dao.finance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.WorkQltFeeTran;
import com.house.home.entity.project.Worker;

@SuppressWarnings("serial")
@Repository
public class AgainSignNotInTimeDao extends BaseDao {

	/**
	 * AgainSignNotInTime分页信息
	 * 
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select b.CustCode,a.Descr,a.Address,c.NOTE statusDescr,b.NewSignDate,d.NameChi designManDescr, "
				+ "isnull(e.Desc1,'')+' '+isnull(f.Desc1,'')+' '+isnull(g.Desc1,'') departmentDescr  "
				+ "from tCustomer a  "
				+ "inner join (select max(NewSignDate)NewSignDate,CustCode from tAgainSign group by CustCode) b on b.CustCode=a.Code "
				+ "left join tXTDM c on a.Status=c.CBM and c.ID='CUSTOMERSTATUS' "
				+ "left join tEmployee d on a.DesignMan=d.Number "
				+ "left join tDepartment1 e on d.Department1=e.Code "
				+ "left join tDepartment2 f on d.Department2=f.Code "
				+ "left join tDepartment3 g on d.Department3=g.Code "
				+ "where a.Status in ('1','2','3') and  1=1 ";
		if (customer.getDateFrom() != null) {
			sql += " and b.NewSignDate >= ? ";
			list.add(customer.getDateFrom());
		}
		if (customer.getDateTo() != null) {
			sql += " and b.NewSignDate <= ?";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDateTo())
					.getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.CustCode";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}
