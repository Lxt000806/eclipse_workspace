package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.WHPosiTranDao;
import com.house.home.entity.insales.WHPosiTran;
import com.house.home.service.insales.WHPosiTranService;

@SuppressWarnings("serial")
@Service
public class WHPosiTranServiceImpl extends BaseServiceImpl implements WHPosiTranService {

	@Autowired
	private WHPosiTranDao wHPosiTranDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHPosiTran wHPosiTran){
		return wHPosiTranDao.findPageBySql(page, wHPosiTran);
	}

}
