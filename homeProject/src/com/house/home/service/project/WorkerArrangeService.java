package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.WorkerArrangeEvt;
import com.house.home.entity.project.Worker;
import com.house.home.entity.project.WorkerArrange;

/**
 * 
 * @author 张海洋
 *
 */

public interface WorkerArrangeService extends BaseService {

    public Page<Map<String, Object>> getOrderWorkType12List(Page<Map<String, Object>> page);
    
    public Page<Map<String, Object>> getWorkerArrangeList(Page<Map<String, Object>> page, WorkerArrangeEvt evt);
    
    public Page<Map<String, Object>> getOrderNoList(Page<Map<String, Object>> page, WorkerArrangeEvt evt);
    
    public Page<Map<String, Object>> getAddresses(Page<Map<String, Object>> page, WorkerArrangeEvt evt);
    
    public Page<Map<String, Object>> getOrderWorkerDetailList(Page<Map<String, Object>> page, WorkerArrangeEvt evt);
    
    public Page<Map<String, Object>> getOrderedList(Page<Map<String, Object>> page, WorkerArrangeEvt evt);
        
	Page<Map<String,Object>> findPageBySqlList(Page<Map<String, Object>> page, WorkerArrange workerArrange,
			UserContext userContext);

	Result doReturn(HttpServletRequest request, HttpServletResponse response, UserContext userContext, Integer pk);

	Result doOrder(UserContext userContext, WorkerArrange workerArrange);

	WorkerArrange getWorkerArrangeWithInfo(Integer id);

	Result doBatchArrange(HttpServletRequest request,
			HttpServletResponse response, WorkerArrange workerArrange,
			UserContext userContext);

	Result doBatchDel(HttpServletRequest request, HttpServletResponse response, String pks);

	List<Map<String, Object>> getWorkerArrangeByCustWorkPk(Integer custWorkPk);
	
	Boolean existsWorkType12OnCustCode(String workType12, String custCode);
}
