package com.house.home.web.controller.basic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
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
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Course;
import com.house.home.entity.basic.CourseScore;
import com.house.home.entity.basic.CourseScoreForm;
import com.house.home.entity.basic.CourseType;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Position;
import com.house.home.entity.basic.ResponseResult;
import com.house.home.service.basic.CourseService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.PositionService;
import com.house.home.serviceImpl.basic.Department1ServiceImpl;
import com.house.home.serviceImpl.basic.Department2ServiceImpl;
import com.house.home.serviceImpl.basic.Department3ServiceImpl;

/**
 * 课程管理控制器
 * @author lenovo-l729
 *
 */
@Controller
@RequestMapping(value = "admin/course")
public class CourseController extends BaseController{
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private Department1ServiceImpl dep1Service;
    
    @Autowired
    private Department2ServiceImpl dep2Service;
    
    @Autowired
    private Department3ServiceImpl dep3Service;
    
    @Autowired
    private PositionService positionService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * 转发到课程列表界面
     */
    @RequestMapping(value = "/goList")
    @ResponseBody
    public ModelAndView goList(){
        
        //所有课程类型列表
        List<CourseType> courseTypeList = courseService.getAllCourseType();
        
        return new ModelAndView("admin/basic/course/course_list")
                .addObject("courseTypeList", courseTypeList);
    }
    
