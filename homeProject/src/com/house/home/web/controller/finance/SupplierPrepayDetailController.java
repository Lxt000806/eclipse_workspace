package com.house.home.web.controller.finance;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.finance.SupplierPrepay;
import com.house.home.entity.finance.SupplierPrepayDetail;
import com.house.home.service.finance.SupplierPrepayDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/supplierPrepayDetail")
public class SupplierPrepayDetailController extends BaseController{
	@Autowired
	private  SupplierPrepayDetailService supplierPrepayDetailService;
	
	/**
	 * 获取材料编号
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepayDetail supplierPrepayDetail) throws Exception {

		return new ModelAndView("admin/finance/supplierPrepayDetail/supplierPrepayDetail_code")
			.addObject("supplierPrepay", supplierPrepayDetail);
	}
	
	/**
	 * 根据id查询材料详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSupplierPrepayDetail")
	@ResponseBody
	public JSONObject getItem(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		SupplierPrepayDetail supplierPrepayDetail =supplierPrepayDetailService.get(SupplierPrepayDetail.class, Integer.parseInt(id));
		if(supplierPrepayDetail == null){
			return this.out("系统中不存在code="+id+"的空间信息", false);
		}
		return this.out(supplierPrepayDetail, true);
	}
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepayDetail supplierPrepayDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierPrepayDetailService.findPageBySql(page, supplierPrepayDetail);
		return new WebPage<Map<String,Object>>(page);
	}	
}
