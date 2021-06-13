package com.house.home.dao.salary;
import com.house.framework.commons.orm.BaseDao;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.salary.SalaryPayment;
import com.sun.org.apache.bcel.internal.generic.NEW;

@SuppressWarnings("serial")
@Repository
public class SalaryPaymentQueryDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryPayment salaryPayment) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.* " +
				"	from tSalaryPayment a" +
				"	where 1=1 " ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> getSchemeList(SalaryPayment salaryPayment){
		List<Object> params = new ArrayList<Object>();
		
		String sql = "select b.Pk SalaryScheme, b.Descr from tSalaryStatusCtrl a " +
				"	left join tSalaryScheme b on b.PK = a.SalaryScheme" +
				"	where a.SalaryMon = ? ";
		params.add(salaryPayment.getSalaryMon());
		if(StringUtils.isNotBlank(salaryPayment.getSalaryStatus())){
			sql += " and a.status = ? ";
			params.add(salaryPayment.getSalaryStatus());
		}
		
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size()>0){
	
			return list;
		}
		
		return null;
	}
	
	public List<Map<String, Object>> getPaymentDefList(SalaryPayment salaryPayment){
		
		String sql = "select * from tSalaryPaymentDef where salaryScheme = ? order by SeqNo ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryPayment.getSalaryScheme()});
		if(list != null && list.size()>0){
	
			return list;
		}
		
		return null;
	}
}
