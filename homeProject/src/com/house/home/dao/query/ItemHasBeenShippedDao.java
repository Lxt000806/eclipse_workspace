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
public class ItemHasBeenShippedDao extends BaseDao {

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.No, a.CustCode, b.Address, "
                + "    b.CustType, c.Desc1 CustTypeDescr, a.DelivType, d.NOTE DelivTypeDescr, "
                + "    a.SupplCode, e.Descr SupplierDescr, a.WHCode, f.Desc1 WarehouseDescr, "
                + "    a.Date AppDate, a.ConfirmDate, a.ArriveDate, a.NotifySendDate, a.SendDate, "
                + "    case when a.NotifySendDate is null "
                + "            then datediff(day, a.ArriveDate, a.SendDate) "
                + "        else "
                + "            datediff(day, case when a.ArriveDate >= dateadd(day, 3, a.NotifySendDate) then a.ArriveDate "
                + "                               else dateadd(day, 3, a.NotifySendDate) end, "
                + "                    a.SendDate) "
                + "        end DelayDays, "
                + "    a.DelayReson, g.NOTE DelayResonDescr, a.DelayRemark, a.SplRemark, "
                + "    dbo.fGetEmpNameChi(a.CustCode, '34') MainSteward "
                + "from tItemApp a "
                + "inner join tCustomer b on b.Code = a.CustCode "
                + "inner join tCusttype c on c.Code = b.CustType "
                + "left join tXTDM d on d.ID = 'DELIVTYPE' and d.CBM = a.DelivType "
                + "left join tSupplier e on e.Code = a.SupplCode "
                + "left join tWareHouse f on f.Code = a.WHCode "
                + "left join tXTDM g on g.ID = 'APPDELAYRESON' and g.CBM = a.DelayReson "
                + "where a.Status = 'SEND' ";
        
        if (StringUtils.isNotBlank(customer.getAddress())) {
            sql += "and b.Address like ? ";
            params.add("%" + customer.getAddress() + "%");
        }
        
        if (StringUtils.isNotBlank(customer.getItemType1())) {
            sql += "and a.ItemType1 = ? ";
            params.add(customer.getItemType1());
        }
        
        if (StringUtils.isNotBlank(customer.getSendType())) {
            sql += "and a.SendType = ? ";
            params.add(customer.getSendType());
        }
        
        if (StringUtils.isNotBlank(customer.getWhCode())) {
            sql += "and a.WHCode = ? ";
            params.add(customer.getWhCode());
        }
        
        if (StringUtils.isNotBlank(customer.getSupplier())) {
            sql += "and a.SupplCode = ? ";
            params.add(customer.getSupplier());
        }
        
        if (StringUtils.isNotBlank(customer.getWhetherNotifySend())) {
            if ("0".equals(customer.getWhetherNotifySend())) {
                sql += "and a.NotifySendDate is null ";
            } else {
                sql += "and a.NotifySendDate is not null ";
            }
        }
        
        if (customer.getNotifySendDateFrom() != null) {
            sql += "and a.NotifySendDate >= ? ";
            params.add(customer.getNotifySendDateFrom());
        }
        
        if (customer.getNotifySendDateTo() != null) {
            sql += "and a.NotifySendDate < dateadd(day, 1, ?) ";
            params.add(customer.getNotifySendDateTo());
        }
        
        if (customer.getDateFrom() != null) {
            sql += "and a.SendDate >= ? ";
            params.add(customer.getDateFrom());
        }
        
        if (customer.getDateTo() != null) {
            sql += "and a.SendDate < dateadd(day, 1, ?) ";
            params.add(customer.getDateTo());
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.No desc ";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }

}
