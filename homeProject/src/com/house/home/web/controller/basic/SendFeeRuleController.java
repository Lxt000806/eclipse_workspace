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
import com.house.home.entity.basic.SendFeeRule;
import com.house.home.service.basic.SendFeeRuleService;

@Controller
@RequestMapping("/admin/sendFeeRule")
public class SendFeeRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SendFeeRuleController.class);

	@Autowired
	private SendFeeRuleService sendFeeRuleService;

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
			HttpServletResponse response, SendFeeRule sendFeeRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sendFeeRuleService.findPageBySql(page, sendFeeRule);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SendFeeRule列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/sendFeeRule/sendFeeRule_list");
	}
	/**
	 * SendFeeRule查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/sendFeeRule/sendFeeRule_code");
	}
	/**
	 * 跳转到新增SendFeeRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SendFeeRule页面");
		SendFeeRule sendFeeRule = null;
		if (StringUtils.isNotBlank(id)){
			sendFeeRule = sendFeeRuleService.get(SendFeeRule.class, id);
			sendFeeRule.setNo(null);
		}else{
			sendFeeRule = new SendFeeRule();
		}
		sendFeeRule.setM_umState("A");
		return new ModelAndView("admin/basic/sendFeeRule/sendFeeRule_save")
			.addObject("sendFeeRule", sendFeeRule);
	}
	
    @RequestMapping("/goCopy")
    public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, String id) {

        SendFeeRule sendFeeRule = null;
        if (StringUtils.isNotBlank(id)) {
            sendFeeRule = sendFeeRuleService.get(SendFeeRule.class, id);
        } else {
            sendFeeRule = new SendFeeRule();
        }
        
        sendFeeRule.setM_umState("C");
        sendFeeRule.setNo(null);
        
        return new ModelAndView("admin/basic/sendFeeRule/sendFeeRule_save")
                .addObject("sendFeeRule", sendFeeRule);
    }
	
	/**
	 * 跳转到修改SendFeeRule页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改SendFeeRule页面");
		SendFeeRule sendFeeRule = null;
		if (StringUtils.isNotBlank(id)){
			sendFeeRule = sendFeeRuleService.get(SendFeeRule.class, id);
		}else{
			sendFeeRule = new SendFeeRule();
		}
		sendFeeRule.setM_umState("M");
		return new ModelAndView("admin/basic/sendFeeRule/sendFeeRule_save")
			.addObject("sendFeeRule", sendFeeRule);
	}
	
	/**
	 * 跳转到查看SendFeeRule页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SendFeeRule页面");
		SendFeeRule sendFeeRule = sendFeeRuleService.get(SendFeeRule.class, id);
		sendFeeRule.setM_umState("V");
		return new ModelAndView("admin/basic/sendFeeRule/sendFeeRule_save")
				.addObject("sendFeeRule", sendFeeRule);
	}
	/**
	 * 添加SendFeeRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SendFeeRule sendFeeRule){
		logger.debug("添加SendFeeRule开始");
		try {	
			sendFeeRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.sendFeeRuleService.doSaveProc(sendFeeRule);
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
	 * 修改SendFeeRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SendFeeRule sendFeeRule){
		logger.debug("修改SendFeeRule开始");
		try{
			sendFeeRule.setLastUpdate(new Date());
			sendFeeRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.sendFeeRuleService.update(sendFeeRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改SendFeeRule失败");
		}
	}
	
	/**
	 * 删除SendFeeRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response,String deleteIds,SendFeeRule sendFeeRule){
		logger.debug("删除SendFeeRule开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SendFeeRule编号不能为空,删除失败");
			return;
		}
		sendFeeRule.setM_umState("D");
		sendFeeRule.setNo(deleteIds);
		sendFeeRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		Result result = this.sendFeeRuleService.doSaveProc(sendFeeRule);
		if (result.isSuccess()){
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}else{
			ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
		}
		
	}

	/**
	 *SendFeeRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SendFeeRule sendFeeRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		sendFeeRuleService.findPageBySql(page, sendFeeRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"SendFeeRule_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 添加匹配材料
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItem")
	public ModelAndView goItem(HttpServletRequest request,
			HttpServletResponse response,SendFeeRule sendFeeRule) throws Exception {

		return new ModelAndView("admin/basic/sendFeeRule/sendFeeRule_item_add").addObject("sendFeeRule", sendFeeRule);
	}
	/**
	 * 查询ItemJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemJqGrid(HttpServletRequest request,
			HttpServletResponse response, SendFeeRule sendFeeRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sendFeeRuleService.findItemPageBySql(page, sendFeeRule);
		return new WebPage<Map<String,Object>>(page);
	}
}
