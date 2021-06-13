package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.FixAreaType;

@Repository
public class FixAreaTypeDao extends BaseDao{

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, FixAreaType fixAreaType){
        List<Object> list = new ArrayList<Object>();
        
        String sql = "select *"
                    + " from tFixAreaType a"
                    + " where 1 = 1";
        
        if(StringUtil.isNotBlank(fixAreaType.getCode())){
            sql += " and a.Code like ?";
            list.add("%" + fixAreaType.getCode() + "%");
        }
        if(StringUtil.isNotBlank(fixAreaType.getDescr())){
            sql += " and a.Descr like ?";
            list.add("%" + fixAreaType.getDescr() + "%");
        }
        if (StringUtils.isBlank(fixAreaType.getExpired())
                || "F".equals(fixAreaType.getExpired())) {
            sql += " and a.Expired='F'";
        }
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += " order by a." + page.getPageOrderBy() + " "+ page.getPageOrder();
        } else {
            sql += " order by a.LastUpdate desc";
        }
        
        return this.findPageBySql(page, sql, list.toArray());
    }
    
    public List<FixAreaType> getByDescr(String descr){
        String hql = "from FixAreaType a where a.descr = ?";
        return find(hql, descr);
    }
}
