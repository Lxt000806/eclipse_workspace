package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.BasePlanTemp;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.insales.BaseItem;
import com.house.home.service.insales.BaseItemService;

@Controller
@RequestMapping("/admin/baseItem")
public class BaseItemController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseItemController.class);

	@Autowired
	private BaseItemService baseItemService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItem baseItem) throws Exception {
		CustType custType = new CustType();
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(baseItem.getCustomerType())){
			custType=baseItemService.get(CustType.class, baseItem.getCustomerType());
			baseItem.setCanUseComBaseItem(custType.getCanUseComBaseItem());
		}
		if(StringUtils.isNotBlank(baseItem.getCustType())){
			custType=baseItemService.get(CustType.class, baseItem.getCustType());
			baseItem.setCanUseComBaseItem(custType.getCanUseComBaseItem());
		}
		baseItemService.findPageBySql(page, baseItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goListJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItem baseItem) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemService.findListPageBySql(page, baseItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItem baseItem) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemService.findDetailPageBySql(page, baseItem);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseItem列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/baseItem/baseItem_list");
	}
	/**
	 * BaseItem查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,BaseItem baseItem) throws Exception {
		
		return new ModelAndView("admin/insales/baseItem/baseItem_code").addObject("baseItem", baseItem);
	}
	/**
	 * 跳转到新增BaseItem页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItem页面");
		BaseItem baseItem = null;
		if (StringUtils.isNotBlank(id)){
			baseItem = baseItemService.get(BaseItem.class, id);
			baseItem.setCode(null);
		}else{
			baseItem = new BaseItem();
		}
		
		return new ModelAndView("admin/insales/baseItem/baseItem_save")
			.addObject("baseItem", baseItem);
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String code){
		logger.debug("跳转到复制BaseItem页面");
		BaseItem baseItem = null;
		if (StringUtils.isNotBlank(code)){
			baseItem = baseItemService.get(BaseItem.class, code);
			if(baseItem!=null && baseItem.getDispSeq()==null){
				baseItem.setDispSeq(0);
			}
			if(baseItem!=null && baseItem.getMarketPrice()==null){
				baseItem.setMarketPrice(0d);
			}
			if(StringUtils.isNotBlank(baseItem.getCustType())){
				baseItem.setCustType(baseItem.getCustType().trim());
			}
			
			if (StringUtils.isNotBlank(baseItem.getItemType2())) {
                ItemType2 itemType2 = baseItemService.get(ItemType2.class, baseItem.getItemType2());
                if (itemType2 != null) {
                    baseItem.setItemType1(itemType2.getItemType1());
                }
            }
		}
		
		return new ModelAndView("admin/insales/baseItem/baseItem_copy")
			.addObject("baseItem", baseItem);
	}
	/**
	 * 跳转到修改BaseItem页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String code){
		logger.debug("跳转到修改BaseItem页面");
		BaseItem baseItem = null;
		if (StringUtils.isNotBlank(code)){
			baseItem = baseItemService.get(BaseItem.class, code);
			if(baseItem!=null && baseItem.getDispSeq()==null){
				baseItem.setDispSeq(0);
			}
			if(baseItem!=null && baseItem.getMarketPrice()==null){
				baseItem.setMarketPrice(0d);
			}
			if(StringUtils.isNotBlank(baseItem.getCustType())){
				baseItem.setCustType(baseItem.getCustType().trim());
			}
			if(StringUtils.isNotBlank(baseItem.getBaseTempNo())){
				 BasePlanTemp  basePlanTemp= baseItemService.get(BasePlanTemp.class, baseItem.getBaseTempNo());
				 if(basePlanTemp!=null){
					 baseItem.setBaseTempDescr(basePlanTemp.getDescr());
				 }
			}
			
			if (StringUtils.isNotBlank(baseItem.getItemType2())) {
                ItemType2 itemType2 = baseItemService.get(ItemType2.class, baseItem.getItemType2());
                if (itemType2 != null) {
                    baseItem.setItemType1(itemType2.getItemType1());
                }
            }
		}
		
		return new ModelAndView("admin/insales/baseItem/baseItem_update")
			.addObject("baseItem", baseItem);
	}
	
	@RequestMapping("/goUpdatePercentage")
	public ModelAndView goUpdatePercentage(HttpServletRequest request, HttpServletResponse response, 
			String code){
		logger.debug("跳转到修改BaseItem页面");
		BaseItem baseItem = null;
		if (StringUtils.isNotBlank(code)){
			baseItem = baseItemService.get(BaseItem.class, code);
			if(baseItem!=null && baseItem.getDispSeq()==null){
				baseItem.setDispSeq(0);
			}
		}
		
		return new ModelAndView("admin/insales/baseItem/baseItem_updatePercentage")
			.addObject("baseItem", baseItem);
	}
	
	@RequestMapping("/goUpdatePrice")
	public ModelAndView goUpdatePrice(HttpServletRequest request, HttpServletResponse response, 
			String code){
		logger.debug("跳转到修改BaseItem页面");
		BaseItem baseItem = null;
		if (StringUtils.isNotBlank(code)){
			baseItem = baseItemService.get(BaseItem.class, code);
			if(baseItem!=null && baseItem.getDispSeq()==null){
				baseItem.setDispSeq(0);
			}
		}
		
		return new ModelAndView("admin/insales/baseItem/baseItem_updatePrice")
			.addObject("baseItem", baseItem);
	}
	
	/**
	 * 跳转到查看BaseItem页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String code){
		logger.debug("跳转到查看BaseItem页面");
		BaseItem baseItem = baseItemService.get(BaseItem.class, code);
		if (StringUtils.isNotBlank(code)){
			baseItem = baseItemService.get(BaseItem.class, code);
			if(baseItem!=null && baseItem.getDispSeq()==null){
				baseItem.setDispSeq(0);
			}
		}
		if(StringUtils.isNotBlank(baseItem.getCustType())){
			baseItem.setCustType(baseItem.getCustType().trim());
		}
		if(StringUtils.isNotBlank(baseItem.getBaseTempNo())){
			 BasePlanTemp  basePlanTemp= baseItemService.get(BasePlanTemp.class, baseItem.getBaseTempNo());
			 if(basePlanTemp!=null){
				 baseItem.setBaseTempDescr(basePlanTemp.getDescr());
			 }	
		}
		
        if (StringUtils.isNotBlank(baseItem.getItemType2())) {
            ItemType2 itemType2 = baseItemService.get(ItemType2.class, baseItem.getItemType2());
            if (itemType2 != null) {
                baseItem.setItemType1(itemType2.getItemType1());
            }
        }
		
		return new ModelAndView("admin/insales/baseItem/baseItem_detail")
				.addObject("baseItem", baseItem);
	}
	/**
	 * 添加BaseItem
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseItem baseItem){
		logger.debug("添加BaseItem开始");
		try{
			baseItem.setLastUpdate(new Date());
			baseItem.setLastUpdatedBy(getUserContext(request).getCzybh());
			baseItem.setExpired("F");
			if(baseItem.getDispSeq()==null){
				baseItem.setDispSeq(0);
			}
			Result result = this.baseItemService.doSave(baseItem);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加失败");
		}
	}
	
	/**
	 * 修改BaseItem
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseItem baseItem){
		logger.debug("修改BaseItem开始");
		try{
			baseItem.setLastUpdate(new Date());
			baseItem.setLastUpdatedBy(getUserContext(request).getCzybh());
			if(baseItem.getDispSeq()==null){
				baseItem.setDispSeq(0);
			}
			Result result = this.baseItemService.doUpdate(baseItem);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItem失败");
		}
	}
	
	@RequestMapping("/doUpdatePrice")
	public void doUpdatePrice(HttpServletRequest request, HttpServletResponse response, BaseItem baseItem){
		logger.debug("修改BaseItem开始");
		try{
			baseItem.setLastUpdate(new Date());
			baseItem.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = this.baseItemService.doUpdatePrice(baseItem);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItem失败");
		}
	}
	
	@RequestMapping("/doUpdatePercentage")
	public void doUpdatePercentage(HttpServletRequest request, HttpServletResponse response, BaseItem baseItem){
		logger.debug("修改BaseItem开始");
		try{
			BaseItem bI=null;
			if(StringUtils.isNotBlank(baseItem.getCode())){
				bI=baseItemService.get(baseItem.getClass(), baseItem.getCode());
			}
			
			bI.setLastUpdate(new Date());
			bI.setLastUpdatedBy(getUserContext(request).getCzybh());
			bI.setPerfPer(baseItem.getPerfPer());
			this.baseItemService.update(bI);
			
			ServletUtils.outPrintSuccess(request, response,"修改成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItem失败");
		}
	}
	
	/**
	 * 删除BaseItem
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseItem开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseItem编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseItem baseItem = baseItemService.get(BaseItem.class, deleteId);
				if(baseItem == null)
					continue;
				baseItem.setExpired("T");
				baseItemService.update(baseItem);
			}
		}
		logger.debug("删除BaseItem IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/goItemSelect")
	public ModelAndView goItemSelect(HttpServletRequest request, 
			HttpServletResponse response,BaseItem baseItem){
		return new ModelAndView("admin/insales/baseItem/baseItem_select")
					.addObject("MENU_TYPE_URL", DictConstant.DICT_MENU_TYPE_URL)
					.addObject("baseItem",baseItem);
	}
	/**
	 * 根据材料类型1和sqlcode返回材料列表
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 */
	@RequestMapping("/goItemSelectJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemSelectJqGrid(HttpServletRequest request, 
			HttpServletResponse response, BaseItem baseItem){
	
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemService.getItemBaseType(page, baseItem);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 根据id查询基础项目信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBaseItem")
	@ResponseBody
	public JSONObject getBaseItem(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		BaseItem baseItem = baseItemService.get(BaseItem.class, id);
		if(baseItem== null){
			return this.out("系统中不存在code="+id+"基础项目信息", false);
		}
		return this.out(baseItem, true);
	}
	/**
	 * 材料类型1，2，3三级联动
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/baseItemType/{type}/{pCode}") //获取商品类型1,2,3
	@ResponseBody
	public JSONObject getBaseItemType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = baseItemService.findBaseItemType(type, pCode);
		return this.out(regionList, true);
	}
	/**
	 * 根据基础项目编号查算法
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBaseAlgorithm")
	@ResponseBody
	public List<Map<String, Object>> getAlgorithm(HttpServletRequest request,HttpServletResponse response,BaseItem baseItem){
		List<Map<String, Object>>list=baseItemService.getBaseAlgorithmByCode(baseItem);
		return list;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			BaseItem baseIte){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		baseItemService.findListPageBySql(page, baseIte);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础项目管理表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
