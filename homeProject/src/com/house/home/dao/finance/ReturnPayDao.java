package com.house.home.dao.finance;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.finance.ReturnPay;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class ReturnPayDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ReturnPay returnPay) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.No,a.Status,d.NOTE StatusDescr,a.DocumentNo,a.CheckDate,a.Remarks, " 
				+ " a.AppCZY,e.NameChi AppCZYDescr,a.Date,a.ConfirmCZY,f.NameChi ConfirmCZYDescr,a.ConfirmDate, "
				+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,g.Amount, "
				+ " a.Type, h.NOTE TypeDescr "
				+ " from tReturnPay a "
                + " left outer join tXTDM d on a.Status=d.CBM and d.ID='RETURNPAYSTATUS' "
                + " left outer join tEmployee e on e.number=a.AppCZY "
                + " left outer join tEmployee f on f.number=a.ConfirmCZY "
                + " left outer join (select in_a.no,sum(Amount) Amount from tReturnPayDetail in_a group by in_a.no) g on a.No = g.No "
                + " left join tXTDM h on h.ID = 'RETURNPAYTYPE' and a.Type = h.CBM "
				+ " where a.Expired='F' ";
		if (StringUtils.isNotBlank(returnPay.getDocumentNo())) {
			sql += "  and a.DocumentNo like ?";
			list.add("%"+returnPay.getDocumentNo()+"%");
		}
		
		if (StringUtils.isNotBlank(returnPay.getType())) {
            sql += " and a.Type = ? ";
            list.add(returnPay.getType());
        }
		
		if (StringUtils.isNotBlank(returnPay.getCustCode())) {
			sql += " and exists(select 1 from tReturnPayDetail m where m.CustCode=? and m.No=a.No) ";	
			list.add(returnPay.getCustCode());
		}
		
		if (!"".equals(returnPay.getAppDateFrom())&&(returnPay.getAppDateFrom()!=null)){
			sql += " and a.date>= ? ";	
			list.add(returnPay.getAppDateFrom());			
		}
		
		if (!"".equals(returnPay.getAppDateTo())&& (returnPay.getAppDateTo()!=null)){
			sql += " and a.date< dateadd(d,1,?) ";	
			list.add(returnPay.getAppDateTo());			
		}
		
		if (StringUtils.isNotBlank(returnPay.getStatus())) {
			sql += " and a.Status in " + "('"+returnPay.getStatus().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Date desc";
		}
		System.out.println(sql);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	

	public Page<Map<String, Object>> findDetailByNo(
			Page<Map<String, Object>> page, ReturnPay returnPay) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.PK,a.No,a.CustCode,d.Descr,d.Address Address,a.Amount,a.Remarks,d.DocumentNo, "
				+" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,x1.note statusdescr,x2.note  Enddescr, "
				+" x3.note IsPubReturnDescr, e.ReturnAmount,d.CheckStatus CustCheckStatus,a.RefSupplPrepayPK, "
				+" spr.no RefSupplPrepayno,isnull(spr.amount,0) thisAmount,f.Descr splDescr, "
				+" h.Code Region, h.Descr RegionDescr, x4.NOTE CustCheckStatusDescr, "
				+" d.PrjDeptLeader, i.NameChi PrjDeptLeaderName, j.NameChi projectmanname "
				+" from tReturnPayDetail a" 
				+" left join dbo.tCustomer d on a.CustCode=d.Code "
				+" left join tXTDM x1 on  d.Status=x1.cbm  and x1.id='CUSTOMERSTATUS'"
				+" left join tXTDM x2 on  d.EndCode=x2.cbm  and x2.id='CUSTOMERENDCODE'"
				+" left join tCustTax e on d.Code=e.CustCode"
				+" left join tXTDM x3 on e.IsPubReturn=x3.cbm and x3.id='YESNO'"
				+" left join tXTDM x4 on d.CheckStatus=x4.cbm and x4.id='CheckStatus'"
				+" left join tSupplierPrepayDetail spr on a.RefSupplPrepayPK=spr.pk  "
				+" left join tSupplier f on spr.Supplier=f.Code "
				+" left join tBuilder g on d.BuilderCode = g.Code "
				+" left join tRegion h on g.RegionCode = h.Code "
				+" left join tEmployee i on d.PrjDeptLeader = i.Number "
				+ "left join tEmployee j on j.Number = d.ProjectMan "
				+" where 1=1 ";
		if (StringUtils.isNotBlank(returnPay.getNo())) {
			sql += "  and a.no = ? ";
			list.add(returnPay.getNo());
		}else{
			sql += "  and a.no = '-1' ";
		}
		sql += " order by a.LastUpdate desc";
	
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public ReturnPay getByNo(String no){
		String hql="from ReturnPay where no =?";
		List<ReturnPay> list = this.find(hql, new Object[]{no});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}
	
	public Result doSave(ReturnPay returnPay) {
		Assert.notNull(returnPay);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGctkgl_Add_forXml(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, returnPay.getM_umState());
			call.setString(2, returnPay.getNo());
			call.setString(3, returnPay.getRemarks());
			call.setTimestamp(4, returnPay.getCheckDate()==null?null : new Timestamp(returnPay.getCheckDate().getTime()));
			call.setString(5, returnPay.getAppCZY());
			call.setString(6, returnPay.getLastUpdatedBy());
			call.setString(7, returnPay.getDocumentNo());
			call.setString(8, returnPay.getDetailJson());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.setString(11, returnPay.getType());
			call.execute();
			result.setCode(String.valueOf(call.getInt(9)));
			result.setInfo(call.getString(10));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doConfirmPass(ReturnPay returnPay) {
		Assert.notNull(returnPay);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGctkgl_Sh_forXml(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, returnPay.getNo());
			call.setString(2, "2");
			call.setString(3, returnPay.getRemarks());
			call.setString(4, returnPay.getLastUpdatedBy());
			call.setString(5, returnPay.getConfirmCZY());
			call.setString(6, returnPay.getDocumentNo());
			call.setTimestamp(7, returnPay.getCheckDate()==null?null : new Timestamp(returnPay.getCheckDate().getTime()));
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
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
	
	public Result doConfirmCancel(ReturnPay returnPay) {
		Assert.notNull(returnPay);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGctkgl_Sh_forXml(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, returnPay.getNo());
			call.setString(2, "3");
			call.setString(3, returnPay.getRemarks());
			call.setString(4, returnPay.getLastUpdatedBy());
			call.setString(5, returnPay.getConfirmCZY());
			call.setString(6, returnPay.getDocumentNo());
			call.setTimestamp(7, returnPay.getCheckDate()==null?null : new Timestamp(returnPay.getCheckDate().getTime()));
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
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
	 * 查询客户编号是否已存在
	 * @param payNo
	 * @return
	 */
	public List<Map<String, Object>> findReturnCustCode(ReturnPay returnPay) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select distinct CustCode  from  tReturnPayDetail a inner join tReturnPay b on a.no=b.No where b.Status='1' ";
		if (StringUtils.isNotBlank(returnPay.getNo())) {
			sql += " and a.no<>? ";
			list.add(returnPay.getNo());
		}
		if (StringUtils.isNotBlank(returnPay.getHasCustCode())) {
			sql += " and a.custcode in(select * from fStrToTable('"+returnPay.getHasCustCode()+"',',')) ";
		}
		return this.findBySql(sql, list.toArray());
	}



    public Page<Map<String, Object>> settleRefundsGrid(Page<Map<String, Object>> page,
            ReturnPay returnPay) {
        
        List<Object> params = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select a.Code CustCode, a.Descr, a.Address, i.Code Region, a.CheckOutDate, "
                + "    i.Descr RegionDescr, a.DocumentNo, k.IsPubReturn, l.NOTE IsPubReturnDescr, "
                + "    k.ReturnAmount, a.Status, c.NOTE StatusDescr, a.EndCode, j.NOTE EndDescr, "
                + "    a.PrjDeptLeader, f.NameChi PrjDeptLeaderName, a.CheckStatus, "
                + "    g.NOTE CheckStatusDescr, a.CustType, b.Desc1 CustTypeDescr, "
                + "    a.DesignMan, d.NameChi DesignManName, a.ProjectMan, e.NameChi ProjectManName, "
                + "    isnull(a.firstPay, 0) + isnull(a.secondPay, 0) + isnull(a.thirdPay, 0) + isnull(a.fourPay,0) "
                + "        + isnull(case when m.DesignFeeType = '2' then a.StdDesignFee else a.DesignFee end, 0) "
                + "            * (case when exists(select 1 from tPayRuleDetail in_a "
                + "                where in_a.No = m.No and in_a.IsRcvDesignFee = '1' and Expired = 'F') then 1 else 0 end) "
                + "        + isnull(n.ItemChgAmount, 0) + isnull(o.BaseItemChgAmount, 0) + isnull(p.ConFeeChgAmount, 0) "
                + "        - isnull(q.PaidAmount, 0) UnpaidAmount "
                + "from tCustomer a "
                + "left join tCusttype b on a.CustType = b.Code "
                + "left join tXTDM c on c.ID = 'CUSTOMERSTATUS' and c.CBM = a.Status "
                + "left join tEmployee d on d.Number = a.DesignMan "
                + "left join tEmployee e on e.Number = a.ProjectMan "
                + "left join tEmployee f on f.Number = a.PrjDeptLeader "
                + "left join tXTDM g on g.ID = 'CheckStatus' and g.CBM = a.CheckStatus "
                + "left join tBuilder h on h.Code = a.BuilderCode "
                + "left join tRegion i on i.Code = h.RegionCode "
                + "left join tXTDM j on j.ID = 'CUSTOMERENDCODE' and j.CBM = a.EndCode "
                + "left join tCustTax k on k.CustCode = a.Code "
                + "left join tXTDM l on l.ID = 'YESNO' and l.CBM = k.IsPubReturn "
                + "left join tPayRule m on m.CustType = a.CustType and m.PayType = a.PayType "
                + "outer apply ( "
                + "    select sum(in_a.Amount) ItemChgAmount "
                + "    from tItemChg in_a "
                + "    where in_a.Status = '2' and in_a.CustCode = a.Code "
                + ") n "
                + "outer apply ( "
                + "    select sum(in_a.Amount) BaseItemChgAmount "
                + "    from tBaseItemChg in_a "
                + "    where in_a.Status = '2' and in_a.CustCode = a.Code "
                + ") o "
                + "outer apply ( "
                + "    select sum(in_a.ChgAmount) ConFeeChgAmount "
                + "    from tConFeeChg in_a "
                + "    where in_a.Status = 'CONFIRMED' and in_a.CustCode = a.Code "
                + ") p "
                + "outer apply ( "
                + "    select sum(in_a.Amount) PaidAmount "
                + "    from tCustPay in_a "
                + "    where in_a.CustCode = a.Code "
                + ") q "
                + "where a.CheckOutDate is not null and a.CheckStatus = '1' "
                + "    and not exists ( "
                + "        select 1 "
                + "        from tReturnPay in_a "
                + "        inner join tReturnPayDetail in_b on in_b.No = in_a.No "
                + "        where in_a.Type = '1' and in_b.CustCode = a.Code "
                + "    ) ";
        
        if (returnPay.getCheckoutDateFrom() != null) {
            sql += " and a.CheckOutDate >= ? ";
            params.add(returnPay.getCheckoutDateFrom());
        }
        
        if (returnPay.getCheckoutDateTo() != null) {
            sql += " and a.CheckOutDate < dateadd(day, 1, ?) ";
            params.add(returnPay.getCheckoutDateTo());
        }
        
        if (StringUtils.isNotBlank(returnPay.getAddress())) {
            sql += " and a.Address like ? ";
            params.add("%" + returnPay.getAddress() + "%");
        }
        
        if (StringUtils.isNotBlank(returnPay.getExcludedCusts())) {
            sql += " and a.Code not in ('" + returnPay.getExcludedCusts().replace(",", "', '") + "') ";
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.CheckOutDate desc, a.CustCode desc";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }
    
    public List<Map<String, Object>> findDetailsByNo(String no) {
        String sql = "select * from tReturnPayDetail where No = ? ";
        
        return findListBySql(sql, no);
    }

}
