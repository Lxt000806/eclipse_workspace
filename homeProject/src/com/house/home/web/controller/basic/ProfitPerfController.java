package com.house.home.web.controller.basic;

import java.text.DecimalFormat;
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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ProfitPerf;
import com.house.home.service.basic.ProfitPerfService;

@Controller
@RequestMapping("/admin/profitPerf")
public class ProfitPerfController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(ProfitPerfController.class);

	@Autowired
	private ProfitPerfService profitPerfService;

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
			HttpServletResponse response, ProfitPerf profitPerf)
			throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		profitPerfService.findPageBySql(page, profitPerf);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * profitPerf列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/profitPerf/profitPerf_list");
	}

	/**
	 * 跳转到新增profitPerf页面
	 * 
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到新增profitPerf页面");
		ProfitPerf profitPerf = null;
		if (StringUtils.isNotBlank(id)) {
			profitPerf = profitPerfService.get(ProfitPerf.class,
					Integer.parseInt(id));
			profitPerf.setPk(null);
		} else {
			profitPerf = new ProfitPerf();
		}

		return new ModelAndView("admin/basic/profitPerf/profitPerf_save")
				.addObject("profitPerf", profitPerf);
	}

	/**
	 * 跳转到修改profitPerf页面
	 * 
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到修改ProfitPerf页面");
		ProfitPerf profitPerf = null;
		if (StringUtils.isNotBlank(id)) {
			profitPerf = profitPerfService.get(ProfitPerf.class,
					Integer.parseInt(id));
		} else {
			profitPerf = new ProfitPerf();
		}
		// 去掉头尾空格
		profitPerf.setItemType12(profitPerf.getItemType12().trim());
		//位数多时去掉e
				DecimalFormat df = new DecimalFormat("##.####");    
				Double d = new Double(profitPerf.getToProfit());  
		return new ModelAndView("admin/basic/profitPerf/profitPerf_update")
				.addObject("profitPerf", profitPerf).addObject("toProfit", df.format(d));
	}

	/**
	 * 跳转到查看profitPerf页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到查看profitPerf页面");
		ProfitPerf profitPerf = profitPerfService.get(ProfitPerf.class,
				Integer.parseInt(id));
		// 去掉头尾空格
		profitPerf.setItemType12(profitPerf.getItemType12().trim());
		//位数多时去掉e
		DecimalFormat df = new DecimalFormat("##.####");    
		Double d = new Double(profitPerf.getToProfit());  
		return new ModelAndView("admin/basic/profitPerf/profitPerf_view")
				.addObject("profitPerf", profitPerf).addObject("toProfit", df.format(d));
	}

	/**
	 * 添加profitPerf
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, ProfitPerf profitPerf) {
		logger.debug("添加profitPerf开始");
		try {
			profitPerf.setLastUpdate(new Date());
			profitPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			profitPerf.setExpired("F");
			profitPerf.setActionLog("ADD");
			this.profitPerfService.save(profitPerf);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加毛利率业绩配置失败");
		}
	}

	/**
	 * 修改profitPerf
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, ProfitPerf profitPerf) {
		logger.debug("修改profitPerf开始");
		try {
			profitPerf.setLastUpdate(new Date());
			profitPerf.setLastUpdatedBy(getUserContext(request).getCzybh());
			profitPerf.setActionLog("Edit");
			if (StringUtils.isBlank(profitPerf.getExpired())
					|| "F".equals(profitPerf.getExpired())) {
				profitPerf.setExpired("F");
			}
			this.profitPerfService.update(profitPerf);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改毛利率业绩配置失败");
		}
	}

	/**
	 * 删除profitPerf
	 * 
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request,
			HttpServletResponse response, Integer pk) {
		logger.debug("删除profitPerf开始");
		try {
			ProfitPerf profitPerf = profitPerfService.get(ProfitPerf.class, pk);
			profitPerfService.delete(profitPerf);
			ServletUtils.outPrintSuccess(request, response, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "删除记录失败");
		}
	}

	/**
	 * profitPerf导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, ProfitPerf profitPerf) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		profitPerfService.findPageBySql(page, profitPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"毛利率业绩配置_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}
	
	/**
	 * 跳转到新增profitPerf页面
	 * 
	 * @return
	 */
	@RequestMapping("/goCommiPer")
	public ModelAndView goCommiPer(HttpServletRequest request,
			HttpServletResponse response) {
		
		return new ModelAndView("admin/basic/profitPerf/profitPerf_commiPer");
	}

}
