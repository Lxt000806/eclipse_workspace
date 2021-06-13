package com.house.home.service.driver;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.driver.ItemSendBatch;

public interface ItemAppSendService extends BaseService {
	/**
	 * 根据司机编号查询送货列表
	 * @param page
	 * @param driver
	 * @return
	 */
	public Page<Map<String, Object>> findPageByDriver(Page<Map<String, Object>> page, String driver,String address);
	/**
	 * 根据司机编号查询到货列表
	 * @param page
	 * @param driver
	 * @return
	 */
	public Page<Map<String, Object>> findPageByDriverArrived(Page<Map<String, Object>> page, String driver,String address);
	/**
	 * 根据送货单编号查询送货详情
	 */
	 public Map<String,Object> getSendDetailById(String No);
		/**获取送货材料明细
		 * @return
		 */
	public List<Map<String,Object>> getMaterialList(String No);
	/**
	 * 根据送货单号查询送货详情
	 */
	public ItemAppSend getAppSendById(String No);
	/**
	 * 根据Id获取送货批次
	 */
	public ItemSendBatch getSendBatchById(String no);
	/**
	 * 判断送货批次中的送货单是否都到货(不含本次操作)
	 */
	public boolean isArrivedAll(String batchNo,String driverCode,String No);
	/**
	 * 到货保存
	 */
	public void updateItemAppSend(String no,String address,String remark);
	/**
	 * 统计未完成的送货申请数
	 */
	public int countItemSend(String sendBatchNo);
	/**
	 * 到货详情接口
	 * @param id
	 * @return
	 */
	public Map<String, Object> getSendArrivedDetailById(String id);
	/**
	 * 根据IaNo查询送货列表
	 * @param page
	 * @param driver
	 * @return
	 */
	public Page<Map<String, Object>> findPageByIaNo(Page<Map<String, Object>> page, String iaNo);
	/**
	 * 查询项目经理的待确认到货单（包含未到货和到货异常、只包含主材和基础的发货单）
	 * @param page
	 * @param projectMan
	 *  @param address
	 * @return
	 */
	public Page<Map<String, Object>> getItemAppSendConfirmList(Page<Map<String, Object>> page, String projectMan,String address);
	/**
	 * 到货确认并更新消息为已读
	 * @param itemAppSend
	 * @param personMessage
	 */
	public  void ItemAppSendDoConfirm(ItemAppSend itemAppSend,PersonMessage personMessage);
	/**
	 * 送货明细
	 * @param page
	 * @param itemAppSend
	 * @return
	 */
	public Page<Map<String, Object>> findSendDetail(Page<Map<String, Object>> page, ItemAppSend itemAppSend);
	/**
	 * 新增发货列表
	 * @param page
	 * @param itemAppSend
	 * @return
	 */
	public Page<Map<String, Object>> findSendDetailAdd(Page<Map<String, Object>> page, ItemAppSend itemAppSend);
	/**
	 * 配送管理-明细管理-送货明细
	 * @param page
	 * @param itemAppSend
	 * @return
	 */
	public Page<Map<String, Object>> findSendDetailMng(Page<Map<String, Object>> page, ItemAppSend itemAppSend);
	/**
	 * 到货
	 * @param itemAppSend
	 */
	public void doArrive(ItemAppSend itemAppSend);
	/**
	 * 配送管理-明细查询-送货明细
	 * @param page
	 * @param itemAppSend
	 * @return
	 */
	public Page<Map<String, Object>> findSendDetailQry(Page<Map<String, Object>> page, ItemAppSend itemAppSend);
	/**
	 * 自动生成搬运费
	 * 
	 * @param itemAppSend
	 * @return
	 */
	public Map<String, Object> autoCteateFee(ItemAppSend itemAppSend);
	
	public List<Map<String, Object>> findNoSendYunPic();
}
