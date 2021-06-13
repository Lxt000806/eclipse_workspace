package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.WorkerArrangeEvt;
import com.house.home.entity.project.Worker;
import com.house.home.entity.project.WorkerArrange;

/**
 * 
 * @author 张海洋
 *
 */

@SuppressWarnings("serial")
@Repository
public class WorkerArrangeDao extends BaseDao {

    public Page<Map<String, Object>> getOrderWorkType12List(Page<Map<String, Object>> page) {
        List<Object> params = new ArrayList<Object>();
        String sql = " select b.Code , b.Descr from tWorkerArrange a "
                +" left join tWorkType12 b on a.WorkType12 = b.Code "
                +" group by b.Code,b.Descr ";
        
        return this.findPageBySql(page, sql, params.toArray());
    }
    
    public Page<Map<String, Object>> getWorkerArrangeList(Page<Map<String, Object>> page,WorkerArrangeEvt evt) {
        List<Object> params = new ArrayList<Object>();
        String sql = " select out_a.Date,out_a.DayType,out_b.NOTE DayTypeDescr,out_a.CanOrderNum from ( "
                +" select a.WorkType12,a.Date,a.DayType, count(case when a.CustWorkPk is null then 1 else null end ) CanOrderNum from  tWorkerArrange a "
                +" left join tWorker b on a.WorkerCode = b.Code "
                +" where DATEDIFF(dd,GETDATE(),a.Date)<=7 and DATEDIFF(dd,GETDATE(),a.Date)>=1 "
                +" and (((b.Department2 is null or b.Department2 = '') and (b.Department1 is null or b.Department1 = '') ) " 
                +" or (b.Department1 = ? and (b.Department2 is null or b.Department2 = '')) " 
                +" or (b.Department1 = ? and b.Department2 = ?) ) "
                +" group by a.WorkType12,a.Date,a.DayType "
                +" ) out_a " 
                +" left join tXTDM out_b on out_a.DayType = out_b.CBM and out_b.ID='DAYTYPE' "
                +" where 1=1 and out_a.WorkType12 = ? ";
        params.add(evt.getDepartment1());
        params.add(evt.getDepartment1());
        params.add(evt.getDepartment2());
        params.add(evt.getWorkType12());
        if(evt.getHideOrdered()){
            sql += " and out_a.CanOrderNum > 0 ";
        }
        sql += " order by out_a.Date ";
        return this.findPageBySql(page, sql, params.toArray());
    }
    
    public Page<Map<String, Object>> getOrderNoList(Page<Map<String, Object>> page,WorkerArrangeEvt evt) {
        List<Object> params = new ArrayList<Object>();
        String sql = " select a.PK, b.NameChi + '   ' + cast(a.No as nvarchar(10))+'号' NoDescr  from  tWorkerArrange a "
                +" left join tWorker b on a.WorkerCode = b.Code "
                +" where datediff(d,a.Date, ? ) = 0 and a.DayType = ? and a.WorkType12 = ? and a.CustWorkPk is null "
                +" and (((b.Department2 is null or b.Department2 = '') and (b.Department1 is null or b.Department1 = '') ) " 
                +" or (b.Department1 = ? and (b.Department2 is null or b.Department2 = '')) " 
                +" or (b.Department1 = ? and b.Department2 = ?) ) ";
        params.add(evt.getDate());
        params.add(evt.getDayType());
        params.add(evt.getWorkType12());
        params.add(evt.getDepartment1());
        params.add(evt.getDepartment1());
        params.add(evt.getDepartment2());
        return this.findPageBySql(page, sql, params.toArray());
    }
    
    public Page<Map<String, Object>> getAddresses(Page<Map<String, Object>> page,WorkerArrangeEvt evt) {
        List<Object> params = new ArrayList<Object>();
        String sql = " select a.Code,a.Address from tCustomer a "
                +" where Status = '4' and a.Expired = 'F' and a.ProjectMan = ? "
                +" and Code not in (select custCode from tCustWorker where WorkType12 = ?) ";
        params.add(evt.getCzybh());
        params.add(evt.getWorkType12());
        if(StringUtils.isNotBlank(evt.getAddress())){
            sql += " and a.Address like ? ";
            params.add("%"+evt.getAddress()+"%");
        }
        return this.findPageBySql(page, sql, params.toArray());
    }
    
