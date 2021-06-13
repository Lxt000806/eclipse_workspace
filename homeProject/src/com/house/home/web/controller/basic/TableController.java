package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Table;
import com.house.home.entity.project.PrjJob;
import com.house.home.service.basic.TableService;

@Controller
@RequestMapping("/admin/table")
public class TableController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TableController.class);

	@Autowired
	private TableService tableService;

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
			HttpServletResponse response, Table table) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		table.setCzybh(getUserContext(request).getCzybh());
		List<Map<String,Object>> result = tableService.getColModel(table);
		page.setTotalCount(result.size());
		page.setResult(result);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Table列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/table/table_list");
	}
	
	/**
	 * 添加Table
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Table table){
		logger.debug("保存");
		try{
			table.setCzybh(getUserContext(request).getCzybh());
			Result result =tableService.doSave(table);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}

	@RequestMapping("/goSetColumn")
	public ModelAndView goSetColumn(HttpServletRequest request,
			HttpServletResponse response, Table table) throws Exception {
		table.setCzybh(getUserContext(request).getCzybh());
		List<Map<String,Object>> columnsList = tableService.getColumns(table);
		Map<String, Object> tableCzyMapperMap = tableService.getTableCzyMapper(table); 
		Integer checkedNum = 0;
		String selectAllStatus = "0";
		String columnHtml = "";
		for (int i = 0; i < columnsList.size(); i++) {
			Map<String, Object> columnMap = columnsList.get(i);
			if (i % 4 == 0) {
				columnHtml+="<tr style='border: none'>";
			}
			columnHtml += "<td style='width: 20px;border:none;padding-bottom: 5px'>"
			+"<input name='child' type='checkbox' "+columnMap.get("IsCheck").toString()
			+" id='"+columnMap.get("ColumnPK").toString()+"' "
			+"onclick='clickBox(this)' value='"+columnMap.get("ColumnPK").toString()+"'/>"
			+"<td class='td-value' style='border:none'><label class='common-label' for='"
			+columnMap.get("ColumnPK").toString()+"'>"+columnMap.get("Descr").toString()+"</label></td>"
			+"</td>";
			if (i % 4 == 3) {
				columnHtml+="</tr>";
			}
			if ("checked".equals(columnMap.get("IsCheck").toString())) {
				checkedNum++;
			}
		}
		if (checkedNum == 0) {
			selectAllStatus = "1";
		}else if(checkedNum == columnsList.size()){
			selectAllStatus = "2";
		}
		return new ModelAndView("admin/basic/table/table_setColumn")
		.addObject("table", table).addObject("columnHtml", columnHtml)
		.addObject("tableCzyMapperMap", tableCzyMapperMap)
		.addObject("selectAllStatus", selectAllStatus);
	}
	
}
