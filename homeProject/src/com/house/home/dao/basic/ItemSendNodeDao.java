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
import com.house.home.entity.basic.ItemSendNode;
import com.house.home.entity.project.FixDuty;

@SuppressWarnings("serial")
@Repository
public class ItemSendNodeDao extends BaseDao {

	/**
	 * ItemSendNode分页信息
	 * 
	 * @param page
	 * @param itemSendNode
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSendNode itemSendNode) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Code,a.Descr,a.Type,x1.NOTE TypeDescr,a.WorkerClassify,x2.NOTE WorkerClassifyDescr,a.ItemType1, "
				+" b.Descr ItemType1Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from  tItemSendNode a "
				+" left join tXTDM x1 on a.Type=x1.CBM and x1.ID='SENDNODETYPE' "
				+" left join tXTDM x2 on a.WorkerClassify=x2.CBM and x2.ID='WORKERCLASSIFY' "
				+" left join tItemType1 b on a.ItemType1=b.Code "
				+" where 1=1 ";

    	if (StringUtils.isNotBlank(itemSendNode.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+itemSendNode.getCode()+"%");
		}
    	if (StringUtils.isNotBlank(itemSendNode.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+itemSendNode.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(itemSendNode.getType())) {
			sql += " and a.Type=? ";
			list.add(itemSendNode.getType());
		}
    	if (StringUtils.isNotBlank(itemSendNode.getWorkerClassify())) {
			sql += " and a.WorkerClassify=? ";
			list.add(itemSendNode.getWorkerClassify());
		}
    	if (StringUtils.isNotBlank(itemSendNode.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemSendNode.getItemType1());
		}
		if (StringUtils.isBlank(itemSendNode.getExpired()) || "F".equals(itemSendNode.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPageBySqlForDetail(Page<Map<String,Object>> page, ItemSendNode itemSendNode) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Code,a.ConfItemType,b.Descr ConfItemTypeDescr,a.BeginNode,c.Descr BeginNodeDescr, "
				+" a.BeginDateType,x1.NOTE BeginDateTypeDescr,a.BeginAddDays,a.EndNode,d.Descr EndNodeDescr,a.EndDateType, "
				+" x2.NOTE EndDateTypeDescr,a.EndAddDays,a.PayNum,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+" from tItemSendNodeDetail a "
				+" left join tConfItemType b on a.ConfItemType=b.Code "
				+" left join tPrjItem1 c on a.BeginNode=c.Code "
				+" left join tXTDM x1 on a.BeginDateType=x1.CBM and x1.ID='ALARMDAYTYPE' " 
				+" left join tPrjItem1 d on a.EndNode=d.Code "
				+" left join tXTDM x2 on a.EndDateType=x2.CBM and x2.ID='ALARMDAYTYPE' " 
				+" where 1=1 and a.Code = ?";
		list.add(itemSendNode.getCode());
		if (StringUtils.isBlank(itemSendNode.getExpired()) || "F".equals(itemSendNode.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("deprecation")
	public Result saveForProc(ItemSendNode itemSendNode, String xml) {
		Assert.notNull(itemSendNode);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemSendNode_forXml(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemSendNode.getM_umState());
			call.setString(2, itemSendNode.getCode());
			call.setString(3, itemSendNode.getDescr());
			call.setString(4, itemSendNode.getType());
			call.setString(5, itemSendNode.getWorkerClassify());
			call.setString(6, itemSendNode.getItemType1());
			call.setString(7, itemSendNode.getLastUpdatedBy());
			call.setString(8, xml);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(9)));
			result.setInfo(call.getString(10));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
}

