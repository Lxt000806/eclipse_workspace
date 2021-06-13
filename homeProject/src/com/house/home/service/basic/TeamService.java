package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Team;

public interface TeamService extends BaseService{

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Team team);

    public List<Department1> getDeptZTree();

    public List<Map<String, Object>> getTeamEmps(String teamCode);
    
    public Result doAddTeam(Team team, List<Map<String, Object>> deptList, List<Employee> empList, String czy) throws Exception;
    
    public Result doUpdateTeam(Team team, List<Map<String, Object>> deptList, List<Employee> empList, String czy) throws Exception;
    
    public List<Map<String, Object>> getAlreadyAddDeptZtree(String teamCode);
    
    public List<Map<String, Object>> getTeamMember(String teamCode);
}
