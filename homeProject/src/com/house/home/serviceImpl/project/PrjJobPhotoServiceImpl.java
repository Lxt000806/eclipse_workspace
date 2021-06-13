package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.PrjJobPhotoDao;
import com.house.home.entity.project.PrjJobPhoto;
import com.house.home.service.project.PrjJobPhotoService;

@SuppressWarnings("serial")
@Service
public class PrjJobPhotoServiceImpl extends BaseServiceImpl implements PrjJobPhotoService {

	@Autowired
	private PrjJobPhotoDao prjJobPhotoDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjJobPhoto prjJobPhoto){
		return prjJobPhotoDao.findPageBySql(page, prjJobPhoto);
	}

	@Override
	public List<String> getPhotoListByPrjJobNo(String id, String type) {
		return prjJobPhotoDao.getPhotoListByPrjJobNo(id, type);
	}

	@Override
	public void deleteByPrjJobNo(String no, String photoName) {
		prjJobPhotoDao.deleteByPrjJobNo(no, photoName);
	}

}
