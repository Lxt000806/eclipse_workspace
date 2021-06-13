package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.JobDepart;

@Repository
public class JobDepartDao extends BaseDao{

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, JobDepart jobDepart){
        List<Object> list = new ArrayList<Object>();
        
        String sql = "select a.PK as pk, b.Descr as jobTypeDescr, c.Desc2 as projectDepartment2Descr,"
        		    + " d.Desc2 as department2, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog"
                    + " from tJobDepart a"
                    + " left outer join tJobType b on a.JobType = b.Code"
                    + " left outer join tDepartment2 c on a.ProjectDepartment2 = c.Code"
                    + " left outer join tDepartment2 d on a.Department2 = d.Code"
                    + " where 1 = 1";
        
        //根据主键查询
        if(jobDepart.getPk() != null){
            sql += " and cast(a.PK as nvarchar) like ?";
            list.add("%" + jobDepart.getPk() + "%");
        }
        
        //根据项目经理部门2查询
        if(StringUtils.isNotBlank(jobDepart.getProjectDepartment2())){
            sql += " and a.ProjectDepartment2 = ?";
            list.add(jobDepart.getProjectDepartment2());
        }
        
        //根据任务类型查询
        if(StringUtils.isNotBlank(jobDepart.getJobType())){
            sql += " and a.JobType = ?";
            list.add(jobDepart.getJobType());
        }
        
        //根据可选二级部门查询
        if(StringUtils.isNotBlank(jobDepart.getDepartment2())){
            sql += " and a.Department2 = ?";
            list.add(jobDepart.getDepartment2());
        }
        
        //根据是否过期查询
        if (StringUtils.isBlank(jobDepart.getExpired())
                || "F".equals(jobDepart.getExpired())) {
            sql += " and a.Expired='F'";
        }
        
        //排序
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += " order by a." + page.getPageOrderBy() + " "+ page.getPageOrder();
        } else {
            sql += " order by a.LastUpdate desc";
        }
        
        return this.findPageBySql(page, sql, list.toArray());
    }
}
