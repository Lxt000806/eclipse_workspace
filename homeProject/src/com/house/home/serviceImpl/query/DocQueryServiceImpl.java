package com.house.home.serviceImpl.query;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.DocQueryDao;
import com.house.home.entity.basic.Doc;
import com.house.home.service.query.DocQuerySerivce;

@Service
@SuppressWarnings("serial")
public class DocQueryServiceImpl extends BaseServiceImpl implements DocQuerySerivce {
	
	@Autowired
	private DocQueryDao docQueryDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Doc doc) {
		
		return docQueryDao.findPageBySql(page, doc);
	}
	
	@Override
	public List<Map<String, Object>> getAuthNode(String czybm, boolean hasAuth) {
		
		return docQueryDao.getAuthNodeList(czybm, hasAuth);
	}
}
