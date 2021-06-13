package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.AssetType;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.basic.AssetTypeService;
import com.house.home.service.finance.AssetService;

@Controller
@RequestMapping("/admin/assetType")
public class AssetTypeController extends BaseController {

    @Autowired
    private AssetTypeService assetTypeService;
    @Autowired
    private AssetService assetService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request,
            HttpServletResponse response) {

        return new ModelAndView("admin/basic/assetType/assetType_list");
    }

    @RequestMapping("/goJqGridList")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            AssetType assetType) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        assetTypeService.findPageBySql(page, assetType);

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/getAssetTypeBySql")
	@ResponseBody
	public WebPage<Map<String,Object>> getAssetTypeBySql(HttpServletRequest request,
			HttpServletResponse response,AssetType assetType) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);
		assetService.findAssetTypeBySql(page, assetType);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goAssetType")
	public ModelAndView goCodeOA(HttpServletRequest request,
			HttpServletResponse response,AssetType assetType) throws Exception {
			
		return new ModelAndView("admin/finance/asset/assetType_code").addObject("assetType", assetType);
	}

    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request,
            HttpServletResponse response) {

        return new ModelAndView("admin/basic/assetType/assetType_save");
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request,
            HttpServletResponse response, AssetType assetType) {

        try {
            Result result = assetTypeService.doSave(assetType, getUserContext(request));
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "保存失败，程序异常");
        }
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request,
            HttpServletResponse response, String code) {
        
        AssetType assetType = new AssetType();
        if (StringUtils.isNotBlank(code)) {            
            assetType = assetTypeService.get(AssetType.class, code);
        }

        return new ModelAndView("admin/basic/assetType/assetType_update")
        .addObject("assetType", assetType);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, AssetType assetType) {

        try {
            Result result = assetTypeService.doUpdate(assetType, getUserContext(request));
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "更新失败，程序异常");
        }
    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request,
            HttpServletResponse response, String code) {
        
        AssetType assetType = new AssetType();
        if (StringUtils.isNotBlank(code)) {            
            assetType = assetTypeService.get(AssetType.class, code);
        }

        return new ModelAndView("admin/basic/assetType/assetType_view")
        .addObject("assetType", assetType);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response,
            AssetType assetType){
        
        Page<Map<String, Object>>page= newPage(request);
        page.setPageSize(-1);
        
        assetTypeService.findPageBySql(page, assetType);
        getExcelList(request);
        
        ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                "固定资产类别_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
}
