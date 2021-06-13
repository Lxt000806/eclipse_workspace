package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.AssetType;
import com.house.home.entity.finance.Asset;

@SuppressWarnings("serial")
@Repository
public class AssetTypeDao extends BaseDao {

    public Page<Map<String, Object>> findPageBySql(
            Page<Map<String, Object>> page, AssetType assetType) {

        ArrayList<Object> params = new ArrayList<Object>();

        String sql = "select a.Code, a.Descr, a.RemCode, a.DeprType, b.NOTE, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tAssetType a "
                + "left join tXTDM b on a.DeprType = b.CBM and b.ID = 'ASSETDEPRTYPE' "
                + "where 1 = 1 ";
        
        if (StringUtils.isNotBlank(assetType.getRemCode())) {
            sql += "and a.RemCode = ? ";
            params.add(assetType.getRemCode());
        }
        
        if (StringUtils.isNotBlank(assetType.getDescr())) {
            sql += "and a.Descr like ? ";
            params.add("%" + assetType.getDescr() + "%");
        }
        
        if (StringUtils.isNotBlank(assetType.getDeprType())) {
            sql += "and a.DeprType = ? ";
            params.add(assetType.getDeprType());
        }
        
        if (StringUtils.isBlank(assetType.getExpired())
                || "F".equals(assetType.getExpired())) {
            sql += "and a.Expired = 'F' ";
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += "order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += "order by a.Code desc";
        }

        return findPageBySql(page, sql, params.toArray());
    }
    
    /**
     * 根据助记码查询固定资产类别
     * 
     * @param remCode 助记码
     * @return 查询到的类别实体或者null
     * @author 张海洋
     */
    public AssetType findByRemCode(String remCode) {
        String sql = "select * from tAssetType where RemCode = ?";
        
        List<Map<String, Object>> list = findBySql(sql, remCode);
        
        if (list.size() > 0) {
            AssetType assetType = new AssetType();
            BeanConvertUtil.mapToBean(list.get(0), assetType);
            return assetType;
        } else {
            return null;
        }
    }
    
    /**
     * 根据资产类别查询资产列表
     * 
     * @param assetTypeCode
     * @return 资产列表
     * @author 张海洋
     */
    public List<Asset> findAssetsByAssetType(String assetTypeCode) {
        String sql = "select * from tAsset where AssetType = ?";
        
        List<Map<String, Object>> list = findBySql(sql, assetTypeCode);
        
        return BeanConvertUtil.beanToBeanList(list, Asset.class);
    }
}
