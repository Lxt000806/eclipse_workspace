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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Technology;
import com.house.home.entity.project.BefWorkType12;
import com.house.home.entity.project.BefWorkType12PK;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.project.WorkType12Item;
import com.house.home.service.basic.BefWorkType12Service;
import com.house.home.service.project.WorkType12Service;

@Controller
@RequestMapping("/admin/workType12")
public class WorkType12Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkType12Controller.class);

	@Autowired
	private WorkType12Service workType12Service;
	
	@Autowired
	private BefWorkType12Service befWorkType12Service;
	
	@RequestMapping(value = "/workType12/{type}/{pCode}") //获取商品类型1,2,3
	@ResponseBody
	public JSONObject getItemType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.workType12Service.findWorkType12Dept(type, pCode,this.getUserContext(request));
		return this.out(regionList, true);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request, WorkType12 workType12) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workType12Service.findPageBySql(page, workType12);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 获取工艺列表
	 * @author	created by zb
	 * @date	2019-4-30--上午11:29:52
	 * @param request
	 * @param workType12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTechnology")
	@ResponseBody
	public WebPage<Map<String,Object>> getTechnology(HttpServletRequest request, WorkType12 workType12) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workType12Service.getTechBySql(page, workType12);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * WorkType12列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(){

		return new ModelAndView("admin/project/workType12/workType12_list");
	}
	/**
	 * WorkType12查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode() throws Exception {

		return new ModelAndView("admin/project/workType12/workType12_code");
	}
	/**
	 * 跳转到新增WorkType12页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(String id){
		WorkType12 workType12 = null;
		if (StringUtils.isNotBlank(id)){
			workType12 = workType12Service.get(WorkType12.class, id);
			workType12.setCode(null);
		}else{
			workType12 = new WorkType12();
		}
		
		return new ModelAndView("admin/project/workType12/workType12_save")
			.addObject("workType12", workType12);
	}
	/**
	 * 跳转到修改WorkType12页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(@RequestParam("id") String id){
		WorkType12 workType12 = null;
		if (StringUtils.isNotBlank(id)){
			workType12 = workType12Service.get(WorkType12.class, id);
		}else{
			workType12 = new WorkType12();
		}
		if(StringUtils.isBlank(workType12.getIsPrjApp())){
			workType12.setIsPrjApp("1");
		}
		
		return new ModelAndView("admin/project/workType12/workType12_update")
			.addObject("workType12", workType12);
		}
	
	/**
	 * 跳转到查看WorkType12页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(@RequestParam("id") String id){
		Map<String, Object> workType12 = workType12Service.findViewInfo(id);
				
		return new ModelAndView("admin/project/workType12/workType12_detail")
				.addObject("workType12", workType12);
	}
	/**
	 * 图片模板配置
	 * @author	created by zb
	 * @date	2019-4-30--上午10:00:26
	 * @param code
	 * @return
	 */
	@RequestMapping("/goPicModelSet")
	@ResponseBody
	public ModelAndView goPicModelSet(String code){
        WorkType12 workType12 = workType12Service.get(WorkType12.class, code);
	    return new ModelAndView("admin/project/workType12/workType12_picModelSet")
        	.addObject("workType12", workType12);
	}
	/**
	 * 工艺信息编辑页面
	 * @author	created by zb
	 * @date	2019-4-30--下午3:18:37
	 * @param workType12
	 * @return
	 */
	@RequestMapping("/goTechItem")
	public ModelAndView goTechItem(WorkType12 workType12){
		Technology technology = new Technology();
		if (StringUtils.isNotBlank(workType12.getTechCode())) {
			technology = this.workType12Service.get(Technology.class, workType12.getTechCode());
		}
	    return new ModelAndView("admin/project/workType12/workType12_techItem")
        	.addObject("workType12", workType12)
        	.addObject("technology", technology);
	}
	/**
	 * 添加WorkType12
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WorkType12 workType12){
		try{
			String str = workType12Service.getSeqNo("tWorkType12");
			workType12.setCode(str);
			workType12.setLastUpdate(new Date());
			workType12.setLastUpdatedBy(getUserContext(request).getCzybh());
			workType12.setExpired("F");
			this.workType12Service.save(workType12);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加WorkType12失败");
		}
	}
	
	/**
	 * 修改WorkType12
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest req, HttpServletResponse resp, WorkType12 workType12){
		try{
			WorkType12 wt12 = this.workType12Service.get(WorkType12.class, workType12.getCode());
			workType12.setIsTechnology(wt12.getIsTechnology());
			workType12.setLastUpdate(new Date());
			workType12.setLastUpdatedBy(getUserContext(req).getCzybh());
			workType12.setActionLog("EDIT");
			if(workType12.getPayNum() == null || workType12.getPayNum().trim().length() == 0){
			    workType12.setPayNum(null);
			}else{
			    workType12.setPayNum(workType12.getPayNum().trim());
			}
			if(workType12.getExpired() == null || workType12.getExpired().trim().length() == 0){
			    workType12.setExpired("F");
			}
			this.workType12Service.update(workType12);
			ServletUtils.outPrintSuccess(req, resp);
		}catch(Exception e){
		    e.printStackTrace();
			ServletUtils.outPrintFail(req, resp, "修改WorkType12失败");
		}
	}
	
	/**
	 * 删除WorkType12
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest req, HttpServletResponse resp, String deleteIds){
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(req, resp, "WorkType12编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				WorkType12 workType12 = workType12Service.get(WorkType12.class, deleteId);
				if(workType12 == null) continue;
				workType12.setExpired("T");
				workType12Service.update(workType12);
			}
		}
		logger.debug("删除WorkType12 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(req, resp, "删除成功");
	}
	/**
	 * 更新是否上传图片模板
	 * @author	created by zb
	 * @date	2019-4-30--下午4:13:19
	 * @param request
	 * @param response
	 * @param workType12
	 */
	@RequestMapping("/doIsTechUpdate")
	public void doIsTechUpdate(HttpServletRequest request, HttpServletResponse response, WorkType12 workType12){
		logger.debug("更新是否上传图片模板信息");
		try{
			if (StringUtils.isNotBlank(workType12.getCode())) {
				WorkType12 wt12 = this.workType12Service.get(WorkType12.class, workType12.getCode());
				wt12.setLastUpdate(new Date());
				wt12.setLastUpdatedBy(workType12.getLastUpdatedBy());
				wt12.setIsTechnology(workType12.getIsTechnology());
				wt12.setActionLog("EDIT");
				this.workType12Service.update(wt12);
				ServletUtils.outPrintSuccess(request, response);
			} else {
				ServletUtils.outPrintFail(request, response, "无编号");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加WorkType12失败");
		}
	}
	/**
	 * 添加工艺信息
	 * @author	created by zb
	 * @date	2019-4-30--下午3:13:09
	 * @param request
	 * @param response
	 * @param technology
	 */
	@RequestMapping("/doTechAdd")
	public void doTechAdd(HttpServletRequest request, HttpServletResponse response, Technology technology){
		logger.debug("添加工艺信息");
		try{
			String str = workType12Service.getSeqNo("tTechnology");
			technology.setCode(str);
			technology.setLastUpdate(new Date());
			technology.setExpired("F");
			technology.setActionLog("ADD");
			this.workType12Service.save(technology);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加工艺信息失败");
		}
	}
	/**
	 * 更新工艺信息
	 * @author	created by zb
	 * @date	2019-4-30--下午3:13:39
	 * @param request
	 * @param response
	 * @param technology
	 */
	@RequestMapping("/doTechUpdate")
	public void doTechUpdate(HttpServletRequest request, HttpServletResponse response, Technology technology){
		logger.debug("更新工艺信息");
		try{
			technology.setLastUpdate(new Date());
			technology.setExpired("F");
			technology.setActionLog("Edit");
			this.workType12Service.update(technology);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "更新工艺信息失败");
		}
	}
	/**
	 * 删除工艺信息
	 * @author	created by zb
	 * @date	2019-4-30--下午3:57:52
	 * @param request
	 * @param response
	 * @param deleteIds
	 */
	@RequestMapping("/doTechDelete")
	public void doTechDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除工艺信息");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "编号不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					Technology technology = this.workType12Service.get(Technology.class, deleteId);
					technology.setExpired("T");
					this.workType12Service.update(technology);
					ServletUtils.outPrintSuccess(request, response, "删除成功");
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除工艺信息失败");
		}
	}
	/**
	 *WorkType12导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest req, WorkType12 workType12){
		Page<Map<String,Object>> page = this.newPage(req);
		page.setPageSize(-1);
		workType12Service.findPageBySql(page, workType12);
	}
	
	/** 获取已经设置的上一工种 */
	@RequestMapping("getBefWorkType12")
	@ResponseBody
	public List<Map<String, Object>> getBefWorkType12(String id){
	    return workType12Service.findBefWorkType(id);
	}
	
	/** 跳转到设置上一工种页面 */
	@RequestMapping("/goSetBefWorkType")
	public ModelAndView goSetBefWorkType(@RequestParam("id") String id){
	    
	    //工种分类12
	    WorkType12 workType12 = workType12Service.get(WorkType12.class, id);
	    
	    return new ModelAndView("admin/project/workType12/workType12_setBefWorkType")
	            .addObject("workType12", workType12);
	}
	
	@RequestMapping("/doDeleteBefWorkType")
	@ResponseBody
	public String doDeleteBefWorkType(String workType12Code, String befWorkType12Code){
	    BefWorkType12 befWorkType12 = new BefWorkType12();
	    BefWorkType12PK befWorkType12PK = new BefWorkType12PK();
	    befWorkType12PK.setWorkType12(workType12Code);
	    befWorkType12PK.setBefWorktype12(befWorkType12Code);
	    befWorkType12.setId(befWorkType12PK);
	    befWorkType12Service.delete(befWorkType12);
	    return "success";
	}
	
	@RequestMapping("/goAddBefWorkType")
	public ModelAndView goAddBefWorkType(@RequestParam("id") String id){
	    
	    //工种分类12
        WorkType12 workType12 = workType12Service.get(WorkType12.class, id);
	    
	    return new ModelAndView("admin/project/workType12/workType12_addBefWorkType12")
	                 .addObject("workType12", workType12);
	}
	
	@RequestMapping("/doAddBefWorkType")
	@ResponseBody
	public String doAddBefWorkType(HttpServletRequest req,
	        String workType12Code, String befWorkType12Code, String isEval, String isNext,String workerArrCtrl){
	    BefWorkType12 befWorkType12 = new BefWorkType12();
        BefWorkType12PK befWorkType12PK = new BefWorkType12PK();
        befWorkType12PK.setWorkType12(workType12Code);
        befWorkType12PK.setBefWorktype12(befWorkType12Code);
        befWorkType12.setId(befWorkType12PK);
        befWorkType12.setIsEval(isEval);
        befWorkType12.setIsNext(isNext);
        befWorkType12.setLastUpdate(new Date());
        befWorkType12.setLastUpdatedBy(getUserContext(req).getCzybh());
        befWorkType12.setExpired("F");
        befWorkType12.setActionLog("ADD");
        befWorkType12.setWorkerArrCtrl(workerArrCtrl);
        befWorkType12Service.merge(befWorkType12);
        return "success";
	}
	
	@RequestMapping("/goUpdateBefWorkType")
	public ModelAndView goUpdateBefWorkType(@RequestParam("id") String id, @RequestParam("befWorkType12") String befWorkType12Id){
	    
	    //工种分类12
        WorkType12 workType12 = workType12Service.get(WorkType12.class, id);
        
        //上一工种记录
        BefWorkType12PK pk = new BefWorkType12PK();
        pk.setWorkType12(id);
        pk.setBefWorktype12(befWorkType12Id);
        BefWorkType12 befWorkTypeRecord = befWorkType12Service.get(BefWorkType12.class, pk);
        
	    return new ModelAndView("admin/project/workType12/workType12_updateBefWorkType12")
                    .addObject("workType12", workType12)
                    .addObject("befWorkType12Record", befWorkTypeRecord);
	}
	
	@RequestMapping("/doUpdateBefWorkType")
	@ResponseBody
	public String doUpdateBefWorkType(@RequestParam("workType12Code") String workType12Code,
	                                  @RequestParam("oldBefWorkTypeId") String oldBefWorkTypeId,
	                                  @RequestParam("newBefWorkTypeId") String newBefWorkTypeId,
	                                  @RequestParam("isEval") String isEval,
	                                  @RequestParam("isNext") String isNext,
	                                  @RequestParam("workerArrCtrl") String workerArrCtrl,
	                                  HttpServletRequest req){
	    
	    Long row = befWorkType12Service.executeUpdateBySql("update tBefWorkType12"
	                                       + " set BefWorkType12 = ?, IsEval = ?, LastUpdate = ?,"
	                                       + " LastUpdatedBy = ?, Expired = ?, ActionLog = ?, isNext=?,workerArrCtrl=? "
	                                       + " where WorkType12 = ? and BefWorkType12 = ?",
	                       newBefWorkTypeId, isEval, new Date(), getUserContext(req).getCzybh(), "F", "EDIT", isNext,workerArrCtrl,
	                       workType12Code, oldBefWorkTypeId);

	    if(!row.equals(new Long(1))){
	        return "false";
	    }
	    return "success";
	}

	@RequestMapping("/goConfig")
	public ModelAndView goConfig(String id){
	    
	    //工种分类12
        WorkType12 workType12 = workType12Service.get(WorkType12.class, id);
	    
	    return new ModelAndView("admin/project/workType12/workType12_config")
	                .addObject("workType12", workType12);
	}
	
	@RequestMapping("/getWorkType12Item")
	@ResponseBody
	public List<Map<String, Object>> getWorkType12Item(String id){
	    return workType12Service.findWorkType12Item(id);
	}
	
	@RequestMapping("/goAddWorkType12Item")
	public ModelAndView goAddWorkType12Item(String id){
	    
	    //工种分类12
	    WorkType12 workType12 = workType12Service.get(WorkType12.class, id);
	    
	    return new ModelAndView("admin/project/workType12/workType12_addWorkType12Item")
	                .addObject("workType12", workType12);
	               
	}
	
	@RequestMapping("/doAddWorkType12Item")
	@ResponseBody
	public String doAddWorkType12Item(WorkType12Item workType12Item,
	                                  HttpServletRequest req){
	    workType12Item.setLastUpdate(new Date());
	    workType12Item.setLastUpdatedBy(getUserContext(req).getCzybh());
	    workType12Item.setActionLog("ADD");
	    workType12Item.setExpired("F");
	    workType12Service.addWorkType12Item(workType12Item);
	    return "success";
	}
	
	@RequestMapping("/doDeleteWorkType12Item")
	@ResponseBody
	public String doDeleteWorkType12Item(String pk){
	    workType12Service.deleteWorkType12Item(Integer.valueOf(pk));
	    return "success";
	}
	
	@RequestMapping("/goUpdateWorkType12Item")
	public ModelAndView goUpdateWorkType12Item(Integer pk){
	    
	    WorkType12Item workType12Item = workType12Service.get(WorkType12Item.class, pk);
	    
	    return new ModelAndView("admin/project/workType12/workType12_updateWorkType12Item")
	                .addObject("workType12Item", workType12Item);
	}
	
	@RequestMapping("/doUpdateWorkType12Item")
	@ResponseBody
	public String doUpdateWorkType12Item(WorkType12Item workType12Item,
	                                     HttpServletRequest req){
	    
	    System.out.println(workType12Item.getExpired());
	    workType12Item.setLastUpdate(new Date());
	    workType12Item.setLastUpdatedBy(getUserContext(req).getCzybh());
	    workType12Item.setActionLog("EDIT");
	    workType12Service.update(workType12Item);
	    return "success";
	}
	/**
	 * 跳转到质保金配置
	 * @author cjg
	 * @date 2019-8-29
	 * @param code
	 * @return
	 */
	@RequestMapping("/goQualityFeeSet")
	@ResponseBody
	public ModelAndView goQualityFeeSet(String code){
        WorkType12 workType12 = workType12Service.get(WorkType12.class, code);
	    return new ModelAndView("admin/project/workType12/workType12_qualityFeeSet")
        	.addObject("workType12", workType12);
	}
	/**
	 * 跳转到质保金配置新增页面
	 * @author cjg
	 * @date 2019-8-29
	 * @param workType12
	 * @return
	 */
	@RequestMapping("/goQltAdd")
	public ModelAndView goQltAdd(HttpServletRequest request,WorkType12 workType12){
	    return new ModelAndView("admin/project/workType12/workType12_qltAdd")
        	.addObject("workType12", workType12);
	}
	/**
	 * 跳转到质保金配置编辑页面
	 * @author cjg
	 * @date 2019-8-29
	 * @param workType12
	 * @return
	 */
	@RequestMapping("/goQltUpdate")
	public ModelAndView goQltItem(HttpServletRequest request,WorkType12 workType12){
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
	    return new ModelAndView("admin/project/workType12/workType12_qltUpdate")
        	.addObject("workType12", jsonObject);
	}
	/**
	 * 添加质保金配置
	 * @author cjg
	 * @date 2019-8-29
	 * @param request
	 * @param response
	 * @param workType12
	 */
	@RequestMapping("/doAddQlt")
	public void doAddQlt(HttpServletRequest request, HttpServletResponse response, WorkType12 workType12){
		logger.debug("添加质保金配置");
		try{
			workType12.setLastUpdate(new Date());
			workType12.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.workType12Service.doAddQlt(workType12);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加质保金配置失败，工人分类重复！");
		}
	}
	/**
	 * 修改质保金配置
	 * @author cjg
	 * @date 2019-8-29
	 * @param request
	 * @param response
	 * @param workType12
	 */
	@RequestMapping("/doUpdateQlt")
	public void doUpdateQlt(HttpServletRequest request, HttpServletResponse response, WorkType12 workType12){
		logger.debug("修改质保金配置");
		try{
			workType12.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.workType12Service.doUpdateQlt(workType12);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改质保金配置失败，工人分类重复！");
		}
	}
	/**
	 * 删除质保金配置
	 * @author cjg
	 * @date 2019-8-29
	 * @param request
	 * @param response
	 * @param workType12
	 */
	@RequestMapping("/doDeleteQlt")
	public void doDeleteQlt(HttpServletRequest request, HttpServletResponse response, WorkType12 workType12){
		logger.debug("删除质保金配置");
		try{
			this.workType12Service.doDeleteQlt(workType12);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除质保金配置失败");
		}
	}
	/**
	 * 质保金配置列表
	 * @author cjg
	 * @date 2019-8-29
	 * @param request
	 * @param workType12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goQualityJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goQualityJqGrid(HttpServletRequest request, WorkType12 workType12) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workType12Service.goQualityJqGrid(page, workType12);
		return new WebPage<Map<String,Object>>(page);
	}
}
