package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.WHPosiBal;
import com.house.home.entity.insales.WareHouse;

public interface WHPosiBalService extends BaseService {

	/**WHPosiBal分页信息
	 * @param page
	 * @param wHPosiBal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHPosiBal wHPosiBal);

	/**
	 * @Description:  库位调整分页查询
	 * @author	created by zb
	 * @date	2018-8-15--下午5:29:00
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String,Object>> findPageByInnerSql(Page<Map<String, Object>> page,
			WareHouse wareHouse);

	/**
	 * @Description:  上下架数据查询
	 * @author	created by zb
	 * @date	2018-8-16--上午9:58:49
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String, Object>> findDataBySql(
			Page<Map<String, Object>> page, WareHouse wareHouse);

	/**
	 * @Description:  库位调整上下架存储过程
	 * @author	created by zb
	 * @date	2018-8-21--上午11:11:09
	 * @param wareHouse
	 * @return
	 */
	public Result doSave(WareHouse wareHouse);

	/**
	 * @Description:  批量移动分页信息
	 * @author	created by zb
	 * @date	2018-8-22--上午11:33:25
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String, Object>> findMovePageBySql(Page<Map<String, Object>> page,
			WareHouse wareHouse);

	/**
	 * @Description:  查询材料分页信息
	 * @author	created by zb
	 * @date	2018-8-23--上午11:11:13
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String, Object>> findItemPageBySql(Page<Map<String, Object>> page,
			WareHouse wareHouse);

	/**
	 * @Description:  导入Excel，检查数据是否在tItemWHBal表中存在
	 * @author	created by zb
	 * @date	2018-8-26--下午4:21:45
	 * @param code 仓库编号
	 * @param itCode 材料编号
	 * @return true-存在，false-不存在
	 */
	public Boolean hasItem(String code, String itCode);

	/**
	 * @Description:  检查是否存在该pk
	 * @author	created by zb
	 * @date	2018-8-26--下午5:42:48
	 */
	public boolean checkPk(String code, Integer pk);

}
