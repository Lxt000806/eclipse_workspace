package com.house.home.web.controller.insales;

import java.util.Date;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.insales.ItemPreAppService;
import com.house.home.service.insales.WareHouseService;

@Controller
@RequestMapping("/admin/wareHouse")
public class WareHouseController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WareHouseController.class);

	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private ItemPreAppService itemPreAppService; 
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param wareHouse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,HttpServletResponse response,WareHouse wareHouse) 
    throws Exception {		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wareHouseService.findPageBySql(page, wareHouse);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goLimitItemType2JqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLimitItemType2JqGrid(HttpServletRequest request,
	        HttpServletResponse response, WareHouse wareHouse) {
	    
	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    wareHouseService.findLimitItemType2PageBySql(page, wareHouse);
	    
	    return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goLimitRegionJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLimitRegionJqGrid(HttpServletRequest request,
	        HttpServletResponse response, WareHouse wareHouse) {
	    
	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    wareHouseService.findLimitRegionPageBySql(page, wareHouse);
	    
	    return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * WareHouse列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response, WareHouse wareHouse) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPage(request);
		wareHouseService.findPageBySql(page, wareHouse);
		return new ModelAndView("admin/insales/wareHouse/wareHouse_list").addObject(CommonConstant.PAGE_KEY, page).addObject("wareHouse", wareHouse); 
	}
	/**
	 * 查询JqGridCode表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridCode")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridCode(HttpServletRequest request,HttpServletResponse response,WareHouse wareHouse) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(wareHouse.getCtrlItemType1())){
			wareHouse.setItemRight(this.getUserContext(request).getItemRight());
		}
		wareHouseService.findPageBySqlCode(page, wareHouse);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到新增WareHouse页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, WareHouse wareHouse){
		logger.debug("跳转到新增WareHouse页面");
		return new ModelAndView("admin/insales/wareHouse/wareHouse_save")
		    .addObject("wareHouse", wareHouse);
	}
	/**
	 * 跳转到复制WareHouse页面
	 * @return
	 */
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,HttpServletResponse response, String code, String m_umState) {
		logger.debug("跳转到复制WareHouse页面");
		WareHouse wareHouse= null;
	    wareHouse = wareHouseService.get(WareHouse.class, code);
	    if(StringUtils.isNotBlank(wareHouse.getItemType1())){
	    	wareHouse.setItemType1(wareHouse.getItemType1().trim());
	    }
	    
	    wareHouse.setM_umState(m_umState);
	    
		return new ModelAndView("admin/insales/wareHouse/wareHouse_copy").addObject("wareHouse", wareHouse);	 
	}
	/**
	 * 跳转到修改WareHouse页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String code, String m_umState){
		logger.debug("跳转到修改WareHouse页面");
		WareHouse wareHouse = wareHouseService.get(WareHouse.class, code);
		if(StringUtils.isNotBlank(wareHouse.getItemType1())){
	    	wareHouse.setItemType1(wareHouse.getItemType1().trim());
	    }

		wareHouse.setM_umState(m_umState);
		
		return new ModelAndView("admin/insales/wareHouse/wareHouse_update").addObject("wareHouse", wareHouse);
	}
	
	/**
	 * 跳转到查看WareHouse页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, String code, String m_umState){
		logger.debug("跳转到查看WareHouse页面");
		WareHouse wareHouse = wareHouseService.get(WareHouse.class, code);	
		if(StringUtils.isNotBlank(wareHouse.getItemType1())){
	    	wareHouse.setItemType1(wareHouse.getItemType1().trim());
	    }
		
		wareHouse.setM_umState(m_umState);
		
		return new ModelAndView("admin/insales/wareHouse/wareHouse_detail").addObject("wareHouse", wareHouse);
	}
	
    @RequestMapping("/addLimitItemType2")
    public ModelAndView addLimitItemType2(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/insales/wareHouse/wareHouse_limitItemType2_add")
            .addObject("map", object);
    }

    @RequestMapping("/updateLimitItemType2")
    public ModelAndView updateLimitItemType2(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/insales/wareHouse/wareHouse_limitItemType2_update")
            .addObject("map", object);
    }
    
    @RequestMapping("/addLimitRegion")
    public ModelAndView addLimitRegion(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/insales/wareHouse/wareHouse_limitRegion_add")
            .addObject("map", object);
    }

    @RequestMapping("/updateLimitRegion")
    public ModelAndView updateLimitRegion(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/insales/wareHouse/wareHouse_limitRegion_update")
            .addObject("map", object);
    }
	
	/**
	 * 添加WareHouse
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WareHouse wareHouse){
		logger.debug("添加WareHouse开始");
		try{
			WareHouse ware = this.wareHouseService.get(WareHouse.class, wareHouse.getCode());
			if (ware!=null){
				ServletUtils.outPrintFail(request, response, "WareHouse重复");
				return;
			}
			wareHouse.setM_umState("A");
			wareHouse.setExpired("F");
			wareHouse.setLastUpdate(new Date());
			wareHouse.setActionLog("ADD");
			wareHouse.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = wareHouseService.saveForProc(wareHouse);
			
			if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加WareHouse失败");
		}
	}
	
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request, HttpServletResponse response, WareHouse wareHouse){
		logger.debug("复制WareHouse");
		try{
			WareHouse ware = this.wareHouseService.get(WareHouse.class, wareHouse.getCode());
			if (ware!=null){
				ServletUtils.outPrintFail(request, response, "复制失败：已存在的仓库编号！");
				return;
			}
			wareHouse.setM_umState("C");
			wareHouse.setLastUpdate(new Date());
			wareHouse.setLastUpdatedBy(getUserContext(request).getCzybh());
			wareHouse.setExpired("F");
			wareHouse.setActionLog("ADD");
	        Result result = wareHouseService.saveForProc(wareHouse);
	            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加WareHouse失败");
		}
	}
	/**
	 * 修改WareHouse
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, WareHouse wareHouse){
		logger.debug("修改WareHouse开始");
		try {
		    
			wareHouse.setM_umState("M");
            wareHouse.setLastUpdate(new Date());
            wareHouse.setLastUpdatedBy(getUserContext(request).getCzybh());
            wareHouse.setActionLog("EDIT");
	        Result result = wareHouseService.saveForProc(wareHouse);
	            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "修改WareHouse失败");
        }
	}
	
	/**
	 * 删除WareHouse
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除WareHouse开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "WareHouse编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				WareHouse wareHouse = wareHouseService.get(WareHouse.class, deleteId);
				if(wareHouse == null)
					continue;
				wareHouse.setExpired("T");
				wareHouseService.update(wareHouse);
			}
		}
		logger.debug("删除WareHouse IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *WareHouse导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, WareHouse wareHouse) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		wareHouseService.findPageBySql(page,wareHouse);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"仓库信息_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	
	/**
	 * 根据ID查询仓库信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getWareHouse")
	@ResponseBody
	public JSONObject getWareHouse(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		WareHouse wareHouse = wareHouseService.get(WareHouse.class, id);
		if(wareHouse == null){
			return this.out("系统中不存在code="+id+"的仓库信息", false);
		}
		return this.out(wareHouse, true);
	}
	/**
	 * 仓库Code
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,HttpServletResponse response,WareHouse wareHouse) 
	throws Exception {

		if("1".equals(wareHouse.getShowLastSendSupplier()) && ("JZ".equals(wareHouse.getItemType1().trim()) || "ZC".equals(wareHouse.getItemType1().trim()))){
			Map<String, Object> map = this.itemPreAppService.getLastSendSupplier(wareHouse.getCustCode());
			if(map != null){
				wareHouse.setLastSendSupplierDescr(map.get("lastSendSupplierDescr").toString());
			}
		}else{
			wareHouse.setShowLastSendSupplier("0");
		}
		return new ModelAndView("admin/insales/wareHouse/wareHouse_code").addObject("wareHouse", wareHouse);
	}
}
