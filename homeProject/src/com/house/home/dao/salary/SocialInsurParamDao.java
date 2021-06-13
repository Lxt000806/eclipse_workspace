package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.home.entity.salary.SocialInsurParam;

@SuppressWarnings("serial")
@Repository
public class SocialInsurParamDao extends BaseDao {

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
            SocialInsurParam socialInsurParam) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		" select a.PK, a.Descr, " +
        		" a.EdmInsurBaseMin, a.EdmInsurBaseMax, a.EdmInsurIndRate, a.EdmInsurCmpRate, " +
        		" a.UneInsurIndRate, a.UneInsurCmpRate, " +
        		" a.MedInsurBaseMin, a.MedInsurBaseMax, a.MedInsurIndRate, a.MedInsurCmpRate, " +
        		" a.BirthInsurCmpRate, " +
        		" a.HouFundBaseMin, a.HouFundBaseMax, a.HouFundIndRate, a.HouFundCmpRate, " +
        		" a.Remarks, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, a.BirthInsurBaseMin," +
        		" a.InjuryInsurRate,a.InjuryInsurBaseMin " +
        		" from tSocialInsurParam a " +
        		" where 1 = 1 ";

        if (StringUtils.isBlank(socialInsurParam.getExpired()) || "F".equals(socialInsurParam.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.LastUpdate desc";
        }

        return this.findPageBySql(page, sql, parameters.toArray());
    }

    public List<SocialInsurParam> findSocialInsurParamsByDescr(String socialInsurParamDescr) {
        String sql = "select * from tSocialInsurParam where Descr = ?";
        
        List<Map<String, Object>> socialInsurParams = findBySql(sql, socialInsurParamDescr);
        
        return BeanConvertUtil.mapToBeanList(socialInsurParams, SocialInsurParam.class);
    }
}

