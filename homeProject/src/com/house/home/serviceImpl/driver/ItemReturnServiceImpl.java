package com.house.home.serviceImpl.driver;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.ItemAppReturnQueryEvt;
import com.house.home.client.service.evt.ItemReturnArriveSaveEvt;
import com.house.home.client.service.evt.ItemReturnArrivedQueryEvt;
import com.house.home.client.service.evt.UpdateItemReturnReceiveEvt;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.dao.driver.ItemAppSendDao;
import com.house.home.dao.driver.ItemAppSendPhotoDao;
import com.house.home.dao.driver.ItemReturnDao;
import com.house.home.dao.driver.ItemReturnDetailDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.driver.ItemReturn;
import com.house.home.entity.driver.ItemReturnArrive;
import com.house.home.entity.driver.ItemReturnDetail;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.entity.driver.ItemSendPhoto;
import com.house.home.service.driver.ItemReturnService;

@SuppressWarnings("serial")
@Service
public class ItemReturnServiceImpl extends BaseServiceImpl implements ItemReturnService {

	@Autowired
	private ItemReturnDao itemReturnDao;
	@Autowired
	private ItemReturnDetailDao itemReturnDetailDao;
	@Autowired
	private ItemAppSendPhotoDao itemAppSendPhotoDao;
	@Autowired
	private ItemAppSendDao itemAppSendDao;
	@Autowired
	private PersonMessageDao personMessageDao;
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReturn itemReturn){
		return itemReturnDao.findPageBySql(page, itemReturn);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_forClient(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		return itemReturnDao.findPageBySql_forClient(page,itemReturn);
	}

	@Override
	public Page<Map<String, Object>> findPageByDriver(
			Page<Map<String, Object>> page, ItemAppReturnQueryEvt evt) {
		return itemReturnDao.findPageByDriver(page,evt);
	}

	@Override
	public Page<Map<String, Object>> findPageByDriverArrived(
			Page<Map<String, Object>> page, ItemReturnArrivedQueryEvt evt) {
		return itemReturnDao.findPageByDriverArrived(page,evt);
	}

	@Override
	public Map<String, Object> getByNo(String id) {
		return itemReturnDao.getByNo(id);
	}

	@Override
	public Result saveForProc(ItemReturn itemReturn, String xmlData) {
		return itemReturnDao.saveForProc(itemReturn,xmlData);
	}

	@Override
	public void saveItemReturnArrive(ItemReturnArrive itemReturnArrive,
			String pk, String photoNames,String id) {
			itemReturnDao.save(itemReturnArrive);
			String[] arr=pk.split(",");
			for(String ppk:arr){
				ItemReturnDetail itemReturnDetail=itemReturnDetailDao.get(ItemReturnDetail.class, Integer.parseInt(ppk));
				if(itemReturnDetail!=null){
					itemReturnDetail.setArriveNo(itemReturnArrive.getNo());
					itemReturnDetail.setLastUpdate(new Date());
					itemReturnDetailDao.update(itemReturnDetail);
				}
			
			}
			if(StringUtils.isNotBlank(photoNames)){
				String[] ph=photoNames.split(",");
				for(String name:ph){
					ItemSendPhoto photo=new ItemSendPhoto();
					photo.setPhotoName(name);
					photo.setActionLog("Add");
					photo.setExpired("F");
					photo.setType("2");
					photo.setLastUpdate(new Date());
					photo.setLastUpdatedBy(itemReturnArrive.getDriverCode());
					photo.setSendNo(itemReturnArrive.getNo());
					photo.setIsSendYun("1");
					photo.setSendDate(new Date());
					this.save(photo);
				}			
			}
			ItemReturn itemReturn=itemReturnDao.get(ItemReturn.class, id);
			ItemSendBatch itemSendBatch=itemAppSendDao.getSendBatchById(itemReturn.getSendBatchNo());
			int num=itemAppSendDao.countItemSend(itemReturn.getSendBatchNo());
			itemSendBatch.setStatus("2");
			itemSendBatch.setLastUpdate(new Date());
			itemReturn.setStatus("5");
			itemReturn.setLastUpdate(new Date());
			List<Map<String,Object>> material=itemReturnDetailDao.getReturnMaterialNotIn(id,pk);
			if(material.size()==0){
				itemReturn.setStatus("6");
				itemReturn.setCompleteDate(new Date());
				if(num==0&&itemReturnDao.isArrivedAll(itemReturn.getSendBatchNo(), itemReturn.getDriverCode(),itemReturn.getNo())){
					itemSendBatch.setStatus("3");
				}
			}
			itemAppSendDao.update(itemSendBatch);
			itemReturnDao.update(itemReturn);
			//推送消息
			Map<String, Object> map=itemReturnDetailDao.getReturnDetailById(id);
			List<Map<String,Object>> materials=itemReturnDetailDao.getMaterialList(id);
			PersonMessage personMessage = new PersonMessage();
			personMessage.setMsgType("6");
			personMessage.setMsgText(map.get("Address")+":您于【"+map.get("Date").toString().substring(0, 11)+"】申请的【"+map.get("descr")+"】-【"+	materials.get(0).get("Descr")+"】等材料已完成退货。");
			personMessage.setMsgRelNo(id);
			personMessage.setMsgRelCustCode(map.get("custCode").toString());
			personMessage.setCrtDate(new Date());
			personMessage.setSendDate(new Date());
			personMessage.setRcvType("1");
			personMessage.setRcvCzy(map.get("number").toString());
			personMessage.setIsPush("1");
			personMessage.setPushStatus("0");
			personMessage.setRcvStatus("0");
			personMessageDao.save(personMessage);
	}

	@Override
	public int countItemReturn(String sendBatchNo) {
		// TODO Auto-generated method stub
		return itemReturnDao.countItemReturn(sendBatchNo);
	}

	@Override
	public void updateItemReturnReceive(ItemReturn itemReturn,UpdateItemReturnReceiveEvt evt,String materialDetail) {
		// TODO Auto-generated method stub
		this.update(itemReturn);
		ItemSendBatch itemSendBatch=itemAppSendDao.getSendBatchById(itemReturn.getSendBatchNo());
		itemSendBatch.setStatus("2");
		itemAppSendDao.update(itemSendBatch);
		String materialDetailJsonArray=materialDetail;
		JSONArray jsonArray = JSONArray.fromObject(materialDetailJsonArray);  
		List<Map<String,Object>> mapListJson = (List)jsonArray;
		String photoNames=evt.getPhotoNames();
		for (int i = 0; i < mapListJson.size(); i++) {  
			Map<String,Object> obj=mapListJson.get(i);  
			int pk=0;
			Double realQty=1.00;
			for(Entry<String,Object> entry : obj.entrySet()){
//								String strkey1 = entry.getKey();  
//								Object strval1 = entry.getValue();
				if(entry.getKey().equals("pk")){
					pk=Integer.parseInt(entry.getValue().toString());
				}
				if(entry.getKey().equals("realQty")){
					if(entry.getValue()!=null)
					realQty=Double.parseDouble(entry.getValue().toString());
				}
				
						
			}
			ItemReturnDetail itemReturnDetail=itemReturnDetailDao.get(ItemReturnDetail.class,pk);
			itemReturnDetail.setRealQty(realQty);
			this.update(itemReturnDetail);
		}
		if(StringUtils.isNotBlank(photoNames)){
			String[] ph=photoNames.split(",");
			for(String name:ph){
				ItemSendPhoto photo=new ItemSendPhoto();
				photo.setPhotoName(name);
				photo.setActionLog("Add");
				photo.setExpired("F");
				photo.setType("3");
				photo.setLastUpdate(new Date());
				photo.setLastUpdatedBy(itemReturn.getDriverCode());
				photo.setSendNo(itemReturn.getNo());
				photo.setIsSendYun("1");
				photo.setSendDate(new Date());
				this.save(photo);
			}			
		}
	}

	@Override
	public boolean isArrivedAll(String batchNo, String driverCode, String No) {
		// TODO Auto-generated method stub
		return itemReturnDao.isArrivedAll(batchNo, driverCode, No);
	}

	@Override
	public Page<Map<String, Object>> findReturnDetail(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		return itemReturnDao.findReturnDetail(page, itemReturn);
	}

	@Override
	public Page<Map<String, Object>> findReturnDetailAdd(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		return itemReturnDao.findReturnDetailAdd(page, itemReturn);
	}

	@Override
	public Page<Map<String, Object>> findReturnDetailMng(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		return itemReturnDao.findReturnDetailMng(page, itemReturn);
	}

	@Override
	public void doCancel(ItemReturn itemReturn) {
		itemReturnDao.doCancel(itemReturn);
	}

	@Override
	public Page<Map<String, Object>> findReturnDetailQry(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		return itemReturnDao.findReturnDetailQry(page, itemReturn);
	}

	@Override
	public List<Map<String, Object>> autoCteateFee(ItemReturn itemReturn) {
		return itemReturnDao.autoCteateFee(itemReturn);
	}

}
