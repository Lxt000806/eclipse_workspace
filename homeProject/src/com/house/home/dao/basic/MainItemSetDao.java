package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
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
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;

@Repository
@SuppressWarnings("serial")
public class MainItemSetDao extends BaseDao {
	
    public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,
            ItemSetDetail itemSetDetail) {

        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ( "
                + " select a.No, a.ItemCode, b.descr ItemDescr, a.UnitPrice, "
                + " a.CustTypeItemPk, a.Algorithm, c.Descr AlgorithmDescr, "
                + " a.AlgorithmPer, a.AlgorithmDeduct, a.ItemTypeCode, d.NOTE ItemTypeDescr, "
                + " a.Remarks, a.Qty, a.CutType, e.NOTE CutTypeDescr, "
                + " a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + " from tItemSetDetail a "
                + " left join tItem b on a.ItemCode = b.Code "
                + " left join tAlgorithm c on a.Algorithm = c.Code "
                + " left join tXTDM d on d.ID = 'ITEMTYPEDESCR' and a.ItemTypeCode = d.CBM "
                + " left join tXTDM e on e.ID = 'CUTTYPE' and a.CutType = e.CBM "
                + " where 1 = 1 ";

        if (StringUtils.isNotBlank(itemSetDetail.getNo())) {
            sql += "and a.No = ? ";
            params.add(itemSetDetail.getNo());
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += " ) a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += " ) a order by a.no";
        }
        
        return this.findPageBySql(page, sql, params.toArray());
    }
	
    @SuppressWarnings("deprecation")
    public Result saveByProcedure(ItemSet itemSet) {
        Assert.notNull(itemSet);
        Result result = new Result();
        Connection conn = null;
        CallableStatement call = null;
        try {
            HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
            Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            conn = session.connection();
            call = conn.prepareCall("{Call pMainItemSet(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            call.setString(1, itemSet.getM_umState());
            call.setString(2, itemSet.getNo());
            call.setString(3, itemSet.getDescr());
            call.setString(4, itemSet.getItemType1());
            call.setString(5, itemSet.getCustType());
            call.setString(6, itemSet.getRemarks());
            call.setString(7, itemSet.getIsOutSet());
            call.setString(8, itemSet.getDetailXml());
            call.setTimestamp(9, new Timestamp(itemSet.getLastUpdate().getTime()));
            call.setString(10, itemSet.getLastUpdatedBy());
            call.setString(11, itemSet.getExpired());
            call.setString(12, itemSet.getActionLog());
            call.registerOutParameter(13, Types.INTEGER);
            call.registerOutParameter(14, Types.NVARCHAR);
            call.execute();
            result.setCode(String.valueOf(call.getInt(13)));
            result.setInfo(call.getString(14));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(null, call, conn);
        }
        
        return result;
    }

}

