package com.house.home.service.driver;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.ItemAppReturnQueryEvt;
import com.house.home.client.service.evt.ItemReturnArriveSaveEvt;
import com.house.home.client.service.evt.ItemReturnArrivedQueryEvt;
import com.house.home.client.service.evt.UpdateItemReturnReceiveEvt;
import com.house.home.entity.driver.ItemReturn;
import com.house.home.entity.driver.ItemReturnArrive;

public interface ItemReturnService extends BaseService {

	/**ItemReturn分页信息
	 * @param page
	 * @param itemReturn
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReturn itemReturn);
	/**
	 * 根据司机编号查询待退货列表
	 * @param page
	 * @param driver
	 * @return
	 */
	public Page<Map<String, Object>> findPageByDriver(Page<Map<String, Object>> page, ItemAppReturnQueryEvt evt);
	/**
	 * 根据司机编号查询已退货列表
	 * @param page
	 * @param driver
	 * @return
	 */
	public Page<Map<String, Object>> findPageByDriverArrived(Page<Map<String, Object>> page, ItemReturnArrivedQueryEvt evt);
	/**ItemReturn分页信息接口
	 * @param page
	 * @param itemReturn
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_forClient(Page<Map<String,Object>> page, ItemReturn itemReturn);
	
	/**
	 * 获取退货申请详情
	 * @param id
	 * @return
	 */
	public Map<String, Object> getByNo(String id);
	
	public Result saveForProc(ItemReturn itemReturn, String xmlData);
	public void saveItemReturnArrive(ItemReturnArrive itemReturnArrive,
			String pk, String photoNames,String id);
	/**
	 * 统计未完成的退货申请数
	 */
	public int countItemReturn(String sendBatchNo);
	/**
	 * 收货
	 */
	public void updateItemReturnReceive(ItemReturn itemReturn,UpdateItemReturnReceiveEvt evt,String materialDetail);
	/**
	 * 判断退货批次中的退货单是否都到货(不含本次操作)
	 */
	public boolean isArrivedAll(String batchNo,String driverCode,String No);
	/**
	 * 退货明细
	 */
	public Page<Map<String, Object>> findReturnDetail(Page<Map<String, Object>> page, ItemReturn itemReturn);
	/**
	 *新增退货明细列表
	 */
	public Page<Map<String, Object>> findReturnDetailAdd(Page<Map<String, Object>> page, ItemReturn itemReturn);
	/**
	 * 配送管理-明细管理-退货明细
	 */
	public Page<Map<String, Object>> findReturnDetailMng(Page<Map<String, Object>> page, ItemReturn itemReturn);
	/**
	 * 配送管理-明细管理-退货取消
	 */
	public void doCancel(ItemReturn itemReturn);
	/**
	 * 配送管理-明细查询-退货明细
	 */
	public Page<Map<String, Object>> findReturnDetailQry(Page<Map<String, Object>> page, ItemReturn itemReturn);
	/**
	 * 自动生成搬运费
	 * 
	 */
	public List<Map<String, Object>> autoCteateFee(ItemReturn itemReturn);
}
