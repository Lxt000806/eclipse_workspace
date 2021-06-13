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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.PurchaseDetail;
import com.house.home.service.insales.PurchaseDetailService;

@Controller
@RequestMapping("/admin/purchaseDetail")
public class PurchaseDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseDetailController.class);

	@Autowired
	private PurchaseDetailService purchaseDetailService;

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
			HttpServletResponse response, PurchaseDetail purchaseDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchaseDetailService.findPageBySql(page, purchaseDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getViewJqGrid(HttpServletRequest request,
			HttpServletResponse response, PurchaseDetail purchaseDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//O:打开状态，C：确认状态
		if("OPEN".equals(purchaseDetail.getStatus())){
			purchaseDetailService.findViewOPageBySql(page, purchaseDetail);
		}else{
			purchaseDetailService.findViewCPageBySql(page, purchaseDetail);

		}
				
				
		return new WebPage<Map<String,Object>>(page);
	}
	 
	/**
	 * PurchaseDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/purchaseDetail/purchaseDetail_list");
	}
	/**
	 * PurchaseDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/purchaseDetail/purchaseDetail_code");
	}
	/**
	 * 跳转到新增PurchaseDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PurchaseDetail页面");
		PurchaseDetail purchaseDetail = null;
		if (StringUtils.isNotBlank(id)){
			purchaseDetail = purchaseDetailService.get(PurchaseDetail.class, Integer.parseInt(id));
			purchaseDetail.setPk(null);
		}else{
			purchaseDetail = new PurchaseDetail();
		}
		return new ModelAndView("admin/insales/purchaseDetail/purchaseDetail_save")
			.addObject("purchaseDetail", purchaseDetail);
	}
	/**
	 * 跳转到修改PurchaseDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PurchaseDetail页面");
		PurchaseDetail purchaseDetail = null;
		if (StringUtils.isNotBlank(id)){
			purchaseDetail = purchaseDetailService.get(PurchaseDetail.class, Integer.parseInt(id));
		}else{
			purchaseDetail = new PurchaseDetail();
		}
		return new ModelAndView("admin/insales/purchaseDetail/purchaseDetail_update")
			.addObject("purchaseDetail", purchaseDetail);
	}
	
	/**
	 * 跳转到查看PurchaseDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PurchaseDetail页面");
		PurchaseDetail purchaseDetail = purchaseDetailService.get(PurchaseDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/insales/purchaseDetail/purchaseDetail_detail")
				.addObject("purchaseDetail", purchaseDetail);
	}
	/**
	 * 添加PurchaseDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PurchaseDetail purchaseDetail){
		logger.debug("添加PurchaseDetail开始");
		try{
			this.purchaseDetailService.save(purchaseDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PurchaseDetail失败");
		}
	}
	
	/**
	 * 修改PurchaseDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PurchaseDetail purchaseDetail){
		logger.debug("修改PurchaseDetail开始");
		try{
			purchaseDetail.setLastUpdate(new Date());
			purchaseDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.purchaseDetailService.update(purchaseDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PurchaseDetail失败");
		}
	}
	
	/**
	 * 删除PurchaseDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PurchaseDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PurchaseDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PurchaseDetail purchaseDetail = purchaseDetailService.get(PurchaseDetail.class, Integer.parseInt(deleteId));
				if(purchaseDetail == null)
					continue;
				purchaseDetail.setExpired("T");
				purchaseDetailService.update(purchaseDetail);
			}
		}
		logger.debug("删除PurchaseDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PurchaseDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PurchaseDetail purchaseDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		purchaseDetailService.findPageBySql(page, purchaseDetail);
	}

}
