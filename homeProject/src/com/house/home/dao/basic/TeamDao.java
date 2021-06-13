package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Team;

@Repository
public class TeamDao extends BaseDao{

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
                                                  Team team) {
        
        List<Object> list = new ArrayList<Object>();
        
        String sql = "select * from (select a.Code,a.Remark,a.Desc1,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.NOTE IsCalcPerfDescr"
                    + " from tTeam a"
        		    + " left join tXTDM b on a.IsCalcPerf=b.CBM and b.ID='YESNO' "
                    + " where 1 = 1";
        
        if(StringUtil.isNotBlank(team.getCode())){//根据编号查询
            sql += " and a.Code like ?";
            list.add("%" + team.getCode() + "%");
        }
        if(StringUtil.isNotBlank(team.getDesc1())){//根据团队名称查询
            sql += " and a.Desc1 like ?";
            list.add("%" + team.getDesc1() + "%");
        }
        if(StringUtils.isBlank(team.getExpired()) || "F".equals(team.getExpired())){//隐藏过期
            sql += " and a.Expired = 'F'";
        }
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
            sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
        }else{
            sql += ")a order by a.LastUpdate desc";
        }
        
        return this.findPageBySql(page, sql, list.toArray());
    }
    
    /**
     * 查找未过期的一级部门
     * @return
     */
    public List<Department1> getNotExpiredDept1(){
        
        String hql = "from Department1 d1"
                    + " where d1.expired = 'F'"
                    + " order by d1.desc1";

        return find(hql);
    }
    
    /**
     * 查找指定的一级部门下的所有未过期的二级部门
     * @param dept1Code
     * @return
     */
    public List<Department2> getNotExpiredDept2ByDept1(String dept1Code){
        
        String sql = "from Department2 d2"
                    + " where d2.department1 = ? and d2.expired = 'F'"
                    + " order by d2.desc1";
        
        return find(sql, dept1Code);
    }
    
    /**
     * 查找指定的二级部门下的所有未过期的三级部门
     * @param dept2Code
     * @return
     */
    public List<Department3> getNotExpiredDept3ByDept2(String dept2Code){
        
        String sql = "from Department3 d3"
                    + " where d3.department2 = ? and d3.expired = 'F'"
                    + " order by d3.desc1";
        
        return find(sql, dept2Code);
    }
    
    public List<Map<String, Object>> getAlreadyAddDept(String teamCode){
        
        String sql = "select a.Department1, a.Department2, a.Department3"
                  + " from tTeamDept a"
                  + " where a.TeamCode = ?";
        
        return findBySql(sql, teamCode);
    }
    
    public List<Map<String,Object>> getTeamEmps(String teamCode){
        
        String sql = "select a.PK pk, a.EMNum number, b.NameChi namechi,"
        		    + " c.Desc2 position, e.Desc2 department1, f.Desc2 department2, g.Desc2 department3,"
        		    + " b.LastUpdate lastupdate, b.LastUpdatedBy lastupdatedby, b.Expired expired, b.ActionLog actionlog"
                    + " from tTeamEmp a"
                    + " left join tEmployee b on a.EMNum = b.Number"
                    + " left join tPosition c on b.Position = c.Code"
                    + " left join tDepartment1 e on b.Department1 = e.Code"
                    + " left join tDepartment2 f on b.Department2 = f.Code"
                    + " left join tDepartment3 g on b.Department3 = g.Code"
                    + " where a.TeamCode = ?";
        
        return findListBySql(sql, teamCode);
    }
    
    public List<Map<String, Object>> getTeamMember(String teamCode){
        
        String sql = "select a.Number number, a.NameChi nameChi, c.Desc2 positionDescr,"
                    + " d.Desc2 dept1Descr, e.Desc2 dept2Descr, f.Desc2 dept3Descr,"
                    + " a.Department1 department1, a.Department2 department2, a.Department3 department3"
                    + " from tEmployee a"
                    + "   left outer join tTeamDept b"
                    + "     on (a.Department1 = b.Department1)"
                    + "     and ((a.Department2 = b.Department2) or (b.Department2 = '') or (b.Department2 is null))"
                    + "     and ((a.Department3 = b.Department3) or (b.Department3 = '') or (b.Department3 is null))"
                    + "   left outer join tPosition c on a.Position = c.Code"
                    + "   left outer join tDepartment1 d on a.Department1 = d.Code"
                    + "   left outer join tDepartment2 e on a.Department2 = e.Code"
                    + "   left outer join tDepartment3 f on a.Department3 = f.Code"
                    + " where b.TeamCode = ? and a.Expired = 'F'"
                    + " union"
                    + " select a.EMNum number, b.NameChi nameChi, c.Desc2 positionDescr,"
                    + " d.Desc2 dept1Descr, e.Desc2 dept2Descr, f.Desc2 dept3Descr,"
                    + " b.Department1 department1, b.Department2 department2, b.Department3 department3"
                    + " from tTeamEmp a"
                    + "   left outer join tEmployee b on a.EMNum = b.Number"
                    + "   left outer join tPosition c on b.Position = c.Code"
                    + "   left outer join tDepartment1 d on b.Department1 = d.Code"
                    + "   left outer join tDepartment2 e on b.Department2 = e.Code"
                    + "   left outer join tDepartment3 f on b.Department3 = f.Code"
                    + " where a.TeamCode = ?"
                    + " order by Department1, Department2, Department3 desc";

        return findListBySql(sql, teamCode, teamCode);
    }
    
    //添加团队部门到临时表
    public void saveTempTeamDept(Team team, List<Map<String, Object>> deptList) throws Exception{
        HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
        Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        Connection conn = session.connection();
        
        try {
            //创建#tTeamDept临时表
            String createTempTeamDeptSql = "if object_id('tempdb..#tTeamDept') is not null"
                    + " drop table #tTeamDept"
                    + " select * into #tTeamDept from tTeamDept where 1<>1";
            Statement stmt = conn.createStatement();
            stmt.execute(createTempTeamDeptSql);
        
            //插入团队部门到临时表sql
            String insertDeptSql1 = "insert into #tTeamDept(TeamCode, Department1, Department2, Department3) values(?, ?, ?, ?)";
            String insertDeptSql2 = "insert into #tTeamDept(TeamCode, Department1, Department2, Department3) values(?, ?, ?, ?)";
            String insertDeptSql3 = "insert into #tTeamDept(TeamCode, Department1, Department2, Department3)"
                                + " values(?,"
                                + "       (select Department1 from tDepartment2 where code = ?),"
                                + "        ?, ?)";
            PreparedStatement pstmt1 = conn.prepareStatement(insertDeptSql1);
            PreparedStatement pstmt2 = conn.prepareStatement(insertDeptSql2);
            PreparedStatement pstmt3 = conn.prepareStatement(insertDeptSql3);
            //遍历需要插入的部门，部门是最底层的部门，根据部门级别拼接sql
            for(Map<String, Object> dept : deptList){
                String deptLevel = (String)dept.get("deptLevel");
                //一级部门
                if(deptLevel.equals("1")){
                    pstmt1.setString(1, team.getCode());
                    pstmt1.setString(2, (String)dept.get("code"));
                    pstmt1.setNull(3, Types.NVARCHAR);
                    pstmt1.setNull(4, Types.NVARCHAR);
                    pstmt1.addBatch();
                }else if(deptLevel.equals("2")){//二级部门
                    pstmt2.setString(1, team.getCode());
                    pstmt2.setString(2, (String)dept.get("department1"));
                    pstmt2.setString(3, (String)dept.get("code"));
                    pstmt2.setNull(4, Types.NVARCHAR);
                    pstmt2.addBatch();
                }else if(deptLevel.equals("3")){//三级部门
                    pstmt3.setString(1, team.getCode());
                    pstmt3.setString(2, (String)dept.get("department2"));
                    pstmt3.setString(3, (String)dept.get("department2"));
                    pstmt3.setString(4, (String)dept.get("code"));
                    pstmt3.addBatch();
                }else{
                    return;
                }
            }
            pstmt1.executeBatch();
            pstmt2.executeBatch();
            pstmt3.executeBatch();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
    //添加团队成员到临时表
    public void saveTempTeamEmp(Team team, List<Employee> empList) throws Exception{
        HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
        Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        Connection conn = session.connection();
        
        try {
            //创建#tTeamEmp临时表
            String createTempTeamEmpSql = "if object_id('tempdb..#tTeamEmp') is not null"
                    + " drop table #tTeamEmp"
                    + " select * into #tTeamEmp from tTeamEmp where 1<>1";
            Statement stmt = conn.createStatement();
            stmt.execute(createTempTeamEmpSql);
            
            //插入团队成员到临时表
            String insertEmpSql = "insert into #tTeamEmp(TeamCode, EMNum)"
                    + " values(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(insertEmpSql);
            for(Employee emp: empList){
                pstmt.setString(1, team.getCode());
                pstmt.setString(2, emp.getNumber());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
    public Result doSaveOrUpdateTeam(Team team, String czy, String operation) throws SQLException{
        HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
        //获取同一事务的Session，临时表只对本连接可见
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Connection conn = session.connection();
        //插入课程信息和课程成绩信息
        CallableStatement call = conn.prepareCall("{Call pTdXxGl(?,?,?,?,?,?,?,?,?)}");
        Result result = new Result();
        try {
            call.setString(1, operation);
            call.setString(2, team.getCode());
            call.setString(3, team.getDesc1());
            call.setString(4, team.getRemark());
            call.setString(5, czy);
            call.setString(6, team.getExpired());
            call.registerOutParameter(7, Types.INTEGER);
            call.registerOutParameter(8, Types.NVARCHAR);
            call.setString(9, team.getIsCalcPerf());
            call.execute();
            result.setCode(String.valueOf(call.getInt(7)));
            result.setInfo(call.getString(8));
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }
}
