package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.JobTypeConfItemType;

@SuppressWarnings("serial")
@Repository
public class JobTypeConfItemTypeDao extends BaseDao {

	/**
	 * JobTypeConfItemType分页信息
	 * 
	 * @param page
	 * @param jobTypeConfItemType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, JobTypeConfItemType jobTypeConfItemType) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.ConfItemType,b.Descr JobTypeDescr,c.Descr ConfItemTypeDescr,a.JobType,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
		" from tJobTypeConfItemType a " +
		" left join tJobType b on a.JobType=b.Code " +
		" left join tConfItemType c on a.ConfItemType=c.Code " +
		" where 1=1 ";

    	if (jobTypeConfItemType.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(jobTypeConfItemType.getPk());
		}
    	if (StringUtils.isNotBlank(jobTypeConfItemType.getConfItemType())) {
			sql += " and a.ConfItemType=? ";
			list.add(jobTypeConfItemType.getConfItemType());
		}
    	if (StringUtils.isNotBlank(jobTypeConfItemType.getJobType())) {
			sql += " and a.JobType=? ";
			list.add(jobTypeConfItemType.getJobType());
		}
    	if (jobTypeConfItemType.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(jobTypeConfItemType.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(jobTypeConfItemType.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(jobTypeConfItemType.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(jobTypeConfItemType.getExpired()) || "F".equals(jobTypeConfItemType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(jobTypeConfItemType.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(jobTypeConfItemType.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> getByJobTypeAndConfItemType(JobTypeConfItemType jobTypeConfItemType){
		List<Object> list = new ArrayList<Object>();

		String sql = "select 1 from tJobTypeConfItemType a where 1 = 1";

    	if (StringUtils.isNotBlank(jobTypeConfItemType.getConfItemType())) {
			sql += " and a.ConfItemType=? ";
			list.add(jobTypeConfItemType.getConfItemType());
		}
    	if (StringUtils.isNotBlank(jobTypeConfItemType.getJobType())) {
			sql += " and a.JobType=? ";
			list.add(jobTypeConfItemType.getJobType());
		}
    	if (jobTypeConfItemType.getPk() != null) {
			sql += " and a.PK!=? ";
			list.add(jobTypeConfItemType.getPk());
		}

		return this.findBySql(sql, list.toArray());
	}

}

