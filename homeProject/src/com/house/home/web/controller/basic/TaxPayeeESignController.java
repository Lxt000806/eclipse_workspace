package com.house.home.web.controller.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.esign.ESignUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.TaxPayeeESign;
import com.house.home.service.basic.TaxPayeeESignService;

@Controller
@RequestMapping("/admin/taxPayeeESign")
public class TaxPayeeESignController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TaxPayeeESignController.class);

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
			HttpServletResponse response, TaxPayeeESign taxPayeeESign) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		taxPayeeESignService.findPageBySql(page, taxPayeeESign);
		
		List<Map<String, Object>> resultList = page.getResult();
		List<Map<String, Object>> newResultList = new ArrayList<Map<String, Object>>();
		Map<String, JSONArray> orgIdMap = new HashMap<String, JSONArray>();
		
		ESignUtils.generateESignToken(true);
		
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> resultMap = resultList.get(i);
			String orgid = "";
			if (resultMap.get("orgid") != null) {
				orgid = resultMap.get("orgid").toString();
			}
			String sealid = "";
			if (resultMap.get("sealid") != null) {
				sealid = resultMap.get("sealid").toString();
			}
			//不存在机构账号，则把机构的印章数组存入map
			if(!orgIdMap.containsKey(orgid)){
				JSONArray sealJsonArray = ESignUtils.queryOrgSeal(orgid);
				orgIdMap.put(orgid, sealJsonArray);
			}
			
			//获取本次机构的印章数组
			JSONArray jsonArray = orgIdMap.get(orgid);
			
			if (jsonArray != null) {
				//遍历印章数组，匹配到印章图片路径存入新的结果集
				for (int j = 0; j < jsonArray.size(); j++) {
					JSONObject jsonObject = jsonArray.getJSONObject(j);
					if (jsonObject.getString("sealId").equals(sealid)) {
						resultMap.put("url", jsonObject.getString("url")) ;
						break;
					}
				}
			}
			
			newResultList.add(resultMap);
		}
		page.setResult(newResultList);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * TaxPayeeESign列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/taxPayeeESign/taxPayeeESign_list");
	}
	/**
	 * TaxPayeeESign查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/taxPayeeESign/taxPayeeESign_code");
	}
	/**
	 * 跳转到新增TaxPayeeESign页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增TaxPayeeESign页面");
		TaxPayeeESign taxPayeeESign =new TaxPayeeESign();
		taxPayeeESign.setM_umState("A");
		return new ModelAndView("admin/basic/taxPayeeESign/taxPayeeESign_save")
			.addObject("taxPayeeESign", taxPayeeESign);
	}
	/**
	 * 跳转到修改TaxPayeeESign页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改TaxPayeeESign页面");
		TaxPayeeESign taxPayeeESign = taxPayeeESignService.get(TaxPayeeESign.class, pk);
		taxPayeeESign.setM_umState("M");
		return new ModelAndView("admin/basic/taxPayeeESign/taxPayeeESign_save")
			.addObject("taxPayeeESign", taxPayeeESign);
	}
	
	/**
	 * 跳转到查看TaxPayeeESign页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看TaxPayeeESign页面");
		TaxPayeeESign taxPayeeESign = taxPayeeESignService.get(TaxPayeeESign.class, pk);
		taxPayeeESign.setM_umState("V");
		return new ModelAndView("admin/basic/taxPayeeESign/taxPayeeESign_save")
				.addObject("taxPayeeESign", taxPayeeESign);
	}
	
	/**
	 * 跳转到查看印章图片
	 * @return
	 */
	@RequestMapping("/goViewSealPic")
	public ModelAndView goViewSealPic(HttpServletRequest request, HttpServletResponse response, 
			TaxPayeeESign taxPayeeESign){
		logger.debug("跳转到查看TaxPayeeESign页面");
		String url = "";
		ESignUtils.generateESignToken(true);
		JSONArray sealJson = ESignUtils.queryOrgSeal(taxPayeeESign.getOrgId());
		
		for (int i = 0; i < sealJson.size(); i++) {
			JSONObject jsonObject = sealJson.getJSONObject(i);
			if (jsonObject.getString("sealId").equals(taxPayeeESign.getSealId())) {
				url = jsonObject.getString("url");
				break;
			}
		}
		return new ModelAndView("admin/basic/taxPayeeESign/taxPayeeESign_viewSealPic")
				.addObject("url", url);
	}
	
	/**
	 * 跳转到查看TaxPayeeESign页面
	 * @return
	 */
	@RequestMapping("/goOrgManage")
	public ModelAndView goOrgManage(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到查看TaxPayeeESign页面");
		return new ModelAndView("admin/basic/organization/organization_list");
	}
	
	/**
	 * 添加TaxPayeeESign
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, TaxPayeeESign taxPayeeESign){
		logger.debug("添加TaxPayeeESign开始");
		try{
			taxPayeeESign.setLastUpdate(new Date());
			taxPayeeESign.setLastUpdatedBy(getUserContext(request).getCzybh());
			taxPayeeESign.setExpired("F");
			taxPayeeESign.setActionLog("ADD");
			this.taxPayeeESignService.save(taxPayeeESign);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加失败，请检查收款单位是否重复");
		}
	}
	
	/**
	 * 修改TaxPayeeESign
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, TaxPayeeESign taxPayeeESign){
		logger.debug("修改TaxPayeeESign开始");
		try{
			taxPayeeESign.setExpired("F");
			taxPayeeESign.setLastUpdate(new Date());
			taxPayeeESign.setLastUpdatedBy(getUserContext(request).getCzybh());
			taxPayeeESign.setActionLog("EDIT");
			this.taxPayeeESignService.update(taxPayeeESign);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改TaxPayeeESign失败");
		}
	}
	
	/**
	 * 删除TaxPayeeESign
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除TaxPayeeESign开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "TaxPayeeESign编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				TaxPayeeESign taxPayeeESign = taxPayeeESignService.get(TaxPayeeESign.class, Integer.parseInt(deleteId));
				if(taxPayeeESign == null)
					continue;
				taxPayeeESignService.delete(taxPayeeESign);
			}
		}
		logger.debug("删除TaxPayeeESign IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *TaxPayeeESign导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, TaxPayeeESign taxPayeeESign){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		taxPayeeESignService.findPageBySql(page, taxPayeeESign);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"电子合同机构管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
