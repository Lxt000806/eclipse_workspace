package com.house.home.service.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.CustVisit;

public interface CustVisitService extends BaseService{

	/**
	 * 主页分页查询
	 * @param page
	 * @param custVisit
	 * @return 
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, 
			CustVisit custVisit);

	/**
	 * 开工回访
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findSearchCust1BySql(Page<Map<String, Object>> page,
			Customer customer);

	/**
	 * 中期和后期回访
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findSearchCust2BySql(Page<Map<String, Object>> page,
			Customer customer);

	/**
	 * 结算回访
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findSearchCust3BySql(Page<Map<String, Object>> page,
			Customer customer);

	/**
	 * 新增计划存储过程
	 * @author	created by zb
	 * @date	2018-7-17--下午4:55:52
	 * @param custVisit
	 * @return
	 */
	public Result doSave(CustVisit custVisit);

	/**
	 * TODO 通过code对工程信息进行查询
	 * @author	created by zb
	 * @date	2018-7-20--上午9:41:33
	 * @param page
	 * @param no
	 * @return
	 */
	public Page<Map<String, Object>> findPrjItemByCode(Page<Map<String, Object>> page, String code);

	/**
	 * 
	 * TODO 通过no查找客户问题
	 * @author	created by zb
	 * @date	2018-7-20--上午11:54:57
	 * @param page
	 * @param no
	 * @return
	 */
	public Page<Map<String, Object>> findCustProblemByNo(Page<Map<String, Object>> page, String no);

	/**
	 * @Description: TODO 回访处理储存过程
	 * @author	created by zb
	 * @date	2018-8-5--下午3:18:43
	 * @param custVisit
	 * @return
	 */
	public Result doUpdate(CustVisit custVisit);

	/**
	 * @Description: TODO 明细列表分页查询
	 * @author	created by zb
	 * @date	2018-8-6--上午11:42:22
	 * @param page
	 * @param custVisit
	 * @param custProblem
	 * @return
	 */
	public Page<Map<String, Object>> findDetailListPageBySql(Page<Map<String, Object>> page,
			CustVisit custVisit, CustProblem custProblem);

}
