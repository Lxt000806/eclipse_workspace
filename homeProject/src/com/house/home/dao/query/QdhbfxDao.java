package com.house.home.dao.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class QdhbfxDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer,String role ) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pQdhbfx ?,?,?,?,?,?,?,?,?,?,?,? ";
		list.add(new Timestamp(
				DateUtil.startOfTheDay( customer.getDateFrom()).getTime()));
		list.add(new Timestamp(
				DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
		list.add(role);
		list.add(customer.getTeam());
		list.add(customer.getCustType());
		list.add(customer.getConstructType());
		list.add(customer.getStatistcsMethod());
		list.add(customer.getDepartment1());
		list.add(customer.getDepartment2());
		list.add(customer.getDepartment3());
		list.add(customer.getExpired());
		list.add(customer.getRegion());
		List<Map<String, Object>> oldList = findListBySql(sql, list.toArray());
		List<Map<String, Object>> newList = new ArrayList<Map<String,Object>>();

		for(int i=0;i<oldList.size();i++){
			Map<String , Object> oldMap=new HashMap<String, Object>();
			oldMap.put("Number", oldList.get(i).get("Number"));
			oldMap.put("Namechi", oldList.get(i).get("Namechi"));
			oldMap.put("Depart1Descr", oldList.get(i).get("Depart1Descr"));
			oldMap.put("Depart2Descr", oldList.get(i).get("Depart2Descr"));
			oldMap.put("Depart3Descr", oldList.get(i).get("Depart3Descr"));
			oldMap.put("TeamDescr", oldList.get(i).get("TeamDescr"));
			oldMap.put("BuilderDescr", oldList.get(i).get("BuilderDescr"));
			oldMap.put("CustTypeDescr", oldList.get(i).get("CustTypeDescr"));
			oldMap.put("lastSignCount", oldList.get(i).get("lastSignCount"));
			oldMap.put("CrtPer",StringUtils.substringBefore( oldList.get(i).get("CrtPer").toString(),".")+"%");
			oldMap.put("SignCount", oldList.get(i).get("SignCount"));
			oldMap.put("SignPer", StringUtils.substringBefore( oldList.get(i).get("SignPer").toString(),".")+"%");
			oldMap.put("CrtCount", oldList.get(i).get("CrtCount"));
			oldMap.put("lastCrtCount", oldList.get(i).get("lastCrtCount"));
			oldMap.put("SetCount", oldList.get(i).get("SetCount"));
			oldMap.put("lastSetCount", oldList.get(i).get("lastSetCount"));
			oldMap.put("SetPer", StringUtils.substringBefore( oldList.get(i).get("SetPer").toString(),".")+"%");
			oldMap.put("AchieveFee", oldList.get(i).get("AchieveFee"));
			oldMap.put("lastAchieveFee", oldList.get(i).get("lastAchieveFee"));
			newList.add(oldMap);
		}
		
		page.setResult(newList); 			
		page.setTotalCount(page.getResult().size());
		return page;
	}
	
}
