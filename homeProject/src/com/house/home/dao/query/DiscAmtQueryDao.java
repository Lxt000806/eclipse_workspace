package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class DiscAmtQueryDao extends BaseDao {

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer, UserContext userContext) {

        ArrayList<Object> parameters = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select ct.Desc1 custTypeDescr,a.Code, a.Address, a.Status, f.NOTE StatusDescr, "
                + "dbo.fGetDept1Descr(a.Code, '00') DesignerDept, "
                + "dbo.fGetDept1Descr(a.Code, '01') SalesmanDept, "
                + "a.DesignerMaxDiscAmount + a.DirectorMaxDiscAmount + isnull(b.ExtraGiftAmount, 0) + "
                + "    isnull(c.DiscAmountExpense, 0) + (case h.QZ when '1' then isnull(cg.lpExpense, 0) else 0 end) DiscBalance, "
                + "a.DesignerMaxDiscAmount + a.DirectorMaxDiscAmount + isnull(b.ExtraGiftAmount, 0) + "
                + "    isnull(c.DiscAmountExpense, 0) + "
                + "    (isnull(a.DesignRiskFund, 0) + isnull(d.FrontendRiskFundExpense, 0)) + "
                + "    (case h.QZ when '1' then isnull(cg.lpExpense, 0) else 0 end) TotalDiscBalance, "
                + "isnull(a.DesignRiskFund, 0) + isnull(d.FrontendRiskFundExpense, 0) FrontendRiskBalance, "
                + "isnull(a.PrjManRiskFund, 0) - isnull(e.PrjRiskFundExpense, 0) PrjRiskBalance, "
                + "a.DesignerMaxDiscAmount + a.DirectorMaxDiscAmount MaxDiscAmount, "
                + "isnull(b.ExtraGiftAmount, 0) ExtraGiftAmount, "
                + "a.DesignerMaxDiscAmount + a.DirectorMaxDiscAmount + isnull(b.ExtraGiftAmount, 0) DiscAmount, "
                + "isnull(a.DesignRiskFund, 0) DesignRiskFund, "
                + "isnull(a.PrjManRiskFund, 0) PrjManRiskFund, "
                + "abs(isnull(c.DiscAmountExpense, 0) + (case h.QZ when '1' then isnull(cg.lpExpense, 0) else 0 end)) DiscAmountExpense, "
                + "abs(isnull(d.FrontendRiskFundExpense, 0)) FrontendRiskFundExpense, "
                + "abs(isnull(e.PrjRiskFundExpense, 0)) PrjRiskFundExpense, "
                + "abs(isnull(cg.lpExpense, 0)) lpExpense, "
                + "a.SignDate, a.CustCheckDate,a.DocumentNo,case when ts.SignDate is null then '否' else '是' end IsCq "
        		+ "from tCustomer a "
                + "left join ( "
                + "    select CustCode, sum(Amount) ExtraGiftAmount "
                + "    from tDiscAmtTran "
                + "    where Type = '1' and IsExtra = '1' and IsRiskFund = '0' "
                + "    group by CustCode "
                + ") b on a.Code = b.CustCode "
                + "left join ( "
                + "    select CustCode, sum(Amount) DiscAmountExpense "
                + "    from tDiscAmtTran "
                + "    where Type = '2' and IsRiskFund = '0' "
                + "    group by CustCode "
                + ") c on a.Code = c.CustCode "
                + "left join ( "
                + "    select CustCode, sum(Amount) FrontendRiskFundExpense "
                + "    from tDiscAmtTran "
                + "    where Type = '2' and IsRiskFund = '1' "
                + "    group by CustCode "
                + ") d on a.Code = d.CustCode "
                + "left join ( "
                + "    select in_a.Code, sum(in_c.RiskFund) PrjRiskFundExpense "
                + "    from tCustomer in_a "
                + "    left join tFixDuty in_b on in_a.Code = in_b.CustCode "
                + "    left join tFixDutyMan in_c on in_b.No = in_c.No "
                + "    where in_b.Status in ('5', '6', '7') "
                + "        and in_a.DesignMan <> in_c.EmpCode "
                + "    group by in_a.Code "
                + ") e on a.Code = e.Code "
                + "left join (" 
                + "    select ts.CustCode,min(ts.SignDate) SignDate " 
                + "    from tAgainSign ts " 
                + "    group by ts.CustCode" 
                + ") ts on a.Code=ts.CustCode "
                + "left join tXTDM f on a.Status = f.CBM and f.ID = 'CUSTOMERSTATUS' " 
                + "left join tCustType ct on ct.code = a.CustType "
                + "left join (" 
                + "    select b.CustCode, "
                + "        isnull(sum(case when b.outType='2' then a.qty else -1*a.qty end * a.Cost), 0) lpExpense "
                + "    from tGiftAppDetail a "
                + "    left join tGiftApp b on a.No=b.no "
			    + "    where b.status in ('send','return') and b.Type='2' "
				+ "    group by b.CustCode "
				+ ") cg on a.Code = cg.CustCode "
				+ "left join tXTCS h on h.ID = 'GiftInDisc' "
                + "where a.Address like ? "
                + "and " + SqlUtil.getCustRight(userContext, "a", 0);

        parameters.add("%" + customer.getAddress() + "%");
        
        if (StringUtils.isNotBlank(customer.getStatus())) {
            sql += " and a.Status in ('" + customer.getStatus().replace(",", "','") + "') ";
        }
        
        if (StringUtils.isNotBlank(customer.getCustType())) {
            sql += " and a.CustType in ('" + customer.getCustType().replace(",", "','") + "') ";
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.Code";
        }
        
        return findPageBySql(page, sql, parameters.toArray());
    }
    
    public Page<Map<String, Object>> findExtraGiftAmountPageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        
        ArrayList<Object> parameters = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select a.Type, b.NOTE TypeDescr, a.Amount, a.Remarks, "
                + "a.IsRiskFund, c.NOTE IsRiskFundDescr, a.IsExtra, d.NOTE IsExtraDescr "
                + "from tDiscAmtTran a "
                + "left join tXTDM b on a.Type = b.CBM and b.ID = 'DISCAMTTRANTYPE' "
                + "left join tXTDM c on a.IsRiskFund = c.CBM and c.ID = 'YESNO' "
                + "left join tXTDM d on a.IsExtra = d.CBM and d.ID = 'YESNO' "
                + "where a.Type = '1' and a.IsExtra = '1' and a.IsRiskFund = '0' " 
                + "and isnull(a.Amount, 0) <> 0 "
                + "and a.CustCode = ? ";
        
        parameters.add(customer.getCode());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a";
        }
        
        return findPageBySql(page, sql, parameters.toArray());
    }

    public Page<Map<String, Object>> findDiscAmountExpensePageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        
        ArrayList<Object> parameters = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select a.Type, b.NOTE TypeDescr, a.Amount, a.Remarks, "
                + "a.IsRiskFund, c.NOTE IsRiskFundDescr, a.IsExtra, d.NOTE IsExtraDescr "
                + "from tDiscAmtTran a "
                + "left join tXTDM b on a.Type = b.CBM and b.ID = 'DISCAMTTRANTYPE' "
                + "left join tXTDM c on a.IsRiskFund = c.CBM and c.ID = 'YESNO' "
                + "left join tXTDM d on a.IsExtra = d.CBM and d.ID = 'YESNO' "
                + "where a.Type = '2' and a.IsRiskFund = '0' " 
                + "and isnull(a.Amount, 0) <> 0 "
                + "and a.CustCode = ? ";
        
        parameters.add(customer.getCode());
        if("1".equals(customer.getGiftInDisc())){
        	 sql += " union all "
                	 + " select '2', '优惠使用',isnull(sum(case when b.outType='2' then a.qty else -1*a.qty end * a.Cost),0), '礼品', "
                	 + " '0', '否', '0', '否' "
                	 + " from tGiftAppDetail a " 
                	 + " left join tGiftApp b on a.No=b.no " 
                	 + " where b.status in ('send','return') and b.Type='2' " 
                	 + " and b.CustCode=? ";
              parameters.add(customer.getCode());
        }
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a";
        }
        
        return findPageBySql(page, sql, parameters.toArray());
    }

    public Page<Map<String, Object>> findFrontendRiskFundExpensePageBySql(
            Page<Map<String, Object>> page, Customer customer) {
        
        ArrayList<Object> parameters = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select a.Type, b.NOTE TypeDescr, a.Amount, a.Remarks, "
                + "a.IsRiskFund, c.NOTE IsRiskFundDescr, a.IsExtra, d.NOTE IsExtraDescr,e.No FixNo "
                + "from tDiscAmtTran a "
                + "left join tXTDM b on a.Type = b.CBM and b.ID = 'DISCAMTTRANTYPE' "
                + "left join tXTDM c on a.IsRiskFund = c.CBM and c.ID = 'YESNO' "
                + "left join tXTDM d on a.IsExtra = d.CBM and d.ID = 'YESNO' "
                + "left join tFixDutyMan e on a.FixDutyManPK=e.PK "
                + "where a.Type = '2' and a.IsRiskFund = '1' " 
                + "and isnull(a.Amount, 0) <> 0 "
                + "and a.CustCode = ? ";
        
        parameters.add(customer.getCode());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a";
        }
        
        return findPageBySql(page, sql, parameters.toArray());
    }

    public Page<Map<String, Object>> findPrjRiskFundExpensePageBySql(
            Page<Map<String, Object>> page, Customer customer) {
        
        ArrayList<Object> parameters = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select c.EmpCode, e.NameChi EmpName, "
                + "c.RiskFund, c.FaultType, d.NOTE FaultTypeDescr "
                + "from tCustomer a "
                + "left join tFixDuty b on a.Code = b.CustCode "
                + "left join tFixDutyMan c on b.No = c.No "
                + "left join tXTDM d on c.FaultType = d.CBM and d.ID = 'FAULTTYPE' "
                + "left join tEmployee e on c.EmpCode = e.Number "
                + "where b.Status in ('5', '6', '7') "
                + "and a.DesignMan <> c.EmpCode "
                + "and isnull(c.RiskFund, 0) <> 0 "
                + "and a.Code = ? ";
        
        parameters.add(customer.getCode());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a";
        }
        
        return findPageBySql(page, sql, parameters.toArray());
    }

}
