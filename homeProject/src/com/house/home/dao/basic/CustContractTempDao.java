package com.house.home.dao.basic;

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
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.CustContractTemp;

@SuppressWarnings("serial")
@Repository
public class CustContractTempDao extends BaseDao {

	/**
	 * CustContractTemp分页信息
	 * 
	 * @param page
	 * @param custContractTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustContractTemp custContractTemp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,x2.Note CustTypeDescr,a.Descr,a.Version,x1.Note TypeDescr, " +
				" a.FileName,(a.Descr+a.Version) fileName1,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog," +
				" a.custType,a.type,b.Descr BuilderDescr " +
				" from tCustContractTemp a " +
				" left join tXTDM x1 on x1.CBM = a.Type and x1.ID = 'CUSTCONTEMPTYPE' " + 
				" left join tXTDM x2 on x2.CBM = a.CustType and x2.ID = 'CUSTTYPE' " +
				" left join tBuilder b on a.BuilderCode = b.Code "+
				" where 1=1 ";

		if(StringUtils.isNotBlank(custContractTemp.getStatus())){
			sql +=" and a.status = ? ";
			list.add(custContractTemp.getStatus());
		}
    	if (custContractTemp.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custContractTemp.getPk());
		}
    	if (StringUtils.isNotBlank(custContractTemp.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(custContractTemp.getCustType());
		}
    	if (StringUtils.isNotBlank(custContractTemp.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+custContractTemp.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(custContractTemp.getVersion())) {
			sql += " and a.Version=? ";
			list.add(custContractTemp.getVersion());
		}
    	if (StringUtils.isNotBlank(custContractTemp.getType())) {
			sql += " and a.Type=? ";
			list.add(custContractTemp.getType());
		}
    	if (StringUtils.isNotBlank(custContractTemp.getFileName())) {
			sql += " and a.FileName=? ";
			list.add(custContractTemp.getFileName());
		}
    	if (custContractTemp.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custContractTemp.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custContractTemp.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custContractTemp.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(custContractTemp.getExpired()) || "F".equals(custContractTemp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custContractTemp.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custContractTemp.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 保存
	 * 
	 * @param custContractTemp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(CustContractTemp custContractTemp) {
		Assert.notNull(custContractTemp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustContractTemp(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, custContractTemp.getPk() == null ? 0 : custContractTemp.getPk());
			call.setString(2, custContractTemp.getM_umState());	
			call.setString(3, custContractTemp.getCustType());
			call.setString(4, custContractTemp.getDescr());
			call.setString(5, custContractTemp.getVersion());
			call.setString(6, custContractTemp.getType());
			call.setString(7, custContractTemp.getFileName());
			call.setString(8, custContractTemp.getRemarks());
			call.setString(9, custContractTemp.getLastUpdatedBy());
			call.setString(10, custContractTemp.getCustContractTempFieldJson());
			call.setString(11, custContractTemp.getExpired());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.setString(14, custContractTemp.getBuilderCode());
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public Page<Map<String, Object>> goDetailJqGrid(Page<Map<String, Object>> page, CustContractTemp custContractTemp) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.PK,a.ConTempPK,a.Code,a.Expression,a.Remarks fieldRemarks," +
					 " a.DispSeq,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
					 " from tCustContractTempField a " +
					 " where a.ConTempPK = ? " +
					 " order by a.ConTempPK" ;
		list.add(custContractTemp.getPk());

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getCustContractTempFileName(Integer pk) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String sql = " select fileName,pk from tCustContractTemp where pk = ?";
		list = this.findBySql(sql, new Object[]{ pk});
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}

