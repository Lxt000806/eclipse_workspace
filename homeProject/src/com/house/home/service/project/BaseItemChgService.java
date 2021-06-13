package com.house.home.service.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.BaseItemChgEvt;
import com.house.home.entity.project.BaseItemChg;

public interface BaseItemChgService extends BaseService {

	/**BaseItemChg分页信息
	 * @param page
	 * @param baseItemChg
	 * @param userContext 
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemChg baseItemChg, UserContext userContext);
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, BaseItemChg baseItemChg);

	/**
	 * 查询基装增减
	 * @param custCode
	 * @return
	 */
	public Page<Map<String,Object>> findPageByCustCode(Page<Map<String,Object>> page, String custCode);

	public Result saveForProc(BaseItemChg baseItemChg, String xml);
	
	/**
	 * 获取客户申请状态的基础增减单数量
	 * @param custCode
	 * @return
	 */
	public int getCountByCustCode(String custCode);
	
	public double getLastBaseDiscPer(String custCode);

	public double getBaseFeeDirct(String custCode);
	
	public void doPrjReceive(String no,String czybh);

	public void doPrjReturn(String no,String czybh);
	/**
	 * 客户经理APP_获取当前操作员申请的基装增减信息
	 * @author	created by zb
	 * @date	2019-3-4--上午9:34:47
	 * @param page
	 * @param evt
	 * @return
	 */
	Page<Map<String, Object>> getBaseItemChgList(
			Page<Map<String, Object>> page, BaseItemChgEvt evt);
	/**
	 * 根据增减no获取详细信息
	 * @author	created by zb
	 * @date	2019-3-4--上午9:35:03
	 * @param no
	 * @return
	 */
	Map<String, Object> getDetailByNo(String no);
	/**
	 * 获取基础增减干系人表
	 * @author	created by zb
	 * @date	2019-12-24--上午11:37:43
	 * @param page
	 * @param baseItemChg
	 * @return
	 */
	public Page<Map<String, Object>> findBaseChgStakeholderPageBySql(Page<Map<String, Object>> page,
			BaseItemChg baseItemChg);
	
	public Result doBaseItemChgTempProc(BaseItemChg baseItemChg);
	
	public Result doRegenBaseItemChgFromPrePlanTemp(BaseItemChg baseItemChg);
	
	public boolean getExistsTemp(String custCode,String no);

	/**
	 * 部门经理确认
	 * 
	 * @param no 基装增减单编号
	 * @param userContext
	 * @return
	 * @author 张海洋
	 */
    public Result doDeptLeaderConfirm(String no, UserContext userContext);
    
    public String getConfirmNotice(String no);
}
