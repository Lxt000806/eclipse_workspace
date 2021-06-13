package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.SaleCust;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.entity.insales.SalesInvoice;
@Repository
@SuppressWarnings("serial")
public class SalesInvoiceDao extends BaseDao {
	@Autowired
	private HttpServletRequest request;
	/**
	 * SalesInvoice分页信息
	 * 
	 * @param page
	 * @param salesInvoice
	 * @return
	 * dd
	 */
	/*public Page<Map<String , Object>> findPageBySql(
				Page<Map<String , Object>> page,SalesInvoice salesInvoice){
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.No,a.Type,b.itcode,i.descr itdescr,a.ItemType1,a.CustCode,a.Status,a.Remarks,a.Date , a.GetItemDate," +
				"a.WHCode,a.CustName,a.Amount,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog ,s1.Note statusdescr, " +
				"s2.Note typedescr ,s3.Note itemtype1descr ,w.Desc1 warehouse, sc.Mobile1 from tSalesInvoice a " 
				 +"left outer join tWareHouse w on a.WHCode=w.Code "
				 +"left outer join tSalesInvoiceDetail b on a.No=b.SINo " //
				 +"left outer join tItem i on i.Code=b.ItCode "//
				 +"left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='SALESINVSTATUS' "
				 +"left outer join tXTDM s2 on a.Type=s2.CBM and s2.ID='SALESINVTYPE' "
				 +"left outer join tXTDM s3 on a.itemType1=s3.CBM and s3.ID='ITEMRIGHT' " +
				 " left join tSaleCust sc on sc.Code = a.CustCode "// 添加手机等信息 --add by zb
				 +" where 1=1 and type<>'R' ";
			//begin 增加销售类型、状态查询条件 --add by zb
			if (StringUtils.isNotBlank(salesInvoice.getType())) {
				sql += " and a.Type = ? ";
				list.add(salesInvoice.getType());
			}
			if (StringUtils.isNotBlank(salesInvoice.getStatus())) {
				sql += " and a.Status = ? ";
				list.add(salesInvoice.getStatus());
			}
			//end
			if (StringUtils.isNotBlank(salesInvoice.getCustCode())) {
				sql += " and a.CustCode=? ";
				list.add(salesInvoice.getCustCode());
			}
			if (StringUtils.isNotBlank(salesInvoice.getItCode())) {
				sql += " and b.itCode=? ";
				list.add(salesInvoice.getItCode());
			}
			if (StringUtils.isNotBlank(salesInvoice.getItCodeDescr())) {
				sql += " and i.descr like ? ";
				list.add("%"+salesInvoice.getItCodeDescr()+"%");
			}
		
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += ") a order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += ") a order by a.No desc";
			}
		
		return this.findPageBySql(page, sql, list.toArray());

	}*/

