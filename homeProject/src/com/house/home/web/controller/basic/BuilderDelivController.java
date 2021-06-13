package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.HashMap;
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
import com.house.home.entity.basic.BuilderDeliv;
import com.house.home.entity.basic.BuilderNum;
import com.house.home.service.basic.BuilderDelivService;
import com.house.home.service.basic.BuilderNumService;

@Controller
@RequestMapping("/admin/builderDeliv")
public class BuilderDelivController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BuilderDelivController.class);

	@Autowired
	private BuilderDelivService builderDelivService;
	@Autowired
	private BuilderNumService builderNumService;
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
			HttpServletResponse response, BuilderDeliv builderDeliv) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		builderDelivService.findPageBySql(page, builderDeliv);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BuilderDeliv列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/builderDeliv/builderDeliv_list");
	}
	/**
	 * BuilderDeliv查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/builderDeliv/builderDeliv_code");
	}
	/**
	 * 跳转到新增BuilderDeliv页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BuilderDeliv页面");
		BuilderDeliv builderDeliv = null;
		if (StringUtils.isNotBlank(id)){
			builderDeliv = builderDelivService.get(BuilderDeliv.class, id);
			builderDeliv.setCode(null);
		}else{
			builderDeliv = new BuilderDeliv();
		}
		builderDeliv.setBuilderCode(request.getParameter("builderCode"));
		return new ModelAndView("admin/basic/builderDeliv/builderDeliv_save")
			.addObject("builderDeliv", builderDeliv);
	}
	/**
	 * 跳转到修改BuilderDeliv页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BuilderDeliv页面");
		BuilderDeliv builderDeliv = null;
		if (StringUtils.isNotBlank(id)){
			builderDeliv = builderDelivService.get(BuilderDeliv.class, id);
		}else{
			builderDeliv = new BuilderDeliv();
		}
		
		return new ModelAndView("admin/basic/builderDeliv/builderDeliv_update")
			.addObject("builderDeliv", builderDeliv);
	}
	
	/**
	 * 跳转到查看BuilderDeliv页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BuilderDeliv页面");
		BuilderDeliv builderDeliv = builderDelivService.get(BuilderDeliv.class, id);
		
		return new ModelAndView("admin/basic/builderDeliv/builderDeliv_detail")
				.addObject("builderDeliv", builderDeliv);
	}
	/**
	 * 添加BuilderDeliv
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BuilderDeliv builderDeliv){
		logger.debug("添加BuilderDeliv开始");
		try{
			if(builderDeliv.getDelivNum()==null){
				builderDeliv.setDelivNum(0);
			}
			String str = builderDelivService.getSeqNo("tBuilderDeliv");
			builderDeliv.setCode(str);
			builderDeliv.setLastUpdate(new Date());
			builderDeliv.setLastUpdatedBy(getUserContext(request).getCzybh());
			builderDeliv.setExpired("F");
			this.builderDelivService.save(builderDeliv);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加BuilderDeliv失败");
		}
	}
	
	/**
	 * 修改BuilderDeliv
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BuilderDeliv builderDeliv){
		logger.debug("修改BuilderDeliv开始");
		try{
			if(builderDeliv.getDelivNum()==null){
				builderDeliv.setDelivNum(0);
			}
			builderDeliv.setLastUpdate(new Date());
			builderDeliv.setExpired("F");
			builderDeliv.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.builderDelivService.update(builderDeliv);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改BuilderDeliv失败");
		}
	}
	
	/**
	 * 删除BuilderDeliv
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BuilderDeliv开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BuilderDeliv编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BuilderDeliv builderDeliv = builderDelivService.get(BuilderDeliv.class, deleteId);
				if(builderDeliv == null)
					continue;
				builderDeliv.setExpired("T");
				builderDelivService.update(builderDeliv);
			}
		}
		BuilderNum builderNum=new BuilderNum();
		builderNum.setBuilderDelivCode(deleteIds);
		builderNumService.deleteBuilderNum(builderNum);
		logger.debug("删除BuilderDeliv IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BuilderDeliv导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BuilderDeliv builderDeliv){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		builderDelivService.findPageBySql(page, builderDeliv);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"BuilderDeliv_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 *找第一个builderDelivCode
	 * @param request
	 * @param response
	 */
	@RequestMapping("/findFirstDelivCode")
	@ResponseBody
	public Map<String, Object> findFirstDelivCode(HttpServletRequest request,
			HttpServletResponse response, String builderCode) throws Exception {
		List<Map<String, Object>> list = builderDelivService.findFirstDelivCode(builderCode);
		return list.size()>0&&list!=null?list.get(0):new HashMap<String, Object>();
	}
	
}
