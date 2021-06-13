package com.house.home.dao.driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.entity.project.WorkCostDetail;

@SuppressWarnings("serial")
@Repository
public class ItemAppSendDao extends BaseDao {
	public Page<Map<String, Object>> findPageByDriver(
			Page<Map<String, Object>> page, String driver, String address) {
		List<Object> list = new ArrayList<Object>();
		list.add(driver);
		String sql = "select a.No, a.IANO,d.DATE,c.address from (tItemAppSend a "
				+ "INNER join tItemApp b on a.IANo=b.no) "
				+ "INNER join tCustomer c on c.Code=b.CustCode "
				+ "left join tItemSendBatch d on a.SendBatchNo=d.No "
				+ "where a.DriverCode=? AND  a.SendStatus='1'";
		if (StringUtils.isNotBlank(address)) {
			sql += " and c.address like ? ";
			list.add("%" + address + "%");
		}
		sql += " order by d.date desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getSendDetailById(String no) {
		String sql = "select  a.no,a.ConfirmReason,a.ProjectManRemark,h.NOTE ConfirmStatusDescr,b.custCode,d.number, " +
				" g.Descr, a.Date, c.address,d.namechi,d.phone,case when b.SendType=1 then e.address else f.address end receiveAddress," +
				" (b.OtherCost+b.ProjectOtherCost) OtherCost,b.OtherCostAdj,b.Date AppDate " +
				" from ((tItemAppSend a inner join tItemApp b on a.IANo=b.No) "
				+ "inner join tCustomer c on b.CustCode=c.Code) " +
				" inner join tEmployee d on c.ProjectMan=d.Number "
				+ "left join tSupplier e on b.SupplCode=e.Code "
				+ "left join tWareHouse f on b.WHCode=f.Code INNER JOIN  tItemType1 g ON b.ItemType1=g.Code "
				+ "LEFT JOIN dbo.tXTDM h ON h.ID='SENDCFMSTATUS' AND h.CBM=a.ConfirmStatus where a.No=? ";

		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { no });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getMaterialList(String no) {
		String sql = "select e.Descr,a.sendQty,c.descr,d.descr uom from (tItemAppSendDetail a inner join tItemAppDetail b on a.RefPk=b.pk)"
				+ "inner join tItem c on b.ItemCode=c.code left join tUOM d on c.Uom=d.code INNER JOIN  tItemType2 e ON c.ItemType2 =e.Code where a.No=?";
		return this.findBySql(sql, new Object[] { no });
	}

