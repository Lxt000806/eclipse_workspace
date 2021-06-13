package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.SupplJob;

@Repository
@SuppressWarnings("serial")
public class IntDeliverAnalysisDao extends BaseDao {
    
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SupplJob supplJob, UserContext userContext) {

        ArrayList<Object> parameters = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.CustCode, e.Address, a.Date, "
                + "    case a.JobType "
                + "        when '08' then f.CupAppDate "
                + "        when '09' then f.IntAppDate "
                + "        when '27' then f.DoorAppDate "
                + "        when '28' then f.TableAppDate "
                + "    end as AppDate, "
                + "    case a.JobType "
                + "        when '08' then dateadd(day, ( "
                + "            select SendDay "
                + "            from tIntSendDay "
                + "            where ItemType12 = '30' "
                + "            and MaterialCode = f.CupMaterial), f.CupAppDate) "
                + "        when '09' then dateadd(day, ( "
                + "            select SendDay "
                + "            from tIntSendDay "
                + "            where ItemType12 = '31' "
                + "            and MaterialCode = f.IntMaterial), f.IntAppDate) "
                + "    end as PlanDelivDate, "
                + "    a.JobType, b.Descr JobTypeDescr, d.Descr SupplierDescr, "
                + "    c.Date AssignedDAte, c.CompleteDate, "
                + "    datediff(day, dateadd(day, 3, c.Date), c.CompleteDate) OverdueDays, "
                + "    c.SupplRemarks,g.InWHDate,h.SendDate "
                + "from tPrjJob a "
                + "left join tJobType b on a.JobType = b.Code "
                + "left join tSupplJob c on a.No = c.PrjJobNo "
                + "left join tSupplier d on c.SupplCode = d.Code "
                + "left join tCustomer e on a.CustCode = e.Code "
                + "left join tCustIntProg f on a.CustCode = f.CustCode "
                + "left join tIntProduce g on g.CustCode = a.CustCode and ((a.JobType = '08' and g.IsCupboard='1') or (a.JobType = '09' and g.IsCupboard='0'))"
                + "left join ( "
                + "    select min(SendDate) SendDate, CustCode, IsCupboard "
                + "    from tItemApp"
                + "	   where SendDate is not null and itemtype1='JC' "
                + "    group by CustCode, IsCupboard " 
                + ") h on h.custcode=a.CustCode and h.IsCupboard=b.IsCupboard and a.JobType in('08', '09')  "
                + "where a.JobType in('08', '09', '27', '28') "
                + "    and a.Status not in('1', '5') "
                + "    and c.PK is not null ";

        if (StringUtils.isNotBlank(supplJob.getAddress())) {
            sql += "and e.Address like ? ";
            parameters.add("%" + SqlUtil.insertPercentSignBeforeFirstDigit(supplJob.getAddress()) + "%");
        }
        
        if (StringUtils.isNotBlank(supplJob.getSupplCode())) {
            sql += "and c.SupplCode = ? ";
            parameters.add(supplJob.getSupplCode());
        }
        
        if (StringUtils.isNotBlank(supplJob.getJobType())) {
            sql += "and a.JobType in('" + supplJob.getJobType().replace(",", "', '") + "') ";
        }
        
        if (supplJob.getCompleteDateFrom() != null) {
            sql += "and c.CompleteDate >= ? ";
            parameters.add(supplJob.getCompleteDateFrom());
        }

        if (supplJob.getCompleteDateTo() != null) {
            sql += "and c.CompleteDate <= dateadd(day, 1, ?) ";
            parameters.add(supplJob.getCompleteDateTo());
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.CustCode";
        }

        return findPageBySql(page, sql, parameters.toArray());
    }
}
