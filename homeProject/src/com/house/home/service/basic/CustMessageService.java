package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.DoUpdateCustMsgStatusEvt;
import com.house.home.entity.basic.CustMessage;

public interface CustMessageService extends BaseService {

	public Page<Map<String,Object>> getCustMessageList(Page<Map<String,Object>> page, CustMessage custMessage);
	
	public Map<String, Object> doUpdateCustMessageStatuc(DoUpdateCustMsgStatusEvt evt);
	
	public List<Map<String, Object>> getCustomerMessagePushList();
	
	public void updateCustomerMessagePushStatus(String phone);
}
