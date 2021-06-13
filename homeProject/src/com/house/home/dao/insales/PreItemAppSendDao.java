package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.PreItemAppSend;

@SuppressWarnings("serial")
@Repository
public class PreItemAppSendDao extends BaseDao {

	/**
	 * PreItemAppSend分页信息
	 * 
	 * @param page
	 * @param preItemAppSend
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PreItemAppSend preItemAppSend, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select " +
					"a.no,  a.iano ,c.address,d.Descr CustName,d.Code CustCode,a.status,t4.note StatusDescr, " +
					"b1.itemType1,b2.Descr ,a.whcode,e.desc1,a.Remarks,a.date, " +
					"a.SendDate,a.SendCZY,h.namechi SendApp,a.appCZY,j.namechi AppCzyDescr, " +
					"a.lastupdate,a.lastUpdatedBy,a.Actionlog,it2.Descr itemtype2Descr,sn.SendBatchNo, " +
					"b.DelivType ItemAppDelivType " +
					"from  tpreitemAppSend a " +
					"left outer join titemapp b on b.no=a.IAno " +
					"left outer join tcustomer c on c.code=b.custcode " +
					"left outer join titemapp b3 on a.IAno=b3.no " +
					"left outer join tcustomer d on b3.custcode=d.code " +
					"left join txtdm t4 on a.status=t4.IBM and t4.id ='PRESENDSTATUS' " +
					"left outer join txtdm f on a.status=f.cbm and f.id='PRESENDSTATUS' " +
					"left outer join titemapp b1 on a.IANo=b1.no " +
					"left outer join tItemType1 b2 on b1.itemtype1 =b2.code " +
					"left outer join tWareHouse e on a.WHCode=e.Code " +
					"left outer join tEmployee h on a.SendCZY=h.Number " +
					"left outer join tEmployee j on a.appCZY=j.Number " +
					"left outer join tItemType2 it2 on  b1.itemtype2 = it2.code " +
					"left outer join ( " +
					"	select max(in_c.SendBatchNo) SendBatchNo, in_a.No" +
					"	from tPreItemAppSendDetail in_a" +
					"	inner join tItemAppSendDetail in_b on in_b.RefPk = in_a.RefPk" +
					"	inner join tItemAppSend in_c on in_c.No = in_b.No" +
					"	group by in_a.no " +
					") sn on sn.no = a.No "  +
					"where 1=1 ";
    	if (StringUtils.isNotBlank(preItemAppSend.getNo())) {
			sql += " and a.No=? "; 
			list.add(preItemAppSend.getNo());
		}
    	if (StringUtils.isNotBlank(preItemAppSend.getStatus())) {
			sql += " and a.Status in " + "('"+preItemAppSend.getStatus().replaceAll(",", "','")+"')";
		}
    	if (StringUtils.isNotBlank(preItemAppSend.getItemType1())) {
			sql += " and b1.ItemType1=? ";
			list.add(preItemAppSend.getItemType1());
		}else{
			String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and b1.ItemType1 in("
					+ itemRight.substring(0, itemRight.length() - 1) + ") ";	
			
		} 
    	if (StringUtils.isNotBlank(preItemAppSend.getIaNo())) {
			sql += " and a.IANo=? ";
			list.add(preItemAppSend.getIaNo());
		}
    	if (StringUtils.isNotBlank(preItemAppSend.getWhCode())) {
			sql += " and a.WHCode=? ";
			list.add(preItemAppSend.getWhCode());
		}
    	if (StringUtils.isNotBlank(preItemAppSend.getAddress())) {
			sql += " and c.address like ? ";
			list.add("%"+preItemAppSend.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(preItemAppSend.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(preItemAppSend.getAppCzy());
		}
    	if (preItemAppSend.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(preItemAppSend.getDateFrom());
		}
		if (preItemAppSend.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(preItemAppSend.getDateTo()).getTime()));
		}
    	if (StringUtils.isNotBlank(preItemAppSend.getSendCzy())) {
			sql += " and a.SendCZY=? ";
			list.add(preItemAppSend.getSendCzy());
		}
    	if (preItemAppSend.getSendDate() != null) {
			sql += " and a.SendDate=? ";
			list.add(preItemAppSend.getSendDate());
		}
    	if (preItemAppSend.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(preItemAppSend.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(preItemAppSend.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(preItemAppSend.getLastUpdatedBy());
		}
//		if (StringUtils.isBlank(preItemAppSend.getExpired()) || "F".equals(preItemAppSend.getExpired())) {
//			sql += " and a.Expired='F' ";
//		}
    	if (StringUtils.isNotBlank(preItemAppSend.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(preItemAppSend.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 材料信息
	 * @author	created by zb
	 * @date	2019-4-9--下午6:17:39
	 * @param page
	 * @param preItemAppSend 是“A”的话就不查
	 * @return
	 */
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, PreItemAppSend preItemAppSend) {
		List<Object> list = new ArrayList<Object>();
		String sql = "";
		if ("F".equals(preItemAppSend.getM_umState()) || "S".equals(preItemAppSend.getM_umState())) {
			sql +=	"select a.SendQty-a.HasSendQty SendQty, ";
		} else {
			sql +=	"select a.SendQty, ";
		}
			sql +=	"a.PK,a.No,a.RefPK,b.ItemCode,b.qty,b.SendQty AllSendQty, " +
					"c.descr itemdescr,d.descr FixAreaDescr,isnull(f.Qty, 0) AppQty, " +
					"a.SendQty ThisAppSendQty,isnull(a.HasSendQty,0)HasSendQty, " +
					"c.SqlCode,g.Descr SqlDescr," +
					"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
					"from ( " ;
		// 增加新数据时获取更多详细
		if (StringUtils.isNotBlank(preItemAppSend.getNo()) && null != preItemAppSend.getRefPk() && null != preItemAppSend.getSendQty()) {
			//将'HasSendQty=null'改成0，因为'1<>null'为false modify by zb on 20190924
			sql +=	"	select PK='',No=?,RefPK=?,SendQty=?,HasSendQty=0,LastUpdate='',LastUpdatedBy='',Expired='',ActionLog='' " ;
			list.add(preItemAppSend.getNo());
			list.add(preItemAppSend.getRefPk());
			list.add(preItemAppSend.getSendQty());
		} else {
			sql +=	"	select PK,No,RefPK,SendQty,LastUpdate,LastUpdatedBy,Expired,ActionLog,HasSendQty " +
					"	FROM tPreItemAppSendDetail ";
			if ("A".equals(preItemAppSend.getM_umState())) { //状态为新增时只获取表格
				sql +=  "where 1<>1 ";
			} else {
				sql +=  "where No=? ";
				list.add(preItemAppSend.getNo());
			}
		}
			sql +=	") a " +
					"left join  tItemAppDetail b on a.RefPK=b.PK " +
					"left join tItem c on b.ItemCode=c.code " +
					"left outer join tFixArea d on b.fixareaPK=d.PK " +
					"left outer join ( " +
					"	select sum(in_a.SendQty) Qty, in_a.RefPK from tPreItemAppSendDetail in_a " +
					"	inner join tPreItemAppSend in_b on in_a.No=in_b.No " +
					"	where in_b.Status = '2' " +
					"	group by in_a.RefPK " +
					") f on f.RefPK=a.RefPK " +
					"left join tBrand g on g.Code=c.SqlCode ";
		if ("F".equals(preItemAppSend.getM_umState()) || "S".equals(preItemAppSend.getM_umState())) {
			sql +=	"where a.SendQty<>a.HasSendQty ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 保存存储过程
	 * @author	created by zb
	 * @date	2019-4-12--上午11:12:19
	 * @param preItemAppSend
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(PreItemAppSend preItemAppSend) {
		Assert.notNull(preItemAppSend);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCkfhsq_forXml(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, preItemAppSend.getM_umState());
			call.setString(2, preItemAppSend.getNo());
			call.setString(3, preItemAppSend.getStatus());
			call.setString(4, preItemAppSend.getIaNo());
			call.setString(5, preItemAppSend.getWhCode());
			call.setString(6, preItemAppSend.getRemarks());
			call.setString(7, preItemAppSend.getAppCzy());
			call.setString(8, preItemAppSend.getLastUpdatedBy());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.setString(11, preItemAppSend.getDetailJson());
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
	/**
	 * 仓库分批发货存储过程
	 * @author	created by zb
	 * @date	2019-4-19--下午2:41:29
	 * @param preItemAppSend
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doCkfhsqSend(PreItemAppSend preItemAppSend) {
		Assert.notNull(preItemAppSend);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCkfhsqSend(?,?,?,?,?)}");
			call.setString(1, preItemAppSend.getNo());
			call.setString(2, preItemAppSend.getSendCzy());
			call.setString(3, preItemAppSend.getSendBachNo());
			call.setString(4, preItemAppSend.getWhCode());
			call.setString(5, preItemAppSend.getDelivType());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			result.setCode(list.get(0).get("ret").toString());
			result.setInfo(list.get(0).get("errmsg").toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 判断是否部分发货
	 * @author	created by zb
	 * @date	2019-4-23--下午2:53:33
	 * @param preItemAppSend
	 * @return
	 */
	public List<Map<String, Object>> isHaveSend(PreItemAppSend preItemAppSend) {
		String sql = "select 1 from  tpreitemAppSenddetail where hasSendQty>0 and  No=? ";
		return this.findBySql(sql, new Object[]{preItemAppSend.getNo()});
	}
	/**
	 * 部分发货
	 * @author	created by zb
	 * @date	2019-4-24--下午3:53:23
	 * @param preItemAppSend
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doCkfhsqSendByPart(PreItemAppSend preItemAppSend) {
		Assert.notNull(preItemAppSend);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCkfhsqSendByPart_forXml(?,?,?,?,?,?,?,?)}");
			call.setString(1, preItemAppSend.getNo());
			call.setString(2, preItemAppSend.getSendCzy());
			call.setString(3, preItemAppSend.getSendBachNo());
			call.setString(4, preItemAppSend.getWhCode());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, preItemAppSend.getDetailJson());
			call.setString(8, preItemAppSend.getDelivType());
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
	 * 仓库发货app--获取list
	 * @author	created by zb
	 * @date	2019-9-17--上午10:49:29
	 * @param page
	 * @param id
	 * @param address
	 * @param itemType1 
	 * @param uc 
	 * @return
	 */
	public Page<Map<String, Object>> findWarehouseSendList(
			Page<Map<String, Object>> page, String id, String address, String itemType1, UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.No,a.IANo,a.LastUpdatedBy,a.Date SendDate,a.WHCode,e.Desc1 WHDescr,b.CustCode,b.ItemType1, "
					+"d1.Descr ItemType1Descr,ItemType2,d2.Descr ItemType2Descr,c.Address,f.SendQtyTotal "
					+"from tItemAppSend a  "
					+"left join tItemApp b on b.no=a.IAno "
					+"left join tCustomer c on c.Code=b.CustCode "
					+"left join tItemType1 d1 on d1.Code=b.ItemType1 "
					+"left join tItemType2 d2 on d2.Code=b.ItemType2 "
					+"left join tWareHouse e on e.Code=a.WHCode "
					+"left join ( "
					+"	select in_a.No,sum(in_a.SendQty) SendQtyTotal "
					+"	from tItemAppSendDetail in_a "
					+"	where in_a.Expired='F' "
					+"	group by in_a.No "
					+")f on f.No=a.No "
					+"where 1=1 and a.LastUpdatedBy=? and a.Date>=? and a.Date<=? ";
		list.add(id);
//		DateUtil.DateFormatString("2019-09-24 00:00:00")
		list.add(new Timestamp(DateUtil.startOfTheDay(new Date()).getTime()));
		list.add(new Timestamp(DateUtil.endOfTheDay(new Date()).getTime()));
    	if (StringUtils.isNotBlank(address)) {
			sql += " and c.Address like ? "; 
			list.add("%"+address+"%");
		}
    	if (StringUtils.isNotBlank(itemType1)) {
			sql += " and b.ItemType1=? ";
			list.add(itemType1.trim());
		}else{
			String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and b.ItemType1 in(" + itemRight.substring(0, itemRight.length() - 1) + ") ";	
			
		} 
    	sql += "order by a.Date desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
}
