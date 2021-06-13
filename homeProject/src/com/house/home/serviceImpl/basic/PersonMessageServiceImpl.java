package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.service.basic.PersonMessageService;

@SuppressWarnings("serial")
@Service
public class PersonMessageServiceImpl extends BaseServiceImpl implements PersonMessageService {

	@Autowired
	private PersonMessageDao personMessageDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PersonMessage personMessage){
		return personMessageDao.findPageBySql(page, personMessage);
	}

	public List<Map<String, Object>> getEmployeePushList() {
		return personMessageDao.getEmployeePushList();
	}

	public long getMessageCount(PersonMessage personMessage) {
		return personMessageDao.getMessageCount(personMessage);
	}

	public void updateBatch(PersonMessage personMessage) {
		personMessageDao.updateBatch(personMessage);
	}

	public Page<Map<String, Object>> findPageBySqlForClient(
			Page<Map<String, Object>> page, PersonMessage personMessage) {
		return personMessageDao.findPageBySqlForClient(page, personMessage);
	}

	@Override
	public void updateEmployeePushList(String phone) {
		personMessageDao.updateEmployeePushList(phone);
	}

	@Override
	public long getNotConfirmedMessageCount(PersonMessage personMessage) {
		// TODO Auto-generated method stub
		return personMessageDao.getNotConfirmedMessageCount(personMessage);
	}

	@Override
	public List<Map<String, Object>> getProjectManReportList() {
		// TODO Auto-generated method stub
		return personMessageDao.getProjectManReportList();
	}

	public Page<Map<String, Object>> findPageBySqlForBS(
			Page<Map<String, Object>> page, PersonMessage personMessage) {
		return personMessageDao.findPageBySqlForBS(page, personMessage);
	}
	
	public Map<String, Object> getPersonMessage(String pk){
		return personMessageDao.getPersonMessage(pk);
	}

	@Override
	public Page<Map<String, Object>> getDelayExecList(
			Page<Map<String, Object>> page, Integer msgPk) {
		return personMessageDao.getDelayExecList(page, msgPk);
	}

	@Override
	public Map<String, Object> getMsgInfo(Integer pk) {
		return personMessageDao.getMsgInfo(pk);
	}

	@Override
	public PersonMessage getPersonMessageByCondition(PersonMessage personMessage){
		return personMessageDao.getPersonMessageByCondition(personMessage);
	}
	
	@Override
	public Boolean existsRelatedRecord(PersonMessage personMessage) {
		return personMessageDao.existsRelatedRecord(personMessage);
	}
}
