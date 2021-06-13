package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.HashMap;
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

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemAppDetail;
import com.house.home.entity.insales.PreItemAppSend;
import com.house.home.service.insales.ItemAppDetailService;
import com.house.home.service.insales.PreItemAppSendService;

@Controller
@RequestMapping("/admin/preItemAppSend")
public class PreItemAppSendController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PreItemAppSendController.class);

	@Autowired
	private PreItemAppSendService preItemAppSendService;
	@Autowired
	private ItemAppDetailService itemAppDetailService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, PreItemAppSend preItemAppSend) throws Exception {
		UserContext uc = getUserContext(request);
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		preItemAppSendService.findPageBySql(page, preItemAppSend,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 材料详细
	 * @author	created by zb
	 * @date	2019-4-8--下午5:28:30
	 * @param request
	 * @param response
	 * @param preItemAppSend
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemJqGrid(HttpServletRequest request,
			HttpServletResponse response, PreItemAppSend preItemAppSend) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		preItemAppSendService.findItemPageBySql(page, preItemAppSend);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 发货申请明细——新增
	 * @author	created by zb
	 * @date	2019-4-11--上午11:26:48
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSendDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSendDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppDetail itemAppDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(1000000);
		itemAppDetailService.findSendDetailBySql(page, itemAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 根据reqpk获取发货申请明细
	 * @author	created by zb
	 * @date	2019-4-11--下午12:01:14
	 * @param request
	 * @param response
	 * @param preItemAppSend
	 * @return
	 */
	@RequestMapping("/getSendDetail")
	@ResponseBody
	public Map<String, Object> getSendDetail(HttpServletRequest request, HttpServletResponse response,
			PreItemAppSend preItemAppSend) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		preItemAppSendService.findItemPageBySql(page, preItemAppSend);
		if (page.getResult().size()>0) {
			return page.getResult().get(0);
		}
		return null;
	}
	/**
	 * 状态是否发生改变
	 * @author	created by zb
	 * @date	2019-4-23--上午11:54:34
	 * @param request
	 * @param response
	 * @param preItemAppSend
	 */
	@RequestMapping("/selectPreItemAppSend")
	@ResponseBody
	public void selectPreItemAppSend(HttpServletRequest request, HttpServletResponse response,
			PreItemAppSend preItemAppSend) {
		PreItemAppSend pias = preItemAppSendService.get(PreItemAppSend.class, preItemAppSend.getNo());
		if (null == pias || !pias.getStatus().equals(preItemAppSend.getStatus())) {
			ServletUtils.outPrintFail(request, response, "发货申请单【"+preItemAppSend.getNo()+"】状态发生改变，请刷新数据");
		}
		ServletUtils.outPrintSuccess(request, response);
	}
	/**
	 * 判断是否部分发货
	 * @author	created by zb
	 * @date	2019-4-23--下午2:59:58
	 * @param request
	 * @param response
	 * @param preItemAppSend
	 */
	@RequestMapping("/isHaveSend")
	@ResponseBody
	public void isHaveSend(HttpServletRequest request, HttpServletResponse response,
			PreItemAppSend preItemAppSend) {
		List<Map<String,Object>> haveSend = preItemAppSendService.isHaveSend(preItemAppSend);
		if (haveSend.size()>0) {
			ServletUtils.outPrintFail(request, response, "仓库发货申请单已部分发货");
		}
		ServletUtils.outPrintSuccess(request, response);
	}
	/**
	 * PreItemAppSend列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_list");
	}
	/**
	 * 跳转到新增PreItemAppSend页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("跳转到新增PreItemAppSend页面");
		Map<String,Object> map = new HashMap<String,Object>();
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_win")
			.addObject("m_umState", preItemAppSend.getM_umState())
			.addObject("preItemAppSend", map);
	}
	/**
	 * 跳转到修改PreItemAppSend页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("跳转到修改PreItemAppSend页面");
		Map<String, Object> map = goWinMap(request, preItemAppSend);
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_win")
			.addObject("m_umState", preItemAppSend.getM_umState())
			.addObject("preItemAppSend", map);
	}
	@RequestMapping("/goCancel")
	public ModelAndView goCancel(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("跳转到取消PreItemAppSend页面");
		Map<String, Object> map = goWinMap(request, preItemAppSend);
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_win")
			.addObject("m_umState", preItemAppSend.getM_umState())
			.addObject("preItemAppSend", map);
	}
	@RequestMapping("/goReturn")
	public ModelAndView goReturn(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("跳转到退回PreItemAppSend页面");
		Map<String, Object> map = goWinMap(request, preItemAppSend);
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_win")
			.addObject("m_umState", preItemAppSend.getM_umState())
			.addObject("preItemAppSend", map);
	}
	@RequestMapping("/goSend")
	public ModelAndView goSend(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("跳转到发货PreItemAppSend页面");
		Map<String, Object> map = goWinMap(request, preItemAppSend);
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_win")
			.addObject("m_umState", preItemAppSend.getM_umState())
			.addObject("preItemAppSend", map);
	}
	@RequestMapping("/goPartSend")
	public ModelAndView goPartSend(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("跳转到部分发货PreItemAppSend页面");
		Map<String, Object> map = goWinMap(request, preItemAppSend);
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_win")
			.addObject("m_umState", preItemAppSend.getM_umState())
			.addObject("preItemAppSend", map);
	}
	/**
	 * 跳转到查看PreItemAppSend页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("跳转到查看PreItemAppSend页面");
		Map<String, Object> map = goWinMap(request, preItemAppSend);
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_win")
			.addObject("m_umState", preItemAppSend.getM_umState())
			.addObject("preItemAppSend", map);
	}
	/**
	 * 根据no获取数据
	 * @author	created by zb
	 * @date	2019-4-23--下午3:44:40
	 * @param request
	 * @param preItemAppSend
	 * @return
	 */
	private Map<String, Object> goWinMap(HttpServletRequest request,PreItemAppSend preItemAppSend) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(preItemAppSend.getNo())) {
			UserContext uc = getUserContext(request);
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			preItemAppSendService.findPageBySql(page, preItemAppSend,uc);
			map = page.getResult().get(0);
			map.put("status", map.get("status").toString().trim());
			map.put("itemtype1", map.get("itemtype1").toString().trim());
		} else {
			map.put("no", "获取不到该数据");
		}
		return map;
	}
	/**
	 * 跳转到发货申请明细页面
	 * @author	created by zb
	 * @date	2019-4-10--下午4:55:33
	 * @param request
	 * @param response
	 * @param m_umState
	 * @param keys
	 * @return
	 */
	@RequestMapping("/goSendDetail")
	public ModelAndView goSendDetail(HttpServletRequest request, HttpServletResponse response, 
			String m_umState, String keys, String no){
		logger.debug("跳转到发货申请明细页面");
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_sendDetail")
				.addObject("m_umState", m_umState)
				.addObject("keys", keys)
				.addObject("no", no.trim());
	}
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("跳转到打印页面");
		return new ModelAndView("admin/insales/preItemAppSend/preItemAppSend_print")
				.addObject("preItemAppSend", preItemAppSend);
	}
	/**
	 * PreItemAppSend存储过程
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PreItemAppSend preItemAppSend){
		logger.debug("保存存储过程开始");
		try{
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "无发货申请明细");
				return;
			}
			if (StringUtils.isBlank(preItemAppSend.getWhCode())) {
				ServletUtils.outPrintFail(request, response, "仓库编号为空，请重新选择。");
				return;
			}
			UserContext uc = getUserContext(request);
			preItemAppSend.setAppCzy(uc.getEmnum());
			preItemAppSend.setLastUpdatedBy(uc.getCzybh());
			Result result = this.preItemAppSendService.doSave(preItemAppSend);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	/**
	 * 仓库分批发货存储过程
	 * @author	created by zb
	 * @date	2019-4-19--下午2:40:15
	 * @param request
	 * @param response
	 * @param preItemAppSend
	 */
	@RequestMapping("/doCkfhsqSend")
	public void doCkfhsqSend(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("仓库分批发货存储过程开始");
		try{
			UserContext uc = getUserContext(request);
			preItemAppSend.setSendCzy(uc.getCzybh());
			preItemAppSend.setSendBachNo(
					StringUtils.isNotBlank(preItemAppSend.getSendBachNo())?
							preItemAppSend.getSendBachNo().trim():"");
			Result result = this.preItemAppSendService.doCkfhsqSend(preItemAppSend);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	/**
	 * 部分发货存储过程
	 * @author	created by zb
	 * @date	2019-4-24--下午3:50:23
	 * @param request
	 * @param response
	 * @param preItemAppSend
	 */
	@RequestMapping("/doCkfhsqSendByPart")
	public void doCkfhsqSendByPart(HttpServletRequest request, HttpServletResponse response, 
			PreItemAppSend preItemAppSend){
		logger.debug("仓库部分发货存储过程开始");
		try{
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "无发货申请明细");
				return;
			}
			UserContext uc = getUserContext(request);
			preItemAppSend.setSendCzy(uc.getCzybh());
			preItemAppSend.setSendBachNo(
					StringUtils.isNotBlank(preItemAppSend.getSendBachNo())?
							preItemAppSend.getSendBachNo().trim():"");
			Result result = this.preItemAppSendService.doCkfhsqSendByPart(preItemAppSend);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	/**
	 *PreItemAppSend导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PreItemAppSend preItemAppSend){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		preItemAppSendService.findPageBySql(page, preItemAppSend,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"仓库发货申请_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
