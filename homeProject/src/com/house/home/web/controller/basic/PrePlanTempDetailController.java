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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.PrePlanTempDetail;
import com.house.home.service.basic.PrePlanTempDetailService;

@Controller
@RequestMapping("/admin/prePlanTempDetail")
public class PrePlanTempDetailController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(PrePlanTempDetailController.class);

	@Autowired
	private PrePlanTempDetailService prePlanTempDetailService;

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
	public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrePlanTempDetail prePlanTempDetail)
			throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		prePlanTempDetailService.findPageBySql(page, prePlanTempDetail);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * PrePlanTempDetail列表
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
				"admin/basic/prePlanTempDetail/prePlanTempDetail_list");
	}

	/**
	 * PrePlanTempDetail查询code
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
				"admin/basic/prePlanTempDetail/prePlanTempDetail_code");
	}

	/**
	 * 跳转到新增PrePlanTempDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response,PrePlanTempDetail prePlanTempDetail) {
		logger.debug("跳转到新增PrePlanTempDetail页面");
		if("A".equals(prePlanTempDetail.getM_umState())){
			prePlanTempDetail.setMarkup(100.00);
			prePlanTempDetail.setIsService(0);
			prePlanTempDetail.setProcessCost(0);
			prePlanTempDetail.setAlgorithmDeduct(0.0);
			prePlanTempDetail.setAlgorithmPer(1.0);
			prePlanTempDetail.setCanModiQty("1");
		}
		return new ModelAndView(
				"admin/basic/prePlanTemp/prePlanTemp_detail_add")
		.addObject("prePlanTempDetail", prePlanTempDetail);
	}

	/**
	 * 跳转到复制PrePlanTempDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("跳转到复制PrePlanTempDetail页面");
		return new ModelAndView(
				"admin/basic/prePlanTemp/prePlanTemp_detail_copy");
	}

	/**
	 * 跳转到修改PrePlanTempDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到修改PrePlanTempDetail页面");
		PrePlanTempDetail prePlanTempDetail = null;
		if (StringUtils.isNotBlank(id)) {
			prePlanTempDetail = prePlanTempDetailService.get(
					PrePlanTempDetail.class, Integer.parseInt(id));
		} else {
			prePlanTempDetail = new PrePlanTempDetail();
		}

		return new ModelAndView(
				"admin/basic/prePlanTempDetail/prePlanTempDetail_update")
				.addObject("prePlanTempDetail", prePlanTempDetail);
	}

	/**
	 * 跳转到查看PrePlanTempDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到查看PrePlanTempDetail页面");
		PrePlanTempDetail prePlanTempDetail = prePlanTempDetailService.get(
				PrePlanTempDetail.class, Integer.parseInt(id));

		return new ModelAndView(
				"admin/basic/prePlanTempDetail/prePlanTempDetail_detail")
				.addObject("prePlanTempDetail", prePlanTempDetail);
	}

	/**
	 * 添加PrePlanTempDetail
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, PrePlanTempDetail prePlanTempDetail) {
		logger.debug("添加PrePlanTempDetail开始");
		try {
			this.prePlanTempDetailService.save(prePlanTempDetail);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response,
					"添加PrePlanTempDetail失败");
		}
	}

	/**
	 * 修改PrePlanTempDetail
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, PrePlanTempDetail prePlanTempDetail) {
		logger.debug("修改PrePlanTempDetail开始");
		try {
			prePlanTempDetail.setLastUpdate(new Date());
			prePlanTempDetail.setLastUpdatedBy(getUserContext(request)
					.getCzybh());
			this.prePlanTempDetailService.update(prePlanTempDetail);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response,
					"修改PrePlanTempDetail失败");
		}
	}

	/**
	 * 删除PrePlanTempDetail
	 * 
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request,
			HttpServletResponse response, String deleteIds) {
		logger.debug("删除PrePlanTempDetail开始");
		if (StringUtils.isBlank(deleteIds)) {
			ServletUtils.outPrintFail(request, response,
					"PrePlanTempDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for (String deleteId : deleteIdList) {
			if (deleteId != null) {
				PrePlanTempDetail prePlanTempDetail = prePlanTempDetailService
						.get(PrePlanTempDetail.class,
								Integer.parseInt(deleteId));
				if (prePlanTempDetail == null)
					continue;
				prePlanTempDetail.setExpired("T");
				prePlanTempDetailService.update(prePlanTempDetail);
			}
		}
		logger.debug("删除PrePlanTempDetail IDS={} 完成", deleteIdList);
		ServletUtils.outPrintSuccess(request, response, "删除成功");
	}

	/**
	 * PrePlanTempDetail导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, PrePlanTempDetail prePlanTempDetail) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prePlanTempDetailService.findPageBySql(page, prePlanTempDetail);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(
				request,
				response,
				page.getResult(),
				"PrePlanTempDetail_"
						+ DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

	/**
	 * 根据no查找快速预报价明细
	 * 
	 * @param request
	 * @param response
	 * @param prePlanTempDetail
	 */
	@RequestMapping("/findDetailByNo")
	@ResponseBody
	public List<Map<String, Object>> findDetailByNo(HttpServletRequest request,
			HttpServletResponse response, PrePlanTempDetail prePlanTempDetail) {
		List<Map<String, Object>>list=prePlanTempDetailService.findDetailByNo(prePlanTempDetail);
		return list;
	}
	/**
	 * 根据切割类型匹配瓷砖尺寸
	 * 
	 * @param request
	 * @param response
	 * @param prePlanTempDetail
	 */
	@RequestMapping("/getQtyByCutType")
	@ResponseBody
	public List<Map<String, Object>> getQtyByCutType(HttpServletRequest request,
			HttpServletResponse response, PrePlanTempDetail prePlanTempDetail) {
		List<Map<String, Object>>list=prePlanTempDetailService.getQtyByCutType(prePlanTempDetail);
		return list;
	}
}
