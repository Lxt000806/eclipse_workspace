package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.MeasureRoomEvt;

@SuppressWarnings("serial")
@Repository
public class MeasureRoomDao extends BaseDao{

	
	public Page<Map<String, Object>> getMeasureRoomList(
			Page<Map<String, Object>> page, MeasureRoomEvt evt) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select c.Address,a.CrtDate MeasureDate,a.No,a.Pk SignInPk from tSignIn a "
				 	+" left join tSignInType2 b on b.Code = a.SignInType2 "
				 	+" left join tCustomer c on a.CustCode = c.Code "
				 	+" where b.Code='51' and a.SignCZY = ? ";
		params.add(evt.getCzybh());
		if(StringUtils.isNotEmpty(evt.getAddress())){
			sql += " and c.Address like ? ";
			params.add("%"+evt.getAddress()+"%");
		}
		
		sql += "order by a.CrtDate desc";
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_custRight(
			Page<Map<String, Object>> page, MeasureRoomEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Code CustCode,a.Address from tcustomer a "
					+" left join tCustStakeholder b on a.Code=b.CustCode and b.Role = '00' "
					+" left join tCustStakeholder c on a.Code=c.CustCode and c.Role = '02' "
					+" left join tEmployee d on b.EmpCode = d.Number "
					+" where a.Status not in ('4','5') and ( ? = b.EmpCode or ? =c.EmpCode or d.Department2 = (select in_a.Department2 from tEmployee in_a where in_a.Number = ?)) and a.Expired='F' ";
		list.add(evt.getCzybh());
		list.add(evt.getCzybh());
		list.add(evt.getCzybh());
		if(StringUtils.isNotEmpty(evt.getAddress())){
			sql += " and a.Address like ? ";
			list.add("%"+evt.getAddress()+"%");
		}
		sql+="order by a.LastUpdate desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> getMeasureRoomPhotoList(MeasureRoomEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select c.pk,c.photoName,isnull(c.isSendYun, '0') isSendYun,b.Remarks,a.Code CustCode,a.Address,b.CrtDate MeasureDate,a.PlanMeasure PlanMeasureDate,c.PhotoName" 
					+" from tCustomer a "
					+" left join tSignIn b on a.Code=b.CustCode "
					+" left join tSignInPic c on b.no=c.No and c.Expired = 'F' "
					+" where b.no = ? ";
		list.add(evt.getNo());
		return this.findBySql(sql, list.toArray());
	}
}
