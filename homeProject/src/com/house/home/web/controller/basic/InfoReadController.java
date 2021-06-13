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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.InfoRead;
import com.house.home.entity.basic.Information;
import com.house.home.service.basic.InfoReadService;
import com.house.home.service.basic.InformationService;

@Controller
@RequestMapping("/admin/infoRead")
public class InfoReadController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(InfoReadController.class);

	@Autowired
	private InfoReadService infoReadService;
	@Autowired
	private InformationService informationService;

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
			HttpServletResponse response, InfoRead infoRead) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		infoReadService.findPageBySql(page, infoRead);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * InfoRead列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/infoRead/infoRead_list");
	}
	/**
	 * InfoRead查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/infoRead/infoRead_code");
	}
	/**
	 * 跳转到新增InfoRead页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增InfoRead页面");
		InfoRead infoRead = null;
		if (StringUtils.isNotBlank(id)){
			infoRead = infoReadService.get(InfoRead.class, Integer.parseInt(id));
			infoRead.setPk(null);
		}else{
			infoRead = new InfoRead();
		}
		
		return new ModelAndView("admin/basic/infoRead/infoRead_save")
			.addObject("infoRead", infoRead);
	}
	/**
	 * 跳转到修改InfoRead页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改InfoRead页面");
		InfoRead infoRead = null;
		if (StringUtils.isNotBlank(id)){
			infoRead = infoReadService.get(InfoRead.class, Integer.parseInt(id));
		}else{
			infoRead = new InfoRead();
		}
		
		return new ModelAndView("admin/basic/infoRead/infoRead_update")
			.addObject("infoRead", infoRead);
	}
	
	/**
	 * 跳转到查看InfoRead页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看InfoRead页面");
		InfoRead infoRead = infoReadService.get(InfoRead.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/infoRead/infoRead_detail")
				.addObject("infoRead", infoRead);
	}
	/**
	 * 添加InfoRead
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, InfoRead infoRead){
		logger.debug("添加InfoRead开始");
		try{
			this.infoReadService.save(infoRead);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加InfoRead失败");
		}
	}
	
	/**
	 * 修改InfoRead
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, InfoRead infoRead){
		logger.debug("修改InfoRead开始");
		try{
			infoRead.setLastUpdate(new Date());
			infoRead.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.infoReadService.update(infoRead);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改InfoRead失败");
		}
	}
	
	/**
	 * 修改InfoRead状态
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateStatus")
	public void doUpdateStatus(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("修改InfoRead状态开始");
		try{
			Information information = infoReadService.get(Information.class, id);
			if("1".equals(information.getInfoType())){
				InfoRead infoRead = new InfoRead();
				infoRead.setInfoNum(id);
				infoRead.setRcvCzy(this.getUserContext(request).getEmnum());
				infoRead.setStatus("1");
				infoRead.setLastUpdate(new Date());
				infoRead.setLastUpdatedBy(getUserContext(request).getCzybh());
				infoRead.setExpired("F");
				infoRead.setActionLog("ADD");
				infoReadService.save(infoRead);
			}else{
				InfoRead infoRead = new InfoRead();
				infoRead.setInfoNum(id);
				infoRead.setStatus("1");
				infoRead.setRcvCzy(this.getUserContext(request).getEmnum());
				infoRead.setLastUpdatedBy(getUserContext(request).getCzybh());
				Result result = infoReadService.updateForProc(infoRead);
				if ("1".equals(result.getCode())){
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改InfoRead状态失败");
		}
	}
	/**
	 * 获取未读数
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/getUnReadNum")
	public void getUnReadNum(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("获取未读数开始");
		try{
			Information info = new Information();
			info.setInfoType("2");
			info.setStatus("3");
			info.setReadStatus("0");
			info.setRcvCzy(this.getUserContext(request).getEmnum());
			Long unReadNum = informationService.getCount(info);
			ServletUtils.outPrintSuccess(request, response, String.valueOf(unReadNum));
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "获取未读数开始失败");
		}
	}
	
	/**
	 * 删除InfoRead
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除InfoRead开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "InfoRead编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				InfoRead infoRead = infoReadService.get(InfoRead.class, Integer.parseInt(deleteId));
				if(infoRead == null)
					continue;
				infoRead.setExpired("T");
				infoReadService.update(infoRead);
			}
		}
		logger.debug("删除InfoRead IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *InfoRead导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, InfoRead infoRead){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		infoReadService.findPageBySql(page, infoRead);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"InfoRead_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
