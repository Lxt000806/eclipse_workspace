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
import com.house.home.entity.basic.CzyGnqx;
import com.house.home.service.basic.CzyGnqxService;

@Controller
@RequestMapping("/admin/czyGnqx")
public class CzyGnqxController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CzyGnqxController.class);

	@Autowired
	private CzyGnqxService czyGnqxService;

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
			HttpServletResponse response, CzyGnqx czyGnqx) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		czyGnqxService.findPageBySql(page, czyGnqx);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CzyGnqx列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czyGnqx/czyGnqx_list");
	}
	/**
	 * CzyGnqx查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czyGnqx/czyGnqx_code");
	}
	/**
	 * 跳转到新增CzyGnqx页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CzyGnqx页面");
		CzyGnqx czyGnqx = null;
		if (StringUtils.isNotBlank(id)){
			czyGnqx = czyGnqxService.get(CzyGnqx.class, id);
			czyGnqx.setCzybh(null);
		}else{
			czyGnqx = new CzyGnqx();
		}
		
		return new ModelAndView("admin/basic/czyGnqx/czyGnqx_save")
			.addObject("czyGnqx", czyGnqx);
	}
	/**
	 * 跳转到修改CzyGnqx页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CzyGnqx页面");
		CzyGnqx czyGnqx = null;
		if (StringUtils.isNotBlank(id)){
			czyGnqx = czyGnqxService.get(CzyGnqx.class, id);
		}else{
			czyGnqx = new CzyGnqx();
		}
		
		return new ModelAndView("admin/basic/czyGnqx/czyGnqx_update")
			.addObject("czyGnqx", czyGnqx);
	}
	
	/**
	 * 跳转到查看CzyGnqx页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CzyGnqx页面");
		CzyGnqx czyGnqx = czyGnqxService.get(CzyGnqx.class, id);
		
		return new ModelAndView("admin/basic/czyGnqx/czyGnqx_detail")
				.addObject("czyGnqx", czyGnqx);
	}
	/**
	 * 添加CzyGnqx
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CzyGnqx czyGnqx){
		logger.debug("添加CzyGnqx开始");
		try{
			String str = czyGnqxService.getSeqNo("tCZYGNQX");
			czyGnqx.setCzybh(str);
			this.czyGnqxService.save(czyGnqx);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CzyGnqx失败");
		}
	}
	
	/**
	 * 修改CzyGnqx
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CzyGnqx czyGnqx){
		logger.debug("修改CzyGnqx开始");
		try{
			this.czyGnqxService.update(czyGnqx);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CzyGnqx失败");
		}
	}
	
	/**
	 * 删除CzyGnqx
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CzyGnqx开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CzyGnqx编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CzyGnqx czyGnqx = czyGnqxService.get(CzyGnqx.class, deleteId);
				if(czyGnqx == null)
					continue;
				czyGnqxService.update(czyGnqx);
			}
		}
		logger.debug("删除CzyGnqx IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CzyGnqx导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CzyGnqx czyGnqx){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		czyGnqxService.findPageBySql(page, czyGnqx);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CzyGnqx_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
