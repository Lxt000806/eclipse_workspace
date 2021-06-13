package com.house.home.serviceImpl.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.TeamDao;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Team;
import com.house.home.service.basic.TeamService;

@Service
public class TeamServiceImpl extends BaseServiceImpl implements TeamService{

    @Autowired
    private TeamDao teamDao;
    
    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Team team) {
        return teamDao.findPageBySql(page, team);
    }
    
    @Override
    public List<Department1> getDeptZTree(){
        List<Department1> dept1List = teamDao.getNotExpiredDept1();
        for(Department1 dept1 : dept1List){
            dept1.setDeptLevel("1");
            String dept1Code = dept1.getCode();
            //获取一级部门下的所有的二级部门
            List<Department2> dept1Children = teamDao.getNotExpiredDept2ByDept1(dept1Code);
            dept1.setChildren(dept1Children);
            for(Department2 dept2 : dept1Children){
                dept2.setDeptLevel("2");
                String detp2Code = dept2.getCode();
                //获取二级部门下的所有三级部门
                List<Department3> dept2Children = teamDao.getNotExpiredDept3ByDept2(detp2Code);
                dept2.setChildren(dept2Children);
                for(Department3 dept3 : dept2Children){
                    dept3.setDeptLevel("3");
                }
            }
        }
        return dept1List;
    }
    
    @Override
    public List<Map<String, Object>> getAlreadyAddDeptZtree(String teamCode){
        return teamDao.getAlreadyAddDept(teamCode);
    }
    
    @Override
    public List<Map<String, Object>> getTeamEmps(String teamCode){
        return teamDao.getTeamEmps(teamCode);
    }
    
    @Override
    public List<Map<String, Object>> getTeamMember(String teamCode){
        return teamDao.getTeamMember(teamCode);
    }
    
    @Override
    public Result doAddTeam(Team team, List<Map<String, Object>> deptList, List<Employee> empList, String czy) throws Exception{
        
        //将团队部门添加到临时表
        teamDao.saveTempTeamDept(team, deptList);
        //将团队成员添加到临时表
        teamDao.saveTempTeamEmp(team, empList);
        //调用存储过程
        Result result = teamDao.doSaveOrUpdateTeam(team, czy, "A");
        return result;
    }

    @Override
    public Result doUpdateTeam(Team team, List<Map<String, Object>> deptList,
            List<Employee> empList, String czy) throws Exception {
      //将团队部门添加到临时表
        teamDao.saveTempTeamDept(team, deptList);
        //将团队成员添加到临时表
        teamDao.saveTempTeamEmp(team, empList);
        //调用存储过程
        Result result = teamDao.doSaveOrUpdateTeam(team, czy, "M");
        return result;
    }
}
