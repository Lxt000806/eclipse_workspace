package com.house.home.web.controller.project;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.BaseItemChgDetail;
import com.house.home.service.project.BaseItemChgDetailService;

@Controller
@RequestMapping("/admin/baseItemChgDetail")
public class BaseItemChgDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseItemChgDetailController.class);

	@Autowired
	private BaseItemChgDetailService baseItemChgDetailService;

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
			HttpServletResponse response, BaseItemChgDetail baseItemChgDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageOrderBy("isgroup,a.fixareapk,a.dispseq");
		baseItemChgDetailService.findPageByNo(page, baseItemChgDetail.getNo());
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseItemChgDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/baseItemChgDetail/baseItemChgDetail_list");
	}
	/**
	 * BaseItemChgDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/baseItemChgDetail/baseItemChgDetail_code");
	}
	/**
	 * 跳转到新增BaseItemChgDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItemChgDetail页面");
		BaseItemChgDetail baseItemChgDetail = null;
		if (StringUtils.isNotBlank(id)){
			baseItemChgDetail = baseItemChgDetailService.get(BaseItemChgDetail.class, Integer.parseInt(id));
			baseItemChgDetail.setPk(null);
		}else{
			baseItemChgDetail = new BaseItemChgDetail();
		}
		
		return new ModelAndView("admin/project/baseItemChgDetail/baseItemChgDetail_save")
			.addObject("baseItemChgDetail", baseItemChgDetail);
	}
	/**
	 * 跳转到修改BaseItemChgDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BaseItemChgDetail页面");
		BaseItemChgDetail baseItemChgDetail = null;
		if (StringUtils.isNotBlank(id)){
			baseItemChgDetail = baseItemChgDetailService.get(BaseItemChgDetail.class, Integer.parseInt(id));
		}else{
			baseItemChgDetail = new BaseItemChgDetail();
		}
		
		return new ModelAndView("admin/project/baseItemChgDetail/baseItemChgDetail_update")
			.addObject("baseItemChgDetail", baseItemChgDetail);
	}
	
	/**
	 * 跳转到查看BaseItemChgDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BaseItemChgDetail页面");
		BaseItemChgDetail baseItemChgDetail = baseItemChgDetailService.get(BaseItemChgDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/baseItemChgDetail/baseItemChgDetail_detail")
				.addObject("baseItemChgDetail", baseItemChgDetail);
	}
	/**
	 * 添加BaseItemChgDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseItemChgDetail baseItemChgDetail){
		logger.debug("添加BaseItemChgDetail开始");
		try{
			this.baseItemChgDetailService.save(baseItemChgDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseItemChgDetail失败");
		}
	}
	
	/**
	 * 修改BaseItemChgDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseItemChgDetail baseItemChgDetail){
		logger.debug("修改BaseItemChgDetail开始");
		try{
			baseItemChgDetail.setLastUpdate(new Date());
			baseItemChgDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseItemChgDetailService.update(baseItemChgDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItemChgDetail失败");
		}
	}
	
	/**
	 * 删除BaseItemChgDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseItemChgDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseItemChgDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseItemChgDetail baseItemChgDetail = baseItemChgDetailService.get(BaseItemChgDetail.class, Integer.parseInt(deleteId));
				if(baseItemChgDetail == null)
					continue;
				baseItemChgDetail.setExpired("T");
				baseItemChgDetailService.update(baseItemChgDetail);
			}
		}
		logger.debug("删除BaseItemChgDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BaseItemChgDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseItemChgDetail baseItemChgDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemChgDetailService.findPageBySql(page, baseItemChgDetail);
	}
	
	/**
	 * 构建合并临时JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/goTmpJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getTmpJqGrid(HttpServletRequest request,
			HttpServletResponse response,String params,String orderBy) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		JSONArray jsonArray = JSONArray.fromObject(params);  
		List<Map<String,Object>> list = (List)jsonArray;  
		Collections.sort(list, new ListCompareUtil(orderBy));
		if(page.isAutoCount()){
			page.setTotalCount(list.size());
		}
		page.setResult(list);		
		return new WebPage<Map<String,Object>>(page);

	}

}
