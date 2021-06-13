package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ItemBatchHeaderQueryEvt;
import com.house.home.client.service.evt.ItemSaveEvt;
import com.house.home.client.service.evt.UpdateItemEvt;
import com.house.home.client.service.evt.WareHouseItemEvt;
import com.house.home.dao.basic.ItemDao;
import com.house.home.dao.basic.ItemType1Dao;
import com.house.home.dao.basic.ItemType2Dao;
import com.house.home.dao.basic.ItemType3Dao;
import com.house.home.entity.basic.Item;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemBatchDetail;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.service.basic.ItemService;
import com.house.home.service.insales.ItemBatchDetailService;

@SuppressWarnings("serial")
@Service
public class ItemServiceImpl extends BaseServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;
	@Autowired
	private ItemType1Dao itemType1Dao;
	@Autowired
	private ItemType2Dao itemType2Dao;
	@Autowired
	private ItemType3Dao itemType3Dao;
	@Autowired
	private ItemBatchDetailService itemBatchDetailService;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Item item){
		return itemDao.findPageBySql(page, item);
	}
	
    @Override
    public Page<Map<String, Object>> findSupplCostPageBySql(Page<Map<String, Object>> page,
            Item item) {
        return itemDao.findSupplCostPageBySql(page, item);
    }

	public Page<Map<String,Object>> findPageBySql_itemMessage(Page<Map<String,Object>> page, Item item){
		return itemDao.findPageBySql_itemMessage(page, item);
	}
	

	
	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, Item item){
		return itemDao.findPurchPageBySql(page, item);
	}


	public List<Map<String,Object>> findItemType(int type,String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.itemType1Dao.findItemType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.itemType2Dao.findItemType2(param);
		}else if(type == 3){//商品类型3
			param.put("pCode", pCode);
			resultList = this.itemType3Dao.findItemType3(param);
		}
		return resultList;
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlForClient(
			Page<Map<String, Object>> page, Item item) {
		return itemDao.findPageBySqlForClient(page,item);
	}

	@Override
	public Map<String, Object> findItemById(String id, String custType) {
		// TODO Auto-generated method stub
		return itemDao.findItemById(id, custType);
	}

	@Override
	public List<Map<String, Object>> getItemTypeById(String id) {
		// TODO Auto-generated method stub
		return itemDao.getItemTypeById(id);
	}

	@Override
	public void saveItem(ItemSaveEvt evt, String seqNo) {
		// TODO Auto-generated method stub
		ItemBatchHeader itemBatchHeader=new ItemBatchHeader();
		itemBatchHeader.setNo(seqNo);
		itemBatchHeader.setDate(new Date());
		itemBatchHeader.setCrtCzy(evt.getId());
		itemBatchHeader.setItemType1(evt.getItemType());
		itemBatchHeader.setRemarks(evt.getRemarks());
		itemBatchHeader.setLastUpdate(new Date());
		itemBatchHeader.setLastUpdatedBy(evt.getId());
		itemBatchHeader.setExpired("F");
		itemBatchHeader.setActionLog("ADD");
		//材料开单为1
		itemBatchHeader.setBatchType(evt.getBatchType()==null?"1":evt.getBatchType());
		this.save(itemBatchHeader);
		String batchJsonArray=evt.getBatchDetail();
		JSONArray jsonArray = JSONArray.fromObject(batchJsonArray);  
		List<Map<String,Object>> mapListJson = (List)jsonArray;  
		for (int i = 0; i < mapListJson.size(); i++) {  
			Map<String,Object> obj=mapListJson.get(i);  
			ItemBatchDetail itemBatchDetail=new ItemBatchDetail();
			itemBatchDetail.setIbdno(itemBatchHeader.getNo());
			itemBatchDetail.setLastUpdate(new Date());
			itemBatchDetail.setLastUpdatedBy(evt.getId());
			itemBatchDetail.setExpired("F");
			itemBatchDetail.setActionLog("ADD");
			itemBatchDetail.setDispSeq(i+1);
			for(Entry<String,Object> entry : obj.entrySet()){
//								String strkey1 = entry.getKey();  
//								Object strval1 = entry.getValue();
				if(entry.getKey().equals("code")){
					itemBatchDetail.setItcode(entry.getValue().toString());
					continue;
				}
				if(entry.getKey().equals("qty")){
					itemBatchDetail.setQty(Double.parseDouble(entry.getValue().toString()));
					continue;
				}
				if(entry.getKey().equals("itemRemark")){
					if(entry.getValue()!=null){
						itemBatchDetail.setRemarks(entry.getValue().toString());
					}
					continue;
				}
//								System.out.println("KEY:"+strkey1+"  -->  Value:"+strval1+"\n"); 
			}
			this.save(itemBatchDetail);
		}

	}

	@Override
	public Page<Map<String, Object>> findPageByCzy(
			Page<Map<String, Object>> page, String czy,String batchType,String itemBatchHeaderRemarks) {
		// TODO Auto-generated method stub
		return itemDao.findPageByCzy(page,czy,batchType,itemBatchHeaderRemarks);
	}

	@Override
	public void updateItem(UpdateItemEvt evt) {
		// TODO Auto-generated method stub
		ItemBatchHeader itemBatchHeader=this.get(ItemBatchHeader.class, evt.getIbdNo());
		itemBatchHeader.setRemarks(evt.getRemarks());
		itemBatchHeader.setLastUpdate(new Date());
		itemBatchHeader.setActionLog("EDIT");
		this.update(itemBatchHeader);
		String pk=evt.getPk();
		if(StringUtils.isNotBlank(pk)){
			String[] arr=pk.split(",");
			for(String str:arr){
				ItemBatchDetail itemBatchDetail=this.get(ItemBatchDetail.class, Integer.parseInt(str));
				this.delete(itemBatchDetail);
			}
		}
		
	
		String batchJsonArray=evt.getBatchDetail();
		JSONArray jsonArray = JSONArray.fromObject(batchJsonArray);  
		List<Map<String,Object>> mapListJson = (List)jsonArray;
		for (int i = 0; i < mapListJson.size(); i++) {  
			Map<String,Object> obj=mapListJson.get(i); 
			if(obj.containsKey("pk")){
				ItemBatchDetail itemBatchDetail=this.get(ItemBatchDetail.class, Integer.parseInt(obj.get("pk").toString()));
				itemBatchDetail.setQty(Double.parseDouble(obj.get("qty").toString()));
				if(obj.get("itemRemark")!=null){
					itemBatchDetail.setRemarks(obj.get("itemRemark").toString());
				}
			
				itemBatchDetail.setLastUpdate(new Date());
				itemBatchDetail.setActionLog("EDIT");
				this.update(itemBatchDetail);
				continue;
			}
			ItemBatchDetail itemBatchDetail=new ItemBatchDetail();
			itemBatchDetail.setIbdno(itemBatchHeader.getNo());
			itemBatchDetail.setItcode(obj.get("code").toString());
			itemBatchDetail.setQty(Double.parseDouble(obj.get("qty").toString()));
			if(obj.get("itemRemark")!=null){
				itemBatchDetail.setRemarks(obj.get("itemRemark").toString());
			}
			itemBatchDetail.setLastUpdate(new Date());
			itemBatchDetail.setLastUpdatedBy(itemBatchHeader.getCrtCzy());
			itemBatchDetail.setExpired("F");
			itemBatchDetail.setActionLog("ADD");
			itemBatchDetail.setDispSeq(i+1);
			this.save(itemBatchDetail);
		}
	}

	@Override
	public void deleteItem(String id) {
		// TODO Auto-generated method stub
		ItemBatchHeader itemBatchHeader=this.get(ItemBatchHeader.class, id);
		if(itemBatchHeader!=null){
			this.delete(itemBatchHeader);
		}
		List<ItemBatchDetail> list=itemBatchDetailService.getItemBatchDetailByIbdNo(id);
		if(list!=null&&list.size()>0){
			for(ItemBatchDetail itemBatchDetail:list){
					this.delete(itemBatchDetail);
			}
		}
		
	}

	@Override
	public Map<String, Object> findWareHouseItemDetail(WareHouseItemEvt evt) {
		// TODO Auto-generated method stub
		return itemDao. findWareHouseItemDetail(evt);
	}

	@Override
	public Page<Map<String, Object>> getItemBySqlCode(
			Page<Map<String, Object>> page, Item item) {
		// TODO Auto-generated method stub
		return itemDao.getItemBySqlCode(page,item);
	}

	@Override
	public Item getItemByCondition(Item item) {
		// TODO Auto-generated method stub
		return itemDao.getItemByCondition(item);
	}

	@Override
	public List<Map<String, Object>> findItemTypeByAuthority(int type,
			String pCode,UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.itemType1Dao.findItemType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.itemType2Dao.findItemType2(param);
		}else if(type == 3){//商品类型3
			param.put("pCode", pCode);
			resultList = this.itemType3Dao.findItemType3(param);
		}
		return resultList;
	}
	@Override
	public Result saveForProc(Item item) {
		return itemDao.saveForProc(item);
	}


	@Override
	public boolean hasQzbyXTCS(String sItemtype2) {
		return itemDao.hasQzbyXTCS(sItemtype2);
	}

	@Override
	public boolean isExistDescr(Item item) {
		return itemDao.isExistDescr(item);
	}
	
	@Override
	public boolean hasItemPlan(String itemCode) {
		return itemDao.hasItemPlan(itemCode);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_updatePrePrice(
			Page<Map<String, Object>> page, Item item) {
		return itemDao.getPageBySql_updatePrePrice(page,item);
	}

	@Override
	public Result doUpdatePrePrice(Item item) {
		// TODO Auto-generated method stub
		return itemDao.doUpdatePrePrice(item);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_updateBatch(
			Page<Map<String, Object>> page, Item item) {
		return itemDao.getPageBySql_updateBatch(page,item);
	}

	@Override
	public double getPerfPer(String itemType2, double cost,
			double price) {
		return itemDao.getPerfPer(itemType2, cost, price );
	}

	@Override
	public Result doSaveBatch(Item item) {
		return itemDao.doSaveBatch(item);
	}

	@Override
	public void doUpdateBatch(Item item) {
		 itemDao.doUpdateBatch(item);
		
	}
	
	@Override
	public void doDelPicture(String photoName){
		itemDao.doDelPicture(photoName);
		
	}

	@Override
	public Page<Map<String, Object>> getItemBySqlCode2(
			Page<Map<String, Object>> page, Item item) {
		return itemDao.getItemBySqlCode2(page, item);
	}


	@Override
	public List<Map<String, Object>> getAlgorithmByCode(Item item) {
		return itemDao.getAlgorithmByCode(item);
	}

	@Override
	public Map<String, Object> getLabelContent(String code) {
		// TODO Auto-generated method stub
		return itemDao.getLabelContent(code) ;
	}

	@Override
	public boolean isSplCompanyItem(String itemCode, String custCode) {
		// TODO Auto-generated method stub
		return itemDao.isSplCompanyItem(itemCode, custCode);
	}

	@Override
	public Page<Map<String, Object>> getCustCodeList(Page<Map<String, Object>> page, UserContext uc, String searchAddress) {
		return itemDao.getCustCodeList(page, uc, searchAddress);
	}

	@Override
	public Page<Map<String, Object>> getPrePlanAreaList(Page<Map<String, Object>> page,String custCode) {
		return itemDao.getPrePlanAreaList(page,custCode);
	}
	
	@Override
	public Page<Map<String, Object>> getFixAreaTypeItemType12AttrList(Page<Map<String, Object>> page,String fixAreaType,String itemType12) {
		return itemDao.getFixAreaTypeItemType12AttrList(page,fixAreaType,itemType12);
	}

	@Override
	public Page<Map<String, Object>> getMaterialOldItemList(Page<Map<String, Object>> page, ItemBatchHeaderQueryEvt evt) {
		return itemDao.getMaterialOldItemList(page, evt);
	}
	
	@Override
	public Map<String, Object> getSameItemNum(String custCode,String id, String alreadyReplaceStr){
		return itemDao.getSameItemNum(custCode,id, alreadyReplaceStr);
	}
	
	@Override
	public Page<Map<String, Object>> getItemType12List(Page<Map<String, Object>> page,String custCode,String itemType1){
		return itemDao.getItemType12List(page, custCode, itemType1);
	}
	
	@Override
	public Result saveCustSelection(ItemBatchHeader itemBatchHeader,String xmlData) {
		return itemDao.saveCustSelection(itemBatchHeader,xmlData);
	}

	@Override
	public Map<String, Object> getItemMainPicPhotoUrl(String itemCode) {
		return itemDao.getItemMainPicPhotoUrl(itemCode);
	}
	
	@Override
	public Page<Map<String, Object>> getItemType2List(Page<Map<String, Object>> page,String itemType1){
		return itemDao.getItemType2List(page, itemType1);
	}
	
	@Override
	public Page<Map<String, Object>> getMaterialNewItemList(Page<Map<String, Object>> page, ItemBatchHeaderQueryEvt evt, String custType, String canUseComItem) {
		return itemDao.getMaterialNewItemList(page, evt, custType, canUseComItem);
	}
	
	@Override
	public Page<Map<String, Object>> getSameItemList(Page<Map<String, Object>> page,ItemBatchHeaderQueryEvt evt) {
		return itemDao.getSameItemList(page, evt);
	}
	
	@Override
	public List<Map<String, Object>> getAuthItemType2ByItemType1(Item item) {
		
		return itemDao.getAuthItemType2ByItemType1(item);
	}
	
	@Override
	public Page<Map<String, Object>> getItemType12List(Page<Map<String, Object>> page,String itemType1){
		return itemDao.getItemType12List(page, itemType1);
	}

	@Override
	public List<Map<String, Object>> getCustTypeItemList(String itemCode) {
		return itemDao.getCustTypeItemList(itemCode);
	}
	
	@Override
	public Page<Map<String, Object>> getMaterialList(Page<Map<String, Object>> page, String searchText,String itemType1) {
		// TODO Auto-generated method stub
		return itemDao.getMaterialList(page,searchText,itemType1);
	}

	@Override
	public boolean canScanItem(String itemCode, String custType, String canUseComItem) {
		return itemDao.canScanItem(itemCode, custType, canUseComItem);
	}

	@Override
	public Page<Map<String, Object>> findSuggestPageBySql(
			Page<Map<String, Object>> page, Item item) {

		return itemDao.findSuggestPageBySql(page, item);
	}

	
}



