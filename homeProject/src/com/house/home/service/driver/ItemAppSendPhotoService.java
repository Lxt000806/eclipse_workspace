package com.house.home.service.driver;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemSendPhoto;
public interface ItemAppSendPhotoService extends BaseService {

	/**根据送货单编号获取图片列表
	 * @param no
	 * @return
	 */
	public List<Map<String, Object>> getPhotoList(String no,String type);
	/**
	 * 根据图片名称获取图片
	 * @param id
	 * @return
	 */
	public ItemSendPhoto getByPhotoName(String id);
	/**
	 * 根据送货单编号获取图片列表
	 * @param page
	 * @param itemSendPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSendPhoto itemSendPhoto);
	/**
	 * 删除送退货图片
	 * @param id
	 */
	public void delPhotoList(String id);
}
