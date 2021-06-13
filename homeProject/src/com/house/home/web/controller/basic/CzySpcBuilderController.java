package com.house.home.web.controller.basic;

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
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.token.FormToken;
import com.house.framework.web.token.impl.FormTokenManagerImpl;
import com.house.home.entity.basic.CzySpcBuilder;
import com.house.home.service.basic.CzySpcBuilderService;

@Controller
@RequestMapping("/admin/czySpcBuilder")
public class CzySpcBuilderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CzySpcBuilderController.class);

	@Autowired
	private CzySpcBuilderService czySpcBuilderService;

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
			HttpServletResponse response, CzySpcBuilder czySpcBuilder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		czySpcBuilderService.findPageBySql(page, czySpcBuilder);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CzySpcBuilder列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czySpcBuilder/czySpcBuilder_list");
	}
	/**
	 * CzySpcBuilder查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czySpcBuilder/czySpcBuilder_code");
	}
	/**
	 * 跳转到新增CzySpcBuilder页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CzySpcBuilder页面");
		CzySpcBuilder czySpcBuilder = null;
		if (StringUtils.isNotBlank(id)){
			czySpcBuilder = czySpcBuilderService.get(CzySpcBuilder.class, Integer.parseInt(id));
			czySpcBuilder.setPk(null);
		}else{
			czySpcBuilder = new CzySpcBuilder();
		}
		
		return new ModelAndView("admin/basic/czySpcBuilder/czySpcBuilder_save")
			.addObject("czySpcBuilder", czySpcBuilder);
	}
	/**
	 * 跳转到修改CzySpcBuilder页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CzySpcBuilder页面");
		CzySpcBuilder czySpcBuilder = null;
		if (StringUtils.isNotBlank(id)){
			czySpcBuilder = czySpcBuilderService.get(CzySpcBuilder.class, Integer.parseInt(id));
		}else{
			czySpcBuilder = new CzySpcBuilder();
		}
		
		return new ModelAndView("admin/basic/czySpcBuilder/czySpcBuilder_update")
			.addObject("czySpcBuilder", czySpcBuilder);
	}
	
	/**
	 * 跳转到查看CzySpcBuilder页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CzySpcBuilder页面");
		CzySpcBuilder czySpcBuilder = czySpcBuilderService.get(CzySpcBuilder.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/czySpcBuilder/czySpcBuilder_detail")
				.addObject("czySpcBuilder", czySpcBuilder);
	}
	/**
	 * 添加CzySpcBuilder
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CzySpcBuilder czySpcBuilder){
		logger.debug("添加CzySpcBuilder开始");
		try{
			CzySpcBuilder czySpcBuilder2 = czySpcBuilderService.getByCzybhAndSpcBuilder(czySpcBuilder.getCzybh(),czySpcBuilder.getSpcBuilder());
			if(czySpcBuilder2!=null){
				ServletUtils.outPrintFail(request, response, "该操作员专盘已存在");
				return;
			}
			czySpcBuilder.setLastUpdate(new Date());
			czySpcBuilder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			czySpcBuilder.setExpired("F");
			czySpcBuilder.setActionLog("ADD");
			this.czySpcBuilderService.save(czySpcBuilder);
			FormToken formToken = SpringContextHolder.getBean("formTokenManagerImpl", FormTokenManagerImpl.class).newFormToken(request);
			JSONObject json = new JSONObject();
			json.put("token", formToken.getToken());
			ServletUtils.outPrintSuccess(request, response, "添加作员专盘成功", json);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加作员专盘失败");
		}
	}
	
	/**
	 * 修改CzySpcBuilder
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CzySpcBuilder czySpcBuilder){
		logger.debug("修改CzySpcBuilder开始");
		try{
			czySpcBuilder.setLastUpdate(new Date());
			czySpcBuilder.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.czySpcBuilderService.update(czySpcBuilder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CzySpcBuilder失败");
		}
	}
	
	/**
	 * 删除CzySpcBuilder
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除UserLeader开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(int i=0;i<deleteIdList.size();i++){
			CzySpcBuilder czySpcBuilder = czySpcBuilderService.get(CzySpcBuilder.class, Integer.parseInt(deleteIdList.get(i)));
			if (czySpcBuilder!=null){
				czySpcBuilderService.delete(czySpcBuilder);
			}
		}
		logger.debug("删除UserLeader IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CzySpcBuilder导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CzySpcBuilder czySpcBuilder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		czySpcBuilderService.findPageBySql(page, czySpcBuilder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CzySpcBuilder_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
