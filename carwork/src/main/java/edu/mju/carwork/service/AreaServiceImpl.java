package edu.mju.carwork.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.carwork.dao.AreaDao;
import edu.mju.carwork.domain.Area;
import edu.mju.carwork.utils.Page;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao = null;
	
	@Override
	public void addArea(Area area) {
		areaDao.addArea(area);
	}

	@Override
	public List<Area> loadArea() {
		// TODO Auto-generated method stub
		return areaDao.loadArea();
	}

	@Override
	public void delArea(int areaNo) {
		// TODO Auto-generated method stub
		areaDao.delArea(areaNo);
	}

	@Override
	public Area getAreaByNo(int areaNo) {
		// TODO Auto-generated method stub
		return areaDao.getAreaByNo(areaNo);
	}

	@Override
	public void updateArea(Area area) {
		areaDao.updateArea(area);

	}

	@Override
	public byte[] getAreaPicByNo(int areaNo) {
        Map picMap = areaDao.getAreaPicByNo(areaNo);
		
		if(picMap!=null){
			return (byte[])picMap.get("imgBytes");
		}
		
		return null;
	}

	@Override
	public List<Area> loadAreaByCondition(AreaQueryHelper helper) {
		// TODO Auto-generated method stub
		return areaDao.loadAreaByCondition(helper);
	}

	@Override
	public Page loadPagedArea(AreaQueryHelper helper, Page page) {
		page.setTotalRecNum(areaDao.cntAreaByCondition(helper));
		page.setPageContent(areaDao.loadScopedAreaByCondition(helper, page.getStartIndex(), page.getPageSize()));
		
		return page;
	}

}
