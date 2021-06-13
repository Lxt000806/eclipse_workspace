package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DesUtils;
import com.house.home.dao.basic.AdvertDao;
import com.house.home.dao.basic.CustManageDao;
import com.house.home.entity.basic.Advert;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.CustMapped;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.AdvertService;
import com.house.home.service.basic.CustManageService;

@SuppressWarnings("serial")
@Service
public class CustManageServiceImpl extends BaseServiceImpl implements CustManageService {

	@Autowired
	private CustManageDao custManageDao;

	@Override
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, CustAccount custAccount){
		return custManageDao.goJqGrid(page, custAccount);
	}
	
	@Override
	public List<Map<String, Object>> getCustomers(String phone){
		return custManageDao.getCustomers(phone);
	}
	
	@Override
	public Map<String, Object> doSave(CustAccount custAccount){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		Page<Map<String, Object>> page = new Page<Map<String, Object>>();
		page.setPageNo(1);
		page.setPageSize(-1);
		CustAccount conditionCustAccount = new CustAccount();
		conditionCustAccount.setMobile1(custAccount.getMobile1());
		conditionCustAccount.setExpired("T");
		custManageDao.goJqGrid(page, conditionCustAccount);
		if(page.getResult().size() > 0){
			returnMap.put("code", "0");
			returnMap.put("msg", "该手机号已被注册");
			return returnMap;
		}
		
		custAccount.setRegisterDate(new Date());
		custAccount.setLastUpdate(new Date());
		custAccount.setPicAddr("https://");
		custAccount.setExpired("F");
		custManageDao.save(custAccount);
		
		StringBuilder returnInfo = new StringBuilder();
		String[] codes = custAccount.getCodes().split(",");
		
		for(int i = 0;i < codes.length;i++){
			Customer customer = custManageDao.get(Customer.class, codes[i]);
			if(customer != null){
/*				if(customer.getCustAccountPk() == null || customer.getCustAccountPk() <=  0){
					customer.setCustAccountPk(custAccount.getPk());
					custManageDao.save(customer);
				}else{
					CustAccount bindCustAccount = custManageDao.get(CustAccount.class, customer.getCustAccountPk());
					if(bindCustAccount != null){
						returnInfo.append(customer.getAddress()+"已经被用户"+bindCustAccount.getMobile1()+"关联<br/>");
					}else{
						returnInfo.append(customer.getAddress()+"关联异常<br/>");
					}
				}*/
				CustMapped custMapped = new CustMapped();
				custMapped.setCustAccountPK(custAccount.getPk());
				custMapped.setCustCode(customer.getCode());
				custManageDao.save(custMapped);
			}
		}
		if(returnInfo.toString().length() > 0){
			returnInfo.append("其他楼盘关联成功");
		}
		returnMap.put("code", "1");
		returnMap.put("msg", returnInfo.toString());
		return returnMap;
	}
	
	@Override
	public Page<Map<String, Object>> goJqGridCustCode(Page<Map<String, Object>> page, CustAccount custAccount){
		return custManageDao.goJqGridCustCode(page, custAccount);
	}
	
	@Override
	public Map<String, Object> doUpdate(CustAccount custAccount){
		Map<String, Object> returnMap = new HashMap<String, Object>();

		CustAccount updateCustAccount = custManageDao.get(CustAccount.class, custAccount.getPk());
		
		if(updateCustAccount != null && StringUtils.isNotBlank(updateCustAccount.getMobile1()) && !updateCustAccount.getMobile1().equals(custAccount.getMobile1())){
			Page<Map<String, Object>> page = new Page<Map<String, Object>>();
			page.setPageNo(1);
			page.setPageSize(-1);
			CustAccount conditionCustAccount = new CustAccount();
			conditionCustAccount.setMobile1(custAccount.getMobile1());
			conditionCustAccount.setExpired("T");
			custManageDao.goJqGrid(page, conditionCustAccount);
			if(page.getResult().size() > 0){
				returnMap.put("code", "0");
				returnMap.put("msg", "该手机号已被注册");
				return returnMap;
			}
		}

		updateCustAccount.setMobile1(custAccount.getMobile1());
		updateCustAccount.setMm(DesUtils.encode(custAccount.getMm()));
		updateCustAccount.setExpired(custAccount.getExpired());
		updateCustAccount.setLastUpdate(new Date());
		updateCustAccount.setNickName(custAccount.getNickName());
		custManageDao.update(updateCustAccount);
		
		StringBuilder returnInfo = new StringBuilder();
		String[] addCodes = custAccount.getAddCodes().split(",");
		
		for(int i = 0;i < addCodes.length;i++){
			Customer customer = custManageDao.get(Customer.class, addCodes[i]);
			if(customer != null){
/*				if(customer.getCustAccountPk() == null || customer.getCustAccountPk() <=  0){
					customer.setCustAccountPk(custAccount.getPk());
					custManageDao.save(customer);
				}else{
					CustAccount bindCustAccount = custManageDao.get(CustAccount.class, customer.getCustAccountPk());
					if(bindCustAccount != null){
						returnInfo.append(customer.getAddress()+"已经被用户"+bindCustAccount.getMobile1()+"关联<br/>");
					}else{
						returnInfo.append(customer.getAddress()+"关联异常<br/>");
					}
				}*/

				CustMapped custMapped = new CustMapped();
				custMapped.setCustAccountPK(custAccount.getPk());
				custMapped.setCustCode(customer.getCode());
				custManageDao.save(custMapped);
			}
		}
		
		String[] delCodes = custAccount.getDelCodes().split(",");
		
		for(int i = 0;i < delCodes.length;i++){
			Customer customer = custManageDao.get(Customer.class, delCodes[i]);
			if(customer != null){
				//if(customer.getCustAccountPk() != null && customer.getCustAccountPk() > 0 && updateCustAccount.getPk().equals(customer.getCustAccountPk())){
	/*				customer.setCustAccountPk(null);
					custManageDao.update(customer);*/
					
					CustMapped custMapped = custManageDao.getCustMapped(updateCustAccount.getPk(), customer.getCode());
					custManageDao.delete(custMapped);
				//}
			}
		}
		if(returnInfo.toString().length() > 0){
			returnInfo.append("其他楼盘关联成功");
		}
		returnMap.put("code", "1");
		returnMap.put("msg", returnInfo.toString());
		return returnMap;
	}
	
}
