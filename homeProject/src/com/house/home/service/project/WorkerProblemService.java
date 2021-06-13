package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.WorkerProblem;
import com.house.home.entity.project.WorkerProblemPic;



public interface WorkerProblemService extends BaseService{
	
	
	public WorkerProblemPic getByPhotoName(String id);

	public void saveWorkerProblem(WorkerProblem workerProblem,String photoList);

	/**
	 * workerProblem分页信息
	 * @param page
	 * @param workerProblem
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
			WorkerProblem workerProblem);

	/**
	 * 获取Pic分页信息
	 * @param page
	 * @param workerProblem
	 */
	public Page<Map<String, Object>> findPicPageBySql(Page<Map<String, Object>> page,
			WorkerProblem workerProblem);
	
	
	public List<Map<String, Object>> getWorkerPicList(String no);
	
	public List<Map<String, Object>> getConfirmPicList(String no);
	
	public List<Map<String, Object>> findNoSendYun();
	
	/**
	 * 确认
	 * @param workerProblem
	 */
	public void doConfirm(WorkerProblem workerProblem);
	/**
	 * 处理
	 * @param workerProblem
	 */
	public void doDeal(WorkerProblem workerProblem);
	/**
	 * 根据No查工人问题明细
	 * @author cjg
	 * @date 2019-11-15
	 * @param workerProblem
	 * @return
	 */
	public Map<String, Object> getWorkerProblemDetail(String no);
}
