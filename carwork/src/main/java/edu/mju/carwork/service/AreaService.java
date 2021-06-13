package edu.mju.carwork.service;

import java.util.List;

import edu.mju.carwork.domain.Area;
import edu.mju.carwork.utils.Page;

public interface AreaService {
	void addArea(Area area);
	List<Area> loadArea();
	List<Area> loadAreaByCondition(AreaQueryHelper helper);
	void delArea(int areaNo);
	Area getAreaByNo(int areaNo);
	void updateArea(Area area);
	byte[] getAreaPicByNo(int areaNo);
	Page loadPagedArea(AreaQueryHelper helper,Page page);
}
