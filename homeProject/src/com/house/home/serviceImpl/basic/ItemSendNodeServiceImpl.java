package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemSendNodeDao;
import com.house.home.entity.basic.ItemSendNode;
import com.house.home.service.basic.ItemSendNodeService;

@SuppressWarnings("serial")
@Service
public class ItemSendNodeServiceImpl extends BaseServiceImpl implements ItemSendNodeService {

	@Autowired
	private ItemSendNodeDao itemSendNodeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSendNode itemSendNode){
		return itemSendNodeDao.findPageBySql(page, itemSendNode);
	}
	
	
	public Page<Map<String,Object>> findPageBySqlForDetail(Page<Map<String,Object>> page, ItemSendNode itemSendNode){
		return itemSendNodeDao.findPageBySqlForDetail(page, itemSendNode);
	}
	
	public Result saveForProc(ItemSendNode itemSendNode, String xml){
		return itemSendNodeDao.saveForProc(itemSendNode,xml);
	}

}
