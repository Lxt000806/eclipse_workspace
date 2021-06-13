package com.house.home.web.controller.finance;

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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.DiscToken;
import com.house.home.service.design.ItemPlanService;
import com.house.home.service.finance.DiscTokenService;

@Controller
@RequestMapping("/admin/discTokenQuery")
public class DiscTokenQueryController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DiscTokenQueryController.class);

	@Autowired
	private DiscTokenService discTokenService;
	@Autowired
	private ItemPlanService itemPlanService;

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
			HttpServletResponse response, DiscToken discToken) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		discTokenService.findPageBySql(page, discToken);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * DiscToken列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/discToken/discToken_list");
	}
	/**
	 * DiscToken查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, DiscToken discToken) throws Exception {
			if (StringUtils.isNotBlank(discToken.getCustCode()) && "1".equals(discToken.getContainOldCustCode())){
				Customer customer = discTokenService.get(Customer.class, discToken.getCustCode());
				if(customer!=null){
					discToken.setOldCustCode(customer.getOldCode());
			    }
			}
		return new ModelAndView("admin/finance/discToken/discToken_code")
			.addObject("discToken", discToken);
	}
	/**
	 * 跳转到新增DiscToken页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增DiscToken页面");
		DiscToken discToken = null;
		if (StringUtils.isNotBlank(id)){
			discToken = discTokenService.get(DiscToken.class, id);
			discToken.setNo(null);
		}else{
			discToken = new DiscToken();
		}
		
		return new ModelAndView("admin/finance/discToken/discToken_save")
			.addObject("discToken", discToken);
	}
	/**
	 * 跳转到修改DiscToken页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改DiscToken页面");
		DiscToken discToken = null;
		if (StringUtils.isNotBlank(id)){
			discToken = discTokenService.get(DiscToken.class, id);
			discToken.setExpiredDate(new Date());
		}else{
			discToken = new DiscToken();
		}
		
		return new ModelAndView("admin/finance/discToken/discToken_update")
			.addObject("discToken", discToken);
	}
	
	/**
	 * 跳转到查看DiscToken页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看DiscToken页面");
		DiscToken discToken = discTokenService.get(DiscToken.class, id);
		
		return new ModelAndView("admin/finance/discToken/discToken_detail")
				.addObject("discToken", discToken);
	}
	/**
	 * 添加DiscToken
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, DiscToken discToken){
		logger.debug("添加DiscToken开始");
		try{
			String str = discTokenService.getSeqNo("tDiscToken");
			discToken.setNo(str);
			discToken.setLastUpdate(new Date());
			discToken.setLastUpdatedBy(getUserContext(request).getCzybh());
			discToken.setExpired("F");
			this.discTokenService.save(discToken);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加优惠券失败");
		}
	}
	
	/**
	 * 修改DiscToken
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, DiscToken discToken){
		logger.debug("修改DiscToken开始");
		try{
			if (StringUtils.isNotBlank(discToken.getNo())){
				DiscToken discTokenNew = discTokenService.get(DiscToken.class, discToken.getNo());
				if(!"2".equals(discTokenNew.getStatus())){
					ServletUtils.outPrintFail(request, response, "只有已生效的楼盘才能过期操作");
					return;
				}
				discTokenNew.setLastUpdate(new Date());
				discTokenNew.setLastUpdatedBy(getUserContext(request).getCzybh());
				discTokenNew.setExpired("F");
				discTokenNew.setExpiredDate(discToken.getExpiredDate());
				discTokenNew.setStatus("5");
				discTokenService.update(discTokenNew);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, "修改状态失败");
			}	
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改优惠券状态失败");
		}
	}
	
	/**
	 * 删除DiscToken
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除DiscToken开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "DiscToken编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				DiscToken discToken = discTokenService.get(DiscToken.class, deleteId);
				if(discToken == null)
					continue;
				discToken.setExpired("T");
				discTokenService.update(discToken);
			}
		}
		logger.debug("删除DiscToken IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *DiscToken导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, DiscToken discToken){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		discTokenService.findPageBySql(page, discToken);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"优惠券信息"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 跳转到选择优惠券编辑页面
	 * @param request
	 * @param response
	 * @param chgStakeholderList
	 * @return
	 */
	@RequestMapping("/goDiscTokenSelect")
	public ModelAndView goChgStakeholderEdit(HttpServletRequest request, HttpServletResponse response, 
			DiscToken discToken){
		logger.debug("跳转到增减干系人编辑页面");
		if (StringUtils.isNotBlank(discToken.getCustCode())) {
			 Customer customer=discTokenService.get(Customer.class, discToken.getCustCode());
			 if(customer!=null){
				 discToken.setOldCustCode(customer.getOldCode());
			 }	
		}
		return new ModelAndView("admin/finance/discToken/discToken_selectDetail")
			.addObject("discToken", discToken);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHasSelectJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goHasSelectJqGrid(HttpServletRequest request,
			HttpServletResponse response, DiscToken discToken) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		discTokenService.findHasSelectPageBySql(page, discToken);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getDiscTokenNo")
	@ResponseBody
	public DiscToken getDiscToken(HttpServletRequest request,HttpServletResponse response,
			DiscToken discToken){
		Customer customer = new Customer();
		discToken.setNo(discTokenService.getDiscTokenNo(discToken));
		String wfProcStatus= "";
		DiscToken discToken_new=null;
	    if (StringUtils.isNotBlank(discToken.getNo())){
	    	discToken_new  = discTokenService.get(DiscToken.class, discToken.getNo());
	    }
	    if(StringUtils.isNotBlank(discToken.getUseCustCode())){
	    	customer.setCode(discToken.getUseCustCode());
	    	wfProcStatus = itemPlanService.getSaleDiscApproveStatus(customer);
	    	if(discToken_new == null){
	    		discToken_new = new DiscToken();
	    	}
	    	discToken_new.setWfProcStatus(wfProcStatus);
	    }
	    
		return discToken_new;
	}
	
	@RequestMapping("/doRecover")
	public void doRecover(HttpServletRequest request, HttpServletResponse response, DiscToken discToken){
		logger.debug("修改DiscToken开始");
		try{
			if (StringUtils.isNotBlank(discToken.getNo())){
				discToken = discTokenService.get(DiscToken.class, discToken.getNo());
				if(!"5".equals(discToken.getStatus())){
					ServletUtils.outPrintFail(request, response, "只有已失效的楼盘才能过期操作");
					return;
				}
				
				discToken.setLastUpdate(new Date());
				discToken.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				discToken.setExpiredDate(null);
				discToken.setStatus("2");
				discTokenService.update(discToken);
				
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, "修改状态失败");
			}	
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改优惠券状态失败");
		}
	}

}
