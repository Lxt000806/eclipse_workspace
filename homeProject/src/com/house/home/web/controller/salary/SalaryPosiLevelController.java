package com.house.home.web.controller.salary;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.salary.SalaryPosiLevel;
import com.house.home.service.salary.SalaryPosiLevelService;

@Controller
@RequestMapping("/admin/salaryPosiLevel")
public class SalaryPosiLevelController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SalaryPosiLevelController.class);
	
	@Autowired
	private SalaryPosiLevelService salaryPosiLevelService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
        
        return new ModelAndView("admin/salary/salaryPosiLevel/salaryPosiLevel_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, SalaryPosiLevel salaryPosiLevel) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        salaryPosiLevelService.findPageBySql(page, salaryPosiLevel);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Integer pk) {
        logger.debug("跳转到编辑薪酬岗位级别页面");

        SalaryPosiLevel salaryPosiLevel = null;
        if (pk != null) {
            salaryPosiLevel = salaryPosiLevelService.get(SalaryPosiLevel.class, pk);
        }
        
        if (salaryPosiLevel == null) {
            salaryPosiLevel = new SalaryPosiLevel();
        }
        
        return new ModelAndView("admin/salary/salaryPosiLevel/salaryPosiLevel_update")
            .addObject("salaryPosiLevel", salaryPosiLevel);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, SalaryPosiLevel salaryPosiLevel) {

        try {

            if (salaryPosiLevel.getPk() == null) {
                ServletUtils.outPrintFail(request, response, "更新薪酬岗位级别失败：岗位级别ID为空");
                return;
            }
            
            if (salaryPosiLevel.getPosiClass() == null) {
                ServletUtils.outPrintFail(request, response, "更新薪酬岗位级别失败：缺少岗位类别");
                return;
            }
            
            List<SalaryPosiLevel> levels =
                    salaryPosiLevelService.findLevelsBySalaryPosiClass(salaryPosiLevel.getPosiClass());
            
            for (SalaryPosiLevel level : levels) {
                if (level.getPk().intValue() != salaryPosiLevel.getPk().intValue()
                        && level.getDescr().equals(salaryPosiLevel.getDescr())) {
                    
                    ServletUtils.outPrintFail(request, response, "更新薪酬岗位级别失败：此岗位类别存在相同级别名称");
                    return;
                }
            }
            
            if (salaryPosiLevel.getDispSeq() == null) salaryPosiLevel.setDispSeq(0);
            
            salaryPosiLevel.setLastUpdate(new Date());
            salaryPosiLevel.setLastUpdatedBy(getUserContext(request).getCzybh());
            salaryPosiLevel.setActionLog("EDIT");
            
            salaryPosiLevelService.doUpdate(salaryPosiLevel);

            ServletUtils.outPrintSuccess(request, response, "薪酬岗位级别更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "薪酬岗位级别更新失败：程序异常");
        }

    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, Integer pk) {
        logger.debug("跳转到查看薪酬岗位级别页面");

        SalaryPosiLevel salaryPosiLevel = null;
        if (pk != null) {
            salaryPosiLevel = salaryPosiLevelService.get(SalaryPosiLevel.class, pk);
        }
        
        if (salaryPosiLevel == null) {
            salaryPosiLevel = new SalaryPosiLevel();
        }
        
        return new ModelAndView("admin/salary/salaryPosiLevel/salaryPosiLevel_view")
            .addObject("salaryPosiLevel", salaryPosiLevel);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response,
            SalaryPosiLevel salaryPosiLevel) {

            Page<Map<String, Object>> page = newPage(request);
            page.setPageSize(-1);
            
            salaryPosiLevelService.findPageBySql(page, salaryPosiLevel);
            getExcelList(request);
            
            ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                    "薪酬岗位级别_"+ DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
                    columnList, titleList, sumList);

    }
}
