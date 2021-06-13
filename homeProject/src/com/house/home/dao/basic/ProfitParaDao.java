package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ProfitPara;

@Repository
public class ProfitParaDao extends BaseDao{

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, ProfitPara profitPara){
        List<Object> list = new ArrayList<Object>();
        String sql = "select *"
                    + " from tProfitPara a"
                    + " where 1 = 1";
        
        if(profitPara.getPk() != null){
            sql += " and a.PK like ?";
            list.add("%" + profitPara.getPk() + "%");
        }
        if (StringUtils.isBlank(profitPara.getExpired())
                || "F".equals(profitPara.getExpired())) {
            sql += " and a.Expired='F'";
        }
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += " order by a." + page.getPageOrderBy() + " "+ page.getPageOrder();
        } else {
            sql += " order by a.LastUpdate desc";
        }
        
        return this.findPageBySql(page, sql, list.toArray());
    }
}
