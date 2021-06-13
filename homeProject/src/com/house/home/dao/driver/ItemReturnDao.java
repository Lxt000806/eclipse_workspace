package com.house.home.dao.driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.client.service.evt.ItemAppReturnQueryEvt;
import com.house.home.client.service.evt.ItemReturnArrivedQueryEvt;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.driver.ItemReturn;

@SuppressWarnings("serial")
@Repository
public class ItemReturnDao extends BaseDao {

	/**
	 * ItemReturn分页信息
	 * 
	 * @param page
	 * @param itemReturn
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.no,a.itemType1,c.descr itemType1Descr,a.custCode,a.status,d.note statusDescr,a.date,a.remarks,"
				+ "a.driverCode,a.sendBathNo,b.address from tItemReturn a inner join tCustomer b on a.custCode=b.code "
				+ "left join tItemType1 c on a.itemType1=c.code "
				+ "left join tXtdm d on a.status=d.cbm and d.id='ITEMRETSTATUS' "
				+ "where 1=1 ";

		if (StringUtils.isNotBlank(itemReturn.getNo())) {
			sql += " and a.No=? ";
			list.add(itemReturn.getNo());
		}
		if (StringUtils.isNotBlank(itemReturn.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemReturn.getItemType1());
		}
		if (StringUtils.isNotBlank(itemReturn.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemReturn.getCustCode());
		}
		if (StringUtils.isNotBlank(itemReturn.getStatus())) {
			sql += " and a.Status=? ";
			list.add(itemReturn.getStatus());
		}
		if (StringUtils.isNotBlank(itemReturn.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(itemReturn.getAppCzy());
		}
		if (itemReturn.getDateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(itemReturn.getDateFrom());
		}
		if (itemReturn.getDateTo() != null) {
			sql += " and a.Date<= ? ";
			list.add(itemReturn.getDateTo());
		}
		if (StringUtils.isNotBlank(itemReturn.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemReturn.getRemarks());
		}
		if (StringUtils.isNotBlank(itemReturn.getDriverCode())) {
			sql += " and a.DriverCode=? ";
			list.add(itemReturn.getDriverCode());
		}
		if (StringUtils.isNotBlank(itemReturn.getSendBatchNo())) {
			sql += " and a.SendBatchNo=? ";
			list.add(itemReturn.getSendBatchNo());
		}
		if (StringUtils.isNotBlank(itemReturn.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemReturn.getActionLog());
		}
		if (itemReturn.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemReturn.getLastUpdate());
		}
		if (StringUtils.isNotBlank(itemReturn.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemReturn.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemReturn.getExpired())
				|| "F".equals(itemReturn.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * ItemReturn分页信息接口
	 * 
	 * @param page
	 * @param itemReturn
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_forClient(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.no,a.itemType1,c.descr itemType1Descr,a.custCode,a.status,d.note statusDescr,a.date,"
				+ "a.driverCode,a.sendBatchNo,b.address from tItemReturn a "
				+ "inner join tCustomer b on a.custCode=b.code "
				+ "left join tItemType1 c on a.itemType1=c.code "
				+ "left join tXtdm d on a.status=d.cbm and d.id='ITEMRETSTATUS' "
				+ "where 1=1 ";

		if (StringUtils.isNotBlank(itemReturn.getProjectMan())) {
			sql += " and b.projectMan=? ";
			list.add(itemReturn.getProjectMan());
		} else {
			return null;
		}
		if (StringUtils.isNotBlank(itemReturn.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%" + itemReturn.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(itemReturn.getNo())) {
			sql += " and a.No=? ";
			list.add(itemReturn.getNo());
		}
		if (StringUtils.isNotBlank(itemReturn.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemReturn.getItemType1());
		}
		if (StringUtils.isNotBlank(itemReturn.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemReturn.getCustCode());
		}
		if (StringUtils.isNotBlank(itemReturn.getStatus())) {
			sql += " and a.Status=? ";
			list.add(itemReturn.getStatus());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByDriver(
			Page<Map<String, Object>> page, ItemAppReturnQueryEvt evt) {
		List<Object> list = new ArrayList<Object>();
		list.add(evt.getId());
		String sql = "select a.no,b.Address,a.Date,c.NameChi,c.Phone from tItemReturn a "
				+ "inner join tCustomer b on a.CustCode=b.Code "
				+ "inner join tEmployee c on b.ProjectMan=c.Number where a.DriverCode=? "
				+ "and a.Status in ('3','4','5') ";
		if (StringUtils.isNotBlank(evt.getAddress())) {
			sql += " and b.address like ?";
			list.add("%" + evt.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(evt.getStatus())) {
			sql += " and a.Status = ? ";
			list.add(evt.getStatus());
		}
		sql += "order by a.Date Desc";
		return this.findPageBySql(page, sql, list.toArray());

	}

	public Page<Map<String, Object>> findPageByDriverArrived(
			Page<Map<String, Object>> page, ItemReturnArrivedQueryEvt evt) {
		List<Object> list = new ArrayList<Object>();
		list.add(evt.getId());
		String sql = "select a.no,b.Address,a.Date,c.NameChi,c.Phone from tItemReturn a "
				+ "inner join tCustomer b on a.CustCode=b.Code "
				+ "inner join tEmployee c on b.ProjectMan=c.Number where a.DriverCode=? "
				+ "and a.Status='6'";
		if (StringUtils.isNotBlank(evt.getAddress())) {
			sql += "and b.address like ?";
			list.add("%" + evt.getAddress() + "%");
		}
		sql += "order by a.Date Desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getByNo(String id) {
		String sql = "select a.no,a.itemType1,c.descr itemType1Descr,a.custCode,a.status,d.note statusDescr,a.date,a.remarks,"
				+ "a.driverCode,a.sendBatchNo,b.address,b.code,a.appCzy,e.zwxm appCzyDescr from tItemReturn a "
				+ "inner join tCustomer b on a.custCode=b.code "
				+ "left join tItemType1 c on a.itemType1=c.code "
				+ "left join tXtdm d on a.status=d.cbm and d.id='ITEMRETSTATUS' "
				+ "left join tCzybm e on a.appCzy=e.czybh " + "where a.no=? ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { id });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public Result saveForProc(ItemReturn itemReturn, String xmlData) {
		Assert.notNull(itemReturn);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pItemReturn_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemReturn.getM_umState());
			call.setString(2, itemReturn.getOldStatus());
			call.setString(3, itemReturn.getNo());
			call.setString(4, itemReturn.getCustCode());
			call.setString(5, itemReturn.getItemType1());
			call.setString(6, itemReturn.getStatus());
			call.setString(7, itemReturn.getAppCzy());
			call.setTimestamp(8, itemReturn.getDate() == null ? null
					: new Timestamp(itemReturn.getDate().getTime()));
			call.setString(9, itemReturn.getRemarks());
			call.setString(10, itemReturn.getDriverCode());
			call.setString(11, itemReturn.getSendBatchNo());
			call.setString(12, itemReturn.getExpired());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);
			call.setString(15, xmlData);
			call.execute();
			result.setCode(String.valueOf(call.getInt(13)));
			result.setInfo(call.getString(14));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public int countItemReturn(String sendBatchNo) {
		String sql = "select COUNT(*) num from tItemReturn where SendBatchNo=? and Status<>6";
		return (Integer) this.findBySql(sql, new Object[] { sendBatchNo })
				.get(0).get("num");

	}

	public boolean isArrivedAll(String batchNo, String driverCode, String no) {
		String sql = "select * from tItemReturn where SendBatchNo=?  "
				+ "and DriverCode=? and Status<>6 and No<>?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {
				batchNo, driverCode, no });
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * ItemReturn分页信息
	 * 
	 * @param page
	 * @param itemReturn
	 * @return
	 */
	public Page<Map<String, Object>> findReturnDetail(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  select a.status,a.NO,a.ItemType1,a.CustCode,a.appczy,a.date,a.Remarks,a.DriverCode,a.SendBatchNo, "
				+ "b.descr ItemType1Descr,c.zwxm appczyDescr, d.address, x1.NOTE statusdescr,f.phone "
				+ ", a.CarryFee, a.TransFee, a.AutoTransFee, a.AutoCarryFee, case when isnull(a.region,'')='' then e.SendRegion else a.region end region, " 
				+ "isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else '' end)CarryType,"
				+ "isnull(a.Floor,d.CarryFloor)floor , "
				+ "isnull(sr.descr,sr2.descr) Regiondescr,x2.note CarryTypeDescr "
				+ "from tItemReturn a "
				+ "left outer join titemType1 b on b.Code = a.itemType1 "
				+ "left outer join tCZYBM  c  on C.CZYBH=a.AppCZY "
				+ "left outer join tCustomer d on d.code = a.CustCode "
				+ "left outer join tBuilder e on d.BuilderCode=e.Code "
				+ "left outer join tXTDM x1 on x1.CBM=a.Status and x1.Id='ITEMRETSTATUS' "
				+ "left outer join tEmployee f on f.Number=c.EMNum "
				+ "left outer join tSendRegion sr on sr.No=a.Region "
				+ "left outer join tXTDM x2 on  x2.id ='CarType' and x2.cbm= isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else ''end) "
				+ "left outer join tSendRegion sr2 on sr2.No = e.SendRegion "
				+ "where 1=1 ";
		if (StringUtils.isNotBlank(itemReturn.getSendBatchNo())) {
			sql += " and a.SendBatchNo=? ";
			list.add(itemReturn.getSendBatchNo());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findReturnDetailAdd(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select 0 IsPs,a.status,a.NO,a.ItemType1,a.CustCode,a.appczy,"
				+ "a.date,a.Remarks,a.DriverCode,a.SendBatchNo,"
				+ "b.descr ItemType1Descr,c.zwxm appczyDescr, d.address,e.phone "
				+ "from tItemReturn a "
				+ "left outer join titemType1 b on b.Code = a.itemType1 "
				+ "left outer join tCZYBM  c  on C.CZYBH=a.AppCZY  "
				+ "left outer join tCustomer d on d.code = a.CustCode "
				+ "left outer join tEmployee e on a.appczy=e.number "
				+ "where  a.status='2' ";
		if (StringUtils.isNotBlank(itemReturn.getNos())) {
			sql += " and a.no not in " + "('"
					+ itemReturn.getNos().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(itemReturn.getNo())) {
			sql += " and a.no like ? ";
			list.add("%" + itemReturn.getNo() + "%");
		}
		if (StringUtils.isNotBlank(itemReturn.getAddress())) {
			sql += " and d.address like ? ";
			list.add("%" + itemReturn.getAddress() + "%");
		}
		if (itemReturn.getDateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(itemReturn.getDateFrom());
		}
		if (itemReturn.getDateTo() != null) {
			sql += " and a.Date< ? ";
			list.add(DateUtil.addInteger(itemReturn.getDateTo(), Calendar.DATE,
					1));
		}
		if (StringUtils.isBlank(itemReturn.getExpired())
				|| "F".equals(itemReturn.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(itemReturn.getItemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(itemReturn.getItemType1());
		} else {
			sql += " and a.ItemType1 in ('"
					+ itemReturn.getItemRight().trim().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.Date desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * ItemReturn分页信息
	 * 
	 * @param page
	 * @param itemReturn
	 * @return
	 */
	public Page<Map<String, Object>> findReturnDetailMng(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		List<Object> list = new ArrayList<Object>();

		String sql = "  select * from (select a.LastUpdate,a.status,a.NO,a.ItemType1,a.CustCode,a.appczy,a.date,a.Remarks,a.DriverCode,a.SendBatchNo,"
				+ "b.descr ItemType1Descr,c.zwxm appczyDescr, d.address, e.Namechi DriverName ,"
				+ "x1.NOTE statusdescr,f.phone,GetDate, CompleteDate,"
				+ " isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else ''end)CarryType,"
				+ " isnull(a.Floor,d.CarryFloor)floor,x2.note CarryTypeDescr " 
				+ " from tItemReturn a "
				+ "left outer join titemType1 b on b.Code = a.itemType1 "
				+ "left outer join tCZYBM  c  on C.CZYBH=a.AppCZY "
				+ "left outer join tCustomer d on d.code = a.CustCode "
				+ "left outer join tDriver e on e.Code = a.DriverCode "
				+ "left outer join tXTDM x1 on x1.CBM=a.Status and x1.Id='ITEMRETSTATUS' "
				+ "left outer join tEmployee f on f.Number=c.EMNum "
				+ "left outer join tXTDM x2 on  x2.id ='CarType' and x2.cbm= isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else ''end) "
				+ "where 1=1";
		if (StringUtils.isNotBlank(itemReturn.getR_sendBatchNo())) {
			sql += " and a.SendBatchNo=? ";
			list.add(itemReturn.getR_sendBatchNo());
		}
		if (StringUtils.isNotBlank(itemReturn.getR_no())) {
			sql += " and a.No like ? ";
			list.add("%" + itemReturn.getR_no() + "%");
		}
		if (StringUtils.isNotBlank(itemReturn.getR_driverCode())) {
			sql += " and a.DriverCode=? ";
			list.add(itemReturn.getR_driverCode());
		}
		if (StringUtils.isNotBlank(itemReturn.getR_address())) {
			sql += " and d.address like ? ";
			list.add("%" + itemReturn.getR_address() + "%");
		}
		if (itemReturn.getR_dateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(itemReturn.getR_dateFrom());
		}
		if (itemReturn.getR_dateTo() != null) {
			sql += " and a.Date< ? ";
			list.add(DateUtil.addInteger(itemReturn.getR_dateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemReturn.getR_status())) {
			sql += " and a.status in ('"
					+ itemReturn.getR_status().trim().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(itemReturn.getR_itemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(itemReturn.getR_itemType1());
		} else {
			sql += " and a.ItemType1 in ('"
					+ itemReturn.getItemRight().trim().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public void doCancel(ItemReturn itemReturn) {
		String sql = "update  tItemReturn set status='7',LastUpdate=getdate(),LastUpdatedBy=? where Status='2' and No=?";
		this.executeUpdateBySql(
				sql,
				new Object[] { itemReturn.getLastUpdatedBy(),
						itemReturn.getNo() });
	}

	/**
	 * ItemReturn分页信息
	 * 
	 * @param page
	 * @param itemReturn
	 * @return
	 */
	public Page<Map<String, Object>> findReturnDetailQry(
			Page<Map<String, Object>> page, ItemReturn itemReturn) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.LastUpdate,a.status,a.NO,a.ItemType1,a.CustCode,a.appczy,a.date,a.Remarks,a.DriverCode,a.SendBatchNo, "
				+ "b.descr ItemType1Descr,c.zwxm appczyDescr, d.address, e.Namechi DriverName ,x1.NOTE statusdescr,f.phone , "
				+ "g.Qty, h.ItemCode, i.descr Itemdescr,m.ArriveDate, m.address Arriveaddress, m.DriverRemark,u.Descr uomDescr, N.SendDate, "
				+ "( case when j.SendType = '1' then k.address when j.SendType = '2' then w.address end ) returnAddress,"
				+ " round(i.PerWeight * g.qty, 2) totalPerWeight,te.nameChi followmandescr "
				+ "from tItemReturn a "
				+ "left outer join titemType1 b on b.Code = a.itemType1 "
				+ "left outer join tCZYBM  c  on C.CZYBH=a.AppCZY "
				+ "left outer join tCustomer d on d.code = a.CustCode "
				+ "left outer join tDriver e on e.Code = a.DriverCode "
				+ "left outer join tXTDM x1 on x1.CBM=a.Status and x1.Id='ITEMRETSTATUS' "
				+ "left outer join tEmployee f on f.Number=c.EMNum "
				+ "left outer join tItemReturnDetail g on g.No=a.No "
				+ "left outer join tItemAppDetail h on h.PK = g.AppDTPK "
				+ "left outer join tItem i on i.code = h.ItemCode "
				+ "left outer join tUom u on u.code = i.uom "
				+ "left outer join titemapp j on j.no = h.no "
				+ "left outer join tWareHouse w on w.code = j.WHCode "
				+ "left outer join tSupplier k on k.code = j.SupplCode "
				+ "left outer join tItemReturnArrive m on m.no = g.arriveNo "
				+ "left outer join tItemSendBatch  isb on isb.no=a.SendBatchNo "
				+ "left outer join tEmployee te on isb.followMan=te.number "
				+ "left outer join ( "
				+ "select  min(SendDate) SendDate,min(ArriveDate)ArriveDate,ird.No from  tItemReturnDetail ird "
				+ "inner join tItemAppDetail iad on iad.PK = ird.AppDTPK "
				+ "inner  join titemapp ia on ia.no = iad.no "
				+ "group by  ird.No )n on n.no=a.no where 1=1 ";
		if (StringUtils.isNotBlank(itemReturn.getR_sendBatchNo())) {
			sql += " and a.SendBatchNo=? ";
			list.add(itemReturn.getR_sendBatchNo());
		}
		if (StringUtils.isNotBlank(itemReturn.getR_no())) {
			sql += " and a.No like ? ";
			list.add("%" + itemReturn.getR_no() + "%");
		}
		if (StringUtils.isNotBlank(itemReturn.getR_driverCode())) {
			sql += " and a.DriverCode=? ";
			list.add(itemReturn.getR_driverCode());
		}
		if (StringUtils.isNotBlank(itemReturn.getR_address())) {
			sql += " and d.address like ? ";
			list.add("%" + itemReturn.getR_address() + "%");
		}
		if (itemReturn.getR_dateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(itemReturn.getR_dateFrom());
		}
		if (itemReturn.getR_dateTo() != null) {
			sql += " and a.Date< ? ";
			list.add(DateUtil.addInteger(itemReturn.getR_dateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemReturn.getR_status())) {
			sql += " and a.status in ('"
					+ itemReturn.getR_status().trim().replace(",", "','")
					+ "')";
		}
		if (itemReturn.getCreateDateFrom() != null) {
			sql += " and isb.Date>= ? ";
			list.add(itemReturn.getCreateDateFrom());
		}
		if (itemReturn.getCreateDateTo() != null) {
			sql += " and isb.Date< ? ";
			list.add(DateUtil.addInteger(itemReturn.getCreateDateTo(),
					Calendar.DATE, 1));
		}
		if (itemReturn.getArriveDateFrom() != null) {
			sql += " and m.ArriveDate>= ? ";
			list.add(itemReturn.getArriveDateFrom());
		}
		if (itemReturn.getArriveDateTo() != null) {
			sql += " and m.ArriveDate< ? ";
			list.add(DateUtil.addInteger(itemReturn.getArriveDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemReturn.getR_itemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(itemReturn.getR_itemType1());
		} else {
			sql += " and a.ItemType1 in ('"
					+ itemReturn.getItemRight().trim().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 自动生成搬运费
	 * 
	 * @param itemReturn
	 * @return
	 */
	public List<Map<String, Object>> autoCteateFee(
			ItemReturn itemReturn) {
		Assert.notNull(itemReturn);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPsgl_thscbyf(?,?)}");
			call.setString(1, itemReturn.getLastUpdatedBy());
			call.setString(2, itemReturn.getItemReturnJson());
			call.execute();
			ResultSet rs= call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
}
