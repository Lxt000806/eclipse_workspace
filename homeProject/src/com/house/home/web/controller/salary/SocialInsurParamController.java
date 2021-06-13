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
import com.house.home.entity.salary.SocialInsurParam;
import com.house.home.service.salary.SocialInsurParamService;

@Controller
@RequestMapping("/admin/socialInsurParam")
public class SocialInsurParamController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SocialInsurParamController.class);
	
	@Autowired
	private SocialInsurParamService socialInsurParamService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
        
        return new ModelAndView("admin/salary/socialInsurParam/socialInsurParam_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, SocialInsurParam socialInsurParam) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        socialInsurParamService.findPageBySql(page, socialInsurParam);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("跳转到新增社保公积金参数页面");
        
        return new ModelAndView("admin/salary/socialInsurParam/socialInsurParam_save");
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request,
            HttpServletResponse response, SocialInsurParam socialInsurParam) {

        try {
            
            List<SocialInsurParam> params =
                    socialInsurParamService.findSocialInsurParamsByDescr(socialInsurParam.getDescr());
            
            for (SocialInsurParam param : params) {
                if (param.getDescr().equals(socialInsurParam.getDescr())) {
                    
                    ServletUtils.outPrintFail(request, response, "新增社保公积金参数失败：此社保公积金参数名称已存在");
                    return;
                }
            }
                        
            socialInsurParam.setLastUpdate(new Date());
            socialInsurParam.setLastUpdatedBy(getUserContext(request).getCzybh());
            socialInsurParam.setExpired("F");
            socialInsurParam.setActionLog("ADD");
            
            socialInsurParamService.save(socialInsurParam);

            ServletUtils.outPrintSuccess(request, response, "新增社保公积金参数成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "新增社保公积金参数失败：程序异常");
        }

    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Integer pk) {
        logger.debug("跳转到编辑社保公积金参数页面");

        SocialInsurParam socialInsurParam = null;
        if (pk != null) {
            socialInsurParam = socialInsurParamService.get(SocialInsurParam.class, pk);
        }
        
        if (socialInsurParam == null) {
            socialInsurParam = new SocialInsurParam();
        }
        
        return new ModelAndView("admin/salary/socialInsurParam/socialInsurParam_update")
            .addObject("socialInsurParam", socialInsurParam);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, SocialInsurParam socialInsurParam) {

        try {

            if (socialInsurParam.getPk() == null) {
                ServletUtils.outPrintFail(request, response, "更新社保公积金参数失败：社保公积金参数ID为空");
                return;
            }
            
            List<SocialInsurParam> params =
                    socialInsurParamService.findSocialInsurParamsByDescr(socialInsurParam.getDescr());
            
            for (SocialInsurParam param : params) {
                if (param.getPk().intValue() != socialInsurParam.getPk().intValue()
                        && param.getDescr().equals(socialInsurParam.getDescr())) {
                    
                    ServletUtils.outPrintFail(request, response, "更新社保公积金参数失败：此社保公积金参数名称已存在");
                    return;
                }
            }
                        
            socialInsurParam.setLastUpdate(new Date());
            socialInsurParam.setLastUpdatedBy(getUserContext(request).getCzybh());
            socialInsurParam.setActionLog("EDIT");
            
            socialInsurParamService.update(socialInsurParam);

            ServletUtils.outPrintSuccess(request, response, "社保公积金参数更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "社保公积金参数更新失败：程序异常");
        }

    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, Integer pk) {
        logger.debug("跳转到查看社保公积金参数页面");

        SocialInsurParam socialInsurParam = null;
        if (pk != null) {
            socialInsurParam = socialInsurParamService.get(SocialInsurParam.class, pk);
        }
        
        if (socialInsurParam == null) {
            socialInsurParam = new SocialInsurParam();
        }
        
        return new ModelAndView("admin/salary/socialInsurParam/socialInsurParam_view")
            .addObject("socialInsurParam", socialInsurParam);
    }
    
    @RequestMapping("/goEmpList")
    public ModelAndView goEmpList(HttpServletRequest request, HttpServletResponse response, Integer pk) {
        logger.debug("跳转到查看社保公积金参数人员名单页面");
        
        return new ModelAndView("admin/salary/socialInsurParam/socialInsurParam_empList")
            .addObject("pk", pk);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response,
            SocialInsurParam socialInsurParam) {

            Page<Map<String, Object>> page = newPage(request);
            page.setPageSize(-1);
            
            socialInsurParamService.findPageBySql(page, socialInsurParam);
            getExcelList(request);
            
            ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                    "社保公积金参数_"+ DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
                    columnList, titleList, sumList);

    }
}
