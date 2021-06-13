package com.house.home.service.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.IntReplenish;
import com.house.home.entity.project.IntReplenishDT;

public interface IntReplenishService extends BaseService {

	/**IntReplenish分页信息
	 * @param page
	 * @param intReplenishDT
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntReplenish intReplenish);
	/**
	 * 补货明细录入保存
	 * @author	created by zb
	 * @date	2019-11-20--下午2:42:06
	 * @param intReplenish
	 * @return
	 */
	public Result doSave(IntReplenish intReplenish);
	/**
	 * 保存时，如果该补货单的所有补货明细都到货了，则更新补货主表的货齐时间、状态（已到货）。
	 * @author	created by zb
	 * @date	2019-11-21--下午2:48:04
	 * @param pk
	 */
	public void updateReadyDate(Integer pk);
	/**
	 * 售中安排
	 * @author	created by zb
	 * @param page 
	 * @date	2019-11-22--下午6:14:17
	 * @param no
	 * @return
	 */
	public IntReplenish goArrange(Page<Map<String, Object>> page, String no);
	/**
	 * 售中安排，存在同楼盘状态是1、2、3的补货单时需要进行提醒
	 * @author	created by zb
	 * @date	2019-12-9--下午2:43:59
	 * @param intReplenish
	 * @return 1：存在，0：不存在
	 */
	public String getIsHad(IntReplenish intReplenish);
	/**
	 * 售中安排，存在同楼盘状态是1、2、3的补货单时，获取这些楼盘的明细
	 * @author	created by zb
	 * @date	2019-12-9--下午4:42:53
	 * @param page
	 * @param intReplenish
	 * @return
	 */
	public Page<Map<String, Object>> findHadPageByNo(Page<Map<String, Object>> page,
			IntReplenish intReplenish);
	
	public void updateArrivedIntReplenish(IntReplenish intReplenish);
	
	public Result doSign(IntReplenish intReplenish);
}
