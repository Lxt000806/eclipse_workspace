package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkCostImportItem;

@SuppressWarnings("serial")
@Repository
public class WorkCostImportItemDao extends BaseDao {

	/**
	 * WorkCostImportItem分页信息
	 * 
	 * @param page
	 * @param workCostImportItem
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCostImportItem workCostImportItem) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.No,a.Descr,x3.NOTE WorkCostTypeDescr,x1.NOTE TypeDescr,e.Descr PrjItemDescr,b.Descr WorkType12Descr,c.Descr OfferWorkType12Descr,"
					+"x2.NOTE CalcTypeDescr,a.Price,d.Descr WorkType2Descr,a.Remark,x4.NOTE IsRepeatImport,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					+"from tWorkCostImportItem a  "
					+"left join tWorkType12 b on a.WorkType12 = b.Code "
					+"left join tWorkType12 c on a.OfferWorkType12 = c.Code "
					+"left join tWorkType2 d on a.WorkType2 = d.Code "
					+"left join tPrjItem1 e on a.PrjItem = e.Code "
					+"left join tXTDM x1 on a.Type = x1.CBM and x1.ID='WORKCOSTIMPTYPE' "
					+"left join tXTDM x2 on a.CalcType = x2.CBM and x2.ID='WORKCOSTIMPCATP' "
					+"left join tXTDM x3 on a.WorkCostType = x3.CBM and x3.ID='WorkCostType' "
					+"left join tXTDM x4 on a.IsRepeatImport = x4.CBM and x4.ID='YESNO' "
					+"where 1=1 ";

    	if (StringUtils.isNotBlank(workCostImportItem.getNo())) {
			sql += " and a.No=? ";
			list.add(workCostImportItem.getNo());
		}
    	if (StringUtils.isNotBlank(workCostImportItem.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+workCostImportItem.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(workCostImportItem.getRemark())) {
			sql += " and a.Remark like ? ";
			list.add("%"+workCostImportItem.getRemark()+"%");
		}
    	if (StringUtils.isNotBlank(workCostImportItem.getWorkCostType())) {
			sql += " and a.WorkCostType=? ";
			list.add(workCostImportItem.getWorkCostType());
		}
    	if (StringUtils.isNotBlank(workCostImportItem.getType())) {
			sql += " and a.Type=? ";
			list.add(workCostImportItem.getType());
		}
		if (StringUtils.isBlank(workCostImportItem.getExpired()) || "F".equals(workCostImportItem.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

