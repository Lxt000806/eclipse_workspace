package com.house.home.serviceImpl.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.WorkerArrangeEvt;
import com.house.home.dao.basic.CzybmDao;
import com.house.home.dao.design.CustomerDao;
import com.house.home.dao.project.CustWorkerDao;
import com.house.home.dao.project.WorkerArrangeDao;
import com.house.home.dao.project.WorkerDao;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.Worker;
import com.house.home.entity.project.WorkerArrange;
import com.house.home.service.project.WorkerArrangeService;

/**
 * 
 * @author 张海洋
 *
 */

@SuppressWarnings("serial")
@Service
public class WorkerArrangeServiceImpl extends BaseServiceImpl implements WorkerArrangeService {
	
	@Autowired
	private WorkerArrangeDao workerArrangeDao;
	
	@Autowired
	private CustWorkerDao custWorkerDao;
	
	@Autowired
	private WorkerDao workerDao;

	@Autowired
	private CzybmDao czybmDao;
	
	@Autowired
	private CustomerDao customerDao;
    
    @Override
    public Page<Map<String, Object>> getOrderWorkType12List(Page<Map<String, Object>> page) {
        return workerArrangeDao.getOrderWorkType12List(page);
    }
    
    @Override
    public Page<Map<String, Object>> getWorkerArrangeList(Page<Map<String, Object>> page, WorkerArrangeEvt evt) {
        return workerArrangeDao.getWorkerArrangeList(page, evt);
    }

    @Override
    public Page<Map<String, Object>> getOrderNoList(Page<Map<String, Object>> page, WorkerArrangeEvt evt) {
        return workerArrangeDao.getOrderNoList(page, evt);
    }

    @Override
    public Page<Map<String, Object>> getAddresses(Page<Map<String, Object>> page, WorkerArrangeEvt evt) {
        return workerArrangeDao.getAddresses(page, evt);
    }

    @Override
    public Page<Map<String, Object>> getOrderWorkerDetailList(Page<Map<String, Object>> page, WorkerArrangeEvt evt) {
        return workerArrangeDao.getOrderWorkerDetailList(page, evt);
    }

    @Override
    public Page<Map<String, Object>> getOrderedList(Page<Map<String, Object>> page, WorkerArrangeEvt evt) {
        return workerArrangeDao.getOrderedList(page, evt);
    }
    
    
	@Override
	public Page<Map<String, Object>> findPageBySqlList(
			Page<Map<String, Object>> page, WorkerArrange workerArrange,
			UserContext userContext) {
		
		return workerArrangeDao.findPageBySqlList(page, workerArrange, userContext);
	}

	@Override
	public Result doReturn(HttpServletRequest request, HttpServletResponse response,
	        UserContext userContext, Integer pk) {
	    
		if (pk == null) {
			return new Result(Result.FAIL_CODE, "取消预约失败，排班主键为空");
		}
		
		WorkerArrange workerArrange = workerArrangeDao.get(WorkerArrange.class, pk);
		if (workerArrange.getCustWorkPk() == null) {
			return new Result(Result.FAIL_CODE, "取消预约失败，此号还未预约");
		}
		
		CustWorker custWorker = custWorkerDao.get(CustWorker.class, workerArrange.getCustWorkPk());
		custWorkerDao.delete(custWorker);
		
		workerArrange.setCustWorkPk(null);
		workerArrange.setCzybh(null);
		workerArrange.setOrderDate(null);
		workerArrange.setCustCode(null);
		workerArrange.setLastUpdate(new Date());
		workerArrange.setLastUpdatedBy(userContext.getCzybh());
		workerArrange.setActionLog("EDIT");
		workerArrangeDao.save(workerArrange);
		
		return new Result(Result.SUCCESS_CODE, "取消预约成功");
	}

