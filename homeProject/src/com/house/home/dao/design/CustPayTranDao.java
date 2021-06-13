package com.house.home.dao.design;

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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.CustPayTran;

@SuppressWarnings("serial")
@Repository
public class CustPayTranDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustPayTran custPayTran) {
		
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( " 
				+ " select a.PK,i.Desc2 CmpDescr, a.CustCode, c.Address, x5.NOTE CustStatus, a.Status, x1.NOTE StatusDescr, a.Type, x2.NOTE TypeDescr, a.Date, a.TranAmount,"
				+ " a.PayAmount, d.CardID RcvAct, a.PosCode, a.ProcedureFee, a.AddDate, a.PayNo, a.CardID, a.BankCode, a.BankName, a.TraceNo, a.ReferNo,"
				+ " a.PrintCZY, a.PrintDate, a.PrintTimes, a.CustPayPK, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, "
				+ " b.CompCode, b.CompName,  b.PosID,a.AppCzy, a.ExceptionRemarks,x3.NOTE IsSignDescr,e.zwxm,f.phone,a.CardAttr,a.ReprintRemarks, "
				+ " x4.NOTE CardAttrDescr ,d.Code RcvActCode,a.PayType, k.Code TaxPayeeCode, k.Descr TaxPayeeDescr "
				+ " from tCustPayTran a"
				+ " left join tBankPos b on b.Code=a.PosCode"
				+ " left join tCustomer c on a.CustCode=c.Code"
				+ " left join tRcvAct d on a.RcvAct=d.Code"
				+ " left join tCzybm e on a.AppCzy=e.czybh"
				+ " left join tEmployee f on e.emnum=f.number"
				+ " left join tBuilder g on c.BuilderCode=g.Code"
				+ " left join tRegion h on h.code=g.regioncode"
				+ " left join tCompany i on i.code=h.cmpCode"	//客户分公司
				+ " left join tCustPay j on a.CustPayPk=j.Pk "
				+ " left join tXTDM x1 on x1.ID='CPTRANSTAT' and a.Status=x1.CBM"
				+ " left join tXTDM x2 on x2.ID='CPTRANTYPE' and a.Type=x2.CBM"
				+ " left join tXTDM x3 on x3.ID='YESNO' and a.IsSign=x3.CBM "
				+ " left join tXTDM x4 on x4.ID='CARDATTR' and a.CardAttr=x4.CBM"
				+ " left join tXTDM x5 on x5.ID='CUSTOMERSTATUS' and c.Status=x5.CBM "	//增加客户状态
				+ " left join tTaxPayee k on d.PayeeCode = k.Code"
				+ " where 1=1 ";
		
		if (custPayTran.getPk() != null) {
			sql += " and a.Pk=? ";
			list.add(custPayTran.getPk());
		}
		if (StringUtils.isNotBlank(custPayTran.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custPayTran.getCustCode());
		}
		if (StringUtils.isNotBlank(custPayTran.getAppCzy())) {
			sql += " and a.AppCzy=? ";
			list.add(custPayTran.getAppCzy());
		}
		if (StringUtils.isNotBlank(custPayTran.getType())) {
			sql += " and a.Type=? ";
			list.add(custPayTran.getType());
		}
		if (StringUtils.isNotBlank(custPayTran.getIsSign())) {
			sql += " and a.IsSign=? ";
			list.add(custPayTran.getIsSign());
		}
		if (StringUtils.isNotBlank(custPayTran.getAddress())) {
			sql += " and c.Address like ? ";
			list.add("%"+custPayTran.getAddress()+"%");
		}
		if (custPayTran.getDateFrom()!=null) {
			sql += " and a.Date >= ? ";
			list.add(custPayTran.getDateFrom());
		}
		if(custPayTran.getDateTo()!=null){
			sql += " and a.Date <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(custPayTran.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(custPayTran.getStatus())) {
			sql += " and a.Status in " + "('"+custPayTran.getStatus().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(custPayTran.getCmpCode())) {
			sql += " and h.CmpCode in " + "('"+custPayTran.getCmpCode().replaceAll(",", "','")+"')";
		}
		
		// 增加收款单位过滤条件
		// 张海洋 20200611
		if (StringUtils.isNotBlank(custPayTran.getPayeeCode())) {
            sql += " and d.PayeeCode = ?";
            list.add(custPayTran.getPayeeCode());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doCustPayTran(CustPayTran custPayTran) {
		Assert.notNull(custPayTran);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustPayTran(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, custPayTran.getM_umState());
			call.setInt(2, custPayTran.getPk());
			call.setString(3, custPayTran.getCustCode());
			call.setString(4, custPayTran.getAppCzy());
			call.setString(5, custPayTran.getType());
			call.setDouble(6, custPayTran.getTranAmount());
			call.setDouble(7, custPayTran.getPayAmount());
			call.setString(8, custPayTran.getPosId());
			call.setString(9, custPayTran.getCardId());
			call.setString(10, custPayTran.getBankCode());
			call.setString(11, custPayTran.getBankName());
			call.setString(12, custPayTran.getTraceNo());
			call.setString(13, custPayTran.getReferNo());
			call.setString(14, custPayTran.getIsSign());
			call.setString(15, custPayTran.getLastUpdatedBy());
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
			call.setString(18, custPayTran.getExceptionRemarks());
			call.setString(19, custPayTran.getCardAttr());
			call.setString(20, custPayTran.getPayType());
			call.execute();
			result.setCode(String.valueOf(call.getInt(16)));
			result.setInfo(call.getString(17));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 重打小票
	 * @param page
	 * @param custPayTran
	 * @return
	 */
	public void doRePrint(CustPayTran custPayTran) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tCustPayTran set PrintCZY=?,PrintDate=getdate(),PrintTimes=PrintTimes+1,ReprintRemarks=?  where pk=?";
		list.add(custPayTran.getLastUpdatedBy());
		list.add(custPayTran.getReprintRemarks());//保存重打说明 add by zb on 20190531
		list.add(custPayTran.getPk());
		executeUpdateBySql(sql, list.toArray());
		
	}
	
	@SuppressWarnings("deprecation")
	public Result doUpdateProcedureFee(CustPayTran custPayTran) {
		Assert.notNull(custPayTran);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustPayTran_procedureFee(?,?,?,?,?,?,?)}");
			call.setInt(1, custPayTran.getPk());
			call.setDouble(2, custPayTran.getProcedureFee() == null ? 0 : custPayTran.getProcedureFee()); 
			call.setString(3, custPayTran.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.setString(6, custPayTran.getPosCode());
			call.setString(7, custPayTran.getCardAttr());
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
}

