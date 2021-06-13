package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.LongFeeRule;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.basic.LongFeeRuleService;

@Controller
@RequestMapping("/admin/longFeeRule")
public class LongFeeRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LongFeeRuleController.class);

	@Autowired
	private LongFeeRuleService longFeeRuleService;

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
			HttpServletResponse response, LongFeeRule longFeeRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		longFeeRuleService.findPageBySql(page, longFeeRule);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * LongFeeRule列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/longFeeRule/longFeeRule_list");
	}
	/**
	 * LongFeeRule查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/longFeeRule/longFeeRule_code");
	}
	/**
	 * 跳转到新增LongFeeRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增LongFeeRule页面");
		LongFeeRule longFeeRule = null;
		if (StringUtils.isNotBlank(id)){
			longFeeRule = longFeeRuleService.get(LongFeeRule.class, id);
			longFeeRule.setNo(null);
		}else{
			longFeeRule = new LongFeeRule();
		}
		longFeeRule.setM_umState("A");
		return new ModelAndView("admin/basic/longFeeRule/longFeeRule_save")
			.addObject("longFeeRule", longFeeRule);
	}
	
    @RequestMapping("/goCopy")
    public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, String id) {
 
        LongFeeRule longFeeRule = null;
        if (StringUtils.isNotBlank(id)) {
            longFeeRule = longFeeRuleService.get(LongFeeRule.class, id);
        } else {
            longFeeRule = new LongFeeRule();
        }
        
        longFeeRule.setM_umState("C");
        
        return new ModelAndView("admin/basic/longFeeRule/longFeeRule_save")
                .addObject("longFeeRule", longFeeRule);
    }
	
	/**
	 * 跳转到修改LongFeeRule页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改LongFeeRule页面");
		LongFeeRule longFeeRule = null;
		if (StringUtils.isNotBlank(id)){
			longFeeRule = longFeeRuleService.get(LongFeeRule.class, id);
			if (longFeeRule!=null && StringUtils.isNotBlank(longFeeRule.getSupplCode())){
				Supplier supplier  = longFeeRuleService.get(Supplier.class, longFeeRule.getSupplCode());
				if(supplier!=null){
					longFeeRule.setSupplDescr(supplier.getDescr());
				}
			}
		}else{
			longFeeRule = new LongFeeRule();
		}
		longFeeRule.setM_umState("M");
		return new ModelAndView("admin/basic/longFeeRule/longFeeRule_save")
			.addObject("longFeeRule", longFeeRule);
	}
	
	/**
	 * 跳转到查看LongFeeRule页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看LongFeeRule页面");
		LongFeeRule longFeeRule = longFeeRuleService.get(LongFeeRule.class, id);
		if (longFeeRule!=null && StringUtils.isNotBlank(longFeeRule.getSupplCode())){
			Supplier supplier  = longFeeRuleService.get(Supplier.class, longFeeRule.getSupplCode());
			if(supplier!=null){
				longFeeRule.setSupplDescr(supplier.getDescr());
			}
		}	
		longFeeRule.setM_umState("V");
		return new ModelAndView("admin/basic/longFeeRule/longFeeRule_save")
				.addObject("longFeeRule", longFeeRule);
	}
	/**
	 * 添加LongFeeRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, LongFeeRule longFeeRule){
		logger.debug("添加LongFeeRule开始");
		try {	
			longFeeRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.longFeeRuleService.doSaveProc(longFeeRule);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 修改LongFeeRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, LongFeeRule longFeeRule){
		logger.debug("修改LongFeeRule开始");
		try{
			longFeeRule.setLastUpdate(new Date());
			longFeeRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.longFeeRuleService.update(longFeeRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改LongFeeRule失败");
		}
	}
	
	/**
	 * 删除LongFeeRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除LongFeeRule开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "LongFeeRule编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				LongFeeRule longFeeRule = longFeeRuleService.get(LongFeeRule.class, deleteId);
				if(longFeeRule == null)
					continue;
				longFeeRule.setExpired("T");
				longFeeRuleService.update(longFeeRule);
			}
		}
		logger.debug("删除LongFeeRule IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *LongFeeRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, LongFeeRule longFeeRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		longFeeRuleService.findPageBySql(page, longFeeRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"LongFeeRule_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到新增明细页面
	 * @return
	 */
	@RequestMapping("/goAddDetail")
	public ModelAndView goAddDetail(HttpServletRequest request, HttpServletResponse response, LongFeeRule longFeeRule){
		logger.debug("跳转到新增明细页面");
		return new ModelAndView("admin/basic/longFeeRule/longFeeRule_detail_add")
				.addObject("longFeeRule", longFeeRule);
	}
	
	/**
	 * 添加匹配材料明细
	 * 
	 * @param request
	 * @param response
	 * @param longFeeRule
	 * @return
	 */
    @RequestMapping("/goAddItemDetail")
    public ModelAndView goAddItemDetail(HttpServletRequest request, HttpServletResponse response,
            LongFeeRule longFeeRule) {

        return new ModelAndView("admin/basic/longFeeRule/longFeeRule_itemDetail_add")
                .addObject("longFeeRule", longFeeRule);
    }
	
	/**
	 * 查询goDetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, LongFeeRule longFeeRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		longFeeRuleService.goDetailJqGrid(page, longFeeRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
    @RequestMapping("/goItemDetailJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goItemDetailJqGrid(HttpServletRequest request,
            HttpServletResponse response, LongFeeRule longFeeRule) {

        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        longFeeRuleService.goItemDetailJqGrid(page, longFeeRule);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
}
