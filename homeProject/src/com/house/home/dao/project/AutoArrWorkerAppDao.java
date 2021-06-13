package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.AutoArrWorkerApp;

@SuppressWarnings("serial")
@Repository
public class AutoArrWorkerAppDao extends BaseDao {

	/**
	 * AutoArrWorkerApp分页信息
	 * 
	 * @param page
	 * @param autoArrWorkerApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AutoArrWorkerApp autoArrWorkerApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tAutoArrWorkerApp a where 1=1 ";

    	if (autoArrWorkerApp.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(autoArrWorkerApp.getPk());
		}
    	if (autoArrWorkerApp.getArrPk() != null) {
			sql += " and a.ArrPK=? ";
			list.add(autoArrWorkerApp.getArrPk());
		}
    	if (autoArrWorkerApp.getAppPk() != null) {
			sql += " and a.AppPK=? ";
			list.add(autoArrWorkerApp.getAppPk());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(autoArrWorkerApp.getCustCode());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getWorkType12())) {
			sql += " and a.WorkType12=? ";
			list.add(autoArrWorkerApp.getWorkType12());
		}
    	if (autoArrWorkerApp.getAppComeDate() != null) {
			sql += " and a.AppComeDate=? ";
			list.add(autoArrWorkerApp.getAppComeDate());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(autoArrWorkerApp.getCustType());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(autoArrWorkerApp.getProjectMan());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getPrjLevel())) {
			sql += " and a.PrjLevel=? ";
			list.add(autoArrWorkerApp.getPrjLevel());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getIsSupvr())) {
			sql += " and a.IsSupvr=? ";
			list.add(autoArrWorkerApp.getIsSupvr());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getSpcBuilder())) {
			sql += " and a.SpcBuilder=? ";
			list.add(autoArrWorkerApp.getSpcBuilder());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getSpcBldExpired())) {
			sql += " and a.SpcBldExpired=? ";
			list.add(autoArrWorkerApp.getSpcBldExpired());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getRegionCode())) {
			sql += " and a.RegionCode=? ";
			list.add(autoArrWorkerApp.getRegionCode());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getRegionCode2())) {
			sql += " and a.RegionCode2=? ";
			list.add(autoArrWorkerApp.getRegionCode2());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getRegIsSpcWorker())) {
			sql += " and a.RegIsSpcWorker=? ";
			list.add(autoArrWorkerApp.getRegIsSpcWorker());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getDepartment1())) {
			sql += " and a.Department1=? ";
			list.add(autoArrWorkerApp.getDepartment1());
		}
    	if (autoArrWorkerApp.getArea() != null) {
			sql += " and a.Area=? ";
			list.add(autoArrWorkerApp.getArea());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getWorkerCode())) {
			sql += " and a.WorkerCode=? ";
			list.add(autoArrWorkerApp.getWorkerCode());
		}
    	if (autoArrWorkerApp.getComeDate() != null) {
			sql += " and a.ComeDate=? ";
			list.add(autoArrWorkerApp.getComeDate());
		}
    	if (autoArrWorkerApp.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(autoArrWorkerApp.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(autoArrWorkerApp.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(autoArrWorkerApp.getExpired()) || "F".equals(autoArrWorkerApp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(autoArrWorkerApp.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(autoArrWorkerApp.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

