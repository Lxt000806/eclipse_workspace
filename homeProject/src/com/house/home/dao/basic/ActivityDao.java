package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Activity;

@SuppressWarnings("serial")
@Repository
public class ActivityDao extends BaseDao {

	/**
	 * Activity分页信息
	 * 
	 * @param page
	 * @param activity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Activity activity) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No, a.ActName, a.Times, a.Sites, a.BeginDate, a.EndDate, a.Remarks,"
          + " a.Prefix, a.Length, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog,  "
          + " a.CmpActNo, b.Descr CmpActDescr "
          + " from tActivity a "
          + " left join tCmpActivity b on a.CmpActNo=b.No "
          + " where 1=1 ";
		if(StringUtils.isNotBlank(activity.getValidAct())){
			sql+=" and  getdate() between a.Begindate and a.endDate ";		
		}
    	if (StringUtils.isNotBlank(activity.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+activity.getNo()+"%");
		}
    	if (StringUtils.isNotBlank(activity.getActName())) {
			sql += " and a.ActName like ? ";
			list.add("%"+activity.getActName()+"%");
		}
    	if (StringUtils.isNotBlank(activity.getTimes())) {
			sql += " and a.Times=? ";
			list.add(activity.getTimes());
		}
    	if (StringUtils.isNotBlank(activity.getSites())) {
			sql += " and a.Sites=? ";
			list.add(activity.getSites());
		}
    	if (activity.getBeginDate() != null) {
			sql += " and a.BeginDate>= ? ";
			list.add(activity.getBeginDate());
		}
    	if (activity.getEndDate() != null) {
			sql += " and a.EndDate<=? ";
			list.add(activity.getEndDate());
		}
    	if (StringUtils.isNotBlank(activity.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(activity.getRemarks());
		}
    	if (StringUtils.isNotBlank(activity.getPrefix())) {
			sql += " and a.Prefix=? ";
			list.add(activity.getPrefix());
		}
    	if (activity.getLength() != null) {
			sql += " and a.Length=? ";
			list.add(activity.getLength());
		}
    	if (activity.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(activity.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(activity.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(activity.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(activity.getExpired()) || "F".equals(activity.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(activity.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(activity.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findActByName(
			Page<Map<String, Object>> page, String actName) {
		List<Object> list=new ArrayList<Object>();
		String sql="SELECT no,actName FROM  tActivity where expired='F' ";
		if(StringUtils.isNotBlank(actName)){
			sql+=" and ActName like ?";
			list.add("%"+actName+"%");
		}
		sql+=" order by endDate desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public String getCurrActivity(){
		String sql = " select top 1 no actNo "
				   + " from tActivity "
				   + " where BeginDate < getdate() and getdate() < EndDate ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size() > 0 ){
			return list.get(0).get("actNo").toString();
		}
		return "";
	}
	/**
	 * 检查是否有效
	 * @author	created by zb
	 * @date	2019-8-26--下午4:59:18
	 * @param activity
	 * @return
	 */
	public Boolean checkActivity(Activity activity) {
		String sql = " select 1 "
				   + " from tActivity "
				   + " where BeginDate < getdate() and getdate() < EndDate "
				   + " and no=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{activity.getNo()});
		if(list != null && list.size() > 0 ){
			return true;
		}
		return false;
	}
}

