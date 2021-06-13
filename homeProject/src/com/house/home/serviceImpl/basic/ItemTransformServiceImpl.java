package com.house.home.serviceImpl.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemTransformDao;
import com.house.home.entity.basic.ItemTransform;
import com.house.home.entity.basic.ItemTransformDetail;
import com.house.home.service.basic.ItemTransformService;

@SuppressWarnings("serial")
@Service 
public class ItemTransformServiceImpl extends BaseServiceImpl implements ItemTransformService {
    
    @Autowired
    private ItemTransformDao itemTransformDao;
    
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            ItemTransform itemTransfrom) {
        return itemTransformDao.findPageBySql(page, itemTransfrom);
    }

    @Override
    public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,
            ItemTransform itemTransform) {
        return itemTransformDao.findDetailPageBySql(page, itemTransform);
    }

    @Override
    public void doSave(ItemTransform itemTransform) {
        save(itemTransform);
        
        List<ItemTransformDetail> details = convertTransformDetailMapsToEntities(itemTransform.getDetails());
        for (ItemTransformDetail transformDetail : details) {
            
            transformDetail.setNo(itemTransform.getNo());
            transformDetail.setLastUpdate(itemTransform.getLastUpdate());
            transformDetail.setLastUpdatedBy(itemTransform.getLastUpdatedBy());
            transformDetail.setActionLog(itemTransform.getActionLog());
            transformDetail.setExpired(itemTransform.getExpired());
            
            save(transformDetail);
        }
        
    }
    
    private List<ItemTransformDetail> convertTransformDetailMapsToEntities(List<Map<String, Object>> maps) {
        List<ItemTransformDetail> transformDetails = new ArrayList<ItemTransformDetail>();
        
        for (Map<String, Object> map : maps) {
            ItemTransformDetail transformDetail = new ItemTransformDetail();
            
            String pkString = (String) map.get("pk");
            
            if (StringUtils.isNotBlank(pkString)) {
                transformDetail.setPk(Integer.parseInt(pkString));
            }
            
            transformDetail.setItemCode((String) map.get("itemcode"));
            transformDetail.setTransformPer(Double.parseDouble((String) map.get("transformper")));
            
            transformDetails.add(transformDetail);
        }
        
        return transformDetails;
    }

    @Override
    public void doUpdate(ItemTransform itemTransform) {
        
        List<ItemTransformDetail> oldDetails = itemTransformDao.findTransformDetailsByNo(itemTransform.getNo());

        // 清掉Hibernate缓存，否则后面保存时会提示缓存中有相同标识的对象
        hibernateTemplate.clear();
        
        update(itemTransform);
        
        List<ItemTransformDetail> newDetails = convertTransformDetailMapsToEntities(itemTransform.getDetails());
        
        Set<Integer> oldDetailPks = new TreeSet<Integer>();
        for (ItemTransformDetail itemTransformDetail : oldDetails) {
            oldDetailPks.add(itemTransformDetail.getPk());
        }
        
        Set<Integer> newDetailPks = new TreeSet<Integer>();
        for (ItemTransformDetail itemTransformDetail : newDetails) {
            if (itemTransformDetail.getPk() != null) {                
                newDetailPks.add(itemTransformDetail.getPk());
            }
        }
        
        for (ItemTransformDetail detail : newDetails) {
            
            if (detail.getPk() == null) {
                
                detail.setNo(itemTransform.getNo());
                detail.setLastUpdate(itemTransform.getLastUpdate());
                detail.setLastUpdatedBy(itemTransform.getLastUpdatedBy());
                detail.setExpired(itemTransform.getExpired());
                detail.setActionLog("ADD");
                
                save(detail);
            } else if (oldDetailPks.contains(detail.getPk())) {
                
                detail.setNo(itemTransform.getNo());
                detail.setLastUpdate(itemTransform.getLastUpdate());
                detail.setLastUpdatedBy(itemTransform.getLastUpdatedBy());
                detail.setExpired(itemTransform.getExpired());
                detail.setActionLog("EDIT");
                
                update(detail);
            }
            
        }
        
        oldDetailPks.removeAll(newDetailPks);
        for (Integer pk : oldDetailPks) {
            ItemTransformDetail transformDetail = new ItemTransformDetail();
            
            transformDetail.setPk(pk);
            
            delete(transformDetail);
        }
        
    }

}
