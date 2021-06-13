package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CutFeeSet;

public interface CutFeeSetService extends BaseService{

	/**
	 * @Description: TODO 切割费设置分页查询
	 * @author	created by zb
	 * @date	2018-10-22--上午10:20:38
	 * @param page
	 * @param cutFeeSet
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, CutFeeSet cutFeeSet);

	/**
	 * @Description: TODO 查询切割类型和瓷砖尺寸是否重复
	 * @author	created by zb
	 * @date	2018-10-22--上午11:40:05
	 * @param cutType
	 * @param size
	 * @return
	 */
	public boolean checkCode(String cutType, String size);

	/**
	 * @Description: TODO 根据cuttype和size做删除操作
	 * @author	created by zb
	 * @date	2018-10-22--下午2:40:02
	 * @param cutFeeSet
	 * @return
	 */
	public Boolean doDelete(CutFeeSet cutFeeSet);

	/**
	 * @Description: TODO 编辑操作
	 * @author	created by zb
	 * @date	2018-10-22--下午4:17:18
	 * @param cutFeeSet
	 * @return
	 */
	public Boolean doUpdate(CutFeeSet cutFeeSet);

}
