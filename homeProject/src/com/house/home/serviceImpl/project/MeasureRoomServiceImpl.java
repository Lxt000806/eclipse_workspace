package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.MeasureRoomEvt;
import com.house.home.dao.project.MeasureRoomDao;
import com.house.home.service.project.MeasureRoomService;


@SuppressWarnings("serial")
@Service
public class MeasureRoomServiceImpl extends BaseServiceImpl implements MeasureRoomService{

	@Autowired
	private MeasureRoomDao measureRoomDao;
	
	@Override
	public Page<Map<String, Object>> getMeasureRoomList(
			Page<Map<String, Object>> page, MeasureRoomEvt evt) {
		return measureRoomDao.getMeasureRoomList(page,evt);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_custRight(
			Page<Map<String, Object>> page, MeasureRoomEvt evt) {
		return measureRoomDao.findPageBySql_custRight(page,evt);
	}

	@Override
	public List<Map<String, Object>> getMeasureRoomPhotoList(MeasureRoomEvt evt) {
		return measureRoomDao.getMeasureRoomPhotoList(evt);
	}
	
	
}
