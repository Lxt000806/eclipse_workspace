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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CzybmAuthority;
import com.house.home.service.basic.CzybmAuthorityService;

@Controller
@RequestMapping("/admin/czybmAuthority")
public class CzybmAuthorityController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CzybmAuthorityController.class);

	@Autowired
	private CzybmAuthorityService czybmAuthorityService;

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
			HttpServletResponse response, CzybmAuthority czybmAuthority) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		czybmAuthorityService.findPageBySql(page, czybmAuthority);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CzybmAuthority列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czybmAuthority/czybmAuthority_list");
	}
	/**
	 * CzybmAuthority查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czybmAuthority/czybmAuthority_code");
	}
	/**
	 * 跳转到新增CzybmAuthority页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CzybmAuthority页面");
		CzybmAuthority czybmAuthority = null;
		if (StringUtils.isNotBlank(id)){
			czybmAuthority = czybmAuthorityService.get(CzybmAuthority.class, Integer.parseInt(id));
			czybmAuthority.setId(null);
		}else{
			czybmAuthority = new CzybmAuthority();
		}
		
		return new ModelAndView("admin/basic/czybmAuthority/czybmAuthority_save")
			.addObject("czybmAuthority", czybmAuthority);
	}
	/**
	 * 跳转到修改CzybmAuthority页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CzybmAuthority页面");
		CzybmAuthority czybmAuthority = null;
		if (StringUtils.isNotBlank(id)){
			czybmAuthority = czybmAuthorityService.get(CzybmAuthority.class, Integer.parseInt(id));
		}else{
			czybmAuthority = new CzybmAuthority();
		}
		
		return new ModelAndView("admin/basic/czybmAuthority/czybmAuthority_update")
			.addObject("czybmAuthority", czybmAuthority);
	}
	
	/**
	 * 跳转到查看CzybmAuthority页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CzybmAuthority页面");
		CzybmAuthority czybmAuthority = czybmAuthorityService.get(CzybmAuthority.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/czybmAuthority/czybmAuthority_detail")
				.addObject("czybmAuthority", czybmAuthority);
	}
	/**
	 * 添加CzybmAuthority
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CzybmAuthority czybmAuthority){
		logger.debug("添加CzybmAuthority开始");
		try{
			this.czybmAuthorityService.save(czybmAuthority);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CzybmAuthority失败");
		}
	}
	
	/**
	 * 修改CzybmAuthority
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CzybmAuthority czybmAuthority){
		logger.debug("修改CzybmAuthority开始");
		try{
			this.czybmAuthorityService.update(czybmAuthority);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CzybmAuthority失败");
		}
	}
	
	/**
	 * 删除CzybmAuthority
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CzybmAuthority开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CzybmAuthority编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CzybmAuthority czybmAuthority = czybmAuthorityService.get(CzybmAuthority.class, Integer.parseInt(deleteId));
				if(czybmAuthority == null)
					continue;
				czybmAuthorityService.update(czybmAuthority);
			}
		}
		logger.debug("删除CzybmAuthority IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CzybmAuthority导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CzybmAuthority czybmAuthority){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		czybmAuthorityService.findPageBySql(page, czybmAuthority);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CzybmAuthority_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
