package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SupplFeeType;

@SuppressWarnings("serial")
@Repository
public class SupplFeeTypeDao extends BaseDao {

	/**
	 * SupplFeeType分页信息
	 * 
	 * @param page
	 * @param supplFeeType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplFeeType supplFeeType) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.code,a.descr,a.isdefault,a.lastupdate,a.lastupdatedby, " 
				+"a.expired,a.actionlog,b.note isdefaultdescr " 
				+"from tSupplFeeType a " 
				+"left join tXTDM b on a.isdefault=b.IBM and b.id='YESNO' " 
				+"where 1=1 ";
		if (StringUtils.isNotBlank(supplFeeType.getIsComponent())) {
			sql += " and (a.isDefault='1' or a.code in " + "('"+(supplFeeType.getCodes() != null ? supplFeeType.getCodes().replaceAll(",", "','"): "")+"'))";
		}
    	if (StringUtils.isNotBlank(supplFeeType.getCode())) {
			sql += " and a.code=? ";
			list.add(supplFeeType.getCode());
		}
    	if (StringUtils.isNotBlank(supplFeeType.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+supplFeeType.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(supplFeeType.getIsDefault())) {
			sql += " and a.IsDefault=? ";
			list.add(supplFeeType.getIsDefault());
		}
		if (StringUtils.isBlank(supplFeeType.getExpired()) || "F".equals(supplFeeType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Code";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}

