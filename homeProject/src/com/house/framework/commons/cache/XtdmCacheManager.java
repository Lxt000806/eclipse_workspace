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
import com.house.home.entity.basic.Xtdm;
import com.house.home.service.basic.XtdmService;

@Component
public class XtdmCacheManager extends SimpleCacheManager {
	
	private static final Logger logger = LoggerFactory.getLogger(XtdmCacheManager.class);
	
	@Autowired
	private XtdmService xtdmService;
	
	public String getCacheKey() {
		return CommonConstant.CACHE_XTDM_KEY;
	}
	@Override
	public void initCacheData() {
		logger.info("初始化系统代码数据开始");
		List<Xtdm> list = xtdmService.loadAll(Xtdm.class);
		for (Xtdm xtdm : list){
			this.put(xtdm.getId()+"_"+xtdm.getCbm(), xtdm);
			//List<Xtdm> st = xtdmService.getById(xtdm.getId());
			//this.put(xtdm.getId()+"list",st);
		}
		Map<String, List<Xtdm>> map =groupList(list);
        for(String key:map.keySet()){
            this.put(key+"list",map.get(key));
        }
		logger.info("初始化系统代码数据结束");
	}
	
	public  Map<String, List<Xtdm>> groupList(List<Xtdm> xtdms) {
        Map<String, List<Xtdm>> map = new HashMap<String, List<Xtdm>>();
        for (Xtdm xtdm : xtdms) {
            List<Xtdm> tmpList = map.get(xtdm.getId());
            if (tmpList == null) {
                tmpList = new ArrayList<Xtdm>();
                tmpList.add(xtdm);
                map.put(xtdm.getId(), tmpList);
            } else {
                tmpList.add(xtdm);
            }
        }
        return map;
    }
	
}
