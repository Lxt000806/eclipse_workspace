package com.house.home.web.controller.workflow;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.house.framework.bean.Result;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.service.design.CustomerService;
import com.house.home.service.workflow.WfProcInstService;

@Component
public class WfCustToConstructionListener implements Serializable, TaskListener,JavaDelegate{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private WfProcInstService wfProcInstService;


	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println(123);
	}


	@Override
	public void execute(DelegateExecution delegateexecution) throws Exception {
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateexecution.getProcessInstanceId());
		
		String custCode = "";
		String lastUpdatedBy = "";
		Customer customer = new Customer();

		// 获取流程变量
		Map<String, Object> map = delegateexecution.getVariables();
		if(map.get("CustCode") != null){
			custCode = map.get("CustCode").toString();
		} else {
			
			return;
		}
		if(wfProcInst != null){
			lastUpdatedBy = wfProcInst.getStartUserId();
		} else {
			lastUpdatedBy = "1";
		}
		
		// 审批通过才继续往下操作
		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		

		/*
			String[] str={fourPayMap.get("FirstPay").toString(),
							fourPayMap.get("SecondPay").toString(),
							fourPayMap.get("ThirdPay").toString(),
							fourPayMap.get("FourPay").toString(),
							designFeeType};
			
		*/
		CustType custType = new CustType();
		
		if(StringUtils.isNotBlank(custCode)){
			if(customer != null){
				customer = customerService.get(Customer.class, custCode);
				Customer customerTemp = new Customer();
				
				Map<String , Object> fourPayMap=customerService.getFourPay(custCode, "1");
		        Map<String, Object> maxDiscMap = customerService.getMaxDiscByCustCode(custCode);

				customerTemp.setFirstPay(Double.parseDouble(fourPayMap.get("FirstPay").toString()));
				customerTemp.setSecondPay(Double.parseDouble(fourPayMap.get("SecondPay").toString()));
				customerTemp.setThirdPay(Double.parseDouble(fourPayMap.get("ThirdPay").toString()));
				customerTemp.setFourPay(Double.parseDouble(fourPayMap.get("FourPay").toString()));
				
				custType = customerService.get(CustType.class, customer.getCustType());
				
				customerTemp.setM_umState("S");
				customerTemp.setFromStatus(customer.getStatus());
				customerTemp.setToStatus("4");
				customerTemp.setConPhone(customer.getMobile1());
				customerTemp.setLastUpdatedBy(lastUpdatedBy);
				customerTemp.setIsInternal(customer.getIsInternal());
				customerTemp.setTileStatus(customer.getTileStatus());
				customerTemp.setBathStatus(customer.getBathStatus());
				customerTemp.setPayType(customer.getPayType());
				customerTemp.setConId(customer.getConId());
				customerTemp.setPerfMarkup(customer.getPerfMarkup());
				customerTemp.setToConstructDate(new Date());
				if(StringUtils.isNotBlank(customer.getPayeeCode())){
					customerTemp.setPayeeCode(customer.getPayeeCode());
				} else {
					customerTemp.setPayeeCode(custType.getPayeeCode());
				}
				customerTemp.setFrontEndDiscAmount(customer.getFrontEndDiscAmount());
				customerTemp.setIsInitSign(customer.getIsInitSign());
				customerTemp.setCmpDiscAmount(customer.getCmpDiscAmount());
				customerTemp.setDesignerMaxDiscAmount(Double.parseDouble(maxDiscMap.get("DirectorMaxDiscAmount").toString()));
				customerTemp.setDirectorMaxDiscAmount(Double.parseDouble(maxDiscMap.get("DirectorMaxDiscAmount").toString()));
				customerTemp.setDesignRiskFund(custType.getDesignRiskFund());
				customerTemp.setPrjManRiskFund(customer.getPrjManRiskFund());
				customerTemp.setCode(customer.getCode());
				
				customer = new Customer();
				Result result =	customerService.doWfProcSaveZsg(customerTemp);
				if(!result.isSuccess()){
					delegateexecution.setVariable("FAILEDREASON", result.getInfo());
					return;
				}
			}
		}
	}

}