	@Override
	public Result doOrder(UserContext userContext,
			WorkerArrange workerArrange) {
	    
	    Date today = DateUtil.startOfTheDay(DateUtil.getToday());
        if (workerArrange.getDate().before(today)) {
            return new Result(Result.FAIL_CODE, "预约失败，不能预约早于今天的班次");
        }

		if (StringUtils.isBlank(workerArrange.getCustCode())) {
			return new Result(Result.FAIL_CODE, "预约失败，预约楼盘为空");
		}
		
		// 如果改工地存在同工种12的工人安排记录需进行提示已安排，不可进行预约		
		Boolean existing = existsWorkType12OnCustCode(
		        workerArrange.getWorkType12(), workerArrange.getCustCode());
		if (existing) {
            return new Result(Result.FAIL_CODE, "预约失败，重复安排");
        }
		
		// 生成一条工地工人信息记录
		CustWorker custWorker = new CustWorker();
		custWorker.setWorkerCode(workerArrange.getWorkerCode());
		custWorker.setWorkType12(workerArrange.getWorkType12());
		custWorker.setCustCode(workerArrange.getCustCode());
		custWorker.setConstructDay(1);
		custWorker.setComeDate(workerArrange.getDate());
        custWorker.setPlanEnd(workerArrange.getDate());
        custWorker.setIsSysArrange("0");
        custWorker.setStatus("1");
		
		Date now = new Date();
        custWorker.setLastUpdate(now);
		custWorker.setLastUpdatedBy(userContext.getCzybh());
		custWorker.setExpired("F");
		custWorker.setAciontLog("ADD");
		custWorkerDao.save(custWorker);
		
		// 更新工人排班记录中预约信息
		workerArrange.setCustWorkPk(custWorker.getPk());
		workerArrange.setCzybh(userContext.getCzybh());
		workerArrange.setOrderDate(now);
		
		workerArrange.setLastUpdate(now);
		workerArrange.setLastUpdatedBy(userContext.getCzybh());
		workerArrange.setExpired("F");
		workerArrange.setActionLog("EDIT");
		
		Long rows = workerArrangeDao.saveOrderInfo(workerArrange);
		
        if (rows < 1) {
            custWorkerDao.delete(custWorker);
		    
		    // 抛出异常，使事务回滚
//		    throw new RuntimeException("预约失败，此号已被预约");
            return new Result(Result.FAIL_CODE, "预约失败，此号已被预约");
        } else if (rows > 1) {
            throw new RuntimeException("预约失败，不允许同时更新多条数据");
        } else {
            return new Result(Result.SUCCESS_CODE, "预约工人成功");            
        }
		
	}
	
