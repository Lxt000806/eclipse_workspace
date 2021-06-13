package com.house.home.web.controller.supplier;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.excel.ExportExcel;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.ItemApp;
import com.house.home.service.insales.MaterialSendService;

@Controller
@RequestMapping("/admin/materialSend")
public class MaterialSendController extends BaseController {

	@Autowired
	private MaterialSendService materialSendService;
	
	private void initSendDate(ItemApp itemApp) {
		if (itemApp == null)
			return;
		
		if (itemApp.getSendDateFrom() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			itemApp.setSendDateFrom(calendar.getTime());
		}
		
		if (itemApp.getSendDateTo() == null) {
			Calendar calendar = Calendar.getInstance();
			itemApp.setSendDateTo(calendar.getTime());
		}
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemApp.setSupplCode(this.getUserContext(request).getEmnum());
		initSendDate(itemApp);
		materialSendService.findPageBySql(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 材料发货分析
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemApp itemApp) throws Exception {
		initSendDate(itemApp);
		return new ModelAndView("admin/supplier/materialSend/materialSend_list")
			.addObject("itemApp", itemApp);
	}
	
	/**
	 * 导出数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/export")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		String exportData = request.getParameter("exportData");
		if(StringUtils.isEmpty(exportData)){
			logger.error("导出查询结果数据传入的参数为空!");
			return;
		}
		JSONObject dataObj = JSONObject.parseObject(exportData);
		JSONArray colHeader = dataObj.getJSONArray("colHeader");
		if(colHeader != null && !colHeader.isEmpty()){
			List<String> colNames = Lists.newArrayList();
			List<String> colLabels = Lists.newArrayList();
			for(int i = 0; i < colHeader.size(); i ++){
				JSONObject obj = colHeader.getJSONObject(i);
				colNames.add(obj.getString("colName"));
				colLabels.add(obj.getString("colLabel"));
			}
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			page.setPageSize(100000000);
			
			ItemApp itemApp = new ItemApp();
			itemApp.setSupplCode(this.getUserContext(request).getEmnum());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if ((dataObj.getString("sendDateFrom")!=null) && !dataObj.getString("sendDateFrom").equals(""))
					itemApp.setSendDateFrom(sdf.parse(dataObj.getString("sendDateFrom")));
				if ((dataObj.getString("sendDateTo")!=null) && !dataObj.getString("sendDateTo").equals(""))
					itemApp.setSendDateTo(sdf.parse(dataObj.getString("sendDateTo")));
			} catch (ParseException e) {
				logger.error("导出查询结果数据至excel文件出错："+e.getMessage());
				e.printStackTrace();
			}
			materialSendService.findPageBySql(page, itemApp);
			String exportTitle = dataObj.getString("exportTitle");
			exportTitle = StringUtils.isEmpty(exportTitle)?"导出数据":exportTitle;
			String fileName = exportTitle+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss")+".xlsx";
			try {
				new ExportExcel(exportTitle,colLabels).setMapDataList(page.getResult(), colNames).write(response, fileName).dispose();
			} catch (IOException e) {
				logger.error("导出查询结果数据至excel文件出错："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
