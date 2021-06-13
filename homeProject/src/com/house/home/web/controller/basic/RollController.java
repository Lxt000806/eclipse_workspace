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
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.entity.basic.Roll;
import com.house.home.service.basic.RollService;

@Controller
@RequestMapping("/admin/roll")
public class RollController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(RollController.class);

	@Autowired
	private RollService rollService;
	
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
			HttpServletResponse response,Roll roll) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		rollService.findPageBySql(page, roll);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Roll列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/roll/roll_list");
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
			HttpServletResponse response, Roll roll) throws Exception {

		Page<Map<String,Object>> page = this.newPage(request);
		rollService.findPageBySql(page, roll);

		return new ModelAndView("admin/basic/roll/roll_code")
			.addObject(CommonConstant.PAGE_KEY, page)
			.addObject("roll", roll);
	}
	/**
	 * 跳转到新增Roll页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		Roll roll = null;
		if (StringUtils.isNotBlank(id)){
			roll = rollService.get(Roll.class, id);
		}else{
			roll = new Roll();
		}
		
		return new ModelAndView("admin/basic/roll/roll_save")
			.addObject("roll", roll);
	}
	/**
	 * 跳转到修改Roll页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		Roll roll = null;
		if (StringUtils.isNotBlank(id)){
			roll = rollService.get(Roll.class, id);
		}else{
			roll = new Roll();
		}
		
		return new ModelAndView("admin/basic/roll/roll_update")
			.addObject("roll", roll);
	}
	
	/**
	 * 跳转到查看Roll页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){

	    Map<String, Object> roll = rollService.getByCode(id);
		
		return new ModelAndView("admin/basic/roll/roll_detail")
				.addObject("roll", roll);
	}
	/**
	 * 添加Roll
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Roll roll){
		try{
			Roll xt = this.rollService.get(Roll.class, roll.getCode());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "编号已存在，请换一个");
				return;
			}
			roll.setActionLog("ADD");
			roll.setExpired("F");
			roll.setLastUpdate(new Date());
			roll.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.rollService.save(roll);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
		    e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加角色失败，请重试！");
		}
	}
	
	/**
	 * 修改Roll
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Roll roll){
		try{
			Roll xt = this.rollService.get(Roll.class, roll.getCode());
			roll.setLastUpdate(new Date());
			roll.setLastUpdatedBy(getUserContext(request).getCzybh());
			if (xt!=null){
			    roll.setActionLog("EDIT");
				this.rollService.update(roll);
				ServletUtils.outPrintSuccess(request, response);
			}else{
			    roll.setActionLog("ADD");
			    roll.setExpired("F");
				this.rollService.save(roll);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改角色失败");
		}
	}
	
	/**
	 * 删除Roll
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Roll开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Roll编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Roll roll = rollService.get(Roll.class, deleteId);
				if(roll == null)
					continue;
				roll.setExpired("T");
				rollService.update(roll);
			}
		}
		logger.debug("删除Roll IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Roll导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Roll roll){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		rollService.findPageBySql(page, roll);
		getExcelList(request);
        ServletUtils.flushExcelOutputStream(request, response,
                page.getResult(),
                "角色信息_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
	}
	
	/**
	 * 根据code查询角色信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getRoll")
	@ResponseBody
	public JSONObject getRoll(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Roll roll = rollService.get(Roll.class,id);
		if(roll == null){
			return this.out("系统中不存在code="+id+"的角色信息", false);
		}
		

		return this.out(roll, true);
	}

}
