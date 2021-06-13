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
import com.house.home.entity.insales.ItemWHBal;


@SuppressWarnings("serial")
@Repository
public class LogWareEffAnalyDao extends BaseDao {
	
	/**
	 * 物流仓储效率分析
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemWHBal itemWHBal) {
		Assert.notNull(itemWHBal);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLogWareEffAnaly(?,?,?,?,?)}");
			call.setTimestamp(1, itemWHBal.getDateFrom() == null ? null : new Timestamp(
					itemWHBal.getDateFrom().getTime()));
			call.setTimestamp(2, itemWHBal.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(itemWHBal.getDateTo()).getTime()));
			call.setString(3, itemWHBal.getWhCode());
			call.setString(4, itemWHBal.getItemType1());
			call.setString(5, itemWHBal.getItemType2());
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

	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from( " 
				+" select c.ITCode,c.Descr, "
				+" isnull(a.lkamount,0) lkamount, "
				+" isnull(a.ckamount,0) ckamount, "
				+" isnull(c.dqkcamount,0)-isnull(b.bdamount,0) qckcamount ,"//期初库存
				+" isnull(c.dqkcamount,0)-isnull(b.bdamount,0)+isnull(a.bdamount,0) qmckamount,"//期末库存, 
				+" case when (isnull(c.dqkcamount,0)-isnull(b.bdamount,0))+(isnull(c.dqkcamount,0)-isnull(b.bdamount,0)-isnull(a.bdamount,0))=0 then 0 "
				+" else round(ckamount*100.0/(((isnull(c.dqkcamount,0)-isnull(b.bdamount,0))+(isnull(c.dqkcamount,0)-isnull(b.bdamount,0)+isnull(a.bdamount,0)))/2),1) end  kczzPer "	
				+" from ( "
				+"	 	 select  a.ITCode, b.Descr, round(sum(a.QtyCal * a.AvgCost), 0) dqkcamount"	//当前库存		
				+"	 	 from  titemwhbal a "
				+"	 	 inner join titem b on a.itcode = b.code "
				+"	 	 where  a.WHCode=? and b.ItemType2 =? " 
				+"	 	 group by a.ITCode, b.Descr "
				+" )c   "
				+" left join ( "
				+"	    select a.ITCode, "
				+"	 	round(sum(a.trsqty * a.cost),0) bdamount, " //统计期变动库存
				+"	 	round(sum(case when a.trsqty > 0 then round(a.trsqty * a.cost, 2) else 0 end),0) lkamount, "
				+"	 	round(sum(case when a.trsqty <= 0 then round(abs(a.trsqty) * a.cost, 2)else 0 end),0) ckamount "
				+"	 	from tItemTransaction a "
				+"	 	left outer join titembaladjdetail c on c.IBHNo=a.Document and a.ITCode=c.ITCode "
				+"	 	inner join titem e on a.itcode = e.code "
				+"	 	where  a.WHCode=? and a.Date>=? and a.Date<=?  "
				+"	 	and e.ItemType2=? "
				+"	 	 group by a.ITCode "	 
				+"	)a on a.ITCode=c.ITCode "
				+"	left join ( "
				+"	 	select  a.ITCode,round(sum(a.trsqty * a.cost),0) bdamount " //变动库存
				+"	 	from tItemTransaction a  "
				+"	 	inner join titem e on a.itcode = e.code "
				+"	 	where a.WHCode=? and a.Date>=?   "
				+"	 	and e.ItemType2=? "	
				+"	 	group by a.ITCode "
				+"	)b on b.ITCode=c.ITCode " ;  
			list.add(itemWHBal.getWhCode());
			list.add(itemWHBal.getItemType2());
			
			list.add(itemWHBal.getWhCode());
			list.add(itemWHBal.getDateFrom());
			list.add(DateUtil.endOfTheDay(itemWHBal.getDateTo()));
			list.add(itemWHBal.getItemType2());
			
			list.add(itemWHBal.getWhCode());
			list.add(itemWHBal.getDateFrom());
			list.add(itemWHBal.getItemType2());
			
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.kczzPer";
			}
			return this.findPageBySql(page, sql, list.toArray());
		}
	public Page<Map<String, Object>> findPageBySql_sendDetail(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from( " 
					+" select ia.No, ia.ConfirmDate,ia.SendDate,ia.DelayReson,ia.DelayRemark, "
					+" case when dateadd(hh,72,ia.ConfirmDate)>=ia.SendDate then '是' else '否' end isSendOnTime,"
					+" x1.note DelayResonDescr,round(isnull(f.zxamount,0),0) zxamount,isnull(e.psAmount,0) psAmount, "
					+" isnull(g.gdckamount,0) gdckamount "
					+" from  titemapp ia " 
					+" inner join (select min(pk) pk,no from titemappdetail "
					+" 	  group by no "
					+" )b on ia.No=b.no " 
					+" left join titemappdetail c on c.pk = b.pk "
					+" left join titem d on d.code = c.itemcode "
					+" left join tXTDM x1 on x1.id='appdelayreson' and x1.cbm=ia.DelayReson " 
					+" left join ("
					+" 	 select IANo,round(sum(isnull(TransFee,0)+"
					+" 	 isnull(CarryFee,0)+isnull(TransFeeAdj,0)),0) psAmount"
					+" 	 from tItemAppSend"
					+" 	 group by IANo "
            		+" )e on e.IANo=ia.No "
            		+" left join ( "
					+" 	 select ias.IANo,sum(lfd.Amount) zxamount "
					+" 	 from tLaborFeeDetail lfd "
					+" 	 inner join tLaborFee lf on lf.No=lfd.No "
					+" 	 inner join tLaborFeeType lft on lft.Code=lfd.FeeType "
					+" 	 inner join tItemAppSend ias on ias.No=lfd.AppSendNo "
					+" 	 where lf.Status='4' and lft.FeeBigType='1' "
					+" 	 group by ias.IANo "
            		+" )f on f.IANo=ia.No "
            		+" left join ( "
					+" 	 select ias.IANo,round(sum(-1 * its.trsqty * its.cost),0) gdckamount "
					+" 	 from tItemTransaction its "
					+" 	 inner join tItemAppSend ias on ias.No=its.Document "
					+" 	 where its.PrefixCode='ias' "
					+" 	 group by ias.IANo "
            		+" )g on g.IANo=ia.No "
					+" where ia.Status='SEND' and  ia.SendType='2' "
					+" and ia.WHCode=? and ia.SendDate>=? and ia.SendDate<=? " ;
			list.add(itemWHBal.getWhCode());
			list.add(itemWHBal.getDateFrom());
			list.add(DateUtil.endOfTheDay(itemWHBal.getDateTo()));
			if (StringUtils.isNotBlank(itemWHBal.getItemType1())) {
				sql += " and d.ItemType1=? ";
				list.add(itemWHBal.getItemType1());
			}
			if(StringUtils.isNotBlank(itemWHBal.getItemType2())){
				sql+=" and d.ItemType2=?";
				list.add(itemWHBal.getItemType2());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.SendDate";
			}
			return this.findPageBySql(page, sql, list.toArray());
		}

}

