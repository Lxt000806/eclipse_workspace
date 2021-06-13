package com.house.home.web.controller.insales;

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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.PurchArr;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.insales.PurchArrService;

@Controller
@RequestMapping("/admin/purchArr")
public class PurchArrController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PurchArrController.class);

	@Autowired
	private PurchArrService purchArrService;

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
			HttpServletResponse response, PurchArr purchArr) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchArrService.findPageBySql(page, purchArr);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurchArrJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPurchArrJqGrid(HttpServletRequest request,
			HttpServletResponse response, PurchArr purchArr) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchArrService.findPurchArrPageBySql(page, purchArr);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * PurchArr列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/purchArr/purchArr_list");
	}
	/**
	 * 查询JqGrid表格数据-到货明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_dhmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_dhmx(HttpServletRequest request,
			HttpServletResponse response, String puno) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchArrService.findPageByPuno(page, puno);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 到货明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDhmx")
	public ModelAndView goDhmx(HttpServletRequest request,
			HttpServletResponse response,String puno) throws Exception {

		return new ModelAndView("admin/insales/purchArr/purchArr_dhmx")
			.addObject("puno", puno);
	}
	/**
	 * PurchArr查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/purchArr/purchArr_code");
	}
	/**
	 * 跳转到新增PurchArr页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PurchArr页面");
		PurchArr purchArr = null;
		if (StringUtils.isNotBlank(id)){
			purchArr = purchArrService.get(PurchArr.class, id);
			purchArr.setNo(null);
		}else{
			purchArr = new PurchArr();
		}
		
		return new ModelAndView("admin/insales/purchArr/purchArr_save")
			.addObject("purchArr", purchArr);
	}
	/**
	 * 跳转到修改PurchArr页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PurchArr页面");
		PurchArr purchArr = null;
		if (StringUtils.isNotBlank(id)){
			purchArr = purchArrService.get(PurchArr.class, id);
		}else{
			purchArr = new PurchArr();
		}
		
		return new ModelAndView("admin/insales/purchArr/purchArr_update")
			.addObject("purchArr", purchArr);
	}
	
	/**
	 * 跳转到查看PurchArr页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PurchArr页面");
		PurchArr purchArr = purchArrService.get(PurchArr.class, id);
		
		return new ModelAndView("admin/insales/purchArr/purchArr_detail")
				.addObject("purchArr", purchArr);
	}
	/**
	 * 添加PurchArr
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PurchArr purchArr){
		logger.debug("添加PurchArr开始");
		try{
			String str = purchArrService.getSeqNo("tPurchArr");
			purchArr.setNo(str);
			purchArr.setLastUpdate(new Date());
			purchArr.setLastUpdatedBy(getUserContext(request).getCzybh());
			purchArr.setExpired("F");
			this.purchArrService.save(purchArr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PurchArr失败");
		}
	}
	
	/**
	 * 修改PurchArr
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PurchArr purchArr){
		logger.debug("修改PurchArr开始");
		try{
			purchArr.setLastUpdate(new Date());
			purchArr.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.purchArrService.update(purchArr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PurchArr失败");
		}
	}
	
	/**
	 * 删除PurchArr
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PurchArr开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PurchArr编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PurchArr purchArr = purchArrService.get(PurchArr.class, deleteId);
				if(purchArr == null)
					continue;
				purchArr.setExpired("T");
				purchArrService.update(purchArr);
			}
		}
		logger.debug("删除PurchArr IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PurchArr导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PurchArr purchArr){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		purchArrService.findPageBySql(page, purchArr);
	}
	
	@RequestMapping("/doPurchArrExcel")
	public void doPurchArrExcel(HttpServletRequest request ,HttpServletResponse response,
			 PurchArr purchArr){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		purchArrService.findPurchArrPageBySql(page, purchArr);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"到货明细表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