    /**
     * 查询课程的JqGrid表格数据
     */
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request, Course course){
        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        courseService.findCoursePageBySql(page, course);
        return new WebPage<Map<String, Object>>(page);
    }
    
    /**
     * 跳转到新增课程界面
     */
    @RequestMapping("/goAddCourse")
    public ModelAndView goAddCourse(){

        //所有课程类型列表
        List<CourseType> courseTypeList = courseService.getAllCourseType();
        
        return new ModelAndView("admin/basic/course/course_add")
                .addObject("courseTypeList", courseTypeList);
    }

    /**
     * 跳转到新增临时课程成绩页面
     */
    @RequestMapping("/goAddScoreDetailInfo")
    public ModelAndView goAddScoreDetailInfo(){
        
        return new ModelAndView("admin/basic/course/course_addScoreDetailInfo");
    }
    
    /**
     * 新增课程之前新增员工课程成绩
     */
    @RequestMapping("/doAddOrUpdateScoreDetailInfo")
    @ResponseBody
    public ResponseResult<Employee> doAddOrUpdateScoreDetailInfo(CourseScore courseScore,
                                                                 HttpSession session,
                                                                 HttpServletRequest req,
                                                                 HttpServletResponse resp){

        Employee emp = employeeService.get(Employee.class, courseScore.getEmpCode());
        ResponseResult<Employee> respResult = new ResponseResult<Employee>();
        
        //员工不存在
        if(emp == null){
            respResult.setSuccess(false);
            respResult.setMessage("员工不存在！"); 
            return respResult;
        }
        
        //一级部门
        Department1 dep1 = dep1Service.get(Department1.class, emp.getDepartment1());
        if(dep1 != null){
            emp.setDepartment1Descr(dep1.getDesc2());
        }
        //二级部门
        Department2 dep2 = dep2Service.get(Department2.class, emp.getDepartment2());
        if(dep2 != null){
            emp.setDepartment2Descr(dep2.getDesc2());     
        }
        //三级部门
        Department3 dep3 = dep3Service.get(Department3.class, emp.getDepartment3());
        if(dep3 != null){
            emp.setDepartment3Descr(dep3.getDesc2());
        }

        //职位
        if(emp.getPosition() != null){
            Position position = positionService.get(Position.class, emp.getPosition());
            emp.setPositionName(position.getDesc2());
        }
        
        //替换性别
        emp.setGender(emp.getGender().equals("F") ? "女" : "男");
        
        respResult.setSuccess(true);
        respResult.setData(emp);
        return respResult;
    }
    
    /**
     * 跳转到编辑成绩明细信息页面
     */
    @RequestMapping("/goUpdateScoreDetailInfo")
    public ModelAndView goUpdateScoreDetailInfo(Employee emp, CourseScore score){
        
        return new ModelAndView("admin/basic/course/course_updateScoreDetailInfo")
                    .addObject("emp", emp)
                    .addObject("score", score);
    }
    
    /**
     * 跳转到查看成绩明细信息
     */
    @RequestMapping("/goViewScoreDetailInfo")
    public ModelAndView goViewScoreDetailInfo(Employee emp, CourseScore score){
        
        return new ModelAndView("admin/basic/course/course_viewScoreDetailInfo")
                    .addObject("emp", emp)
                    .addObject("score", score);
    }
    
    /**
     * 跳转到从Excel导入成绩信息页面
     */
    @RequestMapping("/goScoreDetailInfoLoadExcel")
    public ModelAndView goScoreDetailInfoLoadExcel(){
        
        return new ModelAndView("admin/basic/course/course_scoreDetailInfoLoadExcel");
    }
    
    /**
     * 从excel加载数据
     */
    @RequestMapping("/loadExcel")
    @ResponseBody
    public Map<String, Object> loadExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String, Object> map=new HashMap<String, Object>();
        ExcelImportUtils<CourseScore> eUtils=new ExcelImportUtils<CourseScore>();
        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setHeaderEncoding("UTF-8");
        List fileList = upload.parseRequest(req);
        Iterator it = fileList.iterator();
        List<String> titleList=new ArrayList<String>();
        InputStream in=null;
        while (it.hasNext()){
            FileItem obit = (FileItem) it.next();
                // 如果是普通 表单参数
                String fieldName = obit.getFieldName();
                if ("file".equals(fieldName)){
                    in=obit.getInputStream();
                }
        }

        try {
            List<CourseScore> result=eUtils.importExcel(in,CourseScore.class,titleList);
            List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
            for(CourseScore score:result){
                if(StringUtils.isNotBlank(score.getError())){
                    map.put("success", false);
                    map.put("returnInfo", score.getError());
                    map.put("hasInvalid", true);
                    return map;
                }
                Map<String,Object> data=new HashMap<String, Object>();
                data.put("isinvalid","1");
                data.put("isinvaliddescr", "有效");
                if(StringUtils.isNotBlank(score.getEmpCode())){
                    data.put("empCode", score.getEmpCode());
                    Employee e = employeeService.get(Employee.class, score.getEmpCode());
                    if (e == null){
                        data.put("isinvalid","0");
                        data.put("isinvaliddescr", "员工不存在");
                    }else{
                        data.put("number", score.getEmpCode());
                        data.put("namechi", score.getNameChi());
                        data.put("gender", score.getGender());
                        data.put("department1descr", score.getDepartment1Descr());
                        data.put("department2descr", score.getDepartment2Descr());
                        data.put("department3descr", score.getDepartment3Descr());
                        data.put("positionname", score.getPositionName());
                        data.put("joindate", score.getJoinDate());
                        data.put("phone", score.getPhone());
                        data.put("score", score.getScore());
                        data.put("ispassdescr", score.getIsPass().equals("1") ? "是" : "否");
                        data.put("ismakeupdescr", score.getIsMakeUp().equals("1") ? "是" : "否");
                        data.put("makeupscore", score.getMakeUpScore());
                        data.put("remark", score.getRemark());
                    }
                }else{
                    data.put("isinvalid","0");
                    data.put("isinvaliddescr", "无效，员工编号不能为空");
                }
                
                datas.add(data);
            }  
            map.put("success", true);
            map.put("returnInfo", "数据加载完成");
            map.put("datas", datas);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            map.put("success", false);
            map.put("returnInfo", "导入失败，请检查是否缺少列：员工编号、姓名、性别、一级部门、二级部门、三级部门、职位、加入日期、电话、成绩、是否毕业、是否补考、补考成绩、备注");
            map.put("hasInvalid", true);
            return map;
        }
    }
    
    /**
     * 跳转到编辑课程信息页面
     * @param code
     * @return
     */
    @RequestMapping("/goUpdateCourse")
    public ModelAndView goUpdateCourse(String code){
        //课程信息
        Course course = courseService.get(Course.class, code);
        
        //所有课程类型列表
        List<CourseType> courseTypeList = courseService.getAllCourseType();
                
        return new ModelAndView("admin/basic/course/course_update")
                     .addObject("course", course)
                     .addObject("courseTypeList", courseTypeList);
    }
    
    /**
     * 查询指定课程成绩信息分页
     */
    @RequestMapping("/getCourseScore")
    @ResponseBody
    public WebPage<Map<String,Object>> getCourseScore(HttpServletRequest request, String code) throws Exception {
        
        Page<Map<String,Object>> page = this.newPageForJqGrid(request);
        CourseScore score = new CourseScore();
        score.setCourseCode(code);
        courseService.findScorePageBySql(page, score);
        return new WebPage<Map<String,Object>>(page);
    }
    
    /**
     * 新增课程
     */
    @RequestMapping("/doAddCourse")
    public void doAddCourse(Course course,
                            HttpServletRequest req,
                            HttpServletResponse resp){
        
        String courseScoreListJson = req.getParameter("courseScoreList");
        List<CourseScoreForm> tempCourseScoreFormList = null;
        try {
            //反序列化课程成绩json
            tempCourseScoreFormList = objectMapper.readValue(courseScoreListJson, new TypeReference<List<CourseScoreForm>>(){});
        } catch (JsonParseException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"保存失败，请重试");
            return;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"保存失败，请重试");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"保存失败，请重试");
            return;
        }
        List<CourseScore> tempCourseScoreList = new ArrayList<CourseScore>();
        for(CourseScoreForm scoreForm: tempCourseScoreFormList){
            CourseScore score = new CourseScore();
            score.setEmpCode(scoreForm.getNumber());
            score.setScore(scoreForm.getScore());
            score.setIsPass(scoreForm.getIspass());
            score.setIsMakeUp(scoreForm.getIsmakeup());
            score.setMakeUpScore(scoreForm.getMakeupscore());
            score.setRemark(scoreForm.getRemark());
            tempCourseScoreList.add(score);
        }
        Result result;
        try {
            result = courseService.addCourse(course, tempCourseScoreList, "A", getUserContext(req).getCzybh());
            if (!result.isSuccess()){
                ServletUtils.outPrintFail(req, resp,"保存失败，请重试！错误信息：" + result.getInfo());
                return;
            }
            ServletUtils.outPrintSuccess(req, resp,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"保存失败，请重试！");
        }
    }
    
    /**
     * 更新课程和成绩信息
     */
    @RequestMapping("/doUpdateCourse")
    public void doUpdateCourse(Course course,
                               HttpServletRequest req,
                               HttpServletResponse resp){
        
        String courseScoreListJson = req.getParameter("courseScoreList");
        List<CourseScoreForm> tempCourseScoreFormList = null;
        
        try {
            //反序列化课程成绩json
            tempCourseScoreFormList = objectMapper.readValue(courseScoreListJson, new TypeReference<List<CourseScoreForm>>(){});
        } catch (JsonParseException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"保存失败，请重试");
            return;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"保存失败，请重试");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"保存失败，请重试");
            return;
        }
        List<CourseScore> tempCourseScoreList = new ArrayList<CourseScore>();
        for(CourseScoreForm scoreForm: tempCourseScoreFormList){
            CourseScore score = new CourseScore();
            score.setEmpCode(scoreForm.getNumber());
            score.setScore(scoreForm.getScore());
            score.setIsPass(scoreForm.getIspass());
            score.setIsMakeUp(scoreForm.getIsmakeup());
            score.setMakeUpScore(scoreForm.getMakeupscore());
            score.setRemark(scoreForm.getRemark());
            tempCourseScoreList.add(score);
        }
        Result result;
        try {
            result = courseService.addCourse(course, tempCourseScoreList, "M", getUserContext(req).getCzybh());
            if (!result.isSuccess()){
                ServletUtils.outPrintFail(req, resp,"保存失败，请重试！错误信息：" + result.getInfo());
                return;
            }
        ServletUtils.outPrintSuccess(req, resp,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"保存失败，请重试！");
        }
    }
    
    /**
     * 跳转到查看课程页面
     */
    @RequestMapping("/goViewCourse")
    public ModelAndView goViewCourse(String code){
        
        //课程信息
        Course course = courseService.get(Course.class, code);
        
        //所有课程类型列表
        List<CourseType> courseTypeList = courseService.getAllCourseType();
        
        return new ModelAndView("admin/basic/course/course_view")
                     .addObject("course", course)
                     .addObject("courseTypeList", courseTypeList);
    }
    
    /**
     * 导出到Excel
     */
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, Course course) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        courseService.findCoursePageBySql(page, course);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "课程管理_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
    /**
     * 跳转到成绩查询页面
     */
    @RequestMapping("/goQueryScore")
    public ModelAndView goModelAndView(){
        
        return new ModelAndView("admin/basic/course/course_queryScore");
    }
    
    /**
     * JqGrid成绩查询
     */
    @RequestMapping("/doQueryScore")
    @ResponseBody
    public WebPage<Map<String, Object>> doQueryScore(HttpServletRequest req, CourseScore score){
        Page<Map<String, Object>> page = this.newPageForJqGrid(req);
        courseService.findScorePageBySql(page, score);
        return new WebPage<Map<String, Object>>(page);
    }
    
    /**
     * 导出成绩明细到Excel
     */
    @RequestMapping("/doScoreDetailInfoExcel")
    public void doScoreDetailInfoExcel(HttpServletRequest req, HttpServletResponse resp, CourseScore score) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        courseService.findScorePageBySql(page, score);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "成绩明细_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
}
