package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.CustTax;

public interface CustTaxService extends BaseService{

	/**
	 * @Description:  税务信息登记分页查询
	 * @author	created by zb
	 * @date	2018-8-10--下午4:52:19
	 * @param page
	 * @param custTax
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, CustTax custTax,
			Customer customer);

	/**
	 * @Description:  税务信息登记存储过程
	 * @author	created by zb
	 * @date	2018-8-14--上午10:00:33
	 * @param custTax
	 * @return
	 */
	public Result doSave(CustTax custTax);

	/**
	 * @Description:  发票明细查询
	 * @author	created by zb
	 * @date	2018-8-14--上午11:17:02
	 * @param page
	 * @param custTax
	 */
	public Page<Map<String, Object>> findDetailByCode(Page<Map<String, Object>> page, CustTax custTax);

	/**
	 * @Description:  发票查询jqGrid
	 * @author	created by zb
	 * @date	2018-8-14--下午3:55:38
	 * @param page
	 * @param custTax
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findInvoicePageBySql(Page<Map<String, Object>> page,
			CustTax custTax, Customer customer);
	
	/**
	 * @Description:  显示客户付款信息
	 * @author	created by zb
	 * @date	2018-9-4--上午11:37:01
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String, Object>> findCustPayPageBySql(Page<Map<String, Object>> page,
			String custCode);
	/**
	 * 劳务分包开票信息
	 * @author cjg
	 * @date 2019-9-20
	 * @param page
	 * @param custTax
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findLaborPageBySql(Page<Map<String, Object>> page, CustTax custTax);
	/**
	 * 导入开票明细
	 * @author	created by zb
	 * @date	2019-12-10--下午3:27:11
	 * @param custTax
	 * @return
	 */
	public Result doCustInvoice(CustTax custTax);
	
	/**
	 * 导入劳务分包开票
	 * @param custTax
	 * @return
	 */
	public Result doCustLaborInvoice(CustTax custTax);
	
	/**
	 * 劳务分包查询
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> goLaborCtrlListJqGrid(Page<Map<String, Object>> page, Customer customer);
	
	/**
	 * 查询劳务分包公司列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findLaborCompanyList();
}
