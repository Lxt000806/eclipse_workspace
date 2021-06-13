package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Ticket;

public interface TicketService extends BaseService {

	/**Ticket分页信息
	 * @param page
	 * @param ticket
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Ticket ticket);
	/**
	 * 根据活动名称和状态查询客户信息
	 * @param page
	 * @param actNo
	 * @param status
	 * @return
	 */
	public Page<Map<String,Object>> findTicketByAct(Page<Map<String,Object>> page, String actNo,String status,String businessMan,String custName,String ticketNo,boolean isSign);
	/**
	 * 根据活动名称和票号查询门票信息
	 * @param page
	 * @param actNo
	 * @param status
	 * @return
	 */
	public Map<String,Object> findTicketByActAndNo(String actNo,String ticketNo);
	
	
	public boolean existsTic(String no);
}
