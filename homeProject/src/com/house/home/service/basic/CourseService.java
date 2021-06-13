package com.house.home.service.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Course;
import com.house.home.entity.basic.CourseScore;
import com.house.home.entity.basic.CourseType;

public interface CourseService extends BaseService{

    public Page<Map<String, Object>> findCoursePageBySql(Page<Map<String, Object>> page, Course course);
    
    public List<CourseType> getAllCourseType();
    
    public Result addCourse(Course course, List<CourseScore> tempCourseScoreList, String umState, String czy)  throws SQLException;

    public Page<Map<String,Object>> findScorePageBySql(Page<Map<String,Object>> page, CourseScore score);
}
