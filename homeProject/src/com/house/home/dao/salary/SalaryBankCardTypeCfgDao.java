package com.house.home.dao.salary;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryBankCardTypeCfg;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class SalaryBankCardTypeCfgDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			SalaryBankCardTypeCfg salaryBankCardTypeCfg) {
		List<Object> list = new ArrayList<Object>();
		 
		String sql = " select * from (" +
				" select  a.pk,a.SalaryPayCmp, a.SalarySchemeType, a.PayMode, a.BankCardType, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog ," +
				"	b.Descr SalarySchemeTypeDescr, x1.note bankCardTypeDescr,x2.NOTE PayModeDescr,c.Descr salaryPayCmpDescr" +
				" from  tSalaryBankCardTypeCfg a" +
				" left join tSalarySchemeType b on b.Code = a.SalarySchemeType" +
				" left join tConSignCmp c on c.Code = a.SalaryPayCmp " +
				" left join tXTDM x1 on x1.cbm = a.BankCardType and x1.id = 'SALBANKTYPE'" +
				" left join tXTDM x2 on x2.cbm = a.PayMode and x2.id = 'SALPAYMODE'" +
				" where 1=1 " ;
		if(StringUtils.isNotBlank(salaryBankCardTypeCfg.getSalaryPayCmp())){
			sql+=" and a.SalaryPayCmp = ?";
			list.add(salaryBankCardTypeCfg.getSalaryPayCmp());
		}
		if(StringUtils.isNotBlank(salaryBankCardTypeCfg.getPayMode())){
			sql += " and a.PayMode = ? ";
			list.add(salaryBankCardTypeCfg.getPayMode());
		}
		if (StringUtils.isBlank(salaryBankCardTypeCfg.getExpired())
				|| "F".equals(salaryBankCardTypeCfg.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public boolean checkSalaryBankCardTypeCfg(SalaryBankCardTypeCfg salaryBankCardTypeCfg,String m_umState){

		String sql = "";
		Object[] objects = new Object[]{};
		if("A".equals(m_umState)){
			sql = "select 1 from tSalaryBankCardTypeCfg a where a.SalaryPayCmp = ? and a.PayMode = ? " +
					" and a.SalarySchemeType = ? ";
			objects = new Object[]{salaryBankCardTypeCfg.getSalaryPayCmp(),salaryBankCardTypeCfg.getPayMode()
					,salaryBankCardTypeCfg.getSalarySchemeType()};
		} else {
			sql = "select 1 from tSalaryBankCardTypeCfg a where a.SalaryPayCmp = ? and PayMode = ? " +
					" and a.SalarySchemeType = ? and a.pk <> ? ";
			objects = new Object[]{salaryBankCardTypeCfg.getSalaryPayCmp(),salaryBankCardTypeCfg.getPayMode()
					,salaryBankCardTypeCfg.getSalarySchemeType(), salaryBankCardTypeCfg.getPk()};
		}
		
		List<Map<String,Object>> list = this.findBySql(sql, objects);
		
		if(list != null && list.size() > 0){
			return false;
		}
		
		return true;
	}
	
}
