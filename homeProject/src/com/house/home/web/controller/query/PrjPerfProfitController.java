package com.house.home.web.controller.query;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileHelper;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjPerfProfitService;

/**
 * 工地结算利润分析
 * @author created by zb
 * @date   2019-1-2--下午5:15:25
 */
@Controller
@RequestMapping("/admin/prjPerfProfit")
public class PrjPerfProfitController extends BaseController{
	@Autowired
	private PrjPerfProfitService prjPerfProfitService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjPerfProfitService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "prjPerfProfitSelectCol");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		String colModel = FileHelper.readTxtFile(filePath+fileName);*/
		return new ModelAndView("admin/query/prjPerfProfit/prjPerfProfit_list")
			/*.addObject("colModel", colModel)*/;
	}
	
	/**
	 * 保存列
	 * @author	created by zb
	 * @date	2019-1-17--下午5:37:40
	 * @param request
	 * @param response
	 * @param customer
	 * @return 
	 */
	@RequestMapping("/doSaveCols")
	@ResponseBody
	public void doSaveCols(HttpServletRequest request, 
			HttpServletResponse response){
		String colModel_save_1 = request.getParameter("colModel_save_1");
		String colModel_save_2 = request.getParameter("colModel_save_2");
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "prjPerfProfitSelectCol");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		JSONArray arryCols_1 = JSONArray.fromObject(colModel_save_1);
		for (int i = 0; i < arryCols_1.size(); i++) { 
            JSONObject jsonObject = arryCols_1.getJSONObject(i);
            String name = (String) jsonObject.get("name");
            if (name.equals("enddate")){
            	jsonObject.put("formatter", "formatDate");
            }
        }
		JSONObject jObjectCols = new JSONObject();
		jObjectCols.put("colModel_save_1", arryCols_1.toString().replace("\"formatTime\"", "formatTime").replace("\"formatDate\"", "formatDate"));
		jObjectCols.put("colModel_save_2", colModel_save_2);
		String str = jObjectCols.toString();
		if (FileHelper.strToFile(filePath, fileName, str)){
			ServletUtils.outPrintSuccess(request, response, "设置成功！");
		}else{
			ServletUtils.outPrintFail(request, response, "设置失败！");
		}
	}
	
	/**
	 * 删除列文件
	 * @author	created by zb
	 * @date	2019-1-18--上午9:34:24
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("/doResetCols")
	@ResponseBody
	public boolean doResetCols(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "prjPerfProfitSelectCol");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		File file = new File(filePath+"\\"+fileName);
		if(file.exists()){file.delete();}
		return true;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response){
		String jsonString = request.getParameter("jsonString");
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(jsonString);
		// 表格数据
		String colModel = (String) jo.get("colModel");
		String rows = (String) jo.get("rows");
		net.sf.json.JSONArray arryRows = net.sf.json.JSONArray.fromObject(rows);
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		//数据
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>(); 
		//列名
		List<String> titleList = new ArrayList<String>();
		List<String> columnList = new ArrayList<String>();
		//统计
		List<String> sumList = new ArrayList<String>();
		boolean hasSum = false;
		for (int i = 0; i < arryCols.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryCols.getJSONObject(i);
            String name = (String) jsonObject.get("name");
            Boolean hidden = jsonObject.get("hidden")==null?false:(Boolean)jsonObject.get("hidden");
            Boolean sum = (Boolean) jsonObject.get("sum");
            Boolean avg = (Boolean) jsonObject.get("avg");
            //np：true则不打印
            Boolean np = (Boolean) (jsonObject.get("np")==null?false:jsonObject.get("np"));
            if (!"rn".equals(name) && !"cb".equals(name)&&!np){
            	if (hidden.booleanValue()==false){
            		columnList.add(name);
            		titleList.add((String) jsonObject.get("excelname"));
            		if ((sum!=null && sum==true) || (avg!=null && avg==true)){
            			sumList.add(name);
            			hasSum = true;
            		}else{
            			sumList.add("");
            		}
            	}
            }
        } 
		if (hasSum==false){
			sumList = new ArrayList<String>();
		}
		for (int i = 0; i < arryRows.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryRows.getJSONObject(i); 
            Map<String, Object> map = new HashMap<String, Object>(); 
            int j=1;//最前面有个rs，所以从1开始
            for (Iterator<?> iter = (Iterator<?>) jsonObject.keys(); iter.hasNext();) { 
            	net.sf.json.JSONObject jsonObject1 = arryCols.getJSONObject(j);
 	            String align = (String) jsonObject1.get("align");
 	            String key = (String) iter.next(); 
                String value = jsonObject.get(key).toString(); 
                if (columnList.indexOf(key)!=-1){
                	if("right".equals(align)){
                		try {
                			String st = Arith.strNumToStr(value);
                			map.put(key, Double.parseDouble(st));
						} catch (Exception e) {
	                		map.put(key, value);
						}
                	}else{
                		map.put(key, value);
                	}
                }
                j++;
            } 
            rsList.add(map); 
        }  
		ServletUtils.flushExcelOutputStream(request, response, rsList,
				"工地结算利润分析"+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	}
	
}
