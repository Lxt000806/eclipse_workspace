package com.house.home.serviceImpl.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.BuilderDao;
import com.house.home.entity.basic.Builder;
import com.house.home.service.basic.BuilderService;

@SuppressWarnings("serial")
@Service
public class BuilderServiceImpl extends BaseServiceImpl implements BuilderService {

	@Autowired
	private BuilderDao builderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Builder builder){
		return builderDao.findPageBySql(page, builder);
	}
	
	public Page<Map<String,Object>> findSpcBuilderAddPageBySql(Page<Map<String,Object>> page, Builder builder,String arr){
		return builderDao.findSpcBuilderAddPageBySql(page, builder,arr);
	}
	
	public Page<Map<String,Object>> findSpcBuilderPageBySql(Page<Map<String,Object>> page, Builder builder){
		return builderDao.findSpcBuilderPageBySql(page, builder);
	}

	public List<Builder> findByNoExpired(String condition) {
		return builderDao.findByNoExpired(condition);
	}

	@Override
	public Builder getByDescr(String descr) {
		return builderDao.getByDescr(descr);
	}

	@Override
	public Map<String, Object> findByCode(String code) {
		return builderDao.findByCode(code);
	}

	@Override
	public List<Map<String, Object>> findRegionCodeByAuthority(int type,
			String pCode, UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";			
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.builderDao.findRegionCode1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.builderDao.findRegionCode2(param);
		}
		return resultList;
	}

	@Override
	public void infoUpdate(Builder builder) {
		builderDao.infoUpdate(builder);
	}

	@Override
	public List<Map<String, Object>> findPrjRegion(String regionCode2) {
		return builderDao.findPrjRegion(regionCode2);
	}

	@Override
	public void regionUpdate(Builder builder) {
		builderDao.regionUpdate(builder);
	}

	@Override
	public Page<Map<String, Object>> getBuilderList(
			Page<Map<String, Object>> page, Builder builder) {
		// TODO Auto-generated method stub
		return builderDao.getBuilderList(page,builder);
	}

	
}
