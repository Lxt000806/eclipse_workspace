package com.house.home.web.controller.basic;

import java.io.InputStream;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.SetMainItemPriceBean;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.SetMainItemPrice;
import com.house.home.entity.salary.SalaryEmpDeduction;
import com.house.home.service.basic.SetMainItemPriceService;

@Controller
@RequestMapping("/admin/setMainItemPrice")
public class SetMainItemPriceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SetMainItemPriceController.class);

	@Autowired
	private SetMainItemPriceService setMainItemPriceService;
	
	/**
	 * 检查面积是否重复
	 * @author	created by zb
	 * @date	2020-1-9--下午4:44:50
	 * @param request
	 * @param response
	 * @param custType
	 * @param oldFromArea
	 * @param oldToArea
	 * @param fromArea
	 * @param toArea
	 * @return
	 */
	@RequestMapping("/checkArea")
	@ResponseBody
	public JSONObject checkArea(HttpServletRequest request,HttpServletResponse response,
			String custType, Double oldFromArea, Double oldToArea, Double fromArea, Double toArea){
		if (StringUtils.isBlank(custType)) {
			return this.out("传入的custType为空", true);
		}
		if (null != fromArea) {
			if (null != oldFromArea && (fromArea.equals(oldFromArea)||fromArea.equals(oldToArea))) {
				return this.out(null, true);
			} else {
				List<Map<String,Object>> list = this.setMainItemPriceService.getIsRepeatArea(custType, fromArea);
				if (list.size()>0) {
					
					return this.out("和已存在的面积范围重复", false);
				} else {
					return this.out(null, true);
				}
			}
		} else {
			if (null != oldToArea && (toArea.equals(oldToArea)||toArea.equals(oldFromArea))) {
				return this.out(null, true);
			} else {
				List<Map<String,Object>> list = this.setMainItemPriceService.getIsRepeatArea(custType, toArea);
				if (list.size()>0) {
					return this.out("和已存在的面积范围重复", false);
				} else {
					return this.out(null, true);
				}
			}
		}
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, SetMainItemPrice setMainItemPrice) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		setMainItemPriceService.findPageBySql(page, setMainItemPrice);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/setMainItemPrice/setMainItemPrice_list");
	}

	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, 
			Integer pk, String m_umState){
		logger.debug("跳转到新增页面");
		SetMainItemPrice setMainItemPrice = new SetMainItemPrice();
		setMainItemPrice.setM_umState(m_umState);
		return new ModelAndView("admin/basic/setMainItemPrice/setMainItemPrice_add")
			.addObject("setMainItemPrice", setMainItemPrice);
	}

	@RequestMapping("/goEdit")
	public ModelAndView goEdit(HttpServletRequest request, HttpServletResponse response, 
			Integer pk, String m_umState){
		logger.debug("跳转到修改页面");
		SetMainItemPrice setMainItemPrice = new SetMainItemPrice();
		if (null != pk){
			setMainItemPrice = setMainItemPriceService.get(SetMainItemPrice.class, pk);
		}
		setMainItemPrice.setM_umState(m_umState);
		return new ModelAndView("admin/basic/setMainItemPrice/setMainItemPrice_add")
			.addObject("setMainItemPrice", setMainItemPrice);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			Integer pk, String m_umState){
		logger.debug("跳转到查看页面");
		SetMainItemPrice setMainItemPrice = new SetMainItemPrice();
		if (null != pk){
			setMainItemPrice = setMainItemPriceService.get(SetMainItemPrice.class, pk);
		}
		setMainItemPrice.setM_umState(m_umState);
		return new ModelAndView("admin/basic/setMainItemPrice/setMainItemPrice_add")
				.addObject("setMainItemPrice", setMainItemPrice);
	}
	
	@RequestMapping("/goAddFromExcel")
	public ModelAndView goAddFromExcel(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到查看页面");
		return new ModelAndView("admin/basic/setMainItemPrice/setMainItemPrice_import");
	}

	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SetMainItemPrice setMainItemPrice){
		logger.debug("新增开始");
		try{
			this.setMainItemPriceService.doSave(setMainItemPrice, getUserContext(request));
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			System.out.println(e);
			ServletUtils.outPrintFail(request, response, "添加失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SetMainItemPrice setMainItemPrice){
		logger.debug("修改开始");
		try{
			this.setMainItemPriceService.doUpdate(setMainItemPrice, getUserContext(request));
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			System.out.println(e);
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}

	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "编号不能为空,删除失败");
			return;
		}
		try {
			setMainItemPriceService.doDelete(deleteIds);
			logger.debug("删除完成",deleteIds);
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}

	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SetMainItemPrice setMainItemPrice){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		setMainItemPriceService.findPageBySql(page, setMainItemPrice);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"套餐主项目价格管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * excel加载数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<SetMainItemPriceBean> eUtils=new ExcelImportUtils<SetMainItemPriceBean>();
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
		
		titleList.add("客户类型");
		titleList.add("起始面积");
		titleList.add("截止面积");
		titleList.add("基准单价");
		titleList.add("基准面积");
		titleList.add("每平米单价");
		titleList.add("基准卫生间数");
		titleList.add("卫生间单价");
		try {		
			List<SetMainItemPriceBean> result=eUtils.importExcel(in,SetMainItemPriceBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(SetMainItemPriceBean setMainItemPriceBean:result){
				
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "有效");
				data.put("custtype", setMainItemPriceBean.getCustType());
				data.put("fromarea", setMainItemPriceBean.getFromArea()==null?0:setMainItemPriceBean.getFromArea());
				data.put("toarea", setMainItemPriceBean.getToArea()==null?0:setMainItemPriceBean.getToArea());
				data.put("baseprice", setMainItemPriceBean.getBasePrice()==null?0:setMainItemPriceBean.getBasePrice());
				data.put("basearea", setMainItemPriceBean.getBaseArea()==null?0:setMainItemPriceBean.getBaseArea());
				data.put("unitprice", setMainItemPriceBean.getUnitPrice()==null?0:setMainItemPriceBean.getUnitPrice());
				data.put("basetoiletcount", setMainItemPriceBean.getBaseToiletCount()==null?0:setMainItemPriceBean.getBaseToiletCount());
				data.put("toiletprice", setMainItemPriceBean.getToiletPrice()==null?0:setMainItemPriceBean.getToiletPrice());
				
				if(StringUtils.isNotBlank(setMainItemPriceBean.getCustType())){
					CustType custType = setMainItemPriceService.get(CustType.class, setMainItemPriceBean.getCustType());
					if (custType != null){
						data.put("custtypedescr", custType.getDesc1());
					}else{
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "无效，客户类型不匹配");
					}	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "无效，客户类型不为空");
				}
				
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
			map.put("returnInfo","当前操作使用了错误类型的值,请检查数值列是否包含非法字符!");
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
	@ResponseBody
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,SetMainItemPrice setMainItemPrice){
		logger.debug("批量导入");
		try {
			setMainItemPrice.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.setMainItemPriceService.doSaveBatch(setMainItemPrice);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "导入失败");
		}
	}
	
}
