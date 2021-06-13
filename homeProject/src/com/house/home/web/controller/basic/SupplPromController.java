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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.bean.insales.SupplPromBean;
import com.house.home.entity.basic.SupplProm;
import com.house.home.entity.basic.SupplPromItem;
import com.house.home.service.basic.SupplPromService;
@Controller
@RequestMapping("/admin/supplProm")
public class SupplPromController extends BaseController{
	@Autowired
	private  SupplPromService supplPromService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, SupplProm supplProm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplPromService.findPageBySql(page, supplProm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemJqGrid(HttpServletRequest request,
			HttpServletResponse response, SupplProm supplProm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplPromService.findItemPageBySql(page, supplProm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, SupplProm supplProm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplPromService.findDetailPageBySql(page, supplProm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/supplProm/supplProm_list");
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response, SupplProm supplProm) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m_umState", "A");
		return new ModelAndView("admin/basic/supplProm/supplProm_win")
			.addObject("supplProm", map);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, SupplProm supplProm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplPromService.findPageBySql(page, supplProm);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", supplProm.getM_umState());
		return new ModelAndView("admin/basic/supplProm/supplProm_win")
			.addObject("supplProm", map);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, SupplProm supplProm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		supplPromService.findPageBySql(page, supplProm);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", supplProm.getM_umState());
		return new ModelAndView("admin/basic/supplProm/supplProm_win")
			.addObject("supplProm", map);
	}
	/**
	 * 子表格的CRUD
	 * @author	created by zb
	 * @date	2019-7-13--下午5:36:55
	 * @param request
	 * @param response
	 * @param supplProm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItem")
	public ModelAndView goItem(HttpServletRequest request,
			HttpServletResponse response, SupplPromItem supplPromItem) throws Exception {
		return new ModelAndView("admin/basic/supplProm/supplProm_item")
			.addObject("supplPromItem", supplPromItem);
	}
	/**
	 * 导入Excel
	 * @author	created by zb
	 * @date	2019-7-15--上午11:56:16
	 * @param request
	 * @param response
	 * @param supplPromItem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemExcel")
	public ModelAndView goItemExcel(HttpServletRequest request,
			HttpServletResponse response, SupplPromItem supplPromItem) throws Exception {
		return new ModelAndView("admin/basic/supplProm/supplProm_importExcel")
			.addObject("supplPromItem", supplPromItem);
	}
	
	/**
	 * 导入Excel
	 * @author	created by zb
	 * @date	2019-7-15--下午2:29:39
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<SupplPromBean> eUtils=new ExcelImportUtils<SupplPromBean>();// 导入EXCEL导入工具, 产出指定pojo 列表
		String code="";
		DiskFileItemFactory fac = new DiskFileItemFactory();// 创建FileItem 对象的工厂——环境
		ServletFileUpload upload = new ServletFileUpload(fac);// 核心操作类
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);// 解析请求正文内容
		Iterator it = fileList.iterator();// 迭代器
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// 如果是普通表单参数
			String fieldName = obit.getFieldName();
			String fieldValue = obit.getString();
			if ("file".equals(fieldName)){
				in=obit.getInputStream();
			}
		}
		// 必须包含的列名
		titleList.add("品牌");
		titleList.add("型号");
		titleList.add("有家直供价");
		titleList.add("活动价");
		titleList.add("常规成本");
		titleList.add("活动成本");
		try {
			List<SupplPromBean> result=eUtils.importExcel(in, SupplPromBean.class,titleList);// 必须包含的标题列表，null则无限制
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			int i=0;
			for(SupplPromBean supplPromBean:result){
				i++;
				if(StringUtils.isNotBlank(supplPromBean.getError())){
					map.put("success", false);
					map.put("returnInfo", supplPromBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				// 将数据存入map中
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid", "0");// 有效
				data.put("isinvaliddescr", "有效");
				if (StringUtils.isBlank(supplPromBean.getItemtype3())) {
					map.put("success", false);
					map.put("returnInfo", "存在没有品牌的数据");
					map.put("hasInvalid", true);
					return map;
				}
				data.put("itemtype3", supplPromBean.getItemtype3());
				data.put("itemdescr", supplPromBean.getItemdescr());
				if (StringUtils.isNotBlank(supplPromBean.getModel())) {
					data.put("model", supplPromBean.getModel());
				} else {
					data.put("isinvalid", "1");
					data.put("isinvaliddescr", "型号不允许为空");
					datas.add(data);
					continue;
				}
				data.put("uom", supplPromBean.getUom());
				data.put("itemsize", supplPromBean.getItemsize());
				if(null != supplPromBean.getUnitprice()){
					data.put("unitprice", supplPromBean.getUnitprice());
				}else{
					data.put("unitprice", 0);
				}
				if(null != supplPromBean.getPromprice() && 0 < supplPromBean.getPromprice()){
					data.put("promprice", supplPromBean.getPromprice());
				}else{
					data.put("isinvalid", "1");
					data.put("isinvaliddescr", "活动价不允许为空或‘0’");
					datas.add(data);
					continue;
				}
				if(null != supplPromBean.getCost()){
					data.put("cost", supplPromBean.getCost());
				}else{
					data.put("cost", 0);
				}
				if(null != supplPromBean.getPromcost() && 0 < supplPromBean.getPromcost()){
					data.put("promcost", supplPromBean.getPromcost());
				}else{
					data.put("isinvalid", "1");
					data.put("isinvaliddescr", "活动成本不允许为空或‘0’");
					datas.add(data);
					continue;
				}
				data.put("remarks", supplPromBean.getRemarks());
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
			map.put("returnInfo", "当前操作使用了错误类型的值,请检查数值列是否包含非法字符!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,SupplProm supplProm){
		logger.debug("供应商促销保存");
		try {
			supplProm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "无数据");
				return;
			}
			Result result = this.supplPromService.doSave(supplProm);
			if (result.isSuccess()){
				ServletUtils.outPrint(request, response, true, "保存成功", null, true);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SupplProm supplProm){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		supplPromService.findPageBySql(page, supplProm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"供应商促销管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
