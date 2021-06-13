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
import com.house.home.entity.insales.PurchArrDetail;
import com.house.home.service.insales.PurchArrDetailService;

@Controller
@RequestMapping("/admin/purchArrDetail")
public class PurchArrDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PurchArrDetailController.class);

	@Autowired
	private PurchArrDetailService purchArrDetailService;

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
			HttpServletResponse response, PurchArrDetail purchArrDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchArrDetailService.findPageBySql(page, purchArrDetail);
		return new WebPage<Map<String,Object>>(page);
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
			HttpServletResponse response, String pano) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchArrDetailService.findPageByPano(page, pano);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PurchArrDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/purchArrDetail/purchArrDetail_list");
	}
	/**
	 * PurchArrDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/purchArrDetail/purchArrDetail_code");
	}
	/**
	 * 跳转到新增PurchArrDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PurchArrDetail页面");
		PurchArrDetail purchArrDetail = null;
		if (StringUtils.isNotBlank(id)){
			purchArrDetail = purchArrDetailService.get(PurchArrDetail.class, Integer.parseInt(id));
			purchArrDetail.setPk(null);
		}else{
			purchArrDetail = new PurchArrDetail();
		}
		
		return new ModelAndView("admin/insales/purchArrDetail/purchArrDetail_save")
			.addObject("purchArrDetail", purchArrDetail);
	}
	/**
	 * 跳转到修改PurchArrDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PurchArrDetail页面");
		PurchArrDetail purchArrDetail = null;
		if (StringUtils.isNotBlank(id)){
			purchArrDetail = purchArrDetailService.get(PurchArrDetail.class, Integer.parseInt(id));
		}else{
			purchArrDetail = new PurchArrDetail();
		}
		
		return new ModelAndView("admin/insales/purchArrDetail/purchArrDetail_update")
			.addObject("purchArrDetail", purchArrDetail);
	}
	
	/**
	 * 跳转到查看PurchArrDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PurchArrDetail页面");
		PurchArrDetail purchArrDetail = purchArrDetailService.get(PurchArrDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/insales/purchArrDetail/purchArrDetail_detail")
				.addObject("purchArrDetail", purchArrDetail);
	}
	/**
	 * 添加PurchArrDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PurchArrDetail purchArrDetail){
		logger.debug("添加PurchArrDetail开始");
		try{
			this.purchArrDetailService.save(purchArrDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PurchArrDetail失败");
		}
	}
	
	/**
	 * 修改PurchArrDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PurchArrDetail purchArrDetail){
		logger.debug("修改PurchArrDetail开始");
		try{
			purchArrDetail.setLastUpdate(new Date());
			purchArrDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.purchArrDetailService.update(purchArrDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PurchArrDetail失败");
		}
	}
	
	/**
	 * 删除PurchArrDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PurchArrDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PurchArrDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PurchArrDetail purchArrDetail = purchArrDetailService.get(PurchArrDetail.class, Integer.parseInt(deleteId));
				if(purchArrDetail == null)
					continue;
				purchArrDetail.setExpired("T");
				purchArrDetailService.update(purchArrDetail);
			}
		}
		logger.debug("删除PurchArrDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PurchArrDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PurchArrDetail purchArrDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		purchArrDetailService.findPageBySql(page, purchArrDetail);
	}

}
