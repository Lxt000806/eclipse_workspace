package com.house.home.web.controller.basic;

import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.GiftToken;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.cmpActivity;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.GiftTokenService;

@Controller
@RequestMapping("/admin/giftToken")
public class GiftTokenController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GiftTokenController.class);

	@Autowired
	private GiftTokenService giftTokenService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param giftToken
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,	HttpServletResponse response, GiftToken giftToken) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftTokenService.findPageBySql(page, giftToken);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * GiftToken编号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, GiftToken giftToken) throws Exception {
		return new ModelAndView("admin/basic/giftToken/giftToken_code").addObject("giftToken", giftToken);
	}
	
	@RequestMapping("/getGiftToken")
	@ResponseBody
	public JSONObject getGiftApp(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		 GiftToken giftToken = giftTokenService.get(GiftToken.class, id);
		if(giftToken == null){
			return this.out("系统中不存在code="+id+"的礼品领用信息", false);
		}
		return this.out(giftToken, true);
	}
	/**
	 * GiftToken列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/giftToken/giftToken_list");
	}
	/**
	 * 跳转到新增GiftToken页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增GiftToken页面");
		GiftToken giftToken = new GiftToken();
		giftToken.setStatus("1");
		return new ModelAndView("admin/basic/giftToken/giftToken_save").addObject("giftToken", giftToken);
	}
	/**
	 * 跳转到修改GiftToken页面
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("/goEdit")
	public ModelAndView goEdit(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改GiftToken页面");
		GiftToken gt = giftTokenService.get(GiftToken.class, pk);
		Customer customer = giftTokenService.get(Customer.class, gt.getCustCode());
		Item item = giftTokenService.get(Item.class, gt.getItemCode());
		gt.setCustDescr(customer.getDescr());
		gt.setItemDescr(item.getDescr());
		if(StringUtils.isNotBlank(gt.getNo())){
			cmpActivity cmpActivity = giftTokenService.get(cmpActivity.class, gt.getNo());
			gt.setNoDescr(cmpActivity.getDescr());
		}
		return new ModelAndView("admin/basic/giftToken/giftToken_update").addObject("giftToken", gt);
	}
	
	/**
	 * 跳转到查看GiftToken页面
	 * @param request
	 * @param response
	 * @param giftToken
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,GiftToken giftToken){
		logger.debug("跳转到查看GiftToken页面");
		GiftToken gt = giftTokenService.get(GiftToken.class, giftToken.getPk());
		gt.setAddress(giftToken.getAddress());
		gt.setCustDescr(giftToken.getCustDescr());
		gt.setItemDescr(giftToken.getItemDescr());
		gt.setStatusDescr(giftToken.getStatusDescr());
		return new ModelAndView("admin/basic/giftToken/giftToken_detail").addObject("giftToken", gt);
	}
	/**
	 * 添加GiftToken
	 * @param request
	 * @param response
	 * @param giftToken
	 */
	@RequestMapping("/doAdd")
	public void doAdd(HttpServletRequest request, HttpServletResponse response,GiftToken giftToken){
		logger.debug("添加GiftToken开始");
		try{
			if(giftTokenService.existTokenNo(giftToken.getTokenNo().trim())!=null){
				ServletUtils.outPrintSuccess(request, response,"[{info=该券号已经登记过}]");
				return ;
			}
			giftToken.setLastUpdate(new Date());
			giftToken.setLastUpdatedBy(getUserContext(request).getCzybh());
			giftToken.setExpired("F");
			giftToken.setActionLog("ADD");
			giftTokenService.save(giftToken);
			ServletUtils.outPrintSuccess(request, response,"[{no="+giftToken.getNo()+",info=保存成功}]");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加GiftToken失败");
		}
	}
	
	/**
	 * 修改GiftToken
	 * @param request
	 * @param response
	 * @param giftToken
	 */
	@RequestMapping("/doEdit")
	public void doEdit(HttpServletRequest request, HttpServletResponse response,GiftToken giftToken){
		logger.debug("修改GiftToken开始");
		try{
			GiftToken gt = giftTokenService.get(GiftToken.class,giftToken.getPk());
			if(!gt.getTokenNo().trim().equals(giftToken.getTokenNo().trim())){
				if(giftTokenService.existTokenNo(giftToken.getTokenNo().trim())!=null){
					ServletUtils.outPrintSuccess(request, response,"该券号已经登记过");
					return ;
				}
			}
			giftToken.setLastUpdate(new Date());
			giftToken.setLastUpdatedBy(getUserContext(request).getCzybh());
			giftToken.setExpired("F");
			giftToken.setActionLog("EDIT");
			giftTokenService.update(giftToken);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改GiftToken失败");
		}
	}

	/**
	 * 礼品券管理导出Excel
	 * @param request
	 * @param response
	 * @param giftToken
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response,GiftToken giftToken){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		giftTokenService.findPageBySql(page, giftToken);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"礼品券管理_"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	/**
	 * 礼品券管理删除
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response,Integer pk){
		logger.debug("删除GiftToken记录开始");
		try{
			GiftToken gt = giftTokenService.get(GiftToken.class, pk);
			giftTokenService.delete(gt);
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除GiftToken记录失败");
		}
		
	}
}
