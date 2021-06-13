package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.PrjJobDao;
import com.house.home.dao.project.PrjJobPhotoDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.project.PrjJob;
import com.house.home.entity.project.PrjJobPhoto;
import com.house.home.entity.project.SupplJob;
import com.house.home.service.project.PrjJobService;

@SuppressWarnings("serial")
@Service
public class PrjJobServiceImpl extends BaseServiceImpl implements PrjJobService {

	@Autowired
	private PrjJobDao prjJobDao;
	@Autowired
	private PrjJobPhotoDao prjJobPhotoDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjJob prjJob){
		return prjJobDao.findPageBySql(page, prjJob);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_forClient(
			Page<Map<String, Object>> page, PrjJob prjJob) {
		return prjJobDao.findPageBySql_forClient(page,prjJob);
	}

	@Override
	public Map<String, Object> getByNo(String id) {
		return prjJobDao.getByNo(id);
	}

	@Override
	public boolean addPrjJob(PrjJob prjJob) {
		boolean result = true;
		try{
			String no = prjJobDao.getSeqNo("tPrjJob");
			PrjJob pc = new PrjJob();
			pc.setNo(no);
			pc.setStatus(prjJob.getStatus());
			pc.setCustCode(prjJob.getCustCode());
			pc.setItemType1(prjJob.getItemType1());
			pc.setJobType(prjJob.getJobType());
			pc.setAppCzy(prjJob.getAppCzy());
			pc.setDealCzy(prjJob.getDealCzy());
			pc.setRemarks(prjJob.getRemarks());
			pc.setAddress(prjJob.getAddress());
			pc.setDate(new Date());
			pc.setEndCode("0");
			pc.setLastUpdate(new Date());
			pc.setLastUpdatedBy(prjJob.getAppCzy());
			pc.setActionLog("ADD");
			pc.setExpired("F");
			pc.setWarBrand(prjJob.getWarBrand());
			pc.setCupBrand(prjJob.getCupBrand());
			prjJobDao.save(pc);
			String str = prjJob.getPhotoString();
			if (StringUtils.isNotBlank(str)){
				String[] arr = str.split(",");
				for (String photoName: arr){
					PrjJobPhoto prjJobPhoto = new PrjJobPhoto();
					prjJobPhoto.setPrjJobNo(no);
					prjJobPhoto.setPhotoName(photoName);
					prjJobPhoto.setType("1");
					prjJobPhoto.setLastUpdate(new Date());
					prjJobPhoto.setLastUpdatedBy(prjJob.getAppCzy());
					prjJobPhoto.setActionLog("ADD");
					prjJobPhoto.setExpired("F");
					prjJobPhoto.setIsSendYun("1");
					prjJobPhoto.setSendDate(new Date());
					prjJobPhotoDao.save(prjJobPhoto);
					prjJobPhoto = null;
				}
			}
			if("1".equals(prjJob.getFromInfoAdd())){
				PersonMessage pm = prjJobDao.get(PersonMessage.class, prjJob.getInfoPk());
				pm.setRcvDate(new Date());
				pm.setRcvStatus("1");
				pm.setDealNo(no);
				prjJobDao.update(pm);
			}
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public boolean updatePrjJob(PrjJob prjJob) {
		boolean result = true;
		try{
			String no = prjJob.getNo();
			prjJobDao.update(prjJob);
			prjJobPhotoDao.deleteByPrjJobNo(no,"");
			String str = prjJob.getPhotoString();
			if (StringUtils.isNotBlank(str)){
				String[] arr = str.split(",");
				for (String photoName: arr){
					PrjJobPhoto prjJobPhoto = new PrjJobPhoto();
					prjJobPhoto.setPrjJobNo(no);
					prjJobPhoto.setPhotoName(photoName);
					prjJobPhoto.setType("1");
					prjJobPhoto.setLastUpdate(new Date());
					prjJobPhoto.setLastUpdatedBy(prjJob.getAppCzy());
					prjJobPhoto.setActionLog("ADD");
					prjJobPhoto.setExpired("F");
					prjJobPhoto.setIsSendYun("1");
					prjJobPhoto.setSendDate(new Date());
					prjJobPhotoDao.save(prjJobPhoto);
					prjJobPhoto = null;
				}
			}
		}catch(Exception e){
			result = false;
		}
		return result;
	}

	@Override
	public Page<Map<String, Object>> getDealPrjJobList(
			Page<Map<String, Object>> page, PrjJob prjJob) {
		return prjJobDao.getDealPrjJobList(page,prjJob);
	}
	public Page<Map<String, Object>> getPrjJobReceiveList(
			Page<Map<String, Object>> page, PrjJob prjJob,String itemRight) {
		return prjJobDao.getPrjJobReceiveList(page,prjJob,itemRight);
	}
	@Override
	public String getDefaultDealMan(String custCode, String role, String jobType) {
		return prjJobDao.getDefaultDealMan(custCode,role,jobType);
	}

	@Override
	public void dealPrjJob(PrjJob prjJob, String photoName) {
		if(StringUtils.isNotBlank(photoName)){
			String[] photoNameList=photoName.split(",");
			for(String str:photoNameList){
				PrjJobPhoto photo=new PrjJobPhoto();
				photo.setPrjJobNo(prjJob.getNo());
				photo.setType("2");
				photo.setPhotoName(str);
				photo.setLastUpdate(new Date());
				photo.setLastUpdatedBy(prjJob.getDealCzy());
				photo.setActionLog("Add");
				photo.setExpired("F");
				photo.setIsSendYun("1");
				photo.setSendDate(new Date());
				prjJobPhotoDao.save(photo);
			}
			
		}
		prjJobDao.update(prjJob);
	}

	public  void receivePrjJob(PrjJob prjJob){
		prjJobDao.update(prjJob);
	}

	@Override
	public boolean isNeedReq(String custCode,String itemType1) {
		boolean result=false;
		List<Map<String,Object>> list = prjJobDao.isNeedReq(custCode,itemType1);
		if(list.size()>0){
			result = true;
		}
		return result;
	}

	//  add by hc 集成测量分析   begin 2017/11/21
	@Override
	public Page<Map<String, Object>> findCheckPageBySql(
			Page<Map<String, Object>> page, PrjJob prjJob, UserContext uc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlTJFS(
			Page<Map<String, Object>> page, PrjJob prjJob) {
		// TODO Auto-generated method stub
		return prjJobDao.findPageBySql_gdxj_Tjfs(page, prjJob);
	}
	
	@Override
	public Map<String, Object> getWarName(String warBrand) {
		// TODO Auto-generated method stub
		return prjJobDao.getWarName(warBrand);
	}
	
	@Override
	public Map<String, Object> getCupName(String cupBrand) {
		// TODO Auto-generated method stub
		return prjJobDao.getCupName(cupBrand);
	}
	
	
	@Override
	public Page<Map<String,Object>> getJcclPhotoListByNo(Page<Map<String,Object>> page, PrjJob prjJob){
		return prjJobDao.getJcclPhotoListByNo(page,prjJob);
	}
	
	@Override
	public List<Map<String, Object>> getPhotoList(String no) {
		return prjJobDao.getPhotoList(no);
	}

	@Override
	public Page<Map<String, Object>> findManagePageBySql(
			Page<Map<String, Object>> page, PrjJob prjJob, UserContext uc) {
		// TODO Auto-generated method stub
		return prjJobDao.findManagePageBySql(page, prjJob, uc);
	}
	
	public Page<Map<String, Object>> findSupplPageBySql(
			Page<Map<String, Object>> page, PrjJob prjJob) {
		// TODO Auto-generated method stub
		return prjJobDao.findSupplPageBySql(page, prjJob);
	}

	@Override
	public Page<Map<String, Object>> findSupplListPageBySql(
			Page<Map<String, Object>> page, SupplJob supplJob,String itemRight) {
		// TODO Auto-generated method stub
		return prjJobDao.findSupplListPageBySql(page, supplJob,itemRight);
	}

	@Override
	public Result doPrjJobForProc(PrjJob prjJob) {
		// TODO Auto-generated method stub
		return prjJobDao.doPrjJobForProc(prjJob);
	}
	// add by hc 集成测量分析   end 2017/11/21

	@Override
	public List<Map<String, Object>> findPrjTypeByItemType1(int type,
			String pCode,UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.prjJobDao.findItemType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.prjJobDao.findPrjType(param);
		}
		return resultList;
	}
	
	@Override
	public List<Map<String, Object>> findPrjTypeByItemType1Auth(int type,
			String pCode,UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.prjJobDao.findItemType1Auth(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.prjJobDao.findPrjType(param);
		}
		return resultList;
	}

	@Override
	public Map<String, Object> existPrjJob(String custCode, String jobType, String status){
		return prjJobDao.existPrjJob(custCode, jobType, status);
	}

	@Override
	public boolean hasSupplJob(String prjJobNo) {
		// TODO Auto-generated method stub
		return prjJobDao.hasSupplJob(prjJobNo);
	}

	@Override
	public Page<Map<String, Object>> findAllBySql(
			Page<Map<String, Object>> page, PrjJob prjJob) {
		return prjJobDao.findAllBySql(page, prjJob);
	}

	@Override
	public List<Map<String, Object>> findNoSendYunPic(){
		return prjJobDao.findNoSendYunPic();
	}

}
