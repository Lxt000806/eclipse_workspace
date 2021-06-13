package com.house.home.dao.project;
import com.house.framework.commons.orm.BaseDao;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.ConfirmCustomFiled;
@SuppressWarnings("serial")
@Repository
public class ConfirmCustomFiledDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfirmCustomFiled confirmCustomFiled) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code, a.Descr, a.PrjItem, a.Type, a.Options, a.LastUpdate," +
				"	a.LastUpdatedBy, a.Expired, a.ActionLog,a.dispSeq,b.Descr PrjItemDescr, x.Note TypeDescr " +
				"	from tConfirmCustomFiled a " +
				"	left join tPrjItem1 b on b.Code = a.PrjItem" +
				"	left join tXtdm x on x.cbm = a.Type and x.id = 'CONFFILEDTYPE'" +
				"	where 1=1 " ;

		if(StringUtils.isNotBlank(confirmCustomFiled.getDescr())){
			sql+=" and a.descr like ? ";
			list.add("%"+confirmCustomFiled.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(confirmCustomFiled.getType())){
			sql+=" and a.type = ? ";
			list.add(confirmCustomFiled.getType());
		}
		if(StringUtils.isNotBlank(confirmCustomFiled.getPrjItem())){
			sql+=" and a.prjItem = ? ";
			list.add(confirmCustomFiled.getPrjItem());
		}
		if (StringUtils.isBlank(confirmCustomFiled.getExpired())
				|| "F".equals(confirmCustomFiled.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> checkDescrExists(String descr, String code){
		String sql = "select 1 from tConfirmCustomFiled where descr = ? and (?='' or code <> ? )and expired = 'F'";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{descr, code, code});
		
		return list;
	}

}
