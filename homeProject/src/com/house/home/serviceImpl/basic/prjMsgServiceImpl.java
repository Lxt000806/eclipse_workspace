package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.prjMsgDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.service.basic.prjMsgService;

@SuppressWarnings("serial")
@Service
public class prjMsgServiceImpl extends BaseServiceImpl implements prjMsgService {

	@Autowired
	private prjMsgDao prjjobmessageDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PersonMessage personMessage,boolean hasSelfRight,boolean hasDeptRight,boolean hasAllRight) {
		return prjjobmessageDao.findPageBySql(page, personMessage,hasSelfRight,hasDeptRight,hasAllRight);
	}
	
	@Override
	public Page<Map<String, Object>> goJqGridByWorkType12(
			Page<Map<String, Object>> page, PersonMessage personMessage) {
		return prjjobmessageDao.goJqGridByWorkType12(page, personMessage);
	}
	
	@Override
	public Map<String, Object> getAddress(int PK) {
		return prjjobmessageDao.getByPk(PK);
	}

	@Override
	public Result doSave(PersonMessage personMessage) {
		return prjjobmessageDao.doReturnCheckOut(personMessage);
	}

	@Override
	public Map<String, Object> getMaxAuthId(String czybh) {
		return prjjobmessageDao.getMaxAuthId(czybh);
	}

	@Override
	public Page<Map<String, Object>> goReasonJqGrid(
			Page<Map<String, Object>> page, PersonMessage personMessage) {
		return prjjobmessageDao.goReasonJqGrid(page, personMessage);
	}
	
}
 
