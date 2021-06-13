package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.EmpPicUploadRule;
import com.house.framework.commons.fileUpload.impl.SupplierUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.insales.ItemPreAppService;
import com.house.home.service.insales.SupplierService;

@Controller
@RequestMapping("/admin/supplier")
public class SupplierController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ItemPreAppService itemPreAppService; 
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Supplier supplier) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierService.findPageBySql(page, supplier,this.getUserContext(request).getItemRight());
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 供应商分页查询
	 * @author	created by zb
	 * @date	2018-12-25--下午4:39:35
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSupplierJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSupplierJqGrid(HttpServletRequest request,
			HttpServletResponse response,Supplier supplier) throws Exception {
		supplier.setItemRight(this.getUserContext(request).getItemRight());
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierService.findSupplierPageBySql(page, supplier);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 供应商管理查看详细列表
	 * @author	created by zb
	 * @date	2018-12-28--上午11:11:39
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPrepayTranJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPrepayTranJqGrid(HttpServletRequest request,
			HttpServletResponse response,Supplier supplier) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierService.findPrepayTranJqGridBySql(page, supplier);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 发货时限设定详细查询
	 * @author	created by zb
	 * @date	2018-12-29--上午10:13:06
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSupTimeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSupTimeJqGrid(HttpServletRequest request,
			HttpServletResponse response,Supplier supplier) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierService.findSupTimeJqGridBySql(page, supplier);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 根据ID查询供应商详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSupplier")
	@ResponseBody
	public JSONObject getSupplier(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Supplier supplier = supplierService.get(Supplier.class, id);
		if(supplier == null){
			return this.out("系统中不存在code="+id+"的供应商信息", false);
		}
		return this.out(supplier, true);
	}
	
	/**
	 * Supplier列表
	 * modify by zb on 20181225
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Supplier supplier) throws Exception {
		return new ModelAndView("admin/insales/supplier/supplier_list");
	}
	/**
	 * 获取供应商编号
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, Supplier supplier) throws Exception {
		if("1".equals(supplier.getShowLastSendSupplier()) && ("JZ".equals(supplier.getItemType1().trim()) || "ZC".equals(supplier.getItemType1().trim()))){
			Map<String, Object> map = this.itemPreAppService.getLastSendSupplier(supplier.getCustCode());
			if(map != null){
				supplier.setLastSendSupplierDescr(map.get("lastSendSupplierDescr").toString());
			}
		}else{
			supplier.setShowLastSendSupplier("0");
		}
		return new ModelAndView("admin/insales/supplier/supplier_code").addObject("supplier", supplier);
	}
	
	/**
	 * 跳转到新增供应商信息页面
	 * @author	created by zb
	 * @date	2018-12-25--下午5:45:23
	 * @param request
	 * @param response
	 * @param brand
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, Supplier supplier){
		logger.debug("跳转到新增供应商信息页面");
		supplier.setPurchCostModel("1");
		supplier.setSendMode("1");
		return new ModelAndView("admin/insales/supplier/supplier_save")
			.addObject("supplier", supplier);
	}
	
	/**
	 * 跳转到复制Supplier页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String code, String m_umState){
		logger.debug("跳转到复制Supplier页面");
		Supplier supplier = this.supplierService.get(Supplier.class, code);
		supplier.setCmpCode(getCmpCode(request, supplier));
		supplier.setM_umState(m_umState);
		String picUrl = OssConfigure.cdnAccessUrl+"/"+supplier.getBusinessPhoto();
		return new ModelAndView("admin/insales/supplier/supplier_save")
			.addObject("supplier", supplier).addObject("picUrl", picUrl);
	}
	/**
	 * 跳转到修改Supplier页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String code, String m_umState){
		logger.debug("跳转到修改Supplier页面");
		Supplier supplier = this.supplierService.get(Supplier.class, code);
		supplier.setCmpCode(getCmpCode(request, supplier));
		supplier.setM_umState(m_umState);
		String picUrl = OssConfigure.cdnAccessUrl+"/"+supplier.getBusinessPhoto();
		return new ModelAndView("admin/insales/supplier/supplier_save")
			.addObject("supplier", supplier).addObject("picUrl", picUrl);
	}
	
	/**
	 * 跳转到查看Supplier页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String code, String m_umState){
		logger.debug("跳转到查看Supplier页面");
		Supplier supplier = this.supplierService.get(Supplier.class, code);
		supplier.setCmpCode(getCmpCode(request, supplier));
		supplier.setM_umState(m_umState);
		String picUrl = OssConfigure.cdnAccessUrl+"/"+supplier.getBusinessPhoto();
		return new ModelAndView("admin/insales/supplier/supplier_save")
			.addObject("supplier", supplier).addObject("picUrl", picUrl);
	}
	/**
	 * 获取供应商供应公司编号
	 * @author	created by zb
	 * @date	2019-4-29--下午6:44:49
	 * @param request
	 * @param supplier
	 * @return
	 */
	private String getCmpCode(HttpServletRequest request, Supplier supplier) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String cmpCode = "";
		this.supplierService.getCmpCode(page, supplier);
		if (page.getResult().size() > 0) {
			for (int i = 0; i < page.getResult().size(); i++) {
				Map<String, Object> map = page.getResult().get(i);
				if (0 == i) {
					cmpCode = map.get("CmpCode").toString();
				} else {
					cmpCode += ","+map.get("CmpCode").toString();
				}
			}
		}
		return cmpCode;
	}
	/**
	 * 发货时限设定
	 * @author	created by zb
	 * @date	2018-12-29--上午10:10:28
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/goSetDeliveryDeadline")
	public ModelAndView goSetDeliveryDeadline(HttpServletRequest request, HttpServletResponse response, 
			String code){
		logger.debug("跳转到发货时限信息页面");
		Supplier supplier = this.supplierService.get(Supplier.class, code);
		return new ModelAndView("admin/insales/supplier/supplier_setDeliveryDeadline")
			.addObject("supplier", supplier);
	}
	
	/**
	 * 跳转到供应商时限明细页面
	 * @author	created by zb
	 * @date	2018-12-29--上午11:13:01
	 * @param request
	 * @param response
	 * @param no
	 * @param m_umState
	 * @return
	 */
	@RequestMapping("/goDDD")
	public ModelAndView goDDD(HttpServletRequest request, HttpServletResponse response, 
			String no, String m_umState, String keys){
		logger.debug("跳转到供应商时限明细页面");
		return new ModelAndView("admin/insales/supplier/supplier_deliveryDeadlineDetail")
			.addObject("m_umState", m_umState)
			.addObject("no", no)
			.addObject("keys", keys);
	}
	
	/**
	 * 根据sendTimeNo获取发货时限详细
	 * @author	created by zb
	 * @date	2018-12-29--下午2:38:45
	 * @param request
	 * @param response
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDDD")
	@ResponseBody
	public Map<String, Object> getDDD(HttpServletRequest request,
			HttpServletResponse response,Supplier supplier) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplierService.findSupTimeJqGridBySql(page, supplier);
		return page.getResult().get(0);
	}
	
	/**
	 * 添加Supplier
	 * modify by zb
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Supplier supplier){
		logger.debug("添加Supplier开始");
		try{
			supplier.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			supplier.setExpired("F");
			Result result = this.supplierService.doSave(supplier);
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加Supplier失败");
		}
	}
	
	/**
	 * 修改Supplier
	 * modify by zb
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Supplier supplier){
		logger.debug("修改Supplier开始");
		try{
			supplier.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.supplierService.doSave(supplier);
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Supplier失败");
		}
	}
	
	/**
	 *  发货时限保存
	 * @author	created by zb
	 * @date	2018-12-29--下午3:47:40
	 * @param request
	 * @param response
	 * @param supplier
	 */
	@RequestMapping("/doSetDeliveryDeadline")
	public void doSetDeliveryDeadline(HttpServletRequest request, HttpServletResponse response, Supplier supplier){
		logger.debug("发货时限保存开始");
		try{
			supplier.setM_umState("M");
			supplier.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.supplierService.doDDDSave(supplier);
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Supplier失败");
		}
	}
	
	/**
	 * 删除Supplier
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Supplier开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Supplier编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Supplier supplier = supplierService.get(Supplier.class, deleteId);
				if(supplier == null)
					continue;
				supplier.setExpired("T");
				supplierService.update(supplier);
			}
		}
		logger.debug("删除Supplier IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Supplier导出Excel
	 * modify by zb
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Supplier supplier){
		supplier.setItemRight(this.getUserContext(request).getItemRight());
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		supplierService.findSupplierPageBySql(page, supplier);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"供应商信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 * 营业执照图片上传
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadPic")
    public void uploadEmpPic(HttpServletRequest request,
            HttpServletResponse response) {
	    
	    try {
	        
            MultipartFormData multipartFormData = new MultipartFormData(request);
            
            FileItem fileItem = multipartFormData.getFileItem();
            if (fileItem == null) {
                ServletUtils.outPrintFail(request, response, "上传营业执照图片失败：无法获取上传图片");
                return;
            }
            
            SupplierUploadRule supplierUploadRule = new SupplierUploadRule(fileItem.getName());
            OssManager.uploadFile(fileItem.getInputStream(), supplierUploadRule.getFileName(),
            		supplierUploadRule.getFilePath(), supplierUploadRule.getFileName());
            String baseFileUrl = OssConfigure.cdnAccessUrl+"/";
            
            Map<String, String> datas = new HashMap<String, String>();
            datas.put("picFullName", supplierUploadRule.getFullName());
            datas.put("picUrl", baseFileUrl + supplierUploadRule.getFullName());

            ServletUtils.outPrintSuccess(request, response, "上传营业执照图片成功", datas);
	        
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "上传营业执照图片失败：上传接口异常");
        }
    }
}
