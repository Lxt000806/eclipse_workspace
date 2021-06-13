package com.house.home.dao.mall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.MallAppletsEvt;

@SuppressWarnings("serial")
@Repository
public class MallAppletsDao extends BaseDao {
	
	public Page<Map<String, Object>> getWorkerList(
			Page<Map<String, Object>> page, MallAppletsEvt mallAppletsEvt){
		List<Object> list = new ArrayList<Object>();	
		String sql = " select a.Code, a.NameChi, a.WorkType12, b.Descr WorkType12Descr, c.Score, a.PersonalProfile, d.AcceptNumber, a.AvatarPic "
					+" from  tWorker a "
					+" left join tWorkType12 b on a.WorkType12 = b.Code "
					+" left join (select WorkerCode,Convert(decimal(18,1),avg(Score*1.0)) score "  
					+" 			from tWorkerEval "
					+" 			group by WorkerCode) c on a.Code = c.WorkerCode "
					+" left join (select in_c.WorkerCode,count(in_b.PK) AcceptNumber from (select in_a.CustCode, max(in_a.PK) PK from  tCustWorker in_a "
					+" 			where  in_a.WorkType12 = (select WorkType12 from tWorker where Code = in_a.WorkerCode) "
					+" 			and in_a.EndDate is not null and in_a.EndDate <> '' group by in_a.CustCode) in_b "
					+" 			left join tCustWorker in_c on in_b.PK = in_c.PK "	
					+" 			group by in_c.WorkerCode) d on d.WorkerCode = a.Code "
					+" where a.IsRegisterMall = '1' ";

		if(StringUtils.isNotBlank(mallAppletsEvt.getKeyWords())){
			sql += " and (a.NameChi like ? or b.Descr like ? ) ";
			list.add("%" + mallAppletsEvt.getKeyWords() + "%");
			list.add("%" + mallAppletsEvt.getKeyWords() + "%");
		}
		
		sql +=" order by c.Score desc ";
		return findPageBySql(page, sql, list.toArray());
	}
}
