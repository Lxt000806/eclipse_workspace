package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("serial")
public class SoftSaleAnalyseDao extends BaseDao {

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.SoftDesigner, a.SoftDesignerName 设计师, "
                + "    sum(case a.ItemType12 when '1' then a.Sales else 0.0 end) 墙纸, "
                + "    sum(case a.ItemType12 when '2' then a.Sales else 0.0 end) 窗帘, "
                + "    sum(case a.ItemType12 when '3' then a.Sales else 0.0 end) 灯具, "
                + "    sum(case a.ItemType12 when '4' then a.Sales else 0.0 end) 家具, "
                + "    sum(case a.ItemType12 when '5' then a.Sales else 0.0 end) 软装其它, "
                + "    sum(case a.ItemType12 when '6' then a.Sales else 0.0 end) 床垫, "
                + "    sum(case a.ItemType12 when '7' then a.Sales else 0.0 end) 床品, "
                + "    sum(case a.ItemType12 when '8' then a.Sales else 0.0 end) 饰品, "
                + "    sum(case a.ItemType12 when '9' then a.Sales else 0.0 end) 升降衣架, "
                + "    sum(case a.ItemType12 when '41' then a.Sales else 0.0 end) 皮革材料, "
                + "    sum(case a.ItemType12 when '42' then a.Sales else 0.0 end) 皮革人工 ,"
                + "    sum(a.Sales) 合计 "
                + "from ( "
                + "    select in_g.EmpCode SoftDesigner, in_h.NameChi SoftDesignerName, "
                + "        in_e.Code ItemType12, in_e.Descr ItemType12Descr, "
                + "        sum(case in_b.IsOutSet when '1' then in_b.LineAmount "
                + "                else round(in_b.Qty * in_c.ProjectCost * (in_b.Markup / 100) + in_b.ProcessCost, 0) "
                + "            end) Sales "
                + "    from tItemChg in_a "
                + "    inner join tItemChgDetail in_b on in_b.No = in_a.No "
                + "    inner join tItem in_c on in_c.Code = in_b.ItemCode "
                + "    inner join tItemType2 in_d on in_d.Code = in_c.ItemType2 "
                + "    inner join tItemType12 in_e on in_e.Code = in_d.ItemType12 "
                + "    inner join tCustomer in_f on in_f.Code = in_a.CustCode "
                + "    inner join tCustStakeholder in_g on in_g.CustCode = in_a.CustCode "
                + "    inner join tEmployee in_h on in_h.Number = in_g.EmpCode "
                + "    where in_a.Expired = 'F' "
                + "        and in_a.Status = '2' "
                + "        and in_a.ItemType1 = 'RZ' "
                + "        and in_c.ItemType2 <> '0267' "  // 排除预估
                + "        and in_g.Role = '50' ";
                
        if (StringUtils.isNotBlank(customer.getCustType())) {
            sql += "       and in_f.CustType in ('" + customer.getCustType().replace(",", "', '") + "') ";
        }
        
        if (customer.getDateFrom() != null) {
            sql += "       and in_a.confirmdate >= ? ";
            params.add(customer.getDateFrom());
        }
        
        if (customer.getDateTo() != null) {
            sql += "       and in_a.confirmdate < dateadd(day, 1, ?) ";
            params.add(customer.getDateTo());
        }
                
        sql +=    "    group by in_g.EmpCode, in_h.NameChi, in_e.Code, in_e.Descr "
                + " "
                + "    union all "
                + " "
                + "    select in_f.EmpCode, in_g.NameChi, in_d.Code, in_d.Descr, "
                + "        sum(case in_a.IsOutSet when '1' then in_a.LineAmount "
                + "                else round(in_a.Qty * in_b.ProjectCost * (in_a.Markup / 100) + in_a.ProcessCost, 0) "
                + "            end) Sales "
                + "    from tItemPlan in_a "
                + "    inner join tItem in_b on in_b.Code = in_a.ItemCode "
                + "    inner join tItemType2 in_c on in_c.Code = in_b.ItemType2 "
                + "    inner join tItemType12 in_d on in_d.Code = in_c.ItemType12 "
                + "    inner join tCustomer in_e on in_e.Code = in_a.CustCode "
                + "    inner join tCustStakeholder in_f on in_f.CustCode = in_a.CustCode "
                + "    inner join tEmployee in_g on in_g.Number = in_f.EmpCode "
                + "    where in_a.ItemType1 = 'RZ' "
                + "        and in_e.ContainSoft = 1 "
                + "        and in_b.ItemType2 <> '0267' "  // 排除预估
                + "        and in_f.Role = '50' ";
        
        if (StringUtils.isNotBlank(customer.getCustType())) {
            sql += "       and in_e.CustType in ('" + customer.getCustType().replace(",", "', '") + "') ";
        }
        
        if (customer.getDateFrom() != null) {
            sql += "       and in_e.SignDate >= ? ";
            params.add(customer.getDateFrom());
        }
        
        if (customer.getDateTo() != null) {
            sql += "       and in_e.SignDate < dateadd(day, 1, ?) ";
            params.add(customer.getDateTo());
        }
        
        sql +=    "    group by in_f.EmpCode, in_g.NameChi, in_d.Code, in_d.Descr "
                + " "
                + "    union all "
                + " "
                + "    select in_f.EmpCode, in_g.NameChi, in_e.Code, in_e.Descr, "
                + "        sum(case in_a.Type when 'R' then in_b.LineAmount * -1 else in_b.LineAmount end) Sales "
                + "    from tSalesInvoice in_a "
                + "    inner join tSalesInvoiceDetail in_b on in_b.SINo = in_a.No "
                + "    inner join tItem in_c on in_c.Code = in_b.ITCode "
                + "    inner join tItemType2 in_d on in_d.Code = in_c.ItemType2 "
                + "    inner join tItemType12 in_e on in_e.Code = in_d.ItemType12 "
                + "    inner join tSaleStakeHolder in_f on in_f.SINo = in_a.No "
                + "    inner join tEmployee in_g on in_g.Number = in_f.EmpCode "
                + "    inner join tCustomer in_h on in_h.Code = in_a.CustCode "
                + "    where in_a.Status = 'CONFIRMED' "
                + "        and in_a.ItemType1 = 'RZ' "
                + "        and in_c.ItemType2 <> '0267' "  // 排除预估
                + "        and in_f.Role = '50' ";
        
        if (StringUtils.isNotBlank(customer.getCustType())) {
            sql += "       and in_h.CustType in ('" + customer.getCustType().replace(",", "', '") + "') ";
        }
        
        if (customer.getDateFrom() != null) {
            sql += "       and in_a.GetItemDate >= ? ";
            params.add(customer.getDateFrom());
        }
        
        if (customer.getDateTo() != null) {
            sql += "       and in_a.GetItemDate < dateadd(day, 1, ?) ";
            params.add(customer.getDateTo());
        }
                
        sql +=    "    group by in_f.EmpCode, in_g.NameChi, in_e.Code, in_e.Descr "
                + ") a "
                + "group by a.SoftDesigner, a.SoftDesignerName ";

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.SoftDesigner ";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }

}
