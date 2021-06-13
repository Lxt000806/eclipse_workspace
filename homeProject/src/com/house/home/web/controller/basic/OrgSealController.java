package com.house.home.web.controller.basic;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.esign.ESignUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.OrgSeal;
import com.house.home.entity.basic.OrgSeal;
import com.house.home.service.basic.OrgSealService;
import com.house.home.service.basic.TaxPayeeESignService;

@Controller
@RequestMapping("/admin/orgSeal")
public class OrgSealController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(OrgSealController.class);

	@Autowired
	private OrgSealService orgSealService;
	@Autowired
	private TaxPayeeESignService taxPayeeESignService;

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
			HttpServletResponse response, OrgSeal orgSeal) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		orgSealService.findPageBySql(page, orgSeal);
		
		List<Map<String, Object>> resultList = page.getResult();
		List<Map<String, Object>> newResultList = new ArrayList<Map<String, Object>>();
		ESignUtils.generateESignToken(true);
		JSONArray sealJsonArray = ESignUtils.queryOrgSeal(orgSeal.getOrgId());
		//遍历结果集，匹配url
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> resultMap = resultList.get(i);
			for (int j = 0; j < sealJsonArray.size(); j++) {
				JSONObject jsonObject = sealJsonArray.getJSONObject(j);
				if(resultMap.get("sealid").toString().equals(jsonObject.getString("sealId"))){
					resultMap.put("url", jsonObject.getString("url"));
					break;
				}
			}
			newResultList.add(resultMap);
		}
		
		page.setResult(newResultList);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getOrgSeal")
	@ResponseBody
	public JSONObject getOrgSeal(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		OrgSeal orgSeal = orgSealService.get(OrgSeal.class, Integer.parseInt(id));
		if(orgSeal == null){
			return this.out("系统中不存在code="+id+"的印章信息", false);
		}
		return this.out(orgSeal, true);
	}
	
	/**
	 * 印章列表code
	 * 
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,HttpServletResponse response,OrgSeal orgSeal) throws Exception {
		return new ModelAndView("admin/basic/orgSeal/orgSeal_code").addObject("orgSeal", orgSeal);
	}
	
	/**
	 * OrgSeal列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,OrgSeal orgSeal) throws Exception {

		return new ModelAndView("admin/basic/orgSeal/orgSeal_list")
		.addObject("orgSeal", orgSeal);
	}

	/**
	 * 跳转到新增OrgSeal页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			OrgSeal orgSeal){
		logger.debug("跳转到新增OrgSeal页面");
		return new ModelAndView("admin/basic/orgSeal/orgSeal_save")
			.addObject("orgSeal", orgSeal);
	}
	/**
	 * 跳转到修改OrgSeal页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改OrgSeal页面");
		OrgSeal orgSeal = orgSealService.get(OrgSeal.class, pk);
		orgSeal.setM_umState("M");
		return new ModelAndView("admin/basic/orgSeal/orgSeal_update")
			.addObject("orgSeal", orgSeal);
	}
	
	/**
	 * 跳转到查看OrgSeal页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看OrgSeal页面");
		OrgSeal orgSeal = orgSealService.get(OrgSeal.class,pk);
		orgSeal.setM_umState("V");
		return new ModelAndView("admin/basic/orgSeal/orgSeal_update")
				.addObject("orgSeal", orgSeal);
	}
	/**
	 * 添加OrgSeal
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, OrgSeal orgSeal){
		logger.debug("添加OrgSeal开始");
		try{
			orgSeal.setLastUpdate(new Date());
			orgSeal.setLastUpdatedBy(getUserContext(request).getCzybh());
			orgSeal.setExpired("F");
			orgSeal.setActionLog("ADD");
			this.orgSealService.doSave(orgSeal);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加OrgSeal失败");
		}
	}
	
	/**
	 * 修改OrgSeal
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, OrgSeal orgSeal){
		logger.debug("修改OrgSeal开始");
		try{
			orgSeal.setExpired("F");
			orgSeal.setLastUpdate(new Date());
			orgSeal.setLastUpdatedBy(getUserContext(request).getCzybh());
			orgSeal.setActionLog("EDIT");
			this.orgSealService.doUpdate(orgSeal);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改OrgSeal失败");
		}
	}
	
	/**
	 * 删除OrgSeal
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("删除OrgSeal开始");
		
		OrgSeal orgSeal = orgSealService.get(OrgSeal.class,pk);
		List<Map<String, Object>> list = taxPayeeESignService.isEnableSeal(orgSeal.getSealId());
		if(list.size() > 0 && list != null){
			ServletUtils.outPrintFail(request, response, "该印章已启用，无法删除");
			return;
		}
		
		orgSealService.doDelete(orgSeal);
		
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}


}
