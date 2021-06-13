package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.WorkerProblemDao;
import com.house.home.entity.project.WorkerProblem;
import com.house.home.entity.project.WorkerProblemPic;
import com.house.home.service.project.WorkerProblemService;

@SuppressWarnings("serial")
@Service 
public class WorkerProblemServiceImpl extends BaseServiceImpl implements WorkerProblemService {
	@Autowired
	private  WorkerProblemDao workerProblemDao;

	@Override
	public WorkerProblemPic getByPhotoName(String id) {
		// TODO Auto-generated method stub
		return workerProblemDao.getByPhotoName(id);
	}

	@Override
	public void saveWorkerProblem(WorkerProblem workerProblem, String photoList) {
		if("A".equals(workerProblem.getM_umState())){
			workerProblemDao.save(workerProblem);
		}else{
			workerProblemDao.update(workerProblem);
		}
		if(StringUtils.isNotBlank(photoList)){
			String[] arr = photoList.split(",");
			if(!"A".equals(workerProblem.getM_umState())){
				workerProblemDao.deletePic(workerProblem.getNo());
			}
			for(String str:arr){
				WorkerProblemPic workerProblemPic=new WorkerProblemPic();
				workerProblemPic.setNo(workerProblem.getNo());
				workerProblemPic.setLastUpdate(new Date());
				workerProblemPic.setLastUpdatedBy(workerProblem.getLastUpdatedBy());
				workerProblemPic.setExpired("F");
				workerProblemPic.setActionLog("ADD");
				workerProblemPic.setPhotoName(str);
				workerProblemDao.save(workerProblemPic);
			}
		}
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkerProblem workerProblem) {
		return workerProblemDao.findPageBySql(page, workerProblem);
	}

	@Override
	public Page<Map<String, Object>> findPicPageBySql(Page<Map<String, Object>> page,
			WorkerProblem workerProblem) {
		return workerProblemDao.findPicPageBySql(page, workerProblem);
	}

	@Override
	public List<Map<String, Object>> getWorkerPicList(String no) {
		// TODO Auto-generated method stub
		return workerProblemDao.getWorkerPicList(no);
	}
	
	@Override
	public List<Map<String, Object>> getConfirmPicList(String no) {
		// TODO Auto-generated method stub
		return workerProblemDao.getConfirmPicList(no);
	}

	@Override
	public List<Map<String, Object>> findNoSendYun(){
		return workerProblemDao.findNoSendYun();
	}
	
	@Override
	public void doConfirm(WorkerProblem workerProblem) {
		workerProblemDao.doConfirm(workerProblem);
	}

	@Override
	public void doDeal(WorkerProblem workerProblem) {
		workerProblemDao.doDeal(workerProblem);
	}

	@Override
	public Map<String, Object> getWorkerProblemDetail(String no) {
		return workerProblemDao.getWorkerProblemDetail(no);
	}
}
