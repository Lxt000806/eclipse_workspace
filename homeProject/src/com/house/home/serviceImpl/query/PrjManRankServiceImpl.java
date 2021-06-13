package com.house.home.serviceImpl.query;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjManRankDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjManRankService;

@SuppressWarnings("serial")
@Service 
public class PrjManRankServiceImpl extends BaseServiceImpl implements PrjManRankService {
	@Autowired
	private  PrjManRankDao prjManRankDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjManRankDao.findPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee) {
		if (StringUtils.isNotBlank(employee.getViewType())) {
			if ("C".equals(employee.getViewType())) {	//完工明细
				return prjManRankDao.findCheckOutDetailPageBySql(page, employee);
			} else if ("A".equals(employee.getViewType())) {	//翻单明细
				return prjManRankDao.findAgainDetailPageBySql(page,employee);
			} else if ("T".equals(employee.getViewType())) {	//二次销售明细
				return prjManRankDao.findTwoSaleDetailPageBySql(page,employee);
			} else if ("S".equals(employee.getViewType())) {	//安排单量明细
				return prjManRankDao.findSetUpDetailPageBySql(page,employee);
			} else if ("W".equals(employee.getViewType())) {	//在建明细
				return prjManRankDao.findWorkingDetailPageBySql(page,employee);
			}
		}
		return page;
	}

}
