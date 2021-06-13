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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.project.PrjRegion;
import com.house.home.service.project.PrjRegionService;

@Controller
@RequestMapping("/admin/prjRegion")
public class PrjRegionController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(PrjRegionController.class);

	@Autowired
	private PrjRegionService prjRegionService;

	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjRegion prjRegion) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		prjRegionService.findPageBySql(page, prjRegion);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * PrjRegion列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/prjRegion/prjRegion_list");
	}

	/**
	 * PrjRegion查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/prjRegion/prjRegion_code");
	}

	/**
	 * 跳转到新增PrjRegion页面
	 * 
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("admin/project/prjRegion/prjRegion_save");
	}

	/**
	 * 跳转到修改PrjRegion页面
	 * 
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到修改PrjRegion页面");
		PrjRegion prjRegion = null;
		if (StringUtils.isNotBlank(id)) {
			prjRegion = prjRegionService.get(PrjRegion.class, id);
		} else {
			prjRegion = new PrjRegion();
		}

		return new ModelAndView("admin/project/prjRegion/prjRegion_update")
				.addObject("prjRegion", prjRegion);
	}

	/**
	 * 跳转到查看PrjRegion页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到查看PrjRegion页面");
		PrjRegion prjRegion = prjRegionService.get(PrjRegion.class, id);

		return new ModelAndView("admin/project/prjRegion/prjRegion_view")
				.addObject("prjRegion", prjRegion);
	}
	/**
	 * 根据ID查询工程大区详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBuilder")
	@ResponseBody
	public JSONObject getBuilder(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		PrjRegion prjRegion = prjRegionService.get(PrjRegion.class, id);
		if(prjRegion == null){
			return this.out("系统中不存在code="+id+"的项目名称信息", false);
		}
		return this.out(prjRegion, true);
	}
	/**
	 * 添加PrjRegion
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, PrjRegion prjRegion) {
		logger.debug("添加PrjRegion开始");
		if (prjRegionService.checkExsist(prjRegion)) {
			ServletUtils.outPrintFail(request, response, "大区名称已存在，添加失败");
		} else {
			try {
				prjRegion.setLastUpdate(new Date());
				prjRegion.setLastUpdatedBy(getUserContext(request).getCzybh());
				prjRegion.setExpired("F");
				prjRegion.setActionLog("ADD");
				System.out.println("流水号：" + prjRegion.getCode());
				this.prjRegionService.save(prjRegion);
				ServletUtils.outPrintSuccess(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtils.outPrintFail(request, response, "编码已存在，添加失败");
			}
		}
	}

	/**
	 * 修改PrjRegion
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, PrjRegion prjRegion) {
		logger.debug("修改PrjRegion开始");
		if (prjRegionService.checkExsist(prjRegion)) {
			ServletUtils.outPrintFail(request, response, "大区名称已存在，添加失败");
		} else {
			try {
				if (!"T".equals(prjRegion.getExpired())) {
					prjRegion.setExpired("F");
				}
				prjRegion.setLastUpdate(new Date());
				prjRegion.setActionLog("EDIT");
				prjRegion.setLastUpdatedBy(getUserContext(request).getCzybh());
				prjRegionService.doUpdate(prjRegion);
				ServletUtils.outPrintSuccess(request, response);
			} catch (Exception e) {
				ServletUtils.outPrintFail(request, response, "编码已存在，修改失败");
			}
		}
	}

	/**
	 * 删除PrjRegion
	 * 
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request,
			HttpServletResponse response, String deleteIds) {
		logger.debug("删除PrjRegion开始");
		if (StringUtils.isBlank(deleteIds)) {
			ServletUtils
					.outPrintFail(request, response, "PrjRegion编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for (String deleteId : deleteIdList) {
			if (deleteId != null) {
				PrjRegion prjRegion = prjRegionService.get(PrjRegion.class,
						deleteId);
				if (prjRegion == null)
					continue;
				prjRegion.setExpired("T");
				prjRegionService.update(prjRegion);
			}
		}
		logger.debug("删除PrjRegion IDS={} 完成", deleteIdList);
		ServletUtils.outPrintSuccess(request, response, "删除成功");
	}

	/**
	 * PrjRegion导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, PrjRegion prjRegion) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjRegionService.findPageBySql(page, prjRegion);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"PrjRegion_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

}
