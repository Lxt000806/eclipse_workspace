package com.house.home.web.controller.salary;

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
import com.house.home.entity.salary.InjuryInsurParam;
import com.house.home.service.salary.InjuryInsurParamService;

@Controller
@RequestMapping("/admin/injuryInsurParam")
public class InjuryInsurParamController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(InjuryInsurParamController.class);

	@Autowired
	private InjuryInsurParamService injuryInsurParamService;

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
			HttpServletResponse response, InjuryInsurParam injuryInsurParam) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		injuryInsurParamService.findPageBySql(page, injuryInsurParam);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * InjuryInsurParam列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/injuryInsurParam/injuryInsurParam_list");
	}
	/**
	 * InjuryInsurParam查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/injuryInsurParam/injuryInsurParam_code");
	}
	/**
	 * 跳转到新增InjuryInsurParam页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id,String dialogId){
		logger.debug("跳转到新增InjuryInsurParam页面");
		InjuryInsurParam injuryInsurParam = null;
		if (StringUtils.isNotBlank(id)){
			injuryInsurParam = injuryInsurParamService.get(InjuryInsurParam.class, id);
			injuryInsurParam.setConSignCmp(null);
		}else{
			injuryInsurParam = new InjuryInsurParam();
		}
		injuryInsurParam.setM_umState("A");
		return new ModelAndView("admin/salary/injuryInsurParam/injuryInsurParam_save")
			.addObject("injuryInsurParam", injuryInsurParam).addObject("dialogId", dialogId);
	}
	/**
	 * 跳转到修改InjuryInsurParam页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id,String dialogId){
		logger.debug("跳转到修改InjuryInsurParam页面");
		InjuryInsurParam injuryInsurParam = null;
		if (StringUtils.isNotBlank(id)){
			injuryInsurParam = injuryInsurParamService.get(InjuryInsurParam.class, id);
		}else{
			injuryInsurParam = new InjuryInsurParam();
		}
		injuryInsurParam.setM_umState("M");
		return new ModelAndView("admin/salary/injuryInsurParam/injuryInsurParam_save")
			.addObject("injuryInsurParam", injuryInsurParam).addObject("dialogId", dialogId);
	}
	
	/**
	 * 跳转到查看InjuryInsurParam页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id,String dialogId){
		logger.debug("跳转到查看InjuryInsurParam页面");
		InjuryInsurParam injuryInsurParam = injuryInsurParamService.get(InjuryInsurParam.class, id);
		injuryInsurParam.setM_umState("V");
		return new ModelAndView("admin/salary/injuryInsurParam/injuryInsurParam_save")
				.addObject("injuryInsurParam", injuryInsurParam).addObject("dialogId", dialogId);
	}
	/**
	 * 添加InjuryInsurParam
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, InjuryInsurParam injuryInsurParam){
		logger.debug("添加InjuryInsurParam开始");
		try{
			injuryInsurParam.setLastUpdate(new Date());
			injuryInsurParam.setLastUpdatedBy(getUserContext(request).getCzybh());
			injuryInsurParam.setExpired("F");
			this.injuryInsurParamService.save(injuryInsurParam);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "公司重复！");
		}
	}
	
	/**
	 * 修改InjuryInsurParam
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, InjuryInsurParam injuryInsurParam){
		logger.debug("修改InjuryInsurParam开始");
		try{
			injuryInsurParam.setLastUpdate(new Date());
			injuryInsurParam.setLastUpdatedBy(getUserContext(request).getCzybh());
			injuryInsurParam.setActionLog("EDIT");
			this.injuryInsurParamService.update(injuryInsurParam);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改InjuryInsurParam失败");
		}
	}
	
	/**
	 * 删除InjuryInsurParam
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除InjuryInsurParam开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "InjuryInsurParam编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				InjuryInsurParam injuryInsurParam = injuryInsurParamService.get(InjuryInsurParam.class, deleteId);
				if(injuryInsurParam == null)
					continue;
				injuryInsurParam.setExpired("T");
				injuryInsurParamService.update(injuryInsurParam);
			}
		}
		logger.debug("删除InjuryInsurParam IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *InjuryInsurParam导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, InjuryInsurParam injuryInsurParam){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		injuryInsurParamService.findPageBySql(page, injuryInsurParam);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工商缴费参数管理管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
