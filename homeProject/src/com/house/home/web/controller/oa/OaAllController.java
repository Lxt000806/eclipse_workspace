package com.house.home.web.controller.oa;

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
import com.house.home.entity.oa.OaAll;
import com.house.home.service.oa.OaAllService;

@Controller
@RequestMapping("/admin/oaAll")
public class OaAllController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(OaAllController.class);

	@Autowired
	private OaAllService oaAllService;

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
			HttpServletResponse response, OaAll oaAll) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		oaAllService.findPageBySql(page, oaAll);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * OaAll列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/oa/oaAll/oaAll_list");
	}
	/**
	 * OaAll查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/oa/oaAll/oaAll_code");
	}
	/**
	 * 跳转到新增OaAll页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增OaAll页面");
		OaAll oaAll = null;
		if (StringUtils.isNotBlank(id)){
			oaAll = oaAllService.get(OaAll.class, id);
			oaAll.setProcInstId(null);
		}else{
			oaAll = new OaAll();
		}
		
		return new ModelAndView("admin/oa/oaAll/oaAll_save")
			.addObject("oaAll", oaAll);
	}
	/**
	 * 跳转到修改OaAll页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改OaAll页面");
		OaAll oaAll = null;
		if (StringUtils.isNotBlank(id)){
			oaAll = oaAllService.get(OaAll.class, id);
		}else{
			oaAll = new OaAll();
		}
		
		return new ModelAndView("admin/oa/oaAll/oaAll_update")
			.addObject("oaAll", oaAll);
	}
	
	/**
	 * 跳转到查看OaAll页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看OaAll页面");
		OaAll oaAll = oaAllService.get(OaAll.class, id);
		
		return new ModelAndView("admin/oa/oaAll/oaAll_detail")
				.addObject("oaAll", oaAll);
	}
	/**
	 * 添加OaAll
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, OaAll oaAll){
		logger.debug("添加OaAll开始");
		try{
			String str = oaAllService.getSeqNo("OA_ALL");
			oaAll.setProcInstId(str);
			this.oaAllService.save(oaAll);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加OaAll失败");
		}
	}
	
	/**
	 * 修改OaAll
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, OaAll oaAll){
		logger.debug("修改OaAll开始");
		try{
			this.oaAllService.update(oaAll);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改OaAll失败");
		}
	}
	
	/**
	 * 删除OaAll
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除OaAll开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "OaAll编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				OaAll oaAll = oaAllService.get(OaAll.class, deleteId);
				if(oaAll == null)
					continue;
				oaAllService.update(oaAll);
			}
		}
		logger.debug("删除OaAll IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *OaAll导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, OaAll oaAll){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		oaAllService.findPageBySql(page, oaAll);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"OaAll_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
