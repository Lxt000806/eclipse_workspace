package com.house.home.bean.query;

import java.io.Serializable;
import java.util.List;

import com.house.home.entity.workflow.Department;

public class AssetQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // 资产类别
    private String assetType;
    
    // 操作员编号
    private String czybh;
    

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getCzybh() {
        return czybh;
    }

    public void setCzybh(String czybh) {
        this.czybh = czybh;
    }

}
