package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.AssetType;
import com.house.home.dao.basic.AssetTypeDao;
import com.house.home.service.basic.AssetTypeService;

@SuppressWarnings("serial")
@Service
public class AssetTypeServiceImpl extends BaseServiceImpl implements AssetTypeService {
    
    @Autowired
    private AssetTypeDao assetTypeDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            AssetType assetType) {
        
        return assetTypeDao.findPageBySql(page, assetType);
    }

    @Override
    public Result doSave(AssetType assetType, UserContext userContext) {

        if (StringUtils.isBlank(assetType.getDescr())) {
            return new Result(Result.FAIL_CODE, "保存失败，名称为空");
        }
        
        if (StringUtils.isBlank(assetType.getRemCode())) {
            return new Result(Result.FAIL_CODE, "保存失败，助记码为空");
        }
        
        if (assetTypeDao.findByRemCode(assetType.getRemCode()) != null) {
            return new Result(Result.FAIL_CODE, "保存失败，助记码已存在");
        }
        
        if (StringUtils.isBlank(assetType.getDeprType())) {
            return new Result(Result.FAIL_CODE, "保存失败，折旧方法为空");
        }
        
        String seqNo = getSeqNo("tAssetType");
        assetType.setCode(seqNo);
        assetType.setRemCode(assetType.getRemCode().toUpperCase());
        assetType.setLastUpdate(new Date());
        assetType.setLastUpdatedBy(userContext.getCzybh());
        assetType.setExpired("F");
        assetType.setActionLog("ADD");
        
        assetTypeDao.save(assetType);
        
        return new Result(Result.SUCCESS_CODE, "保存成功");
    }

    @Override
    public Result doUpdate(AssetType assetType, UserContext userContext) {
        
        if (StringUtils.isBlank(assetType.getCode())) {
            return new Result(Result.FAIL_CODE, "更新失败，编号为空");
        }
        
        if (StringUtils.isBlank(assetType.getDeprType())) {
            return new Result(Result.FAIL_CODE, "更新失败，折旧方法为空");
        }
        
        AssetType unmodifiedAssetType = assetTypeDao.get(AssetType.class, assetType.getCode());
        if (!assetType.getRemCode().equalsIgnoreCase(unmodifiedAssetType.getRemCode())) {
            if (assetTypeDao.findAssetsByAssetType(unmodifiedAssetType.getCode()).size() > 0) {
                return new Result(Result.FAIL_CODE, "更新失败，使用中的助记码不允许修改");
            }
        }
        
        unmodifiedAssetType.setDescr(assetType.getDescr());
        unmodifiedAssetType.setRemCode(assetType.getRemCode().toUpperCase());
        unmodifiedAssetType.setDeprType(assetType.getDeprType());
        unmodifiedAssetType.setExpired(assetType.getExpired());
        
        unmodifiedAssetType.setLastUpdate(new Date());
        unmodifiedAssetType.setLastUpdatedBy(userContext.getCzybh());
        unmodifiedAssetType.setActionLog("EDIT");
        
        assetTypeDao.update(unmodifiedAssetType);
        
        return new Result(Result.SUCCESS_CODE, "更新成功");
    }

}
