package com.house.home.web.controller.basic;

import java.util.Date;
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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BasePlanTempDetail;
import com.house.home.service.basic.BasePlanTempDetailService;

@Controller
@RequestMapping("/admin/basePlanTempDetail")
public class BasePlanTempDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BasePlanTempDetailController.class);

	@Autowired
	private BasePlanTempDetailService basePlanTempDetailService;

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
			HttpServletResponse response, BasePlanTempDetail basePlanTempDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		basePlanTempDetailService.findPageBySql(page, basePlanTempDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BasePlanTempDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/basePlanTempDetail/basePlanTempDetail_list");
	}
	/**
	 * BasePlanTempDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/basePlanTempDetail/basePlanTempDetail_code");
	}
	/**
	 * 跳转到新增BasePlanTempDetail页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, 
			BasePlanTempDetail basePlanTempDetail){
		logger.debug("跳转到新增BasePlanTempDetail页面");
		if ("A".equals(basePlanTempDetail.getM_umState())){
			basePlanTempDetail.setQty(0.0);
		}
		return new ModelAndView("admin/basic/basePlanTemp/basePlanTemp_detail_add")
			.addObject("basePlanTempDetail", basePlanTempDetail);
	}
	/**
	 * 跳转到修改BasePlanTempDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BasePlanTempDetail页面");
		BasePlanTempDetail basePlanTempDetail = null;
		if (StringUtils.isNotBlank(id)){
			basePlanTempDetail = basePlanTempDetailService.get(BasePlanTempDetail.class, Integer.parseInt(id));
		}else{
			basePlanTempDetail = new BasePlanTempDetail();
		}
		
		return new ModelAndView("admin/basic/basePlanTempDetail/basePlanTempDetail_update")
			.addObject("basePlanTempDetail", basePlanTempDetail);
	}
	
	/**
	 * 跳转到查看BasePlanTempDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BasePlanTempDetail页面");
		BasePlanTempDetail basePlanTempDetail = basePlanTempDetailService.get(BasePlanTempDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/basePlanTempDetail/basePlanTempDetail_detail")
				.addObject("basePlanTempDetail", basePlanTempDetail);
	}
	/**
	 * 跳转到复制BasePlanTempDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("跳转到复制PrePlanTempDetail页面");
		return new ModelAndView(
				"admin/basic/basePlanTemp/basePlanTemp_detail_copy");
	}
	/**
	 * 添加BasePlanTempDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BasePlanTempDetail basePlanTempDetail){
		logger.debug("添加BasePlanTempDetail开始");
		try{
			this.basePlanTempDetailService.save(basePlanTempDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BasePlanTempDetail失败");
		}
	}
	
	/**
	 * 修改BasePlanTempDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BasePlanTempDetail basePlanTempDetail){
		logger.debug("修改BasePlanTempDetail开始");
		try{
			basePlanTempDetail.setLastUpdate(new Date());
			basePlanTempDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.basePlanTempDetailService.update(basePlanTempDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BasePlanTempDetail失败");
		}
	}
	
	/**
	 * 删除BasePlanTempDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BasePlanTempDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BasePlanTempDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BasePlanTempDetail basePlanTempDetail = basePlanTempDetailService.get(BasePlanTempDetail.class, Integer.parseInt(deleteId));
				if(basePlanTempDetail == null)
					continue;
				basePlanTempDetail.setExpired("T");
				basePlanTempDetailService.update(basePlanTempDetail);
			}
		}
		logger.debug("删除BasePlanTempDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BasePlanTempDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BasePlanTempDetail basePlanTempDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		basePlanTempDetailService.findPageBySql(page, basePlanTempDetail);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"BasePlanTempDetail_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 * 根据no查找基础算量明细
	 * 
	 * @param request
	 * @param response
	 * @param prePlanTempDetail
	 */
	@RequestMapping("/findDetailByNo")
	@ResponseBody
	public List<Map<String, Object>> findDetailByNo(HttpServletRequest request,
			HttpServletResponse response, BasePlanTempDetail basePlanTempDetail) {
		List<Map<String, Object>>list=basePlanTempDetailService.findDetailByNo(basePlanTempDetail);
		return list;
	}
}
