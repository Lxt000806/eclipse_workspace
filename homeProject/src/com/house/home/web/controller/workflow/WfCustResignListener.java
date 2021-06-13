package com.house.home.web.controller.workflow;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.framework.bean.Result;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.CustomerService;

@Component
@Transactional
public class WfCustResignListener implements ExecutionListener{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerService customerService;

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		String custCode = "";
		String comment = "";
		String resignNotify = "";
		String lastUpdatedBy = "";
		Customer customer = new Customer();

		// 获取流程变量
		Map<String, Object> map = delegateExecution.getVariables();
		if(map.get("CustCode") != null){
			custCode = map.get("CustCode").toString();
		}
		if(map.get("LAST_COMMENT") != null){
			comment = map.get("LAST_COMMENT").toString();
		}
		if(map.get("PROC_LASTCZY") != null){
			lastUpdatedBy = map.get("PROC_LASTCZY").toString();
		} else {
			lastUpdatedBy = "1";
		}
		
		// 审批通过才继续往下操作
		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		String endCode = customerService.getEndCodeByCustCode(custCode);
		
		if(StringUtils.isNotBlank(custCode)){
			if(customer != null){
				resignNotify = customerService.resignNotify(custCode, "4", endCode);
				// notify：之前判断能进行重签操作返回的信息
				if("notify".equals(resignNotify) || StringUtils.isBlank(resignNotify)){
					customer.setCode(custCode);
					customer.setRemarks(comment);
					customer.setFromStatus("4");
					customer.setToStatus("3");
					customer.setLastUpdatedBy(lastUpdatedBy);
					Result result =	customerService.doWfCustReSign(customer);
				} else {
					delegateExecution.setVariable("CANTRESIGNREASON", resignNotify);
					return;
				}
			}
		}
		
	}

}
