package com.house.home.web.controller.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.workflow.Department;
import com.house.home.service.basic.Department2Service;

@Controller
@RequestMapping("/admin/department2")
public class Department2Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(Department2Controller.class);

	@Autowired
	private Department2Service department2Service;
	
	@Resource(name = "departmentCacheManager")
	private ICacheManager departmentCacheManager;
	
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
			HttpServletResponse response,Department2 department2) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		department2Service.findPageBySql(page, department2);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *获取二级部门
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,Department2 department2){
		
		return new ModelAndView("admin/basic/department2/department2_code").addObject("department2",department2);
	}
	
	/**
	 * 根据id查询二级部门详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getDepartment2")
	@ResponseBody
	public JSONObject getPurchase(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的code为空", false);
		}
		Department2 department2= department2Service.get(Department2.class, id);
		if(department2 == null){
			return this.out("系统中不存在Code="+id+"的二级部门", false);
		}
		return this.out(department2, true);
	}
	
	/**
	 * 二级部门列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/department2/department2_list");
	}
	
	/**
	 * 跳转到新增二级部门页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,Department2 dpartment2){
		logger.debug("跳转到新增二级部门页面");
		Department2 department2 = null;
		if (StringUtils.isNotBlank(dpartment2.getCode())) {
			department2 = department2Service.getByCode(dpartment2.getCode());
			if("C".equals(dpartment2.getM_umState())){
				department2.setCode("");
			}
		}else{
			department2 = new Department2();
		}
		department2.setM_umState(dpartment2.getM_umState());		
		return new ModelAndView("admin/basic/department2/department2_save")
			.addObject("department2", department2);
	}
	/**
	 * 跳转到修改二级部门页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改二级部门页面");
		Department2 department2 = null;
		if (StringUtils.isNotBlank(id)){
			department2 = department2Service.get(Department2.class, id);
		}else{
			department2 = new Department2();
		}
		
		return new ModelAndView("admin/basic/department2/department2_update")
			.addObject("department2", department2);
	}
	
	/**
	 * 跳转到查看二级部门页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看二级部门页面");
		Department2 department2 = department2Service.get(Department2.class, id);
		
		return new ModelAndView("admin/basic/department2/department2_detail")
				.addObject("department2", department2);
	}
	/**
	 * 二级部门
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Department2 department2){
		logger.debug("添加二级部门开始");
		try{
			if(department2.getPlanNum()==null){
				department2.setPlanNum(0);
			}
			if(department2.getDispSeq()==null){
				department2.setDispSeq(0);
			}
			String str = department2Service.getSeqNo("tDepartment2");
			if(str==null){
				ServletUtils.outPrintFail(request, response, "二级部门编号获取失败");
				return;
			}
			String str2 = department2Service.getSeqNo("tDepartment");
			if (str2 == null) {
				ServletUtils.outPrintFail(request, response, "部门编号获取失败");
				return;
			}
			
			if (StringUtils.isBlank(department2.getIsOutChannel())) {
                department2.setIsOutChannel("0");
            }
			
			department2.setCode(str);
			department2.setDesc1(department2.getDesc2());
			department2.setExpired("F");
			department2.setLastUpdate(new Date());
			department2.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			department2.setActionLog("ADD");
			department2.setDepartment(str2);
			
			// 同步部门表
			Department1 department1= department2Service.get(Department1.class, department2.getDepartment1());//一级部门
			String path=department1.getDepartment()+"/"+str2;//拼接路径
			Department department = new Department(department2.getDepartment(),
					department1.getCmpCode(), department2.getDesc2(),
					department2.getDesc2(), department2.getLastUpdate(),
					department2.getLastUpdatedBy(), department2.getExpired(),
					department2.getActionLog(), department2.getPlanNum(),
					department2.getDispSeq(), department2.getBusiType(),
					department2.getDepType(), department1.getDepartment(), path, "", "1","1");
			
			department.setIsOutChannel(department2.getIsOutChannel());
			
			this.department2Service.save(department);
			this.department2Service.save(department2);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加二级部门失败");
		}
	}
	
	/**
	 * 修改二级部门
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, 
			Department2 department2){
		logger.debug("修改二级部门开始");
		try{
		    
		    if (StringUtils.isBlank(department2.getIsOutChannel())) {
                department2.setIsOutChannel("0");
            }
		    
			department2.setDesc1(department2.getDesc2());
			department2.setLastUpdate(new Date());
			department2.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			department2.setActionLog("EDIT");
			Department dpt=department2Service.get(Department.class,department2.getDepartment());
			// 同步部门表
			Department1 department1= department2Service.get(Department1.class, department2.getDepartment1());//一级部门
			String path=department1.getDepartment()+"/"+department2.getDepartment();//拼接路径
			Department department = new Department(department2.getDepartment(),
					department1.getCmpCode(), department2.getDesc2(),
					department2.getDesc2(), department2.getLastUpdate(),
					department2.getLastUpdatedBy(), department2.getExpired(),
					department2.getActionLog(), department2.getPlanNum(),
					department2.getDispSeq(), department2.getBusiType(),
					department2.getDepType(), department1.getDepartment(), path, "", dpt.getIsActual(),dpt.getIsProcDept());
			
			department.setIsOutChannel(department2.getIsOutChannel());
			
			this.department2Service.update(department2);
			this.department2Service.update(department);
			//过期时，也过期子部门
			if("T".equals(department2.getExpired())){
				this.department2Service.expiredChild(department2);
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存二级部门失败");
		}
	}
	
	/**
	 * 删除二级部门
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除二级部门开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "二级部门编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Department2 department2 = this.department2Service.get(Department2.class, deleteId);
				if(department2 == null)
					continue;
				this.department2Service.delete(department2);
			}
		}
		departmentCacheManager.refresh();
		logger.debug("删除二级部门 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/changeDepartment2")
	public void changeDepartment1(HttpServletRequest request,HttpServletResponse response,String code) {
		logger.debug("异步加载三级部门");
		List<Department3> list = (List<Department3>) departmentCacheManager.get("department2_"+code+"_list");
		StringBuffer sb = new StringBuffer("");
		sb.append("<option value=\"\">请选择...</option>");
		if (list != null && list.size()>0){
			for (Department3 at :list){
				sb.append("<option value=\"").append(at.getCode()).append("\">").append(at.getCode())
				.append(" ").append(at.getDesc2()).append("</option>");
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strSelect", sb.toString());
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/changeDepartment2s")
	public void changeDepartment2s(HttpServletRequest request,HttpServletResponse response,String code,String depType) {
		logger.debug("异步加载三级部门");
		String virtualRootLabel = "请选择";
		String virtualRootId = "_VIRTUAL_RO0T_ID_";
		String sqlValueKey = "code";
		String sqlLableKey = "desc1";
		String retStr = "[]";
		if (StringUtils.isBlank(code)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("strSelect", retStr);
			try {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toJSONString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String sql = "select a.code,a.desc1 "
				+"from tDepartment3 a inner join (select * from fStrToTable('"+code+"',',')) t on a.Department2=t.item "
				+"where a.Expired='F' order by a.Department2";
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", virtualRootId);
		item.put("pId", "");
		item.put("name", virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list != null && list.size() > 0){
			for(Map<String, Object> map : list){
				item = new HashMap<String, Object>();
				item.put("id", map.get(sqlValueKey));
				item.put("pId", virtualRootId);
				item.put("name", map.get(sqlValueKey)+" "+map.get(sqlLableKey));
				rsList.add(item);
			}
		}
		retStr = JSON.toJSONString(rsList);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strSelect", retStr);
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Department2 department2){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		department2Service.findPageBySql(page, department2);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"二级部门表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
