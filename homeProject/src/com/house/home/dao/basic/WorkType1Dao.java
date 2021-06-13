package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkType1;

@SuppressWarnings("serial")
@Repository
public class WorkType1Dao extends BaseDao {

	/**
	 * WorkType1分页信息
	 * 
	 * @param page
	 * @param workType1
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType1 workType1) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tWorkType1 a where 1=1 ";

    	if (StringUtils.isNotBlank(workType1.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+workType1.getCode()+"%");
		}
    	if (StringUtils.isNotBlank(workType1.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+workType1.getDescr()+"%");
		}
    	if (workType1.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(workType1.getDispSeq());
		}
    	if (workType1.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(workType1.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(workType1.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(workType1.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(workType1.getExpired()) || "F".equals(workType1.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(workType1.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(workType1.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<WorkType1> findByNoExpired() {
		String hql = "from WorkType1 a where a.expired='F' order by a.dispSeq";
		return this.find(hql);
	}
	/**三级联动选择出code和descr*/
	
	public List<Map<String,Object>> findWorkType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tWorkType1 a where a.expired='F'";
		//排序
		sql += " order by a.dispSeq";
		//sql携带着第一次选择的行号,list携带查找出来的数据
		return this.findBySql(sql, list.toArray());
	}
	
    

}

