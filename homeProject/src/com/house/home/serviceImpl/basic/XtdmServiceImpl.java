package com.house.home.serviceImpl.basic;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.XtdmDao;
import com.house.home.entity.basic.Xtdm;
import com.house.home.service.basic.XtdmService;

@SuppressWarnings("serial")
@Service
public class XtdmServiceImpl extends BaseServiceImpl implements XtdmService {

	@Autowired
	private XtdmDao xtdmDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Xtdm xtdm){
		return xtdmDao.findPageBySql(page, xtdm);
	}
	
	public Xtdm getByThree(String id, String cbm, Integer ibm){
		return xtdmDao.getByThree(id, cbm, ibm);
	}

	public List<Xtdm> getById(String id) {
		return xtdmDao.getById(id);
	}

	public Xtdm getByIdAndCbm(String id, String cbm) {
		return xtdmDao.getByIdAndCbm(id, cbm);
	}

	@Override
	public Map<String, Object> getFtpData() {
		return xtdmDao.getFtpData();
	}

	@Override
	public Xtdm getByIdAndNote(String id, String note) {
		// TODO Auto-generated method stub
		return xtdmDao.getByIdAndNote(id, note);
	}

    @Override
    public List<Map<String, Object>> getSourcesOrChannels(int level, String code) {
        
        switch (level) {
        case 1:
            return xtdmDao.getSources();
        case 2:
            return xtdmDao.getChannels(code);
        default:
            return Collections.emptyList();
        }
    }

}
