package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.SalaryType;

@SuppressWarnings("serial")
@Repository
public class SalaryTypeDao extends BaseDao {

	/**
	 * SalaryType分页信息
	 * 
	 * @param page
	 * @param salaryType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryType salaryType) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Code,a.Descr,a.IsCalCost,b1.NOTE IsCalCostDescr,a.IsSign,b2.NOTE IsSignDescr,c1.Code Code1,c1.Descr WorkType1Descr,c2.Code Code2,c2.Descr WorkType2Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, " 
				+" a.IsCalProJectCost,b3.Note IsCalProJectCostDescr from tSalaryType a "
                +" left outer join tXTDM b1 on a.IsCalCost=b1.IBM and b1.ID=  'YESNO' "  
                +" left outer join tXTDM b2 on a.IsSign=b2.IBM and b2.ID=  'YESNO' "
                +" left outer join tXTDM b3 on a.IsCalProJectCost=b3.IBM and b3.ID=  'YESNO' "
                +" left outer join tWorkType2 c2 on LTrim(a.WorkType2)=LTrim(c2.Code) "
                +" left outer join tWorkType1 c1 on LTrim(c2.WorkType1)=LTrim(c1.Code) "
                +" where 1=1 ";

    	if (StringUtils.isNotBlank(salaryType.getCode())) {
			sql += " and a.Code=? ";
			list.add(salaryType.getCode());
		}
    	if (StringUtils.isNotBlank(salaryType.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(salaryType.getDescr());
		}
    	if (StringUtils.isNotBlank(salaryType.getIsCalCost())) {
			sql += " and a.IsCalCost=? ";
			list.add(salaryType.getIsCalCost());
		}
    	if (StringUtils.isNotBlank(salaryType.getIsSign())) {
			sql += " and a.IsSign=? ";
			list.add(salaryType.getIsSign());
		}
    	if (StringUtils.isNotBlank(salaryType.getWorkType2())) {
			sql += " and a.WorkType2=? ";
			list.add(salaryType.getWorkType2());
		}
    	if (salaryType.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(salaryType.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(salaryType.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(salaryType.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(salaryType.getExpired()) || "F".equals(salaryType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(salaryType.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(salaryType.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<SalaryType> findByNoExpired() {
		String hql = "from SalaryType a where a.expired='F' order by a.code";
		return this.find(hql);
	}

	public Map<String,Object> findByCode(String code) {
		String sql = "select a.code,a.Descr,a.IsCalCost,a.IsSign,a.WorkType2,b.WorkType1,a.Expired,a.IsCalProjectCost "
				+"from tSalaryType a left join tworktype2 b on a.WorkType2=b.Code where a.code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	


}

