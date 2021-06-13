package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.RzxscxDao;
import com.house.home.entity.project.ItemChg;
import com.house.home.service.query.RzxscxService;

@Service
@SuppressWarnings("serial")
public class RzxscxServiceImpl extends BaseServiceImpl implements RzxscxService{
	@Autowired
	private RzxscxDao rzxscxDao;
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		//way0：
		if("0".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				page= rzxscxDao.findRbZjPageBy_itemType_Sql(page,itemChg);
			}else if("1".equals(itemChg.getCountType())){
				page = rzxscxDao.findRbWgPageBy_itemType_Sql(page,itemChg);
			}
		}else if("1".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				page=  rzxscxDao.findRbZjPageBy_sqlCode_Sql(page,itemChg);
			}else if("1".equals(itemChg.getCountType())){
				page = rzxscxDao.findRbWgPageBy_sqlCode_Sql(page,itemChg);
			}
		}else if("2".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				page = rzxscxDao.findRbZjPageBy_cust_Sql(page,itemChg);
			}else if("1".equals(itemChg.getCountType())){
				page=  rzxscxDao.findRbWgPageBy_cust_Sql(page,itemChg);
			}
		}
		return page;
	
	}
	
	@Override
	public String getYHJEbySql(ItemChg itemChg) {
		//0材料   1品牌  2楼盘
		String yhje="";
		if("0".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getYHJE_rbzj_by_itemType_Sql(itemChg);
			}else if("1".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getYHJE_rbwg_by_itemType_Sql(itemChg);
			}
		}else if("1".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getYHJE_rbzj_by_sqlCode_Sql(itemChg);
			}else if("1".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getYHJE_rbwg_by_sqlCode_Sql(itemChg);
			}
		}else if("2".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getYHJE_rbzj_by_cust_Sql(itemChg);
			}else if("1".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getYHJE_rbwg_by_cust_Sql(itemChg);
			}
		}
		return yhje;
	}

	@Override
	public String getDLYHbySql(ItemChg itemChg) {
		String yhje="";
		if("0".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getDLYH_rbzj_by_itemType_Sql(itemChg);
			}else if("1".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getDLYH_rbwg_by_itemType_Sql(itemChg);
			}
		}else if("1".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getDLYH_rbzj_by_sqlCode_Sql(itemChg);
			}else if("1".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getDLYH_rbwg_by_sqlCode_Sql(itemChg);
			}
		}else if("2".equals(itemChg.getCountWay())){
			if("0".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getDLYH_rbzj_by_cust_Sql(itemChg);
			}else if("1".equals(itemChg.getCountType())){
				yhje=rzxscxDao.getDLYH_rbwg_by_cust_Sql(itemChg);
			}
		}
		return yhje;
	}

	
	@Override
	public String getYSYHbySql(ItemChg itemChg) {
		// TODO Auto-generated method stub
		return rzxscxDao.getDLYH_ysyh_by_itemType_Sql(itemChg);
	}

	@Override
	public Page<Map<String, Object>> findItemPlanBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		return rzxscxDao.findItemPlanSql(page, itemChg);
	}

	@Override
	public Page<Map<String, Object>> findSaleDetailBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		return rzxscxDao.findSaleDetailSql(page, itemChg);
	}

	@Override
	public Page<Map<String, Object>> findItemReqBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		return rzxscxDao.findItemReqSql(page, itemChg);
	}

	@Override
	public Page<Map<String, Object>> findChgDetailBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		return rzxscxDao.findChgDetailSql(page, itemChg);
	}

	@Override
	public String getItem1byItem2(String itemType2) {
		// TODO Auto-generated method stub
		return rzxscxDao.getItem1byItem2(itemType2);
	}
	
	
	
	
}
