package com.house.home.web.controller.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.AgainAward;
import com.house.home.entity.project.AgainAwardDetail;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.project.AgainAwardService;

@Controller
@RequestMapping("/admin/againAward")
public class AgainAwardController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AgainAwardController.class);

	@Autowired
	private AgainAwardService againAwardService;
	
	@Autowired
	private CzybmService czybmService;
	
	//-----表格查询相关

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request, HttpServletResponse response, AgainAward againAward) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		UserContext userContext = getUserContext(request);
        if (czybmService.hasGNQXByCzybh(userContext.getCzybh(), "0336", "查看所有")) {
            againAward.setViewAll(true);
        }
		
		againAwardService.goJqGrid(page, againAward, userContext);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJqGridAddDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridAddDetail(HttpServletRequest request, HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isBlank(customer.getStatus())){
			customer.setStatus("3,4,5");
		}
		againAwardService.goJqGridAddDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJqGridAgainAwardDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridAgainAwardDetail(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		List<Map<String, Object>> list = againAwardService.goJqGridAgainAwardDetail(page, no);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJqGridDetailList")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDetailList(HttpServletRequest request,
	        HttpServletResponse response, AgainAwardDetail againAwardDetail) {
	    
        UserContext userContext = getUserContext(request);
        if (czybmService.hasGNQXByCzybh(userContext.getCzybh(), "0336", "查看所有")) {
            againAwardDetail.setViewAll(true);
        }
	    
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		againAwardService.goJqGridDetailList(page, againAwardDetail, getUserContext(request));
		return new WebPage<Map<String,Object>>(page);
	}
	//-----页面跳转相关

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("跳转到签单管理首页");
		return new ModelAndView("admin/project/againAward/againAward_list");
	}

	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("跳转到签单管理新增页");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appCZY", getUserContext(request).getCzybh());
		map.put("appCZYDescr", againAwardService.get(Employee.class, getUserContext(request).getCzybh()).getNameChi());
		map.put("date", new Date());
		map.put("m_umState", "A");
		map.put("oldStatus", "1");
		map.put("no", "保存时生成");
		return new ModelAndView("admin/project/againAward/againAward_add").addObject("data", map);
	}
	
	@RequestMapping("/goAddSingleDetail")
	public ModelAndView goAddSingleDetail(HttpServletRequest request, HttpServletResponse response) {
	    return new ModelAndView("admin/project/againAward/againAward_addSingleDetail");
	}

	@RequestMapping("/goAddDetail")
	public ModelAndView goAddDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("跳转到签单管理新增明细页");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "4");
		map.put("codes", request.getParameter("codes"));
		return new ModelAndView("admin/project/againAward/againAward_addDetail").addObject("data", map);
	}
	
	@RequestMapping("/applyBonusScheme")
	@ResponseBody
    public List<Map<String, Object>> applyBonusScheme(@RequestParam String bonusScheme,@RequestParam String amount,
            @RequestBody List<Map<String, Object>> customers) {

        Set<String> bonusSchemes = new HashSet<String>();
        bonusSchemes.add("1");
        bonusSchemes.add("2");
        bonusSchemes.add("3");

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        
        if (StringUtils.isBlank(bonusScheme) || !bonusSchemes.contains(bonusScheme)) {
            return result;
        }
        
        for (Map<String, Object> customer : customers) {
            List<Map<String,Object>> stakeholders =
                    againAwardService.getStakeholders((String) customer.get("code"), bonusScheme);
            customer.put("amount", amount);
            
            for (Map<String, Object> stakeholder : stakeholders) {
                HashMap<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.putAll(customer);
                tempMap.putAll(stakeholder);
                result.add(tempMap);
            }
        }
        
        return result;
    }

	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		logger.debug("跳转到签单管理新增页(编辑)");
		Map<String, Object> map = againAwardService.getAgainAwardByNo(no);
		if(map != null){
			map.put("m_umState", "M");
		}
		return new ModelAndView("admin/project/againAward/againAward_add").addObject("data", map);
	}

	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		logger.debug("跳转到签单管理新增页(审核)");
		Map<String, Object> map = againAwardService.getAgainAwardByNo(no);
		if(map != null){
			map.put("m_umState", "C");
			map.put("confirmCZY", getUserContext(request).getCzybh());
			map.put("confirmCZYDescr", againAwardService.get(Employee.class, getUserContext(request).getCzybh()).getNameChi());
			map.put("confirmDate", new Date());
		}
		return new ModelAndView("admin/project/againAward/againAward_add").addObject("data", map);
	}

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		logger.debug("跳转到签单管理新增页(查看)");
		Map<String, Object> map = againAwardService.getAgainAwardByNo(no);
		if(map != null){
			map.put("m_umState", "V");
		}
		return new ModelAndView("admin/project/againAward/againAward_add").addObject("data", map);
	}

	@RequestMapping("/goAgainAwardDetailList")
	public ModelAndView goAgainAwardDetailList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("跳转到签单管理明细查询");
		return new ModelAndView("admin/project/againAward/againAward_detailList");
	}
	//-----操作相关
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, AgainAward againAward){
		logger.debug("新增签单申请开始");
		try{
			
			String detailJson = request.getParameter("detailJson");
			againAward.setCzybh(getUserContext(request).getCzybh());
			
			Result result = againAwardService.doSave(againAward, XmlConverUtil.jsonToXmlNoHead(detailJson));
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增签单申请出现异常");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, AgainAward againAward){
	    
	    UserContext userContext = getUserContext(request);
        if (czybmService.hasGNQXByCzybh(userContext.getCzybh(), "0336", "查看所有")) {
            againAward.setViewAll(true);
        }
	    
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		againAwardService.goJqGrid(page, againAward, userContext);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"签单申请管理-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doDetailListExcel")
	public void doDetailListExcel(HttpServletRequest request, HttpServletResponse response, AgainAwardDetail againAwardDetail){
	    
        UserContext userContext = getUserContext(request);
        if (czybmService.hasGNQXByCzybh(userContext.getCzybh(), "0336", "查看所有")) {
            againAwardDetail.setViewAll(true);
        }

		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		againAwardService.goJqGridDetailList(page, againAwardDetail, userContext);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"签单申请管理明细-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request ,
			HttpServletResponse response ,String no) throws Exception{
		return new ModelAndView("admin/project/againAward/againAward_print").addObject("no", no);
			
	}
}
