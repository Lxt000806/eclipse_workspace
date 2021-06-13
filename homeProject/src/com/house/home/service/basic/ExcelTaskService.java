package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ExcelTask;
import com.house.home.entity.design.ResrCust;

public interface ExcelTaskService extends BaseService {

	/**ExcelTask分页信息
	 * @param page
	 * @param excelTask
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ExcelTask excelTask);
	/**
	 * 获取最新添加的任务pk
	 * @author cjg
	 * @date 2020-2-21
	 * @return
	 */
	public Integer getMaxExcelTaskPk();
	/**
	 * 添加需要导入的集合
	 * @author cjg
	 * @date 2020-2-21
	 * @return
	 */
	public void addExcelList(List<?> result,ExcelTask excelTask);
}
