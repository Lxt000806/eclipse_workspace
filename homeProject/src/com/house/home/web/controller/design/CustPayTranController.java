package com.house.home.web.controller.design;

import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.CustPayTran;
import com.house.home.service.design.CustPayTranService;

@Controller
@RequestMapping("/admin/custPayTran")
public class CustPayTranController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustPayTranController.class);

	@Autowired
	private CustPayTranService custPayTranService;
	
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
			HttpServletResponse response, CustPayTran custPayTran) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayTranService.findPageBySql(page, custPayTran);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustPayTran列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/custPayTran/custPayTran_list");
	}
	/**
	 * 客户付款变动明细
	 * @param request
	 * @param response
	 * @param custPayTran
	 */
	@RequestMapping("/doCustPayTran")
	public void doCustPayTran(HttpServletRequest request, HttpServletResponse response, CustPayTran custPayTran){
		logger.debug("保存客户付款变动明细开始");
		try{
			Result result = this.custPayTranService.doCustPayTran(custPayTran);
			ServletUtils.outPrintSuccess(request, response, result.getInfo());
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "无法保存客户付款变动明细");
		}
	}
	/**
	 *CustPayTran导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustPayTran custPayTran){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custPayTranService.findPageBySql(page, custPayTran);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"POS机刷卡对账_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 重打小票界面
	 * @author	created by zb
	 * @date	2019-5-31--上午10:38:55
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goRePrint")
	public ModelAndView goRePrint(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/finance/custPayTran/custPayTran_rePrint")
				.addObject("custPayTran", jsonObject);
	}
	/**
	 * 重打小票
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/doRePrint")
	public void updateOneStatus(HttpServletRequest request,
			HttpServletResponse response,CustPayTran custPayTran) {
		custPayTran.setLastUpdatedBy(getUserContext(request).getCzybh());
		CustPayTran cpt = this.custPayTranService.get(CustPayTran.class, custPayTran.getPk());
		if (null == cpt) {
			ServletUtils.outPrintFail(request, response, "不存在该数据");
			return;
		}
		String nowDate = DateUtil.DateToString(new Date());
		String rePrintRemarks = nowDate+"\n"+custPayTran.getReprintRemarks();
		if (StringUtils.isNotBlank(cpt.getReprintRemarks())) {
			rePrintRemarks = rePrintRemarks+"\n\n"+cpt.getReprintRemarks();
		}
		custPayTran.setReprintRemarks(rePrintRemarks);//保存重打说明 add by zb on 20190531
		custPayTranService.doRePrint(custPayTran);
	}
	/**
	 * 跳转到查看CustPayTran页面
	 * 
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/finance/custPayTran/custPayTran_view")
				.addObject("map", jsonObject);
	}
	/**
	 * 跳转到查看CustPayTran页面
	 * 
	 * @return
	 */
	@RequestMapping("/goUpdateProcedureFee")
	public ModelAndView goUpdateProcedureFee(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/finance/custPayTran/custPayTran_updateProcedureFee")
				.addObject("map", jsonObject);
	}
	
	/**
	 * 客户付款变动明细修改手续费
	 * @param request
	 * @param response
	 * @param custPayTran
	 */
	@RequestMapping("/doUpdateProcedureFee")
	public void doUpdateProcedureFee(HttpServletRequest request, HttpServletResponse response, CustPayTran custPayTran){
		logger.debug("修改手续费开始");
		try{
			Result result = custPayTranService.doUpdateProcedureFee(custPayTran);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "无法保存客户付款变动明细手续费");
		}
	}
	
	
}
