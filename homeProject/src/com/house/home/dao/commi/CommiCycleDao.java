package com.house.home.dao.commi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import com.house.home.entity.commi.CommiCustStakeholderSuppl;
import com.house.home.entity.commi.CommiCycle;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class CommiCycleDao extends BaseDao {

	/**
	 * CommiCycle分页信息
	 * 
	 * @param page
	 * @param commiCycle
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCycle commiCycle) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select No, a.Mon , a.FloatBeginMon , a.FloatEndMon, a.Status, b.NOTE StatusDescr, "
				+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Remarks "
				+ " from tCommiCycle a "
				+ " left join tXTDM b on b.ID='CYCLESTATUS' and b.CBM=a.Status "
				+ " where 1=1";
    	if (StringUtils.isNotBlank(commiCycle.getNo())) {
			sql += " and a.No=? ";
			list.add(commiCycle.getNo());
		}
    	if (commiCycle.getMon() != null) {
			sql += " and a.Mon=? ";
			list.add(commiCycle.getMon());
		}
    	if (StringUtils.isNotBlank(commiCycle.getStatus())) {
			sql += " and a.Status=? ";
			list.add(commiCycle.getStatus());
		}
    	if (commiCycle.getFloatBeginMon() != null) {
			sql += " and a.FloatBeginMon=? ";
			list.add(commiCycle.getFloatBeginMon());
		}
    	if (commiCycle.getFloatEndMon() != null) {
			sql += " and a.FloatEndMon=? ";
			list.add(commiCycle.getFloatEndMon());
		}
    	if (StringUtils.isNotBlank(commiCycle.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(commiCycle.getRemarks());
		}
    	if (commiCycle.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(commiCycle.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(commiCycle.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(commiCycle.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(commiCycle.getExpired()) || "F".equals(commiCycle.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(commiCycle.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(commiCycle.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.No desc ";
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
		String sql = "select status from tCommiCycle where no = ? ";
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
	public String isExistsPeriod(String no, Integer mon) {
		String sql = " select Mon from tCommiCycle where No= ?  and Status='1'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			String sqlDate = " select 1 from tCommiCycle where Mon< ? and Status='1' ";
			List<Map<String, Object>> listDate = this.findBySql(sqlDate,
					new Object[] { list.get(0).get("Mon") });
			if (listDate != null && listDate.size() > 0) {
				return "2";// 之前的统计周期未计算完成，不允许计算本周期提成!
			} else {
				return null;
			}
		} else {
			return "1";// 没有找到相应的提成统计周期或该统计周期已计算完成!
		}
	}
	
	/**
	 * 计算完成
	 * 
	 * @param no
	 */
	public void doComplete(String no) {
		String sql = " update tCommiCycle set Status='2' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 退回
	 * 
	 * @param no
	 */
	public void doReturn(String no) {
		String sql = " update tCommiCycle set Status='1' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}
	
	/**
	 * 生成提成数据
	 * 
	 * @param commiCycle
	 */
	public Map<String, Object> doCount(CommiCycle commiCycle) {
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCalcCommi(?,?)}");
			call.setString(1, commiCycle.getNo());
			call.setString(2, commiCycle.getLastUpdatedBy());
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

    public Page<Map<String, Object>> findSupplierRebatePageBySql(Page<Map<String, Object>> page,
            CommiCustStakeholderSuppl commiCustStakeholderSuppl) {
        
        ArrayList<Object> params = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select a.PK, a.Date, a.ItemType1, b.Descr ItemType1Descr, "
                + "    SupplCode, c.Descr SupplDescr, a.CustCode, d.Address, "
                + "    a.ItemDescr, a.Amount, a.EmpCode, e.NameChi EmpName, "
                + "    a.CommiAmount, a.Remarks, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tCommiCustStakeholder_Suppl a "
                + "left join tItemType1 b on b.Code = a.ItemType1 "
                + "left join tSupplier c on c.Code = a.SupplCode "
                + "left join tCustomer d on d.Code = a.CustCode "
                + "left join tEmployee e on e.Number = a.EmpCode "
                + "where 1 = 1 ";
        
        if (commiCustStakeholderSuppl.getDateFrom() != null) {
            sql += "and a.Date >= ? ";
            params.add(commiCustStakeholderSuppl.getDateFrom());
        }
        
        if (commiCustStakeholderSuppl.getDateTo() != null) {
            sql += "and a.Date <= ? ";
            params.add(commiCustStakeholderSuppl.getDateTo());
        }
        
        if (StringUtils.isNotBlank(commiCustStakeholderSuppl.getCustCode())) {
            sql += "and a.CustCode = ? ";
            params.add(commiCustStakeholderSuppl.getCustCode());
        }
        
        if (StringUtils.isNotBlank(commiCustStakeholderSuppl.getSupplCode())) {
            sql += "and a.SupplCode = ? ";
            params.add(commiCustStakeholderSuppl.getSupplCode());
        }
        
        if (StringUtils.isNotBlank(commiCustStakeholderSuppl.getItemType1())) {
            sql += "and a.ItemType1 = ? ";
            params.add(commiCustStakeholderSuppl.getItemType1());
        }
        
        if (StringUtils.isNotBlank(commiCustStakeholderSuppl.getEmpCode())) {
            sql += "and a.EmpCode = ? ";
            params.add(commiCustStakeholderSuppl.getEmpCode());
        }
        
        if (StringUtils.isBlank(commiCustStakeholderSuppl.getExpired())
                || "F".equals(commiCustStakeholderSuppl.getExpired())) {
            sql += "and a.Expired='F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.LastUpdate desc";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }
    
    /**
	 * 获取最近计算完成的月份
	 * 
	 * @param mon
	 * @return
	 */
	public String getMaxMon() {
		String sql = " select max(Mon) Mon from tCommiCycle where Status = '2' ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {});
		if (list != null && list.size() > 0) {
			return list.get(0).get("Mon").toString();
		} else {
			return " ";
		}
	}
}

