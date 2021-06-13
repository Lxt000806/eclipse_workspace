package com.house.home.dao.basic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.WorkingDate;

@SuppressWarnings("serial")
@Repository
public class WorkingDateDao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkingDate workingDate) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select Date,HoliType,tx.NOTE HoliTypeDescr,LastUpdate,LastUpdatedBy,a.Expired,ActionLog "
					+"from tCalendar a "
					+"left join tXTDM tx on tx.ID='HoliType' and tx.CBM=a.HoliType "
					+"where 1=1 ";
		
		if (null != workingDate.getDateFrom()) {
			sql += " and a.Date >= ? ";
			list.add(new Timestamp(DateUtil.startOfTheDay(workingDate.getDateFrom()).getTime()));
		}
		if (null != workingDate.getDateTo()) {
			sql += " and a.Date <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(workingDate.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 通过sql保存以datetiem为主键的表
	 * @author	created by zb
	 * @date	2020-2-19--下午2:54:30
	 * @param workingDate
	 */
	public void doUpdate(WorkingDate workingDate) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tCalendar set HoliType=?,LastUpdate=?,LastUpdatedBy=?,Expired=?,ActionLog=? "
					+"where Date=? ";
		list.add(workingDate.getHoliType());
		list.add(workingDate.getLastUpdate());
		list.add(workingDate.getLastUpdatedBy());
		list.add(workingDate.getExpired());
		list.add(workingDate.getActionLog());
		list.add(workingDate.getDate());
		this.executeUpdateBySql(sql, list.toArray());
	}
	/**
	 * 日期类型初始化
	 * @author	created by zb
	 * @date	2020-3-3--下午12:04:51
	 * @param workingDate
	 */
	public void doDateInit(WorkingDate workingDate) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tCalendar set HoliType= "
					+"case when datepart(weekday, Date)-1 in ('6','0') then '2' else '1' end, "
					+"LastUpdate=?,LastUpdatedBy=?,Expired=?,ActionLog=? "
					+"from tCalendar a "
					+"where a.Date>=? and a.Date<=? ";
		list.add(workingDate.getLastUpdate());
		list.add(workingDate.getLastUpdatedBy());
		list.add(workingDate.getExpired());
		list.add(workingDate.getActionLog());
		list.add(workingDate.getDateFrom());
		list.add(new Timestamp(DateUtil.endOfTheDay(workingDate.getDateTo()).getTime()));
		this.executeUpdateBySql(sql, list.toArray());
	}
	/**
	 * 日期类型修改
	 * @author	created by zb
	 * @date	2020-3-3--下午4:45:07
	 * @param workingDate
	 */
	public void doUpdateHoliType(WorkingDate workingDate) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tCalendar set HoliType=?,LastUpdate=?,LastUpdatedBy=?,Expired=?,ActionLog=? "
					+"where Date>=? and Date<=? ";
		list.add(workingDate.getHoliType());
		list.add(workingDate.getLastUpdate());
		list.add(workingDate.getLastUpdatedBy());
		list.add(workingDate.getExpired());
		list.add(workingDate.getActionLog());
		list.add(workingDate.getDateFrom());
		list.add(new Timestamp(DateUtil.endOfTheDay(workingDate.getDateTo()).getTime()));
		this.executeUpdateBySql(sql, list.toArray());
	}
	
}

