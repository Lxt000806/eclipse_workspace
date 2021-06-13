package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.InjuryInsurParam;

@SuppressWarnings("serial")
@Repository
public class InjuryInsurParamDao extends BaseDao {

	/**
	 * InjuryInsurParam分页信息
	 * 
	 * @param page
	 * @param injuryInsurParam
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InjuryInsurParam injuryInsurParam) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.ConSignCmp,b.Descr ConSignCmpDescr,a.InjuryInsurBaseMin,a.InjuryInsurRate,a.Expired,a.LastUpdate,a.LastUpdatedBy,a.ActionLog " 
					+"from tInjuryInsurParam a "
					+"left join tConSignCmp b on a.ConSignCmp = b.Code  where 1=1 ";

    	if (StringUtils.isNotBlank(injuryInsurParam.getConSignCmp())) {
			sql += " and a.ConSignCmp=? ";
			list.add(injuryInsurParam.getConSignCmp());
		}
    	if (StringUtils.isBlank(injuryInsurParam.getExpired()) || "F".equals(injuryInsurParam.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

