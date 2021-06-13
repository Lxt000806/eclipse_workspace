package com.house.home.dao.basic;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Region;

@SuppressWarnings("serial")
@Repository
public class RegionDao extends BaseDao {

	/**
	 * Activity分页信息
	 * 
	 * @param page
	 * @param activity
	 * @return
	 */	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Region region) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.Descr,a.LastUpdate,a.IsSpcWorker,x1.Note IsSpcWorkerDescr," +
				" a.CmpCode,cmp.desc2 CmpDescr,a.LastUpdatedBy, "
	          + " a.Expired,a.ActionLog,x2.note intSendTypeDescr,a.waterLongPension from tRegion a "
	          + " left join txtdm x1 on a.IsSpcWorker=x1.cbm and x1.id='YESNO' "
	          + " left join tCompany cmp on cmp.code=a.CmpCode " +
	          "   left join tXtdm x2 on x2.id= 'INTSENDTYPE' and x2.cbm = a.IntSendType"
	          + " where 1=1  ";
		if(StringUtils.isNotBlank(region.getCode())){
			sql+=" and a.code like ? ";
			list.add("%"+region.getCode()+"%");
		}
		if (StringUtils.isNotBlank(region.getDescr())) {
			sql+="and a.descr like ? ";
			list.add("%"+region.getDescr()+"%");
		}
		if (StringUtils.isBlank(region.getExpired()) || "F".equals(region.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.code ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public boolean validCode(String code){
		String	sql = "select * from tRegion a where a.code=? ";
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(code)) {
			params.add(code);
		}
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if (list!=null && list.size()>0) {
			return true;
		}
		return false;
	};
	public boolean validDescr(String descr){
		String	sql = "select * from tRegion where descr=? ";
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(descr)) {
			params.add(descr);
		}
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if (list!=null && list.size()>0) {
			return true;
		}
		return false;
	};

}

