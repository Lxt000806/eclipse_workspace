package com.house.home.serviceImpl.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CourseDao;
import com.house.home.dao.basic.CourseTypeDao;
import com.house.home.entity.basic.Course;
import com.house.home.entity.basic.CourseScore;
import com.house.home.entity.basic.CourseType;
import com.house.home.service.basic.CourseService;

@Service(value = "courseManageServiceImpl")
public class CourseServiceImpl extends BaseServiceImpl implements CourseService{
    
    @Autowired
    private CourseDao courseDao;
    
    @Autowired
    private CourseTypeDao courseTypeDao;

    @Override
    public Page<Map<String, Object>> findCoursePageBySql(Page<Map<String, Object>> page, Course course) {
        Page<Map<String, Object>> pageData = courseDao.findCoursePageBySql(page, course);
        return pageData;
    }
    
    @Override
    public List<CourseType> getAllCourseType(){
        List<CourseType> courseTypeList = courseTypeDao.loadAll(CourseType.class);
        return courseTypeList;
    }
    
    @Override
    public Result addCourse(Course course, List<CourseScore> tempCourseScoreList, String umState, String czy) throws SQLException{
        for(CourseScore tempCourseScore : tempCourseScoreList){
            tempCourseScore.setCourseCode("-1");
            tempCourseScore.setLastUpdatedBy(czy);
        }
        Result result = null;
        //保存课程成绩信息到#tCourseScore临时表
        courseDao.saveTempScore(tempCourseScoreList);
        //保存课程信息，并将临时表中的课程成绩信息到永久表
        result = courseDao.doSaveCourse(course, umState, czy);
        return result;
    }
    
    /**
     * 查询课程成绩信息分页
     */
    @Override
    public Page<Map<String,Object>> findScorePageBySql(Page<Map<String,Object>> page, CourseScore score){
        return courseDao.findScorePageBySql(page, score);
    }
}
