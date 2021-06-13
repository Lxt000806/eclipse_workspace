package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PrjItem1;

@SuppressWarnings("serial")
@Repository
public class PrjItem1Dao extends BaseDao {

	/**
	 * PrjItem1分页信息
	 * 
	 * @param page
	 * @param prjItem1
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjItem1 prjItem1) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.Descr,a.Seq,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.prjphotoNum,a.SignTimes, "
				 +"b.Descr workType12Descr,x1.NOTE isComfirmDescr,x2.NOTE isFirstPassDescr ,x3.Note isFirstCompleteDescr,a.DesignSignTimes, "
		         +"a.FirstAddConfirm, x4.NOTE FirstAddConfirmDescr "
				 +"from tPrjItem1 a  "
				 +"left join tWorkType12 b on a.worktype12=b.Code  "
				 +"left join tXTDM x1 on a.IsConfirm=x1.CBM and x1.ID='YESNO' "
				 +"left join tXTDM x2 on a.IsFirstPass=x2.CBM and x2.ID='YESNO' "
				 +"left join tXTDM x3 on a.IsFirstComplete=x3.CBM and x3.ID='YESNO' "
				 +"left join tXTDM x4 on a.FirstAddConfirm=x4.CBM and x4.ID='FIRSTADDCONF' "
				 +"where 1=1 ";

    	if (StringUtils.isNotBlank(prjItem1.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+prjItem1.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(prjItem1.getCode())) {
			sql += " and a.Code=? ";
			list.add(prjItem1.getCode());
		}
		if (StringUtils.isBlank(prjItem1.getExpired()) || "F".equals(prjItem1.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

