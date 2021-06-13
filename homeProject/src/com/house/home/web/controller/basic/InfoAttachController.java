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
import com.house.home.entity.basic.InfoAttach;
import com.house.home.service.basic.InfoAttachService;

@Controller
@RequestMapping("/admin/infoAttach")
public class InfoAttachController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(InfoAttachController.class);

	@Autowired
	private InfoAttachService infoAttachService;

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
			HttpServletResponse response, InfoAttach infoAttach) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		infoAttachService.findPageBySql(page, infoAttach);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * InfoAttach列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/infoAttach/infoAttach_list");
	}
	/**
	 * InfoAttach查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/infoAttach/infoAttach_code");
	}
	/**
	 * 跳转到新增InfoAttach页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增InfoAttach页面");
		InfoAttach infoAttach = null;
		if (StringUtils.isNotBlank(id)){
			infoAttach = infoAttachService.get(InfoAttach.class, Integer.parseInt(id));
			infoAttach.setPk(null);
		}else{
			infoAttach = new InfoAttach();
		}
		
		return new ModelAndView("admin/basic/infoAttach/infoAttach_save")
			.addObject("infoAttach", infoAttach);
	}
	/**
	 * 跳转到修改InfoAttach页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改InfoAttach页面");
		InfoAttach infoAttach = null;
		if (StringUtils.isNotBlank(id)){
			infoAttach = infoAttachService.get(InfoAttach.class, Integer.parseInt(id));
		}else{
			infoAttach = new InfoAttach();
		}
		
		return new ModelAndView("admin/basic/infoAttach/infoAttach_update")
			.addObject("infoAttach", infoAttach);
	}
	
	/**
	 * 跳转到查看InfoAttach页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看InfoAttach页面");
		InfoAttach infoAttach = infoAttachService.get(InfoAttach.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/infoAttach/infoAttach_detail")
				.addObject("infoAttach", infoAttach);
	}
	/**
	 * 添加InfoAttach
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, InfoAttach infoAttach){
		logger.debug("添加InfoAttach开始");
		try{
			this.infoAttachService.save(infoAttach);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加InfoAttach失败");
		}
	}
	
	/**
	 * 修改InfoAttach
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, InfoAttach infoAttach){
		logger.debug("修改InfoAttach开始");
		try{
			infoAttach.setLastUpdate(new Date());
			infoAttach.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.infoAttachService.update(infoAttach);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改InfoAttach失败");
		}
	}
	
	/**
	 * 删除InfoAttach
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除InfoAttach开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "InfoAttach编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				InfoAttach infoAttach = infoAttachService.get(InfoAttach.class, Integer.parseInt(deleteId));
				if(infoAttach == null)
					continue;
				infoAttach.setExpired("T");
				infoAttachService.update(infoAttach);
			}
		}
		logger.debug("删除InfoAttach IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *InfoAttach导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, InfoAttach infoAttach){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		infoAttachService.findPageBySql(page, infoAttach);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"InfoAttach_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
