package com.house.home.web.controller.query;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.SoftCostQueryService;

@Controller
@RequestMapping("/admin/softCostQuery")
public class SoftCostQueryController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SoftCostQueryController.class);
	
	@Autowired
	private SoftCostQueryService softCostQueryService;

	/**
	 * 软装收支明细查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {	
		List<Map<String,Object>>  listItemType12Descr=softCostQueryService.getItemType12Descr();
		ArrayList<String> list = new ArrayList<String>();

		for (Map<String, Object> m :listItemType12Descr)  
	    {  
			for (String k : m.keySet())  
			{  
				list.add(((String) m.get(k)).trim());
			}  
	    }
		JSONArray arryItemType12Descr = JSONArray.fromObject(list);
		return new ModelAndView("admin/query/softCostQuery/softCostQuery_list").addObject("customer", customer).
			   addObject("arrayItemType12Descr", arryItemType12Descr);		
	}
    /**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> prjProgCheckMx(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softCostQueryService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response){
		String jsonString = request.getParameter("jsonString");
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(jsonString); 
		String colModel = (String) jo.get("colModel");
		String rows = (String) jo.get("rows");
		int titleNum=0;//第几列
		net.sf.json.JSONArray arryRows = net.sf.json.JSONArray.fromObject(rows);
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>(); 
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		
		List<String> titleList = new ArrayList<String>();
		List<String> columnList = new ArrayList<String>();
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
            		if(titleNum<3){
            			titleList.add((String) jsonObject.get("label"));
            		}else{
            			titleList.add(name);
            		}
            		titleNum++;
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
				"软装收支明细"+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	
	}

		
	
}