    public Page<Map<String, Object>> getOrderWorkerDetailList(Page<Map<String, Object>> page,WorkerArrangeEvt evt) {
        List<Object> params = new ArrayList<Object>();
        String sql = " select d.Address, c.NameChi ProjectManDescr,b.NameChi WorkerDescr, a.No,e.NOTE DayTypeDescr  from  tWorkerArrange a "
                +" left join tWorker b on a.WorkerCode = b.Code "
                +" left join tEmployee c on a.CZYBH = c.Number "
                +" left join tCustomer d on a.CustCode = d.Code"
                +" left join tXTDM e on a.DayType = e.CBM and e.ID='DAYTYPE' "
                +" where datediff(d,a.Date, ? ) = 0  and a.DayType = ? and a.WorkType12 = ? "
                +" and (((b.Department2 is null or b.Department2 = '') and (b.Department1 is null or b.Department1 = '') ) " 
                +" or (b.Department1 = ? and (b.Department2 is null or b.Department2 = '')) " 
                +" or (b.Department1 = ? and b.Department2 = ?) ) ";
        params.add(evt.getDate());
        params.add(evt.getDayType());
        params.add(evt.getWorkType12());
        params.add(evt.getDepartment1());
        params.add(evt.getDepartment1());
        params.add(evt.getDepartment2());
        return this.findPageBySql(page, sql, params.toArray());
    }
    
    public Page<Map<String, Object>> getOrderedList(Page<Map<String, Object>> page,WorkerArrangeEvt evt) {
        List<Object> params = new ArrayList<Object>();
        String sql = " select b.Address, d.NameChi WorkerDescr, c.Descr WorkType12Descr, a.Date, e.NOTE DayTypeDescr,d.Phone from  tWorkerArrange a "
                +" left join tCustomer b on a.CustCode = b.Code "
                +" left join tWorkType12 c on a.WorkType12 = c.Code "
                +" left join tWorker d on a.WorkerCode = d.Code "
                +" left join tXTDM e on a.DayType = e.CBM and e.ID='DAYTYPE'"
                +" where DATEDIFF(dd,GETDATE(),a.Date)<=7 and DATEDIFF(dd,GETDATE(),a.Date)>=0 and a.CZYBH = ? order by a.Date Desc,a.DayType ";
        params.add(evt.getCzybh());
        return this.findPageBySql(page, sql, params.toArray());
    }
    
