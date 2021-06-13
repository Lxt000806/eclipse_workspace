package com.house.home.web.controller.basic;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BaseCheckItem;
import com.house.home.entity.basic.BaseCheckItemWorkerClassify;
import com.house.home.service.basic.BaseCheckItemService;

@Controller
@RequestMapping("/admin/baseCheckItem")
public class BaseCheckItemController extends BaseController {

    @Autowired 
    private BaseCheckItemService baseCheckItemServiceImpl;
    
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,BaseCheckItem baseCheckItem) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseCheckItemServiceImpl.findPageBySql(page, baseCheckItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("admin/basic/baseCheckItem/baseCheckItem_list");
	}
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,HttpServletResponse response){ 
		return new ModelAndView("admin/basic/baseCheckItem/baseCheckItem_save");	
	}
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,HttpServletResponse response,String id){
		logger.debug("跳转到修改基础结算项目管理页面");
		BaseCheckItem baseCheckItem=null;
		if (StringUtils.isNotBlank(id)) {
			baseCheckItem = baseCheckItemServiceImpl.get(BaseCheckItem.class,id);
			/*BaseItemType1 baseItemType1=null;*/
			if (StringUtils.isNotBlank(baseCheckItem.getUom())){
				baseCheckItem.setUom(baseCheckItem.getUom().trim());
				}
			if (StringUtils.isNotBlank(baseCheckItem.getWorkType12())){
				baseCheckItem.setWorkType12(baseCheckItem.getWorkType12().trim());
				}
			baseCheckItem.setWorkerClassify(baseCheckItemServiceImpl.getWorkerClassify(id));
		}else{
			baseCheckItem = new BaseCheckItem();
		}	
		return new ModelAndView("admin/basic/baseCheckItem/baseCheckItem_update")
		           .addObject("baseCheckItem", baseCheckItem);
	
	}
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response,String id){
		logger.debug("跳转到查看基础结算项目管理页面");
		BaseCheckItem baseCheckItem=null;
		if (StringUtils.isNotBlank(id)) {
			baseCheckItem = baseCheckItemServiceImpl.get(BaseCheckItem.class, id);
			if (StringUtils.isNotBlank(baseCheckItem.getUom())){
				baseCheckItem.setUom(baseCheckItem.getUom().trim());
				}
			if (StringUtils.isNotBlank(baseCheckItem.getWorkType12())){
				baseCheckItem.setWorkType12(baseCheckItem.getWorkType12().trim());
				}
			baseCheckItem.setWorkerClassify(baseCheckItemServiceImpl.getWorkerClassify(id));
		}			
		return new ModelAndView("admin/basic/baseCheckItem/baseCheckItem_detail").addObject("baseCheckItem", baseCheckItem);
	}
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,HttpServletResponse response,String id){
		logger.debug("跳转到复制基础结算项目管理页面");
		BaseCheckItem baseCheckItem=null;
		if (StringUtils.isNotBlank(id)) {
			baseCheckItem = baseCheckItemServiceImpl.get(BaseCheckItem.class, id);
			if (StringUtils.isNotBlank(baseCheckItem.getUom())){
				baseCheckItem.setUom(baseCheckItem.getUom().trim());
				}
			if (StringUtils.isNotBlank(baseCheckItem.getWorkType12())){
				baseCheckItem.setWorkType12(baseCheckItem.getWorkType12().trim());
				}
			baseCheckItem.setWorkerClassify(baseCheckItemServiceImpl.getWorkerClassify(id));
		}else{
			baseCheckItem = new BaseCheckItem();
		}			
		return new ModelAndView("admin/basic/baseCheckItem/baseCheckItem_copy").addObject("baseCheckItem", baseCheckItem);
	}
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,BaseCheckItem baseCheckItem){
		logger.debug("添加基础结算项目管理开始");
		try {
			if(baseCheckItem!=null){
				String str = baseCheckItemServiceImpl.getSeqNo("tBaseCheckItem");
				baseCheckItem.setCode(str);
				baseCheckItem.setExpired("F");
				baseCheckItem.setActionLog("ADD");
				baseCheckItem.setLastUpdate(new Date());
				baseCheckItem.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
				baseCheckItemServiceImpl.save(baseCheckItem);
				if(StringUtils.isNotBlank(baseCheckItem.getWorkerClassify())){
					String[] workerClassifys = baseCheckItem.getWorkerClassify().split(",");
					for (int i = 0; i < workerClassifys.length; i++) {
						BaseCheckItemWorkerClassify baseCheckItemWorkerClassify = new BaseCheckItemWorkerClassify();
						baseCheckItemWorkerClassify.setBaseCheckItemCode(str);
						baseCheckItemWorkerClassify.setWorkerClassify(workerClassifys[i]);
						baseCheckItemWorkerClassify.setLastUpdate(new Date());
						baseCheckItemWorkerClassify.setLastUpdatedBy(this.getUserContext(request).getCzybh());
						baseCheckItemWorkerClassify.setExpired("F");
						baseCheckItemWorkerClassify.setActionLog("ADD");
						baseCheckItemServiceImpl.save(baseCheckItemWorkerClassify);
					}
				}
				
				ServletUtils.outPrintSuccess(request, response);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response, "添加基础结算项目管理失败");
		}
	}
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,BaseCheckItem baseCheckItem){
		try {
			baseCheckItem.setActionLog("EDIT");
			baseCheckItem.setLastUpdate(new Date());
			baseCheckItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		    baseCheckItemServiceImpl.update(baseCheckItem);
		    
		    //修改对应的工人分类
		    if(StringUtils.isNotBlank(baseCheckItem.getWorkerClassify())){
		    	baseCheckItemServiceImpl.doUpdateWorkerClassify(baseCheckItem);
			}

		    ServletUtils.outPrintSuccess(request, response);			
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response,"修改基础结算项目管理失败");
		}
	}
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request,HttpServletResponse response,BaseCheckItem baseCheckItem){
		try {
			String str = baseCheckItemServiceImpl.getSeqNo("tBaseCheckItem");
			baseCheckItem.setCode(str);
			baseCheckItem.setLastUpdate(new Date());
			baseCheckItem.setLastUpdatedBy(getUserContext(request).getCzybh());
		    baseCheckItemServiceImpl.save(baseCheckItem);
		    if(StringUtils.isNotBlank(baseCheckItem.getWorkerClassify())){
				String[] workerClassifys = baseCheckItem.getWorkerClassify().split(",");
				for (int i = 0; i < workerClassifys.length; i++) {
					BaseCheckItemWorkerClassify baseCheckItemWorkerClassify = new BaseCheckItemWorkerClassify();
					baseCheckItemWorkerClassify.setBaseCheckItemCode(str);
					baseCheckItemWorkerClassify.setWorkerClassify(workerClassifys[i]);
					baseCheckItemWorkerClassify.setLastUpdate(new Date());
					baseCheckItemWorkerClassify.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					baseCheckItemWorkerClassify.setExpired("F");
					baseCheckItemWorkerClassify.setActionLog("ADD");
					baseCheckItemServiceImpl.save(baseCheckItemWorkerClassify);
				}
			}
		    ServletUtils.outPrintSuccess(request, response);			
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response,"复制基础结算项目管理失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, BaseCheckItem baseCheckItem) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseCheckItemServiceImpl.findPageBySql(page,baseCheckItem);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"基础结算项目管理_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	/**
	 * @Description: TODO 基础结算项目code查询
	 * @author	created by zb
	 * @date	2018-9-20--下午5:37:34
	 */
	@RequestMapping("/goCodeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getCodeJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,BaseCheckItem baseCheckItem) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		baseCheckItemServiceImpl.findCodePageBySql(page, baseCheckItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/goItemSelect")
	public ModelAndView goItemSelect(HttpServletRequest request, 
			HttpServletResponse response,BaseCheckItem baseCheckItem){
		return new ModelAndView("admin/basic/baseCheckItem/baseCheckItem_select")
					.addObject("MENU_TYPE_URL", DictConstant.DICT_MENU_TYPE_URL)
					.addObject("baseCheckItem",baseCheckItem);
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
			HttpServletResponse response, BaseCheckItem baseCheckItem){
	
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseCheckItemServiceImpl.getItemBaseType(page, baseCheckItem);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 根据id查询基础结算项目信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBaseCheckItem")
	@ResponseBody
	public JSONObject getBaseCheckItem(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		BaseCheckItem baseCheckItem = baseCheckItemServiceImpl.get(BaseCheckItem.class, id);
		if(baseCheckItem== null){
			return this.out("系统中不存在code="+id+"基础结算项目信息", false);
		}
		return this.out(baseCheckItem, true);
	}
	/**
	 * BaseCheckItem查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,BaseCheckItem baseCheckItem) throws Exception {

		return new ModelAndView("admin/basic/baseCheckItem/baseCheckItem_code").addObject("baseCheckItem", baseCheckItem);
	}
}
