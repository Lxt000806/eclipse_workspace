package com.house.home.web.controller.finance;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
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
import com.house.home.entity.finance.EndProfPer;
import com.house.home.service.finance.EndProfPerService;

/** 
 * 材料毛利率调整控制器
 */
@Controller
@RequestMapping("/admin/endProfPer")
@Validated
public class EndProfPerController extends BaseController {
	
	@Autowired
	private EndProfPerService endProfPerService;
	
	/**
	 * 查询材料毛利率的JqGrid表格数据
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request, EndProfPer endProfPer){
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		endProfPerService.findPageBySql(page, endProfPer);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * EndProfPer列表
	 */
	@RequestMapping("/goList")
	public ModelAndView goList() {
		return new ModelAndView("admin/finance/endProfPer/endProfPer_list");
	}

	/**
	 * 跳转到新增材料毛利率页面
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd() {
		return new ModelAndView("admin/finance/endProfPer/endProfPer_add");
	}

	/**
	 * 跳转到修改材料毛利率页面
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(String code) {
		Map<String,Object> map = endProfPerService.getEndProfPerDetail(code);
		return new ModelAndView("admin/finance/endProfPer/endProfPer_update").addObject("endProfPer", map);
	}
	
	/**
	 * 跳转到复制材料毛利率页面
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(String code) {
		Map<String,Object> map = endProfPerService.getEndProfPerDetail(code);
		return new ModelAndView("admin/finance/endProfPer/endProfPer_copy").addObject("endProfPer", map);
	}
	
	/**
	 * 跳转到查看材料毛利率页面
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(String code) {
		Map<String,Object> map = endProfPerService.getEndProfPerDetail(code);
		return new ModelAndView("admin/finance/endProfPer/endProfPer_view").addObject("endProfPer", map);
	}

	/**
	 * 添加材料毛利率
	 */
	@RequestMapping("/doAdd")
	public void doAdd(HttpServletRequest req, HttpServletResponse resp, @Valid EndProfPer endProfPer, Errors errors) {
        if(errors.getErrorCount() > 0){
            ServletUtils.outPrintFail(req, resp, "信息填写不正确，请检查");
            return;
        }
	    try {
            endProfPer.setLastUpdate(new Date());
            endProfPer.setLastUpdatedBy(getUserContext(req).getCzybh());
            endProfPer.setExpired("F");
            endProfPer.setActionLog("ADD");
            this.endProfPerService.save(endProfPer);
            ServletUtils.outPrintSuccess(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("记录已存在，添加失败！");
            ServletUtils.outPrintFail(req, resp, "记录已存在，添加失败！");
        }
	}

	/**
	 * 修改材料毛利率
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest req, HttpServletResponse resp, @Valid EndProfPer endProfPer, Errors errors) {
	    if(errors.getErrorCount() > 0){
            ServletUtils.outPrintFail(req, resp, "信息填写不正确，请检查");
            return;
        }
	    try {
			if (!"T".equals(endProfPer.getExpired())) {
			    endProfPer.setExpired("F");
			}
			endProfPer.setLastUpdate(new Date());
			endProfPer.setActionLog("EDIT");
			endProfPer.setLastUpdatedBy(getUserContext(req).getCzybh());
			endProfPerService.update(endProfPer);
			ServletUtils.outPrintSuccess(req, resp);
		} catch (Exception e) {
		    e.printStackTrace();
			ServletUtils.outPrintFail(req, resp, "毛利率表内没有此客户");
		}
	}
	
	/**
	 * 导出材料毛利率到Excel
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, EndProfPer endProfPer) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		endProfPerService.findPageBySql(page, endProfPer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"材料毛利率_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}
	
	/**
     * 从excel加载数据
     */
    @RequestMapping("/loadExcel")
    @ResponseBody
    public Map<String, Object> loadExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String, Object> map=new HashMap<String, Object>();
        ExcelImportUtils<EndProfPer> eUtils=new ExcelImportUtils<EndProfPer>();
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
        
        //根据导入类型添加需要导入的毛利率类型
        titleList.add("客户编号");
        String[] importTypes = req.getParameterValues("importTypes");
        for(String importType : importTypes){
            if("ZC".equals(importType)){
                titleList.add("主材毛利率");
                titleList.add("服务性产品毛利率");
            }
            if("JC".equals(importType)){
                titleList.add("集成橱柜毛利率"); 
            }
            if("RZ".equals(importType)){
                titleList.add("软装毛利率");
                titleList.add("窗帘毛利率");
                titleList.add("家具毛利率");
            }
        }

        try {
            List<EndProfPer> result=eUtils.importExcel(in,EndProfPer.class,titleList);
            List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
            for(EndProfPer endProfPer:result){
                if(StringUtils.isNotBlank(endProfPer.getError())){
                    map.put("success", false);
                    map.put("returnInfo", endProfPer.getError());
                    map.put("hasInvalid", true);
                    return map;
                }
                Map<String,Object> data=new HashMap<String, Object>();
                data.put("isinvalid","1");
                data.put("isinvaliddescr", "有效");
                if(StringUtils.isNotBlank(endProfPer.getCustCode())){
                    data.put("custCode", endProfPer.getCustCode());
                    EndProfPer e = endProfPerService.get(EndProfPer.class, endProfPer.getCustCode());
                    if (e == null){
                        data.put("isinvalid","0");
                        data.put("isinvaliddescr", "毛利率表内不存在");
                    }   
                }else{
                    data.put("isinvalid","0");
                    data.put("isinvaliddescr", "无效，客户编号不能为空");
                }
                
                DecimalFormat df = new DecimalFormat("0.0000");
                for(String importType : importTypes){
                    if("ZC".equals(importType)){
                        data.put("mainProPer", df.format(endProfPer.getMainProPer()));
                        data.put("servProper", df.format(endProfPer.getServProper()));
                    }
                    if("JC".equals(importType)){
                        data.put("intProPer", df.format(endProfPer.getIntProPer())); 
                    }
                    if("RZ".equals(importType)){
                        data.put("softProPer", df.format(endProfPer.getSoftProPer()));
                        data.put("curtainProPer", df.format(endProfPer.getCurtainProPer()));
                        data.put("furnitureProPer", df.format(endProfPer.getFurnitureProPer()));
                    }
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
            map.put("returnInfo", "导入失败，请检查是否缺少列：客户编号、主材毛利率、服务性产品毛利率、集成橱柜毛利率、软装毛利率、窗帘毛利率、家具毛利率");
            map.put("hasInvalid", true);
            return map;
        }
    }
    
    /**
     * 批量新增，调存储过程
     */
    @RequestMapping("/doSaveBatch")
    public void doSaveBatch(HttpServletRequest request,HttpServletResponse response){
        try {
            String detailJson = request.getParameter("detailJson");
            String[] importTypes = request.getParameterValues("importTypes");
            String importTypesStr = "";
            for(int i = 0; i < importTypes.length; i ++){
                if(i != 0){
                    importTypesStr += ",";
                }
                importTypesStr += importTypes[i];
            }
            if(StringUtils.isBlank(detailJson)){
                ServletUtils.outPrintFail(request, response, "无材料毛利率信息，请先导入材料毛利率信息");
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            List<EndProfPer> endProfPerList = objectMapper.readValue(detailJson, new TypeReference<List<EndProfPer>>(){});
            for(EndProfPer endProfPer : endProfPerList){
                endProfPer.setDetailJson(detailJson);
                endProfPer.setLastUpdate(new Date());
                endProfPer.setLastUpdatedBy(getUserContext(request).getCzybh());
            }
            List<Result> resultList = this.endProfPerService.doSaveBatch(endProfPerList, importTypesStr);
            for(Result result : resultList){
                if (!result.isSuccess()){
                    ServletUtils.outPrintFail(request, response,result.getInfo());
                    return;
                }
            }
            ServletUtils.outPrintSuccess(request, response,"保存成功");
        }catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "批量导入失败失败");
        }
    }
	
	/**
     * 跳转到从Excel导入的页面
     */
    @RequestMapping("/goLoad")
    public ModelAndView goBatchAdd(){
        return new ModelAndView("admin/finance/endProfPer/endProfPer_importExcel");
    }
}
