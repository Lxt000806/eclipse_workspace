package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BuilderGroup;
import com.house.home.service.basic.BuilderGroupService;

@Controller
@RequestMapping("/admin/builderGroup")
public class BuilderGroupController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BuilderGroupController.class);

	@Autowired
	private BuilderGroupService builderGroupService;
	@Resource(name = "builderGroupCacheManager")
	private ICacheManager builderGroupCacheManager;
	
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
			HttpServletResponse response,BuilderGroup builderGroup) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		builderGroupService.findPageBySql(page, builderGroup);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 根据ID查询项目大类详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBuilderGroup")
	@ResponseBody
	public JSONObject getBuilderGroup(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		BuilderGroup builderGroup = builderGroupService.get(BuilderGroup.class, id);
		if(builderGroup == null){
			return this.out("系统中不存在code="+id+"的项目大类信息", false);
		}
		return this.out(builderGroup, true);
	}
	/**
	 * 项目大类列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/builderGroup/builderGroup_list");
	}
	
	/**
	 * 项目名称Code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/builderGroup/builderGroup_code");
	}
	
	/**
	 * 跳转到新增项目大类页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增项目大类页面");
		BuilderGroup builderGroup = new BuilderGroup();
		
		return new ModelAndView("admin/basic/builderGroup/builderGroup_save")
			.addObject("builderGroup", builderGroup);
	}
	/**
	 * 跳转到复制项目大类页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到复制项目大类页面");
		BuilderGroup builderGroup = null;
		if (StringUtils.isNotBlank(id)){
			builderGroup = builderGroupService.get(BuilderGroup.class, id);
		}else{
			builderGroup = new BuilderGroup();
		}
		
		return new ModelAndView("admin/basic/builderGroup/builderGroup_copy")
			.addObject("builderGroup", builderGroup);
	}
	/**
	 * 跳转到修改项目大类页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改项目大类页面");
		BuilderGroup builderGroup = null;
		if (StringUtils.isNotBlank(id)){
			builderGroup = builderGroupService.get(BuilderGroup.class, id);
		}else{
			builderGroup = new BuilderGroup();
		}
		
		return new ModelAndView("admin/basic/builderGroup/builderGroup_update")
			.addObject("builderGroup", builderGroup);
	}
	
	/**
	 * 跳转到查看项目大类页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看项目大类页面");
		BuilderGroup builderGroup = builderGroupService.get(BuilderGroup.class, id);
		
		return new ModelAndView("admin/basic/builderGroup/builderGroup_detail")
				.addObject("builderGroup", builderGroup);
	}
	/**
	 * 添加项目大类
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BuilderGroup builderGroup){
		logger.debug("添加项目大类开始");
		try{
			BuilderGroup xt = this.builderGroupService.getByDescr(builderGroup.getDescr());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "项目大类名称重复");
				return;
			}
			String str = builderGroupService.getSeqNo("tBuilderGroup");
			builderGroup.setCode(str);
			builderGroup.setLastUpdate(new Date());
			builderGroup.setLastUpdatedBy(getUserContext(request).getCzybh());
			builderGroup.setExpired("F");
			this.builderGroupService.save(builderGroup);
			builderGroupCacheManager.refresh();
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加项目大类失败");
		}
	}
	
	/**
	 * 复制项目大类
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request, HttpServletResponse response, BuilderGroup builderGroup){
		logger.debug("复制项目大类开始");
		try{
			BuilderGroup xt = this.builderGroupService.getByDescr(builderGroup.getDescr());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "项目大类名称重复");
				return;
			}
			String str = builderGroupService.getSeqNo("tBuilderGroup");
			builderGroup.setCode(str);
			builderGroup.setLastUpdate(new Date());
			builderGroup.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.builderGroupService.save(builderGroup);
			builderGroupCacheManager.refresh();
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加项目大类失败");
		}
	}
	
	/**
	 * 修改项目大类
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, 
			BuilderGroup builderGroup){
		logger.debug("修改项目大类开始");
		try{
			BuilderGroup xt = this.builderGroupService.getByDescr(builderGroup.getDescr());
			if (xt!=null && !xt.getCode().equals(builderGroup.getCode())){
				ServletUtils.outPrintFail(request, response, "项目大类名称重复");
				return;
			}else{
				builderGroup.setLastUpdate(new Date());
				builderGroup.setLastUpdatedBy(getUserContext(request).getCzybh());
				this.builderGroupService.update(builderGroup);
				builderGroupCacheManager.refresh();
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改项目大类失败");
		}
	}
	
	/**
	 * 删除项目大类
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除项目大类开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "项目大类编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BuilderGroup builderGroup = this.builderGroupService.get(BuilderGroup.class, deleteId);
				if(builderGroup == null)
					continue;
				this.builderGroupService.delete(builderGroup);
			}
		}
		builderGroupCacheManager.refresh();
		logger.debug("删除项目大类 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 项目大类导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BuilderGroup builderGroup){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		builderGroupService.findPageBySql(page, builderGroup);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"项目大类_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/checkBlur")
	public void checkBlur(HttpServletRequest request, HttpServletResponse response, String id, String info){
		logger.debug("check项目大类开始");
//		try {
//			Thread.currentThread().sleep(5000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		try{
			if (StringUtils.isNotBlank(id)){
				BuilderGroup builderGroup = builderGroupService.get(BuilderGroup.class, id);
				if (builderGroup!=null){
					JSONObject obj = new JSONObject();
					obj.put("descr", builderGroup.getDescr());
					ServletUtils.outPrintSuccess(request, response, obj);
				}else{
					if (StringUtils.isBlank(info)){
						info = "信息";
					}
					ServletUtils.outPrintFail(request, response, "该"+info+"不存在，请重新输入");
				}
			}else{
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "获取项目大类失败");
		}
	}

}
