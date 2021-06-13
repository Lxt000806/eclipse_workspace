package com.house.home.dao.design;

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
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.home.entity.design.CustTag;
import com.house.home.entity.design.CustTagGroup;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.project.WorkCost;

@SuppressWarnings("serial")
@Repository
public class CustTagDao extends BaseDao{
	
	public List<Map<String, Object>> getCustTagGroup() {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.PK TagGroupPk, a.Descr TagGroupDescr, a.IsMulti, a.Color " 
				+" from tCustTagGroup a" 
				+" where Expired = 'F' and exists(select 1 from tCustTag in_a where a.PK=in_a.TagGroupPK)"
				+" order by DispSeq ";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String, Object>> getCustTagByGroupPk(Integer groupPk) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.PK tagPk, a.Descr tagDescr,a.tagGroupPk,b.color " 
				+" from tCustTag a " 
				+" left join tCustTagGroup b on a.TagGroupPK = b.PK "
				+" where a.Expired = 'F' and TagGroupPK = ? order by b.DispSeq,a.DispSeq";
		list.add(groupPk);
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String, Object>> getCustTagMapper(String resrCustCode, String czybh){
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.PK,b.Descr TagDescr,c.Color,b.PK TagPk " 
				+" from tCustTagMapper a " 
				+" left join tCustTag b on a.TagPK = b.PK "
				+" left join tCustTagGroup c on b.TagGroupPK = c.Pk "
				+" left join tResrCust d on a.ResrCustCode = d.Code "
				+" left join tResrCustPool e on d.ResrCustPoolNo = e.No "
				+" where a.Expired = 'F' and a.ResrCustCode = ? and a.CustCode='' "
				+" and (e.IsVirtualPhone = '0' or d.CustResStat not in('0','2') or (d.CustResStat in ('0','2') and a.LastUpdatedBy = ? ))"
				+" order by c.DispSeq,b.DispSeq ";
		list.add(resrCustCode);
		list.add(czybh);
		
		return this.findBySql(sql, list.toArray());
	}
	
	/**
	 * 批量设置客户标签
	 * @param workCost
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doBatchSetCustTag(ResrCust resrCust) {
		Assert.notNull(resrCust);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBatchSetCustTag(?,?,?,?,?,?)}");
			call.setString(1, resrCust.getCodes());
			call.setString(2, resrCust.getDelTagPks());	
			call.setString(3, resrCust.getAddTagPks());
			call.setString(4, resrCust.getCzybh());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	/**
	 * 客户标签组树形下拉框查询
	 * @author cjg
	 * @return
	 */
	public List<Map<String, Object>> findCustTagsForTree(){
		String sql = "select * from (" 
				+"   select cast(PK as nvarchar(20))+'_p' id,Descr name,'_VIRTUAL_RO0T_ID_' pId,a.DispSeq "
				+"   from tCustTagGroup a " 
				+"   where exists(select 1 from tCustTag b where b.TagGroupPK=a.PK and b.Expired='F') and a.Expired='F' "
				+"   union "
				+"   select cast(PK as nvarchar(20))id,Descr name,cast(TagGroupPK as nvarchar(20))+'_p' pId,c.DispSeq " 
				+"   from tCustTag c"
				+"   where Expired='F' and exists(select 1 from tCustTagGroup d where d.PK=c.TagGroupPK and d.Expired='F')"
				+")e order by e.DispSeq ";
		return this.findBySql(sql, new Object[]{});
	}
	
	public Page<Map<String, Object>> findCustTagGroupPageBySql(
            Page<Map<String, Object>> page, CustTagGroup custTagGroup) {

        ArrayList<Object> params = new ArrayList<Object>();

        String sql = "select * from (select a.PK, a.Descr, a.DispSeq, a.IsMulti, b.NOTE IsMultiDescr, a.Color, "
                + "a.CrtCZY, c.NameChi CrtName, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tCustTagGroup a "
                + "left join tXTDM b on a.IsMulti = b.CBM and b.ID = 'YESNO' "
                + "left join tEmployee c on a.CrtCZY = c.Number "
                + "where 1 = 1 ";
        
        if (StringUtils.isNotBlank(custTagGroup.getDescr())) {
            sql += "and a.Descr like ? ";
            params.add("%" + custTagGroup.getDescr() + "%");
        }
        
        if (StringUtils.isBlank(custTagGroup.getExpired())
                || "F".equals(custTagGroup.getExpired())) {
            sql += "and a.Expired = 'F' ";
        }
    
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.DispSeq";
        }

        return findPageBySql(page, sql, params.toArray());
    }
    
    
    public Page<Map<String, Object>> findCustTagPageBySql(
            Page<Map<String, Object>> page, CustTag custTag) {
        
        ArrayList<Object> params = new ArrayList<Object>();

        String sql = "select * from (select PK, Descr, CrtCZY, b.NameChi CrtCzyName, a.DispSeq, a.LastUpdate, "
                + "a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tCustTag a "
                + "left join tEmployee b on a.CrtCZY = b.Number "
                + "where 1 = 1 ";
        
        if (custTag.getTagGroupPk() != null) {
            sql += "and a.TagGroupPK = ? ";
            params.add(custTag.getTagGroupPk());
        }
        
        if (StringUtils.isNotBlank(custTag.getDescr())) {
            sql += "and a.Descr like ? ";
            params.add("%" + custTag.getDescr() + "%");
        }
        
        if (StringUtils.isBlank(custTag.getExpired())
                || "F".equals(custTag.getExpired())) {
            sql += "and a.Expired = 'F' ";
        }
    
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.DispSeq";
        }

