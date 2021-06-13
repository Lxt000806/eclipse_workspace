package com.house.home.service.basic;

import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.SendMessage;

import java.util.List;
public interface SendMessageService extends BaseService {
	public List<SendMessage> getSendMessageListBySql(SendMessage sendMessage);
}
