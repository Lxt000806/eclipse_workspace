package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.WaterAreaCfm;

@SuppressWarnings("serial")
@Repository
public class WaterAreaCfmDao extends BaseDao {

	/**
	 * WaterAreaCfm分页信息
	 * 
	 * @param page
	 * @param waterAreaCfm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WaterAreaCfm waterAreaCfm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.CustCode,c.Address,c.Descr CustDescr,c.CustType,e.Desc1 CustTypeDescr,a.WorkerCode, "
				+"b.NameChi WorkerName,a.Status,d.NOTE StatusDescr,a.AppDate,a.ConfirmCZY,a.ConfirmDate,a.WaterArea,f.NameChi ConfirmCzyDescr, "
				+"a.ConfirmRemarks,a.Expired,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,isnull(g.WaterCtrlArea,0)WaterCtrlArea "
				+"from tWaterAreaCfm a  "
				+"left join tWorker b on a.WorkerCode=b.Code "
				+"left join tCustomer c on a.CustCode=c.Code "
				+"left join tXTDM d on d.ID='WTAREACFMSTAT' and a.Status=d.CBM "
				+"left join tCusttype e on c.CustType=e.Code "
				+"left join tEmployee f on a.ConfirmCzy=f.Number "
				+"left join (" 
				+"   select sum(Qty) WaterCtrlArea,CustCode from tBaseItemReq a" 
				+"   left join dbo.tBaseItem b on b.Code=a.BaseItemCode " 
				+"   left join tbaseitemtype2 c on c.Code=b.BaseItemType2 " 
				+"   where c.MaterWorkType2='010'  group by  CustCode" 
				+")g on g.CustCode=a.CustCode " 
				+"where 1=1 ";

    	if (StringUtils.isNotBlank(waterAreaCfm.getAddress())) {
			sql += " and c.Address like ? ";
			list.add("%"+waterAreaCfm.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(waterAreaCfm.getStatus())) {
			sql += " and a.Status=? ";
			list.add(waterAreaCfm.getStatus());
		}
		if (StringUtils.isBlank(waterAreaCfm.getExpired()) || "F".equals(waterAreaCfm.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

