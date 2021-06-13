package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.CustTypeGroup;

@Repository
@SuppressWarnings("serial")
public class CustTypeGroupDao extends BaseDao {

    public Page<Map<String, Object>> findGroupPageBySql(Page<Map<String, Object>> page,
            CustTypeGroup custTypeGroup) {

        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.No, a.Descr, a.Remarks, "
                + "    a.LastUpdatedBy, a.LastUpdate, a.Expired, a.ActionLog "
                + "from tCustTypeGroup a "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(custTypeGroup.getNo())) {
            sql += "and a.No = ? ";
            parameters.add(custTypeGroup.getNo());
        }
        
        if (StringUtils.isNotBlank(custTypeGroup.getDescr())) {
            sql += "and a.Descr like ? ";
            parameters.add("%" + custTypeGroup.getDescr() + "%");
        }
        
        if (StringUtils.isBlank(custTypeGroup.getExpired()) || "F".equals(custTypeGroup.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.LastUpdate desc";
        }

		return this.findPageBySql(page, sql, parameters.toArray());
	}

    public Page<Map<String, Object>> findGroupDetailPageBySql(Page<Map<String, Object>> page,
            CustTypeGroup custTypeGroup) {

        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.PK, a.No, a.CustType, b.Desc1 CustTypeDescr, "
                + "    a.LastUpdatedBy, a.LastUpdate, a.Expired, a.ActionLog "
                + "from tCustTypeGroupDt a "
                + "left join tCusttype b on a.CustType = b.Code "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(custTypeGroup.getNo())) {
            sql += "and a.No = ? ";
            parameters.add(custTypeGroup.getNo());
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.CustType";
        }

        return this.findPageBySql(page, sql, parameters.toArray());
    }

    @SuppressWarnings("deprecation")
    public Result saveByProcedure(CustTypeGroup custTypeGroup) {
        Assert.notNull(custTypeGroup);
        Result result = new Result();
        Connection conn = null;
        CallableStatement call = null;
        try {
            HibernateTemplate hibernateTemplate =
                    SpringContextHolder.getBean("hibernateTemplate");
            Session session = SessionFactoryUtils.getSession(
                    hibernateTemplate.getSessionFactory(), true);
            conn = session.connection();
            
            call = conn.prepareCall("{Call pCustTypeGroup(?,?,?,?,?,?,?,?,?,?,?)}");
            call.setString(1, custTypeGroup.getM_umState());
            call.setString(2, custTypeGroup.getNo());
            call.setString(3, custTypeGroup.getDescr());
            call.setString(4, custTypeGroup.getRemarks());
            call.setString(5, custTypeGroup.getLastUpdatedBy());
            call.setTimestamp(6, new Timestamp(custTypeGroup.getLastUpdate().getTime()));
            call.setString(7, custTypeGroup.getExpired());
            call.setString(8, custTypeGroup.getActionLog());
            call.setString(9, custTypeGroup.getDetailXml());
            call.registerOutParameter(10, Types.INTEGER);
            call.registerOutParameter(11, Types.NVARCHAR);
            call.execute();
            
            result.setCode(String.valueOf(call.getInt(10)));
            result.setInfo(call.getString(11));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(null, call, conn);
        }
        
        return result;
    }

}
