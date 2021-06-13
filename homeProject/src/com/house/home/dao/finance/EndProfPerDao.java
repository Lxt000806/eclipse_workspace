package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.finance.EndProfPer;

@SuppressWarnings("serial")
@Repository
public class EndProfPerDao extends BaseDao{

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, EndProfPer endProfPer) {
        List<Object> list = new ArrayList<Object>();

        String sql = "select * from (select b.Code,b.Descr,b.Address LayoutDescr,a.MainProPer,a.ServProPer,a.IntProPer,a.SoftProPer,"
                + " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.CheckStatus,a.CurtainProPer,a.FurnitureProPer"
                + " from tEndProfPer a left outer join tCustomer b on a.CustCode=b.Code"
                + " where 1 = 1";
        
        if ( StringUtils.isNotBlank(endProfPer.getCustCode())) {//根据客户编号查询
            sql += " and a.CustCode = ?";
            list.add(endProfPer.getCustCode());
        }
        if (StringUtils.isNotBlank(endProfPer.getCustDescr())) {//根据客户名称查询
            sql += " and b.Descr like ?";
            list.add("%" + endProfPer.getCustDescr() + "%");
        }
        if (StringUtils.isNotBlank(endProfPer.getCustAddress())) {//根据客户楼盘查询
            sql += " and b.address like ?";
            list.add("%" + endProfPer.getCustAddress() + "%");
        }
        if (StringUtils.isBlank(endProfPer.getExpired()) || "F".equals(endProfPer.getExpired())) {
            sql += " and a.Expired = 'F'";
        }
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
            sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
        }else{
            sql += ")a order by a.LastUpdate desc";
        }
        return this.findPageBySql(page, sql, list.toArray());
    }
    
    /**
     * 根据客户编号查询材料毛利率
     * @param code
     * @return
     */
    public Map<String , Object>  getEndProfPerDetail(String code) {
        String sql = "select * from tCustomer a, tEndProfPer b where a.code = ? and a.code = b.custCode";
        code=code.replace(" ", "+");
        List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
    
    /**
     * 批量导入调过程
     */
    @SuppressWarnings("deprecation")
    public Result doSaveBatch (EndProfPer endProfPer, String importTypes) {
        Result result = new Result();
        Connection conn = null;
        CallableStatement call = null;
        try {
            HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
            Session session = SessionFactoryUtils.getSession(
                    hibernateTemplate.getSessionFactory(), true);
            conn = session.connection();
            call = conn.prepareCall("{Call pClmlltz(?,?,?,?,?,?,?,?,?,?)}");
            call.setString(1, endProfPer.getCustCode());
            call.setDouble(2, endProfPer.getMainProPer() == null ? 0 : endProfPer.getMainProPer());
            call.setDouble(3, endProfPer.getServProper() == null ? 0 : endProfPer.getServProper());
            call.setDouble(4, endProfPer.getIntProPer() == null ? 0 : endProfPer.getIntProPer());
            call.setDouble(5, endProfPer.getSoftProPer() == null ? 0 : endProfPer.getSoftProPer());
            call.setDouble(6, endProfPer.getCurtainProPer() == null ? 0 : endProfPer.getCurtainProPer());
            call.setDouble(7, endProfPer.getFurnitureProPer() == null ? 0 : endProfPer.getFurnitureProPer());
            call.setString(8, importTypes);
            call.registerOutParameter(9, Types.INTEGER);
            call.registerOutParameter(10, Types.NVARCHAR);
            call.execute();
            result.setCode(String.valueOf(call.getInt(9)));
            result.setInfo(call.getString(10));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(null, call, conn);
        }
        return result;
    }
}
