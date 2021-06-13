package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.PrjProgPhotoDao;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.service.project.PrjProgPhotoService;

@SuppressWarnings("serial")
@Service
public class PrjProgPhotoServiceImpl extends BaseServiceImpl implements PrjProgPhotoService {

	@Autowired
	private PrjProgPhotoDao prjProgPhotoDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto){
		//prjProgPhotoDao.updatePhotoPustStatus(prjProgPhoto);
		
		return prjProgPhotoDao.findPageBySql(page, prjProgPhoto);
	}
	
	public Page<Map<String,Object>> findPrjProgPhotoPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto){
		return prjProgPhotoDao.findPrjProgPhotoPageBySql(page, prjProgPhoto);
	}
	
	
	public Page<Map<String,Object>> findConstructionPicturePageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto){
		return prjProgPhotoDao.findConstructionPicturePageBySql(page, prjProgPhoto);
	}
	
	public Page<Map<String,Object>> findPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto){
		return prjProgPhotoDao.findPrjPageBySql(page, prjProgPhoto);
	}
	
	public Page<Map<String,Object>> findXjPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto){
		return prjProgPhotoDao.findXjPrjPageBySql(page, prjProgPhoto);
	}
	
	public Page<Map<String,Object>> findYsPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto){
		return prjProgPhotoDao.findYsPrjPageBySql(page, prjProgPhoto);
	}

	public List<Map<String,Object>> getPhotoList(String custCode, String prjItem) {
		return prjProgPhotoDao.getPhotoList(custCode,prjItem);
	}

	@Override
	public List<String> getPhotoListByTypeAndRefNo(String type, String no) {
		return prjProgPhotoDao.getPhotoListByTypeAndRefNo(type,no);
	}

	@Override
	public List<String> getPhotoListByRefNo(String id,String type) {
		return prjProgPhotoDao.getPhotoListByRefNo(id,type);
	}

	@Override
	public PrjProgPhoto getByPhotoName(String id) {
		// TODO Auto-generated method stub
		return prjProgPhotoDao.getByPhotoName(id);
	}

	@Override
	public void updatePhotoPustStatus(PrjProgPhoto prjProgPhoto){
		
		prjProgPhotoDao.updatePhotoPustStatus(prjProgPhoto);
	}
	
}
