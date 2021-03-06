package com.house.home.web.controller.design;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.design.CustLoanBean;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustLoan;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustLoanService;
import com.house.home.service.design.CustomerService;

@Controller
@RequestMapping("/admin/custLoan")
public class CustLoanController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustLoanController.class);
	@Autowired
	private CustLoanService custLoanService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private XtdmService xtdmService;
	
	private void resetCustLoan(CustLoan custLoan){
		if (custLoan!=null){
			if (StringUtils.isNotBlank(custLoan.getCustCode())){
				Customer customer = custLoanService.get(Customer.class, custLoan.getCustCode());
				if (customer!=null){
					custLoan.setCustDescr(customer.getDescr());
					custLoan.setAddress(customer.getAddress());
					custLoan.setStatusDescr(customer.getStatus());
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
			HttpServletResponse response, CustLoan custLoan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custLoanService.findPageBySql(page, custLoan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustLoan??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custLoan/custLoan_list");
	}
	/**
	 * CustLoan??????code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custLoan/custLoan_code");
	}
	/**
	 * ???????????????CustLoan??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????CustLoan??????");
		CustLoan custLoan = null;
		if (StringUtils.isNotBlank(id)){
			custLoan = custLoanService.get(CustLoan.class, id);
			custLoan.setCustCode(null);
		}else{
			custLoan = new CustLoan();
		}
		
		return new ModelAndView("admin/design/custLoan/custLoan_save")
			.addObject("custLoan", custLoan);
	}
	/**
	 * ?????????Excel????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLoad")
	public ModelAndView goBatchAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/design/custLoan/custLoan_importExcel");
	}
	/**
	 * ???????????????CustLoan??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????CustLoan??????");
		CustLoan custLoan = null;
		if (StringUtils.isNotBlank(id)){
			custLoan = custLoanService.get(CustLoan.class, id);
			resetCustLoan(custLoan);
		}else{
			custLoan = new CustLoan();
		}
		
		return new ModelAndView("admin/design/custLoan/custLoan_update")
			.addObject("custLoan", custLoan);
	}
	
	/**
	 * ???????????????CustLoan??????
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????CustLoan??????");
		CustLoan custLoan = custLoanService.get(CustLoan.class, id);
		resetCustLoan(custLoan);
		return new ModelAndView("admin/design/custLoan/custLoan_detail")
				.addObject("custLoan", custLoan);
	}
	/**
	 * ??????CustLoan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustLoan custLoan){
		logger.debug("??????CustLoan??????");
		try{
			custLoan.setLastUpdate(new Date());
			custLoan.setLastUpdatedBy(getUserContext(request).getCzybh());
			custLoan.setExpired("F");
			custLoan.setActionLog("ADD");
			if(custLoanService.isExistCustCode(custLoan)){
				ServletUtils.outPrintFail(request, response, "?????????????????????,???????????????????????????");
				return;
			}
			this.custLoanService.save(custLoan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ??????CustLoan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustLoan custLoan){
		logger.debug("??????CustLoan??????");
		try{
			custLoan.setLastUpdate(new Date());
			custLoan.setLastUpdatedBy(getUserContext(request).getCzybh());
			custLoan.setActionLog("EDIT");
			if (StringUtils.isBlank(custLoan.getExpired()) || "F".equals(custLoan.getExpired())) {
				custLoan.setExpired("F");
			}
			this.custLoanService.update(custLoan);
		
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ??????CustLoan
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("??????CustLoan??????");
		try{
			CustLoan custLoan  =custLoanService.get(CustLoan.class, id);
			custLoanService.delete(custLoan);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}

	/**
	 * excel????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<CustLoanBean> eUtils=new ExcelImportUtils<CustLoanBean>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
				// ??????????????? ????????????
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList.add("????????????");
		titleList.add("??????????????????");
		titleList.add("????????????");
		titleList.add("????????????/??????");
		titleList.add("??????????????????/??????");
		titleList.add("??????????????????");
		titleList.add("??????????????????/??????");
		titleList.add("??????????????????");
		titleList.add("??????????????????");
		titleList.add("????????????");
		titleList.add("?????????");
		titleList.add("??????");
		try {
			List<CustLoanBean> result=eUtils.importExcel(in,CustLoanBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(CustLoanBean custLoanBean:result){
				if(StringUtils.isNotBlank(custLoanBean.getError())){
					map.put("success", false);
					map.put("returnInfo", custLoanBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "??????");
				if(StringUtils.isNotBlank(custLoanBean.getCustCode())){
					Customer customer=customerService.get(Customer.class,custLoanBean.getCustCode());
					if (customer!=null){
						data.put("custcode", custLoanBean.getCustCode());
						data.put("address", customer.getAddress());
						data.put("custdescr", customer.getDescr());
						Xtdm xtdmCommiType=xtdmService.getByIdAndCbm("CUSTOMERSTATUS",customer.getStatus());
						data.put("statusdescr", xtdmCommiType.getNote());
						
					}	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????????????????");
				}
				data.put("bank", custLoanBean.getBank());
				/*if(StringUtils.isNotBlank(custLoanBean.getAgreeDate())){
					if(!DateUtil.strIsValidDate(custLoanBean.getAgreeDate(), "yyyy-MM-dd ")){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "??????????????????????????????????????????");
					}else{
						data.put("agreedate", custLoanBean.getAgreeDate());
					}
					
				}
				if(StringUtils.isNotBlank(custLoanBean.getFirstDate())){
					if(!DateUtil.strIsValidDate(custLoanBean.getFirstDate(), "yyyy-MM-dd ")){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "??????????????????????????????????????????");
					}else{
						data.put("agreedate", custLoanBean.getFirstDate());
					}
					
				}
				if(StringUtils.isNotBlank(custLoanBean.getSecondDate())){
					if(!DateUtil.strIsValidDate(custLoanBean.getSecondDate(), "yyyy-MM-dd ")){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "??????????????????????????????????????????");
					}else{
						data.put("agreedate", custLoanBean.getSecondDate());
					}	
				}*/
				data.put("agreedate", custLoanBean.getAgreeDate());
				data.put("firstdate", custLoanBean.getFirstDate());
				data.put("seconddate", custLoanBean.getSecondDate());
				data.put("amount", custLoanBean.getAmount());
				data.put("firstamount", custLoanBean.getFirstAmount());
				data.put("secondamount", custLoanBean.getSecondAmount());
				data.put("signremark", custLoanBean.getSignRemark());
				data.put("confuseremark", custLoanBean.getConfuseRemark());
				data.put("followremark", custLoanBean.getFollowRemark());
				data.put("remark", custLoanBean.getRemark());
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "??????????????????");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
			map.put("hasInvalid", true);
			return map;
		}
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSaveBatch")
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,CustLoan custLoan){
		logger.debug("??????????????????");
		try {
			
			String detailJson = request.getParameter("detailJson");
			custLoan.setDetailJson(detailJson);
			custLoan.setLastUpdatedBy(getUserContext(request).getCzybh());
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????");
				return;
			}
			Result result = this.custLoanService.doSaveBatch(custLoan);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}

	/**
	 *CustLoan??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustLoan custLoan){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custLoanService.findPageBySql(page, custLoan);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
