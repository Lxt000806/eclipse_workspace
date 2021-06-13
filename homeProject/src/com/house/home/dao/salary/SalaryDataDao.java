package com.house.home.dao.salary;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryData;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class SalaryDataDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from (" +
				" select a.Code, a.Descr, a.IndLevel,x1.note indLevelDescr, a.ObjType,x2.note objTypeDescr, a.PeriodType,x5.note PeriodTypeDescr, " +
				"		a.Remarks, a.Status,x4.NOTE statusdescr, a.IndUnit, a.CalcMode, x3.note CalcModeDescr , a.Formula , " +
				"	a.FormulaShow, a.FormulaTpl, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog,x6.note IndTypeDescr" +
				" from tSalaryInd a " +
				" left join tXTDM x1 on x1.cbm = a.IndLevel and x1.id = 'SALINDLEVEL' " +//--指标级别
				" left join tXTDM x2 on x2.cbm = a.ObjType and x2.id = 'SALOBJTYPE' " + //-- 适用对象
				" left join tXTDM x3 on x3.cbm = a.CalcMode and x3.id = 'SALCALCMODE' " +// --计算方式
				" left join tXTDM x4 on x4.cbm = a.status and x4.id = 'SALENABLESTAT' " +// 薪酬状态
				" left join tXTDM x5 on x5.cbm = a.PeriodType and x5.ID = 'SALPERIODTYPE' " +//周期
				" left join tXTDM x6 on x6.cbm = a.IndType and x6.Id = 'SALINDTYPE' " +//指标分类
				" where 1=1" ;
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}
