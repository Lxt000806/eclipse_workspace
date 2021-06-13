package com.house.home.web.controller.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department3;
import com.house.home.service.basic.StaffingService;
@Controller
@RequestMapping("/admin/staffing")
public class StaffingController extends BaseController{
	@Autowired
	private StaffingService staffingService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, String departType, Department3 department3) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		staffingService.findPageBySql(page, departType, department3);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/staffing/staffing_list");
	}
	
	/**
	 * 自定义和并列导出Excel
	 * @author	created by zb
	 * @date	2018-12-20--下午3:30:46
	 * @param request
	 * @param response
	 */
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
            //np：true则不打印
            Boolean np = (Boolean) (jsonObject.get("np")==null?false:jsonObject.get("np"));
            if (!"rn".equals(name) && !"cb".equals(name)&&!np){
            	if (hidden.booleanValue()==false){
            		columnList.add(name);
            		titleList.add((String) jsonObject.get("index"));
            		if (sum!=null && sum==true){
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
            int j=0;
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
				"部门编制统计"+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	}

}
