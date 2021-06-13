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
import com.house.home.entity.finance.CommiCal;
import com.house.home.service.finance.CommiCalService;

@Controller
@RequestMapping("/admin/commiCal")
public class CommiCalController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiCalController.class);

	@Autowired
	private CommiCalService commiCalService;

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
			HttpServletResponse response, CommiCal commiCal) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCalService.findPageBySql(page, commiCal);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CommiCal列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/commiCal/commiCal_list");
	}
	/**
	 * CommiCal查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/commiCal/commiCal_code");
	}
	/**
	 * 跳转到新增CommiCal页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CommiCal页面");
		CommiCal commiCal = null;
		if (StringUtils.isNotBlank(id)){
			commiCal = commiCalService.get(CommiCal.class, id);
			commiCal.setNo(null);
		}else{
			commiCal = new CommiCal();
		}
		
		return new ModelAndView("admin/finance/commiCal/commiCal_save")
			.addObject("commiCal", commiCal);
	}
	/**
	 * 跳转到修改CommiCal页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CommiCal页面");
		CommiCal commiCal = null;
		if (StringUtils.isNotBlank(id)){
			commiCal = commiCalService.get(CommiCal.class, id);
		}else{
			commiCal = new CommiCal();
		}
		
		return new ModelAndView("admin/finance/commiCal/commiCal_update")
			.addObject("commiCal", commiCal);
	}
	
	/**
	 * 跳转到查看CommiCal页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CommiCal页面");
		CommiCal commiCal = commiCalService.get(CommiCal.class, id);
		
		return new ModelAndView("admin/finance/commiCal/commiCal_detail")
				.addObject("commiCal", commiCal);
	}
	/**
	 * 添加CommiCal
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiCal commiCal){
		logger.debug("添加CommiCal开始");
		try{
			String str = commiCalService.getSeqNo("tCommiCal");
			commiCal.setNo(str);
			commiCal.setLastUpdate(new Date());
			commiCal.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCal.setExpired("F");
			this.commiCalService.save(commiCal);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CommiCal失败");
		}
	}
	
	/**
	 * 修改CommiCal
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiCal commiCal){
		logger.debug("修改CommiCal开始");
		try{
			commiCal.setLastUpdate(new Date());
			commiCal.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.commiCalService.update(commiCal);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CommiCal失败");
		}
	}
	
	/**
	 * 删除CommiCal
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CommiCal开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CommiCal编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CommiCal commiCal = commiCalService.get(CommiCal.class, deleteId);
				if(commiCal == null)
					continue;
				commiCal.setExpired("T");
				commiCalService.update(commiCal);
			}
		}
		logger.debug("删除CommiCal IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiCal导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCal commiCal){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiCalService.findPageBySql(page, commiCal);
	}

}
