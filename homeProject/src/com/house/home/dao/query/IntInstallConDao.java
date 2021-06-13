package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Arrays;
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
public class IntInstallConDao extends BaseDao {
    
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer, UserContext userContext) {

        ArrayList<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (";
        
        sql += "   select rtrim(j.Code) WorkType12, j.Descr WorkType12Descr, "
                + "    case j.Code when '17' then f.Code "
                + "        when '18' then g.Code end Supplier, "
                + "    case j.Code when '17' then f.Descr "
                + "        when '18' then g.Descr end SupplierDescr, "
                + "    a.Code, a.Address, a.Status, a.ProjectMan, b.NameChi ProjectManName, "
                + "    b.Phone ProjectManPhone, a.DesignMan, c.NameChi DesignerName, "
                + "    c.Department2 DesignerDep2, "
                + "    case j.Code when '17' then d1.Number "
                + "        when '18' then d2.Number end IntDesigner, "
                + "    case j.Code when '17' then d1.NameChi "
                + "        when '18' then d2.NameChi end IntDesignerName, "
                + "    case j.Code when '17' then d1.Phone "
                + "        when '18' then d2.Phone end IntDesignerPhone, "
                + "    case j.Code when '17' then d1.Department2 "
                + "        when '18' then d2.Department2 end IntDesignerDep2, "
                + "    case j.Code when '17' then isnull(m.WardrobeRepCount, 0) "
                + "        when '18' then isnull(n.CupboardRepCount, 0) end RepCount, "
                + "    case j.Code when '17' then round(isnull(i.WardrobeArea, 0), 2) "
                + "        when '18' then round(isnull(h.CupUpHigh, 0) + isnull(h.CupDownHigh, 0), 2) end Effort, "
                + "    case j.Code when '17' then e.IntSendDate "
                + "        when '18' then e.CupSendDate end SendDate, "
                + "    case when isnull(e.IntSendDate, '19700101') > isnull(e.CupSendDate, '19700101') then e.IntSendDate "
                + "        else e.CupSendDate end LatestSendDate, "
                + "    k.ComeDate, k.WorkerCode, l.NameChi WorkerName, "
                + "    case j.Code when '17' then e.IntDeliverDate "
                + "        when '18' then e.CupDeliverDate end DeliverDate, "
                + "    case j.Code when '17' then o.Remarks "
                + "        when '18' then p.Remarks end CanNotInsRemark "
                + "from tCustomer a "
                + "left join tEmployee b on a.ProjectMan = b.Number "
                + "left join tEmployee c on a.DesignMan = c.Number "
                + "outer apply (select in_b.Number, in_b.NameChi, in_b.Department2, "
                + "        in_b.Phone "
                + "    from tCustStakeholder in_a "
                + "    left join tEmployee in_b on in_a.EmpCode = in_b.Number "
                + "    where in_a.PK = (select max(in_in_a.PK) "
                + "        from tCustStakeholder in_in_a "
                + "        where in_in_a.Role = '11' and in_in_a.CustCode = a.Code)) d1 "
                + "outer apply (select in_b.Number, in_b.NameChi, in_b.Department2, "
                + "        in_b.Phone "
                + "    from tCustStakeholder in_a "
                + "    left join tEmployee in_b on in_a.EmpCode = in_b.Number "
                + "    where in_a.PK = (select max(in_in_a.PK) "
                + "        from tCustStakeholder in_in_a "
                + "        where in_in_a.Role = '61' and in_in_a.CustCode = a.Code)) d2 "
                + "left join tCustIntProg e on a.Code = e.CustCode "
                + "left join tSupplier f on e.IntSpl = f.Code "
                + "left join tSupplier g on e.CupSpl = g.Code "
                + "left join tSpecItemReq h on a.Code = h.CustCode "
                + "left join (select in_a.CustCode, sum(in_a.Qty) WardrobeArea "
                + "    from tSpecItemReqDt in_a "
                + "    left join tItem in_b on in_a.ItemCode = in_b.Code "
                + "    left join tItemType2 in_c on in_b.ItemType2 = in_c.Code "
                + "    left join tItemType12 in_d on in_c.ItemType12 = in_d.Code "
                + "    where in_d.Code = '33' "
                + "    group by in_a.CustCode) i on a.Code = i.CustCode "
                
                // 注意：此处会将单条记录复制为两记录，tCustIntProg中一条记录同时包含衣柜（集成）与厨柜的相关信息，
                //     显示的时候要分开显示一个楼盘的衣柜（集成）与厨柜的信息，因此在此处复制公共信息到两条记录（衣柜与厨柜）中，
                //     最后在select中利用case when完成数据筛选
                + "left join tWorkType12 j on j.Code in ('17', '18') "
                + "left join tCustWorker k on a.Code = k.CustCode and j.Code = k.WorkType12 "
                + "left join tWorker l on k.WorkerCode = l.Code "
                + "left join (select in_a.CustCode, count(in_a.No) WardrobeRepCount "
                + "    from tIntReplenish in_a "
                + "    where in_a.IsCupboard = '0' "
                + "    group by in_a.CustCode) m on a.Code = m.CustCode "
                + "left join (select in_a.CustCode, count(in_a.No) CupboardRepCount "
                + "    from tIntReplenish in_a "
                + "    where in_a.IsCupboard = '1' "
                + "    group by in_a.CustCode) n on a.Code = n.CustCode "
                + "outer apply ( "
                + "    select in_a.Remarks, in_a.LastUpdatedBy "
                + "    from tIntProgDetail in_a "
                + "    where in_a.PK = ( "
                + "        select max(in_in_a.PK) "
                + "        from tIntProgDetail in_in_a "
                + "        left join tCZYBM in_in_b on in_in_a.LastUpdatedBy = in_in_b.CZYBH "
                + "        where in_in_a.IsCupboard = '0' and in_in_a.Type = '2' "
                + "            and in_in_b.CZYLB <> '2' "
                + "            and in_in_a.CustCode = a.Code "
                + "    ) "
                + ") o "
                + "outer apply ( "
                + "    select in_a.Remarks, in_a.LastUpdatedBy "
                + "    from tIntProgDetail in_a "
                + "    where in_a.PK = ( "
                + "        select max(in_in_a.PK) "
                + "        from tIntProgDetail in_in_a "
                + "        left join tCZYBM in_in_b on in_in_a.LastUpdatedBy = in_in_b.CZYBH "
                + "        where in_in_a.IsCupboard = '1' and in_in_a.Type = '2' "
                + "            and in_in_b.CZYLB <> '2' "
                + "            and in_in_a.CustCode = a.Code "
                + "    ) "
                + ") p ";
        
        sql += ") a where a.SendDate is not null ";

        if (StringUtils.isNotBlank(customer.getWorkType12())) {
            sql += "and a.WorkType12 in ('" + customer.getWorkType12().replace(",", "', '") + "') ";
        }

        if (StringUtils.isNotBlank(customer.getAddress())) {
            sql += "and a.Address like ? ";
            parameters.add("%" + SqlUtil.insertPercentSignBeforeFirstDigit(customer.getAddress()) + "%");
        }
        
        if (StringUtils.isNotBlank(customer.getSupplier())) {
            sql += "and a.Supplier = ? ";
            parameters.add(customer.getSupplier());
        }
        
        if (StringUtils.isNotBlank(customer.getStatus())) {
            sql += "and a.Status in ('" + customer.getStatus().replace(",", "', '") + "') ";
        }
        
        if (StringUtils.isNotBlank(customer.getDepartment2())) {
            sql += "and (a.DesignerDep2 in ('" + customer.getDepartment2().replace(",", "', '") + "') ";
            sql += "    or a.IntDesignerDep2 in ('" + customer.getDepartment2().replace(",", "', '") + "')) ";
        }
        
        if (customer.getSendDateFrom() != null) {
            sql += "and a.SendDate >= ? ";
            parameters.add(customer.getSendDateFrom());
        }
        
        if (customer.getSendDateTo() != null) {
            sql += "and a.SendDate <= ? ";
            parameters.add(customer.getSendDateTo());
        }
        
        if (customer.getDeliverDateFrom() != null) {
            sql += "and a.DeliverDate >= ? ";
            parameters.add(customer.getDeliverDateFrom());
        }
        
        if (customer.getDeliverDateTo() != null) {
            sql += "and a.DeliverDate <= ? ";
            parameters.add(customer.getDeliverDateTo());
        }
        
        if (StringUtils.isNotBlank(customer.getWorkerCode())) {
            sql += "and a.WorkerCode = ? ";
            parameters.add(customer.getWorkerCode());
        }
        
        if (StringUtils.isNotBlank(customer.getInstallationStatus())) {            
            List<String> status = Arrays.asList(customer.getInstallationStatus().split(","));
                    
            sql += "and ( 1 = 2 ";
            
            if (status.contains("1")) sql += "or (a.ComeDate is null and a.DeliverDate is null) ";
            
            if (status.contains("2")) sql += "or (a.ComeDate is not null and a.DeliverDate is null) ";
            
            if (status.contains("3")) sql += "or (a.RepCount <> 0 and a.DeliverDate is null) ";
            
            if (status.contains("4")) sql += "or a.DeliverDate is not null ";
            
            sql += ") ";
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += "order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += "order by a.LatestSendDate desc, a.Code";
        }

        return findPageBySql(page, sql, parameters.toArray());
    }
}
