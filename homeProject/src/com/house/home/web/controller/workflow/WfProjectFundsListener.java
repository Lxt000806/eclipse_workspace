package com.house.home.web.controller.workflow;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.home.entity.design.CustPay;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcess;
import com.house.home.service.design.CustPayService;
import com.house.home.service.workflow.WfProcInstService;

@Component
@Transactional
public class WfProjectFundsListener implements ExecutionListener{

	private static final long serialVersionUID = 1L;

	@Autowired
	private WfProcInstService wfProcInstService;
	@Autowired
	private CustPayService custPayService;
	/**
	 * 监听器
	 * 当审批通过时自动生成客户付款明细
	 */
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		WfProcess wfProcess = new WfProcess();
		String czybh = "1";
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateExecution.getProcessInstanceId());
		if(wfProcInst != null){
			wfProcess = wfProcInstService.get(WfProcess.class, wfProcInst.getWfProcNo());
		} else {
			return;
		}
		
		if(wfProcess == null){
			return;
		}
		
		// 获取流程变量
		Map<String, Object> map = delegateExecution.getVariables();
		// 审批通过才继续往下操作
		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		String custCode =  map.get("CustCode").toString();
		String newCustCode = "";
		String remarks= "";
		String comment = "";
		/*if(map.get("LAST_COMMENT") != null && StringUtils.isNotBlank(map.get("LAST_COMMENT").toString())){
			comment = map.get("LAST_COMMENT").toString()+",";
		}*/
		
		if(StringUtils.isNotBlank(wfProcInst.getActProcInstId())){
			comment = wfProcInstService.getTaskCommntByPIIDTaskName(wfProcInst.getActProcInstId(),"财务会计审核");
			if(!"".equals(comment)){
				comment += ",";
			}
		}
		
		if(map.get("PROC_LASTCZY") != null){
			czybh = map.get("PROC_LASTCZY").toString();
		}
		
		Double amount = 0.0;
		if("004".equals(wfProcess.getNo())){
			amount = Double.parseDouble(map.get("ConfirmAmount").toString());
			newCustCode =  map.get("NewCustCode").toString();
			remarks = comment+"由 "+custCode+" 转至 "+newCustCode;// 下定转独立销售的备注格式： ‘‘会计审批说明’ + ‘由‘转出客户编号’转至‘转入客户编号’’
		} else {
			amount = custPayService.getProcConfirmAmount(wfProcInst.getNo());
			remarks = comment+wfProcInstService.getWfPrjCardInfo(wfProcInst.getWfProcNo(), wfProcInst.getNo());
		}
		// 退款填的是正数  付款记录取负数
		if(amount > 0){
			amount = -1 * amount;
		}
		
		CustPay custPay = new CustPay();
		custPay.setCustCode(map.get("CustCode").toString());
		custPay.setRemarks(remarks);
		custPay.setWfProcInstNo(wfProcInst.getNo());
		custPay.setDate(new Date());
		custPay.setAmount(amount);
		custPay.setType("2");
		custPay.setLastUpdate(new Date());
		custPay.setLastUpdatedBy(czybh);
		custPay.setExpired("F");
		custPay.setActionLog("ADD");
		custPay.setIsCheckOut("1");
		custPay.setCheckSeq(0);
		custPay.setAddDate(new Date());
		custPay.setProcedureFee(0.0);
		wfProcInstService.save(custPay);
		
		if("004".equals(wfProcess.getNo())){
			amount = Double.parseDouble(map.get("NewConfirmAmount").toString());
			CustPay inCustPay = new CustPay();
			BeanUtils.copyProperties(inCustPay, custPay);
			inCustPay.setCustCode(map.get("NewCustCode").toString());
			inCustPay.setRemarks(remarks);
			inCustPay.setAmount(amount);
			inCustPay.setPk(null);
			custPayService.save(inCustPay);
		}
	}
}
