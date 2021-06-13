package com.house.home.serviceImpl.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.PrjProblemDao;
import com.house.home.entity.project.PrjProblem;
import com.house.home.entity.query.PrjProblemPic;
import com.house.framework.web.login.UserContext;
import java.util.Date;


import com.house.home.service.project.PrjProblemService;

@SuppressWarnings("serial")
@Service
public class PrjProblemServiceImpl extends BaseServiceImpl implements PrjProblemService{
	
	@Autowired
	private PrjProblemDao prjProblemDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjProblem prjProblem , UserContext uc) {
		// TODO Auto-generated method stub
		return prjProblemDao.findPageBySql(page, prjProblem ,uc);
	}

	@Override
	public List<Map<String,Object>> findPromDeptandType(int type,String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.prjProblemDao.findPromDept(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.prjProblemDao.findPromType(param);
		}
		
		return resultList;
	}

	@Override
	public boolean isExistType(String deptCode) {
		// TODO Auto-generated method stub
		return prjProblemDao.isExistType(deptCode);
	}

	@Override
	public Page<Map<String, Object>> getPrjProblemList(Page<Map<String, Object>> page, PrjProblem prjProblem) {
		return prjProblemDao.getPrjProblemList(page, prjProblem);
	}

	@Override
	public List<Map<String, Object>> getPrjPromDeptList(){
		return prjProblemDao.getPrjPromDeptList();
	}

	@Override
	public List<Map<String, Object>> getPrjPromTypeList(String prjPromDept){
		return prjProblemDao.getPrjPromTypeList(prjPromDept);
	}

	@Override
	public String doSavePrjProblem(PrjProblem prjProblem){
		
		if(StringUtils.isBlank(prjProblem.getCustCode())){
			return "请选择楼盘";
		}
		if(StringUtils.isBlank(prjProblem.getPromDeptCode())){
			return "请选择责任部门";
		}
		List<Map<String, Object>> list = prjProblemDao.getPrjPromTypeList(prjProblem.getPromDeptCode());
		if(list.size() > 0 && StringUtils.isBlank(prjProblem.getPromTypeCode())){
			return "请选择责任分类";
		}
		if(StringUtils.isBlank(prjProblem.getPromPropCode())){
			return "请选择问题性质";
		}
		if(StringUtils.isBlank(prjProblem.getRemarks())){
			return "请填写问题描述";
		}
		
		prjProblem.setIsDeal("0");
		prjProblem.setStatus("1");
		prjProblem.setAppDate(new Date());
		prjProblem.setLastUpdate(new Date());
		prjProblem.setExpired("F");
		prjProblem.setActionLog("ADD");
		prjProblemDao.save(prjProblem);
		return "";
	}
	
	
	@Override
	public Map<String, Object> getPrjProblem(String no){
		return prjProblemDao.getPrjProblem(no);
	}

	@Override
	public String doUpdatePrjProblem(PrjProblem prjProblem){
		PrjProblem updatePrjProblem = prjProblemDao.get(PrjProblem.class, prjProblem.getNo());
		if("0".equals(prjProblem.getOpSign())){
			updatePrjProblem.setStatus(prjProblem.getStatus());
			if("1".equals(prjProblem.getStatus())){
				updatePrjProblem.setAppDate(new Date());
			}else if("5".equals(prjProblem.getStatus())){
				updatePrjProblem.setCancelCZY(prjProblem.getAppCZY());
				updatePrjProblem.setCancelDate(new Date());
			}
		}else{
			if(StringUtils.isBlank(prjProblem.getPromDeptCode())){
				return "请选择责任部门";
			}
			List<Map<String, Object>> list = prjProblemDao.getPrjPromTypeList(prjProblem.getPromDeptCode());

			if(list.size() > 0 && StringUtils.isBlank(prjProblem.getPromTypeCode())){
				return "请选择责任分类";
			}
			if(StringUtils.isBlank(prjProblem.getPromPropCode())){
				return "请选择问题性质";
			}
			if(StringUtils.isBlank(prjProblem.getRemarks())){
				return "请填写问题描述";
			}
			updatePrjProblem.setPromDeptCode(prjProblem.getPromDeptCode());
			updatePrjProblem.setPromTypeCode(prjProblem.getPromTypeCode());
			updatePrjProblem.setPromPropCode(prjProblem.getPromPropCode());
			updatePrjProblem.setRemarks(prjProblem.getRemarks());
			updatePrjProblem.setIsBringStop(prjProblem.getIsBringStop());
		}
		updatePrjProblem.setLastUpdate(new Date());
		updatePrjProblem.setLastUpdatedBy(prjProblem.getAppCZY());
		updatePrjProblem.setActionLog("EDIT");
		prjProblemDao.update(updatePrjProblem);
		
		// 保存图片记录
		if(StringUtils.isNotBlank(prjProblem.getPhotoString())){
			String[] arr = prjProblem.getPhotoString().split(",");
			for (String photoName: arr){
				PrjProblemPic prjProblemPic = new PrjProblemPic();
				prjProblemPic.setPhotoName(photoName);
				prjProblemPic.setNo(prjProblem.getNo());
				prjProblemPic.setLastUpdate(new Date());
				prjProblemPic.setLastUpdatedBy(prjProblem.getAppCZY());
				prjProblemPic.setActionLog("ADD");
				prjProblemPic.setExpired("F");
				prjProblemPic.setIsSendYun("1");
				prjProblemPic.setSendDate(new Date());
				save(prjProblemPic);
				prjProblemPic = null;
			}
		}
		
		return "";
	}

	@Override
	public void doUpdate(PrjProblem prjProblem) {
		prjProblemDao.doUpdate(prjProblem);
	}

	@Override
	public PrjProblemPic getPicByName(String photoName) {
		return prjProblemDao.getPicByName(photoName);
	}

	@Override
	public List<Map<String, Object>> getPhotoList(String no) {
		return prjProblemDao.getPhotoList(no);
	}

	@Override
	public Page<Map<String, Object>> findPrjProblemPageBySql(
			Page<Map<String, Object>> page, PrjProblem prjProblem) {

		return prjProblemDao.findPrjProblemPageBySql(page, prjProblem);
	}
	
	@Override
	public Page<Map<String, Object>> goPicJqGrid(Page<Map<String, Object>> page, PrjProblem prjProblem) {
		return prjProblemDao.goPicJqGrid(page, prjProblem);
	}
}
