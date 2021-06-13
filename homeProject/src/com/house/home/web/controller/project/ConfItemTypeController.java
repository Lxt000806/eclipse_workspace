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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.ConfItemType;
import com.house.home.service.project.ConfItemTypeService;
import com.house.home.bean.project.ConfItemTypeBean;

@Controller
@RequestMapping("/admin/confItemType")
public class ConfItemTypeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ConfItemTypeController.class);

	@Autowired
	private ConfItemTypeService confItemTypeService;

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
			HttpServletResponse response, ConfItemType confItemType) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		confItemTypeService.findPageBySql(page, confItemType);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ConfItemType列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/confItemType/confItemType_list");
	}
	/**
	 * ConfItemType查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/confItemType/confItemType_code");
	}
	/**
	 * 跳转到新增ConfItemType页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ConfItemType页面");
		ConfItemType confItemType = null;
		if (StringUtils.isNotBlank(id)){
			confItemType = confItemTypeService.get(ConfItemType.class, id);
			confItemType.setCode(null);
		}else{
			confItemType = new ConfItemType();
		}
		
		return new ModelAndView("admin/project/confItemType/confItemType_save")
			.addObject("confItemType", confItemType);
	}
	/**
	 * 跳转到修改ConfItemType页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ConfItemType页面");
		ConfItemType confItemType = null;
		if (StringUtils.isNotBlank(id)){
			confItemType = confItemTypeService.get(ConfItemType.class, id);
		}else{
			confItemType = new ConfItemType();
		}
		
		return new ModelAndView("admin/project/confItemType/confItemType_update")
			.addObject("confItemType", confItemType);
	}
	
	/**
	 * 跳转到查看ConfItemType页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ConfItemType页面");
		ConfItemType confItemType = confItemTypeService.get(ConfItemType.class, id);
		
		return new ModelAndView("admin/project/confItemType/confItemType_detail")
				.addObject("confItemType", confItemType);
	}
	/**
	 * 添加ConfItemType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ConfItemType confItemType){
		logger.debug("添加ConfItemType开始");
		try{
			String str = confItemTypeService.getSeqNo("tConfItemType");
			confItemType.setCode(str);
			confItemType.setLastUpdate(new Date());
			confItemType.setLastUpdatedBy(getUserContext(request).getCzybh());
			confItemType.setExpired("F");
			this.confItemTypeService.save(confItemType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ConfItemType失败");
		}
	}
	
	/**
	 * 修改ConfItemType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ConfItemType confItemType){
		logger.debug("修改ConfItemType开始");
		try{
			confItemType.setLastUpdate(new Date());
			confItemType.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.confItemTypeService.update(confItemType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ConfItemType失败");
		}
	}
	
	/**
	 * 删除ConfItemType
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ConfItemType开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ConfItemType编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ConfItemType confItemType = confItemTypeService.get(ConfItemType.class, deleteId);
				if(confItemType == null)
					continue;
				confItemType.setExpired("T");
				confItemTypeService.update(confItemType);
			}
		}
		logger.debug("删除ConfItemType IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}



}