	/**
	 * @Description:  code分页查询代替原来的查询
	 * @author	created by zb
	 * @date	2018-11-7--下午6:36:00
	 * @param page
	 * @param salesInvoice
	 * @return
	 */
	public Page<Map<String , Object>> findPageBySql(
				Page<Map<String , Object>> page,SalesInvoice salesInvoice, Czybm czybm){
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from(select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.CustCode,a.CustName, " +
				"a.Amount,a.Status,s1.Note StatusDescr,a.Date,a.GetItemDate,w.Desc1 WareHouse,sc.Address,sc.Phone1, " +
				"sc.Mobile1,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.WHCode  " +
				"from tSalesInvoice a  " +
				"inner join tSaleCust sc on sc.Code=a.CustCode  " +
				"left outer join tItemType1 i on a.ItemType1=i.Code  " +
				"left outer join tWareHouse w on a.WHCode=w.Code  " +
				"left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='SALESINVSTATUS'  " +
				"left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='SALESINVTYPE'  " +
				"where 1=1 ";
		
		//在操作员编码表中搜索材料权限
		if (StringUtils.isNotBlank(salesInvoice.getItemRight())) {
			sql += " and a.Itemtype1 in ('"+salesInvoice.getItemRight().trim().replace(",", "','")+"') ";
		}
		//begin 增加销售类型、状态查询条件
		if (StringUtils.isNotBlank(salesInvoice.getType())) {
			sql += " and a.Type = ? ";
			list.add(salesInvoice.getType());
		}
		if (StringUtils.isNotBlank(salesInvoice.getStatus())) {
			sql += " and a.Status = ? ";
			list.add(salesInvoice.getStatus());
		}
		//end
		if (StringUtils.isNotBlank(salesInvoice.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(salesInvoice.getCustCode());
		}
		if (StringUtils.isNotBlank(salesInvoice.getItCode())) {
			sql += " and exists(" +
					"	select ITCode " +
					"	from tSalesInvoiceDetail sid2 " +
					"	where sid2.SINo=a.no and ITCode=?" +
					") ";
			list.add(salesInvoice.getItCode());
		}
		if (StringUtils.isNotBlank(salesInvoice.getItCodeDescr())) {
			sql += " and exists( " +
					"	select * " +
					"	from (select ITCode from tSalesInvoiceDetail sid where sid.SINo=a.no) ic  " +
					"	left outer join tItem it on it.Code=ic.ITCode  " +
					"	where it.descr like ? " +
					") ";
			list.add("%"+salesInvoice.getItCodeDescr()+"%");
		}
		if (czybm!=null && "2".equals(czybm.getCzylb())) {
			sql += " and exists( " +
				   "  select 1 from tWareHouseOperater in_a where in_a.czybh=? and a.whCode=in_a.whCode " +	
				   ") ";
			list.add(czybm.getCzybh());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.Date desc,a.GetItemDate desc,a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}

	/**
	 * @Description:  销售订单管理分页查询
	 * @author	created by zb
	 * @date	2018-9-14--下午4:18:51
	 * @param page
	 * @param salesInvoice
	 * @return
	 */
	public Page<Map<String, Object>> findSalesInvoicePageBySql(
			Page<Map<String, Object>> page, SalesInvoice salesInvoice) {
		List<Object> list = new ArrayList<Object>();
		UserContext uc = (UserContext) request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT_KEY);
		String sql = "select * from (select a.No,a.Status,s1.Note StatusDescr,a.Date,a.ItemType1,it.Descr ItemType1Descr,a.Type ,s2.Note TypeDescr, " +
				" a.GetItemDate,a.WHCode,w.Desc1 WareHouse,a.CustCode,cc.Desc1 CustDescr,cc.Address,cc.Mobile1, " +
				" case when a.Type='S' then a.BefAmount else a.BefAmount*-1 end BefAmount, " +
				" a.CalOnDiscAmt,i2.NOTE CalOnDiscAmtDescr,a.DiscPercentage, " +
				" case when a.Type='S' then a.DiscAmount else a.DiscAmount*-1 end DiscAmount, " +
				" case when a.Type='S' then a.Amount else a.Amount*-1 end Amount, " +
				" case when a.Type='S' then a.OtherAmount else a.OtherAmount*-1 end OtherAmount, " +
				" case when a.Type='S' then a.FirstAmount else a.FirstAmount*-1 end FirstAmount, " +
				" case when a.Type='S' then a.FirstCash else  a.FirstCash *-1 end FirstCash, " +
				" case when a.Type='S' then a.SecondAmount else a.SecondAmount*-1 end SecondAmount, " +
				" case when a.Type='S' then a.SecondCash else a.SecondCash*-1 end SecondCash, " +
				" case when a.Type='S' then a.RemainAmount else a.RemainAmount*-1 end RemainAmount, " +
				" a.SaleMan,e1.NameChi SaleManDescr,a.SoftDesign,e2.NameChi SoftDesignDescr, " +
				" a.SaleDirt,e3.NameChi SaleDirtDescr,a.Remarks,a.IsCal,i.Note IsCalDescr,a.CalNo,a.OldNo, " +
				" a.IsAuthorized,i3.Note IsAuthorizedDescr,a.Authorizer,e4.NameChi AuthorizerDescr, " +
				" wco.DocumentNo WHCheckOutDocumentNo, " +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
				" from tSalesInvoice a " +
				" left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='SALESINVSTATUS' " +
				" left outer join tWareHouse w on a.WHCode=w.Code " +
				" left outer join tXTDM  s2 on a.Type=s2.CBM and s2.ID='SALESINVTYPE' " +
				" left outer join tXTDM  i2 on a.CalOnDiscAmt=i2.CBM and i2.ID='YESNO' " +
				" left outer join tEmployee e1 on a.SaleMan=e1.Number " +
				" left outer join tEmployee e2 on a.SoftDesign=e2.Number " +
				" left outer join tEmployee e3 on a.SaleDirt=e3.Number " +
				" left outer join tEmployee e4 on a.Authorizer=e4.Number " +
				" left outer join tXTDM i3 on a.IsAuthorized=i3.CBM and i3.ID='YESNO' " +
				" left outer join tItemType1 it on a.ItemType1=it.Code " +
				" left outer join tSaleCust cc on a.CustCode=cc.Code " +
				" left outer join tXTDM i on a.IsCal=i.CBM and i.ID='SALESINVISCAL' " +
				" left outer join tWHCheckOut wco on a.WHCheckOutNo=wco.No " +
				" where 1=1 ";
		
		if (StringUtils.isNotBlank(salesInvoice.getStatus())) {
			sql += " and ( 1<>1 ";
			String[] statusArr = salesInvoice.getStatus().split(",");
			for (int i = 0; i < statusArr.length; i++) {
				sql += " or a.Status = ? ";
				list.add(statusArr[i]);
			}
			sql += ")";
		}
		if (StringUtils.isBlank(salesInvoice.getExpired()) || "F".equals(salesInvoice.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(salesInvoice.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+salesInvoice.getNo()+"%");
		}
		if (StringUtils.isNotBlank(salesInvoice.getCustCode())) {
			sql += " and a.CustCode = ? ";
			list.add(salesInvoice.getCustCode());
		}
		if (StringUtils.isNotBlank(salesInvoice.getSaleMan())) {
			sql += " and a.SaleMan= ? ";
			list.add(salesInvoice.getSaleMan());
		}
		if (StringUtils.isNotBlank(salesInvoice.getItemType1())) {
			sql += " and a.ItemType1= ? ";
			list.add(salesInvoice.getItemType1());
		} else {
			sql += " and a.ItemType1 in (select Code from tItemType1 where Expired != 'T') ";
		}
		if (StringUtils.isNotBlank(salesInvoice.getType())) {
			sql += " and a.Type=? ";
			list.add(salesInvoice.getType());
		}
		if (null != salesInvoice.getDateFrom()) {
			sql += " and a.Date >= ? ";
			list.add(salesInvoice.getDateFrom());
		}
		if (null != salesInvoice.getDateTo()) {
			sql += " and a.Date <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(salesInvoice.getDateTo()).getTime()));
		}
		if ("2".equals(uc.getCzylb())) {
			sql += " and exists( " +
				   "  select 1 from tWareHouseOperater in_a where in_a.czybh=? and a.whCode=in_a.whCode " +	
				   ") ";
			list.add(uc.getCzybh());
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
	 * @Description:  新增客户存储过程
	 * @author	created by zb
	 * @date	2018-9-19--上午9:35:50
	 * @param saleCust
	 * @return
	 */
	public Result doCustSave(SaleCust saleCust) {
		Assert.notNull(saleCust);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pInserttSaleCust_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, saleCust.getDesc1());
			call.setString(2, saleCust.getDesc1());
			call.setString(3, "");
			call.setString(4, saleCust.getAddress());
			call.setString(5, saleCust.getContact());
			call.setString(6, saleCust.getPhone1());
			call.setString(7, saleCust.getPhone2());
			call.setString(8, saleCust.getFax1());
			call.setString(9, saleCust.getFax2());
			call.setString(10, saleCust.getMobile1());
			call.setString(11, saleCust.getMobile2());
			call.setString(12, saleCust.getEmail1());
			call.setString(13, saleCust.getEmail2());
			call.setDouble(14, saleCust.getAmount()==null?0:saleCust.getAmount());
			call.setString(15, saleCust.getQq());
			call.setString(16, saleCust.getMsn());
			call.setString(17, saleCust.getRemDate1());
			call.setString(18, saleCust.getRemDate2());
			call.setString(19, saleCust.getRemarks());
			call.setString(20, saleCust.getLastUpdatedBy());
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(21)));
			result.setInfo(call.getString(22));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description:  根据操作员编号获取员工信息
	 * @author	created by zb
	 * @date	2018-9-17--下午3:00:15
	 * @param czybh
	 * @return
	 */
	public Employee getEmpDescrByCZYBH(String czybh) {
		String sql = " select EMNum,NameChi from tCZYBM a,tEmployee b " +
				" where a.EMNum=b.Number and  CZYBH=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh});
		Employee employee = new Employee(); 
		if (list!=null && list.size()>0){
			employee.setCode(list.get(0).get("EMNum").toString());
			employee.setNameChi(list.get(0).get("NameChi").toString());
		} else {
			employee.setCode("");
			employee.setNameChi("无对应员工信息");
		}
		return employee;
	}
	
	/**
	 * @Description:  是否授权查询
	 * @author	created by zb
	 * @date	2018-9-19--上午9:36:30
	 * @param no
	 * @return
	 */
	public boolean isAuthorized(String no) {
		String sql = " select IsAuthorized from tSalesInvoice where No=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if (list != null && list.size()>0) {
			if("1".equals(list.get(0).get("IsAuthorized"))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @Description:  库存结余分页查询
	 * @author	created by zb
	 * @date	2018-9-19--上午9:32:26
	 * @param page
	 * @param itemWHBal
	 * @return
	 */
	public Page<Map<String, Object>> findItemWHBalPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.WHCode, c.Desc1 WHDescr, a.QtyCal " +
				" from tItemWHBal a " +
				" left join tWareHouse c on a.WHCode=c.Code " +
				" where a.ITCode= ? ";
		list.add(itemWHBal.getItCode());
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description:  根据两个编码获取数量
	 * @author	created by zb
	 * @date	2018-9-19--下午2:54:22
	 * @param itCode
	 * @param whCode
	 * @return
	 */
	public Map<String, Object> getQtyNow(String itCode, String whCode) {
		String sql = " select QtyCal from tItemWHBal where ITCode=? and WHCode=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itCode, whCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("QtyCal", 0);
		return map;
	}

	/**
	 * @Description:  销售单存储过程
	 * @author	created by zb
	 * @date	2018-9-25--上午10:03:02
	 * @param salesInvoice
	 * @return
	 */
	public Result doSalesOrder(SalesInvoice salesInvoice) {
		Assert.notNull(salesInvoice);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pXsddgl_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, salesInvoice.getM_umState());
			call.setString(2, salesInvoice.getNo());
			call.setString(3, salesInvoice.getStatus());
			call.setString(4, salesInvoice.getType());
			call.setString(5, salesInvoice.getItemType1());
			call.setTimestamp(6, salesInvoice.getDate()==null?null : new Timestamp(salesInvoice.getDate().getTime()));
			call.setTimestamp(7, salesInvoice.getGetItemDate()==null?null:new Timestamp(salesInvoice.getGetItemDate().getTime()));
			call.setString(8, salesInvoice.getWhcode());
			call.setString(9, salesInvoice.getCustCode());
			call.setString(10, salesInvoice.getCustName());
			call.setDouble(11, salesInvoice.getBefAmount()==null?0:salesInvoice.getBefAmount());
			call.setDouble(12, salesInvoice.getAmount()==null?0:salesInvoice.getAmount());
			call.setString(13, salesInvoice.getCalOnDiscAmt());
			call.setDouble(14, salesInvoice.getDiscPercentage()==null?0:salesInvoice.getDiscPercentage());
			call.setDouble(15, salesInvoice.getDiscAmount()==null?0:salesInvoice.getDiscAmount());
			call.setString(16, salesInvoice.getRemarks());
			call.setString(17, salesInvoice.getExpired());
			call.setString(18, salesInvoice.getLastUpdatedBy());
			call.setDouble(19, salesInvoice.getFirstCash()==null?0:salesInvoice.getFirstCash());
			call.setDouble(20, salesInvoice.getFirstCash()==null?0:salesInvoice.getFirstCash());
			call.setDouble(21, salesInvoice.getSecondCash()==null?0:salesInvoice.getSecondCash());
			call.setDouble(22, salesInvoice.getSecondCash()==null?0:salesInvoice.getSecondCash());
			call.setDouble(23, salesInvoice.getRemainAmount()==null?0:salesInvoice.getRemainAmount());
			call.setString(24, salesInvoice.getSaleMan());
			call.setString(25, salesInvoice.getSoftDesign());
			call.setString(26, salesInvoice.getSaleDirt());
			call.setString(27, salesInvoice.getIsCal());
			call.setString(28, salesInvoice.getCalNo());
			call.setString(29, salesInvoice.getOldNo());
			call.setDouble(30, salesInvoice.getOtherAmount()==null?0:salesInvoice.getOtherAmount());
			call.registerOutParameter(31, Types.INTEGER);
			call.registerOutParameter(32, Types.NVARCHAR);
			call.setString(33, salesInvoice.getDetailJson());
//			System.out.println(salesInvoice.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(31)));
			result.setInfo(call.getString(32));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description:  销售单明细分页查询
	 * @author	created by zb
	 * @date	2018-10-17--上午11:03:12
	 * @param page
	 * @param no
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, String no) {
		List<Object> list = new ArrayList<Object>();
		String sql = " Select a.PK,a.SINo,it2.Descr ItemType2Descr,a.ITCode,d.Descr ITDescr,a.Qty,a.SaleQty,a.BCost,a.UnitPrice, " +
				" d.UOM,u.Descr UnitDescr,a.BefLineAmount, a.Markup,a.LineAmount,a.Remarks,a.LastUpdate,a.LastUpdatedBy, " +
				" a.ActionLog,a.Expired,d.MarketPrice  " +
				" from tSalesInvoiceDetail a " +
				" left outer join tItem d on a.ITCode = d.Code " +
				" left outer join tItemType2 it2 on it2.code=d.ItemType2 " +
				" left outer join tUOM u on d.UOM = u.Code " +
				" where 1=1 and a.SINo = ? ";
		list.add(no);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description:  采购单明细分页查询
	 * @author	created by zb
	 * @date	2018-9-26--下午5:13:31
	 * @param page
	 * @param sino 销售发票号
	 * @return
	 */
	public Page<Map<String, Object>> findPurDetailPageBySql(
			Page<Map<String, Object>> page, String sino) {
		List<Object> list = new ArrayList<Object>();
		String sql = " Select a.PK,a.PUNo,t.Status,s1.Note StatusDescr,a.ITCode,d.Descr ITDescr,d.AllQty,a.QtyCal,a.ArrivQty,d.Color, " +
				" d.UOM,u.Descr UnitDescr,a.UnitPrice,a.Amount,b.Descr SqlCodeDescr, " +
				" a.Remarks,a.LastUpdate,a.LastUpdatedBy " +
				" from tPurchaseDetail a " +
				" left outer join tPurchase t on t.No = a.PUNo " +
				" left outer join tItem d on a.ITCode = d.Code " +
				" left outer join tUOM u on d.UOM = u.Code " +
				" left outer join tBrand b on b.code=d.SqlCode " +
				" left outer join tXTDM s1 on t.Status=s1.CBM and s1.ID='PURCHSTATUS' " +
				" where t.SINo=? ";
		list.add(sino);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description:  批次导入——获取批次编号
	 * @author	created by zb
	 * @date	2018-9-27--下午5:15:18
	 * @param itemType1
	 * @param czybhCode
	 * @return
	 */
	public Map<String, Object> getItemBatchHeader(String itemType1, String czybhCode) {
		String sql = " select top 1 no,remarks from tItemBatchHeader where CrtCzy=? and ItemType1=? order by Date desc ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybhCode.trim(), itemType1.trim()});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Description:  批次材料分页查询
	 * @author	created by zb
	 * @date	2018-9-27--下午6:06:58
	 * @param page
	 * @param ibdno
	 * @return
	 */
	public Page<Map<String, Object>> findItemBatchPageBySql(
			Page<Map<String, Object>> page, String ibdno) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.pk,a.ITCode,i.Descr ITCodeDescr,a.Qty,a.Remarks,a.DispSeq,a.LastUpdate, " +
				" a.LastUpdatedBy,a.Expired,a.ActionLog,i.ItemType2,i.Uom,d.Descr UomDescr,i.Price, " +
				" round(i.Price*a.Qty,0) BefLineAmount,i.AvgCost,i.MarketPrice " +
				" from tItemBatchDetail a   " +
				" left outer join tItem i on i.code=a.ITCode  " +
				" left outer join tUom d on i.Uom=d.Code " +
				" where a.IBDno=? order by a.DispSeq ";
		list.add(ibdno);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description:  根据材料编号获取材料信息
	 * @author	created by zb
	 * @date	2018-9-28--上午9:51:42
	 * @param itCode
	 * @return
	 */
	public Map<String, Object> getItemInfo(String itCode) {
		String sql = " select it2.Descr ItemType2Descr,a.Descr ITDescr,a.UOM,u.Descr UnitDescr,a.MarketPrice " +
				" from tItem a " +
				" left join tItemType2 it2 on it2.Code = a.ItemType2 " +
				" left join tUOM u on u.Code = a.Uom " +
				" where a.Code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itCode.trim()});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Description:  销售明细查询——销售订单管理用
	 * @author	created by zb
	 * @param page 
	 * @date	2018-9-28--下午4:14:53
	 * @param sino
	 * @return
	 */
	public Page<Map<String, Object>> findSalesInvoiceDetailPageBySql(
			Page<Map<String, Object>> page, String sino) {
		List<Object> list = new ArrayList<Object>();
		String sql = " Select 0 as IsCheck, t1.PK,t1.SIno,t1.ITCode,i.Descr ItemDescr,t1.Qty,t1.BCost, " +
				" t1.UnitPrice,t1.BefLineAmount,t1.Markup,t1.LineAmount,t1.Remarks " +
				" from tSalesInvoiceDetail t1  " +
				" left outer join tItem i on t1.ITCode=i.Code " +
				" where 1=1 and t1.SINo = ? ";
		list.add(sino);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description:  根据no获取采购记录数量
	 * @author	created by zb
	 * @date	2018-10-18--下午5:27:38
	 * @param no
	 * @return false:存在
	 */
	public Boolean getPurchaseCount(String no) {
		String sql = " select 1 from tWareHousePosi where Desc1 = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * @Description:  明细查询
	 * @author	created by zb
	 * @date	2018-10-26--上午11:34:52
	 * @param page
	 * @param salesInvoice
	 * @return
	 */
	public Page<Map<String, Object>> findDetailViewPageBySql(
			Page<Map<String, Object>> page, SalesInvoice salesInvoice) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.No,a.Status,s1.Note StatusDescr,a.Date,a.ItemType1,it.Descr ItemType1Descr,a.Type ,s2.Note TypeDescr, " +
				" a.GetItemDate,a.WHCode,w.Desc1 WareHouse,a.CustCode,cc.Desc1 CustDescr,cc.Address,cc.Mobile1, " +
				" case when a.Type='S' then a.BefAmount else a.BefAmount*-1 end BefAmount, " +
				" a.CalOnDiscAmt,i2.NOTE CalOnDiscAmtDescr,a.DiscPercentage, " +
				" case when a.Type='S' then a.DiscAmount else a.DiscAmount*-1 end DiscAmount, " +
				" case when a.Type='S' then a.Amount else a.Amount*-1 end Amount, " +
				" case when a.Type='S' then a.OtherAmount else a.OtherAmount*-1 end OtherAmount, " +
				" case when a.Type='S' then a.FirstAmount else a.FirstAmount*-1 end FirstAmount, " +
				" case when a.Type='S' then a.FirstCash else  a.FirstCash *-1 end FirstCash, " +
				" case when a.Type='S' then a.SecondAmount else a.SecondAmount*-1 end SecondAmount, " +
				" case when a.Type='S' then a.SecondCash else a.SecondCash*-1 end SecondCash, " +
				" case when a.Type='S' then a.RemainAmount else a.RemainAmount*-1 end RemainAmount, " +
				" a.SaleMan,e1.NameChi SaleManDescr,a.SoftDesign,e2.NameChi SoftDesignDescr, " +
				" a.SaleDirt,e3.NameChi SaleDirtDescr,a.IsCal,i.Note IsCalDescr,a.CalNo,a.OldNo, " +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, " +
				" d.ITCode,t1.Descr ITCodeDescr,t2.Descr ItemType2Descr, " +
				" case when a.Type='S' then d.Qty else d.Qty*-1 end Qty, " +
				" d.BCost,d.UnitPrice,d.Markup, " +
				" case when a.Type='S' then d.BefLineAmount else d.BefLineAmount*-1 end BefLineAmount, " +
				" case when a.Type='S' then d.LineAmount else d.LineAmount*-1 end LineAmount, " +
				" d.Remarks,d.SaleQty,u.Descr UnitDescr " +
				" from tSalesInvoice a " +
				" left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='SALESINVSTATUS' " +
				" left outer join tWareHouse w on a.WHCode=w.Code " +
				" left outer join tXTDM  s2 on a.Type=s2.CBM and s2.ID='SALESINVTYPE' " +
				" left outer join tXTDM  i2 on a.CalOnDiscAmt=i2.CBM and i2.ID='YESNO' " +
				" left outer join tEmployee e1 on a.SaleMan=e1.Number " +
				" left outer join tEmployee e2 on a.SoftDesign=e2.Number " +
				" left outer join tEmployee e3 on a.SaleDirt=e3.Number " +
				" left outer join tItemType1 it on a.ItemType1=it.Code " +
				" left outer join tSaleCust cc on a.CustCode=cc.Code " +
				" left outer join tXTDM i on a.IsCal=i.CBM and i.ID='SALESINVISCAL' " +
				" left outer join tSalesInvoiceDetail d on a.No= d.SINo " +
				" left outer join tItem t1 on t1.Code = d.ITCode " +
				" left outer join tUOM u on u.Code = t1.UOM " +
				" left outer join tItemType2 t2 on t1.ItemType2=t2.Code " +
				" where 1=1 ";
				
		
		if (StringUtils.isNotBlank(salesInvoice.getStatus())) {
			sql += " and ( 1<>1 ";
			String[] statusArr = salesInvoice.getStatus().split(",");
			for (int i = 0; i < statusArr.length; i++) {
				sql += " or a.Status = ? ";
				list.add(statusArr[i]);
			}
			sql += ")";
		}
		if (StringUtils.isBlank(salesInvoice.getExpired()) || "F".equals(salesInvoice.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(salesInvoice.getNo())) {
			sql += " and a.No = ? ";
			list.add(salesInvoice.getNo());
		}
		if (StringUtils.isNotBlank(salesInvoice.getCustCode())) {
			sql += " and a.CustCode = ? ";
			list.add(salesInvoice.getCustCode());
		}
		if (StringUtils.isNotBlank(salesInvoice.getSaleMan())) {
			sql += " and a.SaleMan= ? ";
			list.add(salesInvoice.getSaleMan());
		}
		if (StringUtils.isNotBlank(salesInvoice.getItemType1())) {
			sql += " and a.ItemType1= ? ";
			list.add(salesInvoice.getItemType1());
		} else {
			sql += " and a.ItemType1 in (select Code from tItemType1 where Expired != 'T') ";
		}
		if (StringUtils.isNotBlank(salesInvoice.getType())) {
			sql += " and a.Type=? ";
			list.add(salesInvoice.getType());
		}
		if (null != salesInvoice.getDateFrom()) {
			sql += " and a.Date >= ? ";
			list.add(salesInvoice.getDateFrom());
		}
		if (null != salesInvoice.getDateTo()) {
			sql += " and a.Date <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(salesInvoice.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(salesInvoice.getItCode())) {
			sql += " and d.ITCode =? ";
			list.add(salesInvoice.getItCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	
}
