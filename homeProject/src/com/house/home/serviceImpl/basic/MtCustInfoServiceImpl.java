package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.MtAddCustInfoEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.dao.basic.MtCustInfoDao;
import com.house.home.entity.basic.MtCustInfo;
import com.house.home.entity.basic.MtRegion;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.MtCustInfoService;

@SuppressWarnings("serial")
@Service
public class MtCustInfoServiceImpl extends BaseServiceImpl implements MtCustInfoService {

	@Autowired
	private MtCustInfoDao mtCustInfoDao;


	@Override
	public void addMtCustInfo(MtAddCustInfoEvt evt, BaseResponse respon){
		
		MtRegion mtRegion = this.mtCustInfoDao.get(MtRegion.class, evt.getRegionCode());
		if(mtRegion == null){
			mtRegion = new MtRegion();
			mtRegion.setRegionCode(evt.getRegionCode());
			mtRegion.setDescr(evt.getRegionDescr());
			mtRegion.setBelongRegionCode("");
			mtRegion.setLastUpdate(new Date());
			mtRegion.setLastUpdatedBy("1");
			mtRegion.setExpired("F");
			mtRegion.setActionLog("ADD");
			this.mtCustInfoDao.save(mtRegion);
		}
		
		MtRegion mtRegion2 = this.mtCustInfoDao.get(MtRegion.class, evt.getRegion2Code());
		if(mtRegion2 == null){
			mtRegion2 = new MtRegion();
			mtRegion2.setRegionCode(evt.getRegion2Code());
			mtRegion2.setDescr(evt.getRegion2());
			mtRegion2.setBelongRegionCode(evt.getRegionCode());
			mtRegion2.setLastUpdate(new Date());
			mtRegion2.setLastUpdatedBy("1");
			mtRegion2.setExpired("F");
			mtRegion2.setActionLog("ADD");
			this.mtCustInfoDao.save(mtRegion2);
		}
		
		if(this.existsCustCodeMT(evt.getCustCodeMT())){
			respon.setReturnCode("400002");
			respon.setReturnInfo("编号"+evt.getCustCodeMT()+"重复添加");
			return;
		}
		
		MtCustInfo mtCustInfo = new MtCustInfo();
		mtCustInfo.setStatus("1");
		mtCustInfo.setCrtDate(new Date());
		mtCustInfo.setRegionCode(evt.getRegionCode());
		mtCustInfo.setRegionDescr(evt.getRegionDescr());
		mtCustInfo.setRegion2(evt.getRegion2());
		mtCustInfo.setShopName(evt.getShopName());
		mtCustInfo.setManage(evt.getManage());
		mtCustInfo.setManagePhone(evt.getManagePhone());
		mtCustInfo.setCustCodeMT(evt.getCustCodeMT());
		mtCustInfo.setAddress(evt.getAddress());
		mtCustInfo.setArea(evt.getArea());
		mtCustInfo.setIsFixtures(evt.getIsFixtures());
		mtCustInfo.setCustDescr(evt.getCustDescr());
		mtCustInfo.setCustPhone(evt.getCustPhone());
		mtCustInfo.setLayout(evt.getLayout());
		mtCustInfo.setGender(evt.getGender());
		mtCustInfo.setRemark(evt.getRemark());
		mtCustInfo.setLastUpdate(new Date());
		mtCustInfo.setLastUpdatedBy("1");
		mtCustInfo.setExpired("F");
		mtCustInfo.setActionLog("ADD");
		mtCustInfo.setRegion2Code(evt.getRegion2Code());
		this.mtCustInfoDao.save(mtCustInfo);
		respon.setReturnInfo("保存成功");
	}
	
	@Override
	public boolean existsCustCodeMT(String custCodeMT){
		return this.mtCustInfoDao.existsCustCodeMT(custCodeMT);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, MtCustInfo mtCustInfo) {
		return this.mtCustInfoDao.findPageBySql(page, mtCustInfo);
	}

	@Override
	public void cancel(MtCustInfo mtCustInfo) {
		this.mtCustInfoDao.cancel(mtCustInfo);
	}
	
	@Override
	public Map<String, Object> getMtPerfNoSend(){
		return this.mtCustInfoDao.getMtPerfNoSend();
	}

	@Override
	public Page<Map<String, Object>> goMtRegionJqGrid(Page<Map<String, Object>> page) {
		return this.mtCustInfoDao.goMtRegionJqGrid(page);
	}

	@Override
	public void setCzy(MtCustInfo mtCustInfo) {
		this.mtCustInfoDao.setCzy(mtCustInfo);
	}
	
	@Override
	public Page<Map<String, Object>> findCustConPageBySql(Page<Map<String, Object>> page, MtCustInfo mtCustInfo) {
		return this.mtCustInfoDao.findCustConPageBySql(page, mtCustInfo);
	}

	@Override
	public String getCustCodeByPk(MtCustInfo mtCustInfo) {
		
		return mtCustInfoDao.getCustCodeByPk(mtCustInfo);
	}

	@Override
	public List<Map<String, Object>> getCustPerfData(Customer customer) {
		
		return mtCustInfoDao.getCustPerfData(customer);
	}

	
}

