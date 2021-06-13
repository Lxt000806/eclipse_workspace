package com.house.home.serviceImpl.commi;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiCycleDao;
import com.house.home.entity.commi.CommiCustStakeholderSuppl;
import com.house.home.entity.commi.CommiCycle;
import com.house.home.service.commi.CommiCycleService;

@SuppressWarnings("serial")
@Service
public class CommiCycleServiceImpl extends BaseServiceImpl implements CommiCycleService {

	@Autowired
	private CommiCycleDao commiCycleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCycle commiCycle){
		return commiCycleDao.findPageBySql(page, commiCycle);
	}

	@Override
	public String checkStatus(String no) {
		return commiCycleDao.checkStatus(no);
	}

	@Override
	public String isExistsPeriod(String no, Integer mon) {
		return commiCycleDao.isExistsPeriod(no, mon);
	}

	@Override
	public void doComplete(String no) {
		commiCycleDao.doComplete(no);
	}

	@Override
	public void doReturn(String no) {
		commiCycleDao.doReturn(no);
	}

	@Override
	public Map<String, Object> doCount(CommiCycle commiCycle) {
		return commiCycleDao.doCount(commiCycle);
	}

    @Override
    public Page<Map<String, Object>> findSupplierRebatePageBySql(Page<Map<String, Object>> page,
            CommiCustStakeholderSuppl commiCustStakeholderSuppl) {

        return commiCycleDao.findSupplierRebatePageBySql(page, commiCustStakeholderSuppl);
    }

    @Override
    public void doImportExcelForSupplierRebate(List<CommiCustStakeholderSuppl> objs, String czybh) {
        
        if (objs.size() == 0) {
            throw new RuntimeException("无要导入的数据！");
        }
        
        Date now = new Date();
        for (CommiCustStakeholderSuppl commiCustStakeholderSuppl : objs) {
            commiCustStakeholderSuppl.setActionLog("ADD");
            commiCustStakeholderSuppl.setLastUpdate(now);
            commiCustStakeholderSuppl.setLastUpdatedBy(czybh);
            commiCustStakeholderSuppl.setExpired("F");
            
            save(commiCustStakeholderSuppl);
        }
        
    }

	@Override
	public String getMaxMon() {
		return commiCycleDao.getMaxMon();
	}

}
