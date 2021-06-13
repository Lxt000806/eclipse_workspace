package com.house.home.service.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ItemBatchHeaderQueryEvt;
import com.house.home.client.service.evt.ItemSaveEvt;
import com.house.home.client.service.evt.UpdateItemEvt;
import com.house.home.client.service.evt.WareHouseItemEvt;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.ItemBatchHeader;

public interface ItemService extends BaseService {

	/**Item分页信息
	 * @param page
	 * @param item
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Item item);
	
	public Page<Map<String, Object>> findSupplCostPageBySql(Page<Map<String, Object>> page, Item item);

	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, Item item);

	
	/**Item信息分页信息
	 * @param page
	 * @param item
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_itemMessage(Page<Map<String,Object>> page, Item item);
	
	
	/**
	 * 查询商品类型
	 * @param type
	 * @param pCode
	 * @return
	 */
	public List<Map<String,Object>> findItemType(int type,String pCode);
	/**
	 * 根据权限查询商品类型
	 * @param type
	 * @param pCode
	 * @return
	 */
	public List<Map<String,Object>> findItemTypeByAuthority(int type,String pCode,UserContext uc);

	/**接口用
	 * @param page
	 * @param item
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlForClient(Page<Map<String,Object>> page, Item item);
	/**
	 * 获取材料项明细
	 */
	public  Map<String,Object> findItemById(String id, String custType);
	/**
	 * 获取员工材料类型权限
	 */
	public List<Map<String,Object>> getItemTypeById(String id);
	/**
	 * 保存材料项
	 */
	public  void saveItem(ItemSaveEvt evt,String seqNo);
	/**
	 * 更新材料项
	 */
	public  void updateItem(UpdateItemEvt evt);
	/**
	 * 根据操作员编号、批次类型查询材料批次
	 */
	public Page<Map<String, Object>> findPageByCzy(Page<Map<String, Object>> page, String czy,String batchType,String itemBatchHeaderRemarks);
	
	/**
	 * 删除材料项
	 * @param evt
	 */
	public void deleteItem(String id);
	/**
	 * 仓库中材料详情
	 * @param page
	 * @param item
	 * @return
	 */
	public Map<String,Object> findWareHouseItemDetail(WareHouseItemEvt evt);

	/**
	 * 根据材料类型1和sqlcode返回材料列表
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 */
	public Page<Map<String,Object>> getItemBySqlCode(Page<Map<String, Object>> page, Item item);
	public  Item getItemByCondition(Item item);
	
	/**
	 * 添加材料存储过程
	 * 
	 * @param item
	 * @return
	 */
	public Result saveForProc(Item item);
	
	public boolean hasQzbyXTCS(String sItemtype2);
	
	public boolean isExistDescr(Item item);
	
	public boolean hasItemPlan(String itemCode);

	public Page<Map<String,Object>> findPageBySql_updatePrePrice(Page<Map<String,Object>> page, Item item);
	
	
	public Result doUpdatePrePrice(Item item);
	
	public Page<Map<String,Object>> findPageBySql_updateBatch(Page<Map<String,Object>> page, Item item);
	
	public double getPerfPer(String itemType2, double cost, double price);
	
	public Result doSaveBatch(Item item);
	
	public void doUpdateBatch(Item item);
	
	/**
	 * 删除图片
	 * 
	 * */
	public void doDelPicture(String photoName);

	public Page<Map<String, Object>> getItemBySqlCode2(Page<Map<String, Object>> page, Item item);

	/**
	 * 根据材料编号查算法
	 * @param item
	 * @return
	 */
	public List<Map<String, Object>> getAlgorithmByCode(Item item);
	
	/**
	 * 获取标签打印信息
	 * @param page
	 * @param code
	 * @return
	 */
	public Map<String,Object> getLabelContent(String code);
	
	public boolean isSplCompanyItem(String itemCode,String custCode);
	
	public Page<Map<String,Object>> getCustCodeList(Page<Map<String,Object>> page, UserContext uc, String searchAddress);
	
	public Page<Map<String,Object>> getPrePlanAreaList(Page<Map<String,Object>> page,String custCode);
	
	public Page<Map<String,Object>> getFixAreaTypeItemType12AttrList(Page<Map<String,Object>> page,String fixAreaType,String itemType12);
	
	public Page<Map<String, Object>> getMaterialOldItemList(Page<Map<String, Object>> page,ItemBatchHeaderQueryEvt evt);
	
	public Map<String, Object> getSameItemNum(String custCode,String id, String alreadyReplaceStr);
	
	public Page<Map<String, Object>> getItemType12List(Page<Map<String, Object>> page,String custCode,String itemType1);

	public Result saveCustSelection(ItemBatchHeader itemBatchHeader,String xmlData);
	
	public Map<String,Object> getItemMainPicPhotoUrl(String itemCode);
	
	public Page<Map<String, Object>> getItemType2List(Page<Map<String, Object>> page,String itemType1);
	
	public Page<Map<String, Object>> getMaterialNewItemList(Page<Map<String, Object>> page,ItemBatchHeaderQueryEvt evt, String custType, String canUseComItem);
	
	public Page<Map<String, Object>> getSameItemList(Page<Map<String, Object>> page,ItemBatchHeaderQueryEvt evt);
	
	public List<Map<String, Object>> getAuthItemType2ByItemType1(Item item);
	
	public Page<Map<String, Object>> getItemType12List(Page<Map<String, Object>> page,String itemType1);
	
	public List<Map<String,Object>> getCustTypeItemList(String itemCode);
	
	public Page<Map<String, Object>> getMaterialList(Page<Map<String, Object>> page, String searchText,String itemType1);

	public boolean canScanItem(String itemCode,String custType, String canUseComItem);
	
	public Page<Map<String, Object>> findSuggestPageBySql(Page<Map<String, Object>> page, Item item);
}
