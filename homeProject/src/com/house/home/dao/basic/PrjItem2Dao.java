package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PrjItem2;

@SuppressWarnings("serial")
@Repository
public class PrjItem2Dao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjItem2 prjItem2) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.Code,a.Descr,a.PrjItem,b.Descr PrjItemDescr,a.MinDay, " +
					"a.Seq,a.IsAppWork,c.NOTE IsAppWorkDescr,a.IsUpEndDate,c2.NOTE IsUpEndDateDescr,a.OfferWorkType2, " +
					"d.Descr OfferWorkType2Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, " +
					"a.worktype12,e.Descr workType12Descr " +
					"from tPrjItem2 a " +
					"left join tPrjItem1 b on b.Code=a.PrjItem " +
					"left join tXTDM c on c.ID='YESNO' and c.CBM=a.IsAppWork " +
					"left join tXTDM c2 on c2.ID='YESNO' and c2.CBM=a.IsUpEndDate " +
					"left join tWorkType2 d on d.Code=a.OfferWorkType2 " +
					"left join tWorkType12 e on e.Code=a.worktype12 " +
					"where 1=1 ";

		if (StringUtils.isBlank(prjItem2.getExpired()) || "F".equals(prjItem2.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(prjItem2.getCode())) {
			sql += " and a.Code=? ";
			list.add(prjItem2.getCode());
		}
    	if (StringUtils.isNotBlank(prjItem2.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+prjItem2.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> getPrjItem2ListByPrjItem1(String prjItem1){
		List<Object> params = new ArrayList<Object>();
		String sql = " select code,descr from tPrjItem2 where Expired='F' ";
		if(StringUtils.isNotBlank(prjItem1)){
			sql += " and PrjItem=? ";
			params.add(prjItem1);
		}
		sql += " order by PrjItem asc, Seq asc ";
		return this.findBySql(sql, params.toArray());
	}
}

