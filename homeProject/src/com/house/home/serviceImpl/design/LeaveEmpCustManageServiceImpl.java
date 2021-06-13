package com.house.home.serviceImpl.design;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.design.LeaveEmpCustManageDao;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.LeaveEmpCustManageService;

@SuppressWarnings("serial")
@Service
public class LeaveEmpCustManageServiceImpl extends BaseServiceImpl implements LeaveEmpCustManageService {
	@SuppressWarnings({ "unchecked" })
	@Autowired
	private LeaveEmpCustManageDao leaveEmpCustManageDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer ,UserContext uc) {
		return leaveEmpCustManageDao.findPageBySql(page, customer,uc);
	}

	@Override
	public Result doChgStakeholder(List<Map<String, Object>> list,String showType,String stakeholder,String lastUpdatedBy) {
		CustStakeholder custStakeholder = new CustStakeholder();
		    Result result=null;
			custStakeholder.setM_umState("M");
			custStakeholder.setLastUpdate(new Date());
			custStakeholder.setLastUpdatedBy(lastUpdatedBy);
			custStakeholder.setExpired("F");
			custStakeholder.setIsRight(0);
			Integer successNum=0,failNum=0;
			String errInfo="";
			for(int i=0; i<list.size(); i++){
				custStakeholder.setCustCode(list.get(i).get("custcode").toString());
				custStakeholder.setEmpCode(stakeholder);
				if("1".equals(showType)){
					custStakeholder.setPk(Integer.parseInt(list.get(i).get("dpk").toString()));
					custStakeholder.setRole("00");
				}else if("2".equals(showType)){
					custStakeholder.setPk(Integer.parseInt(list.get(i).get("bpk").toString()));
					custStakeholder.setRole("01");
				}
				result=leaveEmpCustManageDao.updateStakeholder(custStakeholder);
				if("".equals(result.getCode())){
					successNum++;
				}else{
					failNum++;
					errInfo="【"+list.get(i).get("address").toString()+"】"+result.getInfo();
				}
			}
			result.setInfo("修改成功"+Integer.toString(successNum)+"条,失败"+Integer.toString(failNum)+"条。"+errInfo);
			return result;
	}
	
}
