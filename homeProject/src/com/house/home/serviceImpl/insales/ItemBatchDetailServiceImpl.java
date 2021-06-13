package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemBatchDetailDao;
import com.house.home.entity.insales.ItemBatchDetail;
import com.house.home.service.insales.ItemBatchDetailService;

@SuppressWarnings("serial")
@Service
public class ItemBatchDetailServiceImpl extends BaseServiceImpl implements ItemBatchDetailService {

	@Autowired
	private ItemBatchDetailDao itemBatchDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBatchDetail itemBatchDetail){
		return itemBatchDetailDao.findPageBySql(page, itemBatchDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageByIbdNo(
			Page<Map<String, Object>> page, String ibdNo) {
		// TODO Auto-generated method stub
		return itemBatchDetailDao.findPageByIbdNo(page,ibdNo);
	}

	@Override
	public List<ItemBatchDetail> getItemBatchDetailByIbdNo(String ibdNo) {
		return itemBatchDetailDao.getItemBatchDetailByIbdNo(ibdNo);
	}

	@Override
	public Page<Map<String, Object>> getItemBatchDetailByIbdNo(
			Page<Map<String, Object>> page, ItemBatchDetail itemBatchDetail) {
		if(StringUtils.isBlank(itemBatchDetail.getIsLevel())){
			itemBatchDetail.setIsLevel("0");
		}
		if(StringUtils.isBlank(itemBatchDetail.getCustType())){
			itemBatchDetail.setCustType("");
		}
		return itemBatchDetailDao.getItemBatchDetailByIbdNo(page,itemBatchDetail);
	}

	@Override
	public Page<Map<String, Object>> getItemBatchDetailJqGrid(
			Page<Map<String, Object>> page, ItemBatchDetail itemBatchDetail) {

		return itemBatchDetailDao.getItemBatchDetailJqGrid(page,itemBatchDetail);
	}

    @Override
    public Page<Map<String, Object>> getItemChgImportingJqGrid(Page<Map<String, Object>> page,
            ItemBatchDetail itemBatchDetail) {

        return itemBatchDetailDao.getItemChgImportingJqGrid(page, itemBatchDetail);
    }

}
