package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.AppItemTypeBatch;
import com.house.home.entity.basic.PrjItem2;
import com.house.home.service.basic.PrjItem2Service;

@Controller
@RequestMapping("/admin/prjItem2")
public class PrjItem2Controller extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(PrjItem2Controller.class);

	@Autowired
	private PrjItem2Service prjItem2Service;

	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			PrjItem2 prjItem2) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		prjItem2Service.findPageBySql(page, prjItem2);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/checkCode")
	@ResponseBody
	public JSONObject checkCode(HttpServletRequest request,HttpServletResponse response,String code, String oldCode){
		if (StringUtils.isNotBlank(oldCode) && code.equals(oldCode)) {
			return this.out(null, true);
		}
		if(StringUtils.isEmpty(code)){
			return this.out("传入的code为空", false);
		}
		PrjItem2 prjItem2 = this.prjItem2Service.get(PrjItem2.class, code);
		if(prjItem2 != null){
			return this.out("系统中已存在code="+code+"的信息", false);
		}
		return this.out(prjItem2, true);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList() {
		return new ModelAndView("admin/basic/prjItem2/prjItem2_list");
	}

	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, PrjItem2 prjItem2) {
		return new ModelAndView("admin/basic/prjItem2/prjItem2_save")
			.addObject("prjItem2", prjItem2);
	}

	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, PrjItem2 prjItem2) {
		return new ModelAndView("admin/basic/prjItem2/prjItem2_save")
				.addObject("prjItem2", prjItem2);
	}

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, PrjItem2 prjItem2) {
		return new ModelAndView("admin/basic/prjItem2/prjItem2_save")
			.addObject("prjItem2", prjItem2);
	}

	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, PrjItem2 prjItem2) {
		logger.debug("施工项目2保存");
		try {
			prjItem2.setLastUpdate(new Date());
			prjItem2.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjItem2.setExpired("F");
			prjItem2.setActionLog("ADD");
			this.prjItem2Service.save(prjItem2);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "施工项目2保存失败");
		}
	}

	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, PrjItem2 prjItem2) {
		logger.debug("施工项目2编辑");
		try {
			prjItem2.setLastUpdate(new Date());
			prjItem2.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjItem2.setActionLog("Edit");
			this.prjItem2Service.update(prjItem2);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "施工项目2编辑失败");
		}
	}

	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "编号不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					PrjItem2 prjItem2 = this.prjItem2Service.get(PrjItem2.class, deleteId);
					if (null == prjItem2) {
						ServletUtils.outPrintFail(request, response, "该信息不存在");
						return;
					}
					prjItem2.setExpired("T");
					this.prjItem2Service.update(prjItem2);
					ServletUtils.outPrintSuccess(request, response, "删除成功");
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	@RequestMapping("/getPrjItem2ListByPrjItem1")
	@ResponseBody
	public JSONObject getPrjItem2ListByPrjItem1(HttpServletRequest request, HttpServletResponse response, String prjItem1){
		List<Map<String, Object>> list = this.prjItem2Service.getPrjItem2ListByPrjItem1(prjItem1);
		JSONObject jsonObject = new JSONObject();
		StringBuilder buffer = new StringBuilder();
		buffer.append("<option value=\"\">请选择...</option>");
		for(int i = 0; i < list.size(); i++) {
			buffer.append("<option value=\"" + list.get(i).get("code") + "\">" + list.get(i).get("code") + " " + list.get(i).get("descr") + "</option>");
		}
		jsonObject.put("html", buffer.toString());
		return jsonObject;
	}

	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, PrjItem2 prjItem2) {
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjItem2Service.findPageBySql(page, prjItem2);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"施工项目2_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
