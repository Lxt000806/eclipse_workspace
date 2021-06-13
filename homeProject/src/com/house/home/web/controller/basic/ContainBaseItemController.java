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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ContainBaseItem;
import com.house.home.service.basic.ContainBaseItemService;
import com.house.home.bean.basic.ContainBaseItemBean;

@Controller
@RequestMapping("/admin/containBaseItem")
public class ContainBaseItemController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ContainBaseItemController.class);

	@Autowired
	private ContainBaseItemService containBaseItemService;

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
			HttpServletResponse response, ContainBaseItem containBaseItem) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		containBaseItemService.findPageBySql(page, containBaseItem);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ContainBaseItem列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/containBaseItem/containBaseItem_list");
	}
	/**
	 * ContainBaseItem查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/containBaseItem/containBaseItem_code");
	}
	/**
	 * 跳转到新增ContainBaseItem页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ContainBaseItem页面");
		ContainBaseItem containBaseItem = null;
		if (StringUtils.isNotBlank(id)){
			containBaseItem = containBaseItemService.get(ContainBaseItem.class, Integer.parseInt(id));
			containBaseItem.setPk(null);
		}else{
			containBaseItem = new ContainBaseItem();
		}
		
		return new ModelAndView("admin/basic/containBaseItem/containBaseItem_save")
			.addObject("containBaseItem", containBaseItem);
	}
	/**
	 * 跳转到修改ContainBaseItem页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ContainBaseItem页面");
		ContainBaseItem containBaseItem = null;
		if (StringUtils.isNotBlank(id)){
			containBaseItem = containBaseItemService.get(ContainBaseItem.class, Integer.parseInt(id));
		}else{
			containBaseItem = new ContainBaseItem();
		}
		
		return new ModelAndView("admin/basic/containBaseItem/containBaseItem_update")
			.addObject("containBaseItem", containBaseItem);
	}
	
	/**
	 * 跳转到查看ContainBaseItem页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ContainBaseItem页面");
		ContainBaseItem containBaseItem = containBaseItemService.get(ContainBaseItem.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/containBaseItem/containBaseItem_detail")
				.addObject("containBaseItem", containBaseItem);
	}
	/**
	 * 添加ContainBaseItem
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ContainBaseItem containBaseItem){
		logger.debug("添加ContainBaseItem开始");
		try{
			this.containBaseItemService.save(containBaseItem);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ContainBaseItem失败");
		}
	}
	
	/**
	 * 修改ContainBaseItem
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ContainBaseItem containBaseItem){
		logger.debug("修改ContainBaseItem开始");
		try{
			containBaseItem.setLastUpdate(new Date());
			containBaseItem.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.containBaseItemService.update(containBaseItem);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ContainBaseItem失败");
		}
	}
	
	/**
	 * 删除ContainBaseItem
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ContainBaseItem开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ContainBaseItem编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ContainBaseItem containBaseItem = containBaseItemService.get(ContainBaseItem.class, Integer.parseInt(deleteId));
				if(containBaseItem == null)
					continue;
				containBaseItem.setExpired("T");
				containBaseItemService.update(containBaseItem);
			}
		}
		logger.debug("删除ContainBaseItem IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}


}
