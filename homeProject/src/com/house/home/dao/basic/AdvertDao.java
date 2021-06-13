package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Advert;

@SuppressWarnings("serial")
@Repository
public class AdvertDao extends BaseDao {

	/**
	 * Advert分页信息
	 * 
	 * @param page
	 * @param advert
	 * @return
	 */
	public Page<Map<String,Object>> getAdvertList(Page<Map<String,Object>> page, Advert advert) {
		List<Object> params = new ArrayList<Object>();

		String sql = " select * from ( " 
				   + " 		select ta.PicAddr,ta.DispSeq,ta.PK,tx1.NOTE advTypeDescr,ta.content,ta.lastupdate,ta.lastupdatedby,ta.expired," 
				   + "		ta.actionlog,ta.advType,ta.title,ta.OutUrl "
				   + " 		from tAdvert ta "
				   + " 		left join tXTDM tx1 on tx1.ID='ADVTYPE' and tx1.cbm=ta.advType "
				   + " 		where 1=1 ";
		if(StringUtils.isNotBlank(advert.getAdvType())){
			sql += " and ta.AdvType=? ";
			params.add(advert.getAdvType());
		}
		if(StringUtils.isBlank(advert.getExpired()) || "F".equals(advert.getExpired())){
			sql += " and ta.Expired = 'F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.DispSeq asc, PK desc ";
		}

		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Map<String, Object> getAdvertDetail(Integer pk){
		String sql = " select content,title,Outurl from tAdvert where PK=? ";
		if(pk == null){
			pk = 0;
		}
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}

