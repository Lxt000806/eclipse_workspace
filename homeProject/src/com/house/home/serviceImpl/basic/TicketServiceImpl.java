package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.TicketDao;
import com.house.home.entity.basic.Ticket;
import com.house.home.service.basic.TicketService;

@SuppressWarnings("serial")
@Service
public class TicketServiceImpl extends BaseServiceImpl implements TicketService {

	@Autowired
	private TicketDao ticketDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Ticket ticket){
		return ticketDao.findPageBySql(page, ticket);
	}

	@Override
	public Page<Map<String, Object>> findTicketByAct(
			Page<Map<String, Object>> page, String actNo, String status,String businessMan,String custName,String ticketNo,boolean isSign) {
		// TODO Auto-generated method stub
		return ticketDao.findTicketByAct(page,actNo,status,businessMan,custName,ticketNo,isSign);
	}

	@Override
	public Map<String, Object> findTicketByActAndNo(String actNo,String ticketNo) {
		// TODO Auto-generated method stub
		return ticketDao.findTicketByActAndNo(actNo,ticketNo) ;
	}

	@Override
	public boolean existsTic(String no) {
		// TODO Auto-generated method stub
		return ticketDao.existsTic(no);
	}

	
	
}

