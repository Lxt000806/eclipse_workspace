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
 * ?????????????????????
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
     * ???????????????????????????
     */
    @RequestMapping(value = "/goList")
    @ResponseBody
    public ModelAndView goList(){
        
        //????????????????????????
        List<CourseType> courseTypeList = courseService.getAllCourseType();
        
        return new ModelAndView("admin/basic/course/course_list")
                .addObject("courseTypeList", courseTypeList);
    }
    
    /**
     * ???????????????JqGrid????????????
     */
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request, Course course){
        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        courseService.findCoursePageBySql(page, course);
        return new WebPage<Map<String, Object>>(page);
    }
    
    /**
     * ???????????????????????????
     */
    @RequestMapping("/goAddCourse")
    public ModelAndView goAddCourse(){

        //????????????????????????
        List<CourseType> courseTypeList = courseService.getAllCourseType();
        
        return new ModelAndView("admin/basic/course/course_add")
                .addObject("courseTypeList", courseTypeList);
    }

    /**
     * ???????????????????????????????????????
     */
    @RequestMapping("/goAddScoreDetailInfo")
    public ModelAndView goAddScoreDetailInfo(){
        
        return new ModelAndView("admin/basic/course/course_addScoreDetailInfo");
    }
    
    /**
     * ??????????????????????????????????????????
     */
    @RequestMapping("/doAddOrUpdateScoreDetailInfo")
    @ResponseBody
    public ResponseResult<Employee> doAddOrUpdateScoreDetailInfo(CourseScore courseScore,
                                                                 HttpSession session,
                                                                 HttpServletRequest req,
                                                                 HttpServletResponse resp){

        Employee emp = employeeService.get(Employee.class, courseScore.getEmpCode());
        ResponseResult<Employee> respResult = new ResponseResult<Employee>();
        
        //???????????????
        if(emp == null){
            respResult.setSuccess(false);
            respResult.setMessage("??????????????????"); 
            return respResult;
        }
        
        //????????????
        Department1 dep1 = dep1Service.get(Department1.class, emp.getDepartment1());
        if(dep1 != null){
            emp.setDepartment1Descr(dep1.getDesc2());
        }
        //????????????
        Department2 dep2 = dep2Service.get(Department2.class, emp.getDepartment2());
        if(dep2 != null){
            emp.setDepartment2Descr(dep2.getDesc2());     
        }
        //????????????
        Department3 dep3 = dep3Service.get(Department3.class, emp.getDepartment3());
        if(dep3 != null){
            emp.setDepartment3Descr(dep3.getDesc2());
        }

        //??????
        if(emp.getPosition() != null){
            Position position = positionService.get(Position.class, emp.getPosition());
            emp.setPositionName(position.getDesc2());
        }
        
        //????????????
        emp.setGender(emp.getGender().equals("F") ? "???" : "???");
        
        respResult.setSuccess(true);
        respResult.setData(emp);
        return respResult;
    }
    
    /**
     * ???????????????????????????????????????
     */
    @RequestMapping("/goUpdateScoreDetailInfo")
    public ModelAndView goUpdateScoreDetailInfo(Employee emp, CourseScore score){
        
        return new ModelAndView("admin/basic/course/course_updateScoreDetailInfo")
                    .addObject("emp", emp)
                    .addObject("score", score);
    }
    
    /**
     * ?????????????????????????????????
     */
    @RequestMapping("/goViewScoreDetailInfo")
    public ModelAndView goViewScoreDetailInfo(Employee emp, CourseScore score){
        
        return new ModelAndView("admin/basic/course/course_viewScoreDetailInfo")
                    .addObject("emp", emp)
                    .addObject("score", score);
    }
    
    /**
     * ????????????Excel????????????????????????
     */
    @RequestMapping("/goScoreDetailInfoLoadExcel")
    public ModelAndView goScoreDetailInfoLoadExcel(){
        
        return new ModelAndView("admin/basic/course/course_scoreDetailInfoLoadExcel");
    }
    
    /**
     * ???excel????????????
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
                // ??????????????? ????????????
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
                data.put("isinvaliddescr", "??????");
                if(StringUtils.isNotBlank(score.getEmpCode())){
                    data.put("empCode", score.getEmpCode());
                    Employee e = employeeService.get(Employee.class, score.getEmpCode());
                    if (e == null){
                        data.put("isinvalid","0");
                        data.put("isinvaliddescr", "???????????????");
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
                        data.put("ispassdescr", score.getIsPass().equals("1") ? "???" : "???");
                        data.put("ismakeupdescr", score.getIsMakeUp().equals("1") ? "???" : "???");
                        data.put("makeupscore", score.getMakeUpScore());
                        data.put("remark", score.getRemark());
                    }
                }else{
                    data.put("isinvalid","0");
                    data.put("isinvaliddescr", "?????????????????????????????????");
                }
                
                datas.add(data);
            }  
            map.put("success", true);
            map.put("returnInfo", "??????????????????");
            map.put("datas", datas);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            map.put("success", false);
            map.put("returnInfo", "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
            map.put("hasInvalid", true);
            return map;
        }
    }
    
    /**
     * ?????????????????????????????????
     * @param code
     * @return
     */
    @RequestMapping("/goUpdateCourse")
    public ModelAndView goUpdateCourse(String code){
        //????????????
        Course course = courseService.get(Course.class, code);
        
        //????????????????????????
        List<CourseType> courseTypeList = courseService.getAllCourseType();
                
        return new ModelAndView("admin/basic/course/course_update")
                     .addObject("course", course)
                     .addObject("courseTypeList", courseTypeList);
    }
    
    /**
     * ????????????????????????????????????
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
     * ????????????
     */
    @RequestMapping("/doAddCourse")
    public void doAddCourse(Course course,
                            HttpServletRequest req,
                            HttpServletResponse resp){
        
        String courseScoreListJson = req.getParameter("courseScoreList");
        List<CourseScoreForm> tempCourseScoreFormList = null;
        try {
            //????????????????????????json
            tempCourseScoreFormList = objectMapper.readValue(courseScoreListJson, new TypeReference<List<CourseScoreForm>>(){});
        } catch (JsonParseException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"????????????????????????");
            return;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"????????????????????????");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"????????????????????????");
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
                ServletUtils.outPrintFail(req, resp,"??????????????????????????????????????????" + result.getInfo());
                return;
            }
            ServletUtils.outPrintSuccess(req, resp,"????????????");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"???????????????????????????");
        }
    }
    
    /**
     * ???????????????????????????
     */
    @RequestMapping("/doUpdateCourse")
    public void doUpdateCourse(Course course,
                               HttpServletRequest req,
                               HttpServletResponse resp){
        
        String courseScoreListJson = req.getParameter("courseScoreList");
        List<CourseScoreForm> tempCourseScoreFormList = null;
        
        try {
            //????????????????????????json
            tempCourseScoreFormList = objectMapper.readValue(courseScoreListJson, new TypeReference<List<CourseScoreForm>>(){});
        } catch (JsonParseException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"????????????????????????");
            return;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"????????????????????????");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"????????????????????????");
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
                ServletUtils.outPrintFail(req, resp,"??????????????????????????????????????????" + result.getInfo());
                return;
            }
        ServletUtils.outPrintSuccess(req, resp,"????????????");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp,"???????????????????????????");
        }
    }
    
    /**
     * ???????????????????????????
     */
    @RequestMapping("/goViewCourse")
    public ModelAndView goViewCourse(String code){
        
        //????????????
        Course course = courseService.get(Course.class, code);
        
        //????????????????????????
        List<CourseType> courseTypeList = courseService.getAllCourseType();
        
        return new ModelAndView("admin/basic/course/course_view")
                     .addObject("course", course)
                     .addObject("courseTypeList", courseTypeList);
    }
    
    /**
     * ?????????Excel
     */
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, Course course) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        courseService.findCoursePageBySql(page, course);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "????????????_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
    /**
     * ???????????????????????????
     */
    @RequestMapping("/goQueryScore")
    public ModelAndView goModelAndView(){
        
        return new ModelAndView("admin/basic/course/course_queryScore");
    }
    
    /**
     * JqGrid????????????
     */
    @RequestMapping("/doQueryScore")
    @ResponseBody
    public WebPage<Map<String, Object>> doQueryScore(HttpServletRequest req, CourseScore score){
        Page<Map<String, Object>> page = this.newPageForJqGrid(req);
        courseService.findScorePageBySql(page, score);
        return new WebPage<Map<String, Object>>(page);
    }
    
    /**
     * ?????????????????????Excel
     */
    @RequestMapping("/doScoreDetailInfoExcel")
    public void doScoreDetailInfoExcel(HttpServletRequest req, HttpServletResponse resp, CourseScore score) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        courseService.findScorePageBySql(page, score);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "????????????_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
}
