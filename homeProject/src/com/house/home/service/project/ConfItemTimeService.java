package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ConfItemTime;
import com.house.home.entity.project.ConfItemType;

public interface ConfItemTimeService extends BaseService {

	/**ConfItemTime分页信息
	 * @param page
	 * @param confItemTime
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemTime confItemTime);
	/**
	 * 返回需要添加记录的节点编号
	 * @param custCode
	 * @return
	 */
	public List<Map<String,Object>>  findConfItemCodeListBySql(String custCode);
}
