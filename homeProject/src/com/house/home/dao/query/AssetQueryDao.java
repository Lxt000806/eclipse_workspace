package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.bean.query.AssetQuery;

@Repository
@SuppressWarnings("serial")
public class AssetQueryDao extends BaseDao {

    public Page<Map<String, Object>> findPageBySql(
            Page<Map<String, Object>> page, AssetQuery assetQuery) {
        
        String sql = "select a.Code, a.Descr, a.Model, b.Descr AssetTypeDescr, c.Descr UomDescr, "
                    + "d.NOTE AddTypeDescr, e.Desc1 DepartmentDescr, f.NameChi UseManName, "
                    + "g.NOTE StatusDescr, a.Address, a.Qty, a.OriginalValue, a.BeginDate, "
                    + "a.UseYear, h.NameChi CreateName, a.CreateDate "
                    + "from tAsset a "
                    + "left join tAssetType b on a.AssetType = b.Code "
                    + "left join tUOM c on a.Uom = c.Code "
                    + "left join tXTDM d on a.AddType = d.CBM and d.ID = 'ASSETADDTYPE' "
                    + "left join tDepartment e on a.Department = e.Code "
                    + "left join tEmployee f on a.UseMan = f.Number "
                    + "left join tXTDM g on a.Status = g.CBM and g.ID = 'ASSETSTATUS' "
                    + "left join tEmployee h on a.CreateCZY = h.Number "
                    + "where 1 = 1 ";
  
        ArrayList<Object> parameters = new ArrayList<Object>();
        
        if (StringUtils.isNotBlank(assetQuery.getCzybh())) {
            sql += "and (a.UseMan = ? or " +
            	   "exists(select 1 from tDepartment in_a where e.Path like in_a.Path + '%' and in_a.LeaderCode = ?)) ";
            
            parameters.add(assetQuery.getCzybh());
            parameters.add(assetQuery.getCzybh());
        }
        
        if (StringUtils.isNotBlank(assetQuery.getAssetType())) {
            sql += "and a.AssetType = ? ";
            parameters.add(assetQuery.getAssetType());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += "order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += "order by a.BeginDate desc";
        }
        
        return findPageBySql(page, sql, parameters.toArray());
    }

}