	public Page<Map<String, Object>> findPageBySqlList(
			Page<Map<String, Object>> page, WorkerArrange workerArrange,
			UserContext userContext) {

		List<Object> list = new ArrayList<Object>();
		
		String sql = "select a.PK, b.NameChi WorkerName, c.Descr Worktype12Descr, a.Date, e.NOTE DayTypeDescr, a.No, " +
						   "(case when a.CZYBH is null then 0 when a.CZYBH = '' then 0 else 1 end) Booked, d.Area, " +
					       "a.CustWorkPk, d.Address, f.ZWXM Operator, a.OrderDate, a.LastUpdate, " +
					       "a.LastUpdatedBy, a.Expired, a.ActionLog " +
					 "from tWorkerArrange a " +
					         "left join tWorker b on a.WorkerCode = b.Code " +
					         "left join tWorktype12 c on a.WorkType12 = c.Code " +
					         "left join tCustomer d on a.CustCode = d.Code " +
					         "left join tXTDM e on e.ID = 'DAYTYPE' and a.DayType = e.CBM " +
					         "left join tCZYBM f on a.CZYBH = f.CZYBH " +
					         "where 1=1 ";
		
		if (StringUtils.isNotBlank(workerArrange.getPks())) {
			sql += "and a.PK in (" + workerArrange.getPks() + ") ";
		}
		
		if (StringUtils.isNotBlank(workerArrange.getWorkerCode())) {
            sql += "and a.WorkerCode = ? ";
            list.add(workerArrange.getWorkerCode());
        }
		
		if (StringUtils.isNotBlank(workerArrange.getWorkerName())) {
			sql += "and b.NameChi like ? ";
			list.add("%" + workerArrange.getWorkerName() + "%");
		}
		
		if (StringUtils.isNotBlank(workerArrange.getWorkType12())) {
			sql += "and a.WorkType12 = ? ";
			list.add(workerArrange.getWorkType12());
		}
		
		if (StringUtils.isNotBlank(workerArrange.getAddress())) {
			sql += "and d.Address like ? ";
			list.add("%" + workerArrange.getAddress() + "%");
		}
		
		if (workerArrange.getBooked() != null) {
			switch (workerArrange.getBooked()) {
				case 0:
					sql += "and (a.CZYBH is null or a.CZYBH = '') ";
					break;
				case 1:
					sql += "and a.CZYBH is not null and a.CZYBH <> '' ";
					break;
				default:
					break;
			}
		}
		
		if (workerArrange.getDateFrom() != null) {
			sql += "and a.Date >= ? ";
			list.add(workerArrange.getDateFrom());
		}
		
		if (workerArrange.getDateTo() != null) {
			sql += "and a.Date <= ? ";
			list.add(workerArrange.getDateTo());
		}
		
		if (StringUtils.isBlank(workerArrange.getExpired()) 
				|| "F".equals(workerArrange.getExpired()) ) {
			sql += "and a.Expired = 'F' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "order by a." + page.getPageOrderBy() + " " +page.getPageOrder();
		} else {
			sql += "order by a.OrderDate desc";
		}
		
		return findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 按工人编号，某天日期，班次查询是否有排班记录
	 * 
	 * @param workerCode 工人编号
	 * @param date 预约日期
	 * @param dayType 班次
	 * @return 工人排班记录
	 */
	public List<Map<String, Object>> getWorkerArrangesByWorkerCodeAndDateAndDayType(
			String workerCode, Date date, String dayType) {
		String sql = "select * from tWorkerArrange " +
				"where WorkerCode = ? " +
				"and convert(varchar(10), Date, 20) = convert(varchar(10), ?, 20) " +
				"and DayType = ?";
		return findBySql(sql, workerCode, date, dayType);
	}
	
	/**
	 * 根据工种类型和楼盘查询排班预约记录
	 * 
	 * @param workType12 工种
	 * @param custCode 楼盘
	 * @return
	 */
	public List<Map<String, Object>> getWorkerArrangesByWorkType12AndCustCode(
	        String workType12, String custCode) {
	    
	    String sql = "select * from tWorkerArrange where WorkType12 = ? and CustCode = ?";
	    
	    return findBySql(sql, workType12, custCode);
	}
	
	/**
     * 根据工种类型和楼盘查询工地工人安排记录
     * 
     * @param workType12 工种
     * @param custCode 楼盘
     * @return
     */
	public List<Map<String, Object>> getCustWorkersByWorkType12AndCustCode(
            String workType12, String custCode) {
        
        String sql = "select * from tCustWorker where WorkType12 = ? and CustCode = ?";
        
        return findBySql(sql, workType12, custCode);
    }
	
	public List<Map<String, Object>> getWorkerArrangeByCustWorkPk(Integer custWorkPk) {
	    
        String sql = "select * from tWorkerArrange where CustWorkPk = ?";
        
        return findBySql(sql, custWorkPk);
    }
	
	/**
	 * 因为可能存在多人同时预约的问题，所以添加预约信息
	 * 的时候要先判断此排班记录是否已经有预约信息。如果
	 * 没有预约信息，则可以添加预约信息；如果已有预约信
	 * 息，则不能再更新此排班记录的预约信息。
	 * 
	 * @param workerArrange 带有预约信息的排班对象
	 * @return 返回数据库受影响的条数
	 * @author 张海洋
	 * @date 20200515
	 */
	public Long saveOrderInfo(WorkerArrange workerArrange) {
	    String sql = "update tWorkerArrange " +
	    		"set CustCode = ?, CZYBH = ?, OrderDate = ?, CustWorkPk = ?, " +
	    		"LastUpdate = ?, LastUpdatedBy = ?, Expired = ?, ActionLog = ? " +
	    		"where CZYBH is null and CustCode is null and PK = ?";
	    
	    return executeUpdateBySql(sql, workerArrange.getCustCode(),
	            workerArrange.getCzybh(), workerArrange.getOrderDate(),
	            workerArrange.getCustWorkPk(), workerArrange.getLastUpdate(),
	            workerArrange.getLastUpdatedBy(), workerArrange.getExpired(),
	            workerArrange.getActionLog(), workerArrange.getPk());
	}

}

