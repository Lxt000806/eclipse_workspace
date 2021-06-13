package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.MeasureRoomEvt;

public interface MeasureRoomService extends BaseService{
	
	public Page<Map<String, Object>> getMeasureRoomList(Page<Map<String, Object>> page, MeasureRoomEvt evt);
	
	public Page<Map<String, Object>> findPageBySql_custRight(Page<Map<String, Object>> page, MeasureRoomEvt evt);
	
	public List<Map<String, Object>> getMeasureRoomPhotoList(MeasureRoomEvt evt);
}
