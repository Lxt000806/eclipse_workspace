package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.entity.insales.Purchase;
@Repository
@SuppressWarnings("serial")
public class SpcBuilderAnalysisDao extends BaseDao {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SpcBuilder spcBuilder) {
		Assert.notNull(spcBuilder);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pZpsjfx(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, spcBuilder.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(spcBuilder.getDateTo()).getTime()));
			call.setString(2, spcBuilder.getCode());
			call.setTimestamp(3, spcBuilder.getDelivDateFrom() == null ? null : new Timestamp(
					DateUtil.startOfTheDay(spcBuilder.getDelivDateFrom()).getTime()));
			call.setTimestamp(4, spcBuilder.getDelivDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(spcBuilder.getDelivDateTo()).getTime()));
			call.setString(5, spcBuilder.getType());
			call.setString(6, spcBuilder.getBuilderType());
			call.setString(7, page.getPageOrderBy());
			call.setString(8, page.getPageOrder());
			call.setInt(9, page.getPageNo());
			call.setInt(10,page.getPageSize());
			call.setString(11, spcBuilder.getExpired());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
	public Page<Map<String, Object>> findSpcBuilderAnalysisDetailPageBySql(Page<Map<String, Object>> page, SpcBuilder spcBuilder) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from"; 
				 sql+=" (select a.code,a.Address,a.CrtDate,a.setDate,a.signDate,a.EndDate,a.EndCode,a.PerfPK,a.ConfirmBegin,a.source,a.ContractFee, "
					+ " em.NameChi DesignManDescr,em2.NameChi BusinessManDescr,isnull(sp1.SpcBuilder,sp2.SpcBuilder) SpcBuilder , "
				    + " em3.NameChi AgainManDescr,dam2.Desc1 AgainDept2Descr,x1.note StatusDescr,a.VisitDate " 
				    + " from tCustomer a "              
				    + " inner join tEmployee em on a.DesignMan=em.Number " 
				    + " left join tDepartment1 dm1 on em.Department1=dm1.Code " 
				    + " left join tDepartment2 dm2 on em.Department2=dm2.Code "
				    + " left join tDepartment3 dm3 on em.Department2=dm3.Code " 
				    + " left join tEmployee em2 on a.BusinessMan=em2.Number " 
				    + " left join tEmployee em3 on a.AgainMan= em3.Number " 
				    + " left join tDepartment2 dam2 on em3.Department2 = dam2.Code "
				    + " left outer join tXTDM x1 on a.Status=x1.CBM and x1.ID='CUSTOMERSTATUS' "
				    + " inner join tCusttype ct on ct.code=a.CustType " 
				    //同步主页面查询过程修改
				    +" left outer join( " +
				    " select a.SpcBuilder,a.BuilderCode Code,b.BuilderNum from tSpcBuilderDeliv a " +
				    " left join tBuilderNum b on b.BuilderDelivCode=a.BuilderDelivCode " +
				    "  )sp1 on sp1.BuilderNum=a.BuilderNum and sp1.Code=a.BuilderCode and (a.BuilderNum is not null or a.BuilderNum<>'')"
				    +" left outer join(  " +
				    " select a.Code,c.SpcBuilder from tBuilder a  " +
				    " left join tSpcBuilderDeliv c on c.BuilderCode=a.Code " +
				    " where  exists( " +
				    " select 1 from tSpcBuilderDeliv b where b.BuilderCode=a.Code and (b.BuilderDelivCode='' or b.BuilderDelivCode is null)" +
				    " ) )sp2 on  sp2.Code=a.BuilderCode "
				    
				    /*+ " left outer join(" +
				    "	select b.SpcBuilder,a.Code,c.BuilderNum from tBuilder a "
				    + " left outer join tBuilderDeliv b on b.BuilderCode=a.code " 
				    + " left join tBuilderNum c  on c.BuilderDelivCode=b.Code " +
				    "	)sp1 on sp1.BuilderNum=a.BuilderNum and sp1.Code=a.BuilderCode and (a.BuilderNum is not null or a.BuilderNum<>'') " 
				    + " left outer join(" +
				    "		select a.Code,a.SpcBuilder from tBuilder a " 
				    + " where not exists(" +
				    "		select 1 from tBuilderDeliv b where b.BuilderCode=a.Code and(b.SpcBuilder is not null or b.SpcBuilder<>'')))sp2 on  sp2.Code=a.BuilderCode " 
				    */
				    
				    + " where a.Expired='F' and ct.isDefaultStatic='1' " 
				    + " and a.Status <> '1'  "; //and (dm1.DepType='2' or dm2.DepType='2' or dm3.DepType='2')
			if (StringUtils.isNotBlank(spcBuilder.getCode())) {
					sql += " and isnull(sp1.SpcBuilder,sp2.SpcBuilder)=? ";
					list.add(spcBuilder.getCode());
			}else{
				return null;
			};
			if ("1".equals(spcBuilder.getSpcBuilderAnalysisType())) { //下定信息
				sql += " and a.setDate<=? ";
				list.add(new Timestamp(
						DateUtil.endOfTheDay(spcBuilder.getDateTo()).getTime())); 
			}
			if ("2".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //退订信息
				sql += " and a.EndCode='6' and a.EndDate<=? ";
				list.add(new Timestamp(
						DateUtil.endOfTheDay(spcBuilder.getDateTo()).getTime()));
			}
			if ("3".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //有效合同信息
				sql += " and a.SignDate<=? and a.endcode <> '4' and a.status in ('4','5') ";
				list.add(new Timestamp(
						DateUtil.endOfTheDay(spcBuilder.getDateTo()).getTime()));
			}
			if ("4".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //有家开工信息
				sql += " and a.ConfirmBegin <=? ";
				list.add(new Timestamp(
						DateUtil.endOfTheDay(spcBuilder.getDateTo()).getTime()));
			}
			if ("5".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //项目经理翻单信息
				sql += " and a.PerfPK is not null and a.source='6' and a.SignDate<=? ";
				list.add(new Timestamp(
						DateUtil.endOfTheDay(spcBuilder.getDateTo()).getTime()));
			}
			if ("6".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //来客信息
				sql += " and a.VisitDate <= ? ";
				list.add(new Timestamp(
						DateUtil.endOfTheDay(spcBuilder.getDateTo()).getTime()));
			}
			
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.code";
			}
			
			return this.findPageBySql(page, sql, list.toArray());
		}
}
