package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Advert;
import com.house.home.entity.basic.CustAccount;
import com.house.home.service.basic.AdvertService;
import com.house.home.service.basic.CustManageService;

@Controller
@RequestMapping("/admin/custManage")
public class CustManageController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustManageController.class);

	@Autowired
	private CustManageService custManageService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param advert
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,	HttpServletResponse response, CustAccount custAccount) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custManageService.goJqGrid(page, custAccount);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJqGridCustCode")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridCustCode(HttpServletRequest request,	HttpServletResponse response, CustAccount custAccount) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custManageService.goJqGridCustCode(page, custAccount);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,	HttpServletResponse response, CustAccount custAccount){
		logger.info("跳转到客户管理主页");
		return new ModelAndView("admin/basic/custManage/custManage_list").addObject("custAccount", custAccount);
	}

	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增客户页面");
		return new ModelAndView("admin/basic/custManage/custManage_save");
	}
	
	@RequestMapping("/getCustomers")
	@ResponseBody
	public List<Map<String, Object>> getCustomers(HttpServletRequest request, HttpServletResponse response, String phone){
		return custManageService.getCustomers(phone);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustAccount custAccount){
		try{
			Map<String, Object> returnMap = custManageService.doSave(custAccount);
			if(returnMap != null && "1".equals(returnMap.get("code").toString())){
				ServletUtils.outPrintSuccess(request, response, returnMap.get("msg").toString().trim().length() > 0 ? returnMap.get("msg").toString() : "新增客户成功");
			}else{
				ServletUtils.outPrintFail(request, response, returnMap == null || returnMap.get("msg").toString().length() == 0 ? "新增客户出错" : returnMap.get("msg").toString());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增客户异常");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, CustAccount custAccount){
		logger.debug("跳转到编辑客户页面");
		CustAccount ca = custManageService.get(CustAccount.class, custAccount.getPk());
		if(ca != null){
			ca.setMm(DesUtils.decode(ca.getMm()));
		}
		return new ModelAndView("admin/basic/custManage/custManage_update").addObject("custAccount", ca);
	}

	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustAccount custAccount){
		try{
			Map<String, Object> returnMap = custManageService.doUpdate(custAccount);
			if(returnMap != null && "1".equals(returnMap.get("code").toString())){
				ServletUtils.outPrintSuccess(request, response, returnMap.get("msg").toString().trim().length() > 0 ? returnMap.get("msg").toString() : "修改客户信息成功");
			}else{
				ServletUtils.outPrintFail(request, response, returnMap == null || returnMap.get("msg").toString().length() == 0 ? "修改客户信息出错" : returnMap.get("msg").toString());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改客户信息异常");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, CustAccount custAccount){
		logger.debug("跳转到客户详情页面");
		CustAccount ca = custManageService.get(CustAccount.class, custAccount.getPk());
		if(ca != null){
			ca.setMm(DesUtils.decode(ca.getMm()));
		}
		return new ModelAndView("admin/basic/custManage/custManage_view").addObject("custAccount", ca);
	}
}
