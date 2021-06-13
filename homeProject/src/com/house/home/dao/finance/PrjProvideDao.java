package com.house.home.dao.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.PrjCheck;
import com.house.home.entity.finance.PrjProvide;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;

@SuppressWarnings("serial")
@Repository
public class PrjProvideDao extends BaseDao {

	/**
	 * PrjProvid分页信息
	 * 
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProvide prjProvide){
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.no,a.Status,d.Note StatusName,a.AppCZY,e.Zwxm AppCZYName,a.Date,a.Remarks,"
        + "a.ConfirmCZY,f.Zwxm ConfirmCZYName,a.ConfirmDate,"
        + "a.LastUpdate,a.LastUpdatedBy,g.Zwxm LastUpdateByName,a.Expired,a.ActionLog "
        + "from tPrjProvide a "
        + "left join tXTDM d on a.Status=d.CBM and d.ID='PRJPVDSTATUS' "
        + "left join tCzybm e on e.Czybh=a.AppCZY "
        + "left join tCzybm f on f.Czybh=a.ConfirmCZY "
        + "left join tCzybm g on g.Czybh=a.LastUpdatedBy " 
        + "where 1=1 and a.Expired ='F' ";
    	if (prjProvide.getDateFrom()!=null) {
			sql += " and a.date>=? ";
			list.add(prjProvide.getDateFrom());
		}
    	if (prjProvide.getDateTo()!=null) {
			sql += " and a.date< dateAdd(d,1,?) ";
			list.add(prjProvide.getDateTo());
		}
    	if (StringUtils.isNotBlank(prjProvide.getStatus())){
			sql += " and a.Status in ('"+prjProvide.getStatus().replaceAll(",", "','")+"') ";
		}	
    	if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastupdate desc";
    	}
		return this.findPageBySql(page, sql, list.toArray());
	}
	//项目经理提成领取条目个别查询
	public Page<Map<String, Object>> goJqGrid_toPrjProCheck(Page<Map<String, Object>> page,PrjCheck prjCheck){
		List<Object> list = new ArrayList<Object>();
		String sql ="select c.NameChi ProjectMan,b.Address,b.Area,b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg zjFee,a.BaseCtrlAmt,a.Cost,a.RecvFee,a.RecvFee_FixDuty RecvFeeFixDuty, "+
	    "a.MainCoopFee,a.WithHold,a.lastUpdate,"+
	    "a.QualityFee,a.AccidentFee,a.MustAmount,a.RealAmount,a.CustCode, "+ 
	    "cast(round(a.BaseCtrlAmt - a.Cost + a.MainCoopFee - a.WithHold, 0) as int) jsye, "+
	    "cast(case when b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg=0 then 0 else (a.BaseCtrlAmt-a.Cost+a.MainCoopFee-a.WithHold)*100/(b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg) end as varchar(20))+'%' ysper, "+
	    "cast(case when a.BaseCtrlAmt=0 then 0 else (a.BaseCtrlAmt-a.Cost+a.MainCoopFee-a.WithHold)*100/a.BaseCtrlAmt end as varchar(20))+'%' fbper,a.ProvideRemark "+ 
	    "from tPrjCheck a "+ 
	    "inner join tCustomer b on a.CustCode=b.Code "+ 
	    "left join tEmployee c on b.ProjectMan=c.Number "+ 
	    "where 1=1 ";//and b.CheckStatus=3 
		//添加过后的记录不再查询出来
		if (StringUtils.isNotBlank(prjCheck.getAllDetailInfo())) {
			sql += "and a.CustCode not in" + "('"+prjCheck.getAllDetailInfo().replaceAll(",","','")+"')";
		}
		if (StringUtils.isNotBlank(prjCheck.getProvideNo())) {
			sql +="and a.provideNo =? ";
			list.add(prjCheck.getProvideNo());
		}
		if (StringUtils.isNotBlank(prjCheck.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+prjCheck.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.lastUpDate desc";
		}
		return this.findPageBySql(page,sql,list.toArray());		
	}
	//项目经理明细条目查询
/*  sqltxt := sqltxt + 'c.NameChi ProjectMan,b.Address,b.Area,b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg zjFee,a.BaseCtrlAmt,a.Cost,a.RecvFee,'
           + 'a.MainCoopFee,a.WithHold,'
           + 'a.QualityFee,a.AccidentFee,a.MustAmount,a.RealAmount,case when '''+sIsAddRemaks+'''=''0'' then a.ProvideRemark else a.Remarks end ProvideRemark  ,a.IsChecked,a.CustCode '
           + ',(a.BaseCtrlAmt-a.Cost+a.MainCoopFee-a.WithHold) jsye,'
           + 'cast(case when b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg=0 then 0 else (a.BaseCtrlAmt-a.Cost+a.MainCoopFee-a.WithHold)*100/(b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg) end as varchar(20))+''%'' ysper,'
           + 'cast(case when a.BaseCtrlAmt=0 then 0 else (a.BaseCtrlAmt-a.Cost+a.MainCoopFee-a.WithHold)*100/a.BaseCtrlAmt end as varchar(20))+''%'' fbper '
           + ' from #tPrjCheck_add a '
           + ' inner join tCustomer b on a.CustCode=b.Code '
           + ' left join tEmployee c on b.ProjectMan=c.Number '
           + 'where b.CheckStatus=''3'' ';
  if Trim(edtAddress.Text) <> '' then sqltxt := sqltxt + ' and b.Address like ''%'+Trim(edtAddress.Text)+'%''';

  sqltxt := sqltxt + ' Order by b.Address ';*/
	
