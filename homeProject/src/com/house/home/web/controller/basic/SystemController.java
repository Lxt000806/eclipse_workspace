package com.house.home.web.controller.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.JasperReportUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;

/** 
* @ClassName: SystemController 
* @Description: 系统通用controller调用
* @author liuxiaobin
* @date 2015年9月23日 下午5:47:42 
*  
*/
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController{
	
	/**
	 * 
	* @Title: index 
	* @Description: 跳转至打印页面
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,Model model){
		return "japser/print";
	}

	/**
	 * 
	* @Title: print 
	* @Description: 选择打印类型
	* @param @param request
	* @param @param response    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/print",method = {RequestMethod.POST})
	public void print(HttpServletRequest request,HttpServletResponse response){
		String postData = request.getParameter("postData");
		if(StringUtils.isEmpty(postData)){
			logger.debug("传入的postData参数为空！");
			ServletUtils.outPrintFail(request, response, "传入的postData参数为空");
		}else{
			JSONObject obj = JSONObject.parseObject(postData);
			String report = obj.getString("report");
			String data = obj.getString("data");
			Map<String,Object> param = new HashMap<String,Object>();
			if(!StringUtils.isEmpty(data)){
				param = JSONObject.parseObject(data, Map.class);
			}
			String reportDetail = obj.getString("detail");
			List<Map<String,Object>> reportDetailList = Lists.newArrayList();
			if(!StringUtils.isEmpty(reportDetail)){
				JSONArray detailArray = JSONArray.parseArray(reportDetail);
				for(int i = 0; i < detailArray.size(); i ++){
					reportDetailList.add(detailArray.getObject(i, Map.class));
				}
			}
//			UserContext userContext  = UserContextHolder.getUserContext();
//			param.put("Czybh", userContext.getCzybh());
//			param.put("EmployeeId", userContext.getEmnum());
//			param.put("EmployeeName", userContext.getZwxm());
			param.put("SUBREPORT_DIR", JasperReportUtils.getReportPath());
			
			//页面所传的参数通过request.getParameter("postData");获取(json字符串),解析后保存到param变量中;
			//此时isPreview参数已经保存到param中,并且request并没有单独的isPreview数据;
			//然而原来却执行了param.put("isPreview", request.getParameter("isPreview"));
			//通过打印和查看请求参数后发现isPreview不存在,所以request.getParameter("isPreview")取不到值
			//最终对isPreview参数的值改为如下判断
			//isPreview参数判断 begin modify by zzr 2018/06/28 begin
			if(param.get("isPreview") == null || !"true".equals(param.get("isPreview").toString())){
				param.put("isPreview", request.getParameter("isPreview"));//是否预览
			}else{
				param.put("isPreview", param.get("isPreview").toString());
			}
			//isPreview参数判断 begin modify by zzr 2018/06/28 end
			if(reportDetailList == null || reportDetailList.isEmpty()){
				JasperReportUtils.printMultiPDF(response, report, param);
			}else{
				JasperReportUtils.printPDF(response, report,reportDetailList,param);
			}
		}
	}
	
	/**
	 * 报表以excel表格方式导出
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportExcel",method = {RequestMethod.POST})
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		String report = request.getParameter("report");
		String data = request.getParameter("data");
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(data)){
			param = JSONObject.parseObject(data, Map.class);
		}
		String reportDetail = request.getParameter("detail");
		List<Map<String,Object>> reportDetailList = Lists.newArrayList();
		if(!StringUtils.isEmpty(reportDetail)){
			JSONArray detailArray = JSONArray.parseArray(reportDetail);
			for(int i = 0; i < detailArray.size(); i ++){
				reportDetailList.add(detailArray.getObject(i, Map.class));
			}
		}
		UserContext userContext  = UserContextHolder.getUserContext();
		param.put("Czybh", userContext.getCzybh());
		param.put("EmployeeId", userContext.getEmnum());
		param.put("EmployeeName", userContext.getZwxm());
		param.put("SUBREPORT_DIR", JasperReportUtils.getReportPath());
		//param.put("isPreview", request.getParameter("isPreview"));//是否预览
		if(reportDetailList == null || reportDetailList.isEmpty()){
			JasperReportUtils.printXLS(response, report, param);
		}else{
			JasperReportUtils.printPDF(response, report,reportDetailList,param);
		}
	} 
	/**
	 * 导出当前Excel中的数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response){
		String jsonString = request.getParameter("jsonString");
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(jsonString); 
		String colModel = (String) jo.get("colModel");
		String rows = (String) jo.get("rows");
		String preName = jo.get("preName")==null?"":(String) jo.get("preName");
		net.sf.json.JSONArray arryRows = net.sf.json.JSONArray.fromObject(rows);
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>(); 
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		Map<String, Object> alignMap = new HashMap<String, Object>(); //保存需要打印的列的align值
		
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
            		titleList.add(jsonObject.get("excelLabel")!=null?(String) jsonObject.get("excelLabel"):(String) jsonObject.get("label"));
            		columnList.add(name);
            		if (sum!=null && sum==true){
            			sumList.add(name);
            			hasSum = true;
            		}else{
            			sumList.add("");
            		}
            	}
        		alignMap.put(jsonObject.getString("name"), jsonObject.getString("align"));
            }
        } 
		if (hasSum==false){
			sumList = new ArrayList<String>();
		}
		for (int i = 0; i < arryRows.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryRows.getJSONObject(i); 
            Map<String, Object> map = new HashMap<String, Object>(); 
            //int j=0;
            for (Iterator<?> iter = (Iterator<?>) jsonObject.keys(); iter.hasNext();) { 
            	/**
            	 * 由于arryRows和arryCols可能因为rn列不对应,导致align取错
            	 * modify by zzr 2018/07/18
            	 */
            	 //net.sf.json.JSONObject jsonObject1 = arryCols.getJSONObject(j);
 	            //String align = (String) jsonObject1.get("align");
 	            String key = (String) iter.next(); 
                String value = jsonObject.get(key).toString(); 
                String align = alignMap.get(key) == null || StringUtils.isBlank(alignMap.get(key).toString()) ? "left":alignMap.get(key).toString();
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
                //j++;
            } 
            rsList.add(map); 
        } 
		ServletUtils.flushExcelOutputStream(request, response, rsList,
				preName+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	}
}