        return findPageBySql(page, sql, params.toArray());
    }
    
    
    @SuppressWarnings("deprecation")
    public Result saveCustTagGroupByProc(CustTagGroup custTagGroup) {
        Assert.notNull(custTagGroup);
        Result result = new Result();
        Connection conn = null;
        CallableStatement call = null;
        
        try {
            HibernateTemplate hibernateTemplate = SpringContextHolder
                    .getBean("hibernateTemplate");
            Session session = SessionFactoryUtils.getSession(
                    hibernateTemplate.getSessionFactory(), true);
            conn = session.connection();
            call = conn.prepareCall("{Call pCustTagGroup(?,?,?,?,?,?,?,?,?,?,?,?)}");
            
            call.setString(1, "A");
            
            // 新增时用不到PK所以传一个“-1”
            call.setInt(2, -1);
            call.setString(3, custTagGroup.getDescr());
            call.setInt(4, custTagGroup.getDispSeq());
            call.setString(5, custTagGroup.getIsMulti());
            call.setString(6, custTagGroup.getColor());
            call.setString(7, custTagGroup.getCrtCzy());
            call.setString(8, custTagGroup.getCrtCzy());
            call.setString(9, "F");
            call.setString(10, XmlConverUtil.jsonToXmlNoHead(custTagGroup.getTagsJson()));
            call.registerOutParameter(11, Types.INTEGER);
            call.registerOutParameter(12, Types.NVARCHAR);
            
            call.execute();
            
            result.setCode(String.valueOf(call.getInt(11)));
            result.setInfo(call.getString(12));
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(null, call, conn);
        }
        
        return result;
    }


    @SuppressWarnings("deprecation")
    public Result updateCustTagGroupByProc(CustTagGroup custTagGroup) {
        Assert.notNull(custTagGroup);
        Result result = new Result();
        Connection conn = null;
        CallableStatement call = null;
        
        try {
            HibernateTemplate hibernateTemplate = SpringContextHolder
                    .getBean("hibernateTemplate");
            Session session = SessionFactoryUtils.getSession(
                    hibernateTemplate.getSessionFactory(), true);
            conn = session.connection();
            call = conn.prepareCall("{Call pCustTagGroup(?,?,?,?,?,?,?,?,?,?,?,?)}");
            
            call.setString(1, "M");
            call.setInt(2, custTagGroup.getPk());
            call.setString(3, custTagGroup.getDescr());
            call.setInt(4, custTagGroup.getDispSeq());
            call.setString(5, custTagGroup.getIsMulti());
            call.setString(6, custTagGroup.getColor());
            call.setString(7, custTagGroup.getCrtCzy());
            call.setString(8, custTagGroup.getLastUpdatedBy());
            call.setString(9, custTagGroup.getExpired());
            call.setString(10, XmlConverUtil.jsonToXmlNoHead(custTagGroup.getTagsJson()));
            call.registerOutParameter(11, Types.INTEGER);
            call.registerOutParameter(12, Types.NVARCHAR);
            
            call.execute();
            
            result.setCode(String.valueOf(call.getInt(11)));
            result.setInfo(call.getString(12));
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(null, call, conn);
        }
        
        return result;
    }


    public Integer countTagUsage(Integer tagPk) {
        ArrayList<Object> params = new ArrayList<Object>();
        
        String sql = "select * from tCustTagMapper where TagPK = ? and Expired = 'F'";
        params.add(tagPk);
        
        List<Map<String,Object>> tagMappers = findBySql(sql, params.toArray());
        
        return tagMappers.size();
    }
}
