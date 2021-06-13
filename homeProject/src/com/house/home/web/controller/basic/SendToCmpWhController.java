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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.SendToCmpWh;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.basic.SendToCmpWhService;

@Controller
@RequestMapping("/admin/sendToCmpWh")
public class SendToCmpWhController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SendToCmpWhController.class);

	@Autowired
	private SendToCmpWhService sendToCmpWhService;

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
			HttpServletResponse response, SendToCmpWh sendToCmpWh) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sendToCmpWhService.findPageBySql(page, sendToCmpWh);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SendToCmpWh列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/sendToCmpWh/sendToCmpWh_list");
	}
	/**
	 * SendToCmpWh查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/sendToCmpWh/sendToCmpWh_code");
	}
	/**
	 * 跳转到新增SendToCmpWh页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增SendToCmpWh页面");
		SendToCmpWh sendToCmpWh = null;
		if (StringUtils.isNotBlank(id)){
			sendToCmpWh = sendToCmpWhService.get(SendToCmpWh.class, Integer.parseInt(id));
			sendToCmpWh.setPk(null);
		}else{
			sendToCmpWh = new SendToCmpWh();
		}
		sendToCmpWh.setM_umState("A");
		return new ModelAndView("admin/basic/sendToCmpWh/sendToCmpWh_save")
			.addObject("sendToCmpWh", sendToCmpWh);
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到复制SendToCmpWh页面");
		SendToCmpWh sendToCmpWh = null;
		if (StringUtils.isNotBlank(id)){
			sendToCmpWh = sendToCmpWhService.get(SendToCmpWh.class, Integer.parseInt(id));
			sendToCmpWh.setPk(null);
		}else{
			sendToCmpWh = new SendToCmpWh();
		}
		sendToCmpWh.setM_umState("C");
		return new ModelAndView("admin/basic/sendToCmpWh/sendToCmpWh_save")
			.addObject("sendToCmpWh", sendToCmpWh);
	}
	
	/**
	 * 跳转到修改SendToCmpWh页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改SendToCmpWh页面");
		SendToCmpWh sendToCmpWh = null;
		if (StringUtils.isNotBlank(id)){
			sendToCmpWh = sendToCmpWhService.get(SendToCmpWh.class, Integer.parseInt(id));
		}else{
			sendToCmpWh = new SendToCmpWh();
		}
		sendToCmpWh.setM_umState("M");
		if(StringUtils.isNotBlank(sendToCmpWh.getSupplCode())){
			Supplier supplier=sendToCmpWhService.get(Supplier.class, sendToCmpWh.getSupplCode());
			sendToCmpWh.setSupplDescr(supplier.getDescr());
		}
		return new ModelAndView("admin/basic/sendToCmpWh/sendToCmpWh_save")
			.addObject("sendToCmpWh", sendToCmpWh);
	}
	
	/**
	 * 跳转到查看SendToCmpWh页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看SendToCmpWh页面");
		SendToCmpWh sendToCmpWh = sendToCmpWhService.get(SendToCmpWh.class, Integer.parseInt(id));
		sendToCmpWh.setM_umState("V");
		if(StringUtils.isNotBlank(sendToCmpWh.getSupplCode())){
			Supplier supplier=sendToCmpWhService.get(Supplier.class, sendToCmpWh.getSupplCode());
			sendToCmpWh.setSupplDescr(supplier.getDescr());
		}
		return new ModelAndView("admin/basic/sendToCmpWh/sendToCmpWh_save")
				.addObject("sendToCmpWh", sendToCmpWh);
	}
	/**
	 * 添加SendToCmpWh
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SendToCmpWh sendToCmpWh){
		logger.debug("添加SendToCmpWh开始");
		try{
			sendToCmpWh.setLastUpdate(new Date());
			sendToCmpWh.setActionLog("ADD");
			sendToCmpWh.setLastUpdatedBy(getUserContext(request).getCzybh());
			sendToCmpWh.setExpired("F");
			this.sendToCmpWhService.save(sendToCmpWh);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SendToCmpWh失败");
		}
	}
	
	/**
	 * 修改SendToCmpWh
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SendToCmpWh sendToCmpWh){
		logger.debug("修改SendToCmpWh开始");
		try{
			sendToCmpWh.setActionLog("EDIT");
			sendToCmpWh.setLastUpdate(new Date());
			sendToCmpWh.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.sendToCmpWhService.update(sendToCmpWh);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改SendToCmpWh失败");
		}
	}
	
	/**
	 * 删除SendToCmpWh
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除SendToCmpWh开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SendToCmpWh编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SendToCmpWh sendToCmpWh = sendToCmpWhService.get(SendToCmpWh.class, Integer.parseInt(deleteId));
				if(sendToCmpWh == null)
					continue;
				sendToCmpWh.setExpired("T");
				sendToCmpWhService.update(sendToCmpWh);
			}
		}
		logger.debug("删除SendToCmpWh IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *SendToCmpWh导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SendToCmpWh sendToCmpWh){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		sendToCmpWhService.findPageBySql(page, sendToCmpWh);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"发货到公司仓设置_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
