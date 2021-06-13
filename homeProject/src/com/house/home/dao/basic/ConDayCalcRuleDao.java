package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ConDayCalcRule;

@SuppressWarnings("serial")
@Repository
public class ConDayCalcRuleDao extends BaseDao {

	/**
	 * ConDayCalcRule分页信息
	 * 
	 * @param page
	 * @param conDayCalcRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConDayCalcRule conDayCalcRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.BaseConDay,a.BaseWork,a.DayWork,c.Descr WorkType12Descr,d.NOTE TypeDescr, "
			+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.PK,b.NOTE WorkerClassifyDescr "
			+"from tConDayCalcRule a " 
			+"left join tXTDM b on a.WorkerClassify=b.CBM and b.ID='WORKERCLASSIFY' "
			+"left join tWorkType12 c on a.WorkType12=c.Code "
			+"left join tXTDM d on a.Type=d.CBM and d.ID='DAYCALCTYPE' where 1=1 ";

    	if (StringUtils.isNotBlank(conDayCalcRule.getWorkerClassify())) {
			sql += " and a.WorkerClassify=? ";
			list.add(conDayCalcRule.getWorkerClassify());
		}
    	if (StringUtils.isNotBlank(conDayCalcRule.getWorkType12())) {
			sql += " and a.WorkType12=? ";
			list.add(conDayCalcRule.getWorkType12());
		}
    	if (StringUtils.isNotBlank(conDayCalcRule.getType())) {
			sql += " and a.Type=? ";
			list.add(conDayCalcRule.getType());
		}
		if (StringUtils.isBlank(conDayCalcRule.getExpired()) || "F".equals(conDayCalcRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 是否存在重复工种分类12，工人分类
	 * @param code
	 * @return
	 */
	public List<Map<String,Object>> isRepeated(ConDayCalcRule conDayCalcRule) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select b.NOTE WorkerClassifyDescr,c.Descr WorkType12Descr " +
					"from tConDayCalcRule a " +
					"left join tXTDM b on a.WorkerClassify=b.CBM and b.ID='WORKERCLASSIFY' " +
					"left join tWorkType12 c on a.WorkType12=c.Code  " +
					"where WorkerClassify=? and WorkType12=? ";
		list.add(conDayCalcRule.getWorkerClassify());
		list.add(conDayCalcRule.getWorkType12());
		if(conDayCalcRule.getPk()!=null){
			sql+=" and PK<>?";
			list.add(conDayCalcRule.getPk());
		}
		return this.findBySql(sql,list.toArray() );
	}	
}

