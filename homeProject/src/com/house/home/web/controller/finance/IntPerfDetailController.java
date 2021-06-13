package com.house.home.web.controller.finance;

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
import com.house.home.entity.finance.IntPerfDetail;
import com.house.home.service.finance.IntPerfDetailService;

@Controller
@RequestMapping("/admin/intPerfDetail")
public class IntPerfDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IntPerfDetailController.class);

	@Autowired
	private IntPerfDetailService intPerfDetailService;

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
			HttpServletResponse response, IntPerfDetail intPerfDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intPerfDetailService.findPageBySql(page, intPerfDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询报表JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportJqGrid(HttpServletRequest request,
			HttpServletResponse response, IntPerfDetail intPerfDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intPerfDetailService.findReportPageBySql(page, intPerfDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * IntPerfDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/intPerfDetail/intPerfDetail_list");
	}
	/**
	 * IntPerfDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/intPerfDetail/intPerfDetail_code");
	}
	/**
	 * 跳转到新增IntPerfDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增IntPerfDetail页面");
		IntPerfDetail intPerfDetail = null;
		if (StringUtils.isNotBlank(id)){
			intPerfDetail = intPerfDetailService.get(IntPerfDetail.class, Integer.parseInt(id));
			intPerfDetail.setPk(null);
		}else{
			intPerfDetail = new IntPerfDetail();
		}
		
		return new ModelAndView("admin/finance/intPerfDetail/intPerfDetail_save")
			.addObject("intPerfDetail", intPerfDetail);
	}
	/**
	 * 跳转到修改IntPerfDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String pk,String m_umState){
		logger.debug("跳转到修改IntPerfDetail页面");
		Map<String, Object> map=intPerfDetailService.findMapBySql(pk);
		map.put("m_umState", m_umState);
		return new ModelAndView("admin/finance/intPerf/intPerf_updateDetail")
			.addObject("map", map);
	}
	
	/**
	 * 跳转到查看IntPerfDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String pk,String m_umState){
		logger.debug("跳转到查看IntPerfDetail页面");
		Map<String, Object> map=intPerfDetailService.findMapBySql(pk);
		map.put("m_umState", m_umState);
		return new ModelAndView("admin/finance/intPerf/intPerf_updateDetail")
			.addObject("map", map);
	}
	/**
	 * 跳转到查看IntPerfDetail页面
	 * @return
	 */
	@RequestMapping("/goViewDetail")
	public ModelAndView goViewDetail(HttpServletRequest request, HttpServletResponse response, 
			IntPerfDetail intPerfDetail){
		logger.debug("跳转到查看明细IntPerfDetail页面");
		Map<String, Object>map=intPerfDetailService.findDisc(intPerfDetail);
		if(map!=null){
			intPerfDetail.setDisc(intPerfDetailService.findDisc(intPerfDetail).get("disc").toString());
		}else{
			intPerfDetail.setDisc("0.0");
		}
		return new ModelAndView("admin/finance/intPerf/intPerf_viewDetail")
			.addObject("intPerfDetail", intPerfDetail);
	}
	
	/**
	 * 添加IntPerfDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, IntPerfDetail intPerfDetail){
		logger.debug("添加IntPerfDetail开始");
		try{
			this.intPerfDetailService.save(intPerfDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加IntPerfDetail失败");
		}
	}
	
	/**
	 * 修改IntPerfDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, IntPerfDetail intPerfDetail){
		logger.debug("修改IntPerfDetail开始");
		try{
			intPerfDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.intPerfDetailService.doUpdate(intPerfDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改IntPerfDetail失败");
		}
	}
	
	/**
	 * 删除IntPerfDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除IntPerfDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "IntPerfDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				IntPerfDetail intPerfDetail = intPerfDetailService.get(IntPerfDetail.class, Integer.parseInt(deleteId));
				if(intPerfDetail == null)
					continue;
				intPerfDetail.setExpired("T");
				intPerfDetailService.update(intPerfDetail);
			}
		}
		logger.debug("删除IntPerfDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *IntPerfDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, IntPerfDetail intPerfDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intPerfDetailService.findPageBySql(page, intPerfDetail);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成业绩计算明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 *报表导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doReportExcel")
	public void doReportExcel(HttpServletRequest request, 
			HttpServletResponse response, IntPerfDetail intPerfDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intPerfDetailService.findReportPageBySql(page, intPerfDetail);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成业绩报表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 生成业绩数据
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doCount")
	public void doGetIntPerDetail(HttpServletRequest request, HttpServletResponse response,String no,String prjPerfNo){
		logger.debug("生成业绩数据");
		this.intPerfDetailService.doGetIntPerDetail(no,this.getUserContext(request).getCzybh(),prjPerfNo);
		ServletUtils.outPrintSuccess(request, response,"生成数据成功");
	}
	/**
	 * 报表明细
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportView")
	public ModelAndView goReportView(HttpServletRequest request,
			HttpServletResponse response,String designMan,String no,String isCupboard) throws Exception {
		return new ModelAndView("admin/finance/intPerf/intPerf_reportView").addObject("designMan",designMan)
				.addObject("no",no).addObject("isCupboard", isCupboard);
	}
}
