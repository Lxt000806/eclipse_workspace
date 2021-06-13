package com.house.home.entity.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * 团队新增表单
 * @author lenovo-l729
 *
 */
public class TeamForm {
    
    private String code;
    
    private String desc1;
    
    private String remark;

    private ArrayList<Department1> dept1List;
    
    private ArrayList<Department2> dept2List;
    
    private ArrayList<Department3> dept3List;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Department1> getDept1List() {
        return dept1List;
    }

    public void setDept1List(ArrayList<Department1> dept1List) {
        this.dept1List = dept1List;
    }

    public ArrayList<Department2> getDept2List() {
        return dept2List;
    }

    public void setDept2List(ArrayList<Department2> dept2List) {
        this.dept2List = dept2List;
    }

    public ArrayList<Department3> getDept3List() {
        return dept3List;
    }

    public void setDept3List(ArrayList<Department3> dept3List) {
        this.dept3List = dept3List;
    }
}
