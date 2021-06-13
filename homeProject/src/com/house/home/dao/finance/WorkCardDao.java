package com.house.home.dao.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.WorkCard;

@SuppressWarnings("serial")
@Repository
public class WorkCardDao extends BaseDao{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCard workCard) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select * from tWorkCard a where 1=1 and a.expired='F' ";
	
		if(StringUtils.isNotBlank(workCard.getCardID())){
			sql+=" and a.CardId like ? ";
			list.add("%"+workCard.getCardID()+"%");
		}
		if(StringUtils.isNotBlank(workCard.getActName())){
			sql+=" and a.ActName like ? ";
			list.add("%"+workCard.getActName()+"%");
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	
	}


}





