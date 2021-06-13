/**
 * 
 */
package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.Uom;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.basic.UomService;

/**
 * @author lenovo-l821
 *
 */
@Controller
@RequestMapping("/admin/uom")
public class UomController extends BaseController {
	@Autowired
	private UomService uomService;
	
	@RequestMapping("/goList")
	public ModelAndView golist(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("admin/basic/uom/uom_list");
 	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> findPageBySql(HttpServletRequest request,HttpServletResponse response,Uom uom){
		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		uomService.findPageBySql(page, uom);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("admin/basic/uom/uom_save");
 	}
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,HttpServletResponse response,String id){
		Uom uom = null;
		if (StringUtils.isNotBlank(id)) {
			uom = uomService.get(Uom.class, id);
		}else {
			uom = new Uom();
		}
		return new ModelAndView("admin/basic/uom/uom_copy").addObject("uom", uom);
 	}
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,HttpServletResponse response,String id){
		Uom uom = null;
		if (StringUtils.isNotBlank(id)) {
			uom = uomService.get(Uom.class, id);
		}
		return new ModelAndView("admin/basic/uom/uom_update").addObject("uom", uom);
 	}
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,String id){
		Uom uom = null;
		if (StringUtils.isNotBlank(id)) {
			uom = uomService.get(Uom.class, id);
		}
		return new ModelAndView("admin/basic/uom/uom_detail").addObject("uom", uom);
 	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,Uom uom){
		try {
			if (StringUtils.isNotBlank(uom.getCode())&&StringUtils.isNotBlank(uom.getDescr())) {
				if(valideUom(uom.getCode())==true){
					ServletUtils.outPrintFail(request, response, "度量单位["+uom.getCode()+"]已经存在 ");
					return;
				}
				uom.setExpired("F");
				uom.setActionLog("ADD");
				uom.setLastUpdate(new Date());
				uom.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				uomService.save(uom);
				ServletUtils.outPrintSuccess(request, response,"度量单位保存成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response, "度量单位保存失败 ");
		}
 	}
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request,HttpServletResponse response,Uom uom){
		try {
			if (StringUtils.isNotBlank(uom.getCode())&&StringUtils.isNotBlank(uom.getDescr())) {
				if(valideUom(uom.getCode())==true){
					ServletUtils.outPrintFail(request, response, "度量单位["+uom.getCode()+"]已经存在 ");
					return;
				}
				uom.setActionLog("ADD");
				uom.setLastUpdate(new Date());
				uom.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				uomService.save(uom);
				ServletUtils.outPrintSuccess(request, response,"度量单位保存成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response, "度量单位保存失败 ");
		}
 	}
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,Uom uom){
		try {
			if (StringUtils.isNotBlank(uom.getCode())&&StringUtils.isNotBlank(uom.getDescr())) {
				uom.setActionLog("EDIT");
				uom.setLastUpdate(new Date());
				uom.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				uomService.update(uom);
				ServletUtils.outPrintSuccess(request, response,"度量单位修改成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response, "度量单位修改失败 ");
		}
 	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, Uom uom) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		uomService.findPageBySql(page,uom);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"度量单位_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	
	
	/**
	 * 验证度量单位编号是否重复
	 */
	public boolean valideUom(String id){
		return uomService.valideUom(id);	
	}
	/**
	 * 根据ID查询单位
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getUom")
	@ResponseBody
	public JSONObject getSupplier(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Uom uom = uomService.get(Uom.class, id);
		if(uom == null){
			return this.out("系统中不存在code="+id+"的单位编号", false);
		}
		return this.out(uom, true);
	}
	/**
	 * 获取编号
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,Uom uom) throws Exception {

		return new ModelAndView("admin/basic/uom/uom_code")
			.addObject("uom", uom);
	}
}
