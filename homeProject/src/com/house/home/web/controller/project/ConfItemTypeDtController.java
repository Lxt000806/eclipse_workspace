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
import com.house.home.entity.project.ConfItemTypeDt;
import com.house.home.service.project.ConfItemTypeDtService;
import com.house.home.bean.project.ConfItemTypeDtBean;

@Controller
@RequestMapping("/admin/confItemTypeDt")
public class ConfItemTypeDtController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ConfItemTypeDtController.class);

	@Autowired
	private ConfItemTypeDtService confItemTypeDtService;

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
			HttpServletResponse response, ConfItemTypeDt confItemTypeDt) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		confItemTypeDtService.findPageBySql(page, confItemTypeDt);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ConfItemTypeDt列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/confItemTypeDt/confItemTypeDt_list");
	}
	/**
	 * ConfItemTypeDt查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/confItemTypeDt/confItemTypeDt_code");
	}
	/**
	 * 跳转到新增ConfItemTypeDt页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ConfItemTypeDt页面");
		ConfItemTypeDt confItemTypeDt = null;
		if (StringUtils.isNotBlank(id)){
			confItemTypeDt = confItemTypeDtService.get(ConfItemTypeDt.class, Integer.parseInt(id));
			confItemTypeDt.setPk(null);
		}else{
			confItemTypeDt = new ConfItemTypeDt();
		}
		
		return new ModelAndView("admin/project/confItemTypeDt/confItemTypeDt_save")
			.addObject("confItemTypeDt", confItemTypeDt);
	}
	/**
	 * 跳转到修改ConfItemTypeDt页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ConfItemTypeDt页面");
		ConfItemTypeDt confItemTypeDt = null;
		if (StringUtils.isNotBlank(id)){
			confItemTypeDt = confItemTypeDtService.get(ConfItemTypeDt.class, Integer.parseInt(id));
		}else{
			confItemTypeDt = new ConfItemTypeDt();
		}
		
		return new ModelAndView("admin/project/confItemTypeDt/confItemTypeDt_update")
			.addObject("confItemTypeDt", confItemTypeDt);
	}
	
	/**
	 * 跳转到查看ConfItemTypeDt页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ConfItemTypeDt页面");
		ConfItemTypeDt confItemTypeDt = confItemTypeDtService.get(ConfItemTypeDt.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/confItemTypeDt/confItemTypeDt_detail")
				.addObject("confItemTypeDt", confItemTypeDt);
	}
	/**
	 * 添加ConfItemTypeDt
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ConfItemTypeDt confItemTypeDt){
		logger.debug("添加ConfItemTypeDt开始");
		try{
			this.confItemTypeDtService.save(confItemTypeDt);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ConfItemTypeDt失败");
		}
	}
	
	/**
	 * 修改ConfItemTypeDt
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ConfItemTypeDt confItemTypeDt){
		logger.debug("修改ConfItemTypeDt开始");
		try{
			confItemTypeDt.setLastUpdate(new Date());
			confItemTypeDt.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.confItemTypeDtService.update(confItemTypeDt);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ConfItemTypeDt失败");
		}
	}
	
	/**
	 * 删除ConfItemTypeDt
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ConfItemTypeDt开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ConfItemTypeDt编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ConfItemTypeDt confItemTypeDt = confItemTypeDtService.get(ConfItemTypeDt.class, Integer.parseInt(deleteId));
				if(confItemTypeDt == null)
					continue;
				confItemTypeDt.setExpired("T");
				confItemTypeDtService.update(confItemTypeDt);
			}
		}
		logger.debug("删除ConfItemTypeDt IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}


}
