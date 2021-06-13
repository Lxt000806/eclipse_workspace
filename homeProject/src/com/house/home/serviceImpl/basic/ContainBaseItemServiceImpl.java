package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ContainBaseItemDao;
import com.house.home.entity.basic.ContainBaseItem;
import com.house.home.service.basic.ContainBaseItemService;

@SuppressWarnings("serial")
@Service
public class ContainBaseItemServiceImpl extends BaseServiceImpl implements ContainBaseItemService {

	@Autowired
	private ContainBaseItemDao containBaseItemDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ContainBaseItem containBaseItem){
		return containBaseItemDao.findPageBySql(page, containBaseItem);
	}

}
