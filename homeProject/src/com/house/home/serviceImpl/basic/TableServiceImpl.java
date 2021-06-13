package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.basic.TableDao;
import com.house.home.entity.basic.Table;
import com.house.home.service.basic.TableService;

@SuppressWarnings("serial")
@Service
public class TableServiceImpl extends BaseServiceImpl implements TableService {

	@Autowired
	private TableDao tableDao;
	
	public List<Map<String,Object>> getColumns(Table table){
		return tableDao.getColumns(table);
	}

	@Override
	public List<Map<String, Object>> getColModel(Table table) {
		return tableDao.getColModel(table);
	}

	@Override
	public Map<String, Object> getTableCzyMapper(Table table) {
		return tableDao.getTableCzyMapper(table);
	}

	@Override
	public Result doSave(Table table) {
		return tableDao.doSave(table);
	}

}