	public Page<Map<String, Object>> goJqGrid_toPrjProDetail(Page<Map<String, Object>> page,PrjCheck prjCheck){
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from ( select c.NameChi ProjectMan,b.Address,b.Area,b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg zjFee,a.BaseCtrlAmt,a.Cost,a.RecvFee,a.RecvFee_FixDuty RecvFeeFixDuty, "+
	    "a.MainCoopFee,a.WithHold,a.lastUpdate,"+
	    "a.QualityFee,a.AccidentFee,a.MustAmount,a.MustAmount RealAmount,a.CustCode, "+ 
	    "(a.BaseCtrlAmt-a.Cost+a.MainCoopFee-a.WithHold) jsye, "+
	    "cast(case when b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg=0 then 0 else (a.BaseCtrlAmt-a.Cost+a.MainCoopFee-a.WithHold)*100/(b.BaseFee_Dirct+b.BaseFee_comp+a.BaseChg) end as varchar(20))+'%' ysper, "+
	    "cast(case when a.BaseCtrlAmt=0 then 0 else (a.BaseCtrlAmt-a.Cost+a.MainCoopFee-a.WithHold)*100/a.BaseCtrlAmt end as varchar(20))+'%' fbper ";
	    if ("1".equals(prjCheck.getProvideRemark())) {
			sql += ",a.Remarks ProvideRemark ";
	    }else{
	    	sql += ",a.ProvideRemark ";
		}
		sql+="from tPrjCheck a "+ 
	    "inner join tCustomer b on a.CustCode=b.Code "+ 
	    "left join tEmployee c on b.ProjectMan=c.Number "+ 
	    "where 1=1 and a.CustCode=b.Code and b.CheckStatus='3' and a.IsProvide='0' ";//and b.CheckStatus=3 
		if (StringUtils.isNotBlank(prjCheck.getAllDetailInfo())) {
			sql += "and a.CustCode not in" + "('"+prjCheck.getAllDetailInfo().replaceAll(",","','")+"')";
		}
		if (StringUtils.isNotBlank(prjCheck.getProvideNo())) {
			sql +="and a.provideNo =? ";
			list.add(prjCheck.getProvideNo());
		}
		if (StringUtils.isNotBlank(prjCheck.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+prjCheck.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.Address ";
		}
		return this.findPageBySql(page,sql,list.toArray());		
	}
    /**
     *项目经理提成领取（新增 ，修改 ）
     * */
 	@SuppressWarnings("deprecation")
	public Result doPrjProvideForProc(PrjProvide prjProvide) {
		Assert.notNull(prjProvide);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pXmjltclq_Add_forXml(?,?,?,?,?,?,?)}");
			call.setString(1,prjProvide.getM_umState());
			call.setString(2,prjProvide.getNo());
			call.setString(3, prjProvide.getRemarks());
			call.setString(4, prjProvide.getLastUpdatedBy());
			call.setString(5, prjProvide.getPrjproCheckDetailJson());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			System.out.println(prjProvide.getPrjproCheckDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public Result doPrjProvideCheckForProc(PrjProvide prjProvide) {
		// TODO Auto-generated method stub
		Assert.notNull(prjProvide);
		Result result = new Result();
		Connection con = null;
		CallableStatement call=null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
		    con =session.connection();
		    call = con.prepareCall("{Call pXmjltclq_sh_forXml(?,?,?,?,?,?,?,?)}");
			call.setString(1,prjProvide.getNo());
			call.setString(2, prjProvide.getStatus());
			call.setString(3, prjProvide.getRemarks());
			call.setString(4, prjProvide.getLastUpdatedBy());
			call.setString(5,prjProvide.getPrjproCheckDetailJson());
			call.registerOutParameter(6,Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.setString(8,prjProvide.getDocumentNo());
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DbUtil.close(null,call,con);
		}
		return result;
	}
}

