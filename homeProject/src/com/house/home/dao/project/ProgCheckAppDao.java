package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.ProgCheckApp;

@SuppressWarnings("serial")
@Repository
public class ProgCheckAppDao extends BaseDao {

	/**
	 * ProgCheckApp分页信息
	 * 
	 * @param page
	 * @param progCheckApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, 
			ProgCheckApp progCheckApp, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.pk,a.CustCode,b.Address,b.ProjectMan,c.NameChi ProjectManDescr,a.AppDate,a.AppCZY,d.ZWXM AppCzyDescr,"
		+"a.Remarks,e.CheckNo,f.ZWXM XjCzyDescr,e.Status,x1.NOTE StatusDescr,a.expired "
		+"from tProgCheckApp a inner join tcustomer b on a.CustCode=b.Code "
		+"left join tEmployee c on b.ProjectMan=c.Number "
		+"left join tCZYBM d on a.AppCZY=d.CZYBH "
		+"left join tProgCheckPlanDetail e on a.PK=e.AppPK "
		+"left join tCZYBM f on e.AppCZY=f.CZYBH "
		+"left join tXTDM x1 on e.Status=x1.CBM and x1.ID='CHECKDTSTATUS' "
		+"where 1=1 and " + SqlUtil.getCustRight(uc, "b", 0);

    	if (progCheckApp.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(progCheckApp.getPk());
		}
    	if (StringUtils.isNotBlank(progCheckApp.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(progCheckApp.getCustCode());
		}
    	if (progCheckApp.getAppDate() != null) {
			sql += " and a.AppDate=? ";
			list.add(progCheckApp.getAppDate());
		}
    	if (StringUtils.isNotBlank(progCheckApp.getAddress())){
    		sql += " and b.address like ? ";
			list.add("%"+progCheckApp.getAddress()+"%");
    	}
    	if (StringUtils.isNotBlank(progCheckApp.getAppCzyDescr())){
    		sql += " and d.zwxm like ? ";
			list.add("%"+progCheckApp.getAppCzyDescr()+"%");
    	}
    	if (progCheckApp.getDateFrom() != null) {
    		sql += " and a.appDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(progCheckApp.getDateFrom());
		}
		if (progCheckApp.getDateTo() != null) {
			sql += " and a.appDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(progCheckApp.getDateTo());
		}
    	if (StringUtils.isNotBlank(progCheckApp.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(progCheckApp.getAppCzy());
		}
    	if (StringUtils.isNotBlank(progCheckApp.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(progCheckApp.getRemarks());
		}
    	if (progCheckApp.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(progCheckApp.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(progCheckApp.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(progCheckApp.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(progCheckApp.getExpired()) || "F".equals(progCheckApp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(progCheckApp.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(progCheckApp.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getByPk(Integer pk) {
		String sql = "select a.pk,a.CustCode,a.AppDate,a.AppCZY,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,"
		+"a.ActionLog,b.Address,b.Descr,c.ZWXM appCzyDescr "
		+"from tProgCheckApp a inner join tCustomer b on a.CustCode=b.Code "
		+"left join tCZYBM c on a.AppCZY=c.CZYBH where a.pk=?";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{pk});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

