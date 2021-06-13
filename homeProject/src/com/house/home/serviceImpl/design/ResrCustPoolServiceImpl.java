package com.house.home.serviceImpl.design;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.jpush.Notice;
import com.house.home.client.jpush.TestJpushClient;
import com.house.home.dao.design.ResrCustPoolDao;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustPool;
import com.house.home.entity.design.ResrCustPoolEmp;
import com.house.home.entity.design.ResrCustPoolRule;
import com.house.home.entity.design.ResrCustPoolRuleCzy;
import com.house.home.service.design.ResrCustPoolService;

@SuppressWarnings("serial")
@Service 
public class ResrCustPoolServiceImpl extends BaseServiceImpl implements ResrCustPoolService {
    
	@Autowired
	private  ResrCustPoolDao resrCustPoolDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool, UserContext userContext) {
        return resrCustPoolDao.findPageBySql(page, resrCustPool, userContext);
    }

    @Override
    public Page<Map<String, Object>> findAdminPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool) {
        return resrCustPoolDao.findAdminPageBySql(page, resrCustPool);
    }

    @Override
    public Page<Map<String, Object>> findMemberPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool) {
        return resrCustPoolDao.findMemberPageBySql(page, resrCustPool);
    }

    @Override
    public Page<Map<String, Object>> findDispatchPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool) {
        return resrCustPoolDao.findDispatchPageBySql(page, resrCustPool);
    }
    
    @Override
    public Page<Map<String, Object>> findDispatchMemberPageBySql(Page<Map<String, Object>> page,
            ResrCustPoolRule resrCustPoolRule) {
        return resrCustPoolDao.findDispatchMemberPageBySql(page, resrCustPoolRule);
    }

    @Override
    public Page<Map<String, Object>> findRecoverPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool) {
        return resrCustPoolDao.findRecoverPageBySql(page, resrCustPool);
    }

    @Override
    public void saveRuleOrder(int[] pks) {
        
        for (int i = 0; i < pks.length; i++) {
            ResrCustPoolRule rule = resrCustPoolDao.get(ResrCustPoolRule.class, pks[i]);
            rule.setDispSeq(i + 1);
            resrCustPoolDao.update(rule);
        }
        
    }

    @Override
    public void deletePoolRuleByPk(int pk) {
        resrCustPoolDao.deleteRuleByPk(pk);
        resrCustPoolDao.deleteRuleCzyByRulePk(pk);
    }

    @Override
    public void saveDispatch(ResrCustPoolRule resrCustPoolRule) {

        if (resrCustPoolRule.getDispatchCZYScope().equals("1")
                || resrCustPoolRule.getDispatchCZYScope().equals("3")) {
            
            resrCustPoolRule.setGroupSign("");
        }
        
        resrCustPoolDao.save(resrCustPoolRule);
        
        if (resrCustPoolRule.getType().equals("1"))
            if (resrCustPoolRule.getDispatchCZYScope().equals("1")
                    || resrCustPoolRule.getDispatchCZYScope().equals("2")) {
                
                List<ResrCustPoolEmp> members =
                        resrCustPoolDao.findMembersByPoolNo(resrCustPoolRule.getResrCustPoolNo());
                
                List<ResrCustPoolRule> dispatches = Arrays.asList(resrCustPoolRule);
                
                for (ResrCustPoolEmp member : members) {
                    copyMemberToRuleCzy(member, dispatches);
                }
                
            } else if (resrCustPoolRule.getDispatchCZYScope().equals("3")) {
                
                List<Map<String, String>> dispatchMembers = resrCustPoolRule.getDispatchMembers();
                
                if (dispatchMembers != null)
                    for (Map<String, String> map : dispatchMembers) {
                        ResrCustPoolRuleCzy ruleCzy = new ResrCustPoolRuleCzy();
                        
                        ruleCzy.setPoolRulePk(resrCustPoolRule.getPk());
                        ruleCzy.setCzybh(map.get("czybh"));
                        ruleCzy.setDispSeq(new Integer(map.get("dispSeq")));
                        ruleCzy.setResrCustNumber(new Integer(map.get("resrCustNumber")));
                        ruleCzy.setWeight(new Integer(map.get("weight")));
                        ruleCzy.setLastUpdate(DateUtil.parse(map.get("lastUpdate"), "yyyy-MM-dd HH:mm:ss"));
                        ruleCzy.setLastUpdatedBy(map.get("lastUpdatedBy"));
                        ruleCzy.setExpired(map.get("expired"));
                        ruleCzy.setActionLog(map.get("actionLog"));
                        
                        resrCustPoolDao.save(ruleCzy);
                    }
            }
    }

    @Override
    public void updateDispatch(ResrCustPoolRule resrCustPoolRule) {

        if (resrCustPoolRule.getDispatchCZYScope().equals("1")
                || resrCustPoolRule.getDispatchCZYScope().equals("3")) {
            
            resrCustPoolRule.setGroupSign("");
        }
        
        resrCustPoolDao.update(resrCustPoolRule);

        resrCustPoolDao.deleteRuleCzyByRulePk(resrCustPoolRule.getPk());

        if (resrCustPoolRule.getType().equals("1"))
            if (resrCustPoolRule.getDispatchCZYScope().equals("1")
                    || resrCustPoolRule.getDispatchCZYScope().equals("2")) {

                List<ResrCustPoolEmp> members = resrCustPoolDao
                        .findMembersByPoolNo(resrCustPoolRule.getResrCustPoolNo());

                List<ResrCustPoolRule> dispatches = Arrays.asList(resrCustPoolRule);

                for (ResrCustPoolEmp member : members) {
                    copyMemberToRuleCzy(member, dispatches);
                }

            } else if (resrCustPoolRule.getDispatchCZYScope().equals("3")) {

                List<Map<String, String>> dispatchMembers = resrCustPoolRule.getDispatchMembers();

                if (dispatchMembers != null)
                    for (Map<String, String> map : dispatchMembers) {
                        ResrCustPoolRuleCzy ruleCzy = new ResrCustPoolRuleCzy();

                        ruleCzy.setPoolRulePk(resrCustPoolRule.getPk());
                        ruleCzy.setCzybh(map.get("czybh"));
                        ruleCzy.setDispSeq(new Integer(map.get("dispSeq")));
                        ruleCzy.setResrCustNumber(new Integer(map.get("resrCustNumber")));
                        ruleCzy.setWeight(new Integer(map.get("weight")));
                        ruleCzy.setLastUpdate(DateUtil.parse(map.get("lastUpdate"),
                                "yyyy-MM-dd HH:mm:ss"));
                        ruleCzy.setLastUpdatedBy(map.get("lastUpdatedBy"));
                        ruleCzy.setExpired(map.get("expired"));
                        ruleCzy.setActionLog(map.get("actionLog"));

                        resrCustPoolDao.save(ruleCzy);
                    }

            }

    }
    
    @Override
    public ResrCustPoolEmp findAdminByPoolNoAndCzybh(String poolNo, String czybh) {
        return resrCustPoolDao.findAdminByPoolNoAndCzybh(poolNo, czybh);
    }

    @Override
    public ResrCustPoolEmp findMemberByPoolNoAndCzybh(String poolNo, String czybh) {
        return resrCustPoolDao.findMemberByPoolNoAndCzybh(poolNo, czybh);
    }

    @Override
    public void doDeleteMembers(String resrCustPoolNo, String pks) {
        resrCustPoolDao.doDeleteMembers(resrCustPoolNo, pks);
    }

    @Override
    public void setMembersWeight(int[] pks, int weight) {
        for (int pk : pks) {
            ResrCustPoolEmp member = resrCustPoolDao.get(ResrCustPoolEmp.class, pk);
            member.setWeight(weight);
            member.setResrCustNumber(weight);
            resrCustPoolDao.update(member);
        }
    }

    @Override
    public void setMembersGroupSign(int[] pks, String groupSign) {
        
        for (int pk : pks) {
            ResrCustPoolEmp member = resrCustPoolDao.get(ResrCustPoolEmp.class, pk);
            member.setOldGroupSign(member.getGroupSign());
            member.setGroupSign(groupSign);
            resrCustPoolDao.update(member);
            
            if (!groupSign.equals(member.getOldGroupSign())) {
                resrCustPoolDao.deleteRuleCzyByPoolNoAndGroupSignAndCzybh(
                        member.getResrCustPoolNo(),
                        member.getOldGroupSign(),
                        member.getCzybh());
                
                List<ResrCustPoolRule> dispatches =
                        resrCustPoolDao.findDispatchesByPoolNoAndDispatchCZYScope(
                                member.getResrCustPoolNo(), "2");
                
                copyMemberToRuleCzy(member, dispatches);
            }
        }
    }
    
    

    @Override
    public void doSaveMember(ResrCustPoolEmp resrCustPoolEmp) {
        save(resrCustPoolEmp);
        
        List<ResrCustPoolRule> dispatches =
                resrCustPoolDao.findDispatchesByPoolNo(resrCustPoolEmp.getResrCustPoolNo());
        
        copyMemberToRuleCzy(resrCustPoolEmp, dispatches);
    }

    @Override
    public void doImportMemberExcel(List<ResrCustPoolEmp> poolEmps, String czybh) {
        
        if (poolEmps.size() == 0) {
            throw new RuntimeException("没有要导入的数据！");
        }
        
        List<ResrCustPoolRule> dispatches =
                resrCustPoolDao.findDispatchesByPoolNo(poolEmps.get(0).getResrCustPoolNo());
        
        Date now = new Date();
        for (ResrCustPoolEmp emp : poolEmps) {
            
            emp.setResrCustNumber(emp.getWeight());
            emp.setType("1");
            emp.setDispSeq(0);
            emp.setActionLog("ADD");
            emp.setLastUpdate(now);
            emp.setLastUpdatedBy(czybh);
            emp.setExpired("F");
            
            save(emp);
            copyMemberToRuleCzy(emp, dispatches);
        }
        
    }
    
    private void copyMemberToRuleCzy(ResrCustPoolEmp member, List<ResrCustPoolRule> dispatches) {
        for (ResrCustPoolRule dispatch : dispatches) {
            
            // 派单成员范围：所有成员
            if (dispatch.getDispatchCZYScope().equals("1")) {
                ResrCustPoolRuleCzy poolRuleCzy = newPoolRuleCzyFromMember(member);
                
                poolRuleCzy.setPoolRulePk(dispatch.getPk());
                poolRuleCzy.setLastUpdatedBy(member.getLastUpdatedBy());
                
                save(poolRuleCzy);
            
            // 派单成员范围：组标签
            } else if (dispatch.getDispatchCZYScope().equals("2")) {
                if (dispatch.getGroupSign().equals(member.getGroupSign())) {
                    
                    ResrCustPoolRuleCzy poolRuleCzy = newPoolRuleCzyFromMember(member);
                    
                    poolRuleCzy.setPoolRulePk(dispatch.getPk());
                    poolRuleCzy.setLastUpdatedBy(member.getLastUpdatedBy());
                    
                    save(poolRuleCzy);
                }
            }
        }
    }
    
    private ResrCustPoolRuleCzy newPoolRuleCzyFromMember(ResrCustPoolEmp member) {
        ResrCustPoolRuleCzy poolRuleCzy = new ResrCustPoolRuleCzy();
        
        poolRuleCzy.setCzybh(member.getCzybh());
        poolRuleCzy.setDispSeq(0);
        poolRuleCzy.setResrCustNumber(member.getWeight());
        poolRuleCzy.setWeight(member.getWeight());
        poolRuleCzy.setLastUpdate(new Date());
        poolRuleCzy.setActionLog("ADD");
        poolRuleCzy.setExpired("F");
        
        return poolRuleCzy;
    }

    @Override
    public void doUpdateMember(ResrCustPoolEmp resrCustPoolEmp) {
        update(resrCustPoolEmp);
        
        if (!resrCustPoolEmp.getGroupSign().equals(resrCustPoolEmp.getOldGroupSign())) {
            resrCustPoolDao.deleteRuleCzyByPoolNoAndGroupSignAndCzybh(
                    resrCustPoolEmp.getResrCustPoolNo(),
                    resrCustPoolEmp.getOldGroupSign(),
                    resrCustPoolEmp.getCzybh());
            
            List<ResrCustPoolRule> dispatches =
                    resrCustPoolDao.findDispatchesByPoolNoAndDispatchCZYScope(resrCustPoolEmp.getResrCustPoolNo(), "2");
            copyMemberToRuleCzy(resrCustPoolEmp, dispatches);
        }
        
    }

    @Override
    public void doClear(ResrCustPool resrCustPool) {
        resrCustPoolDao.doClear(resrCustPool);
    }

    @Override
    public void doSetMemberDefaultPool(String[] czybhs, String poolNo) {
        for (String czybh : czybhs) {
            Czybm czybm = resrCustPoolDao.get(Czybm.class, czybh);
            czybm.setDefaultPoolNo(poolNo);
            update(czybm);
        }
    }

    @Override
    public void switchMemberState(List<ResrCustPoolEmp> emps) {
        
        for (ResrCustPoolEmp emp : emps) {
            if (emp.getOnLeave().intValue() == 0) {
                resrCustPoolDao.addLeaveLogForToday(emp);
            } else if (emp.getOnLeave().intValue() == 1) {
                resrCustPoolDao.delLeaveLogForToday(emp);
            }
        }
        
    }

    @Override
    public void recover() {
        List<ResrCustPool> allPools = resrCustPoolDao.findAllPools();
        
        for (ResrCustPool pool : allPools) {
            recoverByRule(pool);
        }
    }

    private void recoverByRule(ResrCustPool pool) {
        String rule = pool.getRecoverRule();
        
        if (rule.equals("0")) {
            
        } else if (rule.equals("1")) {
            recoverUseCustomRules(pool);
        }
    }

    private void recoverUseCustomRules(ResrCustPool pool) {
        List<ResrCustPoolRule> recoverRules =
                resrCustPoolDao.findRecoverRulesByPoolNo(pool.getNo());
        
        for (ResrCustPoolRule recoverRule : recoverRules) {
            List<ResrCust> resrCusts = Collections.emptyList();
            
            try {
                resrCusts = resrCustPoolDao.findResrCustsByCustomSql(recoverRule.getSql());
            } catch (Exception e) {
                resrCusts = Collections.emptyList();
                e.printStackTrace();
            }
            
            System.out.printf("回收：[%s]池 [%s]规则 有[%4d]条待回收记录，开始回收%n",
                    pool.getDescr(), recoverRule.getDescr(), resrCusts.size());
            
            for (ResrCust resrCust : resrCusts) {
                
                System.out.printf("回收：线索[%s] [%s] [%s]%n",
                        resrCust.getCode(), resrCust.getAddress(), resrCust.getDescr());
                
                resrCustPoolDao.removeBusinessManForResrCust(resrCust);
            }
        }
    }

    @Override
    public void dispatch() {
        List<ResrCustPool> allPools = resrCustPoolDao.findAllPools();
        
        for (ResrCustPool pool : allPools) {
            dispatchByRule(pool);
        }
    }
    
    private void dispatchByRule(ResrCustPool pool) {
        String rule = pool.getDispatchRule();
        
        if (rule.equals("0")) {
            dispatchManually(pool);
        } else if (rule.equals("1")) {
            dispatchByWeight(pool);
        } else if (rule.equals("2")) {
            dispatchUseCustomRules(pool);
        }
    }

    /**
     * 手动派单
     * 
     * @param pool
     */
    private void dispatchManually(ResrCustPool pool) {
        
    }

    /**
     * 权重派单
     * 
     * @param pool
     */
    private void dispatchByWeight(ResrCustPool pool) {
        List<ResrCust> undispatchedCusts =
                resrCustPoolDao.findUndispatchedResrCustsByPoolNo(pool.getNo());

        List<ResrCustPoolEmp> members =
                resrCustPoolDao.findMembersForDispatchByPoolNo(pool.getNo());
        
        System.out.printf("派单：[%s]池 [权重派单模式] 有[%4d]条待派单记录和[%4d]个成员，开始派单%n",
                pool.getDescr(), undispatchedCusts.size(), members.size());
        
        dispatchResrCustToMembers(pool, undispatchedCusts, members);
        
//        remindAdminsWhenReachMaxNoDispatchAlarmNumber(pool);
    }
    
    private void dispatchResrCustToMembers(ResrCustPool pool, List<ResrCust> resrCusts,
            List<ResrCustPoolEmp> members) {
        
        int weightSum = 0, resrCustNumberSum = 0;
        
        for (ResrCustPoolEmp member : members) {
            weightSum += member.getWeight();
            resrCustNumberSum += member.getResrCustNumber();
        }
        
        if (resrCusts.size() < resrCustNumberSum + weightSum) {
            dispatchToMemberInDynamicWay(resrCusts, members);            
        } else {
            int size = resrCusts.size();
            int quotient = (size - resrCustNumberSum) / weightSum;
            int reminder = (size - resrCustNumberSum) % weightSum;
            
            dispatchToMemberInProportion(resrCusts.subList(0, size - resrCustNumberSum - reminder), members, quotient);
            dispatchToMemberInDynamicWay(resrCusts.subList(size - resrCustNumberSum - reminder, size), members);
        }
        
        for (ResrCust resrCust : resrCusts)
            resrCustPoolDao.addBusinessManForResrCust(resrCust);
        
        if (resrCusts.size() > 0)
            for (ResrCustPoolEmp member : members)
                resrCustPoolDao.updateMemberResrCustNumber(member);
        
    }
    
    private void dispatchToMemberInDynamicWay(List<ResrCust> resrCusts,
            List<ResrCustPoolEmp> members) {
        
        for (ResrCust resrCust : resrCusts) {
            
            Collections.sort(members, new MemberDispatchComparator());
            
            ResrCustPoolEmp member = members.get(0);
            int resrCustNumber = member.getResrCustNumber();
            
            if (resrCustNumber == 0) {
                resetMemberResrCustNumber(members);
                Collections.sort(members, new MemberDispatchComparator());
                member = members.get(0);
            }
            
            resrCust.setBusinessMan(member.getCzybh());
            
            member.setResrCustNumber(member.getResrCustNumber() - 1);
            
            sendMessageToCzyWhenGetResrCust(member.getCzybh(), resrCust);
            
            System.out.printf("派单D: [%s][%s][%s] -> [%s]%n",
                    resrCust.getCode(), resrCust.getAddress(),
                    resrCust.getDescr(), member.getCzybh());
        }
        
    }

    private void dispatchToMemberInProportion(List<ResrCust> resrCusts,
            List<ResrCustPoolEmp> members, int quotient) {
        
        int index = 0, num = 0;
        
        for (ResrCustPoolEmp member : members) {
            num += member.getWeight() * quotient;
            
            for (int i = index; i < num; i++) {
                ResrCust resrCust = resrCusts.get(i);
                resrCust.setBusinessMan(member.getCzybh());
                
                sendMessageToCzyWhenGetResrCust(member.getCzybh(), resrCust);
                
                System.out.printf("派单P: [%s][%s][%s] -> [%s]%n",
                        resrCust.getCode(), resrCust.getAddress(),
                        resrCust.getDescr(), member.getCzybh());
            }
            
            index = num;
        }
    }
    
    private static class MemberDispatchComparator implements Comparator<ResrCustPoolEmp> {

        @Override
        public int compare(ResrCustPoolEmp o1, ResrCustPoolEmp o2) {
            return Integer.compare(o2.getResrCustNumber(), o1.getResrCustNumber());
        }
        
    }
    
    private void resetMemberResrCustNumber(List<ResrCustPoolEmp> members) {
        for (ResrCustPoolEmp member : members) {
            member.setResrCustNumber(member.getWeight());
        }
    }
    
    /**
     * 自定义派单
     * 
     * @param pool
     */
    private void dispatchUseCustomRules(ResrCustPool pool) {
        List<ResrCustPoolRule> dispatchRules = resrCustPoolDao.findDispatchRulesByPoolNo(pool.getNo());
        
        for (ResrCustPoolRule rule : dispatchRules) {
            dispatchByCustomizedRule(pool, rule);
        }
        
//        remindAdminsWhenReachMaxNoDispatchAlarmNumber(pool);
    }

    private void dispatchByCustomizedRule(ResrCustPool pool, ResrCustPoolRule rule) {
        List<ResrCust> undispatchedCusts = Collections.emptyList();
        
        if (rule.getScope().equals("1")) {
            undispatchedCusts = resrCustPoolDao.findUndispatchedResrCustsByPoolNo(pool.getNo());
        } else if (rule.getScope().equals("2")) {
            try {
                undispatchedCusts = resrCustPoolDao.findResrCustsByCustomSql(rule.getSql());
            } catch (Exception e) {
                undispatchedCusts = Collections.emptyList();
                e.printStackTrace();
            }
        }
        
        // 池到人
        if (rule.getType().equals("1")) {
            dispatchToDispatchCzyScope(pool, rule, undispatchedCusts);
            
        // 池到池
        } else if (rule.getType().equals("2")) {
            
            System.out.printf("派单：[%s]池 [自定义派单模式] [%s]规则 有[%4d]条待转移记录，开始转移到[%s]池%n",
                    pool.getDescr(), rule.getDescr(),
                    undispatchedCusts.size(), rule.getToResrCustPoolNo());
            
            for (ResrCust resrCust : undispatchedCusts) {
                resrCust.setResrCustPoolNo(rule.getToResrCustPoolNo());
                resrCustPoolDao.moveResrCustToAnotherPool(resrCust);
            }
        }
    }

    private void dispatchToDispatchCzyScope(ResrCustPool pool, ResrCustPoolRule rule,
            List<ResrCust> resrCusts) {
        List<ResrCustPoolRuleCzy> ruleCzys =
                resrCustPoolDao.findRuleCzysForDispatchByRulePk(rule.getPk());
        
        System.out.printf("派单：[%s]池 [自定义派单模式] [%s]规则 有[%4d]条待派单记录和[%4d]个成员，开始派单%n",
                pool.getDescr(), rule.getDescr(), resrCusts.size(), ruleCzys.size());
        
        if (ruleCzys.size() == 0) {
            return;
        }

        dispatchResrCustToRuleCzys(pool, resrCusts, ruleCzys);
    }

    private void dispatchResrCustToRuleCzys(ResrCustPool pool, List<ResrCust> resrCusts,
            List<ResrCustPoolRuleCzy> ruleCzys) {
        
        int weightSum = 0, resrCustNumberSum = 0;
        
        for (ResrCustPoolRuleCzy ruleCzy : ruleCzys) {
            ResrCustPoolEmp member =
                    resrCustPoolDao.findMemberByPoolNoAndCzybh(pool.getNo(), ruleCzy.getCzybh());
            
            weightSum += member.getWeight();
            resrCustNumberSum += ruleCzy.getResrCustNumber();
        }
        
        if (resrCusts.size() < resrCustNumberSum + weightSum) {
            dispatchToRuleCzyInDynamicWay(resrCusts, ruleCzys, pool);            
        } else {
            int size = resrCusts.size();
            int quotient = (size - resrCustNumberSum) / weightSum;
            int reminder = (size - resrCustNumberSum) % weightSum;
            
            dispatchToRuleCzyInProportion(resrCusts.subList(0, size - resrCustNumberSum - reminder), ruleCzys, quotient);
            dispatchToRuleCzyInDynamicWay(resrCusts.subList(size - resrCustNumberSum - reminder, size), ruleCzys, pool);
        }
        
        for (ResrCust resrCust : resrCusts)
            resrCustPoolDao.addBusinessManForResrCust(resrCust);
        
        if (resrCusts.size() > 0)
            for (ResrCustPoolRuleCzy ruleCzy : ruleCzys)
                resrCustPoolDao.updateRuleCzyResrCustNumber(ruleCzy);
        
    }
    
    private void dispatchToRuleCzyInDynamicWay(List<ResrCust> resrCusts,
            List<ResrCustPoolRuleCzy> ruleCzys, ResrCustPool pool) {
        
        for (ResrCust resrCust : resrCusts) {
            
            Collections.sort(ruleCzys, new RuleCzyDispatchComparator());
            
            ResrCustPoolRuleCzy ruleCzy = ruleCzys.get(0);
            int resrCustNumber = ruleCzy.getResrCustNumber();
            
            if (resrCustNumber == 0) {
                resetRuleCzysResrCustNumber(ruleCzys, pool);
                Collections.sort(ruleCzys, new RuleCzyDispatchComparator());
                ruleCzy = ruleCzys.get(0);
            }
            
            resrCust.setBusinessMan(ruleCzy.getCzybh());
            
            ruleCzy.setResrCustNumber(ruleCzy.getResrCustNumber() - 1);
            
            sendMessageToCzyWhenGetResrCust(ruleCzy.getCzybh(), resrCust);
            
            System.out.printf("派单D: [%s][%s][%s] -> [%s]%n",
                    resrCust.getCode(), resrCust.getAddress(),
                    resrCust.getDescr(), ruleCzy.getCzybh());
        }
        
    }

    private void dispatchToRuleCzyInProportion(List<ResrCust> resrCusts,
            List<ResrCustPoolRuleCzy> ruleCzys, int quotient) {
        
        int index = 0, num = 0;
        
        for (ResrCustPoolRuleCzy ruleCzy : ruleCzys) {
            num += ruleCzy.getWeight() * quotient;
            
            for (int i = index; i < num; i++) {
                ResrCust resrCust = resrCusts.get(i);
                resrCust.setBusinessMan(ruleCzy.getCzybh());
                
                sendMessageToCzyWhenGetResrCust(ruleCzy.getCzybh(), resrCust);
                
                System.out.printf("派单P: [%s][%s][%s] -> [%s]%n",
                        resrCust.getCode(), resrCust.getAddress(),
                        resrCust.getDescr(), ruleCzy.getCzybh());
            }
            
            index = num;
        }
    }

    private static class RuleCzyDispatchComparator implements Comparator<ResrCustPoolRuleCzy> {

        @Override
        public int compare(ResrCustPoolRuleCzy o1, ResrCustPoolRuleCzy o2) {
            return Integer.compare(o2.getResrCustNumber(), o1.getResrCustNumber());
        }
        
    }
    
    private void resetRuleCzysResrCustNumber(List<ResrCustPoolRuleCzy> ruleCzys,
            ResrCustPool pool) {
        
        for (ResrCustPoolRuleCzy ruleCzy : ruleCzys) {
            ResrCustPoolEmp member =
                    resrCustPoolDao.findMemberByPoolNoAndCzybh(pool.getNo(), ruleCzy.getCzybh());
            
            ruleCzy.setResrCustNumber(member.getWeight());
        }
    }
    
    private void sendMessageToCzyWhenGetResrCust(String czybh, ResrCust resrCust) {
        
        PersonMessage personMessage = new PersonMessage();
        
        personMessage.setMsgType("19");
        personMessage.setMsgText("你收到1条线索，" + resrCust.getAddress() + "的" + resrCust.getDescr());
        personMessage.setCrtDate(new Date());
        personMessage.setSendDate(new Date());
        personMessage.setRcvType("3");
        personMessage.setRcvCzy(czybh);
        personMessage.setRcvStatus("0");
        personMessage.setIsPush("0");
        personMessage.setPushStatus("1");

        save(personMessage);
        
        Employee employee = get(Employee.class, czybh);
        
        Notice notice = new Notice();
        notice.setId(employee.getPhone());
        notice.setTitle(personMessage.getMsgText());
        TestJpushClient.doPushEmployee(notice);
        
        // TODO 后续要加短信推送功能
    }
    
    private void remindAdminsWhenReachMaxNoDispatchAlarmNumber(ResrCustPool pool) {
        long noDispatchCount = resrCustPoolDao.findNoDispatchResrCustCountByPoolNo(pool.getNo());
        
        if (pool.getMaxNoDispatchAlarmNumber() == 0 
                || noDispatchCount <= pool.getMaxNoDispatchAlarmNumber())
            return;
        
        List<ResrCustPoolEmp> admins = resrCustPoolDao.findAdminsByPoolNo(pool.getNo());
        
        for (ResrCustPoolEmp admin : admins) {
            PersonMessage personMessage = new PersonMessage();
            personMessage.setMsgType("19");
            personMessage.setMsgText(pool.getDescr() + "存在大量待分配资源，请及时处理");
            personMessage.setCrtDate(new Date());
            personMessage.setSendDate(new Date());
            personMessage.setRcvType("1");
            personMessage.setRcvCzy(admin.getCzybh());
            personMessage.setRcvStatus("0");
            personMessage.setIsPush("1");
            personMessage.setPushStatus("0");
            
            save(personMessage);
        }
    }
    
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Object test() {
        List<ResrCustPool> pools = resrCustPoolDao.findAllPools();
        System.out.println(pools);
        
        ResrCustPool pool = get(ResrCustPool.class, "RCP0000035");
        pool.setExpired("F");
        save(pool);
        
        flush();
        
//        hibernateTemplate.setFlushMode(4);
        
//        resrCustPoolDao.expirePool("RCP0000035", "F");
        
        pools = resrCustPoolDao.findAllPools();
        System.out.println(pools);
        
        return hibernateTemplate.getFlushMode();
    }

}
