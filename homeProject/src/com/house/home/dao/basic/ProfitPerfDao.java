package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ProfitPerf;

@SuppressWarnings("serial")
@Repository
public class ProfitPerfDao extends BaseDao {

	/**
	 * ProfitPerf分页信息
	 * 
	 * @param page
	 * @param activity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ProfitPerf profitPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.*,b.Descr Descr,c.NOTE Note " +
				"from tProfitPerf a " +
				"left join tItemType12 b on a.ItemType12=b.Code " +
				"left join tXTDM c on a.IsClearInv=c.CBM and c.ID='YESNO' where 1=1 ";
		
		if (StringUtils.isBlank(profitPerf.getExpired()) || "F".equals(profitPerf.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(profitPerf.getBuyerCommiPer()!=null){
			sql+=" and BuyerCommiPer = ? ";
			list.add(profitPerf.getBuyerCommiPer());
		}
		if(profitPerf.getDesignCommiPer()!=null){
			sql+=" and DesignCommiPer =? ";
			list.add(profitPerf.getDesignCommiPer());
		}
		if(StringUtils.isNotBlank(profitPerf.getItemType12())){
			sql+=" and ItemType12 = ? ";
			list.add(profitPerf.getItemType12());
		}
		if(StringUtils.isNotBlank(profitPerf.getIsClearInv())){
			sql+=" and IsClearInv = ? ";
			list.add(profitPerf.getIsClearInv());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")k order by k." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else{
			sql += ")k order by k.Descr " ;
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}

