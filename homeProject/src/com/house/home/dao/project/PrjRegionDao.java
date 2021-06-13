package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.PrjRegion;

@SuppressWarnings("serial")
@Repository
public class PrjRegionDao extends BaseDao {

	/**
	 * PrjRegion分页信息
	 * 
	 * @param page
	 * @param prjRegion
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjRegion prjRegion) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from( select a.Code,a.Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from tPrjRegion a where 1=1 ";
		if (StringUtils.isBlank(prjRegion.getExpired()) || "F".equals(prjRegion.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(prjRegion.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+prjRegion.getCode()+"%");
		}
    	if (StringUtils.isNotBlank(prjRegion.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+prjRegion.getDescr()+"%");
		}
    	if (prjRegion.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(prjRegion.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(prjRegion.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prjRegion.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(prjRegion.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prjRegion.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")s order by s."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")s order by s.Code";
		}
		System.out.println(sql);
		System.out.println(this.findPageBySql(page, sql, list.toArray()).getResult());
		return this.findPageBySql(page, sql, list.toArray());
	}

	public void doUpdate(PrjRegion prjRegion) {
		String sql = " update tPrjRegion set Descr=?,lastupdate=?,lastupdatedby=?,expired=?,actionlog=?,code=? where code=?";
		this.executeUpdateBySql(
				sql,
				new Object[] { prjRegion.getDescr(),
						prjRegion.getLastUpdate(),
						prjRegion.getLastUpdatedBy(),
						prjRegion.getExpired(), prjRegion.getActionLog(),
						prjRegion.getCode(),prjRegion.getOldCode() });
	}
	
	public boolean checkExsist(PrjRegion prjRegion) {
		boolean flag=false;
		String sql="select 1 from tPrjRegion where Descr=?";
		List<Map<String, Object>> list=this.findBySql(sql,new Object[]{prjRegion.getDescr()});
		if(list.size()>0){
			flag=true;
		}
		return flag;
	}

}

