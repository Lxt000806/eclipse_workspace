package com.house.home.serviceImpl.project;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.dao.project.IntReplenishDTDao;
import com.house.home.dao.project.IntReplenishDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.project.IntReplenish;
import com.house.home.entity.project.IntReplenishDT;
import com.house.home.entity.project.Worker;
import com.house.home.service.project.IntReplenishService;

@SuppressWarnings("serial")
@Service
public class IntReplenishServiceImpl extends BaseServiceImpl implements IntReplenishService {
	
	@Autowired
	private IntReplenishDTDao intReplenishDTDao;
	@Autowired
	private IntReplenishDao intReplenishDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntReplenish intReplenish){
		return intReplenishDao.findPageBySql(page, intReplenish);
	}

	@Override
	public Result doSave(IntReplenish intReplenish) {
		return intReplenishDao.doSave(intReplenish);
	}

	@Override
	public void updateReadyDate(Integer pk) {
		intReplenishDao.updateReadyDate(pk);
	}

	@Override
	public IntReplenish goArrange(Page<Map<String,Object>> page, String no) {
		IntReplenish intReplenish = this.get(IntReplenish.class, no);
		if (StringUtils.isNotBlank(intReplenish.getServiceManType()) && StringUtils.isNotBlank(intReplenish.getServiceMan())) {
			if ("1".equals(intReplenish.getServiceManType())) {
				Worker Worker=this.get(Worker.class, intReplenish.getServiceMan());
				intReplenish.setServiceManDescr(Worker.getNameChi());
			} else {
				Employee employee = this.get(Employee.class, intReplenish.getServiceMan());
				intReplenish.setServiceManDescr(employee.getNameChi());
			}
		}
		IntReplenishDT intReplenishDT = new IntReplenishDT();
		intReplenishDT.setNo(no);
		intReplenishDTDao.findNoPageBySql(page, intReplenishDT);
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getTotalCount()>0) {
			map = page.getResult().get(0);
			intReplenish.setAddress(map.get("address").toString());
			intReplenish.setWorker(map.get("workercode")==null?"":map.get("workercode").toString());
			intReplenish.setWorkerDescr(map.get("workerdescr")==null?"":map.get("workerdescr").toString());
			intReplenish.setEndDate(map.get("enddate")==null?null:DateUtil.DateFormatString(map.get("enddate").toString()));
		}
		return intReplenish;
	}

	@Override
	public String getIsHad(IntReplenish intReplenish) {
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		Page<Map<String,Object>> hadDetail = intReplenishDTDao.getHadDetail(page, intReplenish);
		int tjNum=0;int yxdNum=0;int ydhNum=0;
		for (int i = 0; i < hadDetail.getTotalCount(); i++) {
			if("提交".equals(hadDetail.getResult().get(i).get("statusdescr"))){
				tjNum++;
			}
			if("已下单".equals(hadDetail.getResult().get(i).get("statusdescr"))){
				yxdNum++;
			}
			if("已到货".equals(hadDetail.getResult().get(i).get("statusdescr"))){
				ydhNum++;
			}
		}
		String hadSameCustIntReplenishTip = "";
		if(tjNum>0||yxdNum>0||ydhNum>0){
			hadSameCustIntReplenishTip +="同楼盘还有补货单，";
			if(tjNum>0){
				hadSameCustIntReplenishTip +="提交"+tjNum+"单，";
			}
			if(yxdNum>0){
				hadSameCustIntReplenishTip +="已下单"+yxdNum+"单，";
			}
			if(ydhNum>0){
				hadSameCustIntReplenishTip +="已到货"+ydhNum+"单，";
			}
			hadSameCustIntReplenishTip = hadSameCustIntReplenishTip.substring(0, hadSameCustIntReplenishTip.length()-1)+"。";
		}
		
		return hadSameCustIntReplenishTip;
	}

	@Override
	public Page<Map<String, Object>> findHadPageByNo(
			Page<Map<String, Object>> page, IntReplenish intReplenish) {
		IntReplenish ir = this.get(IntReplenish.class, intReplenish.getNo());
		return intReplenishDTDao.getHadDetail(page, ir);
	}
	
	@Override
	public void updateArrivedIntReplenish(IntReplenish intReplenish) {
		intReplenishDao.updateArrivedIntReplenish(intReplenish);
	}
	
	@Override
	public Result doSign(IntReplenish intReplenish) {
		return intReplenishDao.doSign(intReplenish);
	}

}
