package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.home.entity.basic.SendMessage;
@SuppressWarnings("serial")
@Repository
public class SendMessageDao extends BaseDao {

	public List<SendMessage> getSendMessageListBySql(SendMessage sendMessage) {
		List<Object> list=new ArrayList<Object>();
		String sql=" from SendMessage where 1=1 ";
		if(StringUtils.isNotBlank(sendMessage.getSendStatus())){
			sql+=" and sendStatus=? ";
			list.add(sendMessage.getSendStatus());
		}
		if(StringUtils.isNotBlank(sendMessage.getMsgType())){
			sql+=" and msgType=? ";
			list.add(sendMessage.getMsgType());
		}
		return this.find(sql, list.toArray());
	}

}
