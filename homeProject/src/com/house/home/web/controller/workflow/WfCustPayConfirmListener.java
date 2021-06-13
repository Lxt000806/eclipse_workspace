package com.house.home.web.controller.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.home.entity.basic.RcvAct;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.service.basic.RcvActService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustPayService;
import com.house.home.service.workflow.WfProcInstService;

@Component
@Transactional
public class WfCustPayConfirmListener implements TaskListener,ExecutionListener{

	private static final long serialVersionUID = 1L;

	@Autowired
	private WfProcInstService wfProcInstService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private RcvActService rcvActService;
	@Autowired
	private CustPayService custPayService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
	}

	/**
	 * 监听器
	 * 当审批通过时自动生成客户付款明细
	 */
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
	
		String preNo = "";
		String midNo = "";
		String sufNo = "";
		
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateExecution.getProcessInstanceId());
		String dateStr = "";
		// 获取流程变量
		Map<String, Object> map = delegateExecution.getVariables();
		
		// 审批通过才继续往下操作
		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		
		CustPay custPay = new CustPay();
		custPay.setLastUpdate(new Date());
		custPay.setLastUpdatedBy("1");
		custPay.setExpired("F");
		custPay.setActionLog("ADD");
		custPay.setWfProcInstNo(wfProcInst.getNo());
		custPay.setAddDate(new Date());
		custPay.setProcedureFee(0.0);
		custPay.setIsCheckOut("0");
		if(map != null && map.get("PayDate") != null){
			dateStr += map.get("PayDate").toString();
			String LoansCard = "";
			if(map.get("LoansCard") != null && StringUtils.isNotBlank(map.get("LoansCard").toString())){
				LoansCard = map.get("LoansCard").toString();
			}
			if(map.get("PayTime") != null && StringUtils.isNotBlank(map.get("PayTime").toString())){
				if("是".equals(LoansCard)){
					custPay.setRemarks("装修贷 " + map.get("PayActName").toString()
							+" "+map.get("PayTime").toString()+" 业主转账确认审批生成");
				} else {
					custPay.setRemarks(map.get("PayActName").toString()
							+" "+map.get("PayTime").toString()+" 业主转账确认审批生成");
				}
				
				String timeStr = map.get("PayTime").toString();
				if(timeStr.length()<8){
					timeStr += ":00";
				}
				dateStr += " "+timeStr;
			} else {
				custPay.setRemarks(map.get("PayActName").toString() + " 业主转账确认审批生成");
				dateStr += " 00:00:00";
			}
			if(StringUtils.isNotBlank(dateStr)){
				custPay.setDate(DateFormatString(dateStr));
			}
		}else {
			custPay.setRemarks("业主转账确认审批生成");
		}
		
		if(map.get("PayAmount") != null){
			custPay.setAmount(Double.parseDouble(map.get("PayAmount").toString()));
		}else{
			custPay.setAmount(0.0);
		}
		
		if(map.get("CustCode") != null){
			custPay.setCustCode(map.get("CustCode").toString());
			midNo = custPay.getCustCode().substring(2, custPay.getCustCode().length());
			sufNo = custPayService.getPayTimesByCustCode(custPay.getCustCode());
		}
		
		if(map.get("PayDetail") != null){
			Xtdm xtdm = xtdmService.getByIdAndNote("CPTRANTYPE", map.get("PayDetail").toString());
			if(xtdm != null){
				custPay.setType(xtdm.getCbm());
			}
		}
		
		if(map.get("RcvActCode") !=null){
			RcvAct rcvAct = rcvActService.getByCode(map.get("RcvActCode").toString());
			if(rcvAct != null){
				custPay.setRcvAct(rcvAct.getCode());
				if(StringUtils.isNotBlank(rcvAct.getCardId())){
					preNo = rcvAct.getCardId().substring(rcvAct.getCardId().length() -2,rcvAct.getCardId().length());   
				}
			}
		} else if(map.get("ReceiveActName") !=null){
			RcvAct rcvAct = rcvActService.getByDescr(map.get("ReceiveActName").toString(), "");
			if(rcvAct != null){
				custPay.setRcvAct(rcvAct.getCode());
				if(StringUtils.isNotBlank(rcvAct.getCardId())){
					preNo = rcvAct.getCardId().substring(rcvAct.getCardId().length() -2,rcvAct.getCardId().length());   
				}
			}
		}
		custPay.setPayNo(preNo + midNo + sufNo);
		
		wfProcInstService.save(custPay);
		
	}
	
	public static Date DateFormatString(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = null;
		}
        return date;
	}

}
