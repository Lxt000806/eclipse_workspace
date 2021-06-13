package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.InfoAttachDao;
import com.house.home.entity.basic.InfoAttach;
import com.house.home.service.basic.InfoAttachService;

@SuppressWarnings("serial")
@Service
public class InfoAttachServiceImpl extends BaseServiceImpl implements InfoAttachService {

	@Autowired
	private InfoAttachDao infoAttachDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InfoAttach infoAttach){
		return infoAttachDao.findPageBySql(page, infoAttach);
	}

	@Override
	public List<Map<String, Object>> getByInfoNum(String num) {
		return infoAttachDao.getByInfoNum(num);
	}

}
