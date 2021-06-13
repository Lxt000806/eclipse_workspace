package com.house.home.web.controller.design;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.Czybm;
import com.house.home.entity.design.ResrCustPool;
import com.house.home.entity.design.ResrCustPoolEmp;
import com.house.home.entity.design.ResrCustPoolRule;
import com.house.home.entity.design.ResrCustPoolRuleCzy;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.design.ResrCustPoolService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/resrCustPool")
public class ResrCustPoolController extends BaseController {
    
    @Autowired
    private ResrCustPoolService resrCustPoolService;
    
    @Autowired
    private CzybmService czybmService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) {
        
        return new ModelAndView("admin/design/resrCustPool/resrCustPool_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request,
            HttpServletResponse response, ResrCustPool resrCustPool) {
        
        resrCustPool.setAdminType(checkAdminType(request));

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        resrCustPoolService.findPageBySql(page, resrCustPool, getUserContext(request));
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/admin/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goAdminJqGrid(HttpServletRequest request,
            HttpServletResponse response, ResrCustPool resrCustPool) {
                
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        
        if (StringUtils.isNotBlank(resrCustPool.getNo())) {            
            resrCustPoolService.findAdminPageBySql(page, resrCustPool);
        }
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/member/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goMemberJqGrid(HttpServletRequest request,
            HttpServletResponse response, ResrCustPool resrCustPool) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        
        if (StringUtils.isNotBlank(resrCustPool.getNo())) {            
            resrCustPoolService.findMemberPageBySql(page, resrCustPool);
        }
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    private String checkAdminType(HttpServletRequest request) {
        UserContext userContext = getUserContext(request);
        
        if ("ADMIN".equals(userContext.getJslx()))
            return "CZYBM_ADMIN";
        else if (czybmService.hasGNQXByCzybh(userContext.getCzybh(), "0227", "高级管理员"))
            return "RESR_CUST_POOL_MODULE_ADMIN";
        else
            return "";
    }
    
    @RequestMapping("/dispatch/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goDispatchJqGrid(HttpServletRequest request,
            HttpServletResponse response, ResrCustPool resrCustPool) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        
        if (StringUtils.isNotBlank(resrCustPool.getNo())) {            
            resrCustPoolService.findDispatchPageBySql(page, resrCustPool);
        }
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/dispatch/member/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goDispatchMemberJqGrid(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        
        if (resrCustPoolRule.getPk() != null) {            
            resrCustPoolService.findDispatchMemberPageBySql(page, resrCustPoolRule);
        }
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/recover/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goRecoverJqGrid(HttpServletRequest request,
            HttpServletResponse response, ResrCustPool resrCustPool) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        
        if (StringUtils.isNotBlank(resrCustPool.getNo())) {            
            resrCustPoolService.findRecoverPageBySql(page, resrCustPool);
        }
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request,
            HttpServletResponse response) {
        
        ResrCustPool resrCustPool = new ResrCustPool();
        resrCustPool.setM_umState("A");
        resrCustPool.setNo(resrCustPoolService.getSeqNo("tResrCustPool"));
        resrCustPool.setType("2");
        resrCustPool.setIsVirtualPhone("0");
        resrCustPool.setIsHideChannel("0");
        resrCustPool.setReceiveRule("1");
        resrCustPool.setDispatchRule("0");
        resrCustPool.setRecoverRule("0");
        resrCustPool.setMaxValidResrCustNumber(0);
        resrCustPool.setMaxNoDispatchAlarmNumber(0);
        resrCustPool.setPriority(0);
        
        ResrCustPoolEmp admin = new ResrCustPoolEmp();
        admin.setResrCustPoolNo(resrCustPool.getNo());
        admin.setType("0");
        admin.setCzybh(getUserContext(request).getCzybh());
        admin.setWeight(0);
        admin.setResrCustNumber(0);
        admin.setDispSeq(0);
        admin.setActionLog("ADD");
        admin.setLastUpdate(new Date());
        admin.setLastUpdatedBy(getUserContext(request).getCzybh());
        admin.setExpired("F");
        resrCustPoolService.save(admin);
        
        return new ModelAndView("admin/design/resrCustPool/resrCustPool_update")
                .addObject("resrCustPool", resrCustPool);
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request,
            HttpServletResponse response, ResrCustPool resrCustPool) {
        try {
            
            resrCustPool.setActionLog("ADD");
            resrCustPool.setLastUpdate(new Date());
            resrCustPool.setLastUpdatedBy(getUserContext(request).getCzybh());
            resrCustPool.setExpired("F");
            
            resrCustPoolService.save(resrCustPool);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "新增线索池失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/doClear")
    public void doClear(HttpServletRequest request,
            HttpServletResponse response, ResrCustPool resrCustPool) {
        try {
            
            resrCustPoolService.doClear(resrCustPool);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "清理线索池失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/goCopy")
    public ModelAndView goCopy(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        ResrCustPool resrCustPool = resrCustPoolService.get(ResrCustPool.class, no);
        resrCustPool.setM_umState("C");
        resrCustPool.setDescr(null);
        
        return new ModelAndView("admin/design/resrCustPool/resrCustPool_update")
                .addObject("resrCustPool", resrCustPool);
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        ResrCustPool resrCustPool = resrCustPoolService.get(ResrCustPool.class, no);
        resrCustPool.setM_umState("M");
        
        return new ModelAndView("admin/design/resrCustPool/resrCustPool_update")
                .addObject("resrCustPool", resrCustPool);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, ResrCustPool resrCustPool) {
        try {
            
            resrCustPool.setActionLog("EDIT");
            resrCustPool.setLastUpdate(new Date());
            resrCustPool.setLastUpdatedBy(getUserContext(request).getCzybh());
            
            resrCustPoolService.update(resrCustPool);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "编辑线索池失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        ResrCustPool resrCustPool = resrCustPoolService.get(ResrCustPool.class, no);
        resrCustPool.setM_umState("V");
        
        return new ModelAndView("admin/design/resrCustPool/resrCustPool_update")
                .addObject("resrCustPool", resrCustPool);
    }
    
    @RequestMapping("/goPoolEmp")
    public ModelAndView goPoolEmp(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        ResrCustPool resrCustPool = resrCustPoolService.get(ResrCustPool.class, no);
        
        return new ModelAndView("admin/design/resrCustPool/resrCustPool_poolEmp")
                .addObject("resrCustPool", resrCustPool);
    }
    
    @RequestMapping("/admin/goAdd")
    public ModelAndView goAddAdmin(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolEmp resrCustPoolEmp) {
        
        resrCustPoolEmp.setM_umState("A");
        resrCustPoolEmp.setType("0");
        resrCustPoolEmp.setWeight(0);
        resrCustPoolEmp.setDispSeq(0);
        
        return new ModelAndView("admin/design/resrCustPool/tab_admin_add")
                .addObject("userContext", getUserContext(request));
    }
    
    @RequestMapping("/admin/doAdd")
    public void doAddAdmin(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolEmp resrCustPoolEmp) {
        try {
            
            ResrCustPoolEmp admin = resrCustPoolService.findAdminByPoolNoAndCzybh(
                    resrCustPoolEmp.getResrCustPoolNo(), resrCustPoolEmp.getCzybh());
            
            if (admin != null) {
                ServletUtils.outPrintFail(request, response, "新增管理员失败：此管理员已存在！");
                return;
            }
            
            resrCustPoolEmp.setResrCustNumber(0);
            resrCustPoolEmp.setActionLog("ADD");
            resrCustPoolEmp.setLastUpdate(new Date());
            resrCustPoolEmp.setLastUpdatedBy(getUserContext(request).getCzybh());
            resrCustPoolEmp.setExpired("F");
            
            resrCustPoolService.save(resrCustPoolEmp);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "新增管理员失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/admin/doDelete")
    public void doDeleteAdmin(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolEmp resrCustPoolEmp) {
        try {
            resrCustPoolService.delete(resrCustPoolEmp);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "删除管理员失败！");
            e.printStackTrace();
        }
    }

    @RequestMapping("/member/goAdd")
    public ModelAndView goAddMember(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolEmp resrCustPoolEmp) {
        
        resrCustPoolEmp.setM_umState("A");
        resrCustPoolEmp.setType("1");
        resrCustPoolEmp.setWeight(1);
        resrCustPoolEmp.setDispSeq(0);
        
        return new ModelAndView("admin/design/resrCustPool/tab_member_update")
                .addObject("resrCustPoolEmp", resrCustPoolEmp);
    }
    
    @RequestMapping("/member/doAdd")
    public void doAddMember(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolEmp resrCustPoolEmp) {
        try {
            
            ResrCustPoolEmp member = resrCustPoolService.findMemberByPoolNoAndCzybh(
                    resrCustPoolEmp.getResrCustPoolNo(), resrCustPoolEmp.getCzybh());
            
            if (member != null) {
                ServletUtils.outPrintFail(request, response, "新增成员失败：此成员已存在！");
                return;
            }
            
            resrCustPoolEmp.setActionLog("ADD");
            resrCustPoolEmp.setLastUpdate(new Date());
            resrCustPoolEmp.setLastUpdatedBy(getUserContext(request).getCzybh());
            resrCustPoolEmp.setExpired("F");
            
            resrCustPoolService.doSaveMember(resrCustPoolEmp);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "新增成员失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/member/goUpdate")
    public ModelAndView goUpdateMember(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolEmp resrCustPoolEmp) {
        
        resrCustPoolEmp =
                resrCustPoolService.get(ResrCustPoolEmp.class, resrCustPoolEmp.getPk());
        
        resrCustPoolEmp.setM_umState("M");
        
        return new ModelAndView("admin/design/resrCustPool/tab_member_update")
                .addObject("resrCustPoolEmp", resrCustPoolEmp);
    }
    
    @RequestMapping("/member/doUpdate")
    public void doUpdateMember(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolEmp resrCustPoolEmp) {
        try {
            
            ResrCustPoolEmp member = resrCustPoolService.findMemberByPoolNoAndCzybh(
                    resrCustPoolEmp.getResrCustPoolNo(), resrCustPoolEmp.getCzybh());
            
            if (member != null && member.getPk() != resrCustPoolEmp.getPk()) {
                ServletUtils.outPrintFail(request, response, "编辑成员失败：此成员已存在！");
                return;
            }
            
            resrCustPoolEmp.setActionLog("EDIT");
            resrCustPoolEmp.setLastUpdate(new Date());
            resrCustPoolEmp.setLastUpdatedBy(getUserContext(request).getCzybh());
            
            resrCustPoolService.doUpdateMember(resrCustPoolEmp);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "编辑成员失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/member/goImport")
    public ModelAndView goImportMember(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolEmp resrCustPoolEmp) {
        
        return new ModelAndView("admin/design/resrCustPool/tab_member_import")
                .addObject("resrCustPoolEmp", resrCustPoolEmp);
    }
    
    @RequestMapping("/member/loadExcel")
    @ResponseBody
    public Map<String, Object> loadMemberExcel(HttpServletRequest request,
            HttpServletResponse response) {
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        try {
            MultipartFormData multipartFormData = new MultipartFormData(request);
            String poolNo = multipartFormData.getParameter("resrCustPoolNo");
            FileItem excelFile = multipartFormData.getFileItem();
            
            if (excelFile == null) {
                result.put("success", false);
                result.put("info", "未读取到上传的文件！");
                return result;
            }
            
            InputStream inputStream = excelFile.getInputStream();
            ExcelImportUtils<ResrCustPoolEmp> excelImportUtils =
                    new ExcelImportUtils<ResrCustPoolEmp>();
            
            List<ResrCustPoolEmp> instances =
                    excelImportUtils.importExcelNew(inputStream, ResrCustPoolEmp.class,
                            Arrays.asList("操作员编号", "权重", "组标签"));
            
            List<Map<String, Object>> records = new ArrayList<Map<String,Object>>();
            List<String> czybhs = new ArrayList<String>();
            
            for (ResrCustPoolEmp instance : instances) {
                Map<String, Object> parsedRecord = new HashMap<String, Object>();
                parsedRecord.put("isvalid", true);
                parsedRecord.put("info", "");
                parsedRecord.put("resrCustPoolNo", poolNo);
                parsedRecord.put("weight", instance.getWeight());
                parsedRecord.put("groupSign", instance.getGroupSign());
                
                if (StringUtils.isNotBlank(instance.getCzybh())) {
                    parsedRecord.put("czybh", instance.getCzybh());
                    
                    Czybm czybm = resrCustPoolService.get(Czybm.class, instance.getCzybh());
                    if (czybm != null) {
                        parsedRecord.put("zwxm", czybm.getZwxm());
                        
                        ResrCustPoolEmp poolEmp =
                                resrCustPoolService.findMemberByPoolNoAndCzybh(poolNo, instance.getCzybh());
                        if (poolEmp != null) {
                            parsedRecord.put("isvalid", false);
                            parsedRecord.put("info", parsedRecord.get("info") + " 线索池已存在此操作员");
                        }
                        
                        if (czybhs.indexOf(instance.getCzybh()) >= 0) {
                            parsedRecord.put("isvalid", false);
                            parsedRecord.put("info", parsedRecord.get("info") + " Excel中已存在此操作员");
                        }
                        
                        czybhs.add(instance.getCzybh());
                        
                    } else {
                        parsedRecord.put("isvalid", false);
                        parsedRecord.put("info", parsedRecord.get("info") + " 操作员编号不存在");
                    }
                    
                } else {
                    parsedRecord.put("isvalid", false);
                    parsedRecord.put("info", parsedRecord.get("info") + " 操作员编号不能为空");
                }
                
                if ((Boolean) parsedRecord.get("isvalid")) {
                    parsedRecord.put("info", "有效");
                } else {
                    parsedRecord.put("info", "无效：" + parsedRecord.get("info"));
                }
                
                records.add(parsedRecord);
            }
            
            result.put("success", true);
            result.put("records", records);
        } catch (Exception e) {
            result.put("success", false);
            result.put("info", "当前操作使用了错误类型的值，请检查数值列是否包含非法字符！");
            e.printStackTrace();
        }
        
        return result;
    }
    
    @RequestMapping("/member/doImportExcel")
    public void doImportMemberExcel(HttpServletRequest request,
            HttpServletResponse response, String records) {
        
        try {
            List<ResrCustPoolEmp> poolEmps = JSON.parseArray(records, ResrCustPoolEmp.class);
            
            resrCustPoolService.doImportMemberExcel(poolEmps, getUserContext(request).getCzybh());
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, e.getCause());
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/member/goSetWeight")
    public ModelAndView goSetMemberWeight(HttpServletRequest request,
            HttpServletResponse response, String pks) {
        
        return new ModelAndView("admin/design/resrCustPool/tab_member_setWeight")
                .addObject("pks", pks);
    }
    
    @RequestMapping("/member/doSetWeight")
    public void doSetMemberWeight(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("pks[]") int[] pks, int weight) {
        try {
            resrCustPoolService.setMembersWeight(pks, weight);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "设置成员权重失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/member/goSetGroupSign")
    public ModelAndView goSetMemberGroupSign(HttpServletRequest request,
            HttpServletResponse response, String pks) {
                
        return new ModelAndView("admin/design/resrCustPool/tab_member_setGroupSign")
                .addObject("pks", pks);
    }
    
    @RequestMapping("/member/doSetGroupSign")
    public void doSetMemberGroupSign(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("pks[]") int[] pks, String groupSign) {
        try {
            resrCustPoolService.setMembersGroupSign(pks, groupSign);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "设置成员标签失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/member/doDelete")
    public void doDeleteMember(HttpServletRequest request, HttpServletResponse response,
            String resrCustPoolNo, String pks) {
        
        try {
            
            resrCustPoolService.doDeleteMembers(resrCustPoolNo, pks);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "删除成员失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/member/doSwitchState")
    public void doSwitchMemberState(HttpServletRequest request, HttpServletResponse response,
            String poolNo, @RequestBody List<Map<String, String>> poolEmps) {
        try {
            List<ResrCustPoolEmp> emps = new ArrayList<ResrCustPoolEmp>();
            
            for (Map<String, String> poolEmp : poolEmps) {
                ResrCustPoolEmp emp = new ResrCustPoolEmp();
                
                emp.setCzybh(poolEmp.get("czybh"));
                emp.setOnLeave(Integer.valueOf(poolEmp.get("onleave")));
                emp.setLastUpdatedBy(getUserContext(request).getCzybh());
                
                emps.add(emp);
            }
            
            resrCustPoolService.switchMemberState(emps);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "切换状态失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/member/doSetDefaultPool")
    public void doSetDefaultPool(HttpServletRequest request, HttpServletResponse response,
            String poolNo, @RequestParam("czybhs[]") String[] czybhs) {
        try {
            
            resrCustPoolService.doSetMemberDefaultPool(czybhs, poolNo);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "设置默认线索池失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/dispatch/goAdd")
    public ModelAndView goAddDispatch(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        
        resrCustPoolRule.setM_umState("A");
        resrCustPoolRule.setRuleClass("1");
        resrCustPoolRule.setDescr("");
        resrCustPoolRule.setType("1");
        resrCustPoolRule.setScope("1");
        resrCustPoolRule.setDispatchCZYScope("1");
        resrCustPoolRule.setDispSeq(0);

        return new ModelAndView("admin/design/resrCustPool/tab_dispatch_update")
                .addObject("resrCustPoolRule", resrCustPoolRule);
    }
    
    @RequestMapping("/dispatch/doAdd")
    public void doAddDispatch(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        try {
            resrCustPoolRule.setActionLog("ADD");
            resrCustPoolRule.setLastUpdate(new Date());
            resrCustPoolRule.setLastUpdatedBy(getUserContext(request).getCzybh());
            resrCustPoolRule.setExpired("F");
            
            resrCustPoolService.saveDispatch(resrCustPoolRule);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "新增派单规则失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/dispatch/goUpdate")
    public ModelAndView goUpdateDispatch(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        
        resrCustPoolRule =
                resrCustPoolService.get(ResrCustPoolRule.class, resrCustPoolRule.getPk());
        
        resrCustPoolRule.setM_umState("M");

        return new ModelAndView("admin/design/resrCustPool/tab_dispatch_update")
                .addObject("resrCustPoolRule", resrCustPoolRule);
    }
    
    @RequestMapping("/dispatch/doUpdate")
    public void doUpdateDispatch(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        try {
            resrCustPoolRule.setActionLog("EDIT");
            resrCustPoolRule.setLastUpdate(new Date());
            resrCustPoolRule.setLastUpdatedBy(getUserContext(request).getCzybh());
            
            resrCustPoolService.updateDispatch(resrCustPoolRule);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "编辑派单规则失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/dispatch/doDelete")
    public void doDeleteDispatch(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        try {
            resrCustPoolService.deletePoolRuleByPk(resrCustPoolRule.getPk());
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "删除派单规则失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/dispatch/saveOrder")
    public void saveDispatchOrder(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("pks[]") int[] pks) {
        try {
            
            resrCustPoolService.saveRuleOrder(pks);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "保存规则顺序失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/dispatch/member/goAdd")
    public ModelAndView goAddDispatchMember(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {

        return new ModelAndView("admin/design/resrCustPool/tab_dispatch_member_add")
                .addObject("resrCustPoolRule", resrCustPoolRule);
    }
    
    @RequestMapping("/dispatch/member/goUpdate")
    public ModelAndView goUpdateDispatchMember(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRuleCzy resrCustPoolRuleCzy) {

        return new ModelAndView("admin/design/resrCustPool/tab_dispatch_member_update")
                .addObject("resrCustPoolRule", resrCustPoolRuleCzy);
    }
    
    @RequestMapping("/recover/goAdd")
    public ModelAndView goAddRecover(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        
        resrCustPoolRule.setM_umState("A");
        
        return new ModelAndView("admin/design/resrCustPool/tab_recover_update")
                .addObject("resrCustPoolRule", resrCustPoolRule);
    }
    
    @RequestMapping("/recover/doAdd")
    public void doAddRecover(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        try {
            resrCustPoolRule.setRuleClass("2");
            resrCustPoolRule.setDispSeq(0);
            resrCustPoolRule.setExpired("F");
            resrCustPoolRule.setActionLog("ADD");
            resrCustPoolRule.setLastUpdate(new Date());
            resrCustPoolRule.setLastUpdatedBy(getUserContext(request).getCzybh());
            
            resrCustPoolService.save(resrCustPoolRule);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "新增回收规则失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/recover/goUpdate")
    public ModelAndView goUpdateRecover(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        
        resrCustPoolRule = resrCustPoolService.get(ResrCustPoolRule.class, resrCustPoolRule.getPk());
        resrCustPoolRule.setM_umState("M");
        
        return new ModelAndView("admin/design/resrCustPool/tab_recover_update")
                .addObject("resrCustPoolRule", resrCustPoolRule);
    }
    
    @RequestMapping("/recover/doUpdate")
    public void doUpdateRecover(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        try {
            resrCustPoolRule.setActionLog("EDIT");
            resrCustPoolRule.setLastUpdate(new Date());
            resrCustPoolRule.setLastUpdatedBy(getUserContext(request).getCzybh());
            
            resrCustPoolService.update(resrCustPoolRule);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "更新回收规则失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/recover/doDelete")
    public void doDeleteRecover(HttpServletRequest request,
            HttpServletResponse response, ResrCustPoolRule resrCustPoolRule) {
        try {            
            resrCustPoolService.delete(resrCustPoolRule);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "删除回收规则失败！");
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/recover/saveOrder")
    public void saveRecoverOrder(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("pks[]") int[] pks) {
        try {
            
            resrCustPoolService.saveRuleOrder(pks);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "保存规则顺序失败！");
            e.printStackTrace();
        }
    }

}
