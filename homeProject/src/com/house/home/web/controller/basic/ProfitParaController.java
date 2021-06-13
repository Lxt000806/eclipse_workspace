package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Course;
import com.house.home.entity.basic.ProfitPara;
import com.house.home.entity.basic.ResponseResult;
import com.house.home.service.basic.ProfitParaService;

@Controller
@RequestMapping(value = "admin/profitPara")
public class ProfitParaController extends BaseController{
    
    @Autowired
    private ProfitParaService profitParaService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(){
        
        return new ModelAndView("admin/basic/profitPara/profitPara_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest req, ProfitPara profitPara){
        Page<Map<String, Object>> page = this.newPageForJqGrid(req);
        profitParaService.findPageBySql(page, profitPara);
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(Integer pk){
        ProfitPara profitPara = profitParaService.get(ProfitPara.class, pk);
        
        return new ModelAndView("admin/basic/profitPara/profitPara_update")
                    .addObject("profitPara", profitPara);
    }
    
    @RequestMapping("/doUpdate")
    @ResponseBody
    public ResponseResult doUpdate(HttpServletRequest req, @Valid ProfitPara profitPara, Errors errors){
        ResponseResult respResult = new ResponseResult();
        
        if(errors.hasErrors()){
            respResult.setSuccess(false);
            respResult.setMessage("数据格式错误");
            return respResult;
        }
        profitPara.setLastUpdate(new Date());
        profitPara.setLastUpdatedBy(getUserContext(req).getCzybh());
        profitPara.setActionLog("EDIT");
        profitPara.setExpired("F");
        
        try{
            profitParaService.update(profitPara);
            respResult.setSuccess(true);
            respResult.setMessage("修改成功");
        }catch(Exception e){
            respResult.setSuccess(false);
            respResult.setMessage("发生错误，请重试");
        }
        
        return respResult;
    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(Integer pk){
        ProfitPara profitPara = profitParaService.get(ProfitPara.class, pk);
        
        return new ModelAndView("admin/basic/profitPara/profitPara_view")
                    .addObject("profitPara", profitPara);
    }
    
    /**
     * 导出到Excel
     */
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, ProfitPara profitPara) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        profitParaService.findPageBySql(page, profitPara);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "毛利参数_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
}
