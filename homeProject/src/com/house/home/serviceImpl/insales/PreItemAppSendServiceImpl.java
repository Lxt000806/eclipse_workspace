package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.insales.PreItemAppSendDao;
import com.house.home.entity.insales.PreItemAppSend;
import com.house.home.service.insales.PreItemAppSendService;

@SuppressWarnings("serial")
@Service
public class PreItemAppSendServiceImpl extends BaseServiceImpl implements PreItemAppSendService {

	@Autowired
	private PreItemAppSendDao preItemAppSendDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, 
			PreItemAppSend preItemAppSend, UserContext uc){
		return preItemAppSendDao.findPageBySql(page, preItemAppSend,uc);
	}

	@Override
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, PreItemAppSend preItemAppSend) {
		return preItemAppSendDao.findItemPageBySql(page, preItemAppSend);
	}

	@Override
	public Result doSave(PreItemAppSend preItemAppSend) {
		return preItemAppSendDao.doSave(preItemAppSend);
	}

	@Override
	public Result doCkfhsqSend(PreItemAppSend preItemAppSend) {
		return preItemAppSendDao.doCkfhsqSend(preItemAppSend);
	}

	@Override
	public List<Map<String, Object>> isHaveSend(PreItemAppSend preItemAppSend) {
		return preItemAppSendDao.isHaveSend(preItemAppSend);
	}

	@Override
	public Result doCkfhsqSendByPart(PreItemAppSend preItemAppSend) {
		return preItemAppSendDao.doCkfhsqSendByPart(preItemAppSend);
	}

	@Override
	public Page<Map<String, Object>> findWarehouseSendList(
			Page<Map<String, Object>> page, String id, String address,
			String itemType1, UserContext uc) {
		return preItemAppSendDao.findWarehouseSendList(page, id, address, itemType1, uc);
	}

}
