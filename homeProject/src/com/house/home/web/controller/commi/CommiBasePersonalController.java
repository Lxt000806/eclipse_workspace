package com.house.home.web.controller.commi;

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
import com.house.home.entity.commi.CommiBasePersonal;
import com.house.home.service.commi.CommiBasePersonalService;

@Controller
@RequestMapping("/admin/commiBasePersonal")
public class CommiBasePersonalController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiBasePersonalController.class);

	@Autowired
	private CommiBasePersonalService commiBasePersonalService;

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
			HttpServletResponse response, CommiBasePersonal commiBasePersonal) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiBasePersonalService.findPageBySql(page, commiBasePersonal);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CommiBasePersonal列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiBasePersonal/commiBasePersonal_list");
	}
	/**
	 * CommiBasePersonal查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiBasePersonal/commiBasePersonal_code");
	}
	/**
	 * 跳转到新增CommiBasePersonal页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CommiBasePersonal页面");
		CommiBasePersonal commiBasePersonal = null;
		if (StringUtils.isNotBlank(id)){
			commiBasePersonal = commiBasePersonalService.get(CommiBasePersonal.class, Integer.parseInt(id));
			commiBasePersonal.setPk(null);
		}else{
			commiBasePersonal = new CommiBasePersonal();
		}
		
		return new ModelAndView("admin/commi/commiBasePersonal/commiBasePersonal_save")
			.addObject("commiBasePersonal", commiBasePersonal);
	}
	/**
	 * 跳转到修改CommiBasePersonal页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CommiBasePersonal页面");
		CommiBasePersonal commiBasePersonal = null;
		if (StringUtils.isNotBlank(id)){
			commiBasePersonal = commiBasePersonalService.get(CommiBasePersonal.class, Integer.parseInt(id));
		}else{
			commiBasePersonal = new CommiBasePersonal();
		}
		
		return new ModelAndView("admin/commi/commiBasePersonal/commiBasePersonal_update")
			.addObject("commiBasePersonal", commiBasePersonal);
	}
	
	/**
	 * 跳转到查看CommiBasePersonal页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CommiBasePersonal页面");
		CommiBasePersonal commiBasePersonal = commiBasePersonalService.get(CommiBasePersonal.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/commi/commiBasePersonal/commiBasePersonal_detail")
				.addObject("commiBasePersonal", commiBasePersonal);
	}
	/**
	 * 添加CommiBasePersonal
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiBasePersonal commiBasePersonal){
		logger.debug("添加基础个性化提成点开始");
		try{
			if(commiBasePersonalService.checkCommiBasePersonalExist(commiBasePersonal)){
				ServletUtils.outPrintFail(request, response, "基装类型1已存在,新增失败");
				return;
			}
			commiBasePersonal.setLastUpdate(new Date());
			commiBasePersonal.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiBasePersonal.setExpired("F");
			commiBasePersonal.setActionLog("ADD");
			this.commiBasePersonalService.save(commiBasePersonal);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加基础个性化提成点失败");
		}
	}
	
	/**
	 * 修改CommiBasePersonal
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiBasePersonal commiBasePersonal){
		logger.debug("修改基础个性化提成点开始");
		try{
			if(commiBasePersonal.getPk() == null){
				ServletUtils.outPrintFail(request, response, "基础个性化提成点PK不能为空,修改失败");
				return;
			}
			CommiBasePersonal modifiCommiBasePersonal = commiBasePersonalService.get(CommiBasePersonal.class, commiBasePersonal.getPk());
			if(modifiCommiBasePersonal == null){
				ServletUtils.outPrintFail(request, response, "基础个性化提成点记录不存在,修改失败");
				return;
			}
			if(commiBasePersonalService.checkCommiBasePersonalExist(commiBasePersonal)){
				ServletUtils.outPrintFail(request, response, "基装类型1已存在,修改失败");
				return;
			}
			modifiCommiBasePersonal.setBaseItemType1(commiBasePersonal.getBaseItemType1());
			modifiCommiBasePersonal.setCommiPer(commiBasePersonal.getCommiPer());
			modifiCommiBasePersonal.setLastUpdate(new Date());
			modifiCommiBasePersonal.setLastUpdatedBy(getUserContext(request).getCzybh());
			modifiCommiBasePersonal.setActionLog("EDIT");
			this.commiBasePersonalService.update(modifiCommiBasePersonal);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改基础个性化提成点失败");
		}
	}
	
	/**
	 * 删除CommiBasePersonal
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doBatchDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("删除基础个性化提成点开始");
		if(StringUtils.isBlank(id)){
			ServletUtils.outPrintFail(request, response, "基础个性化提成pk不能为空,删除失败");
			return;
		}
		CommiBasePersonal commiBasePersonal = commiBasePersonalService.get(CommiBasePersonal.class, Integer.parseInt(id));
		if(commiBasePersonal == null){
			ServletUtils.outPrintFail(request, response, "基础个性化提成记录不能为空,删除失败");
			return;
		}
		commiBasePersonalService.delete(commiBasePersonal);
		logger.debug("删除CommiBasePersonal IDS={} 完成",id);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiBasePersonal导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiBasePersonal commiBasePersonal){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiBasePersonalService.findPageBySql(page, commiBasePersonal);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础个性化提成点_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
