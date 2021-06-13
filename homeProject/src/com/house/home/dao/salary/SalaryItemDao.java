package com.house.home.dao.salary;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryItem;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class SalaryItemDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryItem salaryItem) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = "select * from (" +
				" select a.Precision,a.Code, a.Descr, a.ItemLevel, a.IsAdjustable, a.IsSysRetention, a.ItemGroup, a.Remarks, a.Status, a.LastUpdate," +
				"	LastUpdatedBy, a.Expired, a.ActionLog ,x1.NOTE IsAdjustableDescr,x2.NOTE IsSysRetentionDescr" +
				"	,x3.NOTE ItemGroupDescr ,x4.NOTE StatusDescr,x5.note TypeDescr " +
				" from tSalaryItem a" +
				" left join tXTDM x1 on x1.cbm = a.IsAdjustable and x1.ID = 'YESNO'" +
				" left join tXTDM x2 on x2.cbm = a.IsSysRetention and x2.ID = 'YESNO'" +
				" left join tXTDM x3 on x3.CBM = a.ItemGroup and x3.ID = 'SALITEMGROUP'" +
				" left join tXTDM x4 on x4.CBM = a.Status and x4.ID = 'SALENABLESTAT'" +
				" left join tXTDM x5 on x5.CBM = a.type and x5.ID = 'SALITEMTYPE'" +
				" where 1=1 " ;
		
		if(StringUtils.isNotBlank(salaryItem.getQueryCondition())){
			sql+=" and (a.Code like ? or a.descr like ?) ";
			list.add("%" + salaryItem.getQueryCondition() + "%");
			list.add("%" + salaryItem.getQueryCondition() + "%");
		}
		if(StringUtils.isNotBlank(salaryItem.getType())){
			sql+=" and a.Type = ?";
			list.add(salaryItem.getType());
		}
		if(StringUtils.isNotBlank(salaryItem.getStatus())){
			sql+=" and a.Status = ?";
			list.add(salaryItem.getStatus());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findCategoryDefindPageBySql(
			Page<Map<String, Object>> page, SalaryItem salaryItem) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = "select * from (" +
				" select a.pk,a.FilterRemarks, a.FilterFormula,a.FilterFormulaShow,c.empName NameChi , a.FilterLevel,a.BeginMon,a.EndMon,a.Remarks " +
				" ,x1.note calcmodedescr,a.lastUpdate,a.lastUpdatedby,a.Formula,a.FormulaShow " +
				" from tSalaryItemStatCfg a" +
				" left join tSalaryItem b on b.Code = a.SalaryItem" +
				" left join tSalaryEmp c on a.FilterLevel = '0' and c.empCode = a.FilterFormula" +
				" left join tXTDM x1 on x1.ID = 'SALCALCMODE' and a.CalcMode = x1.CBM " +
				" where 1=1 " ;
		
		if(StringUtils.isNotBlank(salaryItem.getCode())){
			sql+=" and a.SalaryItem = ? ";
			list.add(salaryItem.getCode());
		}
		
		if(StringUtils.isNotBlank(salaryItem.getQueryCondition())){
			sql += " and a.filterlevel " + salaryItem.getQueryCondition();
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean checkSalaryItemDescr(SalaryItem salaryItem, String m_umState){

		String sql = "";
		Object[] objects = new Object[]{};
		if("A".equals(m_umState)){
			sql = "select 1 from tSalaryItem where descr = ? ";
			objects = new Object[]{salaryItem.getDescr()};
		} else {
			sql = "select 1 from tSalaryItem where descr = ? and code <> ? ";
			objects = new Object[]{salaryItem.getDescr(), salaryItem.getCode()};
		}
		
		List<Map<String,Object>> list = this.findBySql(sql, objects);
		
		if(list != null && list.size() > 0){
			return false;
		}
		
		return true;
	}

	public List<Map<String, Object>> findFormulaNodeBySql(SalaryItem salaryItem){
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		String sql = " select  rtrim(code) code,rtrim(name) name ,a.pId, a.id,a.isParent,a.nodeType,rtrim(a.path) path  from (" +
				" select 'XCXM' Code,'薪酬项目' name,'XCXM' AS Path,'true' nodeType,10000 SeqNo ,1 id ,'0' pid,'true' isParent" +
				" UNION ALL" +
				" SELECT '${'+Code+'}' Code,Descr,'XCXM/'+Code AS Path,'true' nodeType,10000 SeqNo,999 id,1 pid,'false' isParent" +
				" FROM tSalaryItem " +
				" WHERE ItemLevel< ? AND Status=1" +
				" UNION ALL" +
				" SELECT 'XTZB' Code,'系统指标' name,'XTZB' AS Path,'true' nodeType,20000 SeqNo, 2 id ,'0' pid,'true' isParent" +
				" UNION ALL" +
				" SELECT '#{'+Code+'}' Code,Descr,'XTZB/'+Code AS Path,'true' nodeType,20000 SeqNo ,999 id, 2 pId,'false' isParent" +
				" FROM tSalaryInd " +
				" WHERE Status=1" +
				" UNION ALL" +
				" SELECT 'XTHS' Code,'系统函数' name,'XTHS' AS Path,'true' nodeType,30000 SeqNo,3 id ,'0' pid,'true' isParent" +
				" UNION ALL" +
				" SELECT '@{'+Code+'}' Code,Descr,'XTHS/'+Code AS Path,'true' nodeType,30000 SeqNo ,999 id,3 pid,'false' isParent" +
				" FROM tSysFuncCfg " +
				" WHERE Status=1" +
				" ) a";
		list = this.findBySql(sql, new Object[]{salaryItem.getItemLevel()});

		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<Map<String, Object>> getOperatorCfg(){

		String sql = "select code ,descr from tOperatorCfg where status = '1' and expired = 'F' ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		
		if(list != null && list.size() > 0){
			return list;
		}
		
		return null;
	}
}
