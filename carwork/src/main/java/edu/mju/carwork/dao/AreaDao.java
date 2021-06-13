package edu.mju.carwork.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.mju.carwork.domain.Area;
import edu.mju.carwork.domain.Car;
import edu.mju.carwork.service.AreaQueryHelper;
import edu.mju.carwork.service.CarQueryHelper;

public interface AreaDao {
	
	void addArea(Area area);
	List<Area> loadArea();
	List<Area> loadAreaByCondition(AreaQueryHelper helper);
	void delArea(int areaNo);
	Area getAreaByNo(int areaNo);
	void updateArea(Area area);
	Map getAreaPicByNo(int areaNo);
	
	/**
	    * ��ѯ��ĳ��ѯ��������£��ܹ��ļ�¼����
	    * @param helper
	    * @return
	    */
		long cntAreaByCondition(AreaQueryHelper helper);
		List<Car> loadScopedAreaByCondition(@Param("helper") AreaQueryHelper helper, 
										    @Param("begin") int beginIdx, 
										    @Param("size") int pageSize);
}
