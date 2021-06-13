package com.house.home.web.controller.basic;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Ticket;
import com.house.home.service.basic.TicketService;

@Controller
@RequestMapping("/admin/ticket")
public class TicketController extends BaseController{

	@Autowired
	private TicketService ticketService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Ticket ticket) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		ticketService.findPageBySql(page, ticket);
		return new WebPage<Map<String,Object>>(page);

	}
	
	/**
	 *获取活动编号
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,Ticket ticket){
		
		return new ModelAndView("admin/basic/ticket/ticket_code").addObject("ticket",ticket);
	}
	
	/**
	 * 根据id查询activity详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getTicket")
	@ResponseBody
	public JSONObject getActivity(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Ticket ticket= ticketService.get(Ticket.class, id);
		if(ticket == null){
			return this.out("系统中不存在No="+id+"门票号", false);
		}
		return this.out(ticket, true);
	}
	
	
}
