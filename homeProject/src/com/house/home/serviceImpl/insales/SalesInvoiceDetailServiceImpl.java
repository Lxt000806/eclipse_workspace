package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.SalesInvoiceDetailDao;
import com.house.home.entity.insales.SalesInvoiceDetail;
import com.house.home.service.insales.SalesInvoiceDetailService;

@SuppressWarnings("serial")
@Service
public class SalesInvoiceDetailServiceImpl extends BaseServiceImpl implements SalesInvoiceDetailService {

	@Autowired
	private SalesInvoiceDetailDao salesInvoiceDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalesInvoiceDetail salesInvoiceDetail){
		return salesInvoiceDetailDao.findPageBySql(page, salesInvoiceDetail);
	}
	
	public Page<Map<String,Object>> findImportSalePageBySql(Page<Map<String,Object>> page, SalesInvoiceDetail salesInvoiceDetail){
		return salesInvoiceDetailDao.findImportSalePageBySql(page, salesInvoiceDetail);
	}

}
