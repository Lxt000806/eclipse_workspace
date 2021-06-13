package com.house.home.serviceImpl.driver;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.dao.driver.ItemAppSendDao;
import com.house.home.dao.driver.ItemReturnDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.service.driver.ItemAppSendService;
@SuppressWarnings("serial")
@Service
public class ItemAppSendServiceImpl extends BaseServiceImpl implements
		ItemAppSendService {
	@Autowired
	private ItemAppSendDao itemAppSendDao;
	@Autowired
	private ItemReturnDao itemReturnDao;
	@Autowired
	private PersonMessageDao personMessageDao;
	@Override
	public Page<Map<String, Object>> findPageByDriver(
			Page<Map<String, Object>> page, String driver,String address) {
		return itemAppSendDao.findPageByDriver(page, driver,address);
	}
	@Override
	public Map<String, Object> getSendDetailById(String No) {
		return itemAppSendDao.getSendDetailById(No);
	}
	@Override
	public List<Map<String,Object>> getMaterialList(String No) {
		return itemAppSendDao.getMaterialList(No);
	}
	@Override
	public ItemAppSend getAppSendById(String No) {
		return itemAppSendDao.getAppSendById(No);
	}
	@Override
	public ItemSendBatch getSendBatchById(String no) {
		return itemAppSendDao.getSendBatchById(no);
	}
	@Override
	public boolean isArrivedAll(String batchNo, String driverCode, String No) {
		return itemAppSendDao.isArrivedAll(batchNo,driverCode,No);
	}
	@Override
	public void updateItemAppSend(String no, String address, String remark) {
		ItemAppSend itemAppSend=itemAppSendDao.getAppSendById(no);
		if(itemAppSend!=null){
			itemAppSend.setSendStatus("2");
			itemAppSend.setArriveAddress(address);
			itemAppSend.setArriveDate(new Date());
			itemAppSend.setDriverRemark(remark);
			itemAppSend.setLastUpdate(new Date());
			ItemSendBatch sendBatch=itemAppSendDao.getSendBatchById(itemAppSend.getSendBatchNo());
			int num=itemReturnDao.countItemReturn(itemAppSend.getSendBatchNo());
			if(sendBatch!=null){
				sendBatch.setLastUpdate(new Date());
				sendBatch.setStatus("2");
				sendBatch.setLastUpdate(new Date());
				itemAppSendDao.update(itemAppSend);
				if(itemAppSendDao.isArrivedAll(itemAppSend.getSendBatchNo(), itemAppSend.getDriverCode(),itemAppSend.getNo())&&num==0){
					sendBatch.setStatus("3");
				}
				itemAppSendDao.update(sendBatch);
				
			}
			
		}
		//推送通知
		
		Map<String,Object> map=itemAppSendDao.getSendDetailById(no);
			if(map!=null){
				List<Map<String,Object>> material=itemAppSendDao.getMaterialList(no);
				PersonMessage personMessage = new PersonMessage();
				personMessage.setMsgType("5");
				personMessage.setMsgText(map.get("address")+":您于【"+map.get("Date").toString().substring(0, 11)+"】申请的【"+map.get("Descr")+"】-【"+	material.get(0).get("Descr")+"】等材料已到货。");
				personMessage.setMsgRelNo(no);
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
		
		
	}
	@Override
	public Page<Map<String, Object>> findPageByDriverArrived(
			Page<Map<String, Object>> page, String driver,String address) {
		return itemAppSendDao.findPageByDriverArrived(page,driver,address);
	}
	@Override
	public int countItemSend(String sendBatchNo) {
		return itemAppSendDao.countItemSend(sendBatchNo);
	}
	@Override
	public Map<String, Object> getSendArrivedDetailById(String id) {
		return itemAppSendDao.getSendArrivedDetailById(id);
	}
	@Override
	public Page<Map<String, Object>> findPageByIaNo(
			Page<Map<String, Object>> page, String iaNo) {
		return itemAppSendDao.findPageByIaNo(page,iaNo);
	}
	@Override
	public Page<Map<String, Object>> getItemAppSendConfirmList(
			Page<Map<String, Object>> page, String projectMan, String address) {
		// TODO Auto-generated method stub
		return itemAppSendDao.getItemAppSendConfirmList(page, projectMan,address);
	}
	@Override
	public void ItemAppSendDoConfirm(ItemAppSend itemAppSend,
			PersonMessage personMessage) {
		itemAppSend.setIsConfirm("1");
		itemAppSend.setConfirmDate(new Date());	
		personMessage.setMsgRelNo(itemAppSend.getNo());
		personMessage.setRcvCzy(itemAppSend.getConfirmMan());
		personMessage.setMsgType("5");
		Page page=new Page();
		page.setPageSize(-1);
		page.setPageNo(1);
		personMessageDao.findPageBySql(page,personMessage);
		List<PersonMessage> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), PersonMessage.class);
		// 由于非主材,基装的到货单,在供应商发货时会自动设置成确认到货,因此针对这种情况,增加判断:若发货单已确认则只修改推送消息状态 add by zzr 2021/04/10
		if(!"1".equals(itemAppSend.getConfirmStatus().trim())){
			this.update(itemAppSend);
		}
		for(PersonMessage message:listBean){
			message.setRcvStatus("1");
			message.setRcvDate(new Date());
			this.update(message);
		}
		
		
	}
	@Override
	public Page<Map<String, Object>> findSendDetail(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		return itemAppSendDao.findSendDetail(page, itemAppSend);
	}
	@Override
	public Page<Map<String, Object>> findSendDetailAdd(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		return itemAppSendDao.findSendDetailAdd(page, itemAppSend);
	}
	@Override
	public Page<Map<String, Object>> findSendDetailMng(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		return itemAppSendDao.findSendDetailMng(page, itemAppSend);
	}
	@Override
	public void doArrive(ItemAppSend itemAppSend) {
		itemAppSendDao.doArrive(itemAppSend);
	}
	@Override
	public Page<Map<String, Object>> findSendDetailQry(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		return itemAppSendDao.findSendDetailQry(page, itemAppSend);
	}
	@Override
	public Map<String, Object> autoCteateFee(ItemAppSend itemAppSend) {
		return itemAppSendDao.autoCteateFee(itemAppSend);
	}
	
	@Override
	public List<Map<String, Object>> findNoSendYunPic(){
		return itemAppSendDao.findNoSendYunPic();
	}
}
