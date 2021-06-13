package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.MainCommiPerc;
import com.house.home.entity.finance.MainCommi;

@SuppressWarnings("serial")
@Repository
public class MainCommiDao extends BaseDao {

	/**
	 * MainCommi分页信息
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommi mainCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.No, a.BeginDate, a.EndDate, a.Status, b.NOTE StatusDescr,"
				+ " a.PrjPerfNo,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Remarks "
				+ " from tMainCommi a"
				+ " left join tXTDM b on b.ID='COMMISTATUS' and b.CBM=a.Status"
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(mainCommi.getNo())) {
			sql += " and a.no = ? ";
			list.add(mainCommi.getNo());
		}
		if (mainCommi.getDateFrom() != null) {
			sql += " and a.beginDate>= ?  ";
			list.add(mainCommi.getDateFrom());
		}
		if (mainCommi.getDateTo() != null) {
			sql += " and a.beginDate< dateAdd(d,1,?) ";
			list.add(mainCommi.getDateTo());
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.beginDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 查询状态
	 * 
	 * @param no
	 * @return
	 */
	public String checkStatus(String no) {
		String sql = " select Status from tMainCommi where No= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			return list.get(0).get("Status").toString();
		} else {
			return null;
		}
	}

	/**
	 * 计算完成
	 * 
	 * @param no
	 */
	public void doSaveCount(String no) {
		String sql = " update tMainCommi set Status='2' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 退回
	 * 
	 * @param no
	 */
	public void doSaveCountBack(String no) {
		String sql = " update tMainCommi set Status='1' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 检查是否能计算周期
	 * 
	 * @param no
	 * @param beginDate
	 * @return
	 */
	public String isExistsPeriod(String no, String beginDate) {
		String sql = " select BeginDate from tMainCommi where No= ?  and Status='1'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			String sqlDate = " select 1 from tMainCommi where BeginDate< ? and Status='1' ";
			List<Map<String, Object>> listDate = this.findBySql(sqlDate,
					new Object[] { list.get(0).get("BeginDate") });
			if (listDate != null && listDate.size() > 0) {
				return "2";// 之前的统计周期未计算完成，不允许计算本周期业绩!
			} else {
				return null;
			}
		} else {
			return "1";// 没有找到相应的业绩统计周期或该统计周期已计算完成!
		}
	}
	/**
	 * 生成提成数据
	 * 
	 * @param no
	 * @param lastUpdatedBy
	 */
	public Map<String, Object> doCount(String no, String lastUpdatedBy) {
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCalcMainCommi(?,?)}");
			call.setString(1, no);
			call.setString(2, lastUpdatedBy);
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			if (list.size() != 0 && list != null) {
				if (list.get(0).get("errmsg") != null)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
	/**
	 *非独立销售明细
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> goFdlJqGrid(Page<Map<String,Object>> page, MainCommi mainCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.No,a.CustCode,b.Address,b.Area,b.DocumentNo,b.SignDate,a.TaxPer,a.CheckAmount,a.MainCheckAmount_OutSet,a.MainChgAmount_Outset,"
			+"a.MainPlanAmount_OutSet,a.ServPlanAmount_OutSet,a.ServChgAmount_OutSet,a.ServCheckAmount_OutSet,a.CheckAmount_CentralPurch,a.ManagerCommi,"
			+"a.MainBusiManCommi,a.DeclareManCommi,a.CheckManCommi,a.DeptFundCommi,a.TotalCommi,e.NOTE IsModifiedDescr,a.LastUpdate,"
			+"a.LastUpdatedBy,a.Expired,a.ActionLog,a.CheckAmount_InSet,c.Desc1 CustTypeDescr,d.NameChi MainBusiManDescr,a.DirectorManCommi "
			+"from tMainCommiDetail a "
			+"left join tCustomer b on a.CustCode=b.Code "
			+"left join tCusttype c on b.CustType=c.Code "
			+"left join tEmployee d on a.MainBusiMan=d.Number "
			+"left join tXTDM e on a.IsModified=e.CBM and e.ID='YESNO' where a.No=? ";
		list.add(mainCommi.getNo());
		if(StringUtils.isNotBlank(mainCommi.getAddress())){
			sql+=" and b.Address like ?";
			list.add("%"+mainCommi.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(mainCommi.getCustType())){
			sql+=" and b.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"')";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 *独立销售明细
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> goDlJqGrid(Page<Map<String,Object>> page, MainCommi mainCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.No,a.CustCode,b.Address,b.DocumentNo,b.SignDate,a.TaxPer,a.MainCheckAmount,a.ServCheckAmount,a.CheckAmount,a.ElecSaleAmount, "
				+"a.ElecCost,d.NameChi BusinessManDescr,a.BusinessManCommi,e.NOTE IsModifiedDescr,g.Desc1 department1descr," 
				+"c.Desc1 CustTypeDescr,f.NOTE SaleTypeDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, " 
				+"h.NameChi MainBusiManDescr, a.CheckAmount_CentralPurch,a.ManagerCommi,a.MainBusiManCommi,a.DeclareManCommi,a.DirectorManCommi,a.TotalCommi,i.NameChi DeclareManDescr "
				+"from tMainCommiDetail_IndeSale a "
				+"left join tCustomer b on a.CustCode=b.Code "
				+"left join tCusttype c on b.CustType=c.Code "
				+"left join tEmployee d on a.BusinessMan=d.Number "
				+"left join tXTDM f on a.SaleType=f.CBM and f.ID='COMMISALETYPE'"
				+"left join tXTDM e on a.IsModified=e.CBM and e.ID='YESNO' " 
				+"left join tDepartment1 g on g.Code=d.Department1 "
				+"left join tEmployee h on a.MainBusiMan=h.Number "
				+"left join tEmployee i on a.DeclareMan=i.Number "
				+"where a.CheckAmount>0 and a.No=? ";
		list.add(mainCommi.getNo());
		if(StringUtils.isNotBlank(mainCommi.getAddress())){
			sql+=" and b.Address like ?";
			list.add("%"+mainCommi.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(mainCommi.getCustType())){
			sql+=" and b.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"')";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 更新非独立销售明细
	 * 
	 * @param pk
	 * @return
	 */
	public void doUpdateFdl(Integer pk,Double managercommi,Double mainbusimancommi,
				Double declaremancommi,Double checkmancommi,Double deptfundcommi,Double totalcommi,String lastupdatedby) {
		String sql = " update tMainCommiDetail set managercommi=?,mainbusimancommi=?,declaremancommi=?,"
				+"checkmancommi=?,deptfundcommi=?,totalcommi=?,ismodified='1',lastupdate=getdate(),lastupdatedby=?,actionlog='EDIT' where pk=?";
		this.executeUpdateBySql(sql, new Object[]{managercommi,mainbusimancommi,declaremancommi,checkmancommi,deptfundcommi,totalcommi,lastupdatedby,pk});
	}
	/**
	 * 更新独立销售明细
	 * 
	 * @param pk
	 * @return
	 */
	public void doUpdateDl(Integer pk,Double businessmancommi,String lastupdatedby) {
		String sql = " update tMainCommiDetail_IndeSale set businessmancommi=?,ismodified='1',lastupdate=getdate(),lastupdatedby=?,actionlog='EDIT' where pk=?";
		this.executeUpdateBySql(sql, new Object[]{businessmancommi,lastupdatedby,pk});
	}
	/**
	 *非独立销售报表
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> goFdlReportJqGrid(Page<Map<String,Object>> page, MainCommi mainCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from( "
			+"select sum(MainBusiManCommi)commi ,'主材管家' role,b.NameChi name,cp.Desc2 CmpDescr  "
			+"from tMainCommiDetail a  "
			+"left join tEmployee b on a.MainBusiMan=b.Number "
			+"left join tCustomer c on a.CustCode=c.Code "
			+"left join tCustType d on c.CustType=d.Code "
			+"left join tBuilder bd on bd.Code=c.BuilderCode "
			+"left join tRegion r on r.Code=bd.RegionCode "
			+"left join tCompany cp on cp.code = r.CmpCode "
			+"where 1=1 ";
			if(StringUtils.isNotBlank(mainCommi.getNo())){
				sql+=" and a.No=?";
				list.add(mainCommi.getNo());
			}
			if(StringUtils.isNotBlank(mainCommi.getCustType())){
				sql+=" and c.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"')";
			}
			if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
				sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
			}
			sql+=" group by MainBusiMan,b.NameChi,cp.Desc2 union all "
			+"select sum(ManagerCommi)commi ,'经理' role,'经理' name,cp.Desc2 CmpDescr "
			+"from tMainCommiDetail a " 
			+"left join tCustomer c on a.CustCode=c.Code "
			+"left join tCustType d on c.CustType=d.Code "
			+"left join tBuilder bd on bd.Code=c.BuilderCode "
			+"left join tRegion r on r.Code=bd.RegionCode "
			+"left join tCompany cp on cp.code = r.CmpCode "
			+"where 1=1 ";
			if(StringUtils.isNotBlank(mainCommi.getNo())){
				sql+=" and a.No=?";
				list.add(mainCommi.getNo());
			}
			if(StringUtils.isNotBlank(mainCommi.getCustType())){
				sql+=" and c.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"') ";
			}
			if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
				sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
			}
			sql+=" group by cp.Desc2 union all "
			+"select sum(DeclareManCommi)commi,'报单' role,'报单' name,cp.Desc2 CmpDescr "
			+"from tMainCommiDetail a " 
			+"left join tCustomer c on a.CustCode=c.Code "
			+"left join tCustType d on c.CustType=d.Code "
			+"left join tBuilder bd on bd.Code=c.BuilderCode "
			+"left join tRegion r on r.Code=bd.RegionCode "
			+"left join tCompany cp on cp.code = r.CmpCode "
			+"where 1=1 ";
			if(StringUtils.isNotBlank(mainCommi.getNo())){
				sql+=" and a.No=?";
				list.add(mainCommi.getNo());
			}
			if(StringUtils.isNotBlank(mainCommi.getCustType())){
				sql+=" and c.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"') ";
			}
			if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
				sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
			}
			sql+=" group by cp.Desc2 union all "
			+"select sum(CheckManCommi)commi,'结算' role,'结算' name,cp.Desc2 CmpDescr "
			+"from tMainCommiDetail a " 
			+"left join tCustomer c on a.CustCode=c.Code "
			+"left join tCustType d on c.CustType=d.Code "
			+"left join tBuilder bd on bd.Code=c.BuilderCode "
			+"left join tRegion r on r.Code=bd.RegionCode "
			+"left join tCompany cp on cp.code = r.CmpCode "
			+"where 1=1 ";
			if(StringUtils.isNotBlank(mainCommi.getNo())){
				sql+=" and a.No=?";
				list.add(mainCommi.getNo());
			}
			if(StringUtils.isNotBlank(mainCommi.getCustType())){
				sql+=" and c.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"') ";
			}
			if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
				sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
			}
			sql+=" group by cp.Desc2 union all "
			+"select sum(DeptFundCommi)commi,'部门基金' role,'部门基金' name,cp.Desc2 CmpDescr "
			+"from tMainCommiDetail a " 
			+"left join tCustomer c on a.CustCode=c.Code "
			+"left join tCustType d on c.CustType=d.Code "
			+"left join tBuilder bd on bd.Code=c.BuilderCode "
			+"left join tRegion r on r.Code=bd.RegionCode "
			+"left join tCompany cp on cp.code = r.CmpCode "
			+"where 1=1  ";
			if(StringUtils.isNotBlank(mainCommi.getNo())){
				sql+=" and a.No=?";
				list.add(mainCommi.getNo());
			}
			if(StringUtils.isNotBlank(mainCommi.getCustType())){
				sql+=" and c.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"') ";
			}
			if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
				sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
			}
			sql+=" group by cp.Desc2 ) a order by CmpDescr,a.role desc,a.name asc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 *独立销售报表
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> goDlReportJqGrid(Page<Map<String,Object>> page, MainCommi mainCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select '业务员' role,b.NameChi name,sum(BusinessManCommi)commi,c.Desc1 Department1Descr "
			+"from tMainCommiDetail_IndeSale a "
			+"left join tEmployee b on a.BusinessMan=b.Number "
			+"left join tDepartment1 c on b.Department1=c.Code "
			+"left join tCustomer d on a.CustCode=d.Code "
			+"left join tBuilder bd on bd.Code=d.BuilderCode "
			+"left join tRegion r on r.Code=bd.RegionCode "
			+"left join tCompany cp on cp.code = r.CmpCode "
			+ " where 1=1 and a.CheckAmount>0 ";
		if(StringUtils.isNotBlank(mainCommi.getNo())){
			sql+=" and a.No=?";
			list.add(mainCommi.getNo());
		}
		if(StringUtils.isNotBlank(mainCommi.getCustType())){
			sql+=" and d.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
			sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
		}
		sql+=" group by b.NameChi,c.Desc1  ";
		
		sql +=" union all select '主材管家' role,b.NameChi name, sum(MainBusiManCommi)commi,c.Desc1 Department1Descr "
			+"from tMainCommiDetail_IndeSale a "
			+"left join tEmployee b on a.MainBusiMan=b.Number "
			+"left join tDepartment1 c on b.Department1=c.Code "
			+"left join tCustomer d on a.CustCode=d.Code "
			+"left join tBuilder bd on bd.Code=d.BuilderCode "
			+"left join tRegion r on r.Code=bd.RegionCode "
			+"left join tCompany cp on cp.code = r.CmpCode "
			+ " where 1=1 and a.CheckAmount>0 ";
		if(StringUtils.isNotBlank(mainCommi.getNo())){
			sql+=" and a.No=?";
			list.add(mainCommi.getNo());
		}
		if(StringUtils.isNotBlank(mainCommi.getCustType())){
			sql+=" and d.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
			sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
		}
		sql+=" group by b.NameChi,c.Desc1  ";
		
		sql +=" union all select '经理' role,'经理' name, sum(ManagerCommi)commi,'' Department1Descr "
			+"from tMainCommiDetail_IndeSale a "
			+"left join tCustomer d on a.CustCode=d.Code "
			+"left join tBuilder bd on bd.Code=d.BuilderCode "
			+"left join tRegion r on r.Code=bd.RegionCode "
			+"left join tCompany cp on cp.code = r.CmpCode "
			+ " where 1=1 and a.CheckAmount>0 ";
		if(StringUtils.isNotBlank(mainCommi.getNo())){
			sql+=" and a.No=?";
			list.add(mainCommi.getNo());
		}
		if(StringUtils.isNotBlank(mainCommi.getCustType())){
			sql+=" and d.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
			sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
		}
		
		sql +=" union all select '报单' role,isNull(b.NameChi,'报单') name, sum(DeclareManCommi) commi,c.Desc1 Department1Descr "
				+"from tMainCommiDetail_IndeSale a "
				+"left join tCustomer d on a.CustCode=d.Code "
				+"left join tBuilder bd on bd.Code=d.BuilderCode "
				+"left join tRegion r on r.Code=bd.RegionCode "
				+"left join tCompany cp on cp.code = r.CmpCode "
				+"left join tEmployee b on a.DeclareMan=b.Number "
				+"left join tDepartment1 c on b.Department1=c.Code "
				+ " where 1=1 and a.CheckAmount>0 ";
		if(StringUtils.isNotBlank(mainCommi.getNo())){
			sql+=" and a.No=?";
			list.add(mainCommi.getNo());
		}
		if(StringUtils.isNotBlank(mainCommi.getCustType())){
			sql+=" and d.CustType in ('"+mainCommi.getCustType().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(mainCommi.getCmpCode())){
			sql+=" and r.CmpCode in ('"+mainCommi.getCmpCode().replace(",", "','")+"')";
		}
		sql+=" group by b.NameChi,c.Desc1  ";
		sql+=") a order by a.role desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}

