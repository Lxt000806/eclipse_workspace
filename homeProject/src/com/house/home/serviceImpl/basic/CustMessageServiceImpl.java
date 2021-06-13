package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.DoUpdateCustMsgStatusEvt;
import com.house.home.dao.basic.CustMessageDao;
import com.house.home.entity.basic.CustMessage;
import com.house.home.service.basic.CustMessageService;

@SuppressWarnings("serial")
@Service
public class CustMessageServiceImpl extends BaseServiceImpl implements CustMessageService {

	@Autowired
	private CustMessageDao custMessageDao;

	@Override
	public Page<Map<String,Object>> getCustMessageList(Page<Map<String,Object>> page, CustMessage custMessage){
		return custMessageDao.getCustMessageList(page, custMessage);
	}
	
	@Override
	public Map<String, Object> doUpdateCustMessageStatuc(DoUpdateCustMsgStatusEvt evt){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if(evt.getPk() == 0 || evt.getPk() == null){
			returnMap.put("returnCode", "400001");
			returnMap.put("returnInfo", "标记失败,请刷新后再试");
			return returnMap;
		}
		
		CustMessage custMessage = this.custMessageDao.get(CustMessage.class, evt.getPk());
		
		if(custMessage == null){
			returnMap.put("returnCode", "400001");
			returnMap.put("returnInfo", "消息不存在,请联系客服");
			return returnMap;
		}
		if("1".equals(custMessage.getRcvStatus().trim())){
			returnMap.put("returnCode", "400001");
			returnMap.put("returnInfo", "此消息已经是已读,请刷新列表查看");
			return returnMap;
		}
		
		custMessage.setRcvStatus("1");
		custMessage.setRcvDate(new Date());
		this.custMessageDao.update(custMessage);
		returnMap.put("returnCode", "000000");
		returnMap.put("returnInfo", "操作成功");
		return returnMap;
	}
	
	@Override
	public List<Map<String, Object>> getCustomerMessagePushList(){
		return custMessageDao.getCustomerMessagePushList();
	}

	@Override
	public void updateCustomerMessagePushStatus(String phone){
		custMessageDao.updateCustomerMessagePushStatus(phone);
	}
}
