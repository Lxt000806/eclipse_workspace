package com.house.home.web.controller.project;

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

import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.design.CustLoan;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PrjCheck;
import com.house.home.entity.project.PrjWithHold;
import com.house.home.entity.project.Worker;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.WorkType2Service;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjWithHoldService;

@Controller
@RequestMapping("/admin/prjWithHold")
public class PrjWithHoldController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjWithHoldController.class);

	@Autowired
	private PrjWithHoldService prjWithHoldService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private WorkType2Service  workType2Service;
	
	private void resetPrjWithHold(PrjWithHold prjWithHold){
		if (prjWithHold!=null){
			if (StringUtils.isNotBlank(prjWithHold.getCustCode())){
				Customer customer = customerService.get(Customer.class, prjWithHold.getCustCode());
				if (customer!=null){
					prjWithHold.setCustDescr(customer.getDescr());
					prjWithHold.setAddress(customer.getAddress());
				}	
			}
			if (StringUtils.isNotBlank(prjWithHold.getWorkType2())){
				WorkType2 workType2 = workType2Service.get(WorkType2.class, prjWithHold.getWorkType2());
				if (workType2!=null){
					prjWithHold.setWorkType1(workType2.getWorkType1());
				}	
			}
		}			
			
	}
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjWithHold prjWithHold) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjWithHoldService.findPageBySql(page, prjWithHold);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PrjWithHold??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/prjWithHold/prjWithHold_list");
	}
	/**
	 * PrjWithHold??????code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,PrjWithHold prjWithHold) throws Exception {
		// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//		Boolean flag=czybmService.hasAuthByCzybh(getUserContext(request).getCzybh(), 1089);
		Boolean flag=czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0316", "????????????????????????");
		String isEditWorkType2=flag==false?"0":"1";
		return new ModelAndView("admin/project/prjWithHold/prjWithHold_code").addObject("prjWithHold", prjWithHold).addObject("isEditWorkType2",isEditWorkType2);
	}
	
	@RequestMapping("/goCodeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCodeJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjWithHold prjWithHold) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjWithHoldService.findCodePageBySql(page, prjWithHold);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ???????????????PrjWithHold??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,PrjWithHold prjWithHold){
		logger.debug("???????????????PrjWithHold??????");
		prjWithHold.setAmount(0.0);
		prjWithHold.setType("1");
		return new ModelAndView("admin/project/prjWithHold/prjWithHold_save")
			.addObject("prjWithHold", prjWithHold);
	}
	/**
	 * ???????????????PrjWithHold??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer id){
		logger.debug("???????????????PrjWithHold??????");
		PrjWithHold prjWithHold=null; 
		if (id!=null){
			prjWithHold = prjWithHoldService.get(PrjWithHold.class,id);	
			resetPrjWithHold(prjWithHold);
		}
		return new ModelAndView("admin/project/prjWithHold/prjWithHold_update")
			.addObject("prjWithHold", prjWithHold);
	}
	
	/**
	 * ???????????????PrjWithHold??????
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer id){
		logger.debug("???????????????PrjWithHold??????");
		PrjWithHold prjWithHold=null; 
		if (id!=null){
			prjWithHold = prjWithHoldService.get(PrjWithHold.class,id);	
			resetPrjWithHold(prjWithHold);
		}
		return new ModelAndView("admin/project/prjWithHold/prjWithHold_detail")
				.addObject("prjWithHold", prjWithHold);
	}
	/**
	 * ??????PrjWithHold
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrjWithHold prjWithHold){
		logger.debug("??????PrjWithHold??????");
		try{
			prjWithHold.setLastUpdate(new Date());
			prjWithHold.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjWithHold.setExpired("F");
			prjWithHold.setActionLog("ADD");
			prjWithHold.setIsCreate("0");
			this.prjWithHoldService.save(prjWithHold);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????PrjWithHold??????");
		}
	}
	
	/**
	 * ??????PrjWithHold
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjWithHold prjWithHold){
		logger.debug("??????PrjWithHold??????");
		try{
			prjWithHold.setLastUpdate(new Date());
			prjWithHold.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prjWithHoldService.update(prjWithHold);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????PrjWithHold??????");
		}
	}
	
	/**
	 * ??????PrjWithHold
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("??????PrjWithHold??????");
		if(StringUtils.isBlank(id)){
			ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
			return;
		}else{
			PrjWithHold prjWithHold = prjWithHoldService.get(PrjWithHold.class, Integer.parseInt(id));
			if( prjWithHold==null){
				ServletUtils.outPrintFail(request, response, "?????????????????????");
				return;
			}
			prjWithHoldService.delete(prjWithHold);	
		}	
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}

	/**
	 *PrjWithHold??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PrjWithHold prjWithHold){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjWithHoldService.findPageBySql(page, prjWithHold);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/goVerifyPrjWithHold")
	public void goVerifyPrjCheck(HttpServletRequest request, HttpServletResponse response,  PrjWithHold prjWithHold){
		logger.debug("VerifyPrjCheck??????????????????");
		try {
			if(StringUtils.isBlank(prjWithHold.getCustCode())){
				ServletUtils.outPrintFail(request, response, "????????????????????????,????????????????????????");
				return;
			};
			Customer customer = customerService.get(Customer.class, prjWithHold.getCustCode());
			if ( "010".equals(prjWithHold.getWorkType2())&&"3".equals(prjWithHold.getType())&&(!"1".equals(customer.getIsWaterCtrl())) ) {
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????????????????????????????");
				return;	
			}
			ServletUtils.outPrintSuccess(request, response, "??????????????????", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "?????????????????????");
		}
	}

}
