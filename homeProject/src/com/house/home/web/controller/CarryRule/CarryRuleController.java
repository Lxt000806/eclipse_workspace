package com.house.home.web.controller.CarryRule;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CarryRule;
import com.house.home.entity.basic.CarryRuleFloor;
import com.house.home.entity.basic.CarryRuleItem;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.basic.CarryRuleService;

@Controller
@RequestMapping("/admin/CarryRule")
public class CarryRuleController extends BaseController { 
		
	@Autowired
	private CarryRuleService carryRuleService;

	/**
	 * @param request
	 * @param response 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		return new ModelAndView("admin/basic/carryRule/CarryRule_list").addObject("abc", null);
	}
	/*
	 * 新增跳转CarryRule表格数据

	/*
	 *CarryRule保存 
	 * */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到添加匹配楼层页面");						
		CarryRule carryRule = new CarryRule();		
		return new ModelAndView("admin/basic/carryRule/CarryRule_save")
			.addObject("carryRule", carryRule).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/*
	 *CarryRule编辑页面 
	 * */
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到编辑材料套餐包页面");
		CarryRule carryRule = null;
		if (StringUtils.isNotBlank(id)){
			carryRule = carryRuleService.get(CarryRule.class, id);
			if (carryRule!=null && StringUtils.isNotBlank(carryRule.getSupplCode())){
				Supplier supplier  = carryRuleService.get(Supplier.class, carryRule.getSupplCode());
				if(supplier!=null){
					carryRule.setSupplDescr(supplier.getDescr());
				}
			}	
		}else{
			carryRule = new CarryRule();
		}
		return new ModelAndView("admin/basic/carryRule/carryRule_update")
			.addObject("carryRule", carryRule).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
    @RequestMapping("/goCopy")
    public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, String id) {
        
        CarryRule carryRule = null;
        if (StringUtils.isNotBlank(id)) {
            carryRule = carryRuleService.get(CarryRule.class, id);
            if (carryRule != null && StringUtils.isNotBlank(carryRule.getSupplCode())) {
                Supplier supplier = carryRuleService.get(Supplier.class, carryRule.getSupplCode());
                if (supplier != null) {
                    carryRule.setSupplDescr(supplier.getDescr());
                }
            }
        } else {
            carryRule = new CarryRule();
        }
        
        return new ModelAndView("admin/basic/carryRule/carryRule_copy")
                .addObject("carryRule",carryRule)
                .addObject("czy", this.getUserContext(request).getCzybh());
    }
	
	@RequestMapping("/goitem3")
	public ModelAndView goitem3(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到编辑材料套餐包页面");
		CarryRule carryRule = null;
		if (StringUtils.isNotBlank(id)){
			carryRule = carryRuleService.get(CarryRule.class, id);
		}else{
			carryRule = new CarryRule();
		}
		return new ModelAndView("admin/basic/carryRule/carryRuleItem3_update")
			.addObject("carryRule", carryRule).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/*
	 *i搬运费查看页面 
	 * */
	@RequestMapping("/goview")
	public ModelAndView goview(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看搬运费页面");
		
		CarryRule carryRule = null;
		if (StringUtils.isNotBlank(id)){
			carryRule = carryRuleService.get(CarryRule.class, id);
			if (carryRule!=null && StringUtils.isNotBlank(carryRule.getSupplCode())){
				Supplier supplier  = carryRuleService.get(Supplier.class, carryRule.getSupplCode());
				if(supplier!=null){
					carryRule.setSupplDescr(supplier.getDescr());
				}
			}				
		}else{
			carryRule = new CarryRule();
		}
		
		return new ModelAndView("admin/basic/carryRule/carryRule_view")
			.addObject("carryRule", carryRule);
	}
	
	@RequestMapping("/goadd")
	public ModelAndView goadd(HttpServletRequest request, HttpServletResponse response,CarryRuleFloor carryRuleFloor){
		logger.debug("跳转到新增匹配楼层页面");	
		
		return new ModelAndView("admin/basic/carryRule/carryRuleFloor_add")
			.addObject("carryRuleFloor", carryRuleFloor);
	}
	//新增的材料类型3的搬运规则
	@RequestMapping("/goItemadd")
	public ModelAndView goItemadd(HttpServletRequest request, HttpServletResponse response,CarryRuleItem carryRuleItem){
		logger.debug("跳转到新增搬运材料规则页面");	
		
		return new ModelAndView("admin/basic/carryRule/ItemRule_add")
			.addObject("carryRuleItem", carryRuleItem).addObject("itemType3",carryRuleItem.getItemType3Descr());
	}
	/*
	 * 编辑的材料类型3的搬运规则
	 * */
	@RequestMapping("/goitemUpdate")
	public ModelAndView goitemUpdate(HttpServletRequest request, HttpServletResponse response,CarryRuleItem carryRuleItem){
		logger.debug("跳转到修改材料类型页面");			
		return new ModelAndView("admin/basic/carryRule/ItemRule_update")
			.addObject("carryRuleItem", carryRuleItem);
	}
	/*
	 * 查看的材料类型3的搬运规则
	 * */
	@RequestMapping("/goitemView")
	public ModelAndView goitemView(HttpServletRequest request, HttpServletResponse response,CarryRuleItem carryRuleItem){
		logger.debug("跳转到查看匹配楼层页面");	
		return new ModelAndView("admin/basic/carryRule/ItemRule_view")
			.addObject("carryRuleItem", carryRuleItem);
	}

	/*
	 * 新增页面的 编辑
	 * */
	@RequestMapping("/goaddUpdate")
	public ModelAndView goaddUpdate(HttpServletRequest request, HttpServletResponse response,CarryRuleFloor carryRuleFloor){
		logger.debug("跳转到修改匹配楼层页面");			
		return new ModelAndView("admin/basic/carryRule/carryRuleFloor_update")
			.addObject("carryRuleFloor", carryRuleFloor);
	}
	
	/*
	 * 新增页面的查看
	 * */
	@RequestMapping("/goaddView")
	public ModelAndView goaddview(HttpServletRequest request, HttpServletResponse response,CarryRuleFloor carryRuleFloor){
		logger.debug("跳转到查看匹配楼层页面");	
		return new ModelAndView("admin/basic/carryRule/carryRuleFloor_view")
			.addObject("carryRuleFloor", carryRuleFloor);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,CarryRule carryRule) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		carryRuleService.findPageBySql(page, carryRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	//新增
	@RequestMapping("/goJqGridDetailadd")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridadd(HttpServletRequest request,
			HttpServletResponse response,CarryRuleFloor carryRuleFloor) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		carryRuleService.findPageBySqlDetailadd(page, carryRuleFloor);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	//编辑
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,CarryRuleFloor carryRuleFloor) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		carryRuleService.findPageBySqlDetail(page, carryRuleFloor);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	//编辑item3
		@RequestMapping("/goJqGridItem3")
		@ResponseBody
		public WebPage<Map<String,Object>> getJqGridItem3(HttpServletRequest request,
				HttpServletResponse response,CarryRuleItem carryRuleItem) throws Exception {
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			carryRuleService.findPageBySqlItem3(page, carryRuleItem);
			return new WebPage<Map<String,Object>>(page);
		}	
	
	/**
	 *搬运费规则保存 
	 *
	 */
	@RequestMapping("/docarryRuleSave")
	public void doPurchaseSave(HttpServletRequest request,HttpServletResponse response,CarryRule carryRule){
		logger.debug("搬运费规则新增开始");		
		try {
			carryRule.setLastUpdate(new Date());			
			carryRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			carryRule.setLastUpdate(new Date());
			carryRule.setM_umState("A");
			carryRule.setExpired("F"); 									
			String itemAppsendDetail =request.getParameter("itemAppsendDetailJson");
			String salesInvoiceDetail =request.getParameter("salesInvoiceDetailJson");
			JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  itemAppsendDetailJson=jsonArray.toString();
			carryRule.setItemAppsendDetailJson(itemAppsendDetailJson);
			JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
			JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());//先转化成json数组 
			String salesInvoiceDetailJson=jsonArray2.toString();
			carryRule.setSalesInvoiceDetailJson(salesInvoiceDetailJson);
			
			CarryRule iSet = this.carryRuleService.getByNo2(carryRule.getItemType1(),
			        carryRule.getItemType2(), carryRule.getCarryType(),
			        carryRule.getDistanceType(), carryRule.getSendType());			
			if (iSet!=null){				
				carryRule.setM_umState("H");				
			}	
			
			Result result = this.carryRuleService.docarryRuleSave(carryRule);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "搬运费规则新增失败");
		}
	}
	
    @RequestMapping("/doCopy")
    public void doCopy(HttpServletRequest request, HttpServletResponse response,
            CarryRule carryRule) {
        
        try {
            carryRule.setLastUpdate(new Date());
            carryRule.setLastUpdatedBy(getUserContext(request).getCzybh());
            carryRule.setLastUpdate(new Date());
            carryRule.setM_umState("C");
            carryRule.setExpired("F");
            String itemAppsendDetail = request.getParameter("itemAppsendDetailJson");
            String salesInvoiceDetail = request.getParameter("salesInvoiceDetailJson");
            JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
            JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());
            String itemAppsendDetailJson = jsonArray.toString();
            carryRule.setItemAppsendDetailJson(itemAppsendDetailJson);
            JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
            JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());
            String salesInvoiceDetailJson = jsonArray2.toString();
            carryRule.setSalesInvoiceDetailJson(salesInvoiceDetailJson);

            CarryRule iSet = this.carryRuleService.getByNo2(carryRule.getItemType1(),
                    carryRule.getItemType2(), carryRule.getCarryType(),
                    carryRule.getDistanceType(), carryRule.getSendType());
            
            if (iSet != null) {
                carryRule.setM_umState("H");
            }

            Result result = this.carryRuleService.docarryRuleSave(carryRule);
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, "保存成功");
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "搬运费规则新增失败");
        }
    }
	
	/**
	 *搬运费规则编辑 
	 *
	 */
	@RequestMapping("/doitemSetUpdate")
	public void doitemSetUpdate(HttpServletRequest request,HttpServletResponse response,CarryRule carryRule){
		logger.debug("搬运费规则编辑开始");
		try {

			carryRule.setLastUpdate(new Date());
			String detailJson = request.getParameter("detailJson");
			carryRule.setDetailJson(detailJson);
			carryRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			carryRule.setLastUpdate(new Date());
			carryRule.setM_umState("M");
			carryRule.setExpired("F");	
		
			String itemAppsendDetail =request.getParameter("itemAppsendDetailJson");
			String salesInvoiceDetail =request.getParameter("salesInvoiceDetailJson");
			JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  itemAppsendDetailJson=jsonArray.toString();
			carryRule.setItemAppsendDetailJson(itemAppsendDetailJson);
			JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
			JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());//先转化成json数组 
			String salesInvoiceDetailJson=jsonArray2.toString();
			carryRule.setSalesInvoiceDetailJson(salesInvoiceDetailJson);
			
			CarryRule iSet = this.carryRuleService.getByNo(carryRule.getNo(),
			        carryRule.getItemType1(),carryRule.getItemType2(),
			        carryRule.getCarryType(),carryRule.getDistanceType(),
			        carryRule.getSendType());			
			if (iSet!=null){				
				carryRule.setM_umState("J");				
			}
			Result result = this.carryRuleService.docarryRuleSave(carryRule);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "搬运费规则编辑失败");
		}
	}
	
	
	//匹配材料类型的添加材料类型3
	@RequestMapping("/doitemSave")
	public void doitemSave(HttpServletRequest request,HttpServletResponse response,CarryRule carryRule){
		logger.debug("材料类型添加开始");
		try {							
			carryRule.setLastUpdate(new Date());
			String detailJson = request.getParameter("detailJson");
			carryRule.setDetailJson(detailJson);
			carryRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			carryRule.setLastUpdate(new Date());
			carryRule.setM_umState("I");
			carryRule.setExpired("F");	
			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}		
			Result result = this.carryRuleService.docarryRuleSave(carryRule);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料套餐编辑失败");
		}
	}
	
	/**
	 * 删除搬运费规则
	 * @param request
	 * @param response
	 * @param roleId
	 */

	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除搬运费规则管理信息开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CarryRule carryRule = this.carryRuleService.get(CarryRule.class, deleteId);
				carryRule.setExpired("T");
				carryRule.setM_umState("M");			
				carryRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = carryRuleService.deleteForProc(carryRule);
				if ("1".equals(result.getCode())){
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		}
		logger.debug(" IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
}
