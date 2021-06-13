package com.house.home.web.controller.commi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.bean.commi.CommiCustStakeholderSupplBean;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.commi.CommiCustStakeholderSuppl;
import com.house.home.entity.commi.CommiCycle;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.commi.CommiCycleService;

@Controller
@RequestMapping("/admin/commiCycle")
public class CommiCycleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiCycleController.class);

	@Autowired
	private CommiCycleService commiCycleService;

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
			HttpServletResponse response, CommiCycle commiCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCycleService.findPageBySql(page, commiCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CommiCycle列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiCycle/commiCycle_list");
	}
	/**
	 * CommiCycle查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiCycle/commiCycle_code");
	}
	/**
	 * 根据id查询CommiCycle详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCommiCycle")
	@ResponseBody
	public JSONObject getCommiCycle(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		CommiCycle commiCycle= commiCycleService.get(CommiCycle.class, id);
		if(commiCycle == null){
			return this.out("系统中不存在No="+id+"的周期编号", false);
		}
		return this.out(commiCycle, true);
	}
	
	/**
	 * 跳转到计算页面
	 * @return
	 */
	@RequestMapping("/goCount")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String no,String m_umState){
		logger.debug("跳转到计算页面");
		CommiCycle commiCycle = commiCycleService.get(CommiCycle.class, no);
		commiCycle.setM_umState(m_umState);
		return new ModelAndView("admin/commi/commiCycle/commiCycle_count")
				.addObject("commiCycle", commiCycle);
	}
	
	/**
	 * 跳转到新增CommiCycle页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CommiCycle页面");
		CommiCycle commiCycle = null;
		if (StringUtils.isNotBlank(id)){
			commiCycle = commiCycleService.get(CommiCycle.class, id);
			commiCycle.setNo(null);
		}else{
			commiCycle = new CommiCycle();
		}
		commiCycle.setM_umState("A");
		return new ModelAndView("admin/commi/commiCycle/commiCycle_save")
			.addObject("commiCycle", commiCycle);
	}
	/**
	 * 跳转到修改CommiCycle页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String no){
		logger.debug("跳转到修改CommiCycle页面");
		CommiCycle commiCycle = null;
		if (StringUtils.isNotBlank(no)){
			commiCycle = commiCycleService.get(CommiCycle.class, no);
			commiCycle.setM_umState("M");
		}else{
			commiCycle = new CommiCycle();
		}
		
		return new ModelAndView("admin/commi/commiCycle/commiCycle_update")
			.addObject("commiCycle", commiCycle);
	}
	
	/**
	 * 跳转到查看CommiCycle页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String no){
		logger.debug("跳转到查看CommiCycle页面");
		CommiCycle commiCycle = commiCycleService.get(CommiCycle.class, no);
		commiCycle.setM_umState("V");
		return new ModelAndView("admin/commi/commiCycle/commiCycle_count")
				.addObject("commiCycle", commiCycle);
	}
	
	/**
	 * 商家返利信息功能跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSupplierRebate")
	public ModelAndView goSupplierRebate(HttpServletRequest request,
	        HttpServletResponse response) {
	    
	    logger.debug("跳转到商家返利信息页面");
	    
	    return new ModelAndView("admin/commi/commiCycle/commiCycle_supplierRebate_list");
	}
	
	/**
	 * 商家返利信息主页查询
	 * 
	 * @param request
	 * @param response
	 * @param commiCustStakeholderSuppl
	 * @return
	 */
    @RequestMapping("/goSupplierRebateJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goSupplierRebateJqGrid(HttpServletRequest request,
            HttpServletResponse response, CommiCustStakeholderSuppl commiCustStakeholderSuppl) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        commiCycleService.findSupplierRebatePageBySql(page, commiCustStakeholderSuppl);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    /**
     * 商家返利信息新增
     * 
     * @param request
     * @param response
     * @param commiCustStakeholderSuppl
     * @return
     */
    @RequestMapping("/supplierRebate/goSave")
    public ModelAndView supplierRebateGoSave(HttpServletRequest request,
            HttpServletResponse response) {
        
        CommiCustStakeholderSuppl commiCustStakeholderSuppl =
                new CommiCustStakeholderSuppl();
        commiCustStakeholderSuppl.setM_umState("A");
        
        return new ModelAndView("admin/commi/commiCycle/commiCycle_supplierRebate_update")
                .addObject("commiCustStakeholderSuppl", commiCustStakeholderSuppl);
    }
    
    /**
     * 商家返利信息新增保存
     * 
     * @param request
     * @param response
     * @param commiCustStakeholderSuppl
     */
    @RequestMapping("/supplierRebate/doSave")
    public void supplierRebateDoSave(HttpServletRequest request,
            HttpServletResponse response, CommiCustStakeholderSuppl commiCustStakeholderSuppl) {
        try {
            commiCustStakeholderSuppl.setExpired("F");
            commiCustStakeholderSuppl.setActionLog("ADD");
            commiCustStakeholderSuppl.setLastUpdate(new Date());
            commiCustStakeholderSuppl.setLastUpdatedBy(getUserContext(request).getCzybh());
            
            commiCycleService.save(commiCustStakeholderSuppl);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "新增商家返利信息失败！");
            e.printStackTrace();
        }
    }
    
    /**
     * 商家返利信息编辑
     * 
     * @param request
     * @param response
     * @param commiCustStakeholderSuppl
     * @return
     */
    @RequestMapping("/supplierRebate/goUpdate")
    public ModelAndView supplierRebateGoUpdate(HttpServletRequest request,
            HttpServletResponse response, int pk) {
        
        CommiCustStakeholderSuppl commiCustStakeholderSuppl =
                commiCycleService.get(CommiCustStakeholderSuppl.class, pk);
        
        commiCustStakeholderSuppl.setM_umState("M");
        
        return new ModelAndView("admin/commi/commiCycle/commiCycle_supplierRebate_update")
                .addObject("commiCustStakeholderSuppl", commiCustStakeholderSuppl);
    }
    
    /**
     * 商家返利信息编辑保存
     * 
     * @param request
     * @param response
     * @param commiCustStakeholderSuppl
     */
    @RequestMapping("/supplierRebate/doUpdate")
    public void supplierRebateDoUpdate(HttpServletRequest request,
            HttpServletResponse response, CommiCustStakeholderSuppl commiCustStakeholderSuppl) {
        try {
            commiCustStakeholderSuppl.setActionLog("EDIT");
            commiCustStakeholderSuppl.setLastUpdate(new Date());
            commiCustStakeholderSuppl.setLastUpdatedBy(getUserContext(request).getCzybh());
            
            commiCycleService.update(commiCustStakeholderSuppl);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "更新商家返利信息失败！");
            e.printStackTrace();
        }
    }
    
    /**
     * 商家返利信息从Excel导入
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/supplierRebate/goImportExcel")
    public ModelAndView supplierRebateGoImportExcel(HttpServletRequest request,
            HttpServletResponse response) {
        
        return new ModelAndView("admin/commi/commiCycle/commiCycle_supplierRebate_importExcel");
    }
    
    /**
     * 商家返利信息从Excel导入加载数据
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/supplierRebate/loadExcel")
    @ResponseBody
	public Map<String, Object> supplierRebateLoadExcel(HttpServletRequest request,
	        HttpServletResponse response) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        try {
            MultipartFormData multipartFormData = new MultipartFormData(request);
            FileItem excelFile = multipartFormData.getFileItem();
            
            if (excelFile == null) {
                result.put("success", false);
                result.put("info", "未读取到上传的文件！");
                return result;
            }
            
            InputStream inputStream = excelFile.getInputStream();
            ExcelImportUtils<CommiCustStakeholderSupplBean> excelImportUtils =
                    new ExcelImportUtils<CommiCustStakeholderSupplBean>();
            
            List<CommiCustStakeholderSupplBean> instances =
                    excelImportUtils.importExcelNew(inputStream, CommiCustStakeholderSupplBean.class,
                            Arrays.asList("销售日期", "材料类型1", "供应商编号", "客户编号",
                                    "材料名称", "返利总金额", "干系人编号", "业务员提成", "备注"));
            List<Map<String, Object>> records = new ArrayList<Map<String,Object>>();
            
            for (CommiCustStakeholderSupplBean instance : instances) {
                Map<String, Object> parsedRecord = new HashMap<String, Object>();
                parsedRecord.put("isvalid", true);
                parsedRecord.put("info", "");
                parsedRecord.put("itemDescr", instance.getItemDescr());
                parsedRecord.put("amount", instance.getAmount());
                parsedRecord.put("commiAmount", instance.getCommiAmount());
                parsedRecord.put("remarks", instance.getRemarks());
                
                if (instance.getDate() != null) {
                    parsedRecord.put("date", instance.getDate());
                } else {
                    parsedRecord.put("isvalid", false);
                    parsedRecord.put("info", parsedRecord.get("info") + " 销售日期解析失败");
                }
                
                if (StringUtils.isNotBlank(instance.getItemType1())) {
                    parsedRecord.put("itemType1", instance.getItemType1());
                    
                    ItemType1 itemType1 = commiCycleService.get(ItemType1.class, instance.getItemType1());
                    if (itemType1 != null) {
                        parsedRecord.put("itemType1Descr", itemType1.getDescr());
                    } else {
                        parsedRecord.put("isvalid", false);
                        parsedRecord.put("info", parsedRecord.get("info") + " 材料类型1不存在");
                    }
                } else {
                    parsedRecord.put("isvalid", false);
                    parsedRecord.put("info", parsedRecord.get("info") + " 材料类型1不能为空");
                }
                
                if (StringUtils.isNotBlank(instance.getSupplCode())) {
                    parsedRecord.put("supplCode", instance.getSupplCode());
                    
                    Supplier supplier = commiCycleService.get(Supplier.class, instance.getSupplCode());
                    if (supplier != null) {
                        parsedRecord.put("supplDescr", supplier.getDescr());
                    } else {
                        parsedRecord.put("isvalid", false);
                        parsedRecord.put("info", parsedRecord.get("info") + " 供应商编号不存在");
                    }
                } else {
                    parsedRecord.put("isvalid", false);
                    parsedRecord.put("info", parsedRecord.get("info") + " 供应商编号不能为空");
                }
                
                if (StringUtils.isNotBlank(instance.getCustCode())) {
                    parsedRecord.put("custCode", instance.getCustCode());
                    
                    Customer customer = commiCycleService.get(Customer.class, instance.getCustCode());
                    if (customer != null) {
                        parsedRecord.put("address", customer.getAddress());
                    } else {
                        parsedRecord.put("isvalid", false);
                        parsedRecord.put("info", parsedRecord.get("info") + " 客户编号不存在");
                    }
                } else {
                    parsedRecord.put("isvalid", false);
                    parsedRecord.put("info", parsedRecord.get("info") + " 客户编号不能为空");
                }
                
                if (StringUtils.isNotBlank(instance.getEmpCode())) {
                    parsedRecord.put("empCode", instance.getEmpCode());
                    
                    Employee employee = commiCycleService.get(Employee.class, instance.getEmpCode());
                    if (employee != null) {
                        parsedRecord.put("empName", employee.getNameChi());
                    } else {
                        parsedRecord.put("isvalid", false);
                        parsedRecord.put("info", parsedRecord.get("info") + " 干系人编号不存在");
                    }
                } else {
                    parsedRecord.put("isvalid", false);
                    parsedRecord.put("info", parsedRecord.get("info") + " 干系人编号不能为空");
                }
                
                if ((Boolean) parsedRecord.get("isvalid")) {
                    parsedRecord.put("info", "有效");
                } else {
                    parsedRecord.put("info", "无效：" + parsedRecord.get("info"));
                }
                
                records.add(parsedRecord);
            }
            
            result.put("success", true);
            result.put("records", records);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("info", "当前操作使用了错误类型的值，请检查数值列是否包含非法字符！");
        }
        
        return result;
    }
    
    /**
     * 商家返利信息将解析过的记录存入数据库
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/supplierRebate/doImportExcel")
    public void supplierRebateDoImportExcel(HttpServletRequest request,
            HttpServletResponse response, String records) {
        
        try {
            List<CommiCustStakeholderSuppl> objs =
                    JSON.parseArray(records, CommiCustStakeholderSuppl.class);
            
            commiCycleService.doImportExcelForSupplierRebate(objs, getUserContext(request).getCzybh());
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, e.getCause());
            e.printStackTrace();
        }
    }
    
	/**
	 * 添加CommiCycle
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiCycle commiCycle){
		logger.debug("添加CommiCycle开始");
		try{
			String str = commiCycleService.getSeqNo("tCommiCycle");
			commiCycle.setNo(str);
			commiCycle.setLastUpdate(new Date());
			commiCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCycle.setExpired("F");
			commiCycle.setActionLog("ADD");
			this.commiCycleService.save(commiCycle);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加CommiCycle失败");
		}
	}
	
	/**
	 * 修改CommiCycle
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiCycle commiCycle){
		logger.debug("修改CommiCycle开始");
		try{
			commiCycle.setLastUpdate(new Date());
			commiCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCycle.setActionLog("EDIT");
			commiCycle.setExpired("F");
			this.commiCycleService.update(commiCycle);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改CommiCycle失败");
		}
	}
	
	/**
	 * 删除CommiCycle
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CommiCycle开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CommiCycle编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CommiCycle commiCycle = commiCycleService.get(CommiCycle.class, deleteId);
				if(commiCycle == null)
					continue;
				commiCycle.setExpired("T");
				commiCycleService.update(commiCycle);
			}
		}
		logger.debug("删除CommiCycle IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiCycle导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCycle commiCycle){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiCycleService.findPageBySql(page, commiCycle);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"提成计算_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 查状态
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkStatus")
	public String checkStatus(HttpServletRequest request,
			HttpServletResponse response, String no) {
		logger.debug("提成计算查状态");
		return commiCycleService.checkStatus(no);
	}
	
	/**
	 * 检查周期
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping("/isExistsPeriod")
	@ResponseBody
	public String isExistsPeriod(HttpServletRequest request,HttpServletResponse response,
			String no,Integer mon){
	
		return commiCycleService.isExistsPeriod(no,mon);
	}
	
	/**
	 * 计算完成
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doComplete")
	public void doComplete(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("提成计算完成");
		this.commiCycleService.doComplete(no);
		ServletUtils.outPrintSuccess(request, response,"计算完成");
	}
	
	/**
	 * 退回
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("提成计算退回");
		this.commiCycleService.doReturn(no);
		ServletUtils.outPrintSuccess(request, response,"退回成功");
	}
	
	/**
	 * 生成提成数据
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doCount")
	public void doCount(HttpServletRequest request, HttpServletResponse response,CommiCycle commiCycle){
		logger.debug("生成提成数据");
		commiCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
		Map<String, Object>resultMap=this.commiCycleService.doCount(commiCycle);
		ServletUtils.outPrintSuccess(request, response, resultMap);
	}
	
}

