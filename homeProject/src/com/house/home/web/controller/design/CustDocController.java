package com.house.home.web.controller.design;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.CustDocUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.CustDoc;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.DesignPicPrg;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustDocService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.PrePlanAreaService;

@Controller
@RequestMapping("/admin/custDoc")
public class CustDocController extends BaseController{
	
	@Autowired
	private CustDocService custDocService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PrePlanAreaService prePlanAreaService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustDoc custDoc ) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		custDocService.findPageBySql(page,custDoc,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 合同资料
	 *  
	 *  */
	@RequestMapping("/goChgJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goChgJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustDoc custDoc,String docType1 ) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc= this.getUserContext(request);
		custDocService.findChgPageBySql(page,custDoc,docType1,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDocJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDocJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustDoc custDoc,String docType1 ) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custDoc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		custDocService.findDocPageBySql(page,custDoc,docType1);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDocChgConByJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDocChgConByJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustDoc custDoc) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custDoc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		custDocService.goDocChgConByJqGrid(page,custDoc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		String custTypes="";
		List<CustType> custTypeList= custTypeService.findByIsAddAllInfo();
		for(int i=0;i<custTypeList.size();i++){
			custTypes=custTypes+custTypeList.get(i).getCode()+",";
		}
		return new ModelAndView("admin/design/custDoc/custDoc_list").addObject("custTypes",custTypes.substring(0,custTypes.length() - 1));
	}
	
	@RequestMapping("/goContractDoc")
	public ModelAndView goContractDoc(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark) throws Exception{
		Customer customer=null;
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;
		return new ModelAndView("admin/design/custDoc/custDoc_contractDoc")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark);
	}
	
	@RequestMapping("/goContractDocView")
	public ModelAndView goContractDocView(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark) throws Exception{
		Customer customer=null;
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;
		return new ModelAndView("admin/design/custDoc/custDoc_contractDocView")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark);
	}
	
	@RequestMapping("/goDesignDoc")
	public ModelAndView goDesignDoc(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark,String picStatus ) throws Exception{
		Customer customer=new Customer();
		    
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_designDoc")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh)
		.addObject("url",url)
		.addObject("confirmRemark", confirmRemark)
		.addObject("picStatus", picStatus).addObject("type", "1");
	}
	
	@RequestMapping("/goDocChg")
	public ModelAndView goDocChg(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark,String picStatus ) throws Exception{
		Customer customer=new Customer();
		    
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_docChg")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark)
		.addObject("picStatus", picStatus).addObject("type","2");
	}
	
	@RequestMapping("/goDocChgAdd")
	public ModelAndView goDocChgAdd(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark,String picStatus ) throws Exception{
		Customer customer=new Customer();
		    
		//customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_chgDoc")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("picStatus", picStatus).addObject("type","2");
	}
	
	@RequestMapping("/goViewChg")
	public ModelAndView goViewChg(HttpServletRequest request ,
			HttpServletResponse response,int pk) throws Exception{
		Customer customer=new Customer();
		CustDoc custDoc = new CustDoc();
		String confirmCzyDescr= "";
		Employee employee = new Employee();
		custDoc = custDocService.get(CustDoc.class, pk);
		if(StringUtils.isNotBlank(custDoc.getCustCode())){
			customer=customerService.get(Customer.class, custDoc.getCustCode());
		}
		if(StringUtils.isNotBlank(custDoc.getConfirmCZY())){
			employee = employeeService.get(Employee.class, custDoc.getConfirmCZY());
			if(employee != null){
				confirmCzyDescr = employee.getNameChi();
			}
		}
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		// String url = getCustDocDownloadPath(request, custDoc.getCustCode(), custDoc.getDocName());
		String url = FileUploadUtils.getFileUrl(custDoc.getDocName());

		return new ModelAndView("admin/design/custDoc/custDoc_viewChgDoc")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("type","2").addObject("custDoc", custDoc).addObject("confirmCzyDescr", confirmCzyDescr);
	}
	
	@RequestMapping("/goChgConfirm")
	public ModelAndView goChgConfirm(HttpServletRequest request ,
			HttpServletResponse response) throws Exception{

		String url = getCustDocDownloadPath(request,"","");
		System.out.println(url.substring(0, url.length()-1));
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "预算差异分析");

		return new ModelAndView("admin/design/custDoc/custDoc_chgConfirm")
			.addObject("url", url.substring(0, url.length()-1)).addObject("hasAuthByCzybh", hasAuthByCzybh);
	}
	
	@RequestMapping("/goDesignDocView")
	public ModelAndView goDesignDocView(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark ) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;
		
		DesignPicPrg designPicPrg = customerService.get(DesignPicPrg.class, code);
		designPicPrg = designPicPrg != null ? designPicPrg : new DesignPicPrg();
		
		if (!"4".equals(designPicPrg.getStatus())) {
            designPicPrg.setConfirmDate(null);
        }

		return new ModelAndView("admin/design/custDoc/custDoc_designDocView")
		        .addObject("customer",customer)
		        .addObject("hasAuthByCzybh",hasAuthByCzybh)
		        .addObject("url",url)
		        .addObject("designPicPrg", designPicPrg);
	}
	
	@RequestMapping("/goSoftDoc")
	public ModelAndView goSoftDoc(HttpServletRequest request ,
			HttpServletResponse response ,String code,String confirmRemark) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_softDoc")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark);
	}
	
	@RequestMapping("/goSoftDocView")
	public ModelAndView goSoftDocView(HttpServletRequest request ,
			HttpServletResponse response ,String code,String confirmRemark) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_softDocView")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark);
	}
	
	@RequestMapping("/goIntDoc")
	public ModelAndView goIntDoc(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark ) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_intDoc")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark);
	}
	
	@RequestMapping("/goIntDocView")
	public ModelAndView goIntDocView(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark ) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_intDocView")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark);
	}
	
	@RequestMapping("/goDocConfirm")
	public ModelAndView goDocConfirm(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark ) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_docConfirm")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark)
		;
	}
	
	@RequestMapping("/goRetConfirm")
	public ModelAndView goRetConfirm(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark ) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_confirmBack")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url)
		.addObject("confirmRemark", confirmRemark)
		;
	}
	
	/**
	 * 文件上传页面单个上传
	 * @param custDoc
	 * 
	 * */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request ,
			HttpServletResponse response,CustDoc custDoc ) throws Exception{
		
		custDoc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		custDoc.setUploadCZY(this.getUserContext(request).getCzybh());
		Map<String , Object> map=custDocService.getMinDocType2(custDoc.getDocType1());
		custDoc.setMinDocType2(map.get("Code").toString());
		custDoc.setMaxLen(Integer.parseInt(map.get("MaxLen").toString()));
		custDoc.setFileType(map.get("FileType").toString());
		if("2".equals(custDoc.getType())){
			custDoc.setMinDocType2("6");
			custDoc.setMaxLen(10*1024);
			custDoc.setFileType("dwg");
		} else if (StringUtils.isNotBlank(custDoc.getUpDocType2())) { //add by zb on 20200102
			Map<String, Object> docType2Info = custDocService.getDocType2Info(custDoc.getUpDocType2());
			custDoc.setMinDocType2(custDoc.getUpDocType2());
			custDoc.setMaxLen(Integer.parseInt(docType2Info.get("MaxLen").toString()));
			custDoc.setFileType(docType2Info.get("FileType").toString());
		}
		return new ModelAndView("admin/design/custDoc/custDoc_add").addObject("custDoc",custDoc);
	}
	
	/**
	 *批量上传 
	 * 
	 * */
	@RequestMapping("/goVolumeAdd")
	public ModelAndView goVolumeAdd(HttpServletRequest request ,
			HttpServletResponse response,CustDoc custDoc ) throws Exception{
		
		custDoc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		custDoc.setUploadCZY(this.getUserContext(request).getCzybh());
		custDoc.setMinDocType2(custDocService.getMinDocType2(custDoc.getDocType1()).get("Code").toString());
		custDoc.setMaxLen(Integer.parseInt(custDocService.getMinDocType2(custDoc.getDocType1()).get("MaxLen").toString()));
		custDoc.setFileType(custDocService.getMinDocType2(custDoc.getDocType1()).get("FileType").toString());
		if (StringUtils.isNotBlank(custDoc.getUpDocType2())) { //add by zb on 20200102
			Map<String, Object> docType2Info = custDocService.getDocType2Info(custDoc.getUpDocType2());
			custDoc.setMinDocType2(custDoc.getUpDocType2());
			custDoc.setMaxLen(Integer.parseInt(docType2Info.get("MaxLen").toString()));
			custDoc.setFileType(docType2Info.get("FileType").toString());
		}
		return new ModelAndView("admin/design/custDoc/custDoc_batchAdd").addObject("custDoc",custDoc);
	}
	/**
	 * 平面图纸上传
	 * @author	created by zb
	 * @date	2020-1-2--上午9:46:30
	 * @param request
	 * @param response
	 * @param code 客户编号
	 * @param confirmRemark 审核说明
	 * @param picStatus 设计图状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPlaneUp")
	public ModelAndView goPlaneUp(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark,String picStatus) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_planeUp")
			.addObject("customer",customer)
			.addObject("hasAuthByCzybh",hasAuthByCzybh)
			.addObject("url",url)
			.addObject("confirmRemark", confirmRemark)
			.addObject("picStatus", picStatus)
			.addObject("type", "1")
			.addObject("upDocType2", "3");
	}
	/**
	 * 跳转框图上传页面
	 * @author	created by zb
	 * @date	2020-1-3--下午5:57:04
	 * @param request
	 * @param response
	 * @param code
	 * @param confirmRemark
	 * @param picStatus
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFlowUp")
	public ModelAndView goFlowUp(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark,String picStatus) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = FileUploadUtils.DOWNLOAD_URL;

		return new ModelAndView("admin/design/custDoc/custDoc_flowUp")
		.addObject("customer",customer)
		.addObject("hasAuthByCzybh",hasAuthByCzybh)
		.addObject("url",url)
		.addObject("confirmRemark", confirmRemark)
		.addObject("picStatus", picStatus)
		.addObject("type", "1")
		.addObject("upDocType2", "10");
	}
	/**
	 * 资料文件上传
	 * 
	 * */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadCustDoc")
	public void uploadCustDoc(HttpServletRequest request,
			HttpServletResponse response) {
		try {
//			UploadPhotoResp respon = new UploadPhotoResp();
//			BufferedImage bim =null;
//			try {
//				String fileRealPath = "";//文件存放真实地址 
//				String firstFileName = ""; 
//				String PhotoPath="";
//				DiskFileItemFactory fac = new DiskFileItemFactory();
//				ServletFileUpload upload = new ServletFileUpload(fac);
//				upload.setHeaderEncoding("UTF-8");
//				// 设置允许上传的最大文件大小 500k
//				//upload.setSizeMax(500 * 1024);
//				// 获取多个上传文件
//				List fileList = upload.parseRequest(request);
//				Iterator it = fileList.iterator();
//				String custCode=request.getParameter("custCode"),
//						descr=request.getParameter("descr"),
//						remark=request.getParameter("remark"),
//						newType1=request.getParameter("docType1"),
//						newType2=request.getParameter("docType2"),
//						nowNum=request.getParameter("firstNum"),
//						type=request.getParameter("type"),
//						upDocType2=request.getParameter("upDocType2");
//				
//				//如果有要固定的上传文件类型，就赋值给文件类型2 add by zb on 20200103
//				if (StringUtils.isNotBlank(upDocType2)) {
//					newType2 = upDocType2;
//				}
//				
//				int fileSize=Integer.parseInt(request.getParameter("allowDocSize"));
//				int firstNum=-1;
//				if(StringUtils.isNotBlank(nowNum)){
//					firstNum=Integer.parseInt(nowNum);
//				}
//				List<String> fileRealPathList = new ArrayList<String>();
//				List<String> PhotoPathList = new ArrayList<String>();
//				List<String> fileNameList = new ArrayList<String>();
//				Map<String, Object> dataMap= new HashMap<String, Object>();
//				// 遍历上传文件写入磁盘
//				while (it.hasNext()) {
//					FileItem obit = (FileItem) it.next();
//					// 如果是普通 表单参数
//					if (obit.isFormField()) { //普通域,获取页面参数
//						String fieldName = obit.getFieldName();
//						String fieldValue = obit.getString("UTF-8");
//						if ("custCode".equals(fieldName)){
//							custCode = fieldValue;
//						}
//						if ("docType2".equals(fieldName)){
//							newType2 = fieldValue;
//						}
//						if("newType1".equals(fieldName)){
//							newType1=fieldValue;
//						}
//						if("firstNum".equals(fieldName)){
//							firstNum=Integer.parseInt(fieldValue);
//							nowNum=fieldValue;
//						}
//						
//					}
//					// 如果是多媒体
//					if (obit instanceof DiskFileItem) {
//						DiskFileItem item = (DiskFileItem) obit;
//						// 如果item是文件上传表单域
//						// 获得文件名及路径
//						String fileName = item.getName();
//						if (fileName != null) {
//							if(fileName.indexOf("?")!=-1){
//								fileName=fileName.substring(0,fileName.indexOf("?"));
//								
//							}
//							firstFileName = fileName.substring(
//									fileName.lastIndexOf("\\") + 1);
//							String formatName = firstFileName
//									.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
//							String fileNameNew = System.currentTimeMillis()+formatName;
//							String filePath = getCustDocPath(fileNameNew,custCode);
//							PhotoPath=getCustDocDownloadPath(request,fileNameNew,custCode)+fileNameNew;
//							//savePath=PhotoPath;
//							FileUploadServerMgr.makeDir(filePath);
//							fileRealPath = filePath + fileNameNew;// 文件存放真实地址
//							//Thumbnails.of(item.getStoreLocation()).scale(0.50f).toFile(fileRealPath);
	//
//							BufferedInputStream in = new BufferedInputStream(
//									item.getInputStream());// 获得文件输入流
//							BufferedOutputStream outStream = new BufferedOutputStream(
//									new FileOutputStream(new File(fileRealPath)));// 获得文件输出流
//							Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹 item.getStoreLocation();
//							
//							in.close();
//							outStream.close();
//							File srcFileJPG = new File(fileRealPath); 
//							ImageIO.setUseCache(false);
//							//ImageIO.setCacheDirectory(new File("D:/homePhoto/usercache"));
//							bim = ImageIO.read(srcFileJPG);
//							float srcWdith=0f;
//							float srcHeigth=0f;
//							if(bim!=null&& fileSize*1024<item.getSize()){
//								 srcWdith = bim.getWidth();  
//								 srcHeigth = bim.getHeight();
//						        float scale;
//							}
//					        
//					        if(srcWdith>=1200f||srcHeigth>=1200f ){
//					        	if("3".equals(newType2)){
//					        		Thumbnails.of(item.getStoreLocation()).size(2048, 2048).keepAspectRatio(true).outputQuality(1.0).toFile(fileRealPath);
//					        	}else{
//					        		Thumbnails.of(item.getStoreLocation()).size(1024, 1024).keepAspectRatio(true).outputQuality(1.0).toFile(fileRealPath);
//					        	}
//					        }
//					        //保存资料信息
//							custDocService.save(getCustDocEntity(newType1,custCode,descr, newType2, this.getUserContext(request).getCzybh(),firstNum,remark,type,fileNameNew));
//							//设计图纸上传流程
//							if("2".equals(newType1.trim())){
//								custDocService.doSaveDesignPic(custCode,this.getUserContext(request).getCzybh());
//							}
//							//记录第一张平面图时间为“平面时间”（tCustomer. PlaneDate）。 add by zb on 20200102
//							if (StringUtils.isNotBlank(upDocType2)) {
//								custDocService.updateCustCon(custCode, upDocType2, this.getUserContext(request));
//							}
//							dataMap.put("nowNum", firstNum);
//							PhotoPathList.add(PhotoPath);
//							fileNameList.add(fileNameNew);
//						}
//					}
//				}
//		        
//				respon.setPhotoPathList(PhotoPathList);
//				respon.setPhotoNameList(PhotoPathList);
//				respon.setDatas(dataMap);
//				ServletUtils.outPrintSuccess(request, response, dataMap);
//			} catch (Exception e) {
//				e.printStackTrace();
//				ServletUtils.outPrintFail(request, response, "上传失败");
//			}
			// 上传成功后返回数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			// 解析参数
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String custCode=multipartFormData.getParameter("custCode"),
					descr=multipartFormData.getParameter("descr"),
					remark=multipartFormData.getParameter("remark"),
					newType1=multipartFormData.getParameter("docType1"),
					newType2=multipartFormData.getParameter("docType2"),
					nowNum=multipartFormData.getParameter("firstNum"),
					type=multipartFormData.getParameter("type"),
					upDocType2=multipartFormData.getParameter("upDocType2");
			
			// 批量上传试获取当前图片编号
			int firstNum=-1;
			if(StringUtils.isNotBlank(nowNum)){
				firstNum=Integer.parseInt(nowNum);
			}
			
			FileItem fileItem = multipartFormData.getFileItem();
			
			// 项目资料管理文件命名规则
			CustDocUploadRule custDocUploadRule = new CustDocUploadRule(fileItem.getName(), custCode);

			// 调用上传工具将文件上传到oss
			Result result = FileUploadUtils.upload(fileItem.getInputStream(),
					custDocUploadRule.getFileName(),custDocUploadRule.getFilePath());
			
			if (!Result.SUCCESS_CODE.equals(result.getCode())) {
				logger.error("上传文件失败，原文件名:{}",fileItem.getName());
				ServletUtils.outPrintFail(request, response, "上传文件失败");
				return;
			}
	        //保存资料信息
			custDocService.save(getCustDocEntity(newType1,custCode,descr, newType2, 
					this.getUserContext(request).getCzybh(),firstNum,remark,type,custDocUploadRule.getFullName()));
			
			//图纸为设计图纸类型 设计图纸上传流程
			if("2".equals(newType1.trim())){
				custDocService.doSaveDesignPic(custCode,this.getUserContext(request).getCzybh());
			}
			
			//记录第一张平面图时间为“平面时间”（tCustomer. PlaneDate）。 add by zb on 20200102
			if (StringUtils.isNotBlank(upDocType2)) {
				custDocService.updateCustCon(custCode, upDocType2, this.getUserContext(request));
			}
			
			dataMap.put("nowNum", firstNum);
			ServletUtils.outPrintSuccess(request, response, dataMap);

		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "上传失败");
		}
		
	}
	
	@RequestMapping("/uploadFileNew")
    public void UploadTheme(HttpServletRequest request,
			HttpServletResponse response,MultipartFile meFile){
        if (meFile != null) {
            Map<String, String[]> map = request.getParameterMap();
            System.out.println(map);
        }
    }
	
	@RequestMapping("/doSaveDocName")
	@ResponseBody
	public CustDoc doSaveDocName(HttpServletRequest request, HttpServletResponse response,CustDoc custDoc){
			String docNameDescr="";
		try {
			if("1".equals(custDoc.getDocType1())){
				docNameDescr="合同资料";
			}else if("2".equals(custDoc.getDocType1())){
				docNameDescr="设计资料";
			}else if("3".equals(custDoc.getDocType1())){
				docNameDescr="软装资料";
			}else if("4".equals(custDoc.getDocType1())){
				docNameDescr="集成资料";
			}
			if(StringUtils.isBlank(custDoc.getDescr())){
				custDoc.setDescr(docNameDescr);
			}
			custDoc.setDocName("方法删除了");
			custDoc.setUploadCZY(this.getUserContext(request).getCzybh());
			custDoc.setUploadDate(new Date());
			custDoc.setLastUpdate(new Date());
			custDoc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custDoc.setExpired("F");
			custDoc.setActionLog("Add");
			this.custDocService.save(custDoc);
			//Thumbnails.of().scale(1.00f).outputQuality(0.75f).toFile(fileRealPath);
			ServletUtils.outPrintSuccess(request, response,"上传成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		return custDoc;
	
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response,String docNameArr,
				String docType2Descr,String custCode){
		try{ 
			// 楼盘名称当压缩包名称
			String address="";
			if(StringUtils.isNotBlank(custCode)){
				address=customerService.get(Customer.class, custCode).getAddress();
			}
			String zip = address+"_"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss")+".zip";

			String[] docName = docNameArr.split(",");
			StringBuilder[] files = null;
			files = new StringBuilder[docName.length];
			
			for(int i=0;i<docName.length;i++){
				
				files[i] = new StringBuilder();
				files[i].append(FileUploadUtils.DOWNLOAD_URL + docName[i]);
				
			}
			
			ServletUtils.downLoadFiles(request,response,files,zip,true);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doDeleteDoc")
	@ResponseBody
	public boolean doDeleteDoc(HttpServletRequest request, HttpServletResponse response,
			String custCode,String path,String docName){
		boolean result = true;
		try {
			
			String[] docNames = docName.split(",");
			for(int x=0;x<docNames.length;x++){
				// File deleteDoc = new File("D:/homePhoto/custDoc/"+custCode+"/"+docNames[x]);
				custDocService.doDeleteDoc(custCode,docNames[x]);
				// deleteDoc.delete();
				FileUploadUtils.deleteFile(docNames[x]);
			}
			
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		return result;
	}
	
	/**
	 * 过期文档
	 * @author	created by zb
	 * @date	2020-1-3--下午2:31:46
	 * @param request
	 * @param response
	 * @param deleteIds
	 */
	@RequestMapping("/doExpiredDoc")
	public void doExpiredDoc(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("过期doc开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "项目资料编号不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					this.custDocService.doExpiredDoc(deleteId, this.getUserContext(request));
				}
			}
			ServletUtils.outPrintSuccess(request, response, "删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	@RequestMapping("/doEditDescr")
	@ResponseBody
	public boolean doEditDescr(HttpServletRequest request, HttpServletResponse response,
			int pk,String descr){
		boolean editSuccess;
		CustDoc custDoc=null;
		custDoc=custDocService.get(CustDoc.class, pk);
		try {//authId=1104 :项目管理的管理员权限 ID号
			if(
					// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh 
//					czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(),1104)
					czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限")
					||custDoc.getUploadCZY().trim().equals(this.getUserContext(request).getCzybh().trim())){
				custDocService.doEditDescr(pk,descr,this.getUserContext(request).getCzybh());
				editSuccess=true;
			}else{
				editSuccess=false;
				ServletUtils.outPrintFail(request, response, "修改失败，不是本人上传或没有管理员权限");
			}
		} catch (Exception e) {
			editSuccess=false;
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		return editSuccess;
	}
	
	/**
	 * 获取文件上传地址
	 * 
	 * */
	public static String getCustDocPath(String fileCustCode, String fileName){
		String custDocUrl = SystemConfig.getProperty("custDoc", "", "photo");
		if (StringUtils.isBlank(custDocUrl)){
			return "";
		}
		if (StringUtils.isNotBlank(fileName)){
			custDocUrl = "D:/homePhoto/" + fileName;
		}
		return custDocUrl;
	}
	
	/**
	 * 图片文件查看下载地址
	 * 
	 * */
	public static String getCustDocDownloadPath(HttpServletRequest request, String fileCustCode, String fileName){
		
		String path = getCustDocPath(fileCustCode, fileName);
		
		return PathUtil.getWebRootAddress(request) + path.substring(path.indexOf("/")+1);
	
	}
	
	public static CustDoc getCustDocEntity(String docType1,String custCode,String descr,
					String docType2,String uploadCZY,int firstNum,String remark,String type,String saveCustDocName){
		String docNameDescr="";
		
		CustDoc custDoc=new CustDoc();
		custDoc.setDescr(descr+(firstNum==-1?"":firstNum));
		custDoc.setCustCode(custCode);
		custDoc.setDocType2(docType2);
		custDoc.setDocType1(docType1);
		custDoc.setRemark(remark);
		if(StringUtils.isNotBlank(type)){
			custDoc.setType(type);
		}else {
			custDoc.setType("1");
		}
		custDoc.setStatus("1");
		if("1".equals(custDoc.getDocType1())){
			docNameDescr="合同资料"+(firstNum==-1?"":firstNum);
		}else if("2".equals(custDoc.getDocType1())){
			docNameDescr="设计资料"+(firstNum==-1?"":firstNum);
		}else if("3".equals(custDoc.getDocType1())){
			docNameDescr="软装资料"+(firstNum==-1?"":firstNum);
		}else if("4".equals(custDoc.getDocType1())){
			docNameDescr="集成资料"+(firstNum==-1?"":firstNum);
		}
		if(StringUtils.isBlank(custDoc.getDescr())){
			custDoc.setDescr(docNameDescr);
		}
		custDoc.setDocName(saveCustDocName);
		custDoc.setUploadCZY(uploadCZY);
		custDoc.setUploadDate(new Date());
		custDoc.setLastUpdate(new Date());
		custDoc.setLastUpdatedBy(uploadCZY);
		custDoc.setExpired("F");
		custDoc.setActionLog("Add");
		return custDoc;
	}
	
	@ResponseBody
	@RequestMapping("/getDocType2Info")
	public Map<String , Object> getDocType2Info(String docType2){
		
		//custDocService.getDocType2Info(docType2);
		
		return custDocService.getDocType2Info(docType2);
	}
	
	@ResponseBody
	@RequestMapping("/getIsAllowChg")
	public boolean getIsAllowChg(String custCode){
		
		return custDocService.getIsAllowChg(custCode);
	}
	
	@ResponseBody
	@RequestMapping("/getIsAllowCommi")
	public boolean getIsAllowCommi(String custCode){
		
		return custDocService.getIsAllowCommi(custCode);
	}
	
	@RequestMapping("/doCommiCheck")
	public void doCommiCheck(HttpServletRequest request,HttpServletResponse response,String custCode,String type){
		try {
			if("2".equals(type)){
				this.custDocService.updateCustDocStatus(custCode,this.getUserContext(request).getCzybh());
			}else {

				this.custDocService.updateDesignPic(custCode,"2",this.getUserContext(request).getCzybh(),
						"","",this.getUserContext(request).getCzybh(),"", null,null);
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "提交失败");
		}
	}
	
	@RequestMapping("/doConfirmPic")
	public void doConfirmPic(HttpServletRequest request,HttpServletResponse response,CustDoc custDoc){
		try {
            custDocService.updateDesignPic(custDoc.getCustCode(), "4", "",
                    getUserContext(request).getCzybh(), custDoc.getConfirmRemark(),
                    getUserContext(request).getCzybh(), custDoc.getIsFullColorDraw(),
                    custDoc.getDrawQty(),custDoc.getDraw3DQty());
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核失败");
		}
	}
	
	/**
	 * 图纸变更审核通过
	 * 
	 * @param request
	 * @param response
	 * @param custDoc
	 * @author 张海洋
	 */
	@RequestMapping("/doConfirmPass")
	public void doConfirmPass(HttpServletRequest request, HttpServletResponse response, CustDoc custDoc) {
	    
		try {
		    custDoc.setLastUpdatedBy(getUserContext(request).getCzybh());
			custDocService.doConfirmPass(custDoc);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核失败");
		}
	}
	
	/**
	 * 图纸变更审核退回
	 * 
	 * @param request
	 * @param response
	 * @param custDoc
	 * @author 张海洋
	 */
    @RequestMapping("/doConfirmBack")
    public void doConfirmBack(HttpServletRequest request, HttpServletResponse response, CustDoc custDoc) {
        
        try {
            custDoc.setLastUpdatedBy(getUserContext(request).getCzybh());
            custDocService.doConfirmBack(custDoc);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "审核失败");
        }
        
    }
    
    /**
     * 审核取消
     * 
     * @param request
     * @param response
     * @param custDoc
     */
    @RequestMapping("/doConfirmCancel")
    public void doConfirmCancel(HttpServletRequest request, HttpServletResponse response, CustDoc custDoc) {
        
        try {
            if (custDoc.getPk() == null) {
                ServletUtils.outPrintFail(request, response, "缺少图纸编号，请重新操作");
                return;
            }
            
            CustDoc originalDoc = custDocService.get(CustDoc.class, custDoc.getPk());
            if (!originalDoc.getStatus().equals("2")) {
                ServletUtils.outPrintFail(request, response, "非待审核状态不允许执行取消操作");
                return;
            }
            
            originalDoc.setStatus("6");
            originalDoc.setConfirmRemark(custDoc.getConfirmRemark());
            originalDoc.setConfirmCZY(getUserContext(request).getCzybh());
            originalDoc.setConfirmDate(DateUtil.getNow());
            originalDoc.setLastUpdate(DateUtil.getNow());
            originalDoc.setLastUpdatedBy(getUserContext(request).getCzybh());
            originalDoc.setActionLog("EDIT");
            
            custDocService.update(originalDoc);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "操作失败");
        }
        
    }
	
	@RequestMapping("/doFinishChg")
	public void doFinishChg(HttpServletRequest request,HttpServletResponse response,CustDoc custDoc){
		try {
			this.custDocService.doFinishChg(custDoc,this.getUserContext(request).getCzybh());
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核失败");
		}
	}
	
	@RequestMapping("/doConfirmBackPic")
	public void doConfirmBackPic(HttpServletRequest request,HttpServletResponse response,CustDoc custDoc){
		try {
											
            this.custDocService.updateDesignPic(custDoc.getCustCode(), "3", "",
                    this.getUserContext(request).getCzybh(), custDoc.getConfirmRemark(), 
                    this.getUserContext(request).getCzybh(), "", custDoc.getDrawQty(),custDoc.getDraw3DQty());
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核失败");
		}
	}
	
	@RequestMapping("/doRetConfirm")
	public void doRetConfirm(HttpServletRequest request,HttpServletResponse response,CustDoc custDoc){
		try {
												//客户编号，状态，提交人，审核人，审核内容，最后修改人
			this.custDocService.doRetConfirm(custDoc.getCustCode(),"2","",
						"",custDoc.getConfirmRemark(),this.getUserContext(request).getCzybh());
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CustDoc custDoc){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		custDocService.findPageBySql(page, custDoc,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"项目资料管理表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doChgConfirmExcel")
	public void doChgConfirmExcel(HttpServletRequest request ,HttpServletResponse response,
			CustDoc custDoc){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		custDoc.setLastUpdatedBy(uc.getCzybh());
		custDocService.findDocPageBySql(page,custDoc,"");
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"变更图纸审核明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goEditInnerArea")
	public ModelAndView goEditInnerArea(HttpServletRequest request ,
			HttpServletResponse response,String code) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		CustType custType=custTypeService.get(CustType.class,customer.getCustType());
		double csInnerArea =custType.getInnerAreaPer()*customer.getArea();
	 
		List<Map<String, Object>>list=custDocService.hasManageAreaRight(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/design/custDoc/custDoc_editInnerArea")
		.addObject("customer",customer)
		.addObject("hasManageAreaRight",list.size()).addObject("custType", custType)
		.addObject("csInnerArea",csInnerArea);
	}
	@RequestMapping("/doUpdateInnerArea")
	public void doUpdateInnerArea(HttpServletRequest request,HttpServletResponse response,Customer customer){
		try {
			customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.custDocService.updateInnerArea(customer);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/goPrePlanArea")
	public ModelAndView goPrePlanArea(HttpServletRequest request,
			HttpServletResponse response,String custCode,String module) throws Exception {
		Customer customer=null;
		PrePlanArea prePlanArea=new PrePlanArea();
		String tempNo="";
		String designManDescr="";
			
		if(StringUtils.isNotBlank(custCode)){
			prePlanArea.setCustCode(custCode);
			prePlanArea.setM_umState("I");
			this.prePlanAreaService.doAutoAddDefaultArea(prePlanArea);
			customer=customerService.get(Customer.class, custCode);
			tempNo=prePlanAreaService.getNoByCustCode(custCode);
			if(StringUtils.isNotBlank(customer.getDesignMan())){
				designManDescr=employeeService.get(Employee.class, customer.getDesignMan()).getNameChi();
			}
		}
		
		return new ModelAndView("admin/design/custDoc/custDoc_prePlanArea")
		.addObject("customer",customer)
		.addObject("tempNo",tempNo)
		.addObject("designManDescr",designManDescr)
		.addObject("module",module);
	}
	@RequestMapping("/goPlanDiffAnaly")
	public ModelAndView goPlanDiffAnaly(HttpServletRequest request,
			HttpServletResponse response,String custCode) throws Exception {
		Customer customer=null;	
		if(StringUtils.isNotBlank(custCode)){
			customer=customerService.get(Customer.class, custCode);	
		}
		return new ModelAndView("admin/design/custDoc/custDoc_planDiffAnaly")
			.addObject("customer",customer);
	}
	
	@RequestMapping("/doDrawNoChg")
	public void doDrawNoChg(HttpServletRequest request,HttpServletResponse response,CustDoc custDoc){
		try {
			this.custDocService.doDrawNoChg(custDoc, this.getUserContext(request).getCzybh());
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/getIsAllowAdd")
	public boolean getIsAllowAdd(String custCode){
		return custDocService.getIsAllowAdd(custCode);
	}
	
	@RequestMapping("/goDocChgCon")
	public ModelAndView goDocChgCon(HttpServletRequest request ,
			HttpServletResponse response) throws Exception{
		CustDoc custDoc = new CustDoc();
		custDoc.setDateFrom(DateUtil.addMonth(new Date(), -1));
		custDoc.setDateTo(new Date());
		
		return new ModelAndView("admin/design/custDoc/custDoc_docChgCon").addObject("custDoc", custDoc);
	}
	
}
