package com.house.home.web.controller.insales;

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

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.BaseItem;
import com.house.home.entity.insales.BaseItemTemp;
import com.house.home.entity.insales.BaseItemTempDetail;
import com.house.home.service.insales.BaseItemTempService;

@Controller
@RequestMapping("/admin/baseItemTemp")
public class BaseItemTempController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseItemController.class);

	@Autowired
	private BaseItemTempService baseItemTempService;
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
			HttpServletResponse response, BaseItemTemp baseItemTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemTempService.findPageBySql(page, baseItemTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 主页查询
	 * @author	created by zb
	 * @date	2019-5-22--下午5:45:11
	 * @param request
	 * @param response
	 * @param baseItemTemp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goListJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemTemp baseItemTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemTempService.findListPageBySql(page, baseItemTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemTemp baseItemTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemTempService.findDetailPageBySql(page, baseItemTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("跳转到基础报价模板主页");
		return new ModelAndView("admin/basic/baseItemTemp/baseItemTemp_list");
	}
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("跳转到基础报价模板新增");
		BaseItemTemp baseItemTemp = new BaseItemTemp();
		return new ModelAndView("admin/basic/baseItemTemp/baseItemTemp_add")
			.addObject("baseItemTemp", baseItemTemp);
	}
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, BaseItemTemp baseItemTemp) throws Exception {
		logger.debug("跳转到基础报价模板更新");
		BaseItemTemp bit = baseItemTempService.get(BaseItemTemp.class, baseItemTemp.getNo());
		bit.setM_umState(baseItemTemp.getM_umState());
		return new ModelAndView("admin/basic/baseItemTemp/baseItemTemp_add")
			.addObject("baseItemTemp", bit);
	}
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, BaseItemTemp baseItemTemp) throws Exception {
		logger.debug("跳转到基础报价模板查看");
		BaseItemTemp bit = baseItemTempService.get(BaseItemTemp.class, baseItemTemp.getNo());
		bit.setM_umState(baseItemTemp.getM_umState());
		return new ModelAndView("admin/basic/baseItemTemp/baseItemTemp_add")
			.addObject("baseItemTemp", bit);
	}
	/**
	 * 明细编辑
	 * @author	created by zb
	 * @date	2019-5-23--下午3:12:42
	 * @param request
	 * @param response
	 * @param baseItemTemp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWin")
	public ModelAndView goWin(HttpServletRequest request,
			HttpServletResponse response, BaseItemTempDetail baseItemTempDetail) throws Exception {
		logger.debug("跳转到基础报价模板明细编辑");
		if (StringUtils.isNotBlank(baseItemTempDetail.getBaseItemCode())) {
			BaseItem baseItem = this.baseItemTempService.get(BaseItem.class, baseItemTempDetail.getBaseItemCode());
			baseItemTempDetail.setBaseItemDescr(baseItem.getDescr());
		}
		return new ModelAndView("admin/basic/baseItemTemp/baseItemTemp_win")
			.addObject("baseItemTempDetail", baseItemTempDetail);
	}
	/**
	 * 存储过程保存
	 * @author	created by zb
	 * @date	2019-5-23--下午5:36:19
	 * @param request
	 * @param response
	 * @param baseItemTemp
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,BaseItemTemp baseItemTemp){
		logger.debug("存储过程保存");
		try {
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "明细表为空");
				return;
			}
			/*执行存储过程*/
			Result result = this.baseItemTempService.doSave(baseItemTemp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增回访失败");
		}
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseItemTemp baseItemTemp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemTempService.findListPageBySql(page, baseItemTemp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"基础报价模板_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
