package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemPreApp;
import com.house.home.entity.project.PrjJob;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.entity.project.SupplJob;

public interface PrjJobService extends BaseService {

	/**PrjJob分页信息
	 * @param page
	 * @param prjJob
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjJob prjJob);

	public Page<Map<String,Object>> findPageBySql_forClient(Page<Map<String,Object>> page, PrjJob prjJob);
	public Page<Map<String,Object>> getDealPrjJobList(Page<Map<String,Object>> page, PrjJob prjJob);
	public Page<Map<String,Object>> getPrjJobReceiveList(Page<Map<String,Object>> page, PrjJob prjJob,String itemRight);
	public Map<String, Object> getByNo(String id);

	public boolean addPrjJob(PrjJob prjJob);

	public boolean updatePrjJob(PrjJob prjJob);
	
	/**
	 * 如果任务类型对应的tJobType表ChooseMan=‘0’并且Role<>’’时，根据当前楼盘干系人自动设定处理人。
	 * @param custCode
	 * @param role
	 * @return
	 */
	public String getDefaultDealMan(String custCode,String role,String jobType);
	/**
	 * 任务处理保存接口
	 */
	public  void dealPrjJob(PrjJob prjJob,String photoName);

	public  void receivePrjJob(PrjJob prjJob);
	
	public boolean isNeedReq(String custCode,String itemType1);

	public boolean hasSupplJob(String prjJobNo);

	//    add by hc  集成测量分析   2017/11/21   begin 
	public Page<Map<String,Object>> findCheckPageBySql(Page<Map<String,Object>> page, PrjJob prjJob,UserContext uc);
	public Page<Map<String,Object>> findPageBySqlTJFS(Page<Map<String,Object>> page,  PrjJob prjJob);
	public Map<String, Object> getCupName(String cupBrand);
	public Map<String, Object> getWarName(String warBrand);
	public Page<Map<String,Object>> getJcclPhotoListByNo(Page<Map<String,Object>> page, PrjJob prjJob);
	/**根据集成测量编号获取图片列表
	 * @param no
	 * @return
	 */
	public List<Map<String, Object>> getPhotoList(String no);
	/**
	 * 任务管理模块列表
	 * @param page
	 * @param prjJob
	 * @return
	 */
	public Page<Map<String,Object>> findManagePageBySql(Page<Map<String, Object>> page,
			PrjJob prjJob, UserContext uc);
	
	public Page<Map<String,Object>> findSupplPageBySql(Page<Map<String, Object>> page,
			PrjJob prjJob);
	
	public Page<Map<String,Object>> findSupplListPageBySql(Page<Map<String, Object>> page,
			SupplJob supplJob,String itemRight);
	/**
	 * 任务管理 接收 指派 完成，取消 
	 * @param prjJob
	 * @return
	 */
	public Result doPrjJobForProc(PrjJob prjJob);

	public List<Map<String,Object>> findPrjTypeByItemType1(int type,String pCode,UserContext uc);

	public List<Map<String,Object>> findPrjTypeByItemType1Auth(int type,String pCode,UserContext uc);


	//    add by hc  集成测量分析   2017/11/21   end
	
	/**
	 * 判断是否存在某些状态的任务单
	 * @author zzr
	 * @param custCode
	 * @param jobType
	 * @param status
	 * @return
	 */
	public Map<String, Object> existPrjJob(String custCode, String jobType, String status);
	/**集成进度售前/售后
	 * @param page
	 * @param prjJob
	 * @return
	 */
	public Page<Map<String,Object>> findAllBySql(Page<Map<String,Object>> page, PrjJob prjJob);
	
	public List<Map<String, Object>> findNoSendYunPic();
	
}
