package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PayCheckOut;

@SuppressWarnings("serial")
@Repository
public class PayCheckOutDao extends BaseDao {

	/**
	 * 显示主界面数据和查询SQL语句
	 * 
	 * @param page
	 * @param payCheckOut
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PayCheckOut payCheckOut) {
		List<Object> list = new ArrayList<Object>();

		// 对于查询收入记账的基本sql语句
		String sql = "select * from(select a.No,a.Status,d.NOTE StatusDescr,a.DocumentNo,a.CheckDate,a.Remarks,"
				+ " a.AppCZY,e.NameChi AppCZYDescr,a.Date,a.ConfirmCZY,f.NameChi ConfirmCZYDescr,a.ConfirmDate,a.ReturnPayNo,"
				+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+ " from tPayCheckOut a "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='WHChkOutStatus'"
				+ " left outer join tEmployee e on e.number=a.AppCZY"
				+ " left outer join tEmployee f on f.number=a.ConfirmCZY"
				+ " where 1 = 1";
		// 判断记账单号是否不为空
		if (StringUtils.isNotBlank(payCheckOut.getNo())) {
			sql += " and a.No like ?";
			list.add("%"+payCheckOut.getNo()+"%");
		}
		// 判断状态是否不为空
		if (StringUtils.isNotBlank(payCheckOut.getStatus())) {
			sql += " and a.Status in " + "('"
					+ payCheckOut.getStatus().replaceAll(",", "','") + "')";
		}
		// 判断凭证号是否不为空
		if (StringUtils.isNotBlank(payCheckOut.getDocumentNo())) {
			sql += " and a.DocumentNo like ?";
			list.add("%" + payCheckOut.getDocumentNo() + "%");
		}
		// 判断起始的申请日期是否不为空
		if (payCheckOut.getDateFrom() != null) {
			sql += " and a.Date >= ?";
			list.add(payCheckOut.getDateFrom());
		}
		// 判断终止的申请日期是否不为空
		if (payCheckOut.getDateTo() != null) {
			sql += " and a.Date <DATEADD(d,1,?)";
			list.add(payCheckOut.getDateTo());
		}
		// 判断起始的记账日期是否不为空
		if (payCheckOut.getCheckDateFrom() != null) {
			sql += " and a.CheckDate >= ?";
			list.add(payCheckOut.getCheckDateFrom());
		}
		// 判断终止的记账日期是否不为空
		if (payCheckOut.getCheckDateTo() != null) {
			sql += " and a.CheckDate <DATEADD(d,1,?)";
			list.add(payCheckOut.getCheckDateTo());
		}
		// 判断客户编号是否不为空
		if (StringUtils.isNotBlank(payCheckOut.getCustCode())) {
			sql += " and exists(select 1 from tCustPay m inner join tCustomer n on m.CustCode=n.Code "
					+ "where m.CustCode= ? and m.PayCHeckOutNo=a.No)";
			list.add(payCheckOut.getCustCode());
		}
		// 查看页面排序是否不为空
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 多表查询客户付款单信息
	 * 
	 * @param page
	 * @param custPay
	 * @param payCheckOut
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findCustPayPageBySql(
			Page<Map<String, Object>> page, CustPay custPay,
			PayCheckOut payCheckOut, Customer customer) {

		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.IsCheckOut,a.payNo,a.PK,a.Date,a.RcvAct,a.ProcedureFee,a.Amount,a.Remarks,a.LastUpdate, "
				+ " c.NOTE CustStatus, e.Descr rcvactdescr,pos.descr posdescr,a.Amount-a.ProcedureFee ActualAmount, "
				+ " d.DocumentNo,d.Code,d.Descr as custDescr,d.Address Address,z.Note CustTypedescr,g.descr regiondescr, "
				+ " a.type custPayType,h.NOTE custPayTypeDescr,g.CmpCode,i.Desc2 CmpDesc2, "
				+ " case when j.IsPartDecorate = '3' then '2' "
				+ "      when d.DocumentNo is null or d.DocumentNo = '' then '1' "
                + "      else '2' end CheckOutType "
				+ " from tCustPay a "
				+ " left outer join tCustomer d on d.Code=a.CustCode "
				+ " left outer join tXTDM c on c.CBM=d.Status and c.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM z on z.CBM=d.CustType and z.id='CustType' "
				+ " left outer join tRcvAct e on e.Code = a.RcvAct "
				+ " left join tBankPos pos on pos.code=a.posCode "
				+ " left join tBuilder f on d.builderCode=f.code "
				+ " left join tRegion g on g.Code=f.RegionCode "
				+ " left join tXTDM h on h.CBM=a.Type and h.id='CPTRANTYPE' "
				+ " left join tCompany i on i.Code=g.CmpCode "
				+ " left join tCustType j on d.CustType = j.Code "
				+ " where 1=1 and a.IsCheckOut='0' ";

		// 筛选掉已被添加的新增付款单记账pk
		sql += " and a.PK not in " + "('"
				+ payCheckOut.getAllDetailInfo().replaceAll(",", "','") + "')";
		//公司编号
		if (StringUtils.isNotBlank(payCheckOut.getCmpCode())) {
			sql += " and g.CmpCode=? ";
			list.add(payCheckOut.getCmpCode());
		}
		// 判断客户编号是否不为空
		if (StringUtils.isNotBlank(custPay.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custPay.getCustCode());
		}
		// 判断楼盘号是否不为空
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		// 判断起始入款日期是否不是空指针
		if (custPay.getDateFrom() != null) {
			sql += " and a.Date >= ?";
			list.add(custPay.getDateFrom());
		}
		// 判断终止付款日期是否不是空指针
		if (custPay.getDateTo() != null) {
			sql += " and a.Date <DATEADD(d,1,?)";
			list.add(custPay.getDateTo());
		}
		if (StringUtils.isNotBlank(payCheckOut.getCustStatus())) {
			sql += " and d.Status in " + "('"
					+ payCheckOut.getCustStatus().replaceAll(",", "','") + "')";
		}
		// 判断付款单号是否不为空
		if (StringUtils.isNotBlank(custPay.getPayCheckOutNo())) {
			sql += " and a.PK=? ";
			list.add(custPay.getPayCheckOutNo());
		}
		 // 判断收款账户是否不为空
		if (StringUtils.isNotBlank(custPay.getRcvAct())) {
			sql += " and a.RcvAct in ('"+custPay.getRcvAct().replace(",","','")+"')";
		}
		
		if (custPay.getAddDateFrom() != null) {
            sql += " and a.AddDate >= ? ";
            list.add(custPay.getAddDateFrom());
        }
		
		if (custPay.getAddDateTo() != null) {
		    sql += " and a.AddDate < dateadd(day, 1, ?) ";
            list.add(custPay.getAddDateTo());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 新增、编辑
	 * @param payCheckOut
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result savePayCheckOut(PayCheckOut payCheckOut) {
		Assert.notNull(payCheckOut);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSrjz_Add_forXml(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, payCheckOut.getM_umState());
			call.setString(2, payCheckOut.getNo());
			call.setString(3, payCheckOut.getRemarks());
			call.setTimestamp(4, payCheckOut.getCheckDate() == null ? null
					: new Timestamp(payCheckOut.getDate().getTime()));
			call.setString(5, payCheckOut.getAppCZY());
			call.setString(6, payCheckOut.getLastUpdateBy());
			call.setString(7, payCheckOut.getDocumentNo());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.setString(10, payCheckOut.getPayAppDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 审核、审核取消，反审核
	 * @param payCheckOut
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result checkPayCheckOut(PayCheckOut payCheckOut) {
		Assert.notNull(payCheckOut);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSrjz_Sh_forProc(?,?,?,?,?,?,?,?)}");
			call.setString(1, payCheckOut.getNo());
			call.setString(2, payCheckOut.getStatus());
			call.setString(3, payCheckOut.getRemarks());
			call.setString(4, payCheckOut.getLastUpdateBy());
			call.setString(5, payCheckOut.getConfirmCZY());
			call.setString(6, payCheckOut.getDocumentNo());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 显示明细表
	 * 
	 * @param page
	 * @param payCheckOut
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, PayCheckOut payCheckOut) {
		List<Object> list = new ArrayList<Object>();
		// 对于查询收入记账的基本sql语句
		String sql = "select a.IsCheckOut,a.PK,d.Code,d.Descr custdescr,d.Address Address,"
				+ " c.NOTE CustStatus,a.Date,a.Amount,a.Remarks,d.DocumentNo,"
				+ " a.RcvAct,a.ProcedureFee,a.Amount-a.ProcedureFee ActualAmount,"
				+ " d.CustType,e.Note CustTypedescr,a.payNo ,pos.descr posdescr,"
				+ " b.Descr RcvActDescr,g.descr regiondescr,h.NOTE custPayTypeDescr,a.type custPayType,i.NOTE EndCode,a.checkSeq, "
				+ " cpt.ReferNo, a.CheckOutType, j.NOTE CheckOutTypeDescr "
				+ " from tCustPay a "
				+ " left outer join tRcvAct b on a.RcvAct=b.Code "
				+ " left outer join tCustomer d on d.Code=a.CustCode  "
				+ " left outer join tXTDM c on c.CBM=d.Status and c.ID='CUSTOMERSTATUS' "
				+ " left outer join tXTDM e on e.CBM=d.CustType and e.id='CustType' "
				+ " left join tBankPos pos on pos.code=a.posCode "
				+ " left join tBuilder f on d.builderCode=f.code "
				+ " left join tRegion g on g.Code=f.RegionCode "
				+ " left join tXTDM h on h.CBM=a.Type and h.id='CPTRANTYPE' "
				+ " left join tXTDM i on i.CBM=d.EndCode and i.id='CUSTOMERENDCODE' "
				+ " left join tCustPayTran cpt on a.pk=cpt.CustPayPK "
				+ " left join tXTDM j on a.CheckOutType = j.CBM and j.ID = 'CPTRANTYPE' "
				+ " where a.PayCheckOutNo=? order by a.checkSeq";
		list.add(payCheckOut.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 主表map
	 * 
	 * @param page
	 * @param payCheckOut
	 * @return
	 */
	public Map<String, Object> findMapBySql(PayCheckOut payCheckOut) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.no,a.status,d.NOTE statusDescr,a.documentNo,a.checkDate,a.remarks,"
				+ " a.appCZY,e.nameChi appCZYDescr,a.date,a.confirmCZY,f.nameChi confirmCZYDescr,a.confirmDate,a.returnPayNo,"
				+ " a.lastUpdate,a.lastUpdatedBy,a.expired,a.actionLog "
				+ " from tPayCheckOut a "
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='WHChkOutStatus'"
				+ " left outer join tEmployee e on e.number=a.AppCZY"
				+ " left outer join tEmployee f on f.number=a.ConfirmCZY"
				+ " where a.no=?";
		list.add(payCheckOut.getNo());
		return this.findBySql(sql, list.toArray()).get(0);
	}
}
