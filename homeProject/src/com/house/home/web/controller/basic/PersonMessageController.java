package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.service.basic.PersonMessageService;

@Controller
@RequestMapping("/admin/personMessage")
public class PersonMessageController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PersonMessageController.class);

	@Autowired
	private PersonMessageService personMessageService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,PersonMessage personMessage) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		personMessageService.findPageBySqlForBS(page, personMessage);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PersonMessage列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/personMessage/personMessage_list");
	}
	
	/**
	 * 我的主页查看个人消息主页
	 */
	@RequestMapping("/goListFromHome")
	public ModelAndView goListFromHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PersonMessage personMessage = new PersonMessage();
		String czybh = getUserContext(request).getCzybh();
		personMessage.setRcvCzy(czybh);
		personMessage.setRcvType("3");
		Czybm czybm = this.personMessageService.get(Czybm.class, czybh);
		if(czybm != null){
			personMessage.setRcvCzyDescr(czybm.getZwxm());
		}
		return new ModelAndView("admin/basic/personMessage/personMessage_list_home")
				.addObject("personMessage", personMessage);
	}
	/**
	 * 跳转到新增PersonMessage页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PersonMessage页面");
		PersonMessage personMessage = null;
		if (StringUtils.isNotBlank(id)){
			personMessage = personMessageService.get(PersonMessage.class, Integer.parseInt(id));
			personMessage.setPk(null);
		}else{
			personMessage = new PersonMessage();
		}
		
		return new ModelAndView("admin/basic/personMessage/personMessage_save")
			.addObject("personMessage", personMessage);
	}
	/**
	 * 跳转到修改PersonMessage页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PersonMessage页面");
		PersonMessage personMessage = null;
		if (StringUtils.isNotBlank(id)){
			personMessage = personMessageService.get(PersonMessage.class, Integer.parseInt(id));
		}else{
			personMessage = new PersonMessage();
		}
		
		return new ModelAndView("admin/basic/personMessage/personMessage_update")
			.addObject("personMessage", personMessage);
	}
	
	/**
	 * 跳转到查看PersonMessage页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String pk){
		logger.debug("跳转到查看PersonMessage页面");
		Map<String,Object> map = personMessageService.getPersonMessage(pk);
		PersonMessage personMessage = new PersonMessage();
		BeanConvertUtil.mapToBean(map, personMessage);
		return new ModelAndView("admin/basic/personMessage/personMessage_view")
				.addObject("personMessage", personMessage);
	}
	/**
	 * 我的主页查看消息详情
	 */
	@RequestMapping("/goViewFromHome")
	public ModelAndView goViewFromHome(HttpServletRequest request, HttpServletResponse response, 
			String pk){
		logger.debug("跳转到查看PersonMessage页面");
		Map<String,Object> map = personMessageService.getPersonMessage(pk);
		PersonMessage personMessage = new PersonMessage();
		BeanConvertUtil.mapToBean(map, personMessage);
		return new ModelAndView("admin/basic/personMessage/personMessage_view_home")
				.addObject("personMessage", personMessage);
	}
	/**
	 * 添加PersonMessage
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PersonMessage personMessage){
		logger.debug("添加PersonMessage开始");
		try{
			PersonMessage xt = this.personMessageService.get(PersonMessage.class, personMessage.getPk());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "PersonMessage重复");
				return;
			}
			this.personMessageService.save(personMessage);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PersonMessage失败");
		}
	}
	
	/**
	 * 修改PersonMessage
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PersonMessage personMessage){
		logger.debug("修改PersonMessage开始");
		try{
			PersonMessage xt = this.personMessageService.get(PersonMessage.class, personMessage.getPk());
			if (xt!=null){
				this.personMessageService.update(personMessage);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.personMessageService.save(personMessage);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PersonMessage失败");
		}
	}

	/**
	 * 消息已读接口
	 */
	@RequestMapping("/doRcv")
	public void doRcv(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("修改PersonMessage开始");
		try{
			PersonMessage xt = this.personMessageService.get(PersonMessage.class, pk);
			if (xt!=null){
				xt.setRcvStatus("1");
				xt.setRcvCzy(getUserContext(request).getCzybh().trim());
				xt.setRcvDate(new Date());
				this.personMessageService.update(xt);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, "修改PersonMessage失败");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PersonMessage失败");
		}
	}
	
	/**
	 * 删除PersonMessage
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PersonMessage开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PersonMessage编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PersonMessage personMessage = personMessageService.get(PersonMessage.class, Integer.parseInt(deleteId));
				if(personMessage == null)
					continue;
//				personMessage.setExpired("T");
				personMessageService.delete(personMessage);
			}
		}
		logger.debug("删除PersonMessage IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PersonMessage导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PersonMessage personMessage){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		personMessageService.findPageBySqlForBS(page, personMessage);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"个人消息管理表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
