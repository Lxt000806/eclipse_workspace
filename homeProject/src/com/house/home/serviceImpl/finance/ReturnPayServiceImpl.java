package com.house.home.serviceImpl.finance;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.house.home.dao.design.CustomerDao;
import com.house.home.dao.finance.ReturnPayDao;
import com.house.home.dao.project.BaseItemChgDao;
import com.house.home.dao.project.ItemChgDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.ReturnPay;
import com.house.home.service.finance.ReturnPayService;

@SuppressWarnings("serial")
@Service 
public class ReturnPayServiceImpl extends BaseServiceImpl implements ReturnPayService {
	@Autowired
	private  ReturnPayDao returnPayDao;
	
	@Autowired
	private ItemChgDao itemChgDao;
	
	@Autowired
	private BaseItemChgDao baseItemChgDao;
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ReturnPay returnPay) {
		return returnPayDao.findPageBySql(page, returnPay);
	}
	
	@Override
	public Page<Map<String, Object>> findDetailByNo(
			Page<Map<String, Object>> page, ReturnPay returnPay) {
		return returnPayDao.findDetailByNo(page, returnPay);
	}

	@Override
	public ReturnPay getByNo(String no) {
		// TODO Auto-generated method stub
		return returnPayDao.getByNo(no);
	}
	@Override
	public Result doSave(ReturnPay returnPay) {
		return returnPayDao.doSave(returnPay);
	}

	@Override
	public Result doConfirmPass(ReturnPay returnPay) {
		// TODO Auto-generated method stub
		return returnPayDao.doConfirmPass(returnPay);
	}

	@Override
	public Result doConfirmCancel(ReturnPay returnPay) {
		// TODO Auto-generated method stub
		return returnPayDao.doConfirmCancel(returnPay);
	}

	@Override
	public List<Map<String, Object>> findReturnCustCode(ReturnPay returnPay) {
		// TODO Auto-generated method stub
		return returnPayDao.findReturnCustCode(returnPay);
	}

    @Override
    public Page<Map<String, Object>> settleRefundsGrid(Page<Map<String, Object>> page,
            ReturnPay returnPay) {

        return returnPayDao.settleRefundsGrid(page, returnPay);
    }

    @Override
    public void doBatchCheck(HttpServletRequest request, HttpServletResponse response,
            ReturnPay returnPay) {
        
        if (!returnPay.getType().equals("1")) {
            throw new IllegalStateException("非结算退款类型记录不能进行批量结算操作！");
        }
        
        List<Map<String, Object>> returnPayDetails = returnPayDao.findDetailsByNo(returnPay.getNo());
        
        // 一个客户可能会有多条退款记录，需要去掉重复记录
        Set<String> custCodes = new HashSet<String>();
        
        for (Map<String, Object> detail : returnPayDetails) {
            custCodes.add((String) detail.get("CustCode"));
        }
        
        for (String custCode : custCodes) {
            Customer customer = customerDao.get(Customer.class, custCode);
            
            if (!customer.getStatus().trim().equals("5")) {
                throw new IllegalStateException("客户" + custCode + "为非归档状态，不能进行结算操作！");
            }
            
            int itemChgCount = itemChgDao.getCountByCustCode(custCode);
            if (itemChgCount > 0) {
                throw new IllegalStateException("客户" + custCode + "存在申请状态的材料增减单，不能进行结算操作！");
            }
            
            int baseItemChgCount = baseItemChgDao.getCountByCustCode(custCode);
            if (baseItemChgCount > 0) {
                throw new IllegalStateException("客户" + custCode + "存在申请状态的基础增减单，不能进行结算操作！");
            }
            
            // 不能直接使用customer对象，因为Hibernate缓存与调用存储过程会有冲突
            Customer tempCustomer = new Customer();
            BeanUtils.copyProperties(customer, tempCustomer);
            
            tempCustomer.setM_umState("J");
            tempCustomer.setCheckDocumentNo(returnPay.getDocumentNo());
            tempCustomer.setCustCheckDate(customer.getCheckOutDate());
            
            Result result = customerDao.doGcwg_Khjs(tempCustomer);
            if (!result.isSuccess()) {
                throw new IllegalStateException(custCode + result.getInfo());
            }
        }

    }
	
}
