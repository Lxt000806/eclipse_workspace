package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Czrz;
import com.house.home.service.basic.CzrzService;

@Controller
@RequestMapping("/admin/czrz")
public class CzrzController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CzrzController.class);

	@Autowired
	private CzrzService czrzService;
	
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
			HttpServletResponse response, Czrz czrz) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		czrzService.findPageBySql(page, czrz);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Czrz列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czrz/czrz_list");
	}
	
	/**
	 * 跳转到新增Czrz页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增Czrz页面");
		Czrz czrz = null;
		if (StringUtils.isNotBlank(id)){
			czrz = czrzService.get(Czrz.class, Long.parseLong(id));
			czrz.setCid(null);
		}else{
			czrz = new Czrz();
		}
		
		return new ModelAndView("admin/basic/czrz/czrz_save")
			.addObject("czrz", czrz);
	}
	/**
	 * 跳转到修改Czrz页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改Czrz页面");
		Czrz czrz = null;
		if (StringUtils.isNotBlank(id)){
			czrz = czrzService.get(Czrz.class, Long.parseLong(id));
		}else{
			czrz = new Czrz();
		}
		
		return new ModelAndView("admin/basic/czrz/czrz_update")
			.addObject("czrz", czrz);
	}
	
	/**
	 * 跳转到查看Czrz页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看Czrz页面");
		Czrz czrz = czrzService.get(Czrz.class, Long.parseLong(id));
		
		return new ModelAndView("admin/basic/czrz/czrz_detail")
				.addObject("czrz", czrz);
	}
	/**
	 * 添加Czrz
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Czrz czrz){
		logger.debug("添加Czrz开始");
		try{
			Czrz xt = this.czrzService.get(Czrz.class, czrz.getCid());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "Czrz重复");
				return;
			}
			this.czrzService.save(czrz);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加Czrz失败");
		}
	}
	
	/**
	 * 修改Czrz
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Czrz czrz){
		logger.debug("修改Czrz开始");
		try{
			Czrz xt = this.czrzService.get(Czrz.class, czrz.getCid());
			if (xt!=null){
				this.czrzService.update(czrz);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.czrzService.save(czrz);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Czrz失败");
		}
	}
	
	/**
	 * 删除Czrz
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Czrz开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Czrz编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Czrz czrz = czrzService.get(Czrz.class, Long.parseLong(deleteId));
				if(czrz == null)
					continue;
				czrzService.update(czrz);
			}
		}
		logger.debug("删除Czrz IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Czrz导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Czrz czrz){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		czrzService.findPageBySql(page, czrz);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"Czrz_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
