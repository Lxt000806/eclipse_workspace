package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustPool;
import com.house.home.entity.design.ResrCustPoolEmp;
import com.house.home.entity.design.ResrCustPoolRule;
import com.house.home.entity.design.ResrCustPoolRuleCzy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class ResrCustPoolDao extends BaseDao {

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool, UserContext userContext) {
        
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.No, a.Descr, a.Type, b.NOTE TypeDescr, "
                + "    a.IsVirtualPhone, c.NOTE IsVirtualPhoneDescr, "
                + "    a.IsHideChannel, d.NOTE IsHideChannelDescr, "
                + "    a.ReceiveRule, e.NOTE ReceiveRuleDescr, "
                + "    a.DispatchRule, f.NOTE DispatchRuleDescr, "
                + "    a.RecoverRule, g.NOTE RecoverRuleDescr, "
                + "    a.MaxValidResrCustNumber, a.MaxNoDispatchAlarmNumber, "
                + "    a.Priority, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from  tResrCustPool a "
                + "left join tXTDM b on b.ID = 'POOLTYPE' and b.CBM = a.Type "
                + "left join tXTDM c on c.ID = 'YESNO' and c.CBM = a.IsVirtualPhone "
                + "left join tXTDM d on d.ID = 'YESNO' and d.CBM = a.IsHideChannel "
                + "left join tXTDM e on e.ID = 'RECEIVERULE' and e.CBM = a.ReceiveRule "
                + "left join tXTDM f on f.ID = 'DISPATCHRULE' and f.CBM = a.DispatchRule "
                + "left join tXTDM g on g.ID = 'RECOVERRULE' and g.CBM = a.RecoverRule "
                + "where 1 = 1 ";
        
        if ("CZYBM_ADMIN".equals(resrCustPool.getAdminType())) {
            // 超级管理员，不作限制
        } else if ("RESR_CUST_POOL_MODULE_ADMIN".equals(resrCustPool.getAdminType())) {
            sql += "and a.Type <> '1' ";
        } else {
            sql += "and exists(select 1 from tResrCustPoolEmp in_a "
                    + "where in_a.ResrCustPoolNo = a.No and in_a.Type = '0' and in_a.CZYBH = ?) ";
            params.add(userContext.getCzybh());
        }
        
        if (StringUtils.isNotBlank(resrCustPool.getDescr())) {
            sql += "and a.Descr like ? ";
            params.add("%" + resrCustPool.getDescr() + "%");
        }
        
        if (StringUtils.isNotBlank(resrCustPool.getIsVirtualPhone())) {
            sql += "and a.IsVirtualPhone = ? ";
            params.add(resrCustPool.getIsVirtualPhone());
        }
        
        if (StringUtils.isNotBlank(resrCustPool.getIsHideChannel())) {
            sql += "and a.IsHideChannel = ? ";
            params.add(resrCustPool.getIsHideChannel());
        }
        
        if (StringUtils.isNotBlank(resrCustPool.getReceiveRule())) {
            sql += "and a.ReceiveRule = ? ";
            params.add(resrCustPool.getReceiveRule());
        }
        
        if (StringUtils.isNotBlank(resrCustPool.getDispatchRule())) {
            sql += "and a.DispatchRule = ? ";
            params.add(resrCustPool.getDispatchRule());
        }
        
        if (StringUtils.isNotBlank(resrCustPool.getRecoverRule())) {
            sql += "and a.RecoverRule = ? ";
            params.add(resrCustPool.getRecoverRule());
        }
        
        if (StringUtils.isNotBlank(resrCustPool.getType())) {
            sql += "and a.Type = ? ";
            params.add(resrCustPool.getType());
        }
        
        if (StringUtils.isBlank(resrCustPool.getExpired())
                || "F".equals(resrCustPool.getExpired())) {
            sql += "and a.Expired = 'F' ";
        }
    
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.Type asc, a.Priority desc";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }

    public Page<Map<String, Object>> findAdminPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool) {
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.PK, a.ResrCustPoolNo, a.Type, b.NOTE TypeDescr, "
                + "    a.CZYBH, c.ZWXM, a.Weight, a.GroupSign, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tResrCustPoolEmp a "
                + "left join tXTDM b on b.ID = 'POOLEMPTYPE' and b.CBM = a.Type "
                + "left join tCZYBM c on c.CZYBH = a.CZYBH "
                + "where a.ResrCustPoolNo = ? and a.Type = '0' ";
        
        params.add(resrCustPool.getNo());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.PK";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }

    public Page<Map<String, Object>> findMemberPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool) {
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.PK, a.ResrCustPoolNo, a.Type, b.NOTE TypeDescr, "
                + "    a.CZYBH, c.ZWXM, a.Weight, a.GroupSign, "
                + "    case when d.Cnt is null then 0 else 1 end OnLeave, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tResrCustPoolEmp a "
                + "left join tXTDM b on b.ID = 'POOLEMPTYPE' and b.CBM = a.Type "
                + "left join tCZYBM c on c.CZYBH = a.CZYBH "
                + "left join ( "
                + "    select in_a.CZYBH, count(in_a.PK) Cnt "
                + "    from tEmpLeaveDayLog in_a "
                + "    where in_a.Expired = 'F' "
                + "        and in_a.LeaveFrom <= ? "
                + "        and in_a.LeaveTo >= ? "
                + "    group by in_a.CZYBH "
                + ") d on d.CZYBH = a.CZYBH "
                + "where a.ResrCustPoolNo = ? and a.Type = '1' ";
        
        Date now = new Date();
        params.add(now);
        params.add(now);
        params.add(resrCustPool.getNo());
        
        if (StringUtils.isNotBlank(resrCustPool.getExcludedCzys())) {
            sql += " and a.CZYBH not in ('" + resrCustPool.getExcludedCzys().replace(",", "', '") + "') ";
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.Weight desc";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }

    public Page<Map<String, Object>> findDispatchPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool) {
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.PK, a.ResrCustPoolNo, a.Descr, "
                + "    a.RuleClass, b.NOTE RuleClassDescr, a.Scope, c.NOTE ScopeDescr, "
                + "    a.Type, c.NOTE TypeDescr, a.DispatchCZYScope, e.NOTE DispatchCZYScopeDescr, "
                + "    a.GroupSign, a.ToResrCustPoolNo, a.Sql, a.DispSeq, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tResrCustPoolRule a "
                + "left join tXTDM b on b.ID = 'POOLRULECLASS' and b.CBM = a.RuleClass "
                + "left join tXTDM c on c.ID = 'POOLRULESCOPE' and c.CBM = a.Scope "
                + "left join tXTDM d on d.ID = 'POOLRULETYPE' and d.CBM = a.Type "
                + "left join tXTDM e on e.ID = 'DPCZYSCOPE' and e.CBM = a.DispatchCZYScope "
                + "where a.ResrCustPoolNo = ? and a.RuleClass = '1' ";
        
        params.add(resrCustPool.getNo());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.DispSeq";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }
    
    public Page<Map<String, Object>> findDispatchMemberPageBySql(Page<Map<String, Object>> page,
            ResrCustPoolRule resrCustPoolRule) {
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.PK, a.PoolRulePK, "
                + "    a.CZYBH, b.ZWXM, "
                + "    a.DispSeq, a.ResrCustNumber, "
                + "    a.Weight, "
                + "    a.LastUpdate, a.LastUpdatedBy, "
                + "    a.Expired, a.ActionLog "
                + "from tResrCustPoolRuleCZY a "
                + "left join tCZYBM b on b.CZYBH = a.CZYBH "
                + "where a.PoolRulePK = ? ";
        
        params.add(resrCustPoolRule.getPk());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.Weight desc";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }

    public Page<Map<String, Object>> findRecoverPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool) {
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.PK, a.ResrCustPoolNo, a.Descr, "
                + "    a.RuleClass, b.NOTE RuleClassDescr, a.Scope, c.NOTE ScopeDescr, "
                + "    a.Type, c.NOTE TypeDescr, a.DispatchCZYScope, e.NOTE DispatchCZYScopeDescr, "
                + "    a.GroupSign, a.ToResrCustPoolNo, a.Sql, a.DispSeq, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tResrCustPoolRule a "
                + "left join tXTDM b on b.ID = 'POOLRULECLASS' and b.CBM = a.RuleClass "
                + "left join tXTDM c on c.ID = 'POOLRULESCOPE' and c.CBM = a.Scope "
                + "left join tXTDM d on d.ID = 'POOLRULETYPE' and d.CBM = a.Type "
                + "left join tXTDM e on e.ID = 'DPCZYSCOPE' and e.CBM = a.DispatchCZYScope "
                + "where a.ResrCustPoolNo = ? and a.RuleClass = '2' ";
        
        params.add(resrCustPool.getNo());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.DispSeq";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }
    
    public ResrCustPoolEmp findAdminByPoolNoAndCzybh(String poolNo, String czybh) {
        String sql = "select a.* from tResrCustPoolEmp a "
                + "where a.Type = '0' and a.ResrCustPoolNo = ? and a.CZYBH = ?";
        
        List<ResrCustPoolEmp> admins =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo, czybh), ResrCustPoolEmp.class);
        
        if (admins != null && admins.size() > 0) {
            return admins.get(0);
        }
        
        return null;
    }
    
    public List<ResrCustPoolEmp> findAdminsByPoolNo(String poolNo) {
        String sql = "select a.* from tResrCustPoolEmp a "
                + "where a.Type = '0' and a.ResrCustPoolNo = ?";
        
        List<ResrCustPoolEmp> admins =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo), ResrCustPoolEmp.class);
        
        if (admins != null) {
            return admins;
        }
        
        return Collections.emptyList();
    }
    
    public ResrCustPoolEmp findMemberByPoolNoAndCzybh(String poolNo, String czybh) {
        String sql = "select a.* from tResrCustPoolEmp a "
        		+ "where a.Type = '1' and a.ResrCustPoolNo = ? and a.CZYBH = ?";
        
        List<ResrCustPoolEmp> members =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo, czybh), ResrCustPoolEmp.class);
        
        if (members != null && members.size() > 0) {
            return members.get(0);
        }
        
        return null;
    }
    
    public List<ResrCustPoolEmp> findMembersByPoolNo(String poolNo) {
        String sql = "select a.* from tResrCustPoolEmp a "
                + "where a.Type = '1' and a.Expired = 'F' and a.ResrCustPoolNo = ?";
        
        List<ResrCustPoolEmp> members =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo), ResrCustPoolEmp.class);
        
        if (members != null && members.size() > 0) {
            return members;
        }
        
        return Collections.emptyList();
    }
    
    public List<ResrCustPoolEmp> findMembersByPoolNoAndGroupSign(String poolNo, String groupSign) {
        String sql = "select a.* from tResrCustPoolEmp a "
                + "where a.Type = '1' and a.Expired = 'F' "
                + "    and a.ResrCustPoolNo = ? and a.GroupSign = ?";
        
        List<ResrCustPoolEmp> members =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo, groupSign), ResrCustPoolEmp.class);
        
        if (members != null && members.size() > 0) {
            return members;
        }
        
        return Collections.emptyList();
    }
    
    public List<ResrCustPoolRule> findDispatchesByPoolNo(String poolNo) {
        String sql = "" 
                + "select a.* "
                + "from tResrCustPoolRule a "
                + "where a.RuleClass = '1' and a.Expired = 'F' "
                + "and a.ResrCustPoolNo = ?";
        
        List<ResrCustPoolRule> dispatches =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo), ResrCustPoolRule.class);
        
        if (dispatches != null && dispatches.size() > 0) {
            return dispatches;
        }
        
        return Collections.emptyList();
    }
    
    public List<ResrCustPoolRule> findDispatchesByPoolNoAndDispatchCZYScope(
            String poolNo, String dispatchCZYScope) {
        
        String sql = ""
                + "select a.* "
                + "from tResrCustPoolRule a "
                + "where a.RuleClass = '1' and a.Expired = 'F' and "
                + "a.ResrCustPoolNo = ? and a.DispatchCZYScope = ?";
        
        List<ResrCustPoolRule> dispatches =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo, dispatchCZYScope), ResrCustPoolRule.class);
        
        if (dispatches != null && dispatches.size() > 0) {
            return dispatches;
        }
        
        return Collections.emptyList();
    }
    
    public List<ResrCustPoolRuleCzy> findRuleCzysByRulePk(int rulePk) {
        String sql = ""
                + "select a.* "
                + "from tResrCustPoolRuleCZY a "
                + "where a.Expired = 'F' "
                + "    and a.PoolRulePK = ? ";
        
        List<ResrCustPoolRuleCzy> ruleCzys =
                BeanConvertUtil.mapToBeanList(findBySql(sql, rulePk), ResrCustPoolRuleCzy.class);
        
        if (ruleCzys != null && ruleCzys.size() > 0) {
            return ruleCzys;
        }
        
        return Collections.emptyList();
    }
    
    public void deleteRuleByPk(int pk) {
        String sql = "delete from tResrCustPoolRule where PK = ?";
        
        executeUpdateBySql(sql, pk);
    }
    
    public void deleteRuleCzyByRulePk(int rulePk) {
        String sql = "delete from tResrCustPoolRuleCZY where PoolRulePK = ?";
        
        executeUpdateBySql(sql, rulePk);
    }
    
    public void addLeaveLogForToday(ResrCustPoolEmp poolEmp) {
        List<Object> params = new ArrayList<Object>();
        
        String sql = ""
                + "if not exists (select 1 from tEmpLeaveDayLog "
                + "    where LeaveFrom <= ? and LeaveTo >= ? "
                + "    and Expired = 'F' and CZYBH = ?) "
                + "begin "
                + "    insert into tEmpLeaveDayLog (CZYBH, LeaveFrom, LeaveTo, "
                + "        LastUpdate, LastUpdatedBy, Expired, ActionLog) "
                + "    values (?, "
                + "        dateadd(day, datediff(day, '20210101 00:00:00', ?), '20210101 00:00:00'), "
                + "        dateadd(day, datediff(day, '20210101 23:59:59', ?), '20210101 23:59:59'), "
                + "        getdate(), ?, 'F', 'ADD') "
                + "end ";
        
        Date now = new Date();
        params.add(now);
        params.add(now);
        params.add(poolEmp.getCzybh());
        params.add(poolEmp.getCzybh());
        params.add(now);
        params.add(now);
        params.add(poolEmp.getLastUpdatedBy());
        
        executeUpdateBySql(sql, params.toArray());
    }
    
    public void delLeaveLogForToday(ResrCustPoolEmp poolEmp) {
        String sql = ""
        		+ "delete from tEmpLeaveDayLog "
                + "where Expired = 'F' "
                + "    and LeaveFrom <= ? "
                + "    and LeaveTo >= ? "
                + "    and CZYBH = ? ";
        
        Date now = new Date();
        executeUpdateBySql(sql, now, now, poolEmp.getCzybh());
    }

    public void doClear(ResrCustPool resrCustPool) {
        String sql = ""
                + "delete from tResrCustPoolEmp where ResrCustPoolNo = ? " + System.lineSeparator()
                + "delete from tResrCustPoolRuleCZY "
                + "where exists(select 1 from tResrCustPoolRule in_a "
                + "    where in_a.PK = PoolRulePK and in_a.ResrCustPoolNo = ?) " + System.lineSeparator()
                + "delete from tResrCustPoolRule where ResrCustPoolNo = ? ";
        
        executeUpdateBySql(sql, resrCustPool.getNo(), resrCustPool.getNo(), resrCustPool.getNo());
    }

    public void doDeleteMembers(String poolNo, String pks) {

        String sql = ""
                + "delete from tResrCustPoolRuleCZY "
                + "where CZYBH in (select in_a.CZYBH from tResrCustPoolEmp in_a where PK in (" + pks + ")) "
                + "    and exists(select 1 from tResrCustPoolRule in_a "
                + "            where in_a.PK = PoolRulePK "
                + "                and in_a.RuleClass = '1' "
                + "                and in_a.ResrCustPoolNo = ?) "
                + System.lineSeparator()
                + "delete from tResrCustPoolEmp "
                + "where PK in(" + pks + ") ";
        
        executeUpdateBySql(sql, poolNo);
    }
    
    public void deleteRuleCzyByPoolNoAndGroupSignAndCzybh(String poolNo,
            String groupSign, String czybh) {
        
        String sql = ""
                + "delete from tResrCustPoolRuleCZY "
                + "where exists(select 1 from tResrCustPoolRule in_a "
                + "            where in_a.PK = PoolRulePK "
                + "                and in_a.RuleClass = '1' "
                + "                and in_a.DispatchCZYScope = '2' "
                + "                and in_a.GroupSign = ? "
                + "                and in_a.ResrCustPoolNo = ?) "
                + "    and CZYBH = ? ";
        
        executeUpdateBySql(sql, groupSign, poolNo, czybh);
    }
    
    public List<ResrCustPool> findAllPools() {
        String sql = ""
                + "select * "
                + "from tResrCustPool a "
                + "where a.Expired = 'F' "
                + "order by a.Type asc, a.Priority desc";
        
        List<ResrCustPool> pools =
                BeanConvertUtil.mapToBeanList(findBySql(sql), ResrCustPool.class);
        
        return pools != null ? pools : Collections.<ResrCustPool>emptyList();
    }
    
    /**
     * 查询待派单资源
     * 
     * @param poolNo
     * @return
     */
    public List<ResrCust> findUndispatchedResrCustsByPoolNo(String poolNo) {
        String sql = ""
                + "select * "
                + "from tResrCust a "
                + "where a.Expired = 'F' "
                + "    and isnull(a.BusinessMan, '') = '' "
                + "    and a.ValidDispatchCount = 0 "
                + "    and a.ResrCustPoolNo = ? ";
        
        List<ResrCust> resrCusts =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo), ResrCust.class);
        
        return resrCusts != null ? resrCusts : Collections.<ResrCust>emptyList();
    }
    
    /**
     * 查询一轮派单后残留资源数量
     * 
     * @param poolNo
     * @return
     */
    public long findNoDispatchResrCustCountByPoolNo(String poolNo) {
        String sql = ""
                + "select * "
                + "from tResrCust a "
                + "where a.Expired = 'F' "
                + "    and isnull(a.BusinessMan, '') = '' "
                + "    and a.ResrCustPoolNo = ? ";
        
        return countSqlResult(sql, null, poolNo);
    }
    
    public List<ResrCustPoolEmp> findMembersForDispatchByPoolNo(String poolNo) {
        String sql = ""
                + "select a.* "
                + "from tResrCustPoolEmp a "
                + "where a.Expired = 'F' "
                + "    and a.Type = '1' "
                + "    and not exists( "
                + "        select 1 from tEmpLeaveDayLog in_a "
                + "        where in_a.Expired = 'F' "
                + "            and in_a.LeaveFrom <= ? "
                + "            and in_a.LeaveTo >= ? "
                + "            and in_a.CZYBH = a.CZYBH "
                + "    ) "
                + "    and ( "
                + "            select count(in_a.Code) "
                + "            from tResrCust in_a "
                + "            where in_a.BusinessMan = a.CZYBH "
                + "                and in_a.CustResStat in ('0', '1') "
                + "                and in_a.Expired = 'F' "
                + "                and in_a.ResrCustPoolNo = a.ResrCustPoolNo "
                + "        ) <= ( "
                + "            select in_b.MaxValidResrCustNumber "
                + "            from tResrCustPool in_b "
                + "            where in_b.No = a.ResrCustPoolNo "
                + "        ) "
                + "    and a.ResrCustPoolNo = ? ";
        
        Date now = new Date();
        
        List<ResrCustPoolEmp> members =
                BeanConvertUtil.mapToBeanList(findBySql(sql, now, now, poolNo), ResrCustPoolEmp.class);
        
        return members != null ? members : Collections.<ResrCustPoolEmp>emptyList();
    }
    
    public List<ResrCustPoolRule> findDispatchRulesByPoolNo(String poolNo) {
        String sql = ""
                + "select * "
                + "from tResrCustPoolRule a "
                + "where a.Expired = 'F' "
                + "    and a.RuleClass = '1' "
                + "    and a.ResrCustPoolNo = ? "
                + "order by a.DispSeq asc";
        
        List<ResrCustPoolRule> dispatchRule =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo), ResrCustPoolRule.class);
        
        return dispatchRule != null ? dispatchRule : Collections.<ResrCustPoolRule>emptyList();
    }
    
    public List<ResrCustPoolRule> findRecoverRulesByPoolNo(String poolNo) {
        String sql = ""
                + "select * "
                + "from tResrCustPoolRule a "
                + "where a.Expired = 'F' "
                + "    and a.RuleClass = '2' "
                + "    and a.ResrCustPoolNo = ? "
                + "order by a.DispSeq asc";
        
        List<ResrCustPoolRule> dispatchRule =
                BeanConvertUtil.mapToBeanList(findBySql(sql, poolNo), ResrCustPoolRule.class);
        
        return dispatchRule != null ? dispatchRule : Collections.<ResrCustPoolRule>emptyList();
    }

    public List<ResrCust> findResrCustsByCustomSql(String sql) {
        long count = 0;
        
        try {
            
            count = countSqlResult(sql, null);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        int maxCount = 200;
        
        if (count > maxCount) {
            throw new RuntimeException(
                    "自定义SQL筛选结果超过" + maxCount + "条，请检查SQL筛选条件是否准确！");
        }
        
        List<ResrCust> undispatchedCusts =
                BeanConvertUtil.mapToBeanList(findBySql(sql), ResrCust.class);
        
        return undispatchedCusts != null ? undispatchedCusts
                : Collections.<ResrCust>emptyList();
    }

    public List<ResrCustPoolRuleCzy> findRuleCzysForDispatchByRulePk(Integer rulePk) {
        String sql = ""
                + "select a.* "
                + "from tResrCustPoolRuleCZY a "
                + "where a.Expired = 'F' "
                + "    and not exists( "
                + "        select 1 from tEmpLeaveDayLog in_a "
                + "        where in_a.Expired = 'F' "
                + "            and in_a.LeaveFrom <= ? "
                + "            and in_a.LeaveTo >= ? "
                + "            and in_a.CZYBH = a.CZYBH "
                + "    ) "
                + "    and ( "
                + "            select count(in_a.Code) "
                + "            from tResrCust in_a "
                + "            where in_a.BusinessMan = a.CZYBH "
                + "                and in_a.CustResStat in ('0', '1') "
                + "                and in_a.Expired = 'F' "
                + "                and in_a.ResrCustPoolNo = ( "
                + "                    select in_c.ResrCustPoolNo "
                + "                    from tResrCustPoolRule in_c "
                + "                    where in_c.PK = a.PoolRulePK "
                + "                ) "
                + "        ) <= ( "
                + "            select in_b.MaxValidResrCustNumber "
                + "            from tResrCustPool in_b "
                + "            where in_b.No = ( "
                + "                select in_c.ResrCustPoolNo "
                + "                from tResrCustPoolRule in_c "
                + "                where in_c.PK = a.PoolRulePK "
                + "            ) "
                + "        ) "
                + "    and a.PoolRulePK = ? ";
        
        Date now = new Date();
        
        List<ResrCustPoolRuleCzy> ruleCzys =
                BeanConvertUtil.mapToBeanList(findBySql(sql, now, now, rulePk), ResrCustPoolRuleCzy.class);
        
        return ruleCzys != null ? ruleCzys : Collections.<ResrCustPoolRuleCzy>emptyList();
    }
    
    public void addBusinessManForResrCust(ResrCust resrCust) {
        String sql = ""
                + "update tResrCust "
                + "set BusinessMan = ?, "
                + "    DispatchDate = getdate(), "
                + "    CustResStat = '0', "
                + "    PublicResrLevel = '0', "
                + "    Department = ("
                + "        select in_a.Department2 "
                + "        from tEmployee in_a "
                + "        where in_a.Number = ? "
                + "    ) "
                + "where Code = ? ";
        
        executeUpdateBySql(sql, resrCust.getBusinessMan(),
                resrCust.getBusinessMan(), resrCust.getCode());
    }
    
    public void removeBusinessManForResrCust(ResrCust resrCust) {
        String sql = ""
                + "update tResrCust "
                + "set BusinessMan = '', "
                + "    CustResStat = '0', "
                + "    DispatchDate = null "
                + "where Code = ? ";
        
        executeUpdateBySql(sql, resrCust.getCode());
    }
    
    public void moveResrCustToAnotherPool(ResrCust resrCust) {
        String sql = ""
                + "update tResrCust "
                + "set BusinessMan = '', "
                + "    CustResStat = '0', "
                + "    ResrCustPoolNo = ?, "
                + "    DispatchDate = null "
                + "where Code = ? ";
        
        executeUpdateBySql(sql, resrCust.getResrCustPoolNo(), resrCust.getCode());
    }
    
    public void updateMemberResrCustNumber(ResrCustPoolEmp member) {
        String sql = ""
                + "update tResrCustPoolEmp "
                + "set ResrCustNumber = ? "
                + "where PK = ? ";
        
        executeUpdateBySql(sql, member.getResrCustNumber(), member.getPk());
    }
    
    public void updateRuleCzyResrCustNumber(ResrCustPoolRuleCzy ruleCzy) {
        String sql = ""
                + "update tResrCustPoolRuleCZY "
                + "set ResrCustNumber = ? "
                + "where PK = ? ";
        
        executeUpdateBySql(sql, ruleCzy.getResrCustNumber(), ruleCzy.getPk());
    }
    
    public void expirePool(String poolNo, String expired) {
        String sql = ""
                + "update tResrCustPool "
                + "set Expired = ? "
                + "where No = ? ";
        
        executeUpdateBySql(sql, expired, poolNo);
    }

}
