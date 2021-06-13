package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;

import com.house.home.entity.design.ResrCustPool;
import com.house.home.entity.design.ResrCustPoolEmp;
import com.house.home.entity.design.ResrCustPoolRule;

public interface ResrCustPoolService extends BaseService{

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool, UserContext userContext);

    Page<Map<String, Object>> findAdminPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool);

    Page<Map<String, Object>> findMemberPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool);

    Page<Map<String, Object>> findDispatchPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool);
    
    Page<Map<String, Object>> findDispatchMemberPageBySql(Page<Map<String, Object>> page,
            ResrCustPoolRule resrCustPoolRule);

    Page<Map<String, Object>> findRecoverPageBySql(Page<Map<String, Object>> page,
            ResrCustPool resrCustPool);
    
    void saveRuleOrder(int[] pks);

    void deletePoolRuleByPk(int pk);

    void saveDispatch(ResrCustPoolRule resrCustPoolRule);

    void updateDispatch(ResrCustPoolRule resrCustPoolRule);
    
    ResrCustPoolEmp findAdminByPoolNoAndCzybh(String poolNo, String czybh);
    
    ResrCustPoolEmp findMemberByPoolNoAndCzybh(String poolNo, String czybh);

    void setMembersWeight(int[] pks, int weight);

    void setMembersGroupSign(int[] pks, String groupSign);

    void doImportMemberExcel(List<ResrCustPoolEmp> poolEmps, String czybh);

    void doClear(ResrCustPool resrCustPool);

    void doSetMemberDefaultPool(String[] czybhs, String poolNo);

    void switchMemberState(List<ResrCustPoolEmp> emps);

    void doDeleteMembers(String resrCustPoolNo, String pks);

    void doSaveMember(ResrCustPoolEmp resrCustPoolEmp);

    void doUpdateMember(ResrCustPoolEmp resrCustPoolEmp);

    /**
     * 线索池回收
     */
    void recover();

    /**
     * 线索池派单
     */
    void dispatch();
    
    Object test();

}
