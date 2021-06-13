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
import com.house.home.entity.basic.CzyDept;
import com.house.home.service.basic.CzyDeptService;

@Controller
@RequestMapping("/admin/czyDept")
public class CzyDeptController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CzyDeptController.class);

	@Autowired
	private CzyDeptService czyDeptService;
	
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
			HttpServletResponse response, CzyDept czyDept) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		czyDeptService.findPageBySql(page, czyDept);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CzyDept列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czyDept/czyDept_list");
	}
	
	/**
	 * 跳转到新增CzyDept页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CzyDept页面");
		CzyDept czyDept = null;
		if (StringUtils.isNotBlank(id)){
			czyDept = czyDeptService.get(CzyDept.class, Integer.parseInt(id));
			czyDept.setPk(null);
		}else{
			czyDept = new CzyDept();
		}
		
		return new ModelAndView("admin/basic/czyDept/czyDept_save")
			.addObject("czyDept", czyDept);
	}
	/**
	 * 跳转到修改CzyDept页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CzyDept页面");
		CzyDept czyDept = null;
		if (StringUtils.isNotBlank(id)){
			czyDept = czyDeptService.get(CzyDept.class, Integer.parseInt(id));
		}else{
			czyDept = new CzyDept();
		}
		
		return new ModelAndView("admin/basic/czyDept/czyDept_update")
			.addObject("czyDept", czyDept);
	}
	
	/**
	 * 跳转到查看CzyDept页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CzyDept页面");
		CzyDept czyDept = czyDeptService.get(CzyDept.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/czyDept/czyDept_detail")
				.addObject("czyDept", czyDept);
	}
	/**
	 * 添加CzyDept
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CzyDept czyDept){
		logger.debug("添加CzyDept开始");
		try{
			CzyDept xt = this.czyDeptService.get(CzyDept.class, czyDept.getPk());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "CzyDept重复");
				return;
			}
			this.czyDeptService.save(czyDept);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CzyDept失败");
		}
	}
	
	/**
	 * 修改CzyDept
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CzyDept czyDept){
		logger.debug("修改CzyDept开始");
		try{
			CzyDept xt = this.czyDeptService.get(CzyDept.class, czyDept.getPk());
			if (xt!=null){
				this.czyDeptService.update(czyDept);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.czyDeptService.save(czyDept);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CzyDept失败");
		}
	}
	
	/**
	 * 删除CzyDept
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CzyDept开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CzyDept编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CzyDept czyDept = czyDeptService.get(CzyDept.class, Integer.parseInt(deleteId));
				if(czyDept == null)
					continue;
				czyDept.setExpired("T");
				czyDeptService.update(czyDept);
			}
		}
		logger.debug("删除CzyDept IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CzyDept导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CzyDept czyDept){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		czyDeptService.findPageBySql(page, czyDept);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CzyDept_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
