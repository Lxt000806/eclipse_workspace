package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.CustWorkerDao;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.WorkSign;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.project.Worker;
import com.house.home.service.project.CustWorkerService;

@SuppressWarnings("serial")
@Service
public class CustWorkerServiceImpl extends BaseServiceImpl implements CustWorkerService{
	
	@Autowired
	private CustWorkerDao custWorkerDao;
	
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustWorker custWorker,String czybh, UserContext uc) {
		return custWorkerDao.findPageBySql(page, custWorker,czybh, uc);
	}

	@Override
	public Page<Map<String, Object>> findViewSignPageBySql(
			Page<Map<String, Object>> page, CustWorker custWorker, String czybh) {
		return custWorkerDao.findViewSignPageBySql(page, custWorker,czybh);
	}

	@Override
	public Map<String,Object> getPk(String actionLog){
		return custWorkerDao.getPk(actionLog);
	
	}

	@Override
	public CustWorker getByCode(String custCode, String workType12,
			String workerCode) {

		return custWorkerDao.getByCode(workerCode, workerCode, workerCode);
	}

	@Override
	public void doDelWorkerCode(String custWorkPk) {
		custWorkerDao.doDelWorkerCode(custWorkPk);
	}

	@Override 
	public Page<Map<String, Object>> findWorkerDetailPageBySql(
			Page<Map<String, Object>> page, CustWorker custWorker) {
		return custWorkerDao.findWorkerDetailPageBySql(page, custWorker);
	}

	@Override
	public Page<Map<String, Object>> getWorkSignPicBySql(
			Page<Map<String, Object>> page, CustWorker custWorker) {
		return custWorkerDao.getWorkSignPicBySql(page, custWorker);
	}

	@Override
	public boolean getIsExistsWorkerArr(CustWorker custWorker) {
		return custWorkerDao.getIsExistsWorkerArr(custWorker);
	}

	@Override
	public Page<Map<String, Object>> findPageByCustCode(
			Page<Map<String, Object>> page, String custCode) {
		return custWorkerDao.findPageByCustCode(page,custCode);
	}

	@Override
	public void updateIsPushCust(CustWorker custWorker) {
		if("1".equals(custWorker.getIsPushCust())){
			custWorker.setIsPushCust("0");
		}else{
			custWorker.setIsPushCust("1");
		}
		custWorkerDao.updateIsPushCust(custWorker);
	}

	@Override
	public void updateIsPushCustAll(CustWorker custWorker) {
		custWorkerDao.updateIsPushCustAll(custWorker);
	}
	
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String,Object>> page, CustWorker custWorker){
		return custWorkerDao.findCodePageBySql(page,custWorker);
	}
	
	public List<Map<String, Object>> getWorkType12Dept(String workType12){
		return custWorkerDao.getWorkType12Dept(workType12);
	}
	
	public Page<Map<String,Object>> getWorkType12List(Page<Map<String,Object>> page,String czybh){
		return custWorkerDao.getWorkType12List(page,czybh);
	}
	
	public Page<Map<String,Object>> getPrjRegionList(Page<Map<String,Object>> page){
		return custWorkerDao.getPrjRegionList(page);
	}
	
	public Page<Map<String,Object>> getWorkerList(Page<Map<String,Object>> page,Worker worker){
		return custWorkerDao.getWorkerList(page, worker);
	}
	
	public Page<Map<String,Object>> getCustWorkerList(Page<Map<String,Object>> page,CustWorker custWorker){
		return custWorkerDao.getCustWorkerList(page, custWorker);
	}
	
	public Page<Map<String,Object>> getCustWorkerAppList(Page<Map<String,Object>> page,CustWorker custWorker){
		return custWorkerDao.getCustWorkerAppList(page,custWorker);
	}
	
	public Page<Map<String,Object>> getAllCustomerList(Page<Map<String,Object>> page,CustWorker custWorker){
		return custWorkerDao.getAllCustomerList(page,custWorker);
	}
	
	public Page<Map<String,Object>> getDepartment2List(Page<Map<String,Object>> page){
		return custWorkerDao.getDepartment2List(page);
	}

	@Override
	public Page<Map<String, Object>> findWorkDaysPageByCustCode(
			Page<Map<String, Object>> page, String custCode) {
		return custWorkerDao.findWorkDaysPageByCustCode(page, custCode);
	}

	@Override
	public void updateBeginDateByWorkType12(CustWorker custWorker) {
		WorkType12 workType12 = this.get(WorkType12.class, custWorker.getWorkType12());
		if (StringUtils.isNotBlank(workType12.getBeginPrjItem()) && StringUtils.isNotBlank(custWorker.getCustCode()) &&
				null != custWorker.getComeDate()) {
			custWorkerDao.getPrjProg(custWorker.getCustCode(),workType12.getBeginPrjItem(),custWorker.getComeDate());
		}
	}

	@Override
	public Page<Map<String, Object>> findWaterAftInsItemAppPageBySql(
			Page<Map<String, Object>> page, CustWorker custWorker) {
		return custWorkerDao.findWaterAftInsItemAppPageBySql(page,custWorker);
	}

    @Override
    public Result doComplete(HttpServletRequest request,
            HttpServletResponse response, Integer workSignPk,
            UserContext userContext) {
        
        if (workSignPk == null) {
            return new Result(Result.FAIL_CODE, "设置完成失败， 工人签到主键为空");
        }
        
        WorkSign workSign = get(WorkSign.class, workSignPk);
        workSign.setIsComplete("1");
        update(workSign);
        
        return new Result(Result.SUCCESS_CODE, "设置完成成功");
    }

    @Override
    public Result undoComplete(HttpServletRequest request,
            HttpServletResponse response, Integer workSignPk,
            UserContext userContext) {

        if (workSignPk == null) {
            return new Result(Result.FAIL_CODE, "设置未完成失败， 工人签到主键为空");
        }
        
        WorkSign workSign = get(WorkSign.class, workSignPk);
        workSign.setIsComplete("0");
        update(workSign);
        
        return new Result(Result.SUCCESS_CODE, "设置未完成成功");
    }

	@Override
	public Page<Map<String, Object>> goLogJqGrid(Page<Map<String, Object>> page, CustWorker custWorker) {
		return custWorkerDao.goLogJqGrid(page, custWorker);
	}
    
}
