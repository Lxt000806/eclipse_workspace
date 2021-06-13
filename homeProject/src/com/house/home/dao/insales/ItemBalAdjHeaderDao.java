package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.components.barbecue.BarcodeProviders.UCC128Provider;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemBalAdjHeader;
@SuppressWarnings("serial")
@Repository
public class ItemBalAdjHeaderDao extends BaseDao {


	/**
	 * 仓库调整分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBalAdjHeader itemBalAdjHeader,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.No, a.WHCode, a.Date, a.AdjType, a.AdjReason, b.NOTE AdjTypeDescr, " +
				"c.NOTE AdjReasonDescr, a.Remarks, a.LastUpdate, a.LastUpdatedBy, " 
				+" a.Expired, a.ActionLog, a.Status, d.NOTE StatusDescr, a.AppEmp, e.NameChi AppEmpDescr, a.AppDate, a.ConfirmEmp," +
				" f.NameChi ConfirmEmpDescr, a.ConfirmDate, a.DocumentNo " 
				+ "from tItemBalAdjHeader a " 
				+ " left outer join tXTDM b on Rtrim(a.AdjType)=b.CBM and b.ID='ADJTYP' " 
				+ " left outer join tXTDM c on a.AdjReason=c.CBM and c.ID='AdjReason' " 
				+ " left outer join tXTDM d on a.Status=d.CBM and d.ID='BALADJSTATUS' " 
				+ " left outer join tEmployee e on e.Number=a.AppEmp " 
				+ " left outer join tEmployee f on f.Number=a.ConfirmEmp where a.Expired = 'F' " +
				" and a.WHCode in (select WHCode from dbo.tWareHouseOperater where CZYBH = ?)";
				list.add(uc.getCzybh());		 
		
		if (StringUtils.isNotBlank(itemBalAdjHeader.getNo())) {
			sql += " and a.No=? ";
			list.add(itemBalAdjHeader.getNo());
		}
		if (StringUtils.isNotBlank(itemBalAdjHeader.getStatus())) {
			sql += " and a.Status in " + "('"+itemBalAdjHeader.getStatus().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(itemBalAdjHeader.getDocumentNo())) {// 增加凭证号查询条件 add by zb
			sql += " and a.DocumentNo like ? ";
			list.add("%"+itemBalAdjHeader.getDocumentNo()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
		} else {
			sql += ") a order by a.AppDate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	/**
	 * 仓库调整新增保存
	 */
	@SuppressWarnings("deprecation")
	public Result doItemBalAdjHeaderSave(ItemBalAdjHeader itemBalAdjHeader) {
		Assert.notNull(itemBalAdjHeader);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pckbd_tItemWHBal_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemBalAdjHeader.getNo());
			call.setString(2, "A");
			call.setString(3, itemBalAdjHeader.getWhCode());
			call.setTimestamp(4, itemBalAdjHeader.getDate() == null ? null : new Timestamp(itemBalAdjHeader.getDate().getTime()));
			call.setString(5, itemBalAdjHeader.getAdjType());
			call.setString(6, itemBalAdjHeader.getAdjReason());
			call.setString(7, itemBalAdjHeader.getRemarks());
			call.setString(8, itemBalAdjHeader.getStatus());
			call.setString(9, itemBalAdjHeader.getAppEmp());
			call.setString(10, itemBalAdjHeader.getConfirmEmp());
			call.setString(11, itemBalAdjHeader.getDocumentNo());
			call.setString(12, itemBalAdjHeader.getLastUpdatedBy());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);
			call.setString(15, itemBalAdjHeader.getItemBalAdjDetailXml());
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
	
	/**
	 * 仓库调整编辑保存
	 */
	@SuppressWarnings("deprecation")
	public Result doItemBalAdjHeaderUpdate(ItemBalAdjHeader itemBalAdjHeader) {
		Assert.notNull(itemBalAdjHeader);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pckbd_tItemWHBal_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemBalAdjHeader.getNo());
			call.setString(2, "M");
			call.setString(3, itemBalAdjHeader.getWhCode());
			call.setTimestamp(4, itemBalAdjHeader.getDate() == null ? null : new Timestamp(itemBalAdjHeader.getDate().getTime()));
			call.setString(5, itemBalAdjHeader.getAdjType());
			call.setString(6, itemBalAdjHeader.getAdjReason());
			call.setString(7, itemBalAdjHeader.getRemarks());
			call.setString(8, itemBalAdjHeader.getStatus());
			call.setString(9, itemBalAdjHeader.getAppEmp());
			call.setString(10, itemBalAdjHeader.getLastUpdatedBy());
			call.setString(11, itemBalAdjHeader.getDocumentNo());
			call.setString(12, itemBalAdjHeader.getLastUpdatedBy());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);
			call.setString(15, itemBalAdjHeader.getItemBalAdjDetailXml());
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
	
	/**
	 * 仓库调整审核
	 */
	@SuppressWarnings("deprecation")
	public Result doItemBalAdjHeaderVerify(ItemBalAdjHeader itemBalAdjHeader,String status) {
		Assert.notNull(itemBalAdjHeader);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pckbd_tItemWHBal_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemBalAdjHeader.getNo());
			call.setString(2, "C");
			call.setString(3, itemBalAdjHeader.getWhCode());
			call.setTimestamp(4, itemBalAdjHeader.getDate() == null ? null : new Timestamp(itemBalAdjHeader.getDate().getTime()));
			call.setString(5, itemBalAdjHeader.getAdjType());
			call.setString(6, itemBalAdjHeader.getAdjReason());
			call.setString(7, itemBalAdjHeader.getRemarks());
			call.setString(8, status);
			call.setString(9, itemBalAdjHeader.getAppEmp());
			call.setString(10, itemBalAdjHeader.getLastUpdatedBy());
			call.setString(11, itemBalAdjHeader.getDocumentNo());
			call.setString(12, itemBalAdjHeader.getLastUpdatedBy());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);
			call.setString(15, itemBalAdjHeader.getItemBalAdjDetailXml());
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
