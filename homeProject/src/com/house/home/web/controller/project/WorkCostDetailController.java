package com.house.home.web.controller.project;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.CutCheckOut;
import com.house.home.entity.project.PreWorkCostDetail;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.project.WorkCostDetail;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.project.PreWorkCostDetailService;
import com.house.home.service.project.WorkCostDetailService;

@Controller
@RequestMapping("/admin/workCostDetail")
public class WorkCostDetailController extends BaseController {
	
	@Autowired
	private CzybmService czybmService;

	private static final Logger logger = LoggerFactory
			.getLogger(WorkCostDetailController.class);

	@Autowired
	private WorkCostDetailService workCostDetailService;
	@Autowired
	private PreWorkCostDetailService preWorkCostDetailService;
	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail)
			throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workCostDetailService.findPageBySql(page, workCostDetail);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCardJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goCardJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			WorkCostDetail workCostDetail) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workCostDetailService.findCardListBySql(page, workCostDetail);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * WorkCostDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(
				"admin/project/workCostDetail/workCostDetail_list");
	}

	/**
	 * WorkCostDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(
				"admin/project/workCostDetail/workCostDetail_code");
	}

	/**
	 * 跳转到新增WorkCostDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("跳转到新增WorkCostDetail页面");
		List<Map<String,Object>> list = workCostDetailService.isFz();
		if (StringUtils.isNotBlank(workCostDetail.getSalaryType()))
			workCostDetail.setSalaryType(workCostDetail.getSalaryType().trim());
		workCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
		Xtcs xtcs=workCostDetailService.get(Xtcs.class, "AftCustCode");
		
		if(list.size()>0){
			workCostDetail.setIsFz("1");
		}else{
			workCostDetail.setIsFz("0");
		}
		Customer customer=null;
		if(StringUtils.isNotBlank(workCostDetail.getCustCode())){
			customer=workCostDetailService.get(Customer.class,workCostDetail.getCustCode());
		}
		return new ModelAndView("admin/project/workCost/workCost_detail_add")
				.addObject("workCostDetail", workCostDetail).addObject("AftCustCode", xtcs.getQz())
				.addObject("customer",customer);
	}

	/**
	 * 跳转收支明细页面
	 * 
	 * @return
	 */
	@RequestMapping("/goIncome")
	public ModelAndView goIncome(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("跳转到收支明细页面");
		return new ModelAndView("admin/project/workCost/workCost_income")
				.addObject("workCostDetail", workCostDetail);
	}

	/**
	 * 跳转到查看WorkCostDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到查看WorkCostDetail页面");
		WorkCostDetail workCostDetail = workCostDetailService.get(
				WorkCostDetail.class, Integer.parseInt(id));

		return new ModelAndView(
				"admin/project/workCostDetail/workCostDetail_detail")
				.addObject("workCostDetail", workCostDetail);
	}

	/**
	 * 添加WorkCostDetail
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("添加WorkCostDetail开始");
		try {
			this.workCostDetailService.save(workCostDetail);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "添加WorkCostDetail失败");
		}
	}

	/**
	 * 修改WorkCostDetail
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("修改WorkCostDetail开始");
		try {
			workCostDetail.setLastUpdate(new Date());
			workCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.workCostDetailService.update(workCostDetail);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改WorkCostDetail失败");
		}
	}

	/**
	 * 删除WorkCostDetail
	 * 
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request,
			HttpServletResponse response, String deleteIds) {
		logger.debug("删除WorkCostDetail开始");
		if (StringUtils.isBlank(deleteIds)) {
			ServletUtils.outPrintFail(request, response,
					"WorkCostDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for (String deleteId : deleteIdList) {
			if (deleteId != null) {
				WorkCostDetail workCostDetail = workCostDetailService.get(
						WorkCostDetail.class, Integer.parseInt(deleteId));
				if (workCostDetail == null)
					continue;
				workCostDetail.setExpired("T");
				workCostDetailService.update(workCostDetail);
			}
		}
		logger.debug("删除WorkCostDetail IDS={} 完成", deleteIdList);
		ServletUtils.outPrintSuccess(request, response, "删除成功");
	}

	/**
	 * profitPerf导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workCostDetailService.findPageBySql2(page, workCostDetail);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"基础人工成本明细查询_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}
	
	/**
	 * profitPerf导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doCheckOutExcel")
	public void doCheckOutExcel(HttpServletRequest request,
			HttpServletResponse response, WorkCost workCost) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workCostDetailService.goCheckOutJqGrid(page, workCost);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"基础人工成本出账查询_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

	/**
	 * 统计单项工种余额、单项工种发包、单项工种累计支出、总发包余额、总发包、总支出
	 * 
	 * @param custCode
	 *            ,workType2
	 * @return
	 */
	@RequestMapping("/findCostByCodeWork")
	@ResponseBody
	public Map<String, Object> findCostByCodeWork(HttpServletRequest request,
			HttpServletResponse response, String custCode, String workType2)
			throws Exception {
		Map<String, Object> map = workCostDetailService.findCostByCodeWork(
				custCode, workType2);
		return map;
	}

	/**
	 * 查询prj相关,合同总价，已领工资
	 * 
	 * @param custCode
	 *            ,workType2
	 * @return
	 */
	@RequestMapping("/findPrjByCodeWork")
	@ResponseBody
	public Map<String, Object> findPrjByCodeWork(HttpServletRequest request,
			HttpServletResponse response, String custCode, String workType2)
			throws Exception {
		PreWorkCostDetail preWorkCostDetail=new PreWorkCostDetail();
		preWorkCostDetail.setCustCode(custCode);
		preWorkCostDetail.setWorkType2(workType2);
		Map<String, Object> totalMap = new HashMap<String, Object>();
		String workerPlanOffer= preWorkCostDetailService.getQuotaSalary(preWorkCostDetail);
		totalMap.put("WorkerPlanOffer", workerPlanOffer);
		Map<String, Object> map = workCostDetailService.findPrjByCodeWork(
				custCode, workType2);
		Map<String, Object> map1 = workCostDetailService
				.findTotalAcountByCodeWork(custCode, workType2);
		Map<String, Object> map2 = workCostDetailService
				.findGotAcountByCodeWork(custCode, workType2);
		if (map != null)
			totalMap.putAll(map);
		if (map1 != null)
			totalMap.putAll(map1);
		if (map2 != null)
			totalMap.putAll(map2);
		return totalMap;
	}

	/**
	 * 预扣金额计算
	 * 
	 */
	@RequestMapping("/findYukou")
	@ResponseBody
	public Map<String, Object> findYukou(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("预扣金额计算");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (workCostDetail.getWithHoldNo() != 0
					&& workCostDetail.getWithHoldNo() != null) {
				map = workCostDetailService.findYukou(workCostDetail);
			} else {
				map.put("ret", 0);
				map.put("Amount", 0);
			}
		} catch (Exception e) {
			
		}
		return map;
	}

	/**
	 * 从id对应的表根据code查descr
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDescr")
	@ResponseBody
	public Map<String, Object> findDescr(HttpServletRequest request,
			HttpServletResponse response, String value, String id)
			throws Exception {
		Map<String, Object> map = workCostDetailService.findDescr(value, id);
		return map;

	}

	/**
	 * 跳转到新增WorkCostDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/addFromPre")
	public ModelAndView addFromPre(HttpServletRequest request,
			HttpServletResponse response, PreWorkCostDetail preWorkCostDetail) {
		logger.debug("跳转到新增WorkCostDetail页面");
		// 整理预申请pk，去除空值
		if (StringUtils.isNotBlank(preWorkCostDetail.getRefPrePks())) {
			String[] array = preWorkCostDetail.getRefPrePks().split(",");
			String pks = "";
			for (int i = 0; i < array.length; i++) {
				if (StringUtils.isNotBlank(array[i]) || "0".equals(array[i])) {
					pks += array[i] + ",";
				}
			}
			if (StringUtils.isNotBlank(pks)) {
				preWorkCostDetail.setRefPrePks(pks.substring(0,
						pks.length() - 1));
			} else {
				preWorkCostDetail.setRefPrePks("");
			}
		}
		preWorkCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView(
				"admin/project/workCost/workCost_detail_addFromPre").addObject(
				"preWorkCostDetail", preWorkCostDetail);
	}

	/**
	 * 跳转到防水工资及补贴选择时间页面
	 * 
	 * @return
	 */
	@RequestMapping("/goSubsidy")
	public ModelAndView goSubsidy(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("跳转到新增WorkCostDetail页面");
		workCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView("admin/project/workCost/workCost_subsidy")
				.addObject("workCostDetail", workCostDetail);
	}

	/**
	 * 执行生成防水工资及补贴
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSubsidy")
	public List<Map<String, Object>> doSubsidy(HttpServletRequest request,
			HttpServletResponse response, String year, String month,
			WorkCostDetail workCostDetail) {
		logger.debug("跳转到新增WorkCostDetail页面");
		workCostDetail.setLastUpdate(new Date());
		workCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
		workCostDetail.setActionLog("ADD");
		workCostDetail.setExpired("F");
		List<Map<String, Object>> list = workCostDetailService.findSubsidyByDate(year, month,workCostDetail);
		return list;
	}

	/**
	 * 跳转到明细查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("跳转到新增WorkCostDetail页面");
		return new ModelAndView("admin/project/workCost/workCost_detailQuery");
	}

	/**
	 * 查询JqGrid2表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid2")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid2(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail)
			throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workCostDetailService.findPageBySql2(page, workCostDetail);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * 基础人工成本明细--取消
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/doCancel")
	public void doCancel(HttpServletRequest request,
			HttpServletResponse response, String pk, String confirmRemark,
			String refPrePk) {
		logger.debug("集成进度明细--取消");
		workCostDetailService.cancel(pk, getUserContext(request).getCzybh(),
				confirmRemark, refPrePk);
	}

	/**
	 * 基础人工成本--恢复
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/doRenew")
	public void doRenew(HttpServletRequest request,
			HttpServletResponse response, String pk, String confirmRemark,
			String refPrePk) {
		logger.debug("集成进度明细--恢复");
		workCostDetailService.renew(pk, getUserContext(request).getCzybh(),
				confirmRemark, refPrePk);
	}

	/**
	 * 是否允许进行非预扣的工人工资审批
	 * 
	 * @param request
	 * @param response
	 * @param workCostDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isAllowConfirm")
	public List<Map<String, Object>> isAllowConfirm(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("是否允许进行非预扣的工人工资审批");
		List<Map<String, Object>> list1 = workCostDetailService
				.hasStatus3(workCostDetail);
		if (list1.isEmpty() && list1 != null) {
			List<Map<String, Object>> list2 = workCostDetailService
					.isAllowConfirm(workCostDetail);
			return list2;
		}
		return null;
	}

	/**
	 * 质保金超出的工人
	 * 
	 * @param request
	 * @param response
	 * @param workCostDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/overQualityFeeWorker")
	public List<Map<String, Object>> overQualityFeeWorker(
			HttpServletRequest request, HttpServletResponse response,
			WorkCostDetail workCostDetail) {
		logger.debug("质保金超出的工人");
		List<Map<String, Object>> list = workCostDetailService
				.overQualityFeeWorker(workCostDetail);
		return list;
	}

	/**
	 * 优秀班组奖励累计总额超过单项人工发包额的10%
	 * 
	 * @param request
	 * @param response
	 * @param workCostDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/overCustCtrl")
	public List<Map<String, Object>> overCustCtrl(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("优秀班组奖励累计总额超过单项人工发包额的10%");
		List<Map<String, Object>> list = workCostDetailService
				.overCustCtrl(workCostDetail);
		return list;
	}

	/**
	 * 预扣领取金额超过 预扣金额-预扣已发放金额
	 * 
	 * @param request
	 * @param response
	 * @param workCostDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/overWithHold")
	public List<Map<String, Object>> overWithHold(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("预扣领取金额超过 预扣金额-预扣已发放金额");
		List<Map<String, Object>> list = workCostDetailService
				.overWithHold(workCostDetail);
		return list;
	}

	/**
	 * 保存时需要的前提条件
	 * 
	 * @param request
	 * @param response
	 * @param workCostDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/beforeSave")
	public Map<String, Object> beforeSave(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("保存时需要的前提条件");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list1 = workCostDetailService.isFz();
		// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//		workCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
//		List<Map<String, Object>> list2 = workCostDetailService.hasAddManageRight(workCostDetail);
		String checkWorkType1 = workCostDetailService.isMultiWorkType1(workCostDetail);
		map.put("isFz", list1.size() > 0 ? "1" : "0");
		boolean hasAddManageRight = czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0316", "新增管理"); // list2.size() > 0;
		map.put("hasAddManageRight", hasAddManageRight ? "1" : "0");
		if (checkWorkType1 != null)
			map.put("checkWorkType1", checkWorkType1.trim());
		return map;
	}

	/**
	 * 工种类型1，2 二级联动
	 * 
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workTypeByAuthority/{type}/{pCode}")
	@ResponseBody
	public JSONObject getWorkTypeByAuthority(@PathVariable Integer type,
			@PathVariable String pCode, HttpServletRequest request) {
		UserContext uc = (UserContext) request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT_KEY);
		List<Map<String, Object>> regionList = this.workCostDetailService
				.findWorkTypeByAuthority(type, pCode, uc);
		return this.out(regionList, true);
	}
	/**
	 * 基础人工成本明细--审核
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/doCheck")
	public void doCheck(HttpServletRequest request,
			HttpServletResponse response, String pk, String col,String value) {
		logger.debug("基础人工成本明细--审核");
		workCostDetailService.doCheck(pk, col, value);
	}
	/**
	 * 跳转到生成水电材料奖惩页面
	 * 
	 * @return
	 */
	@RequestMapping("/goWaterItem")
	public ModelAndView goWaterItem(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("跳转到生成水电材料奖惩页面");
		workCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView(
				"admin/project/workCost/workCost_detail_waterItem")
				.addObject("workCostDetail", workCostDetail);
	}
	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWaterItemJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goWaterItemJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			WorkCostDetail workCostDetail) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workCostDetailService.createWaterItem(page, workCostDetail);
		return new WebPage<Map<String, Object>>(page);
	}
	/**
	 * 是否已生成水电材料奖惩
	 * 
	 * @param request
	 * @param response
	 * @param workCostDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isCreatedWaterItem")
	public List<Map<String, Object>> isCreatedWaterItem(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("是否已生成水电材料奖惩");
		List<Map<String, Object>> list = workCostDetailService.isCreatedWaterItem(workCostDetail);
		return list;
	}
	/**
	 * profitPerf导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doWaterItemExcel")
	public void doWaterItemExcel(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workCostDetailService.createWaterItem(page, workCostDetail);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"水电材料奖惩_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}
	/**
	 * 跳转到定额工资详细页面
	 * @author cjg
	 * @date 2019-7-30
	 * @param request
	 * @param response
	 * @param preWorkCostDetail
	 * @return
	 */
	@RequestMapping("/goDeDetail")
	public ModelAndView goDeDetail(HttpServletRequest request, HttpServletResponse response, 
			WorkCostDetail workCostDetail){
		logger.debug("跳转到查看定额工资详细页面");
		
		return new ModelAndView("admin/project/workCost/workCost_workerPlanOffer")
				.addObject("workCostDetail", workCostDetail);
	}
	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goDeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			WorkCostDetail workCostDetail) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workCostDetailService.goDeJqGrid(page, workCostDetail);
		return new WebPage<Map<String, Object>>(page);
	}
	/**
	 * 跳转到批量生成明细选择时间页面
	 * 
	 * @return
	 */
	@RequestMapping("/goBatchCrtDetail")
	public ModelAndView goWaterPicFee(HttpServletRequest request,
			HttpServletResponse response, WorkCostDetail workCostDetail) {
		logger.debug("跳转到新增WorkCostDetail页面");
		workCostDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
		System.out.println(workCostDetail.getJsonString());
		return new ModelAndView("admin/project/workCost/workCost_detail_batchCrtDetail")
				.addObject("workCostDetail", workCostDetail);
	}
	/**
	 * 执行生成拍照费
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doBatchCrtDetail")
	public List<Map<String, Object>> dobatchCrtDetail(HttpServletRequest request,
			HttpServletResponse response, String year, String month,
			WorkCostDetail workCostDetail) {
		logger.debug("跳转到新增WorkCostDetail页面");
		List<Map<String, Object>> list = workCostDetailService.findBatchCrtDetailByDate(year, month,workCostDetail);
		return list;
	}
	
	/**
	 * 查询goMemberJqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goMemberJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goMemberJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			WorkCostDetail workCostDetail) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workCostDetailService.goMemberJqGrid(page, workCostDetail);
		return new WebPage<Map<String, Object>>(page);
	}
	
	/**
	 * 查询goCheckOutJqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCheckOutJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goCheckOutJqGrid(HttpServletRequest request, HttpServletResponse response,
			WorkCost workCost) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workCostDetailService.goCheckOutJqGrid(page, workCost);
		return new WebPage<Map<String, Object>>(page);
	}
	
	/**
	 * 执行生成防水工资及补贴
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMemberSalary")
	public List<Map<String, Object>> getMemberSalary(HttpServletRequest request,
			HttpServletResponse response,WorkCostDetail workCostDetail) {
		List<Map<String, Object>> list = workCostDetailService.getMemberSalary(workCostDetail);
		return list;
	}
	
	/**
	 * 工资出账处理保存
	 * @param request
	 * @param response
	 * @param workCostDetail
	 */
	@RequestMapping("/doWorkerCostProvide")
	@ResponseBody
	public void doWorkerCostProvide(HttpServletRequest request,HttpServletResponse response,WorkCostDetail workCostDetail){
		logger.debug("工资出账处理保存");
		try {
			workCostDetail.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.workCostDetailService.doWorkerCostProvide(workCostDetail);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 劳务公司
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getLaborCompny")
	@ResponseBody
	public List<Map<String, Object>> getCutTypeBySize(HttpServletRequest request,HttpServletResponse response,CutCheckOut cutCheckOut){
		List<Map<String, Object>>list=this.workCostDetailService.getLaborCompny();
		return list;
	}
}
