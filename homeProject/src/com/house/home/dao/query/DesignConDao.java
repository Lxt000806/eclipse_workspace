package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class DesignConDao extends BaseDao {

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer, UserContext userContext) {
        
        ArrayList<Object> parameters = new ArrayList<Object>();
        
        String contactRoles = "'00', '01', '24'";
        
        if (StringUtils.isNotBlank(customer.getContactRole())) {
            contactRoles = "'" + customer.getContactRole() + "'";
        }
        
        String sql = "select * from ("
                + "select a.Code, a.Address, a.Descr CustName, "
                + "       a.CustType, b.Desc1 CustTypeDescr, "
                + "       a.Status, c.NOTE StatusDescr, "
                + "       dbo.fGetEmpNameChi(a.Code, '01') BusinessManName, "
                + "       dbo.fGetEmpNameChi(a.Code, '00') DesignManName, "
                + "       dbo.fGetEmpNameChi(a.Code, '24') AgainManName, "
                + "       j.Pk SignInPk, j.CrtDate MeasureDate, "
                + "       a.VisitDate, a.SetDate, a.PriceDate, "
                + "       h.DesignDrawingDate, i.RenderingDate, "
                + "       e.ConMan, f.ZWXM ConManDescr, e.ConWay, g.NOTE ConWayDescr, "
                + "       e.ConDate, e.Remarks ConRemarks, e.NextConDate, "
                + "       a.Expired "
                + "from tCustomer a "
                + "left join tCusttype b on a.CustType = b.Code "
                + "left join tXTDM c on c.ID = 'CUSTOMERSTATUS' and a.Status = c.CBM "
                + "outer apply ( "
                + "    select max(in_a.PK) MaxCustConPk "
                + "    from tCustCon in_a "
                + "    where in_a.CustCode = a.Code "
                + "        and exists ( "
                + "            select 1 "
                + "            from tCustStakeholder in_in_a "
                + "            left join tCZYBM in_in_b on in_in_a.EmpCode = in_in_b.EMNum "
                + "            where in_in_a.CustCode = in_a.CustCode "
                + "                and in_in_b.CZYBH = in_a.ConMan "
                + "                and in_in_a.Role in (" + contactRoles + ") "
                + "        ) "
                + ") d "
                + "left join tCustCon e on e.PK = d.MaxCustConPk "
                + "left join tCZYBM f on e.ConMan = f.CZYBH "
                + "left join tXTDM g on g.ID = 'CONWAY' and e.ConWay = g.CBM "
                + "outer apply ( "
                + "    select in_a.UploadDate DesignDrawingDate "
                + "    from tCustDoc in_a "
                + "    where in_a.PK = ( "
                + "        select min(in_in_a.PK) "
                + "        from tCustDoc in_in_a "
                + "        where in_in_a.CustCode = a.Code "
                + "            and in_in_a.DocType2 in ('3', '10') "
                + "    ) "
                + ") h "
                + "outer apply ( "
                + "    select in_a.UploadDate RenderingDate "
                + "    from tCustDoc in_a "
                + "    where in_a.PK = ( "
                + "        select min(in_in_a.PK) "
                + "        from tCustDoc in_in_a "
                + "        where in_in_a.CustCode = a.Code "
                + "            and in_in_a.DocType2 in ('2') "
                + "    ) "
                + ") i "
                + "outer apply ( "
                + "    select in_a.PK, in_a.CrtDate "
                + "    from tSignIn in_a "
                + "    where in_a.PK = ( "
                + "        select min(in_in_a.PK) "
                + "        from tSignIn in_in_a "
                + "        where in_in_a.CustCode = a.Code "
                + "            and in_in_a.SignInType2 = '51' "
                + "    ) "
                + ") j "
                + "where " + SqlUtil.getCustRight(userContext, "a", "00,01,24");
        
        sql += ") a where a.Status in ('1', '2', '3') ";
        
        if (StringUtils.isNotBlank(customer.getCode())) {
            sql += "and a.Code = ? ";
            parameters.add(customer.getCode());
        }
        
        if (StringUtils.isNotBlank(customer.getAddress())) {
            sql += "and a.Address like ? ";
            
            String keyword = SqlUtil.insertPercentSignBeforeFirstDigit(customer.getAddress());
            parameters.add("%" + keyword + "%");
        }
        
        if (StringUtils.isNotBlank(customer.getStatus())) {
            sql += "and a.Status in ('" + customer.getStatus().replace(",", "', '") + "') ";
        }
        
        if (customer.getMeasureDateFrom() != null) {
            sql += "and a.MeasureDate >= ? ";
            parameters.add(customer.getMeasureDateFrom());
        }
        
        if (customer.getMeasureDateTo() != null) {
            sql += "and a.MeasureDate < dateadd(day, 1, ?) ";
            parameters.add(customer.getMeasureDateTo());
        }
        
        if (customer.getPriceDateFrom() != null) {
            sql += "and a.PriceDate >= ? ";
            parameters.add(customer.getPriceDateFrom());
        }
        
        if (customer.getPriceDateTo() != null) {
            sql += "and a.PriceDate < dateadd(day, 1, ?) ";
            parameters.add(customer.getPriceDateTo());
        }
        
        if (customer.getDesignDrawingDateFrom() != null) {
            sql += "and a.DesignDrawingDate >= ? ";
            parameters.add(customer.getDesignDrawingDateFrom());
        }
        
        if (customer.getDesignDrawingDateTo() != null) {
            sql += "and a.DesignDrawingDate < dateadd(day, 1, ?) ";
            parameters.add(customer.getDesignDrawingDateTo());
        }
        
        if (StringUtils.isNotBlank(customer.getDepartment1())) {
            String departments = "('" + customer.getDepartment1().replace(",", "', '") + "') ";
            sql += "and exists (select * from tCustStakeholder _a "
                    + "    left join tEmployee _b on _a.EmpCode = _b.Number "
                    + "    where _a.CustCode = a.Code "
                    + "        and _a.Role in ('00', '01', '24') "
                    + "        and _b.Department1 in " + departments
                    + ") ";
        }
        
        if (StringUtils.isNotBlank(customer.getDepartment2())) {
            String departments = "('" + customer.getDepartment2().replace(",", "', '") + "') ";
            sql += "and exists (select * from tCustStakeholder _a "
                    + "    left join tEmployee _b on _a.EmpCode = _b.Number "
                    + "    where _a.CustCode = a.Code "
                    + "        and _a.Role in ('00', '01', '24') "
                    + "        and _b.Department2 in " + departments
                    + ") ";
        }
        
        if (StringUtils.isNotBlank(customer.getDesignStatus())) {
            String status = customer.getDesignStatus();
            
            if (status.equals("unmeasured"))
                sql += "and a.MeasureDate is null ";
            else if (status.equals("undrawn"))
                sql += "and a.DesignDrawingDate is null ";
            else if (status.equals("unpriced"))
                sql += "and a.PriceDate is null ";
        }
        
        if (StringUtils.isNotBlank(customer.getCzybh())) {
            sql += "and exists(select * from tCustStakeholder _a "
                    + "    left join tCZYBM _b on _a.EmpCode = _b.EMNum "
                    + "    where _a.CustCode = a.Code "
                    + "        and _a.Role in ('00', '01', '24') "
                    + "        and _b.CZYBH = ?) ";
            
            parameters.add(customer.getCzybh());
        }
        
        if (StringUtils.isBlank(customer.getExpired())
                || "F".equals(customer.getExpired())) {
            sql += "and a.Expired = 'F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += " order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += " order by a.ConDate desc";
        }

        return findPageBySql(page, sql, parameters.toArray());
    }
    
    public Page<Map<String, Object>> findConPageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        
        List<Object> params = new ArrayList<Object>();
        
        String sql = "select * from (select a.PK,a.ConDate,a.Remarks,c.NameChi ConManDescr,"
                + "case when isnull(a.CustCode,'')='' and isnull(b.CustCode,'')='' then  isnull(b.ResrCustCode,a.ResrCustCode) "
                + "else case when isnull(a.CustCode,'')<>'' then a.CustCode else b.CustCode end end CustCode, "
                + "a.NextConDate,d.NOTE TypeDescr,e.NOTE ConWayDescr,isnull(a.ConDuration,0) ConDuration, "
                + "    case when f.Status = '1' then '/' + substring(f.Name, 0, 6) + '/' + f.Name else '' end callRecordPath, "
                + "    case when exists (select * from tCustStakeholder in_a "
                + "            left join tCZYBM in_b on in_a.EmpCode = in_b.EMNum "
                + "            where in_a.CustCode = a.CustCode "
                + "                    and in_b.CZYBH = a.ConMan "
                + "                    and in_a.Role = '00') then '设计师' "
                + "        when exists (select * from tCustStakeholder in_a "
                + "            left join tCZYBM in_b on in_a.EmpCode = in_b.EMNum "
                + "            where in_a.CustCode = a.CustCode "
                + "                    and in_b.CZYBH = a.ConMan "
                + "                    and in_a.Role = '01') then '业务员' "
                + "        when exists (select * from tCustStakeholder in_a "
                + "            left join tCZYBM in_b on in_a.EmpCode = in_b.EMNum "
                + "            where in_a.CustCode = a.CustCode "
                + "                    and in_b.CZYBH = a.ConMan "
                + "                    and in_a.Role = '24') then '翻单员' "
                + "    end ConManRoleDescr "
                + "from tCustCon a  "
                + "left join ("
                + "  select max(PK) PK,ResrCustCode from tResrCustMapper group by ResrCustCode "
                + ") g on a.ResrCustCode=g.ResrCustCode "
                + "left join tResrCustMapper b on b.PK=g.PK "
                + "left join tEmployee c on a.ConMan=c.Number "
                + "left join tXTDM d on a.Type=d.CBM and d.ID='CUSTCONTYPE' "
                + "left join tXTDM e on a.ConWay=e.CBM and e.ID='CONWAY' "
                + "left join tCallRecord f on f.PK = a.CallRecordPK and f.status = '1' "
                + "where a.CustCode = ? ";

        params.add(customer.getCode());
        
        String role = "'00', '01', '24'";
        
        if (StringUtils.isNotBlank(customer.getRole())) {
            role = "'" + customer.getRole() + "'";
        }
        
        sql += "   and exists( "
                + "    select * "
                + "    from tCustStakeholder in_a "
                + "    left join tCZYBM in_b on in_a.EmpCode = in_b.EMNum "
                + "    where in_a.CustCode = a.CustCode "
                + "        and in_b.CZYBH = a.ConMan "
                + "        and in_a.Role in (" + role + ") "
                + ") ";

        if (StringUtils.isNotBlank(customer.getCzybh())) {
            sql += "and a.ConMan = ? ";
            params.add(customer.getCzybh());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.ConDate desc ";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }

    public Map<String, Object> getDesignConInfoByCustCode(String custCode, UserContext userContext) {
        
        if (StringUtils.isBlank(custCode)) {
            return new HashMap<String, Object>();
        }
        
        Customer customer = new Customer();
        customer.setCode(custCode);
        
        Page<Map<String, Object>> page = findPageBySql(new Page<Map<String,Object>>(), customer, userContext);
        List<Map<String, Object>> list = page.getResult();
        
        return list.isEmpty() ? new HashMap<String, Object>() : list.get(0);
    }

}
