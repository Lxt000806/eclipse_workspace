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
	 * 查询JqGrid表格数据
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
	 * CustLoan列表
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
	 * CustLoan查询code
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
	 * 跳转到新增CustLoan页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustLoan页面");
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
	 * 跳转从Excel导入页面
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
	 * 跳转到修改CustLoan页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustLoan页面");
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
	 * 跳转到查看CustLoan页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustLoan页面");
		CustLoan custLoan = custLoanService.get(CustLoan.class, id);
		resetCustLoan(custLoan);
		return new ModelAndView("admin/design/custLoan/custLoan_detail")
				.addObject("custLoan", custLoan);
	}
	/**
	 * 添加CustLoan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustLoan custLoan){
		logger.debug("添加CustLoan开始");
		try{
			custLoan.setLastUpdate(new Date());
			custLoan.setLastUpdatedBy(getUserContext(request).getCzybh());
			custLoan.setExpired("F");
			custLoan.setActionLog("ADD");
			if(custLoanService.isExistCustCode(custLoan)){
				ServletUtils.outPrintFail(request, response, "已存在相同客户,请重新选择客户编号");
				return;
			}
			this.custLoanService.save(custLoan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加贷款信息失败");
		}
	}
	
	/**
	 * 修改CustLoan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustLoan custLoan){
		logger.debug("修改CustLoan开始");
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
			ServletUtils.outPrintFail(request, response, "修改贷款信息失败");
		}
	}
	
	/**
	 * 删除CustLoan
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("删除CustLoan开始");
		try{
			CustLoan custLoan  =custLoanService.get(CustLoan.class, id);
			custLoanService.delete(custLoan);
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除记录失败");
		}
	}

	/**
	 * excel加载数据
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
				// 如果是普通 表单参数
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList.add("客户编号");
		titleList.add("协议提交日期");
		titleList.add("贷款银行");
		titleList.add("贷款总额/万元");
		titleList.add("首次放款金额/万元");
		titleList.add("首次放款时间");
		titleList.add("二次放款金额/万元");
		titleList.add("二次放款时间");
		titleList.add("已签约待放款");
		titleList.add("退件拒批");
		titleList.add("需跟踪");
		titleList.add("备注");
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
				data.put("isinvaliddescr", "有效");
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
					data.put("isinvaliddescr", "无效，客户编号不能为空");
				}
				data.put("bank", custLoanBean.getBank());
				/*if(StringUtils.isNotBlank(custLoanBean.getAgreeDate())){
					if(!DateUtil.strIsValidDate(custLoanBean.getAgreeDate(), "yyyy-MM-dd ")){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，协议提交日期格式不正确");
					}else{
						data.put("agreedate", custLoanBean.getAgreeDate());
					}
					
				}
				if(StringUtils.isNotBlank(custLoanBean.getFirstDate())){
					if(!DateUtil.strIsValidDate(custLoanBean.getFirstDate(), "yyyy-MM-dd ")){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，首次放款日期格式不正确");
					}else{
						data.put("agreedate", custLoanBean.getFirstDate());
					}
					
				}
				if(StringUtils.isNotBlank(custLoanBean.getSecondDate())){
					if(!DateUtil.strIsValidDate(custLoanBean.getSecondDate(), "yyyy-MM-dd ")){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，二次放款日期格式不正确");
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
			map.put("returnInfo", "数据加载完成");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "导入失败，请检查是否缺少列：客户编号、协议提交日期、贷款银行、贷款总额、首次放款金额、首次放款时间、二次放款金额、二次放款时间、已签约待放款、退件拒批、需跟踪、备注");
			map.put("hasInvalid", true);
			return map;
		}
	}
	/**
	 * 批量新增，调存储过程
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSaveBatch")
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,CustLoan custLoan){
		logger.debug("批量导入开始");
		try {
			
			String detailJson = request.getParameter("detailJson");
			custLoan.setDetailJson(detailJson);
			custLoan.setLastUpdatedBy(getUserContext(request).getCzybh());
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "无贷款信息，请先导入贷款信息");
				return;
			}
			Result result = this.custLoanService.doSaveBatch(custLoan);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "批量导入失败失败");
		}
	}

	/**
	 *CustLoan导出Excel
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
				"贷款信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
