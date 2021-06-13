package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Applicant;

@Repository
public class ApplicantDao extends BaseDao{

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Applicant applicant) {
        List<Object> list = new ArrayList<Object>();

        String sql = "select a.pk pk, a.NameChi nameChi, a.Source source, a.Remarks remarks,"
                + " a.Gender gender, d.Note genderDescr, a.Phone phone, a.DeptDescr deptDescr,"
                + " a.Dept1Descr dept1Descr, a.PositionDescr positionDescr, a.AppDate appDate,"
                + " a.Status status, b.NOTE statusDescr, a.ComeInDate comeInDate, a.BirtPlace birtPlace, a.IDNum IDNum,"
                + " a.birth birth, FLOOR(datediff(DY,birth,getdate())/365.25) age, a.Address address, a.Edu edu, c.NOTE eduDescr, a.School school, a.SchDept schDept, a.LastUpdate lastUpdate,"
                + " a.LastUpdatedBy lastUpdatedBy, a.Expired expired, a.ActionLog actionLog"
                + " from tApplicant a"
                + " left outer join tXTDM b on a.Status = b.CBM and b.ID = 'APPLICANTSTS'"
                + " left outer join tXTDM c on a.edu = c.CBM and c.ID = 'EDU'"
                + " left outer join tXTDM d on a.Gender = d.CBM and d.ID = 'GENDER' "
                + " where 1 = 1";

        if (applicant.getPk() != null) {//根据序号主键查询
            sql += " and a.PK = ?";
            list.add(applicant.getPk());    
        }
        if (StringUtils.isNotBlank(applicant.getNameChi())) {//根据姓名查询
            sql += " and a.NameChi like ?";
            list.add("%" + applicant.getNameChi() + "%");
        }
        if(StringUtils.isNotBlank(applicant.getPhone())){//根据电话查询
            sql += " and a.Phone like ?";
            list.add("%" + applicant.getPhone() + "%");
        }
        if(StringUtils.isNotBlank(applicant.getPositionDescr())){//根据面试岗位查询
            sql += " and a.PositionDescr like ?";
            list.add("%" + applicant.getPositionDescr() + "%");
        }
        if(StringUtils.isNotBlank(applicant.getStatus())){//根据状态查询
            sql += " and a.Status = ?";
            list.add(applicant.getStatus());
        }
        if(StringUtils.isNotBlank(applicant.getDeptDescr())){//根据应聘部门查询
            sql += " and a.DeptDescr like ?";
            list.add("%" + applicant.getDeptDescr() + "%");
        }
        if(StringUtils.isNotBlank(applicant.getSource())){//根据途径查询
            sql += " and a.Source like ?";
            list.add("%" + applicant.getSource() + "%");
        }
        if(applicant.getDateTo() != null){//根据最早应聘时间查询
            sql += " and a.AppDate >= ?";
            list.add(applicant.getDateTo());
        }
        if(applicant.getDateFrom() != null){//根据最晚应聘时间查询
            sql += " and a.AppDate <= ?";
            list.add(applicant.getDateFrom());
        }
        if(applicant.getEarliestComeInDate() != null){//根据最早预入时间查询
            sql += " and a.ComeInDate >= ?";
            list.add(applicant.getEarliestComeInDate());
        }
        if(applicant.getLastestComeInDate() != null){//根据最晚预入时间查询
            sql += " and a.ComeInDate <= ?";
            list.add(applicant.getLastestComeInDate());
        }
        if(StringUtils.isBlank(applicant.getExpired()) || "F".equals(applicant.getExpired())){//隐藏过期
            sql += " and a.Expired = 'F'";
        }
        if(StringUtils.isNotBlank(applicant.getOthersEntering()) && "F".equals(applicant.getOthersEntering())){//只查看本人录入
            sql += " and a.LastUpdatedBy = ?";
            list.add(applicant.getLastUpdatedBy());
        }
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
            sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
        }else{
            sql += " order by a.PK desc";
        }

        return this.findPageBySql(page, sql, list.toArray());
    }
    
    public Map<String, Object> findMapByCode(String code) {
        String sql = "select a.PK, a.NameChi, a.Gender, a.Phone, a.DeptDescr, a.Dept1Descr, a.PositionDescr, a.AppDate,"
              + " a.ComeInDate, a.Status, a.BirtPlace, a.IDNum, a.birth, a.Address, a.Edu, a.School, x3.note StatusDescr,"
              + " a.SchDept, a.Source, a.Remarks, a.LastUpdatedBy, a.Expired, a.ActionLog, x1.note GenderDescr,x2.note eduDescr , "
              + " a.LastUpdate,datediff(year,a.birth,getdate()) age "
              + " from tApplicant a"
              + " left outer join tXTDM x1 on x1.cbm = a.Gender and x1.ID = 'GENDER' "
              + " left outer join tXTDM x2 on x2.cbm= a.Edu and x2.ID = 'EDU'  "
              + " left outer join txtdm x3 on x3.cbm=a.status and x3.id='APPLICANTSTS' "
              + " where a.pk=? ";
        List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
        if (list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
    
    public List<Applicant> getByIdNum(String idNum){
        String hql = "from Applicant where idNum = ?";
        List<Applicant> applicantList = find(hql, idNum);
        return applicantList;
    }
}
