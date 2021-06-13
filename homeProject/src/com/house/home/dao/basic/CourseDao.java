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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Course;
import com.house.home.entity.basic.CourseScore;

@Repository
public class CourseDao extends BaseDao{
    
    @Autowired
    private HibernateTemplate hibernateTemplate;

    /** 查询课程信息 */
    public Page<Map<String,Object>> findCoursePageBySql(Page<Map<String,Object>> page, Course course) {
        List<Object> list = new ArrayList<Object>();

        String sql = "select * from"
        		+ " (select a.Code, b.Descr CourseType, a.Nums, a.BeginDate, a.EndDate, a.Remark,"
                + " a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, convert(varchar(10), a.nums) + '期' + a.Descr as numsDescr"
                + " from tCourse a"
                + " left join tCourseType b on a.CourseType = b.Code"
                + " where 1 = 1";
        if ( StringUtils.isNotBlank(course.getCode())) {//根据课程编号查询
            sql += " and a.Code = ?";
            list.add(course.getCode());
        }
        if (StringUtils.isNotBlank(course.getDescr())) {//根据课程名称查询
            sql += " and a.Descr like ?";
            list.add("%" + course.getDescr() + "%");
        }
        if(course.getNums() != null){//根据课程期数查询
            sql += " and a.Nums = ?";
            list.add(course.getNums());
        }
        if (StringUtils.isNotBlank(course.getCourseTypeName())) {//根据课程类型查询
            sql += " and b.Descr = ?";
            list.add(course.getCourseTypeName());
        }
        if(course.getEarliestBeginDate() != null){//根据最早开课时间查询
            sql += " and a.beginDate >= ?";
            list.add(course.getEarliestBeginDate());
        }
        if(course.getLastestBeginDate() != null){//根据最晚开课时间查询
            sql += " and a.beginDate <= ?";
            list.add(course.getLastestBeginDate());
        }
        if (StringUtils.isBlank(course.getExpired()) || "F".equals(course.getExpired())) {//隐藏过期
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
     * 调用pkcgl存储过程，保存课程信息，并将#tCourseScore临时表的数据插入tCourseScore永久表
     * @param course 课程信息
     * @param umState
     * @param czy 操作员编号
     * @return
     * @throws Exception 
     */
    public Result doSaveCourse(Course course, String umState, String czy) throws SQLException{
        
        HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
        //获取同一事务的Session，临时表只对本连接可见
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Connection conn = session.connection();
        //插入课程信息和课程成绩信息
        CallableStatement call = conn.prepareCall("{Call pkcgl(?,?,?,?,?,?,?,?,?,?,?,?)}");
        Result result = new Result();
        try {
            call.setString(1, umState);
            call.setString(2, course.getCode());
            call.setString(3, course.getCourseType());
            call.setInt(4, course.getNums());
            call.setString(5, course.getDescr());
            call.setDate(6, new java.sql.Date(course.getBeginDate().getTime()));
            call.setDate(7, new java.sql.Date(course.getEndDate().getTime()));
            call.setString(8, course.getRemark());
            call.setString(9, course.getExpired());
            call.setString(10, czy);
            call.registerOutParameter(11, Types.INTEGER);
            call.registerOutParameter(12, Types.NVARCHAR);
            call.execute();
            result.setCode(String.valueOf(call.getInt(11)));
            result.setInfo(call.getString(12));
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }
    
    
    /**
     * 保存课程成绩在临时表
     * @throws Exception 
     */
    public void saveTempScore(List<CourseScore> tempCourseScoreList) throws SQLException{
        HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
        //getCurrentSession保证获取同一事务的Session，临时表只对本连接可见
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Connection conn = session.connection();
        
        try {
            //创建#tCourseScore临时表
            String createTempCourseScoreSql = "if object_id('#tCourseScore') is not null"
                    + " drop table #tCourseScore"
                    + " select * into #tCourseScore from tCourseScore where 1<>1";
            Statement stmt = conn.createStatement();
            stmt.execute(createTempCourseScoreSql);

            //插入课程成绩到临时表
            String insertScoreSql = "insert into #tCourseScore(CourseCode, EmpCode, Score, IsPass, IsMakeUp, MakeUpScore, Remark)"
                    + " values(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(insertScoreSql);
            for(CourseScore tempCourseScore : tempCourseScoreList){
                pstmt.setString(1, tempCourseScore.getCourseCode());
                pstmt.setString(2, tempCourseScore.getEmpCode());
                pstmt.setDouble(3, tempCourseScore.getScore());
                pstmt.setString(4, tempCourseScore.getIsPass());
                pstmt.setString(5, tempCourseScore.getIsMakeUp());
                if(tempCourseScore.getMakeUpScore() == null){//未补考则补考成绩为null
                    pstmt.setNull(6,Types.DOUBLE);
                }else{
                    pstmt.setDouble(6, tempCourseScore.getMakeUpScore());
                }
                pstmt.setString(7, tempCourseScore.getRemark());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 查询课程的成绩信息分页
     */
    public Page<Map<String,Object>> findScorePageBySql(Page<Map<String,Object>> page, CourseScore score){
        List<Object> list = new ArrayList<Object>();
        
        String sql = "select * from"
                + " (select b.Number number, b.NameChi nameChi,"
                + " case(b.Gender)"
                + " when 'F' then '女'"
                + " when 'M' then '男' end gender,"
                + " b.JoinDate joinDate, b.Phone phone,"
                + " c.Desc2 department1Descr, d.Desc2 department2Descr, e.Desc2 department3Descr,"
                + " f.Desc2 positionName,"
                + " a.Score score, a.IsPass isPass, a.IsMakeUp isMakeUp,"
                + " case(a.IsPass)"
                + " when '1' then '是'"
                + " when '0' then '否' end isPassDescr,"
                + " case(a.IsMakeUp)"
                + " when '1' then '是'"
                + " when '0' then '否' end isMakeUpDescr,"
                + " a.MakeUpScore makeUpScore, g.Remark remark,"
                + " convert(varchar(10), g.Nums) + '期' + g.Descr numsDescr,"
                + " a.LastUpdate lastUpdate"
                + " from tCourseScore a"
                + " inner join tEmployee b on a.EmpCode = b.Number" 
                + " left join tDepartment1 c on b.Department1 = c.Code"
                + " left join tDepartment2 d on b.Department2 = d.Code"
                + " left join tDepartment3 e on b.Department3 = e.Code"
                + " left join tPosition f on b.Position = f.Code"
                + " left join tCourse g on a.CourseCode = g.Code"
                + " where 1 = 1";
        if(StringUtils.isNotBlank(score.getCourseCode())){//根据课程编号查询
            sql += " and a.CourseCode = ?";
            list.add(score.getCourseCode());
        }
        if(StringUtils.isNotBlank(score.getCourseType())){//根据课程类型查询
            sql += " and g.CourseType = ?";
            list.add(score.getCourseType());
        }
        if(score.getEarliestBeginDate() != null){//根据最早开课时间查询
            sql += " and g.BeginDate >= ?";
            list.add(score.getEarliestBeginDate());
        }
        if(score.getLastestBeginDate() != null){//根据最晚开课时间查询
            sql += " and g.BeginDate <= ?";
            list.add(score.getLastestBeginDate());
        }
        if(StringUtil.isNotBlank(score.getIsPass())){//根据是否毕业查询
            sql += " and a.IsPass = ?";
            list.add(score.getIsPass());
        }
        if(StringUtil.isNotBlank(score.getIsMakeUp())){//根据是否补考查询
            sql += " and a.IsMakeUp = ?";
            list.add(score.getIsMakeUp());
        }
        if(score.getNums() != null){//根据课程期数查询
            sql += " and g.Nums = ?";
            list.add(score.getNums());
        }
        if(StringUtil.isNotBlank(score.getCourseDescr())){//根据课程名称查询
            sql += " and g.Descr like ?";
            list.add("%" + score.getCourseDescr() + "%");
        }
        if(StringUtil.isNotBlank(score.getEmpCode())){//根据员工编号查询
            sql += " and a.EmpCode = ?";
            list.add(score.getEmpCode());
        }
        if(StringUtil.isBlank(score.getExpired()) || "F".equals(score.getExpired())){//根据是否过期查询
           sql += " and a.Expired = 'F'";
        }
        if(StringUtil.isNotBlank(page.getPageOrderBy())){//排序
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        }else{
            sql += ") a order by a.lastUpdate desc";//默认按成绩信息的更新时间排序
        }
        return this.findPageBySql(page, sql, list.toArray());
    }
}
