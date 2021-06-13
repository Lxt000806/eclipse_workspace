package com.house.home.service.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetConfirmInfoEvt;
import com.house.home.client.service.evt.SavePrjProgConfirmEvt;
import com.house.home.entity.project.PrjConfirmApp;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgConfirm;
public interface PrjProgConfirmService extends BaseService {
	
	/**
	 * PrjProg分页信息
	 * 
	 * @param page
	 * @param prjProg
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjProgConfirm prjProgConfirm);

	public Page<Map<String, Object>> findConfirmPageBySql(
			Page<Map<String, Object>> page, PrjProgConfirm prjProgConfirm,UserContext uc);

	public Page<Map<String, Object>> findPrjConfirmPageBySql(
			Page<Map<String, Object>> page, PrjProgConfirm prjProgConfirm);

	
	/**PrjProgConfirm分页信息
	 * @param page
	 * @param prjProgConfirm
	 * @return
	 */
	public Page<Map<String,Object>> getSiteConfirmListById(Page<Map<String,Object>> page, String czy,String custCode, String fromPageFlag);
	/**
	 * 
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String,Object>> getPrjItemDescr(Page<Map<String,Object>> page, String custCode,String prjRole,String prjItem);

	
	public Page<Map<String,Object>> getWorkerAppPrjItemDescr(Page<Map<String,Object>> page, String custCode,String prjRole,String prjItem,String workerAPp);
/**
	 *验收保存接口
	 */
	public void savePrjProgConfirm(PrjProgConfirm prjProgConfirm,String photoName,String type,Date endDate,JSONArray confirmCustomFiledValue);
	/**
	 * 验收详情
	 */
	public Map<String,Object> getSiteConfirmDetail(String no, String platform, String czybh, String custCode, String prjItem);

	public Page<Map<String,Object>> findConfirmAppPageBySql(Page<Map<String,Object>> page, PrjConfirmApp prjConfirmApp, UserContext uc);
	
	public Map<String,Object> prjConfirmAppExist(String custCode,String prjItem);

	public Map<String,Object> checkPrjConfirmPassProg(String custCode,String prjItem);
	
	public List<Map<String,Object>> getConfirmPrjItem(String custCode);
	
	public Map<String,Object> getConfirmInfo(String custCode,String prjItem);
	
	public Page<Map<String,Object>> getPrjConfirmByPage(Page<Map<String,Object>> page,String id,String address,String prjItem,String prjRole);

	public Page<Map<String,Object>> getPrjConfirmByPage(Page<Map<String,Object>> page,String id,String address,String prjItem,String prjRole,String regionCode,String isLeaveProblem);

	public Page<Map<String,Object>> getWorkerAppPrjConfirmByPage(Page<Map<String,Object>> page,String id,String address,String prjItem,String prjRole);

	public void updatePrjProgConfirm(SavePrjProgConfirmEvt evt);
	
	public boolean existCustPrjItemPass(String custCode, String prjItem);
	
	public Map<String, Object> getSiteConfirmStatus(String custCode, String prjItem);
	
	public String getAllowItemApp(String id);
	
	public List<Map<String, Object>> findNoSendYunPics(String type);
	
	public void setPrjConfirmPhotoIsPushCust();
	
	public void setPrjProgConfirmIsPushCust();

	public String getSeqNo(String tableName);
	
	public List<Map<String, Object>> getConfirmCustomFiledList(String prjItem, String prjProgConfirmNo);
	
	public List<Map<String, Object>> getPrjProgPhoto(String no);
}
