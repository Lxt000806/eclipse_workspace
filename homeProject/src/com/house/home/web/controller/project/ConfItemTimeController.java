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
import com.house.home.entity.project.ConfItemTime;
import com.house.home.service.project.ConfItemTimeService;
import com.house.home.bean.project.ConfItemTimeBean;

@Controller
@RequestMapping("/admin/confItemTime")
public class ConfItemTimeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ConfItemTimeController.class);

	@Autowired
	private ConfItemTimeService confItemTimeService;

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
			HttpServletResponse response, ConfItemTime confItemTime) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		confItemTimeService.findPageBySql(page, confItemTime);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ConfItemTime列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/confItemTime/confItemTime_list");
	}
	/**
	 * ConfItemTime查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/confItemTime/confItemTime_code");
	}
	/**
	 * 跳转到新增ConfItemTime页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ConfItemTime页面");
		ConfItemTime confItemTime = null;
		if (StringUtils.isNotBlank(id)){
			confItemTime = confItemTimeService.get(ConfItemTime.class, id);
			confItemTime.setCode(null);
		}else{
			confItemTime = new ConfItemTime();
		}
		
		return new ModelAndView("admin/project/confItemTime/confItemTime_save")
			.addObject("confItemTime", confItemTime);
	}
	/**
	 * 跳转到修改ConfItemTime页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ConfItemTime页面");
		ConfItemTime confItemTime = null;
		if (StringUtils.isNotBlank(id)){
			confItemTime = confItemTimeService.get(ConfItemTime.class, id);
		}else{
			confItemTime = new ConfItemTime();
		}
		
		return new ModelAndView("admin/project/confItemTime/confItemTime_update")
			.addObject("confItemTime", confItemTime);
	}
	
	/**
	 * 跳转到查看ConfItemTime页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ConfItemTime页面");
		ConfItemTime confItemTime = confItemTimeService.get(ConfItemTime.class, id);
		
		return new ModelAndView("admin/project/confItemTime/confItemTime_detail")
				.addObject("confItemTime", confItemTime);
	}
	/**
	 * 添加ConfItemTime
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ConfItemTime confItemTime){
		logger.debug("添加ConfItemTime开始");
		try{
			String str = confItemTimeService.getSeqNo("tConfItemTime");
			confItemTime.setCode(str);
			confItemTime.setLastUpdate(new Date());
			confItemTime.setLastUpdatedBy(getUserContext(request).getCzybh());
			confItemTime.setExpired("F");
			this.confItemTimeService.save(confItemTime);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ConfItemTime失败");
		}
	}
	
	/**
	 * 修改ConfItemTime
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ConfItemTime confItemTime){
		logger.debug("修改ConfItemTime开始");
		try{
			confItemTime.setLastUpdate(new Date());
			confItemTime.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.confItemTimeService.update(confItemTime);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ConfItemTime失败");
		}
	}
	
	/**
	 * 删除ConfItemTime
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ConfItemTime开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ConfItemTime编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ConfItemTime confItemTime = confItemTimeService.get(ConfItemTime.class, deleteId);
				if(confItemTime == null)
					continue;
				confItemTime.setExpired("T");
				confItemTimeService.update(confItemTime);
			}
		}
		logger.debug("删除ConfItemTime IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}


}
