package com.house.home.web.controller.basic;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Team;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.TeamService;

@Controller
@RequestMapping("admin/team")
public class TeamController extends BaseController{
    
    @Autowired
    private TeamService teamService;
    
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/goList")
    public ModelAndView goList(){
        
        return new ModelAndView("admin/basic/team/team_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest req,
                                                 HttpServletResponse resp,
                                                 Team team) 
                                                 throws Exception {
        
        Page<Map<String,Object>> page = this.newPageForJqGrid(req);
        teamService.findPageBySql(page, team);
        return new WebPage<Map<String,Object>>(page);
    }
    
    /**
     * 导出到Excel
     */
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, Team team) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        teamService.findPageBySql(page, team);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "团队信息_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
    /**
     * 跳转到新增团队信息界面
     */
    @RequestMapping("/goSave")
    public ModelAndView goSave(String code){
        
        List<Department1> deptZTree = teamService.getDeptZTree();
        String deptZTreeJson = null;
        try {
            deptZTreeJson = new ObjectMapper().writeValueAsString(deptZTree);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new ModelAndView("admin/basic/team/team_save")
                    .addObject("deptZTreeJson", deptZTreeJson);
    }
    
    /**
     * 跳转到编辑团队信息界面
     */
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(String code){
        Team team = teamService.get(Team.class, code);
        List<Department1> deptZTree = teamService.getDeptZTree();
        List<Map<String, Object>> alreadyAddDeptList = teamService.getAlreadyAddDeptZtree(code);
        String deptZTreeJson = null;
        String alreadyAddDeptListJson = null;
        try {
            deptZTreeJson = new ObjectMapper().writeValueAsString(deptZTree);
            alreadyAddDeptListJson = new ObjectMapper().writeValueAsString(alreadyAddDeptList);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new ModelAndView("admin/basic/team/team_update")
                    .addObject("team", team)
                    .addObject("deptZTreeJson", deptZTreeJson)
                    .addObject("alreadyAddDeptListJson", alreadyAddDeptListJson);
    }
    
    /**
     * 获取团队成员jqGrid信息
     */
    @RequestMapping("/getTeamEmps")
    @ResponseBody
    public List<Map<String, Object>> getTeamEmps(String teamCode){
        if(teamCode == null || teamCode.trim().length() == 0){
            return null;
        }
        return teamService.getTeamEmps(teamCode);
    }
    
    /**
     * 获取团队人员jqGrid信息
     */
    @RequestMapping("/getTeamMember")
    @ResponseBody
    public List<Map<String, Object>> getTeamMember(String teamCode){
        return teamService.getTeamMember(teamCode);
    }
    
    /**
     * 跳转到新增团队成员界面
     */
    @RequestMapping("/goAddTeamEmp")
    public ModelAndView goAddTeamEmp(){
        return new ModelAndView("admin/basic/team/team_addTeamEmp");
    }
    
    /**
     * 获取待添加的团队成员的详细信息
     */
    @RequestMapping("/doAddTeamEmp")
    @ResponseBody
    public Employee doAddTeamEmp(String number){
        return employeeService.get(Employee.class, number);
    }
    
    /**
     * 跳转到编辑团队成员界面
     */
    @RequestMapping("/goUpdateTeamEmp")
    public ModelAndView goUpdateTeamEmp(String number){
        
        Employee emp = employeeService.get(Employee.class, number);
        
        return new ModelAndView("admin/basic/team/team_updateTeamEmp")
                .addObject("emp", emp);
    }
    
    /**
     * 新增团队
     */
    @RequestMapping("/doAddTeam")
    @ResponseBody
    public Result doAddTeam(Team team, String deptListJson, String teamEmpListJson, HttpServletRequest req){
        List<Map<String, Object>> deptList = null;
        List<Employee> teamEmpList = null;
        Result result = null;
        if(teamService.get(Team.class, team.getCode()) != null){
            result = new Result("2", "编号已存在，请重新输入");
            return result;
        }
        try {
            deptList = new ObjectMapper().readValue(deptListJson, new TypeReference<List<Map<String, Object>>>(){});
            teamEmpList = new ObjectMapper().readValue(teamEmpListJson, new TypeReference<List<Employee>>(){});
            String czy = getUserContext(req).getCzybh();
            team.setExpired("F");
            result = teamService.doAddTeam(team, deptList, teamEmpList, czy);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 跳转到查看团队页面
     */
    @RequestMapping("/goView")
    public ModelAndView goView(String code){
        Team team = teamService.get(Team.class, code);
        List<Department1> deptZTree = teamService.getDeptZTree();
        List<Map<String, Object>> alreadyAddDeptList = teamService.getAlreadyAddDeptZtree(code);
        String deptZTreeJson = null;
        String alreadyAddDeptListJson = null;
        try {
            deptZTreeJson = new ObjectMapper().writeValueAsString(deptZTree);
            alreadyAddDeptListJson = new ObjectMapper().writeValueAsString(alreadyAddDeptList);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new ModelAndView("admin/basic/team/team_view")
                    .addObject("team", team)
                    .addObject("deptZTreeJson", deptZTreeJson)
                    .addObject("alreadyAddDeptListJson", alreadyAddDeptListJson);
    }
    
    /**
     * 编辑团队
     */
    @RequestMapping("/doUpdateTeam")
    @ResponseBody
    public Result doUpdateTeam(Team team, String deptListJson, String teamEmpListJson, HttpServletRequest req){
        List<Map<String, Object>> deptList = null;
        List<Employee> teamEmpList = null;
        Result result = null;
        try {
            deptList = new ObjectMapper().readValue(deptListJson, new TypeReference<List<Map<String, Object>>>(){});
            teamEmpList = new ObjectMapper().readValue(teamEmpListJson, new TypeReference<List<Employee>>(){});
            String czy = getUserContext(req).getCzybh();
            result = teamService.doUpdateTeam(team, deptList, teamEmpList, czy);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
