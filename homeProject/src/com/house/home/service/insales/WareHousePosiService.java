package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.OperateWareHousePosiEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.entity.insales.WareHousePosi;

public interface WareHousePosiService extends BaseService {

	/**WareHousePosi分页信息
	 * @param page
	 * @param wareHousePosi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHousePosi wareHousePosi);
	/**
	 * 货架查询
	 */
	public Page<Map<String,Object>> getWareHousePosiList(Page<Map<String,Object>> page,String whCode,String idCode,String desc1);
	/**
	 * 上下架操作
	 */
	public void operateWareHousePosi(BaseResponse respon,OperateWareHousePosiEvt evt);
	
	/**
	 * @Description: TODO 库位管理左链接分页查询
	 * @author	created by zb
	 * @date	2018-8-9--上午10:25:40
	 * @param page
	 * @param wareHousePosi
	 * @return
	 */
	public Page<Map<String,Object>> findPageByleftSql(Page<Map<String, Object>> page,
			WareHousePosi wareHousePosi);
	
	/**
	 * @Description: TODO 检查desc1是否重复
	 * @author	created by zb
	 * @date	2018-8-9--下午4:29:57
	 * @param desc1
	 * @param pk
	 * @return
	 */
	public boolean checkDesc1PK(String desc1, Integer pk);
	
	/**
	 * @Description: TODO wareHousePosi_code分页查询
	 * @author	created by zb
	 * @date	2018-8-15--下午4:17:11
	 * @param page
	 * @param wareHousePosi
	 */
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String, Object>> page,
			WareHousePosi wareHousePosi);
}
