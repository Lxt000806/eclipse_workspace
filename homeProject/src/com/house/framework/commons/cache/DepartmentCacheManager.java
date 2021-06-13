package com.house.framework.commons.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Xtdm;
import com.house.home.service.basic.Department1Service;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.Department3Service;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtdmService;

@Component
public class DepartmentCacheManager extends SimpleCacheManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentCacheManager.class);
	
	@Autowired
	private Department1Service department1Service;
	@Autowired
	private Department2Service department2Service;
	@Autowired
	private Department3Service department3Service;
	@Autowired
	private XtdmService xtdmService;
	
	public String getCacheKey() {
		return CommonConstant.CACHE_DEPARTMENT_KEY;
	}
	@Override
	public void initCacheData() {
		logger.info("初始化部门数据开始");
		List<Department1> listDepartment1 = department1Service.findByNoExpired();
		for (Department1 department1 : listDepartment1){
			this.put("department1_"+department1.getCode(), department1);
//			List<Department2> st = department2Service.getByDepartment1(department1.getCode());
//			for(int i=0;i<st.size();i++){
//				st.get(i).setDepLeader(employeeService.getDepLeader(st.get(i).getCode()) );
//			}
//			this.put("department1_"+department1.getCode()+"_list",st);
		}
		this.put("department1",listDepartment1);
		
		List<Department2> listDepartment2 = department2Service.getDepartment2WithLeader();
		Map<String, List<Department2>> department2GroupbyDepartment1Map = department2ListGroupbyDepartment1(listDepartment2);
        for(String key:department2GroupbyDepartment1Map.keySet()){
        	this.put("department1_"+key+"_list",department2GroupbyDepartment1Map.get(key));
        }

		List<Xtdm> listDepType = xtdmService.getById("DEPTYPE");
		for (Xtdm xtdm : listDepType){
			this.put("depTypeDept1_"+xtdm.getCbm(), xtdm);
			List<Department1> st = department1Service.getByDepType(xtdm.getCbm());
			this.put("depTypeDept1_"+xtdm.getCbm()+"_list",st);
		}
		
		for (Xtdm xtdm : listDepType){
			this.put("depType_"+xtdm.getCbm(), xtdm);
//			List<Department2> st = department2Service.getByDepType(xtdm.getCbm());
//			for(int i=0;i<st.size();i++){
//				st.get(i).setDepLeader(employeeService.getDepLeader(st.get(i).getCode()) );
//			}
//			this.put("depType_"+xtdm.getCbm()+"_list",st);
		}
		this.put("depType",listDepType);
		
		Map<String, List<Department2>> department2ListGroupByDepTypeMap = department2ListGroupByDepType(listDepartment2);
        for(String key:department2ListGroupByDepTypeMap.keySet()){
        	this.put("depType_"+key+"_list",department2ListGroupByDepTypeMap.get(key));
        }
        
        List<Department3> listDepartment3 = department3Service.findByNoExpired();	
//		List<Department2> listDepartment2 = department2Service.findByNoExpired();
		for (Department2 department2 : listDepartment2){
//			String leader=employeeService.getDepLeader(department2.getCode());
//			department2.setDepLeader(leader);
			this.put("department2_"+department2.getCode(), department2);
//			List<Department3> st = department3Service.getByDepartment2(department2.getCode());
//			this.put("department2_"+department2.getCode()+"_list",st);
		}
		this.put("department2",listDepartment2);
		
		Map<String, List<Department3>> department3GroupbyDepartment2Map = department3ListGroupbyDepartment2(listDepartment3);
        for(String key:department3GroupbyDepartment2Map.keySet()){
        	this.put("department2_"+key+"_list",department3GroupbyDepartment2Map.get(key));
        }
        
		for (Department3 department3 : listDepartment3){
			this.put("department3_"+department3.getCode(), department3);
		}
		this.put("department3",listDepartment3);
		
		logger.info("初始化部门数据结束");
	}
	
	public Map<String, List<Department2>> department2ListGroupbyDepartment1(List<Department2> department2s) {
        Map<String, List<Department2>> map = new HashMap<String, List<Department2>>();
        for (Department2 department2 : department2s) {
            List<Department2> tmpList = map.get(department2.getDepartment1());
            if (tmpList == null) {
                tmpList = new ArrayList<Department2>();
                tmpList.add(department2);
                map.put(department2.getDepartment1(), tmpList);
            } else {
                tmpList.add(department2);
            }
        }
        return map;
    }
	
	public Map<String, List<Department2>> department2ListGroupByDepType(List<Department2> department2s) {
        Map<String, List<Department2>> map = new HashMap<String, List<Department2>>();
        for (Department2 department2 : department2s) {
            List<Department2> tmpList = map.get(department2.getDepType());
            if (tmpList == null) {
                tmpList = new ArrayList<Department2>();
                tmpList.add(department2);
                map.put(department2.getDepType(), tmpList);
            } else {
                tmpList.add(department2);
            }
        }
        return map;
    }
	
	public Map<String, List<Department1>> department1ListGroupByDepType(List<Department1> department1s) {
        Map<String, List<Department1>> map = new HashMap<String, List<Department1>>();
        for (Department1 department1 : department1s) {
            List<Department1> tmpList = map.get(department1.getDepType());
            if (tmpList == null) {
                tmpList = new ArrayList<Department1>();
                tmpList.add(department1);
                map.put(department1.getDepType(), tmpList);
            } else {
                tmpList.add(department1);
            }
        }
        return map;
    }
	
	public Map<String, List<Department3>> department3ListGroupbyDepartment2(List<Department3> department3s) {
        Map<String, List<Department3>> map = new HashMap<String, List<Department3>>();
        for (Department3 department3 : department3s) {
            List<Department3> tmpList = map.get(department3.getDepartment2());
            if (tmpList == null) {
                tmpList = new ArrayList<Department3>();
                tmpList.add(department3);
                map.put(department3.getDepartment2(), tmpList);
            } else {
                tmpList.add(department3);
            }
        }
        return map;
    }	
}
