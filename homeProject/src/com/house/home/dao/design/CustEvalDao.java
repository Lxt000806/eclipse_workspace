package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustEval;
import com.house.home.entity.design.ItemPlanTemp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class CustEvalDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,CustEval custEval) {
		List<Object> list = new ArrayList<Object>();
	
		String sql ="select a.custCode code, b.descr custDescr , cast(a.prjScore as nvarchar(1))+'星' prjScore ," +
				" cast(a.designScore as nvarchar(1))+'星' designScore , e.nameChi projectDescr," +
				" e2.nameChi designdescr,a.lastUpdate from tCustEval a " +
				" left join tCustomer b on a.custCode= b.code " +
				" left join tEmployee e on e.number = b.projectMan " +
				" left join tEmployee e2 on e2.number = b.designMan  " +
				" where 1=1  " ;
		if(StringUtils.isNotBlank(custEval.getCustCode())){
			sql+=" and a.custCOde= ? ";
			list.add(custEval.getCustCode());
		}
		if(StringUtils.isNotBlank(custEval.getProjectMan())){
			sql+=" and b.projectman = ? ";
			list.add(custEval.getProjectMan());
		}
		if(StringUtils.isNotBlank(custEval.getDesignMan())){
			sql+=" and b.designMan = ? ";
			list.add(custEval.getDesignMan());
		}
		

		return this.findPageBySql(page, sql, list.toArray());
	}
}
