package com.house.home.web.controller.insales;

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
import com.house.home.entity.insales.BaseItemReq;
import com.house.home.service.insales.BaseItemReqService;

@Controller
@RequestMapping("/admin/baseItemReq")
public class BaseItemReqController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseItemReqController.class);

	@Autowired
	private BaseItemReqService baseItemReqService;

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
			HttpServletResponse response, BaseItemReq baseItemReq) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemReqService.findPageBySql(page, baseItemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseItemReq列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/baseItemReq/baseItemReq_list");
	}
	/**
	 * BaseItemReq查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/baseItemReq/baseItemReq_code");
	}
	/**
	 * 跳转到新增BaseItemReq页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItemReq页面");
		BaseItemReq baseItemReq = null;
		if (StringUtils.isNotBlank(id)){
			baseItemReq = baseItemReqService.get(BaseItemReq.class, Integer.parseInt(id));
			baseItemReq.setPk(null);
		}else{
			baseItemReq = new BaseItemReq();
		}
		
		return new ModelAndView("admin/insales/baseItemReq/baseItemReq_save")
			.addObject("baseItemReq", baseItemReq);
	}
	/**
	 * 跳转到修改BaseItemReq页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BaseItemReq页面");
		BaseItemReq baseItemReq = null;
		if (StringUtils.isNotBlank(id)){
			baseItemReq = baseItemReqService.get(BaseItemReq.class, Integer.parseInt(id));
		}else{
			baseItemReq = new BaseItemReq();
		}
		
		return new ModelAndView("admin/insales/baseItemReq/baseItemReq_update")
			.addObject("baseItemReq", baseItemReq);
	}
	
	/**
	 * 跳转到查看BaseItemReq页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BaseItemReq页面");
		BaseItemReq baseItemReq = baseItemReqService.get(BaseItemReq.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/insales/baseItemReq/baseItemReq_detail")
				.addObject("baseItemReq", baseItemReq);
	}
	/**
	 * 添加BaseItemReq
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseItemReq baseItemReq){
		logger.debug("添加BaseItemReq开始");
		try{
			this.baseItemReqService.save(baseItemReq);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseItemReq失败");
		}
	}
	
	/**
	 * 修改BaseItemReq
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseItemReq baseItemReq){
		logger.debug("修改BaseItemReq开始");
		try{
			baseItemReq.setLastUpdate(new Date());
			baseItemReq.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseItemReqService.update(baseItemReq);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItemReq失败");
		}
	}
	
	/**
	 * 删除BaseItemReq
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseItemReq开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseItemReq编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseItemReq baseItemReq = baseItemReqService.get(BaseItemReq.class, Integer.parseInt(deleteId));
				if(baseItemReq == null)
					continue;
				baseItemReq.setExpired("T");
				baseItemReqService.update(baseItemReq);
			}
		}
		logger.debug("删除BaseItemReq IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BaseItemReq导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseItemReq baseItemReq){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemReqService.findPageBySql(page, baseItemReq);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"BaseItemReq_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
