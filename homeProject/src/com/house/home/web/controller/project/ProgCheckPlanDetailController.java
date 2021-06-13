package com.house.home.web.controller.project;

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
import com.house.home.entity.project.ProgCheckPlanDetail;
import com.house.home.service.project.ProgCheckPlanDetailService;

@Controller
@RequestMapping("/admin/progCheckPlanDetail")
public class ProgCheckPlanDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProgCheckPlanDetailController.class);

	@Autowired
	private ProgCheckPlanDetailService progCheckPlanDetailService;

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
			HttpServletResponse response, ProgCheckPlanDetail progCheckPlanDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		progCheckPlanDetailService.findPageBySql(page, progCheckPlanDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ProgCheckPlanDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/progCheckPlanDetail/progCheckPlanDetail_list");
	}
	/**
	 * ProgCheckPlanDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/progCheckPlanDetail/progCheckPlanDetail_code");
	}
	/**
	 * 跳转到新增ProgCheckPlanDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ProgCheckPlanDetail页面");
		ProgCheckPlanDetail progCheckPlanDetail = null;
		if (StringUtils.isNotBlank(id)){
			progCheckPlanDetail = progCheckPlanDetailService.get(ProgCheckPlanDetail.class, Integer.parseInt(id));
			progCheckPlanDetail.setPk(null);
		}else{
			progCheckPlanDetail = new ProgCheckPlanDetail();
		}
		
		return new ModelAndView("admin/project/progCheckPlanDetail/progCheckPlanDetail_save")
			.addObject("progCheckPlanDetail", progCheckPlanDetail);
	}
	/**
	 * 跳转到修改ProgCheckPlanDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ProgCheckPlanDetail页面");
		ProgCheckPlanDetail progCheckPlanDetail = null;
		if (StringUtils.isNotBlank(id)){
			progCheckPlanDetail = progCheckPlanDetailService.get(ProgCheckPlanDetail.class, Integer.parseInt(id));
		}else{
			progCheckPlanDetail = new ProgCheckPlanDetail();
		}
		
		return new ModelAndView("admin/project/progCheckPlanDetail/progCheckPlanDetail_update")
			.addObject("progCheckPlanDetail", progCheckPlanDetail);
	}
	
	/**
	 * 跳转到查看ProgCheckPlanDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ProgCheckPlanDetail页面");
		ProgCheckPlanDetail progCheckPlanDetail = progCheckPlanDetailService.get(ProgCheckPlanDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/progCheckPlanDetail/progCheckPlanDetail_detail")
				.addObject("progCheckPlanDetail", progCheckPlanDetail);
	}
	/**
	 * 添加ProgCheckPlanDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ProgCheckPlanDetail progCheckPlanDetail){
		logger.debug("添加ProgCheckPlanDetail开始");
		try{
			this.progCheckPlanDetailService.save(progCheckPlanDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ProgCheckPlanDetail失败");
		}
	}
	
	/**
	 * 修改ProgCheckPlanDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ProgCheckPlanDetail progCheckPlanDetail){
		logger.debug("修改ProgCheckPlanDetail开始");
		try{
			progCheckPlanDetail.setLastUpdate(new Date());
			progCheckPlanDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.progCheckPlanDetailService.update(progCheckPlanDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ProgCheckPlanDetail失败");
		}
	}
	
	/**
	 * 删除ProgCheckPlanDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ProgCheckPlanDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ProgCheckPlanDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ProgCheckPlanDetail progCheckPlanDetail = progCheckPlanDetailService.get(ProgCheckPlanDetail.class, Integer.parseInt(deleteId));
				if(progCheckPlanDetail == null)
					continue;
				progCheckPlanDetail.setExpired("T");
				progCheckPlanDetailService.update(progCheckPlanDetail);
			}
		}
		logger.debug("删除ProgCheckPlanDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ProgCheckPlanDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ProgCheckPlanDetail progCheckPlanDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		progCheckPlanDetailService.findPageBySql(page, progCheckPlanDetail);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ProgCheckPlanDetail_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
