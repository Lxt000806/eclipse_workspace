package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BuilderNum;

@SuppressWarnings("serial")
@Repository
public class BuilderNumDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BuilderNum builderNum) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  a.*,c.descr builderDescr from tBuilderNum a  "
				+ " left  join tBuilderDeliv b on b.Code=a.BuilderDelivCode"
				+ " left join tbuilder c on c.code=b.builderCode "
				+ " where 1=1  ";
		if (StringUtils.isNotBlank(builderNum.getBuilderNum())) {
			sql += " and a.builderNum like ? ";
			list.add("%" + builderNum.getBuilderNum() + "%");
		}
		if (builderNum.getPk() != null) {
			sql += "and a.PK= ? ";
			list.add(builderNum.getPk());
		}
		if (StringUtils.isNotBlank(builderNum.getBuilderCode())) {
			sql += " and c.code= ?";
			list.add(builderNum.getBuilderCode());
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 根据BuilderDelivCode查BuilderNum
	 * 
	 * @param page
	 * @param builderDelivCode
	 * @return  
	 */
	public Page<Map<String, Object>> findNumByCode(
			Page<Map<String, Object>> page, String builderDelivCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select pk, BuilderNum, BuilderDelivCode,LastUpdate ,LastUpdatedBy ,Expired,substring('00000000',1,8-len(BuilderNum))+BuilderNum ordername  "
				+ " from tBuilderNum "
				+ " where  BuilderDelivCode=? and Expired='F' "
				+ " order by BuilderNum";
		list.add(builderDelivCode);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 批量增加楼号信息
	 * 
	 * @param qz 前缀
	 * @param beginNum 开始序号
	 * @param endNum 结束序号
	 * @param builderDelivCode 批次号
	 * @param lastUpdatedBy 最后修改人
	 */
	public void multiAdd(String qz, Integer beginNum, Integer endNum,String builderDelivCode,String lastUpdatedBy) {
		List<Object> list = new ArrayList<Object>();
		String sql = "insert into tBuilderNum(BuilderNum,BuilderDelivCode,LastUpdate,LastUpdatedBy,Expired,ActionLog)values";
		// 根据开始和结束序号拼接批量插入语句
		for (int i = beginNum; i <= endNum; i++) {
			sql += "(?,?,getdate(),?,'F','ADD'),";
			list.add(qz + i + "#");
			list.add(builderDelivCode);
			list.add(lastUpdatedBy);
		}
		//去掉最后一个逗号
		sql=sql.substring(0, sql.length() - 1);
		this.executeUpdateBySql(sql,list.toArray());
	}
	/**
	 * 删除buildnum
	 * @param beginNum
	 */
	public void deleteBuilderNum(BuilderNum builderNum) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tBuilderNum set expired='T' where BuilderDelivCode=?";
		list.add(builderNum.getBuilderDelivCode());
		this.executeUpdateBySql(sql,list.toArray());
	}
}