	@Override
	public WorkerArrange getWorkerArrangeWithInfo(Integer id) {
		WorkerArrange workerArrange = new WorkerArrange();
		if (id != null) workerArrange = workerArrangeDao.get(WorkerArrange.class, id);
		
		if (StringUtils.isNotBlank(workerArrange.getWorkerCode())) {
			workerArrange.setWorkerName(workerDao.get(Worker.class, workerArrange.getWorkerCode()).getNameChi());
		}
		
		if (StringUtils.isNotBlank(workerArrange.getCzybh())) {
			workerArrange.setOperator(czybmDao.get(Czybm.class, workerArrange.getCzybh()).getZwxm());
			workerArrange.setBooked(1);
		} else {
			workerArrange.setBooked(0);
		}
		
		if (StringUtils.isNotBlank(workerArrange.getDayType())) {
			workerArrange.setDayType(workerArrange.getDayType().trim());
		}
		
		if (StringUtils.isNotBlank(workerArrange.getCustCode())) {
			workerArrange.setAddress(customerDao.get(Customer.class, workerArrange.getCustCode()).getAddress());
		}
		
		return workerArrange;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public Result doBatchArrange(HttpServletRequest request,
			HttpServletResponse response, WorkerArrange workerArrange,
			UserContext userContext) {
		
		String[] codes = null;
		if (StringUtils.isBlank(workerArrange.getCodes())) {
			return new Result(Result.FAIL_CODE, "批量排班失败，工人编号为空");
		} else {
			codes = workerArrange.getCodes().split(",");
		}
		
		String workType12 = workerArrange.getWorkType12();
		if (StringUtils.isBlank(workType12)) {
			return new Result(Result.FAIL_CODE, "批量排班失败，工种分类为空");
		}
		
		Date tomorrow = DateUtil.startOfTheDay(DateUtil.getTomorrow());
		if (workerArrange.getFromDate().before(tomorrow)) {
		    return new Result(Result.FAIL_CODE, "批量排班失败，起始日期不能早于明天");
        }
		
		if (workerArrange.getFromDate() == null
				|| workerArrange.getToDate() == null) {
			return new Result(Result.FAIL_CODE, "批量排班失败，起始日期和截止日期都不能为空");
		}
		
		if (workerArrange.getToDate().before(workerArrange.getFromDate())) {
			return new Result(Result.FAIL_CODE, "批量排班失败，截止时间不能早于起始时间");
		}
		
		if (StringUtils.isBlank(workerArrange.getWeekdays())) {
			return new Result(Result.FAIL_CODE, "批量排班失败，工作日为空");
		}
		
		ArrayList<Integer> weekdays = new ArrayList<Integer>();
		for (String string : workerArrange.getWeekdays().split(",")) {
			if (StringUtils.isNotBlank(string)) {				
				weekdays.add(new Integer(string));
			}
		}
		
		if (StringUtils.isBlank(workerArrange.getDayTypes())) {
			return new Result(Result.FAIL_CODE, "批量排班失败，班次为空");
		}
		
		ArrayList<String> dayTypes = new ArrayList<String>();
		for (String string : workerArrange.getDayTypes().split(",")) {
			if (StringUtils.isNotBlank(string)) {
				dayTypes.add(string);
			}
		}
		
		if (workerArrange.getNumber() == null) {
			return new Result(Result.FAIL_CODE, "批量排班失败，号数为空");
		} else if (workerArrange.getNumber() < 1) {
			return new Result(Result.FAIL_CODE, "批量排班失败，号数不能小于1");
		}
				
		// 1.根据日期范围按天步进
		while (workerArrange.getFromDate().getTime() <= workerArrange.getToDate().getTime()) {
			
			// JDK  0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday
			// XTDM 0 星期一, 1 星期二, 2 星期三, 3 星期四, 4 星期五, 5 星期六, 6 星期日
			int day = workerArrange.getFromDate().getDay();
			day = day == 0 ? 6 : day - 1;
			
			// 在日期范围中选择工作日
			if (weekdays.contains(day)) {
			    
			    // 2.遍历一个或多个员工编号
				for (String code : codes) {
				    
				    // 3.遍历班次
					for (String dayType : dayTypes) {
						
					    // 按工人编号，某天日期，班次查询是否有排班记录
						List<Map<String,Object>> workerArranges = 
								workerArrangeDao.getWorkerArrangesByWorkerCodeAndDateAndDayType(
										code, workerArrange.getFromDate(), dayType);
						
						// 如果存在已排班的记录，抛出异常，回滚事务
						if (workerArranges.size() > 0) {
							throw new RuntimeException("批量排班失败，不能重复排班");
						}
						
						// 4.遍历预约号数
						for (int i = 0; i < workerArrange.getNumber(); i++) {
							WorkerArrange newWorkerArrange = new WorkerArrange();
							newWorkerArrange.setWorkerCode(code);
							newWorkerArrange.setWorkType12(workerArrange.getWorkType12());
							newWorkerArrange.setDate(new Date(workerArrange.getFromDate().getTime()));							
							newWorkerArrange.setDayType(dayType);
							newWorkerArrange.setNo(i + 1);
							
							newWorkerArrange.setLastUpdate(new Date());
							newWorkerArrange.setLastUpdatedBy(userContext.getCzybh());
							newWorkerArrange.setExpired("F");
							newWorkerArrange.setActionLog("ADD");
							
							workerArrangeDao.save(newWorkerArrange);
						}
						
					}
				}
			}
			
			workerArrange.getFromDate().setDate(workerArrange.getFromDate().getDate() + 1);
		}
		
		return new Result(Result.SUCCESS_CODE, "批量排班成功");
	}

	@Override
	public Result doBatchDel(HttpServletRequest request,
			HttpServletResponse response, String pks) {
		
		if (StringUtils.isBlank(pks)) {
			return new Result(Result.FAIL_CODE, "批量删除失败，待删除的主键为空");
		}
		
		for (String pkString : pks.split(",")) {
			WorkerArrange workerArrange = workerArrangeDao.get(WorkerArrange.class, new Integer(pkString));
			if (workerArrange != null) {
				workerArrangeDao.delete(workerArrange);
			}
		}
		
		return new Result(Result.SUCCESS_CODE, "批量删除成功");
	}

    @Override
    public List<Map<String, Object>> getWorkerArrangeByCustWorkPk(
            Integer custWorkPk) {
        
        return workerArrangeDao.getWorkerArrangeByCustWorkPk(custWorkPk);
    }

    /**
     * 判断一个楼盘是否已安排了某工种工人进场
     */
    @Override
    public Boolean existsWorkType12OnCustCode(String workType12, String custCode) {
        List<Map<String, Object>> custWorkers = 
                workerArrangeDao.getCustWorkersByWorkType12AndCustCode(workType12, custCode);
        
        System.out.println(custWorkers.size());
        if (custWorkers.size() > 0) return true;
        
        List<Map<String, Object>> workerArranges = 
                workerArrangeDao.getWorkerArrangesByWorkType12AndCustCode(workType12, custCode);
        
        System.out.println(workerArranges.size());
        if (workerArranges.size() > 0) return true;
        
        return false;
    }

}
