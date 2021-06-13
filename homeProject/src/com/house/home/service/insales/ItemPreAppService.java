package com.house.home.service.insales;

import java.util.List;
import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemPreApp;
import com.house.home.entity.project.ItemPreMeasure;

public interface ItemPreAppService extends BaseService {

	/**ItemPreApp分页信息
	 * @param page
	 * @param itemPreApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreApp itemPreApp);
	
	public Result saveForProc(ItemPreApp itemPreApp, String xml);
	
	public Result checkForProc(ItemPreApp itemPreApp, String xml);
	
	//20171011领料预申请管理
	public Page<Map<String,Object>> findItemPreAppManageListPageBySql(Page<Map<String,Object>> page, ItemPreApp itemPreApp);
	
	public Page<Map<String,Object>> getApplyListByNo(Page<Map<String,Object>> page,String no,String supplCode);

	public Page<Map<String,Object>> getItemPreAppPhotoListByNo(Page<Map<String,Object>> page, ItemPreApp itemPreApp);
	
	public Page<Map<String,Object>> getMeasureByCondition(Page<Map<String,Object>> page,String no,Integer pk);
	
	public Result updatePreItemAppStatus(ItemPreApp itemPreApp);

	public Map<String,Object> existPreMeasure(Integer pk,String supplCode,String preAppNo);

	public Page<Map<String,Object>> goClhxdJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure);

	public List<Map<String,Object>> findOtherMaterial(String no);
	
	public Map<String,Object> getMeasureInfoByPk(Integer pk);
	
	public boolean hasInSetReq(String custCode,String itemType1);	
	
	public boolean containServiceItem(String no);
	
	public Map<String,Object> getCanInOutPlan(String itemType1,String custCode);
	
	public List<Map<String,Object>> getItemType2Opt(String itemType1);

	public Page<Map<String,Object>> goYyxxzJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure);
	
	public boolean judgeItemInTitles(String itemCode);
	
	public Map<String,Object> getItemNum(String itemCode,Double qty);

	public Page<Map<String,Object>> goPreApplyJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure);

	public Page<Map<String,Object>> goYlclJqGrid(Page<Map<String,Object>> page, String custCode,String appNo,String itemCodes);
	
	public Map<String,Object> getMeasureInfoByNo(String no);

	public Page<Map<String,Object>> goKsxzJqGrid(Page<Map<String,Object>> page,ItemPreMeasure itemPreMeasure);
	
	public Result AddllForProc(ItemPreMeasure itemPreMeasure, String xml);
	
	public boolean isStayAddPage(String preAppNo);
	
	public Map<String,Object> findMessageInfo(Integer pk);
	
	public List<Map<String,Object>> custItemConf(String custCode);
	
	public Map<String,Object> getFsArea(String custCode);
	
	public Map<String,Object> getCustPayInfo(String custCode);
	
	public Map<String,Object> getSendCount(String no);
	
	public Page<Map<String,Object>> goItemAppDetailJqGrid(Page<Map<String,Object>> page,ItemPreMeasure itemPreMeasure);
	
	public Map<String,Object> checkCost(String custCode,String materWorkType2);
	
	public Result checkQty(String custCode,String appNo ,String itemType1,String xml);
	
	public List<Map<String,Object>> checkQtyByItemType2(String custCode,String appNo ,String itemType1);
	
	public Map<String,Object> checkCustStatus(String custCode);
	
	public Map<String,Object> checkCustPay(String appNo);
	
	public Page<Map<String,Object>> goCodeJqGrid(Page<Map<String,Object>> page,ItemPreApp itemPreApp);
	
	public Page<Map<String,Object>> goCodeDetailJqGrid(Page<Map<String,Object>> page,String no);
	
	public Page<Map<String,Object>> goJqGridSend(Page<Map<String,Object>> page, String no);
	
	public Page<Map<String,Object>> goJqGridSendMx(Page<Map<String,Object>> page, String no);
	
	public Map<String, Object> getItemInfoByCode(String itemCode, String itemType1, String itemType2, String no, String custCode); 
	
	public Map<String, Object> getIntProd(String descr, String custCode);
	
	public Page<Map<String,Object>> getWorkerAppItemList(Page<Map<String,Object>> page, Integer custWkPk,String custCode);

	public Page<Map<String,Object>> getWorkerItemApp(Page<Map<String,Object>> page, String no);

	public Page<Map<String,Object>> getItemAppDetail(Page<Map<String,Object>> page, String no);
	
	public Page<Map<String,Object>> goJqGridSendCountView(Page<Map<String,Object>> page, String no);
	
	public Page<Map<String,Object>> goJqGirdUnItemAppMaterial(Page<Map<String,Object>> page, String custCode, String itemType1, String itemType2, String whCode, String supplCode);
	
	public Integer getSendCountNum(String no);
	
	public Map<String,Object> getRatedMatrial(String custCode,String workType12);

	public Page<Map<String, Object>> goJddrJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure);
	
	public String checkIsSpecReq(String itemType1);
	
	public Page<Map<String, Object>> goLlcbJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure);

	public Page<Map<String, Object>> goCgazcbJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure);
	
	public Page<Map<String, Object>> goYgazcbJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure);
	
	public Map<String,Object> getAppliedMoney(String custCode,String workType12);
	
	public Map<String,Object> getLastSendSupplier(String custCode);
	
	public Page<Map<String, Object>> goIntProDetailJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure);

	public Page<Map<String,Object>> getWaterItemList(Page<Map<String,Object>> page, String custCode);
	
	public Page<Map<String,Object>> getWaterItemDetail(Page<Map<String,Object>> page, String no);
	
	public Page<Map<String,Object>> goAutoOrderJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure);
	
	public Result autoOrderForProc(ItemPreMeasure itemPreMeasure, String xml);
	
	public void updateRemarks( ItemPreMeasure itemPreMeasure);
	
	public List<Map<String, Object>> findNoSendYunPic();
	
	public boolean isExistsSaleIndependence(String custCode);
	
	public void doNotify(ItemPreMeasure itemPreMeasure);
	
	public String checkKgQty(ItemPreMeasure itemPreMeasure);
	
	public List<Map<String, Object>> isStopWork(String custCode);

	public List<Map<String, Object>> checkSendCmpWh(ItemPreMeasure itemPreMeasure);
	
	public Page<Map<String,Object>> goJqGridItemPlanSoftSend(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure);
	
	public String getSoftSendCount(String custCode, String itemType1);
	
	public Page<Map<String,Object>> findItemShouldOrderJqGrid(Page<Map<String,Object>> page, Customer customer);
	
	public Result doBatchSave(ItemPreApp itemPreApp);
	
	public Result doBackstageOrder(ItemPreMeasure itemPreMeasure);
	
	public Page<Map<String, Object>> goXqazcbJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure);

	public Map<String, Object> getByPreAppNo(String preAppNo);
}
