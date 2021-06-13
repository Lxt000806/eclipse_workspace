package com.house.home.serviceImpl.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.basic.SendMessageDao;
import com.house.home.entity.basic.SendMessage;
import com.house.home.service.basic.SendMessageService;
@Service
public class SendMessageServiceImpl extends BaseServiceImpl implements
		SendMessageService {
	@Autowired
	private SendMessageDao sendMessageDao;
	@Override
	public List<SendMessage> getSendMessageListBySql(SendMessage sendMessage) {
		// TODO Auto-generated method stub
		return sendMessageDao.getSendMessageListBySql(sendMessage);
	}

}
