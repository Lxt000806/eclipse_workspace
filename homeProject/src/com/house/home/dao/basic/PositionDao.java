package com.house.home.dao.basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Position;

@SuppressWarnings("serial")
@Repository
public class PositionDao extends BaseDao {

	/**
	 * 职位分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Position position) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Code, a.Desc2, a.Desc1, a.LastUpdate, a.LastUpdatedBy,"
		        + " a.Expired, a.ActionLog, a.type, b.Note typeDescr,c.Note isSignDescr  " 
		        + " from tPosition a"
				+ " left join tXTDM b on a.type = b.cbm and b.ID = 'POSTIONTYPE' "
				+ " left join tXTDM c on a.isSign = c.cbm and c.ID = 'YESNO' "
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(position.getCode())) {
			sql += " and a.Code like ?";
			list.add("%" + position.getCode() + "%");
		}
		if (StringUtils.isNotBlank(position.getDesc2())) {
			sql += " and a.Desc2 like ?";
			list.add("%" + position.getDesc2() + "%");
		}
		if(StringUtils.isNotBlank(position.getType())){
		    sql += " and a.type = ?";
		    list.add(position.getType());
		}
		if(StringUtils.isBlank(position.getExpired()) || "F".equals(position.getExpired())){
		    sql += " and a.Expired = 'F'";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public Position getByDesc2(String desc2) {
		String hql = "from Position a where a.desc2=? ";
		List<Position> list = this.find(hql, new Object[]{desc2});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/** 根据主键查找职位 */
	public Position getByCode(String code){
	    return get(Position.class, code);
	}

	@SuppressWarnings("unchecked")
	public List<Position> findByNoExpired() {
		String hql = "from Position a where a.expired<>'T' ";
		return this.find(hql);
	}
	
	public String getDepLeader(String code) {
        String sql=" select a.code,a.desc2 from tPosition a where a.type=?";
        
        List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code});
        if (list!=null && list.size()>0){
            return list.get(0).get("NameChi").toString();
        }       
        return "";
    }
	
	/** 获取当前的最大编号 
	 * @throws SQLException */
	public Integer getMaxCode() throws SQLException{
	    String sql = "select max(cast(Code as int)) maxCode from tPosition";
	    HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Connection conn = session.connection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getInt("maxCode");
        }
        return null;
	}
}
