package com.house.home.service.insales;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemPreAppPhoto;

public interface ItemPreAppPhotoService extends BaseService {

	/**ItemPreAppPhoto分页信息
	 * @param page
	 * @param itemPreAppPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreAppPhoto itemPreAppPhoto);

	/**根据预申请单编号获取图片列表
	 * @param no
	 * @return
	 */
	public List<Map<String, Object>> getPhotoList(String no);

	/**
	 * 根据图片名称获取图片
	 * @param id
	 * @return
	 */
	public ItemPreAppPhoto getByPhotoName(String id);
	
}
