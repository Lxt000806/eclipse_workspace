package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.ItemTransform;
import com.house.home.service.basic.ItemTransformService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/itemTransform")
public class ItemTransformController extends BaseController {
    
    @Autowired
    private ItemTransformService itemTransformService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) {
        
        return new ModelAndView("admin/basic/itemTransform/itemTransform_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request,
            HttpServletResponse response, ItemTransform itemTransform) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        itemTransformService.findPageBySql(page, itemTransform);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/detail/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goDetailJqGrid(HttpServletRequest request,
            HttpServletResponse response, ItemTransform itemTransform) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        
        if (StringUtils.isNotBlank(itemTransform.getNo())) {            
            itemTransformService.findDetailPageBySql(page, itemTransform);
        }
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request,
            HttpServletResponse response) {
        
        ItemTransform itemTransform = new ItemTransform();
        itemTransform.setM_umState("A");
        itemTransform.setNo(itemTransformService.getSeqNo("tItemTransform"));
        
        return new ModelAndView("admin/basic/itemTransform/itemTransform_update")
                .addObject("itemTransform", itemTransform);
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request,
            HttpServletResponse response, ItemTransform itemTransform) {
        try {
            
            itemTransform.setActionLog("ADD");
            itemTransform.setLastUpdate(new Date());
            itemTransform.setLastUpdatedBy(getUserContext(request).getCzybh());
            itemTransform.setExpired("F");

            itemTransformService.doSave(itemTransform);
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, e.getMessage());
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        ItemTransform itemTransform = itemTransformService.get(ItemTransform.class, no);
        itemTransform.setM_umState("M");
        
        return new ModelAndView("admin/basic/itemTransform/itemTransform_update")
                .addObject("itemTransform", itemTransform);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, ItemTransform itemTransform) {
        try {
            
            itemTransform.setActionLog("EDIT");
            itemTransform.setLastUpdate(new Date());
            itemTransform.setLastUpdatedBy(getUserContext(request).getCzybh());

            itemTransformService.doUpdate(itemTransform);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, e.getMessage());
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/detail/goAdd")
    public ModelAndView goAddDetail(HttpServletRequest request,
            HttpServletResponse response, @RequestParam Map<String, String> itemTransformDetail) {
        
        itemTransformDetail.put("transformper", "0.0");
        
        return new ModelAndView("admin/basic/itemTransform/tab_detail_update")
                .addObject("itemTransformDetail", itemTransformDetail);
    }
    
    @RequestMapping("/detail/goUpdate")
    public ModelAndView goUpdateDetail(HttpServletRequest request,
            HttpServletResponse response, @RequestParam Map<String, String> itemTransformDetail) {
        
        return new ModelAndView("admin/basic/itemTransform/tab_detail_update")
                .addObject("itemTransformDetail", itemTransformDetail);
    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        ItemTransform itemTransform = itemTransformService.get(ItemTransform.class, no);
        itemTransform.setM_umState("V");
        
        return new ModelAndView("admin/basic/itemTransform/itemTransform_update")
                .addObject("itemTransform", itemTransform);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response, ItemTransform itemTransform) {

            Page<Map<String, Object>> page = newPage(request);
            page.setPageSize(-1);
            
            itemTransformService.findPageBySql(page, itemTransform);
            getExcelList(request);
            
            ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                    "材料转换信息_"+ DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
                    columnList, titleList, sumList);

    }
    
    /**
	 * ItemTransform查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, ItemTransform itemTransform) throws Exception {

		return new ModelAndView("admin/basic/itemTransform/itemTransform_code")
				.addObject("itemTransform", itemTransform);
	}
	
	/**
	 * 根据id查询材料详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getItemTransform")
	@ResponseBody
	public JSONObject getItem(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		ItemTransform itemTransform = itemTransformService.get(ItemTransform.class, id);
		if(itemTransform == null){
			return this.out("系统中不存在code="+id+"的材料信息", false);
		}
		return this.out(itemTransform, true);
	}
    
}
