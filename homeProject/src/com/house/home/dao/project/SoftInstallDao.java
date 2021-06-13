package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.SoftInstall;

@SuppressWarnings("serial")
@Repository
public class SoftInstallDao extends BaseDao {

	/**
	 * SoftInstall分页信息
	 * 
	 * @param page
	 * @param softInstall
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftInstall softInstall) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.pk,a.ItemType12,b.Descr ItemType12Descr,a.CustCode,c.address,a.InstallEmp,d.NameChi InstallEmpDescr,"
			+"a.LastUpdate,a.LastUpdatedBy,a.InstallDate from tSoftInstall a left join tItemType12 b on a.ItemType12=b.Code "
			+"left join tcustomer c on a.CustCode=c.Code left join tEmployee d on a.InstallEmp=d.Number "
			+"where a.Expired='F' ";

    	if (softInstall.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(softInstall.getPk());
		}
    	if (StringUtils.isNotBlank(softInstall.getItemType12())) {
			sql += " and a.ItemType12=? ";
			list.add(softInstall.getItemType12());
		}
    	if (StringUtils.isNotBlank(softInstall.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(softInstall.getCustCode());
		}
    	if (StringUtils.isNotBlank(softInstall.getInstallEmp())) {
			sql += " and a.InstallEmp=? ";
			list.add(softInstall.getInstallEmp());
		}
    	if (softInstall.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(softInstall.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(softInstall.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(softInstall.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(softInstall.getExpired()) || "F".equals(softInstall.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(softInstall.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(softInstall.getActionLog());
		}
    	if (softInstall.getInstallDate() != null) {
			sql += " and a.InstallDate=? ";
			list.add(softInstall.getInstallDate());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

