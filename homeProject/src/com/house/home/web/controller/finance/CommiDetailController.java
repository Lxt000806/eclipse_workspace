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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.finance.CommiDetail;
import com.house.home.service.finance.CommiDetailService;

@Controller
@RequestMapping("/admin/commiDetail")
public class CommiDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiDetailController.class);

	@Autowired
	private CommiDetailService commiDetailService;

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
			HttpServletResponse response, CommiDetail commiDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiDetailService.findPageBySql(page, commiDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CommiDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/commiDetail/commiDetail_list");
	}
	/**
	 * CommiDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/commiDetail/commiDetail_code");
	}
	/**
	 * 跳转到新增CommiDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CommiDetail页面");
		CommiDetail commiDetail = null;
		if (StringUtils.isNotBlank(id)){
			commiDetail = commiDetailService.get(CommiDetail.class, Integer.parseInt(id));
			commiDetail.setPk(null);
		}else{
			commiDetail = new CommiDetail();
		}
		
		return new ModelAndView("admin/finance/commiDetail/commiDetail_save")
			.addObject("commiDetail", commiDetail);
	}
	/**
	 * 跳转到修改CommiDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CommiDetail页面");
		CommiDetail commiDetail = null;
		if (StringUtils.isNotBlank(id)){
			commiDetail = commiDetailService.get(CommiDetail.class, Integer.parseInt(id));
		}else{
			commiDetail = new CommiDetail();
		}
		
		return new ModelAndView("admin/finance/commiDetail/commiDetail_update")
			.addObject("commiDetail", commiDetail);
	}
	
	/**
	 * 跳转到查看CommiDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CommiDetail页面");
		CommiDetail commiDetail = commiDetailService.get(CommiDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/finance/commiDetail/commiDetail_detail")
				.addObject("commiDetail", commiDetail);
	}
	/**
	 * 添加CommiDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiDetail commiDetail){
		logger.debug("添加CommiDetail开始");
		try{
			this.commiDetailService.save(commiDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CommiDetail失败");
		}
	}
	
	/**
	 * 修改CommiDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiDetail commiDetail){
		logger.debug("修改CommiDetail开始");
		try{
			commiDetail.setLastUpdate(new Date());
			commiDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.commiDetailService.update(commiDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CommiDetail失败");
		}
	}
	
	/**
	 * 删除CommiDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CommiDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CommiDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CommiDetail commiDetail = commiDetailService.get(CommiDetail.class, Integer.parseInt(deleteId));
				if(commiDetail == null)
					continue;
				commiDetail.setExpired("T");
				commiDetailService.update(commiDetail);
			}
		}
		logger.debug("删除CommiDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiDetail commiDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiDetailService.findPageBySql(page, commiDetail);
	}

}