	@SuppressWarnings("unchecked")
	public ItemAppSend getAppSendById(String no) {
		String sql = "from ItemAppSend where no=?";
		List<ItemAppSend> list = this.find(sql, new Object[] { no });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ItemSendBatch getSendBatchById(String no) {
		String hql = "from ItemSendBatch where no=?";
		List<ItemSendBatch> list = this.find(hql, new Object[] { no });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public boolean isArrivedAll(String batchNo, String driverCode, String no) {
		String sql = "select * from tItemAppSend where SendBatchNo=?  "
				+ "and DriverCode=? and SendStatus<>2 and No<>?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {
				batchNo, driverCode, no });
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	public Page<Map<String, Object>> findPageByDriverArrived(
			Page<Map<String, Object>> page, String driver, String addrsss) {
		List<Object> list = new ArrayList<Object>();
		list.add(driver);
		String sql = "select a.No, a.IANO,a.arriveDate,c.address from (tItemAppSend a "
				+ "INNER join tItemApp b on a.IANo=b.no) "
				+ "INNER join tCustomer c on c.Code=b.CustCode "
				+ "where a.DriverCode=? AND  a.SendStatus='2' ";
		if (StringUtils.isNotBlank(addrsss)) {
			sql += "and c.address like ?";
			list.add("%" + addrsss + "%");
		}
		System.out.println("sdsd");
		sql += "order by a.arriveDate desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public int countItemSend(String sendBatchNo) {
		String sql = "select COUNT(*) num from tItemAppSend	 where SendBatchNo=? and sendStatus<>2";
		return (Integer) this.findBySql(sql, new Object[] { sendBatchNo })
				.get(0).get("num");
	}

	public Map<String, Object> getSendArrivedDetailById(String id) {
		String sql = "select a.DriverRemark,a.ArriveAddress,a.arriveDate,a.Date, c.address,d.namechi,d.phone,case when b.SendType=1 then e.address else f.address end receiveAddress from ((tItemAppSend a inner join tItemApp b on a.IANo=b.No) "
				+ "inner join tCustomer c on b.CustCode=c.Code) inner join tEmployee d on c.ProjectMan=d.Number "
				+ "left join tSupplier e on b.SupplCode=e.Code "
				+ "left join tWareHouse f on b.WHCode=f.Code where a.No=? AND  a.SendStatus='2'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { id });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String, Object>> findPageByIaNo(
			Page<Map<String, Object>> page, String iaNo) {
		String sql = "select * from (select a.no,a.IANo,a.Date,a.WHCode,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,"
				+ "b.Desc1 WHCodeDescr from tItemAppSend a "
				+ "left join tWareHouse b on a.WHCode=b.code "
				+ "where a.iano=? ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}

		return this.findPageBySql(page, sql, new Object[] { iaNo });
	}

	public Page<Map<String, Object>> getItemAppSendConfirmList(
			Page<Map<String, Object>> page, String projectMan, String address) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT a.No,a.ConfirmStatus,a.Date,a.ConfirmReason,a.ProjectManRemark,c.Descr itemType1Descr,d.Descr itemType2Descr,e.Address,f.NOTE ConfirmStatusDescr FROM  tItemAppSend a INNER JOIN tItemApp b ON a.IANo=b.No "
				+ "LEFT JOIN dbo.tItemType1 c ON b.ItemType1=c.Code "
				+ "LEFT JOIN dbo.tItemType2 d ON b.ItemType2=d.Code "
				+ "LEFT JOIN dbo.tCustomer e ON e.Code=b.CustCode "
				+ "LEFT JOIN dbo.tXTDM f ON f.ID='SENDCFMSTATUS' AND f.CBM=a.ConfirmStatus "
				+ "WHERE a.ConfirmStatus IN ('0','2') ";
		if (StringUtils.isNotBlank(projectMan)) {
			sql += " and e.ProjectMan=? ";
			list.add(projectMan);
		}
		if (StringUtils.isNotBlank(address)) {
			sql += " and e.address like ?";
			list.add("%" + address + "%");
		}
		sql += "order by  a.date desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findSendDetail(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select  a.lastupdate,a.lastupdatedby,a.no , a.IANo , a.date ,a.WHCode ,x1.NOTE SendType,d.address,b.Descr SupplierDescr,"
				+ "w.desc1 WHDescr,a.driverRemark,a.DriverCode,a.arriveDate,a.ArriveAddress, "
				+ " a.SendStatus,a.SendBatchNo,a.sendFee,a.CarryFee,i1.Descr ItemType1Descr,"
				+ "i2.Descr ItemType2Descr,x2.note SendStatusDescr, t1.ConfirmDate, "
				+ " ( case when t1.SendType='1' then  b.address  when  t1.SendType='2' then  w.address end )TakeAddress "
				+ " ,a.TransFee,a.AutoTransFee,a.AutoCarryFee,case when isnull(a.region,'')='' then e.SendRegion else a.region end region," 
				+ " isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else ''end)CarryType,"
				+ " isnull(a.Floor,d.CarryFloor)floor ," 
				+ " isnull(sr.descr,sr2.descr) Regiondescr,x3.note  CarryTypeDescr,t1.CustCode,AutoTransFeeAdj,TransFeeAdj,ManySendRemarks "
				+ " from tItemAppSend  a "
				+ " left outer join tItemApp t1 on t1.no = a.IANo "
				+ " left outer join tXTDM x1 on x1.IBM = t1.SendType and x1.id = 'ITEMAPPSENDTYPE' "
				+ " left outer join tSupplier b on b.code=t1.SupplCode "
				+ " left outer join tWareHouse w on w.code = a.WHCode "
				+ " left outer join tCustomer d on d.Code = t1.CustCode "
				+ " left outer join tBuilder e on d.BuilderCode=e.Code "
				+ " left outer join titemtype1 i1 on i1.Code=t1.ItemType1 "
				+ " left outer join titemtype2 i2 on i2.code=t1.ItemType2 "
				+ " left outer join txtdm x2 on x2.cbm=a.sendstatus and x2.id='APPSENDSTATUS' "
				+ " left outer join tSendRegion sr on sr.No=a.Region "
				+ " left outer join tXTDM x3 on  x3.id = 'CarType' and x3.cbm = isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else ''end) "
				+ " left outer join tSendRegion sr2 on sr2.No = e.SendRegion "
				+ " where 1=1 " ;
		if (StringUtils.isNotBlank(itemAppSend.getSendBatchNo())) {
			sql += " and a.SendBatchNo = ? ";
			list.add(itemAppSend.getSendBatchNo());
		}
		sql+=" order by a.no ";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findSendDetailAdd(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select * from(select 0 IsPs,a.SendStatus, a.no, a.IANo , a.date ,a.WHCode ,x1.NOTE SendType,d.address,b.Descr SupplierDescr,w.desc1 WHDescr, "
				+ " a.SendBatchNo,t1.DelivType,a.SendFee,a.CarryFee, i1.Descr ItemType1Descr,i2.Descr ItemType2Descr,t1.ConfirmDate, "
				+ " ( case when t1.SendType='1' then  b.address  when  t1.SendType='2' then  w.address end )TakeAddress  "
				+ " from tItemAppSend  a  "
				+ " left outer join tItemApp t1 on t1.no = a.IANo  "
				+ " left outer join tXTDM x1 on x1.IBM = t1.SendType and x1.id = 'ITEMAPPSENDTYPE' "
				+ " left outer join tSupplier b on b.code=t1.SupplCode  "
				+ " left outer join tWareHouse w on w.code = a.WHCode  "
				+ " left outer join tCustomer d on d.Code = t1.CustCode  "
				+ " left outer join titemtype1 i1 on i1.Code=t1.ItemType1  "
				+ " left outer join titemtype2 i2 on i2.code=t1.ItemType2  "
				+ " where t1.DelivType in ('1','4') and a.SendStatus='0' ";
		if (StringUtils.isNotBlank(itemAppSend.getNos())) {
			sql += " and a.no not in " + "('"
					+ itemAppSend.getNos().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(itemAppSend.getNo())) {
			sql += " and a.no like ? ";
			list.add("%" + itemAppSend.getNo() + "%");
		}
		if (StringUtils.isNotBlank(itemAppSend.getIaNo())) {
			sql += " and a.IANo like ? ";
			list.add("%" + itemAppSend.getIaNo() + "%");
		}
		if (StringUtils.isNotBlank(itemAppSend.getWhCode())) {
			sql += " and a.WhCode = ? ";
			list.add(itemAppSend.getWhCode());
		}
		if (itemAppSend.getDateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(itemAppSend.getDateFrom());
		}
		if (itemAppSend.getDateTo() != null) {
			sql += " and a.Date< ? ";
			list.add(DateUtil.addInteger(itemAppSend.getDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemAppSend.getItemType1())) {
			sql += " and t1.ItemType1 = ? ";
			list.add(itemAppSend.getItemType1());
		} else {
			sql += " and t1.ItemType1 in ('"
					+ itemAppSend.getItemRight().trim().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isBlank(itemAppSend.getExpired())
				|| "F".equals(itemAppSend.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(itemAppSend.getSendType())) {
			sql += " and t1.SendType = ? ";
			list.add(itemAppSend.getSendType());
		}
		sql+=")b where 1=1 ";
		if(StringUtils.isNotBlank(itemAppSend.getAddress())){
			sql+=" and b.address like ? ";
			list.add("%"+itemAppSend.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.Date desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findSendDetailMng(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from(select * from (select a.LastUpdate,a.no,a.IANo,a.date ,a.WHCode ,x1.NOTE SendType,d.address,b.Descr SupplierDescr,"
				+ "a.driverRemark,a.arriveDate,a.ArriveAddress,a.IsConfirm ,a.ProjectManRemark,x3.note IsConfirmDescr , "
				+ "a.SendBatchNo,a.SendStatus,a.SendFee,a.CarryFee,x2.note SendStatusDescr,dr.namechi,isb.remarks sendBatchRemarks,"
				+ "i1.Descr ItemType1Descr,i2.Descr ItemType2Descr,t1.ConfirmDate,w.desc1 WHDescr,e.SendRegion region,sr.descr Regiondescr,"
				+ "( case when t1.SendType='1' then  b.address  when  t1.SendType='2' then  w.address end )TakeAddress,a.transfee,ManySendRemarks,TransFeeAdj, "
				+ "isnull(tfac.TransFeeAdjCount, 0) TransFeeAdjCount,round(isnull((select sum(in_a.Cost * in_a.SendQty) from tItemAppSendDetail in_a where in_a.No = a.No), 0), 2) allCost, "
				+ " isnull(a.carrytype,case when d.installelev='1' then '1' when d.installelev='0' then '2'  else '' end)carrytype,x4.note  CarryTypeDescr, "
				+ " isnull(a.floor, d.carryfloor)floor "
				+ "from tItemAppSend  a  "
				+ "left outer join tItemApp t1 on t1.no = a.IANo "
				+ "left outer join tXTDM x1 on x1.cbm = t1.SendType and x1.id = 'ITEMAPPSENDTYPE' "
				+ "left outer join tSupplier b on b.code=t1.SupplCode "
				+ "left outer join tWareHouse w on w.code = a.WHCode "
				+ "left outer join tCustomer d on d.Code = t1.CustCode "
				+ "left outer join tBuilder e on d.BuilderCode=e.Code "
				+ "left outer join txtdm x2 on x2.cbm=a.sendstatus and x2.id='APPSENDSTATUS' "
				+ "left outer join tDriver dr on dr.code=a.DriverCode "
				+ "left outer join titemtype1 i1 on i1.Code=t1.ItemType1 "
				+ "left outer join titemtype2 i2 on i2.code=t1.ItemType2 "
				+ "left outer join txtdm x3 on x3.cbm=a.IsConfirm and x3.id='YESNO' "
				+ "left outer join tSendRegion sr on sr.No=e.SendRegion "
				+ "left outer join tXTDM x4 on x4.id = 'CarType' and x4.cbm = isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else ''end) "
				+ "left join ( "
				+ " 	select tia.CustCode,count(*) TransFeeAdjCount "
				+ " 	from tItemAppSend tias "
				+ " 	left join tItemApp tia on tias.IANo = tia.No "
				+ " 	where tias.TransFeeAdj <> 0 "
				+ " 	group by tia.CustCode "
				+ ") tfac on tfac.CustCode = d.Code "
				+ "left join tItemSendBatch isb on a.SendBatchNo=isb.No "
				+ "where t1.DelivType in ('1', '4') ";
		if (StringUtils.isNotBlank(itemAppSend.getSendBatchNo())) {
			sql += " and a.SendBatchNo = ? ";
			list.add(itemAppSend.getSendBatchNo());
		}
		if (StringUtils.isNotBlank(itemAppSend.getNo())) {
			sql += " and a.No like ? ";
			list.add("%" + itemAppSend.getNo() + "%");
		}
		if (StringUtils.isNotBlank(itemAppSend.getIaNo())) {
			sql += " and a.IANo = ? ";
			list.add(itemAppSend.getIaNo());
		}
		if (StringUtils.isNotBlank(itemAppSend.getDriverCode())) {
			sql += " and a.DriverCode = ? ";
			list.add(itemAppSend.getDriverCode());
		}
		if (itemAppSend.getDateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(itemAppSend.getDateFrom());
		}
		if (itemAppSend.getDateTo() != null) {
			sql += " and a.Date< ? ";
			list.add(DateUtil.addInteger(itemAppSend.getDateTo(),
					Calendar.DATE, 1));
		}
		if (itemAppSend.getCreateDateFrom() != null) {
			sql += " and isb.Date>= ? ";
			list.add(itemAppSend.getCreateDateFrom());
		}
		if (itemAppSend.getCreateDateTo() != null) {
			sql += " and isb.Date< ? ";
			list.add(DateUtil.addInteger(itemAppSend.getCreateDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemAppSend.getSendStatus())) {
			sql += " and a.SendStatus in ('"
					+ itemAppSend.getSendStatus().trim().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(itemAppSend.getItemType1())) {
			sql += " and t1.ItemType1 = ? ";
			list.add(itemAppSend.getItemType1());
		} else {
			sql += " and t1.ItemType1 in ('"
					+ itemAppSend.getItemRight().trim().replace(",", "','")
					+ "')";
		}
		sql+=")b where 1=1 ";
		if(StringUtils.isNotBlank(itemAppSend.getAddress())){
			sql+=" and b.address like ? ";
			list.add("%"+itemAppSend.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public void doArrive(ItemAppSend itemAppSend) {
		String sql = " update tItemAppSend set  sendstatus='2',LastUpdate=getdate(),LastUpdatedBy=?, driverRemark=? where no=? and SendStatus='0' ";
		this.executeUpdateBySql(
				sql,
				new Object[] {itemAppSend.getLastUpdatedBy(), itemAppSend.getDriverRemark(),
						itemAppSend.getNo() });
	}

	public Page<Map<String, Object>> findSendDetailQry(
			Page<Map<String, Object>> page, ItemAppSend itemAppSend) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select * from (select a.LastUpdate,a.no,a.IANo,a.date ,a.WHCode ,x1.NOTE SendType,t1.CustCode,d.address,d.DocumentNo,b.Descr SupplierDescr,w.desc1 WHDescr, "
				+ "a.driverRemark,a.arriveDate,a.ArriveAddress,a.IsConfirm,a.ProjectManRemark,x3.note IsConfirmDescr ,trg.Descr RegionDescr, "
				+ "a.SendBatchNo,a.SendStatus,a.SendFee,a.CarryFee,x2.note SendStatusDescr,dr.namechi,i1.Descr ItemType1Descr,i2.Descr ItemType2Descr, "
				+ "( case when t1.SendType='1' then  b.address  when  t1.SendType='2' then  w.address end )TakeAddress,t1.ConfirmDate, "
				+ "e.SendQty, f.ItemCode, g.descr Itemdescr,u.Descr uomDescr, f.Remarks,round(g.PerWeight * e.SendQty, 2) totalPerWeight ,isb.Remarks psRemarks, "
				+ "e.SendQty*isnull(e.cost,0) costamount,h.nameChi followmandescr,a.transfee,TransFeeAdj,ManySendRemarks, "
				+ "case when  sfr.caltype='1' then case when e.SendQty < 0 then -1 else 1 end * e.SendQty * g.PerWeight / 1000  * sr.TransFee  "
				+ "when  sfr.caltype='2' then case when e.SendQty < 0 then -1 else 1 end * e.SendQty * sfr.Price "
				+ "else 0 end   AutoTransFee, "
				+ "(case when cr.caltype='1' then case when e.SendQty < 0 then -1 else 1  end * e.SendQty * g.PerWeight / 1000 "
				+ " when cr.caltype='2' then case when e.SendQty < 0 then -1 else 1 end * e.SendQty " 
				+ " when cr.caltype='3'  then 1 else 0 end)* "
				+ "isnull((case when a.floor < crf.BeginFloor then 0  else (a.floor-crf.BeginFloor)* crf.IncValue + crf.CardAmount end),0) AutoCarryFee, "
				+ " isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else ''end)CarryType,"
				+ " isnull(a.Floor,d.CarryFloor)floor , x4.note  CarryTypeDescr "
				+ "from tItemAppSend  a "
				+ "left outer join tItemApp t1 on t1.no = a.IANo "
				+ "left outer join tXTDM x1 on x1.cbm = t1.SendType and x1.id = 'ITEMAPPSENDTYPE' "
				+ "left outer join tSupplier b on b.code=t1.SupplCode "
				+ "left outer join tWareHouse w on w.code = a.WHCode "
				+ "left outer join tCustomer d on d.Code = t1.CustCode "
				+ "left outer join tBuilder tbd on d.BuilderCode = tbd.code "
				+ "left outer join tSendRegion trg on tbd.SendRegion = trg.No "
				+ "left outer join txtdm x2 on x2.cbm=a.sendstatus and x2.id='APPSENDSTATUS' "
				+ "left outer join tDriver dr on dr.code=a.DriverCode "
				+ "left outer join titemtype1 i1 on i1.Code=t1.ItemType1 "
				+ "left outer join txtdm x3 on x3.cbm=a.IsConfirm and x3.id='YESNO' "
				+ "left outer join tItemAppSendDetail e on e.no=a.No "
				+ "left outer join tItemAppDetail f on f.PK = e.RefPk "
				+ "left outer join tItem g on g.code = f.ItemCode "
				+ "left outer join titemtype2 i2 on i2.code=g.ItemType2 "//改成显示材料信息对应的材料类型2 
				+ "left outer join tUom u on u.code = g.uom "
				+ "left outer join tItemSendBatch  isb on isb.no=a.SendBatchNo "
				+ "left outer join tEmployee h on isb.followMan=h.number "
				+ "left outer join tSendRegion sr on sr.No = a.Region  "
				+ "left outer join tXTDM x4 on  x4.id = 'CarType'and x4.cbm = isnull(a.CarryType,case when d.InstallElev='1' then '1' when d.InstallElev='0' then '2' else ''end) "
				+ "left outer join tSendFeeRule sfr on sfr.ItemType1 = g.ItemType1 and sfr.ItemType2 =g.ItemType2 and sfr.Expired = 'F'  " // 车费
				+ "		and sfr.sendType='2' and isnull(sfr.WHCode,'')='' "
				+ "		and (exists ( select 1 from tSendFeeRuleItem cri inner join tSendFeeRule cr1 on cr1.no = cri.no " // 存在匹配的材料类型3  
				+ "				  where  cri.no = sfr.no and cri.ItemType3 = g.ItemType3 ) "
				+ "			 or (not exists ( select 1 from tSendFeeRuleItem cri " // 当没有存在匹配的材料类型3规则，才匹配不带材料规则项 
				+ "						  inner join tSendFeeRule cr1 on cr1.no = cri.no " 
				+ "						  where cri.ItemType3 = g.ItemType3 and cr1.SendType='2' and  cr1.Expired = 'F' and isnull(cr1.WHCode,'')='')  "
				+ "				 and not exists ( select 1 from tSendFeeRuleItem cri " 
				+ "					inner join  tSendFeeRule cr1 on cr1.no = cri.no where cri.no = sfr.no ) "
				+ "			 ) "
				+ "		) "            
		        + "left  outer join tCarryRule cr on cr.ItemType1 = g.ItemType1  and cr.ItemType2 = g.ItemType2 and cr.CarryType = a.CarryType "
				+ "	 	and cr.Expired = 'F' and cr.sendType='2' and isnull(cr.WHCode,'')=''  "
				+ "		and (exists ( select 1 from tCarryRuleItem cri inner join tCarryRule cr1 on cr1.no = cri.no  " // 存在匹配的材料类型3
				+ "				   	  where cri.no = cr.no and cri.ItemType3 = g.ItemType3 and cr1.CarryType = a.CarryType )  "
				+ "			 or (not exists ( select 1 from tCarryRuleItem cri inner join tCarryRule cr1 on cr1.no = cri.no  " // 当没有存在匹配的材料类型3规则，才匹配不带材料规则项 
				+ "						  where cri.ItemType3 = g.ItemType3 and cr1.CarryType = a.CarryType and cr1.SendType='2'  "
				+ "						  and cr1.Expired = 'F' and isnull(cr1.WHCode,'')=''  )  "
				+ "			 	 and not exists ( select 1 from tCarryRuleItem cri "
				+ "					  inner join tCarryRule cr1 on cr1.no = cri.no "
				+ "					  where cri.no = cr.no and cr1.CarryType = a.CarryType ) "
				+ "		 	) "
				+ "	 ) " 
				+ " left  outer join tCarryRuleFloor crf on crf.no = cr.No and ((a.Floor >= crf.BeginFloor and a.Floor <= crf.EndFloor) or a.floor is  null) "
				+ "where t1.DelivType in ('1', '4') ";
		if (StringUtils.isNotBlank(itemAppSend.getSendBatchNo())) {
			sql += " and a.SendBatchNo = ? ";
			list.add(itemAppSend.getSendBatchNo());
		}
		if (StringUtils.isNotBlank(itemAppSend.getNo())) {
			sql += " and a.No like ? ";
			list.add("%" + itemAppSend.getNo() + "%");
		}
		if (StringUtils.isNotBlank(itemAppSend.getIaNo())) {
			sql += " and a.IANo = ? ";
			list.add(itemAppSend.getIaNo());
		}
		if (StringUtils.isNotBlank(itemAppSend.getDriverCode())) {
			sql += " and a.DriverCode = ? ";
			list.add(itemAppSend.getDriverCode());
		}
		if (itemAppSend.getDateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(itemAppSend.getDateFrom());
		}
		if (itemAppSend.getDateTo() != null) {
			sql += " and a.Date< ? ";
			list.add(DateUtil.addInteger(itemAppSend.getDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemAppSend.getSendStatus())) {
			sql += " and a.SendStatus in ('"
					+ itemAppSend.getSendStatus().trim().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(itemAppSend.getItemType1())) {
			sql += " and t1.ItemType1 = ? ";
			list.add(itemAppSend.getItemType1());
		} else {
			sql += " and t1.ItemType1 in ('"
					+ itemAppSend.getItemRight().trim().replace(",", "','")
					+ "')";
		}
		if (itemAppSend.getCreateDateFrom() != null) {
			sql += " and isb.Date>= ? ";
			list.add(itemAppSend.getCreateDateFrom());
		}
		if (itemAppSend.getCreateDateTo() != null) {
			sql += " and isb.Date< ? ";
			list.add(DateUtil.addInteger(itemAppSend.getCreateDateTo(),
					Calendar.DATE, 1));
		}
		if (itemAppSend.getArriveDateFrom() != null) {
			sql += " and a.ArriveDate>= ? ";
			list.add(itemAppSend.getArriveDateFrom());
		}
		if (itemAppSend.getArriveDateTo() != null) {
			sql += " and a.ArriveDate< ? ";
			list.add(DateUtil.addInteger(itemAppSend.getArriveDateTo(),
					Calendar.DATE, 1));
		}
		sql+=")b where 1=1 ";
		if(StringUtils.isNotBlank(itemAppSend.getAddress())){
			sql+=" and b.address like ? ";
			list.add("%"+itemAppSend.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 自动生成搬运费
	 * 
	 * @param itemAppSend
	 * @return
	 */
	public Map<String, Object> autoCteateFee(
			ItemAppSend itemAppSend) {
		Assert.notNull(itemAppSend);
		Connection conn = null;
		CallableStatement call = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPsgl_fhscbyf(?)}");
			call.setString(1, itemAppSend.getItemAppSendJson());
			call.execute();
			ResultSet rs1 = call.getResultSet();
			List<Map<String, Object>> list1 = BeanConvertUtil
					.resultSetToList(rs1);
			map.put("list1", list1);
			//下一个结果集
		/*	if (call.getMoreResults()){
				ResultSet rs2 = call.getResultSet();
				List<Map<String, Object>> list2 = BeanConvertUtil
						.resultSetToList(rs2);
				System.out.println(list2);
				map.put("list2", list2);
			}*/
			System.out.println(map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}

	public List<Map<String, Object>> findNoSendYunPic(){
		String sql = " select top 100 PK, PhotoName "
				   + " from tItemSendPhoto "
				   + " where (IsSendYun is null or IsSendYun = '0') and LastUpdate >= dateadd(day,-30,getdate()) "
				   + " order by PK desc ";
		return this.findBySql(sql, new Object[]{});
	}
}
