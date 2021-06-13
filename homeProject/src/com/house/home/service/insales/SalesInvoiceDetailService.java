package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.SalesInvoiceDetail;

public interface SalesInvoiceDetailService extends BaseService {

	/**SalesInvoiceDetail未发货分页信息
	 * @param page
	 * @param salesInvoiceDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalesInvoiceDetail salesInvoiceDetail);

	/**
	 *从销售单号导入页面
	 * @param page
	 * @param salesInvoiceDetail
	 * @return
	 */
	public Page<Map<String,Object>> findImportSalePageBySql(Page<Map<String,Object>> page, SalesInvoiceDetail salesInvoiceDetail);

	
	
	
	
	
}
